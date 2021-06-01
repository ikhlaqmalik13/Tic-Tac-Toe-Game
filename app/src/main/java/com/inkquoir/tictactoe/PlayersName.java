package com.inkquoir.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PlayersName extends AppCompatDialogFragment {

    private EditText personFirst, personSecond;
    private PlayerNameListner listner;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setCancelable(false)
        .setTitle("Enter Player Names")
        .setPositiveButton("Submit", (dialog, which) -> {
            String player1 = personFirst.getText().toString();
            String player2 = personSecond.getText().toString();
            listner.applyTexts(player1, player2);
        });
        personFirst = view.findViewById(R.id.personFirst);
        personSecond = view.findViewById(R.id.personSecond);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listner = (PlayerNameListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" Must implement PlayerNameListner");
        }
    }

    public interface PlayerNameListner{
        void applyTexts(String player1, String player2);
    }
}
