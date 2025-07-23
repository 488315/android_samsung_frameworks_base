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
    private static final String LOG_TAG = "BlockedNumbersManager";
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
        } catch (IllegalArgumentException | NullPointerException unused) {
            Log.w((String) null, "notifyEmergencyContact: provider not ready.", new Object[0]);
        }
    }

    @SystemApi
    public void endBlockSuppression() {
        verifyBlockedNumbersPermission();
        Log.i(LOG_TAG, "endBlockSuppression: caller=%s", this.mContext.getOpPackageName());
        this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_END_BLOCK_SUPPRESSION, (String) null, (Bundle) null);
    }

    @SystemApi
    public int shouldSystemBlockNumber(String str, int i, boolean z) {
        verifyBlockedNumbersPermission();
        try {
            String opPackageName = this.mContext.getOpPackageName();
            Bundle bundle = new Bundle();
            bundle.putInt(BlockedNumberContract.EXTRA_CALL_PRESENTATION, i);
            bundle.putBoolean(BlockedNumberContract.EXTRA_CONTACT_EXIST, z);
            Bundle call = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_SHOULD_SYSTEM_BLOCK_NUMBER, str, bundle);
            int i2 = call != null ? call.getInt(BlockedNumberContract.RES_BLOCK_STATUS, 0) : 0;
            Log.d(LOG_TAG, "shouldSystemBlockNumber: number=%s, caller=%s, result=%s", Log.piiHandle(str), opPackageName, BlockedNumberContract.SystemContract.blockStatusToString(i2));
            return i2;
        } catch (IllegalArgumentException | NullPointerException unused) {
            Log.w((String) null, "shouldSystemBlockNumber: provider not ready.", new Object[0]);
            return 0;
        }
    }

    @SystemApi
    public BlockSuppressionStatus getBlockSuppressionStatus() {
        verifyBlockedNumbersPermission();
        Bundle call = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_GET_BLOCK_SUPPRESSION_STATUS, (String) null, (Bundle) null);
        BlockSuppressionStatus blockSuppressionStatus = new BlockSuppressionStatus(call.getBoolean(BlockedNumberContract.SystemContract.RES_IS_BLOCKING_SUPPRESSED, false), call.getLong(BlockedNumberContract.SystemContract.RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP, 0L));
        Log.d(LOG_TAG, "getBlockSuppressionStatus: caller=%s, status=%s", this.mContext.getOpPackageName(), blockSuppressionStatus);
        return blockSuppressionStatus;
    }

    @SystemApi
    public boolean shouldShowEmergencyCallNotification() {
        verifyBlockedNumbersPermission();
        try {
            Bundle call = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION, (String) null, (Bundle) null);
            if (call != null) {
                if (call.getBoolean("show_emergency_call_notification", false)) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException | NullPointerException unused) {
            Log.w((String) null, "shouldShowEmergencyCallNotification: provider not ready.", new Object[0]);
            return false;
        }
    }

    @SystemApi
    public boolean getBlockedNumberSetting(String str) {
        verifyBlockedNumbersPermission();
        Bundle bundle = new Bundle();
        bundle.putString(BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, str);
        try {
            Bundle call = this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_GET_ENHANCED_BLOCK_SETTING, (String) null, bundle);
            if (call != null) {
                if (call.getBoolean(BlockedNumberContract.RES_ENHANCED_SETTING_IS_ENABLED, false)) {
                    return true;
                }
            }
            return false;
        } catch (IllegalArgumentException | NullPointerException unused) {
            Log.w((String) null, "getEnhancedBlockSetting: provider not ready.", new Object[0]);
            return false;
        }
    }

    @SystemApi
    public void setBlockedNumberSetting(String str, boolean z) {
        verifyBlockedNumbersPermission();
        Bundle bundle = new Bundle();
        bundle.putString(BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, str);
        bundle.putBoolean(BlockedNumberContract.EXTRA_ENHANCED_SETTING_VALUE, z);
        this.mContext.getContentResolver().call(BlockedNumberContract.AUTHORITY_URI, BlockedNumberContract.SystemContract.METHOD_SET_ENHANCED_BLOCK_SETTING, (String) null, bundle);
    }

    @SystemApi
    public static final class BlockSuppressionStatus {
        private boolean mIsSuppressed;
        private long mUntilTimestampMillis;

        BlockSuppressionStatus(boolean z, long j) {
            this.mIsSuppressed = z;
            this.mUntilTimestampMillis = j;
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
