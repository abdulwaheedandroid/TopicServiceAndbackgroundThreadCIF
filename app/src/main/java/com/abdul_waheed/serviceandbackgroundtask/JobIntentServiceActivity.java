package com.abdul_waheed.serviceandbackgroundtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JobIntentServiceActivity extends AppCompatActivity {

    private EditText etInput;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_intent_service);

        etInput = findViewById(R.id.et_input);

    }

    public void enqueueWork(View view) {
        String input = etInput.getText().toString();
        Intent serviveIntent = new Intent(this, ExampleJobIntentService.class);
        serviveIntent.putExtra("input_extra", input);

        /*
        * If constraints needs to be set then it is not a suitable solution because it mimics as if it were IntentService class.
        * If constraints are required then JobSchedular is a better approach
        * */
        ExampleJobIntentService.enqueuWork(this, serviveIntent);


    }
}
