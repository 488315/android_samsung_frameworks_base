package com.android.server.policy;

import android.content.ComponentName;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.wm.OneHandOpPolicy;
import com.android.server.wm.OneHandOpPolicy$$ExternalSyntheticLambda0;
import com.samsung.android.rune.InputRune;
import com.samsung.android.view.SemWindowManager;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhoneWindowManagerExt$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PhoneWindowManagerExt$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PhoneWindowManagerExt.PenSoundInfo penSoundInfo;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                PhoneWindowManagerExt phoneWindowManagerExt = (PhoneWindowManagerExt) obj2;
                boolean z = Settings.System.getInt(phoneWindowManagerExt.mContext.getContentResolver(), "tvmode_state", 0) == 1;
                RCPManagerService$$ExternalSyntheticOutline0.m("PhoneWindowManagerExt", BatteryService$$ExternalSyntheticOutline0.m("updateSettings tvModeEnabled=", ", ", z), phoneWindowManagerExt.mTvModeEnabled);
                if (z != phoneWindowManagerExt.mTvModeEnabled) {
                    phoneWindowManagerExt.mTvModeEnabled = z;
                    phoneWindowManagerExt.updateKeyCustomizationInfoTvMode();
                    break;
                }
                break;
            case 1:
                PhoneWindowManagerExt phoneWindowManagerExt2 = (PhoneWindowManagerExt) obj2;
                boolean z2 = Settings.System.getInt(phoneWindowManagerExt2.mContext.getContentResolver(), "pwrkey_owner_status", 0) == 1;
                RCPManagerService$$ExternalSyntheticOutline0.m("PhoneWindowManagerExt", BatteryService$$ExternalSyntheticOutline0.m("updateSettings tvModeDoublePressEnabled=", ", ", z2), phoneWindowManagerExt2.mTvModeDoublePressEnabled);
                if (z2 != phoneWindowManagerExt2.mTvModeDoublePressEnabled) {
                    phoneWindowManagerExt2.mTvModeDoublePressEnabled = z2;
                    phoneWindowManagerExt2.updateKeyCustomizationInfoTvMode();
                    break;
                }
                break;
            case 2:
                PhoneWindowManagerExt phoneWindowManagerExt3 = (PhoneWindowManagerExt) obj2;
                String string = Settings.System.getString(phoneWindowManagerExt3.mContext.getContentResolver(), "double_tab_launch_component");
                Log.d("PhoneWindowManagerExt", "updateSettings doublePressLaunchComponent=" + string);
                phoneWindowManagerExt3.updateDoublePressLaunchInfo(string);
                if (InputRune.PWM_POWER_KEY_DOUBLE_PRESS_ATT_TV_MODE) {
                    ComponentName componentName = phoneWindowManagerExt3.mDoublePressLaunchComponentName;
                    if (componentName == null ? false : "com.samsung.tvmode/com.samsung.tvmode.activity.MainActivity".equals(componentName.flattenToString())) {
                        phoneWindowManagerExt3.updateKeyCustomizationInfoTvMode();
                        break;
                    }
                }
                break;
            case 3:
                PhoneWindowManagerExt phoneWindowManagerExt4 = (PhoneWindowManagerExt) obj2;
                phoneWindowManagerExt4.mQuickLaunchCameraBehavior = Settings.System.getInt(phoneWindowManagerExt4.mContext.getContentResolver(), "double_tab_launch", 2);
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("updateSettings, mQuickLaunchCameraBehavior="), phoneWindowManagerExt4.mQuickLaunchCameraBehavior, "PhoneWindowManagerExt");
                int i2 = phoneWindowManagerExt4.mQuickLaunchCameraBehavior;
                int i3 = (i2 == 1 || i2 == 0) ? 3 : 26;
                KeyCustomizationManager keyCustomizationManager = phoneWindowManagerExt4.mKeyCustomizationPolicy;
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo = keyCustomizationManager.mKeyCustomizationInfoManager.get(2001, 8, i3, null);
                if (i2 == 3 || i2 == 1) {
                    if (keyCustomizationInfo == null) {
                        keyCustomizationManager.putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(8, 2001, i3, 1));
                    }
                } else if ((i2 == 2 || i2 == 0) && keyCustomizationInfo != null) {
                    keyCustomizationManager.removeKeyCustomizationInfo(2001, 8, i3, null);
                }
                if (i3 == 26) {
                    phoneWindowManagerExt4.updateDoublePressPowerBehavior();
                    break;
                }
                break;
            case 4:
                PhoneWindowManagerExt phoneWindowManagerExt5 = (PhoneWindowManagerExt) obj2;
                String stringForUser = Settings.System.getStringForUser(phoneWindowManagerExt5.mContext.getContentResolver(), "pen_detachment_notification", -2);
                phoneWindowManagerExt5.mPenSoundFilePath = stringForUser;
                if (!TextUtils.isEmpty(stringForUser) && (penSoundInfo = phoneWindowManagerExt5.mPenSoundInfo) != null && !phoneWindowManagerExt5.mPenSoundFilePath.equals(penSoundInfo.mPenSoundPath)) {
                    phoneWindowManagerExt5.mPenSoundInfo.updatePenSound(phoneWindowManagerExt5.mPenSoundFilePath);
                    break;
                }
                break;
            default:
                PhoneWindowManagerExt.SettingsObserver settingsObserver = (PhoneWindowManagerExt.SettingsObserver) obj2;
                boolean z3 = Settings.System.getIntForUser(PhoneWindowManagerExt.this.mContext.getContentResolver(), "any_screen_enabled", 0, -2) == 1;
                OneHandOpPolicy oneHandOpPolicy = PhoneWindowManagerExt.this.mPolicy.mDefaultDisplayPolicy.mExt.mOneHandOpPolicy;
                oneHandOpPolicy.mIsOneHandOpEnabled = z3;
                if (oneHandOpPolicy.mBootCompleted) {
                    OneHandOpPolicy.OneHandOpMonitor oneHandOpMonitor = oneHandOpPolicy.mOneHandOpMonitor;
                    if (!z3) {
                        if (oneHandOpMonitor.mWatcher != null) {
                            ActivityManagerService$$ExternalSyntheticOutline0.m(3, new StringBuilder("stopService(), caller="), "OneHandOpPolicy");
                            oneHandOpPolicy.mReasonToStart = -1;
                            oneHandOpPolicy.mHandler.post(new OneHandOpPolicy$$ExternalSyntheticLambda0(oneHandOpPolicy, 2));
                            break;
                        }
                    } else if (oneHandOpMonitor.mWatcher == null) {
                        oneHandOpPolicy.startService(1);
                        break;
                    }
                }
                break;
        }
    }
}
