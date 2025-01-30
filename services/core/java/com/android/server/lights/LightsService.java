package com.android.server.lights;

import android.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SystemSensorManager;
import android.hardware.light.HwLightState;
import android.hardware.light.ILights;
import android.hardware.lights.ILightsManager;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.net.Uri;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.util.SparseArray;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.lights.LightsService;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import vendor.samsung.hardware.light.ISehLights;
import vendor.samsung.hardware.light.SehHwLight;

/* loaded from: classes2.dex */
public class LightsService extends SystemService {
    public static int mPrevSvcLedState;
    public static int mSvcLedColor;
    public static int mSvcLedMode;
    public static int mSvcLedOffMS;
    public static int mSvcLedOnMS;
    public static int mSvcLedState;
    public final String LED_LOW_POWER_PATH;
    public boolean isLightSensorEnabled;
    public AlarmManager mAlarmManagerForSvcLED;
    public final Context mContext;
    public LogicalLight mCoverBatteryLight;
    public CoverManager mCoverManager;
    public LogicalLight mCoverNotiLight;
    public boolean mCoverOpened;
    public CoverManager.StateListener mCoverStateListener;
    public int mCoverType;
    public int mDelayForcedSvcLEDTask;

    /* renamed from: mH */
    public Handler f1711mH;
    public boolean mInitCompleteForSvcLED;
    public boolean mInitializedWakeLockPath;
    public boolean mIsLEDChanged;
    public int mLastSvcLedId;
    public int mLedLowPower;
    public SensorEventListener mLightListener;
    public Sensor mLightSensor;
    public final SparseArray mLightsById;
    public final LightImpl[] mLightsByType;
    final LightsManagerBinderService mManagerService;
    public final String[] mNewWakeLockPaths;
    public SensorManager mSensorManager;
    public final LightsManager mService;
    public SvcLEDHandler mSvcLEDHandler;
    public final HandlerThread mSvcLEDThread;
    public int mUpdateSvcLEDDelay;
    public PendingIntent mUpdateSvcLEDPendingIntent;
    public boolean mUseLEDAutoBrightness;
    public boolean mUsePatternLED;
    public boolean mUseSoftwareAutoBrightness;
    public final Supplier mVintfLights;
    public final Supplier mVintfSehLights;
    public boolean mWakeLockAcquired;
    public String mWakeLockPath;
    public String mWakeUnlockPath;

    public static native void setLight_native(int i, int i2, int i3, int i4, int i5, int i6);

    public int convertToBitMask(int i) {
        switch (i) {
            case 10:
                return 8;
            case 11:
                return 1;
            case 12:
            default:
                return -58;
            case 13:
                return 16;
            case 14:
                return 32;
        }
    }

    public final void populateAvailableLightsFromHidl(Context context) {
    }

    public String translateID(int i) {
        switch (i) {
            case 0:
                return "BACKLIGHT";
            case 1:
                return "KEYBOARD";
            case 2:
                return "BUTTON";
            case 3:
                return "BATTERY";
            case 4:
                return "NOTIFICATIONS";
            case 5:
                return "ATTENTION";
            case 6:
                return "BLUETOOTH";
            case 7:
                return "WIFI";
            case 8:
            default:
                return "translateID error";
            case 9:
                return "SUB_BACKLIGHT";
            case 10:
                return "COUNT";
        }
    }

    public String translateLightType(int i) {
        switch (i) {
            case 0:
                return "BACKLIGHT";
            case 1:
                return "KEYBOARD";
            case 2:
                return "BUTTON";
            case 3:
                return "BATTERY";
            case 4:
                return "NOTIFICATIONS";
            case 5:
                return "ATTENTION";
            case 6:
                return "BLUETOOTH";
            case 7:
                return "WIFI";
            case 8:
            default:
                return "translateLightType error";
            case 9:
                return "SUB_BACKLIGHT";
        }
    }

    public String translateMode(int i) {
        if (i == 0) {
            return "LIGHT_FLASH_NONE";
        }
        if (i == 1) {
            return "LIGHT_FLASH_TIMED";
        }
        if (i == 2) {
            return "LIGHT_FLASH_HARDWARE";
        }
        switch (i) {
            case 10:
                return "LIGHT_SEC_FLASH_CHARGING";
            case 11:
                return "LIGHT_SEC_FLASH_CHARGING_ERROR";
            case 12:
                return "LIGHT_SEC_FLASH_MISSED_NOTIFICATION";
            case 13:
                return "LIGHT_SEC_FLASH_LOW_BATTERY";
            case 14:
                return "LIGHT_SEC_FLASH_FULLY_CHARGED";
            default:
                return "translateMode error";
        }
    }

    public final class LightsManagerBinderService extends ILightsManager.Stub {
        public final List mSessions;

        public LightsManagerBinderService() {
            this.mSessions = new ArrayList();
        }

        public final class Session implements Comparable {
            public final int mPriority;
            public final SparseArray mRequests = new SparseArray();
            public final IBinder mToken;

