package com.test.testsat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
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

public class ComposerActivity extends AppCompatActivity {
    private static final String TAG = "Activity_Login";
    // Composer Data Variables
    NumberPicker numberPickerSC1,numberPickerSC2,numberPickerSC3,numberPickerSC4,numberPickerSC5,numberPickerSC6,
    numberPickerDC1,numberPickerDC2,numberPickerDC3,numberPickerDC4,numberPickerDC5,numberPickerDC6;
    TextView textSC1,textSC2,textSC3,textSC4,textSC5,textSC6,textDC1,textDC2,textDC3,textDC4,textDC5,textDC6;


    EditText tname,tmail,tphone,thome,tcapability,tterms,tpages,trate,tcnic;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button btn_save,btn;
    private Spinner spinner;
    Members member;
    DatabaseReference reff;
    FirebaseDatabase database;
    //for spinner variables choose qualification
    TextView selected;
   // Membber membber;

    int maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer);

        // Composer Single Column number Picker
        textSC1=(TextView)findViewById(R.id.text_SC_1);
        numberPickerSC1=(NumberPicker)findViewById(R.id.numberPicker_SC_1);
        numberPickerSC1.setMinValue(0);
        numberPickerSC1.setMaxValue(50);
        numberPickerSC1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerSC1.getValue();
            }
        });

        textSC2=(TextView)findViewById(R.id.text_SC_2);
        numberPickerSC2=(NumberPicker)findViewById(R.id.numberPicker_SC_2);
        numberPickerSC2.setMinValue(0);
        numberPickerSC2.setMaxValue(25);
        numberPickerSC2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerSC2.getValue();
            }
        });

        textSC3=(TextView)findViewById(R.id.text_SC_3);
        numberPickerSC3=(NumberPicker)findViewById(R.id.numberPicker_SC_3);
        numberPickerSC3.setMinValue(0);
        numberPickerSC3.setMaxValue(29);
        numberPickerSC3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerSC3.getValue();
            }
        });

        textSC4=(TextView)findViewById(R.id.text_SC_4);
        numberPickerSC4=(NumberPicker)findViewById(R.id.numberPicker_SC_4);
        numberPickerSC4.setMinValue(0);
        numberPickerSC4.setMaxValue(13);
        numberPickerSC4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerSC4.getValue();
            }
        });

        textSC5=(TextView)findViewById(R.id.text_SC_5);
        numberPickerSC5=(NumberPicker)findViewById(R.id.numberPicker_SC_5);
        numberPickerSC5.setMinValue(0);
        numberPickerSC5.setMaxValue(17);
        numberPickerSC5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerSC5.getValue();
            }
        });

        textSC6=(TextView)findViewById(R.id.text_SC_6);
        numberPickerSC6=(NumberPicker)findViewById(R.id.numberPicker_SC_6);
        numberPickerSC6.setMinValue(0);
        numberPickerSC6.setMaxValue(19);
        numberPickerSC6.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerSC6.getValue();
            }
        });

        textDC1=(TextView)findViewById(R.id.text_DC_1);
        numberPickerDC1=(NumberPicker)findViewById(R.id.numberPicker_DC_1);
        numberPickerDC1.setMinValue(0);
        numberPickerDC1.setMaxValue(21);
        numberPickerDC1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDC1.getValue();
            }
        });

        textDC2=(TextView)findViewById(R.id.text_DC_2);
        numberPickerDC2=(NumberPicker)findViewById(R.id.numberPicker_DC_2);
        numberPickerDC2.setMinValue(0);
        numberPickerDC2.setMaxValue(29);
        numberPickerDC2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDC3.getValue();
            }
        });

        textDC3=(TextView)findViewById(R.id.text_DC_3);
        numberPickerDC3=(NumberPicker)findViewById(R.id.numberPicker_DC_3);
        numberPickerDC3.setMinValue(0);
        numberPickerDC3.setMaxValue(39);
        numberPickerDC3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDC3.getValue();
            }
        });

        textDC4=(TextView)findViewById(R.id.text_DC_4);
        numberPickerDC4=(NumberPicker)findViewById(R.id.numberPicker_DC_4);
        numberPickerDC4.setMinValue(0);
        numberPickerDC4.setMaxValue(47);
        numberPickerDC4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDC4.getValue();
            }
        });

        textDC5=(TextView)findViewById(R.id.text_DC_5);
        numberPickerDC5=(NumberPicker)findViewById(R.id.numberPicker_DC_5);
        numberPickerDC5.setMinValue(0);
        numberPickerDC5.setMaxValue(67);
        numberPickerDC5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDC5.getValue();
            }
        });

        textDC6=(TextView)findViewById(R.id.text_DC_6);
        numberPickerDC6=(NumberPicker)findViewById(R.id.numberPicker_DC_6);
        numberPickerDC6.setMinValue(0);
        numberPickerDC6.setMaxValue(79);
        numberPickerDC6.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numberPickerDC6.getValue();
            }
        });




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
                        ComposerActivity.this,
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
        tpages=(EditText)findViewById(R.id.pages) ;
        trate=(EditText)findViewById(R.id.rate) ;
        textSC1=(TextView)findViewById(R.id.text_SC_1);
        textSC2=(TextView)findViewById(R.id.text_SC_2);
        textSC3=(TextView)findViewById(R.id.text_SC_3);
        textSC4=(TextView)findViewById(R.id.text_SC_4);
        textSC5=(TextView)findViewById(R.id.text_SC_5);
        textSC6=(TextView)findViewById(R.id.text_SC_6);
        textDC1=(TextView)findViewById(R.id.text_DC_1);
        textDC2=(TextView)findViewById(R.id.text_DC_2);
        textDC3=(TextView)findViewById(R.id.text_DC_3);
        textDC4=(TextView)findViewById(R.id.text_DC_4);
        textDC5=(TextView)findViewById(R.id.text_DC_5);
        textDC6=(TextView)findViewById(R.id.text_DC_6);
        numberPickerSC1=(NumberPicker)findViewById(R.id.numberPicker_SC_1);
        numberPickerSC2=(NumberPicker)findViewById(R.id.numberPicker_SC_2);
        numberPickerSC3=(NumberPicker)findViewById(R.id.numberPicker_SC_3);
        numberPickerSC4=(NumberPicker)findViewById(R.id.numberPicker_SC_4);
        numberPickerSC5=(NumberPicker)findViewById(R.id.numberPicker_SC_5);
        numberPickerSC6=(NumberPicker)findViewById(R.id.numberPicker_SC_6);
        numberPickerDC1=(NumberPicker)findViewById(R.id.numberPicker_DC_1);
        numberPickerDC2=(NumberPicker)findViewById(R.id.numberPicker_DC_2);
        numberPickerDC3=(NumberPicker)findViewById(R.id.numberPicker_DC_3);
        numberPickerDC4=(NumberPicker)findViewById(R.id.numberPicker_DC_4);
        numberPickerDC5=(NumberPicker)findViewById(R.id.numberPicker_DC_5);
        numberPickerDC6=(NumberPicker)findViewById(R.id.numberPicker_DC_6);
        //spinner
      //  selected=findViewById(R.id.choose_qualification);
       // spinner=findViewById(R.id.spinner_qualification);
        //btn=findViewById(R.id.btn);
        //membber=new Membber();
        //reff=database.getInstance().getReference().child("COMPOSER SPIN");
       // List<String>Categories=new ArrayList<>();
        //Categories.add(0,"choose capability");
        //Categories.add("Single Column  Size 6.25*8.5 12pt  " );


        //remove the line spacing altready had given also delete it from the database
        //Categories.add("Single Column  Size 6.25*8.5 14pt   ");
        //remove the line spacing altready had given also delete it from the database
        //Categories.add("Double Column  Size 6.25*8.5 12pt  ");
        //remove the line spacing altready had given also delete it from the database
        //Categories.add("Double Column  Size 6.25*8.5 14pt  ");
        //remove the line spacing altready had given also delete it from the database
        //Categories.add("Double Column  Size 6.25*8.5 12pt  ");
        //ArrayAdapter<String> dataAdapter;
        //dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,Categories);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     //   spinner.setAdapter(dataAdapter);
     //   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       //     @Override
         //   public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           //     if(adapterView.getItemAtPosition(i).equals("choose qualification")){

             //   }else{
               //     selected.setText(adapterView.getSelectedItem().toString());
           //     }
            //}

            //@Override
            //public void onNothingSelected(AdapterView<?> parent) {
