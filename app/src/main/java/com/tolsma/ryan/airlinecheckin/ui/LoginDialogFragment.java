package com.tolsma.ryan.airlinecheckin.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.Login;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

/**

 */
public class LoginDialogFragment extends DialogFragment implements ExtendedFragment {
    final int MIN_PER_HOUR = 60, SEC_PER_MIN = 60, SEC_PER_MILLISEC = 1000;


    ScrollView mDialogLayout;

    @Inject
    Context ctx;

    // @Bind(R.id.login_dialog_date_picker)
    DatePicker mDatePicker;
    ///  @Bind(R.id.login_dialog_time_picker)
    TimePicker mTimePicker;
    //  @Bind(R.id.login_dialog_first_name_edit_text)
    EditText mFirstName;
    //  @Bind(R.id.login_dialog_last_name_edit_text)
    EditText mLastName;
    //  @Bind(R.id.login_dialog_confirmation_code_edit_text)
    EditText mConfirmationCode;



   // @Inject
    SouthwestLogins loginList;
    boolean mIsEdit;
    private int loginPosition;
    private SouthwestLogin login;
    private SouthwestLoginEvent loginEvent;
    private OnSuccessfulCompletion onCompletion;
   private String mLoginType = null;

    public LoginDialogFragment() {
        this(false, -1);
    }
    public LoginDialogFragment(boolean changeLogin, int pos) {
        this.mIsEdit = changeLogin;
        this.loginPosition=pos;


    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        CleanupApplication.getLoginComponent().inject(this);
        loginList = CleanupApplication.getLoginComponent().swLogins();
        mDialogLayout = (ScrollView) getActivity().getLayoutInflater().inflate(R.layout.fragment_login_dialog, null);
        ButterKnife.bind(this, mDialogLayout);

        mTimePicker = (TimePicker) mDialogLayout.findViewById(R.id.login_dialog_time_picker);
        mDatePicker = (DatePicker) mDialogLayout.findViewById(R.id.login_dialog_date_picker);
        mLastName = (EditText) mDialogLayout.findViewById(R.id.login_dialog_last_name_edit_text);
        mFirstName = (EditText) mDialogLayout.findViewById(R.id.login_dialog_first_name_edit_text);
        mConfirmationCode = (EditText) mDialogLayout.findViewById(R.id.login_dialog_confirmation_code_edit_text);

        mDatePicker.setCalendarViewShown(false);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(mLoginType);
        //dialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.fragment_login_dialog, null));
        dialogBuilder.setView(mDialogLayout);
        //.setIcon(id)


        if (mIsEdit && loginPosition >= 0) {

            login= (SouthwestLogin) loginList.get(loginPosition);
            Date flightDate=login.getSouthwestLoginEvent().getFlightDate();
            Calendar cal= Calendar.getInstance();
            cal.setTime(flightDate);
            mDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)
                    , cal.get(Calendar.DAY_OF_MONTH));
        if(Build.VERSION.SDK_INT>=23) {
            mTimePicker.setHour(cal.get(Calendar.HOUR));
            mTimePicker.setMinute(cal.get(Calendar.MINUTE));
        } else {
            mTimePicker.setCurrentHour(cal.get(Calendar.HOUR));
            mTimePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
        }

            mFirstName.setText(login.getSouthwestLoginEvent().getFirstName());
            mLastName.setText(login.getSouthwestLoginEvent().getLastName());
            mConfirmationCode.setText(login.getConfirmationCode());

            dialogBuilder.setPositiveButton(R.string.dialog_save, null)
                    .setNegativeButton(R.string.dialog_delete, null);


        } else {

            dialogBuilder.setPositiveButton(R.string.dialog_submit, null).
                    setNegativeButton(R.string.dialog_cancel, null); //TODO);
        }

        AlertDialog alertDialog = dialogBuilder.create();

        /*
        Used to override the onClick listener so that I can prevent non
        formatted data from being created
         */


        View.OnClickListener dialogButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch ((int) v.getTag()) {

                    case AlertDialog.BUTTON_POSITIVE:

                        for (Login l : loginList.getList()) {
                            if (((SouthwestLogin) l).getConfirmationCode()
                                    .equals(mConfirmationCode.getText().toString()) && !mIsEdit) {
                                Toast.makeText(ctx, "Must have uniqe confirmation codes!"
                                        , Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        Date flightDate = getDate(mDatePicker, mTimePicker);

                        loginEvent = new SouthwestLoginEvent();
                        loginEvent.setFirstName(mFirstName.getText().toString());
                        loginEvent.setLastName(mLastName.getText().toString());
                        loginEvent.setConfirmationCode(mConfirmationCode.getText().toString());
                        loginEvent.setFlightDate(flightDate);
                        /*
                        Case checking for correct data
                         */
                        if (loginEvent.getFirstName().equals("") || loginEvent.getLastName().equals("")) {
                            Toast.makeText(ctx, "Names must not be blank!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (loginEvent.getConfirmationCode().length() != 6) {
                            Toast.makeText(ctx, "Confirmation code must be a unique 6-digit alphanumeric sequence!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (login != null) {
                            loginList.delete(loginPosition);

                        }

                        login = new SouthwestLogin(loginEvent);
                        onCompletion.onComplete(login);
                        alertDialog.dismiss();
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        if (login != null) {
                            loginList.delete(loginPosition);
                            onCompletion.onComplete(null);
                        }
                        alertDialog.cancel();

                        break;
                    default: //shouldn't happen
                }
            }
        };


        DialogInterface.OnShowListener dialogShowListener = new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positive.setTag(AlertDialog.BUTTON_POSITIVE);
                negative.setTag(AlertDialog.BUTTON_NEGATIVE);
                positive.setOnClickListener(dialogButtonListener);
                negative.setOnClickListener(dialogButtonListener);

            }
        };


        alertDialog.setOnShowListener(dialogShowListener);
        return alertDialog;

    }

    @Override
    public void setArguments(Bundle bundle) {
        mLoginType=(String) bundle.get("loginEvent");


    }

    @DebugLog
    public Date getDate(DatePicker dp, TimePicker tp) {
        if (tp != null & dp != null) {

            Calendar cal=Calendar.getInstance();
            long time = cal.getTimeInMillis();
            if (Build.VERSION.SDK_INT >= 23)
                cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getHour(), tp.getMinute());

            else
                cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(),
                        tp.getCurrentHour(), tp.getCurrentMinute());

            return new Date(cal.getTimeInMillis() - cal.getTimeInMillis() % 10000); //Don't count the seconds
        }
        return null;

    }

   /* public Login getLoginEvent() {
        return new SouthwestLogin(loginEvent);
    } */

    public void onComplete(OnSuccessfulCompletion onCompletion) {
        this.onCompletion = onCompletion;

    }

    public interface OnSuccessfulCompletion {
        void onComplete(SouthwestLogin login);
    }


}