            public Session(IBinder iBinder, int i) {
                this.mToken = iBinder;
                this.mPriority = i;
            }

            public void setRequest(int i, LightState lightState) {
                if (lightState != null) {
                    this.mRequests.put(i, lightState);
                } else {
                    this.mRequests.remove(i);
                }
            }

            @Override // java.lang.Comparable
            public int compareTo(Session session) {
                return Integer.compare(session.mPriority, this.mPriority);
            }
        }

        public List getLights() {
            ArrayList arrayList;
            LightsService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_LIGHTS", "getLights requires CONTROL_DEVICE_LIGHTS_PERMISSION");
            synchronized (LightsService.this) {
                arrayList = new ArrayList();
                for (int i = 0; i < LightsService.this.mLightsById.size(); i++) {
                    if (!((LightImpl) LightsService.this.mLightsById.valueAt(i)).isSystemLight()) {
                        SehHwLight sehHwLight = ((LightImpl) LightsService.this.mLightsById.valueAt(i)).mHwLight;
                        arrayList.add(new Light(sehHwLight.f1801id, sehHwLight.ordinal, sehHwLight.type));
                    }
                }
            }
            return arrayList;
        }

        public void setLightStates(IBinder iBinder, int[] iArr, LightState[] lightStateArr) {
            LightsService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_LIGHTS", "setLightStates requires CONTROL_DEVICE_LIGHTS permission");
            boolean z = true;
            Preconditions.checkState(iArr.length == lightStateArr.length);
            synchronized (LightsService.this) {
                Session sessionLocked = getSessionLocked((IBinder) Preconditions.checkNotNull(iBinder));
                if (sessionLocked == null) {
                    z = false;
                }
                Preconditions.checkState(z, "not registered");
                checkRequestIsValid(iArr);
                for (int i = 0; i < iArr.length; i++) {
                    sessionLocked.setRequest(iArr[i], lightStateArr[i]);
                }
                invalidateLightStatesLocked();
            }
        }

        public LightState getLightState(int i) {
            LightState lightState;
            LightsService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_LIGHTS", "getLightState(@TestApi) requires CONTROL_DEVICE_LIGHTS permission");
            synchronized (LightsService.this) {
                LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.get(i);
                if (lightImpl == null || lightImpl.isSystemLight()) {
                    throw new IllegalArgumentException("Invalid light: " + i);
                }
                lightState = new LightState(lightImpl.getColor());
            }
            return lightState;
        }

