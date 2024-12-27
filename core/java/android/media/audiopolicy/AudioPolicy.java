package android.media.audiopolicy;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.AttributionSource;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFocusInfo;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.FadeManagerConfiguration;
import android.media.IAudioService;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

@SystemApi
/* loaded from: classes2.dex */
public class AudioPolicy {
    private static final boolean DEBUG = false;
    public static final int FOCUS_POLICY_DUCKING_DEFAULT = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_APP = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_POLICY = 1;
    private static final int MSG_FOCUS_ABANDON = 5;
    private static final int MSG_FOCUS_GRANT = 1;
    private static final int MSG_FOCUS_LOSS = 2;
    private static final int MSG_FOCUS_REQUEST = 4;
    private static final int MSG_MIX_STATE_UPDATE = 3;
    private static final int MSG_POLICY_STATUS_CHANGE = 0;
    private static final int MSG_VOL_ADJUST = 6;
    public static final int POLICY_STATUS_REGISTERED = 2;
    public static final int POLICY_STATUS_UNREGISTERED = 1;
    private static final String TAG = "AudioPolicy";
    private static IAudioService sService;
    private ArrayList<WeakReference<AudioRecord>> mCaptors;
    private AudioPolicyConfig mConfig;
    private Context mContext;
    private final EventHandler mEventHandler;
    private AudioPolicyFocusListener mFocusListener;
    private ArrayList<WeakReference<AudioTrack>> mInjectors;
    private final boolean mIsFocusPolicy;
    private final boolean mIsTestFocusPolicy;
    private final Object mLock;
    private final IAudioPolicyCallback mPolicyCb;
    private final MediaProjection mProjection;
    private String mRegistrationId;
    private int mStatus;
    private final AudioPolicyStatusListener mStatusListener;
    private final AudioPolicyVolumeCallback mVolCb;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyStatus {}

    public AudioPolicyConfig getConfig() {
        return this.mConfig;
    }

    public boolean hasFocusListener() {
        return this.mFocusListener != null;
    }

    public boolean isFocusPolicy() {
        return this.mIsFocusPolicy;
    }

    public boolean isTestFocusPolicy() {
        return this.mIsTestFocusPolicy;
    }

    public boolean isVolumeController() {
        return this.mVolCb != null;
    }

    public MediaProjection getMediaProjection() {
        return this.mProjection;
    }

    public AttributionSource getAttributionSource() {
        return getAttributionSource(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AttributionSource getAttributionSource(Context context) {
        return context == null
                ? AttributionSource.myAttributionSource()
                : context.getAttributionSource();
    }

    private AudioPolicy(
            AudioPolicyConfig config,
            Context context,
            Looper looper,
            AudioPolicyFocusListener fl,
            AudioPolicyStatusListener sl,
            boolean isFocusPolicy,
            boolean isTestFocusPolicy,
            AudioPolicyVolumeCallback vc,
            MediaProjection projection) {
        this.mLock = new Object();
        this.mPolicyCb =
                new IAudioPolicyCallback
                        .Stub() { // from class: android.media.audiopolicy.AudioPolicy.1
                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyAudioFocusGrant(AudioFocusInfo afi, int requestResult) {
                        AudioPolicy.this.sendMsg(1, afi, requestResult);
                    }

                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean z) {
                        AudioPolicy.this.sendMsg(2, audioFocusInfo, z ? 1 : 0);
                    }

                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyAudioFocusRequest(AudioFocusInfo afi, int requestResult) {
                        AudioPolicy.this.sendMsg(4, afi, requestResult);
                    }

                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyAudioFocusAbandon(AudioFocusInfo afi) {
                        AudioPolicy.this.sendMsg(5, afi, 0);
                    }

                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyMixStateUpdate(String regId, int state) {
                        Iterator<AudioMix> it = AudioPolicy.this.mConfig.getMixes().iterator();
                        while (it.hasNext()) {
                            AudioMix mix = it.next();
                            if (mix.getRegistration().equals(regId)) {
                                mix.mMixState = state;
                                AudioPolicy.this.sendMsg(3, mix, 0);
                            }
                        }
                    }

                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyVolumeAdjust(int adjustment) {
                        AudioPolicy.this.sendMsg(6, null, adjustment);
                    }

                    @Override // android.media.audiopolicy.IAudioPolicyCallback
                    public void notifyUnregistration() {
                        AudioPolicy.this.setRegistration(null);
                    }
                };
        this.mConfig = config;
        this.mStatus = 1;
        this.mContext = context;
        looper = looper == null ? Looper.getMainLooper() : looper;
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            this.mEventHandler = null;
            Log.e(TAG, "No event handler due to looper without a thread");
        }
        this.mFocusListener = fl;
        this.mStatusListener = sl;
        this.mIsFocusPolicy = isFocusPolicy;
        this.mIsTestFocusPolicy = isTestFocusPolicy;
        this.mVolCb = vc;
        this.mProjection = projection;
    }

