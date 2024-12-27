package android.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.android.internal.R;

public class SemSwitchPreferenceScreen extends SwitchPreference {
    @Deprecated
    public SemSwitchPreferenceScreen(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.Preference, defStyleAttr, defStyleRes);
        Configuration configuration = context.getResources().getConfiguration();
        String fragment = a.getString(13);
        if ("".equals(fragment)) {
            Log.w(
                    "SemSwitchPreferenceScreen",
                    "SwitchPreferenceScreen should get fragment property. Fragment property does"
                        + " not exist in SwitchPreferenceScreen");
        }
        if ((configuration.screenWidthDp <= 320 && configuration.fontScale >= 1.1f)
                || (configuration.screenWidthDp < 411 && configuration.fontScale >= 1.3f)) {
            setLayoutResource(R.layout.tw_switch_preference_screen_large);
        } else {
            setLayoutResource(R.layout.tw_switch_preference_screen_material);
        }
        if (this.mIsDeviceDefaultDark) {
            setWidgetLayoutResource(R.layout.tw_switch_preference_screen_widget_divider_dark);
        } else {
            setWidgetLayoutResource(R.layout.tw_switch_preference_screen_widget_divider);
        }
        setRecycleEnabled(true);
        a.recycle();
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context, AttributeSet attrs) {
        this(context, attrs, 16843629);
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context) {
        this(context, null);
    }

    @Override // android.preference.TwoStatePreference, android.preference.Preference
    @Deprecated
    protected void onClick() {}

    public void performClick() {
        super.onClick();
    }

    @Override // android.preference.Preference
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        int action = event.getAction();
        int pressedKeyCode = event.getKeyCode();
        boolean checked = isChecked();
        boolean isRTL = isRTL() && hasRTL();
        if (action != 0 || !isEnabled()) {
            return false;
        }
        switch (pressedKeyCode) {
            case 21:
                if (isRTL && !checked) {
                    if (callChangeListener(true)) {
                        setChecked(true);
                        break;
                    }
                } else if (!isRTL && checked) {
                    if (callChangeListener(false)) {
                        setChecked(false);
                        break;
                    }
                }
                break;
            case 22:
                if (isRTL && checked) {
                    if (callChangeListener(false)) {
                        setChecked(false);
                        break;
                    }
                } else if (!isRTL && !checked) {
                    if (callChangeListener(true)) {
                        setChecked(true);
                        break;
                    }
                }
                break;
        }
        return false;
    }

    @Override // android.preference.SwitchPreference, android.preference.Preference
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(16908310);
        View switchView = view.findViewById(16908352);
        if (titleView != null && switchView != null) {
            switchView.semSetHoverPopupType(0);
            switchView.setContentDescription(titleView.getText().toString());
        }
    }
}
