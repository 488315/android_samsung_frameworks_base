package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.IInterface;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.util.Log;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.internal.telephony.util.RemoteCallbackListExt;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes4.dex */
public abstract class ImsFeature {

    @SystemApi
    public static final int CAPABILITY_ERROR_GENERIC = -1;

    @SystemApi
    public static final int CAPABILITY_SUCCESS = 0;

    @SystemApi
    public static final int FEATURE_EMERGENCY_MMTEL = 0;
    public static final int FEATURE_INVALID = -1;
    public static final int FEATURE_MAX = 3;

    @SystemApi
    public static final int FEATURE_MMTEL = 1;

    @SystemApi
    public static final int FEATURE_RCS = 2;
    private static final String LOG_TAG = "ImsFeature";

    @SystemApi
    public static final int STATE_INITIALIZING = 1;

    @SystemApi
    public static final int STATE_READY = 2;

    @SystemApi
    public static final int STATE_UNAVAILABLE = 0;
    protected Context mContext;
    public static final Map<Integer, String> FEATURE_LOG_MAP = Map.of(0, "EMERGENCY_MMTEL", 1, "MMTEL", 2, "RCS");
    public static final Map<Integer, String> STATE_LOG_MAP = Map.of(0, "UNAVAILABLE", 1, "INITIALIZING", 2, "READY");
    protected final Object mLock = new Object();
    private final RemoteCallbackListExt<IImsFeatureStatusCallback> mStatusCallbacks = new RemoteCallbackListExt<>();
    private int mState = 0;
    private int mSlotId = -1;
    private final RemoteCallbackListExt<IImsCapabilityCallback> mCapabilityCallbacks = new RemoteCallbackListExt<>();
    private Capabilities mCapabilityStatus = new Capabilities();

    @Retention(RetentionPolicy.SOURCE)
    public @interface FeatureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsCapabilityError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsState {
    }

