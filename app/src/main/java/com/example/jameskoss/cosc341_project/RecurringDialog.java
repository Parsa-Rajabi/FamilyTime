package com.example.jameskoss.cosc341_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class RecurringDialog extends AppCompatDialogFragment {

    private EditText numberInput;
    private Spinner spinner;
    private RecurringDialogListener listener;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.RecurringDialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_recurringpopup, null);
        numberInput = view.findViewById(R.id.recurringpopup_numberInput);
        numberInput.setText(0 + "");

            populateSpinners(view);

        builder.setView(view).setTitle("Recurring Events")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                 })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedSpinnerItem = spinner.getSelectedItem().toString();
                        int level = spinner.getSelectedItemPosition();
                        int repetitions = Integer.parseInt(numberInput.getText().toString());


                        listener.applyTexts(selectedSpinnerItem, level,repetitions);
                        listener.applyTexts(selectedSpinnerItem, level, repetitions);

                    }
                });

        numberInput = view.findViewById(R.id.recurringpopup_numberInput);
        spinner = view.findViewById(R.id.recurringpopup_spinner);

    return builder.create();
    }
/*
Note this.getContext()//view.getContext() might cause an error
 */
    private void populateSpinners(View view) {
        spinner = view.findViewById(R.id.recurringpopup_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.event_recurringEventSpinner, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (RecurringDialogListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement RecurringDialog Listener");
        }
    }

    public interface RecurringDialogListener {
        void applyTexts(String text, int level, int repetitions);
//        void applyTexts(int repetitions);

    }

}
