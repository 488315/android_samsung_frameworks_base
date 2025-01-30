package com.android.server.biometrics.sensors;

import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.Handler;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Pair;
import android.util.Slog;
import com.android.server.ServiceThread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public abstract class BiometricServiceRegistry {
    public volatile List mAllProps;
    public final Supplier mBiometricServiceSupplier;
    public final RemoteCallbackList mRegisteredCallbacks = new RemoteCallbackList();
    public volatile List mServiceProviders;

    public abstract void invokeRegisteredCallback(IInterface iInterface, List list);

    public abstract void registerService(IBiometricService iBiometricService, SensorPropertiesInternal sensorPropertiesInternal);

    public BiometricServiceRegistry(Supplier supplier) {
        this.mBiometricServiceSupplier = supplier;
    }

    public void registerAll(final Supplier supplier) {
        ServiceThread serviceThread = new ServiceThread("BiometricServiceRegistry", 10, true);
        serviceThread.start();
        new Handler(serviceThread.getLooper()).post(new Runnable() { // from class: com.android.server.biometrics.sensors.BiometricServiceRegistry$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BiometricServiceRegistry.this.lambda$registerAll$0(supplier);
            }
        });
        serviceThread.quitSafely();
    }

    /* renamed from: registerAllInBackground, reason: merged with bridge method [inline-methods] */
    public void lambda$registerAll$0(Supplier supplier) {
        List list = (List) supplier.get();
        if (list == null) {
            list = new ArrayList();
        }
        IBiometricService iBiometricService = (IBiometricService) this.mBiometricServiceSupplier.get();
        if (iBiometricService == null) {
            throw new IllegalStateException("biometric service cannot be null");
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List sensorProperties = ((BiometricServiceProvider) it.next()).getSensorProperties();
            Iterator it2 = sensorProperties.iterator();
            while (it2.hasNext()) {
                registerService(iBiometricService, (SensorPropertiesInternal) it2.next());
            }
            arrayList.addAll(sensorProperties);
        }
        finishRegistration(list, arrayList);
    }

    public final synchronized void finishRegistration(List list, List list2) {
        this.mServiceProviders = Collections.unmodifiableList(list);
        this.mAllProps = Collections.unmodifiableList(list2);
        broadcastAllAuthenticatorsRegistered();
    }

    public synchronized void addAllRegisteredCallback(IInterface iInterface) {
        if (iInterface == null) {
            Slog.e("BiometricServiceRegistry", "addAllRegisteredCallback, callback is null");
            return;
        }
        boolean register = this.mRegisteredCallbacks.register(iInterface);
        boolean z = this.mServiceProviders != null;
        if (register && z) {
            broadcastAllAuthenticatorsRegistered();
        } else if (!register) {
            Slog.e("BiometricServiceRegistry", "addAllRegisteredCallback failed to register callback");
        }
    }

    public final synchronized void broadcastAllAuthenticatorsRegistered() {
        RemoteCallbackList remoteCallbackList;
        int beginBroadcast = this.mRegisteredCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            IInterface broadcastItem = this.mRegisteredCallbacks.getBroadcastItem(i);
            try {
                try {
                    invokeRegisteredCallback(broadcastItem, this.mAllProps);
                    remoteCallbackList = this.mRegisteredCallbacks;
                } catch (RemoteException e) {
                    Slog.e("BiometricServiceRegistry", "Remote exception in broadcastAllAuthenticatorsRegistered", e);
                    remoteCallbackList = this.mRegisteredCallbacks;
                }
                remoteCallbackList.unregister(broadcastItem);
            } catch (Throwable th) {
                this.mRegisteredCallbacks.unregister(broadcastItem);
                throw th;
            }
        }
        this.mRegisteredCallbacks.finishBroadcast();
    }

    public List getProviders() {
        return this.mServiceProviders != null ? this.mServiceProviders : Collections.emptyList();
    }

    public BiometricServiceProvider getProviderForSensor(int i) {
        if (this.mServiceProviders == null) {
            return null;
        }
        for (BiometricServiceProvider biometricServiceProvider : this.mServiceProviders) {
            if (biometricServiceProvider.containsSensor(i)) {
                return biometricServiceProvider;
            }
        }
        return null;
    }

    public Pair getSingleProvider() {
        String str;
        if (this.mAllProps == null || this.mAllProps.isEmpty()) {
            Slog.e("BiometricServiceRegistry", "No sensors found");
            return null;
        }
        try {
            if (this.mAllProps.size() > 1) {
                Slog.e("BiometricServiceRegistry", "getSingleProvider() called but multiple sensors present: " + this.mAllProps.size());
            }
            int i = ((SensorPropertiesInternal) this.mAllProps.get(0)).sensorId;
            BiometricServiceProvider providerForSensor = getProviderForSensor(i);
            if (providerForSensor != null) {
                return new Pair(Integer.valueOf(i), providerForSensor);
            }
            Slog.e("BiometricServiceRegistry", "Single sensor: " + i + ", but provider not found");
            return null;
        } catch (NullPointerException e) {
            if (this.mAllProps == null) {
                str = "mAllProps: null";
            } else {
                str = "mAllProps.size(): " + this.mAllProps.size();
            }
            Slog.e("BiometricServiceRegistry", "This shouldn't happen. " + str, e);
            throw e;
        }
    }

    public List getAllProperties() {
        return this.mAllProps != null ? this.mAllProps : Collections.emptyList();
    }
}
