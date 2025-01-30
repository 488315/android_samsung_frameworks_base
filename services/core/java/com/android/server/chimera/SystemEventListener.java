package com.android.server.chimera;

import android.content.BroadcastReceiver;
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
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.chimera.ChimeraQuotaMonitor;
import com.android.server.chimera.SystemRepository;
import com.android.server.wm.ActivityMetricsLaunchObserver;
import com.android.server.wm.ActivityMetricsLaunchObserverRegistry;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class SystemEventListener extends BroadcastReceiver {
    public static boolean mFirstTriggeredAfterBooting = false;
    public SystemEventHandler mHandler;
    public final SystemRepository mSystemRepository;
    public final List mBottleNeckHintListeners = new ArrayList();
    public final List mPmmCriticalListeners = new ArrayList();
    public final List mPmmStateChangeListeners = new ArrayList();
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
    public final SystemRepository.ChimeraProcessObserver mProcessObserver = new SystemRepository.ChimeraProcessObserver() { // from class: com.android.server.chimera.SystemEventListener$$ExternalSyntheticLambda0
        @Override // com.android.server.chimera.SystemRepository.ChimeraProcessObserver
        public final void onForegroundActivitiesChanged(int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
            SystemEventListener.this.lambda$new$0(i, i2, z, i3, strArr, z2);
        }
    };
    public AppLaunchIntent mAppLaunchObserver = new AppLaunchIntent();

    public interface AlwaysRunningQuotaExceedListener {
        void onQuotaExceed(ChimeraQuotaMonitor.QuotaReclaimTarget quotaReclaimTarget);
    }

    public interface AppLaunchIntentListener {
        void onAppLaunchIntent(String str);
    }

    public interface BottleNeckHintListener {
        void onBottleNeckHintTriggered();
    }

    public interface CameraStateListener {
        void onCameraClose();

        void onCameraOpen();
    }

    public interface CarModeChangeListener {
        void onCarModeChanged(boolean z);
    }

    public interface DeviceIdleListener {
        void onDeviceIdle();
    }

    public interface HomeLaunchListener {
        void onHomeLaunched();
    }

    public interface LmkdEventListener {
        void onLmkdEventTriggered(int i, int i2, int i3);
    }

    public interface MediaScanFinishedListener {
        void onMediaScanFinished();
    }

    public interface OneHourTimerListener {
        void onOneHourTimer();
    }

    public interface PmmCriticalListener {
        void onPmmCriticalTriggered();
    }

    public interface PmmStateChangeListener {
        void onPmmStateChanged(int i);
    }

    public interface TimeOrTimeZoneChangedListener {
        void onTimeOrTimeZoneChanged(String str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        if (intent == null || intent.getAction() == null) {
            return;
        }
        String action = intent.getAction();
        this.mSystemRepository.log("SystemEventListener", "onReceive() - action: " + action);
        action.hashCode();
        switch (action.hashCode()) {
            case -1142424621:
                if (action.equals("android.intent.action.MEDIA_SCANNER_FINISHED")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -671746826:
                if (action.equals("com.samsung.BOTTLENECK_HINT_FOR_CHIMERA")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -340910879:
                if (action.equals("android.app.action.ENTER_CAR_MODE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -332007453:
                if (action.equals("android.app.action.EXIT_CAR_MODE")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 37241134:
                if (action.equals("com.samsung.KPM_STATE_CHANGED")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 502473491:
                if (action.equals("android.intent.action.TIMEZONE_CHANGED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 505380757:
                if (action.equals("android.intent.action.TIME_SET")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 870701415:
                if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1790399016:
                if (action.equals("com.samsung.PMM_CRITICAL_TRIGGER")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (!mFirstTriggeredAfterBooting) {
                    this.mHandler.sendEmptyMessage(8);
                    mFirstTriggeredAfterBooting = true;
                    break;
                }
                break;
            case 1:
                this.mHandler.sendEmptyMessage(2);
                break;
            case 2:
                this.mHandler.sendEmptyMessage(6);
                break;
            case 3:
                this.mHandler.sendEmptyMessage(7);
                break;
            case 4:
                int intExtra = intent.getIntExtra("kpm_level", -1);
                if (intExtra != -1) {
                    this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(intExtra)));
                    break;
                }
                break;
            case 5:
            case 6:
                Message obtainMessage = this.mHandler.obtainMessage(15);
                obtainMessage.obj = intent.getAction();
                this.mHandler.sendMessage(obtainMessage);
                break;
            case 7:
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                if (powerManager != null) {
                    if (powerManager.isDeviceIdleMode()) {
                        this.mSystemRepository.log("SystemEventListener", "Device idle is true ! ");
                        this.mHandler.sendEmptyMessage(10);
                        break;
                    } else {
                        this.mSystemRepository.log("SystemEventListener", "Device idle is false ! ");
                        break;
                    }
                }
                break;
            case '\b':
                this.mHandler.sendEmptyMessage(1);
                break;
        }
    }

    public final void registerBroadcastReceivers(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.BOTTLENECK_HINT_FOR_CHIMERA");
        intentFilter.addAction("com.samsung.KPM_STATE_CHANGED");
        intentFilter.addAction("android.app.action.ENTER_CAR_MODE");
        intentFilter.addAction("android.app.action.EXIT_CAR_MODE");
        context.registerReceiver(this, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.PMM_CRITICAL_TRIGGER");
        context.registerReceiver(this, intentFilter2, "com.samsung.android.permission.BROADCAST_PMM_CRITICAL_TRIGGER", null);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        intentFilter3.addDataScheme("file");
        context.registerReceiver(this, intentFilter3);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        context.registerReceiver(this, intentFilter4);
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("android.intent.action.TIME_SET");
        intentFilter5.addAction("android.intent.action.TIMEZONE_CHANGED");
        context.registerReceiver(this, intentFilter5);
    }

    public void addCarModeChangeListener(CarModeChangeListener carModeChangeListener) {
        this.mCarModeChangeListeners.add(carModeChangeListener);
    }

    public SystemEventListener(Context context, Looper looper, SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
        SystemEventHandler systemEventHandler = new SystemEventHandler(looper);
        this.mHandler = systemEventHandler;
        systemRepository.setSystemEventListenerHandler(systemEventHandler);
        registerBroadcastReceivers(context);
        createSocketServer();
        registerProcessObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
        if (z) {
            if (z2) {
                if (this.mHandler.hasMessages(5)) {
                    return;
                }
                this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 5, Integer.valueOf(i2)), 2000L);
                return;
            }
            this.mHandler.removeMessages(5);
            Message obtain = Message.obtain(this.mHandler, 9);
            obtain.obj = strArr[0];
            this.mHandler.sendMessage(obtain);
        }
    }

    public final void registerProcessObserver() {
        this.mSystemRepository.registerProcessObserver(this.mProcessObserver);
    }

    public final void createSocketServer() {
        new LmkdEventServerThread(this.mSystemRepository, this.mHandler).start();
    }

    public class AppLaunchIntent extends ActivityMetricsLaunchObserver {
        public AppLaunchIntent() {
        }

        @Override // com.android.server.wm.ActivityMetricsLaunchObserver
        public void onIntentStarted(Intent intent, long j) {
            String str;
            if (intent.getComponent() != null) {
                str = intent.getComponent().getPackageName();
            } else if (intent.getPackage() != null) {
                str = intent.getPackage();
            } else {
                SystemEventListener.this.mSystemRepository.log("SystemEventListener", "Not an effective intent: " + intent);
                return;
            }
            if (str.contains(SystemEventListener.this.mSystemRepository.getCurrentHomePackageName()) || str.contains("com.samsung.android.permissioncontroller")) {
                return;
            }
            Message obtain = Message.obtain(SystemEventListener.this.mHandler, 11);
            obtain.obj = str;
            SystemEventListener.this.mSystemRepository.log("SystemEventListener", "appLaunchIntent package name is: " + str);
            SystemEventListener.this.mHandler.sendMessage(obtain);
        }
    }

    public ActivityMetricsLaunchObserverRegistry provideLaunchObserverRegistry() {
        return ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getLaunchObserverRegistry();
    }

    public void removeCarModeChangeListener(CarModeChangeListener carModeChangeListener) {
        this.mCarModeChangeListeners.remove(carModeChangeListener);
    }

    public void addBottleNeckHintListener(BottleNeckHintListener bottleNeckHintListener) {
        this.mBottleNeckHintListeners.add(bottleNeckHintListener);
    }

    public void addPmmCriticalListener(PmmCriticalListener pmmCriticalListener) {
        this.mPmmCriticalListeners.add(pmmCriticalListener);
    }

    public void addPmmStateChangeListener(PmmStateChangeListener pmmStateChangeListener) {
        this.mPmmStateChangeListeners.add(pmmStateChangeListener);
    }

    public void addLmkdEventListener(LmkdEventListener lmkdEventListener) {
        this.mLmkdEventListeners.add(lmkdEventListener);
    }

    public void addHomeLaunchListener(HomeLaunchListener homeLaunchListener) {
        this.mHomeLaunchListeners.add(homeLaunchListener);
    }

    public void addMediaScanFinishedListener(MediaScanFinishedListener mediaScanFinishedListener) {
        this.mMediaScanFinishedListeners.add(mediaScanFinishedListener);
    }

    public void addAppLaunchIntentListener(AppLaunchIntentListener appLaunchIntentListener) {
        this.mAppLaunchIntentListeners.add(appLaunchIntentListener);
    }

    public void addDeviceIdleListener(DeviceIdleListener deviceIdleListener) {
        this.mDeviceIdleListeners.add(deviceIdleListener);
    }

    public void addCameraDeviceStateCallback(Context context) {
        ((CameraManager) context.getSystemService("camera")).registerSemCameraDeviceStateCallback(this.mSystemRepository.getCameraDeviceStateCallback(), this.mHandler);
    }

    public void addAppLaunchIntent() {
        provideLaunchObserverRegistry().registerLaunchObserver(this.mAppLaunchObserver);
    }

    public void addCameraStateListener(CameraStateListener cameraStateListener) {
        this.mCameraStateListeners.add(cameraStateListener);
    }

    public void addOneHourTimerListener(OneHourTimerListener oneHourTimerListener) {
        if (this.mOneHourTimerListeners.size() == 0) {
            startOneHourTimer();
        }
        this.mOneHourTimerListeners.add(oneHourTimerListener);
    }

    public void addTimeOrTimeZoneChangedListener(TimeOrTimeZoneChangedListener timeOrTimeZoneChangedListener) {
        this.mTimeOrTimeZoneChangedListeners.add(timeOrTimeZoneChangedListener);
    }

    public void removeBottleNeckHintListener(BottleNeckHintListener bottleNeckHintListener) {
        this.mBottleNeckHintListeners.remove(bottleNeckHintListener);
    }

    public void removePmmCriticalListener(PmmCriticalListener pmmCriticalListener) {
        this.mPmmCriticalListeners.remove(pmmCriticalListener);
    }

    public void removePmmStateChangeListener(PmmStateChangeListener pmmStateChangeListener) {
        this.mPmmStateChangeListeners.remove(pmmStateChangeListener);
    }

    public void removeLmkdEventListener(LmkdEventListener lmkdEventListener) {
        this.mLmkdEventListeners.remove(lmkdEventListener);
    }

    public void removeHomeLaunchListener(HomeLaunchListener homeLaunchListener) {
        this.mHomeLaunchListeners.remove(homeLaunchListener);
    }

    public void removeAppLaunchIntentListener(AppLaunchIntentListener appLaunchIntentListener) {
        this.mAppLaunchIntentListeners.remove(appLaunchIntentListener);
    }

    public void removeDeviceIdleListener(DeviceIdleListener deviceIdleListener) {
        this.mDeviceIdleListeners.remove(deviceIdleListener);
    }

    public void removeCameraStateListener(CameraStateListener cameraStateListener) {
        this.mCameraStateListeners.remove(cameraStateListener);
    }

    public void removeTimeOrTimeZoneChangedListener(TimeOrTimeZoneChangedListener timeOrTimeZoneChangedListener) {
        this.mTimeOrTimeZoneChangedListeners.remove(timeOrTimeZoneChangedListener);
    }

    public void removeOneHourTimerListener(OneHourTimerListener oneHourTimerListener) {
        this.mOneHourTimerListeners.remove(oneHourTimerListener);
        if (this.mOneHourTimerListeners.size() == 0) {
            this.mHandler.removeMessages(14);
        }
    }

    public class SystemEventHandler extends Handler {
        public SystemEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 1:
                        Iterator it = SystemEventListener.this.mPmmCriticalListeners.iterator();
                        while (it.hasNext()) {
                            ((PmmCriticalListener) it.next()).onPmmCriticalTriggered();
                        }
                        return;
                    case 2:
                        Iterator it2 = SystemEventListener.this.mBottleNeckHintListeners.iterator();
                        while (it2.hasNext()) {
                            ((BottleNeckHintListener) it2.next()).onBottleNeckHintTriggered();
                        }
                        return;
                    case 3:
                        Iterator it3 = SystemEventListener.this.mPmmStateChangeListeners.iterator();
                        while (it3.hasNext()) {
                            ((PmmStateChangeListener) it3.next()).onPmmStateChanged(((Integer) message.obj).intValue());
                        }
                        return;
                    case 4:
                        Iterator it4 = SystemEventListener.this.mLmkdEventListeners.iterator();
                        while (it4.hasNext()) {
                            ((LmkdEventListener) it4.next()).onLmkdEventTriggered(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        }
                        return;
                    case 5:
                        Iterator it5 = SystemEventListener.this.mHomeLaunchListeners.iterator();
                        while (it5.hasNext()) {
                            ((HomeLaunchListener) it5.next()).onHomeLaunched();
                        }
                        return;
                    case 6:
                        Iterator it6 = SystemEventListener.this.mCarModeChangeListeners.iterator();
                        while (it6.hasNext()) {
                            ((CarModeChangeListener) it6.next()).onCarModeChanged(true);
                        }
                        return;
                    case 7:
                        Iterator it7 = SystemEventListener.this.mCarModeChangeListeners.iterator();
                        while (it7.hasNext()) {
                            ((CarModeChangeListener) it7.next()).onCarModeChanged(false);
                        }
                        return;
                    case 8:
                        Iterator it8 = SystemEventListener.this.mMediaScanFinishedListeners.iterator();
                        while (it8.hasNext()) {
                            ((MediaScanFinishedListener) it8.next()).onMediaScanFinished();
                        }
                        return;
                    case 9:
                        Iterator it9 = SystemEventListener.this.mAppLaunchListeners.iterator();
                        if (it9.hasNext()) {
                            CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(it9.next());
                            throw null;
                        }
                        return;
                    case 10:
                        Iterator it10 = SystemEventListener.this.mDeviceIdleListeners.iterator();
                        while (it10.hasNext()) {
                            ((DeviceIdleListener) it10.next()).onDeviceIdle();
                        }
                        return;
                    case 11:
                        Iterator it11 = SystemEventListener.this.mAppLaunchIntentListeners.iterator();
                        while (it11.hasNext()) {
                            ((AppLaunchIntentListener) it11.next()).onAppLaunchIntent((String) message.obj);
                        }
                        return;
                    case 12:
                        Iterator it12 = SystemEventListener.this.mCameraStateListeners.iterator();
                        while (it12.hasNext()) {
                            ((CameraStateListener) it12.next()).onCameraOpen();
                        }
                        return;
                    case 13:
                        Iterator it13 = SystemEventListener.this.mCameraStateListeners.iterator();
                        while (it13.hasNext()) {
                            ((CameraStateListener) it13.next()).onCameraClose();
                        }
                        return;
                    case 14:
                        SystemEventListener.this.mSystemRepository.log("SystemEventListener", "MSG_ONE_HOUR_TIMER");
                        Iterator it14 = SystemEventListener.this.mOneHourTimerListeners.iterator();
                        while (it14.hasNext()) {
                            ((OneHourTimerListener) it14.next()).onOneHourTimer();
                        }
                        SystemEventListener.this.startOneHourTimer();
                        return;
                    case 15:
                        Iterator it15 = SystemEventListener.this.mTimeOrTimeZoneChangedListeners.iterator();
                        while (it15.hasNext()) {
                            ((TimeOrTimeZoneChangedListener) it15.next()).onTimeOrTimeZoneChanged((String) message.obj);
                        }
                        return;
                    case 16:
                        Iterator it16 = SystemEventListener.this.mQuotaListeners.iterator();
                        while (it16.hasNext()) {
                            ((AlwaysRunningQuotaExceedListener) it16.next()).onQuotaExceed((ChimeraQuotaMonitor.QuotaReclaimTarget) message.obj);
                        }
                        return;
                    default:
                        return;
                }
            } catch (RuntimeException e) {
                Log.e(SystemRepositoryDefault.convertToChimeraTag("SystemEventListener"), "Handler execute with exception " + e.getMessage());
            }
        }
    }

    public void startOneHourTimer() {
        this.mSystemRepository.log("SystemEventListener", "startOneHourTimer");
        this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 14), Duration.ofHours(1L).toMillis());
    }

    public void removeCameraDeviceStateCallback(Context context) {
        ((CameraManager) context.getSystemService("camera")).unregisterSemCameraDeviceStateCallback(this.mSystemRepository.getCameraDeviceStateCallback());
    }

    public void removeAppLaunchIntent() {
        provideLaunchObserverRegistry().unregisterLaunchObserver(this.mAppLaunchObserver);
    }

    public void addAlwaysRunningQuotaExceedListener(AlwaysRunningQuotaExceedListener alwaysRunningQuotaExceedListener) {
        this.mQuotaListeners.add(alwaysRunningQuotaExceedListener);
    }

    public void removeAlwaysRunningQuotaExceedListener(AlwaysRunningQuotaExceedListener alwaysRunningQuotaExceedListener) {
        this.mQuotaListeners.remove(alwaysRunningQuotaExceedListener);
    }

    public void sendQuotaExceedMessage(ChimeraQuotaMonitor.QuotaReclaimTarget quotaReclaimTarget) {
        Message obtain = Message.obtain(this.mHandler, 16);
        obtain.obj = quotaReclaimTarget;
        this.mHandler.sendMessage(obtain);
    }

    public class LmkdEventServerThread extends Thread {
        public Handler mHandler;
        public LocalServerSocket mServerSocket;
        public LocalSocket mSocket;
        public SystemRepository mSystemRepository;

        public LmkdEventServerThread(SystemRepository systemRepository, Handler handler) {
            this.mSystemRepository = systemRepository;
            this.mHandler = handler;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                this.mServerSocket = new LocalServerSocket("chimera");
            } catch (Exception unused) {
                this.mSystemRepository.log("SystemEventListener", "Failed to execute socket service.");
            }
            if (this.mServerSocket == null) {
                return;
            }
            this.mSystemRepository.log("SystemEventListener", "Waiting Client connected...");
            try {
                LocalSocket accept = this.mServerSocket.accept();
                this.mSocket = accept;
                accept.setReceiveBufferSize(256);
                this.mSocket.setSendBufferSize(256);
                this.mSystemRepository.log("SystemEventListener", "There is a client is accepted: " + this.mSocket.toString());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.mSocket.getInputStream()), 256);
                while (true) {
                    String[] split = bufferedReader.readLine().split(XmlUtils.STRING_ARRAY_SEPARATOR);
                    if (split != null && split.length > 1) {
                        if (split[0] != null && split[1] != null) {
                            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.parseInt(split[2].trim()), Integer.parseInt(split[3].trim()), new Integer(Integer.parseInt(split[4].trim()))));
                        }
                        Log.e("SystemEventListener", "Received lmkd data error");
                    }
                }
            } catch (Exception e) {
                this.mSystemRepository.log("SystemEventListener", "Socket Exception: " + e.toString());
            }
        }
    }
}
