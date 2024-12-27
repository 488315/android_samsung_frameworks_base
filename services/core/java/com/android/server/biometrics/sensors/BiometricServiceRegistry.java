package com.android.server.biometrics.sensors;

import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Pair;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BiometricServiceRegistry {
    public volatile List mAllProps;
    public final Supplier mBiometricServiceSupplier;
    public final RemoteCallbackList mRegisteredCallbacks = new RemoteCallbackList();
    public volatile List mServiceProviders;

    public BiometricServiceRegistry(Supplier supplier) {
        this.mBiometricServiceSupplier = supplier;
    }

    public final synchronized void addAllRegisteredCallback(IInterface iInterface) {
        if (iInterface == null) {
            Slog.e("BiometricServiceRegistry", "addAllRegisteredCallback, callback is null");
            return;
        }
        boolean register = this.mRegisteredCallbacks.register(iInterface);
        boolean z = this.mServiceProviders != null;
        if (register && z) {
            broadcastAllAuthenticatorsRegistered();
        } else if (!register) {
            Slog.e(
                    "BiometricServiceRegistry",
                    "addAllRegisteredCallback failed to register callback");
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
                } catch (Throwable th) {
                    this.mRegisteredCallbacks.unregister(broadcastItem);
                    throw th;
                }
            } catch (RemoteException e) {
                Slog.e(
                        "BiometricServiceRegistry",
                        "Remote exception in broadcastAllAuthenticatorsRegistered",
                        e);
                remoteCallbackList = this.mRegisteredCallbacks;
            }
            remoteCallbackList.unregister(broadcastItem);
        }
        this.mRegisteredCallbacks.finishBroadcast();
    }

    public final List getAllProperties() {
        return this.mAllProps != null ? this.mAllProps : Collections.emptyList();
    }

    public final BiometricServiceProvider getProviderForSensor(int i) {
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

    public final List getProviders() {
        return this.mServiceProviders != null ? this.mServiceProviders : Collections.emptyList();
    }

    public final Pair getSingleProvider() {
        String str;
        if (this.mAllProps == null || this.mAllProps.isEmpty()) {
            Slog.e("BiometricServiceRegistry", "No sensors found");
            return null;
        }
        try {
            if (this.mAllProps.size() > 1) {
                Slog.e(
                        "BiometricServiceRegistry",
                        "getSingleProvider() called but multiple sensors present: "
                                + this.mAllProps.size());
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

    public abstract void invokeRegisteredCallback(IInterface iInterface, List list);

    public void registerAllInBackground(Supplier supplier) {
        List<BiometricServiceProvider> list = (List) supplier.get();
        if (list == null) {
            list = new ArrayList();
        }
        IBiometricService iBiometricService =
                (IBiometricService) this.mBiometricServiceSupplier.get();
        if (iBiometricService == null) {
            throw new IllegalStateException("biometric service cannot be null");
        }
        ArrayList arrayList = new ArrayList();
        for (BiometricServiceProvider biometricServiceProvider : list) {
            Slog.d(
                    "BiometricServiceRegistry",
                    "registerAllInBackground: " + biometricServiceProvider);
            if (biometricServiceProvider != null) {
                List sensorProperties = biometricServiceProvider.getSensorProperties();
                Iterator it = ((ArrayList) sensorProperties).iterator();
                while (it.hasNext()) {
                    SensorPropertiesInternal sensorPropertiesInternal =
                            (SensorPropertiesInternal) it.next();
                    if (sensorPropertiesInternal != null) {
                        registerService(iBiometricService, sensorPropertiesInternal);
                    }
                }
                arrayList.addAll(sensorProperties);
            }
        }
        synchronized (this) {
            this.mServiceProviders = Collections.unmodifiableList(list);
            this.mAllProps = Collections.unmodifiableList(arrayList);
            broadcastAllAuthenticatorsRegistered();
        }
    }

    public abstract void registerService(
            IBiometricService iBiometricService, SensorPropertiesInternal sensorPropertiesInternal);
}
