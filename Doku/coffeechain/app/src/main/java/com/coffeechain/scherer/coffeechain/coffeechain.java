package com.coffeechain.scherer.coffeechain;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class coffeechain extends AppCompatActivity {

    ImageButton button_back;
    ImageButton button_blockchain;
    ImageButton button_coffee;

    SharedPreferences Block_Save;

    TextView Text_1;
    TextView Text_2;
    TextView Text_3;
    TextView Text_4;
    TextView Text_5;
    TextView Text_6;
    TextView Text_7;

    String block;
    int position;

    int state = 0;
    int blocksize = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_coffeechain);
        getSupportActionBar().hide();

        button_back        = (ImageButton) findViewById(R.id.button_back);
        button_blockchain  = (ImageButton) findViewById(R.id.button_create);
        button_coffee      = (ImageButton) findViewById(R.id.button_coffee);

        Text_1 = (TextView) findViewById(R.id.number_1);
        Text_2 = (TextView) findViewById(R.id.number_2);
        Text_3 = (TextView) findViewById(R.id.number_3);
        Text_4 = (TextView) findViewById(R.id.number_4);
        Text_5 = (TextView) findViewById(R.id.number_5);
        Text_6 = (TextView) findViewById(R.id.number_6);
        Text_7 = (TextView) findViewById(R.id.number_7);

        Block_Save = getSharedPreferences("BLOCK", 0);
        block = Block_Save.getString("BLOCK","");
        position = Block_Save.getInt("POSITION", 3);

        if(block.equals("")){
            block = generate_new_block();
            save_block(Block_Save, block);
        }
        else{
            Log.d("Meine App", "block vorhanden");
        }
        set_new_block_positon(position);

        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(position > 3) {
                    position = position - 1;
                    set_new_block_positon(position);
                    save_position(Block_Save, position);
                }
            }
        });

        button_coffee.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(position <= blocksize) {
                    position = position + 1;
                    set_new_block_positon(position);
                    save_position(Block_Save, position);
                }
            }
        });

        button_blockchain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(state == 0){
                    Toast.makeText(getApplicationContext(), "Sicher neuer Block erzeugen?", Toast.LENGTH_LONG).show();
                    state = 1;
                }
                else {
                    state = 0;
                    block = generate_new_block();
                    position = 3;
                    save_block(Block_Save, block);
                    save_position(Block_Save, position);
                    set_new_block_positon(position);
                }
            }
        });

    }

    String generate_new_block(){
        String new_block = "   ";
        Random random = new Random();

        for ( int faktor = 1; faktor <= blocksize; faktor ++ ) {
            int new_item = (int)(Math.random() * 10);
            new_block = new_block + Integer.toString(new_item);
        }
        Log.d("Meine App", "block: " + new_block);
        new_block = new_block + "        ";
        return new_block;
    }

    void save_block(SharedPreferences pref, String new_block){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("BLOCK", new_block);
        editor.commit();
    }

    void save_position(SharedPreferences pref, int pos){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("POSITION", pos);
        editor.commit();
    }

    void set_new_block_positon(int pos)
    {
        Text_1.setText(Character.toString(block.charAt(pos-3)));
        Text_2.setText(Character.toString(block.charAt(pos-2)));
        Text_3.setText(Character.toString(block.charAt(pos-1)));
        Text_4.setText(Character.toString(block.charAt(pos)));
        Text_5.setText(Character.toString(block.charAt(pos+1)));
        Text_6.setText(Character.toString(block.charAt(pos+2)));
        Text_7.setText(Character.toString(block.charAt(pos+3)));
    }
}
