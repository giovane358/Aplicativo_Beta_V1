package com.abayomi.stockbay;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfigActivity extends AppCompatActivity {

    private CardView txtExit, txtResetSenha, txtTemo;
    private ImageView Viewvoltar, Viewshare, img_photo;
    private TextView txtID;
    private TextView txtEmail;
    private Button btnSelectPhoto, btnsave;
    private EditText editNamePhot;

    //Firebase//
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Inicialização do firebase//
        db  = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("Photo");
        mDatabaseref = FirebaseDatabase.getInstance().getReference("Photo");
        txtExit = findViewById(R.id.txtExit);
        txtEmail = findViewById(R.id.txtEmail);
        txtID = findViewById(R.id.txtID);
        Viewvoltar = findViewById(R.id.Viewvoltar);
        Viewshare = findViewById(R.id.Viewshare);
        btnSelectPhoto = findViewById(R.id.btnSelectPhoto);
        btnsave = findViewById(R.id.btnsave);
        img_photo = findViewById(R.id.img_photo);

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

            txtID.setText("ID: " + " " + user.getUid());
            txtEmail.setText("" + user.getEmail());
            Uri photoUrl = user.getPhotoUrl();

        }
    }

    private void selectImg() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecionar imagem"), PICK_IMAGE_REQUEST);
        btnSelectPhoto.setAlpha(0);
        savePhoto();

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void savePhotoFirestore()
    {
        if (filePath != null) {
            String idP = UUID.randomUUID().toString();
            userID = mAuth.getCurrentUser().getUid();
            DocumentReference documentReference = db.collection("User").document(userID)
                    .collection("Photo").document(idP);
            Map<String, Object> photo = new HashMap<>();
            photo.put("Id", idP);
            documentReference.set(photo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void Void) {
                    Toast.makeText(ConfigActivity.this, "Produto Registrado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent Sucesso = new Intent(getApplicationContext(), ConfigActivity.class);
                    startActivity(Sucesso);
                }
            });
        }
    }

    private void savePhoto() {
        if (filePath != null) {

            String photo = UUID.randomUUID().toString();
            userID = mAuth.getCurrentUser().getUid();
            final StorageReference ref = storageReference.child("PhotoPerfil/").child(userID).child (UUID.randomUUID().toString());
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
                                            Toast.makeText(ConfigActivity.this, "Salvo", Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ConfigActivity.this, "Falha: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
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
                Intent temo = new Intent(this, TermActivity.class);
                startActivity(temo);
                break;

            case R.id.Viewvoltar:
                Intent voltar = new Intent(this, PrincipalActivity.class);
                startActivity(voltar);
                break;
            case R.id.Config:
                Intent setting = new Intent(this, ConfigActivity.class);
                startActivity(setting);
                break;
            case R.id.notification:
                Intent notification = new Intent(this, NotificationActivity.class);
                startActivity(notification);
                break;
            case R.id.btnSelectPhoto:
                selectImg();
                break;
            case R.id.btnsave:
                savePhoto();
                break;

        }
    }


}
