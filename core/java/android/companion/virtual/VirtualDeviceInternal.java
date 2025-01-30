package android.companion.virtual;

import android.app.PendingIntent;
import android.companion.virtual.IVirtualDeviceActivityListener;
import android.companion.virtual.IVirtualDeviceIntentInterceptor;
import android.companion.virtual.IVirtualDeviceSoundEffectListener;
import android.companion.virtual.VirtualDeviceInternal;
import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.audio.VirtualAudioDevice;
import android.companion.virtual.sensor.VirtualSensor;
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
import android.hardware.input.VirtualTouchscreen;
import android.hardware.input.VirtualTouchscreenConfig;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.RemoteException;
import android.p009os.ResultReceiver;
import android.util.ArrayMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public class VirtualDeviceInternal {
    private final IVirtualDeviceActivityListener mActivityListenerBinder;
    private final Context mContext;
    private final IVirtualDeviceManager mService;
    private final IVirtualDeviceSoundEffectListener mSoundEffectListener;
    private VirtualAudioDevice mVirtualAudioDevice;
    private final IVirtualDevice mVirtualDevice;
    private final Object mActivityListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.ActivityListener, ActivityListenerDelegate> mActivityListeners = new ArrayMap<>();
    private final Object mIntentInterceptorListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.IntentInterceptorCallback, IntentInterceptorDelegate> mIntentInterceptorListeners = new ArrayMap<>();
    private final Object mSoundEffectListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.SoundEffectListener, SoundEffectListenerDelegate> mSoundEffectListeners = new ArrayMap<>();

    VirtualDeviceInternal(IVirtualDeviceManager service, Context context, int associationId, VirtualDeviceParams params) throws RemoteException {
        IVirtualDeviceActivityListener.Stub stub = new IVirtualDeviceActivityListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.1
            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onTopActivityChanged(int displayId, ComponentName topActivity, int userId) {
                long token = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i = 0; i < VirtualDeviceInternal.this.mActivityListeners.size(); i++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i)).onTopActivityChanged(displayId, topActivity);
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i)).onTopActivityChanged(displayId, topActivity, userId);
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
                        for (int i = 0; i < VirtualDeviceInternal.this.mActivityListeners.size(); i++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i)).onDisplayEmpty(displayId);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        };
        this.mActivityListenerBinder = stub;
        IVirtualDeviceSoundEffectListener.Stub stub2 = new IVirtualDeviceSoundEffectListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.2
            @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
            public void onPlaySoundEffect(int soundEffect) {
                long token = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mSoundEffectListenersLock) {
                        for (int i = 0; i < VirtualDeviceInternal.this.mSoundEffectListeners.size(); i++) {
                            ((SoundEffectListenerDelegate) VirtualDeviceInternal.this.mSoundEffectListeners.valueAt(i)).onPlaySoundEffect(soundEffect);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(token);
                }
            }
        };
        this.mSoundEffectListener = stub2;
        this.mService = service;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mVirtualDevice = service.createVirtualDevice(new Binder(), applicationContext.getPackageName(), associationId, params, stub, stub2);
    }

    int getDeviceId() {
        try {
            return this.mVirtualDevice.getDeviceId();
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

    void launchPendingIntent(int displayId, PendingIntent pendingIntent, Executor executor, IntConsumer listener) {
        try {
            this.mVirtualDevice.launchPendingIntent(displayId, pendingIntent, new ResultReceiverC05903(new Handler(Looper.getMainLooper()), executor, listener));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.companion.virtual.VirtualDeviceInternal$3 */
    class ResultReceiverC05903 extends ResultReceiver {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ IntConsumer val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ResultReceiverC05903(Handler handler, Executor executor, IntConsumer intConsumer) {
            super(handler);
            this.val$executor = executor;
            this.val$listener = intConsumer;
        }

        @Override // android.p009os.ResultReceiver
        protected void onReceiveResult(final int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Executor executor = this.val$executor;
            final IntConsumer intConsumer = this.val$listener;
            executor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(resultCode);
                }
            });
        }
    }

    VirtualDisplay createVirtualDisplay(VirtualDisplayConfig config, Executor executor, VirtualDisplay.Callback callback) {
        IVirtualDisplayCallback callbackWrapper = new DisplayManagerGlobal.VirtualDisplayCallback(callback, executor);
        try {
            int displayId = this.mService.createVirtualDisplay(config, callbackWrapper, this.mVirtualDevice, this.mContext.getPackageName());
            DisplayManagerGlobal displayManager = DisplayManagerGlobal.getInstance();
            return displayManager.createVirtualDisplayWrapper(config, callbackWrapper, displayId);
        } catch (RemoteException ex) {
            throw ex.rethrowFromSystemServer();
        }
    }

    void close() {
        try {
            this.mVirtualDevice.close();
            VirtualAudioDevice virtualAudioDevice = this.mVirtualAudioDevice;
            if (virtualAudioDevice != null) {
                virtualAudioDevice.close();
                this.mVirtualAudioDevice = null;
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualDpad createVirtualDpad(VirtualDpadConfig config) {
        try {
            IBinder token = new Binder("android.hardware.input.VirtualDpad:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualDpad(config, token);
            return new VirtualDpad(this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualKeyboard createVirtualKeyboard(VirtualKeyboardConfig config) {
        try {
            IBinder token = new Binder("android.hardware.input.VirtualKeyboard:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualKeyboard(config, token);
            return new VirtualKeyboard(this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualMouse createVirtualMouse(VirtualMouseConfig config) {
        try {
            IBinder token = new Binder("android.hardware.input.VirtualMouse:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualMouse(config, token);
            return new VirtualMouse(this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualTouchscreen createVirtualTouchscreen(VirtualTouchscreenConfig config) {
        try {
            IBinder token = new Binder("android.hardware.input.VirtualTouchscreen:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualTouchscreen(config, token);
            return new VirtualTouchscreen(this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualNavigationTouchpad createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig config) {
        try {
            IBinder token = new Binder("android.hardware.input.VirtualNavigationTouchpad:" + config.getInputDeviceName());
            this.mVirtualDevice.createVirtualNavigationTouchpad(config, token);
            return new VirtualNavigationTouchpad(this.mVirtualDevice, token);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualAudioDevice createVirtualAudioDevice(VirtualDisplay display, Executor executor, VirtualAudioDevice.AudioConfigurationChangeCallback callback) {
        if (this.mVirtualAudioDevice == null) {
            this.mVirtualAudioDevice = new VirtualAudioDevice(this.mContext, this.mVirtualDevice, display, executor, callback, new VirtualAudioDevice.CloseListener() { // from class: android.companion.virtual.VirtualDeviceInternal$$ExternalSyntheticLambda0
                @Override // android.companion.virtual.audio.VirtualAudioDevice.CloseListener
                public final void onClosed() {
                    VirtualDeviceInternal.this.lambda$createVirtualAudioDevice$0();
                }
            });
        }
        return this.mVirtualAudioDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVirtualAudioDevice$0() {
        this.mVirtualAudioDevice = null;
    }

    void setShowPointerIcon(boolean showPointerIcon) {
        try {
            this.mVirtualDevice.setShowPointerIcon(showPointerIcon);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityListener(Executor executor, VirtualDeviceManager.ActivityListener listener) {
        ActivityListenerDelegate delegate = new ActivityListenerDelegate((VirtualDeviceManager.ActivityListener) Objects.requireNonNull(listener), (Executor) Objects.requireNonNull(executor));
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.put(listener, delegate);
        }
    }

    void removeActivityListener(VirtualDeviceManager.ActivityListener listener) {
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.remove(Objects.requireNonNull(listener));
        }
    }

    void addSoundEffectListener(Executor executor, VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        SoundEffectListenerDelegate delegate = new SoundEffectListenerDelegate((Executor) Objects.requireNonNull(executor), (VirtualDeviceManager.SoundEffectListener) Objects.requireNonNull(soundEffectListener));
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.put(soundEffectListener, delegate);
        }
    }

    void removeSoundEffectListener(VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.remove(Objects.requireNonNull(soundEffectListener));
        }
    }

    void registerIntentInterceptor(IntentFilter interceptorFilter, Executor executor, VirtualDeviceManager.IntentInterceptorCallback interceptorCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(interceptorFilter);
        Objects.requireNonNull(interceptorCallback);
        IntentInterceptorDelegate delegate = new IntentInterceptorDelegate(executor, interceptorCallback);
        try {
            this.mVirtualDevice.registerIntentInterceptor(delegate, interceptorFilter);
            synchronized (this.mIntentInterceptorListenersLock) {
                this.mIntentInterceptorListeners.put(interceptorCallback, delegate);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void unregisterIntentInterceptor(VirtualDeviceManager.IntentInterceptorCallback interceptorCallback) {
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

    /* JADX INFO: Access modifiers changed from: private */
    static class ActivityListenerDelegate {
        private final VirtualDeviceManager.ActivityListener mActivityListener;
        private final Executor mExecutor;

        ActivityListenerDelegate(VirtualDeviceManager.ActivityListener listener, Executor executor) {
            this.mActivityListener = listener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopActivityChanged$0(int displayId, ComponentName topActivity) {
            this.mActivityListener.onTopActivityChanged(displayId, topActivity);
        }

        public void onTopActivityChanged(final int displayId, final ComponentName topActivity) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onTopActivityChanged$0(displayId, topActivity);
                }
            });
        }

        public void onTopActivityChanged(final int displayId, final ComponentName topActivity, final int userId) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onTopActivityChanged$1(displayId, topActivity, userId);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopActivityChanged$1(int displayId, ComponentName topActivity, int userId) {
            this.mActivityListener.onTopActivityChanged(displayId, topActivity, userId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayEmpty$2(int displayId) {
            this.mActivityListener.onDisplayEmpty(displayId);
        }

        public void onDisplayEmpty(final int displayId) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onDisplayEmpty$2(displayId);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IntentInterceptorDelegate extends IVirtualDeviceIntentInterceptor.Stub {
        private final Executor mExecutor;
        private final VirtualDeviceManager.IntentInterceptorCallback mIntentInterceptorCallback;

        private IntentInterceptorDelegate(Executor executor, VirtualDeviceManager.IntentInterceptorCallback interceptorCallback) {
            this.mExecutor = executor;
            this.mIntentInterceptorCallback = interceptorCallback;
        }

        @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
        public void onIntentIntercepted(final Intent intent) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$IntentInterceptorDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceInternal.IntentInterceptorDelegate.this.lambda$onIntentIntercepted$0(intent);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onIntentIntercepted$0(Intent intent) {
            this.mIntentInterceptorCallback.onIntentIntercepted(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SoundEffectListenerDelegate {
        private final Executor mExecutor;
        private final VirtualDeviceManager.SoundEffectListener mSoundEffectListener;

        private SoundEffectListenerDelegate(Executor executor, VirtualDeviceManager.SoundEffectListener soundEffectCallback) {
            this.mSoundEffectListener = soundEffectCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPlaySoundEffect$0(int effectType) {
            this.mSoundEffectListener.onPlaySoundEffect(effectType);
        }

        public void onPlaySoundEffect(final int effectType) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$SoundEffectListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.SoundEffectListenerDelegate.this.lambda$onPlaySoundEffect$0(effectType);
                }
            });
        }
    }
}
