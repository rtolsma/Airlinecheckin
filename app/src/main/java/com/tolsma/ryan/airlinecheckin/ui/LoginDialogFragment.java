package com.tolsma.ryan.airlinecheckin.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.widget.TimePicker;

import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.Login;

import java.sql.Time;

/**

 */
public class LoginDialogFragment extends DialogFragment implements ExtendedFragment {

    private LinearLayout mDialogLayout;
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private EditText mFirstName, mLastName;

    private String mLoginType=null;
    private Login mLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDialogLayout=(LinearLayout) inflater.inflate(R.layout.fragment_login_dialog, container, false);
        mDatePicker=(DatePicker) mDialogLayout.findViewById(R.id.login_dialog_date_picker);
        mTimePicker=(TimePicker) mDialogLayout.findViewById(R.id.login_dialog_time_picker);
        mFirstName=(EditText) mDialogLayout.findViewById(R.id.login_dialog_first_name);
        mLastName=(EditText) mDialogLayout.findViewById(R.id.login_dialog_last_name);




        return mDialogLayout;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {

        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(getActivity())
                dialogBuilder.setTitle(mLoginType);
                //.setIcon(id)
                dialogBuilder.setPositiveButton(R.string.dialog_submit,//TODO);


    }

    @Override
    public void setArguments(Bundle bundle) {
        mLoginType=(String) bundle.get("login");


    }


}
