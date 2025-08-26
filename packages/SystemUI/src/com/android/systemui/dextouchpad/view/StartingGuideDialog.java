package com.android.systemui.dextouchpad.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.systemui.R;
import com.android.systemui.dextouchpad.settings.Settings$Key;
import com.android.systemui.dextouchpad.settings.SettingsKeys;
import com.android.systemui.dextouchpad.settings.SettingsRepository;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.shared.system.QuickStepContract;

/* loaded from: classes2.dex */
public class StartingGuideDialog extends AlertDialog {
    public final SettingsRepository mSettingsRepo;

    public StartingGuideDialog(Context context, int i) {
        super(context);
        this.mSettingsRepo = SettingsRepository.getInstance(context);
        setTitle(Features.IS_SUPPORT_TABLET ? R.string.dex_touchpad_dialog_how_to_start_touchpad_tablet : R.string.dex_touchpad_dialog_how_to_start_touchpad);
        setMessage(QuickStepContract.isGesturalMode(i) ? context.getResources().getString(R.string.dex_touchpad_dialog_how_to_start_touchpad_desc) : context.getResources().getString(R.string.dex_touchpad_dialog_how_to_start_touchpad_navbar_desc));
        semSetBackgroundBlurEnabled(true);
        setButton(-1, context.getResources().getText(R.string.dex_touchpad_dialog_btn_ok), new DialogInterface.OnClickListener() { // from class: com.android.systemui.dextouchpad.view.StartingGuideDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                if (Features.DEBUG) {
                    Log.d("StartGuideDialog", PopupUIUtil.EXTRA_SIM_CARD_TRAY_WATER_PROTECTION_POPUP_DISMISS);
                }
                StartingGuideDialog.this.dismiss();
            }
        });
        View viewInflate = LayoutInflater.from(context).inflate(QuickStepContract.isGesturalMode(i) ? R.layout.dialog_start_touchpad_on_your_phone_noti : R.layout.dialog_start_touchpad_on_your_phone_navbar, (ViewGroup) null);
        Configuration configuration = context.getResources().getConfiguration();
        int i2 = ((configuration.screenHeightDp * configuration.densityDpi) / 160) - 34;
        if (viewInflate.getHeight() > i2) {
            ViewGroup.LayoutParams layoutParams = viewInflate.getLayoutParams();
            layoutParams.height = i2;
            viewInflate.setLayoutParams(layoutParams);
        }
        setView(viewInflate);
        Window window = getWindow();
        if (window != null) {
            window.setType(2008);
        }
    }

    @Override // android.app.Dialog
    public final void show() {
        if (Features.DEBUG) {
            Log.d("StartGuideDialog", "show");
        }
        super.show();
        SettingsRepository settingsRepository = this.mSettingsRepo;
        Settings$Key settings$Key = SettingsKeys.TOUCHPAD_STARTING_GUIDE;
        synchronized (settingsRepository.mLock) {
            settingsRepository.mPrefs.edit().putString(settings$Key.mName, "confirmed").apply();
        }
    }
}
