package com.test.testsat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


///

public class HelpingBooks extends AppCompatActivity {

    EditText tname,tmail,tphone,tdob,thome,tcapability,tterms,tcnic;
    Button btn_save,btn;
    private Spinner spinner;
    Members member;
    DatabaseReference reff;
    FirebaseDatabase database1;
    //firebase storage image
    private StorageReference objectStorageReference;
    private FirebaseFirestore objectFirebaseFirestore;
    //for spinner variables choose qualification
    TextView selected;
    Membber membber;

    int maxid=0;
    //profile pic class variables
    private CircleImageView profilIV;
    private Uri profileImageURL;
    private static int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_books);
        attachjavatoXMLObjects();

        //firebase storage
       objectStorageReference=FirebaseStorage.getInstance().getReference("ImageFolder");
       btn_save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               uploadUserDataToFirebase();
           }
       });

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
        reff=database1.getInstance().getReference().child("choose");
        List<String>Categories=new ArrayList<>();
        Categories.add(0,"choose capability");
        Categories.add("10 hrs per page");
        Categories.add("8 hrs per page");
        Categories.add("6 hrs per page");
        Categories.add("5 hrs per page");
        Categories.add("4 hrs per page");
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
                Toast.makeText(HelpingBooks.this,"value Stored Successfully",Toast.LENGTH_SHORT).show();
                reff.child(String.valueOf(maxid+1)).setValue(membber);
            }

        });
        ///spinner capability speed

        ////

        btn_save=(Button)findViewById(R.id.btn_save);
        member=new Members();
        reff=FirebaseDatabase.getInstance().getReference().child("Helping Books");
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
                Toast.makeText(HelpingBooks.this,"data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //profile pic
    private void attachjavatoXMLObjects(){
        try {
            profilIV=findViewById(R.id.RegisterPage_userProfileIV);
            btn_save=findViewById(R.id.btn_save);

            profilIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseImageFromMobileGallery();
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(this,"Reister Helping Book"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void chooseImageFromMobileGallery()
    {
        try {
            openMobileGallery();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Reister Helping Book"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void openMobileGallery()
    {
        try {
            Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");

            startActivityForResult(galleryIntent,REQUEST_CODE);
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Reister Helping Book"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData()!=null && data!=null)
        {
            profileImageURL=data.getData();
            profilIV.setImageURI(profileImageURL);
        }
        else
        {
            Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show();
        }
    }


    //Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));


/*
riversRef.putFile(file)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // Get a URL to the uploaded content
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
        }
    })
            .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads
            // ...
        }
    });

 */
private void uploadUserDataToFirebase()
{
    try {
        {
            if (profileImageURL!=null)
            {
                String imageName=tname.getText().toString()+"."+getExtension(profileImageURL);
                final StorageReference imageRef=objectStorageReference.child("s");

                UploadTask objectUploadTask=imageRef.putFile(profileImageURL);
                objectUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        return  imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            Map<String,Object>objectMap=new HashMap<>();
                            objectMap.put("profileimageurl",task.getResult().toString());

                            objectMap.put("nooninformation",0);
                            objectMap.put("noimagestatus",0);
                        }
                        else if (!task.isSuccessful())
                        {
                            Toast.makeText(HelpingBooks.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else
            {
                Toast.makeText(this, "Please Choose a Profile Image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    catch (Exception e)
    {
        Toast.makeText(this, "Upload Data", Toast.LENGTH_SHORT).show();
    }
}

private String getExtension(Uri uri){
    try {
        ContentResolver objectContentResolver=getContentResolver();
        MimeTypeMap objectMimeTypeMap=MimeTypeMap.getSingleton();

        return objectMimeTypeMap.getExtensionFromMimeType(objectContentResolver.getType(uri));

    }
    catch (Exception e)
    {
        Toast.makeText(this, "Register Page", Toast.LENGTH_SHORT).show();
        return null;
    }
}
}