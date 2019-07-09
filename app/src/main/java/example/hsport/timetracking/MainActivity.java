package example.hsport.timetracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText _startDateTime;
    private EditText _endDateTime;
    private Button _startCommand;
    private Button _endCommand;

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



            }
        });


        _endCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Beenden geklickt!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}
