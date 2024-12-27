package com.android.systemui.edgelighting.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.util.Slog;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.edgelighting.data.AppInfo;
import com.android.systemui.edgelighting.data.EdgeLightingSettingItem;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdgeLightingSettingManager {
    public static EdgeLightingSettingManager sInstance;
    public final boolean mAllApplication;
    public final AnonymousClass1 mAppNameComparator;
    public final Context mContext;
    public final HashMap mEnableSet;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class GetAppNameListAsyncTask extends AsyncTask {
        public /* synthetic */ GetAppNameListAsyncTask(EdgeLightingSettingManager edgeLightingSettingManager, int i) {
            this();
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String str;
            EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.this;
            edgeLightingSettingManager.getClass();
            ArrayList arrayList = new ArrayList();
            PackageManager packageManager = edgeLightingSettingManager.mContext.getPackageManager();
            EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.getInstance(edgeLightingSettingManager.mContext, false);
            HashMap hashMap = (edgeLightingPolicyManager.mPolicyType & 4) != 0 ? (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(2) : null;
            HashMap hashMap2 = (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(10);
            ArrayList arrayList2 = new ArrayList();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            if (queryIntentActivities != null) {
                Iterator<ResolveInfo> it = queryIntentActivities.iterator();
                while (it.hasNext()) {
                    ActivityInfo activityInfo = it.next().activityInfo;
                    String str2 = activityInfo.name;
                    String str3 = activityInfo.packageName;
                    if (hashMap == null || !hashMap.containsKey(str3)) {
                        ComponentName componentName = new ComponentName(str3, str2);
                        String flattenToString = componentName.flattenToString();
                        if (flattenToString != null) {
                            componentName = ComponentName.unflattenFromString(flattenToString);
                        }
                        try {
                            str = packageManager.getActivityInfo(componentName, 0).loadLabel(packageManager).toString();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            str = null;
                        }
                        if (str == null || str3 == null) {
                            Slog.e("EdgeLightingSettingManager", "Error...");
                        } else {
                            PolicyInfo policyInfo = hashMap2 != null ? (PolicyInfo) hashMap2.get(str3) : null;
                            arrayList2.add(new AppInfo(str, str3, null, policyInfo != null ? policyInfo.priority : 0, false));
                        }
                    }
                }
            }
            Collections.sort(arrayList2, edgeLightingSettingManager.mAppNameComparator);
            for (int i = 0; i < arrayList2.size(); i++) {
                AppInfo appInfo = (AppInfo) arrayList2.get(i);
                if (appInfo == null) {
                    Slog.e("EdgeLightingSettingManager", "updateAppList item is null..");
                } else {
                    arrayList.add(appInfo.packageName);
                }
            }
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            List<String> list = (List) obj;
            super.onPostExecute(list);
            for (String str : list) {
                EdgeLightingSettingManager.this.mEnableSet.put(str, new EdgeLightingSettingItem(str, -11761985));
            }
        }

        private GetAppNameListAsyncTask() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:122:0x016a A[Catch: all -> 0x0164, IOException -> 0x0166, TRY_LEAVE, TryCatch #14 {IOException -> 0x0166, blocks: (B:133:0x0160, B:122:0x016a), top: B:132:0x0160, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0189 A[Catch: IOException -> 0x0177, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0177, blocks: (B:131:0x0189, B:128:0x0173, B:133:0x0160, B:122:0x016a), top: B:119:0x015e, inners: #14 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00db A[Catch: JSONException -> 0x0146, TryCatch #5 {JSONException -> 0x0146, blocks: (B:18:0x00c3, B:21:0x00d7, B:23:0x00db, B:25:0x00eb, B:26:0x00f8, B:28:0x00fe, B:31:0x0114, B:34:0x011e, B:37:0x0124, B:39:0x0134, B:41:0x013c, B:42:0x0140, B:51:0x0148, B:70:0x014d), top: B:17:0x00c3 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x014d A[Catch: JSONException -> 0x0146, TRY_LEAVE, TryCatch #5 {JSONException -> 0x0146, blocks: (B:18:0x00c3, B:21:0x00d7, B:23:0x00db, B:25:0x00eb, B:26:0x00f8, B:28:0x00fe, B:31:0x0114, B:34:0x011e, B:37:0x0124, B:39:0x0134, B:41:0x013c, B:42:0x0140, B:51:0x0148, B:70:0x014d), top: B:17:0x00c3 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00d6  */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.edgelighting.manager.EdgeLightingSettingManager$1] */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v23, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r7v2, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.android.systemui.edgelighting.manager.EdgeLightingSettingManager] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private EdgeLightingSettingManager(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 619
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.manager.EdgeLightingSettingManager.<init>(android.content.Context):void");
    }

    public static synchronized EdgeLightingSettingManager getInstance(Context context) {
        EdgeLightingSettingManager edgeLightingSettingManager;
        synchronized (EdgeLightingSettingManager.class) {
            edgeLightingSettingManager = sInstance;
            if (edgeLightingSettingManager == null) {
                edgeLightingSettingManager = new EdgeLightingSettingManager(context);
                sInstance = edgeLightingSettingManager;
            }
        }
        return edgeLightingSettingManager;
    }

    public static void putStringSet(SharedPreferences sharedPreferences, String str, Set set) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putStringSet(str, set);
        edit.apply();
    }

    public static void remove(SharedPreferences sharedPreferences, String str) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(str);
        edit.apply();
    }

    public final void removeBlockListInEnabledEdgeLightingList(Context context, HashMap hashMap) {
        if (this.mAllApplication || hashMap == null) {
            return;
        }
        Iterator it = hashMap.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (this.mEnableSet.remove((String) ((Map.Entry) it.next()).getKey()) != null) {
                z = true;
            }
        }
        if (z) {
            SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
            edit.putInt("version", 1);
            edit.putBoolean("all_application", false);
            edit.putStringSet("enable_list", this.mEnableSet.keySet());
            edit.apply();
        }
    }

    public final void replaceSilentInstalledPackage(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("edge_lighting_settings", 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        String string = sharedPreferences.getString("update_package_name", null);
        boolean z = sharedPreferences.getBoolean("update_package_enable", false);
        if (string != null && !string.isEmpty() && string.equals(str)) {
            Slog.d("EdgeLightingSettingManager", "replaceSilentInstalledPackage : " + z + ", packageName = " + str);
            if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
                if (z) {
                    setEnablePackage(context, str);
                } else {
                    setDisablePackage(context, str);
                }
                EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, this.mAllApplication);
            } else if (z) {
                Set<String> stringSet = sharedPreferences.getStringSet("silent_add_list", new HashSet());
                stringSet.add(str);
                remove(sharedPreferences, "silent_add_list");
                putStringSet(sharedPreferences, "silent_add_list", stringSet);
                Set<String> stringSet2 = sharedPreferences.getStringSet("silent_remove_list", new HashSet());
                stringSet2.remove(str);
                remove(sharedPreferences, "silent_remove_list");
                putStringSet(sharedPreferences, "silent_remove_list", stringSet2);
            } else {
                Set<String> stringSet3 = sharedPreferences.getStringSet("silent_remove_list", new HashSet());
                stringSet3.add(str);
                remove(sharedPreferences, "silent_remove_list");
                putStringSet(sharedPreferences, "silent_remove_list", stringSet3);
                Set<String> stringSet4 = sharedPreferences.getStringSet("silent_add_list", new HashSet());
                stringSet4.remove(str);
                remove(sharedPreferences, "silent_add_list");
                putStringSet(sharedPreferences, "silent_add_list", stringSet4);
            }
        }
        edit.putString("update_package_name", null);
        edit.putBoolean("update_package_enable", false);
        edit.apply();
    }

    public final void setDisablePackage(Context context, String str) {
        this.mEnableSet.remove(str);
        SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
        edit.putInt("version", 1);
        edit.putStringSet("enable_list", this.mEnableSet.keySet());
        edit.apply();
        EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, this.mAllApplication);
    }

    public final void setEnablePackage(Context context, String str) {
        this.mEnableSet.put(str, new EdgeLightingSettingItem(str, -11761985));
        SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
        edit.putInt("version", 1);
        edit.putStringSet("enable_list", this.mEnableSet.keySet());
        edit.apply();
    }

    public final void writeAppNameList(List list) {
        String str;
        if (this.mAllApplication) {
            str = "AllAppsAvailable";
        } else {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                String str2 = "";
                while (it.hasNext()) {
                    String str3 = (String) it.next();
                    str2 = "".equals(str2) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, str3) : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, ",", str3);
                }
                str = str2;
            } else {
                str = "";
            }
        }
        Slog.d("EdgeLightingSettingManager", "write default enable app list... " + str);
    }
}
