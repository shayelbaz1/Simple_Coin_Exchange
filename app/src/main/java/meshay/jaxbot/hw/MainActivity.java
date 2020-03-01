package meshay.jaxbot.hw;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import meshay.jaxbot.hw.R;

public class MainActivity extends AppCompatActivity {
    double USD2PHP = 0;//52.25;//52.250133;
    double USD2ILS = 0;//3.56;//
    double USD2EUR = 0;//0.85;
    double USD2CNY = 0;
    double USD2RUB = 0;
    double USD2GBP = 0;
    boolean boolIsFocusUp;
    boolean boolIsFocusDown;
    EditText editTextUp;
    EditText editTextDown;
    Spinner coinListLeft;
    Spinner coinListRight;
    ImageButton imageButtonSwap;
    ImageView imageViewUp;
    ImageView imageViewDown;
    ImageButton imageButtonClear;

    //--------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//--------------------------------------------------------------------------------//
        //Variables
        imageButtonClear = findViewById(R.id.imageButtonClear);
        coinListLeft = findViewById(R.id.spinnerLeft);
        coinListRight = findViewById(R.id.spinnerRight);
        editTextUp = findViewById(R.id.editTextAmountUp);
        editTextDown = findViewById(R.id.editTextDown);
        imageButtonSwap = findViewById(R.id.imageButtonSwap);
        imageViewUp = findViewById(R.id.imageViewUp);
        imageViewDown = findViewById(R.id.imageViewDown);
//--------------------------------------------------------------------------------//

        imageViewUp.setImageResource(R.drawable.israel_4);
        imageViewDown.setImageResource(R.drawable.israel_4);


//--------------------------------------------------------------------------------//

        ArrayAdapter<String> ListAdaptor = new ArrayAdapter<>
                (MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.spinnerLeft));

        ListAdaptor.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        coinListLeft.setAdapter(ListAdaptor);
        coinListLeft.setSelection(2);
        coinListRight.setAdapter(ListAdaptor);
        coinListRight.setSelection(0);
//--------------------------------------------------------------------------------//
        imageButtonSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapLeftandRightSpinners();
                convertUpToDown();

            }
        });

//--------------------------------------------------------------------------------//
        imageButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDown.getText().clear();
                editTextUp.getText().clear();
            }
        });
//--------------------------------------------------------------------------------//

        coinListRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                chooseCoinImage(coinListRight, imageViewDown);
                convertUpToDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
//--------------------------------------------------------------------------------//
        imageViewUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinListLeft.performClick();
            }
        });
//--------------------------------------------------------------------------------//
        imageViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coinListRight.performClick();
            }
        });
//--------------------------------------------------------------------------------//
        coinListLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                chooseCoinImage(coinListLeft, imageViewUp);
                convertUpToDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

//--------------------------------------------------------------------------------//
        editTextUp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolIsFocusUp = hasFocus;
            }
        });
        editTextUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (boolIsFocusUp) {
                    convertUpToDown();
                }
            }
        });
        editTextDown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                boolIsFocusDown = hasFocus;
            }
        });
//--------------------------------------------------------------------------------//
        editTextDown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (boolIsFocusDown) {
                    convertDownToUp();
                }
            }
        });

//--------------------------------------------------------------------------------//

        //String url = "https://free.currencyconverterapi.com/api/v5/convert?q=USD_PHP&compact=ultra";
        String url = "http://www.floatrates.com/daily/usd.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String res = response.toString();
                        try {
                            JSONObject obj = new JSONObject(res);
                            JSONObject PHP = obj.getJSONObject("php");
                            String ratePHP = PHP.getString("rate");
                            USD2PHP = Double.parseDouble(ratePHP);

                            JSONObject ILS = obj.getJSONObject("ils");
                            String rateIls = ILS.getString("rate");
                            USD2ILS = Double.parseDouble(rateIls);


                            JSONObject EUR = obj.getJSONObject("eur");
                            String rateEUR = EUR.getString("rate");
                            USD2EUR = Double.parseDouble(rateEUR);

                            JSONObject CNY = obj.getJSONObject("cny");
                            String rateCNY = CNY.getString("rate");
                            USD2CNY = Double.parseDouble(rateCNY);

                            JSONObject RUB = obj.getJSONObject("rub");
                            String rateRUB = RUB.getString("rate");
                            USD2RUB = Double.parseDouble(rateRUB);

                            JSONObject GBP = obj.getJSONObject("gbp");
                            String rateGBP = GBP.getString("rate");
                            USD2GBP = Double.parseDouble(rateGBP);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        //Access the RequestQueue through your singleton class.
        RequestQueue queue1 = Volley.newRequestQueue(this);
        queue1.add(jsonObjectRequest);
    }
