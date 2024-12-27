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

    public boolean getEmergencyCallOnly(boolean allAdmins) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.getEmergencyCallOnly(allAdmins);
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-getEmergencyCallOnly returning default value");
            return false;
        }
    }

    public boolean addNumberOfIncomingCalls() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.addNumberOfIncomingCalls();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-addNumberOfIncomingCalls returning default value");
            return false;
        }
    }

    public boolean addNumberOfOutgoingCalls() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.addNumberOfOutgoingCalls();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-addNumberOfOutgoingCalls returning default value");
            return false;
        }
    }

    public boolean isLimitNumberOfSmsEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isLimitNumberOfSmsEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isLimitNumberOfSmsEnabled returning default value");
            return false;
        }
    }

    public boolean addNumberOfIncomingSms() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.addNumberOfIncomingSms();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-addNumberOfIncomingSms returning default value");
            return false;
        }
    }

    public boolean addNumberOfOutgoingSms() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.addNumberOfOutgoingSms();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-addNumberOfOutgoingSms returning default value");
            return false;
        }
    }

    public boolean decreaseNumberOfOutgoingSms() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.decreaseNumberOfOutgoingSms();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-decreaseNumberOfOutgoingSms returning default value");
            return false;
        }
    }

    public boolean canOutgoingSms(String phoneNumber) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.canOutgoingSms(phoneNumber);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-canOutgoingSms returning default value");
            return true;
        }
    }

    public boolean canIncomingSms(String phoneNumber) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.canIncomingSms(phoneNumber);
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-canIncomingSms returning default value");
            return true;
        }
    }

    public boolean isSmsPatternCheckRequired() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isSmsPatternCheckRequired();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isSmsPatternCheckRequired returning default value");
            return false;
        }
    }

    public boolean isIncomingSmsAllowed() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isIncomingSmsAllowed();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isIncomingSmsAllowed returning default value");
            return true;
        }
    }

    public boolean isOutgoingSmsAllowed() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isOutgoingSmsAllowed();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isOutgoingSmsAllowed returning default value");
            return true;
        }
    }

    public boolean isIncomingMmsAllowed() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isIncomingMmsAllowed();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isIncomingMmsAllowed returning default value");
            return true;
        }
    }

    public boolean isBlockSmsWithStorageEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isBlockSmsWithStorageEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isBlockSmsWithStorageEnabled returning default value", e);
            return false;
        }
    }

    public boolean isBlockMmsWithStorageEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isBlockMmsWithStorageEnabled();
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isBlockMmsWithStorageEnabled returning default value", e);
            return false;
        }
    }

    public void storeBlockedSmsMms(boolean isSms, byte[] pdu, String srcAddress, int sendType, String timeStamp, String messageId, String subId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                lService.storeBlockedSmsMms(isSms, pdu, srcAddress, sendType, timeStamp, messageId, subId);
            }
        } catch (Exception e) {
            Log.d(TAG, "PXY-storeBlockedSmsMms fail to save sms/mms");
        }
    }

    public boolean isWapPushAllowed() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isWapPushAllowed();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isWapPushAllowed returning default value");
            return true;
        }
    }

    public boolean isCopyContactToSimAllowed(int message) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isCopyContactToSimAllowed(message);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isCopyContactToSimAllowed returning default value ", e);
            return true;
        }
    }

    public boolean isIncomingSmsAllowedFromSimSlot(int slotNum) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isIncomingSmsAllowedFromSimSlot(slotNum);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isIncomingSmsAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean isOutgoingSmsAllowedFromSimSlot(int slotNum) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isOutgoingSmsAllowedFromSimSlot(slotNum);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isOutgoingSmsAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean isMmsAllowedFromSimSlot(int slotNum) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isMmsAllowedFromSimSlot(slotNum);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isMmsAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean isOutgoingCallAllowedFromSimSlot(int simSlot) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isOutgoingCallAllowedFromSimSlot(simSlot);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-phoneRestrictionPolicy returning default value ", e);
            return true;
        }
    }

    public boolean isIncomingCallAllowedFromSimSlot(int simSlot) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isIncomingCallAllowedFromSimSlot(simSlot);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isIncomingCallAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }

    public boolean canOutgoingCall(String dialNumber) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.canOutgoingCall(dialNumber);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-canOutgoingCall returning default value ", e);
            return true;
        }
    }

    public boolean canIncomingCall(String dialNumber) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.canIncomingCall(dialNumber);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-canIncomingCall returning default value ", e);
            return true;
        }
    }

    public boolean isDataAllowedFromSimSlot(int simSlot) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isDataAllowedFromSimSlot(simSlot);
            }
            return true;
        } catch (Exception e) {
            Log.w(TAG, "PXY-isDataAllowedFromSimSlot returning default value ", e);
            return true;
        }
    }
}
