package com.test.testsat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class proman extends AppCompatActivity {
    private static final String TAG = "Activity_Login";
EditText tname,tmail,tphone,tdob,thome,tcapability,tterms,tcnic;
Button btn_save,btn;
private Spinner spinner;
Members member;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
DatabaseReference reff;
FirebaseDatabase database;
    private CircleImageView profilIV;
    private Uri profileImageURL;
    private static int REQUEST_CODE=1;


//for spinner variables choose qualification
    TextView selected;
Membber membber;

int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proman);

        attachjavatoXMLObjects();


        mDisplayDate=(TextView)findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        proman.this,
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

        //spinner c


        tname=(EditText)findViewById(R.id.edt_name);
        tmail=(EditText)findViewById(R.id.edt_mail);
        tphone=(EditText)findViewById(R.id.phone_number);
        mDisplayDate=(TextView) findViewById(R.id.tvDate);
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
        Categories.add(0,"choose capability");
        Categories.add("Concentrated workمرتکز کام کی صلاحیت ");
        Categories.add("Work with Flow  بہاؤ میں کام کی صلاحیت");
        Categories.add("Team Working and Leadingمشترکہ کام کریں");
        Categories.add("Managing the specified Tasksخاص کام کو ترتیب دیں");
        Categories.add("Can Manage all above fields");
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
                Toast.makeText(proman.this,"value Stored Successfully",Toast.LENGTH_SHORT).show();
                reff.child(String.valueOf(maxid+1)).setValue(membber);
            }

        });
        ///spinner capability speed

        ////

        btn_save=(Button)findViewById(R.id.btn_save);
        member=new Members();
        reff=FirebaseDatabase.getInstance().getReference().child("Project Manager");
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
                membber.setSpinner(spinner.getSelectedItem().toString().trim());
                reff.push().setValue(member);
                Toast.makeText(proman.this,"data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

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

}