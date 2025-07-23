package com.android.systemui.dextouchpad.manager.notification;

import android.app.ActivityOptions;
import android.app.SemStatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Display;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.dextouchpad.activity.TouchpadActivity;
import com.android.systemui.dextouchpad.data.SPenGesturesGuideItems;
import com.android.systemui.dextouchpad.settings.Settings$Key;
import com.android.systemui.dextouchpad.settings.SettingsKeys;
import com.android.systemui.dextouchpad.settings.SettingsRepository;
import com.android.systemui.dextouchpad.util.Utils;
import com.android.systemui.dextouchpad.view.TouchpadSpenExternalGesturesDialog;
import com.android.systemui.dextouchpad.view.TouchpadSpenGesturesDialog;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DualModeReceiver extends BroadcastReceiver {
    public final Context mContext;
    public final SemStatusBarManager mSemStatusBarManager;
    public final SettingsRepository mSettingsRepo;

    public DualModeReceiver(Context context) {
        this.mContext = context;
        this.mSemStatusBarManager = (SemStatusBarManager) context.getSystemService(SemStatusBarManager.class);
        this.mSettingsRepo = SettingsRepository.getInstance(context);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        int intExtra = intent.getIntExtra("ACTION_CLICK_DISPLAYID", 0);
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(intExtra, "onReceive: action = ", action, " displayId = ", "DexTouchpadDualModeReceiver");
        if ("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_PRESSED".equals(action)) {
            this.mSemStatusBarManager.collapsePanels();
            if (TouchpadActivity.getActivity() != null) {
                TouchpadActivity activity = TouchpadActivity.getActivity();
                Display display = activity.mDisplayManager.getDisplay(intExtra);
                if (display == null) {
                    return;
                }
                boolean z = (activity.getResources().getConfiguration().uiMode & 48) == 32;
                SPenGesturesGuideItems sPenGesturesGuideItems = new SPenGesturesGuideItems();
                if (intExtra == 0) {
                    new TouchpadSpenGesturesDialog(activity, sPenGesturesGuideItems, z).show();
                    return;
                } else {
                    new TouchpadSpenExternalGesturesDialog(new ContextThemeWrapper(activity.createDisplayContext(display).createWindowContext(2008, null), 2132018766), sPenGesturesGuideItems, z).show();
                    return;
                }
            }
            return;
        }
        if ("com.samsung.android.desktopmode.action.SPEN_NOTIFICATION_CHANGE_MODE_PRESSED".equals(action)) {
            this.mSemStatusBarManager.collapsePanels();
            Intent flags = new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$DexModeSpenSettingsActivity")).setFlags(337641472);
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchDisplayId(Utils.mDesktopDisplayId);
            this.mContext.startActivityAsUser(flags, makeBasic.toBundle(), UserHandle.of(0));
            return;
        }
        if ("com.samsung.android.desktopmode.action.TOUCHPAD_AVAILABLE_NOTIFICATION_PRESSED".equals(action)) {
            ActivityOptions makeBasic2 = ActivityOptions.makeBasic();
            makeBasic2.setLaunchDisplayId(0);
            this.mContext.startActivityAsUser(Utils.getTouchActivityIntent(), makeBasic2.toBundle(), UserHandle.CURRENT);
            SettingsRepository settingsRepository = this.mSettingsRepo;
            Settings$Key settings$Key = SettingsKeys.TOUCHPAD_AUTO_RUN_GUIDE_COUNT;
            int i = settingsRepository.getInt(settings$Key);
            if (i < 3) {
                this.mSettingsRepo.putInt(settings$Key, i + 1);
            }
        }
    }
}