    public abstract void changeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, CapabilityCallbackProxy capabilityCallbackProxy);

    protected abstract IInterface getBinder();

    public abstract void onFeatureReady();

    public abstract void onFeatureRemoved();

    public abstract boolean queryCapabilityConfiguration(int i, int i2);

    protected static class CapabilityCallbackProxy {
        private final IImsCapabilityCallback mCallback;

        public CapabilityCallbackProxy(IImsCapabilityCallback iImsCapabilityCallback) {
            this.mCallback = iImsCapabilityCallback;
        }

        public void onChangeCapabilityConfigurationError(int i, int i2, int i3) {
            IImsCapabilityCallback iImsCapabilityCallback = this.mCallback;
            if (iImsCapabilityCallback == null) {
                return;
            }
            try {
                iImsCapabilityCallback.onChangeCapabilityConfigurationError(i, i2, i3);
            } catch (RemoteException unused) {
                Log.e(ImsFeature.LOG_TAG, "onChangeCapabilityConfigurationError called on dead binder.");
            }
        }
    }

    @SystemApi
    @Deprecated
    public static class Capabilities {
        protected int mCapabilities;

        public Capabilities() {
            this.mCapabilities = 0;
        }

        protected Capabilities(int i) {
            this.mCapabilities = i;
        }

        public void addCapabilities(int i) {
            this.mCapabilities = i | this.mCapabilities;
        }

        public void removeCapabilities(int i) {
            this.mCapabilities = (~i) & this.mCapabilities;
        }

        public boolean isCapable(int i) {
            return (this.mCapabilities & i) == i;
        }

        public Capabilities copy() {
            return new Capabilities(this.mCapabilities);
        }

        public int getMask() {
            return this.mCapabilities;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Capabilities) && this.mCapabilities == ((Capabilities) obj).mCapabilities;
        }

        public int hashCode() {
            return this.mCapabilities;
        }

        public String toString() {
            return "Capabilities: " + Integer.toBinaryString(this.mCapabilities);
        }
    }

    public void initialize(Context context, int i) {
        this.mContext = context;
        this.mSlotId = i;
    }

    @SystemApi
    public final int getSlotIndex() {
        return this.mSlotId;
    }

    @SystemApi
    public int getFeatureState() {
        int i;
        synchronized (this.mLock) {
            i = this.mState;
        }
        return i;
    }

    @SystemApi
    public final void setFeatureState(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mState != i) {
                this.mState = i;
                z = true;
            } else {
                z = false;
            }
        }
        if (z) {
            notifyFeatureState(i);
        }
    }

    public void addImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        try {
            synchronized (this.mStatusCallbacks) {
                this.mStatusCallbacks.register(iImsFeatureStatusCallback);
                iImsFeatureStatusCallback.notifyImsFeatureStatus(getFeatureState());
            }
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "Couldn't notify feature state: " + e.getMessage());
        }
    }

    public void removeImsFeatureStatusCallback(IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mStatusCallbacks) {
            this.mStatusCallbacks.unregister(iImsFeatureStatusCallback);
        }
    }

    private void notifyFeatureState(final int i) {
        synchronized (this.mStatusCallbacks) {
            this.mStatusCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.feature.ImsFeature$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImsFeature.lambda$notifyFeatureState$0(i, (IImsFeatureStatusCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyFeatureState$0(int i, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        try {
            iImsFeatureStatusCallback.notifyImsFeatureStatus(i);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + " notifyFeatureState() - Skipping callback.");
        }
    }

    public final void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbacks.register(iImsCapabilityCallback);
        try {
            iImsCapabilityCallback.onCapabilitiesStatusChanged(queryCapabilityStatus().mCapabilities);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, "addCapabilityCallback: error accessing callback: " + e.getMessage());
        }
    }

    final void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) {
        this.mCapabilityCallbacks.unregister(iImsCapabilityCallback);
    }

    final void queryCapabilityConfigurationInternal(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) {
        boolean zQueryCapabilityConfiguration = queryCapabilityConfiguration(i, i2);
        if (iImsCapabilityCallback != null) {
            try {
                iImsCapabilityCallback.onQueryCapabilityConfiguration(i, i2, zQueryCapabilityConfiguration);
            } catch (RemoteException unused) {
                Log.e(LOG_TAG, "queryCapabilityConfigurationInternal called on dead binder!");
            }
        }
    }

    public Capabilities queryCapabilityStatus() {
        Capabilities capabilitiesCopy;
        synchronized (this.mLock) {
            capabilitiesCopy = this.mCapabilityStatus.copy();
        }
        return capabilitiesCopy;
    }

    public final void requestChangeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) {
        if (capabilityChangeRequest == null) {
            throw new IllegalArgumentException("ImsFeature#requestChangeEnabledCapabilities called with invalid params.");
        }
        changeEnabledCapabilities(capabilityChangeRequest, new CapabilityCallbackProxy(iImsCapabilityCallback));
    }

    protected final void notifyCapabilitiesStatusChanged(final Capabilities capabilities) {
        Log.i(LOG_TAG, "notifyCapabilitiesStatusChanged()");
        synchronized (this.mLock) {
            this.mCapabilityStatus = capabilities.copy();
        }
        synchronized (this.mCapabilityCallbacks) {
            this.mCapabilityCallbacks.broadcastAction(new Consumer() { // from class: android.telephony.ims.feature.ImsFeature$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImsFeature.lambda$notifyCapabilitiesStatusChanged$1(capabilities, (IImsCapabilityCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyCapabilitiesStatusChanged$1(Capabilities capabilities, IImsCapabilityCallback iImsCapabilityCallback) {
        try {
            Log.d(LOG_TAG, "ImsFeature notifyCapabilitiesStatusChanged Capabilities = " + capabilities.mCapabilities);
            iImsCapabilityCallback.onCapabilitiesStatusChanged(capabilities.mCapabilities);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + " notifyCapabilitiesStatusChanged() - Skipping callback.");
        }
    }
}
