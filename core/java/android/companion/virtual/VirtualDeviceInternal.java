package android.companion.virtual;

import android.app.PendingIntent;
import android.companion.virtual.audio.VirtualAudioDevice;
import android.companion.virtual.camera.VirtualCamera;
import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtualdevice.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.hardware.input.VirtualDpad;
import android.hardware.input.VirtualDpadConfig;
import android.hardware.input.VirtualKeyboard;
import android.hardware.input.VirtualKeyboardConfig;
import android.hardware.input.VirtualMouse;
import android.hardware.input.VirtualMouseConfig;
import android.hardware.input.VirtualNavigationTouchpad;
import android.hardware.input.VirtualNavigationTouchpadConfig;
import android.hardware.input.VirtualStylus;
import android.hardware.input.VirtualStylusConfig;
import android.hardware.input.VirtualTouchscreen;
import android.hardware.input.VirtualTouchscreenConfig;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.util.ArrayMap;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public class VirtualDeviceInternal {
    private final Context mContext;
    private final IVirtualDeviceManager mService;
    private VirtualAudioDevice mVirtualAudioDevice;
    private final IVirtualDevice mVirtualDevice;
    private final Object mActivityListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.ActivityListener, ActivityListenerDelegate>
            mActivityListeners = new ArrayMap<>();
    private final Object mIntentInterceptorListenersLock = new Object();
    private final ArrayMap<
                    VirtualDeviceManager.IntentInterceptorCallback, IntentInterceptorDelegate>
            mIntentInterceptorListeners = new ArrayMap<>();
    private final Object mSoundEffectListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.SoundEffectListener, SoundEffectListenerDelegate>
            mSoundEffectListeners = new ArrayMap<>();
    private final IVirtualDeviceActivityListener mActivityListenerBinder =
            new IVirtualDeviceActivityListener
                    .Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.1
                @Override // android.companion.virtual.IVirtualDeviceActivityListener
                public void onTopActivityChanged(
                        int displayId, ComponentName topActivity, int userId) {
                    long token = Binder.clearCallingIdentity();
                    try {
                        synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                            for (int i = 0;
                                    i < VirtualDeviceInternal.this.mActivityListeners.size();
                                    i++) {
                                ((ActivityListenerDelegate)
                                                VirtualDeviceInternal.this.mActivityListeners
                                                        .valueAt(i))
                                        .onTopActivityChanged(displayId, topActivity);
                                ((ActivityListenerDelegate)
                                                VirtualDeviceInternal.this.mActivityListeners
                                                        .valueAt(i))
                                        .onTopActivityChanged(displayId, topActivity, userId);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(token);
                    }
                }

                @Override // android.companion.virtual.IVirtualDeviceActivityListener
                public void onDisplayEmpty(int displayId) {
                    long token = Binder.clearCallingIdentity();
                    try {
                        synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                            for (int i = 0;
                                    i < VirtualDeviceInternal.this.mActivityListeners.size();
                                    i++) {
                                ((ActivityListenerDelegate)
                                                VirtualDeviceInternal.this.mActivityListeners
                                                        .valueAt(i))
                                        .onDisplayEmpty(displayId);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(token);
                    }
                }
            };
    private final IVirtualDeviceSoundEffectListener mSoundEffectListener =
            new IVirtualDeviceSoundEffectListener
                    .Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.2
                @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
                public void onPlaySoundEffect(int soundEffect) {
                    long token = Binder.clearCallingIdentity();
                    try {
                        synchronized (VirtualDeviceInternal.this.mSoundEffectListenersLock) {
                            for (int i = 0;
                                    i < VirtualDeviceInternal.this.mSoundEffectListeners.size();
                                    i++) {
                                ((SoundEffectListenerDelegate)
                                                VirtualDeviceInternal.this.mSoundEffectListeners
                                                        .valueAt(i))
                                        .onPlaySoundEffect(soundEffect);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(token);
                    }
                }
            };

    VirtualDeviceInternal(
            IVirtualDeviceManager service,
            Context context,
            int associationId,
            VirtualDeviceParams params)
            throws RemoteException {
        this.mService = service;
        this.mContext = context.getApplicationContext();
        this.mVirtualDevice =
                service.createVirtualDevice(
                        new Binder(),
                        this.mContext.getAttributionSource(),
                        associationId,
                        params,
                        this.mActivityListenerBinder,
                        this.mSoundEffectListener);
    }

    int getDeviceId() {
        try {
            return this.mVirtualDevice.getDeviceId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    String getPersistentDeviceId() {
        try {
            return this.mVirtualDevice.getPersistentDeviceId();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    Context createContext() {
        try {
            return this.mContext.createDeviceContext(this.mVirtualDevice.getDeviceId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    List<VirtualSensor> getVirtualSensorList() {
        try {
            return this.mVirtualDevice.getVirtualSensorList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void launchPendingIntent(
            int displayId, PendingIntent pendingIntent, Executor executor, IntConsumer listener) {
        try {
            this.mVirtualDevice.launchPendingIntent(
                    displayId,
                    pendingIntent,
                    new AnonymousClass3(new Handler(Looper.getMainLooper()), executor, listener));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.companion.virtual.VirtualDeviceInternal$3, reason: invalid class name */
    class AnonymousClass3 extends ResultReceiver {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ IntConsumer val$listener;

        AnonymousClass3(Handler handler, Executor executor, IntConsumer intConsumer) {
            super(handler);
            this.val$executor = executor;
            this.val$listener = intConsumer;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Executor executor = this.val$executor;
            final IntConsumer intConsumer = this.val$listener;
            executor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.VirtualDeviceInternal$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            intConsumer.accept(resultCode);
                        }
                    });
        }
    }

    VirtualDisplay createVirtualDisplay(
            VirtualDisplayConfig config, Executor executor, VirtualDisplay.Callback callback) {
        IVirtualDisplayCallback callbackWrapper =
                new DisplayManagerGlobal.VirtualDisplayCallback(callback, executor);
        try {
            int displayId =
                    this.mService.createVirtualDisplay(
                            config,
                            callbackWrapper,
                            this.mVirtualDevice,
                            this.mContext.getPackageName());
            DisplayManagerGlobal displayManager = DisplayManagerGlobal.getInstance();
            return displayManager.createVirtualDisplayWrapper(config, callbackWrapper, displayId);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void close() {
        try {
            this.mVirtualDevice.close();
            if (this.mVirtualAudioDevice != null) {
                this.mVirtualAudioDevice.close();
                this.mVirtualAudioDevice = null;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setDevicePolicy(int policyType, int devicePolicy) {
        try {
            this.mVirtualDevice.setDevicePolicy(policyType, devicePolicy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityPolicyExemption(ComponentName componentName) {
        try {
            this.mVirtualDevice.addActivityPolicyExemption(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void removeActivityPolicyExemption(ComponentName componentName) {
        try {
            this.mVirtualDevice.removeActivityPolicyExemption(componentName);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualDpad createVirtualDpad(VirtualDpadConfig config) {
        try {
            IBinder token =
                    new Binder("android.hardware.input.VirtualDpad:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualDpad(config, token);
            return new VirtualDpad(config, this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualKeyboard createVirtualKeyboard(VirtualKeyboardConfig config) {
        try {
            IBinder token =
                    new Binder(
                            "android.hardware.input.VirtualKeyboard:"
                                    + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualKeyboard(config, token);
            return new VirtualKeyboard(config, this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualMouse createVirtualMouse(VirtualMouseConfig config) {
        try {
            IBinder token =
                    new Binder(
                            "android.hardware.input.VirtualMouse:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualMouse(config, token);
            return new VirtualMouse(config, this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualTouchscreen createVirtualTouchscreen(VirtualTouchscreenConfig config) {
        try {
            IBinder token =
                    new Binder(
                            "android.hardware.input.VirtualTouchscreen:"
                                    + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualTouchscreen(config, token);
            return new VirtualTouchscreen(config, this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualStylus createVirtualStylus(VirtualStylusConfig config) {
        try {
            IBinder token =
                    new Binder(
                            "android.hardware.input.VirtualStylus:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualStylus(config, token);
            return new VirtualStylus(config, this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualNavigationTouchpad createVirtualNavigationTouchpad(
            VirtualNavigationTouchpadConfig config) {
        try {
            IBinder token =
                    new Binder(
                            "android.hardware.input.VirtualNavigationTouchpad:"
                                    + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualNavigationTouchpad(config, token);
            return new VirtualNavigationTouchpad(config, this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualAudioDevice createVirtualAudioDevice(
            VirtualDisplay display,
            Executor executor,
            VirtualAudioDevice.AudioConfigurationChangeCallback callback) {
        if (this.mVirtualAudioDevice == null) {
            try {
                Context context = this.mContext;
                if (Flags.deviceAwareRecordAudioPermission()
                        && this.mVirtualDevice.getDevicePolicy(1) == 1) {
                    context = this.mContext.createDeviceContext(getDeviceId());
                }
                this.mVirtualAudioDevice =
                        new VirtualAudioDevice(
                                context,
                                this.mVirtualDevice,
                                display,
                                executor,
                                callback,
                                new VirtualAudioDevice.CloseListener() { // from class:
                                    // android.companion.virtual.VirtualDeviceInternal$$ExternalSyntheticLambda0
                                    @Override // android.companion.virtual.audio.VirtualAudioDevice.CloseListener
                                    public final void onClosed() {
                                        VirtualDeviceInternal.this
                                                .lambda$createVirtualAudioDevice$0();
                                    }
                                });
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mVirtualAudioDevice;
    }

    public /* synthetic */ void lambda$createVirtualAudioDevice$0() {
        this.mVirtualAudioDevice = null;
    }

    VirtualCamera createVirtualCamera(VirtualCameraConfig config) {
        try {
            this.mVirtualDevice.registerVirtualCamera(config);
            return new VirtualCamera(
                    this.mVirtualDevice, this.mVirtualDevice.getVirtualCameraId(config), config);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setShowPointerIcon(boolean showPointerIcon) {
        try {
            this.mVirtualDevice.setShowPointerIcon(showPointerIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setDisplayImePolicy(int displayId, int policy) {
        try {
            this.mVirtualDevice.setDisplayImePolicy(displayId, policy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityListener(Executor executor, VirtualDeviceManager.ActivityListener listener) {
        ActivityListenerDelegate delegate =
                new ActivityListenerDelegate(
                        (VirtualDeviceManager.ActivityListener) Objects.requireNonNull(listener),
                        (Executor) Objects.requireNonNull(executor));
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.put(listener, delegate);
        }
    }

    void removeActivityListener(VirtualDeviceManager.ActivityListener listener) {
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.remove(Objects.requireNonNull(listener));
        }
    }

    void addSoundEffectListener(
            Executor executor, VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        SoundEffectListenerDelegate delegate =
                new SoundEffectListenerDelegate(
                        (Executor) Objects.requireNonNull(executor),
                        (VirtualDeviceManager.SoundEffectListener)
                                Objects.requireNonNull(soundEffectListener));
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.put(soundEffectListener, delegate);
        }
    }

    void removeSoundEffectListener(VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.remove(Objects.requireNonNull(soundEffectListener));
        }
    }

    void registerIntentInterceptor(
            IntentFilter interceptorFilter,
            Executor executor,
            VirtualDeviceManager.IntentInterceptorCallback interceptorCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(interceptorFilter);
        Objects.requireNonNull(interceptorCallback);
        IntentInterceptorDelegate delegate =
                new IntentInterceptorDelegate(executor, interceptorCallback);
        try {
            this.mVirtualDevice.registerIntentInterceptor(delegate, interceptorFilter);
            synchronized (this.mIntentInterceptorListenersLock) {
                this.mIntentInterceptorListeners.put(interceptorCallback, delegate);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void unregisterIntentInterceptor(
            VirtualDeviceManager.IntentInterceptorCallback interceptorCallback) {
        IntentInterceptorDelegate delegate;
        Objects.requireNonNull(interceptorCallback);
        synchronized (this.mIntentInterceptorListenersLock) {
            delegate = this.mIntentInterceptorListeners.remove(interceptorCallback);
        }
        if (delegate != null) {
            try {
                this.mVirtualDevice.unregisterIntentInterceptor(delegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    static class ActivityListenerDelegate {
        private final VirtualDeviceManager.ActivityListener mActivityListener;
        private final Executor mExecutor;

        ActivityListenerDelegate(
                VirtualDeviceManager.ActivityListener listener, Executor executor) {
            this.mActivityListener = listener;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onTopActivityChanged$0(
                int displayId, ComponentName topActivity) {
            this.mActivityListener.onTopActivityChanged(displayId, topActivity);
        }

        public void onTopActivityChanged(final int displayId, final ComponentName topActivity) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualDeviceInternal.ActivityListenerDelegate.this
                                    .lambda$onTopActivityChanged$0(displayId, topActivity);
                        }
                    });
        }

        public void onTopActivityChanged(
                final int displayId, final ComponentName topActivity, final int userId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualDeviceInternal.ActivityListenerDelegate.this
                                    .lambda$onTopActivityChanged$1(displayId, topActivity, userId);
                        }
                    });
        }

        public /* synthetic */ void lambda$onTopActivityChanged$1(
                int displayId, ComponentName topActivity, int userId) {
            this.mActivityListener.onTopActivityChanged(displayId, topActivity, userId);
        }

        public /* synthetic */ void lambda$onDisplayEmpty$2(int displayId) {
            this.mActivityListener.onDisplayEmpty(displayId);
        }

        public void onDisplayEmpty(final int displayId) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualDeviceInternal.ActivityListenerDelegate.this
                                    .lambda$onDisplayEmpty$2(displayId);
                        }
                    });
        }
    }

    static class IntentInterceptorDelegate extends IVirtualDeviceIntentInterceptor.Stub {
        private final Executor mExecutor;
        private final VirtualDeviceManager.IntentInterceptorCallback mIntentInterceptorCallback;

        private IntentInterceptorDelegate(
                Executor executor,
                VirtualDeviceManager.IntentInterceptorCallback interceptorCallback) {
            this.mExecutor = executor;
            this.mIntentInterceptorCallback = interceptorCallback;
        }

        @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
        public void onIntentIntercepted(final Intent intent) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.companion.virtual.VirtualDeviceInternal$IntentInterceptorDelegate$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                VirtualDeviceInternal.IntentInterceptorDelegate.this
                                        .lambda$onIntentIntercepted$0(intent);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        public /* synthetic */ void lambda$onIntentIntercepted$0(Intent intent) {
            this.mIntentInterceptorCallback.onIntentIntercepted(intent);
        }
    }

    static class SoundEffectListenerDelegate {
        private final Executor mExecutor;
        private final VirtualDeviceManager.SoundEffectListener mSoundEffectListener;

        private SoundEffectListenerDelegate(
                Executor executor, VirtualDeviceManager.SoundEffectListener soundEffectCallback) {
            this.mSoundEffectListener = soundEffectCallback;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onPlaySoundEffect$0(int effectType) {
            this.mSoundEffectListener.onPlaySoundEffect(effectType);
        }

        public void onPlaySoundEffect(final int effectType) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.companion.virtual.VirtualDeviceInternal$SoundEffectListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            VirtualDeviceInternal.SoundEffectListenerDelegate.this
                                    .lambda$onPlaySoundEffect$0(effectType);
                        }
                    });
        }
    }
}
