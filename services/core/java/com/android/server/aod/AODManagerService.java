package com.android.server.aod;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SemSystemProperties;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.BatteryStatsService;
import com.samsung.android.aod.AODManager;
import com.samsung.android.aod.AODManagerInternal;
import com.samsung.android.aod.AODToast;
import com.samsung.android.aod.IAODCallback;
import com.samsung.android.aod.IAODDozeCallback;
import com.samsung.android.aod.IAODManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AODManagerService extends SystemService {
    public AODDozeCallbackRecord mAODCallback;
    public AODManager.AODChangeListener mAODChangeListener;
    public final ArrayList mAODDozeLocks;
    public int mAODEndTime;
    public final AODHandler mAODHandler;
    public final AnonymousClass2 mAODLogHandler;
    public final AODSettingHelper mAODSettingHelper;
    public int mAODStartTime;
    public final AODLogger mAppLogger;
    public AnonymousClass4 mContentObserver;
    public final Context mContext;
    public Context mContextForUser;
    public int mDefaultDisplayState;
    public final AnonymousClass3 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public SemInputDeviceManager mInputDeviceManager;
    public boolean mIsAODAnalogLiveClock;
    public boolean mIsAODAuto;
    public boolean mIsAODModeEnabled;
    public boolean mIsAODShowForNewNoti;
    public boolean mIsAODStartStop;
    public boolean mIsAODTapToShow;
    public boolean mIsEdgeShowWhenScreenOff;
    public boolean mIsFingerScreenLock;
    public boolean mIsFingerScreenOffIconAOD;
    public boolean mIsMPSMEnabled;
    public boolean mIsSingleTouchMode;
    public boolean mIsUPSMEnabled;
    public final ArrayList mListeners;
    public final AODLogger mLiveClockLogger;
    public final Object mLock;
    public final Looper mLooper;
    public final Object mScreenTurningOnLock;
    public final AnonymousClass1 mScreenTurningOnRunnable;
    public int mSpenUspLevel;
    public int mSystemUiUid;
    public int mTspH;
    public final AODLogger mTspLogger;
    public final HashMap mTspRects;
    public int mTspW;
    public int mTspX;
    public int mTspY;
    public final UserManager mUserManager;
    public boolean requestedReCalToTSP;
    public static final int DEBUG_TURNING_ON_DELAYED = SemSystemProperties.getInt("debug.aod.turningondelay", 0);
    public static final Uri AOD_SETTING_CLOCK_TYPE_URI_PARSED = Uri.parse("content://com.samsung.android.app.aodservice.provider/settings/aod_clock_type");
    public static final int GREAT_SPEN_USP_LEVEL = 30;
    public static final boolean SUPPORT_AOD = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("aodversion");
    public static final boolean SUPPORT_AOD_LIVE_CLOCK = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "").contains("activeclock");

    /* renamed from: com.android.server.aod.AODManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends Handler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass2(Object obj, Looper looper, int i) {
            super(looper);
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Object obj = this.this$0;
            switch (this.$r8$classId) {
                case 0:
                    if (message.what == 100) {
                        ArrayList<String> stringArrayList = message.getData().getStringArrayList("logs");
                        int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                        AODManagerService aODManagerService = (AODManagerService) obj;
                        aODManagerService.getClass();
                        if (stringArrayList != null) {
                            for (int i2 = 0; i2 < stringArrayList.size(); i2++) {
                                aODManagerService.mAppLogger.add(stringArrayList.get(i2), false);
                            }
                            break;
                        }
                    }
                    break;
                default:
                    super.handleMessage(message);
                    int i3 = message.what;
                    AODDozeCallbackRecord aODDozeCallbackRecord = (AODDozeCallbackRecord) obj;
                    if (i3 == 1) {
                        aODDozeCallbackRecord.getClass();
                        try {
                            IAODDozeCallback asInterface = IAODDozeCallback.Stub.asInterface(aODDozeCallbackRecord.token);
                            if (asInterface != null) {
                                asInterface.onDozeAcquired();
                                break;
                            }
                        } catch (RemoteException e) {
                            int i4 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                            Slog.e("AODManagerService", "handleAODDozeAcquired : RemoteException : ", e);
                        }
                    } else if (i3 == 2) {
                        aODDozeCallbackRecord.getClass();
                        try {
                            IAODDozeCallback asInterface2 = IAODDozeCallback.Stub.asInterface(aODDozeCallbackRecord.token);
                            if (asInterface2 != null) {
                                asInterface2.onDozeReleased();
                                break;
                            }
                        } catch (RemoteException e2) {
                            int i5 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                            Slog.e("AODManagerService", "handleAODDozeReleased : RemoteException : ", e2);
                            return;
                        }
                    } else if (i3 == 3) {
                        AODToast aODToast = (AODToast) message.obj;
                        aODDozeCallbackRecord.getClass();
                        try {
                            IAODDozeCallback asInterface3 = IAODDozeCallback.Stub.asInterface(aODDozeCallbackRecord.token);
                            if (asInterface3 != null) {
                                asInterface3.onAODToastRequested(aODToast);
                                break;
                            }
                        } catch (RemoteException e3) {
                            int i6 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                            Slog.e("AODManagerService", "handleAODDozeReleased : RemoteException : ", e3);
                            return;
                        }
                    }
                    break;
            }
        }
    }

    public final class AODDozeCallbackRecord implements IBinder.DeathRecipient {
        public final AnonymousClass2 mHandler;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public AODDozeCallbackRecord(IBinder iBinder, int i, int i2) {
            this.mHandler = new AnonymousClass2(this, AODManagerService.this.mLooper, 1);
            this.token = iBinder;
            this.pid = i;
            this.uid = i2;
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    int i3 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                    Slog.e("AODManagerService", "AODListenerRecord : linkToDeath error");
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
            Slog.v("AODManagerService", "binderDied");
            this.mHandler.removeCallbacksAndMessages(null);
            synchronized (AODManagerService.this.mAODDozeLocks) {
                AODManagerService.this.mAODCallback = null;
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[callback: pid:(");
            sb.append(this.pid);
            sb.append(") uid:(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")]");
        }
    }

    public final class AODDozeLock implements IBinder.DeathRecipient {
        public final IBinder mLock;
        public final int mOwnerPid;
        public final int mOwnerUid;
        public final String mPackageName;
        public final String mTag;

        public AODDozeLock(IBinder iBinder, String str, String str2, int i, int i2) {
            this.mLock = iBinder;
            this.mTag = str;
            this.mPackageName = str2;
            this.mOwnerUid = i;
            this.mOwnerPid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
            Slog.v("AODManagerService", "AODDozeLock : binderDied");
            synchronized (AODManagerService.this.mAODDozeLocks) {
                AODManagerService.this.mAODDozeLocks.remove(this);
            }
            this.mLock.unlinkToDeath(this, 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[AODDozeLock: tag:(");
            sb.append(this.mTag);
            sb.append(") packageName:(");
            sb.append(this.mPackageName);
            sb.append(") uid:(");
            sb.append(this.mOwnerUid);
            sb.append(") pid:(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mOwnerPid, sb, ")]");
        }
    }

    public final class AODHandler extends Handler {
    }

    public final class AODListenerRecord implements IBinder.DeathRecipient {
        public final int pid;
        public final IBinder token;
        public final int uid;

        public AODListenerRecord(IBinder iBinder, int i, int i2) {
            this.token = iBinder;
            this.pid = i;
            this.uid = i2;
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    int i3 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                    Slog.e("AODManagerService", "AODListenerRecord : linkToDeath error");
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
            Slog.v("AODManagerService", "binderDied");
            synchronized (AODManagerService.this.mListeners) {
                AODManagerService.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("  [Listener: pid:(");
            sb.append(this.pid);
            sb.append(") uid:(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")]");
        }
    }

    public final class BinderService extends IAODManager.Stub {
        public BinderService() {
        }

        public final void acquireDoze(IBinder iBinder, String str, String str2) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m213$$Nest$macquireDozeInternal(AODManagerService.this, iBinder, str, str2, callingUid, callingPid);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void addLogText(List list) {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService aODManagerService = AODManagerService.this;
                aODManagerService.getClass();
                AODManagerService.checkSystemUid();
                AnonymousClass2 anonymousClass2 = aODManagerService.mAODLogHandler;
                Message obtainMessage = anonymousClass2.obtainMessage(100);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("logs", new ArrayList<>(list));
                obtainMessage.setData(bundle);
                anonymousClass2.sendMessage(obtainMessage);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (AODManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") == 0) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AODManagerService.m216$$Nest$mdumpInternal(AODManagerService.this, printWriter);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            printWriter.println("Permission Denial: can't dump AODManagerService from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " without permission android.permission.DUMP");
        }

        public final String getActiveImageInfo() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService aODManagerService = AODManagerService.this;
                int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                aODManagerService.getClass();
                AODManagerService.checkSystemUid();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final String getAodActiveArea(boolean z) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            int i = z ? 2 : 1;
            try {
                Log.i("AODManagerService", "getAodActiveArea isSubDisplay=" + z + " devid=" + i);
                SemInputDeviceManager semInputDeviceManager = AODManagerService.this.mInputDeviceManager;
                return semInputDeviceManager != null ? semInputDeviceManager.getAodActiveArea(i) : "NG";
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isAODState() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.System.getIntForUser(AODManagerService.this.mAODSettingHelper.mResolver, "aod_show_state", 0, -2) == 1;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isSViewCoverBrightnessHigh() {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            Binder.restoreCallingIdentity(Binder.clearCallingIdentity());
            return false;
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            int callingUid = Binder.getCallingUid();
            if (callingUid != 2000 && callingUid != 0) {
                throw new SecurityException("Caller must be shell");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Context context = AODManagerService.this.mContext;
                AODManagerShellCommand aODManagerShellCommand = new AODManagerShellCommand();
                aODManagerShellCommand.mContext = context;
                aODManagerShellCommand.exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void readyToScreenTurningOn() {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m217$$Nest$mreadyToScreenTurningOnInternal(AODManagerService.this);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerAODDozeCallback(IBinder iBinder) {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m218$$Nest$mregisterAODDozeCallbackInternal(AODManagerService.this, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerAODListener(IBinder iBinder) {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m219$$Nest$mregisterAODListenerInternal(AODManagerService.this, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseDoze(IBinder iBinder) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m220$$Nest$mreleaseDozeInternal(AODManagerService.this, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestAODToast(String str, AODToast aODToast) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m221$$Nest$mrequestAODToastInternal(AODManagerService.this, aODToast);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setGripData(String str) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.this.mInputDeviceManager.setGripData(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setLiveClockCommand(int i, int i2, int i3, int[] iArr) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (iArr == null) {
                int i4 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                Slog.e("AODManagerService", "setLiveClockCommand dataArray is null");
                return -1;
            }
            try {
                try {
                    AODManagerService aODManagerService = AODManagerService.this;
                    int i5 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                    aODManagerService.getClass();
                    AODManagerService.checkSystemUid();
                    Log.e("AODManagerService", "AODConfig.SUPPORT_ACTIVE_CLOCK is FALSE");
                } catch (Exception e) {
                    int i6 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                    Slog.e("AODManagerService", "failed setLiveClockCommand = " + e);
                }
                return -1;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setLiveClockImage(int i, int i2, byte[] bArr, String str) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (bArr == null) {
                int i3 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                Slog.e("AODManagerService", "setLiveClockImage img_buf is null");
                return -1;
            }
            try {
                try {
                    AODManagerService aODManagerService = AODManagerService.this;
                    int i4 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                    aODManagerService.getClass();
                    AODManagerService.checkSystemUid();
                    Log.e("AODManagerService", "AODConfig.SUPPORT_ACTIVE_CLOCK is FALSE");
                } catch (Exception e) {
                    int i5 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                    Slog.e("AODManagerService", "failed setLiveClockImage = " + e);
                }
                return -1;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService aODManagerService = AODManagerService.this;
                int i2 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                aODManagerService.getClass();
                AODManagerService.checkSystemUid();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void setLiveClockNeedle(byte[] bArr) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService aODManagerService = AODManagerService.this;
                int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                aODManagerService.getClass();
                AODManagerService.checkSystemUid();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean setTspEnabled(String str, String str2) {
            int i;
            if (Build.VERSION.SEM_FIRST_SDK_INT < 31) {
                int i2 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                Slog.i("AODManagerService", "setTSPEnabled: First SDK version is less than S OS");
                return false;
            }
            AODManagerService aODManagerService = AODManagerService.this;
            if (aODManagerService.mInputDeviceManager == null) {
                int i3 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                Slog.i("AODManagerService", "setTSPEnabled: mInputDeviceManager is null");
                return false;
            }
            int i4 = aODManagerService.mIsSingleTouchMode ? 22 : 21;
            if ("/sys/class/sec/sec_epen/input/enabled".equals(str)) {
                i = 11;
                AODManagerService.this.mInputDeviceManager.setSpenEnabled(11, i4, false);
            } else {
                i = "/sys/class/sec/tsp2/input/enabled".equals(str) ? 2 : 1;
                AODManagerService.this.mInputDeviceManager.setTspEnabled(i, i4, false);
            }
            AODManagerService aODManagerService2 = AODManagerService.this;
            aODManagerService2.requestedReCalToTSP = aODManagerService2.mIsSingleTouchMode;
            int i5 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("setTspEnabled: location=", str, ", cmd=", str2, ", devid=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(i, i4, ", mode=", ", mIsSingleTouchMode=", m);
            HeimdAllFsService$$ExternalSyntheticOutline0.m("AODManagerService", m, AODManagerService.this.mIsSingleTouchMode);
            return true;
        }

        public final void unregisterAODDozeCallback(IBinder iBinder) {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m222$$Nest$munregisterAODDozeCallbackInternal(AODManagerService.this, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterAODListener(IBinder iBinder) {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m223$$Nest$munregisterAODListenerInternal(AODManagerService.this, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void updateAODNotiTspRect(int i, int i2, int i3, int i4, String str) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.m224$$Nest$mupdateAODNotiTspRectInternal(AODManagerService.this, i, i2, i3, i4, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void updateAODTspRect(int i, int i2, int i3, int i4, String str) {
            AODManagerService.m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AODManagerService.this.updateAODTspRectInternal(i, i2, i3, i4, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void writeAODCommand(String str, String str2, String str3, String str4, String str5) {
            AODManagerService.m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if ("/sys/class/sec/tsp/input/enabled".equals(str) || "/sys/class/sec/sec_epen/input/enabled".equals(str) || "/sys/class/sec/tsp1/input/enabled".equals(str) || "/sys/class/sec/tsp2/input/enabled".equals(str)) {
                    AODManagerService.this.mIsSingleTouchMode = "1".equals(str2);
                    if (setTspEnabled(str, str2)) {
                        return;
                    }
                }
                AODManagerService.m225$$Nest$mwriteAODCommandInternal(AODManagerService.this, str, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final class LocalService extends AODManagerInternal {
        public LocalService() {
        }

        public final boolean isAODAnalogLiveClock() {
            int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
            FlashNotificationsController$$ExternalSyntheticOutline0.m("AODManagerService", new StringBuilder("isAODAnalogLiveClock: mIsAODAnalogLiveClock : "), AODManagerService.this.mIsAODAnalogLiveClock);
            return AODManagerService.this.mIsAODAnalogLiveClock;
        }

        public final void screenTurningOn(AODManager.AODChangeListener aODChangeListener) {
            int i;
            AODManagerService aODManagerService;
            if (!AODConfig.SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_AOD_DOZE_SERVICE || (i = AODManagerService.DEBUG_TURNING_ON_DELAYED) == 0) {
                int i2 = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                Log.d("AODManagerService", "screenTurningOn directly");
                if (aODChangeListener != null) {
                    aODChangeListener.readyToScreenTurningOn();
                    return;
                } else {
                    Log.d("AODManagerService", "screenTurningOn : Do nothing, There is no Listener");
                    return;
                }
            }
            AODManagerService aODManagerService2 = AODManagerService.this;
            if (aODManagerService2.mAODHandler.hasCallbacks(aODManagerService2.mScreenTurningOnRunnable)) {
                AODManagerService aODManagerService3 = AODManagerService.this;
                aODManagerService3.mAODHandler.removeCallbacks(aODManagerService3.mScreenTurningOnRunnable);
            }
            Log.d("AODManagerService", "screenTurningOn post");
            if (i != 0) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(i, "screenTurningOn post - add delay +", "AODManagerService");
            }
            synchronized (AODManagerService.this.mScreenTurningOnLock) {
                aODManagerService = AODManagerService.this;
                aODManagerService.mAODChangeListener = aODChangeListener;
            }
            aODManagerService.mAODHandler.postDelayed(aODManagerService.mScreenTurningOnRunnable, i);
            AODManagerService aODManagerService4 = AODManagerService.this;
            aODManagerService4.getClass();
            AODManagerService.checkSystemUid();
            synchronized (aODManagerService4.mListeners) {
                try {
                } catch (RemoteException e) {
                    Slog.e("AODManagerService", "_onScreenTurningOn : RemoteException : ", e);
                } finally {
                }
                if (aODManagerService4.mListeners.size() < 1) {
                    return;
                }
                Iterator it = aODManagerService4.mListeners.iterator();
                while (it.hasNext()) {
                    AODListenerRecord aODListenerRecord = (AODListenerRecord) it.next();
                    if (aODListenerRecord != null) {
                        IBinder iBinder = aODListenerRecord.token;
                        if (iBinder == null) {
                            Slog.w("AODManagerService", "onScreenTurningOn : token is null");
                        } else {
                            IAODCallback asInterface = IAODCallback.Stub.asInterface(iBinder);
                            if (asInterface != null) {
                                asInterface.onScreenTurningOn();
                            }
                        }
                    }
                }
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri mAODLowPowerUri;
        public final Uri mAODPowerSavingModeUri;
        public final Uri mAODShowStateUri;
        public final Uri mDozeAlwaysOnUri;

        public SettingsObserver(AODHandler aODHandler) {
            super(aODHandler);
            this.mAODShowStateUri = Settings.System.getUriFor("aod_show_state");
            Settings.System.getUriFor("aod_mode");
            this.mDozeAlwaysOnUri = Settings.Secure.getUriFor("doze_always_on");
            this.mAODLowPowerUri = Settings.System.getUriFor("low_power");
            this.mAODPowerSavingModeUri = Settings.System.getUriFor("ultra_powersaving_mode");
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0056, code lost:
        
            if (r3 != null) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0058, code lost:
        
            r3.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x006b, code lost:
        
            if (r3 == null) goto L37;
         */
        @Override // android.database.ContentObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onChange(boolean r11, android.net.Uri r12) {
            /*
                Method dump skipped, instructions count: 411
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.aod.AODManagerService.SettingsObserver.onChange(boolean, android.net.Uri):void");
        }
    }

    /* renamed from: -$$Nest$macquireDozeInternal, reason: not valid java name */
    public static void m213$$Nest$macquireDozeInternal(AODManagerService aODManagerService, IBinder iBinder, String str, String str2, int i, int i2) {
        AODDozeCallbackRecord aODDozeCallbackRecord;
        synchronized (aODManagerService.mAODDozeLocks) {
            try {
                StringBuilder sb = new StringBuilder("acquireDozeInternal: mAODCallback ");
                sb.append(aODManagerService.mAODCallback != null ? "existed" : "null");
                sb.append(", display = ");
                sb.append(aODManagerService.mDefaultDisplayState);
                Log.d("AODManagerService", sb.toString());
                if (aODManagerService.mDefaultDisplayState == 2) {
                    return;
                }
                int size = aODManagerService.mAODDozeLocks.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        i3 = -1;
                        break;
                    } else if (((AODDozeLock) aODManagerService.mAODDozeLocks.get(i3)).mLock == iBinder) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i3 >= 0) {
                    Log.d("AODManagerService", "acquireDozeInternal: already acquired");
                    return;
                }
                AODDozeLock aODDozeLock = aODManagerService.new AODDozeLock(iBinder, str, str2, i, i2);
                try {
                    iBinder.linkToDeath(aODDozeLock, 0);
                    boolean isEmpty = aODManagerService.mAODDozeLocks.isEmpty();
                    aODManagerService.mAODDozeLocks.add(aODDozeLock);
                    if (isEmpty) {
                        int i4 = aODManagerService.mDefaultDisplayState;
                        if ((i4 == 1 || i4 == 3 || i4 == 4) && (aODDozeCallbackRecord = aODManagerService.mAODCallback) != null) {
                            if (aODDozeCallbackRecord.token == null) {
                                Slog.w("AODManagerService", "onAODDozeAcquired : token is null");
                            } else {
                                aODDozeCallbackRecord.mHandler.sendMessage(aODDozeCallbackRecord.mHandler.obtainMessage(1));
                            }
                        }
                    }
                } catch (RemoteException unused) {
                    throw new IllegalArgumentException("AOD doze lock is already dead.");
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mcheckSystemUidOrSystemUiUid, reason: not valid java name */
    public static void m214$$Nest$mcheckSystemUidOrSystemUiUid(AODManagerService aODManagerService) {
        aODManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || UserHandle.isSameApp(callingUid, aODManagerService.mSystemUiUid)) {
            return;
        }
        throw new SecurityException("Disallowed call for uid " + Binder.getCallingUid());
    }

    /* renamed from: -$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp, reason: not valid java name */
    public static void m215$$Nest$mcheckSystemUidOrSystemUiUidOrSystemApp(AODManagerService aODManagerService) {
        aODManagerService.getClass();
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000 || UserHandle.isSameApp(callingUid, aODManagerService.mSystemUiUid)) {
            return;
        }
        PackageManager packageManager = aODManagerService.mContext.getPackageManager();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (packagesForUid != null) {
                for (String str : packagesForUid) {
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                        if (packageInfo != null && (packageInfo.applicationInfo.flags & 129) != 0) {
                            return;
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.w("AODManagerService", "Could not find package [" + str + "]", e);
                    }
                }
            } else {
                NetworkScoreService$$ExternalSyntheticOutline0.m(callingUid, "No known packages with uid ", "AODManagerService");
            }
            throw new SecurityException("Disallowed call for uid " + Binder.getCallingUid());
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$mdumpInternal, reason: not valid java name */
    public static void m216$$Nest$mdumpInternal(AODManagerService aODManagerService, PrintWriter printWriter) {
        aODManagerService.getClass();
        printWriter.println("AODMANAGER (dumpsys AODManagerService)");
        printWriter.println();
        boolean z = AODConfig.SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_AOD_DOZE_SERVICE;
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, " AOD Config", "  - isAODTouchDisabled="), AODConfig.isAODTouchDisabled, printWriter, "  - isAODDefaultOn="), (AODConfig.isFactoryBinary || "OFF".contains(AODConfig.AOD_MODE_DEFAULT_VALUE)) ? false : true, printWriter, "ActivityManager.getCurrentUser()=");
        m.append(ActivityManager.getCurrentUser());
        printWriter.println(m.toString());
        printWriter.println();
        synchronized (aODManagerService.mAODDozeLocks) {
            try {
                printWriter.println("mAODCallback= " + aODManagerService.mAODCallback);
                Iterator it = aODManagerService.mAODDozeLocks.iterator();
                while (it.hasNext()) {
                    printWriter.println("AODDozeLock= " + ((AODDozeLock) it.next()));
                }
            } finally {
            }
        }
        printWriter.println();
        printWriter.println("----- Regarding AOD TSP -----");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("mIsAODModeEnabled(tsp.cmd aod_enable)="), aODManagerService.mIsAODModeEnabled, printWriter, "mIsSingleTouchMode(tsp.input.enabled)="), aODManagerService.mIsSingleTouchMode, printWriter);
        synchronized (aODManagerService.mTspRects) {
            try {
                for (Map.Entry entry : aODManagerService.mTspRects.entrySet()) {
                    printWriter.println("tsp touch rect(uid :" + ((String) entry.getKey()) + "), " + entry.getValue());
                }
            } finally {
            }
        }
        printWriter.println("tsp touch : x=" + aODManagerService.mTspX + ", y=" + aODManagerService.mTspY + ", w=" + aODManagerService.mTspW + ", h=" + aODManagerService.mTspH);
        printWriter.println("live clock image info : null");
        printWriter.println("Self Icon image info : null");
        aODManagerService.mAppLogger.dump(printWriter);
        aODManagerService.mLiveClockLogger.dump(printWriter);
        aODManagerService.mTspLogger.dump(printWriter);
    }

    /* renamed from: -$$Nest$mreadyToScreenTurningOnInternal, reason: not valid java name */
    public static void m217$$Nest$mreadyToScreenTurningOnInternal(AODManagerService aODManagerService) {
        aODManagerService.getClass();
        checkSystemUid();
        if (!AODConfig.SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_AOD_DOZE_SERVICE) {
            Log.w("AODManagerService", "readyToScreenTurningOn : Not allowed");
            return;
        }
        synchronized (aODManagerService.mScreenTurningOnLock) {
            try {
                Log.d("AODManagerService", "readyToScreenTurningOn");
                AODManager.AODChangeListener aODChangeListener = aODManagerService.mAODChangeListener;
                if (aODChangeListener != null) {
                    aODChangeListener.readyToScreenTurningOn();
                    aODManagerService.mAODChangeListener = null;
                    aODManagerService.mAODHandler.removeCallbacks(aODManagerService.mScreenTurningOnRunnable);
                } else {
                    Log.e("AODManagerService", "readyToScreenTurningOn : Do nothing, There is no Listener");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mregisterAODDozeCallbackInternal, reason: not valid java name */
    public static void m218$$Nest$mregisterAODDozeCallbackInternal(AODManagerService aODManagerService, IBinder iBinder) {
        synchronized (aODManagerService.mAODDozeLocks) {
            try {
                if (aODManagerService.mAODCallback == null) {
                    Log.d("AODManagerService", "registerAODDozeCallbackInternal");
                    aODManagerService.mAODCallback = aODManagerService.new AODDozeCallbackRecord(iBinder, Binder.getCallingPid(), Binder.getCallingUid());
                } else {
                    Log.w("AODManagerService", "registerAODDozeCallbackInternal : already registered");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mregisterAODListenerInternal, reason: not valid java name */
    public static void m219$$Nest$mregisterAODListenerInternal(AODManagerService aODManagerService, IBinder iBinder) {
        synchronized (aODManagerService.mListeners) {
            try {
                Iterator it = aODManagerService.mListeners.iterator();
                while (it.hasNext()) {
                    AODListenerRecord aODListenerRecord = (AODListenerRecord) it.next();
                    if (aODListenerRecord != null && iBinder.equals(aODListenerRecord.token)) {
                        Log.w("AODManagerService", "registerAODListenerInternal : already registered");
                        return;
                    }
                }
                aODManagerService.mListeners.add(aODManagerService.new AODListenerRecord(iBinder, Binder.getCallingPid(), Binder.getCallingUid()));
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mreleaseDozeInternal, reason: not valid java name */
    public static void m220$$Nest$mreleaseDozeInternal(AODManagerService aODManagerService, IBinder iBinder) {
        AODDozeCallbackRecord aODDozeCallbackRecord;
        synchronized (aODManagerService.mAODDozeLocks) {
            try {
                StringBuilder sb = new StringBuilder("releaseDozeInternal: mAODCallback ");
                sb.append(aODManagerService.mAODCallback != null ? "existed" : "null");
                sb.append(", display = ");
                sb.append(aODManagerService.mDefaultDisplayState);
                Log.d("AODManagerService", sb.toString());
                int i = aODManagerService.mDefaultDisplayState;
                if (i != 2 && i != 1) {
                    int size = aODManagerService.mAODDozeLocks.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            i2 = -1;
                            break;
                        } else if (((AODDozeLock) aODManagerService.mAODDozeLocks.get(i2)).mLock == iBinder) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i2 < 0) {
                        Log.d("AODManagerService", "releaseDozeInternal: cannot find");
                        return;
                    }
                    AODDozeLock aODDozeLock = (AODDozeLock) aODManagerService.mAODDozeLocks.get(i2);
                    aODDozeLock.mLock.unlinkToDeath(aODDozeLock, 0);
                    aODManagerService.mAODDozeLocks.remove(i2);
                    if (aODManagerService.mAODDozeLocks.isEmpty() && (aODDozeCallbackRecord = aODManagerService.mAODCallback) != null) {
                        if (aODDozeCallbackRecord.token == null) {
                            Slog.w("AODManagerService", "onAODDozeReleased : token is null");
                        } else {
                            aODDozeCallbackRecord.mHandler.sendMessage(aODDozeCallbackRecord.mHandler.obtainMessage(2));
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mrequestAODToastInternal, reason: not valid java name */
    public static void m221$$Nest$mrequestAODToastInternal(AODManagerService aODManagerService, AODToast aODToast) {
        synchronized (aODManagerService.mAODDozeLocks) {
            try {
                StringBuilder sb = new StringBuilder("requestAODToastInternal: mAODCallback ");
                sb.append(aODManagerService.mAODCallback != null ? "existed" : "null");
                sb.append(", toast: ");
                sb.append(aODToast);
                Log.d("AODManagerService", sb.toString());
                AODDozeCallbackRecord aODDozeCallbackRecord = aODManagerService.mAODCallback;
                if (aODDozeCallbackRecord != null) {
                    if (aODDozeCallbackRecord.token == null) {
                        Slog.w("AODManagerService", "onAODDozeReleased : token is null");
                    } else {
                        Message obtainMessage = aODDozeCallbackRecord.mHandler.obtainMessage(3);
                        obtainMessage.obj = aODToast;
                        aODDozeCallbackRecord.mHandler.sendMessage(obtainMessage);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$munregisterAODDozeCallbackInternal, reason: not valid java name */
    public static void m222$$Nest$munregisterAODDozeCallbackInternal(AODManagerService aODManagerService, IBinder iBinder) {
        synchronized (aODManagerService.mAODDozeLocks) {
            try {
                AODDozeCallbackRecord aODDozeCallbackRecord = aODManagerService.mAODCallback;
                if (aODDozeCallbackRecord == null || !iBinder.equals(aODDozeCallbackRecord.token)) {
                    Log.w("AODManagerService", "unregisterAODDozeCallbackInternal : cannot find matched callback");
                } else {
                    Log.d("AODManagerService", "unregisterAODDozeCallbackInternal");
                    iBinder.unlinkToDeath(aODManagerService.mAODCallback, 0);
                    aODManagerService.mAODCallback = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$munregisterAODListenerInternal, reason: not valid java name */
    public static void m223$$Nest$munregisterAODListenerInternal(AODManagerService aODManagerService, IBinder iBinder) {
        synchronized (aODManagerService.mListeners) {
            try {
                Iterator it = aODManagerService.mListeners.iterator();
                AODListenerRecord aODListenerRecord = null;
                while (it.hasNext()) {
                    AODListenerRecord aODListenerRecord2 = (AODListenerRecord) it.next();
                    if (aODListenerRecord2 != null && iBinder.equals(aODListenerRecord2.token)) {
                        aODListenerRecord = aODListenerRecord2;
                    }
                }
                if (aODListenerRecord == null) {
                    Log.e("AODManagerService", "unregisterAODListenerInternal : cannot find the matched host");
                    return;
                }
                if (!aODManagerService.mListeners.isEmpty()) {
                    aODManagerService.mListeners.remove(aODListenerRecord);
                }
                iBinder.unlinkToDeath(aODListenerRecord, 0);
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mupdateAODNotiTspRectInternal, reason: not valid java name */
    public static void m224$$Nest$mupdateAODNotiTspRectInternal(AODManagerService aODManagerService, int i, int i2, int i3, int i4, String str) {
        aODManagerService.getClass();
        checkSystemUid();
        synchronized (aODManagerService.mTspRects) {
            try {
                if (i3 >= 0 || i4 >= 0 || i >= 0 || i2 >= 0) {
                    Rect rect = (Rect) aODManagerService.mTspRects.get(str);
                    if (rect == null) {
                        aODManagerService.mTspRects.put(str, new Rect(i3, i4, i + i3, i2 + i4));
                    } else {
                        rect.left = i3;
                        rect.top = i4;
                        rect.right = i3 + i;
                        rect.bottom = i4 + i2;
                    }
                } else {
                    aODManagerService.mTspRects.remove(str);
                }
                if (aODManagerService.mTspRects.size() > 0) {
                    Rect rect2 = new Rect();
                    for (Map.Entry entry : aODManagerService.mTspRects.entrySet()) {
                        rect2.union((Rect) entry.getValue());
                        Slog.i("AODManagerService", "updateAODNotiTspRectInternal union: key : " + ((String) entry.getKey()) + " rect : " + rect2);
                    }
                    int i5 = rect2.left;
                    aODManagerService.mTspX = i5;
                    int i6 = rect2.top;
                    aODManagerService.mTspY = i6;
                    int i7 = rect2.right - i5;
                    aODManagerService.mTspW = i7;
                    int i8 = rect2.bottom - i6;
                    aODManagerService.mTspH = i8;
                    SemInputDeviceManager semInputDeviceManager = aODManagerService.mInputDeviceManager;
                    if (semInputDeviceManager != null) {
                        semInputDeviceManager.setAodNotiRect(i7, i8, i5, i6);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$mwriteAODCommandInternal, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m225$$Nest$mwriteAODCommandInternal(com.android.server.aod.AODManagerService r7, java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.String r0 = "writeAODCommandInternal finally Exception : "
            java.lang.String r1 = "AODManagerService"
            java.lang.String r2 = "writeAODCommandInternal FileNotFoundException : "
            java.lang.String r3 = "writeAODCommandInternal IOException : "
            java.lang.String r4 = "writeAODCommandInternal file.exists() : "
            r7.getClass()
            checkSystemUid()
            r7 = 0
            java.io.File r5 = new java.io.File     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            r5.<init>(r8)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            boolean r8 = r5.exists()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            if (r8 == 0) goto L66
            boolean r8 = r5.canWrite()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            if (r8 != 0) goto L27
            goto L66
        L27:
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            r8.<init>(r5)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            java.lang.String r7 = "UTF-8"
            byte[] r7 = r9.getBytes(r7)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52 java.io.FileNotFoundException -> L54
            r8.write(r7)     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52 java.io.FileNotFoundException -> L54
            r8.flush()     // Catch: java.lang.Throwable -> L4f java.io.IOException -> L52 java.io.FileNotFoundException -> L54
            r8.close()     // Catch: java.lang.Exception -> L3d
            goto Lc5
        L3d:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
        L43:
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.d(r1, r7)
            goto Lc5
        L4f:
            r7 = move-exception
            goto Lc6
        L52:
            r7 = move-exception
            goto L86
        L54:
            r7 = move-exception
            goto La5
        L56:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto Lc6
        L5c:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto L86
        L61:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto La5
        L66:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            r8.<init>(r4)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            boolean r9 = r5.exists()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            r8.append(r9)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            java.lang.String r9 = " , file.canWrite() : "
            r8.append(r9)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            boolean r9 = r5.canWrite()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            r8.append(r9)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            android.util.Log.d(r1, r8)     // Catch: java.lang.Throwable -> L56 java.io.IOException -> L5c java.io.FileNotFoundException -> L61
            goto Lc5
        L86:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
            r9.<init>(r3)     // Catch: java.lang.Throwable -> L4f
            r9.append(r7)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L4f
            android.util.Log.d(r1, r9)     // Catch: java.lang.Throwable -> L4f
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L4f
            if (r8 == 0) goto Lc5
            r8.close()     // Catch: java.lang.Exception -> L9e
            goto Lc5
        L9e:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            goto L43
        La5:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
            r9.<init>(r2)     // Catch: java.lang.Throwable -> L4f
            r9.append(r7)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r9 = r9.toString()     // Catch: java.lang.Throwable -> L4f
            android.util.Log.d(r1, r9)     // Catch: java.lang.Throwable -> L4f
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L4f
            if (r8 == 0) goto Lc5
            r8.close()     // Catch: java.lang.Exception -> Lbd
            goto Lc5
        Lbd:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r0)
            goto L43
        Lc5:
            return
        Lc6:
            if (r8 == 0) goto Ldc
            r8.close()     // Catch: java.lang.Exception -> Lcc
            goto Ldc
        Lcc:
            r8 = move-exception
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r0)
            r9.append(r8)
            java.lang.String r8 = r9.toString()
            android.util.Log.d(r1, r8)
        Ldc:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.aod.AODManagerService.m225$$Nest$mwriteAODCommandInternal(com.android.server.aod.AODManagerService, java.lang.String, java.lang.String):void");
    }

    public AODManagerService(Context context) {
        super(context);
        this.mListeners = new ArrayList();
        this.mAODDozeLocks = new ArrayList();
        this.mLock = new Object();
        this.mScreenTurningOnLock = new Object();
        this.mIsAODModeEnabled = false;
        this.mIsAODTapToShow = false;
        this.mIsAODAuto = false;
        this.mIsAODShowForNewNoti = false;
        this.mAODStartTime = 0;
        this.mAODEndTime = 0;
        this.mIsFingerScreenLock = false;
        this.mIsFingerScreenOffIconAOD = false;
        this.mIsMPSMEnabled = false;
        this.mIsUPSMEnabled = false;
        this.mIsEdgeShowWhenScreenOff = false;
        this.mIsAODStartStop = false;
        new ArrayList();
        new ArrayList();
        this.mTspRects = new HashMap();
        this.mIsSingleTouchMode = false;
        this.mSpenUspLevel = -1;
        this.mAppLogger = new AODLogger("AODManagerService_App", 1000);
        this.mLiveClockLogger = new AODLogger("AODManagerService_LiveClock", 150);
        this.mTspLogger = new AODLogger("AODManagerService_TSP", 150);
        this.mScreenTurningOnRunnable = new Runnable() { // from class: com.android.server.aod.AODManagerService.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (AODManagerService.this.mScreenTurningOnLock) {
                    try {
                        int i = AODManagerService.DEBUG_TURNING_ON_DELAYED;
                        Log.d("AODManagerService", "screenTurningOn");
                        AODManager.AODChangeListener aODChangeListener = AODManagerService.this.mAODChangeListener;
                        if (aODChangeListener != null) {
                            aODChangeListener.readyToScreenTurningOn();
                            AODManagerService.this.mAODChangeListener = null;
                        } else {
                            Log.d("AODManagerService", "screenTurningOn : Do nothing, There is no Listener");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.requestedReCalToTSP = false;
        this.mContext = context;
        this.mContextForUser = context;
        this.mUserManager = UserManager.get(context);
        AODHandler aODHandler = new AODHandler(FgThread.get().getLooper(), null, true);
        this.mAODHandler = aODHandler;
        this.mLooper = new Handler().getLooper();
        HandlerThread handlerThread = new HandlerThread("AODManagerService.LogThread", 10);
        handlerThread.start();
        this.mAODLogHandler = new AnonymousClass2(this, handlerThread.getLooper(), 0);
        this.mInputDeviceManager = (SemInputDeviceManager) context.getSystemService("SemInputDeviceManagerService");
        AODSettingHelper aODSettingHelper = new AODSettingHelper();
        aODSettingHelper.mAODDefaultSetting = 1;
        ContentResolver contentResolver = context.getContentResolver();
        aODSettingHelper.mResolver = contentResolver;
        int intForUser = Settings.System.getIntForUser(contentResolver, "aod_mode", -1, -2);
        if (!SUPPORT_AOD) {
            Settings.System.putIntForUser(contentResolver, "aod_mode", 0, -2);
        } else if (intForUser == -1) {
            int i = (AODConfig.isFactoryBinary || "OFF".contains(AODConfig.AOD_MODE_DEFAULT_VALUE)) ? 0 : 1;
            aODSettingHelper.mAODDefaultSetting = i;
            Settings.System.putIntForUser(contentResolver, "aod_mode", i, -2);
        }
        this.mAODSettingHelper = aODSettingHelper;
        SettingsObserver settingsObserver = new SettingsObserver(aODHandler);
        BatteryStatsService.getService();
        ContentResolver contentResolver2 = context.getContentResolver();
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_mode"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_show_state"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_tap_to_show_mode"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_display_mode_auto"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_show_for_new_noti"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_mode_start_time"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("aod_mode_end_time"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.Secure.getUriFor("fingerprint_screen_lock"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.Secure.getUriFor("fingerprint_screen_off_icon_aod"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("edge_lighting_show_condition"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.Secure.getUriFor("doze_always_on"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.Global.getUriFor("low_power"), false, settingsObserver, -1);
        contentResolver2.registerContentObserver(Settings.System.getUriFor("ultra_powersaving_mode"), false, settingsObserver, -1);
        addAODTspLog("observe");
        updateSettings();
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.aod.AODManagerService.3
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i2) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i2) {
                Display display;
                DisplayManager displayManager2 = AODManagerService.this.mDisplayManager;
                if (displayManager2 == null || (display = displayManager2.getDisplay(i2)) == null || i2 != 0) {
                    return;
                }
                AODManagerService.this.updateDefaultDisplayState(display.getState());
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i2) {
            }
        };
        if (displayManager != null) {
            displayManager.registerDisplayListener(displayListener, null);
            Display display = displayManager.getDisplay(0);
            if (display != null) {
                updateDefaultDisplayState(display.getState());
            }
        }
        Log.d("AODManagerService", "AOD_SCREEN_TURNING_ON_TIMEOUT : 0");
    }

    public static void checkSystemUid() {
        if (Binder.getCallingUid() == 1000) {
            return;
        }
        throw new SecurityException("Disallowed call for uid " + Binder.getCallingUid());
    }

    public static String displayStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? String.valueOf(i) : "DOZE_SUSPEND" : "DOZE" : "ON" : "OFF" : "UNKNOWN";
    }

    public static native int setActiveImage(int i, byte[] bArr);

    public static native int setAnalogClockInfo(int i, int i2, int i3, int i4);

    public static native int setAnalogClockInfoV4(int i, int i2, int i3, int i4, int i5, int i6);

    public static native int setCurrentTime(int i, int i2, int i3, int i4, int i5, int i6);

    public static native int setDigitalClockInfo(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20);

    public static native int setDigitalClockInfoV4(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16);

    public static native int setLiveClockCommand(int i, int i2, int i3, int[] iArr);

    public static native int setLiveClockImage(int i, int i2, byte[] bArr);

    public static native int setLiveClockInfo(int i, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8);

    public static native void setLiveClockNeedle(byte[] bArr);

    public static native int setSelfGridInfo(int i, int i2, int i3, int i4, int i5);

    public static native int setSelfIconInfo(int i, int i2, int i3, int i4, int i5, int i6);

    public static native int setSelfPartialHLPMScan(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    public static native int turnOffSelfMove();

    public static native int turnOnSelfMove();

    public final void addAODTspLog(String str) {
        Log.d("AODManagerService", str);
        this.mTspLogger.add(str, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0030, code lost:
    
        if (r1 == null) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getAODClockType() {
        /*
            r8 = this;
            r0 = -1
            r1 = 0
            android.content.Context r8 = r8.mContextForUser     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            android.net.Uri r3 = com.android.server.aod.AODManagerService.AOD_SETTING_CLOCK_TYPE_URI_PARSED     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            r6 = 0
            r7 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            if (r1 == 0) goto L27
            int r8 = r1.getCount()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            if (r8 <= 0) goto L27
            r1.moveToFirst()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            r8 = 0
            int r0 = r1.getInt(r8)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L25
            goto L27
        L23:
            r8 = move-exception
            goto L34
        L25:
            r8 = move-exception
            goto L2d
        L27:
            if (r1 == 0) goto L33
        L29:
            r1.close()
            goto L33
        L2d:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L23
            if (r1 == 0) goto L33
            goto L29
        L33:
            return r0
        L34:
            if (r1 == 0) goto L39
            r1.close()
        L39:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.aod.AODManagerService.getAODClockType():int");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 600 && this.mInputDeviceManager == null) {
            SemInputDeviceManager semInputDeviceManager = (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
            this.mInputDeviceManager = semInputDeviceManager;
            if (semInputDeviceManager == null) {
                Slog.e("AODManagerService", "onBootPhase() mInputDeviceManager is null");
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        int packageUid = ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 1048576L, 0);
        this.mSystemUiUid = packageUid;
        if (packageUid <= 0) {
            Slog.wtf("AODManagerService", "SysUI package not found!");
        }
        publishBinderService("AODManagerService", new BinderService());
        publishLocalService(AODManagerInternal.class, new LocalService());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        addAODTspLog("onUserSwitching from=" + targetUser + " to=" + targetUser2);
        this.mContextForUser = this.mContext.createContextAsUser(targetUser2.getUserHandle(), 0);
        if (SUPPORT_AOD_LIVE_CLOCK) {
            this.mIsAODAnalogLiveClock = false;
            if (this.mUserManager.isUserUnlocked(targetUser2.getUserIdentifier())) {
                registerAODClockContentObserver();
            }
        }
        updateSettings();
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocked(SystemService.TargetUser targetUser) {
        Log.i("AODManagerService", "onUserUnlocked: user=" + targetUser);
        if (SUPPORT_AOD_LIVE_CLOCK) {
            registerAODClockContentObserver();
        }
    }

    public final void registerAODClockContentObserver() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mContentObserver != null) {
            Log.i("AODManagerService", "registerAODClockContentObserver unregister before ContentObserver");
            contentResolver.unregisterContentObserver(this.mContentObserver);
            this.mContentObserver = null;
        }
        this.mIsAODAnalogLiveClock = getAODClockType() / 10000 == 1;
        Log.i("AODManagerService", "registerAODClockContentObserver: mIsAODAnalogLiveClock : " + this.mIsAODAnalogLiveClock);
        ?? r1 = new ContentObserver(this.mAODHandler) { // from class: com.android.server.aod.AODManagerService.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                if (uri != null && AODManagerService.AOD_SETTING_CLOCK_TYPE_URI_PARSED.equals(uri)) {
                    AODManagerService aODManagerService = AODManagerService.this;
                    aODManagerService.mIsAODAnalogLiveClock = aODManagerService.getAODClockType() / 10000 == 1;
                    FlashNotificationsController$$ExternalSyntheticOutline0.m("AODManagerService", new StringBuilder("onChange: mIsAODAnalogLiveClock : "), AODManagerService.this.mIsAODAnalogLiveClock);
                }
            }
        };
        this.mContentObserver = r1;
        contentResolver.registerContentObserver(AOD_SETTING_CLOCK_TYPE_URI_PARSED, false, r1, -2);
    }

    public final void updateAODTspRectInternal(int i, int i2, int i3, int i4, String str) {
        checkSystemUid();
        synchronized (this.mTspRects) {
            try {
                if (i3 >= 0 || i4 >= 0 || i >= 0 || i2 >= 0) {
                    Rect rect = (Rect) this.mTspRects.get(str);
                    if (rect == null) {
                        this.mTspRects.put(str, new Rect(i3, i4, i + i3, i2 + i4));
                    } else {
                        rect.left = i3;
                        rect.top = i4;
                        rect.right = i3 + i;
                        rect.bottom = i4 + i2;
                    }
                } else {
                    this.mTspRects.remove(str);
                }
                if (this.mTspRects.size() > 0) {
                    Rect rect2 = new Rect();
                    for (Map.Entry entry : this.mTspRects.entrySet()) {
                        rect2.union((Rect) entry.getValue());
                        Slog.i("AODManagerService", "updateAODTspRectInternal union: key : " + ((String) entry.getKey()) + " rect : " + rect2);
                    }
                    int i5 = rect2.left;
                    this.mTspX = i5;
                    int i6 = rect2.top;
                    this.mTspY = i6;
                    int i7 = rect2.right - i5;
                    this.mTspW = i7;
                    int i8 = rect2.bottom - i6;
                    this.mTspH = i8;
                    SemInputDeviceManager semInputDeviceManager = this.mInputDeviceManager;
                    if (semInputDeviceManager != null) {
                        semInputDeviceManager.setAodRect(i7, i8, i5, i6);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateAODTspState$1() {
        StringBuilder sb;
        if (FactoryTest.isFactoryBinary()) {
            addAODTspLog("updateAODTspState: [Factory Binary Mode] NOT update TSP state");
            return;
        }
        if (!SUPPORT_AOD) {
            addAODTspLog("updateAODTspState: [Not support AOD model] NOT update TSP state");
            return;
        }
        addAODTspLog("updateAODTspState: mIsAODModeEnabled=" + this.mIsAODModeEnabled + ", mIsEdgeShowEnabled=" + this.mIsEdgeShowWhenScreenOff);
        boolean z = true;
        int i = (this.mIsAODModeEnabled || this.mIsEdgeShowWhenScreenOff) ? 1 : 0;
        checkSystemUid();
        boolean z2 = AODConfig.isAODTouchDisabled;
        if (z2) {
            addAODTspLog("updateAODTspState skip -- AOD TSP");
        } else {
            if (i == 0) {
                synchronized (this.mTspRects) {
                    try {
                        Rect rect = (Rect) this.mTspRects.get("com.samsung.android.app.aodservice");
                        if (rect != null && !rect.isEmpty()) {
                            updateAODTspRectInternal(0, 0, 0, 0, "com.samsung.android.app.aodservice");
                        }
                    } finally {
                    }
                }
            }
            SemInputDeviceManager semInputDeviceManager = this.mInputDeviceManager;
            if (semInputDeviceManager != null) {
                semInputDeviceManager.setAodEnable(i);
            }
        }
        if (this.mSpenUspLevel == -1) {
            this.mSpenUspLevel = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", -1);
            GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("updateAODTspState: mSpenUspLevel = "), this.mSpenUspLevel, "AODManagerService");
        }
        if (this.mSpenUspLevel < GREAT_SPEN_USP_LEVEL) {
            return;
        }
        addAODTspLog("updateAODWacomState");
        if (!this.mIsAODModeEnabled && !this.mIsEdgeShowWhenScreenOff) {
            z = false;
        }
        checkSystemUid();
        if (z2) {
            addAODTspLog("updateAODWacomState skip -- AOD Wacom");
            return;
        }
        FileOutputStream fileOutputStream = null;
        fileOutputStream = null;
        fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        FileOutputStream fileOutputStream3 = null;
        try {
            try {
                File file = new File("/sys/class/sec/sec_epen/aod_enable");
                if (file.exists() && file.canWrite()) {
                    FileOutputStream fileOutputStream4 = new FileOutputStream(file);
                    try {
                        ?? r3 = "UTF-8";
                        fileOutputStream4.write((z ? "1" : "0").getBytes("UTF-8"));
                        fileOutputStream4.flush();
                        try {
                            fileOutputStream4.close();
                            fileOutputStream = r3;
                        } catch (Exception e) {
                            e = e;
                            sb = new StringBuilder("updateAODWacomState -- ");
                            sb.append(e.toString());
                            addAODTspLog(sb.toString());
                        }
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        fileOutputStream2 = fileOutputStream4;
                        e.printStackTrace();
                        addAODTspLog("updateAODWacomState -- FileNotFoundException");
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                                fileOutputStream = fileOutputStream2;
                            } catch (Exception e3) {
                                e = e3;
                                sb = new StringBuilder("updateAODWacomState -- ");
                                sb.append(e.toString());
                                addAODTspLog(sb.toString());
                            }
                        }
                    } catch (IOException e4) {
                        e = e4;
                        fileOutputStream3 = fileOutputStream4;
                        e.printStackTrace();
                        addAODTspLog("updateAODWacomState -- IOException");
                        fileOutputStream = fileOutputStream3;
                        if (fileOutputStream3 != null) {
                            try {
                                fileOutputStream3.close();
                                fileOutputStream = fileOutputStream3;
                            } catch (Exception e5) {
                                e = e5;
                                sb = new StringBuilder("updateAODWacomState -- ");
                                sb.append(e.toString());
                                addAODTspLog(sb.toString());
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream4;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e6) {
                                addAODTspLog("updateAODWacomState -- " + e6.toString());
                            }
                        }
                        throw th;
                    }
                }
            } catch (FileNotFoundException e7) {
                e = e7;
            } catch (IOException e8) {
                e = e8;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void updateDefaultDisplayState(int i) {
        int i2 = this.mDefaultDisplayState;
        this.mDefaultDisplayState = i;
        synchronized (this.mAODDozeLocks) {
            try {
                if (i == 1) {
                    Iterator it = this.mAODDozeLocks.iterator();
                    while (it.hasNext()) {
                        this.mAODDozeLocks.remove((AODDozeLock) it.next());
                    }
                } else if (i == 2) {
                    Log.d("AODManagerService", "requestReCalToTSP IsSingleTouchMode = " + this.mIsSingleTouchMode + "requestedReCalToTSP = " + this.requestedReCalToTSP + " / previousDisplayState = " + displayStateToString(i2));
                    if (this.requestedReCalToTSP && (i2 == 3 || i2 == 4)) {
                        SemInputDeviceManager semInputDeviceManager = this.mInputDeviceManager;
                        if (semInputDeviceManager != null) {
                            semInputDeviceManager.setSyncChanged(1);
                        }
                        this.requestedReCalToTSP = false;
                    }
                    if (i2 != i && Settings.System.getIntForUser(this.mAODSettingHelper.mResolver, "aod_show_state", 0, -2) == 1) {
                        Settings.System.putIntForUser(this.mAODSettingHelper.mResolver, "aod_show_state", 0, -2);
                        Log.d("AODManagerService", "updateDefaultDisplayState clear aod_show_state, previousDisplayState=" + displayStateToString(i2));
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x013f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0140, code lost:
    
        r19 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x013d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0142, code lost:
    
        android.util.Log.d("AODManagerService", "updateAODChargingMode : doze_always_on doesn't exist. " + r0.toString());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSettings() {
        /*
            Method dump skipped, instructions count: 755
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.aod.AODManagerService.updateSettings():void");
    }
}
