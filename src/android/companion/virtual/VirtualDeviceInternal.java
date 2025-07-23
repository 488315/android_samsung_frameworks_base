package android.companion.virtual;

import android.app.PendingIntent;
import android.companion.virtual.IVirtualDeviceActivityListener;
import android.companion.virtual.IVirtualDeviceIntentInterceptor;
import android.companion.virtual.IVirtualDeviceSoundEffectListener;
import android.companion.virtual.VirtualDeviceInternal;
import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.audio.VirtualAudioDevice;
import android.companion.virtual.camera.VirtualCamera;
import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtual.sensor.VirtualSensor;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.hardware.display.DisplayManagerGlobal;
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
import android.hardware.input.VirtualRotaryEncoder;
import android.hardware.input.VirtualRotaryEncoderConfig;
import android.hardware.input.VirtualStylus;
import android.hardware.input.VirtualStylusConfig;
import android.hardware.input.VirtualTouchscreen;
import android.hardware.input.VirtualTouchscreenConfig;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.ArrayMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public class VirtualDeviceInternal {
    private final IVirtualDeviceActivityListener mActivityListenerBinder;
    private final Context mContext;
    private final IVirtualDeviceSoundEffectListener mSoundEffectListener;
    private VirtualAudioDevice mVirtualAudioDevice;
    private final IVirtualDevice mVirtualDevice;
    private final Object mActivityListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.ActivityListener, ActivityListenerDelegate> mActivityListeners = new ArrayMap<>();
    private final Object mIntentInterceptorListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.IntentInterceptorCallback, IntentInterceptorDelegate> mIntentInterceptorListeners = new ArrayMap<>();
    private final Object mSoundEffectListenersLock = new Object();
    private final ArrayMap<VirtualDeviceManager.SoundEffectListener, SoundEffectListenerDelegate> mSoundEffectListeners = new ArrayMap<>();

    VirtualDeviceInternal(IVirtualDeviceManager iVirtualDeviceManager, Context context, int i, VirtualDeviceParams virtualDeviceParams) throws RemoteException {
        IVirtualDeviceActivityListener.Stub stub = new IVirtualDeviceActivityListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.1
            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onTopActivityChanged(int i2, ComponentName componentName, int i3) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i4 = 0; i4 < VirtualDeviceInternal.this.mActivityListeners.size(); i4++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i4)).onTopActivityChanged(i2, componentName);
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i4)).onTopActivityChanged(i2, componentName, i3);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onDisplayEmpty(int i2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onDisplayEmpty(i2);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onActivityLaunchBlocked(int i2, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onActivityLaunchBlocked(i2, componentName, userHandle, intentSender);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onSecureWindowShown(int i2, ComponentName componentName, UserHandle userHandle) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onSecureWindowShown(i2, componentName, userHandle);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onSecureWindowHidden(int i2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onSecureWindowHidden(i2);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mActivityListenerBinder = stub;
        IVirtualDeviceSoundEffectListener.Stub stub2 = new IVirtualDeviceSoundEffectListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.2
            @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
            public void onPlaySoundEffect(int i2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mSoundEffectListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mSoundEffectListeners.size(); i3++) {
                            ((SoundEffectListenerDelegate) VirtualDeviceInternal.this.mSoundEffectListeners.valueAt(i3)).onPlaySoundEffect(i2);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mSoundEffectListener = stub2;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mVirtualDevice = iVirtualDeviceManager.createVirtualDevice(new Binder(), applicationContext.getAttributionSource(), i, virtualDeviceParams, stub, stub2);
    }

    VirtualDeviceInternal(Context context, IVirtualDevice iVirtualDevice) {
        IVirtualDeviceActivityListener.Stub stub = new IVirtualDeviceActivityListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.1
            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onTopActivityChanged(int i2, ComponentName componentName, int i3) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i4 = 0; i4 < VirtualDeviceInternal.this.mActivityListeners.size(); i4++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i4)).onTopActivityChanged(i2, componentName);
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i4)).onTopActivityChanged(i2, componentName, i3);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onDisplayEmpty(int i2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onDisplayEmpty(i2);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onActivityLaunchBlocked(int i2, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onActivityLaunchBlocked(i2, componentName, userHandle, intentSender);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onSecureWindowShown(int i2, ComponentName componentName, UserHandle userHandle) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onSecureWindowShown(i2, componentName, userHandle);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            @Override // android.companion.virtual.IVirtualDeviceActivityListener
            public void onSecureWindowHidden(int i2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mActivityListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                            ((ActivityListenerDelegate) VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onSecureWindowHidden(i2);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mActivityListenerBinder = stub;
        IVirtualDeviceSoundEffectListener.Stub stub2 = new IVirtualDeviceSoundEffectListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.2
            @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
            public void onPlaySoundEffect(int i2) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (VirtualDeviceInternal.this.mSoundEffectListenersLock) {
                        for (int i3 = 0; i3 < VirtualDeviceInternal.this.mSoundEffectListeners.size(); i3++) {
                            ((SoundEffectListenerDelegate) VirtualDeviceInternal.this.mSoundEffectListeners.valueAt(i3)).onPlaySoundEffect(i2);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mSoundEffectListener = stub2;
        this.mContext = context.getApplicationContext();
        this.mVirtualDevice = iVirtualDevice;
        try {
            iVirtualDevice.setListeners(stub, stub2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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

    void goToSleep() {
        try {
            this.mVirtualDevice.goToSleep();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void wakeUp() {
        try {
            this.mVirtualDevice.wakeUp();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void launchPendingIntent(int i, PendingIntent pendingIntent, Executor executor, IntConsumer intConsumer) {
        try {
            this.mVirtualDevice.launchPendingIntent(i, pendingIntent, new AnonymousClass3(this, new Handler(Looper.getMainLooper()), executor, intConsumer));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.companion.virtual.VirtualDeviceInternal$3, reason: invalid class name */
    class AnonymousClass3 extends ResultReceiver {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ IntConsumer val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(VirtualDeviceInternal virtualDeviceInternal, Handler handler, Executor executor, IntConsumer intConsumer) {
            super(handler);
            this.val$executor = executor;
            this.val$listener = intConsumer;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, Bundle bundle) {
            super.onReceiveResult(i, bundle);
            Executor executor = this.val$executor;
            final IntConsumer intConsumer = this.val$listener;
            executor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(i);
                }
            });
        }
    }

    VirtualDisplay createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, Executor executor, VirtualDisplay.Callback callback) {
        DisplayManagerGlobal.VirtualDisplayCallback virtualDisplayCallback = new DisplayManagerGlobal.VirtualDisplayCallback(callback, executor);
        try {
            return DisplayManagerGlobal.getInstance().createVirtualDisplayWrapper(virtualDisplayConfig, virtualDisplayCallback, this.mVirtualDevice.createVirtualDisplay(virtualDisplayConfig, virtualDisplayCallback));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
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

    void setDevicePolicy(int i, int i2) {
        if (i != 2 && i != 3 && i != 4 && i != 6) {
            throw new IllegalArgumentException("Device policy " + i + " cannot be changed at runtime. ");
        }
        try {
            this.mVirtualDevice.setDevicePolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) {
        try {
            this.mVirtualDevice.addActivityPolicyExemption(activityPolicyExemption);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void removeActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) {
        try {
            this.mVirtualDevice.removeActivityPolicyExemption(activityPolicyExemption);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setDevicePolicyForDisplay(int i, int i2, int i3) {
        if (i2 != 2 && i2 != 3) {
            throw new IllegalArgumentException("Device policy " + i2 + " cannot be changed for a specific display. ");
        }
        try {
            this.mVirtualDevice.setDevicePolicyForDisplay(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualDpad createVirtualDpad(VirtualDpadConfig virtualDpadConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualDpad:" + virtualDpadConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualDpad(virtualDpadConfig, binder);
            return new VirtualDpad(virtualDpadConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualKeyboard createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualKeyboard:" + virtualKeyboardConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualKeyboard(virtualKeyboardConfig, binder);
            return new VirtualKeyboard(virtualKeyboardConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualMouse createVirtualMouse(VirtualMouseConfig virtualMouseConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualMouse:" + virtualMouseConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualMouse(virtualMouseConfig, binder);
            return new VirtualMouse(virtualMouseConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualTouchscreen createVirtualTouchscreen(VirtualTouchscreenConfig virtualTouchscreenConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualTouchscreen:" + virtualTouchscreenConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualTouchscreen(virtualTouchscreenConfig, binder);
            return new VirtualTouchscreen(virtualTouchscreenConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualStylus createVirtualStylus(VirtualStylusConfig virtualStylusConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualStylus:" + virtualStylusConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualStylus(virtualStylusConfig, binder);
            return new VirtualStylus(virtualStylusConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualRotaryEncoder createVirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualRotaryEncoder:" + virtualRotaryEncoderConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualRotaryEncoder(virtualRotaryEncoderConfig, binder);
            return new VirtualRotaryEncoder(virtualRotaryEncoderConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualNavigationTouchpad createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig) {
        try {
            Binder binder = new Binder("android.hardware.input.VirtualNavigationTouchpad:" + virtualNavigationTouchpadConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig, binder);
            return new VirtualNavigationTouchpad(virtualNavigationTouchpadConfig, this.mVirtualDevice, binder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    VirtualAudioDevice createVirtualAudioDevice(VirtualDisplay virtualDisplay, Executor executor, VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback) {
        Context context;
        if (this.mVirtualAudioDevice == null) {
            try {
                if (this.mVirtualDevice.getDevicePolicy(1) == 1) {
                    context = this.mContext.createDeviceContext(getDeviceId());
                } else {
                    context = this.mContext;
                }
                this.mVirtualAudioDevice = new VirtualAudioDevice(context, this.mVirtualDevice, virtualDisplay, executor, audioConfigurationChangeCallback, new VirtualAudioDevice.CloseListener() { // from class: android.companion.virtual.VirtualDeviceInternal$$ExternalSyntheticLambda0
                    @Override // android.companion.virtual.audio.VirtualAudioDevice.CloseListener
                    public final void onClosed() {
                        VirtualDeviceInternal.this.lambda$createVirtualAudioDevice$0();
                    }
                });
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mVirtualAudioDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVirtualAudioDevice$0() {
        this.mVirtualAudioDevice = null;
    }

    VirtualCamera createVirtualCamera(VirtualCameraConfig virtualCameraConfig) {
        try {
            this.mVirtualDevice.registerVirtualCamera(virtualCameraConfig);
            IVirtualDevice iVirtualDevice = this.mVirtualDevice;
            return new VirtualCamera(iVirtualDevice, iVirtualDevice.getVirtualCameraId(virtualCameraConfig), virtualCameraConfig);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setShowPointerIcon(boolean z) {
        try {
            this.mVirtualDevice.setShowPointerIcon(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setDisplayImePolicy(int i, int i2) {
        try {
            this.mVirtualDevice.setDisplayImePolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityListener(Executor executor, VirtualDeviceManager.ActivityListener activityListener) {
        ActivityListenerDelegate activityListenerDelegate = new ActivityListenerDelegate((VirtualDeviceManager.ActivityListener) Objects.requireNonNull(activityListener), (Executor) Objects.requireNonNull(executor));
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.put(activityListener, activityListenerDelegate);
        }
    }

    void removeActivityListener(VirtualDeviceManager.ActivityListener activityListener) {
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.remove(Objects.requireNonNull(activityListener));
        }
    }

    void addSoundEffectListener(Executor executor, VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        SoundEffectListenerDelegate soundEffectListenerDelegate = new SoundEffectListenerDelegate((Executor) Objects.requireNonNull(executor), (VirtualDeviceManager.SoundEffectListener) Objects.requireNonNull(soundEffectListener));
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.put(soundEffectListener, soundEffectListenerDelegate);
        }
    }

    void removeSoundEffectListener(VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.remove(Objects.requireNonNull(soundEffectListener));
        }
    }

    void registerIntentInterceptor(IntentFilter intentFilter, Executor executor, VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(intentFilter);
        Objects.requireNonNull(intentInterceptorCallback);
        IntentInterceptorDelegate intentInterceptorDelegate = new IntentInterceptorDelegate(executor, intentInterceptorCallback);
        try {
            this.mVirtualDevice.registerIntentInterceptor(intentInterceptorDelegate, intentFilter);
            synchronized (this.mIntentInterceptorListenersLock) {
                this.mIntentInterceptorListeners.put(intentInterceptorCallback, intentInterceptorDelegate);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void unregisterIntentInterceptor(VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
        IntentInterceptorDelegate remove;
        Objects.requireNonNull(intentInterceptorCallback);
        synchronized (this.mIntentInterceptorListenersLock) {
            remove = this.mIntentInterceptorListeners.remove(intentInterceptorCallback);
        }
        if (remove != null) {
            try {
                this.mVirtualDevice.unregisterIntentInterceptor(remove);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ActivityListenerDelegate {
        private final VirtualDeviceManager.ActivityListener mActivityListener;
        private final Executor mExecutor;

        ActivityListenerDelegate(VirtualDeviceManager.ActivityListener activityListener, Executor executor) {
            this.mActivityListener = activityListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopActivityChanged$0(int i, ComponentName componentName) {
            this.mActivityListener.onTopActivityChanged(i, componentName);
        }

        public void onTopActivityChanged(final int i, final ComponentName componentName) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onTopActivityChanged$0(i, componentName);
                }
            });
        }

        public void onTopActivityChanged(final int i, final ComponentName componentName, final int i2) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onTopActivityChanged$1(i, componentName, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopActivityChanged$1(int i, ComponentName componentName, int i2) {
            this.mActivityListener.onTopActivityChanged(i, componentName, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayEmpty$2(int i) {
            this.mActivityListener.onDisplayEmpty(i);
        }

        public void onDisplayEmpty(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onDisplayEmpty$2(i);
                }
            });
        }

        public void onActivityLaunchBlocked(final int i, final ComponentName componentName, final UserHandle userHandle, final IntentSender intentSender) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onActivityLaunchBlocked$3(i, componentName, userHandle, intentSender);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActivityLaunchBlocked$3(int i, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) {
            this.mActivityListener.onActivityLaunchBlocked(i, componentName, userHandle, intentSender);
        }

        public void onSecureWindowShown(final int i, final ComponentName componentName, final UserHandle userHandle) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onSecureWindowShown$4(i, componentName, userHandle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSecureWindowShown$4(int i, ComponentName componentName, UserHandle userHandle) {
            this.mActivityListener.onSecureWindowShown(i, componentName, userHandle);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSecureWindowHidden$5(int i) {
            this.mActivityListener.onSecureWindowHidden(i);
        }

        public void onSecureWindowHidden(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onSecureWindowHidden$5(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IntentInterceptorDelegate extends IVirtualDeviceIntentInterceptor.Stub {
        private final Executor mExecutor;
        private final VirtualDeviceManager.IntentInterceptorCallback mIntentInterceptorCallback;

        private IntentInterceptorDelegate(Executor executor, VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
            this.mExecutor = executor;
            this.mIntentInterceptorCallback = intentInterceptorCallback;
        }

        @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
        public void onIntentIntercepted(final Intent intent) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$IntentInterceptorDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceInternal.IntentInterceptorDelegate.this.lambda$onIntentIntercepted$0(intent);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
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

        private SoundEffectListenerDelegate(Executor executor, VirtualDeviceManager.SoundEffectListener soundEffectListener) {
            this.mSoundEffectListener = soundEffectListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPlaySoundEffect$0(int i) {
            this.mSoundEffectListener.onPlaySoundEffect(i);
        }

        public void onPlaySoundEffect(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$SoundEffectListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VirtualDeviceInternal.SoundEffectListenerDelegate.this.lambda$onPlaySoundEffect$0(i);
                }
            });
        }
    }
}
