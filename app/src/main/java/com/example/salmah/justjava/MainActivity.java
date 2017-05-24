package com.example.salmah.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    int quantityWhippedCream = 0;
    int quantityChocolate = 0;
    //initial price
    int basePrice = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method called when whipped cream minus button is clicked
    public void decrementWhippedCream(View view){
        if (quantityWhippedCream == 0){
            return;
        }
        quantityWhippedCream = quantityWhippedCream - 1;
        displayQuantity(quantityWhippedCream, quantityChocolate);
    }

    //method called when whipped cream plus button is clicked
    public void incrementWhippedCream(View view){
        if (quantityWhippedCream == 100){
            return;
        }
        quantityWhippedCream = quantityWhippedCream + 1;
        displayQuantity(quantityWhippedCream, quantityChocolate);
    }

    //method called when chocolate minus button is clicked
    public void decrementChocolate(View view){
        if (quantityChocolate == 0){
            return;
        }
        quantityChocolate = quantityChocolate - 1;
        displayQuantity(quantityWhippedCream, quantityChocolate);
    }

    //method called when chocolate plus button is clicked
    public void incrementChocolate(View view){
        if (quantityChocolate == 100){
            return;
        }
        quantityChocolate = quantityChocolate + 1;
        displayQuantity(quantityWhippedCream, quantityChocolate);
    }

    //method called when order button is clicked
    public void submitOrder(View view){
        //get the name of user
        EditText nameEditText = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameEditText.getText();
        String name = nameEditable.toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int whippedCreamPrice = calculateWhippedCreamPrice(hasWhippedCream);
        int chocolatePrice = calculateChocolatePrice(hasChocolate);
        int totalPrice = calculateTotalPrice(whippedCreamPrice, chocolatePrice);



        String message = createOrderSummary(name, whippedCreamPrice, chocolatePrice, totalPrice, hasWhippedCream, hasChocolate);

        //create intent to send email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    //method displays the given quantity value on screen
    private void displayQuantity(int numberOfWhippedCreamCoffees, int numberOfChocolateCoffees){
        TextView quantityWhippedCreamTextView = (TextView) findViewById(R.id.quantity_whipped_cream_view);
        quantityWhippedCreamTextView.setText("" + numberOfWhippedCreamCoffees);

        TextView quantityChocolateTextView = (TextView) findViewById(R.id.quantity_chocolate_view);
        quantityChocolateTextView.setText("" + numberOfChocolateCoffees);

    }

    //method to calculate price
    public int calculateWhippedCreamPrice(boolean addWhippedCream){
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        //calculating price
        return quantityWhippedCream * basePrice;
    }

    public int calculateChocolatePrice(boolean addChocolate){
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        return quantityChocolate * basePrice;
    }

    public int calculateTotalPrice(int whippedCreamPrice, int chocolatePrice){
        return whippedCreamPrice + chocolatePrice;
    }


    public String createOrderSummary(String name, int whippedCreamPrice, int chocolatePrice, int totalPrice, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream_quantity, quantityWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate_quantity, quantityChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream_price,
                NumberFormat.getCurrencyInstance().format(whippedCreamPrice));
        priceMessage += "\n" + getString(R.string.order_summary_chocolate_price,
                NumberFormat.getCurrencyInstance().format(chocolatePrice));
        priceMessage += "\n" + getString(R.string.order_summary_total_price,
                NumberFormat.getCurrencyInstance().format(totalPrice));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}
