package com.a0014.unknownassistant.Activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a0014.unknownassistant.R;

public class Activityest extends Activity {

    ConsumerIrManager mCIR;

    private static final int SAMSUNG_FREQ = 38028;
    private static final int[] SAMSUNG_POWER_TOGGLE_DURATION = {4495,4368,546,1638,546,1638,546,1638,546,546,546,546,546,546,546,546,546,546,546,1638,546,1638,546,1638,546,546,546,546,546,546,546,546,546,546,546,546,546,1638,546,546,546,546,546,546,546,546,546,546,546,546,546,1664,546,546,546,1638,546,1638,546,1638,546,1638,546,1638,546,1638,546,46644,4394,4368,546,546,546,96044};
    private static final int[] SAMSUNG_POWER_TOGGLE_COUNT = {169,168,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,63,21,63,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,63,21,21,21,21,21,21,21,21,21,21,21,21,21,64,21,21,21,63,21,63,21,63,21,63,21,63,21,63,21,1794,169,168,21,21,21,3694};

    TextView txtTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityest);

        Button btnTest = (Button) findViewById(R.id.btnTest);
        txtTest = (TextView) findViewById(R.id.txtTest);

        // Get a reference to the ConsumerIrManager
        mCIR = (ConsumerIrManager) this.getSystemService(Context.CONSUMER_IR_SERVICE);

        btnTest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Later version of Android 4.4.3
                if (!mCIR.hasIrEmitter()) {
                    txtTest.setText("No Emitter found");
                    return;
                }else{

                    txtTest.setText(SAMSUNG_POWER_TOGGLE_DURATION.length + "," + SAMSUNG_POWER_TOGGLE_COUNT.length);
                }

                mCIR.transmit(SAMSUNG_FREQ, SAMSUNG_POWER_TOGGLE_DURATION);
            }
        });
    }
}
