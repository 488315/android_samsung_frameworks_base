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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIConditionChecker implements ConditionChecker {
    public final ConditionChecker mDefaultSystemCondition;
    public final LogWrapper mLogWrapper;
    public final UtilFactory mUtilFactory;

    public SystemUIConditionChecker(UtilFactory utilFactory, ConditionChecker conditionChecker, LogWrapper logWrapper) {
        this.mUtilFactory = utilFactory;
        this.mDefaultSystemCondition = conditionChecker;
        this.mLogWrapper = logWrapper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x010a, code lost:
    
        if (r7.getPhoneRestrictionPolicy(null).checkEnableUseOfPacketData(true) != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x022d, code lost:
    
        if (r7.getBundle("function_key_setting").getBoolean("grayout") != false) goto L52;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0239  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEnabled(Object obj) {
        boolean isEnabled;
        boolean switchState;
        String obj2 = obj.toString();
        boolean z = true;
        if (obj2.equals(SystemUIConditions.IS_CLEAR_COVER_CLOSED.toString())) {
            CoverUtilWrapper coverUtilWrapper = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState = coverUtilWrapper.mCoverState;
            if (coverState != null && coverState.getType() == 8) {
                switchState = coverUtilWrapper.mCoverState.getSwitchState();
                isEnabled = !switchState;
            }
            isEnabled = false;
        } else if (obj2.equals(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED.toString())) {
            CoverUtilWrapper coverUtilWrapper2 = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState2 = coverUtilWrapper2.mCoverState;
            if (coverState2 != null && coverState2.getType() == 15) {
                switchState = coverUtilWrapper2.mCoverState.getSwitchState();
                isEnabled = !switchState;
            }
            isEnabled = false;
        } else if (obj2.equals(SystemUIConditions.IS_CLEAR_CAMERA_VIEW_COVER_CLOSED.toString())) {
            CoverUtilWrapper coverUtilWrapper3 = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
            CoverState coverState3 = coverUtilWrapper3.mCoverState;
            if (coverState3 != null && coverState3.getType() == 17) {
                switchState = coverUtilWrapper3.mCoverState.getSwitchState();
                isEnabled = !switchState;
            }
            isEnabled = false;
        } else {
            if (obj2.equals(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED.toString())) {
                CoverUtilWrapper coverUtilWrapper4 = (CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class);
                CoverState coverState4 = coverUtilWrapper4.mCoverState;
                if (coverState4 != null && coverState4.getType() == 16) {
                    switchState = coverUtilWrapper4.mCoverState.getSwitchState();
                    isEnabled = !switchState;
                }
            } else {
                if (!obj2.equals(SystemUIConditions.IS_MINI_OPEN_COVER.toString())) {
                    if (obj2.equals(SystemUIConditions.IS_CELLULAR_DATA_ALLOWED.toString())) {
                        EnterpriseDeviceManager enterpriseDeviceManager = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM;
                        if (enterpriseDeviceManager.getRestrictionPolicy().isCellularDataAllowed()) {
                        }
                        z = false;
                    } else if (obj2.equals(SystemUIConditions.IS_SETTINGS_CHANGES_ALLOWED.toString())) {
                        isEnabled = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM.getRestrictionPolicy().isSettingsChangesAllowed(false);
                    } else if (obj2.equals(SystemUIConditions.IS_POWER_OFF_ALLOWED.toString())) {
                        isEnabled = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM.getRestrictionPolicy().isPowerOffAllowed(true);
                    } else if (obj2.equals(SystemUIConditions.IS_SAFE_MODE_ALLOWED.toString())) {
                        isEnabled = ((KnoxEDMWrapper) this.mUtilFactory.get(KnoxEDMWrapper.class)).mEDM.getRestrictionPolicy().isSafeModeAllowed();
                    } else if (obj2.equals(SystemUIConditions.GET_PROKIOSK_STATE.toString())) {
                        isEnabled = ((ProKioskManagerWrapper) this.mUtilFactory.get(ProKioskManagerWrapper.class)).mProKioskManager.getProKioskState();
                    } else if (obj2.equals(SystemUIConditions.GET_POWER_DIALOG_CUSTOM_ITEMS_STATE.toString())) {
                        isEnabled = ((KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class)).mKnoxCustomManager.getPowerDialogCustomItemsState();
                    } else if (obj2.equals(SystemUIConditions.IS_ALLOWED_SHOW_ACTIONS.toString())) {
                        isEnabled = ((KnoxCustomManagerWrapper) this.mUtilFactory.get(KnoxCustomManagerWrapper.class)).mKnoxCustomManager.getPowerMenuLockedState();
                    } else if (obj2.equals(SystemUIConditions.IS_KIOSK_MODE.toString())) {
                        isEnabled = ((KioskModeWrapper) this.mUtilFactory.get(KioskModeWrapper.class)).mKioskMode.isKioskModeEnabled();
                    } else if (obj2.equals(SystemUIConditions.IS_FUNCTION_KEY_SETTING_HIDE.toString())) {
                        Bundle applicationRestrictions = ((SemEnterpriseDeviceManagerWrapper) this.mUtilFactory.get(SemEnterpriseDeviceManagerWrapper.class)).mSemEnterpriseDeviceManager.getApplicationRestrictions(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                        if (applicationRestrictions != null) {
                            if (!applicationRestrictions.isEmpty()) {
                                if (applicationRestrictions.containsKey("function_key_setting")) {
                                    if (!applicationRestrictions.getBundle("function_key_setting").getBoolean("hide")) {
                                    }
                                }
                            }
                        }
                        z = false;
                    } else {
                        isEnabled = this.mDefaultSystemCondition.isEnabled(obj);
                    }
                    if (!z) {
                        this.mLogWrapper.i("SystemUIConditionChecker", "[" + obj2.toLowerCase() + "] " + isEnabled);
                    }
                    return isEnabled;
                }
                if (((CoverUtilWrapper) this.mUtilFactory.get(CoverUtilWrapper.class)).mCoverState.getType() == 16) {
                    String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_COVER");
                    if (string == null || !string.contains("mini_open")) {
                        z = false;
                    }
                }
                isEnabled = z;
            }
            isEnabled = false;
        }
        z = false;
        if (!z) {
        }
        return isEnabled;
    }
}
