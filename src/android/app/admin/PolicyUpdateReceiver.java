package android.app.admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.hidden_from_bootclasspath.android.app.admin.flags.Flags;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class PolicyUpdateReceiver extends BroadcastReceiver {
    public static final String ACTION_DEVICE_POLICY_CHANGED = "android.app.admin.action.DEVICE_POLICY_CHANGED";
    public static final String ACTION_DEVICE_POLICY_SET_RESULT = "android.app.admin.action.DEVICE_POLICY_SET_RESULT";
    public static final String EXTRA_ACCOUNT_TYPE = "android.app.admin.extra.ACCOUNT_TYPE";
    public static final String EXTRA_INTENT_FILTER = "android.app.admin.extra.INTENT_FILTER";
    public static final String EXTRA_PACKAGE_NAME = "android.app.admin.extra.PACKAGE_NAME";
    public static final String EXTRA_PERMISSION_NAME = "android.app.admin.extra.PERMISSION_NAME";
    public static final String EXTRA_POLICY_BUNDLE_KEY = "android.app.admin.extra.POLICY_BUNDLE_KEY";
    public static final String EXTRA_POLICY_KEY = "android.app.admin.extra.POLICY_KEY";
    public static final String EXTRA_POLICY_TARGET_USER_ID = "android.app.admin.extra.POLICY_TARGET_USER_ID";
    public static final String EXTRA_POLICY_UPDATE_RESULT_KEY = "android.app.admin.extra.POLICY_UPDATE_RESULT_KEY";
    private static final String MEMORY_TAGGING_POLICY = "memoryTagging";
    private static String TAG = "PolicyUpdateReceiver";

    public void onPolicyChanged(Context context, String str, Bundle bundle, TargetUser targetUser, PolicyUpdateResult policyUpdateResult) {
    }

    public void onPolicySetResult(Context context, String str, Bundle bundle, TargetUser targetUser, PolicyUpdateResult policyUpdateResult) {
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Objects.requireNonNull(intent.getAction());
        String action = intent.getAction();
        action.hashCode();
        if (action.equals(ACTION_DEVICE_POLICY_SET_RESULT)) {
            Log.i(TAG, "Received ACTION_DEVICE_POLICY_SET_RESULT");
            String policyKey = getPolicyKey(intent);
            if (!shouldPropagatePolicy(policyKey)) {
                Log.d(TAG, TextUtils.formatSimple("Skipping propagation of policy %s", policyKey));
                return;
            } else {
                onPolicySetResult(context, policyKey, getPolicyExtraBundle(intent), getTargetUser(intent), getPolicyChangedReason(intent));
                return;
            }
        }
        if (action.equals(ACTION_DEVICE_POLICY_CHANGED)) {
            Log.i(TAG, "Received ACTION_DEVICE_POLICY_CHANGED");
            String policyKey2 = getPolicyKey(intent);
            if (!shouldPropagatePolicy(policyKey2)) {
                Log.d(TAG, TextUtils.formatSimple("Skipping propagation of policy %s", policyKey2));
                return;
            } else {
                onPolicyChanged(context, policyKey2, getPolicyExtraBundle(intent), getTargetUser(intent), getPolicyChangedReason(intent));
                return;
            }
        }
        Log.e(TAG, "Unknown action received: " + intent.getAction());
    }

    static String getPolicyKey(Intent intent) {
        if (!intent.hasExtra(EXTRA_POLICY_KEY)) {
            throw new IllegalArgumentException("PolicyKey has to be provided.");
        }
        return intent.getStringExtra(EXTRA_POLICY_KEY);
    }

    static Bundle getPolicyExtraBundle(Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra(EXTRA_POLICY_BUNDLE_KEY);
        return bundleExtra == null ? new Bundle() : bundleExtra;
    }

    static PolicyUpdateResult getPolicyChangedReason(Intent intent) {
        if (!intent.hasExtra(EXTRA_POLICY_UPDATE_RESULT_KEY)) {
            throw new IllegalArgumentException("PolicyUpdateResult has to be provided.");
        }
        return new PolicyUpdateResult(intent.getIntExtra(EXTRA_POLICY_UPDATE_RESULT_KEY, -1));
    }

    static TargetUser getTargetUser(Intent intent) {
        if (!intent.hasExtra(EXTRA_POLICY_TARGET_USER_ID)) {
            throw new IllegalArgumentException("TargetUser has to be provided.");
        }
        return new TargetUser(intent.getIntExtra(EXTRA_POLICY_TARGET_USER_ID, -1));
    }

    private boolean shouldPropagatePolicy(String str) {
        return !"memoryTagging".equals(str) || Flags.setMtePolicyCoexistence();
    }
}
