package android.telephony;

import android.annotation.SystemApi;
import android.compat.Compatibility;
import android.content.Context;
import android.media.AudioPort$$ExternalSyntheticLambda0;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyRegistryManager;
import android.telephony.emergency.EmergencyNumber;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SatelliteStateChangeListener;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.telephony.ICarrierConfigChangeListener;
import com.android.internal.telephony.ICarrierPrivilegesCallback;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.ISatelliteStateChangeListener;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.util.FunctionalUtils;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@SystemApi
/* loaded from: classes4.dex */
public class TelephonyRegistryManager {
    private static final long LISTEN_CODE_CHANGE = 147600208;
    public static final int SIM_ACTIVATION_TYPE_DATA = 1;
    public static final int SIM_ACTIVATION_TYPE_VOICE = 0;
    private static final String TAG = "TelephonyRegistryManager";
    private static ITelephonyRegistry sRegistry;
    private final Context mContext;
    private static final Map<SatelliteStateChangeListener, WeakReference<SatelliteStateChangeListenerWrapper>> sSatelliteStateChangeListeners = new ArrayMap();
    private static final WeakHashMap<TelephonyManager.CarrierPrivilegesCallback, WeakReference<CarrierPrivilegesCallbackWrapper>> sCarrierPrivilegeCallbacks = new WeakHashMap<>();
    private final ConcurrentHashMap<SubscriptionManager.OnSubscriptionsChangedListener, IOnSubscriptionsChangedListener> mSubscriptionChangedListenerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<SubscriptionManager.OnOpportunisticSubscriptionsChangedListener, IOnSubscriptionsChangedListener> mOpportunisticSubscriptionChangedListenerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<CarrierConfigManager.CarrierConfigChangeListener, ICarrierConfigChangeListener> mCarrierConfigChangeListenerMap = new ConcurrentHashMap<>();

    public TelephonyRegistryManager(Context context) {
        this.mContext = context;
        if (sRegistry == null) {
            sRegistry = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
        }
    }

