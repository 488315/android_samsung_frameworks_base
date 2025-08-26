package android.security.advancedprotection;

import android.annotation.SystemApi;
import android.content.Intent;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserManager;
import android.security.advancedprotection.IAdvancedProtectionCallback;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class AdvancedProtectionManager {
    public static final String ACTION_SHOW_ADVANCED_PROTECTION_SUPPORT_DIALOG = "android.security.advancedprotection.action.SHOW_ADVANCED_PROTECTION_SUPPORT_DIALOG";
    public static final String ADVANCED_PROTECTION_SYSTEM_ENTITY = "android.security.advancedprotection";
    private static final Set<Integer> ALL_FEATURE_IDS = Set.of(0, 1, 2, 3, 4);
    private static final Set<Integer> ALL_SUPPORT_DIALOG_TYPES = Set.of(0, 1, 2);
    public static final String EXTRA_SUPPORT_DIALOG_FEATURE = "android.security.advancedprotection.extra.SUPPORT_DIALOG_FEATURE";
    public static final String EXTRA_SUPPORT_DIALOG_TYPE = "android.security.advancedprotection.extra.SUPPORT_DIALOG_TYPE";

    @SystemApi
    public static final int FEATURE_ID_DISALLOW_CELLULAR_2G = 0;

    @SystemApi
    public static final int FEATURE_ID_DISALLOW_INSTALL_UNKNOWN_SOURCES = 1;

    @SystemApi
    public static final int FEATURE_ID_DISALLOW_USB = 2;

    @SystemApi
    public static final int FEATURE_ID_DISALLOW_WEP = 3;

    @SystemApi
    public static final int FEATURE_ID_ENABLE_MTE = 4;
    private static final String MEMORY_TAGGING_POLICY = "memoryTagging";
    private static final String PKG_SETTINGS = "com.android.settings";
    public static final int SUPPORT_DIALOG_TYPE_BLOCKED_INTERACTION = 1;
    public static final int SUPPORT_DIALOG_TYPE_DISABLED_SETTING = 2;
    public static final int SUPPORT_DIALOG_TYPE_UNKNOWN = 0;
    private static final String TAG = "AdvancedProtectionMgr";
    private final ConcurrentHashMap<Callback, IAdvancedProtectionCallback> mCallbackMap = new ConcurrentHashMap<>();
    private final IAdvancedProtectionService mService;

    public interface Callback {
        void onAdvancedProtectionChanged(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FeatureId {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SupportDialogType {
    }

    public static String featureIdToString(int i) {
        if (i == 0) {
            return "DISALLOW_CELLULAR_2G";
        }
        if (i == 1) {
            return "DISALLOW_INSTALL_UNKNOWN_SOURCES";
        }
        if (i == 2) {
            return "DISALLOW_USB";
        }
        if (i == 3) {
            return "DISALLOW_WEP";
        }
        if (i == 4) {
            return "ENABLE_MTE";
        }
        return "UNKNOWN";
    }

    public static String supportDialogTypeToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "BLOCKED_INTERACTION";
        }
        if (i != 2) {
            return "UNKNOWN";
        }
        return "DISABLED_SETTING";
    }

    public AdvancedProtectionManager(IAdvancedProtectionService iAdvancedProtectionService) {
        this.mService = iAdvancedProtectionService;
    }

    public boolean isAdvancedProtectionEnabled() {
        try {
            return this.mService.isAdvancedProtectionEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerAdvancedProtectionCallback(Executor executor, Callback callback) {
        if (this.mCallbackMap.get(callback) != null) {
            Log.d(TAG, "registerAdvancedProtectionCallback callback already present");
            return;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, executor, callback);
        try {
            this.mService.registerAdvancedProtectionCallback(anonymousClass1);
            this.mCallbackMap.put(callback, anonymousClass1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.security.advancedprotection.AdvancedProtectionManager$1, reason: invalid class name */
    class AnonymousClass1 extends IAdvancedProtectionCallback.Stub {
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(AdvancedProtectionManager advancedProtectionManager, Executor executor, Callback callback) {
            this.val$executor = executor;
            this.val$callback = callback;
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionCallback
        public void onAdvancedProtectionChanged(final boolean z) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final Callback callback = this.val$callback;
                executor.execute(new Runnable() { // from class: android.security.advancedprotection.AdvancedProtectionManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        callback.onAdvancedProtectionChanged(z);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    public void unregisterAdvancedProtectionCallback(Callback callback) {
        IAdvancedProtectionCallback iAdvancedProtectionCallback = this.mCallbackMap.get(callback);
        if (iAdvancedProtectionCallback == null) {
            Log.d(TAG, "unregisterAdvancedProtectionCallback callback not present");
            return;
        }
        try {
            this.mService.unregisterAdvancedProtectionCallback(iAdvancedProtectionCallback);
            this.mCallbackMap.remove(callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAdvancedProtectionEnabled(boolean z) {
        try {
            this.mService.setAdvancedProtectionEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<AdvancedProtectionFeature> getAdvancedProtectionFeatures() {
        try {
            return this.mService.getAdvancedProtectionFeatures();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static Intent createSupportIntent(int i, int i2) {
        if (!ALL_FEATURE_IDS.contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException(i + " is not a valid feature ID. See FEATURE_ID_* APIs.");
        }
        if (!ALL_SUPPORT_DIALOG_TYPES.contains(Integer.valueOf(i2))) {
            throw new IllegalArgumentException(i2 + " is not a valid type. See SUPPORT_DIALOG_TYPE_* APIs.");
        }
        Intent intent = new Intent(ACTION_SHOW_ADVANCED_PROTECTION_SUPPORT_DIALOG);
        intent.setPackage("com.android.settings");
        intent.setFlags(268435456);
        intent.putExtra(EXTRA_SUPPORT_DIALOG_FEATURE, i);
        intent.putExtra(EXTRA_SUPPORT_DIALOG_TYPE, i2);
        return intent;
    }

    public static Intent createSupportIntentForPolicyIdentifierOrRestriction(String str, int i) {
        int i2;
        Objects.requireNonNull(str);
        if (!ALL_SUPPORT_DIALOG_TYPES.contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException(i + " is not a valid type. See SUPPORT_DIALOG_TYPE_* APIs.");
        }
        if (UserManager.DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY.equals(str)) {
            i2 = 1;
        } else if (UserManager.DISALLOW_CELLULAR_2G.equals(str)) {
            if (i == 0) {
                i = 2;
            }
            i2 = 0;
        } else {
            if (!"memoryTagging".equals(str)) {
                throw new UnsupportedOperationException("Unsupported identifier: " + str);
            }
            if (i == 0) {
                i = 2;
            }
            i2 = 4;
        }
        return createSupportIntent(i2, i);
    }

    public void logDialogShown(int i, int i2, boolean z) {
        try {
            this.mService.logDialogShown(i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
