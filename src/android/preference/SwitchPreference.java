package android.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public class SwitchPreference extends TwoStatePreference {
    private static final ClickListener mClickListener = new ClickListener();
    private final Listener mListener;
    private CharSequence mSwitchOff;
    private CharSequence mSwitchOn;

    private class Listener implements CompoundButton.OnCheckedChangeListener {
        private Listener() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (!SwitchPreference.this.callChangeListener(Boolean.valueOf(z))) {
                compoundButton.setChecked(!z);
            } else {
                SwitchPreference.this.setChecked(z);
            }
        }
    }

    private static class ClickListener implements View.OnClickListener {
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
        }

        private ClickListener() {
        }
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mListener = new Listener();
        Configuration configuration = context.getResources().getConfiguration();
        if ((configuration.screenWidthDp <= 320 && configuration.fontScale >= 1.1f) || (configuration.screenWidthDp < 411 && configuration.fontScale >= 1.3f)) {
            setLayoutResource(R.layout.tw_preference_switch_large);
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SwitchPreference, i, i2);
        setSummaryOn(obtainStyledAttributes.getString(0));
        setSummaryOff(obtainStyledAttributes.getString(1));
        setSwitchTextOn(obtainStyledAttributes.getString(3));
        setSwitchTextOff(obtainStyledAttributes.getString(4));
        setDisableDependentsState(obtainStyledAttributes.getBoolean(2, false));
        obtainStyledAttributes.recycle();
    }

    public SwitchPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843629);
    }

    public SwitchPreference(Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        KeyEvent.Callback findViewById = view.findViewById(16908352);
        if (findViewById != null && (findViewById instanceof Checkable)) {
            boolean z = findViewById instanceof Switch;
            if (z) {
                ((Switch) findViewById).setOnCheckedChangeListener(null);
            }
            ((Checkable) findViewById).setChecked(this.mChecked);
            if (z) {
                Switch r0 = (Switch) findViewById;
                r0.setTextOn(this.mSwitchOn);
                r0.setTextOff(this.mSwitchOff);
                r0.setOnCheckedChangeListener(this.mListener);
                if (r0.isClickable()) {
                    r0.setOnClickListener(mClickListener);
                }
                AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(view.getContext());
                if ((accessibilityManager.semIsAccessibilityServiceEnabled(32) || accessibilityManager.semIsAccessibilityServiceEnabled(16) || accessibilityManager.semIsAccessibilityServiceEnabled(64)) && !(this instanceof SemSwitchPreferenceScreen)) {
                    r0.setBackground(null);
                    r0.setClickable(false);
                }
            }
        }
        syncSummaryView(view);
    }

    public void setSwitchTextOn(CharSequence charSequence) {
        this.mSwitchOn = charSequence;
        notifyChanged();
    }

    public void setSwitchTextOff(CharSequence charSequence) {
        this.mSwitchOff = charSequence;
        notifyChanged();
    }

    public void setSwitchTextOn(int i) {
        setSwitchTextOn(getContext().getString(i));
    }

    public void setSwitchTextOff(int i) {
        setSwitchTextOff(getContext().getString(i));
    }

    public CharSequence getSwitchTextOn() {
        return this.mSwitchOn;
    }

    public CharSequence getSwitchTextOff() {
        return this.mSwitchOff;
    }
}
