package android.telephony;

import android.app.PropertyInvalidatedCache;
import android.content.Context;
import android.content.ContextParams;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telecom.PhoneAccountHandle;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.telephony.satellite.SemSatelliteState;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.ISemPhoneSubInfo;
import com.android.internal.telephony.ISemTelephony;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.FunctionalUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class SemTelephonyManager {
    private static final String CACHE_KEY_PHONE_ACCOUNT_TO_SUBID = "cache_key.telephony.phone_account_to_subid";
    private static final int CACHE_MAX_SIZE = 4;
    public static final int SEM_SET_SATELLITE_RESULT_INVALID_STATE = 2;
    public static final int SEM_SET_SATELLITE_RESULT_MODEM_ERROR = 1;
    public static final int SEM_SET_SATELLITE_RESULT_RADIOS_OFF_ERROR = 3;
    public static final int SEM_SET_SATELLITE_RESULT_SUCCESS = 0;
    private static final String TAG = "SemTelephonyManager";
    private static ISemPhoneSubInfo sISemPhoneSubInfo;
    private static ISemTelephony sISemTelephony;
    private static ITelephony sITelephony;
    private final Context mContext;
    private PropertyInvalidatedCache<PhoneAccountHandle, Integer> mPhoneAccountHandleToSubIdCache;
    private final int mSubId;
    private static final Object sCacheLock = new Object();
    private static final DeathRecipient sServiceDeath = new DeathRecipient();
    private static SemTelephonyManager sInstance = new SemTelephonyManager();

    public interface SemSatelliteStateListener {
        default void onSemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) {
        }

        default void onSemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetSatelliteModeResult {
    }

    public SemTelephonyManager(Context context) {
        this(context, Integer.MAX_VALUE);
    }

    public SemTelephonyManager(Context context, int i) {
        this.mPhoneAccountHandleToSubIdCache = new PropertyInvalidatedCache<PhoneAccountHandle, Integer>(4, "cache_key.telephony.phone_account_to_subid") { // from class: android.telephony.SemTelephonyManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PhoneAccountHandle phoneAccountHandle) {
                try {
                    ITelephony iTelephony = SemTelephonyManager.getITelephony();
                    if (iTelephony != null) {
                        return Integer.valueOf(iTelephony.getSubIdForPhoneAccountHandle(phoneAccountHandle, SemTelephonyManager.this.mContext.getOpPackageName(), SemTelephonyManager.this.mContext.getAttributionTag()));
                    }
                    return -1;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }
        };
        this.mSubId = i;
        this.mContext = mergeAttributionAndRenouncedPermissions(context.getApplicationContext(), context);
    }

    private SemTelephonyManager() {
        this.mPhoneAccountHandleToSubIdCache = new PropertyInvalidatedCache<PhoneAccountHandle, Integer>(4, "cache_key.telephony.phone_account_to_subid") { // from class: android.telephony.SemTelephonyManager.1
            @Override // android.app.PropertyInvalidatedCache
            public Integer recompute(PhoneAccountHandle phoneAccountHandle) {
                try {
                    ITelephony iTelephony = SemTelephonyManager.getITelephony();
                    if (iTelephony != null) {
                        return Integer.valueOf(iTelephony.getSubIdForPhoneAccountHandle(phoneAccountHandle, SemTelephonyManager.this.mContext.getOpPackageName(), SemTelephonyManager.this.mContext.getAttributionTag()));
                    }
                    return -1;
                } catch (RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }
        };
        this.mContext = null;
        this.mSubId = -1;
    }

    private Context mergeAttributionAndRenouncedPermissions(Context context, Context context2) {
        if (context == null) {
            return context2;
        }
        Context contextCreateAttributionContext = !Objects.equals(context2.getAttributionTag(), context.getAttributionTag()) ? context.createAttributionContext(context2.getAttributionTag()) : context;
        Set<String> renouncedPermissions = context2.getAttributionSource().getRenouncedPermissions();
        if (renouncedPermissions.isEmpty()) {
            return contextCreateAttributionContext;
        }
        if (context.getParams() != null) {
            return contextCreateAttributionContext.createContext(new ContextParams.Builder(context.getParams()).setRenouncedPermissions(renouncedPermissions).build());
        }
        return contextCreateAttributionContext.createContext(new ContextParams.Builder().setRenouncedPermissions(renouncedPermissions).build());
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context != null) {
            return context.getOpPackageName();
        }
        ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getCurrentPackageName();
        } catch (RemoteException | NullPointerException unused) {
            return null;
        }
    }

    private String getAttributionTag() {
        Context context = this.mContext;
        if (context != null) {
            return context.getAttributionTag();
        }
        return null;
    }

    private Set<String> getRenouncedPermissions() {
        Context context = this.mContext;
        if (context != null) {
            return context.getAttributionSource().getRenouncedPermissions();
        }
        return Collections.EMPTY_SET;
    }

    public SemTelephonyManager createForSubscriptionId(int i) {
        return new SemTelephonyManager(this.mContext, i);
    }

    public SemTelephonyManager createForPhoneAccountHandle(PhoneAccountHandle phoneAccountHandle) {
        int subscriptionId = getSubscriptionId(phoneAccountHandle);
        if (SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
            return new SemTelephonyManager(this.mContext, subscriptionId);
        }
        return null;
    }

    public int getSubscriptionId(PhoneAccountHandle phoneAccountHandle) {
        return this.mPhoneAccountHandleToSubIdCache.query(phoneAccountHandle).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ITelephony getITelephony() {
        if (sITelephony == null) {
            ITelephony iTelephonyAsInterface = ITelephony.Stub.asInterface(TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sITelephony == null && iTelephonyAsInterface != null) {
                    try {
                        sITelephony = iTelephonyAsInterface;
                        iTelephonyAsInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
                        sITelephony = null;
                    }
                }
            }
        }
        return sITelephony;
    }

    private ISemTelephony getISemTelephony() {
        if (sISemTelephony == null) {
            ISemTelephony iSemTelephonyAsInterface = ISemTelephony.Stub.asInterface(ServiceManager.getService("isemtelephony"));
            synchronized (sCacheLock) {
                if (sISemTelephony == null && iSemTelephonyAsInterface != null) {
                    try {
                        sISemTelephony = iSemTelephonyAsInterface;
                        iSemTelephonyAsInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
                        sISemTelephony = null;
                    }
                }
            }
        }
        return sISemTelephony;
    }

    public void registerSemSatelliteStateListener(Executor executor, SemSatelliteStateListener semSatelliteStateListener) {
        Objects.requireNonNull(executor, "Executor should be non-null.");
        Objects.requireNonNull(semSatelliteStateListener, "Listener should be non-null.");
        SemTelephonyRegistryManager semTelephonyRegistryManager = (SemTelephonyRegistryManager) this.mContext.getSystemService(SemTelephonyRegistryManager.class);
        if (semTelephonyRegistryManager == null) {
            throw new IllegalStateException("Samsung Telephony registry service is null");
        }
        semTelephonyRegistryManager.addTiantongSatelliteChangeListener(executor, semSatelliteStateListener);
    }

    public void unregisterSemSatelliteStateListener(SemSatelliteStateListener semSatelliteStateListener) {
        Objects.requireNonNull(semSatelliteStateListener, "Listener should be non-null.");
        SemTelephonyRegistryManager semTelephonyRegistryManager = (SemTelephonyRegistryManager) this.mContext.getSystemService(SemTelephonyRegistryManager.class);
        if (semTelephonyRegistryManager == null) {
            throw new IllegalStateException("Samsung Telephony registry service is null");
        }
        semTelephonyRegistryManager.removeTiantongSatelliteChangedListener(semSatelliteStateListener);
    }

    private static class DeathRecipient implements IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            SemTelephonyManager.resetServiceCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetServiceCache() {
        synchronized (sCacheLock) {
            ITelephony iTelephony = sITelephony;
            if (iTelephony != null) {
                iTelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sITelephony = null;
            }
            ISemTelephony iSemTelephony = sISemTelephony;
            if (iSemTelephony != null) {
                iSemTelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISemTelephony = null;
            }
            ISemPhoneSubInfo iSemPhoneSubInfo = sISemPhoneSubInfo;
            if (iSemPhoneSubInfo != null) {
                iSemPhoneSubInfo.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISemPhoneSubInfo = null;
            }
        }
    }

    static ISemPhoneSubInfo getSemSubscriberInfoService() {
        if (sISemPhoneSubInfo == null) {
            ISemPhoneSubInfo iSemPhoneSubInfoAsInterface = ISemPhoneSubInfo.Stub.asInterface(ServiceManager.getService("isemphonesubinfo"));
            synchronized (sCacheLock) {
                if (sISemPhoneSubInfo == null && iSemPhoneSubInfoAsInterface != null) {
                    try {
                        sISemPhoneSubInfo = iSemPhoneSubInfoAsInterface;
                        iSemPhoneSubInfoAsInterface.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (Exception unused) {
                        sISemPhoneSubInfo = null;
                    }
                }
            }
        }
        return sISemPhoneSubInfo;
    }

    public String semGetSatelliteImei() {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "semGetSatelliteImei(): ISemTelephony instance is NULL");
                throw new IllegalStateException("SemTelephony service not available.");
            }
            return iSemTelephony.semGetSatelliteImei(getOpPackageName(), getAttributionTag());
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetSatelliteImei() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public void semRequestSatelliteMode(int i, boolean z, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "semRequestSatelliteMode() invalid telephony");
                executor.execute(new Runnable() { // from class: android.telephony.SemTelephonyManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.SemTelephonyManager$$ExternalSyntheticLambda0
                            @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                            public final void runOrThrow() {
                                consumer.accept(2);
                            }
                        });
                    }
                });
            } else {
                iSemTelephony.semRequestSatelliteMode(i, z, new AnonymousClass2(this, executor, consumer));
            }
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semRequestSatelliteMode is fail. " + e);
            executor.execute(new Runnable() { // from class: android.telephony.SemTelephonyManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.SemTelephonyManager$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(2);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.SemTelephonyManager$2, reason: invalid class name */
    class AnonymousClass2 extends IIntegerConsumer.Stub {
        final /* synthetic */ Consumer val$callback;
        final /* synthetic */ Executor val$executor;

        AnonymousClass2(SemTelephonyManager semTelephonyManager, Executor executor, Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            Executor executor = this.val$executor;
            final Consumer consumer = this.val$callback;
            executor.execute(new Runnable() { // from class: android.telephony.SemTelephonyManager$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.SemTelephonyManager$2$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            consumer.accept(Integer.valueOf(i));
                        }
                    });
                }
            });
        }
    }

    public SemSatelliteState semGetSatelliteState(int i) {
        try {
            ISemTelephony iSemTelephony = getISemTelephony();
            if (iSemTelephony != null) {
                return iSemTelephony.semGetSatelliteState(i);
            }
            return null;
        } catch (RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "semGetSatelliteState is fail. " + e);
            return null;
        }
    }
}
