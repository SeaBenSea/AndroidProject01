package com.example.project01;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText studentIDInput, studentNameInput, studentLastNameInput, studentGPAInput, studentAdditionalInfo;
    private RadioButton rdMale, rdFemale, rdFull, rdHalf, rdNone;
    private CheckBox checkAdditionalInfo;
    private Button buttonSubmit, buttonReset, buttonExit;
    private TextView textResult, textCityName;
    private DatePicker dateBirth;
    private Spinner birthPlace, faculty, department;
    private ArrayAdapter<String> facultyAdapter, departmentAdapter;
    private ArrayAdapter<Integer> cityAdapter;
    private String Gender, Birth, Scholarship, AdditionalInfo;
    private Integer[] cityCodes = {1, 2, 3, 4, 5};
    private String[] faculties = {"Faculty of Communication", "Faculty of Engineering and Natural Science", "Faculty of Architecture", "Faculty of Business"};
    private String[] comDepartments = {"Digital Game Design", "Film", "Media"};
    private String[] engDepartments = {"Computer Engineering", "Mechanical Engineering", "Civil Engineering", "Electrical and Electronic Engineering", "Mechatronics Engineering"};
    private String[] arcDepartments = {"Architecture", "Industrial Design", "Interior Design"};
    private String[] busDepartments = {"Economics", "Business Administration"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentIDInput = findViewById(R.id.StudentIDInput);
        studentNameInput = findViewById(R.id.studentNameInput);
        studentLastNameInput = findViewById(R.id.studentLastNameInput);
        studentGPAInput = findViewById(R.id.studentGPAInput);
        studentAdditionalInfo = findViewById(R.id.studentAdditionalInfo);

        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);
        rdMale.setChecked(true);

        rdFull = findViewById(R.id.rdFull);
        rdHalf = findViewById(R.id.rdHalf);
        rdNone = findViewById(R.id.rdNone);
        rdFull.setChecked(true);

        checkAdditionalInfo = findViewById(R.id.checkAdditionalInfo);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonReset = findViewById(R.id.buttonReset);
        buttonExit = findViewById(R.id.buttonExit);

        textResult = findViewById(R.id.textResult);
        textCityName = findViewById(R.id.textcityName);

        dateBirth = findViewById(R.id.dateBirth);

        birthPlace = findViewById(R.id.cityID);
        faculty = findViewById(R.id.facultyID);
        department = findViewById(R.id.departmentID);
        cityAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, cityCodes);
        facultyAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, faculties);
        faculty.setAdapter(facultyAdapter);
        faculty.setOnItemSelectedListener(this);

        birthPlace.setAdapter(cityAdapter);
        birthPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    textCityName.setText("Ankara");
                } else if (i == 1) {
                    textCityName.setText("Istanbul");
                } else if (i == 2) {
                    textCityName.setText("Eskisehir");
                } else if (i == 3) {
                    textCityName.setText("Izmir");
                } else if (i == 4) {
                    textCityName.setText("Bursa");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    public void onCheckBoxClicked(View v) {
        boolean checked = ((CheckBox) v).isChecked();

        if (checked) {
            studentAdditionalInfo.setVisibility(View.VISIBLE);
        } else {
            studentAdditionalInfo.setVisibility(View.INVISIBLE);
        }
    }

    public void submit(View v) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        if (studentIDInput.getText().toString().length() != 11) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your id must be have length of 11", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else if (studentNameInput.getText().toString().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your name can't be blank!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else if (studentLastNameInput.getText().toString().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your last name can't be blank!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        else if (studentGPAInput.getText().toString().length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your GPA must be a number between 0 and 4", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }
        else if (Double.parseDouble(studentGPAInput.getText().toString()) < 0 || Double.parseDouble(studentGPAInput.getText().toString()) > 4.0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your GPA must be a number between 0 and 4", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }
        else if (checkAdditionalInfo.isChecked() && studentAdditionalInfo.getText().toString().length() == 0){
            Toast toast = Toast.makeText(getApplicationContext(), "Additional info can't be blank!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        else {

            Gender = rdMale.isChecked() ? "Male" : "Female";
            Birth = String.valueOf(dateBirth.getDayOfMonth() + "/" + (dateBirth.getMonth() + 1) + "/" + dateBirth.getYear());
            if (rdFull.isChecked()) {
                Scholarship = "Full";
            } else if (rdHalf.isChecked()) {
                Scholarship = "Half";
            } else {
                Scholarship = "None";
            }

            if (checkAdditionalInfo.isChecked()) {
                AdditionalInfo = studentAdditionalInfo.getText().toString();
            } else {
                AdditionalInfo = "";
            }


            textResult.setText(Html.fromHtml("ID: " + "<font color=red>" + studentIDInput.getText().toString() + "</font><br>" +
                    "Name: " + "<font color=red>" + studentNameInput.getText().toString() + "</font><br>" +
                    "Last Name: " + "<font color=red>" + studentLastNameInput.getText().toString() + "</font><br>" +
                    "Birth Date: " + "<font color=red>" + Birth + "</font><br>" +
                    "Birth Place: " + "<font color=red>" + textCityName.getText().toString() + "</font><br>" +
                    "Gender: " + "<font color=red>" + Gender + "</font><br>" +
                    "Faculty: " + "<font color=red>" + faculty.getSelectedItem().toString() + "</font><br>" +
                    "Department: " + "<font color=red>" + department.getSelectedItem().toString() + "</font><br>" +
                    "GPA: " + "<font color=red>" + studentGPAInput.getText().toString() + "</font><br>" +
                    "Scholarship: " + "<font color=red>" + Scholarship + "</font><br>" +
                    "Additional Info: " + "<font color=red>" + AdditionalInfo + "</font><br>"));


        }

    }

    public void reset(View v) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        studentIDInput.setText("");
        studentNameInput.setText("");
        studentLastNameInput.setText("");
        dateBirth.init(mYear, mMonth, mDay, null);
        birthPlace.setSelection(0);
        rdMale.setChecked(true);
        faculty.setSelection(0);
        department.setSelection(0);
        studentGPAInput.setText("");
        rdFull.setChecked(true);
        checkAdditionalInfo.setChecked(false);
        studentAdditionalInfo.setVisibility(View.INVISIBLE);
        studentAdditionalInfo.setText("");
        textResult.setText(Html.fromHtml("ID: " + "<font color=red>" + "" + "</font><br>" +
                "Name: " + "<font color=red>" + "" + "</font><br>" +
                "Last Name: " + "<font color=red>" + "" + "</font><br>" +
                "Birth Date: " + "<font color=red>" + "" + "</font><br>" +
                "Birth Place: " + "<font color=red>" + "" + "</font><br>" +
                "Gender: " + "<font color=red>" + "" + "</font><br>" +
                "Faculty: " + "<font color=red>" + "" + "</font><br>" +
                "Department: " + "<font color=red>" + "" + "</font><br>" +
                "GPA: " + "<font color=red>" + "" + "</font><br>" +
                "Scholarship: " + "<font color=red>" + "" + "</font><br>" +
                "Additional Info: " + "<font color=red>" + "" + "</font><br>"));

    }

    public void exit(View v) {
        finish();
        System.exit(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            departmentAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, comDepartments);
            department.setAdapter(departmentAdapter);
        } else if (i == 1) {
            departmentAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, engDepartments);
            department.setAdapter(departmentAdapter);
        } else if (i == 2) {
            departmentAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arcDepartments);
            department.setAdapter(departmentAdapter);
        } else if (i == 3) {
            departmentAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, busDepartments);
            department.setAdapter(departmentAdapter);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}