package android.media;

import android.annotation.SystemApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceVolumeManager;
import android.media.CallbackUtil;
import android.media.IAudioDeviceVolumeDispatcher;
import android.media.IAudioService;
import android.media.IDeviceVolumeBehaviorDispatcher;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes2.dex */
public class AudioDeviceVolumeManager {
    public static final int ADJUST_MODE_END = 2;
    public static final int ADJUST_MODE_NORMAL = 0;
    public static final int ADJUST_MODE_START = 1;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE = 3;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY = 5;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_MULTI_MODE = 4;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_FIXED = 2;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_FULL = 1;
    public static final int DEVICE_VOLUME_BEHAVIOR_UNSET = -1;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_VARIABLE = 0;
    private static final String TAG = "AudioDeviceVolumeManager";
    private static IAudioService sService;
    private DeviceVolumeDispatcherStub mDeviceVolumeDispatcherStub;
    private ArrayList<ListenerInfo> mDeviceVolumeListeners;
    private final String mPackageName;
    private final Object mDeviceVolumeListenerLock = new Object();
    private final CallbackUtil.LazyListenerManager<OnDeviceVolumeBehaviorChangedListener> mDeviceVolumeBehaviorChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface AbsoluteDeviceVolumeBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceVolumeBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceVolumeBehaviorState {
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public interface OnAudioDeviceVolumeChangedListener {
        void onAudioDeviceVolumeAdjusted(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, int i, int i2);

        void onAudioDeviceVolumeChanged(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo);
    }

    public interface OnDeviceVolumeBehaviorChangedListener {
        void onDeviceVolumeBehaviorChanged(AudioDeviceAttributes audioDeviceAttributes, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VolumeAdjustmentMode {
    }

    public static void enforceValidVolumeBehavior(int i) {
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            return;
        }
        throw new IllegalArgumentException("Illegal volume behavior " + i);
    }

    public AudioDeviceVolumeManager(Context context) {
        Objects.requireNonNull(context);
        this.mPackageName = context.getApplicationContext().getOpPackageName();
    }

    static class ListenerInfo {
        final AudioDeviceAttributes mDevice;
        final Executor mExecutor;
        final boolean mHandlesVolumeAdjustment;
        final OnAudioDeviceVolumeChangedListener mListener;

        ListenerInfo(OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, Executor executor, AudioDeviceAttributes audioDeviceAttributes, boolean z) {
            this.mListener = onAudioDeviceVolumeChangedListener;
            this.mExecutor = executor;
            this.mDevice = audioDeviceAttributes;
            this.mHandlesVolumeAdjustment = z;
        }
    }

    final class DeviceVolumeDispatcherStub extends IAudioDeviceVolumeDispatcher.Stub {
        DeviceVolumeDispatcherStub() {
        }

        public void register(boolean z, AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, boolean z2, int i) {
            try {
                AudioDeviceVolumeManager.getService().registerDeviceVolumeDispatcherForAbsoluteVolume(z, this, AudioDeviceVolumeManager.this.mPackageName, (AudioDeviceAttributes) Objects.requireNonNull(audioDeviceAttributes), (List) Objects.requireNonNull(list), z2, i);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeChanged(final AudioDeviceAttributes audioDeviceAttributes, final VolumeInfo volumeInfo) {
            ArrayList arrayList;
            synchronized (AudioDeviceVolumeManager.this.mDeviceVolumeListenerLock) {
                arrayList = (ArrayList) AudioDeviceVolumeManager.this.mDeviceVolumeListeners.clone();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                final ListenerInfo listenerInfo = (ListenerInfo) it.next();
                if (listenerInfo.mDevice.equalTypeAddress(audioDeviceAttributes)) {
                    listenerInfo.mExecutor.execute(new Runnable() { // from class: android.media.AudioDeviceVolumeManager$DeviceVolumeDispatcherStub$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            listenerInfo.mListener.onAudioDeviceVolumeChanged(audioDeviceAttributes, volumeInfo);
                        }
                    });
                }
            }
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeAdjusted(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, int i, int i2) {
            ArrayList arrayList;
            final AudioDeviceAttributes audioDeviceAttributes2;
            final VolumeInfo volumeInfo2;
            final int i3;
            final int i4;
            synchronized (AudioDeviceVolumeManager.this.mDeviceVolumeListenerLock) {
                arrayList = (ArrayList) AudioDeviceVolumeManager.this.mDeviceVolumeListeners.clone();
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                final ListenerInfo listenerInfo = (ListenerInfo) it.next();
                if (listenerInfo.mDevice.equalTypeAddress(audioDeviceAttributes)) {
                    audioDeviceAttributes2 = audioDeviceAttributes;
                    volumeInfo2 = volumeInfo;
                    i3 = i;
                    i4 = i2;
                    listenerInfo.mExecutor.execute(new Runnable() { // from class: android.media.AudioDeviceVolumeManager$DeviceVolumeDispatcherStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            listenerInfo.mListener.onAudioDeviceVolumeAdjusted(audioDeviceAttributes2, volumeInfo2, i3, i4);
                        }
                    });
                } else {
                    audioDeviceAttributes2 = audioDeviceAttributes;
                    volumeInfo2 = volumeInfo;
                    i3 = i;
                    i4 = i2;
                }
                audioDeviceAttributes = audioDeviceAttributes2;
                volumeInfo = volumeInfo2;
                i = i3;
                i2 = i4;
            }
        }
    }

    @SystemApi
    public void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i) {
        Objects.requireNonNull(audioDeviceAttributes);
        enforceValidVolumeBehavior(i);
        try {
            getService().setDeviceVolumeBehavior(audioDeviceAttributes, i, this.mPackageName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            return getService().getDeviceVolumeBehavior(audioDeviceAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFullVolumeDevice() {
        try {
            Iterator<AudioDeviceAttributes> it = getService().getDevicesForAttributes(new AudioAttributes.Builder().setUsage(1).build()).iterator();
            while (it.hasNext()) {
                if (getDeviceVolumeBehavior(it.next()) == 1) {
                    return true;
                }
            }
            return false;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setDeviceAbsoluteVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener) {
        setDeviceAbsoluteVolumeBehavior(audioDeviceAttributes, volumeInfo, false, executor, onAudioDeviceVolumeChangedListener);
    }

    public void setDeviceAbsoluteVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, boolean z, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(volumeInfo);
        setDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, arrayList, z, executor, onAudioDeviceVolumeChangedListener);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setDeviceAbsoluteMultiVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener) {
        setDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, list, false, executor, onAudioDeviceVolumeChangedListener);
    }

    public void setDeviceAbsoluteMultiVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, boolean z, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener) {
        baseSetDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, list, executor, onAudioDeviceVolumeChangedListener, z, 3);
    }

    public void setDeviceAbsoluteVolumeAdjustOnlyBehavior(AudioDeviceAttributes audioDeviceAttributes, VolumeInfo volumeInfo, boolean z, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(volumeInfo);
        setDeviceAbsoluteMultiVolumeAdjustOnlyBehavior(audioDeviceAttributes, arrayList, z, executor, onAudioDeviceVolumeChangedListener);
    }

    public void setDeviceAbsoluteMultiVolumeAdjustOnlyBehavior(AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, boolean z, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener) {
        baseSetDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, list, executor, onAudioDeviceVolumeChangedListener, z, 5);
    }

    private void baseSetDeviceAbsoluteMultiVolumeBehavior(final AudioDeviceAttributes audioDeviceAttributes, List<VolumeInfo> list, Executor executor, OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z, int i) {
        Objects.requireNonNull(audioDeviceAttributes);
        Objects.requireNonNull(list);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onAudioDeviceVolumeChangedListener);
        ListenerInfo listenerInfo = new ListenerInfo(onAudioDeviceVolumeChangedListener, executor, audioDeviceAttributes, z);
        synchronized (this.mDeviceVolumeListenerLock) {
            if (this.mDeviceVolumeListeners == null) {
                this.mDeviceVolumeListeners = new ArrayList<>();
            }
            if (this.mDeviceVolumeListeners.size() == 0) {
                if (this.mDeviceVolumeDispatcherStub == null) {
                    this.mDeviceVolumeDispatcherStub = new DeviceVolumeDispatcherStub();
                }
            } else {
                this.mDeviceVolumeListeners.removeIf(new Predicate() { // from class: android.media.AudioDeviceVolumeManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((AudioDeviceVolumeManager.ListenerInfo) obj).mDevice.equalTypeAddress(audioDeviceAttributes);
                    }
                });
            }
            this.mDeviceVolumeListeners.add(listenerInfo);
            this.mDeviceVolumeDispatcherStub.register(true, audioDeviceAttributes, list, z, i);
        }
    }

    public void addOnDeviceVolumeBehaviorChangedListener(Executor executor, OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener) throws SecurityException {
        this.mDeviceVolumeBehaviorChangedListenerMgr.addListener(executor, onDeviceVolumeBehaviorChangedListener, "addOnDeviceVolumeBehaviorChangedListener", new Supplier() { // from class: android.media.AudioDeviceVolumeManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnDeviceVolumeBehaviorChangedListener$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnDeviceVolumeBehaviorChangedListener$1() {
        return new DeviceVolumeBehaviorDispatcherStub();
    }

    public void removeOnDeviceVolumeBehaviorChangedListener(OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener) {
        this.mDeviceVolumeBehaviorChangedListenerMgr.removeListener(onDeviceVolumeBehaviorChangedListener, "removeOnDeviceVolumeBehaviorChangedListener");
    }

    @SystemApi
    public void setDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes) {
        try {
            getService().setDeviceVolume(volumeInfo, audioDeviceAttributes, this.mPackageName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public VolumeInfo getDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes) {
        try {
            return getService().getDeviceVolume(volumeInfo, audioDeviceAttributes, this.mPackageName);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return VolumeInfo.getDefaultVolumeInfo();
        }
    }

    public void setInputGainIndex(AudioDeviceAttributes audioDeviceAttributes, int i) {
        try {
            getService().setInputGainIndex(audioDeviceAttributes, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getInputGainIndex(AudioDeviceAttributes audioDeviceAttributes) {
        try {
            return getService().getInputGainIndex(audioDeviceAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxInputGainIndex() {
        try {
            return getService().getMaxInputGainIndex();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMinInputGainIndex() {
        try {
            return getService().getMinInputGainIndex();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInputGainFixed(AudioDeviceAttributes audioDeviceAttributes) {
        try {
            return getService().isInputGainFixed(audioDeviceAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String volumeBehaviorName(int i) {
        if (i == 0) {
            return "DEVICE_VOLUME_BEHAVIOR_VARIABLE";
        }
        if (i == 1) {
            return "DEVICE_VOLUME_BEHAVIOR_FULL";
        }
        if (i == 2) {
            return "DEVICE_VOLUME_BEHAVIOR_FIXED";
        }
        if (i == 3) {
            return "DEVICE_VOLUME_BEHAVIOR_ABSOLUTE";
        }
        if (i == 4) {
            return "DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_MULTI_MODE";
        }
        if (i == 5) {
            return "DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY";
        }
        return "invalid volume behavior " + i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DeviceVolumeBehaviorDispatcherStub extends IDeviceVolumeBehaviorDispatcher.Stub implements CallbackUtil.DispatcherStub {
        private DeviceVolumeBehaviorDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                AudioDeviceVolumeManager.getService().registerDeviceVolumeBehaviorDispatcher(z, this);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IDeviceVolumeBehaviorDispatcher
        public void dispatchDeviceVolumeBehaviorChanged(final AudioDeviceAttributes audioDeviceAttributes, final int i) {
            AudioDeviceVolumeManager.this.mDeviceVolumeBehaviorChangedListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioDeviceVolumeManager$DeviceVolumeBehaviorDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener) obj).onDeviceVolumeBehaviorChanged(audioDeviceAttributes, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        IAudioService iAudioServiceAsInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        sService = iAudioServiceAsInterface;
        return iAudioServiceAsInterface;
    }
}
