package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Checkable;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public class CheckBoxPreference extends TwoStatePreference {
    public CheckBoxPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CheckBoxPreference, i, i2);
        setSummaryOn(obtainStyledAttributes.getString(0));
        setSummaryOff(obtainStyledAttributes.getString(1));
        setDisableDependentsState(obtainStyledAttributes.getBoolean(2, false));
        obtainStyledAttributes.recycle();
    }

    public CheckBoxPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842895);
    }

    public CheckBoxPreference(Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        KeyEvent.Callback findViewById = view.findViewById(16908289);
        if (findViewById != null && (findViewById instanceof Checkable)) {
            ((Checkable) findViewById).setChecked(this.mChecked);
        }
        syncSummaryView(view);
    }
}
