package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
    //Class-level variable for quantity.
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method will increment the + sign.
     */
    public void increment(View view)
    {
        if (quantity == 100)
        {
            Toast.makeText(this, "You cannot have more than 100 cups of coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method will decrement the - sign.
     */
    public void decrement(View view)
    {
        if (quantity == 0)
        {
            Toast.makeText(this, "You cannot have less than 0 cups of coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        EditText nameText = findViewById(R.id.name_field);
        String name = nameText.getText().toString();
        //Log.v("MainActivity", "Name: " + name);

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "Has chocolate: " + hasChocolate);

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }

        //displayMessage(priceMessage);
    }


    /**
     * Create a summary of the order.
     *
     * @param name of customer
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate  is whether or not the user wants chocolate topping.
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate)
    {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $ " + price;
        priceMessage += "\n" + getString(R.string.thank_you);

        return priceMessage;
    }

    /**
     * Calculates price of the order based on the quantity.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 5;

        if (addWhippedCream == true)
        {
            basePrice += 1;
        }
        if (addChocolate == true)
        {
            basePrice += 2;
        }
        return basePrice * quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
/*    private void displayMessage(String message)
    {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }*/



}
