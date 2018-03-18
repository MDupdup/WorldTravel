package com.ynov.malo.worldtravel.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ynov.malo.worldtravel.CountryActivity;

/**
 * Created by Malo on 18/03/2018.
 */

public class CountriesDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Ce pays (" + getArguments().getString("name") + ") est déjà dans votre liste !\nSouhaitez-vous tout de même l'ajouter ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(getActivity() instanceof CountryActivity) {
                            ((CountryActivity) getActivity()).startCalendarActivity(getArguments().getInt("position"));
                        }
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }
}