        public void openSession(final IBinder iBinder, int i) {
            LightsService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_LIGHTS", "openSession requires CONTROL_DEVICE_LIGHTS permission");
            Preconditions.checkNotNull(iBinder);
            synchronized (LightsService.this) {
                Preconditions.checkState(getSessionLocked(iBinder) == null, "already registered");
                try {
                    iBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.lights.LightsService$LightsManagerBinderService$$ExternalSyntheticLambda0
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            LightsService.LightsManagerBinderService.this.lambda$openSession$0(iBinder);
                        }
                    }, 0);
                    this.mSessions.add(new Session(iBinder, i));
                    Collections.sort(this.mSessions);
                } catch (RemoteException e) {
                    Slog.m75e("LightsService", "Couldn't open session, client already died", e);
                    throw new IllegalArgumentException("Client is already dead.");
                }
            }
        }

        public void closeSession(IBinder iBinder) {
            LightsService.this.getContext().enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_LIGHTS", "closeSession requires CONTROL_DEVICE_LIGHTS permission");
            Preconditions.checkNotNull(iBinder);
            lambda$openSession$0(iBinder);
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(LightsService.this.getContext(), "LightsService", printWriter)) {
                synchronized (LightsService.this) {
                    if (LightsService.this.mVintfLights != null) {
                        printWriter.println("Service: aidl (" + LightsService.this.mVintfLights.get() + ")");
                    } else {
                        printWriter.println("Service: hidl");
                    }
                    printWriter.println("Lights:");
                    for (int i = 0; i < LightsService.this.mLightsById.size(); i++) {
                        LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.valueAt(i);
                        printWriter.println(String.format("  Light id=%d ordinal=%d color=%08x", Integer.valueOf(lightImpl.mHwLight.f1801id), Integer.valueOf(lightImpl.mHwLight.ordinal), Integer.valueOf(lightImpl.getColor())));
                    }
                    printWriter.println("Session clients:");
                    for (Session session : this.mSessions) {
                        printWriter.println("  Session token=" + session.mToken);
                        for (int i2 = 0; i2 < session.mRequests.size(); i2++) {
                            printWriter.println(String.format("    Request id=%d color=%08x", Integer.valueOf(session.mRequests.keyAt(i2)), Integer.valueOf(((LightState) session.mRequests.valueAt(i2)).getColor())));
                        }
                    }
                }
            }
        }

        /* renamed from: closeSessionInternal, reason: merged with bridge method [inline-methods] */
        public final void lambda$openSession$0(IBinder iBinder) {
            synchronized (LightsService.this) {
                Session sessionLocked = getSessionLocked(iBinder);
                if (sessionLocked != null) {
                    this.mSessions.remove(sessionLocked);
                    invalidateLightStatesLocked();
                }
            }
        }

        public final void checkRequestIsValid(int[] iArr) {
            for (int i : iArr) {
                LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.get(i);
                Preconditions.checkState((lightImpl == null || lightImpl.isSystemLight()) ? false : true, "Invalid lightId " + i);
            }
        }

        public final void invalidateLightStatesLocked() {
            int i;
            HashMap hashMap = new HashMap();
            int size = this.mSessions.size();
            while (true) {
                size--;
                i = 0;
                if (size < 0) {
                    break;
                }
                SparseArray sparseArray = ((Session) this.mSessions.get(size)).mRequests;
                while (i < sparseArray.size()) {
                    hashMap.put(Integer.valueOf(sparseArray.keyAt(i)), (LightState) sparseArray.valueAt(i));
                    i++;
                }
            }
            while (i < LightsService.this.mLightsById.size()) {
                LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.valueAt(i);
                if (!lightImpl.isSystemLight()) {
                    LightState lightState = (LightState) hashMap.get(Integer.valueOf(lightImpl.mHwLight.f1801id));
                    if (lightState != null) {
                        lightImpl.setColor(lightState.getColor());
                    } else {
                        lightImpl.turnOff();
                    }
                }
                i++;
            }
        }

        public final Session getSessionLocked(IBinder iBinder) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                if (iBinder.equals(((Session) this.mSessions.get(i)).mToken)) {
                    return (Session) this.mSessions.get(i);
                }
            }
            return null;
        }
    }

    public final class LightImpl extends LogicalLight {
        public int mBrightnessMode;
        public int mColor;
        public int mExtendedBrightness;
        public boolean mFlashing;
        public SehHwLight mHwLight;
        public boolean mInitialized;
        public final boolean mIsIDUsingPatternLED;
        public int mLastBrightnessMode;
        public int mLastColor;
        public int mMode;
        public int mOffMS;
        public int mOnMS;
        public boolean mUseLowPersistenceForVR;
        public boolean mVrModeEnabled;

        public LightImpl(Context context, SehHwLight sehHwLight) {
            int i;
            this.mHwLight = sehHwLight;
            this.mIsIDUsingPatternLED = LightsService.this.mUsePatternLED && ((i = this.mHwLight.type) == 3 || i == 4);
        }

        @Override // com.android.server.lights.LogicalLight
        public void setBrightness(float f) {
            setBrightness(f, 0);
        }

        public void setBrightness(float f, int i) {
            if (Float.isNaN(f)) {
                Slog.m79w("LightsService", "Brightness is not valid: " + f);
                return;
            }
            synchronized (this) {
                if (i == 2) {
                    Slog.m79w("LightsService", "setBrightness with LOW_PERSISTENCE unexpected #" + this.mHwLight.f1801id + ": brightness=" + f);
                    return;
                }
                int brightnessFloatToInt = BrightnessSynchronizer.brightnessFloatToInt(f) & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
                int i2 = (brightnessFloatToInt << 16) | (-16777216) | (brightnessFloatToInt << 8) | brightnessFloatToInt;
                int i3 = this.mHwLight.type;
                if (i3 != 0 && i3 != 9) {
                    setLightLocked(i2, 0, 0, 0, i);
                }
                setLightLocked(i2, Math.round(f * 255.0f * (PowerManagerUtil.USE_PERSONAL_AUTO_BRIGHTNESS_V3 ? 100 : 1)), 0, 0, 0, i);
            }
        }

        public void setColor(int i) {
            Slog.m72d("LightsService", "[api] [SvcLED] setColor : color: " + Integer.toHexString(i));
            synchronized (this) {
                setLightLocked(i, 0, 0, 0, 0);
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void setFlashing(int i, int i2, int i3, int i4) {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("[api] [SvcLED] setFlashing : type: ");
            sb.append(this.mHwLight.type);
            sb.append("(");
            sb.append(LightsService.this.translateLightType(this.mHwLight.type));
            sb.append("), color: ");
            sb.append(Integer.toHexString(i));
            sb.append(", mode: ");
            if (i2 == -1) {
                str = "Off";
            } else {
                str = i2 + "(" + LightsService.this.translateMode(i2) + ")";
            }
            sb.append(str);
            sb.append(", onMS: ");
            sb.append(i3);
            sb.append(", offMS: ");
            sb.append(i4);
            sb.append(", ");
            sb.append(LightsService.callerInfoToString());
            Slog.m72d("LightsService", sb.toString());
            if (LightsService.this.mCoverType != 8 && !LightsService.this.mCoverOpened) {
                Slog.m72d("LightsService", "[api] [SvcLED] S Cover is closed so don't need LED On");
                return;
            }
            synchronized (this) {
                if (this.mIsIDUsingPatternLED) {
                    synchronized (LightsService.this.mSvcLEDThread) {
                        LightsService.this.acquireWakeLockForLED();
                        LightsService.this.setSvcLedStateLocked(this.mHwLight.type, i, i2, i3, i4, true);
                        LightsService.this.enableSvcLEDLightSensorLocked(true);
                        LightsService.this.mSvcLEDHandler.removeMessages(1);
                        LightsService.this.mSvcLEDHandler.sendMessageDelayed(LightsService.this.mSvcLEDHandler.obtainMessage(1), LightsService.this.mDelayForcedSvcLEDTask);
                    }
                } else {
                    Slog.m72d("LightsService", "[SvcLED] not mIsIDUsingPatternLED");
                    setLightLocked(i, i2, i3, i4, 0);
                }
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void pulse() {
            pulse(16777215, 7);
        }

        public void pulse(int i, int i2) {
            Slog.m72d("LightsService", "[api] pulse : lightType: " + LightsService.this.translateLightType(this.mHwLight.type) + ", color: " + Integer.toHexString(i) + ", onMS: " + i2 + LightsService.callerInfoToString());
            synchronized (this) {
                if (this.mIsIDUsingPatternLED) {
                    Slog.m72d("LightsService", "[SvcLED] pulse : Not Support");
                } else if (this.mColor == 0 && !this.mFlashing) {
                    setLightLocked(i, 2, i2, 1000, 0);
                    this.mColor = 0;
                    LightsService.this.f1711mH.postDelayed(new Runnable() { // from class: com.android.server.lights.LightsService$LightImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LightsService.LightImpl.this.stopFlashing();
                        }
                    }, i2);
                }
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void turnOff() {
            Slog.m72d("LightsService", "[api] [SvcLED] turnOff : lightType: " + LightsService.this.translateLightType(this.mHwLight.type) + LightsService.callerInfoToString());
            synchronized (this) {
                if (this.mIsIDUsingPatternLED) {
                    synchronized (LightsService.this.mSvcLEDThread) {
                        LightsService.this.acquireWakeLockForLED();
                        LightsService.this.clearSvcLedStateLocked(this.mHwLight.type);
                        if (LightsService.mSvcLedState != 0) {
                            LightsService.this.enableSvcLEDLightSensorLocked(true);
                            LightsService.this.mSvcLEDHandler.removeMessages(1);
                            LightsService.this.mSvcLEDHandler.sendMessageDelayed(LightsService.this.mSvcLEDHandler.obtainMessage(1), LightsService.this.mDelayForcedSvcLEDTask);
                        } else {
                            LightsService.this.mSvcLEDHandler.removeMessages(1);
                            LightsService.this.mSvcLEDHandler.sendMessage(LightsService.this.mSvcLEDHandler.obtainMessage(1));
                        }
                    }
                } else {
                    setLightLocked(0, 0, 0, 0, 0);
                }
            }
        }

        public final void stopFlashing() {
            synchronized (this) {
                Slog.m72d("LightsService", "[api] [SvcLED] stopFlashing : lightType: " + LightsService.this.translateLightType(this.mHwLight.type) + LightsService.callerInfoToString());
                if (this.mIsIDUsingPatternLED) {
                    synchronized (LightsService.this.mSvcLEDThread) {
                        LightsService.this.acquireWakeLockForLED();
                        LightsService.this.clearSvcLedStateLocked(this.mHwLight.type);
                        LightsService.this.mSvcLEDHandler.removeMessages(1);
                        LightsService.this.mSvcLEDHandler.sendMessage(LightsService.this.mSvcLEDHandler.obtainMessage(1));
                    }
                } else {
                    setLightLocked(this.mColor, 0, 0, 0, 0);
                }
            }
        }

        public final void setLightLocked(int i, int i2, int i3, int i4, int i5) {
            setLightLocked(i, -1, i2, i3, i4, i5);
        }

        public final void setLightLocked(int i, int i2, int i3, int i4, int i5, int i6) {
            int i7;
            if (shouldBeInLowPersistenceMode()) {
                i7 = 2;
            } else {
                if (i6 == 2) {
                    i6 = this.mLastBrightnessMode;
                }
                i7 = i6;
            }
            if (this.mInitialized && i == this.mColor && i3 == this.mMode && i4 == this.mOnMS && i5 == this.mOffMS && this.mBrightnessMode == i7 && i2 == this.mExtendedBrightness && !LightsService.this.mIsLEDChanged) {
                return;
            }
            LightsService.this.mIsLEDChanged = false;
            this.mInitialized = true;
            this.mLastColor = this.mColor;
            this.mColor = i;
            this.mExtendedBrightness = i2;
            this.mMode = i3;
            this.mOnMS = i4;
            this.mOffMS = i5;
            this.mBrightnessMode = i7;
            if (this.mHwLight.f1801id != 0) {
                Slog.m72d("LightsService", "[SvcLED] [setLightLocked] lightType:" + LightsService.this.translateLightType(this.mHwLight.type) + ", color:" + i + ", mode: " + i3 + ", onMS: " + i4 + ", offMS: " + i5);
            }
            setLightUnchecked(i, i2, i3, i4, i5, i7);
        }

        public final void setLightUnchecked(int i, int i2, int i3, int i4, int i5, int i6) {
            Trace.traceBegin(131072L, "setLightState(" + this.mHwLight.f1801id + ", 0x" + Integer.toHexString(i) + ")");
            try {
                try {
                    if (LightsService.this.mVintfSehLights != null) {
                        HwLightState hwLightState = new HwLightState();
                        hwLightState.color = i;
                        hwLightState.flashMode = (byte) i3;
                        hwLightState.flashOnMs = i4;
                        hwLightState.flashOffMs = i5;
                        hwLightState.brightnessMode = (byte) i6;
                        ((ISehLights) LightsService.this.mVintfSehLights.get()).setLightState(this.mHwLight.f1801id, hwLightState, i2);
                    } else {
                        LightsService.setLight_native(this.mHwLight.f1801id, i, i3, i4, i5, i6);
                    }
                } catch (RemoteException | UnsupportedOperationException e) {
                    Slog.m75e("LightsService", "Failed issuing setLightState", e);
                }
            } finally {
                Trace.traceEnd(131072L);
            }
        }

        public final boolean shouldBeInLowPersistenceMode() {
            return this.mVrModeEnabled && this.mUseLowPersistenceForVR;
        }

        public final boolean isSystemLight() {
            int i = this.mHwLight.type;
            return i >= 0 && i < 10;
        }

        public final int getColor() {
            return this.mColor;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LightsService(Context context) {
        this(context, new VintfHalCache(), Looper.myLooper(), new VintfExtHalCache());
    }

    public LightsService(Context context, Supplier supplier, Looper looper, Supplier supplier2) {
        super(context);
        this.mInitCompleteForSvcLED = false;
        this.mDelayForcedSvcLEDTask = 700;
        this.mUseLEDAutoBrightness = false;
        this.mUsePatternLED = true;
        this.mUpdateSvcLEDDelay = 600000;
        this.mCoverManager = null;
        this.mCoverOpened = true;
        this.mCoverType = 2;
        this.mIsLEDChanged = false;
        String[] strArr = {"/sys/power/wake_lock", "/sys/power/wake_unlock"};
        this.mNewWakeLockPaths = strArr;
        this.mWakeLockPath = strArr[0];
        this.mWakeUnlockPath = strArr[1];
        this.mInitializedWakeLockPath = false;
        this.mLightsByType = new LightImpl[10];
        this.mLightsById = new SparseArray();
        this.mService = new LightsManager() { // from class: com.android.server.lights.LightsService.2
            @Override // com.android.server.lights.LightsManager
            public LogicalLight getLight(int i) {
                if (LightsService.this.mLightsByType == null || i < 0 || i >= LightsService.this.mLightsByType.length) {
                    return null;
                }
                return LightsService.this.mLightsByType[i];
            }
        };
        this.mCoverStateListener = new CoverManager.StateListener() { // from class: com.android.server.lights.LightsService.3
            public void onCoverStateChanged(CoverState coverState) {
                LightsManager lightsManager;
                LightsService.this.mCoverType = coverState.getType();
                if (coverState.getSwitchState()) {
                    Slog.m72d("LightsService", " onCoverStateChanged : SWITCH_STATE_COVER_OPEN covertype: " + LightsService.this.mCoverType);
                    LightsService.this.mCoverOpened = true;
                    return;
                }
                Slog.m72d("LightsService", " onCoverStateChanged : SWITCH_STATE_COVER_CLOSE covertype: " + LightsService.this.mCoverType);
                LightsService.this.mCoverOpened = false;
                if (LightsService.this.mCoverType == 8 || (lightsManager = (LightsManager) LocalServices.getService(LightsManager.class)) == null) {
                    return;
                }
                LightsService.this.mCoverBatteryLight = lightsManager.getLight(3);
                LightsService.this.mCoverNotiLight = lightsManager.getLight(4);
                if (LightsService.this.mCoverBatteryLight != null) {
                    LightsService.this.mCoverBatteryLight.turnOff();
                }
                if (LightsService.this.mCoverNotiLight != null) {
                    LightsService.this.mCoverNotiLight.turnOff();
                }
            }
        };
        this.isLightSensorEnabled = false;
        this.mLightListener = new SensorEventListener() { // from class: com.android.server.lights.LightsService.4
            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                int i = (int) sensorEvent.values[0];
                Slog.m72d("LightsService", "[SvcLED] onSensorChanged : light value: " + i);
                synchronized (LightsService.this.mSvcLEDThread) {
                    LightsService.this.mSvcLEDHandler.removeMessages(1);
                    LightsService.this.enableSvcLEDLightSensorLocked(false);
                    LightsService.this.setSvcLedLightLocked(i);
                }
                LightsService.this.mAlarmManagerForSvcLED.cancel(LightsService.this.mUpdateSvcLEDPendingIntent);
                if (LightsService.mSvcLedState != 0) {
                    LightsService.this.mAlarmManagerForSvcLED.set(3, SystemClock.elapsedRealtime() + LightsService.this.mUpdateSvcLEDDelay, LightsService.this.mUpdateSvcLEDPendingIntent);
                }
            }
        };
        this.LED_LOW_POWER_PATH = "/sys/class/sec/led/led_lowpower";
        this.mLedLowPower = -1;
        this.mLastSvcLedId = 3;
        this.mWakeLockAcquired = false;
        this.f1711mH = new Handler(looper);
        this.mVintfLights = supplier.get() == null ? null : supplier;
        this.mVintfSehLights = supplier2.get() == null ? null : supplier2;
        populateAvailableLights(context);
        this.mManagerService = new LightsManagerBinderService();
        this.mContext = context;
        this.mInitCompleteForSvcLED = false;
        HandlerThread handlerThread = new HandlerThread("mSvcLEDThread") { // from class: com.android.server.lights.LightsService.1
            @Override // android.os.HandlerThread
            public void onLooperPrepared() {
                LightsService lightsService = LightsService.this;
                LightsService lightsService2 = LightsService.this;
                lightsService.mSvcLEDHandler = lightsService2.new SvcLEDHandler(lightsService2.mSvcLEDThread.getLooper());
                synchronized (LightsService.this.mSvcLEDThread) {
                    LightsService.this.mInitCompleteForSvcLED = true;
                    LightsService.this.mSvcLEDThread.notifyAll();
                    Resources resources = LightsService.this.mContext.getResources();
                    LightsService.this.mUsePatternLED = resources.getBoolean(R.bool.config_supportsSeamlessRefreshRateSwitching);
                    LightsService.this.mUseSoftwareAutoBrightness = resources.getBoolean(R.bool.config_autoPowerModeUseMotionSensor);
                    if (LightsService.this.mUseSoftwareAutoBrightness && PowerManagerUtil.fileExist("/sys/class/sec/led/led_lowpower")) {
                        LightsService.this.mUseLEDAutoBrightness = true;
                    }
                    LightsService lightsService3 = LightsService.this;
                    lightsService3.mDelayForcedSvcLEDTask = lightsService3.mUseLEDAutoBrightness ? 700 : 0;
                    Slog.m72d("LightsService", "[SvcLED]   mUseSoftwareAutoBrightness: " + LightsService.this.mUseSoftwareAutoBrightness + ",  mUseLEDAutoBrightness: " + LightsService.this.mUseLEDAutoBrightness + ",  mDelayForcedSvcLEDTask: " + LightsService.this.mDelayForcedSvcLEDTask + ",  mUsePatternLED: " + LightsService.this.mUsePatternLED);
                }
            }
        };
        this.mSvcLEDThread = handlerThread;
        handlerThread.start();
        synchronized (handlerThread) {
            while (!this.mInitCompleteForSvcLED) {
                try {
                    this.mSvcLEDThread.wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public final void populateAvailableLights(Context context) {
        if (this.mVintfSehLights != null) {
            populateAvailableLightsFromAidl(context);
        } else {
            populateAvailableLightsFromHidl(context);
        }
        for (int size = this.mLightsById.size() - 1; size >= 0; size--) {
            int i = ((LightImpl) this.mLightsById.valueAt(size)).mHwLight.type;
            if (i >= 0) {
                LightImpl[] lightImplArr = this.mLightsByType;
                if (i < lightImplArr.length) {
                    lightImplArr[i] = (LightImpl) this.mLightsById.valueAt(size);
                }
            }
        }
    }

    public final void populateAvailableLightsFromAidl(Context context) {
        try {
            for (SehHwLight sehHwLight : ((ISehLights) this.mVintfSehLights.get()).getLights()) {
                this.mLightsById.put(sehHwLight.f1801id, new LightImpl(context, sehHwLight));
            }
        } catch (RemoteException e) {
            Slog.m75e("LightsService", "Unable to get lights from HAL", e);
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishLocalService(LightsManager.class, this.mService);
        publishBinderService("lights", this.mManagerService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            systemReady();
        }
    }

    public class VintfHalCache implements Supplier, IBinder.DeathRecipient {
        public ILights mInstance;

        public VintfHalCache() {
            this.mInstance = null;
        }

        @Override // java.util.function.Supplier
        public synchronized ILights get() {
            if (this.mInstance == null) {
                IBinder allowBlocking = Binder.allowBlocking(ServiceManager.waitForDeclaredService(ILights.DESCRIPTOR + "/default"));
                if (allowBlocking != null) {
                    this.mInstance = ILights.Stub.asInterface(allowBlocking);
                    try {
                        allowBlocking.linkToDeath(this, 0);
                    } catch (RemoteException unused) {
                        Slog.m74e("LightsService", "Unable to register DeathRecipient for " + this.mInstance);
                    }
                }
            }
            return this.mInstance;
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            this.mInstance = null;
        }
    }

    public class VintfExtHalCache implements Supplier, IBinder.DeathRecipient {
        public ISehLights mInstance;

        public VintfExtHalCache() {
            this.mInstance = null;
        }

        @Override // java.util.function.Supplier
        public synchronized ISehLights get() {
            IBinder allowBlocking;
            if (this.mInstance == null && (allowBlocking = Binder.allowBlocking(ServiceManager.waitForDeclaredService("android.hardware.light.ILights/default"))) != null) {
                try {
                    this.mInstance = ISehLights.Stub.asInterface(allowBlocking.getExtension());
                    allowBlocking.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    Slog.m74e("LightsService", "Unable to register DeathRecipient for " + this.mInstance);
                }
            }
            return this.mInstance;
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            this.mInstance = null;
        }
    }

    public void systemReady() {
        Slog.m72d("LightsService", "[SvcLED] systemReady");
        this.mAlarmManagerForSvcLED = (AlarmManager) this.mContext.getSystemService("alarm");
        this.mUpdateSvcLEDPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.android.server.LightsService.action.UPDATE_SVC_LED", (Uri) null), 67108864);
        SystemSensorManager systemSensorManager = new SystemSensorManager(this.mContext, this.mSvcLEDThread.getLooper());
        this.mSensorManager = systemSensorManager;
        if (this.mUseLEDAutoBrightness) {
            this.mLightSensor = systemSensorManager.getDefaultSensor(5);
        }
        this.mCoverManager = new CoverManager(this.mContext);
        initCoverState();
        CoverManager coverManager = this.mCoverManager;
        if (coverManager != null) {
            coverManager.registerListener(this.mCoverStateListener);
        } else {
            Slog.m72d("LightsService", "initLightsService() : mCoverManager is null!!!!");
        }
        initializeWakeLockPath();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.LightsService.action.UPDATE_SVC_LED");
        this.mContext.registerReceiver(new SvcLEDReceiver(), intentFilter);
    }

    public final void initCoverState() {
        Slog.m72d("LightsService", "initCoverState()start");
        CoverManager coverManager = this.mCoverManager;
        if (coverManager != null) {
            CoverState coverState = coverManager.getCoverState();
            if (coverState != null) {
                this.mCoverType = coverState.getType();
                this.mCoverOpened = coverState.getSwitchState();
                Slog.m72d("LightsService", "initCoverState() coverstate : " + this.mCoverType);
                return;
            }
            Slog.m72d("LightsService", "initCoverState() : state is null");
            return;
        }
        Slog.m72d("LightsService", "initCoverState() : mCoverManager is null!!!!");
    }

    public final void enableSvcLEDLightSensorLocked(boolean z) {
        SensorManager sensorManager;
        if (!this.mUseLEDAutoBrightness || (sensorManager = this.mSensorManager) == null) {
            return;
        }
        if (z && mSvcLedState != 0) {
            if (this.isLightSensorEnabled) {
                return;
            }
            sensorManager.registerListener(this.mLightListener, this.mLightSensor, 3);
            this.isLightSensorEnabled = true;
            return;
        }
        if (this.isLightSensorEnabled) {
            sensorManager.unregisterListener(this.mLightListener);
            this.isLightSensorEnabled = false;
        }
    }

    public final class SvcLEDReceiver extends BroadcastReceiver {
        public SvcLEDReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.server.LightsService.action.UPDATE_SVC_LED")) {
                synchronized (LightsService.this.mSvcLEDThread) {
                    LightsService.this.acquireWakeLockForLED();
                    Slog.m72d("LightsService", "[api] onReceive : SvcLEDReceiver re-enables LightSenor and sends MSG_FORCEDSVCLEDTASK");
                    LightsService.this.enableSvcLEDLightSensorLocked(true);
                    LightsService.this.mSvcLEDHandler.removeMessages(1);
                    LightsService.this.mSvcLEDHandler.sendMessageDelayed(LightsService.this.mSvcLEDHandler.obtainMessage(1), LightsService.this.mDelayForcedSvcLEDTask);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setSvcLedLightLocked(int i) {
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        int i6 = i < 20 ? 1 : 0;
        if (i6 == 1 && !this.mCoverOpened && this.mCoverType == 8) {
            i6 = 0;
        }
        if (this.mUseLEDAutoBrightness && i6 != this.mLedLowPower) {
            this.mLedLowPower = i6;
            PowerManagerUtil.fileWriteInt("/sys/class/sec/led/led_lowpower", i6);
            this.mIsLEDChanged = true;
        }
        int i7 = 0;
        while (i7 < 6 && ((mSvcLedState >> i7) & 1) == 0) {
            i7++;
        }
        if (mSvcLedState == 0) {
            i2 = this.mLastSvcLedId;
        } else {
            if (i7 != 0) {
                if (i7 == 1) {
                    i3 = 12;
                } else if (i7 == 2) {
                    i3 = mSvcLedMode;
                } else if (i7 == 3) {
                    i5 = 10;
                } else if (i7 == 4) {
                    i5 = 13;
                } else if (i7 != 5) {
                    i2 = 5;
                } else {
                    i5 = 14;
                }
                i4 = i3;
                i2 = 4;
                this.mLightsByType[i2].setLightLocked(mSvcLedColor, i4, mSvcLedOnMS, mSvcLedOffMS, 0);
                if (this.mLastSvcLedId == i2) {
                    Slog.m72d("LightsService", "[SvcLED] setSvcLedLightLocked : priority changed! SvcLED(" + translateID(this.mLastSvcLedId) + ") OUT; (" + translateID(i2) + ") IN");
                } else {
                    Slog.m72d("LightsService", "[SvcLED] setSvcLedLightLocked : Current SvcLED(" + translateID(i2) + ") maintains its priority right");
                }
                this.mLastSvcLedId = i2;
                releaseWakeLockForLED();
            }
            i5 = 11;
            i2 = 3;
        }
        i4 = i5;
        this.mLightsByType[i2].setLightLocked(mSvcLedColor, i4, mSvcLedOnMS, mSvcLedOffMS, 0);
        if (this.mLastSvcLedId == i2) {
        }
        this.mLastSvcLedId = i2;
        releaseWakeLockForLED();
    }

    public final void handleForcedSvcLEDTask() {
        Slog.m72d("LightsService", "[SvcLED] handleForcedSvcLEDTask()");
        enableSvcLEDLightSensorLocked(false);
        setSvcLedLightLocked(19);
    }

    public final class SvcLEDHandler extends Handler {
        public SvcLEDHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            LightsService.this.handleForcedSvcLEDTask();
        }
    }

    public final void initializeWakeLockPath() {
        if (this.mInitializedWakeLockPath) {
            return;
        }
        if (PowerManagerUtil.fileExist(this.mNewWakeLockPaths[0])) {
            String[] strArr = this.mNewWakeLockPaths;
            this.mWakeLockPath = strArr[0];
            if (PowerManagerUtil.fileExist(strArr[1])) {
                this.mWakeUnlockPath = this.mNewWakeLockPaths[1];
                this.mInitializedWakeLockPath = true;
                return;
            } else {
                Slog.m72d("LightsService", "wake_unlock path does not exists ");
                return;
            }
        }
        Slog.m72d("LightsService", "wake_lock path does not exists");
    }

    public final void acquireWakeLockForLED() {
        if (!this.mInitializedWakeLockPath) {
            Slog.m76i("LightsService", "acquireWakeLockForLED : WakeLock path is not initialized");
        } else {
            if (this.mWakeLockAcquired) {
                return;
            }
            PowerManagerUtil.fileWriteString(this.mWakeLockPath, "LightsService");
            this.mWakeLockAcquired = true;
        }
    }

    public final void releaseWakeLockForLED() {
        if (!this.mInitializedWakeLockPath) {
            Slog.m76i("LightsService", "releaseWakeLockForLED: WakeLock path is not initialized");
        } else {
            if (!this.mWakeLockAcquired || this.mSvcLEDHandler.hasMessages(1)) {
                return;
            }
            PowerManagerUtil.fileWriteString(this.mWakeUnlockPath, "LightsService");
            this.mWakeLockAcquired = false;
        }
    }

    public final void clearSvcLedStateLocked(int i) {
        setSvcLedStateLocked(i, 0, -1, 0, 0, false);
    }

    public final void setSvcLedStateLocked(int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6 = mSvcLedState;
        mPrevSvcLedState = i6;
        if (i == 3) {
            int i7 = i6 & (-58);
            mSvcLedState = i7;
            if (z) {
                mSvcLedState = i7 | convertToBitMask(i3);
            }
        } else if (i != 4) {
            Slog.m72d("LightsService", "[SvcLED] setSvcLedStateLocked : Not Support, " + translateLightType(i));
        } else if (!z) {
            mSvcLedState = i6 & (-7);
        } else if (i3 == 12) {
            mSvcLedState = i6 | 2;
        } else {
            writeSvcLedData(4, i2, i3, i4, i5);
        }
        if (mPrevSvcLedState != mSvcLedState) {
            this.mIsLEDChanged = true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[SvcLED] setSvcLedStateLocked : SvcLEDState: 0x");
        sb.append(Integer.toHexString(mPrevSvcLedState));
        sb.append(" -> 0x");
        sb.append(Integer.toHexString(mSvcLedState));
        sb.append(" | SvcLED(");
        sb.append(translateLightType(i));
        sb.append(") set ");
        sb.append(z ? "On" : "Off");
        Slog.m72d("LightsService", sb.toString());
    }

    public void writeSvcLedData(int i, int i2, int i3, int i4, int i5) {
        mSvcLedState |= i;
        mSvcLedColor = i2;
        mSvcLedMode = i3;
        mSvcLedOnMS = i4;
        mSvcLedOffMS = i5;
    }

    public static String callerInfoToString() {
        return " (uid: " + Binder.getCallingUid() + " pid: " + Binder.getCallingPid() + ") ";
    }
}
