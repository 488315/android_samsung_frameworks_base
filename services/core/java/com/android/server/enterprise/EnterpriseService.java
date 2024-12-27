package com.android.server.enterprise;

import android.os.IBinder;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EnterpriseService {
    public static Object sEdmsInstance;
    public static final Map sPolicyServices = new TreeMap();
    public static final List sInitializedList = new ArrayList();
    public static final List sLazyServiceList = new ArrayList();

    public static void addPolicyService(String str, Object obj) {
        if (obj == null
                || !(obj instanceof EnterpriseServiceCallback)
                || !(obj instanceof IBinder)) {
            throw new IllegalArgumentException(
                    "addPolicyService failed because it's not enterprise service");
        }
        try {
            ((TreeMap) sPolicyServices).put(str, (EnterpriseServiceCallback) obj);
        } catch (Exception unused) {
        }
    }

    public static Object getPolicyService(String str) {
        return ((TreeMap) sPolicyServices).get(str);
    }

    public static Map getPolicyServices() {
        return new TreeMap(sPolicyServices);
    }

    public static void invokeSystemReadyIfNeeded(
            EnterpriseServiceCallback enterpriseServiceCallback, String str) {
        List list = sInitializedList;
        if (((ArrayList) list).contains(str)) {
            return;
        }
        enterpriseServiceCallback.systemReady();
        if (((ArrayList) list).add(str)) {
            return;
        }
        StorageManagerService$$ExternalSyntheticOutline0.m(
                "Unexpected Error on calling system ready for ", str, "EnterpriseService");
    }
}
