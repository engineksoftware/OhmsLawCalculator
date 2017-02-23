package enginek.ohmslawcalculator;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Joseph Kessler on 2/22/2017.
 */

public class CalculatorFragment extends Fragment {

    private View view;
    private Context context;

    private Button save, reset, solve;
    private EditText voltage, current, resistance, power;

    private boolean volatageSet, currentSet, resistanceSet, powerSet;
    private int solvable;

    private final int sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.calculator_fragment, container, false);
        context = view.getContext();

        //These values are used to see if three set values are set
        powerSet = false;
        resistanceSet = false;
        currentSet = false;
        volatageSet = false;
        solvable = 0;

        save = (Button) view.findViewById(R.id.save);
        reset = (Button) view.findViewById(R.id.reset);
        solve = (Button) view.findViewById(R.id.solve);

        voltage = (EditText) view.findViewById(R.id.voltage);
        current = (EditText) view.findViewById(R.id.current);
        resistance = (EditText) view.findViewById(R.id.resistance);
        power = (EditText) view.findViewById(R.id.power);

        //Changes the set, and solvable values depending on what's typed.
        voltage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 && !volatageSet){
                    volatageSet = true;
                    solvable++;
                }else
                    if(s.length() == 0 && volatageSet){
                        voltage.setBackgroundResource(R.drawable.edittext_white_background);
                        volatageSet = false;
                        solvable--;
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 && !currentSet){
                    currentSet = true;
                    solvable++;
                }else
                if(s.length() == 0 && currentSet){
                    currentSet = false;
                    solvable--;
                    current.setBackgroundResource(R.drawable.edittext_white_background);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        resistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 && !resistanceSet){
                    resistanceSet = true;
                    solvable++;
                }else
                if(s.length() == 0 && resistanceSet){
                    resistanceSet = false;
                    solvable--;
                    resistance.setBackgroundResource(R.drawable.edittext_white_background);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        power.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 && !powerSet){
                    powerSet = true;
                    solvable++;
                }else
                if(s.length() == 0 && powerSet){
                    powerSet = false;
                    solvable--;
                    power.setBackgroundResource(R.drawable.edittext_white_background);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(solvable > 3){
                    Toast.makeText(context, "Everything is filled in already!", Toast.LENGTH_SHORT).show();
                }else
                /*
                 * 0 = voltage and current aren't set
                 * 1 = voltage and resistance aren't set
                 * 2 = voltage and power aren't set
                 * 3 - current and resistance aren't set
                 * 4 - current and power aren't set
                 * 5 - resistance and power aren't set
                 */
                    if(solvable == 2){
                        if(!volatageSet && !currentSet)
                            solve(0);
                        else
                            if(!volatageSet && !resistanceSet)
                                solve(1);
                        else
                                if(!volatageSet && !powerSet)
                                    solve(2);
                        else
                                    if(!currentSet && !resistanceSet)
                                        solve(3);
                        else
                                        if(!currentSet && !powerSet)
                                            solve(4);
                        else
                                            solve(5);
                    }else{
                        Toast.makeText(context, "You need to fill in two of the values.", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Unhighlights the backgrounds
                voltage.setBackgroundResource(R.drawable.edittext_white_background);
                current.setBackgroundResource(R.drawable.edittext_white_background);
                resistance.setBackgroundResource(R.drawable.edittext_white_background);
                power.setBackgroundResource(R.drawable.edittext_white_background);

                //Sets the edittext back to empty
                voltage.setText("");
                current.setText("");
                resistance.setText("");
                power.setText("");


            }
        });

        return view;
    }

    //Solves for the unknowns variables
    private void solve(int isntSet){
        //Resets all the background to the non highlighted ones
        voltage.setBackgroundResource(R.drawable.edittext_white_background);
        current.setBackgroundResource(R.drawable.edittext_white_background);
        resistance.setBackgroundResource(R.drawable.edittext_white_background);
        power.setBackgroundResource(R.drawable.edittext_white_background);

        if(isntSet == 0){
            //Solve for voltage and current
            //Given Power and Resistance

            float power = Float.parseFloat(this.power.getText().toString());
            float resistance = Float.parseFloat(this.resistance.getText().toString());

            float voltage = (float) Math.sqrt(power*resistance);
            float current = (power / voltage);

            this.voltage.setText(String.valueOf(voltage));
            this.voltage.setBackgroundResource(R.drawable.edittext_white_background_highlighted);
            this.current.setText(String.valueOf(current));
            this.current.setBackgroundResource(R.drawable.edittext_white_background_highlighted);


        }else
            if(isntSet == 1){
                //Solve for voltage and resistance
                //Given Current and Power

                float power = Float.parseFloat(this.power.getText().toString());
                float current = Float.parseFloat(this.current.getText().toString());

                float voltage = (power / current);
                float resistance = (voltage / current);

                this.voltage.setText(String.valueOf(voltage));
                this.voltage.setBackgroundResource(R.drawable.edittext_white_background_highlighted);
                this.resistance.setText(String.valueOf(resistance));
                this.resistance.setBackgroundResource(R.drawable.edittext_white_background_highlighted);


            }else
                if(isntSet == 2){
                    //Solve for voltage and power
                    //Given current and resistance

                    float resistance = Float.parseFloat(this.resistance.getText().toString());
                    float current = Float.parseFloat(this.current.getText().toString());

                    float voltage = (current * resistance);
                    float power = (voltage * current);

                    this.voltage.setText(String.valueOf(voltage));
                    this.voltage.setBackgroundResource(R.drawable.edittext_white_background_highlighted);
                    this.power.setText(String.valueOf(power));
                    this.power.setBackgroundResource(R.drawable.edittext_white_background_highlighted);


                }else
                    if(isntSet == 3){
                        //Solve for current and resistance
                        //Given voltage and power

                        float voltage = Float.parseFloat(this.voltage.getText().toString());
                        float power = Float.parseFloat(this.power.getText().toString());

                        float current = (power / voltage);
                        float resistance = (voltage / current);

                        this.current.setText(String.valueOf(current));
                        this.current.setBackgroundResource(R.drawable.edittext_white_background_highlighted);
                        this.resistance.setText(String.valueOf(resistance));
                        this.resistance.setBackgroundResource(R.drawable.edittext_white_background_highlighted);



                    }else
                        if(isntSet == 4){
                            //Solve for current and power
                            //Given voltage and resistance

                            float voltage = Float.parseFloat(this.voltage.getText().toString());
                            float resistance = Float.parseFloat(this.resistance.getText().toString());

                            float current = (voltage / resistance);
                            float power = (voltage * current);

                            this.current.setText(String.valueOf(current));
                            this.current.setBackgroundResource(R.drawable.edittext_white_background_highlighted);
                            this.power.setText(String.valueOf(power));
                            this.power.setBackgroundResource(R.drawable.edittext_white_background_highlighted);

                        }else{
                            //Solve for resistance and power
                            //Given Voltage and Current

                            float voltage = Float.parseFloat(this.voltage.getText().toString());
                            float current = Float.parseFloat(this.current.getText().toString());

                            float resistance = (voltage / current);
                            float power = (voltage * current);

                            this.resistance.setText(String.valueOf(resistance));
                            this.resistance.setBackgroundResource(R.drawable.edittext_white_background_highlighted);
                            this.power.setText(String.valueOf(power));
                            this.power.setBackgroundResource(R.drawable.edittext_white_background_highlighted);

                        }
    }
}
