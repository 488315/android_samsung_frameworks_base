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

/* loaded from: classes.dex */
public class SeslSwitchPreferenceScreen extends SwitchPreferenceCompat {
    public final AnonymousClass1 mSwitchKeyListener;

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
                    if (seslSwitchPreferenceScreen.mChecked) {
                        if (seslSwitchPreferenceScreen.callChangeListener(Boolean.FALSE)) {
                            SeslSwitchPreferenceScreen.this.setChecked(false);
                        }
                        return true;
                    }
                } else if (keyCode == 22) {
                    SeslSwitchPreferenceScreen seslSwitchPreferenceScreen2 = SeslSwitchPreferenceScreen.this;
                    if (!seslSwitchPreferenceScreen2.mChecked) {
                        if (seslSwitchPreferenceScreen2.callChangeListener(Boolean.TRUE)) {
                            SeslSwitchPreferenceScreen.this.setChecked(true);
                        }
                        return true;
                    }
                }
                return false;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        String string = typedArrayObtainStyledAttributes.getString(13);
        if (string == null || string.equals("")) {
            Log.w("SwitchPreferenceScreen", "SwitchPreferenceScreen should getfragment property. Fragment property does not exsit in SwitchPreferenceScreen");
        }
        this.mLayoutResId = R.layout.sesl_preference_switch_screen;
        this.mWidgetLayoutResId = R.layout.sesl_switch_preference_screen_widget_divider;
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this.mSwitchKeyListener);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        View viewFindViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        View viewFindViewById2 = preferenceViewHolder.findViewById(R.id.switch_widget);
        if (textView == null || viewFindViewById == null || viewFindViewById2 == null) {
            return;
        }
        SeslViewReflector.semSetHoverPopupType(viewFindViewById, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
        viewFindViewById.setContentDescription(textView.getText().toString());
        viewFindViewById2.setContentDescription(textView.getText().toString());
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
