package com.tolsma.ryan.airlinecheckin.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.tolsma.ryan.airlinecheckin.R;
import com.tolsma.ryan.airlinecheckin.model.SouthwestLogin;
import com.tolsma.ryan.airlinecheckin.utils.RealmUtils;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**

 */
public class LoginDialogFragment extends DialogFragment implements ExtendedFragment {
    final int MIN_PER_HOUR = 60, SEC_PER_MIN = 60;
    ScrollView mDialogLayout;
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
    DialogInterface.OnClickListener dialogClickListener= new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case AlertDialog.BUTTON_POSITIVE:
                    Date flightDate = getDate(mDatePicker, mTimePicker);

                    SouthwestLogin login = new SouthwestLogin();
                    login.setFirstName(mFirstName.getText().toString());
                    login.setLastName(mLastName.getText().toString());
                    login.setConfirmationCode(mConfirmationCode.getText().toString());
                    login.setFlightDate(flightDate);
                    RealmUtils.saveToRealm(getActivity(), login);
                    dialog.dismiss();
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
   private String mLoginType = null;

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

    public Date getDate(DatePicker dp, TimePicker tp) {
        if (tp != null & dp != null) {

            long time = dp.getMinDate();
            time += tp.getHour() * MIN_PER_HOUR * SEC_PER_MIN + tp.getMinute() * SEC_PER_MIN;
            return new Date(time);
        }
        return null;

    }


}