//--------------------------------------------------------------------------------//


    private void chooseCoinImage(Spinner s, ImageView i) {
        //1 ILS 2 USA 3 EUR 4 CNY 5 RUB
        int spinnerIndex = s.getSelectedItemPosition();
        switch (spinnerIndex) {
            case 0:
                i.setImageResource(R.drawable.israel_4);
                break;
            case 1:
                i.setImageResource(R.drawable.usa_4);
                break;
            case 2:
                i.setImageResource(R.drawable.europe_4);
                break;
            case 3:
                i.setImageResource(R.drawable.china_4);
                break;
            case 4:
                i.setImageResource(R.drawable.russia_4);
                break;
            case 5:
                i.setImageResource(R.drawable.england_5);
                break;
        }
    }

    private void swapLeftandRightSpinners() {
        int temp = coinListLeft.getSelectedItemPosition();
        coinListLeft.setSelection(coinListRight.getSelectedItemPosition());
        coinListRight.setSelection(temp);
    }

    //convert 2 coins
    public String convert2Coins(double d, int coinA , double coinA_rate,int spinnerIndexLeft,int spinnerIndexRight){
        int ILS = 0;
        int USD = 1;
        int EUR = 2;
        int CNY = 3;
        int RUB = 4;
        int GBP = 5;

        if (spinnerIndexLeft == coinA && spinnerIndexRight == coinA)
            return String.format("%.2f", d);
        else if (spinnerIndexLeft == coinA && spinnerIndexRight == USD)
            return String.format("%.2f", d / coinA_rate);
        else if (spinnerIndexLeft == coinA && spinnerIndexRight == EUR)
            return String.format("%.2f", d * (USD2EUR/coinA_rate));
        else if (spinnerIndexLeft == coinA && spinnerIndexRight == CNY)
            return String.format("%.2f", d * (USD2CNY/coinA_rate));
        else if (spinnerIndexLeft == coinA && spinnerIndexRight == RUB)
            return String.format("%.2f", d * (USD2RUB/coinA_rate));
        else if (spinnerIndexLeft == coinA && spinnerIndexRight == GBP)
            return String.format("%.2f", d * (USD2GBP/coinA_rate));
        else if (spinnerIndexLeft == coinA && spinnerIndexRight == ILS)
            return String.format("%.2f", d * (USD2ILS/coinA_rate));
        else return "0";
    }

    @SuppressLint("DefaultLocale")
    public String Calculate(double d, Spinner spinnerLeft, Spinner spinnerRight) {
        int spinnerIndexLeft = spinnerLeft.getSelectedItemPosition();
        int spinnerIndexRight = spinnerRight.getSelectedItemPosition();
        int coin_index = spinnerIndexLeft;
        int ILS = 0;
        int USD = 1;
        int EUR = 2;
        int CNY = 3;
        int RUB = 4;
        int GBP = 5;

        switch (coin_index) {
            case 0:
                return convert2Coins(d, ILS, USD2ILS, spinnerIndexLeft, spinnerIndexRight);
            case 1:
                return convert2Coins(d, USD, 1, spinnerIndexLeft, spinnerIndexRight);
            case 2:
                return convert2Coins(d, EUR, USD2EUR, spinnerIndexLeft, spinnerIndexRight);
            case 3:
                return convert2Coins(d, CNY, USD2CNY, spinnerIndexLeft, spinnerIndexRight);
            case 4:
                return convert2Coins(d, RUB, USD2RUB, spinnerIndexLeft, spinnerIndexRight);
            case 5:
                return convert2Coins(d, GBP, USD2GBP, spinnerIndexLeft, spinnerIndexRight);
        }
        return"0";
    }