    public static class Builder {
        private Context mContext;
        private AudioPolicyFocusListener mFocusListener;
        private Looper mLooper;
        private MediaProjection mProjection;
        private AudioPolicyStatusListener mStatusListener;
        private AudioPolicyVolumeCallback mVolCb;
        private boolean mIsFocusPolicy = false;
        private boolean mIsTestFocusPolicy = false;
        private ArrayList<AudioMix> mMixes = new ArrayList<>();

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder addMix(AudioMix mix) throws IllegalArgumentException {
            if (mix == null) {
                throw new IllegalArgumentException("Illegal null AudioMix argument");
            }
            if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                    .deviceAwarePermissionApisEnabled()) {
                mix.setVirtualDeviceId(
                        AudioPolicy.getAttributionSource(this.mContext).getDeviceId());
            }
            this.mMixes.add(mix);
            return this;
        }

        public Builder setLooper(Looper looper) throws IllegalArgumentException {
            if (looper == null) {
                throw new IllegalArgumentException("Illegal null Looper argument");
            }
            this.mLooper = looper;
            return this;
        }

        public void setAudioPolicyFocusListener(AudioPolicyFocusListener l) {
            this.mFocusListener = l;
        }

        public Builder setIsAudioFocusPolicy(boolean isFocusPolicy) {
            this.mIsFocusPolicy = isFocusPolicy;
            return this;
        }

        public Builder setIsTestFocusPolicy(boolean isTestFocusPolicy) {
            this.mIsTestFocusPolicy = isTestFocusPolicy;
            return this;
        }

        public void setAudioPolicyStatusListener(AudioPolicyStatusListener l) {
            this.mStatusListener = l;
        }

        public Builder setAudioPolicyVolumeCallback(AudioPolicyVolumeCallback vc) {
            if (vc == null) {
                throw new IllegalArgumentException("Invalid null volume callback");
            }
            this.mVolCb = vc;
            return this;
        }

        public Builder setMediaProjection(MediaProjection projection) {
            if (projection == null) {
                throw new IllegalArgumentException("Invalid null volume callback");
            }
            this.mProjection = projection;
            return this;
        }

