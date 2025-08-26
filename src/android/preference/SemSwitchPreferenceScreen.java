package android.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes3.dex */
public class SemSwitchPreferenceScreen extends SwitchPreference {
    @Override // android.preference.TwoStatePreference, android.preference.Preference
    @Deprecated
    protected void onClick() {
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Preference, i, i2);
        Configuration configuration = context.getResources().getConfiguration();
        if ("".equals(typedArrayObtainStyledAttributes.getString(13))) {
            Log.w("SemSwitchPreferenceScreen", "SwitchPreferenceScreen should get fragment property. Fragment property does not exist in SwitchPreferenceScreen");
        }
        if ((configuration.screenWidthDp <= 320 && configuration.fontScale >= 1.1f) || (configuration.screenWidthDp < 411 && configuration.fontScale >= 1.3f)) {
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
        typedArrayObtainStyledAttributes.recycle();
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843629);
    }

    @Deprecated
    public SemSwitchPreferenceScreen(Context context) {
        this(context, null);
    }

    public void performClick() {
        super.onClick();
    }

    @Override // android.preference.Preference
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        boolean zIsChecked = isChecked();
        boolean z = isRTL() && hasRTL();
        if (action == 0 && isEnabled()) {
            if (keyCode != 21) {
                if (keyCode == 22) {
                    if (z && zIsChecked) {
                        if (callChangeListener(false)) {
                            setChecked(false);
                        }
                        return true;
                    }
                    if (!z && !zIsChecked) {
                        if (callChangeListener(true)) {
                            setChecked(true);
                        }
                        return true;
                    }
                }
            } else {
                if (z && !zIsChecked) {
                    if (callChangeListener(true)) {
                        setChecked(true);
                    }
                    return true;
                }
                if (!z && zIsChecked) {
                    if (callChangeListener(false)) {
                        setChecked(false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.preference.SwitchPreference, android.preference.Preference
    protected void onBindView(View view) throws Resources.NotFoundException {
        super.onBindView(view);
        TextView textView = (TextView) view.findViewById(16908310);
        View viewFindViewById = view.findViewById(16908352);
        if (textView == null || viewFindViewById == null) {
            return;
        }
        viewFindViewById.semSetHoverPopupType(0);
        viewFindViewById.setContentDescription(textView.getText().toString());
    }
}
