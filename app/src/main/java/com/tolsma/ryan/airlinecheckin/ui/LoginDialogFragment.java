package com.tolsma.ryan.airlinecheckin.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.Login;

import java.sql.Time;

import butterknife.Bind;
import butterknife.ButterKnife;

/**

 */
public class LoginDialogFragment extends DialogFragment implements ExtendedFragment {
    ScrollView mDialogLayout;
    @Bind(R.id.login_dialog_date_picker)
    DatePicker mDatePicker;
    @Bind(R.id.login_dialog_time_picker)
    TimePicker mTimePicker;
    @Bind(R.id.login_dialog_first_name_edit_text)
    EditText mFirstName;
    @Bind(R.id.login_dialog_last_name_edit_text)
    EditText mLastName;

    private String mLoginType=null;
    private Login mLogin;

    DialogInterface.OnClickListener dialogClickListener= new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case AlertDialog.BUTTON_POSITIVE: dialog.dismiss();
                    break;
                case AlertDialog.BUTTON_NEGATIVE: dialog.cancel();

                    break;
                default: //shouldn't happen

            }

        }
    };

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDialogLayout=(LinearLayout) inflater.inflate(R.layout.fragment_login_dialog, container, false);
        mDatePicker=(DatePicker) mDialogLayout.findViewById(R.id.login_dialog_date_picker);
        mTimePicker=(TimePicker) mDialogLayout.findViewById(R.id.login_dialog_time_picker);
        mFirstName=(EditText) mDialogLayout.findViewById(R.id.login_dialog_first_name);
        mLastName=(EditText) mDialogLayout.findViewById(R.id.login_dialog_last_name);




        return inflater.inflate;
    }
    */

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        mDialogLayout=(ScrollView) getActivity().getLayoutInflater().inflate(R.layout.fragment_login_dialog, null);
        ButterKnife.bind(this, mDialogLayout);

        mDatePicker.setCalendarViewShown(false);
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle(mLoginType);
                //dialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.fragment_login_dialog, null));
                dialogBuilder.setView(mDialogLayout);
                //.setIcon(id)
                dialogBuilder.setPositiveButton(R.string.dialog_submit, dialogClickListener ).
                        setNegativeButton(R.string.dialog_cancel,dialogClickListener); //TODO);



        return dialogBuilder.create();
    }

    @Override
    public void setArguments(Bundle bundle) {
        mLoginType=(String) bundle.get("login");


    }


}
