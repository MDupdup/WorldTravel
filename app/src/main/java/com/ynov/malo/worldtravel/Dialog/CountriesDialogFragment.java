package com.ynov.malo.worldtravel.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ynov.malo.worldtravel.CountryActivity;
import com.ynov.malo.worldtravel.R;

/**
 * Created by Malo on 18/03/2018.
 */

// Dialog qui s'affiche si l'utilisateur clique sur un pays qu'il a deja ajoute auparavant, lui demandant s'il souhaite l'ajouter
// une deuxieme fois
public class CountriesDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getResources().getString(R.string.popup1) + getArguments().getString("name") + getResources().getString(R.string.popup2))
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
