package com.android.systemui.settings.brightness;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSSwitchPreference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class QuickBarBrightnessExtraBrightness {
    public final Context context;
    public View divider;
    public SecQSSwitchPreference extraBrightnessContainer;
    public TextView extraBrightnessSummary;
    public SwitchCompat extraBrightnessSwitch;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public QuickBarBrightnessExtraBrightness(Context context) {
        this.context = context;
    }

    public static final void access$putExtraBrightness(QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness, boolean z) {
        quickBarBrightnessExtraBrightness.getClass();
        Log.secD("QuickBarBrightnessExtraBrightness", "putExtraBrightness : " + (z ? 1 : 0));
        Settings.Secure.putIntForUser(quickBarBrightnessExtraBrightness.context.getContentResolver(), "screen_extra_brightness", z ? 1 : 0, -2);
    }

    public final void setExtraBrightnessLayoutClickListener() {
        SecQSSwitchPreference secQSSwitchPreference = this.extraBrightnessContainer;
        if (secQSSwitchPreference != null) {
            secQSSwitchPreference.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.QuickBarBrightnessExtraBrightness$setExtraBrightnessLayoutClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness = QuickBarBrightnessExtraBrightness.this;
                    SwitchCompat switchCompat = quickBarBrightnessExtraBrightness.extraBrightnessSwitch;
                    if (switchCompat != null) {
                        boolean z = !switchCompat.isChecked();
                        switchCompat.setChecked(z);
                        QuickBarBrightnessExtraBrightness.access$putExtraBrightness(quickBarBrightnessExtraBrightness, z);
                    }
                }
            });
        }
        final SwitchCompat switchCompat = this.extraBrightnessSwitch;
        if (switchCompat != null) {
            switchCompat.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.QuickBarBrightnessExtraBrightness$setExtraBrightnessLayoutClickListener$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SwitchCompat switchCompat2 = view instanceof SwitchCompat ? (SwitchCompat) view : null;
                    boolean z = false;
                    if (switchCompat2 != null && switchCompat2.isChecked()) {
                        z = true;
                    }
                    SwitchCompat switchCompat3 = SwitchCompat.this;
                    QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness = this;
                    switchCompat3.setChecked(z);
                    QuickBarBrightnessExtraBrightness.access$putExtraBrightness(quickBarBrightnessExtraBrightness, z);
                }
            });
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.settings.brightness.QuickBarBrightnessExtraBrightness$setExtraBrightnessLayoutClickListener$2$2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    SwitchCompat.this.setChecked(z);
                    QuickBarBrightnessExtraBrightness.access$putExtraBrightness(this, z);
                    SwitchCompat.this.announceForAccessibility(SwitchCompat.this.getContext().getString(z ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
        }
    }

    public final void setExtraBrightnessLayoutVisibilityLogic(Boolean bool) {
        View requireViewById;
        View requireViewById2;
        if (Intrinsics.areEqual(bool, Boolean.FALSE)) {
            setExtraBrightnessLayoutClickListener();
            SecQSSwitchPreference secQSSwitchPreference = this.extraBrightnessContainer;
            if (secQSSwitchPreference != null) {
                secQSSwitchPreference.setClickable(true);
            }
            SecQSSwitchPreference secQSSwitchPreference2 = this.extraBrightnessContainer;
            if (secQSSwitchPreference2 != null && (requireViewById2 = secQSSwitchPreference2.requireViewById(R.id.title)) != null) {
                requireViewById2.setAlpha(1.0f);
            }
            SwitchCompat switchCompat = this.extraBrightnessSwitch;
            if (switchCompat != null) {
                switchCompat.setEnabled(true);
                return;
            }
            return;
        }
        SecQSSwitchPreference secQSSwitchPreference3 = this.extraBrightnessContainer;
        if (secQSSwitchPreference3 != null) {
            secQSSwitchPreference3.setOnClickListener(null);
        }
        SecQSSwitchPreference secQSSwitchPreference4 = this.extraBrightnessContainer;
        if (secQSSwitchPreference4 != null) {
            secQSSwitchPreference4.setClickable(false);
        }
        SecQSSwitchPreference secQSSwitchPreference5 = this.extraBrightnessContainer;
        if (secQSSwitchPreference5 != null && (requireViewById = secQSSwitchPreference5.requireViewById(R.id.title)) != null) {
            requireViewById.setAlpha(0.4f);
        }
        SwitchCompat switchCompat2 = this.extraBrightnessSwitch;
        if (switchCompat2 != null) {
            switchCompat2.setOnCheckedChangeListener(null);
        }
        SwitchCompat switchCompat3 = this.extraBrightnessSwitch;
        if (switchCompat3 != null) {
            switchCompat3.setEnabled(false);
        }
    }
}
