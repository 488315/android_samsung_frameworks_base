package com.android.server.chimera;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.camera2.CameraManager;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.LocalServices;
import com.android.server.chimera.psitracker.PSITracker;
import com.android.server.wm.ActivityMetricsLaunchObserver;
import com.android.server.wm.ActivityMetricsLaunchObserverRegistry;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemEventListener extends BroadcastReceiver {
    public static boolean mFirstTriggeredAfterBooting;
    public final AppLaunchIntent mAppLaunchObserver;
    public final SystemEventHandler mHandler;
    public final SystemEventListener$$ExternalSyntheticLambda0 mProcessObserver;
    public final SystemRepository mSystemRepository;
    public final List mLmkdEventListeners = new ArrayList();
    public final List mHomeLaunchListeners = new ArrayList();
    public final List mAppLaunchListeners = new ArrayList();
    public final List mCarModeChangeListeners = new ArrayList();
    public final List mMediaScanFinishedListeners = new ArrayList();
    public final List mAppLaunchIntentListeners = new ArrayList();
    public final List mDeviceIdleListeners = new ArrayList();
    public final List mCameraStateListeners = new ArrayList();
    public final List mTimeOrTimeZoneChangedListeners = new ArrayList();
    public final List mOneHourTimerListeners = new ArrayList();
    public final List mQuotaListeners = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AlwaysRunningQuotaExceedListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppLaunchIntent extends ActivityMetricsLaunchObserver {
        public AppLaunchIntent() {}

        @Override // com.android.server.wm.ActivityMetricsLaunchObserver
        public final void onIntentStarted(Intent intent, long j) {
            String str;
            if (intent == null) {
                return;
            }
            ComponentName component = intent.getComponent();
            SystemEventListener systemEventListener = SystemEventListener.this;
            if (component != null) {
                str = intent.getComponent().getPackageName();
            } else {
                if (intent.getPackage() == null) {
                    systemEventListener.mSystemRepository.getClass();
                    SystemRepository.log(
                            "SystemEventListener", "Not an effective intent: " + intent);
                    return;
                }
                str = intent.getPackage();
            }
            systemEventListener.mSystemRepository.getClass();
            if (str.contains(SystemRepository.getCurrentHomePackageName())
                    || str.contains("com.samsung.android.permissioncontroller")) {
                return;
            }
            Message obtain = Message.obtain(systemEventListener.mHandler, 11);
            obtain.obj = str;
            SystemRepository systemRepository = systemEventListener.mSystemRepository;
            String concat = "appLaunchIntent package name is: ".concat(str);
            systemRepository.getClass();
            SystemRepository.log("SystemEventListener", concat);
            systemEventListener.mHandler.sendMessage(obtain);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AppLaunchIntentListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface CameraStateListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface CarModeChangeListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DeviceIdleListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface HomeLaunchListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface LmkdEventListener {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LmkdEventServerThread extends Thread {
        public Handler mHandler;
        public LocalServerSocket mServerSocket;
        public LocalSocket mSocket;
        public SystemRepository mSystemRepository;

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                this.mServerSocket = new LocalServerSocket("chimera");
            } catch (Exception unused) {
                this.mSystemRepository.getClass();
                SystemRepository.log("SystemEventListener", "Failed to execute socket service.");
            }
            if (this.mServerSocket == null) {
                return;
            }
            this.mSystemRepository.getClass();
            SystemRepository.log("SystemEventListener", "Waiting Client connected...");
            try {
                LocalSocket accept = this.mServerSocket.accept();
                this.mSocket = accept;
                accept.setReceiveBufferSize(256);
                this.mSocket.setSendBufferSize(256);
                SystemRepository systemRepository = this.mSystemRepository;
                String str = "There is a client is accepted: " + this.mSocket.toString();
                systemRepository.getClass();
                SystemRepository.log("SystemEventListener", str);
                BufferedReader bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(this.mSocket.getInputStream()), 256);
                while (true) {
                    String[] split = bufferedReader.readLine().split(":");
                    if (split != null && split.length > 1) {
                        if (split[0] != null && split[1] != null) {
                            this.mHandler.sendMessage(
                                    this.mHandler.obtainMessage(
                                            4,
                                            Integer.parseInt(split[2].trim()),
                                            Integer.parseInt(split[3].trim()),
                                            new Integer(Integer.parseInt(split[4].trim()))));
                        }
                        Log.e("SystemEventListener", "Received lmkd data error");
                    }
                }
            } catch (Exception e) {
                SystemRepository systemRepository2 = this.mSystemRepository;
                String str2 = "Socket Exception: " + e.toString();
                systemRepository2.getClass();
                SystemRepository.log("SystemEventListener", str2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SystemEventHandler extends Handler {
        public SystemEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                int i = message.what;
                SystemEventListener systemEventListener = SystemEventListener.this;
                switch (i) {
                    case 4:
                        Iterator it =
                                ((ArrayList) systemEventListener.mLmkdEventListeners).iterator();
                        while (it.hasNext()) {
                            ((PolicyHandler) ((LmkdEventListener) it.next()))
                                    .onLmkdEventTriggered(
                                            message.arg1, ((Integer) message.obj).intValue());
                        }
                        return;
                    case 5:
                        Iterator it2 =
                                ((ArrayList) systemEventListener.mHomeLaunchListeners).iterator();
                        while (it2.hasNext()) {
                            ((PolicyHandler) ((HomeLaunchListener) it2.next())).onHomeLaunched();
                        }
                        return;
                    case 6:
                        Iterator it3 =
                                ((ArrayList) systemEventListener.mCarModeChangeListeners)
                                        .iterator();
                        while (it3.hasNext()) {
                            ((PolicyHandler) ((CarModeChangeListener) it3.next())).mIsCarMode =
                                    true;
                        }
                        return;
                    case 7:
                        Iterator it4 =
                                ((ArrayList) systemEventListener.mCarModeChangeListeners)
                                        .iterator();
                        while (it4.hasNext()) {
                            ((PolicyHandler) ((CarModeChangeListener) it4.next())).mIsCarMode =
                                    false;
                        }
                        return;
                    case 8:
                        Iterator it5 =
                                ((ArrayList) systemEventListener.mMediaScanFinishedListeners)
                                        .iterator();
                        while (it5.hasNext()) {
                            ((ChimeraManager) it5.next()).onMediaScanFinished();
                        }
                        return;
                    case 9:
                        Iterator it6 =
                                ((ArrayList) systemEventListener.mAppLaunchListeners).iterator();
                        if (it6.hasNext()) {
                            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(it6.next());
                            throw null;
                        }
                        return;
                    case 10:
                        Iterator it7 =
                                ((ArrayList) systemEventListener.mDeviceIdleListeners).iterator();
                        while (it7.hasNext()) {
                            ((PolicyHandler) ((DeviceIdleListener) it7.next())).onDeviceIdle();
                        }
                        return;
                    case 11:
                        Iterator it8 =
                                ((ArrayList) systemEventListener.mAppLaunchIntentListeners)
                                        .iterator();
                        while (it8.hasNext()) {
                            ((PolicyHandler) ((AppLaunchIntentListener) it8.next()))
                                    .onAppLaunchIntent((String) message.obj);
                        }
                        return;
                    case 12:
                        Iterator it9 =
                                ((ArrayList) systemEventListener.mCameraStateListeners).iterator();
                        while (it9.hasNext()) {
                            ((PolicyHandler) ((CameraStateListener) it9.next())).onCameraOpen();
                        }
                        return;
                    case 13:
                        Iterator it10 =
                                ((ArrayList) systemEventListener.mCameraStateListeners).iterator();
                        while (it10.hasNext()) {
                            ((PolicyHandler) ((CameraStateListener) it10.next())).onCameraClose();
                        }
                        return;
                    case 14:
                        systemEventListener.mSystemRepository.getClass();
                        SystemRepository.log("SystemEventListener", "MSG_ONE_HOUR_TIMER");
                        Iterator it11 =
                                ((ArrayList) systemEventListener.mOneHourTimerListeners).iterator();
                        while (it11.hasNext()) {
                            ((AbnormalFgsDetector) it11.next()).onOneHourTimer();
                        }
                        systemEventListener.startOneHourTimer();
                        return;
                    case 15:
                        Iterator it12 =
                                ((ArrayList) systemEventListener.mTimeOrTimeZoneChangedListeners)
                                        .iterator();
                        while (it12.hasNext()) {
                            PSITracker pSITracker = (PSITracker) it12.next();
                            String str = (String) message.obj;
                            pSITracker.getClass();
                            pSITracker.mSystemRepository.getClass();
                            SystemRepository.log(
                                    "PSITracker", "onTimeOrTimeZoneChanged() action: " + str);
                            pSITracker.scheduleAvailMem240PeriodRecord("TIME_CHANGED");
                        }
                        return;
                    case 16:
                        Iterator it13 =
                                ((ArrayList) systemEventListener.mQuotaListeners).iterator();
                        while (it13.hasNext()) {
                            ((PolicyHandler) ((AlwaysRunningQuotaExceedListener) it13.next()))
                                    .onQuotaKill(message.arg1 == 1);
                        }
                        return;
                    default:
                        return;
                }
            } catch (RuntimeException e) {
                Log.e(
                        SystemRepository.convertToChimeraTag("SystemEventListener"),
                        "Handler execute with exception " + e.getMessage());
            }
        }
    }

    public SystemEventListener(Context context, Looper looper, SystemRepository systemRepository) {
        SystemRepository.ChimeraProcessObserver chimeraProcessObserver =
                new SystemRepository
                        .ChimeraProcessObserver() { // from class:
                                                    // com.android.server.chimera.SystemEventListener$$ExternalSyntheticLambda0
                    @Override // com.android.server.chimera.SystemRepository.ChimeraProcessObserver
                    public final void onForegroundActivitiesChanged(
                            int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
                        SystemEventListener systemEventListener = SystemEventListener.this;
                        if (!z) {
                            systemEventListener.getClass();
                            return;
                        }
                        if (z2) {
                            if (systemEventListener.mHandler.hasMessages(5)) {
                                return;
                            }
                            systemEventListener.mHandler.sendMessageDelayed(
                                    Message.obtain(
                                            systemEventListener.mHandler, 5, Integer.valueOf(i2)),
                                    2000L);
                            return;
                        }
                        systemEventListener.mHandler.removeMessages(5);
                        Message obtain = Message.obtain(systemEventListener.mHandler, 9);
                        obtain.obj = strArr[0];
                        systemEventListener.mHandler.sendMessage(obtain);
                    }
                };
        this.mAppLaunchObserver = new AppLaunchIntent();
        this.mSystemRepository = systemRepository;
        SystemEventHandler systemEventHandler = new SystemEventHandler(looper);
        this.mHandler = systemEventHandler;
        systemRepository.mSystemEventListenerHandler = systemEventHandler;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.app.action.ENTER_CAR_MODE");
        intentFilter.addAction("android.app.action.EXIT_CAR_MODE");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        context.registerReceiver(this, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        intentFilter2.addDataScheme("file");
        context.registerReceiver(this, intentFilter2, 2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        context.registerReceiver(this, intentFilter3);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.intent.action.TIME_SET");
        intentFilter4.addAction("android.intent.action.TIMEZONE_CHANGED");
        context.registerReceiver(this, intentFilter4);
        LmkdEventServerThread lmkdEventServerThread = new LmkdEventServerThread();
        lmkdEventServerThread.mSystemRepository = systemRepository;
        lmkdEventServerThread.mHandler = systemEventHandler;
        lmkdEventServerThread.start();
        systemRepository.registerProcessObserver(chimeraProcessObserver);
    }

    public final void addCameraDeviceStateCallback(Context context) {
        ((CameraManager) context.getSystemService("camera"))
                .registerSemCameraDeviceStateCallback(
                        this.mSystemRepository.mCameraDeviceStateCallback, this.mHandler);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        String action = intent.getAction();
        this.mSystemRepository.getClass();
        SystemRepository.log("SystemEventListener", "onReceive() - action: " + action);
        action.getClass();
        switch (action) {
            case "android.intent.action.SCREEN_OFF":
                this.mHandler.sendEmptyMessageDelayed(16, 2000L);
                break;
            case "android.intent.action.SCREEN_ON":
                this.mHandler.removeMessages(16);
                break;
            case "android.intent.action.MEDIA_SCANNER_FINISHED":
                if (!mFirstTriggeredAfterBooting) {
                    this.mHandler.sendEmptyMessage(8);
                    mFirstTriggeredAfterBooting = true;
                    break;
                }
                break;
            case "android.app.action.ENTER_CAR_MODE":
                this.mHandler.sendEmptyMessage(6);
                break;
            case "android.app.action.EXIT_CAR_MODE":
                this.mHandler.sendEmptyMessage(7);
                break;
            case "android.intent.action.TIMEZONE_CHANGED":
            case "android.intent.action.TIME_SET":
                Message obtainMessage = this.mHandler.obtainMessage(15);
                obtainMessage.obj = intent.getAction();
                this.mHandler.sendMessage(obtainMessage);
                break;
            case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                if (powerManager != null) {
                    if (!powerManager.isDeviceIdleMode()) {
                        this.mSystemRepository.getClass();
                        SystemRepository.log("SystemEventListener", "Device idle is false ! ");
                        break;
                    } else {
                        this.mSystemRepository.getClass();
                        SystemRepository.log("SystemEventListener", "Device idle is true ! ");
                        this.mHandler.sendEmptyMessage(10);
                        break;
                    }
                }
                break;
        }
    }

    public ActivityMetricsLaunchObserverRegistry provideLaunchObserverRegistry() {
        return ((ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class))
                .getLaunchObserverRegistry();
    }

    public final void startOneHourTimer() {
        this.mSystemRepository.getClass();
        SystemRepository.log("SystemEventListener", "startOneHourTimer");
        this.mHandler.sendMessageDelayed(
                Message.obtain(this.mHandler, 14), Duration.ofHours(1L).toMillis());
    }
}
