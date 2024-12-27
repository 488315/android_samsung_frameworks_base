package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;
import com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController$$ExternalSyntheticLambda0;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public final class FlashlightControllerImpl implements FlashlightController {
    public final Calendar mCalendar;
    public final AtomicReference mCameraId;
    public final CameraManager mCameraManager;
    public final Executor mExecutor;
    public final ArrayList mFlashLightDebugLogs;
    public boolean mFlashlightEnabled;
    public final boolean mHasFlashlight;
    public final ArrayList mListeners = new ArrayList(1);
    public final SecFlashlightControllerImpl mSecFlashlightController;
    public final SecureSettings mSecureSettings;
    public boolean mTorchAvailable;
    public final AnonymousClass1 mTorchCallback;

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.policy.FlashlightControllerImpl$1] */
    public FlashlightControllerImpl(Context context, SettingsHelper settingsHelper, DumpManager dumpManager, CameraManager cameraManager, Executor executor, SecureSettings secureSettings, BroadcastSender broadcastSender, PackageManager packageManager) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.mFlashLightDebugLogs = new ArrayList();
        this.mCalendar = Calendar.getInstance(TimeZone.getDefault());
        this.mTorchCallback = new CameraManager.TorchCallback() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl.1
            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public final void onTorchModeChanged(String str, boolean z) {
                if (TextUtils.equals(str, (CharSequence) FlashlightControllerImpl.this.mCameraId.get())) {
                    SecFlashlightControllerImpl secFlashlightControllerImpl = FlashlightControllerImpl.this.mSecFlashlightController;
                    if (secFlashlightControllerImpl != null) {
                        boolean isFlashlightBlinking = secFlashlightControllerImpl.isFlashlightBlinking();
                        EmergencyButtonController$$ExternalSyntheticOutline0.m("onTorchModeChanged enabled: ", " isFlashlightBlinking: ", "FlashlightController", z, isFlashlightBlinking);
                        if (isFlashlightBlinking) {
                            return;
                        }
                        SecFlashlightControllerImpl secFlashlightControllerImpl2 = FlashlightControllerImpl.this.mSecFlashlightController;
                        secFlashlightControllerImpl2.mIsEnabled = z;
                        secFlashlightControllerImpl2.mSecureSettings.putIntForUser("flashlight_enabled", z ? 1 : 0, ((UserTrackerImpl) secFlashlightControllerImpl2.mUserTracker).getUserId());
                        secFlashlightControllerImpl2.updateFlashlightNotification(z);
                        if (QpRune.QUICK_SUBSCREEN_SETTINGS) {
                            SubscreenFlashLightController subscreenFlashLightController = secFlashlightControllerImpl2.mSubscreenFlashlightController;
                            Handler handler = subscreenFlashLightController.mUiHandler;
                            if (z) {
                                SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = subscreenFlashLightController.mFlashLightPresentationView;
                                if (subscreenQSControllerContract$FlashLightView == null || ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).getActivityState() == 0) {
                                    subscreenFlashLightController.startFlashActivity();
                                }
                                handler.post(new SubscreenFlashLightController$$ExternalSyntheticLambda0(subscreenFlashLightController, 0));
                            } else {
                                SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView2 = subscreenFlashLightController.mFlashLightPresentationView;
                                if (subscreenQSControllerContract$FlashLightView2 != null) {
                                    SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = (SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView2;
                                    RecyclerView$$ExternalSyntheticOutline0.m(subroomFlashLightSettingsActivity.mSubScreen, "SubroomFlashLightSettingsActivity", new StringBuilder("getCurrentSubScreen: "));
                                    if (subroomFlashLightSettingsActivity.mSubScreen == 5) {
                                        handler.post(new SubscreenFlashLightController$$ExternalSyntheticLambda0(subscreenFlashLightController, 1));
                                    }
                                }
                            }
                        }
                    }
                    setCameraAvailable(true);
                    setTorchMode(z);
                    FlashlightControllerImpl.this.mSecureSettings.putInt("flashlight_available", 1);
                }
            }

            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public final void onTorchModeUnavailable(String str) {
                if (TextUtils.equals(str, (CharSequence) FlashlightControllerImpl.this.mCameraId.get())) {
                    setCameraAvailable(false);
                    FlashlightControllerImpl.this.mSecureSettings.putInt("flashlight_available", 0);
                    if (FlashlightControllerImpl.this.mSecFlashlightController != null) {
                        setTorchMode(false);
                        SecFlashlightControllerImpl secFlashlightControllerImpl = FlashlightControllerImpl.this.mSecFlashlightController;
                        secFlashlightControllerImpl.getClass();
                        if (QpRune.QUICK_SUBSCREEN_SETTINGS && !secFlashlightControllerImpl.mDisplayLifecycle.mIsFolderOpened) {
                            if (secFlashlightControllerImpl.mIsEnabled) {
                                secFlashlightControllerImpl.mUiHandler.postDelayed(new SecFlashlightControllerImpl$$ExternalSyntheticLambda0(secFlashlightControllerImpl, 0), 500L);
                            }
                            SubscreenFlashLightController subscreenFlashLightController = secFlashlightControllerImpl.mSubscreenFlashlightController;
                            subscreenFlashLightController.mUiHandler.post(new SubscreenFlashLightController$$ExternalSyntheticLambda0(subscreenFlashLightController, 2));
                        }
                        secFlashlightControllerImpl.updateFlashlightNotification(false);
                    }
                }
            }

            public final void setCameraAvailable(boolean z) {
                boolean z2;
                synchronized (FlashlightControllerImpl.this) {
                    FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                    z2 = flashlightControllerImpl.mTorchAvailable != z;
                    flashlightControllerImpl.mTorchAvailable = z;
                }
                if (z2) {
                    Log.d("FlashlightController", "dispatchAvailabilityChanged(" + z + ")");
                    FlashlightControllerImpl.this.dispatchListeners(2, z);
                }
            }

            public final void setTorchMode(boolean z) {
                boolean z2;
                synchronized (FlashlightControllerImpl.this) {
                    FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                    z2 = flashlightControllerImpl.mFlashlightEnabled != z;
                    flashlightControllerImpl.mFlashlightEnabled = z;
                }
                if (z2) {
                    Log.d("FlashlightController", "dispatchModeChanged(" + z + ")");
                    FlashlightControllerImpl.this.dispatchListeners(1, z);
                    FlashlightControllerImpl.this.mFlashLightDebugLogs.add("callback at : " + FlashlightControllerImpl.this.makeTime$1() + " enabled = " + z);
                    if (FlashlightControllerImpl.this.mFlashLightDebugLogs.size() > 20) {
                        FlashlightControllerImpl.this.mFlashLightDebugLogs.remove(0);
                    }
                }
            }
        };
        this.mCameraManager = cameraManager;
        this.mExecutor = executor;
        this.mCameraId = new AtomicReference(null);
        this.mSecureSettings = secureSettings;
        this.mHasFlashlight = packageManager.hasSystemFeature("android.hardware.camera.flash");
        if (!atomicBoolean.getAndSet(true)) {
            dumpManager.getClass();
            DumpManager.registerDumpable$default(dumpManager, "FlashlightControllerImpl", this);
            executor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this));
        }
        this.mSecFlashlightController = new SecFlashlightControllerImpl(cameraManager, context, secureSettings, settingsHelper, packageManager, this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) obj;
        synchronized (this.mListeners) {
            try {
                if (this.mCameraId.get() == null) {
                    this.mExecutor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this));
                }
                cleanUpListenersLocked(flashlightListener);
                this.mListeners.add(new WeakReference(flashlightListener));
                flashlightListener.onFlashlightAvailabilityChanged(isAvailable());
                flashlightListener.onFlashlightChanged(isEnabled());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cleanUpListenersLocked(FlashlightController.FlashlightListener flashlightListener) {
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            FlashlightController.FlashlightListener flashlightListener2 = (FlashlightController.FlashlightListener) ((WeakReference) this.mListeners.get(size)).get();
            if (flashlightListener2 == null || flashlightListener2 == flashlightListener) {
                this.mListeners.remove(size);
            }
        }
    }

    public final void dispatchListeners(int i, boolean z) {
        synchronized (this.mListeners) {
            try {
                ArrayList arrayList = new ArrayList(this.mListeners);
                int size = arrayList.size();
                boolean z2 = false;
                for (int i2 = 0; i2 < size; i2++) {
                    FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) ((WeakReference) arrayList.get(i2)).get();
                    if (flashlightListener == null) {
                        z2 = true;
                    } else if (i == 0) {
                        flashlightListener.onFlashlightError();
                    } else if (i == 1) {
                        flashlightListener.onFlashlightChanged(z);
                    } else if (i == 2) {
                        flashlightListener.onFlashlightAvailabilityChanged(z);
                    }
                }
                if (z2) {
                    cleanUpListenersLocked(null);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        boolean z;
        printWriter.println("FlashlightController state:");
        printWriter.print("  mCameraId=");
        printWriter.println(this.mCameraId);
        printWriter.print("  mFlashlightEnabled=");
        printWriter.println(this.mFlashlightEnabled);
        printWriter.print("  mTorchAvailable=");
        printWriter.println(this.mTorchAvailable);
        SecFlashlightControllerImpl secFlashlightControllerImpl = this.mSecFlashlightController;
        if (secFlashlightControllerImpl != null) {
            printWriter.print("mIsThermalRestricted=");
            synchronized (secFlashlightControllerImpl) {
                z = secFlashlightControllerImpl.mIsThermalRestricted;
            }
            printWriter.println(z);
        }
    }

    public final String ensureCameraID() {
        if (this.mCameraId.get() != null) {
            return (String) this.mCameraId.get();
        }
        this.mExecutor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this));
        return null;
    }

    public final synchronized boolean isAvailable() {
        boolean z;
        boolean z2;
        try {
            SecFlashlightControllerImpl secFlashlightControllerImpl = this.mSecFlashlightController;
            if (secFlashlightControllerImpl == null) {
                return this.mTorchAvailable;
            }
            if (this.mTorchAvailable) {
                synchronized (secFlashlightControllerImpl) {
                    z2 = secFlashlightControllerImpl.mIsThermalRestricted;
                }
                if (!z2 && !this.mSecFlashlightController.isFlashlightBlinking()) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized boolean isEnabled() {
        return this.mFlashlightEnabled;
    }

    public final String makeTime$1() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        return String.format("%02d:%02d:%02d.%03d", Integer.valueOf(this.mCalendar.get(11)), Integer.valueOf(this.mCalendar.get(12)), Integer.valueOf(this.mCalendar.get(13)), Integer.valueOf(this.mCalendar.get(14)));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) obj;
        synchronized (this.mListeners) {
            cleanUpListenersLocked(flashlightListener);
        }
    }

    public final void setFlashlight(final boolean z) {
        if (this.mHasFlashlight) {
            if (this.mCameraId.get() == null) {
                this.mExecutor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this));
            }
            this.mExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                    boolean z2 = z;
                    if (flashlightControllerImpl.mCameraId.get() == null) {
                        return;
                    }
                    synchronized (flashlightControllerImpl) {
                        if (z2) {
                            try {
                                if (!flashlightControllerImpl.isAvailable()) {
                                    return;
                                }
                            } catch (CameraAccessException e) {
                                Log.e("FlashlightController", "Couldn't set torch mode", e);
                                flashlightControllerImpl.dispatchListeners(1, false);
                            } finally {
                            }
                        }
                        if (flashlightControllerImpl.mFlashlightEnabled != z2) {
                            flashlightControllerImpl.mCameraManager.setTorchMode((String) flashlightControllerImpl.mCameraId.get(), z2);
                            SecFlashlightControllerImpl secFlashlightControllerImpl = flashlightControllerImpl.mSecFlashlightController;
                            if (secFlashlightControllerImpl != null) {
                                if (z2) {
                                    secFlashlightControllerImpl.setFlashlightLevel(secFlashlightControllerImpl.mFlashlightLevel + 1, false);
                                }
                                flashlightControllerImpl.mFlashLightDebugLogs.add("setTorch at : " + flashlightControllerImpl.makeTime$1() + " cameraID = " + flashlightControllerImpl.mCameraId + " enabled = " + z2);
                                if (flashlightControllerImpl.mFlashLightDebugLogs.size() > 20) {
                                    flashlightControllerImpl.mFlashLightDebugLogs.remove(0);
                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
