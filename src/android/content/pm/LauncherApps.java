package android.content.pm;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.LocusId;
import android.content.pm.ILauncherApps;
import android.content.pm.IOnAppsChangedListener;
import android.content.pm.IPinItemRequest;
import android.content.pm.IShortcutChangeCallback;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.multiuser.Flags;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.window.IDumpCallback;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.samsung.android.knox.KnoxHelper;
import com.samsung.android.knox.SemPersonaManager;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class LauncherApps {
    public static final String ACTION_CONFIRM_PIN_APPWIDGET = "android.content.pm.action.CONFIRM_PIN_APPWIDGET";
    public static final String ACTION_CONFIRM_PIN_SHORTCUT = "android.content.pm.action.CONFIRM_PIN_SHORTCUT";
    static final boolean DEBUG = false;
    public static final String EXTRA_PIN_ITEM_REQUEST = "android.content.pm.extra.PIN_ITEM_REQUEST";
    public static final int FLAG_CACHE_BUBBLE_SHORTCUTS = 1;
    public static final int FLAG_CACHE_NOTIFICATION_SHORTCUTS = 0;
    public static final int FLAG_CACHE_PEOPLE_TILE_SHORTCUTS = 2;
    private static final String LAUNCHER_USER_INFO_EXTRA_KEY = "launcher_user_info";
    static final String TAG = "LauncherApps";
    private final IOnAppsChangedListener.Stub mAppsChangedListener;
    private final List<CallbackMessageHandler> mCallbacks;
    private final Context mContext;
    private final List<PackageInstaller.SessionCallbackDelegate> mDelegates;
    private final PackageManager mPm;
    private final ILauncherApps mService;
    private final Map<ShortcutChangeCallback, Pair<Executor, IShortcutChangeCallback>> mShortcutChangeCallbacks;
    private final SemPersonaManager mSpm;
    private final UserManager mUserManager;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShortcutCacheFlags {
    }

    public interface ShortcutChangeCallback {
        default void onShortcutsAddedOrUpdated(String str, List<ShortcutInfo> list, UserHandle userHandle) {
        }

        default void onShortcutsRemoved(String str, List<ShortcutInfo> list, UserHandle userHandle) {
        }
    }

    public static abstract class Callback {
        public abstract void onPackageAdded(String str, UserHandle userHandle);

        public abstract void onPackageChanged(String str, UserHandle userHandle);

        public void onPackageLoadingProgressChanged(String str, UserHandle userHandle, float f) {
        }

        public abstract void onPackageRemoved(String str, UserHandle userHandle);

        public abstract void onPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z);

        public void onPackagesSuspended(String[] strArr, UserHandle userHandle) {
        }

        public abstract void onPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z);

        public void onPackagesUnsuspended(String[] strArr, UserHandle userHandle) {
        }

        public void onShortcutsChanged(String str, List<ShortcutInfo> list, UserHandle userHandle) {
        }

        public void onUserConfigChanged(LauncherUserInfo launcherUserInfo) {
        }

        @Deprecated
        public void onPackagesSuspended(String[] strArr, UserHandle userHandle, Bundle bundle) {
            onPackagesSuspended(strArr, userHandle);
        }
    }

    public static class ShortcutQuery {

        @Deprecated
        public static final int FLAG_GET_ALL_KINDS = 27;

        @Deprecated
        public static final int FLAG_GET_DYNAMIC = 1;
        public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;

        @Deprecated
        public static final int FLAG_GET_MANIFEST = 8;

        @SystemApi
        public static final int FLAG_GET_PERSISTED_DATA = 4096;

        @SystemApi
        public static final int FLAG_GET_PERSONS_DATA = 2048;

        @Deprecated
        public static final int FLAG_GET_PINNED = 2;
        public static final int FLAG_MATCH_ALL_KINDS = 27;
        public static final int FLAG_MATCH_ALL_KINDS_WITH_ALL_PINNED = 1051;
        public static final int FLAG_MATCH_CACHED = 16;
        public static final int FLAG_MATCH_DYNAMIC = 1;
        public static final int FLAG_MATCH_MANIFEST = 8;
        public static final int FLAG_MATCH_PINNED = 2;
        public static final int FLAG_MATCH_PINNED_BY_ANY_LAUNCHER = 1024;
        ComponentName mActivity;
        long mChangedSince;
        List<LocusId> mLocusIds;
        String mPackage;
        int mQueryFlags;
        List<String> mShortcutIds;

        @Retention(RetentionPolicy.SOURCE)
        public @interface QueryFlags {
        }

        public ShortcutQuery setChangedSince(long j) {
            this.mChangedSince = j;
            return this;
        }

        public ShortcutQuery setPackage(String str) {
            this.mPackage = str;
            return this;
        }

        public ShortcutQuery setShortcutIds(List<String> list) {
            this.mShortcutIds = list;
            return this;
        }

        public ShortcutQuery setLocusIds(List<LocusId> list) {
            this.mLocusIds = list;
            return this;
        }

        public ShortcutQuery setActivity(ComponentName componentName) {
            this.mActivity = componentName;
            return this;
        }

        public ShortcutQuery setQueryFlags(int i) {
            this.mQueryFlags = i;
            return this;
        }
    }

    private static class ShortcutChangeCallbackProxy extends IShortcutChangeCallback.Stub {
        private final WeakReference<Pair<Executor, ShortcutChangeCallback>> mRemoteReferences;

        ShortcutChangeCallbackProxy(Executor executor, ShortcutChangeCallback shortcutChangeCallback) {
            this.mRemoteReferences = new WeakReference<>(new Pair(executor, shortcutChangeCallback));
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsAddedOrUpdated(String str, List<ShortcutInfo> list, UserHandle userHandle) {
            Pair<Executor, ShortcutChangeCallback> pair = this.mRemoteReferences.get();
            if (pair == null) {
                return;
            }
            pair.first.execute(PooledLambda.obtainRunnable(new QuadConsumer() { // from class: android.content.pm.LauncherApps$ShortcutChangeCallbackProxy$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((LauncherApps.ShortcutChangeCallback) obj).onShortcutsAddedOrUpdated((String) obj2, (List) obj3, (UserHandle) obj4);
                }
            }, pair.second, str, list, userHandle).recycleOnUse());
        }

        @Override // android.content.pm.IShortcutChangeCallback
        public void onShortcutsRemoved(String str, List<ShortcutInfo> list, UserHandle userHandle) {
            Pair<Executor, ShortcutChangeCallback> pair = this.mRemoteReferences.get();
            if (pair == null) {
                return;
            }
            pair.first.execute(PooledLambda.obtainRunnable(new QuadConsumer() { // from class: android.content.pm.LauncherApps$ShortcutChangeCallbackProxy$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuadConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                    ((LauncherApps.ShortcutChangeCallback) obj).onShortcutsRemoved((String) obj2, (List) obj3, (UserHandle) obj4);
                }
            }, pair.second, str, list, userHandle).recycleOnUse());
        }
    }

    public LauncherApps(Context context, ILauncherApps iLauncherApps) {
        this.mCallbacks = new ArrayList();
        this.mDelegates = new ArrayList();
        this.mShortcutChangeCallbacks = new HashMap();
        this.mAppsChangedListener = new IOnAppsChangedListener.Stub() { // from class: android.content.pm.LauncherApps.1
            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageRemoved(UserHandle userHandle, String str) throws RemoteException {
                Log.d(LauncherApps.TAG, "onPackageRemoved " + userHandle.getIdentifier() + "," + str);
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackageRemoved(str, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageChanged(UserHandle userHandle, String str) throws RemoteException {
                Log.d(LauncherApps.TAG, "onPackageChanged " + userHandle.getIdentifier() + "," + str);
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackageChanged(str, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageAdded(UserHandle userHandle, String str) throws RemoteException {
                Log.d(LauncherApps.TAG, "onPackageAdded " + userHandle.getIdentifier() + "," + str);
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackageAdded(str, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesAvailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackagesAvailable(strArr, userHandle, z);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnavailable(UserHandle userHandle, String[] strArr, boolean z) throws RemoteException {
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackagesUnavailable(strArr, userHandle, z);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesSuspended(UserHandle userHandle, String[] strArr, Bundle bundle) throws RemoteException {
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackagesSuspended(strArr, bundle, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackagesUnsuspended(UserHandle userHandle, String[] strArr) throws RemoteException {
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackagesUnsuspended(strArr, userHandle);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onShortcutChanged(UserHandle userHandle, String str, ParceledListSlice parceledListSlice) {
                List<ShortcutInfo> list = parceledListSlice.getList();
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnShortcutChanged(str, userHandle, list);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onPackageLoadingProgressChanged(UserHandle userHandle, String str, float f) {
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnPackageLoadingProgressChanged(userHandle, str, f);
                    }
                }
            }

            @Override // android.content.pm.IOnAppsChangedListener
            public void onUserConfigChanged(LauncherUserInfo launcherUserInfo) {
                synchronized (LauncherApps.this) {
                    Iterator it = LauncherApps.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((CallbackMessageHandler) it.next()).postOnUserConfigChanged(launcherUserInfo);
                    }
                }
            }
        };
        this.mContext = context;
        this.mService = iLauncherApps;
        this.mPm = context.getPackageManager();
        this.mSpm = (SemPersonaManager) context.getSystemService(SemPersonaManager.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public LauncherApps(Context context) {
        this(context, ILauncherApps.Stub.asInterface(ServiceManager.getService(Context.LAUNCHER_APPS_SERVICE)));
    }

    private void logErrorForInvalidProfileAccess(UserHandle userHandle) {
        if (UserHandle.myUserId() == userHandle.getIdentifier() || !this.mUserManager.isManagedProfile() || this.mContext.checkSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) == 0) {
            return;
        }
        Log.w(TAG, "Accessing other profiles/users from managed profile is no longer allowed.");
    }

    public List<UserHandle> getProfiles() {
        if (this.mUserManager.isManagedProfile() || (Flags.enableLauncherAppsHiddenProfileChecks() && com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && Flags.enablePrivateSpaceFeatures() && this.mUserManager.isPrivateProfile())) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(Process.myUserHandle());
            return arrayList;
        }
        if (Flags.enableLauncherAppsHiddenProfileChecks()) {
            try {
                return this.mService.getUserProfiles();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mUserManager.getUserProfiles();
    }

    public List<LauncherActivityInfo> getActivityList(String str, UserHandle userHandle) {
        List<LauncherActivityInfo> activityList;
        logErrorForInvalidProfileAccess(userHandle);
        if (this.mContext.getUserId() != userHandle.getIdentifier() && (activityList = KnoxHelper.getActivityList(this.mContext, this.mService, str, userHandle)) != null) {
            return isAppSeparationPresent(userHandle.getIdentifier()) ? updateLauncherInfoListWithAppSeparation(activityList) : activityList;
        }
        try {
            return convertToActivityList(this.mService.getLauncherActivities(this.mContext.getPackageName(), str, userHandle), userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static LauncherActivityInfo getLauncherActivityInfo(Context context, UserHandle userHandle, LauncherActivityInfoInternal launcherActivityInfoInternal) {
        return new LauncherActivityInfo(context, launcherActivityInfoInternal);
    }

    public PendingIntent getMainActivityLaunchIntent(ComponentName componentName, Bundle bundle, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.getActivityLaunchIntent(this.mContext.getPackageName(), componentName, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final LauncherUserInfo getLauncherUserInfo(UserHandle userHandle) {
        try {
            return this.mService.getLauncherUserInfo(userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IntentSender getAppMarketActivityIntent(String str, UserHandle userHandle) {
        try {
            return this.mService.getAppMarketActivityIntent(this.mContext.getPackageName(), str, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<String> getPreInstalledSystemPackages(UserHandle userHandle) {
        try {
            return this.mService.getPreInstalledSystemPackages(userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IntentSender getPrivateSpaceSettingsIntent() {
        try {
            return this.mService.getPrivateSpaceSettingsIntent();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public LauncherActivityInfo resolveActivity(Intent intent, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            LauncherActivityInfoInternal resolveLauncherActivityInternal = this.mService.resolveLauncherActivityInternal(this.mContext.getPackageName(), intent.getComponent(), userHandle);
            if (resolveLauncherActivityInternal == null) {
                return null;
            }
            return new LauncherActivityInfo(this.mContext, resolveLauncherActivityInternal);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<String, LauncherActivityInfo> getActivityOverrides() {
        ArrayMap arrayMap = new ArrayMap();
        try {
            for (Map.Entry<String, LauncherActivityInfoInternal> entry : this.mService.getActivityOverrides(this.mContext.getPackageName(), this.mContext.getUserId()).entrySet()) {
                arrayMap.put(entry.getKey(), new LauncherActivityInfo(this.mContext, entry.getValue()));
            }
            return arrayMap;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startMainActivity(ComponentName componentName, UserHandle userHandle, Rect rect, Bundle bundle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, rect, bundle, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startPackageInstallerSessionDetailsActivity(PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle) {
        try {
            this.mService.startSessionDetailsActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), sessionInfo, rect, bundle, sessionInfo.getUser());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startAppDetailsActivity(ComponentName componentName, UserHandle userHandle, Rect rect, Bundle bundle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.showAppDetailsAsUser(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), this.mContext.getAttributionTag(), componentName, rect, bundle, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PendingIntent getShortcutIntent(String str, String str2, Bundle bundle, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.getShortcutIntent(this.mContext.getPackageName(), str, str2, null, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<LauncherActivityInfo> getShortcutConfigActivityList(String str, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return convertToActivityList(this.mService.getShortcutConfigActivities(this.mContext.getPackageName(), str, userHandle), userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private List<LauncherActivityInfo> convertToActivityList(ParceledListSlice<LauncherActivityInfoInternal> parceledListSlice, UserHandle userHandle) {
        if (parceledListSlice == null || parceledListSlice.getList().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = parceledListSlice.getList().iterator();
        while (it.hasNext()) {
            arrayList.add(new LauncherActivityInfo(this.mContext, (LauncherActivityInfoInternal) it.next()));
        }
        return isAppSeparationPresent(userHandle.getIdentifier()) ? updateLauncherInfoListWithAppSeparation(arrayList) : arrayList;
    }

    public IntentSender getShortcutConfigActivityIntent(LauncherActivityInfo launcherActivityInfo) {
        try {
            return this.mService.getShortcutConfigActivityIntent(this.mContext.getPackageName(), launcherActivityInfo.getComponentName(), launcherActivityInfo.getUser());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean isAppSeparationPresent(int i) {
        SemPersonaManager semPersonaManager;
        return SemPersonaManager.isDoEnabled(i) && (semPersonaManager = this.mSpm) != null && semPersonaManager.isAppSeparationPresent();
    }

    private List<LauncherActivityInfo> updateLauncherInfoListWithAppSeparation(List<LauncherActivityInfo> list) {
        HashSet hashSet = new HashSet(this.mSpm.getSeparatedAppsList());
        ArrayList arrayList = new ArrayList();
        for (LauncherActivityInfo launcherActivityInfo : list) {
            if (!hashSet.contains(launcherActivityInfo.getActivityInfo().packageName)) {
                arrayList.add(launcherActivityInfo);
            }
        }
        return arrayList;
    }

    public boolean isPackageEnabled(String str, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.isPackageEnabled(this.mContext.getPackageName(), str, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getSuspendedPackageLauncherExtras(String str, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return this.mService.getSuspendedPackageLauncherExtras(str, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldHideFromSuggestions(String str, UserHandle userHandle) {
        Objects.requireNonNull(str, "packageName");
        Objects.requireNonNull(userHandle, "user");
        try {
            return this.mService.shouldHideFromSuggestions(str, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ApplicationInfo getApplicationInfo(String str, int i, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        Objects.requireNonNull(str, "packageName");
        Objects.requireNonNull(userHandle, "user");
        logErrorForInvalidProfileAccess(userHandle);
        try {
            ApplicationInfo applicationInfo = this.mService.getApplicationInfo(this.mContext.getPackageName(), str, i, userHandle);
            if (applicationInfo != null) {
                return applicationInfo;
            }
            throw new PackageManager.NameNotFoundException("Package " + str + " not found for user " + userHandle.getIdentifier());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public AppUsageLimit getAppUsageLimit(String str, UserHandle userHandle) {
        try {
            return this.mService.getAppUsageLimit(this.mContext.getPackageName(), str, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActivityEnabled(ComponentName componentName, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            if (isAppSeparationPresent(userHandle.getIdentifier()) && this.mSpm.isInSeparatedAppsOnly(componentName.getPackageName())) {
                return false;
            }
            return this.mService.isActivityEnabled(this.mContext.getPackageName(), componentName, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasShortcutHostPermission() {
        try {
            return this.mService.hasShortcutHostPermission(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private List<ShortcutInfo> maybeUpdateDisabledMessage(List<ShortcutInfo> list) {
        if (list == null) {
            return null;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            ShortcutInfo shortcutInfo = list.get(size);
            String disabledReasonForRestoreIssue = ShortcutInfo.getDisabledReasonForRestoreIssue(this.mContext, shortcutInfo.getDisabledReason());
            if (disabledReasonForRestoreIssue != null) {
                shortcutInfo.setDisabledMessage(disabledReasonForRestoreIssue);
            }
        }
        return list;
    }

    public void registerDumpCallback(IDumpCallback iDumpCallback) {
        try {
            this.mService.registerDumpCallback(iDumpCallback);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void saveViewCaptureData() {
        try {
            this.mService.saveViewCaptureData();
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public void unRegisterDumpCallback(IDumpCallback iDumpCallback) {
        try {
            this.mService.unRegisterDumpCallback(iDumpCallback);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public List<ShortcutInfo> getShortcuts(ShortcutQuery shortcutQuery, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            return maybeUpdateDisabledMessage(this.mService.getShortcuts(this.mContext.getPackageName(), new ShortcutQueryWrapper(shortcutQuery), userHandle).getList());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public List<ShortcutInfo> getShortcutInfo(String str, List<String> list, UserHandle userHandle) {
        ShortcutQuery shortcutQuery = new ShortcutQuery();
        shortcutQuery.setPackage(str);
        shortcutQuery.setShortcutIds(list);
        shortcutQuery.setQueryFlags(27);
        return getShortcuts(shortcutQuery, userHandle);
    }

    public void pinShortcuts(String str, List<String> list, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.pinShortcuts(this.mContext.getPackageName(), str, list, userHandle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cacheShortcuts(String str, List<String> list, UserHandle userHandle, int i) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.cacheShortcuts(this.mContext.getPackageName(), str, list, userHandle, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uncacheShortcuts(String str, List<String> list, UserHandle userHandle, int i) {
        logErrorForInvalidProfileAccess(userHandle);
        try {
            this.mService.uncacheShortcuts(this.mContext.getPackageName(), str, list, userHandle, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getShortcutIconResId(ShortcutInfo shortcutInfo) {
        return shortcutInfo.getIconResourceId();
    }

    @Deprecated
    public int getShortcutIconResId(String str, String str2, UserHandle userHandle) {
        ShortcutQuery shortcutQuery = new ShortcutQuery();
        shortcutQuery.setPackage(str);
        shortcutQuery.setShortcutIds(Arrays.asList(str2));
        shortcutQuery.setQueryFlags(27);
        List<ShortcutInfo> shortcuts = getShortcuts(shortcutQuery, userHandle);
        if (shortcuts.size() > 0) {
            return shortcuts.get(0).getIconResourceId();
        }
        return 0;
    }

    public ParcelFileDescriptor getShortcutIconFd(ShortcutInfo shortcutInfo) {
        return getShortcutIconFd(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
    }

    public ParcelFileDescriptor getShortcutIconFd(String str, String str2, UserHandle userHandle) {
        return getShortcutIconFd(str, str2, userHandle.getIdentifier());
    }

    private ParcelFileDescriptor getShortcutIconFd(String str, String str2, int i) {
        try {
            return this.mService.getShortcutIconFd(this.mContext.getPackageName(), str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getUriShortcutIconFd(ShortcutInfo shortcutInfo) {
        return getUriShortcutIconFd(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
    }

    private ParcelFileDescriptor getUriShortcutIconFd(String str, String str2, int i) {
        String shortcutIconUri = getShortcutIconUri(str, str2, i);
        if (shortcutIconUri == null) {
            return null;
        }
        try {
            return this.mContext.getContentResolver().openFileDescriptor(Uri.parse(shortcutIconUri), "r");
        } catch (Exception e) {
            Log.e(TAG, "Failed to open icon file: " + shortcutIconUri, e);
            return null;
        }
    }

    private String getShortcutIconUri(String str, String str2, int i) {
        try {
            return this.mService.getShortcutIconUri(this.mContext.getPackageName(), str, str2, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Drawable getShortcutIconDrawable(ShortcutInfo shortcutInfo, int i) {
        if (shortcutInfo.hasIconFile()) {
            return loadDrawableFromFileDescriptor(getShortcutIconFd(shortcutInfo), shortcutInfo.hasAdaptiveBitmap());
        }
        if (shortcutInfo.hasIconUri()) {
            return loadDrawableFromFileDescriptor(getUriShortcutIconFd(shortcutInfo), shortcutInfo.hasAdaptiveBitmap());
        }
        if (shortcutInfo.hasIconResource()) {
            return loadDrawableResourceFromPackage(shortcutInfo.getPackage(), shortcutInfo.getIconResourceId(), shortcutInfo.getUserHandle(), i);
        }
        if (shortcutInfo.getIcon() == null) {
            return null;
        }
        Icon icon = shortcutInfo.getIcon();
        int type = icon.getType();
        if (type != 1) {
            if (type == 2) {
                return loadDrawableResourceFromPackage(shortcutInfo.getPackage(), icon.getResId(), shortcutInfo.getUserHandle(), i);
            }
            if (type != 5) {
                return null;
            }
        }
        return icon.loadDrawable(this.mContext);
    }

    private Drawable loadDrawableFromFileDescriptor(ParcelFileDescriptor parcelFileDescriptor, boolean z) {
        if (parcelFileDescriptor == null) {
            return null;
        }
        try {
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
            if (decodeFileDescriptor == null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException unused) {
                }
                return null;
            }
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), decodeFileDescriptor);
            if (z) {
                return new AdaptiveIconDrawable((Drawable) null, bitmapDrawable);
            }
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused2) {
            }
            return bitmapDrawable;
        } finally {
            try {
                parcelFileDescriptor.close();
            } catch (IOException unused3) {
            }
        }
    }

    public Icon getShortcutIcon(ShortcutInfo shortcutInfo) {
        if (shortcutInfo.hasIconFile()) {
            ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(shortcutInfo);
            if (shortcutIconFd == null) {
                return null;
            }
            try {
                Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(shortcutIconFd.getFileDescriptor());
                if (decodeFileDescriptor == null) {
                    try {
                        shortcutIconFd.close();
                    } catch (IOException unused) {
                    }
                    return null;
                }
                if (shortcutInfo.hasAdaptiveBitmap()) {
                    return Icon.createWithAdaptiveBitmap(decodeFileDescriptor);
                }
                Icon createWithBitmap = Icon.createWithBitmap(decodeFileDescriptor);
                try {
                    shortcutIconFd.close();
                } catch (IOException unused2) {
                }
                return createWithBitmap;
            } finally {
                try {
                    shortcutIconFd.close();
                } catch (IOException unused3) {
                }
            }
        }
        if (shortcutInfo.hasIconUri()) {
            String shortcutIconUri = getShortcutIconUri(shortcutInfo.getPackage(), shortcutInfo.getId(), shortcutInfo.getUserId());
            if (shortcutIconUri == null) {
                return null;
            }
            if (shortcutInfo.hasAdaptiveBitmap()) {
                return Icon.createWithAdaptiveBitmapContentUri(shortcutIconUri);
            }
            return Icon.createWithContentUri(shortcutIconUri);
        }
        if (shortcutInfo.hasIconResource()) {
            return Icon.createWithResource(shortcutInfo.getPackage(), shortcutInfo.getIconResourceId());
        }
        return shortcutInfo.getIcon();
    }

    private Drawable loadDrawableResourceFromPackage(String str, int i, UserHandle userHandle, int i2) {
        if (i == 0) {
            return null;
        }
        try {
            return this.mContext.getPackageManager().getResourcesForApplication(getApplicationInfo(str, 0, userHandle)).getDrawableForDensity(i, i2);
        } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
            return null;
        }
    }

    public Drawable getShortcutBadgedIconDrawable(ShortcutInfo shortcutInfo, int i) {
        Drawable shortcutIconDrawable = getShortcutIconDrawable(shortcutInfo, i);
        if (shortcutIconDrawable == null) {
            return null;
        }
        return this.mContext.getPackageManager().getUserBadgedIcon(shortcutIconDrawable, shortcutInfo.getUserHandle());
    }

    public void startShortcut(String str, String str2, Rect rect, Bundle bundle, UserHandle userHandle) {
        logErrorForInvalidProfileAccess(userHandle);
        startShortcut(str, str2, rect, bundle, userHandle.getIdentifier());
    }

    public void startShortcut(ShortcutInfo shortcutInfo, Rect rect, Bundle bundle) {
        startShortcut(shortcutInfo.getPackage(), shortcutInfo.getId(), rect, bundle, shortcutInfo.getUserId());
    }

    private void startShortcut(String str, String str2, Rect rect, Bundle bundle, int i) {
        try {
            if (this.mService.startShortcut(this.mContext.getPackageName(), str, null, str2, rect, bundle, i)) {
            } else {
                throw new ActivityNotFoundException("Shortcut could not be started");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerCallback(Callback callback) {
        registerCallback(callback, null);
    }

    public void registerCallback(Callback callback, Handler handler) {
        synchronized (this) {
            if (callback != null) {
                if (findCallbackLocked(callback) < 0) {
                    boolean z = this.mCallbacks.size() == 0;
                    addCallbackLocked(callback, handler);
                    if (z) {
                        try {
                            this.mService.addOnAppsChangedListener(this.mContext.getPackageName(), this.mAppsChangedListener);
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            }
        }
    }

    public void unregisterCallback(Callback callback) {
        synchronized (this) {
            removeCallbackLocked(callback);
            if (this.mCallbacks.size() == 0) {
                try {
                    this.mService.removeOnAppsChangedListener(this.mAppsChangedListener);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void setArchiveCompatibility(ArchiveCompatibilityParams archiveCompatibilityParams) {
        try {
            this.mService.setArchiveCompatibilityOptions(archiveCompatibilityParams.isEnableIconOverlay(), archiveCompatibilityParams.isEnableUnarchivalConfirmation());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findCallbackLocked(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null");
        }
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            if (this.mCallbacks.get(i).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    private void removeCallbackLocked(Callback callback) {
        int findCallbackLocked = findCallbackLocked(callback);
        if (findCallbackLocked >= 0) {
            this.mCallbacks.remove(findCallbackLocked);
        }
    }

    private void addCallbackLocked(Callback callback, Handler handler) {
        removeCallbackLocked(callback);
        if (handler == null) {
            handler = new Handler();
        }
        this.mCallbacks.add(new CallbackMessageHandler(handler.getLooper(), callback));
    }

    public static class ArchiveCompatibilityParams {
        private boolean mEnableIconOverlay = true;
        private boolean mEnableUnarchivalConfirmation = true;

        public boolean isEnableIconOverlay() {
            return this.mEnableIconOverlay;
        }

        public boolean isEnableUnarchivalConfirmation() {
            return this.mEnableUnarchivalConfirmation;
        }

        public void setEnableIconOverlay(boolean z) {
            this.mEnableIconOverlay = z;
        }

        public void setEnableUnarchivalConfirmation(boolean z) {
            this.mEnableUnarchivalConfirmation = z;
        }
    }

    private static class CallbackMessageHandler extends Handler {
        private static final int MSG_ADDED = 1;
        private static final int MSG_AVAILABLE = 4;
        private static final int MSG_CHANGED = 3;
        private static final int MSG_LOADING_PROGRESS_CHANGED = 9;
        private static final int MSG_REMOVED = 2;
        private static final int MSG_SHORTCUT_CHANGED = 8;
        private static final int MSG_SUSPENDED = 6;
        private static final int MSG_UNAVAILABLE = 5;
        private static final int MSG_UNSUSPENDED = 7;
        private static final int MSG_USER_CONFIG_CHANGED = 10;
        private final Callback mCallback;

        private static class CallbackInfo {
            Bundle launcherExtras;
            float mLoadingProgress;
            String packageName;
            String[] packageNames;
            boolean replacing;
            List<ShortcutInfo> shortcuts;
            UserHandle user;

            private CallbackInfo() {
            }
        }

        public CallbackMessageHandler(Looper looper, Callback callback) {
            super(looper, null, true);
            this.mCallback = callback;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mCallback == null || !(message.obj instanceof CallbackInfo)) {
                return;
            }
            CallbackInfo callbackInfo = (CallbackInfo) message.obj;
            switch (message.what) {
                case 1:
                    this.mCallback.onPackageAdded(callbackInfo.packageName, callbackInfo.user);
                    break;
                case 2:
                    this.mCallback.onPackageRemoved(callbackInfo.packageName, callbackInfo.user);
                    break;
                case 3:
                    this.mCallback.onPackageChanged(callbackInfo.packageName, callbackInfo.user);
                    break;
                case 4:
                    this.mCallback.onPackagesAvailable(callbackInfo.packageNames, callbackInfo.user, callbackInfo.replacing);
                    break;
                case 5:
                    this.mCallback.onPackagesUnavailable(callbackInfo.packageNames, callbackInfo.user, callbackInfo.replacing);
                    break;
                case 6:
                    this.mCallback.onPackagesSuspended(callbackInfo.packageNames, callbackInfo.user, callbackInfo.launcherExtras);
                    break;
                case 7:
                    this.mCallback.onPackagesUnsuspended(callbackInfo.packageNames, callbackInfo.user);
                    break;
                case 8:
                    this.mCallback.onShortcutsChanged(callbackInfo.packageName, callbackInfo.shortcuts, callbackInfo.user);
                    break;
                case 9:
                    this.mCallback.onPackageLoadingProgressChanged(callbackInfo.packageName, callbackInfo.user, callbackInfo.mLoadingProgress);
                    break;
                case 10:
                    if (com.android.internal.hidden_from_bootclasspath.android.os.Flags.allowPrivateProfile() && Flags.addLauncherUserConfig()) {
                        this.mCallback.onUserConfigChanged((LauncherUserInfo) Objects.requireNonNull((LauncherUserInfo) callbackInfo.launcherExtras.getParcelable(LauncherApps.LAUNCHER_USER_INFO_EXTRA_KEY, LauncherUserInfo.class)));
                        break;
                    }
                    break;
            }
        }

        public void postOnPackageAdded(String str, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            obtainMessage(1, callbackInfo).sendToTarget();
        }

        public void postOnPackageRemoved(String str, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            obtainMessage(2, callbackInfo).sendToTarget();
        }

        public void postOnPackageChanged(String str, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            obtainMessage(3, callbackInfo).sendToTarget();
        }

        public void postOnPackagesAvailable(String[] strArr, UserHandle userHandle, boolean z) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.replacing = z;
            callbackInfo.user = userHandle;
            obtainMessage(4, callbackInfo).sendToTarget();
        }

        public void postOnPackagesUnavailable(String[] strArr, UserHandle userHandle, boolean z) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.replacing = z;
            callbackInfo.user = userHandle;
            obtainMessage(5, callbackInfo).sendToTarget();
        }

        public void postOnPackagesSuspended(String[] strArr, Bundle bundle, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.user = userHandle;
            callbackInfo.launcherExtras = bundle;
            obtainMessage(6, callbackInfo).sendToTarget();
        }

        public void postOnPackagesUnsuspended(String[] strArr, UserHandle userHandle) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageNames = strArr;
            callbackInfo.user = userHandle;
            obtainMessage(7, callbackInfo).sendToTarget();
        }

        public void postOnShortcutChanged(String str, UserHandle userHandle, List<ShortcutInfo> list) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            callbackInfo.shortcuts = list;
            obtainMessage(8, callbackInfo).sendToTarget();
        }

        public void postOnPackageLoadingProgressChanged(UserHandle userHandle, String str, float f) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.packageName = str;
            callbackInfo.user = userHandle;
            callbackInfo.mLoadingProgress = f;
            obtainMessage(9, callbackInfo).sendToTarget();
        }

        public void postOnUserConfigChanged(LauncherUserInfo launcherUserInfo) {
            CallbackInfo callbackInfo = new CallbackInfo();
            callbackInfo.launcherExtras = new Bundle();
            callbackInfo.launcherExtras.putParcelable(LauncherApps.LAUNCHER_USER_INFO_EXTRA_KEY, launcherUserInfo);
            obtainMessage(10, callbackInfo).sendToTarget();
        }
    }

    public void registerPackageInstallerSessionCallback(Executor executor, PackageInstaller.SessionCallback sessionCallback) {
        if (executor == null) {
            throw new NullPointerException("Executor must not be null");
        }
        synchronized (this.mDelegates) {
            PackageInstaller.SessionCallbackDelegate sessionCallbackDelegate = new PackageInstaller.SessionCallbackDelegate(sessionCallback, executor);
            try {
                this.mService.registerPackageInstallerCallback(this.mContext.getPackageName(), sessionCallbackDelegate);
                this.mDelegates.add(sessionCallbackDelegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterPackageInstallerSessionCallback(PackageInstaller.SessionCallback sessionCallback) {
        synchronized (this.mDelegates) {
            Iterator<PackageInstaller.SessionCallbackDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                PackageInstaller.SessionCallbackDelegate next = it.next();
                if (next.mCallback == sessionCallback) {
                    this.mPm.getPackageInstaller().unregisterSessionCallback(next.mCallback);
                    it.remove();
                }
            }
        }
    }

    public List<PackageInstaller.SessionInfo> getAllPackageInstallerSessions() {
        try {
            return this.mService.getAllSessions(this.mContext.getPackageName()).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerShortcutChangeCallback(ShortcutChangeCallback shortcutChangeCallback, ShortcutQuery shortcutQuery, Executor executor) {
        Objects.requireNonNull(shortcutChangeCallback, "Callback cannot be null");
        Objects.requireNonNull(shortcutQuery, "Query cannot be null");
        Objects.requireNonNull(executor, "Executor cannot be null");
        synchronized (this.mShortcutChangeCallbacks) {
            ShortcutChangeCallbackProxy shortcutChangeCallbackProxy = new ShortcutChangeCallbackProxy(executor, shortcutChangeCallback);
            this.mShortcutChangeCallbacks.put(shortcutChangeCallback, new Pair<>(executor, shortcutChangeCallbackProxy));
            try {
                this.mService.registerShortcutChangeCallback(this.mContext.getPackageName(), new ShortcutQueryWrapper(shortcutQuery), shortcutChangeCallbackProxy);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterShortcutChangeCallback(ShortcutChangeCallback shortcutChangeCallback) {
        Objects.requireNonNull(shortcutChangeCallback, "Callback cannot be null");
        synchronized (this.mShortcutChangeCallbacks) {
            if (this.mShortcutChangeCallbacks.containsKey(shortcutChangeCallback)) {
                try {
                    this.mService.unregisterShortcutChangeCallback(this.mContext.getPackageName(), this.mShortcutChangeCallbacks.remove(shortcutChangeCallback).second);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public PinItemRequest getPinItemRequest(Intent intent) {
        return (PinItemRequest) intent.getParcelableExtra(EXTRA_PIN_ITEM_REQUEST, PinItemRequest.class);
    }

    public static final class PinItemRequest implements Parcelable {
        public static final Parcelable.Creator<PinItemRequest> CREATOR = new Parcelable.Creator<PinItemRequest>() { // from class: android.content.pm.LauncherApps.PinItemRequest.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PinItemRequest createFromParcel(Parcel parcel) {
                return new PinItemRequest(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PinItemRequest[] newArray(int i) {
                return new PinItemRequest[i];
            }
        };
        public static final int REQUEST_TYPE_APPWIDGET = 2;
        public static final int REQUEST_TYPE_SHORTCUT = 1;
        private final IPinItemRequest mInner;
        private final int mRequestType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RequestType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public PinItemRequest(IPinItemRequest iPinItemRequest, int i) {
            this.mInner = iPinItemRequest;
            this.mRequestType = i;
        }

        public int getRequestType() {
            return this.mRequestType;
        }

        public ShortcutInfo getShortcutInfo() {
            try {
                return this.mInner.getShortcutInfo();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public AppWidgetProviderInfo getAppWidgetProviderInfo(Context context) {
            try {
                AppWidgetProviderInfo appWidgetProviderInfo = this.mInner.getAppWidgetProviderInfo();
                if (appWidgetProviderInfo == null) {
                    return null;
                }
                appWidgetProviderInfo.updateDimensions(context.getResources().getDisplayMetrics());
                return appWidgetProviderInfo;
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public Bundle getExtras() {
            try {
                return this.mInner.getExtras();
            } catch (RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }

        public boolean isValid() {
            try {
                return this.mInner.isValid();
            } catch (RemoteException unused) {
                return false;
            }
        }

        public boolean accept(Bundle bundle) {
            try {
                return this.mInner.accept(bundle);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean accept() {
            return accept(null);
        }

        private PinItemRequest(Parcel parcel) {
            getClass().getClassLoader();
            this.mRequestType = parcel.readInt();
            this.mInner = IPinItemRequest.Stub.asInterface(parcel.readStrongBinder());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mRequestType);
            parcel.writeStrongBinder(this.mInner.asBinder());
        }
    }

    @SystemApi
    public static final class AppUsageLimit implements Parcelable {
        public static final Parcelable.Creator<AppUsageLimit> CREATOR = new Parcelable.Creator<AppUsageLimit>() { // from class: android.content.pm.LauncherApps.AppUsageLimit.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppUsageLimit createFromParcel(Parcel parcel) {
                return new AppUsageLimit(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AppUsageLimit[] newArray(int i) {
                return new AppUsageLimit[i];
            }
        };
        private final long mTotalUsageLimit;
        private final long mUsageRemaining;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public AppUsageLimit(long j, long j2) {
            this.mTotalUsageLimit = j;
            this.mUsageRemaining = j2;
        }

        public long getTotalUsageLimit() {
            return this.mTotalUsageLimit;
        }

        public long getUsageRemaining() {
            return this.mUsageRemaining;
        }

        private AppUsageLimit(Parcel parcel) {
            this.mTotalUsageLimit = parcel.readLong();
            this.mUsageRemaining = parcel.readLong();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.mTotalUsageLimit);
            parcel.writeLong(this.mUsageRemaining);
        }
    }
}
