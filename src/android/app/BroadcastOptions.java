package android.app;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.BundleMerger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public class BroadcastOptions extends ComponentOptions {
    public static final long CHANGE_ALWAYS_DISABLED = 210856463;
    public static final long CHANGE_ALWAYS_ENABLED = 209888056;
    public static final long CHANGE_INVALID = Long.MIN_VALUE;
    public static final int DEFERRAL_POLICY_DEFAULT = 0;
    public static final int DEFERRAL_POLICY_NONE = 1;
    public static final int DEFERRAL_POLICY_UNTIL_ACTIVE = 2;
    public static final int DELIVERY_GROUP_POLICY_ALL = 0;
    public static final int DELIVERY_GROUP_POLICY_MERGED = 2;
    public static final int DELIVERY_GROUP_POLICY_MOST_RECENT = 1;
    private static final int FLAG_ALLOW_BACKGROUND_ACTIVITY_STARTS = 2;
    private static final int FLAG_DEBUG_LOG = 64;
    private static final int FLAG_DONT_SEND_TO_RESTRICTED_APPS = 1;
    private static final int FLAG_INTERACTIVE = 32;
    private static final int FLAG_IS_ALARM_BROADCAST = 8;
    private static final int FLAG_REQUIRE_COMPAT_CHANGE_ENABLED = 4;
    private static final int FLAG_SHARE_IDENTITY = 16;
    private static final String KEY_DEFERRAL_POLICY = "android:broadcast.deferralPolicy";
    private static final String KEY_DELIVERY_GROUP_EXTRAS_MERGER = "android:broadcast.deliveryGroupExtrasMerger";
    private static final String KEY_DELIVERY_GROUP_KEY = "android:broadcast.deliveryGroupMatchingKey";
    private static final String KEY_DELIVERY_GROUP_MATCHING_FILTER = "android:broadcast.deliveryGroupMatchingFilter";
    private static final String KEY_DELIVERY_GROUP_NAMESPACE = "android:broadcast.deliveryGroupMatchingNamespace";
    private static final String KEY_DELIVERY_GROUP_POLICY = "android:broadcast.deliveryGroupPolicy";
    private static final String KEY_FLAGS = "android:broadcast.flags";
    private static final String KEY_ID_FOR_RESPONSE_EVENT = "android:broadcast.idForResponseEvent";
    private static final String KEY_MAX_MANIFEST_RECEIVER_API_LEVEL = "android:broadcast.maxManifestReceiverApiLevel";
    private static final String KEY_MIN_MANIFEST_RECEIVER_API_LEVEL = "android:broadcast.minManifestReceiverApiLevel";
    public static final String KEY_REQUIRE_ALL_OF_PERMISSIONS = "android:broadcast.requireAllOfPermissions";
    private static final String KEY_REQUIRE_COMPAT_CHANGE_ID = "android:broadcast.requireCompatChangeId";
    public static final String KEY_REQUIRE_NONE_OF_PERMISSIONS = "android:broadcast.requireNoneOfPermissions";
    private static final String KEY_TEMPORARY_APP_ALLOWLIST_DURATION = "android:broadcast.temporaryAppAllowlistDuration";
    private static final String KEY_TEMPORARY_APP_ALLOWLIST_REASON = "android:broadcast.temporaryAppAllowlistReason";
    private static final String KEY_TEMPORARY_APP_ALLOWLIST_REASON_CODE = "android:broadcast.temporaryAppAllowlistReasonCode";
    private static final String KEY_TEMPORARY_APP_ALLOWLIST_TYPE = "android:broadcast.temporaryAppAllowlistType";

    @Deprecated
    public static final int TEMPORARY_WHITELIST_TYPE_FOREGROUND_SERVICE_ALLOWED = 0;

    @Deprecated
    public static final int TEMPORARY_WHITELIST_TYPE_FOREGROUND_SERVICE_NOT_ALLOWED = 1;
    private int mDeferralPolicy;
    private BundleMerger mDeliveryGroupExtrasMerger;
    private IntentFilter mDeliveryGroupMatchingFilter;
    private String mDeliveryGroupMatchingKeyFragment;
    private String mDeliveryGroupMatchingNamespaceFragment;
    private int mDeliveryGroupPolicy;
    private int mFlags;
    private long mIdForResponseEvent;
    private int mMaxManifestReceiverApiLevel;
    private int mMinManifestReceiverApiLevel;
    private String[] mRequireAllOfPermissions;
    private long mRequireCompatChangeId;
    private String[] mRequireNoneOfPermissions;
    private long mTemporaryAppAllowlistDuration;
    private String mTemporaryAppAllowlistReason;
    private int mTemporaryAppAllowlistReasonCode;
    private int mTemporaryAppAllowlistType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeferralPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeliveryGroupPolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static BroadcastOptions makeBasic() {
        return new BroadcastOptions();
    }

    public BroadcastOptions() {
        this.mMinManifestReceiverApiLevel = 0;
        this.mMaxManifestReceiverApiLevel = 10000;
        this.mRequireCompatChangeId = Long.MIN_VALUE;
        resetTemporaryAppAllowlist();
    }

    public BroadcastOptions(Bundle bundle) {
        super(bundle);
        this.mMinManifestReceiverApiLevel = 0;
        this.mMaxManifestReceiverApiLevel = 10000;
        this.mRequireCompatChangeId = Long.MIN_VALUE;
        this.mFlags = bundle.getInt(KEY_FLAGS, 0);
        if (bundle.containsKey(KEY_TEMPORARY_APP_ALLOWLIST_DURATION)) {
            this.mTemporaryAppAllowlistDuration = bundle.getLong(KEY_TEMPORARY_APP_ALLOWLIST_DURATION);
            this.mTemporaryAppAllowlistType = bundle.getInt(KEY_TEMPORARY_APP_ALLOWLIST_TYPE);
            this.mTemporaryAppAllowlistReasonCode = bundle.getInt(KEY_TEMPORARY_APP_ALLOWLIST_REASON_CODE, 0);
            this.mTemporaryAppAllowlistReason = bundle.getString(KEY_TEMPORARY_APP_ALLOWLIST_REASON);
        } else {
            resetTemporaryAppAllowlist();
        }
        this.mMinManifestReceiverApiLevel = bundle.getInt(KEY_MIN_MANIFEST_RECEIVER_API_LEVEL, 0);
        this.mMaxManifestReceiverApiLevel = bundle.getInt(KEY_MAX_MANIFEST_RECEIVER_API_LEVEL, 10000);
        this.mRequireAllOfPermissions = bundle.getStringArray(KEY_REQUIRE_ALL_OF_PERMISSIONS);
        this.mRequireNoneOfPermissions = bundle.getStringArray(KEY_REQUIRE_NONE_OF_PERMISSIONS);
        this.mRequireCompatChangeId = bundle.getLong(KEY_REQUIRE_COMPAT_CHANGE_ID, Long.MIN_VALUE);
        this.mIdForResponseEvent = bundle.getLong(KEY_ID_FOR_RESPONSE_EVENT);
        this.mDeliveryGroupPolicy = bundle.getInt(KEY_DELIVERY_GROUP_POLICY, 0);
        this.mDeliveryGroupMatchingNamespaceFragment = bundle.getString(KEY_DELIVERY_GROUP_NAMESPACE);
        this.mDeliveryGroupMatchingKeyFragment = bundle.getString(KEY_DELIVERY_GROUP_KEY);
        this.mDeliveryGroupExtrasMerger = (BundleMerger) bundle.getParcelable(KEY_DELIVERY_GROUP_EXTRAS_MERGER, BundleMerger.class);
        this.mDeliveryGroupMatchingFilter = (IntentFilter) bundle.getParcelable(KEY_DELIVERY_GROUP_MATCHING_FILTER, IntentFilter.class);
        this.mDeferralPolicy = bundle.getInt(KEY_DEFERRAL_POLICY, 0);
    }

    public static BroadcastOptions makeWithDeferUntilActive(boolean z) {
        BroadcastOptions makeBasic = makeBasic();
        if (z) {
            makeBasic.setDeferralPolicy(2);
        }
        return makeBasic;
    }

    @SystemApi
    @Deprecated
    public void setTemporaryAppWhitelistDuration(long j) {
        setTemporaryAppAllowlist(j, 0, 0, null);
    }

    @SystemApi
    public void setTemporaryAppAllowlist(long j, int i, int i2, String str) {
        this.mTemporaryAppAllowlistDuration = j;
        this.mTemporaryAppAllowlistType = i;
        this.mTemporaryAppAllowlistReasonCode = i2;
        this.mTemporaryAppAllowlistReason = str;
        if (isTemporaryAppAllowlistSet()) {
            return;
        }
        resetTemporaryAppAllowlist();
    }

    private boolean isTemporaryAppAllowlistSet() {
        return this.mTemporaryAppAllowlistDuration > 0 && this.mTemporaryAppAllowlistType != -1;
    }

    private void resetTemporaryAppAllowlist() {
        this.mTemporaryAppAllowlistDuration = 0L;
        this.mTemporaryAppAllowlistType = -1;
        this.mTemporaryAppAllowlistReasonCode = 0;
        this.mTemporaryAppAllowlistReason = null;
    }

    public long getTemporaryAppAllowlistDuration() {
        return this.mTemporaryAppAllowlistDuration;
    }

    public int getTemporaryAppAllowlistType() {
        return this.mTemporaryAppAllowlistType;
    }

    public int getTemporaryAppAllowlistReasonCode() {
        return this.mTemporaryAppAllowlistReasonCode;
    }

    public String getTemporaryAppAllowlistReason() {
        return this.mTemporaryAppAllowlistReason;
    }

    @Deprecated
    public void setMinManifestReceiverApiLevel(int i) {
        this.mMinManifestReceiverApiLevel = i;
    }

    @Deprecated
    public int getMinManifestReceiverApiLevel() {
        return this.mMinManifestReceiverApiLevel;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    @Deprecated
    public void setMaxManifestReceiverApiLevel(int i) {
        this.mMaxManifestReceiverApiLevel = i;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    @Deprecated
    public int getMaxManifestReceiverApiLevel() {
        return this.mMaxManifestReceiverApiLevel;
    }

    @SystemApi
    public void setDontSendToRestrictedApps(boolean z) {
        if (z) {
            this.mFlags |= 1;
        } else {
            this.mFlags &= -2;
        }
    }

    public boolean isDontSendToRestrictedApps() {
        return (this.mFlags & 1) != 0;
    }

    @SystemApi
    public void setBackgroundActivityStartsAllowed(boolean z) {
        if (z) {
            this.mFlags |= 2;
        } else {
            this.mFlags &= -3;
        }
    }

    @Deprecated
    public boolean allowsBackgroundActivityStarts() {
        return (this.mFlags & 2) != 0;
    }

    @SystemApi
    public void setRequireAllOfPermissions(String[] strArr) {
        this.mRequireAllOfPermissions = strArr;
    }

    @SystemApi
    public void setRequireNoneOfPermissions(String[] strArr) {
        this.mRequireNoneOfPermissions = strArr;
    }

    @SystemApi
    public void setRequireCompatChange(long j, boolean z) {
        this.mRequireCompatChangeId = j;
        if (z) {
            this.mFlags |= 4;
        } else {
            this.mFlags &= -5;
        }
    }

    @SystemApi
    public void clearRequireCompatChange() {
        setRequireCompatChange(Long.MIN_VALUE, true);
    }

    public void setAlarmBroadcast(boolean z) {
        if (z) {
            this.mFlags |= 8;
        } else {
            this.mFlags &= -9;
        }
    }

    public boolean isAlarmBroadcast() {
        return (this.mFlags & 8) != 0;
    }

    public BroadcastOptions setShareIdentityEnabled(boolean z) {
        if (z) {
            this.mFlags |= 16;
            return this;
        }
        this.mFlags &= -17;
        return this;
    }

    public boolean isShareIdentityEnabled() {
        return (this.mFlags & 16) != 0;
    }

    public boolean isPushMessagingBroadcast() {
        return this.mTemporaryAppAllowlistReasonCode == 101;
    }

    public boolean isPushMessagingOverQuotaBroadcast() {
        return this.mTemporaryAppAllowlistReasonCode == 102;
    }

    public long getRequireCompatChangeId() {
        return this.mRequireCompatChangeId;
    }

    public boolean testRequireCompatChange(int i) {
        long j = this.mRequireCompatChangeId;
        if (j != Long.MIN_VALUE) {
            return CompatChanges.isChangeEnabled(j, i) == ((this.mFlags & 4) != 0);
        }
        return true;
    }

    @SystemApi
    public void recordResponseEventWhileInBackground(long j) {
        this.mIdForResponseEvent = j;
    }

    public long getIdForResponseEvent() {
        return this.mIdForResponseEvent;
    }

    public BroadcastOptions setDeferralPolicy(int i) {
        this.mDeferralPolicy = i;
        return this;
    }

    public int getDeferralPolicy() {
        return this.mDeferralPolicy;
    }

    public void clearDeferralPolicy() {
        this.mDeferralPolicy = 0;
    }

    public BroadcastOptions setDeliveryGroupPolicy(int i) {
        this.mDeliveryGroupPolicy = i;
        return this;
    }

    public int getDeliveryGroupPolicy() {
        return this.mDeliveryGroupPolicy;
    }

    public void clearDeliveryGroupPolicy() {
        this.mDeliveryGroupPolicy = 0;
    }

    public BroadcastOptions setDeliveryGroupMatchingKey(String str, String str2) {
        this.mDeliveryGroupMatchingNamespaceFragment = (String) Objects.requireNonNull(str);
        this.mDeliveryGroupMatchingKeyFragment = (String) Objects.requireNonNull(str2);
        return this;
    }

    public String getDeliveryGroupMatchingKey() {
        String str;
        String str2 = this.mDeliveryGroupMatchingNamespaceFragment;
        if (str2 == null || (str = this.mDeliveryGroupMatchingKeyFragment) == null) {
            return null;
        }
        return String.join(":", str2, str);
    }

    public String getDeliveryGroupMatchingNamespaceFragment() {
        return this.mDeliveryGroupMatchingNamespaceFragment;
    }

    public String getDeliveryGroupMatchingKeyFragment() {
        return this.mDeliveryGroupMatchingKeyFragment;
    }

    public void clearDeliveryGroupMatchingKey() {
        this.mDeliveryGroupMatchingNamespaceFragment = null;
        this.mDeliveryGroupMatchingKeyFragment = null;
    }

    public BroadcastOptions setDeliveryGroupMatchingFilter(IntentFilter intentFilter) {
        this.mDeliveryGroupMatchingFilter = (IntentFilter) Objects.requireNonNull(intentFilter);
        return this;
    }

    public IntentFilter getDeliveryGroupMatchingFilter() {
        return this.mDeliveryGroupMatchingFilter;
    }

    public void clearDeliveryGroupMatchingFilter() {
        this.mDeliveryGroupMatchingFilter = null;
    }

    public BroadcastOptions setDeliveryGroupExtrasMerger(BundleMerger bundleMerger) {
        this.mDeliveryGroupExtrasMerger = (BundleMerger) Objects.requireNonNull(bundleMerger);
        return this;
    }

    public BundleMerger getDeliveryGroupExtrasMerger() {
        return this.mDeliveryGroupExtrasMerger;
    }

    public void clearDeliveryGroupExtrasMerger() {
        this.mDeliveryGroupExtrasMerger = null;
    }

    public BroadcastOptions setInteractive(boolean z) {
        if (z) {
            this.mFlags |= 32;
            return this;
        }
        this.mFlags &= -33;
        return this;
    }

    public boolean isInteractive() {
        return (this.mFlags & 32) != 0;
    }

    @Override // android.app.ComponentOptions
    @SystemApi
    @Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean z) {
        super.setPendingIntentBackgroundActivityLaunchAllowed(z);
    }

    @Override // android.app.ComponentOptions
    @SystemApi
    @Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        return super.isPendingIntentBackgroundActivityLaunchAllowed();
    }

    @Override // android.app.ComponentOptions
    @SystemApi
    public BroadcastOptions setPendingIntentBackgroundActivityStartMode(int i) {
        super.setPendingIntentBackgroundActivityStartMode(i);
        return this;
    }

    @Override // android.app.ComponentOptions
    @SystemApi
    public int getPendingIntentBackgroundActivityStartMode() {
        return super.getPendingIntentBackgroundActivityStartMode();
    }

    public BroadcastOptions setDebugLogEnabled(boolean z) {
        if (z) {
            this.mFlags |= 64;
            return this;
        }
        this.mFlags &= -65;
        return this;
    }

    public boolean isDebugLogEnabled() {
        return (this.mFlags & 64) != 0;
    }

    @Override // android.app.ComponentOptions
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        int i = this.mFlags;
        if (i != 0) {
            bundle.putInt(KEY_FLAGS, i);
        }
        if (isTemporaryAppAllowlistSet()) {
            bundle.putLong(KEY_TEMPORARY_APP_ALLOWLIST_DURATION, this.mTemporaryAppAllowlistDuration);
            bundle.putInt(KEY_TEMPORARY_APP_ALLOWLIST_TYPE, this.mTemporaryAppAllowlistType);
            bundle.putInt(KEY_TEMPORARY_APP_ALLOWLIST_REASON_CODE, this.mTemporaryAppAllowlistReasonCode);
            bundle.putString(KEY_TEMPORARY_APP_ALLOWLIST_REASON, this.mTemporaryAppAllowlistReason);
        }
        int i2 = this.mMinManifestReceiverApiLevel;
        if (i2 != 0) {
            bundle.putInt(KEY_MIN_MANIFEST_RECEIVER_API_LEVEL, i2);
        }
        int i3 = this.mMaxManifestReceiverApiLevel;
        if (i3 != 10000) {
            bundle.putInt(KEY_MAX_MANIFEST_RECEIVER_API_LEVEL, i3);
        }
        String[] strArr = this.mRequireAllOfPermissions;
        if (strArr != null) {
            bundle.putStringArray(KEY_REQUIRE_ALL_OF_PERMISSIONS, strArr);
        }
        String[] strArr2 = this.mRequireNoneOfPermissions;
        if (strArr2 != null) {
            bundle.putStringArray(KEY_REQUIRE_NONE_OF_PERMISSIONS, strArr2);
        }
        long j = this.mRequireCompatChangeId;
        if (j != Long.MIN_VALUE) {
            bundle.putLong(KEY_REQUIRE_COMPAT_CHANGE_ID, j);
        }
        long j2 = this.mIdForResponseEvent;
        if (j2 != 0) {
            bundle.putLong(KEY_ID_FOR_RESPONSE_EVENT, j2);
        }
        int i4 = this.mDeliveryGroupPolicy;
        if (i4 != 0) {
            bundle.putInt(KEY_DELIVERY_GROUP_POLICY, i4);
        }
        String str = this.mDeliveryGroupMatchingNamespaceFragment;
        if (str != null) {
            bundle.putString(KEY_DELIVERY_GROUP_NAMESPACE, str);
        }
        String str2 = this.mDeliveryGroupMatchingKeyFragment;
        if (str2 != null) {
            bundle.putString(KEY_DELIVERY_GROUP_KEY, str2);
        }
        if (this.mDeliveryGroupPolicy == 2) {
            BundleMerger bundleMerger = this.mDeliveryGroupExtrasMerger;
            if (bundleMerger != null) {
                bundle.putParcelable(KEY_DELIVERY_GROUP_EXTRAS_MERGER, bundleMerger);
            } else {
                throw new IllegalStateException("Extras merger cannot be empty when delivery group policy is 'MERGED'");
            }
        }
        IntentFilter intentFilter = this.mDeliveryGroupMatchingFilter;
        if (intentFilter != null) {
            bundle.putParcelable(KEY_DELIVERY_GROUP_MATCHING_FILTER, intentFilter);
        }
        int i5 = this.mDeferralPolicy;
        if (i5 != 0) {
            bundle.putInt(KEY_DEFERRAL_POLICY, i5);
        }
        return bundle;
    }

    public static BroadcastOptions fromBundle(Bundle bundle) {
        return new BroadcastOptions(bundle);
    }

    public static BroadcastOptions fromBundleNullable(Bundle bundle) {
        if (bundle != null) {
            return new BroadcastOptions(bundle);
        }
        return null;
    }
}
