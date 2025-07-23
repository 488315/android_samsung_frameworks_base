package android.telephony;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SemTelephonyManager;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.util.Log;
import com.android.internal.telephony.ISemTelephonyRegistry;
import com.android.internal.telephony.ITiantongSatelliteChangeListener;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public class SemTelephonyRegistryManager {
    private static final String TAG = "SemTelephonyRegistryManager";
    private static ISemTelephonyRegistry sRegistry;
    private final Context mContext;
    private final ConcurrentHashMap<SemTelephonyManager.SemSatelliteStateListener, ITiantongSatelliteChangeListener> mTiantongSatelliteChangeListenerMap = new ConcurrentHashMap<>();

    public SemTelephonyRegistryManager(Context context) {
        this.mContext = context;
        if (sRegistry == null) {
            sRegistry = ISemTelephonyRegistry.Stub.asInterface(ServiceManager.getService("sem.telephony.registry"));
        }
    }

    public void addTiantongSatelliteChangeListener(Executor executor, SemTelephonyManager.SemSatelliteStateListener semSatelliteStateListener) {
        Objects.requireNonNull(executor, "Executor should be non-null.");
        Objects.requireNonNull(semSatelliteStateListener, "Listener should be non-null.");
        Log.d(TAG, "addTiantongSatelliteChangeListener");
        if (this.mTiantongSatelliteChangeListenerMap.get(semSatelliteStateListener) != null) {
            Log.e(TAG, "registerCarrierConfigChangeListener: listener already present");
            return;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, executor, semSatelliteStateListener);
        try {
            sRegistry.addTiantongSatelliteChangeListener(anonymousClass1, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            this.mTiantongSatelliteChangeListenerMap.put(semSatelliteStateListener, anonymousClass1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.SemTelephonyRegistryManager$1, reason: invalid class name */
    class AnonymousClass1 extends ITiantongSatelliteChangeListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ SemTelephonyManager.SemSatelliteStateListener val$listener;

        AnonymousClass1(SemTelephonyRegistryManager semTelephonyRegistryManager, Executor executor, SemTelephonyManager.SemSatelliteStateListener semSatelliteStateListener) {
            this.val$executor = executor;
            this.val$listener = semSatelliteStateListener;
        }

        @Override // com.android.internal.telephony.ITiantongSatelliteChangeListener
        public void onSemSatelliteServiceStateChanged(final int i, final int i2, final SemSatelliteServiceState semSatelliteServiceState) {
            Log.d(SemTelephonyRegistryManager.TAG, "onSemSatelliteServiceStateChanged call in ITiantongSatelliteChangeListener callback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final SemTelephonyManager.SemSatelliteStateListener semSatelliteStateListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.telephony.SemTelephonyRegistryManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemTelephonyManager.SemSatelliteStateListener.this.onSemSatelliteServiceStateChanged(i, i2, semSatelliteServiceState);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.internal.telephony.ITiantongSatelliteChangeListener
        public void onSemSatelliteSignalStrengthChanged(final int i, final int i2, final SemSatelliteSignalStrength semSatelliteSignalStrength) {
            Log.d(SemTelephonyRegistryManager.TAG, "onSemSatelliteSignalStrengthChanged call in ITiantongSatelliteChangeListener callback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final SemTelephonyManager.SemSatelliteStateListener semSatelliteStateListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.telephony.SemTelephonyRegistryManager$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SemTelephonyManager.SemSatelliteStateListener.this.onSemSatelliteSignalStrengthChanged(i, i2, semSatelliteSignalStrength);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeTiantongSatelliteChangedListener(SemTelephonyManager.SemSatelliteStateListener semSatelliteStateListener) {
        Objects.requireNonNull(semSatelliteStateListener, "Listener should be non-null.");
        if (this.mTiantongSatelliteChangeListenerMap.get(semSatelliteStateListener) == null) {
            Log.e(TAG, "removeTiantongSatelliteChangedListener: listener was not present");
            return;
        }
        try {
            sRegistry.removeTiantongSatelliteChangeListener(this.mTiantongSatelliteChangeListenerMap.get(semSatelliteStateListener), this.mContext.getOpPackageName());
            this.mTiantongSatelliteChangeListenerMap.remove(semSatelliteStateListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) {
        if (!SubscriptionManager.isValidPhoneId(i)) {
            Log.e(TAG, "notifySemSatelliteServiceStateChanged, ignored: invalid phoneId " + i);
        } else {
            try {
                sRegistry.notifySemSatelliteServiceStateChanged(i, i2, semSatelliteServiceState);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) {
        if (!SubscriptionManager.isValidPhoneId(i)) {
            Log.e(TAG, "notifySemSatelliteSignalStrengthChanged, ignored: invalid slotIndex " + i);
        } else {
            try {
                sRegistry.notifySemSatelliteSignalStrengthChanged(i, i2, semSatelliteSignalStrength);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