//
//        //USD 2 USD 2 ILS 2 EUR 2 CNY 2 RUB 2 GBP
//        if (spinnerIndexLeft == USD && spinnerIndexRight == USD)
//            return String.format("%.2f", d);
//
//        else if (spinnerIndexLeft == USD && spinnerIndexRight == ILS)
//            return String.format("%.2f", d * USD2ILS);
//
//        else if (spinnerIndexLeft == USD && spinnerIndexRight == EUR)
//            return String.format("%.2f", d * USD2EUR);
//
//        else if (spinnerIndexLeft == USD && spinnerIndexRight == CNY)
//            return String.format("%.2f", d * USD2CNY);
//        else if (spinnerIndexLeft == USD && spinnerIndexRight == RUB)
//            return String.format("%.2f", d * USD2RUB);
//        else if (spinnerIndexLeft == USD && spinnerIndexRight == GBP)
//            return String.format("%.2f", d * USD2GBP);
//
//            //ILS 2 ILS 2 USD 2 EUR 2 CNY 2 RUB 2 GBP
//        else if (spinnerIndexLeft == ILS && spinnerIndexRight == ILS)
//            return String.format("%.2f", d);
//        else if (spinnerIndexLeft == ILS && spinnerIndexRight == USD)
//            return String.format("%.2f", d / USD2ILS);
//        else if (spinnerIndexLeft == ILS && spinnerIndexRight == EUR)
//            return String.format("%.2f", d * (USD2EUR/USD2ILS));
//        else if (spinnerIndexLeft == ILS && spinnerIndexRight == CNY)
//            return String.format("%.2f", d * (USD2CNY/USD2ILS));
//        else if (spinnerIndexLeft == ILS && spinnerIndexRight == RUB)
//            return String.format("%.2f", d * (USD2RUB/USD2ILS));
//        else if (spinnerIndexLeft == ILS && spinnerIndexRight == GBP)
//            return String.format("%.2f", d * (USD2GBP/USD2ILS));
//
//
//            //EUR to CNY to EUR ILS USD RUB 2 GBP
//        else if (spinnerIndexLeft == EUR && spinnerIndexRight == EUR)
//            return String.format("%.2f", d);
//        else if (spinnerIndexLeft == EUR && spinnerIndexRight == ILS)
//            return String.format("%.2f", d * (USD2ILS / USD2EUR));
//
//        else if (spinnerIndexLeft == EUR && spinnerIndexRight == USD)
//            return String.format("%.2f", d * (1 / USD2EUR));
//
//        else if (spinnerIndexLeft == EUR && spinnerIndexRight == CNY)
//            return String.format("%.2f", d * (USD2CNY / USD2EUR));
//        else if (spinnerIndexLeft == EUR && spinnerIndexRight == RUB)
//            return String.format("%.2f", d * (USD2RUB / USD2EUR));
//        else if (spinnerIndexLeft == EUR && spinnerIndexRight == GBP)
//            return String.format("%.2f", d * (USD2GBP / USD2EUR));
//
//            //CNY to CNY to EUR ILS USD RUB 2 GBP
//        else if (spinnerIndexLeft == CNY && spinnerIndexRight == CNY)
//            return String.format("%.2f", d);
//        else if (spinnerIndexLeft == CNY && spinnerIndexRight == ILS)
//            return String.format("%.2f", d * (USD2ILS/USD2CNY));
//
//        else if (spinnerIndexLeft == CNY && spinnerIndexRight == USD)
//            return String.format("%.3f", d * (1 / USD2CNY));
//
//        else if (spinnerIndexLeft == CNY && spinnerIndexRight == EUR)
//            return String.format("%.2f", d * (USD2EUR/USD2CNY));
//        else if (spinnerIndexLeft == CNY && spinnerIndexRight == RUB)
//            return String.format("%.2f", d * (USD2RUB/USD2CNY));
//        else if (spinnerIndexLeft == CNY && spinnerIndexRight == GBP)
//            return String.format("%.2f", d * (USD2GBP/USD2CNY));
//
//            //RUB to CNY EUR ILS USD 2 GBP
//        else if (spinnerIndexLeft == RUB && spinnerIndexRight == RUB)
//            return String.format("%.2f", d);
//        else if (spinnerIndexLeft == RUB && spinnerIndexRight == USD)
//            return String.format("%.2f", d * (1 / USD2RUB));
//        else if (spinnerIndexLeft == RUB && spinnerIndexRight == EUR)
//            return String.format("%.2f", d * (USD2EUR / USD2RUB));
//        else if (spinnerIndexLeft == RUB && spinnerIndexRight == ILS)
//            return String.format("%.2f", d * (USD2ILS / USD2RUB));
//        else if (spinnerIndexLeft == RUB && spinnerIndexRight == CNY)
//            return String.format("%.2f", d * (USD2CNY / USD2RUB));
//        else if (spinnerIndexLeft == RUB && spinnerIndexRight == GBP)
//            return String.format("%.2f", d * (USD2GBP / USD2RUB));
//
//            //GBP to USD  EUR ILS CNY RUB
//        else if (spinnerIndexLeft == GBP && spinnerIndexRight == GBP)
//            return String.format("%.2f", d);
//        else if (spinnerIndexLeft == GBP && spinnerIndexRight == USD)
//            return String.format("%.2f", d * (1 / USD2GBP));
//        else if (spinnerIndexLeft == GBP && spinnerIndexRight == EUR)
//            return String.format("%.2f", d * (USD2CNY / USD2GBP));
//        else if (spinnerIndexLeft == GBP && spinnerIndexRight == ILS)
//            return String.format("%.2f", d * (USD2ILS / USD2GBP));
//        else if (spinnerIndexLeft == GBP && spinnerIndexRight == CNY)
//            return String.format("%.2f", d * (USD2RUB / USD2GBP));
//        else if (spinnerIndexLeft == GBP && spinnerIndexRight == RUB)
//            return String.format("%.2f", d * (USD2EUR / USD2GBP));
//
//
//        return "0";
 //   }

    @SuppressLint("SetTextI18n")
    public void convertUpToDown() {
        try {
            double doubleUpAmount;
            String stringUpAmount = editTextUp.getText().toString();
            doubleUpAmount = Double.parseDouble(stringUpAmount);
            editTextDown.setText(Calculate(doubleUpAmount, coinListLeft, coinListRight));
        } catch (Exception e) {
            //editTextDown.setText("");
        }
    }

    @SuppressLint("SetTextI18n")
    public void convertDownToUp() {
        try {
            double doubleDownAmount;
            String stringDownAmount = editTextDown.getText().toString();
            doubleDownAmount = Double.parseDouble(stringDownAmount);
            editTextUp.setText(Calculate(doubleDownAmount, coinListRight, coinListLeft));
        } catch (Exception e) {
            //editTextUp.setText("");
        }
    }
}
