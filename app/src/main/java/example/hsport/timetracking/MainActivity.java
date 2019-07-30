package example.hsport.timetracking;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import example.hsport.timetracking.db.DbHelper;
import example.hsport.timetracking.db.TimeDataContract;

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
    protected void onStart() {
        super.onStart();
        initFromDb();
    }

    private void initFromDb() {
        // Deactivate buttons
        _startCommand.setEnabled(false);
        _endCommand.setEnabled(false);

        // See if there is an open dataset (means, that end should be enabled)
        Cursor data = getContentResolver().query(
                TimeDataContract.TimeData.NOT_FINISHED_CONTENT_URI,
                new String[]{TimeDataContract.TimeData.Columns.START_TIME},
                null,
                null,
                null);

        // Check if data is available
        if (data.moveToFirst()) {
            try {
                Calendar startTime = TimeDataContract.Converter.parse(data.getString((0));
                _startDateTime.setText(_dateTimeFormatter.format(startTime.getTime()));
            } catch (ParseException e) {
                // Error while converting starttime
                _startDateTime.setText("Falsches Datumformat in der Datenbank");
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        _startCommand.setOnClickListener(new StartButtonClicked());
        _endCommand.setOnClickListener(new EndButtonClick());

//        _startCommand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Startbutton geklickt!", Toast.LENGTH_SHORT).show();
//
//
//                Calendar currentTime = Calendar.getInstance();
//                _startDateTime.setText(_dateTimeFormatter.format(currentTime.getTime()));
//
//                // Init database and dbhelper
//                DbHelper dbHelper = new DbHelper(getApplicationContext());
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//                // Prepare what is gonna be written in the database
//                DateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.GERMANY);
//
//                ContentValues values = new ContentValues();
//                //values.put("start_time", currentTime.getTime().toString());
//
//                values.put("start_time", dbFormat.format(currentTime.getTime())); //better way of getting the time but not working
//
//
//                // Actually put stuff in the database
//                db.insert(
//                        "time_data",
//                        null,
//                        values
//                );
//
//                // Close the database to save memory
//                db.close();
//                dbHelper.close();
//
//
//            }
//        });


//        _endCommand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Beenden geklickt!", Toast.LENGTH_SHORT).show();
//
//
//                Calendar currentTime = Calendar.getInstance();
//                _endDateTime.setText(_dateTimeFormatter.format(currentTime.getTime()));
//
//            }
//        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        _startCommand.setOnClickListener(null);
        _endCommand.setOnClickListener(null);


    }

    class StartButtonClicked implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Toast.makeText(MainActivity.this, "Startbutton geklickt!", Toast.LENGTH_SHORT).show();

            // Get current time
            Calendar currentTime = Calendar.getInstance();

            // Convert for db
            String dbTime = TimeDataContract.Converter.format(currentTime);

            // Time for db
            ContentValues values = new ContentValues();
            values.put(TimeDataContract.TimeData.Columns.START_TIME, dbTime);

            // Save into db
            getContentResolver().insert(TimeDataContract.TimeData.CONTENT_URI, values);

            // Show in UI
            _startDateTime.setText(_dateTimeFormatter.format(currentTime.getTime()));

        }

    }

    class EndButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // Current time
            Calendar currentTime = Calendar.getInstance();

            // Convert for db
            String dbTime = TimeDataContract.Converter.format(currentTime);

            // Time for db
            ContentValues values = new ContentValues();
            values.put(TimeDataContract.TimeData.Columns.END_TIME, dbTime);

            // Save in db
            getContentResolver().update(TimeDataContract.TimeData.NOT_FINISHED_CONTENT_URI,
                    values, null, null);

            // Output for UI
            _endDateTime.setText(_dateTimeFormatter.format(currentTime.getTime()));

        }

    }
}
