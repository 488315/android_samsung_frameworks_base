package android.companion.virtual;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.companion.virtual.audio.VirtualAudioDevice;
import android.companion.virtual.camera.VirtualCamera;
import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtual.flags.Flags;
import android.companion.virtual.sensor.VirtualSensor;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
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
import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import android.view.Surface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public final class VirtualDeviceManager {
    public static final String ACTION_VIRTUAL_DEVICE_REMOVED =
            "android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED";
    public static final String EXTRA_VIRTUAL_DEVICE_ID =
            "android.companion.virtual.extra.VIRTUAL_DEVICE_ID";

    @SystemApi public static final int LAUNCH_FAILURE_NO_ACTIVITY = 2;

    @SystemApi public static final int LAUNCH_FAILURE_PENDING_INTENT_CANCELED = 1;

    @SystemApi public static final int LAUNCH_SUCCESS = 0;

    @SystemApi public static final String PERSISTENT_DEVICE_ID_DEFAULT = "default:0";
    private static final String TAG = "VirtualDeviceManager";
    private final Context mContext;
    private final IVirtualDeviceManager mService;
    private final List<VirtualDeviceListenerDelegate> mVirtualDeviceListeners = new ArrayList();

    @SystemApi
    public interface IntentInterceptorCallback {
        void onIntentIntercepted(Intent intent);
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PendingIntentLaunchStatus {}

    @SystemApi
    public interface SoundEffectListener {
        void onPlaySoundEffect(int i);
    }

    public VirtualDeviceManager(IVirtualDeviceManager service, Context context) {
        this.mService = service;
        this.mContext = context;
    }

    @SystemApi
    public VirtualDevice createVirtualDevice(int associationId, VirtualDeviceParams params) {
        Objects.requireNonNull(params, "params must not be null");
        try {
            return new VirtualDevice(this.mService, this.mContext, associationId, params);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<android.companion.virtual.VirtualDevice> getVirtualDevices() {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return new ArrayList();
        }
        try {
            return this.mService.getVirtualDevices();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.companion.virtual.VirtualDevice getVirtualDevice(int deviceId) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return null;
        }
        if (deviceId == -1 || deviceId == 0) {
            return null;
        }
        try {
            return this.mService.getVirtualDevice(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerVirtualDeviceListener(Executor executor, VirtualDeviceListener listener) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to register listener; no virtual device manager service.");
            return;
        }
        VirtualDeviceListenerDelegate delegate =
                new VirtualDeviceListenerDelegate(
                        (Executor) Objects.requireNonNull(executor),
                        (VirtualDeviceListener) Objects.requireNonNull(listener));
        synchronized (this.mVirtualDeviceListeners) {
            try {
                try {
                    this.mService.registerVirtualDeviceListener(delegate);
                    this.mVirtualDeviceListeners.add(delegate);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterVirtualDeviceListener(VirtualDeviceListener listener) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to unregister listener; no virtual device manager service.");
            return;
        }
        Objects.requireNonNull(listener);
        synchronized (this.mVirtualDeviceListeners) {
            Iterator<VirtualDeviceListenerDelegate> it = this.mVirtualDeviceListeners.iterator();
            while (it.hasNext()) {
                VirtualDeviceListenerDelegate delegate = it.next();
                if (delegate.mListener == listener) {
                    try {
                        this.mService.unregisterVirtualDeviceListener(delegate);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public int getDevicePolicy(int deviceId, int policyType) {
        if (deviceId == 0) {
            return 0;
        }
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve device policy; no virtual device manager service.");
            return 0;
        }
        try {
            return this.mService.getDevicePolicy(deviceId, policyType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDeviceIdForDisplayId(int displayId) {
        if (displayId == 0 || displayId == -1) {
            return 0;
        }
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return 0;
        }
        try {
            return this.mService.getDeviceIdForDisplayId(displayId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public CharSequence getDisplayNameForPersistentDeviceId(String persistentDeviceId) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return null;
        }
        try {
            return this.mService.getDisplayNameForPersistentDeviceId(
                    (String) Objects.requireNonNull(persistentDeviceId));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Set<String> getAllPersistentDeviceIds() {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve persistent ids; no virtual device manager service.");
            return Collections.emptySet();
        }
        try {
            return new ArraySet(this.mService.getAllPersistentDeviceIds());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isValidVirtualDeviceId(int deviceId) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return this.mService.isValidVirtualDeviceId(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioPlaybackSessionId(int deviceId) {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getAudioPlaybackSessionId(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioRecordingSessionId(int deviceId) {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getAudioRecordingSessionId(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int deviceId, int effectType) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to dispatch sound effect; no virtual device manager service.");
            return;
        }
        try {
            this.mService.playSoundEffect(deviceId, effectType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVirtualDeviceOwnedMirrorDisplay(int displayId) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return this.mService.isVirtualDeviceOwnedMirrorDisplay(displayId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static class VirtualDevice implements AutoCloseable {
        private final VirtualDeviceInternal mVirtualDeviceInternal;

        private VirtualDevice(
                IVirtualDeviceManager service,
                Context context,
                int associationId,
                VirtualDeviceParams params)
                throws RemoteException {
            this.mVirtualDeviceInternal =
                    new VirtualDeviceInternal(service, context, associationId, params);
        }

        public int getDeviceId() {
            return this.mVirtualDeviceInternal.getDeviceId();
        }

        public String getPersistentDeviceId() {
            return this.mVirtualDeviceInternal.getPersistentDeviceId();
        }

        public Context createContext() {
            return this.mVirtualDeviceInternal.createContext();
        }

        public List<VirtualSensor> getVirtualSensorList() {
            return this.mVirtualDeviceInternal.getVirtualSensorList();
        }

        public void launchPendingIntent(
                int displayId,
                PendingIntent pendingIntent,
                Executor executor,
                IntConsumer listener) {
            Objects.requireNonNull(pendingIntent, "pendingIntent must not be null");
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(listener, "listener must not be null");
            this.mVirtualDeviceInternal.launchPendingIntent(
                    displayId, pendingIntent, executor, listener);
        }

        @Deprecated
        public VirtualDisplay createVirtualDisplay(
                int width,
                int height,
                int densityDpi,
                Surface surface,
                int flags,
                Executor executor,
                VirtualDisplay.Callback callback) {
            String virtualDisplayName = "VirtualDevice_" + getDeviceId();
            VirtualDisplayConfig.Builder builder =
                    new VirtualDisplayConfig.Builder(virtualDisplayName, width, height, densityDpi)
                            .setFlags(flags);
            if (surface != null) {
                builder.setSurface(surface);
            }
            return this.mVirtualDeviceInternal.createVirtualDisplay(
                    builder.build(), executor, callback);
        }

        public VirtualDisplay createVirtualDisplay(
                VirtualDisplayConfig config, Executor executor, VirtualDisplay.Callback callback) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDisplay(config, executor, callback);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mVirtualDeviceInternal.close();
        }

        public void setDevicePolicy(int policyType, int devicePolicy) {
            this.mVirtualDeviceInternal.setDevicePolicy(policyType, devicePolicy);
        }

        public void addActivityPolicyExemption(ComponentName componentName) {
            this.mVirtualDeviceInternal.addActivityPolicyExemption(
                    (ComponentName) Objects.requireNonNull(componentName));
        }

        public void removeActivityPolicyExemption(ComponentName componentName) {
            this.mVirtualDeviceInternal.removeActivityPolicyExemption(
                    (ComponentName) Objects.requireNonNull(componentName));
        }

        public VirtualDpad createVirtualDpad(VirtualDpadConfig config) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDpad(config);
        }

        public VirtualKeyboard createVirtualKeyboard(VirtualKeyboardConfig config) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualKeyboard(config);
        }

        @Deprecated
        public VirtualKeyboard createVirtualKeyboard(
                VirtualDisplay display, String inputDeviceName, int vendorId, int productId) {
            VirtualKeyboardConfig keyboardConfig =
                    new VirtualKeyboardConfig.Builder()
                            .setVendorId(vendorId)
                            .setProductId(productId)
                            .setInputDeviceName(inputDeviceName)
                            .setAssociatedDisplayId(display.getDisplay().getDisplayId())
                            .build();
            return this.mVirtualDeviceInternal.createVirtualKeyboard(keyboardConfig);
        }

        public VirtualMouse createVirtualMouse(VirtualMouseConfig config) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualMouse(config);
        }

        @Deprecated
        public VirtualMouse createVirtualMouse(
                VirtualDisplay display, String inputDeviceName, int vendorId, int productId) {
            VirtualMouseConfig mouseConfig =
                    new VirtualMouseConfig.Builder()
                            .setVendorId(vendorId)
                            .setProductId(productId)
                            .setInputDeviceName(inputDeviceName)
                            .setAssociatedDisplayId(display.getDisplay().getDisplayId())
                            .build();
            return this.mVirtualDeviceInternal.createVirtualMouse(mouseConfig);
        }

        public VirtualTouchscreen createVirtualTouchscreen(VirtualTouchscreenConfig config) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(config);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Deprecated
        public VirtualTouchscreen createVirtualTouchscreen(
                VirtualDisplay display, String inputDeviceName, int vendorId, int productId) {
            Point size = new Point();
            display.getDisplay().getSize(size);
            VirtualTouchscreenConfig touchscreenConfig =
                    ((VirtualTouchscreenConfig.Builder)
                                    ((VirtualTouchscreenConfig.Builder)
                                                    ((VirtualTouchscreenConfig.Builder)
                                                                    ((VirtualTouchscreenConfig
                                                                                            .Builder)
                                                                                    new VirtualTouchscreenConfig
                                                                                                    .Builder(
                                                                                                    size.x,
                                                                                                    size.y)
                                                                                            .setVendorId(
                                                                                                    vendorId))
                                                                            .setProductId(
                                                                                    productId))
                                                            .setInputDeviceName(inputDeviceName))
                                            .setAssociatedDisplayId(
                                                    display.getDisplay().getDisplayId()))
                            .build();
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(touchscreenConfig);
        }

        public VirtualNavigationTouchpad createVirtualNavigationTouchpad(
                VirtualNavigationTouchpadConfig config) {
            return this.mVirtualDeviceInternal.createVirtualNavigationTouchpad(config);
        }

        public VirtualStylus createVirtualStylus(VirtualStylusConfig config) {
            return this.mVirtualDeviceInternal.createVirtualStylus(config);
        }

        public VirtualAudioDevice createVirtualAudioDevice(
                VirtualDisplay display,
                Executor executor,
                VirtualAudioDevice.AudioConfigurationChangeCallback callback) {
            Objects.requireNonNull(display, "display must not be null");
            return this.mVirtualDeviceInternal.createVirtualAudioDevice(
                    display, executor, callback);
        }

        public VirtualCamera createVirtualCamera(VirtualCameraConfig config) {
            if (!Flags.virtualCamera()) {
                throw new UnsupportedOperationException(
                        "Flag is not enabled: %s".formatted(Flags.FLAG_VIRTUAL_CAMERA));
            }
            return this.mVirtualDeviceInternal.createVirtualCamera(
                    (VirtualCameraConfig) Objects.requireNonNull(config));
        }

        public void setShowPointerIcon(boolean showPointerIcon) {
            this.mVirtualDeviceInternal.setShowPointerIcon(showPointerIcon);
        }

        public void setDisplayImePolicy(int displayId, int policy) {
            if (Flags.vdmCustomIme()) {
                this.mVirtualDeviceInternal.setDisplayImePolicy(displayId, policy);
            }
        }

        public void addActivityListener(Executor executor, ActivityListener listener) {
            this.mVirtualDeviceInternal.addActivityListener(executor, listener);
        }

        public void removeActivityListener(ActivityListener listener) {
            this.mVirtualDeviceInternal.removeActivityListener(listener);
        }

        public void addSoundEffectListener(
                Executor executor, SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.addSoundEffectListener(executor, soundEffectListener);
        }

        public void removeSoundEffectListener(SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.removeSoundEffectListener(soundEffectListener);
        }

        public void registerIntentInterceptor(
                IntentFilter interceptorFilter,
                Executor executor,
                IntentInterceptorCallback interceptorCallback) {
            this.mVirtualDeviceInternal.registerIntentInterceptor(
                    interceptorFilter, executor, interceptorCallback);
        }

        public void unregisterIntentInterceptor(IntentInterceptorCallback interceptorCallback) {
            this.mVirtualDeviceInternal.unregisterIntentInterceptor(interceptorCallback);
        }
    }

    @SystemApi
    public interface ActivityListener {
        void onDisplayEmpty(int i);

        void onTopActivityChanged(int i, ComponentName componentName);

        default void onTopActivityChanged(int displayId, ComponentName topActivity, int userId) {}
    }

    public interface VirtualDeviceListener {
        default void onVirtualDeviceCreated(int deviceId) {}

        default void onVirtualDeviceClosed(int deviceId) {}
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VirtualDeviceListenerDelegate extends IVirtualDeviceListener.Stub {
        private final Executor mExecutor;
        private final VirtualDeviceListener mListener;

        private VirtualDeviceListenerDelegate(Executor executor, VirtualDeviceListener listener) {
            this.mExecutor = executor;
            this.mListener = listener;
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceCreated(final int deviceId) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.companion.virtual.VirtualDeviceManager$VirtualDeviceListenerDelegate$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                VirtualDeviceManager.VirtualDeviceListenerDelegate.this
                                        .lambda$onVirtualDeviceCreated$0(deviceId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVirtualDeviceCreated$0(int deviceId) {
            this.mListener.onVirtualDeviceCreated(deviceId);
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceClosed(final int deviceId) {
            long token = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.companion.virtual.VirtualDeviceManager$VirtualDeviceListenerDelegate$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                VirtualDeviceManager.VirtualDeviceListenerDelegate.this
                                        .lambda$onVirtualDeviceClosed$1(deviceId);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVirtualDeviceClosed$1(int deviceId) {
            this.mListener.onVirtualDeviceClosed(deviceId);
        }
    }
}
