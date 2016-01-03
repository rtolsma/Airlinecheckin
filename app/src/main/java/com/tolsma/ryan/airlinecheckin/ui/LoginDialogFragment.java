package com.tolsma.ryan.airlinecheckin.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.tolsma.ryan.airlinecheckin.CleanupApplication;
import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.Login;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogins;
import com.tolsma.ryan.airlinecheckin.model.realmobjects.SouthwestLoginEvent;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**

 */
public class LoginDialogFragment extends DialogFragment implements ExtendedFragment {
    final int MIN_PER_HOUR = 60, SEC_PER_MIN = 60, SEC_PER_MILLISEC = 1000;


    ScrollView mDialogLayout;

    @Inject
    Context ctx;

    @Bind(R.id.login_dialog_date_picker)
    DatePicker mDatePicker;
    @Bind(R.id.login_dialog_time_picker)
    TimePicker mTimePicker;
    @Bind(R.id.login_dialog_first_name_edit_text)
    EditText mFirstName;
    @Bind(R.id.login_dialog_last_name_edit_text)
    EditText mLastName;
    @Bind(R.id.login_dialog_confirmation_code_edit_text)
    EditText mConfirmationCode;



   // @Inject
    SouthwestLogins loginList;

    private int loginPosition;
    private SouthwestLogin login;
    private SouthwestLoginEvent loginEvent;
    private OnSuccessfulCompletion onCompletion;


    boolean mIsEdit;


   private String mLoginType = null;

    public LoginDialogFragment() {
        this(false, -1);
    }
    public LoginDialogFragment(boolean changeLogin, int pos) {
        this.mIsEdit = changeLogin;
        this.loginPosition=pos;
        CleanupApplication.getLoginComponent().inject(this);
       loginList= CleanupApplication.getLoginComponent().swLogins();

    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        mDialogLayout = (ScrollView) getActivity().getLayoutInflater().inflate(R.layout.fragment_login_dialog, null);
        ButterKnife.bind(this, mDialogLayout);


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

            dialogBuilder.setPositiveButton(R.string.dialog_save, dialogClickListener)
                    .setNegativeButton(R.string.dialog_cancel, dialogClickListener);

        } else {

            dialogBuilder.setPositiveButton(R.string.dialog_submit, dialogClickListener).
                    setNegativeButton(R.string.dialog_cancel, dialogClickListener); //TODO);
        }

        return dialogBuilder.create();
    }

    @Override
    public void setArguments(Bundle bundle) {
        mLoginType=(String) bundle.get("loginEvent");


    }

    public Date getDate(DatePicker dp, TimePicker tp) {
        if (tp != null & dp != null) {

            Calendar cal=Calendar.getInstance();
            cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
            long time = cal.getTimeInMillis();
            if (Build.VERSION.SDK_INT >= 23)
                time += (tp.getHour() * MIN_PER_HOUR * SEC_PER_MIN + tp.getMinute() * SEC_PER_MIN) * SEC_PER_MILLISEC;
            else
                time += (tp.getCurrentHour() * MIN_PER_HOUR * SEC_PER_MIN + tp.getCurrentMinute() * SEC_PER_MIN) * SEC_PER_MILLISEC;
            return new Date(time);
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


    DialogInterface.OnClickListener dialogClickListener= new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case AlertDialog.BUTTON_POSITIVE:
                    Date flightDate = getDate(mDatePicker, mTimePicker);

                    loginEvent = new SouthwestLoginEvent();
                    loginEvent.setFirstName(mFirstName.getText().toString());
                    loginEvent.setLastName(mLastName.getText().toString());
                    loginEvent.setConfirmationCode(mConfirmationCode.getText().toString());
                    loginEvent.setFlightDate(flightDate);
                    if(login!=null) {
                        login.cancelAlarm(ctx);
                        loginList.getList().remove(loginPosition);

                    }

                    login=new SouthwestLogin(loginEvent);
                    onCompletion.onComplete(login);
                    dialog.dismiss();
                    break;
                case AlertDialog.BUTTON_NEGATIVE: dialog.cancel();

                    break;
                default: //shouldn't happen

            }

        }
    };


}
