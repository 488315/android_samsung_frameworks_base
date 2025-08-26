package android.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import com.android.internal.R;
import java.util.Calendar;

/* loaded from: classes.dex */
public class DatePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, DatePicker.OnDateChangedListener {
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    private final DatePicker mDatePicker;
    private OnDateSetListener mDateSetListener;
    private final DatePicker.ValidationCallback mValidationCallback;

    public interface OnDateSetListener {
        void onDateSet(DatePicker datePicker, int i, int i2, int i3);
    }

    public DatePickerDialog(Context context) {
        this(context, 0, null, Calendar.getInstance(), -1, -1, -1);
    }

    public DatePickerDialog(Context context, int i) {
        this(context, i, null, Calendar.getInstance(), -1, -1, -1);
    }

    public DatePickerDialog(Context context, OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        this(context, 0, onDateSetListener, null, i, i2, i3);
    }

    public DatePickerDialog(Context context, int i, OnDateSetListener onDateSetListener, int i2, int i3, int i4) {
        this(context, i, onDateSetListener, null, i2, i3, i4);
    }

    private DatePickerDialog(Context context, int i, OnDateSetListener onDateSetListener, Calendar calendar, int i2, int i3, int i4) {
        super(context, resolveDialogTheme(context, i));
        DatePicker.ValidationCallback validationCallback = new DatePicker.ValidationCallback() { // from class: android.app.DatePickerDialog.1
            @Override // android.widget.DatePicker.ValidationCallback
            public void onValidationChanged(boolean z) {
                Button button = DatePickerDialog.this.getButton(-1);
                if (button != null) {
                    button.setEnabled(z);
                }
            }
        };
        this.mValidationCallback = validationCallback;
        Context context2 = getContext();
        View viewInflate = LayoutInflater.from(context2).inflate(R.layout.date_picker_dialog, (ViewGroup) null);
        setView(viewInflate);
        setButton(-1, context2.getString(17039370), this);
        setButton(-2, context2.getString(17039360), this);
        setButtonPanelLayoutHint(1);
        if (calendar != null) {
            i2 = calendar.get(1);
            i3 = calendar.get(2);
            i4 = calendar.get(5);
        }
        DatePicker datePicker = (DatePicker) viewInflate.findViewById(R.id.datePicker);
        this.mDatePicker = datePicker;
        datePicker.init(i2, i3, i4, this);
        datePicker.setValidationCallback(validationCallback);
        this.mDateSetListener = onDateSetListener;
    }

    static int resolveDialogTheme(Context context, int i) {
        if (i != 0) {
            return i;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843948, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // android.widget.DatePicker.OnDateChangedListener
    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        this.mDatePicker.init(i, i2, i3, this);
    }

    public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
        this.mDateSetListener = onDateSetListener;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i != -2) {
            if (i == -1 && this.mDateSetListener != null) {
                this.mDatePicker.clearFocus();
                OnDateSetListener onDateSetListener = this.mDateSetListener;
                DatePicker datePicker = this.mDatePicker;
                onDateSetListener.onDateSet(datePicker, datePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
                return;
            }
            return;
        }
        cancel();
    }

    public DatePicker getDatePicker() {
        return this.mDatePicker;
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDatePicker.updateDate(i, i2, i3);
    }

    @Override // android.app.Dialog
    public Bundle onSaveInstanceState() {
        Bundle bundleOnSaveInstanceState = super.onSaveInstanceState();
        bundleOnSaveInstanceState.putInt("year", this.mDatePicker.getYear());
        bundleOnSaveInstanceState.putInt("month", this.mDatePicker.getMonth());
        bundleOnSaveInstanceState.putInt("day", this.mDatePicker.getDayOfMonth());
        return bundleOnSaveInstanceState;
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDatePicker.init(bundle.getInt("year"), bundle.getInt("month"), bundle.getInt("day"), this);
    }
}
