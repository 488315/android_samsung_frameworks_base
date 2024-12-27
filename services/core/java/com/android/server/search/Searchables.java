package com.android.server.search;

import android.app.AppGlobals;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;

import com.android.server.LocalServices;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Searchables {
    public static final AnonymousClass1 GLOBAL_SEARCH_RANKER = new AnonymousClass1();
    public final Context mContext;
    public List mGlobalSearchActivities;
    public final int mUserId;
    public HashMap mSearchablesMap = null;
    public ArrayList mSearchablesList = null;
    public ArrayList mSearchablesInGlobalSearchList = null;
    public ComponentName mCurrentGlobalSearchActivity = null;
    public ComponentName mWebSearchActivity = null;
    public boolean mRebuildSearchables = true;
    public ArraySet mKnownSearchablePackageNames = new ArraySet();
    public final IPackageManager mPm = AppGlobals.getPackageManager();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.search.Searchables$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            ResolveInfo resolveInfo = (ResolveInfo) obj;
            ResolveInfo resolveInfo2 = (ResolveInfo) obj2;
            if (resolveInfo == resolveInfo2) {
                return 0;
            }
            boolean z = (resolveInfo.activityInfo.applicationInfo.flags & 1) != 0;
            boolean z2 = (resolveInfo2.activityInfo.applicationInfo.flags & 1) != 0;
            if (z && !z2) {
                return -1;
            }
            if (!z2 || z) {
                return resolveInfo2.priority - resolveInfo.priority;
            }
            return 1;
        }
    }

    public Searchables(Context context, int i) {
        this.mContext = context;
        this.mUserId = i;
    }

    public static ArrayList createFilterdResolveInfoList(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            if (packageManagerInternal.canAccessComponent(
                    callingUid, callingUserId, resolveInfo.activityInfo.getComponentName())) {
                arrayList.add(resolveInfo);
            }
        }
        return arrayList;
    }

    public static ArrayList createFilterdSearchableInfoList(List list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = (ArrayList) list;
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SearchableInfo searchableInfo = (SearchableInfo) it.next();
            if (packageManagerInternal.canAccessComponent(
                    callingUid, callingUserId, searchableInfo.getSearchActivity())) {
                arrayList2.add(searchableInfo);
            }
        }
        return arrayList2;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("Searchable authorities:");
        synchronized (this) {
            try {
                ArrayList arrayList = this.mSearchablesList;
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        SearchableInfo searchableInfo = (SearchableInfo) it.next();
                        printWriter.print("  ");
                        printWriter.println(searchableInfo.getSuggestAuthority());
                    }
                }
                printWriter.println("mRebuildSearchables = " + this.mRebuildSearchables);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ComponentName findGlobalSearchActivity(List list) {
        ComponentName unflattenFromString;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String stringForUser =
                Settings.Secure.getStringForUser(
                        contentResolver,
                        "search_global_search_activity",
                        contentResolver.getUserId());
        if (!TextUtils.isEmpty(stringForUser)
                && (unflattenFromString = ComponentName.unflattenFromString(stringForUser))
                        != null) {
            Intent intent = new Intent("android.search.action.GLOBAL_SEARCH");
            intent.setComponent(unflattenFromString);
            List queryIntentActivities =
                    queryIntentActivities(
                            EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, intent);
            if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {
                return unflattenFromString;
            }
        }
        if (list == null || list.isEmpty()) {
            Log.w("Searchables", "No global search activity found");
            return null;
        }
        ActivityInfo activityInfo = ((ResolveInfo) list.get(0)).activityInfo;
        return new ComponentName(activityInfo.packageName, activityInfo.name);
    }

    public final ComponentName findWebSearchActivity(ComponentName componentName) {
        if (componentName == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.WEB_SEARCH");
        intent.setPackage(componentName.getPackageName());
        List queryIntentActivities =
                queryIntentActivities(
                        EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT, intent);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            Log.w("Searchables", "No web search activity found");
            return null;
        }
        ActivityInfo activityInfo = ((ResolveInfo) queryIntentActivities.get(0)).activityInfo;
        return new ComponentName(activityInfo.packageName, activityInfo.name);
    }

    public final List queryIntentActivities(int i, Intent intent) {
        try {
            return this.mPm
                    .queryIntentActivities(
                            intent,
                            intent.resolveTypeIfNeeded(this.mContext.getContentResolver()),
                            i | 8388608,
                            this.mUserId)
                    .getList();
        } catch (RemoteException unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0065, code lost:

       r12 = 0;
    */
    /* JADX WARN: Removed duplicated region for block: B:26:0x012a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSearchableListIfNeeded() {
        /*
            Method dump skipped, instructions count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.search.Searchables.updateSearchableListIfNeeded():void");
    }
}
