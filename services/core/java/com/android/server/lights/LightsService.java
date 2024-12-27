package com.android.server.lights;

import android.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SystemSensorManager;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.light.HwLight;
import android.hardware.light.HwLightState;
import android.hardware.light.ILights;
import android.hardware.lights.ILightsManager;
import android.hardware.lights.Light;
import android.hardware.lights.LightState;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.util.SparseArray;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.lights.LightsService;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import vendor.samsung.hardware.light.ISehLights;
import vendor.samsung.hardware.light.SehHwLight;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class LightsService extends SystemService {
    public static int mPrevSvcLedState;
    public static int mSvcLedColor;
    public static int mSvcLedMode;
    public static int mSvcLedOffMS;
    public static int mSvcLedOnMS;
    public static int mSvcLedState;
    public boolean isLightSensorEnabled;
    public AlarmManager mAlarmManagerForSvcLED;
    public final Context mContext;
    public LogicalLight mCoverBatteryLight;
    public CoverManager mCoverManager;
    public LogicalLight mCoverNotiLight;
    public boolean mCoverOpened;
    public final AnonymousClass3 mCoverStateListener;
    public int mCoverType;
    public int mDelayForcedSvcLEDTask;
    public final Handler mH;
    public boolean mInitCompleteForSvcLED;
    public boolean mInitializedWakeLockPath;
    public boolean mIsLEDChanged;
    public int mLastSvcLedId;
    public int mLedLowPower;
    public final AnonymousClass4 mLightListener;
    public Sensor mLightSensor;
    public final SparseArray mLightsById;
    public final LightImpl[] mLightsByType;
    final LightsManagerBinderService mManagerService;
    public final String[] mNewWakeLockPaths;
    public SensorManager mSensorManager;
    public final AnonymousClass2 mService;
    public SvcLEDHandler mSvcLEDHandler;
    public final AnonymousClass1 mSvcLEDThread;
    public final int mUpdateSvcLEDDelay;
    public PendingIntent mUpdateSvcLEDPendingIntent;
    public boolean mUseLEDAutoBrightness;
    public boolean mUsePatternLED;
    public boolean mUseSoftwareAutoBrightness;
    public final Supplier mVintfLights;
    public final Supplier mVintfSehLights;
    public boolean mWakeLockAcquired;
    public String mWakeLockPath;
    public String mWakeUnlockPath;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LightImpl extends LogicalLight {
        public int mBrightnessMode;
        public int mColor;
        public final HwLight mHwLight;
        public boolean mInitialized;
        public final boolean mIsIDUsingPatternLED;
        public int mMode;
        public int mOffMS;
        public int mOnMS;

        /* renamed from: -$$Nest$misSystemLight, reason: not valid java name */
        public static boolean m637$$Nest$misSystemLight(LightImpl lightImpl) {
            byte b = lightImpl.mHwLight.type;
            return b >= 0 && b < 10;
        }

        public LightImpl(HwLight hwLight) {
            byte b;
            this.mHwLight = hwLight;
            this.mIsIDUsingPatternLED = LightsService.this.mUsePatternLED && ((b = hwLight.type) == 3 || b == 4);
        }

        @Override // com.android.server.lights.LogicalLight
        public final void setFlashing(int i, int i2, int i3, int i4) {
            String str;
            String m;
            StringBuilder sb = new StringBuilder("[api] [SvcLED] setFlashing : type: ");
            sb.append((int) this.mHwLight.type);
            sb.append("(");
            LightsService lightsService = LightsService.this;
            byte b = this.mHwLight.type;
            lightsService.getClass();
            sb.append(LightsService.translateLightType(b));
            sb.append("), color: ");
            sb.append(Integer.toHexString(i));
            sb.append(", mode: ");
            if (i2 == -1) {
                m = "Off";
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(i2);
                sb2.append("(");
                LightsService.this.getClass();
                if (i2 == 0) {
                    str = "LIGHT_FLASH_NONE";
                } else if (i2 == 1) {
                    str = "LIGHT_FLASH_TIMED";
                } else if (i2 != 2) {
                    switch (i2) {
                        case 10:
                            str = "LIGHT_SEC_FLASH_CHARGING";
                            break;
                        case 11:
                            str = "LIGHT_SEC_FLASH_CHARGING_ERROR";
                            break;
                        case 12:
                            str = "LIGHT_SEC_FLASH_MISSED_NOTIFICATION";
                            break;
                        case 13:
                            str = "LIGHT_SEC_FLASH_LOW_BATTERY";
                            break;
                        case 14:
                            str = "LIGHT_SEC_FLASH_FULLY_CHARGED";
                            break;
                        default:
                            str = "translateMode error";
                            break;
                    }
                } else {
                    str = "LIGHT_FLASH_HARDWARE";
                }
                m = AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb2, str, ")");
            }
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i3, m, ", onMS: ", ", offMS: ", sb);
            sb.append(i4);
            sb.append(", ");
            sb.append(LightsService.m636$$Nest$smcallerInfoToString());
            Slog.d("LightsService", sb.toString());
            LightsService lightsService2 = LightsService.this;
            if (lightsService2.mCoverType != 8 && !lightsService2.mCoverOpened) {
                Slog.d("LightsService", "[api] [SvcLED] S Cover is closed so don't need LED On");
                return;
            }
            synchronized (this) {
                try {
                    if (this.mIsIDUsingPatternLED) {
                        synchronized (LightsService.this.mSvcLEDThread) {
                            LightsService.m635$$Nest$macquireWakeLockForLED(LightsService.this);
                            LightsService.this.setSvcLedStateLocked(this.mHwLight.type, i, i2, i3, i4, true);
                            LightsService.this.enableSvcLEDLightSensorLocked(true);
                            LightsService.this.mSvcLEDHandler.removeMessages(1);
                            LightsService.this.mSvcLEDHandler.sendMessageDelayed(LightsService.this.mSvcLEDHandler.obtainMessage(1), r13.mDelayForcedSvcLEDTask);
                        }
                    } else {
                        Slog.d("LightsService", "[SvcLED] not mIsIDUsingPatternLED");
                        setLightLocked(i, i2, i3, i4);
                    }
                } finally {
                }
            }
        }

        public final void setLightLocked(int i, int i2, int i3, int i4) {
            boolean z = this.mInitialized;
            LightsService lightsService = LightsService.this;
            if (z && i == this.mColor && i2 == this.mMode && i3 == this.mOnMS && i4 == this.mOffMS && this.mBrightnessMode == 0 && !lightsService.mIsLEDChanged) {
                return;
            }
            lightsService.mIsLEDChanged = false;
            this.mInitialized = true;
            this.mColor = i;
            this.mMode = i2;
            this.mOnMS = i3;
            this.mOffMS = i4;
            this.mBrightnessMode = 0;
            HwLight hwLight = this.mHwLight;
            byte b = hwLight.type;
            if (b != 0 && b != 9) {
                StringBuilder sb = new StringBuilder("[SvcLED] [setLightLocked] lightType:");
                sb.append(LightsService.translateLightType(hwLight.type));
                sb.append(", color:");
                sb.append(i);
                sb.append(", mode: ");
                ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, ", onMS: ", ", offMS: ", sb);
                BatteryService$$ExternalSyntheticOutline0.m(sb, i4, "LightsService");
            }
            Trace.traceBegin(131072L, "setLightState(" + hwLight.id + ", 0x" + Integer.toHexString(i) + ")");
            try {
                try {
                } catch (RemoteException | UnsupportedOperationException e) {
                    Slog.e("LightsService", "Failed issuing setLightState", e);
                }
                if (lightsService.mVintfLights == null && lightsService.mVintfSehLights == null) {
                    LightsService.setLight_native(hwLight.id, i, i2, i3, i4, 0);
                    Trace.traceEnd(131072L);
                }
                HwLightState hwLightState = new HwLightState();
                hwLightState.color = i;
                hwLightState.flashMode = (byte) i2;
                hwLightState.flashOnMs = i3;
                hwLightState.flashOffMs = i4;
                hwLightState.brightnessMode = (byte) 0;
                Supplier supplier = lightsService.mVintfLights;
                if (supplier != null) {
                    ((ILights.Stub.Proxy) ((ILights) supplier.get())).setLightState(hwLight.id, hwLightState);
                } else {
                    ((ISehLights.Stub.Proxy) ((ISehLights) lightsService.mVintfSehLights.get())).setLightState(hwLight.id, hwLightState);
                }
                Trace.traceEnd(131072L);
            } catch (Throwable th) {
                Trace.traceEnd(131072L);
                throw th;
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public final void turnOff() {
            StringBuilder sb = new StringBuilder("[api] [SvcLED] turnOff : lightType: ");
            LightsService lightsService = LightsService.this;
            byte b = this.mHwLight.type;
            lightsService.getClass();
            sb.append(LightsService.translateLightType(b));
            sb.append(LightsService.m636$$Nest$smcallerInfoToString());
            Slog.d("LightsService", sb.toString());
            synchronized (this) {
                try {
                    if (this.mIsIDUsingPatternLED) {
                        synchronized (LightsService.this.mSvcLEDThread) {
                            LightsService.m635$$Nest$macquireWakeLockForLED(LightsService.this);
                            LightsService.this.setSvcLedStateLocked(this.mHwLight.type, 0, -1, 0, 0, false);
                            if (LightsService.mSvcLedState != 0) {
                                LightsService.this.enableSvcLEDLightSensorLocked(true);
                                LightsService.this.mSvcLEDHandler.removeMessages(1);
                                LightsService.this.mSvcLEDHandler.sendMessageDelayed(LightsService.this.mSvcLEDHandler.obtainMessage(1), r2.mDelayForcedSvcLEDTask);
                            } else {
                                LightsService.this.mSvcLEDHandler.removeMessages(1);
                                LightsService.this.mSvcLEDHandler.sendMessage(LightsService.this.mSvcLEDHandler.obtainMessage(1));
                            }
                        }
                    } else {
                        setLightLocked(0, 0, 0, 0);
                    }
                } catch (Throwable th) {
                    throw th;
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LightsManagerBinderService extends ILightsManager.Stub {
        public final List mSessions;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Session implements Comparable {
            public final int mPriority;
            public final SparseArray mRequests = new SparseArray();
            public final IBinder mToken;

            public Session(int i, IBinder iBinder) {
                this.mToken = iBinder;
                this.mPriority = i;
            }

            @Override // java.lang.Comparable
            public final int compareTo(Object obj) {
                return Integer.compare(((Session) obj).mPriority, this.mPriority);
            }
        }

        public LightsManagerBinderService() {
            super(PermissionEnforcer.fromContext(LightsService.this.getContext()));
            this.mSessions = new ArrayList();
        }

        public final void closeSession(IBinder iBinder) {
            closeSession_enforcePermission();
            Preconditions.checkNotNull(iBinder);
            closeSessionInternal(iBinder);
        }

        public final void closeSessionInternal(IBinder iBinder) {
            synchronized (LightsService.this) {
                try {
                    Session sessionLocked = getSessionLocked(iBinder);
                    if (sessionLocked != null) {
                        ((ArrayList) this.mSessions).remove(sessionLocked);
                        invalidateLightStatesLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(LightsService.this.getContext(), "LightsService", printWriter)) {
                synchronized (LightsService.this) {
                    try {
                        LightsService lightsService = LightsService.this;
                        if (lightsService.mVintfLights != null) {
                            printWriter.println("Service: aidl (" + LightsService.this.mVintfLights.get() + ")");
                        } else if (lightsService.mVintfSehLights != null) {
                            printWriter.println("Service: aidl(extension) (" + LightsService.this.mVintfSehLights.get() + ")");
                        } else {
                            printWriter.println("Service: hidl");
                        }
                        printWriter.println("Lights:");
                        for (int i = 0; i < LightsService.this.mLightsById.size(); i++) {
                            LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.valueAt(i);
                            printWriter.println(String.format("  Light id=%d ordinal=%d color=%08x", Integer.valueOf(lightImpl.mHwLight.id), Integer.valueOf(lightImpl.mHwLight.ordinal), Integer.valueOf(lightImpl.mColor)));
                        }
                        printWriter.println("Session clients:");
                        Iterator it = ((ArrayList) this.mSessions).iterator();
                        while (it.hasNext()) {
                            Session session = (Session) it.next();
                            printWriter.println("  Session token=" + session.mToken);
                            for (int i2 = 0; i2 < session.mRequests.size(); i2++) {
                                printWriter.println(String.format("    Request id=%d color=%08x", Integer.valueOf(session.mRequests.keyAt(i2)), Integer.valueOf(((LightState) session.mRequests.valueAt(i2)).getColor())));
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final LightState getLightState(int i) {
            LightState lightState;
            getLightState_enforcePermission();
            synchronized (LightsService.this) {
                try {
                    LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.get(i);
                    if (lightImpl == null || LightImpl.m637$$Nest$misSystemLight(lightImpl)) {
                        throw new IllegalArgumentException("Invalid light: " + i);
                    }
                    lightState = new LightState(lightImpl.mColor);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return lightState;
        }

        public final List getLights() {
            ArrayList arrayList;
            getLights_enforcePermission();
            synchronized (LightsService.this) {
                try {
                    arrayList = new ArrayList();
                    for (int i = 0; i < LightsService.this.mLightsById.size(); i++) {
                        if (!LightImpl.m637$$Nest$misSystemLight((LightImpl) LightsService.this.mLightsById.valueAt(i))) {
                            HwLight hwLight = ((LightImpl) LightsService.this.mLightsById.valueAt(i)).mHwLight;
                            arrayList.add(new Light(hwLight.id, hwLight.ordinal, hwLight.type));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        public final Session getSessionLocked(IBinder iBinder) {
            for (int i = 0; i < ((ArrayList) this.mSessions).size(); i++) {
                if (iBinder.equals(((Session) ((ArrayList) this.mSessions).get(i)).mToken)) {
                    return (Session) ((ArrayList) this.mSessions).get(i);
                }
            }
            return null;
        }

        public final void invalidateLightStatesLocked() {
            HashMap hashMap = new HashMap();
            int size = ((ArrayList) this.mSessions).size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                SparseArray sparseArray = ((Session) ((ArrayList) this.mSessions).get(size)).mRequests;
                for (int i = 0; i < sparseArray.size(); i++) {
                    hashMap.put(Integer.valueOf(sparseArray.keyAt(i)), (LightState) sparseArray.valueAt(i));
                }
            }
            for (int i2 = 0; i2 < LightsService.this.mLightsById.size(); i2++) {
                LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.valueAt(i2);
                if (!LightImpl.m637$$Nest$misSystemLight(lightImpl)) {
                    LightState lightState = (LightState) hashMap.get(Integer.valueOf(lightImpl.mHwLight.id));
                    if (lightState != null) {
                        int color = lightState.getColor();
                        Slog.d("LightsService", "[api] [SvcLED] setColor : color: " + Integer.toHexString(color));
                        synchronized (lightImpl) {
                            lightImpl.setLightLocked(color, 0, 0, 0);
                        }
                    } else {
                        lightImpl.turnOff();
                    }
                }
            }
        }

        public final void openSession(final IBinder iBinder, int i) {
            openSession_enforcePermission();
            Preconditions.checkNotNull(iBinder);
            synchronized (LightsService.this) {
                try {
                    Preconditions.checkState(getSessionLocked(iBinder) == null, "already registered");
                    try {
                        iBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.lights.LightsService$LightsManagerBinderService$$ExternalSyntheticLambda0
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                LightsService.LightsManagerBinderService.this.closeSessionInternal(iBinder);
                            }
                        }, 0);
                        ((ArrayList) this.mSessions).add(new Session(i, iBinder));
                        Collections.sort(this.mSessions);
                    } catch (RemoteException e) {
                        Slog.e("LightsService", "Couldn't open session, client already died", e);
                        throw new IllegalArgumentException("Client is already dead.");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setLightStates(IBinder iBinder, int[] iArr, LightState[] lightStateArr) {
            setLightStates_enforcePermission();
            Preconditions.checkState(iArr.length == lightStateArr.length);
            synchronized (LightsService.this) {
                try {
                    Session sessionLocked = getSessionLocked((IBinder) Preconditions.checkNotNull(iBinder));
                    Preconditions.checkState(sessionLocked != null, "not registered");
                    for (int i : iArr) {
                        LightImpl lightImpl = (LightImpl) LightsService.this.mLightsById.get(i);
                        Preconditions.checkState((lightImpl == null || LightImpl.m637$$Nest$misSystemLight(lightImpl)) ? false : true, "Invalid lightId " + i);
                    }
                    for (int i2 = 0; i2 < iArr.length; i2++) {
                        int i3 = iArr[i2];
                        LightState lightState = lightStateArr[i2];
                        if (lightState != null) {
                            sessionLocked.mRequests.put(i3, lightState);
                        } else {
                            sessionLocked.mRequests.remove(i3);
                        }
                    }
                    invalidateLightStatesLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SvcLEDHandler extends Handler {
        public SvcLEDHandler(Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            LightsService lightsService = LightsService.this;
            lightsService.getClass();
            Slog.d("LightsService", "[SvcLED] handleForcedSvcLEDTask()");
            lightsService.enableSvcLEDLightSensorLocked(false);
            lightsService.setSvcLedLightLocked(19);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SvcLEDReceiver extends BroadcastReceiver {
        public SvcLEDReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.server.LightsService.action.UPDATE_SVC_LED")) {
                synchronized (LightsService.this.mSvcLEDThread) {
                    LightsService.m635$$Nest$macquireWakeLockForLED(LightsService.this);
                    Slog.d("LightsService", "[api] onReceive : SvcLEDReceiver re-enables LightSenor and sends MSG_FORCEDSVCLEDTASK");
                    LightsService.this.enableSvcLEDLightSensorLocked(true);
                    LightsService.this.mSvcLEDHandler.removeMessages(1);
                    LightsService.this.mSvcLEDHandler.sendMessageDelayed(LightsService.this.mSvcLEDHandler.obtainMessage(1), r3.mDelayForcedSvcLEDTask);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VintfHalCache implements Supplier, IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId;
        public Object mInstance;

        public /* synthetic */ VintfHalCache(int i) {
            this.$r8$classId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final synchronized void binderDied() {
            int i = this.$r8$classId;
            synchronized (this) {
                switch (i) {
                    case 0:
                        this.mInstance = null;
                        return;
                    default:
                        this.mInstance = null;
                        return;
                }
            }
        }

        @Override // java.util.function.Supplier
        public final Object get() {
            ILights iLights;
            IInterface iInterface;
            ISehLights iSehLights;
            IBinder allowBlocking;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this) {
                        if (((ILights) this.mInstance) == null) {
                            StringBuilder sb = new StringBuilder();
                            String str = ILights.DESCRIPTOR;
                            sb.append(str);
                            sb.append("/default");
                            IBinder allowBlocking2 = Binder.allowBlocking(ServiceManager.waitForDeclaredService(sb.toString()));
                            if (allowBlocking2 != null) {
                                int i = ILights.Stub.$r8$clinit;
                                IInterface queryLocalInterface = allowBlocking2.queryLocalInterface(str);
                                if (queryLocalInterface == null || !(queryLocalInterface instanceof ILights)) {
                                    ILights.Stub.Proxy proxy = new ILights.Stub.Proxy();
                                    proxy.mRemote = allowBlocking2;
                                    iInterface = proxy;
                                } else {
                                    iInterface = (ILights) queryLocalInterface;
                                }
                                this.mInstance = iInterface;
                                try {
                                    allowBlocking2.linkToDeath(this, 0);
                                } catch (RemoteException unused) {
                                    Slog.e("LightsService", "Unable to register DeathRecipient for " + ((ILights) this.mInstance));
                                }
                            }
                        }
                        iLights = (ILights) this.mInstance;
                    }
                    return iLights;
                default:
                    synchronized (this) {
                        if (((ISehLights) this.mInstance) == null && (allowBlocking = Binder.allowBlocking(ServiceManager.waitForDeclaredService("android.hardware.light.ILights/default"))) != null) {
                            try {
                                this.mInstance = ISehLights.Stub.asInterface(allowBlocking.getExtension());
                                allowBlocking.linkToDeath(this, 0);
                            } catch (RemoteException unused2) {
                                Slog.e("LightsService", "Unable to register DeathRecipient for " + ((ISehLights) this.mInstance));
                            }
                        }
                        iSehLights = (ISehLights) this.mInstance;
                    }
                    return iSehLights;
            }
        }
    }

    /* renamed from: -$$Nest$macquireWakeLockForLED, reason: not valid java name */
    public static void m635$$Nest$macquireWakeLockForLED(LightsService lightsService) {
        if (!lightsService.mInitializedWakeLockPath) {
            Slog.i("LightsService", "acquireWakeLockForLED : WakeLock path is not initialized");
        } else {
            if (lightsService.mWakeLockAcquired) {
                return;
            }
            PowerManagerUtil.fileWriteString(lightsService.mWakeLockPath, "LightsService");
            lightsService.mWakeLockAcquired = true;
        }
    }

    /* renamed from: -$$Nest$smcallerInfoToString, reason: not valid java name */
    public static String m636$$Nest$smcallerInfoToString() {
        return DualAppManagerService$$ExternalSyntheticOutline0.m(Binder.getCallingUid(), Binder.getCallingPid(), " (uid: ", " pid: ", ") ");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LightsService(android.content.Context r6) {
        /*
            r5 = this;
            com.android.server.lights.LightsService$VintfHalCache r0 = new com.android.server.lights.LightsService$VintfHalCache
            r1 = 0
            r0.<init>(r1)
            r1 = 0
            r0.mInstance = r1
            android.os.Looper r2 = android.os.Looper.myLooper()
            com.android.server.lights.LightsService$VintfHalCache r3 = new com.android.server.lights.LightsService$VintfHalCache
            r4 = 1
            r3.<init>(r4)
            r3.mInstance = r1
            r5.<init>(r6, r0, r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.lights.LightsService.<init>(android.content.Context):void");
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [android.os.HandlerThread, com.android.server.lights.LightsService$1] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.server.lights.LightsService$2] */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.server.lights.LightsService$3] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.server.lights.LightsService$4] */
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
        LightImpl[] lightImplArr = new LightImpl[10];
        this.mLightsByType = lightImplArr;
        this.mLightsById = new SparseArray();
        this.mService = new LightsManager() { // from class: com.android.server.lights.LightsService.2
            @Override // com.android.server.lights.LightsManager
            public final LightImpl getLight(int i) {
                LightImpl[] lightImplArr2 = LightsService.this.mLightsByType;
                if (lightImplArr2 == null || i < 0 || i >= lightImplArr2.length) {
                    return null;
                }
                return lightImplArr2[i];
            }
        };
        this.mCoverStateListener = new CoverManager.StateListener() { // from class: com.android.server.lights.LightsService.3
            public final void onCoverStateChanged(CoverState coverState) {
                LightsManager lightsManager;
                LightsService.this.mCoverType = coverState.getType();
                if (coverState.getSwitchState()) {
                    BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder(" onCoverStateChanged : SWITCH_STATE_COVER_OPEN covertype: "), LightsService.this.mCoverType, "LightsService");
                    LightsService.this.mCoverOpened = true;
                    return;
                }
                BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder(" onCoverStateChanged : SWITCH_STATE_COVER_CLOSE covertype: "), LightsService.this.mCoverType, "LightsService");
                LightsService lightsService = LightsService.this;
                lightsService.mCoverOpened = false;
                if (lightsService.mCoverType == 8 || (lightsManager = (LightsManager) LocalServices.getService(LightsManager.class)) == null) {
                    return;
                }
                LightsService.this.mCoverBatteryLight = lightsManager.getLight(3);
                LightsService.this.mCoverNotiLight = lightsManager.getLight(4);
                LogicalLight logicalLight = LightsService.this.mCoverBatteryLight;
                if (logicalLight != null) {
                    logicalLight.turnOff();
                }
                LogicalLight logicalLight2 = LightsService.this.mCoverNotiLight;
                if (logicalLight2 != null) {
                    logicalLight2.turnOff();
                }
            }
        };
        this.isLightSensorEnabled = false;
        this.mLightListener = new SensorEventListener() { // from class: com.android.server.lights.LightsService.4
            @Override // android.hardware.SensorEventListener
            public final void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public final void onSensorChanged(SensorEvent sensorEvent) {
                int i = (int) sensorEvent.values[0];
                Slog.d("LightsService", "[SvcLED] onSensorChanged : light value: " + i);
                synchronized (LightsService.this.mSvcLEDThread) {
                    LightsService.this.mSvcLEDHandler.removeMessages(1);
                    LightsService.this.enableSvcLEDLightSensorLocked(false);
                    LightsService.this.setSvcLedLightLocked(i);
                }
                LightsService lightsService = LightsService.this;
                lightsService.mAlarmManagerForSvcLED.cancel(lightsService.mUpdateSvcLEDPendingIntent);
                if (LightsService.mSvcLedState != 0) {
                    LightsService.this.mAlarmManagerForSvcLED.set(3, SystemClock.elapsedRealtime() + r4.mUpdateSvcLEDDelay, LightsService.this.mUpdateSvcLEDPendingIntent);
                }
            }
        };
        this.mLedLowPower = -1;
        this.mLastSvcLedId = 3;
        this.mWakeLockAcquired = false;
        this.mH = new Handler(looper);
        if (PowerManagerUtil.SEC_FEATURE_USE_LIGHTS_HAL_EXTENSION) {
            this.mVintfLights = null;
            this.mVintfSehLights = supplier2.get() == null ? null : supplier2;
        } else {
            this.mVintfSehLights = null;
            this.mVintfLights = supplier.get() == null ? null : supplier;
        }
        if (this.mVintfLights != null) {
            Slog.d("LightsService", "populateAvailableLightsFromAidl");
            try {
                for (HwLight hwLight : ((ILights.Stub.Proxy) ((ILights) this.mVintfLights.get())).getLights()) {
                    this.mLightsById.put(hwLight.id, new LightImpl(hwLight));
                }
            } catch (RemoteException e) {
                Slog.e("LightsService", "Unable to get lights from HAL", e);
            }
        } else if (this.mVintfSehLights != null) {
            Slog.d("LightsService", "populateAvailableLightsFromAidlExtension");
            try {
                for (SehHwLight sehHwLight : ((ISehLights.Stub.Proxy) ((ISehLights) this.mVintfSehLights.get())).getLights()) {
                    HwLight hwLight2 = new HwLight();
                    byte b = (byte) sehHwLight.id;
                    hwLight2.id = b;
                    hwLight2.ordinal = (byte) sehHwLight.ordinal;
                    hwLight2.type = (byte) sehHwLight.type;
                    this.mLightsById.put(b, new LightImpl(hwLight2));
                }
            } catch (RemoteException e2) {
                Slog.e("LightsService", "Unable to get lights from HAL extension", e2);
            }
        } else {
            Slog.d("LightsService", "populateAvailableLightsFromHidl");
            for (int i = 0; i < 10; i++) {
                HwLight hwLight3 = new HwLight();
                byte b2 = (byte) i;
                hwLight3.id = b2;
                hwLight3.ordinal = 1;
                hwLight3.type = b2;
                this.mLightsById.put(b2, new LightImpl(hwLight3));
            }
        }
        for (int size = this.mLightsById.size() - 1; size >= 0; size--) {
            LightImpl lightImpl = (LightImpl) this.mLightsById.valueAt(size);
            byte b3 = lightImpl.mHwLight.type;
            if (b3 >= 0 && b3 < 10) {
                lightImplArr[b3] = lightImpl;
                Slog.d("LightsService", "populateAvailableLights: ".concat(translateLightType(b3)));
            }
        }
        this.mManagerService = new LightsManagerBinderService();
        this.mContext = context;
        this.mInitCompleteForSvcLED = false;
        ?? r10 = new HandlerThread() { // from class: com.android.server.lights.LightsService.1
            @Override // android.os.HandlerThread
            public final void onLooperPrepared() {
                LightsService lightsService = LightsService.this;
                LightsService lightsService2 = LightsService.this;
                lightsService.mSvcLEDHandler = lightsService2.new SvcLEDHandler(lightsService2.mSvcLEDThread.getLooper());
                synchronized (LightsService.this.mSvcLEDThread) {
                    try {
                        LightsService lightsService3 = LightsService.this;
                        lightsService3.mInitCompleteForSvcLED = true;
                        lightsService3.mSvcLEDThread.notifyAll();
                        Resources resources = LightsService.this.mContext.getResources();
                        LightsService.this.mUsePatternLED = resources.getBoolean(R.bool.config_windowEnableCircularEmulatorDisplayOverlay);
                        LightsService.this.mUseSoftwareAutoBrightness = resources.getBoolean(R.bool.config_batterySaverStickyBehaviourDisabled);
                        if (LightsService.this.mUseSoftwareAutoBrightness && PowerManagerUtil.fileExist("/sys/class/sec/led/led_lowpower")) {
                            LightsService.this.mUseLEDAutoBrightness = true;
                        }
                        LightsService lightsService4 = LightsService.this;
                        lightsService4.mDelayForcedSvcLEDTask = lightsService4.mUseLEDAutoBrightness ? 700 : 0;
                        Slog.d("LightsService", "[SvcLED]   mUseSoftwareAutoBrightness: " + LightsService.this.mUseSoftwareAutoBrightness + ",  mUseLEDAutoBrightness: " + LightsService.this.mUseLEDAutoBrightness + ",  mDelayForcedSvcLEDTask: " + LightsService.this.mDelayForcedSvcLEDTask + ",  mUsePatternLED: " + LightsService.this.mUsePatternLED);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mSvcLEDThread = r10;
        r10.start();
        synchronized (r10) {
            while (!this.mInitCompleteForSvcLED) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public static native void setLight_native(int i, int i2, int i3, int i4, int i5, int i6);

    public static String translateID(int i) {
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

    public static String translateLightType(int i) {
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

    public final void enableSvcLEDLightSensorLocked(boolean z) {
        SensorManager sensorManager;
        if (!this.mUseLEDAutoBrightness || (sensorManager = this.mSensorManager) == null) {
            return;
        }
        if (!z || mSvcLedState == 0) {
            if (this.isLightSensorEnabled) {
                sensorManager.unregisterListener(this.mLightListener);
                this.isLightSensorEnabled = false;
                return;
            }
            return;
        }
        if (this.isLightSensorEnabled) {
            return;
        }
        sensorManager.registerListener(this.mLightListener, this.mLightSensor, 3);
        this.isLightSensorEnabled = true;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            Slog.d("LightsService", "[SvcLED] systemReady");
            this.mAlarmManagerForSvcLED = (AlarmManager) this.mContext.getSystemService("alarm");
            this.mUpdateSvcLEDPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.android.server.LightsService.action.UPDATE_SVC_LED", (Uri) null), 67108864);
            SystemSensorManager systemSensorManager = new SystemSensorManager(this.mContext, getLooper());
            this.mSensorManager = systemSensorManager;
            if (this.mUseLEDAutoBrightness) {
                this.mLightSensor = systemSensorManager.getDefaultSensor(5);
            }
            this.mCoverManager = new CoverManager(this.mContext);
            Slog.d("LightsService", "initCoverState()start");
            CoverManager coverManager = this.mCoverManager;
            if (coverManager != null) {
                CoverState coverState = coverManager.getCoverState();
                if (coverState != null) {
                    this.mCoverType = coverState.getType();
                    this.mCoverOpened = coverState.getSwitchState();
                    BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("initCoverState() coverstate : "), this.mCoverType, "LightsService");
                } else {
                    Slog.d("LightsService", "initCoverState() : state is null");
                }
            } else {
                Slog.d("LightsService", "initCoverState() : mCoverManager is null!!!!");
            }
            CoverManager coverManager2 = this.mCoverManager;
            if (coverManager2 != null) {
                coverManager2.registerListener(this.mCoverStateListener);
            } else {
                Slog.d("LightsService", "initLightsService() : mCoverManager is null!!!!");
            }
            if (!this.mInitializedWakeLockPath) {
                String[] strArr = this.mNewWakeLockPaths;
                if (PowerManagerUtil.fileExist(strArr[0])) {
                    this.mWakeLockPath = strArr[0];
                    if (PowerManagerUtil.fileExist(strArr[1])) {
                        this.mWakeUnlockPath = strArr[1];
                        this.mInitializedWakeLockPath = true;
                    } else {
                        Slog.d("LightsService", "wake_unlock path does not exists ");
                    }
                } else {
                    Slog.d("LightsService", "wake_lock path does not exists");
                }
            }
            this.mContext.registerReceiver(new SvcLEDReceiver(), BatteryService$$ExternalSyntheticOutline0.m("com.android.server.LightsService.action.UPDATE_SVC_LED"));
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishLocalService(LightsManager.class, this.mService);
        publishBinderService("lights", this.mManagerService);
    }

    public final void setSvcLedLightLocked(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = i < 20 ? 1 : 0;
        if (i6 == 1 && !this.mCoverOpened && this.mCoverType == 8) {
            i6 = 0;
        }
        if (this.mUseLEDAutoBrightness && i6 != this.mLedLowPower) {
            this.mLedLowPower = i6;
            int i7 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
            Slog.d("PowerManagerUtil", "fileWriteInt to /sys/class/sec/led/led_lowpower, " + i6);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File("/sys/class/sec/led/led_lowpower"));
                try {
                    fileOutputStream.write(Integer.toString(i6).getBytes());
                    fileOutputStream.close();
                } finally {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mIsLEDChanged = true;
        }
        int i8 = 0;
        while (i8 < 6 && ((mSvcLedState >> i8) & 1) == 0) {
            i8++;
        }
        if (mSvcLedState == 0) {
            i4 = this.mLastSvcLedId;
            i3 = 0;
        } else {
            if (i8 != 0) {
                int i9 = 4;
                if (i8 == 1) {
                    i5 = 12;
                } else if (i8 == 2) {
                    i5 = mSvcLedMode;
                } else if (i8 == 3) {
                    i2 = 10;
                } else if (i8 != 4) {
                    i9 = 5;
                    if (i8 != 5) {
                        i3 = 0;
                        i4 = i9;
                    } else {
                        i2 = 14;
                    }
                } else {
                    i2 = 13;
                }
                i3 = i5;
                i4 = i9;
            } else {
                i2 = 11;
            }
            i3 = i2;
            i4 = 3;
        }
        this.mLightsByType[i4].setLightLocked(mSvcLedColor, i3, mSvcLedOnMS, mSvcLedOffMS);
        if (this.mLastSvcLedId != i4) {
            Slog.d("LightsService", "[SvcLED] setSvcLedLightLocked : priority changed! SvcLED(" + translateID(this.mLastSvcLedId) + ") OUT; (" + translateID(i4) + ") IN");
        } else {
            Slog.d("LightsService", "[SvcLED] setSvcLedLightLocked : Current SvcLED(" + translateID(i4) + ") maintains its priority right");
        }
        this.mLastSvcLedId = i4;
        if (!this.mInitializedWakeLockPath) {
            Slog.i("LightsService", "releaseWakeLockForLED: WakeLock path is not initialized");
        } else {
            if (!this.mWakeLockAcquired || this.mSvcLEDHandler.hasMessages(1)) {
                return;
            }
            PowerManagerUtil.fileWriteString(this.mWakeUnlockPath, "LightsService");
            this.mWakeLockAcquired = false;
        }
    }

    public final void setSvcLedStateLocked(int i, int i2, int i3, int i4, int i5, boolean z) {
        int i6 = mSvcLedState;
        mPrevSvcLedState = i6;
        if (i == 3) {
            int i7 = -58;
            int i8 = i6 & (-58);
            mSvcLedState = i8;
            if (z) {
                switch (i3) {
                    case 10:
                        i7 = 8;
                        break;
                    case 11:
                        i7 = 1;
                        break;
                    case 13:
                        i7 = 16;
                        break;
                    case 14:
                        i7 = 32;
                        break;
                }
                mSvcLedState = i7 | i8;
            }
        } else if (i != 4) {
            Slog.d("LightsService", "[SvcLED] setSvcLedStateLocked : Not Support, ".concat(translateLightType(i)));
        } else if (!z) {
            mSvcLedState = i6 & (-7);
        } else if (i3 == 12) {
            mSvcLedState = i6 | 2;
        } else {
            mSvcLedState = i6 | 4;
            mSvcLedColor = i2;
            mSvcLedMode = i3;
            mSvcLedOnMS = i4;
            mSvcLedOffMS = i5;
        }
        if (mPrevSvcLedState != mSvcLedState) {
            this.mIsLEDChanged = true;
        }
        StringBuilder sb = new StringBuilder("[SvcLED] setSvcLedStateLocked : SvcLEDState: 0x");
        BatteryService$$ExternalSyntheticOutline0.m(mPrevSvcLedState, sb, " -> 0x");
        sb.append(Integer.toHexString(mSvcLedState));
        sb.append(" | SvcLED(");
        sb.append(translateLightType(i));
        sb.append(") set ");
        sb.append(z ? "On" : "Off");
        Slog.d("LightsService", sb.toString());
    }
}
