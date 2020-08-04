package com.abayomi.stockbay.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.abayomi.stockbay.CustomAdapterHistoric;
import com.abayomi.stockbay.ImageAdpter;
import com.abayomi.stockbay.Model.ModelTotal;
import com.abayomi.stockbay.Model.UploadImg;
import com.abayomi.stockbay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ConfigurationActivity extends AppCompatActivity {

    private CardView txtExit, txtResetSenha, txtTemo, btnSelectPhoto, Viewshare;
    private ImageView img_photo;
    private TextView txtID, txtConfSenha;
    private TextView txtEmail;
    private Button btnsave;
    private EditText editNamePhot;
    private Switch switchDark;

    //Firebase//
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference mDatabaseref;
    private Uri filePath;
    private static final String TAG = "DocSnippets";
    private String urlPht;

    private Uri mSelectUri;
    private final int PICK_IMAGE_REQUEST = 71;
    StorageReference storageReference;
    String userID;
    private FirebaseFirestore db;
    private ProgressBar mProgressbar;

    private ImageAdpter mAdapter;
    private List<UploadImg> mUploads;

    private RecyclerView mRecycleView;
    CustomAdapterHistoric adapter;
    RecyclerView.LayoutManager layoutManager;
    List<ModelTotal> modelTotalList = new ArrayList<>();


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darktheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //Inicialização do firebase//
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        txtID = findViewById(R.id.txtID);
        txtExit = findViewById(R.id.txtExit);
        txtEmail = findViewById(R.id.txtEmail);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        switchDark = findViewById(R.id.switchDark);
        /*btnsave = findViewById(R.id.btnsave);*/
        img_photo = findViewById(R.id.img_photo);
    }

    private void restartApp() {
        Intent i = new Intent(getApplicationContext(), ConfigurationActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        virificar();
    }

    //Pegando Info do Usuário//
    public void virificar() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            finish();
        } else {
            txtID.setText(" " + " " + user.getUid());
            txtEmail.setText("" + user.getEmail());
            Uri photoUrl = user.getPhotoUrl();
        }
    }

    private void selectImg()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar imagem"), PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void savePhoto() {
        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Salvando foto de perfil...");
            progressDialog.show();
            String photo = UUID.randomUUID().toString();
            userID = mAuth.getCurrentUser().getUid();
            final StorageReference ref = storageReference.child(userID).child(UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String urlPhoto = uri.toString();
                                    userID = mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("User").document(userID)
                                            .collection("Photo").document();
                                    Map<String, Object> photo = new HashMap<>();
                                    photo.put("Id", urlPhoto);
                                    documentReference.set(photo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void Void) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ConfigurationActivity.this, "Salvo", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ConfigurationActivity.this, "Falha: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                        }

                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Link dos text de Config//
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtExit:
                FirebaseAuth.getInstance().signOut();
                Intent sair = new Intent(this, MainActivity.class);
                startActivity(sair);
                break;
            case R.id.txtResetSenha:
                Intent ResetSenha = new Intent(this, ResetActivity.class);
                startActivity(ResetSenha);
                break;
            case R.id.txtTemo:
                Toast.makeText(this, "Em desenvolvimento!", Toast.LENGTH_SHORT).show();
              /*  Intent temo = new Intent(this, TermActivity.class);
                startActivity(temo);*/
                break;

            case R.id.Viewvoltar:
                Intent voltar = new Intent(this, PrincipalActivity.class);
                startActivity(voltar);
                break;
          /*  case R.id.Config:
                Intent setting = new Intent(this, ConfigActivity.class);
                startActivity(setting);
                break;*/
            case R.id.notification:
                Intent notification = new Intent(this, NotificationActivity.class);
                startActivity(notification);
                break;
            case R.id.btnSelectPhoto:
                selectImg();
                break;
            case R.id.btnConta:
                Intent account = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(account);
                /*  Toast.makeText(this, "Em desenvolvimento!", Toast.LENGTH_SHORT).show();*/
                break;
            case R.id.Viewshare:
                Toast.makeText(this, "Em desenvolvimento!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Viewhistoric:
                Toast.makeText(this, "Em desenvolvimento!", Toast.LENGTH_SHORT).show();
                /*Intent historic = new Intent(getApplicationContext(), ListHistoricActivity.class);
                startActivity(historic);*/
                break;
        }
    }

    private void share() {
        String msg = "tst";
        if (user != null) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType(msg);
            startActivity(share.createChooser(share, "Compartilhar o App"));
        }
    }
}
