package com.android.server.enterprise;

import android.content.Context;
import android.os.IBinder;
import android.os.IServiceCreator;
import android.os.ServiceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public abstract class EnterpriseService {
    public static Object sEdmsInstance;
    public static final Map sPolicyServices = new TreeMap();
    public static final List sInitializedList = new ArrayList();
    public static final List sLazyServiceList = new ArrayList();

    public static Object getPolicyService(String str) {
        return sPolicyServices.get(str);
    }

    public static void addPolicyService(String str, Object obj) {
        if (obj == null || !(obj instanceof EnterpriseServiceCallback) || !(obj instanceof IBinder)) {
            throw new IllegalArgumentException("addPolicyService failed because it's not enterprise service");
        }
        try {
            sPolicyServices.put(str, (EnterpriseServiceCallback) obj);
        } catch (Exception unused) {
        }
    }

    public static Map getPolicyServices() {
        return new TreeMap(sPolicyServices);
    }

    public static void addLazySystemService(final String str, EnterpriseServiceCallback enterpriseServiceCallback) {
        addPolicyService(str, enterpriseServiceCallback);
        sLazyServiceList.add(str);
        ServiceManager.addService(str, new IServiceCreator() { // from class: com.android.server.enterprise.EnterpriseService$$ExternalSyntheticLambda0
            public final IBinder createService(Context context) {
                IBinder lambda$addLazySystemService$0;
                lambda$addLazySystemService$0 = EnterpriseService.lambda$addLazySystemService$0(str, context);
                return lambda$addLazySystemService$0;
            }
        });
    }

    public static /* synthetic */ IBinder lambda$addLazySystemService$0(String str, Context context) {
        Log.d("EnterpriseService", "addLazySystemService : " + str);
        return (IBinder) getPolicyService(str);
    }

    public static void wakeUpLazyServices() {
        for (String str : sLazyServiceList) {
            ServiceManager.getService(str);
            EnterpriseServiceCallback enterpriseServiceCallback = (EnterpriseServiceCallback) getPolicyService(str);
            enterpriseServiceCallback.notifyToAddSystemService(str, (IBinder) enterpriseServiceCallback);
        }
    }

    public static void setEdmsInstance(Object obj) {
        sEdmsInstance = obj;
    }

    public static Object getEdmsInstance() {
        return sEdmsInstance;
    }

    public static void addSystemService(String str, EnterpriseServiceCallback enterpriseServiceCallback) {
        IBinder iBinder = (IBinder) enterpriseServiceCallback;
        ServiceManager.addService(str, iBinder);
        enterpriseServiceCallback.notifyToAddSystemService(str, iBinder);
        if (getPolicyServices().containsKey(str)) {
            return;
        }
        addPolicyService(str, enterpriseServiceCallback);
    }

    public static void invokeSystemReadyIfNeeded() {
        for (Map.Entry entry : getPolicyServices().entrySet()) {
            try {
                invokeSystemReadyIfNeeded((EnterpriseServiceCallback) entry.getValue(), (String) entry.getKey());
            } catch (Exception e) {
                Log.e("EnterpriseService", "invokeSystemReadyIfNeeded() failed in" + ((String) entry.getKey()), e);
            }
        }
    }

    public static void invokeSystemReadyIfNeeded(EnterpriseServiceCallback enterpriseServiceCallback, String str) {
        List list = sInitializedList;
        if (list.contains(str)) {
            return;
        }
        enterpriseServiceCallback.systemReady();
        if (list.add(str)) {
            return;
        }
        Log.e("EnterpriseService", "Unexpected Error on calling system ready for " + str);
    }
}
