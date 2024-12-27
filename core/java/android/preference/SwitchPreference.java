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
public class SwitchPreference extends TwoStatePreference {
    private static final ClickListener mClickListener = new ClickListener();
    private final Listener mListener;
    private CharSequence mSwitchOff;
    private CharSequence mSwitchOn;

    private class Listener implements CompoundButton.OnCheckedChangeListener {
        private Listener() {}

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!SwitchPreference.this.callChangeListener(Boolean.valueOf(isChecked))) {
                buttonView.setChecked(!isChecked);
            } else {
                SwitchPreference.this.setChecked(isChecked);
            }
        }
    }

    private static class ClickListener implements View.OnClickListener {
        private ClickListener() {}

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {}
    }

    public SwitchPreference(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mListener = new Listener();
        Configuration configuration = context.getResources().getConfiguration();
        if ((configuration.screenWidthDp <= 320 && configuration.fontScale >= 1.1f)
                || (configuration.screenWidthDp < 411 && configuration.fontScale >= 1.3f)) {
            setLayoutResource(R.layout.tw_preference_switch_large);
        }
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.SwitchPreference, defStyleAttr, defStyleRes);
        setSummaryOn(a.getString(0));
        setSummaryOff(a.getString(1));
        setSwitchTextOn(a.getString(3));
        setSwitchTextOff(a.getString(4));
        setDisableDependentsState(a.getBoolean(2, false));
        a.recycle();
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 16843629);
    }

    public SwitchPreference(Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        KeyEvent.Callback findViewById = view.findViewById(16908352);
        if (findViewById != null && (findViewById instanceof Checkable)) {
            if (findViewById instanceof Switch) {
                ((Switch) findViewById).setOnCheckedChangeListener(null);
            }
            ((Checkable) findViewById).setChecked(this.mChecked);
            if (findViewById instanceof Switch) {
                Switch switchView = (Switch) findViewById;
                switchView.setTextOn(this.mSwitchOn);
                switchView.setTextOff(this.mSwitchOff);
                switchView.setOnCheckedChangeListener(this.mListener);
                if (switchView.isClickable()) {
                    switchView.setOnClickListener(mClickListener);
                }
                AccessibilityManager accessibilityManager =
                        AccessibilityManager.getInstance(view.getContext());
                boolean enable =
                        accessibilityManager.semIsAccessibilityServiceEnabled(32)
                                || accessibilityManager.semIsAccessibilityServiceEnabled(16)
                                || accessibilityManager.semIsAccessibilityServiceEnabled(64);
                if (enable && !(this instanceof SemSwitchPreferenceScreen)) {
                    switchView.setBackground(null);
                    switchView.setClickable(false);
                }
            }
        }
        syncSummaryView(view);
    }

    public void setSwitchTextOn(CharSequence onText) {
        this.mSwitchOn = onText;
        notifyChanged();
    }

    public void setSwitchTextOff(CharSequence offText) {
        this.mSwitchOff = offText;
        notifyChanged();
    }

    public void setSwitchTextOn(int resId) {
        setSwitchTextOn(getContext().getString(resId));
    }

    public void setSwitchTextOff(int resId) {
        setSwitchTextOff(getContext().getString(resId));
    }

    public CharSequence getSwitchTextOn() {
        return this.mSwitchOn;
    }

    public CharSequence getSwitchTextOff() {
        return this.mSwitchOff;
    }
}
