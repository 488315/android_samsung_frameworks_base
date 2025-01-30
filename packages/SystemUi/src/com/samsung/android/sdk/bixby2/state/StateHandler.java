package com.samsung.android.sdk.bixby2.state;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.samsung.android.sdk.bixby2.AppMetaInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StateHandler {
    public static StateHandler mInstance;
    public Callback mCallback = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Callback {
        public List<String> getUsedPermissionsWhenAppStateRequested() {
            return null;
        }

        public abstract String onAppStateRequested();

        public String onCapsuleIdRequested() {
            return null;
        }
    }

    private StateHandler() {
    }

    public static void adjustConceptsDueToPermissions(List list, JSONObject jSONObject) {
        try {
            if (((ArrayList) list).isEmpty() || !jSONObject.has("concepts")) {
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray("concepts");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                if (jSONObject2.has("values")) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("deniedPermissions", new JSONArray((Collection) list));
                    JSONArray jSONArray2 = new JSONArray();
                    jSONArray2.put(jSONObject3);
                    jSONObject2.put("values", jSONArray2);
                    return;
                }
            }
        } catch (Exception e) {
            AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("removeValuesInJSONObject exception : "), "StateHandler");
        }
    }

    public static List getClientDeniedPermissions(List list, Context context, Bundle bundle) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = bundle != null ? new HashSet(bundle.getStringArrayList("SUPPORTED_PERMISSIONS")) : null;
            Log.i("StateHandler", "supportedPermissionsInClient = " + hashSet);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (hashSet == null || hashSet.contains(str)) {
                    if (!(packageManager.checkPermission(str, "com.samsung.android.bixby.agent") == 0)) {
                        arrayList.add(str);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("exception : "), "StateHandler");
            return null;
        }
    }

    public static AppMetaInfo getDefaultAppMetaInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            String packageName = context.getPackageName();
            Bundle bundle = packageManager.getApplicationInfo(packageName, 128).metaData;
            if (bundle != null && bundle.containsKey("com.samsung.android.bixby.capsuleId")) {
                return new AppMetaInfo(bundle.getString("com.samsung.android.bixby.capsuleId"), packageManager.getPackageInfo(packageName, 0).versionCode);
            }
            Log.e("StateHandler", "Can't get Capsule ID from Meta data:" + packageName);
            return null;
        } catch (PackageManager.NameNotFoundException | NullPointerException e) {
            AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("Failed to get Meta data info: "), "StateHandler");
            return null;
        }
    }

    public static synchronized StateHandler getInstance() {
        StateHandler stateHandler;
        synchronized (StateHandler.class) {
            if (mInstance == null) {
                mInstance = new StateHandler();
            }
            stateHandler = mInstance;
        }
        return stateHandler;
    }
}
