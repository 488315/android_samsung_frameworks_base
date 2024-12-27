package com.android.server.notification.edgelighting.policy;

import android.os.Debug;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import com.samsung.android.edge.EdgeLightingPolicyInfo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EdgeLightingPolicyRepository {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final HashMap mRepository = new HashMap();

    public final void dump(PrintWriter printWriter, String str) {
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                printWriter, "-EdgeLightingPolicyInfo(", str, ") start");
        synchronized (this.mRepository) {
            try {
                Iterator it = this.mRepository.entrySet().iterator();
                while (it.hasNext()) {
                    printWriter.println(
                            "  "
                                    + ((EdgeLightingPolicyInfo) ((Map.Entry) it.next()).getValue())
                                            .toString());
                }
                printWriter.println("");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final EdgeLightingPolicyInfo getPolicyInfoWithPackageName(String str) {
        EdgeLightingPolicyInfo edgeLightingPolicyInfo;
        synchronized (this.mRepository) {
            edgeLightingPolicyInfo = (EdgeLightingPolicyInfo) this.mRepository.get(str);
        }
        return edgeLightingPolicyInfo;
    }

    public final int size() {
        int size;
        synchronized (this.mRepository) {
            size = this.mRepository.size();
        }
        return size;
    }

    public final void updatePolicy(EdgeLightingPolicyInfo edgeLightingPolicyInfo) {
        if (DEBUG) {
            Slog.d(
                    "EdgeLightingPolicyRepository",
                    "updatePolicy " + edgeLightingPolicyInfo.toString());
        }
        synchronized (this.mRepository) {
            this.mRepository.put(edgeLightingPolicyInfo.packageName, edgeLightingPolicyInfo);
        }
    }
}
