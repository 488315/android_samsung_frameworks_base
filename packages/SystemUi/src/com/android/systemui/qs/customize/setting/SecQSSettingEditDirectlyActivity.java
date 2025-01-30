package com.android.systemui.qs.customize.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSSettingEditDirectlyActivity extends ComponentActivity {
    public final SecQSSettingEditDirectlyActivity$accessibilityDelegate$1 accessibilityDelegate = new AccessibilityDelegateCompat() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditDirectlyActivity$accessibilityDelegate$1
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setSelected(false);
            accessibilityNodeInfoCompat.setContentDescription(SecQSSettingEditDirectlyActivity.this.getString(R.string.editscreen_back_button_content_description) + ", Button");
        }
    };
    public final SecQSSettingEditResources editResources;
    public SwitchCompat onOffSwitch;
    public final SettingsHelper settingsHelper;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.customize.setting.SecQSSettingEditDirectlyActivity$accessibilityDelegate$1] */
    public SecQSSettingEditDirectlyActivity(SettingsHelper settingsHelper, SecQSSettingEditResources secQSSettingEditResources) {
        this.settingsHelper = settingsHelper;
        this.editResources = secQSSettingEditResources;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.qs_setting_edit_directly_activity);
        if (this.editResources.isPhoneLandscape()) {
            requireViewById(R.id.action_bar).setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height_phone_land));
        } else {
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditDirectlyActivity$onCreate$1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    SecQSSettingEditDirectlyActivity secQSSettingEditDirectlyActivity = SecQSSettingEditDirectlyActivity.this;
                    secQSSettingEditDirectlyActivity.editResources.getClass();
                    int sidePadding = SecQSSettingEditResources.getSidePadding(secQSSettingEditDirectlyActivity);
                    int dimensionPixelSize = SecQSSettingEditDirectlyActivity.this.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
                    SecQSSettingEditDirectlyActivity secQSSettingEditDirectlyActivity2 = SecQSSettingEditDirectlyActivity.this;
                    secQSSettingEditDirectlyActivity2.editResources.getClass();
                    int sidePadding2 = SecQSSettingEditResources.getSidePadding(secQSSettingEditDirectlyActivity2);
                    SecQSSettingEditDirectlyActivity secQSSettingEditDirectlyActivity3 = SecQSSettingEditDirectlyActivity.this;
                    secQSSettingEditDirectlyActivity3.editResources.getClass();
                    view.setPadding(sidePadding, dimensionPixelSize, sidePadding2, SecQSSettingEditResources.getBottomPadding(secQSSettingEditDirectlyActivity3, windowInsets));
                    return WindowInsets.CONSUMED;
                }
            });
        }
        requireViewById(R.id.action_arrow).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditDirectlyActivity$onCreate$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecQSSettingEditDirectlyActivity.this.finish();
            }
        });
        ((TextView) requireViewById(R.id.action_bar_text)).setText(R.string.qs_setting_instant_access);
        SwitchCompat switchCompat = (SwitchCompat) requireViewById(R.id.on_off_switch);
        this.onOffSwitch = switchCompat;
        switchCompat.setChecked(this.settingsHelper.isExpandQsAtOnceEnabled());
        boolean isChecked = switchCompat.isChecked();
        SwitchCompat switchCompat2 = this.onOffSwitch;
        if (switchCompat2 != null) {
            switchCompat2.setText(getResources().getText(isChecked ? R.string.qs_setting_instant_access_on : R.string.qs_setting_instant_access_off));
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.customize.setting.SecQSSettingEditDirectlyActivity$onCreate$5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SecQSSettingEditDirectlyActivity secQSSettingEditDirectlyActivity = SecQSSettingEditDirectlyActivity.this;
                Settings.Global.putInt(secQSSettingEditDirectlyActivity.settingsHelper.mContext.getContentResolver(), "swipe_directly_to_quick_setting", z ? 1 : 0);
                SwitchCompat switchCompat3 = secQSSettingEditDirectlyActivity.onOffSwitch;
                if (switchCompat3 != null) {
                    switchCompat3.setText(secQSSettingEditDirectlyActivity.getResources().getText(z ? R.string.qs_setting_instant_access_on : R.string.qs_setting_instant_access_off));
                }
                SecQSSettingEditResources secQSSettingEditResources = secQSSettingEditDirectlyActivity.editResources;
                secQSSettingEditResources.getClass();
                String str = z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                SharedPreferences.Editor editor = secQSSettingEditResources.editor;
                editor.putString("QPPS1027", str);
                editor.apply();
            }
        });
        ViewCompat.setAccessibilityDelegate(requireViewById(R.id.action_arrow), this.accessibilityDelegate);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.semAddExtensionFlags(16777216);
        getWindow().setAttributes(attributes);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
    }
}