        public AudioPolicy build() {
            if (this.mStatusListener != null) {
                Iterator<AudioMix> it = this.mMixes.iterator();
                while (it.hasNext()) {
                    AudioMix mix = it.next();
                    mix.mCallbackFlags |= 1;
                }
            }
            if (this.mIsFocusPolicy && this.mFocusListener == null) {
                throw new IllegalStateException(
                        "Cannot be a focus policy without an AudioPolicyFocusListener");
            }
            return new AudioPolicy(
                    new AudioPolicyConfig(this.mMixes),
                    this.mContext,
                    this.mLooper,
                    this.mFocusListener,
                    this.mStatusListener,
                    this.mIsFocusPolicy,
                    this.mIsTestFocusPolicy,
                    this.mVolCb,
                    this.mProjection);
        }
    }

    public int attachMixes(List<AudioMix> mixes) {
        int status;
        if (mixes == null) {
            throw new IllegalArgumentException("Illegal null list of AudioMix");
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException("Cannot alter unregistered AudioPolicy");
            }
            ArrayList<AudioMix> zeMixes = new ArrayList<>(mixes.size());
            for (AudioMix mix : mixes) {
                if (mix == null) {
                    throw new IllegalArgumentException("Illegal null AudioMix in attachMixes");
                }
                if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                        .deviceAwarePermissionApisEnabled()) {
                    mix.setVirtualDeviceId(getAttributionSource(this.mContext).getDeviceId());
                }
                zeMixes.add(mix);
            }
            AudioPolicyConfig cfg = new AudioPolicyConfig(zeMixes);
            IAudioService service = getService();
            try {
                status = service.addMixForPolicy(cfg, cb());
                if (status == 0) {
                    this.mConfig.add(zeMixes);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in attachMixes", e);
                return -1;
            }
        }
        return status;
    }

    public int detachMixes(List<AudioMix> mixes) {
        int status;
        if (mixes == null) {
            throw new IllegalArgumentException("Illegal null list of AudioMix");
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException("Cannot alter unregistered AudioPolicy");
            }
            ArrayList<AudioMix> zeMixes = new ArrayList<>(mixes.size());
            for (AudioMix mix : mixes) {
                if (mix == null) {
                    throw new IllegalArgumentException("Illegal null AudioMix in detachMixes");
                }
                if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                        .deviceAwarePermissionApisEnabled()) {
                    mix.setVirtualDeviceId(getAttributionSource(this.mContext).getDeviceId());
                }
                zeMixes.add(mix);
            }
            AudioPolicyConfig cfg = new AudioPolicyConfig(zeMixes);
            IAudioService service = getService();
            try {
                status = service.removeMixForPolicy(cfg, cb());
                if (status == 0) {
                    this.mConfig.remove(zeMixes);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in detachMixes", e);
                return -1;
            }
        }
        return status;
    }

    public int updateMixingRules(List<Pair<AudioMix, AudioMixingRule>> mixingRuleUpdates) {
        int status;
        Objects.requireNonNull(mixingRuleUpdates);
        IAudioService service = getService();
        try {
            synchronized (this.mLock) {
                status =
                        service.updateMixingRulesForPolicy(
                                (AudioMix[])
                                        mixingRuleUpdates.stream()
                                                .map(
                                                        new Function() { // from class:
                                                            // android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda1
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                return AudioPolicy
                                                                        .lambda$updateMixingRules$0(
                                                                                (Pair) obj);
                                                            }
                                                        })
                                                .toArray(
                                                        new IntFunction() { // from class:
                                                            // android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda2
                                                            @Override // java.util.function.IntFunction
                                                            public final Object apply(int i) {
                                                                return AudioPolicy
                                                                        .lambda$updateMixingRules$1(
                                                                                i);
                                                            }
                                                        }),
                                (AudioMixingRule[])
                                        mixingRuleUpdates.stream()
                                                .map(
                                                        new Function() { // from class:
                                                            // android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda3
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                return AudioPolicy
                                                                        .lambda$updateMixingRules$2(
                                                                                (Pair) obj);
                                                            }
                                                        })
                                                .toArray(
                                                        new IntFunction() { // from class:
                                                            // android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda4
                                                            @Override // java.util.function.IntFunction
                                                            public final Object apply(int i) {
                                                                return AudioPolicy
                                                                        .lambda$updateMixingRules$3(
                                                                                i);
                                                            }
                                                        }),
                                cb());
                if (status == 0) {
                    this.mConfig.updateMixingRules(mixingRuleUpdates);
                }
            }
            return status;
        } catch (RemoteException e) {
            Log.e(TAG, "Received remote exeception in updateMixingRules call: ", e);
            return -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ AudioMix lambda$updateMixingRules$0(Pair p) {
        return (AudioMix) p.first;
    }

    static /* synthetic */ AudioMix[] lambda$updateMixingRules$1(int x$0) {
        return new AudioMix[x$0];
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ AudioMixingRule lambda$updateMixingRules$2(Pair p) {
        return (AudioMixingRule) p.second;
    }

    static /* synthetic */ AudioMixingRule[] lambda$updateMixingRules$3(int x$0) {
        return new AudioMixingRule[x$0];
    }

    @SystemApi
    public boolean setUidDeviceAffinity(int uid, List<AudioDeviceInfo> devices) {
        boolean z;
        if (devices == null) {
            throw new IllegalArgumentException("Illegal null list of audio devices");
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            int[] deviceTypes = new int[devices.size()];
            String[] deviceAdresses = new String[devices.size()];
            int i = 0;
            for (AudioDeviceInfo device : devices) {
                if (device == null) {
                    throw new IllegalArgumentException(
                            "Illegal null AudioDeviceInfo in setUidDeviceAffinity");
                }
                deviceTypes[i] =
                        AudioDeviceInfo.convertDeviceTypeToInternalDevice(device.getType());
                deviceAdresses[i] = device.getAddress();
                i++;
            }
            IAudioService service = getService();
            try {
                int status = service.setUidDeviceAffinity(cb(), uid, deviceTypes, deviceAdresses);
                z = status == 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in setUidDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    @SystemApi
    public boolean removeUidDeviceAffinity(int uid) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            IAudioService service = getService();
            try {
                int status = service.removeUidDeviceAffinity(cb(), uid);
                z = status == 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in removeUidDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    @SystemApi
    public boolean removeUserIdDeviceAffinity(int userId) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            IAudioService service = getService();
            try {
                int status = service.removeUserIdDeviceAffinity(cb(), userId);
                z = status == 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in removeUserIdDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    @SystemApi
    public boolean setUserIdDeviceAffinity(int userId, List<AudioDeviceInfo> devices) {
        boolean z;
        Objects.requireNonNull(devices, "Illegal null list of audio devices");
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            int[] deviceTypes = new int[devices.size()];
            String[] deviceAddresses = new String[devices.size()];
            int i = 0;
            for (AudioDeviceInfo device : devices) {
                if (device == null) {
                    throw new IllegalArgumentException(
                            "Illegal null AudioDeviceInfo in setUserIdDeviceAffinity");
                }
                deviceTypes[i] =
                        AudioDeviceInfo.convertDeviceTypeToInternalDevice(device.getType());
                deviceAddresses[i] = device.getAddress();
                i++;
            }
            IAudioService service = getService();
            try {
                int status =
                        service.setUserIdDeviceAffinity(cb(), userId, deviceTypes, deviceAddresses);
                z = status == 0;
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in setUserIdDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    public void reset() {
        setRegistration(null);
    }

    public List<AudioMix> getMixes() {
        List<AudioMix> copyOf;
        if (!Flags.audioMixTestApi()) {
            return Collections.emptyList();
        }
        synchronized (this.mLock) {
            copyOf = List.copyOf(this.mConfig.getMixes());
        }
        return copyOf;
    }

    public void setRegistration(String regId) {
        synchronized (this.mLock) {
            this.mRegistrationId = regId;
            this.mConfig.setRegistration(regId);
            if (regId != null) {
                this.mStatus = 2;
            } else {
                this.mStatus = 1;
                this.mConfig.reset();
            }
        }
        sendMsg(0);
    }

    public String getRegistration() {
        return this.mRegistrationId;
    }

    @SystemApi
    public int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fmcForFocusLoss) {
        int fadeManagerConfigurationForFocusLoss;
        Objects.requireNonNull(
                fmcForFocusLoss, "FadeManagerConfiguration for focus loss cannot be null");
        IAudioService service = getService();
        synchronized (this.mLock) {
            Preconditions.checkState(
                    isAudioPolicyRegisteredLocked(),
                    "Cannot set FadeManagerConfiguration with unregistered AudioPolicy");
            try {
                fadeManagerConfigurationForFocusLoss =
                        service.setFadeManagerConfigurationForFocusLoss(fmcForFocusLoss);
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Received remote exception for setFadeManagerConfigurationForFocusLoss:",
                        e);
                throw e.rethrowFromSystemServer();
            }
        }
        return fadeManagerConfigurationForFocusLoss;
    }

    @SystemApi
    public int clearFadeManagerConfigurationForFocusLoss() {
        int clearFadeManagerConfigurationForFocusLoss;
        IAudioService service = getService();
        synchronized (this.mLock) {
            Preconditions.checkState(
                    isAudioPolicyRegisteredLocked(),
                    "Cannot clear FadeManagerConfiguration from unregistered AudioPolicy");
            try {
                clearFadeManagerConfigurationForFocusLoss =
                        service.clearFadeManagerConfigurationForFocusLoss();
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Received remote exception for clearFadeManagerConfigurationForFocusLoss:",
                        e);
                throw e.rethrowFromSystemServer();
            }
        }
        return clearFadeManagerConfigurationForFocusLoss;
    }

    @SystemApi
    public FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() {
        FadeManagerConfiguration fadeManagerConfigurationForFocusLoss;
        IAudioService service = getService();
        synchronized (this.mLock) {
            Preconditions.checkState(
                    isAudioPolicyRegisteredLocked(),
                    "Cannot get FadeManagerConfiguration from unregistered AudioPolicy");
            try {
                fadeManagerConfigurationForFocusLoss =
                        service.getFadeManagerConfigurationForFocusLoss();
            } catch (RemoteException e) {
                Log.e(
                        TAG,
                        "Received remote exception for getFadeManagerConfigurationForFocusLoss:",
                        e);
                throw e.rethrowFromSystemServer();
            }
        }
        return fadeManagerConfigurationForFocusLoss;
    }

    private boolean isAudioPolicyRegisteredLocked() {
        return this.mStatus == 2;
    }

    private boolean policyReadyToUse() {
        boolean canProjectAudio;
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                Log.e(TAG, "Cannot use unregistered AudioPolicy");
                return false;
            }
            if (this.mRegistrationId == null) {
                Log.e(TAG, "Cannot use unregistered AudioPolicy");
                return false;
            }
            boolean canModifyAudioRouting =
                    checkCallingOrSelfPermission(Manifest.permission.MODIFY_AUDIO_ROUTING) == 0;
            boolean canInterceptCallAudio =
                    checkCallingOrSelfPermission(Manifest.permission.CALL_AUDIO_INTERCEPTION) == 0;
            try {
                if (this.mProjection != null) {
                    if (this.mProjection.getProjection().canProjectAudio()) {
                        canProjectAudio = true;
                        if ((!isLoopbackRenderPolicy() && canProjectAudio)
                                || ((isCallRedirectionPolicy() && canInterceptCallAudio)
                                        || canModifyAudioRouting)) {
                            return true;
                        }
                        Slog.w(
                                TAG,
                                "Cannot use AudioPolicy for pid "
                                        + Binder.getCallingPid()
                                        + " / uid "
                                        + Binder.getCallingUid()
                                        + ", needs MODIFY_AUDIO_ROUTING or MediaProjection that can"
                                        + " project audio.");
                        return false;
                    }
                }
                canProjectAudio = false;
                if (!isLoopbackRenderPolicy()) {}
                Slog.w(
                        TAG,
                        "Cannot use AudioPolicy for pid "
                                + Binder.getCallingPid()
                                + " / uid "
                                + Binder.getCallingUid()
                                + ", needs MODIFY_AUDIO_ROUTING or MediaProjection that can project"
                                + " audio.");
                return false;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to check if MediaProjection#canProjectAudio");
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private boolean isLoopbackRenderPolicy() {
        boolean allMatch;
        synchronized (this.mLock) {
            allMatch =
                    this.mConfig.mMixes.stream()
                            .allMatch(
                                    new Predicate() { // from class:
                                        // android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return AudioPolicy.lambda$isLoopbackRenderPolicy$4(
                                                    (AudioMix) obj);
                                        }
                                    });
        }
        return allMatch;
    }

    static /* synthetic */ boolean lambda$isLoopbackRenderPolicy$4(AudioMix mix) {
        return mix.getRouteFlags() == 3;
    }

    private boolean isCallRedirectionPolicy() {
        synchronized (this.mLock) {
            Iterator<AudioMix> it = this.mConfig.mMixes.iterator();
            while (it.hasNext()) {
                AudioMix mix = it.next();
                if (mix.isForCallRedirection()) {
                    return true;
                }
            }
            return false;
        }
    }

    private int checkCallingOrSelfPermission(String permission) {
        if (this.mContext != null) {
            return this.mContext.checkCallingOrSelfPermission(permission);
        }
        Slog.v(TAG, "Null context, checking permission via ActivityManager");
        int pid = Binder.getCallingPid();
        int uid = Binder.getCallingUid();
        try {
            return ActivityManager.getService().checkPermission(permission, pid, uid);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void checkMixReadyToUse(AudioMix mix, boolean forTrack)
            throws IllegalArgumentException {
        if (mix == null) {
            String msg =
                    forTrack
                            ? "Invalid null AudioMix for AudioTrack creation"
                            : "Invalid null AudioMix for AudioRecord creation";
            throw new IllegalArgumentException(msg);
        }
        if (!this.mConfig.mMixes.contains(mix)) {
            throw new IllegalArgumentException("Invalid mix: not part of this policy");
        }
        if ((mix.getRouteFlags() & 2) != 2) {
            throw new IllegalArgumentException("Invalid AudioMix: not defined for loop back");
        }
        if (forTrack && mix.getMixType() != 1) {
            throw new IllegalArgumentException(
                    "Invalid AudioMix: not defined for being a recording source");
        }
        if (!forTrack && mix.getMixType() != 0) {
            throw new IllegalArgumentException(
                    "Invalid AudioMix: not defined for capturing playback");
        }
    }

    public int getFocusDuckingBehavior() {
        return this.mConfig.mDuckingPolicy;
    }

    public int setFocusDuckingBehavior(int behavior)
            throws IllegalArgumentException, IllegalStateException {
        int status;
        if (behavior != 0 && behavior != 1) {
            throw new IllegalArgumentException("Invalid ducking behavior " + behavior);
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new IllegalStateException(
                        "Cannot change ducking behavior for unregistered policy");
            }
            if (behavior == 1 && this.mFocusListener == null) {
                throw new IllegalStateException(
                        "Cannot handle ducking without an audio focus listener");
            }
            IAudioService service = getService();
            try {
                status = service.setFocusPropertiesForPolicy(behavior, cb());
                if (status == 0) {
                    this.mConfig.mDuckingPolicy = behavior;
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in setFocusPropertiesForPolicy for behavior", e);
                return -1;
            }
        }
        return status;
    }

    public List<AudioFocusInfo> getFocusStack() {
        try {
            return getService().getFocusStack();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendFocusLoss(AudioFocusInfo focusLoser) throws IllegalStateException {
        Objects.requireNonNull(focusLoser);
        try {
            return getService().sendFocusLoss(focusLoser, cb());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AudioRecord createAudioRecordSink(AudioMix mix) throws IllegalArgumentException {
        if (!policyReadyToUse()) {
            Log.e(TAG, "Cannot create AudioRecord sink for AudioMix");
            return null;
        }
        checkMixReadyToUse(mix, false);
        AudioFormat mixFormat =
                new AudioFormat.Builder(mix.getFormat())
                        .setChannelMask(
                                AudioFormat.inChannelMaskFromOutChannelMask(
                                        mix.getFormat().getChannelMask()))
                        .build();
        AudioAttributes.Builder ab =
                new AudioAttributes.Builder()
                        .setInternalCapturePreset(8)
                        .addTag(addressForTag(mix))
                        .addTag(AudioRecord.SUBMIX_FIXED_VOLUME);
        if (mix.isForCallRedirection()) {
            ab.setForCallRedirection();
        }
        AudioRecord ar =
                new AudioRecord(
                        ab.build(),
                        mixFormat,
                        AudioRecord.getMinBufferSize(
                                mix.getFormat().getSampleRate(), 12, mix.getFormat().getEncoding()),
                        0);
        synchronized (this.mLock) {
            if (this.mCaptors == null) {
                this.mCaptors = new ArrayList<>(1);
            }
            this.mCaptors.add(new WeakReference<>(ar));
        }
        return ar;
    }

    public AudioTrack createAudioTrackSource(AudioMix mix) throws IllegalArgumentException {
        if (!policyReadyToUse()) {
            Log.e(TAG, "Cannot create AudioTrack source for AudioMix");
            return null;
        }
        checkMixReadyToUse(mix, true);
        AudioAttributes.Builder ab =
                new AudioAttributes.Builder().setUsage(15).addTag(addressForTag(mix));
        if (mix.isForCallRedirection()) {
            ab.setForCallRedirection();
        }
        AudioTrack at =
                new AudioTrack(
                        ab.build(),
                        mix.getFormat(),
                        AudioTrack.getMinBufferSize(
                                mix.getFormat().getSampleRate(),
                                mix.getFormat().getChannelMask(),
                                mix.getFormat().getEncoding()),
                        1,
                        0);
        synchronized (this.mLock) {
            if (this.mInjectors == null) {
                this.mInjectors = new ArrayList<>(1);
            }
            this.mInjectors.add(new WeakReference<>(at));
        }
        return at;
    }

    public void invalidateCaptorsAndInjectors() {
        if (!policyReadyToUse()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mInjectors != null) {
                Iterator<WeakReference<AudioTrack>> it = this.mInjectors.iterator();
                while (it.hasNext()) {
                    WeakReference<AudioTrack> weakTrack = it.next();
                    AudioTrack track = weakTrack.get();
                    if (track != null) {
                        try {
                            track.stop();
                            track.flush();
                        } catch (IllegalStateException e) {
                        }
                    }
                }
                this.mInjectors.clear();
            }
            if (this.mCaptors != null) {
                Iterator<WeakReference<AudioRecord>> it2 = this.mCaptors.iterator();
                while (it2.hasNext()) {
                    WeakReference<AudioRecord> weakRecord = it2.next();
                    AudioRecord record = weakRecord.get();
                    if (record != null) {
                        try {
                            record.stop();
                        } catch (IllegalStateException e2) {
                        }
                    }
                }
                this.mCaptors.clear();
            }
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    public abstract static class AudioPolicyStatusListener {
        public void onStatusChange() {}

        public void onMixStateUpdate(AudioMix mix) {}
    }

    public abstract static class AudioPolicyFocusListener {
        public void onAudioFocusGrant(AudioFocusInfo afi, int requestResult) {}

        public void onAudioFocusLoss(AudioFocusInfo afi, boolean wasNotified) {}

        public void onAudioFocusRequest(AudioFocusInfo afi, int requestResult) {}

        public void onAudioFocusAbandon(AudioFocusInfo afi) {}
    }

    public abstract static class AudioPolicyVolumeCallback {
        public void onVolumeAdjustment(int adjustment) {}
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPolicyStatusChange() {
        if (this.mStatusListener != null) {
            this.mStatusListener.onStatusChange();
        }
    }

    public IAudioPolicyCallback cb() {
        return this.mPolicyCb;
    }

    private class EventHandler extends Handler {
        public EventHandler(AudioPolicy ap, Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AudioPolicy.this.onPolicyStatusChange();
                    break;
                case 1:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusGrant(
                                (AudioFocusInfo) msg.obj, msg.arg1);
                        break;
                    }
                    break;
                case 2:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusLoss(
                                (AudioFocusInfo) msg.obj, msg.arg1 != 0);
                        break;
                    }
                    break;
                case 3:
                    if (AudioPolicy.this.mStatusListener != null) {
                        AudioPolicy.this.mStatusListener.onMixStateUpdate((AudioMix) msg.obj);
                        break;
                    }
                    break;
                case 4:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusRequest(
                                (AudioFocusInfo) msg.obj, msg.arg1);
                        break;
                    } else {
                        Log.e(
                                AudioPolicy.TAG,
                                "Invalid null focus listener for focus request event");
                        break;
                    }
                case 5:
                    if (AudioPolicy.this.mFocusListener != null) {
                        AudioPolicy.this.mFocusListener.onAudioFocusAbandon(
                                (AudioFocusInfo) msg.obj);
                        break;
                    } else {
                        Log.e(
                                AudioPolicy.TAG,
                                "Invalid null focus listener for focus abandon event");
                        break;
                    }
                case 6:
                    if (AudioPolicy.this.mVolCb != null) {
                        AudioPolicy.this.mVolCb.onVolumeAdjustment(msg.arg1);
                        break;
                    } else {
                        Log.e(AudioPolicy.TAG, "Invalid null volume event");
                        break;
                    }
                default:
                    Log.e(AudioPolicy.TAG, "Unknown event " + msg.what);
                    break;
            }
        }
    }

    private static String addressForTag(AudioMix mix) {
        return "addr=" + mix.getRegistration();
    }

    private void sendMsg(int msg) {
        if (this.mEventHandler != null) {
            this.mEventHandler.sendEmptyMessage(msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMsg(int msg, Object obj, int i) {
        if (this.mEventHandler != null) {
            this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(msg, i, 0, obj));
        }
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }

    public String toLogFriendlyString() {
        String textDump = new String("android.media.audiopolicy.AudioPolicy:\n");
        return textDump + "config=" + this.mConfig.toLogFriendlyString();
    }
}
