package com.test.testsat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.test.testsat.Models.putPDF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Designer extends AppCompatActivity {
    private static final String TAG = "Activity_Login";
    EditText tname,tmail,tphone,tdob,thome,tcapability,tterms,tcnic,tpages,trate, editText;
    Button btn_save,btn;
    private Spinner spinner;
    Members member;
    DatabaseReference reff, databaseReference;
    StorageReference storageReference;
    FirebaseDatabase database;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    //for spinner variables choose qualification
    TextView selected;
    Membber membber;

    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);
        //dateof birth
        mDisplayDate=(TextView)findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Designer.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));          //we can convert DKGRAY into TRANSPARENCE
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                Log.d(TAG,"onDateSet: mm/dd/yyy:"+month+"/"+day+"/"+year);
                String date =month+ "/"+day+"/"+year;
                mDisplayDate.setText(date);
            }
        };

        //upload cv to firebase
        editText=findViewById(R.id.editText);
        btn_save=findViewById(R.id.btn_save);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("upload CV");

        btn_save.setEnabled(true);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });

        //spinner c


        tname=(EditText)findViewById(R.id.edt_name);
        tmail=(EditText)findViewById(R.id.edt_mail);
        tphone=(EditText)findViewById(R.id.phone_number);
        mDisplayDate=(TextView) findViewById(R.id.tvDate);
        thome=(EditText)findViewById(R.id.home_add);
        tcapability=(EditText)findViewById(R.id.capability_perpage);
        tterms=(EditText)findViewById(R.id.terms_conditions);
        tcnic=(EditText)findViewById(R.id.cnic);
        tpages=(EditText)findViewById(R.id.pages) ;
        trate=(EditText)findViewById(R.id.rate) ;
        //spinner
        selected=findViewById(R.id.choose_qualification);
        spinner=findViewById(R.id.spinner_qualification);
        btn=findViewById(R.id.btn);
        membber=new Membber();
        reff=database.getInstance().getReference().child("choose");
        List<String>Categories=new ArrayList<>();
        Categories.add(0,"choose capability");
        Categories.add("Single Color ");
        Categories.add("Double Color");
        Categories.add("3 Color");
        Categories.add("4 Color");
        Categories.add("Editing");
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
                Toast.makeText(Designer.this,"value Stored Successfully",Toast.LENGTH_SHORT).show();
                reff.child(String.valueOf(maxid+1)).setValue(membber);
            }

        });
        ///spinner capability speed

        ////

        btn_save=(Button)findViewById(R.id.btn_save);
        member=new Members();
        reff=FirebaseDatabase.getInstance().getReference().child("DESIGNER");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Long phn=Long.parseLong(tphone.getText().toString().trim());
                member.setName(tname.getText().toString().trim());
                member.setMail(tmail.getText().toString().trim());
                member.setPhone(tphone.getText().toString().trim());
                member.setDOB(mDisplayDate.getText().toString().trim());
                member.setHome(thome.getText().toString().trim());
                member.setCapability(tcapability.getText().toString().trim());
                member.setTerms(tterms.getText().toString().trim());
                member.setCnic(tcnic.getText().toString().trim());
                member.setPages(tpages.getText().toString().trim());
                member.setRate(trate.getText().toString().trim());
                //membe
                membber.setSpinner(spinner.getSelectedItem().toString().trim());
                reff.push().setValue(member);
                Toast.makeText(Designer.this,"data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectPDF() {
        Intent intent= new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            btn_save.setEnabled(true);
            editText.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/")+1));
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadPDFFileFirebase(data.getData());
                }
            });
        }
    }
    private void uploadPDFFileFirebase(Uri data){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading");
        progressDialog.show();

        StorageReference reference=storageReference.child("upload"+System.currentTimeMillis()+".pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putPDF putPDF = new putPDF(editText.getText().toString(),uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                        Toast.makeText(Designer.this,"CV UPLOADED",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress=(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("CV UPLOADED.."+ (int) progress+"%");

            }
        });

    }
}