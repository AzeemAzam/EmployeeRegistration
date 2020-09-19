package com.test.testsat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class Books extends AppCompatActivity {
    EditText tname,tmail,tphone,tdob,thome,tcapability,tterms,tcnic;
    Button btn_save,btn;
    private Spinner spinner;
    Members member;
    DatabaseReference reff;
    FirebaseDatabase database;
    //for spinner variables choose qualification
    TextView selected;
    Membber membber;



    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        //spinner c


        tname=(EditText)findViewById(R.id.edt_name);
        tmail=(EditText)findViewById(R.id.edt_mail);
        tphone=(EditText)findViewById(R.id.phone_number);
        tdob=(EditText)findViewById(R.id.dob);
        thome=(EditText)findViewById(R.id.home_add);
        tcapability=(EditText)findViewById(R.id.capability_perpage);
        tterms=(EditText)findViewById(R.id.terms_conditions);
        tcnic=(EditText)findViewById(R.id.cnic);
        //spinner
        selected=findViewById(R.id.choose_qualification);
        spinner=findViewById(R.id.spinner_qualification);
        btn=findViewById(R.id.btn);
        membber=new Membber();
        reff=database.getInstance().getReference().child("choose");
        List<String>Categories=new ArrayList<>();
        Categories.add(0,"Books All copyrights");
        Categories.add("Distributing to all segments");
        Categories.add("Distribute with specified segments");
        Categories.add("Publis local books");
        Categories.add("Behavioural Books");
        Categories.add("Model papers and Key Books");
        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,Categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("choose qualification")){

                }else{
                    selected.setText(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
///
            }
        });

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    maxid=(int)datasnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                membber.setSpinner(spinner.getSelectedItem().toString());
                Toast.makeText(Books.this,"value Stored Successfully",Toast.LENGTH_SHORT).show();
                reff.child(String.valueOf(maxid+1)).setValue(membber);
            }

        });
        ///spinner capability speed

        ////

        btn_save=(Button)findViewById(R.id.btn_save);
        member=new Members();
        reff=FirebaseDatabase.getInstance().getReference().child("Books");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Long phn=Long.parseLong(tphone.getText().toString().trim());
                member.setName(tname.getText().toString().trim());
                member.setMail(tmail.getText().toString().trim());
                member.setPhone(tphone.getText().toString().trim());
                member.setDOB(tdob.getText().toString().trim());
                member.setHome(thome.getText().toString().trim());
                member.setCapability(tcapability.getText().toString().trim());
                member.setTerms(tterms.getText().toString().trim());
                member.setCnic(tcnic.getText().toString().trim());
                membber.setSpinner(spinner.getSelectedItem().toString().trim());
                reff.push().setValue(member);
                Toast.makeText(Books.this,"data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    public void handleImageClick(View view) {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_IMAGE_CODE) {
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                    profileImageView.setImageBitmap(bitmap);
                    handleUpload(bitmap);

            }

        }
    }
    private void handleUpload (Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference= FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid+".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      //  Log.e(com.test.testsat.TAG,"onFailure: ",e.getCause());
                    }
                });
    }
    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       // Log.d("onSuccess: "+uri);
                        setUserProfileUrl(uri);
                    }
                });
    }
    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Books.this, "Update success", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Books.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

 */
}