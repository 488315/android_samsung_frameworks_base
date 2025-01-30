package com.android.systemui.edgelighting.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.EdgeLightingService;
import com.android.systemui.edgelighting.data.AppInfo;
import com.android.systemui.edgelighting.data.EdgeLightingSettingItem;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingSettingManager {
    public static EdgeLightingSettingManager sInstance;
    public boolean mAllApplication;
    public final C13491 mAppNameComparator;
    public final Context mContext;
    public final HashMap mEnableSet;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            Context context = edgeLightingSettingManager.mContext;
            PackageManager packageManager = context.getPackageManager();
            EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.getInstance(context, false);
            int i = edgeLightingPolicyManager.mPolicyType & 4;
            SparseArray sparseArray = edgeLightingPolicyManager.mPolicyInfoData;
            HashMap hashMap = i != 0 ? (HashMap) sparseArray.get(2) : null;
            HashMap hashMap2 = (HashMap) sparseArray.get(10);
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
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                AppInfo appInfo = (AppInfo) arrayList2.get(i2);
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

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00a0, code lost:
    
        if (r7 == null) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x00ae, code lost:
    
        if (r7 != null) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x016b, code lost:
    
        if (r7 == null) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x017b, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x017f, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0180, code lost:
    
        r11.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0179, code lost:
    
        if (r7 != null) goto L168;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0164 A[Catch: all -> 0x015e, IOException -> 0x0160, TRY_LEAVE, TryCatch #16 {IOException -> 0x0160, blocks: (B:144:0x015a, B:120:0x0164), top: B:143:0x015a, outer: #18 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x015a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.edgelighting.manager.EdgeLightingSettingManager] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.edgelighting.manager.EdgeLightingSettingManager$1] */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v24, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r8v2, types: [android.content.pm.PackageManager] */
    /* JADX WARN: Type inference failed for: r8v6, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v9, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private EdgeLightingSettingManager(Context context) {
        ApplicationInfo applicationInfo;
        boolean z;
        IOException e;
        InputStream inputStream;
        ApplicationInfo applicationInfo2;
        this.mAllApplication = true;
        HashMap hashMap = new HashMap();
        this.mEnableSet = hashMap;
        new HashMap();
        this.mAppNameComparator = new Comparator(this) { // from class: com.android.systemui.edgelighting.manager.EdgeLightingSettingManager.1
            public final Collator collator = Collator.getInstance();

            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                AppInfo appInfo = (AppInfo) obj;
                AppInfo appInfo2 = (AppInfo) obj2;
                try {
                    int i = appInfo.priority;
                    int i2 = appInfo2.priority;
                    return i == i2 ? this.collator.compare(appInfo.appName, appInfo2.appName) : i2 - i;
                } catch (NullPointerException e2) {
                    Slog.e("EdgeLightingSettingManager", "Failed to compare AppInfo. " + e2);
                    return 0;
                }
            }
        };
        this.mContext = context;
        hashMap.clear();
        int i = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences("edge_lighting_settings", 0);
        int i2 = sharedPreferences.getInt("version", 0);
        this.mAllApplication = sharedPreferences.getBoolean("all_application", true);
        InputStream inputStream2 = null;
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            try {
                inputStream = context.getResources().openRawResource(R.raw.edge_lighting_settings);
            } catch (IOException e2) {
                e = e2;
                inputStream = null;
                z = null;
            } catch (Throwable th) {
                th = th;
                z = null;
                if (inputStream2 != null) {
                }
                if (z != null) {
                }
            }
            try {
                z = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    try {
                        try {
                            String readLine = z.readLine();
                            if (readLine == null) {
                                try {
                                    break;
                                } catch (Throwable th2) {
                                    try {
                                        z.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                    throw th2;
                                }
                            }
                            sb.append(readLine);
                        } catch (IOException e4) {
                            e = e4;
                            e.printStackTrace();
                            if (inputStream != null) {
                                try {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                } finally {
                                    if (z != null) {
                                        try {
                                            z.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                }
                            }
                            if (z != null) {
                                z.close();
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        inputStream2 = inputStream;
                        if (inputStream2 != null) {
                            try {
                                try {
                                    inputStream2.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            } finally {
                            }
                        }
                        if (z != null) {
                            z.close();
                        }
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                z.close();
            } catch (IOException e9) {
                e = e9;
                z = null;
            } catch (Throwable th4) {
                th = th4;
                z = null;
                inputStream2 = inputStream;
                if (inputStream2 != null) {
                }
                if (z != null) {
                }
            }
            try {
                z.close();
            } catch (IOException e10) {
                e10.printStackTrace();
            }
            try {
                boolean z2 = new JSONObject(sb.toString()).getInt("edge_lighting_default_type") == 1;
                this.mAllApplication = z2;
                if (z2) {
                    new GetAppNameListAsyncTask(this, i).execute(new Void[0]);
                } else {
                    HashMap hashMap2 = (HashMap) EdgeLightingPolicyManager.getInstance(context, false).mPolicyInfoData.get(10);
                    if (hashMap2 != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Map.Entry entry : hashMap2.entrySet()) {
                            ?? r8 = (String) entry.getKey();
                            if (((PolicyInfo) entry.getValue()).defaultOn) {
                                List appInfoSupportingEdgeLighting = EdgeLightingSettingUtils.getAppInfoSupportingEdgeLighting(context.getPackageManager(), r8);
                                z = appInfoSupportingEdgeLighting != null && appInfoSupportingEdgeLighting.size() > 0;
                                if (z) {
                                    hashMap.put(r8, new EdgeLightingSettingItem(r8, -11761985));
                                    ?? packageManager = this.mContext.getPackageManager();
                                    try {
                                        applicationInfo2 = packageManager.getApplicationInfo(r8, 0);
                                    } catch (PackageManager.NameNotFoundException unused) {
                                        applicationInfo2 = null;
                                    }
                                    arrayList.add((String) (applicationInfo2 != null ? packageManager.getApplicationLabel(applicationInfo2) : r8));
                                }
                            }
                        }
                        writeAppNameList(arrayList);
                    }
                }
            } catch (JSONException e11) {
                e11.printStackTrace();
            }
        } else {
            Set<String> stringSet = sharedPreferences.getStringSet("enable_list", null);
            if (stringSet != null) {
                ArrayList arrayList2 = new ArrayList();
                for (String str : stringSet) {
                    hashMap.put(str, new EdgeLightingSettingItem(str, -11761985));
                    ?? packageManager2 = this.mContext.getPackageManager();
                    try {
                        applicationInfo = packageManager2.getApplicationInfo(str, 0);
                    } catch (PackageManager.NameNotFoundException unused2) {
                        applicationInfo = null;
                    }
                    if (applicationInfo != null) {
                        str = packageManager2.getApplicationLabel(applicationInfo);
                    }
                    arrayList2.add((String) str);
                }
                writeAppNameList(arrayList2);
            }
        }
        EdgeLightingSettingUtils.initializeSettingValue(context.getContentResolver(), false);
        if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
            Intent intent = new Intent(context, (Class<?>) EdgeLightingService.class);
            intent.putExtra("forUpdatePolicy", true);
            context.startService(intent);
        }
        String effectEnglishName = Utils.getEffectEnglishName(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(context.getContentResolver()));
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("edgelighting_pref", 0);
        if (sharedPreferences2 != null) {
            SharedPreferences.Editor edit = sharedPreferences2.edit();
            edit.putString("36105", effectEnglishName);
            edit.apply();
        }
        String colorName = Utils.getColorName(EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(context.getContentResolver()));
        SharedPreferences sharedPreferences3 = context.getSharedPreferences("edgelighting_pref", 0);
        if (sharedPreferences3 != null) {
            SharedPreferences.Editor edit2 = sharedPreferences3.edit();
            edit2.putString("36106", colorName);
            edit2.apply();
        }
        int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_transparency", 0, -2);
        SharedPreferences sharedPreferences4 = context.getSharedPreferences("edgelighting_pref", 0);
        if (sharedPreferences4 != null) {
            SharedPreferences.Editor edit3 = sharedPreferences4.edit();
            edit3.putInt("36107", intForUser);
            edit3.apply();
        }
        int intForUser2 = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_thickness", 0, -2);
        SharedPreferences sharedPreferences5 = context.getSharedPreferences("edgelighting_pref", 0);
        if (sharedPreferences5 != null) {
            SharedPreferences.Editor edit4 = sharedPreferences5.edit();
            edit4.putInt("36108", intForUser2);
            edit4.apply();
            return;
        }
        return;
        throw th;
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
        HashMap hashMap2;
        if (this.mAllApplication || hashMap == null) {
            return;
        }
        Iterator it = hashMap.entrySet().iterator();
        boolean z = false;
        while (true) {
            boolean hasNext = it.hasNext();
            hashMap2 = this.mEnableSet;
            if (!hasNext) {
                break;
            } else if (hashMap2.remove((String) ((Map.Entry) it.next()).getKey()) != null) {
                z = true;
            }
        }
        if (z) {
            SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
            edit.putInt("version", 1);
            edit.putBoolean("all_application", false);
            edit.putStringSet("enable_list", hashMap2.keySet());
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
        HashMap hashMap = this.mEnableSet;
        hashMap.remove(str);
        SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
        edit.putInt("version", 1);
        edit.putStringSet("enable_list", hashMap.keySet());
        edit.apply();
        EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, this.mAllApplication);
    }

    public final void setEnablePackage(Context context, String str) {
        HashMap hashMap = this.mEnableSet;
        hashMap.put(str, new EdgeLightingSettingItem(str, -11761985));
        SharedPreferences.Editor edit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
        edit.putInt("version", 1);
        edit.putStringSet("enable_list", hashMap.keySet());
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
                    str2 = "".equals(str2) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str2, str3) : AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str2, ",", str3);
                }
                str = str2;
            } else {
                str = "";
            }
        }
        Slog.d("EdgeLightingSettingManager", "write default enable app list... " + str);
    }
}
