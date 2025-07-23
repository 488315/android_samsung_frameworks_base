package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class PhoneRestrictionPolicy {
    public static final String ACTION_ICCID_AVAILABLE_INTERNAL = "com.samsung.android.knox.intent.action.ICCID_AVAILABLE_INTERNAL";
    public static final String ACTION_PHONE_READY_INTERNAL = "com.samsung.android.knox.intent.action.PHONE_READY_INTERNAL";
    public static final String ACTION_SEND_BLOCKED_MMS_INTERNAL = "com.samsung.android.knox.intent.action.SEND_BLOCKED_MMS_INTERNAL";
    public static final String ACTION_SEND_BLOCKED_SMS_INTERNAL = "com.samsung.android.knox.intent.action.SEND_BLOCKED_SMS_INTERNAL";
    public static final String EXTRA_ORIG_ADDRESS_INTERNAL = "com.samsung.android.knox.intent.extra.ORIG_ADDRESS_INTERNAL";
    public static final String EXTRA_PDU_INTERNAL = "com.samsung.android.knox.intent.extra.PDU_INTERNAL";
    public static final String EXTRA_SEND_TYPE_INTERNAL = "com.samsung.android.knox.intent.extra.SEND_TYPE_INTERNAL";
    public static final String EXTRA_TIME_STAMP_INTERNAL = "com.samsung.android.knox.intent.extra.TIME_STAMP_INTERNAL";
    public static final String PERMISSION_RECEIVE_BLOCKED_SMS_MMS_INTERNAL = "com.samsung.android.knox.permission.KNOX_RECEIVE_BLOCKED_SMS_MMS_INTERNAL";
    public static final int RCS_FEATURE_ALL = 1;
    public static final int SENDTYPE_GENERIC = -1;
    private static String TAG = "PhoneRestrictionPolicy";

    public boolean getEmergencyCallOnly(boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getEmergencyCallOnly(z);
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getEmergencyCallOnly returning default value");
            return false;
        }
    }

    public boolean addNumberOfIncomingCalls() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.addNumberOfIncomingCalls();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-addNumberOfIncomingCalls returning default value");
            return false;
        }
    }

    public boolean addNumberOfOutgoingCalls() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.addNumberOfOutgoingCalls();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-addNumberOfOutgoingCalls returning default value");
            return false;
        }
    }

    public boolean isLimitNumberOfSmsEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isLimitNumberOfSmsEnabled();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isLimitNumberOfSmsEnabled returning default value");
            return false;
        }
    }

    public boolean addNumberOfIncomingSms() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.addNumberOfIncomingSms();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-addNumberOfIncomingSms returning default value");
            return false;
        }
    }

    public boolean addNumberOfOutgoingSms() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.addNumberOfOutgoingSms();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-addNumberOfOutgoingSms returning default value");
            return false;
        }
    }

    public boolean decreaseNumberOfOutgoingSms() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.decreaseNumberOfOutgoingSms();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-decreaseNumberOfOutgoingSms returning default value");
            return false;
        }
    }

    public boolean canOutgoingSms(String str) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.canOutgoingSms(str);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-canOutgoingSms returning default value");
            return true;
        }
    }

    public boolean canIncomingSms(String str) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.canIncomingSms(str);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-canIncomingSms returning default value");
            return true;
        }
    }

    public boolean isSmsPatternCheckRequired() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isSmsPatternCheckRequired();
            }
            return false;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isSmsPatternCheckRequired returning default value");
            return false;
        }
    }

    public boolean isIncomingSmsAllowed() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isIncomingSmsAllowed();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isIncomingSmsAllowed returning default value");
            return true;
        }
    }

    public boolean isOutgoingSmsAllowed() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isOutgoingSmsAllowed();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isOutgoingSmsAllowed returning default value");
            return true;
        }
    }

    public boolean isIncomingMmsAllowed() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isIncomingMmsAllowed();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isIncomingMmsAllowed returning default value");
            return true;
        }
    }

    public boolean isBlockSmsWithStorageEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isBlockSmsWithStorageEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isBlockSmsWithStorageEnabled returning default value", e);
            return false;
        }
    }

    public boolean isBlockMmsWithStorageEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isBlockMmsWithStorageEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isBlockMmsWithStorageEnabled returning default value", e);
            return false;
        }
    }

    public void storeBlockedSmsMms(boolean z, byte[] bArr, String str, int i, String str2, String str3, String str4) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.storeBlockedSmsMms(z, bArr, str, i, str2, str3, str4);
            }
        } catch (Exception unused) {
            Log.d(TAG, "PXY-storeBlockedSmsMms fail to save sms/mms");
        }
    }

    public boolean isWapPushAllowed() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isWapPushAllowed();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isWapPushAllowed returning default value");
            return true;
        }
    }

    public boolean isCopyContactToSimAllowed(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isCopyContactToSimAllowed(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isCopyContactToSimAllowed returning default value ", e);
            return true;
        }
    }

    public boolean isIncomingSmsAllowedFromSimSlot(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isIncomingSmsAllowedFromSimSlot(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isIncomingSmsAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean isOutgoingSmsAllowedFromSimSlot(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isOutgoingSmsAllowedFromSimSlot(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isOutgoingSmsAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean isMmsAllowedFromSimSlot(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isMmsAllowedFromSimSlot(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isMmsAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean isOutgoingCallAllowedFromSimSlot(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isOutgoingCallAllowedFromSimSlot(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-phoneRestrictionPolicy returning default value ", e);
            return true;
        }
    }

    public boolean isIncomingCallAllowedFromSimSlot(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isIncomingCallAllowedFromSimSlot(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isIncomingCallAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean canOutgoingCall(String str) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.canOutgoingCall(str);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-canOutgoingCall returning default value ", e);
            return true;
        }
    }

    public boolean canIncomingCall(String str) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.canIncomingCall(str);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-canIncomingCall returning default value ", e);
            return true;
        }
    }

    public boolean isDataAllowedFromSimSlot(int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isDataAllowedFromSimSlot(i);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isDataAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }
}