///
            //}
        //});

        //reff.addValueEventListener(new ValueEventListener() {
          //  @Override
           // public void onDataChange(@NonNull DataSnapshot datasnapshot) {
             //   if(datasnapshot.exists()){
               //     maxid=(int)datasnapshot.getChildrenCount();
                //}
           // }

            //@Override
            //public void onCancelled(@NonNull DatabaseError error) {

            //}
        //});
        //btn.setOnClickListener(new View.OnClickListener() {
          ///  @Override
            //public void onClick(View v) {
              //  membber.setSpinner(spinner.getSelectedItem().toString());
               // Toast.makeText(ComposerActivity.this,"value Stored Successfully",Toast.LENGTH_SHORT).show();
                //reff.child(String.valueOf(maxid+1)).setValue(membber);
            //}

       // });
        ///spinner next space






        ////

        btn_save=(Button)findViewById(R.id.btn_save);
        member=new Members();
        reff=FirebaseDatabase.getInstance().getReference().child("COMPOSER");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Long phn=Long.parseLong(tphone.getText().toString().trim());
                member.setName(tname.getText().toString().trim());
                member.setMail(tmail.getText().toString().trim());
                member.setPhone(tphone.getText().toString().trim());
                member.setDOB(mDisplayDate.getText().toString().trim());
                member.setHome(thome.getText().toString().trim());
