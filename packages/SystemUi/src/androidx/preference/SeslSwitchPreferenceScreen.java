package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslSwitchPreferenceScreen extends SwitchPreferenceCompat {
    public final ViewOnKeyListenerC04231 mSwitchKeyListener;

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.preference.SeslSwitchPreferenceScreen$1] */
    public SeslSwitchPreferenceScreen(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSwitchKeyListener = new View.OnKeyListener() { // from class: androidx.preference.SeslSwitchPreferenceScreen.1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i3, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                if (keyEvent.getAction() != 0) {
                    return false;
                }
                if (keyCode == 21) {
                    SeslSwitchPreferenceScreen seslSwitchPreferenceScreen = SeslSwitchPreferenceScreen.this;
                    if (!seslSwitchPreferenceScreen.mChecked) {
                        return false;
                    }
                    if (seslSwitchPreferenceScreen.callChangeListener(Boolean.FALSE)) {
                        SeslSwitchPreferenceScreen.this.setChecked(false);
                    }
                } else {
                    if (keyCode != 22) {
                        return false;
                    }
                    SeslSwitchPreferenceScreen seslSwitchPreferenceScreen2 = SeslSwitchPreferenceScreen.this;
                    if (seslSwitchPreferenceScreen2.mChecked) {
                        return false;
                    }
                    if (seslSwitchPreferenceScreen2.callChangeListener(Boolean.TRUE)) {
                        SeslSwitchPreferenceScreen.this.setChecked(true);
                    }
                }
                return true;
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        String string = obtainStyledAttributes.getString(13);
        if (string == null || string.equals("")) {
            Log.w("SwitchPreferenceScreen", "SwitchPreferenceScreen should getfragment property. Fragment property does not exsit in SwitchPreferenceScreen");
        }
        this.mLayoutResId = R.layout.sesl_preference_switch_screen;
        this.mWidgetLayoutResId = R.layout.sesl_switch_preference_screen_widget_divider;
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this.mSwitchKeyListener);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        View findViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        View findViewById2 = preferenceViewHolder.findViewById(R.id.switch_widget);
        if (textView == null || findViewById == null || findViewById2 == null) {
            return;
        }
        SeslViewReflector.semSetHoverPopupType(findViewById, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
        findViewById.setContentDescription(textView.getText().toString());
        findViewById2.setContentDescription(textView.getText().toString());
    }

    public SeslSwitchPreferenceScreen(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslSwitchPreferenceScreen(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchPreferenceStyle);
    }

    public SeslSwitchPreferenceScreen(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void callClickListener() {
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
    }
}
