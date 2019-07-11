package example.hsport.timetracking;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import example.hsport.timetracking.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    private EditText _startDateTime;
    private EditText _endDateTime;
    private Button _startCommand;
    private Button _endCommand;

    private final DateFormat _dateTimeFormatter = DateFormat.getDateTimeInstance(
            DateFormat.SHORT,
            DateFormat.LONG);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get references to the layout-elements
        _startDateTime = findViewById(R.id.StartDateTime);
        _endDateTime = findViewById(R.id.EndDateTime);
        _startCommand = findViewById(R.id.StartCommand);
        _endCommand = findViewById(R.id.EndCommand);

    }

    @Override
    protected void onResume() {
        super.onResume();

        _startCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Startbutton geklickt!", Toast.LENGTH_SHORT).show();


                Calendar currentTime = Calendar.getInstance();
                _startDateTime.setText(_dateTimeFormatter.format(currentTime.getTime()));

                // Init database and dbhelper
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Prepare what is gonna be written in the database
                DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd'HH:mm", Locale.GERMANY);

                ContentValues values = new ContentValues();
                values.put("start_time", dbFormat.format(currentTime.getTime()));

                // Actually put stuff in the database
                db.insert(
                        "time_data",
                        null,
                        values
                );

                // Close the database to save memory
                db.close();
                dbHelper.close();


            }
        });


        _endCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Beenden geklickt!", Toast.LENGTH_SHORT).show();


                Calendar currentTime = Calendar.getInstance();
                _endDateTime.setText(_dateTimeFormatter.format(currentTime.getTime()));

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        _startCommand.setOnClickListener(null);
        _endCommand.setOnClickListener(null);


    }
}
