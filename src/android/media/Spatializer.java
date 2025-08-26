package android.media;

import android.annotation.SystemApi;
import android.media.CallbackUtil;
import android.media.ISpatializerCallback;
import android.media.ISpatializerHeadToSoundStagePoseCallback;
import android.media.ISpatializerHeadTrackerAvailableCallback;
import android.media.ISpatializerHeadTrackingModeCallback;
import android.media.ISpatializerOutputCallback;
import android.media.Spatializer;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.os.RemoteException;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class Spatializer {

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_DISABLED = -1;

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_OTHER = 0;

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_RELATIVE_DEVICE = 2;

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_RELATIVE_WORLD = 1;

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_UNSUPPORTED = -2;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_MCHAN_BED_PLUS_OBJECTS = 2;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_MULTICHANNEL = 1;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_NONE = 0;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_OTHER = -1;
    private static final String TAG = "Spatializer";
    private final AudioManager mAm;
    private SpatializerOutputDispatcherStub mOutputDispatcher;
    private CallbackUtil.ListenerInfo<OnSpatializerOutputChangedListener> mOutputListener;
    private SpatializerPoseDispatcherStub mPoseDispatcher;
    private CallbackUtil.ListenerInfo<OnHeadToSoundstagePoseUpdatedListener> mPoseListener;
    private final CallbackUtil.LazyListenerManager<OnSpatializerStateChangedListener> mStateListenerMgr = new CallbackUtil.LazyListenerManager<>();
    private final CallbackUtil.LazyListenerManager<OnHeadTrackingModeChangedListener> mHeadTrackingListenerMgr = new CallbackUtil.LazyListenerManager<>();
    private final CallbackUtil.LazyListenerManager<OnHeadTrackerAvailableListener> mHeadTrackerListenerMgr = new CallbackUtil.LazyListenerManager<>();
    private final Object mPoseListenerLock = new Object();
    private final Object mOutputListenerLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface HeadTrackingMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HeadTrackingModeSet {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HeadTrackingModeSupported {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImmersiveAudioLevel {
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public interface OnHeadToSoundstagePoseUpdatedListener {
        void onHeadToSoundstagePoseUpdated(Spatializer spatializer, float[] fArr);
    }

    public interface OnHeadTrackerAvailableListener {
        void onHeadTrackerAvailableChanged(Spatializer spatializer, boolean z);
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public interface OnHeadTrackingModeChangedListener {
        void onDesiredHeadTrackingModeChanged(Spatializer spatializer, int i);

        void onHeadTrackingModeChanged(Spatializer spatializer, int i);
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public interface OnSpatializerOutputChangedListener {
        void onSpatializerOutputChanged(Spatializer spatializer, int i);
    }

    public interface OnSpatializerStateChangedListener {
        void onSpatializerAvailableChanged(Spatializer spatializer, boolean z);

        void onSpatializerEnabledChanged(Spatializer spatializer, boolean z);
    }

    protected Spatializer(AudioManager audioManager) {
        this.mAm = (AudioManager) Objects.requireNonNull(audioManager);
    }

    public boolean isEnabled() {
        try {
            return AudioManager.getService().isSpatializerEnabled();
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying isSpatializerEnabled, returning false", e);
            return false;
        }
    }

    public boolean isAvailable() {
        try {
            return AudioManager.getService().isSpatializerAvailable();
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying isSpatializerAvailable, returning false", e);
            return false;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public boolean isAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            return AudioManager.getService().isSpatializerAvailableForDevice(audioDeviceAttributes);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            return AudioManager.getService().hasHeadTracker(audioDeviceAttributes);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            AudioManager.getService().setHeadTrackerEnabled(z, audioDeviceAttributes);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public boolean isHeadTrackerEnabled(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            return AudioManager.getService().isHeadTrackerEnabled(audioDeviceAttributes);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isHeadTrackerAvailable() {
        try {
            return AudioManager.getService().isHeadTrackerAvailable();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void addOnHeadTrackerAvailableListener(Executor executor, OnHeadTrackerAvailableListener onHeadTrackerAvailableListener) {
        this.mHeadTrackerListenerMgr.addListener(executor, onHeadTrackerAvailableListener, "addOnHeadTrackerAvailableListener", new Supplier() { // from class: android.media.Spatializer$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnHeadTrackerAvailableListener$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnHeadTrackerAvailableListener$0() {
        return new SpatializerHeadTrackerAvailableDispatcherStub();
    }

    public void removeOnHeadTrackerAvailableListener(OnHeadTrackerAvailableListener onHeadTrackerAvailableListener) {
        this.mHeadTrackerListenerMgr.removeListener(onHeadTrackerAvailableListener, "removeOnHeadTrackerAvailableListener");
    }

    public static final String headtrackingModeToString(int i) {
        if (i == -2) {
            return "HEAD_TRACKING_MODE_UNSUPPORTED";
        }
        if (i == -1) {
            return "HEAD_TRACKING_MODE_DISABLED";
        }
        if (i == 0) {
            return "HEAD_TRACKING_MODE_OTHER";
        }
        if (i == 1) {
            return "HEAD_TRACKING_MODE_RELATIVE_WORLD";
        }
        if (i == 2) {
            return "HEAD_TRACKING_MODE_RELATIVE_DEVICE";
        }
        return "head tracking mode unknown " + i;
    }

    public int getImmersiveAudioLevel() {
        try {
            return AudioManager.getService().getSpatializerImmersiveAudioLevel();
        } catch (Exception unused) {
            return 0;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setEnabled(boolean z) {
        try {
            AudioManager.getService().setSpatializerEnabled(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling setSpatializerEnabled", e);
        }
    }

    public boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat) {
        try {
            return AudioManager.getService().canBeSpatialized((AudioAttributes) Objects.requireNonNull(audioAttributes), (AudioFormat) Objects.requireNonNull(audioFormat));
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying canBeSpatialized for attr:" + audioAttributes + " format:" + audioFormat + " returning false", e);
            return false;
        }
    }

    public List<Integer> getSpatializedChannelMasks() {
        try {
            return AudioManager.getService().getSpatializedChannelMasks();
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying getSpatializedChannelMasks", e);
            return Collections.EMPTY_LIST;
        }
    }

    public void addOnSpatializerStateChangedListener(Executor executor, OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
        this.mStateListenerMgr.addListener(executor, onSpatializerStateChangedListener, "addOnSpatializerStateChangedListener", new Supplier() { // from class: android.media.Spatializer$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnSpatializerStateChangedListener$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnSpatializerStateChangedListener$1() {
        return new SpatializerInfoDispatcherStub();
    }

    public void removeOnSpatializerStateChangedListener(OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
        this.mStateListenerMgr.removeListener(onSpatializerStateChangedListener, "removeOnSpatializerStateChangedListener");
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public List<AudioDeviceAttributes> getCompatibleAudioDevices() {
        try {
            return AudioManager.getService().getSpatializerCompatibleAudioDevices();
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying getSpatializerCompatibleAudioDevices(),  returning empty list", e);
            return new ArrayList(0);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void addCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        try {
            AudioManager.getService().addSpatializerCompatibleAudioDevice((AudioDeviceAttributes) Objects.requireNonNull(audioDeviceAttributes));
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling addSpatializerCompatibleAudioDevice(), ", e);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void removeCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        try {
            AudioManager.getService().removeSpatializerCompatibleAudioDevice((AudioDeviceAttributes) Objects.requireNonNull(audioDeviceAttributes));
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling removeSpatializerCompatibleAudioDevice(), ", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerInfoDispatcherStub extends ISpatializerCallback.Stub implements CallbackUtil.DispatcherStub {
        private SpatializerInfoDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager unused = Spatializer.this.mAm;
                    AudioManager.getService().registerSpatializerCallback(this);
                } else {
                    AudioManager unused2 = Spatializer.this.mAm;
                    AudioManager.getService().unregisterSpatializerCallback(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerEnabledChanged(final boolean z) {
            Spatializer.this.mStateListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerInfoDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    this.f$0.lambda$dispatchSpatializerEnabledChanged$0(z, (Spatializer.OnSpatializerStateChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerEnabledChanged$0(boolean z, OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
            onSpatializerStateChangedListener.onSpatializerEnabledChanged(Spatializer.this, z);
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerAvailableChanged(final boolean z) {
            Spatializer.this.mStateListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerInfoDispatcherStub$$ExternalSyntheticLambda1
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    this.f$0.lambda$dispatchSpatializerAvailableChanged$1(z, (Spatializer.OnSpatializerStateChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerAvailableChanged$1(boolean z, OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
            onSpatializerStateChangedListener.onSpatializerAvailableChanged(Spatializer.this, z);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public int getHeadTrackingMode() {
        try {
            return AudioManager.getService().getActualHeadTrackingMode();
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling getActualHeadTrackingMode", e);
            return -2;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public int getDesiredHeadTrackingMode() {
        try {
            return AudioManager.getService().getDesiredHeadTrackingMode();
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling getDesiredHeadTrackingMode", e);
            return -2;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public List<Integer> getSupportedHeadTrackingModes() {
        try {
            int[] supportedHeadTrackingModes = AudioManager.getService().getSupportedHeadTrackingModes();
            ArrayList arrayList = new ArrayList(0);
            for (int i : supportedHeadTrackingModes) {
                arrayList.add(Integer.valueOf(i));
            }
            return arrayList;
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling getSupportedHeadTrackModes", e);
            return new ArrayList(0);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setDesiredHeadTrackingMode(int i) {
        try {
            AudioManager.getService().setDesiredHeadTrackingMode(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling setDesiredHeadTrackingMode to " + i, e);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void recenterHeadTracker() {
        try {
            AudioManager.getService().recenterHeadTracker();
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling recenterHeadTracker", e);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void addOnHeadTrackingModeChangedListener(Executor executor, OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
        this.mHeadTrackingListenerMgr.addListener(executor, onHeadTrackingModeChangedListener, "addOnHeadTrackingModeChangedListener", new Supplier() { // from class: android.media.Spatializer$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnHeadTrackingModeChangedListener$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnHeadTrackingModeChangedListener$2() {
        return new SpatializerHeadTrackingDispatcherStub();
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void removeOnHeadTrackingModeChangedListener(OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
        this.mHeadTrackingListenerMgr.removeListener(onHeadTrackingModeChangedListener, "removeOnHeadTrackingModeChangedListener");
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setOnHeadToSoundstagePoseUpdatedListener(Executor executor, OnHeadToSoundstagePoseUpdatedListener onHeadToSoundstagePoseUpdatedListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onHeadToSoundstagePoseUpdatedListener);
        synchronized (this.mPoseListenerLock) {
            if (this.mPoseListener != null) {
                throw new IllegalStateException("Trying to overwrite existing listener");
            }
            this.mPoseListener = new CallbackUtil.ListenerInfo<>(onHeadToSoundstagePoseUpdatedListener, executor);
            this.mPoseDispatcher = new SpatializerPoseDispatcherStub();
            try {
                AudioManager.getService().registerHeadToSoundstagePoseCallback(this.mPoseDispatcher);
            } catch (RemoteException unused) {
                this.mPoseListener = null;
                this.mPoseDispatcher = null;
            }
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void clearOnHeadToSoundstagePoseUpdatedListener() {
        synchronized (this.mPoseListenerLock) {
            if (this.mPoseDispatcher == null) {
                throw new IllegalStateException("No listener to clear");
            }
            try {
                AudioManager.getService().unregisterHeadToSoundstagePoseCallback(this.mPoseDispatcher);
            } catch (RemoteException unused) {
            }
            this.mPoseListener = null;
            this.mPoseDispatcher = null;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setGlobalTransform(float[] fArr) {
        if (((float[]) Objects.requireNonNull(fArr)).length != 6) {
            throw new IllegalArgumentException("transform array must be of size 6, was " + fArr.length);
        }
        try {
            AudioManager.getService().setSpatializerGlobalTransform(fArr);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling setGlobalTransform", e);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setEffectParameter(int i, byte[] bArr) {
        Objects.requireNonNull(bArr);
        try {
            AudioManager.getService().setSpatializerParameter(i, bArr);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling setEffectParameter", e);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void getEffectParameter(int i, byte[] bArr) {
        Objects.requireNonNull(bArr);
        try {
            AudioManager.getService().getSpatializerParameter(i, bArr);
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling getEffectParameter", e);
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public int getOutput() {
        try {
            return AudioManager.getService().getSpatializerOutput();
        } catch (RemoteException e) {
            Log.e(TAG, "Error calling getSpatializerOutput", e);
            return 0;
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void setOnSpatializerOutputChangedListener(Executor executor, OnSpatializerOutputChangedListener onSpatializerOutputChangedListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onSpatializerOutputChangedListener);
        synchronized (this.mOutputListenerLock) {
            if (this.mOutputListener != null) {
                throw new IllegalStateException("Trying to overwrite existing listener");
            }
            this.mOutputListener = new CallbackUtil.ListenerInfo<>(onSpatializerOutputChangedListener, executor);
            this.mOutputDispatcher = new SpatializerOutputDispatcherStub();
            try {
                AudioManager.getService().registerSpatializerOutputCallback(this.mOutputDispatcher);
                this.mOutputDispatcher.dispatchSpatializerOutputChanged(getOutput());
            } catch (RemoteException unused) {
                this.mOutputListener = null;
                this.mOutputDispatcher = null;
            }
        }
    }

    @SystemApi(client = SystemApi.Client.PRIVILEGED_APPS)
    public void clearOnSpatializerOutputChangedListener() {
        synchronized (this.mOutputListenerLock) {
            if (this.mOutputDispatcher == null) {
                throw new IllegalStateException("No listener to clear");
            }
            try {
                AudioManager.getService().unregisterSpatializerOutputCallback(this.mOutputDispatcher);
            } catch (RemoteException unused) {
            }
            this.mOutputListener = null;
            this.mOutputDispatcher = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerHeadTrackingDispatcherStub extends ISpatializerHeadTrackingModeCallback.Stub implements CallbackUtil.DispatcherStub {
        private SpatializerHeadTrackingDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager unused = Spatializer.this.mAm;
                    AudioManager.getService().registerSpatializerHeadTrackingCallback(this);
                } else {
                    AudioManager unused2 = Spatializer.this.mAm;
                    AudioManager.getService().unregisterSpatializerHeadTrackingCallback(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerActualHeadTrackingModeChanged(final int i) {
            Spatializer.this.mHeadTrackingListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerHeadTrackingDispatcherStub$$ExternalSyntheticLambda1
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    this.f$0.lambda$dispatchSpatializerActualHeadTrackingModeChanged$0(i, (Spatializer.OnHeadTrackingModeChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerActualHeadTrackingModeChanged$0(int i, OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
            onHeadTrackingModeChangedListener.onHeadTrackingModeChanged(Spatializer.this, i);
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerDesiredHeadTrackingModeChanged(final int i) {
            Spatializer.this.mHeadTrackingListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerHeadTrackingDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    this.f$0.lambda$dispatchSpatializerDesiredHeadTrackingModeChanged$1(i, (Spatializer.OnHeadTrackingModeChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerDesiredHeadTrackingModeChanged$1(int i, OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
            onHeadTrackingModeChangedListener.onDesiredHeadTrackingModeChanged(Spatializer.this, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerHeadTrackerAvailableDispatcherStub extends ISpatializerHeadTrackerAvailableCallback.Stub implements CallbackUtil.DispatcherStub {
        private SpatializerHeadTrackerAvailableDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                AudioManager unused = Spatializer.this.mAm;
                AudioManager.getService().registerSpatializerHeadTrackerAvailableCallback(this, z);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ISpatializerHeadTrackerAvailableCallback
        public void dispatchSpatializerHeadTrackerAvailable(final boolean z) {
            Spatializer.this.mHeadTrackerListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerHeadTrackerAvailableDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    this.f$0.lambda$dispatchSpatializerHeadTrackerAvailable$0(z, (Spatializer.OnHeadTrackerAvailableListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerHeadTrackerAvailable$0(boolean z, OnHeadTrackerAvailableListener onHeadTrackerAvailableListener) {
            onHeadTrackerAvailableListener.onHeadTrackerAvailableChanged(Spatializer.this, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerPoseDispatcherStub extends ISpatializerHeadToSoundStagePoseCallback.Stub {
        private SpatializerPoseDispatcherStub() {
        }

        @Override // android.media.ISpatializerHeadToSoundStagePoseCallback
        public void dispatchPoseChanged(final float[] fArr) {
            final CallbackUtil.ListenerInfo listenerInfo;
            synchronized (Spatializer.this.mPoseListenerLock) {
                listenerInfo = Spatializer.this.mPoseListener;
            }
            if (listenerInfo == null) {
                return;
            }
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                listenerInfo.mExecutor.execute(new Runnable() { // from class: android.media.Spatializer$SpatializerPoseDispatcherStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$dispatchPoseChanged$0(listenerInfo, fArr);
                    }
                });
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
            } catch (Throwable th) {
                if (safeCloseableCreate != null) {
                    try {
                        safeCloseableCreate.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchPoseChanged$0(CallbackUtil.ListenerInfo listenerInfo, float[] fArr) {
            ((OnHeadToSoundstagePoseUpdatedListener) listenerInfo.mListener).onHeadToSoundstagePoseUpdated(Spatializer.this, fArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerOutputDispatcherStub extends ISpatializerOutputCallback.Stub {
        private SpatializerOutputDispatcherStub() {
        }

        @Override // android.media.ISpatializerOutputCallback
        public void dispatchSpatializerOutputChanged(final int i) {
            final CallbackUtil.ListenerInfo listenerInfo;
            synchronized (Spatializer.this.mOutputListenerLock) {
                listenerInfo = Spatializer.this.mOutputListener;
            }
            if (listenerInfo == null) {
                return;
            }
            SafeCloseable safeCloseableCreate = ClearCallingIdentityContext.create();
            try {
                listenerInfo.mExecutor.execute(new Runnable() { // from class: android.media.Spatializer$SpatializerOutputDispatcherStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$dispatchSpatializerOutputChanged$0(listenerInfo, i);
                    }
                });
                if (safeCloseableCreate != null) {
                    safeCloseableCreate.close();
                }
            } catch (Throwable th) {
                if (safeCloseableCreate != null) {
                    try {
                        safeCloseableCreate.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerOutputChanged$0(CallbackUtil.ListenerInfo listenerInfo, int i) {
            ((OnSpatializerOutputChangedListener) listenerInfo.mListener).onSpatializerOutputChanged(Spatializer.this, i);
        }
    }

    public void semSetEnabled(boolean z) {
        setEnabled(z);
    }
}