//                member.setCapability(tcapability.getText().toString().trim());
                member.setPages(tpages.getText().toString().trim());
                member.setRate(trate.getText().toString().trim());
                member.setTerms(tterms.getText().toString().trim());
//                member.setCnic(tcnic.getText().toString().trim());
                member.setTextSC1(textSC1.getText().toString().trim());
                member.setTextSC2(textSC2.getText().toString().trim());
                member.setTextSC3(textSC3.getText().toString().trim());
                member.setTextSC4(textSC4.getText().toString().trim());
                member.setTextSC5(textSC5.getText().toString().trim());
                member.setTextSC6(textSC6.getText().toString().trim());
                member.setTextDC1(textDC1.getText().toString().trim());
                member.setTextDC2(textDC2.getText().toString().trim());
                member.setTextDC3(textDC3.getText().toString().trim());
                member.setTextDC4(textDC4.getText().toString().trim());
                member.setTextDC5(textDC5.getText().toString().trim());
                member.setTextDC6(textDC6.getText().toString().trim());
                member.setNumberPickerSC1(numberPickerSC1.getValue());
                member.setNumberPickerSC2(numberPickerSC2.getValue());
                member.setNumberPickerSC3(numberPickerSC3.getValue());
                member.setNumberPickerSC4(numberPickerSC4.getValue());
                member.setNumberPickerSC5(numberPickerSC5.getValue());
                member.setNumberPickerSC6(numberPickerSC6.getValue());
                member.setNumberPickerDC1(numberPickerDC1.getValue());
                member.setNumberPickerDC2(numberPickerDC2.getValue());
                member.setNumberPickerDC3(numberPickerDC3.getValue());
                member.setNumberPickerDC4(numberPickerDC4.getValue());
                member.setNumberPickerDC5(numberPickerDC5.getValue());
                member.setNumberPickerDC6(numberPickerDC6.getValue());
             //   membber.setSpinner(spinner.getSelectedItem().toString().trim());
                reff.push().setValue(member);
                Toast.makeText(ComposerActivity.this,"data inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}