package com.android.systemui.globalactions.util;

import android.os.Bundle;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.samsung.android.cover.CoverState;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.UtilFactory;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* loaded from: classes2.dex */
public class SystemUIConditionChecker implements ConditionChecker {
    public final ConditionChecker mDefaultSystemCondition;
    public final LogWrapper mLogWrapper;
    public final UtilFactory mUtilFactory;

    public SystemUIConditionChecker(UtilFactory utilFactory, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mUtilFactory = utilFactory;
        this.mDefaultSystemCondition = conditionChecker;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0233  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEnabled(Object obj) {
        boolean zIsEnabled;
        String string;
        boolean switchState;
        String string2 = obj.toString();
        boolean z = true;
        if (string2.equals(SystemUIConditions.IS_CLEAR_COVER_CLOSED.toString())) {
            CoverUtilWrapper coverUtilWrapper = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState = coverUtilWrapper.mCoverState;
            if (coverState != null && coverState.getType() == 8) {
                switchState = coverUtilWrapper.mCoverState.getSwitchState();
                zIsEnabled = !switchState;
            }
            zIsEnabled = false;
        } else if (string2.equals(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED.toString())) {
            CoverUtilWrapper coverUtilWrapper2 = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState2 = coverUtilWrapper2.mCoverState;
            if (coverState2 != null && coverState2.getType() == 15) {
                switchState = coverUtilWrapper2.mCoverState.getSwitchState();
                zIsEnabled = !switchState;
            }
            zIsEnabled = false;
        } else if (string2.equals(SystemUIConditions.IS_CLEAR_CAMERA_VIEW_COVER_CLOSED.toString())) {
            CoverUtilWrapper coverUtilWrapper3 = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState3 = coverUtilWrapper3.mCoverState;
            if (coverState3 != null && coverState3.getType() == 17) {
                switchState = coverUtilWrapper3.mCoverState.getSwitchState();
                zIsEnabled = !switchState;
            }
            zIsEnabled = false;
        } else {
            if (!string2.equals(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED.toString())) {
                if (string2.equals(SystemUIConditions.IS_MINI_OPEN_COVER.toString())) {
                    if (((CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class)).mCoverState.getType() != 16 || (string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_COVER")) == null || !string.contains("mini_open")) {
                        z = false;
                    }
                    zIsEnabled = z;
                } else if (string2.equals(SystemUIConditions.IS_CELLULAR_DATA_ALLOWED.toString())) {
                    EnterpriseDeviceManager enterpriseDeviceManager = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM;
                    if (!enterpriseDeviceManager.getRestrictionPolicy().isCellularDataAllowed() || !enterpriseDeviceManager.getPhoneRestrictionPolicy().checkEnableUseOfPacketData(true)) {
                    }
                    zIsEnabled = z;
                } else if (string2.equals(SystemUIConditions.IS_SETTINGS_CHANGES_ALLOWED.toString())) {
                    zIsEnabled = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM.getRestrictionPolicy().isSettingsChangesAllowed(false);
                } else if (string2.equals(SystemUIConditions.IS_POWER_OFF_ALLOWED.toString())) {
                    zIsEnabled = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM.getRestrictionPolicy().isPowerOffAllowed(true);
                } else if (string2.equals(SystemUIConditions.IS_SAFE_MODE_ALLOWED.toString())) {
                    zIsEnabled = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM.getRestrictionPolicy().isSafeModeAllowed();
                } else if (string2.equals(SystemUIConditions.GET_PROKIOSK_STATE.toString())) {
                    zIsEnabled = ((ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class)).mProKioskManager.getProKioskState();
                } else if (string2.equals(SystemUIConditions.GET_POWER_DIALOG_CUSTOM_ITEMS_STATE.toString())) {
                    zIsEnabled = ((KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class)).mKnoxCustomManager.getPowerDialogCustomItemsState();
                } else if (string2.equals(SystemUIConditions.IS_ALLOWED_SHOW_ACTIONS.toString())) {
                    zIsEnabled = ((KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class)).mKnoxCustomManager.getPowerMenuLockedState();
                } else if (string2.equals(SystemUIConditions.IS_KIOSK_MODE.toString())) {
                    zIsEnabled = ((KioskModeWrapper) this.mUtilFactory.get(KioskModeWrapper.class)).mKioskMode.isKioskModeEnabled();
                } else if (string2.equals(SystemUIConditions.IS_FUNCTION_KEY_SETTING_HIDE.toString())) {
                    Bundle applicationRestrictions = ((SemEnterpriseDeviceManagerWrapper) this.mUtilFactory.get(SemEnterpriseDeviceManagerWrapper.class)).mSemEnterpriseDeviceManager.getApplicationRestrictions(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                    if (applicationRestrictions == null || applicationRestrictions.isEmpty() || !applicationRestrictions.containsKey("function_key_setting") || (!applicationRestrictions.getBundle("function_key_setting").getBoolean("hide") && !applicationRestrictions.getBundle("function_key_setting").getBoolean("grayout"))) {
                    }
                    zIsEnabled = z;
                } else {
                    zIsEnabled = this.mDefaultSystemCondition.isEnabled(obj);
                }
                if (!z) {
                    this.mLogWrapper.i("SystemUIConditionChecker", "[" + string2.toLowerCase() + "] " + zIsEnabled);
                }
                return zIsEnabled;
            }
            CoverUtilWrapper coverUtilWrapper4 = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState4 = coverUtilWrapper4.mCoverState;
            if (coverState4 != null && coverState4.getType() == 16) {
                switchState = coverUtilWrapper4.mCoverState.getSwitchState();
                zIsEnabled = !switchState;
            }
            zIsEnabled = false;
        }
        z = false;
        if (!z) {
        }
        return zIsEnabled;
    }
}
