package android.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import com.android.internal.R;

/* loaded from: classes.dex */
public class TimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, TimePicker.OnTimeChangedListener {
    private static final String HOUR = "hour";
    private static final String IS_24_HOUR = "is24hour";
    private static final String MINUTE = "minute";
    private final int mInitialHourOfDay;
    private final int mInitialMinute;
    private final boolean mIs24HourView;
    private final TimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetListener;

    public interface OnTimeSetListener {
        void onTimeSet(TimePicker timePicker, int i, int i2);
    }

    @Override // android.widget.TimePicker.OnTimeChangedListener
    public void onTimeChanged(TimePicker timePicker, int i, int i2) {
    }

    public TimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int i, int i2, boolean z) {
        this(context, 0, onTimeSetListener, i, i2, z);
    }

    static int resolveDialogTheme(Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843934, typedValue, true);
        return typedValue.resourceId;
    }

    public TimePickerDialog(Context context, int i, OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z) {
        super(context, resolveDialogTheme(context, i));
        this.mTimeSetListener = onTimeSetListener;
        this.mInitialHourOfDay = i2;
        this.mInitialMinute = i3;
        this.mIs24HourView = z;
        Context context2 = getContext();
        View inflate = LayoutInflater.from(context2).inflate(R.layout.time_picker_dialog, (ViewGroup) null);
        setView(inflate);
        setButton(-1, context2.getString(17039370), this);
        setButton(-2, context2.getString(17039360), this);
        setButtonPanelLayoutHint(1);
        TimePicker timePicker = (TimePicker) inflate.findViewById(R.id.timePicker);
        this.mTimePicker = timePicker;
        timePicker.setIs24HourView(Boolean.valueOf(z));
        timePicker.setCurrentHour(Integer.valueOf(i2));
        timePicker.setCurrentMinute(Integer.valueOf(i3));
        timePicker.setOnTimeChangedListener(this);
    }

    public TimePicker getTimePicker() {
        return this.mTimePicker;
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: android.app.TimePickerDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TimePickerDialog.this.mTimePicker.validateInput()) {
                    TimePickerDialog timePickerDialog = TimePickerDialog.this;
                    timePickerDialog.onClick(timePickerDialog, -1);
                    TimePickerDialog.this.mTimePicker.clearFocus();
                    TimePickerDialog.this.dismiss();
                }
            }
        });
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        OnTimeSetListener onTimeSetListener;
        if (i == -2) {
            cancel();
        } else if (i == -1 && (onTimeSetListener = this.mTimeSetListener) != null) {
            TimePicker timePicker = this.mTimePicker;
            onTimeSetListener.onTimeSet(timePicker, timePicker.getCurrentHour().intValue(), this.mTimePicker.getCurrentMinute().intValue());
        }
    }

    public void updateTime(int i, int i2) {
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }

    @Override // android.app.Dialog
    public Bundle onSaveInstanceState() {
        Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt("hour", this.mTimePicker.getCurrentHour().intValue());
        onSaveInstanceState.putInt("minute", this.mTimePicker.getCurrentMinute().intValue());
        onSaveInstanceState.putBoolean(IS_24_HOUR, this.mTimePicker.is24HourView());
        return onSaveInstanceState;
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int i = bundle.getInt("hour");
        int i2 = bundle.getInt("minute");
        this.mTimePicker.setIs24HourView(Boolean.valueOf(bundle.getBoolean(IS_24_HOUR)));
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }
}
