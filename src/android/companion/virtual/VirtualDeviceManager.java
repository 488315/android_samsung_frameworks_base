package android.companion.virtual;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.companion.virtual.ActivityPolicyExemption;
import android.companion.virtual.IVirtualDeviceListener;
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
import android.hardware.input.VirtualRotaryEncoder;
import android.hardware.input.VirtualRotaryEncoderConfig;
import android.hardware.input.VirtualStylus;
import android.hardware.input.VirtualStylusConfig;
import android.hardware.input.VirtualTouchscreen;
import android.hardware.input.VirtualTouchscreenConfig;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Surface;
import com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.Flags;
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

    @SystemApi
    public static final String PERSISTENT_DEVICE_ID_DEFAULT = "default:0";
    private static final String TAG = "VirtualDeviceManager";
    private final Context mContext;
    private final IVirtualDeviceManager mService;
    private final List<VirtualDeviceListenerDelegate> mVirtualDeviceListeners = new ArrayList();

    @SystemApi
    public interface ActivityListener {
        default void onActivityLaunchBlocked(int i, ComponentName componentName, UserHandle userHandle, IntentSender intentSender) {
        }

        void onDisplayEmpty(int i);

        default void onSecureWindowHidden(int i) {
        }

        default void onSecureWindowShown(int i, ComponentName componentName, UserHandle userHandle) {
        }

        void onTopActivityChanged(int i, ComponentName componentName);

        default void onTopActivityChanged(int i, ComponentName componentName, int i2) {
        }
    }

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

    public interface VirtualDeviceListener {
        default void onVirtualDeviceClosed(int i) {
        }

        default void onVirtualDeviceCreated(int i) {
        }
    }

    public VirtualDeviceManager(IVirtualDeviceManager iVirtualDeviceManager, Context context) {
        this.mService = iVirtualDeviceManager;
        this.mContext = context;
    }

    @SystemApi
    public VirtualDevice createVirtualDevice(int i, VirtualDeviceParams virtualDeviceParams) {
        Objects.requireNonNull(virtualDeviceParams, "params must not be null");
        try {
            return new VirtualDevice(this.mService, this.mContext, i, virtualDeviceParams);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<android.companion.virtual.VirtualDevice> getVirtualDevices() {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return new ArrayList();
        }
        try {
            return iVirtualDeviceManager.getVirtualDevices();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.companion.virtual.VirtualDevice getVirtualDevice(int i) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return null;
        }
        if (i == -1 || i == 0) {
            return null;
        }
        try {
            return iVirtualDeviceManager.getVirtualDevice(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerVirtualDeviceListener(Executor executor, VirtualDeviceListener virtualDeviceListener) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to register listener; no virtual device manager service.");
            return;
        }
        VirtualDeviceListenerDelegate virtualDeviceListenerDelegate = new VirtualDeviceListenerDelegate((Executor) Objects.requireNonNull(executor), (VirtualDeviceListener) Objects.requireNonNull(virtualDeviceListener));
        synchronized (this.mVirtualDeviceListeners) {
            try {
                try {
                    this.mService.registerVirtualDeviceListener(virtualDeviceListenerDelegate);
                    this.mVirtualDeviceListeners.add(virtualDeviceListenerDelegate);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterVirtualDeviceListener(VirtualDeviceListener virtualDeviceListener) {
        if (this.mService == null) {
            Log.w(TAG, "Failed to unregister listener; no virtual device manager service.");
            return;
        }
        Objects.requireNonNull(virtualDeviceListener);
        synchronized (this.mVirtualDeviceListeners) {
            Iterator<VirtualDeviceListenerDelegate> it = this.mVirtualDeviceListeners.iterator();
            while (it.hasNext()) {
                VirtualDeviceListenerDelegate next = it.next();
                if (next.mListener == virtualDeviceListener) {
                    try {
                        this.mService.unregisterVirtualDeviceListener(next);
                        it.remove();
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public int getDevicePolicy(int i, int i2) {
        if (i == 0) {
            return 0;
        }
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve device policy; no virtual device manager service.");
            return 0;
        }
        try {
            return iVirtualDeviceManager.getDevicePolicy(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDeviceIdForDisplayId(int i) {
        if (i == 0 || i == -1) {
            return 0;
        }
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return 0;
        }
        try {
            return iVirtualDeviceManager.getDeviceIdForDisplayId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public CharSequence getDisplayNameForPersistentDeviceId(String str) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return null;
        }
        try {
            return iVirtualDeviceManager.getDisplayNameForPersistentDeviceId((String) Objects.requireNonNull(str));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Set<String> getAllPersistentDeviceIds() {
        if (this.mService == null) {
            Log.w(TAG, "Failed to retrieve persistent ids; no virtual device manager service.");
            return Collections.EMPTY_SET;
        }
        try {
            return new ArraySet(this.mService.getAllPersistentDeviceIds());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isValidVirtualDeviceId(int i) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return iVirtualDeviceManager.isValidVirtualDeviceId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioPlaybackSessionId(int i) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            return 0;
        }
        try {
            return iVirtualDeviceManager.getAudioPlaybackSessionId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioRecordingSessionId(int i) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            return 0;
        }
        try {
            return iVirtualDeviceManager.getAudioRecordingSessionId(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int i, int i2) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to dispatch sound effect; no virtual device manager service.");
            return;
        }
        try {
            iVirtualDeviceManager.playSoundEffect(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVirtualDeviceOwnedMirrorDisplay(int i) {
        IVirtualDeviceManager iVirtualDeviceManager = this.mService;
        if (iVirtualDeviceManager == null) {
            Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return iVirtualDeviceManager.isVirtualDeviceOwnedMirrorDisplay(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static class VirtualDevice implements AutoCloseable {
        private final VirtualDeviceInternal mVirtualDeviceInternal;

        private VirtualDevice(IVirtualDeviceManager iVirtualDeviceManager, Context context, int i, VirtualDeviceParams virtualDeviceParams) throws RemoteException {
            this.mVirtualDeviceInternal = new VirtualDeviceInternal(iVirtualDeviceManager, context, i, virtualDeviceParams);
        }

        public VirtualDevice(Context context, IVirtualDevice iVirtualDevice) {
            this.mVirtualDeviceInternal = new VirtualDeviceInternal(context, iVirtualDevice);
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

        public void goToSleep() {
            this.mVirtualDeviceInternal.goToSleep();
        }

        public void wakeUp() {
            this.mVirtualDeviceInternal.wakeUp();
        }

        public void launchPendingIntent(int i, PendingIntent pendingIntent, Executor executor, IntConsumer intConsumer) {
            Objects.requireNonNull(pendingIntent, "pendingIntent must not be null");
            Objects.requireNonNull(executor, "executor must not be null");
            Objects.requireNonNull(intConsumer, "listener must not be null");
            this.mVirtualDeviceInternal.launchPendingIntent(i, pendingIntent, executor, intConsumer);
        }

        @Deprecated
        public VirtualDisplay createVirtualDisplay(int i, int i2, int i3, Surface surface, int i4, Executor executor, VirtualDisplay.Callback callback) {
            VirtualDisplayConfig.Builder flags = new VirtualDisplayConfig.Builder("VirtualDevice_" + getDeviceId(), i, i2, i3).setFlags(i4);
            if (surface != null) {
                flags.setSurface(surface);
            }
            return this.mVirtualDeviceInternal.createVirtualDisplay(flags.build(), executor, callback);
        }

        public VirtualDisplay createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, Executor executor, VirtualDisplay.Callback callback) {
            Objects.requireNonNull(virtualDisplayConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDisplay(virtualDisplayConfig, executor, callback);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mVirtualDeviceInternal.close();
        }

        public void setDevicePolicy(int i, int i2) {
            this.mVirtualDeviceInternal.setDevicePolicy(i, i2);
        }

        public void addActivityPolicyExemption(ComponentName componentName) {
            addActivityPolicyExemption(new ActivityPolicyExemption.Builder().setComponentName(componentName).build());
        }

        public void removeActivityPolicyExemption(ComponentName componentName) {
            removeActivityPolicyExemption(new ActivityPolicyExemption.Builder().setComponentName(componentName).build());
        }

        public void addActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) {
            this.mVirtualDeviceInternal.addActivityPolicyExemption((ActivityPolicyExemption) Objects.requireNonNull(activityPolicyExemption));
        }

        public void removeActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) {
            this.mVirtualDeviceInternal.removeActivityPolicyExemption((ActivityPolicyExemption) Objects.requireNonNull(activityPolicyExemption));
        }

        public void setDevicePolicy(int i, int i2, int i3) {
            this.mVirtualDeviceInternal.setDevicePolicyForDisplay(i3, i, i2);
        }

        public VirtualDpad createVirtualDpad(VirtualDpadConfig virtualDpadConfig) {
            Objects.requireNonNull(virtualDpadConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDpad(virtualDpadConfig);
        }

        public VirtualKeyboard createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig) {
            Objects.requireNonNull(virtualKeyboardConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualKeyboard(virtualKeyboardConfig);
        }

        @Deprecated
        public VirtualKeyboard createVirtualKeyboard(VirtualDisplay virtualDisplay, String str, int i, int i2) {
            return this.mVirtualDeviceInternal.createVirtualKeyboard(new VirtualKeyboardConfig.Builder().setVendorId(i).setProductId(i2).setInputDeviceName(str).setAssociatedDisplayId(virtualDisplay.getDisplay().getDisplayId()).build());
        }

        public VirtualMouse createVirtualMouse(VirtualMouseConfig virtualMouseConfig) {
            Objects.requireNonNull(virtualMouseConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualMouse(virtualMouseConfig);
        }

        @Deprecated
        public VirtualMouse createVirtualMouse(VirtualDisplay virtualDisplay, String str, int i, int i2) {
            return this.mVirtualDeviceInternal.createVirtualMouse(new VirtualMouseConfig.Builder().setVendorId(i).setProductId(i2).setInputDeviceName(str).setAssociatedDisplayId(virtualDisplay.getDisplay().getDisplayId()).build());
        }

        public VirtualTouchscreen createVirtualTouchscreen(VirtualTouchscreenConfig virtualTouchscreenConfig) {
            Objects.requireNonNull(virtualTouchscreenConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(virtualTouchscreenConfig);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Deprecated
        public VirtualTouchscreen createVirtualTouchscreen(VirtualDisplay virtualDisplay, String str, int i, int i2) {
            Point point = new Point();
            virtualDisplay.getDisplay().getSize(point);
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(((VirtualTouchscreenConfig.Builder) ((VirtualTouchscreenConfig.Builder) ((VirtualTouchscreenConfig.Builder) ((VirtualTouchscreenConfig.Builder) new VirtualTouchscreenConfig.Builder(point.x, point.y).setVendorId(i)).setProductId(i2)).setInputDeviceName(str)).setAssociatedDisplayId(virtualDisplay.getDisplay().getDisplayId())).build());
        }

        public VirtualNavigationTouchpad createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig) {
            return this.mVirtualDeviceInternal.createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig);
        }

        public VirtualStylus createVirtualStylus(VirtualStylusConfig virtualStylusConfig) {
            return this.mVirtualDeviceInternal.createVirtualStylus(virtualStylusConfig);
        }

        public VirtualRotaryEncoder createVirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig) {
            if (!Flags.virtualRotary()) {
                throw new UnsupportedOperationException("Virtual rotary support not enabled");
            }
            return this.mVirtualDeviceInternal.createVirtualRotaryEncoder(virtualRotaryEncoderConfig);
        }

        public VirtualAudioDevice createVirtualAudioDevice(VirtualDisplay virtualDisplay, Executor executor, VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback) {
            Objects.requireNonNull(virtualDisplay, "display must not be null");
            return this.mVirtualDeviceInternal.createVirtualAudioDevice(virtualDisplay, executor, audioConfigurationChangeCallback);
        }

        public VirtualCamera createVirtualCamera(VirtualCameraConfig virtualCameraConfig) {
            return this.mVirtualDeviceInternal.createVirtualCamera((VirtualCameraConfig) Objects.requireNonNull(virtualCameraConfig));
        }

        public void setShowPointerIcon(boolean z) {
            this.mVirtualDeviceInternal.setShowPointerIcon(z);
        }

        public void setDisplayImePolicy(int i, int i2) {
            this.mVirtualDeviceInternal.setDisplayImePolicy(i, i2);
        }

        public void addActivityListener(Executor executor, ActivityListener activityListener) {
            this.mVirtualDeviceInternal.addActivityListener(executor, activityListener);
        }

        public void removeActivityListener(ActivityListener activityListener) {
            this.mVirtualDeviceInternal.removeActivityListener(activityListener);
        }

        public void addSoundEffectListener(Executor executor, SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.addSoundEffectListener(executor, soundEffectListener);
        }

        public void removeSoundEffectListener(SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.removeSoundEffectListener(soundEffectListener);
        }

        public void registerIntentInterceptor(IntentFilter intentFilter, Executor executor, IntentInterceptorCallback intentInterceptorCallback) {
            this.mVirtualDeviceInternal.registerIntentInterceptor(intentFilter, executor, intentInterceptorCallback);
        }

        public void unregisterIntentInterceptor(IntentInterceptorCallback intentInterceptorCallback) {
            this.mVirtualDeviceInternal.unregisterIntentInterceptor(intentInterceptorCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VirtualDeviceListenerDelegate extends IVirtualDeviceListener.Stub {
        private final Executor mExecutor;
        private final VirtualDeviceListener mListener;

        private VirtualDeviceListenerDelegate(Executor executor, VirtualDeviceListener virtualDeviceListener) {
            this.mExecutor = executor;
            this.mListener = virtualDeviceListener;
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceCreated(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceManager$VirtualDeviceListenerDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceManager.VirtualDeviceListenerDelegate.this.lambda$onVirtualDeviceCreated$0(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVirtualDeviceCreated$0(int i) {
            this.mListener.onVirtualDeviceCreated(i);
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceClosed(final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceManager$VirtualDeviceListenerDelegate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceManager.VirtualDeviceListenerDelegate.this.lambda$onVirtualDeviceClosed$1(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVirtualDeviceClosed$1(int i) {
            this.mListener.onVirtualDeviceClosed(i);
        }
    }
}
