package com.jkgarage.homecashflowcalculator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

public class MainActivity extends ActionBarActivity {

    EditText editPropertyVal, editPercent, editCPF;
    TextView vwLoan, vwStampDuty, vwABSD, vwCashDownPayment, vwCashReq,
            vwDownPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPropertyVal = (EditText) findViewById(R.id.txtPropertyVal);
        editPercent = (EditText) findViewById(R.id.txtLoanPercent);

        vwLoan = (TextView) findViewById(R.id.txtLoanAmt);
        vwStampDuty = (TextView) findViewById(R.id.txtStampDuty);
        vwABSD = (TextView) findViewById(R.id.txtABSD);
        vwCashDownPayment = (TextView) findViewById(R.id.txtMinCash);
        vwDownPayment = (TextView) findViewById(R.id.txtDownpayment);

        editCPF = (EditText) findViewById(R.id.txtCPF);
        vwCashReq = (TextView) findViewById(R.id.txtMinCashReq);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void computeHomeLoan(View v) {

        //hide the soft keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editPropertyVal.getWindowToken(), 0);


        if (editPropertyVal.getText().toString().isEmpty() ||
                editPropertyVal.getText().toString().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "Input fields empty", Toast.LENGTH_SHORT);
            return;
        }
        int propertyValue = Integer.parseInt( editPropertyVal.getText().toString() );
        int loanPercent = Integer.parseInt( editPercent.getText().toString() );

        DecimalFormat formatter = new DecimalFormat("$#,###.00");


        vwLoan.setText( formatter.format((double) (propertyValue * loanPercent) / 100) );
        vwStampDuty.setText( formatter.format((double) (propertyValue * 3) / 100) );
        vwABSD.setText( formatter.format((double) (propertyValue * 5) / 100) );

        double cashDownpayment = (double) (propertyValue * 5) / 100;
        vwCashDownPayment.setText( formatter.format(cashDownpayment) );

        double totalDownPayment = propertyValue * (100-loanPercent) / 100;
        vwDownPayment.setText (formatter.format(totalDownPayment));

        int CPFDownPayment;
        if ( editCPF.getText().toString().isEmpty() ) {
            CPFDownPayment = (int) (totalDownPayment-cashDownpayment);
            editCPF.setText( String.valueOf(CPFDownPayment) );
        }
        else
            CPFDownPayment = Integer.parseInt( editCPF.getText().toString() );


        double totalCash = (totalDownPayment-CPFDownPayment) + (double) (propertyValue * 8) / 100;
        vwCashReq.setText( formatter.format(totalCash));
    }

    public void clearFields(View v) {
        editPropertyVal.setText("");
        editPercent.setText("");

        vwLoan.setText( "$0" );
        vwStampDuty.setText( "$0" );
        vwABSD.setText( "$0" );
        vwCashDownPayment.setText( "$0" );
        vwDownPayment.setText( "$0" );

        editCPF.setText("");
        vwCashReq.setText( "$0" );
    }

    @Override
    public void onBackPressed () {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                } )
                .setNegativeButton("No", null)
                .show();
    }
}
