package com.android.server.search;

import android.app.ISearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManagerInternal;
import android.database.ContentObserver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.BackgroundThread;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SearchManagerService extends ISearchManager.Stub {
    public final Context mContext;
    public final SparseArray mSearchables = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GlobalSearchProviderObserver extends ContentObserver {
        public GlobalSearchProviderObserver(ContentResolver contentResolver) {
            super(null);
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("search_global_search_activity"), false, this);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            synchronized (SearchManagerService.this.mSearchables) {
                for (int i = 0; i < SearchManagerService.this.mSearchables.size(); i++) {
                    try {
                        Searchables searchables = (Searchables) SearchManagerService.this.mSearchables.valueAt(i);
                        synchronized (searchables) {
                            searchables.mRebuildSearchables = true;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            SearchManagerService.this.mContext.sendBroadcastAsUser(BatteryService$$ExternalSyntheticOutline0.m(536870912, "android.search.action.GLOBAL_SEARCH_ACTIVITY_CHANGED"), UserHandle.ALL);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final AnonymousClass1 mBootCompleteReceiver;
        public SearchManagerService mService;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.search.SearchManagerService$Lifecycle$1] */
        public Lifecycle(Context context) {
            super(context);
            this.mBootCompleteReceiver = new BroadcastReceiver() { // from class: com.android.server.search.SearchManagerService.Lifecycle.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, final Intent intent) {
                    ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
                    newSingleThreadExecutor.execute(new Runnable() { // from class: com.android.server.search.SearchManagerService.Lifecycle.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Intent intent2 = intent;
                            if (intent2 == null) {
                                Log.e("SearchManagerService", "onReceive: null intent");
                                return;
                            }
                            if ("android.intent.action.BOOT_COMPLETED".equals(intent2.getAction())) {
                                Lifecycle lifecycle = Lifecycle.this;
                                lifecycle.getClass();
                                Intent intent3 = new Intent("com.samsung.intent.action.SEARCH_MANAGER_READY");
                                intent3.addFlags(-1996488704);
                                lifecycle.getContext().sendBroadcastAsUser(intent3, UserHandle.ALL);
                            }
                        }
                    });
                    newSingleThreadExecutor.shutdown();
                }
            };
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            if (i == 1000) {
                try {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                    intentFilter.setPriority(1000);
                    getContext().registerReceiverAsUser(this.mBootCompleteReceiver, UserHandle.ALL, intentFilter, null, null);
                } catch (Exception unused) {
                    Log.e("SearchManagerService", "Lifecycle: fail boot register");
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v0, types: [android.os.IBinder, com.android.server.search.SearchManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? searchManagerService = new SearchManagerService(getContext());
            this.mService = searchManagerService;
            publishBinderService("search", searchManagerService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStopped(SystemService.TargetUser targetUser) {
            SearchManagerService searchManagerService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (searchManagerService.mSearchables) {
                searchManagerService.mSearchables.remove(userIdentifier);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public final ArrayList mChangedPackages = new ArrayList();
        public boolean mSearchablePackageAppeared = false;

        public MyPackageMonitor() {
        }

        public final void onBeginPackageChanges() {
            this.mChangedPackages.clear();
            this.mSearchablePackageAppeared = false;
        }

        public final void onFinishPackageChanges() {
            int changingUserId = getChangingUserId();
            if (!this.mSearchablePackageAppeared) {
                ArraySet arraySet = new ArraySet();
                synchronized (SearchManagerService.this.mSearchables) {
                    Searchables searchables = (Searchables) SearchManagerService.this.mSearchables.get(changingUserId);
                    if (searchables != null) {
                        synchronized (searchables) {
                            arraySet = searchables.mKnownSearchablePackageNames;
                        }
                    }
                }
                int size = this.mChangedPackages.size();
                for (int i = 0; i < size; i++) {
                    if (!arraySet.contains((String) this.mChangedPackages.get(i))) {
                    }
                }
                this.mChangedPackages.clear();
                this.mSearchablePackageAppeared = false;
            }
            Log.i("SearchManagerService", "_updateSearchables");
            synchronized (SearchManagerService.this.mSearchables) {
                Searchables searchables2 = (Searchables) SearchManagerService.this.mSearchables.get(changingUserId);
                if (searchables2 != null) {
                    synchronized (searchables2) {
                        searchables2.mRebuildSearchables = true;
                    }
                }
            }
            Log.i("SearchManagerService", "_updateSearchables completed.");
            Intent intent = new Intent("android.search.action.SEARCHABLES_CHANGED");
            intent.addFlags(603979776);
            SearchManagerService.this.mContext.sendBroadcastAsUser(intent, new UserHandle(changingUserId));
            this.mChangedPackages.clear();
            this.mSearchablePackageAppeared = false;
        }

        public final void onPackageAppeared(String str, int i) {
            if (!this.mSearchablePackageAppeared) {
                int changingUserId = getChangingUserId();
                Context context = SearchManagerService.this.mContext;
                boolean z = true;
                if (context.getPackageManager().queryIntentActivitiesAsUser(new Intent("android.intent.action.SEARCH").setPackage(str), 276824192, changingUserId).isEmpty()) {
                    Context context2 = SearchManagerService.this.mContext;
                    if (context2.getPackageManager().queryIntentActivitiesAsUser(new Intent("android.intent.action.WEB_SEARCH").setPackage(str), 276824192, changingUserId).isEmpty()) {
                        Context context3 = SearchManagerService.this.mContext;
                        z = true ^ context3.getPackageManager().queryIntentActivitiesAsUser(new Intent("android.search.action.GLOBAL_SEARCH").setPackage(str), 276824192, changingUserId).isEmpty();
                    }
                }
                this.mSearchablePackageAppeared = z;
            }
            this.mChangedPackages.add(str);
        }

        public final void onPackageDisappeared(String str, int i) {
            this.mChangedPackages.add(str);
        }

        public final void onPackageModified(String str) {
            this.mChangedPackages.add(str);
        }
    }

    public SearchManagerService(Context context) {
        this.mContext = context;
        new MyPackageMonitor().register(context, (Looper) null, UserHandle.ALL, true);
        new GlobalSearchProviderObserver(context.getContentResolver());
        BackgroundThread.getHandler();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "SearchManagerService", printWriter)) {
            PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            synchronized (this.mSearchables) {
                for (int i = 0; i < this.mSearchables.size(); i++) {
                    try {
                        indentingPrintWriter.print("\nUser: ");
                        indentingPrintWriter.println(this.mSearchables.keyAt(i));
                        indentingPrintWriter.increaseIndent();
                        ((Searchables) this.mSearchables.valueAt(i)).dump(indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public final List getGlobalSearchActivities() {
        ArrayList createFilterdResolveInfoList;
        Searchables searchables = getSearchables(UserHandle.getCallingUserId());
        synchronized (searchables) {
            createFilterdResolveInfoList = Searchables.createFilterdResolveInfoList(searchables.mGlobalSearchActivities);
        }
        return createFilterdResolveInfoList;
    }

    public final ComponentName getGlobalSearchActivity() {
        Searchables searchables = getSearchables(UserHandle.getCallingUserId());
        synchronized (searchables) {
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            ComponentName componentName = searchables.mCurrentGlobalSearchActivity;
            if (componentName == null || !packageManagerInternal.canAccessComponent(callingUid, callingUserId, componentName)) {
                return null;
            }
            return searchables.mCurrentGlobalSearchActivity;
        }
    }

    public final SearchableInfo getSearchableInfo(ComponentName componentName) {
        Bundle bundle;
        if (componentName == null) {
            Log.e("SearchManagerService", "getSearchableInfo(), activity == null");
            return null;
        }
        Searchables searchables = getSearchables(UserHandle.getCallingUserId());
        synchronized (searchables) {
            try {
                SearchableInfo searchableInfo = (SearchableInfo) searchables.mSearchablesMap.get(componentName);
                if (searchableInfo == null) {
                    try {
                        ActivityInfo activityInfo = searchables.mPm.getActivityInfo(componentName, 128L, searchables.mUserId);
                        if (activityInfo == null) {
                            Log.e("Searchables", "Error activity info is null:" + componentName);
                            return null;
                        }
                        Bundle bundle2 = activityInfo.metaData;
                        String string = bundle2 != null ? bundle2.getString("android.app.default_searchable") : null;
                        if (string == null && (bundle = activityInfo.applicationInfo.metaData) != null) {
                            string = bundle.getString("android.app.default_searchable");
                        }
                        if (string == null || string.equals("*")) {
                            return null;
                        }
                        String packageName = componentName.getPackageName();
                        ComponentName componentName2 = string.charAt(0) == '.' ? new ComponentName(packageName, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(packageName, string)) : new ComponentName(packageName, string);
                        synchronized (searchables) {
                            try {
                                searchableInfo = (SearchableInfo) searchables.mSearchablesMap.get(componentName2);
                                if (searchableInfo != null) {
                                    searchables.mSearchablesMap.put(componentName, searchableInfo);
                                }
                            } finally {
                            }
                        }
                        if (searchableInfo == null) {
                            return null;
                        }
                        if (!((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).canAccessComponent(Binder.getCallingUid(), UserHandle.getCallingUserId(), searchableInfo.getSearchActivity())) {
                            return null;
                        }
                    } catch (RemoteException e) {
                        NetdService$$ExternalSyntheticOutline0.m("Error getting activity info ", e, "Searchables");
                        return null;
                    }
                } else if (!((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).canAccessComponent(Binder.getCallingUid(), UserHandle.getCallingUserId(), searchableInfo.getSearchActivity())) {
                    return null;
                }
                return searchableInfo;
            } finally {
            }
        }
    }

    public final Searchables getSearchables(int i) {
        Searchables searchables;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
            if (userManager.getUserInfo(i) == null) {
                throw new IllegalStateException("User " + i + " doesn't exist");
            }
            if (!userManager.isUserUnlockingOrUnlocked(i)) {
                throw new IllegalStateException("User " + i + " isn't unlocked");
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            synchronized (this.mSearchables) {
                try {
                    searchables = (Searchables) this.mSearchables.get(i);
                    if (searchables == null) {
                        searchables = new Searchables(this.mContext, i);
                        this.mSearchables.put(i, searchables);
                    }
                    searchables.updateSearchableListIfNeeded();
                } finally {
                }
            }
            return searchables;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final List getSearchablesInGlobalSearch() {
        ArrayList createFilterdSearchableInfoList;
        Searchables searchables = getSearchables(UserHandle.getCallingUserId());
        synchronized (searchables) {
            createFilterdSearchableInfoList = Searchables.createFilterdSearchableInfoList(searchables.mSearchablesInGlobalSearchList);
        }
        return createFilterdSearchableInfoList;
    }

    public final ComponentName getWebSearchActivity() {
        Searchables searchables = getSearchables(UserHandle.getCallingUserId());
        synchronized (searchables) {
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            int callingUid = Binder.getCallingUid();
            int callingUserId = UserHandle.getCallingUserId();
            ComponentName componentName = searchables.mWebSearchActivity;
            if (componentName == null || !packageManagerInternal.canAccessComponent(callingUid, callingUserId, componentName)) {
                return null;
            }
            return searchables.mWebSearchActivity;
        }
    }

    public final void launchAssist(int i, Bundle bundle) {
        IStatusBar iStatusBar;
        StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
        if (statusBarManagerInternal == null || (iStatusBar = StatusBarManagerService.this.mBar) == null) {
            return;
        }
        try {
            iStatusBar.startAssist(bundle);
        } catch (RemoteException unused) {
        }
    }
}
