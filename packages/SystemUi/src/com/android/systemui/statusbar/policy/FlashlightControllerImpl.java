package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController$$ExternalSyntheticLambda0;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FlashlightControllerImpl implements FlashlightController {
    public static final boolean DEBUG = Log.isLoggable("FlashlightControllerImpl", 3);
    public static final int[] FLASHLIGHT_VALUE = {1001, 1002, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, 1006, EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE};
    public final C33945 mCameraDeviceStateCallback;
    public AtomicReference mCameraId;
    public CameraManager mCameraManager;
    public String mClientName;
    public final Context mContext;
    public final DisplayLifecycle mDisplayLifecycle;
    public final Executor mExecutor;
    public boolean mFlashlightEnabled;
    public int mFlashlightLevel;
    public Handler mHandler;
    public final boolean mHasFlashlight;
    public boolean mIsLowBattery;
    public boolean mIsThermalRestricted;
    public final PackageManager mPackageManager;
    public final C33934 mSettingsCallback;
    public final SettingsHelper mSettingsHelper;
    public final SubscreenFlashLightController mSubscreenFlashlightController;
    public boolean mTorchAvailable;
    public final C33923 mTorchCallback;
    public final Handler mUiHandler;
    public final ArrayList mFlashLightDebugLogs = new ArrayList();
    public final Calendar mCalendar = Calendar.getInstance(TimeZone.getDefault());
    public final ArrayList mListeners = new ArrayList(1);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.FlashlightControllerImpl$4 */
    public final class C33934 implements SettingsHelper.OnChangedCallback {
        public C33934() {
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            if (uri.equals(Settings.System.getUriFor("Flashlight_brightness_level"))) {
                FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                int intValue = flashlightControllerImpl.mSettingsHelper.mItemLists.get("Flashlight_brightness_level").getIntValue();
                int i = 0;
                while (true) {
                    int[] iArr = FlashlightControllerImpl.FLASHLIGHT_VALUE;
                    if (i >= iArr.length) {
                        i = iArr.length / 2;
                        break;
                    } else if (intValue == iArr[i]) {
                        break;
                    } else {
                        i++;
                    }
                }
                flashlightControllerImpl.mFlashlightLevel = i;
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("FlashlightLevel changed : "), flashlightControllerImpl.mFlashlightLevel, "FlashlightControllerImpl");
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.policy.FlashlightControllerImpl$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.policy.FlashlightControllerImpl$5] */
    public FlashlightControllerImpl(Context context, DumpManager dumpManager, CameraManager cameraManager, Executor executor, SecureSettings secureSettings, BroadcastSender broadcastSender, PackageManager packageManager, DisplayLifecycle displayLifecycle, SettingsHelper settingsHelper) {
        int i = 0;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.mUiHandler = new Handler(Looper.getMainLooper());
        this.mTorchCallback = new CameraManager.TorchCallback() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl.3
            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public final void onTorchModeChanged(String str, boolean z) {
                FlashlightControllerImpl flashlightControllerImpl;
                DisplayLifecycle displayLifecycle2;
                SubscreenFlashLightController subscreenFlashLightController;
                if (TextUtils.equals(str, (CharSequence) FlashlightControllerImpl.this.mCameraId.get())) {
                    int i2 = 1;
                    setCameraAvailable(true);
                    FlashlightControllerImpl.this.getClass();
                    boolean equalsIgnoreCase = "1".equalsIgnoreCase(SystemProperties.get("service.cameraflashnoti.running"));
                    EmergencyButtonController$$ExternalSyntheticOutline0.m59m("onTorchModeChanged enabled: ", z, " isBlinking: ", equalsIgnoreCase, "FlashlightControllerImpl");
                    if (equalsIgnoreCase) {
                        return;
                    }
                    setTorchMode(z);
                    Settings.Secure.putInt(FlashlightControllerImpl.this.mSettingsHelper.mContext.getContentResolver(), "flashlight_available", 1);
                    Settings.Secure.putInt(FlashlightControllerImpl.this.mSettingsHelper.mContext.getContentResolver(), "flashlight_enabled", z ? 1 : 0);
                    if (!QpRune.QUICK_SETTINGS_SUBSCREEN || (displayLifecycle2 = (flashlightControllerImpl = FlashlightControllerImpl.this).mDisplayLifecycle) == null || displayLifecycle2.mIsFolderOpened || (subscreenFlashLightController = flashlightControllerImpl.mSubscreenFlashlightController) == null) {
                        return;
                    }
                    Handler handler = subscreenFlashLightController.mUiHandler;
                    if (z) {
                        SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = subscreenFlashLightController.mFlashLightPresentationView;
                        if (!((subscreenQSControllerContract$FlashLightView == null || ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).getActivityState() == 0) ? false : true)) {
                            subscreenFlashLightController.startFlashActivity();
                        }
                        handler.post(new SubscreenFlashLightController$$ExternalSyntheticLambda1(subscreenFlashLightController, i2));
                        return;
                    }
                    SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView2 = subscreenFlashLightController.mFlashLightPresentationView;
                    if (subscreenQSControllerContract$FlashLightView2 != null) {
                        SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = (SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView2;
                        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("getCurrentSubScreen: "), subroomFlashLightSettingsActivity.mSubScreen, "SubroomFlashLightSettingsActivity");
                        if (subroomFlashLightSettingsActivity.mSubScreen == 5) {
                            handler.post(new SubscreenFlashLightController$$ExternalSyntheticLambda1(subscreenFlashLightController, 2));
                        }
                    }
                }
            }

            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public final void onTorchModeUnavailable(String str) {
                FlashlightControllerImpl flashlightControllerImpl;
                SubscreenFlashLightController subscreenFlashLightController;
                DisplayLifecycle displayLifecycle2;
                if (TextUtils.equals(str, (CharSequence) FlashlightControllerImpl.this.mCameraId.get())) {
                    Log.d("FlashlightControllerImpl", "onTorchModeUnavailable");
                    int i2 = 0;
                    if (QpRune.QUICK_PANEL_SUBSCREEN && (subscreenFlashLightController = (flashlightControllerImpl = FlashlightControllerImpl.this).mSubscreenFlashlightController) != null && (displayLifecycle2 = flashlightControllerImpl.mDisplayLifecycle) != null && !displayLifecycle2.mIsFolderOpened) {
                        boolean z = flashlightControllerImpl.mFlashlightEnabled;
                        Handler handler = subscreenFlashLightController.mUiHandler;
                        if (z) {
                            Log.d("SubscreenFlashLightController", "onTorchModeUnavailable - flashlightEnabled" + z);
                            handler.postDelayed(new SubscreenFlashLightController$$ExternalSyntheticLambda0(), 500L);
                        }
                        handler.post(new SubscreenFlashLightController$$ExternalSyntheticLambda1(subscreenFlashLightController, i2));
                    }
                    setCameraAvailable(false);
                    setTorchMode(false);
                    Settings.Secure.putInt(FlashlightControllerImpl.this.mSettingsHelper.mContext.getContentResolver(), "flashlight_available", 0);
                }
            }

            @Override // android.hardware.camera2.CameraManager.TorchCallback
            public final void onTorchStrengthLevelChanged(String str, int i2) {
                if (TextUtils.equals(str, FlashlightControllerImpl.this.mCameraId.toString())) {
                    FlashlightControllerImpl.this.setFlashlightLevel(i2, false);
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
                    if (FlashlightControllerImpl.DEBUG) {
                        Log.d("FlashlightControllerImpl", "dispatchAvailabilityChanged(" + z + ")");
                    }
                    FlashlightControllerImpl flashlightControllerImpl2 = FlashlightControllerImpl.this;
                    flashlightControllerImpl2.dispatchListeners(2, flashlightControllerImpl2.isAvailable());
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
                    if (FlashlightControllerImpl.DEBUG) {
                        Log.d("FlashlightControllerImpl", "dispatchModeChanged(" + z + ")");
                    }
                    FlashlightControllerImpl.this.dispatchListeners(1, z);
                    FlashlightControllerImpl.this.mFlashLightDebugLogs.add("callback at : " + FlashlightControllerImpl.this.makeTime() + " enabled = " + z);
                    if (FlashlightControllerImpl.this.mFlashLightDebugLogs.size() > 20) {
                        FlashlightControllerImpl.this.mFlashLightDebugLogs.remove(0);
                    }
                }
            }
        };
        this.mSettingsCallback = new C33934();
        this.mCameraDeviceStateCallback = new CameraManager.SemCameraDeviceStateCallback() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl.5
            public final void onCameraDeviceStateChanged(String str, int i2, int i3, String str2) {
                if (i3 == 1) {
                    FlashlightControllerImpl.this.mClientName = str2;
                }
            }
        };
        this.mExecutor = executor;
        this.mCameraId = new AtomicReference(null);
        this.mHasFlashlight = packageManager.hasSystemFeature("android.hardware.camera.flash");
        this.mContext = context;
        this.mCameraManager = null;
        this.mSettingsHelper = settingsHelper;
        ensureHandler();
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mIsThermalRestricted = false;
        boolean z = powerManager.getCurrentThermalStatus() >= 5;
        if (this.mIsThermalRestricted != z) {
            this.mIsThermalRestricted = z;
            if (z) {
                setFlashlight(false);
            }
            dispatchListeners(2, isAvailable());
        }
        powerManager.addThermalStatusListener(new PowerManager.OnThermalStatusChangedListener() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl.1
            @Override // android.os.PowerManager.OnThermalStatusChangedListener
            public final void onThermalStatusChanged(int i2) {
                boolean z2 = i2 >= 5;
                FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                boolean z3 = FlashlightControllerImpl.DEBUG;
                if (flashlightControllerImpl.mIsThermalRestricted == z2) {
                    return;
                }
                flashlightControllerImpl.mIsThermalRestricted = z2;
                if (z2) {
                    flashlightControllerImpl.setFlashlight(false);
                }
                flashlightControllerImpl.dispatchListeners(2, flashlightControllerImpl.isAvailable());
            }
        });
        if (QpRune.QUICK_SETTINGS_SUBSCREEN) {
            this.mSubscreenFlashlightController = SubscreenFlashLightController.getInstance(context);
            this.mDisplayLifecycle = displayLifecycle;
        }
        this.mPackageManager = packageManager;
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "FlashlightControllerImpl", this);
        executor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this, i));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) obj;
        synchronized (this.mListeners) {
            if (this.mCameraId.get() == null) {
                this.mExecutor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this, 1));
            }
            cleanUpListenersLocked(flashlightListener);
            this.mListeners.add(new WeakReference(flashlightListener));
            flashlightListener.onFlashlightAvailabilityChanged(isAvailable());
            flashlightListener.onFlashlightChanged(this.mFlashlightEnabled);
        }
    }

    public final void addListener(FlashlightController.FlashlightListener flashlightListener) {
        synchronized (this.mListeners) {
            if (this.mCameraId.get() == null) {
                this.mExecutor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this, 2));
            }
            cleanUpListenersLocked(flashlightListener);
            this.mListeners.add(new WeakReference(flashlightListener));
        }
        this.mSettingsHelper.registerCallback(this.mSettingsCallback, Settings.System.getUriFor("Flashlight_brightness_level"));
        this.mSettingsCallback.onChanged(Settings.System.getUriFor("Flashlight_brightness_level"));
    }

    public final void cleanUpListenersLocked(FlashlightController.FlashlightListener flashlightListener) {
        ArrayList arrayList = this.mListeners;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            FlashlightController.FlashlightListener flashlightListener2 = (FlashlightController.FlashlightListener) ((WeakReference) arrayList.get(size)).get();
            if (flashlightListener2 == null || flashlightListener2 == flashlightListener) {
                arrayList.remove(size);
            }
        }
    }

    public final void dispatchListeners(int i, boolean z) {
        synchronized (this.mListeners) {
            int size = this.mListeners.size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                FlashlightController.FlashlightListener flashlightListener = (FlashlightController.FlashlightListener) ((WeakReference) this.mListeners.get(i2)).get();
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
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("FlashlightController state:");
        printWriter.print("mCameraId=");
        printWriter.println(this.mCameraId);
        printWriter.print("mFlashlightEnabled=");
        printWriter.println(this.mFlashlightEnabled);
        printWriter.print("mTorchAvailable=");
        printWriter.println(this.mTorchAvailable);
        printWriter.print("mIsThermalRestricted=");
        printWriter.println(this.mIsThermalRestricted);
    }

    public final synchronized void ensureHandler() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("FlashlightControllerImpl", 10);
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        }
    }

    public final String getCameraId() {
        for (String str : this.mCameraManager.getCameraIdList()) {
            CameraCharacteristics cameraCharacteristics = this.mCameraManager.getCameraCharacteristics(str);
            Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (bool != null && bool.booleanValue() && num != null && num.intValue() == 1) {
                return str;
            }
        }
        return null;
    }

    public final synchronized boolean isAvailable() {
        boolean z;
        if (this.mTorchAvailable && !this.mIsThermalRestricted) {
            z = "1".equalsIgnoreCase(SystemProperties.get("service.cameraflashnoti.running")) ? false : true;
        }
        return z;
    }

    public final synchronized boolean isEnabled() {
        return this.mFlashlightEnabled;
    }

    public final String makeTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Calendar calendar = this.mCalendar;
        calendar.setTimeInMillis(currentTimeMillis);
        return String.format("%02d:%02d:%02d.%03d", Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)));
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
            Object obj = this.mCameraId.get();
            Executor executor = this.mExecutor;
            if (obj == null) {
                executor.execute(new FlashlightControllerImpl$$ExternalSyntheticLambda0(this, 3));
            }
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                    boolean z2 = z;
                    if (flashlightControllerImpl.mCameraId.get() == null) {
                        return;
                    }
                    synchronized (flashlightControllerImpl) {
                        if (z2) {
                            if (flashlightControllerImpl.mIsThermalRestricted) {
                                return;
                            }
                        }
                        if (flashlightControllerImpl.mFlashlightEnabled != z2) {
                            flashlightControllerImpl.mFlashlightEnabled = z2;
                            try {
                                flashlightControllerImpl.mCameraManager.semSetTorchMode(flashlightControllerImpl.mCameraId.toString(), z2, flashlightControllerImpl.mFlashlightLevel + 1);
                                if (z2) {
                                    flashlightControllerImpl.setFlashlightLevel(flashlightControllerImpl.mFlashlightLevel + 1, false);
                                }
                                flashlightControllerImpl.mFlashLightDebugLogs.add("setTorch at : " + flashlightControllerImpl.makeTime() + " cameraID = " + flashlightControllerImpl.mCameraId + " enabled = " + z2);
                                if (flashlightControllerImpl.mFlashLightDebugLogs.size() > 20) {
                                    flashlightControllerImpl.mFlashLightDebugLogs.remove(0);
                                }
                            } catch (CameraAccessException e) {
                                Log.e("FlashlightControllerImpl", "Couldn't set torch mode", e);
                                flashlightControllerImpl.mFlashlightEnabled = false;
                                flashlightControllerImpl.dispatchListeners(1, false);
                            }
                        }
                        flashlightControllerImpl.dispatchListeners(1, flashlightControllerImpl.mFlashlightEnabled);
                    }
                }
            });
        }
    }

    public final void setFlashlightLevel(int i, boolean z) {
        try {
            this.mCameraManager.semSetTorchMode(this.mCameraId.toString(), this.mFlashlightEnabled, i);
        } catch (CameraAccessException e) {
            Log.d("FlashlightControllerImpl", "Couldn't set flashlight level", e);
        }
        if (z) {
            int i2 = FLASHLIGHT_VALUE[i - 1];
            SettingsHelper settingsHelper = this.mSettingsHelper;
            Settings.System.putIntForUser(settingsHelper.mContext.getContentResolver(), "Flashlight_brightness_level", i2, -2);
            settingsHelper.mItemLists.get("Flashlight_brightness_level").mIntValue = i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.pm.PackageManager] */
    public final void showUnavailableMessage() {
        ApplicationInfo applicationInfo;
        boolean equalsIgnoreCase = "1".equalsIgnoreCase(SystemProperties.get("service.cameraflashnoti.running"));
        Context context = this.mContext;
        if (equalsIgnoreCase) {
            showWarningMessage(context.getString(R.string.unable_to_turn_on_being_used_by_app, context.getString(R.string.flash_notification)));
            return;
        }
        String str = this.mClientName;
        if (str == null || str.contains("client.pid")) {
            showWarningMessage(context.getString(R.string.unable_to_turn_on_being_used_by_camera));
            return;
        }
        Object[] objArr = new Object[1];
        ?? r3 = this.mClientName;
        ?? r4 = this.mPackageManager;
        try {
            applicationInfo = r4.getApplicationInfo(r3, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            r3 = r4.getApplicationLabel(applicationInfo);
        }
        objArr[0] = (String) r3;
        showWarningMessage(context.getString(R.string.unable_to_turn_on_being_used_by_app, objArr));
    }

    public final void showWarningMessage(CharSequence charSequence) {
        Context context;
        DisplayLifecycle displayLifecycle;
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened) {
            context = this.mContext;
        } else {
            ((SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class)).getClass();
            context = SubscreenQsPanelController.mContext;
        }
        Toast.makeText(context, charSequence, 0).show();
    }

    public final void tryInitCamera() {
        if (this.mCameraId.get() != null || this.mHandler == null) {
            return;
        }
        final AtomicReference atomicReference = new AtomicReference(null);
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.FlashlightControllerImpl.2
            @Override // java.lang.Runnable
            public final void run() {
                FlashlightControllerImpl flashlightControllerImpl = FlashlightControllerImpl.this;
                flashlightControllerImpl.mCameraManager = (CameraManager) flashlightControllerImpl.mContext.getSystemService("camera");
                try {
                    atomicReference.set(FlashlightControllerImpl.this.getCameraId());
                    Log.d("FlashlightControllerImpl", "cameraId" + atomicReference);
                    FlashlightControllerImpl flashlightControllerImpl2 = FlashlightControllerImpl.this;
                    AtomicReference atomicReference2 = atomicReference;
                    flashlightControllerImpl2.mCameraId = atomicReference2;
                    if (atomicReference2.get() != null) {
                        FlashlightControllerImpl flashlightControllerImpl3 = FlashlightControllerImpl.this;
                        flashlightControllerImpl3.mCameraManager.registerTorchCallback(flashlightControllerImpl3.mExecutor, flashlightControllerImpl3.mTorchCallback);
                        FlashlightControllerImpl flashlightControllerImpl4 = FlashlightControllerImpl.this;
                        flashlightControllerImpl4.mCameraManager.registerSemCameraDeviceStateCallback(flashlightControllerImpl4.mCameraDeviceStateCallback, flashlightControllerImpl4.mHandler);
                    }
                } catch (Throwable th) {
                    try {
                        Log.e("FlashlightControllerImpl", "Couldn't initialize.", th);
                    } finally {
                        FlashlightControllerImpl.this.mCameraId = atomicReference;
                    }
                }
            }
        });
    }

    public final void updateTorchCallback() {
        Log.d("FlashlightControllerImpl", "updateTorchCallback for mCameraId" + this.mCameraId);
        if (this.mCameraId.get() == null) {
            if (this.mCameraManager == null) {
                this.mCameraManager = (CameraManager) this.mContext.getSystemService("camera");
            }
            AtomicReference atomicReference = new AtomicReference(null);
            try {
                atomicReference.set(getCameraId());
                this.mCameraId = atomicReference;
                Log.d("FlashlightControllerImpl", "updateTorchCallback mCameraId" + atomicReference);
                if (this.mCameraId.get() != null) {
                    ensureHandler();
                    this.mCameraManager.registerTorchCallback(this.mExecutor, this.mTorchCallback);
                    this.mCameraManager.registerSemCameraDeviceStateCallback(this.mCameraDeviceStateCallback, this.mHandler);
                }
            } catch (Throwable th) {
                try {
                    Log.e("FlashlightControllerImpl", "updateTorchCallback Couldn't initialize.", th);
                } finally {
                    this.mCameraId = atomicReference;
                    Log.d("FlashlightControllerImpl", "updateTorchCallback mCameraId" + atomicReference);
                }
            }
        }
    }
}
