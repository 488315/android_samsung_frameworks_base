package android.provider;

import android.Manifest;
import android.annotation.SystemApi;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.telecom.Log;

@SystemApi
/* loaded from: classes3.dex */
public final class BlockedNumbersManager {

    @SystemApi
    public static final String ACTION_BLOCK_SUPPRESSION_STATE_CHANGED = "android.provider.action.BLOCK_SUPPRESSION_STATE_CHANGED";

    @SystemApi
    public static final String ENHANCED_SETTING_KEY_BLOCK_PAYPHONE = "block_payphone_calls_setting";

    @SystemApi
    public static final String ENHANCED_SETTING_KEY_BLOCK_PRIVATE = "block_private_number_calls_setting";

    @SystemApi
    public static final String ENHANCED_SETTING_KEY_BLOCK_UNAVAILABLE = "block_unavailable_calls_setting";

    @SystemApi
    public static final String ENHANCED_SETTING_KEY_BLOCK_UNKNOWN = "block_unknown_calls_setting";

    @SystemApi
    public static final String ENHANCED_SETTING_KEY_BLOCK_UNREGISTERED = "block_numbers_not_in_contacts_setting";

    @SystemApi
    public static final String ENHANCED_SETTING_KEY_SHOW_EMERGENCY_CALL_NOTIFICATION = "show_emergency_call_notification";
    private static final String LOG_TAG = BlockedNumbersManager.class.getSimpleName();
    private Context mContext;

    public BlockedNumbersManager(Context context) {
        this.mContext = context;
    }

    @SystemApi
    public void notifyEmergencyContact() {
        verifyBlockedNumbersPermission();
        try {
            Log.i(LOG_TAG, "notifyEmergencyContact; caller=%s", this.mContext.getOpPackageName());
            this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_NOTIFY_EMERGENCY_CONTACT, (String) null, (Bundle) null);
        } catch (IllegalArgumentException | NullPointerException e) {
            Log.w((String) null, "notifyEmergencyContact: provider not ready.", new Object[0]);
        }
    }

    @SystemApi
    public void endBlockSuppression() {
        verifyBlockedNumbersPermission();
        String caller = this.mContext.getOpPackageName();
        Log.i(LOG_TAG, "endBlockSuppression: caller=%s", caller);
        this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_END_BLOCK_SUPPRESSION, (String) null, (Bundle) null);
    }

    @SystemApi
    public int shouldSystemBlockNumber(String phoneNumber, int numberPresentation, boolean isNumberInContacts) {
        int blockResult;
        verifyBlockedNumbersPermission();
        try {
            String caller = this.mContext.getOpPackageName();
            Bundle extras = new Bundle();
            extras.putInt(BlockedNumberContract.EXTRA_CALL_PRESENTATION, numberPresentation);
            extras.putBoolean(BlockedNumberContract.EXTRA_CONTACT_EXIST, isNumberInContacts);
            Bundle res = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_SHOULD_SYSTEM_BLOCK_NUMBER, phoneNumber, extras);
            if (res != null) {
                blockResult = res.getInt(BlockedNumberContract.RES_BLOCK_STATUS, 0);
            } else {
                blockResult = 0;
            }
            Log.d(LOG_TAG, "shouldSystemBlockNumber: number=%s, caller=%s, result=%s", Log.piiHandle(phoneNumber), caller, BlockedNumberContract.SystemContract.blockStatusToString(blockResult));
            return blockResult;
        } catch (IllegalArgumentException | NullPointerException e) {
            Log.w((String) null, "shouldSystemBlockNumber: provider not ready.", new Object[0]);
            return 0;
        }
    }

    @SystemApi
    public BlockSuppressionStatus getBlockSuppressionStatus() {
        verifyBlockedNumbersPermission();
        Bundle res = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_GET_BLOCK_SUPPRESSION_STATUS, (String) null, (Bundle) null);
        BlockSuppressionStatus blockSuppressionStatus = new BlockSuppressionStatus(res.getBoolean(BlockedNumberContract.SystemContract.RES_IS_BLOCKING_SUPPRESSED, false), res.getLong(BlockedNumberContract.SystemContract.RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP, 0L));
        Log.d(LOG_TAG, "getBlockSuppressionStatus: caller=%s, status=%s", this.mContext.getOpPackageName(), blockSuppressionStatus);
        return blockSuppressionStatus;
    }

    @SystemApi
    public boolean shouldShowEmergencyCallNotification() {
        verifyBlockedNumbersPermission();
        try {
            Bundle res = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION, (String) null, (Bundle) null);
            if (res != null) {
                return res.getBoolean("show_emergency_call_notification", false);
            }
            return false;
        } catch (IllegalArgumentException | NullPointerException e) {
            Log.w((String) null, "shouldShowEmergencyCallNotification: provider not ready.", new Object[0]);
            return false;
        }
    }

    @SystemApi
    public boolean getBlockedNumberSetting(String key) {
        verifyBlockedNumbersPermission();
        Bundle extras = new Bundle();
        extras.putString(BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, key);
        try {
            Bundle res = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_GET_ENHANCED_BLOCK_SETTING, (String) null, extras);
            if (res != null) {
                return res.getBoolean(BlockedNumberContract.RES_ENHANCED_SETTING_IS_ENABLED, false);
            }
            return false;
        } catch (IllegalArgumentException | NullPointerException e) {
            Log.w((String) null, "getEnhancedBlockSetting: provider not ready.", new Object[0]);
            return false;
        }
    }

    @SystemApi
    public void setBlockedNumberSetting(String key, boolean value) {
        verifyBlockedNumbersPermission();
        Bundle extras = new Bundle();
        extras.putString(BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, key);
        extras.putBoolean(BlockedNumberContract.EXTRA_ENHANCED_SETTING_VALUE, value);
        this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_SET_ENHANCED_BLOCK_SETTING, (String) null, extras);
    }

    @SystemApi
    public static final class BlockSuppressionStatus {
        private boolean mIsSuppressed;
        private long mUntilTimestampMillis;

        public BlockSuppressionStatus(boolean isSuppressed, long untilTimestampMillis) {
            this.mIsSuppressed = isSuppressed;
            this.mUntilTimestampMillis = untilTimestampMillis;
        }

        public String toString() {
            return "[BlockSuppressionStatus; isSuppressed=" + this.mIsSuppressed + ", until=" + this.mUntilTimestampMillis + NavigationBarInflaterView.SIZE_MOD_END;
        }

        public boolean getIsSuppressed() {
            return this.mIsSuppressed;
        }

        public long getUntilTimestampMillis() {
            return this.mUntilTimestampMillis;
        }
    }

    private void verifyBlockedNumbersPermission() {
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.READ_BLOCKED_NUMBERS, "Caller does not have the android.permission.READ_BLOCKED_NUMBERS permission");
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.WRITE_BLOCKED_NUMBERS, "Caller does not have the android.permission.WRITE_BLOCKED_NUMBERS permission");
    }
}
