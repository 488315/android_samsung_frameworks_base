package com.samsung.android.server.dynamicfeature;

import android.content.Intent;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import com.samsung.android.provider.Feature;
import com.samsung.android.provider.SemDynamicFeature;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public final class DFeature {
    public final CircularQueue abComment;
    public final JSONArray abInfo;
    public String abType;
    public String name;
    public String namespace;
    public ArrayList packagenames;
    public boolean property;
    public boolean reboot;
    public ArrayList signatures;
    public String value;
    public int version;

    public final class CircularQueue {
        public int size = 0;
        public int front = 0;
        public int rear = -1;
        public final String[] queue = new String[10];
    }

    public DFeature() {
        String str = InfoBoard.sExecutableBinaryType;
        this.abComment = new CircularQueue();
    }

    public DFeature(JSONObject jSONObject) {
        String str = InfoBoard.sExecutableBinaryType;
        this.abComment = new CircularQueue();
        try {
            this.namespace = jSONObject.getString("namespace");
            this.name = jSONObject.getString("name");
            this.version = jSONObject.getInt("version");
            this.reboot = jSONObject.getBoolean("reboot");
            this.property = jSONObject.getBoolean("property");
            if (jSONObject.has("value")) {
                this.value = jSONObject.getString("value");
            }
            if (!jSONObject.has("abTestTypes") || jSONObject.isNull("abTestTypes")) {
                return;
            }
            this.abInfo = jSONObject.getJSONArray("abTestTypes");
        } catch (Exception e) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Feature creation failed."), "dynamicfeature_Feature");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0043, code lost:

       if (r3.version == r1.version) goto L27;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isSame(java.util.ArrayList r6, java.util.ArrayList r7) {
        /*
            r0 = 0
            if (r6 == 0) goto L48
            int r1 = r6.size()
            if (r1 != 0) goto La
            goto L48
        La:
            int r1 = r6.size()
            int r2 = r7.size()
            if (r1 == r2) goto L15
            return r0
        L15:
            java.util.Iterator r6 = r6.iterator()
        L19:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L46
            java.lang.Object r1 = r6.next()
            com.samsung.android.server.dynamicfeature.DFeature r1 = (com.samsung.android.server.dynamicfeature.DFeature) r1
            java.util.Iterator r2 = r7.iterator()
        L29:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L45
            java.lang.Object r3 = r2.next()
            com.samsung.android.server.dynamicfeature.DFeature r3 = (com.samsung.android.server.dynamicfeature.DFeature) r3
            java.lang.String r4 = r1.namespace
            java.lang.String r5 = r1.name
            boolean r4 = r3.isThis(r4, r5)
            if (r4 == 0) goto L29
            int r2 = r3.version
            int r1 = r1.version
            if (r2 == r1) goto L19
        L45:
            return r0
        L46:
            r6 = 1
            return r6
        L48:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.dynamicfeature.DFeature.isSame(java.util.ArrayList,"
                    + " java.util.ArrayList):boolean");
    }

    public final String dump() {
        StringBuilder sb = new StringBuilder("dump feature item ");
        sb.append(
                " namespace:"
                        + this.namespace
                        + " name:"
                        + this.name
                        + " version:"
                        + this.version
                        + " reboot:"
                        + this.reboot
                        + " value:"
                        + this.value
                        + " abType: "
                        + this.abType
                        + " abInfo:"
                        + this.abInfo
                        + " property:"
                        + this.property);
        if (this.packagenames != null) {
            sb.append(" packages :");
            Iterator it = this.packagenames.iterator();
            while (it.hasNext()) {
                sb.append(" " + ((String) it.next()));
            }
        }
        if (this.signatures != null) {
            sb.append(" signatures :");
            Iterator it2 = this.signatures.iterator();
            while (it2.hasNext()) {
                sb.append(" " + ((String) it2.next()));
            }
        }
        Slog.d("dynamicfeature_Feature", sb.toString());
        return sb.toString();
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + dump());
        CircularQueue circularQueue = this.abComment;
        if (circularQueue != null) {
            String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "   ");
            int i = circularQueue.front;
            for (int i2 = 0; i2 < circularQueue.size; i2++) {
                printWriter.println(m$1 + "[" + i2 + "] " + circularQueue.queue[i]);
                i = (i + 1) % 10;
            }
        }
    }

    public final JSONArray getCommentJSONArray() {
        CircularQueue circularQueue = this.abComment;
        circularQueue.getClass();
        JSONArray jSONArray = new JSONArray();
        synchronized (circularQueue) {
            for (int i = 0; i < circularQueue.size; i++) {
                try {
                    String[] strArr = circularQueue.queue;
                    int i2 = circularQueue.front;
                    String str = strArr[i2];
                    circularQueue.front = (i2 + 1) % 10;
                    jSONArray.put(str);
                } catch (Throwable th) {
                    throw th;
                }
            }
            circularQueue.size = 0;
        }
        return jSONArray;
    }

    public final String getIntentAction() {
        return BootReceiver$$ExternalSyntheticOutline0.m(
                "com.sec.df.changed.", this.namespace, ".", this.name);
    }

    public final String getPersistPropertyKey() {
        return "persist.sys.df." + this.namespace + "." + this.name;
    }

    public final boolean isThis(String str, String str2) {
        return this.namespace.equals(str) && this.name.equals(str2);
    }

    public final void loadCargo(Intent intent, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (!z) {
            arrayList.add(new Feature(this.name, this.value, this.abType != null));
        }
        intent.putExtra(
                "PROPERTY_CARGO",
                (Parcelable) new SemDynamicFeature.Properties(this.namespace, arrayList));
    }

    public final void setAbTypeAndValue(String str, String str2, String str3) {
        JSONArray jSONArray = this.abInfo;
        if (jSONArray == null || jSONArray.length() == 0 || str.length() == 0) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "ABInfo is NULL ! type : ", str, "dynamicfeature_Feature");
            return;
        }
        this.abType = str;
        for (int i = 0; i < this.abInfo.length(); i++) {
            try {
                JSONObject jSONObject = this.abInfo.getJSONObject(i);
                if (jSONObject.has("type") && str.compareTo(jSONObject.getString("type")) == 0) {
                    this.value = jSONObject.getString("value");
                    if (str2.isEmpty() || str3.isEmpty()) {
                        return;
                    }
                    setPackageNames(str2);
                    setSignatures(str3);
                    return;
                }
            } catch (Exception e) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("setAbType : "), "dynamicfeature_Feature");
            }
        }
        Slog.e("dynamicfeature_Feature", "Fail to find item for ".concat(str));
        dump();
    }

    public final void setPackageNames(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.packagenames = new ArrayList(Arrays.asList(str.split(",,")));
    }

    public final void setSignatures(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.signatures = new ArrayList(Arrays.asList(str.split(",,")));
    }
}
