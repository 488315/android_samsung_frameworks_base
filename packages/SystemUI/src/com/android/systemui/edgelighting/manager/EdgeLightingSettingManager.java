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
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.EdgeLightingService;
import com.android.systemui.edgelighting.data.AppInfo;
import com.android.systemui.edgelighting.data.EdgeLightingSettingItem;
import com.android.systemui.edgelighting.data.policy.PolicyInfo;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.android.systemui.edgelighting.utils.Utils;
import com.android.systemui.util.SystemUIAnalytics;
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

/* loaded from: classes2.dex */
public class EdgeLightingSettingManager {
    public static EdgeLightingSettingManager sInstance;
    public final boolean mAllApplication;
    public final AnonymousClass1 mAppNameComparator;
    public final Context mContext;
    public final HashMap mEnableSet;

    public class GetAppNameListAsyncTask extends AsyncTask {
        public /* synthetic */ GetAppNameListAsyncTask(EdgeLightingSettingManager edgeLightingSettingManager, int i) {
            this();
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String string;
            EdgeLightingSettingManager edgeLightingSettingManager = EdgeLightingSettingManager.this;
            edgeLightingSettingManager.getClass();
            ArrayList arrayList = new ArrayList();
            PackageManager packageManager = edgeLightingSettingManager.mContext.getPackageManager();
            EdgeLightingPolicyManager edgeLightingPolicyManager = EdgeLightingPolicyManager.getInstance(edgeLightingSettingManager.mContext, false);
            HashMap map = (edgeLightingPolicyManager.mPolicyType & 4) != 0 ? (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(2) : null;
            HashMap map2 = (HashMap) edgeLightingPolicyManager.mPolicyInfoData.get(10);
            ArrayList arrayList2 = new ArrayList();
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            if (listQueryIntentActivities != null) {
                Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
                while (it.hasNext()) {
                    ActivityInfo activityInfo = it.next().activityInfo;
                    String str = activityInfo.name;
                    String str2 = activityInfo.packageName;
                    if (map == null || !map.containsKey(str2)) {
                        ComponentName componentName = new ComponentName(str2, str);
                        String strFlattenToString = componentName.flattenToString();
                        if (strFlattenToString != null) {
                            componentName = ComponentName.unflattenFromString(strFlattenToString);
                        }
                        try {
                            string = packageManager.getActivityInfo(componentName, 0).loadLabel(packageManager).toString();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                            string = null;
                        }
                        if (string == null || str2 == null) {
                            Slog.e("EdgeLightingSettingManager", "Error...");
                        } else {
                            PolicyInfo policyInfo = map2 != null ? (PolicyInfo) map2.get(str2) : null;
                            arrayList2.add(new AppInfo(string, str2, null, policyInfo != null ? policyInfo.priority : 0, false));
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
    /* JADX WARN: Removed duplicated region for block: B:101:0x016a A[Catch: all -> 0x0164, IOException -> 0x0166, TRY_LEAVE, TryCatch #14 {IOException -> 0x0166, blocks: (B:95:0x0160, B:101:0x016a), top: B:160:0x0160, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0189 A[Catch: IOException -> 0x0177, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0177, blocks: (B:116:0x0189, B:105:0x0173, B:95:0x0160, B:101:0x016a), top: B:150:0x015e, inners: #14 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0160 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:186:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00db A[Catch: JSONException -> 0x0146, TryCatch #5 {JSONException -> 0x0146, blocks: (B:64:0x00c3, B:68:0x00d7, B:70:0x00db, B:72:0x00eb, B:73:0x00f8, B:75:0x00fe, B:77:0x0114, B:79:0x011e, B:81:0x0124, B:82:0x0134, B:86:0x013c, B:87:0x0140, B:90:0x0148, B:91:0x014d), top: B:152:0x00c3 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x014d A[Catch: JSONException -> 0x0146, TRY_LEAVE, TryCatch #5 {JSONException -> 0x0146, blocks: (B:64:0x00c3, B:68:0x00d7, B:70:0x00db, B:72:0x00eb, B:73:0x00f8, B:75:0x00fe, B:77:0x0114, B:79:0x011e, B:81:0x0124, B:82:0x0134, B:86:0x013c, B:87:0x0140, B:90:0x0148, B:91:0x014d), top: B:152:0x00c3 }] */
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
    */
    private EdgeLightingSettingManager(Context context) throws Throwable {
        ApplicationInfo applicationInfo;
        SharedPreferences sharedPreferences;
        SharedPreferences sharedPreferences2;
        SharedPreferences sharedPreferences3;
        SharedPreferences sharedPreferences4;
        BufferedReader bufferedReader;
        InputStream inputStreamOpenRawResource;
        boolean z;
        List appInfoSupportingEdgeLighting;
        ApplicationInfo applicationInfo2;
        this.mAllApplication = true;
        HashMap map = new HashMap();
        this.mEnableSet = map;
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
                } catch (NullPointerException e) {
                    Slog.e("EdgeLightingSettingManager", "Failed to compare AppInfo. " + e);
                    return 0;
                }
            }
        };
        this.mContext = context;
        map.clear();
        int i = 0;
        SharedPreferences sharedPreferences5 = context.getSharedPreferences("edge_lighting_settings", 0);
        int i2 = sharedPreferences5.getInt("version", 0);
        this.mAllApplication = sharedPreferences5.getBoolean("all_application", true);
        InputStream inputStream = null;
        if (i2 == 0) {
            StringBuilder sb = new StringBuilder();
            try {
                try {
                    inputStreamOpenRawResource = context.getResources().openRawResource(R.raw.edge_lighting_settings);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e2) {
                e = e2;
                inputStreamOpenRawResource = null;
                bufferedReader = null;
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
                try {
                    try {
                        if (inputStream != null) {
                        }
                        if (bufferedReader != null) {
                        }
                        if (bufferedReader != null) {
                        }
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    throw th;
                } finally {
                }
            }
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenRawResource));
                while (true) {
                    try {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                try {
                                    break;
                                } finally {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                            }
                            sb.append(line);
                        } catch (IOException e5) {
                            e = e5;
                            e.printStackTrace();
                            try {
                                if (inputStreamOpenRawResource != null) {
                                    try {
                                        inputStreamOpenRawResource.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                        if (bufferedReader != null) {
                                            bufferedReader.close();
                                        }
                                    }
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                if (new JSONObject(sb.toString()).getInt("edge_lighting_default_type") != 1) {
                                }
                                this.mAllApplication = z;
                                if (z) {
                                }
                                EdgeLightingSettingUtils.initializeSettingValue(context.getContentResolver(), false);
                                if (EdgeLightingSettingUtils.isEdgeLightingEnabled(context.getContentResolver())) {
                                }
                                String effectEnglishName = Utils.getEffectEnglishName(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(context.getContentResolver()));
                                sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
                                if (sharedPreferences != null) {
                                }
                                String colorName = Utils.getColorName(EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(context.getContentResolver()));
                                sharedPreferences2 = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
                                if (sharedPreferences2 != null) {
                                }
                                int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_transparency", 0, -2);
                                sharedPreferences3 = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
                                if (sharedPreferences3 != null) {
                                }
                                int intForUser2 = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_thickness", 0, -2);
                                sharedPreferences4 = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
                                if (sharedPreferences4 == null) {
                                }
                            } finally {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream = inputStreamOpenRawResource;
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e7) {
                                e7.printStackTrace();
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                throw th;
                            }
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if (bufferedReader != null) {
                            bufferedReader.close();
                        }
                        throw th;
                    }
                }
                if (inputStreamOpenRawResource != null) {
                    try {
                        inputStreamOpenRawResource.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                        bufferedReader.close();
                    }
                }
                bufferedReader.close();
                bufferedReader.close();
            } catch (IOException e9) {
                e = e9;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                inputStream = inputStreamOpenRawResource;
                if (inputStream != null) {
                }
                if (bufferedReader != null) {
                }
                if (bufferedReader != null) {
                }
                throw th;
            }
            try {
                z = new JSONObject(sb.toString()).getInt("edge_lighting_default_type") != 1;
                this.mAllApplication = z;
                if (z) {
                    HashMap map2 = (HashMap) EdgeLightingPolicyManager.getInstance(context, false).mPolicyInfoData.get(10);
                    if (map2 != null) {
                        ArrayList arrayList = new ArrayList();
                        for (Map.Entry entry : map2.entrySet()) {
                            ?? r7 = (String) entry.getKey();
                            if (((PolicyInfo) entry.getValue()).defaultOn && (appInfoSupportingEdgeLighting = EdgeLightingSettingUtils.getAppInfoSupportingEdgeLighting(context.getPackageManager(), r7)) != null && appInfoSupportingEdgeLighting.size() > 0) {
                                this.mEnableSet.put(r7, new EdgeLightingSettingItem(r7, -11761985));
                                ?? packageManager = this.mContext.getPackageManager();
                                try {
                                    applicationInfo2 = packageManager.getApplicationInfo(r7, 0);
                                } catch (PackageManager.NameNotFoundException unused) {
                                    applicationInfo2 = null;
                                }
                                arrayList.add((String) (applicationInfo2 != null ? packageManager.getApplicationLabel(applicationInfo2) : r7));
                            }
                        }
                        writeAppNameList(arrayList);
                    }
                } else {
                    new GetAppNameListAsyncTask(this, i).execute(new Void[0]);
                }
            } catch (JSONException e10) {
                e10.printStackTrace();
            }
        } else {
            Set<String> stringSet = sharedPreferences5.getStringSet("enable_list", null);
            if (stringSet != null) {
                ArrayList arrayList2 = new ArrayList();
                for (String applicationLabel : stringSet) {
                    this.mEnableSet.put(applicationLabel, new EdgeLightingSettingItem(applicationLabel, -11761985));
                    ?? packageManager2 = this.mContext.getPackageManager();
                    try {
                        applicationInfo = packageManager2.getApplicationInfo(applicationLabel, 0);
                    } catch (PackageManager.NameNotFoundException unused2) {
                        applicationInfo = null;
                    }
                    if (applicationInfo != null) {
                        applicationLabel = packageManager2.getApplicationLabel(applicationInfo);
                    }
                    arrayList2.add((String) applicationLabel);
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
        String effectEnglishName2 = Utils.getEffectEnglishName(EdgeLightingStyleManager.getInstance().getEdgeLightingStyleType(context.getContentResolver()));
        sharedPreferences = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString(SystemUIAnalytics.EDGE_LIGHTING_STATUS_STYLE_EFFECT, effectEnglishName2);
            editorEdit.apply();
        }
        String colorName2 = Utils.getColorName(EdgeLightingSettingUtils.getEdgeLightingBasicColorIndex(context.getContentResolver()));
        sharedPreferences2 = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
        if (sharedPreferences2 != null) {
            SharedPreferences.Editor editorEdit2 = sharedPreferences2.edit();
            editorEdit2.putString(SystemUIAnalytics.EDGE_LIGHTING_STATUS_STYLE_COLOR, colorName2);
            editorEdit2.apply();
        }
        int intForUser3 = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_transparency", 0, -2);
        sharedPreferences3 = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
        if (sharedPreferences3 != null) {
            SharedPreferences.Editor editorEdit3 = sharedPreferences3.edit();
            editorEdit3.putInt(SystemUIAnalytics.EDGE_LIGHTING_STATUS_STYLE_TRANSPARENCY, intForUser3);
            editorEdit3.apply();
        }
        int intForUser22 = Settings.System.getIntForUser(context.getContentResolver(), "edge_lighting_thickness", 0, -2);
        sharedPreferences4 = context.getSharedPreferences(SystemUIAnalytics.EDGE_LIGHTING_PREF_NAME, 0);
        if (sharedPreferences4 == null) {
            SharedPreferences.Editor editorEdit4 = sharedPreferences4.edit();
            editorEdit4.putInt(SystemUIAnalytics.EDGE_LIGHTING_STATUS_STYLE_WITDH, intForUser22);
            editorEdit4.apply();
        }
    }

    public static synchronized EdgeLightingSettingManager getInstance(Context context) {
        EdgeLightingSettingManager edgeLightingSettingManager;
        edgeLightingSettingManager = sInstance;
        if (edgeLightingSettingManager == null) {
            edgeLightingSettingManager = new EdgeLightingSettingManager(context);
            sInstance = edgeLightingSettingManager;
        }
        return edgeLightingSettingManager;
    }

    public static void putStringSet(SharedPreferences sharedPreferences, String str, Set set) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putStringSet(str, set);
        editorEdit.apply();
    }

    public static void remove(SharedPreferences sharedPreferences, String str) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.remove(str);
        editorEdit.apply();
    }

    public final void removeBlockListInEnabledEdgeLightingList(Context context, HashMap map) {
        if (this.mAllApplication || map == null) {
            return;
        }
        Iterator it = map.entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (this.mEnableSet.remove((String) ((Map.Entry) it.next()).getKey()) != null) {
                z = true;
            }
        }
        if (z) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
            editorEdit.putInt("version", 1);
            editorEdit.putBoolean("all_application", false);
            editorEdit.putStringSet("enable_list", this.mEnableSet.keySet());
            editorEdit.apply();
        }
    }

    public final void replaceSilentInstalledPackage(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("edge_lighting_settings", 0);
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
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
        editorEdit.putString("update_package_name", null);
        editorEdit.putBoolean("update_package_enable", false);
        editorEdit.apply();
    }

    public final void setDisablePackage(Context context, String str) {
        this.mEnableSet.remove(str);
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
        editorEdit.putInt("version", 1);
        editorEdit.putStringSet("enable_list", this.mEnableSet.keySet());
        editorEdit.apply();
        EdgeLightingPolicyManager.getInstance(context, false).updateEdgeLightingPolicy(context, this.mAllApplication);
    }

    public final void setEnablePackage(Context context, String str) {
        this.mEnableSet.put(str, new EdgeLightingSettingItem(str, -11761985));
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("edge_lighting_settings", 0).edit();
        editorEdit.putInt("version", 1);
        editorEdit.putStringSet("enable_list", this.mEnableSet.keySet());
        editorEdit.apply();
    }

    public final void writeAppNameList(List list) {
        String str;
        if (this.mAllApplication) {
            str = "AllAppsAvailable";
        } else {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() > 0) {
                int size = arrayList.size();
                int i = 0;
                String strM = "";
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    String str2 = (String) obj;
                    strM = "".equals(strM) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, str2) : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, ",", str2);
                }
                str = strM;
            } else {
                str = "";
            }
        }
        Slog.d("EdgeLightingSettingManager", "write default enable app list... " + str);
    }
}
