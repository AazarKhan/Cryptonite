package com.example.aazar.cryptonite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aazar.cryptonite.controller.Cipher;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Switch switchCryptoMode;
    private TextView switchTextView;

    private TextView myTextView;
    private TextView editTextMsg;
    private TextView editTextShiftKey;
    private TextView editTextVigenereKey;

    private RelativeLayout relativeLayoutAffine;
    private TextView editTextAffineKeyA;
    private TextView editTextAffineKeyB;

    private RelativeLayout relativeLayoutHill;
    private TextView editTextHillKeyA;
    private TextView editTextHillKeyB;
    private TextView editTextHillKeyC;
    private TextView editTextHillKeyD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton;
        final Spinner spinnerAlgoList = (Spinner) findViewById(R.id.spinner_cipher_algo_list);

        InitializeWidgets();

        myButton = (Button) findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTextView.setText(GetCracking(spinnerAlgoList.getSelectedItemPosition(), switchCryptoMode.isChecked()));
            }
        });

        // showing and hiding widgets
        spinnerAlgoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        SetViewState(View.GONE, View.GONE, View.GONE, View.GONE);
                    }
                    // shift
                    case 1: {
                        SetViewState(View.VISIBLE, View.GONE, View.GONE, View.GONE);
                        break;
                    }

                    // vigenere
                    case 2: {
                        SetViewState(View.GONE, View.VISIBLE, View.GONE, View.GONE);
                        break;
                    }

                    // affine
                    case 3: {
                        SetViewState(View.GONE, View.GONE, View.VISIBLE, View.GONE);
                        break;
                    }

                    // hill
                    case 4:{
                        SetViewState(View.GONE, View.GONE, View.GONE, View.VISIBLE);
                        break;
                    }

                    // substitution
                    case 5:
                        break;

                    // transposition
                    case 6:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switchCryptoMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    switchTextView.setText(getResources().getString(R.string.switch_text_encrypt));
                else
                    switchTextView.setText(getResources().getString(R.string.switch_text_decrypt));
            }
        });
    }


    public void InitializeWidgets() {
        try{
            myTextView = (TextView) findViewById(R.id.myTextView);

            switchTextView = (TextView) findViewById(R.id.switchTextView);
            switchCryptoMode = (Switch) findViewById(R.id.switchCryptoMode);

            // initializing edit text with all caps on
            editTextMsg = (TextView) findViewById(R.id.editTextMsg);
            editTextMsg.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

            // initializing shift cipher key with no visibility
            editTextShiftKey = (TextView) findViewById(R.id.editTextShiftKey);
            editTextShiftKey.setVisibility(View.GONE);

            // initializing vigenere cipher key with all caps and no visibility
            editTextVigenereKey = (TextView) findViewById(R.id.editTextVigenereKey);
            editTextVigenereKey.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            editTextVigenereKey.setVisibility(View.GONE);

            // affine
            editTextAffineKeyA = (TextView) findViewById(R.id.editTextAffineKeyA);
            editTextAffineKeyB = (TextView) findViewById(R.id.editTextAffineKeyB);
            relativeLayoutAffine = (RelativeLayout) findViewById(R.id.relativeLayoutAffineKey);
            relativeLayoutAffine.setVisibility(View.GONE);

            // hill cipher
            editTextHillKeyA = (TextView) findViewById(R.id.editTextHillKeyA);
            editTextHillKeyB = (TextView) findViewById(R.id.editTextHillKeyB);
            editTextHillKeyC = (TextView) findViewById(R.id.editTextHillKeyC);
            editTextHillKeyD = (TextView) findViewById(R.id.editTextHillKeyD);
            relativeLayoutHill = (RelativeLayout) findViewById(R.id.relativeLayoutHillKey);
            relativeLayoutHill.setVisibility(View.GONE);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error initializing widgets", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetViewState(int shift, int vigenere, int affine, int hill){
        try{
            editTextShiftKey.setVisibility(shift);
            editTextVigenereKey.setVisibility(vigenere);
            relativeLayoutAffine.setVisibility(affine);
            relativeLayoutHill.setVisibility(hill);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Something's wrong with edit texts visibility", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean CheckState(int cipherAlgoID){
        switch (cipherAlgoID){
            case 1:{
                if (editTextMsg.getText() != null && editTextShiftKey.getText() != null)
                    return true;
            }

            case 2: {
                if (editTextMsg.getText() != null && editTextVigenereKey.getText() != null)
                    return true;
            }
            case 3: {
                if (editTextMsg.getText() != null && editTextAffineKeyA.getText() != null && editTextAffineKeyB.getText() != null)
                    return true;
            }
            case 4: {
                if (editTextMsg.getText() != null
                        && editTextHillKeyA.getText() != null
                        && editTextHillKeyB.getText() != null
                        && editTextHillKeyC.getText() != null
                        && editTextHillKeyD.getText() != null )
                    return true;
            }
            case 5: break;
            case 6: break;
        }
        return false;
    }

    public String GetCracking(int cipherAlgoID, boolean mode) {
        try{
            if (CheckState(cipherAlgoID)){
                switch (cipherAlgoID) {
                    case 1: {
                        if (mode)
                            return Cipher.ShiftCipher.encrypt(editTextMsg.getText().toString(),
                                    Integer.parseInt(editTextShiftKey.getText().toString()));
                        else
                            return Cipher.ShiftCipher.decrypt(editTextMsg.getText().toString(),
                                    Integer.parseInt(editTextShiftKey.getText().toString()));
                    }

                    case 2: {
                        if (mode)
                            return Cipher.VigenereCipher.encrypt(editTextMsg.getText().toString(),
                                    editTextVigenereKey.getText().toString());
                        else
                            return Cipher.VigenereCipher.decrypt(editTextMsg.getText().toString(),
                                    editTextVigenereKey.getText().toString());
                    }
                    case 3: {
                        if (mode)
                            return Cipher.AffineCipher.encrypt(editTextMsg.getText().toString(),
                                    Integer.parseInt(editTextAffineKeyA.getText().toString()),
                                    Integer.parseInt(editTextAffineKeyB.getText().toString()));
                        else
                            return Cipher.AffineCipher.decrypt(editTextMsg.getText().toString(),
                                    Integer.parseInt(editTextAffineKeyA.getText().toString()),
                                    Integer.parseInt(editTextAffineKeyB.getText().toString()));
                    }
                    case 4: break;
                    case 5: break;
                    case 6: break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error cracking the text", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
}