    public void addOnSubscriptionsChangedListener(SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener, Executor executor) {
        if (this.mSubscriptionChangedListenerMap.get(onSubscriptionsChangedListener) != null) {
            Log.d(TAG, "addOnSubscriptionsChangedListener listener already present");
            return;
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(this, executor, onSubscriptionsChangedListener);
        this.mSubscriptionChangedListenerMap.put(onSubscriptionsChangedListener, anonymousClass1);
        try {
            sRegistry.addOnSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), anonymousClass1);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.TelephonyRegistryManager$1, reason: invalid class name */
    class AnonymousClass1 extends IOnSubscriptionsChangedListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ SubscriptionManager.OnSubscriptionsChangedListener val$listener;

        AnonymousClass1(TelephonyRegistryManager telephonyRegistryManager, Executor executor, SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
            this.val$executor = executor;
            this.val$listener = onSubscriptionsChangedListener;
        }

        @Override // com.android.internal.telephony.IOnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyRegistryManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscriptionManager.OnSubscriptionsChangedListener.this.onSubscriptionsChanged();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeOnSubscriptionsChangedListener(SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener) {
        if (this.mSubscriptionChangedListenerMap.get(onSubscriptionsChangedListener) == null) {
            return;
        }
        try {
            sRegistry.removeOnSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mSubscriptionChangedListenerMap.get(onSubscriptionsChangedListener));
            this.mSubscriptionChangedListenerMap.remove(onSubscriptionsChangedListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnOpportunisticSubscriptionsChangedListener(SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener, Executor executor) {
        if (this.mOpportunisticSubscriptionChangedListenerMap.get(onOpportunisticSubscriptionsChangedListener) != null) {
            Log.d(TAG, "addOnOpportunisticSubscriptionsChangedListener listener already present");
            return;
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(this, executor, onOpportunisticSubscriptionsChangedListener);
        this.mOpportunisticSubscriptionChangedListenerMap.put(onOpportunisticSubscriptionsChangedListener, anonymousClass2);
        try {
            sRegistry.addOnOpportunisticSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), anonymousClass2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.TelephonyRegistryManager$2, reason: invalid class name */
    class AnonymousClass2 extends IOnSubscriptionsChangedListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ SubscriptionManager.OnOpportunisticSubscriptionsChangedListener val$listener;

        AnonymousClass2(TelephonyRegistryManager telephonyRegistryManager, Executor executor, SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
            this.val$executor = executor;
            this.val$listener = onOpportunisticSubscriptionsChangedListener;
        }

        @Override // com.android.internal.telephony.IOnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.d(TelephonyRegistryManager.TAG, "onOpportunisticSubscriptionsChanged callback received.");
                Executor executor = this.val$executor;
                final SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyRegistryManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.this.onOpportunisticSubscriptionsChanged();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeOnOpportunisticSubscriptionsChangedListener(SubscriptionManager.OnOpportunisticSubscriptionsChangedListener onOpportunisticSubscriptionsChangedListener) {
        if (this.mOpportunisticSubscriptionChangedListenerMap.get(onOpportunisticSubscriptionsChangedListener) == null) {
            return;
        }
        try {
            sRegistry.removeOnSubscriptionsChangedListener(this.mContext.getOpPackageName(), this.mOpportunisticSubscriptionChangedListenerMap.get(onOpportunisticSubscriptionsChangedListener));
            this.mOpportunisticSubscriptionChangedListenerMap.remove(onOpportunisticSubscriptionsChangedListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void listenFromListener(int i, boolean z, boolean z2, String str, String str2, PhoneStateListener phoneStateListener, int i2, boolean z3) {
        if (phoneStateListener == null) {
            throw new IllegalStateException("telephony service is null.");
        }
        try {
            int[] array = getEventsFromBitmask(i2).stream().mapToInt(new ToIntFunction() { // from class: android.telephony.TelephonyRegistryManager$$ExternalSyntheticLambda2
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    int intValue;
                    intValue = ((Integer) obj).intValue();
                    return intValue;
                }
            }).toArray();
            if (Compatibility.isChangeEnabled(LISTEN_CODE_CHANGE)) {
                phoneStateListener.mSubId = Integer.valueOf(array.length == 0 ? -1 : i);
            } else if (phoneStateListener.mSubId != null) {
                i = phoneStateListener.mSubId.intValue();
            }
            sRegistry.listenWithEventList(z, z2, i, str, str2, phoneStateListener.callback, array, z3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void listenFromCallback(boolean z, boolean z2, int i, String str, String str2, TelephonyCallback telephonyCallback, int[] iArr, boolean z3) {
        try {
            sRegistry.listenWithEventList(z, z2, i, str, str2, telephonyCallback.callback, iArr, z3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierNetworkChange(boolean z) {
        try {
            sRegistry.notifyCarrierNetworkChange(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierNetworkChange(int i, boolean z) {
        try {
            sRegistry.notifyCarrierNetworkChangeWithSubId(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallStateChanged(int i, int i2, int i3, String str) {
        try {
            sRegistry.notifyCallState(i, i2, i3, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyCallStateChangedForAllSubscriptions(int i, String str) {
        try {
            sRegistry.notifyCallStateForAllSubs(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySubscriptionInfoChanged() {
        try {
            sRegistry.notifySubscriptionInfoChanged();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyOpportunisticSubscriptionInfoChanged() {
        try {
            sRegistry.notifyOpportunisticSubscriptionInfoChanged();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyServiceStateChanged(int i, int i2, ServiceState serviceState) {
        try {
            sRegistry.notifyServiceStateForPhoneId(i, i2, serviceState);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySignalStrengthChanged(int i, int i2, SignalStrength signalStrength) {
        try {
            sRegistry.notifySignalStrengthForPhoneId(i, i2, signalStrength);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyMessageWaitingChanged(int i, int i2, boolean z) {
        try {
            sRegistry.notifyMessageWaitingChangedForPhoneId(i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallForwardingChanged(int i, boolean z) {
        try {
            sRegistry.notifyCallForwardingChangedForSubscriber(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataActivityChanged(int i, int i2) {
        try {
            sRegistry.notifyDataActivityForSubscriber(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataActivityChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifyDataActivityForSubscriberWithSlot(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataConnectionForSubscriber(int i, int i2, PreciseDataConnectionState preciseDataConnectionState) {
        try {
            sRegistry.notifyDataConnectionForSubscriber(i, i2, preciseDataConnectionState);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallQualityChanged(int i, int i2, CallQuality callQuality, int i3) {
        try {
            sRegistry.notifyCallQualityChanged(callQuality, i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyMediaQualityStatusChanged(int i, int i2, MediaQualityStatus mediaQualityStatus) {
        try {
            sRegistry.notifyMediaQualityStatusChanged(i, i2, mediaQualityStatus);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyEmergencyNumberList(int i, int i2) {
        try {
            sRegistry.notifyEmergencyNumberList(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void notifyOutgoingEmergencyCall(int i, int i2, EmergencyNumber emergencyNumber) {
        try {
            sRegistry.notifyOutgoingEmergencyCall(i, i2, emergencyNumber);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyOutgoingEmergencySms(int i, int i2, EmergencyNumber emergencyNumber) {
        try {
            sRegistry.notifyOutgoingEmergencySms(i, i2, emergencyNumber);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyRadioPowerStateChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifyRadioPowerStateChanged(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPhoneCapabilityChanged(PhoneCapability phoneCapability) {
        try {
            sRegistry.notifyPhoneCapabilityChanged(phoneCapability);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataActivationStateChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifySimActivationStateChangedForPhoneId(i, i2, 1, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyVoiceActivationStateChanged(int i, int i2, int i3) {
        try {
            sRegistry.notifySimActivationStateChangedForPhoneId(i, i2, 0, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyUserMobileDataStateChanged(int i, int i2, boolean z) {
        try {
            sRegistry.notifyUserMobileDataStateChangedForPhoneId(i, i2, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDisplayInfoChanged(int i, int i2, TelephonyDisplayInfo telephonyDisplayInfo) {
        try {
            sRegistry.notifyDisplayInfoChanged(i, i2, telephonyDisplayInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyImsDisconnectCause(int i, ImsReasonInfo imsReasonInfo) {
        try {
            sRegistry.notifyImsDisconnectCause(i, imsReasonInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySrvccStateChanged(int i, int i2) {
        try {
            sRegistry.notifySrvccStateChanged(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPreciseCallState(int i, int i2, int[] iArr, String[] strArr, int[] iArr2, int[] iArr3) {
        try {
            sRegistry.notifyPreciseCallState(i, i2, iArr, strArr, iArr2, iArr3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDisconnectCause(int i, int i2, int i3, int i4) {
        try {
            sRegistry.notifyDisconnectCause(i, i2, i3, i4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCellLocation(int i, CellIdentity cellIdentity) {
        try {
            sRegistry.notifyCellLocationForSubscriber(i, cellIdentity);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCellInfoChanged(int i, List<CellInfo> list) {
        try {
            sRegistry.notifyCellInfoForSubscriber(i, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyActiveDataSubIdChanged(int i) {
        try {
            sRegistry.notifyActiveDataSubIdChanged(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyRegistrationFailed(int i, int i2, CellIdentity cellIdentity, String str, int i3, int i4, int i5) {
        try {
            sRegistry.notifyRegistrationFailed(i, i2, cellIdentity, str, i3, i4, i5);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyBarringInfoChanged(int i, int i2, BarringInfo barringInfo) {
        try {
            sRegistry.notifyBarringInfoChanged(i, i2, barringInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPhysicalChannelConfigForSubscriber(int i, int i2, List<PhysicalChannelConfig> list) {
        try {
            sRegistry.notifyPhysicalChannelConfigForSubscriber(i, i2, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyDataEnabled(int i, int i2, boolean z, int i3) {
        try {
            sRegistry.notifyDataEnabled(i, i2, z, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyAllowedNetworkTypesChanged(int i, int i2, int i3, long j) {
        try {
            sRegistry.notifyAllowedNetworkTypesChanged(i, i2, i3, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyLinkCapacityEstimateChanged(int i, int i2, List<LinkCapacityEstimate> list) {
        try {
            sRegistry.notifyLinkCapacityEstimateChanged(i, i2, list);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySimultaneousCellularCallingSubscriptionsChanged(Set<Integer> set) {
        try {
            sRegistry.notifySimultaneousCellularCallingSubscriptionsChanged(set.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.TelephonyRegistryManager$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    int intValue;
                    intValue = ((Integer) obj).intValue();
                    return intValue;
                }
            }).toArray());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierRoamingNtnModeChanged(int i, boolean z) {
        try {
            sRegistry.notifyCarrierRoamingNtnModeChanged(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierRoamingNtnEligibleStateChanged(int i, boolean z) {
        try {
            sRegistry.notifyCarrierRoamingNtnEligibleStateChanged(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierRoamingNtnAvailableServicesChanged(int i, int[] iArr) {
        try {
            sRegistry.notifyCarrierRoamingNtnAvailableServicesChanged(i, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void notifyCarrierRoamingNtnSignalStrengthChanged(int i, NtnSignalStrength ntnSignalStrength) {
        try {
            sRegistry.notifyCarrierRoamingNtnSignalStrengthChanged(i, ntnSignalStrength);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySecurityAlgorithmsChanged(int i, int i2, SecurityAlgorithmUpdate securityAlgorithmUpdate) {
        try {
            sRegistry.notifySecurityAlgorithmsChanged(i, i2, securityAlgorithmUpdate);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCellularIdentifierDisclosedChanged(int i, int i2, CellularIdentifierDisclosure cellularIdentifierDisclosure) {
        try {
            sRegistry.notifyCellularIdentifierDisclosedChanged(i, i2, cellularIdentifierDisclosure);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) {
        try {
            sRegistry.notifySemSatelliteServiceStateChanged(i, i2, semSatelliteServiceState);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) {
        try {
            sRegistry.notifySemSatelliteSignalStrengthChanged(i, i2, semSatelliteSignalStrength);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCpaiModelUpdate(int i, int i2) {
        try {
            sRegistry.notifyCpaiModelUpdate(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCpaiFeatureInfo(int i, int i2) {
        try {
            sRegistry.notifyCpaiFeatureInfo(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCpaiDataGathering(int i, int i2, byte[] bArr) {
        try {
            sRegistry.notifyCpaiDataGathering(i, i2, bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) {
        try {
            sRegistry.notifyCpaiDevAppMessage(i, i2, i3, bArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<Integer> getEventsFromCallback(TelephonyCallback telephonyCallback) {
        ArraySet arraySet = new ArraySet();
        if (telephonyCallback instanceof TelephonyCallback.ServiceStateListener) {
            arraySet.add(1);
        }
        if (telephonyCallback instanceof TelephonyCallback.MessageWaitingIndicatorListener) {
            arraySet.add(3);
        }
        if (telephonyCallback instanceof TelephonyCallback.CallForwardingIndicatorListener) {
            arraySet.add(4);
        }
        if (telephonyCallback instanceof TelephonyCallback.CellLocationListener) {
            arraySet.add(5);
        }
        if (telephonyCallback instanceof TelephonyCallback.CallStateListener) {
            arraySet.add(6);
        }
        if (telephonyCallback instanceof TelephonyCallback.DataConnectionStateListener) {
            arraySet.add(7);
        }
        if (telephonyCallback instanceof TelephonyCallback.DataActivityListener) {
            arraySet.add(8);
        }
        if (telephonyCallback instanceof TelephonyCallback.SignalStrengthsListener) {
            arraySet.add(9);
        }
        if (telephonyCallback instanceof TelephonyCallback.CellInfoListener) {
            arraySet.add(11);
        }
        if (telephonyCallback instanceof TelephonyCallback.PreciseCallStateListener) {
            arraySet.add(12);
        }
        if (telephonyCallback instanceof TelephonyCallback.CallDisconnectCauseListener) {
            arraySet.add(26);
        }
        if (telephonyCallback instanceof TelephonyCallback.ImsCallDisconnectCauseListener) {
            arraySet.add(28);
        }
        if (telephonyCallback instanceof TelephonyCallback.PreciseDataConnectionStateListener) {
            arraySet.add(13);
        }
        if (telephonyCallback instanceof TelephonyCallback.SrvccStateListener) {
            arraySet.add(16);
        }
        if (telephonyCallback instanceof TelephonyCallback.VoiceActivationStateListener) {
            arraySet.add(18);
        }
        if (telephonyCallback instanceof TelephonyCallback.DataActivationStateListener) {
            arraySet.add(19);
        }
        if (telephonyCallback instanceof TelephonyCallback.UserMobileDataStateListener) {
            arraySet.add(20);
        }
        if (telephonyCallback instanceof TelephonyCallback.DisplayInfoListener) {
            arraySet.add(21);
        }
        if (telephonyCallback instanceof TelephonyCallback.EmergencyNumberListListener) {
            arraySet.add(25);
        }
        if (telephonyCallback instanceof TelephonyCallback.OutgoingEmergencyCallListener) {
            arraySet.add(29);
        }
        if (telephonyCallback instanceof TelephonyCallback.OutgoingEmergencySmsListener) {
            arraySet.add(30);
        }
        if (telephonyCallback instanceof TelephonyCallback.PhoneCapabilityListener) {
            arraySet.add(22);
        }
        if (telephonyCallback instanceof TelephonyCallback.ActiveDataSubscriptionIdListener) {
            arraySet.add(23);
        }
        if (telephonyCallback instanceof TelephonyCallback.RadioPowerStateListener) {
            arraySet.add(24);
        }
        if (telephonyCallback instanceof TelephonyCallback.CarrierNetworkListener) {
            arraySet.add(17);
        }
        if (telephonyCallback instanceof TelephonyCallback.RegistrationFailedListener) {
            arraySet.add(31);
        }
        if (telephonyCallback instanceof TelephonyCallback.CallAttributesListener) {
            arraySet.add(27);
        }
        if (telephonyCallback instanceof TelephonyCallback.BarringInfoListener) {
            arraySet.add(32);
        }
        if (telephonyCallback instanceof TelephonyCallback.PhysicalChannelConfigListener) {
            arraySet.add(33);
        }
        if (telephonyCallback instanceof TelephonyCallback.DataEnabledListener) {
            arraySet.add(34);
        }
        if (telephonyCallback instanceof TelephonyCallback.AllowedNetworkTypesListener) {
            arraySet.add(35);
        }
        if (telephonyCallback instanceof TelephonyCallback.LinkCapacityEstimateChangedListener) {
            arraySet.add(37);
        }
        if (telephonyCallback instanceof TelephonyCallback.MediaQualityStatusChangedListener) {
            arraySet.add(39);
        }
        if (telephonyCallback instanceof TelephonyCallback.EmergencyCallbackModeListener) {
            arraySet.add(40);
        }
        if (telephonyCallback instanceof TelephonyCallback.SimultaneousCellularCallingSupportListener) {
            arraySet.add(41);
        }
        if (telephonyCallback instanceof TelephonyCallback.CarrierRoamingNtnListener) {
            arraySet.add(42);
            arraySet.add(43);
            arraySet.add(44);
            arraySet.add(45);
        }
        if (telephonyCallback instanceof TelephonyCallback.CellularIdentifierDisclosedListener) {
            arraySet.add(47);
        }
        if (telephonyCallback instanceof TelephonyCallback.SecurityAlgorithmsListener) {
            arraySet.add(46);
        }
        if (telephonyCallback instanceof TelephonyCallback.SemSatelliteStateListener) {
            arraySet.add(10000);
            arraySet.add(10001);
        }
        if (telephonyCallback instanceof TelephonyCallback.CpaiModelUpdateListener) {
            arraySet.add(11000);
        }
        if (telephonyCallback instanceof TelephonyCallback.CpaiFeatureInforListener) {
            arraySet.add(11001);
        }
        if (telephonyCallback instanceof TelephonyCallback.CpaiDataGatheringListener) {
            arraySet.add(11002);
        }
        if (telephonyCallback instanceof TelephonyCallback.CpaiDevAppMessageListener) {
            arraySet.add(11003);
        }
        return arraySet;
    }

    private Set<Integer> getEventsFromBitmask(int i) {
        ArraySet arraySet = new ArraySet();
        if ((i & 1) != 0) {
            arraySet.add(1);
        }
        if ((i & 2) != 0) {
            arraySet.add(2);
        }
        if ((i & 4) != 0) {
            arraySet.add(3);
        }
        if ((i & 8) != 0) {
            arraySet.add(4);
        }
        if ((i & 16) != 0) {
            arraySet.add(5);
        }
        if ((i & 32) != 0) {
            arraySet.add(36);
        }
        if ((i & 64) != 0) {
            arraySet.add(7);
        }
        if ((i & 128) != 0) {
            arraySet.add(8);
        }
        if ((i & 256) != 0) {
            arraySet.add(9);
        }
        if ((i & 1024) != 0) {
            arraySet.add(11);
        }
        if ((i & 2048) != 0) {
            arraySet.add(12);
        }
        if ((i & 4096) != 0) {
            arraySet.add(13);
        }
        if ((i & 8192) != 0) {
            arraySet.add(14);
        }
        if ((32768 & i) != 0) {
            arraySet.add(15);
        }
        if ((i & 16384) != 0) {
            arraySet.add(16);
        }
        if ((65536 & i) != 0) {
            arraySet.add(17);
        }
        if ((131072 & i) != 0) {
            arraySet.add(18);
        }
        if ((262144 & i) != 0) {
            arraySet.add(19);
        }
        if ((524288 & i) != 0) {
            arraySet.add(20);
        }
        if ((1048576 & i) != 0) {
            arraySet.add(21);
        }
        if ((2097152 & i) != 0) {
            arraySet.add(22);
        }
        if ((4194304 & i) != 0) {
            arraySet.add(23);
        }
        if ((8388608 & i) != 0) {
            arraySet.add(24);
        }
        if ((16777216 & i) != 0) {
            arraySet.add(25);
        }
        if ((33554432 & i) != 0) {
            arraySet.add(26);
        }
        if ((67108864 & i) != 0) {
            arraySet.add(27);
        }
        if ((134217728 & i) != 0) {
            arraySet.add(28);
        }
        if ((268435456 & i) != 0) {
            arraySet.add(29);
        }
        if ((536870912 & i) != 0) {
            arraySet.add(30);
        }
        if ((1073741824 & i) != 0) {
            arraySet.add(31);
        }
        if ((i & Integer.MIN_VALUE) != 0) {
            arraySet.add(32);
        }
        return arraySet;
    }

    public void registerTelephonyCallback(boolean z, boolean z2, Executor executor, int i, String str, String str2, TelephonyCallback telephonyCallback, boolean z3) {
        if (telephonyCallback == null) {
            throw new IllegalStateException("telephony service is null.");
        }
        telephonyCallback.init(executor);
        listenFromCallback(z, z2, i, str, str2, telephonyCallback, getEventsFromCallback(telephonyCallback).stream().mapToInt(new ToIntFunction() { // from class: android.telephony.TelephonyRegistryManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
            }
        }).toArray(), z3);
    }

    public void unregisterTelephonyCallback(int i, String str, String str2, TelephonyCallback telephonyCallback, boolean z) {
        listenFromCallback(false, false, i, str, str2, telephonyCallback, new int[0], z);
    }

    public void addSatelliteStateChangeListener(Executor executor, SatelliteStateChangeListener satelliteStateChangeListener) {
        if (satelliteStateChangeListener == null || executor == null) {
            throw new IllegalArgumentException("Listener and executor must be non-null");
        }
        Map<SatelliteStateChangeListener, WeakReference<SatelliteStateChangeListenerWrapper>> map = sSatelliteStateChangeListeners;
        synchronized (map) {
            WeakReference<SatelliteStateChangeListenerWrapper> weakReference = map.get(satelliteStateChangeListener);
            if (weakReference != null && weakReference.get() != null) {
                Log.d(TAG, "addSatelliteStateChangeListener: listener already registered");
                return;
            }
            SatelliteStateChangeListenerWrapper satelliteStateChangeListenerWrapper = new SatelliteStateChangeListenerWrapper(executor, satelliteStateChangeListener);
            try {
                sRegistry.addSatelliteStateChangeListener(satelliteStateChangeListenerWrapper, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                map.put(satelliteStateChangeListener, new WeakReference<>(satelliteStateChangeListenerWrapper));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeSatelliteStateChangeListener(SatelliteStateChangeListener satelliteStateChangeListener) {
        if (satelliteStateChangeListener == null) {
            throw new IllegalArgumentException("listener must be non-null");
        }
        Map<SatelliteStateChangeListener, WeakReference<SatelliteStateChangeListenerWrapper>> map = sSatelliteStateChangeListeners;
        synchronized (map) {
            WeakReference<SatelliteStateChangeListenerWrapper> weakReference = map.get(satelliteStateChangeListener);
            if (weakReference == null) {
                return;
            }
            SatelliteStateChangeListenerWrapper satelliteStateChangeListenerWrapper = weakReference.get();
            if (satelliteStateChangeListenerWrapper == null) {
                return;
            }
            try {
                sRegistry.removeSatelliteStateChangeListener(satelliteStateChangeListenerWrapper, this.mContext.getOpPackageName());
                map.remove(satelliteStateChangeListener);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifySatelliteStateChanged(boolean z) {
        try {
            sRegistry.notifySatelliteStateChanged(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SatelliteStateChangeListenerWrapper extends ISatelliteStateChangeListener.Stub implements ListenerExecutor {
        private final Executor mExecutor;
        private final WeakReference<SatelliteStateChangeListener> mListener;

        SatelliteStateChangeListenerWrapper(Executor executor, SatelliteStateChangeListener satelliteStateChangeListener) {
            this.mExecutor = executor;
            this.mListener = new WeakReference<>(satelliteStateChangeListener);
        }

        @Override // com.android.internal.telephony.ISatelliteStateChangeListener
        public void onSatelliteEnabledStateChanged(final boolean z) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyRegistryManager$SatelliteStateChangeListenerWrapper$$ExternalSyntheticLambda2
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyRegistryManager.SatelliteStateChangeListenerWrapper.this.lambda$onSatelliteEnabledStateChanged$1(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSatelliteEnabledStateChanged$1(final boolean z) throws Exception {
            Executor executor = this.mExecutor;
            final WeakReference<SatelliteStateChangeListener> weakReference = this.mListener;
            Objects.requireNonNull(weakReference);
            executeSafely(executor, new Supplier() { // from class: android.telephony.TelephonyRegistryManager$SatelliteStateChangeListenerWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return (SatelliteStateChangeListener) weakReference.get();
                }
            }, new ListenerExecutor.ListenerOperation() { // from class: android.telephony.TelephonyRegistryManager$SatelliteStateChangeListenerWrapper$$ExternalSyntheticLambda1
                @Override // com.android.internal.listeners.ListenerExecutor.ListenerOperation
                public final void operate(Object obj) {
                    ((SatelliteStateChangeListener) obj).onEnabledStateChanged(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CarrierPrivilegesCallbackWrapper extends ICarrierPrivilegesCallback.Stub implements ListenerExecutor {
        private final WeakReference<TelephonyManager.CarrierPrivilegesCallback> mCallback;
        private final Executor mExecutor;

        CarrierPrivilegesCallbackWrapper(TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback, Executor executor) {
            this.mCallback = new WeakReference<>(carrierPrivilegesCallback);
            this.mExecutor = executor;
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierPrivilegesChanged(List<String> list, int[] iArr) {
            final Set copyOf = Set.copyOf(list);
            final Set set = (Set) Arrays.stream(iArr).boxed().collect(Collectors.toSet());
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda4
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper.this.lambda$onCarrierPrivilegesChanged$1(copyOf, set);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierPrivilegesChanged$1(final Set set, final Set set2) throws Exception {
            Executor executor = this.mExecutor;
            WeakReference<TelephonyManager.CarrierPrivilegesCallback> weakReference = this.mCallback;
            Objects.requireNonNull(weakReference);
            executeSafely(executor, new TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1(weakReference), new ListenerExecutor.ListenerOperation() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda3
                @Override // com.android.internal.listeners.ListenerExecutor.ListenerOperation
                public final void operate(Object obj) {
                    ((TelephonyManager.CarrierPrivilegesCallback) obj).onCarrierPrivilegesChanged(set, set2);
                }
            });
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierServiceChanged(final String str, final int i) {
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    TelephonyRegistryManager.CarrierPrivilegesCallbackWrapper.this.lambda$onCarrierServiceChanged$3(str, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCarrierServiceChanged$3(final String str, final int i) throws Exception {
            Executor executor = this.mExecutor;
            WeakReference<TelephonyManager.CarrierPrivilegesCallback> weakReference = this.mCallback;
            Objects.requireNonNull(weakReference);
            executeSafely(executor, new TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda1(weakReference), new ListenerExecutor.ListenerOperation() { // from class: android.telephony.TelephonyRegistryManager$CarrierPrivilegesCallbackWrapper$$ExternalSyntheticLambda2
                @Override // com.android.internal.listeners.ListenerExecutor.ListenerOperation
                public final void operate(Object obj) {
                    ((TelephonyManager.CarrierPrivilegesCallback) obj).onCarrierServiceChanged(str, i);
                }
            });
        }
    }

    public void addCarrierPrivilegesCallback(int i, Executor executor, TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback) {
        if (carrierPrivilegesCallback == null || executor == null) {
            throw new IllegalArgumentException("callback and executor must be non-null");
        }
        WeakHashMap<TelephonyManager.CarrierPrivilegesCallback, WeakReference<CarrierPrivilegesCallbackWrapper>> weakHashMap = sCarrierPrivilegeCallbacks;
        synchronized (weakHashMap) {
            WeakReference<CarrierPrivilegesCallbackWrapper> weakReference = weakHashMap.get(carrierPrivilegesCallback);
            if (weakReference != null && weakReference.get() != null) {
                Log.d(TAG, "addCarrierPrivilegesCallback: callback already registered");
                return;
            }
            CarrierPrivilegesCallbackWrapper carrierPrivilegesCallbackWrapper = new CarrierPrivilegesCallbackWrapper(carrierPrivilegesCallback, executor);
            weakHashMap.put(carrierPrivilegesCallback, new WeakReference<>(carrierPrivilegesCallbackWrapper));
            try {
                sRegistry.addCarrierPrivilegesCallback(i, carrierPrivilegesCallbackWrapper, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeCarrierPrivilegesCallback(TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback) {
        if (carrierPrivilegesCallback == null) {
            throw new IllegalArgumentException("listener must be non-null");
        }
        WeakHashMap<TelephonyManager.CarrierPrivilegesCallback, WeakReference<CarrierPrivilegesCallbackWrapper>> weakHashMap = sCarrierPrivilegeCallbacks;
        synchronized (weakHashMap) {
            WeakReference<CarrierPrivilegesCallbackWrapper> remove = weakHashMap.remove(carrierPrivilegesCallback);
            if (remove == null) {
                return;
            }
            CarrierPrivilegesCallbackWrapper carrierPrivilegesCallbackWrapper = remove.get();
            if (carrierPrivilegesCallbackWrapper == null) {
                return;
            }
            try {
                sRegistry.removeCarrierPrivilegesCallback(carrierPrivilegesCallbackWrapper, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifyCarrierPrivilegesChanged(int i, Set<String> set, Set<Integer> set2) {
        if (set == null || set2 == null) {
            throw new IllegalArgumentException("privilegedPackageNames and privilegedUids must be non-null");
        }
        try {
            sRegistry.notifyCarrierPrivilegesChanged(i, List.copyOf(set), set2.stream().mapToInt(new AudioPort$$ExternalSyntheticLambda0()).toArray());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierServiceChanged(int i, String str, int i2) {
        try {
            sRegistry.notifyCarrierServiceChanged(i, str, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addCarrierConfigChangedListener(Executor executor, CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener) {
        Objects.requireNonNull(executor, "Executor should be non-null.");
        Objects.requireNonNull(carrierConfigChangeListener, "Listener should be non-null.");
        if (this.mCarrierConfigChangeListenerMap.get(carrierConfigChangeListener) != null) {
            Log.e(TAG, "registerCarrierConfigChangeListener: listener already present");
            return;
        }
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(this, executor, carrierConfigChangeListener);
        try {
            sRegistry.addCarrierConfigChangeListener(anonymousClass3, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            this.mCarrierConfigChangeListenerMap.put(carrierConfigChangeListener, anonymousClass3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.telephony.TelephonyRegistryManager$3, reason: invalid class name */
    class AnonymousClass3 extends ICarrierConfigChangeListener.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ CarrierConfigManager.CarrierConfigChangeListener val$listener;

        AnonymousClass3(TelephonyRegistryManager telephonyRegistryManager, Executor executor, CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener) {
            this.val$executor = executor;
            this.val$listener = carrierConfigChangeListener;
        }

        @Override // com.android.internal.telephony.ICarrierConfigChangeListener
        public void onCarrierConfigChanged(final int i, final int i2, final int i3, final int i4) {
            Log.d(TelephonyRegistryManager.TAG, "onCarrierConfigChanged call in ICarrierConfigChangeListener callback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Executor executor = this.val$executor;
                final CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener = this.val$listener;
                executor.execute(new Runnable() { // from class: android.telephony.TelephonyRegistryManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarrierConfigManager.CarrierConfigChangeListener.this.onCarrierConfigChanged(i, i2, i3, i4);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void removeCarrierConfigChangedListener(CarrierConfigManager.CarrierConfigChangeListener carrierConfigChangeListener) {
        Objects.requireNonNull(carrierConfigChangeListener, "Listener should be non-null.");
        if (this.mCarrierConfigChangeListenerMap.get(carrierConfigChangeListener) == null) {
            Log.e(TAG, "removeCarrierConfigChangedListener: listener was not present");
            return;
        }
        try {
            sRegistry.removeCarrierConfigChangeListener(this.mCarrierConfigChangeListenerMap.get(carrierConfigChangeListener), this.mContext.getOpPackageName());
            this.mCarrierConfigChangeListenerMap.remove(carrierConfigChangeListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCarrierConfigChanged(int i, int i2, int i3, int i4) {
        if (!SubscriptionManager.isValidPhoneId(i)) {
            Log.e(TAG, "notifyCarrierConfigChanged, ignored: invalid slotIndex " + i);
        } else {
            try {
                sRegistry.notifyCarrierConfigChanged(i, i2, i3, i4);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void notifyCallbackModeStarted(int i, int i2, int i3, long j) {
        try {
            Log.d(TAG, "notifyCallbackModeStarted:type=" + i3);
            sRegistry.notifyCallbackModeStarted(i, i2, i3, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallbackModeRestarted(int i, int i2, int i3, long j) {
        try {
            Log.d(TAG, "notifyCallbackModeRestarted:type=" + i3);
            sRegistry.notifyCallbackModeRestarted(i, i2, i3, j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyCallbackModeStopped(int i, int i2, int i3, int i4) {
        try {
            Log.d(TAG, "notifyCallbackModeStopped:type=" + i3 + ", reason=" + i4);
            sRegistry.notifyCallbackModeStopped(i, i2, i3, i4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearPreciseDataConnectionStates(int i) {
        try {
            sRegistry.clearPreciseDataConnectionStates(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
