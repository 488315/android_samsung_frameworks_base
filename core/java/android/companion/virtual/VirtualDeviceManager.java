package android.companion.virtual;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.companion.virtual.audio.VirtualAudioDevice;
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
import android.hardware.input.VirtualTouchscreen;
import android.hardware.input.VirtualTouchscreenConfig;
import android.p009os.RemoteException;
import android.util.Log;
import android.view.Surface;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

/* loaded from: classes.dex */
public final class VirtualDeviceManager {
    public static final String ACTION_VIRTUAL_DEVICE_REMOVED = "android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED";
    public static final String EXTRA_VIRTUAL_DEVICE_ID = "android.companion.virtual.extra.VIRTUAL_DEVICE_ID";

    @SystemApi
    public static final int LAUNCH_FAILURE_NO_ACTIVITY = 2;

    @SystemApi
    public static final int LAUNCH_FAILURE_PENDING_INTENT_CANCELED = 1;

    @SystemApi
    public static final int LAUNCH_SUCCESS = 0;
    private static final String TAG = "VirtualDeviceManager";
    private final Context mContext;
    private final IVirtualDeviceManager mService;

    @SystemApi
    public interface IntentInterceptorCallback {
        void onIntentIntercepted(Intent intent);
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PendingIntentLaunchStatus {
    }

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
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.m102w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return new ArrayList();
        }
        try {
            return iVirtualDeviceManager.getVirtualDevices();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDevicePolicy(int deviceId, int policyType) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.m102w(TAG, "Failed to retrieve device policy; no virtual device manager service.");
            return 0;
        }
        try {
            return iVirtualDeviceManager.getDevicePolicy(deviceId, policyType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDeviceIdForDisplayId(int displayId) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.m102w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return 0;
        }
        try {
            return iVirtualDeviceManager.getDeviceIdForDisplayId(displayId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isValidVirtualDeviceId(int deviceId) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.m102w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return iVirtualDeviceManager.isValidVirtualDeviceId(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioPlaybackSessionId(int deviceId) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            return 0;
        }
        try {
            return iVirtualDeviceManager.getAudioPlaybackSessionId(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioRecordingSessionId(int deviceId) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            return 0;
        }
        try {
            return iVirtualDeviceManager.getAudioRecordingSessionId(deviceId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int deviceId, int effectType) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.m102w(TAG, "Failed to dispatch sound effect; no virtual device manager service.");
            return;
        }
        try {
            iVirtualDeviceManager.playSoundEffect(deviceId, effectType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static class VirtualDevice implements AutoCloseable {
        private final VirtualDeviceInternal mVirtualDeviceInternal;

        private VirtualDevice(IVirtualDeviceManager service, Context context, int associationId, VirtualDeviceParams params) throws RemoteException {
            this.mVirtualDeviceInternal = new VirtualDeviceInternal(service, context, associationId, params);
        }

        public int getDeviceId() {
            return this.mVirtualDeviceInternal.getDeviceId();
        }

        public Context createContext() {
            return this.mVirtualDeviceInternal.createContext();
        }

        public List<VirtualSensor> getVirtualSensorList() {
            return this.mVirtualDeviceInternal.getVirtualSensorList();
        }

        public void launchPendingIntent(int displayId, PendingIntent pendingIntent, Executor executor, IntConsumer listener) {
            Objects.requireNonNull(pendingIntent, "pendingIntent must not be null");
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(listener, "listener must not be null");
            this.mVirtualDeviceInternal.launchPendingIntent(displayId, pendingIntent, executor, listener);
        }

        @Deprecated
        public VirtualDisplay createVirtualDisplay(int width, int height, int densityDpi, Surface surface, int flags, Executor executor, VirtualDisplay.Callback callback) {
            String virtualDisplayName = "VirtualDevice_" + getDeviceId();
            VirtualDisplayConfig.Builder builder = new VirtualDisplayConfig.Builder(virtualDisplayName, width, height, densityDpi).setFlags(flags);
            if (surface != null) {
                builder.setSurface(surface);
            }
            return this.mVirtualDeviceInternal.createVirtualDisplay(builder.build(), executor, callback);
        }

        public VirtualDisplay createVirtualDisplay(VirtualDisplayConfig config, Executor executor, VirtualDisplay.Callback callback) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDisplay(config, executor, callback);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mVirtualDeviceInternal.close();
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
        public VirtualKeyboard createVirtualKeyboard(VirtualDisplay display, String inputDeviceName, int vendorId, int productId) {
            VirtualKeyboardConfig keyboardConfig = new VirtualKeyboardConfig.Builder().setVendorId(vendorId).setProductId(productId).setInputDeviceName(inputDeviceName).setAssociatedDisplayId(display.getDisplay().getDisplayId()).build();
            return this.mVirtualDeviceInternal.createVirtualKeyboard(keyboardConfig);
        }

        public VirtualMouse createVirtualMouse(VirtualMouseConfig config) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualMouse(config);
        }

        @Deprecated
        public VirtualMouse createVirtualMouse(VirtualDisplay display, String inputDeviceName, int vendorId, int productId) {
            VirtualMouseConfig mouseConfig = new VirtualMouseConfig.Builder().setVendorId(vendorId).setProductId(productId).setInputDeviceName(inputDeviceName).setAssociatedDisplayId(display.getDisplay().getDisplayId()).build();
            return this.mVirtualDeviceInternal.createVirtualMouse(mouseConfig);
        }

        public VirtualTouchscreen createVirtualTouchscreen(VirtualTouchscreenConfig config) {
            Objects.requireNonNull(config, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(config);
        }

        @Deprecated
        public VirtualTouchscreen createVirtualTouchscreen(VirtualDisplay display, String inputDeviceName, int vendorId, int productId) {
            Point size = new Point();
            display.getDisplay().getSize(size);
            VirtualTouchscreenConfig touchscreenConfig = new VirtualTouchscreenConfig.Builder(size.f85x, size.f86y).setVendorId(vendorId).setProductId(productId).setInputDeviceName(inputDeviceName).setAssociatedDisplayId(display.getDisplay().getDisplayId()).build();
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(touchscreenConfig);
        }

        public VirtualNavigationTouchpad createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig config) {
            return this.mVirtualDeviceInternal.createVirtualNavigationTouchpad(config);
        }

        public VirtualAudioDevice createVirtualAudioDevice(VirtualDisplay display, Executor executor, VirtualAudioDevice.AudioConfigurationChangeCallback callback) {
            Objects.requireNonNull(display, "display must not be null");
            return this.mVirtualDeviceInternal.createVirtualAudioDevice(display, executor, callback);
        }

        public void setShowPointerIcon(boolean showPointerIcon) {
            this.mVirtualDeviceInternal.setShowPointerIcon(showPointerIcon);
        }

        public void addActivityListener(Executor executor, ActivityListener listener) {
            this.mVirtualDeviceInternal.addActivityListener(executor, listener);
        }

        public void removeActivityListener(ActivityListener listener) {
            this.mVirtualDeviceInternal.removeActivityListener(listener);
        }

        public void addSoundEffectListener(Executor executor, SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.addSoundEffectListener(executor, soundEffectListener);
        }

        public void removeSoundEffectListener(SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.removeSoundEffectListener(soundEffectListener);
        }

        public void registerIntentInterceptor(IntentFilter interceptorFilter, Executor executor, IntentInterceptorCallback interceptorCallback) {
            this.mVirtualDeviceInternal.registerIntentInterceptor(interceptorFilter, executor, interceptorCallback);
        }

        public void unregisterIntentInterceptor(IntentInterceptorCallback interceptorCallback) {
            this.mVirtualDeviceInternal.unregisterIntentInterceptor(interceptorCallback);
        }
    }

    @SystemApi
    public interface ActivityListener {
        void onDisplayEmpty(int i);

        void onTopActivityChanged(int i, ComponentName componentName);

        default void onTopActivityChanged(int displayId, ComponentName topActivity, int userId) {
        }
    }
}
