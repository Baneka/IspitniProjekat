package com.example.androiddevelopment.ispitniprojekat.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.androiddevelopment.ispitniprojekat.R;

/**
 * Created by androiddevelopment on 20.3.17..
 */

public class AboutDialog extends AlertDialog.Builder {
    public AboutDialog(Context context) {
        super(context);

        setTitle(R.string.dialog_about_title);
        setMessage("Milos Baneka");
        setCancelable(false);

        setPositiveButton(R.string.dialog_about_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        setNegativeButton(R.string.dialog_about_no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
    }
    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }


}