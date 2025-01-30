package com.android.systemui.hdmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.app.LocalePicker;
import com.android.systemui.R;
import com.android.systemui.p032tv.TvBottomSheetActivity;
import com.android.systemui.util.settings.SecureSettingsImpl;
import java.util.HashSet;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class HdmiCecSetMenuLanguageActivity extends TvBottomSheetActivity implements View.OnClickListener {
    public final HdmiCecSetMenuLanguageHelper mHdmiCecSetMenuLanguageHelper;

    public HdmiCecSetMenuLanguageActivity(HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper) {
        this.mHdmiCecSetMenuLanguageHelper = hdmiCecSetMenuLanguageHelper;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getId() == R.id.bottom_sheet_positive_button) {
            final HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper = this.mHdmiCecSetMenuLanguageHelper;
            hdmiCecSetMenuLanguageHelper.getClass();
            hdmiCecSetMenuLanguageHelper.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocalePicker.updateLocale(HdmiCecSetMenuLanguageHelper.this.mLocale);
                }
            });
        } else {
            HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper2 = this.mHdmiCecSetMenuLanguageHelper;
            HashSet hashSet = hdmiCecSetMenuLanguageHelper2.mDenylist;
            hashSet.add(hdmiCecSetMenuLanguageHelper2.mLocale.toLanguageTag());
            ((SecureSettingsImpl) hdmiCecSetMenuLanguageHelper2.mSecureSettings).putStringForUser(-2, "hdmi_cec_set_menu_language_denylist", String.join(",", hashSet));
        }
        finish();
    }

    @Override // com.android.systemui.p032tv.TvBottomSheetActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addPrivateFlags(524288);
        String stringExtra = getIntent().getStringExtra("android.hardware.hdmi.extra.LOCALE");
        HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper = this.mHdmiCecSetMenuLanguageHelper;
        hdmiCecSetMenuLanguageHelper.getClass();
        hdmiCecSetMenuLanguageHelper.mLocale = Locale.forLanguageTag(stringExtra);
        HdmiCecSetMenuLanguageHelper hdmiCecSetMenuLanguageHelper2 = this.mHdmiCecSetMenuLanguageHelper;
        if (hdmiCecSetMenuLanguageHelper2.mDenylist.contains(hdmiCecSetMenuLanguageHelper2.mLocale.toLanguageTag())) {
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        String string = getString(R.string.hdmi_cec_set_menu_language_title, new Object[]{this.mHdmiCecSetMenuLanguageHelper.mLocale.getDisplayLanguage()});
        String string2 = getString(R.string.hdmi_cec_set_menu_language_description);
        TextView textView = (TextView) findViewById(R.id.bottom_sheet_title);
        TextView textView2 = (TextView) findViewById(R.id.bottom_sheet_body);
        ImageView imageView = (ImageView) findViewById(R.id.bottom_sheet_icon);
        ImageView imageView2 = (ImageView) findViewById(R.id.bottom_sheet_second_icon);
        Button button = (Button) findViewById(R.id.bottom_sheet_positive_button);
        Button button2 = (Button) findViewById(R.id.bottom_sheet_negative_button);
        textView.setText(string);
        textView2.setText(string2);
        imageView.setImageResource(android.R.drawable.ic_qs_battery_saver);
        imageView2.setVisibility(8);
        button.setText(R.string.hdmi_cec_set_menu_language_accept);
        button.setOnClickListener(this);
        button2.setText(R.string.hdmi_cec_set_menu_language_decline);
        button2.setOnClickListener(this);
        button2.requestFocus();
    }
}
