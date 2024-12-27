package com.android.server.profcollect;

import android.R;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UpdateEngineCallback;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.os.BackgroundThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.SystemService;
import com.android.server.wm.ActivityMetricsLaunchObserver;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ProfcollectForwardingService extends SystemService {
    public static final long BG_PROCESS_INTERVAL = TimeUnit.HOURS.toMillis(4);
    public static ProfcollectForwardingService sSelfService;
    public final AppLaunchObserver mAppLaunchObserver;
    public final AnonymousClass2 mBroadcastReceiver;
    public final ProfcollectdHandler mHandler;
    public IProfCollectd mIProfcollect;
    public final AnonymousClass1 mProviderStatusCallback;
    public final boolean mUploadEnabled;
    public final int mUsageSetting;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.profcollect.ProfcollectForwardingService$1, reason: invalid class name */
    public final class AnonymousClass1 extends Binder implements IInterface {
        public AnonymousClass1() {
            attachInterface(this, "com.android.server.profcollect.IProviderStatusCallback");
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.server.profcollect.IProviderStatusCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.server.profcollect.IProviderStatusCallback");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            ProfcollectForwardingService.this.mHandler.sendEmptyMessage(1);
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.profcollect.ProfcollectForwardingService$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.android.server.profcollect.UPLOAD_PROFILES".equals(intent.getAction())) {
                Log.d(
                        "ProfcollectForwardingService",
                        "Received broadcast to pack and upload reports");
                BackgroundThread.get()
                        .getThreadHandler()
                        .post(
                                new ProfcollectForwardingService$$ExternalSyntheticLambda0(
                                        1, ProfcollectForwardingService.sSelfService));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.profcollect.ProfcollectForwardingService$3, reason: invalid class name */
    public final class AnonymousClass3 extends UpdateEngineCallback {
        public final void onPayloadApplicationComplete(int i) {}

        public final void onStatusUpdate(int i, float f) {
            if (i == 6) {
                BackgroundThread.get()
                        .getThreadHandler()
                        .post(
                                new ProfcollectForwardingService$$ExternalSyntheticLambda0(
                                        1, ProfcollectForwardingService.sSelfService));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.profcollect.ProfcollectForwardingService$4, reason: invalid class name */
    public final class AnonymousClass4 extends CameraManager.AvailabilityCallback {
        public AnonymousClass4() {}

        public final void onCameraOpened(String str, String str2) {
            Log.d("ProfcollectForwardingService", "Received camera open event from: " + str2);
            if (str2.startsWith("client.pid") || str2.equals("com.google.android.as")) {
                return;
            }
            if (ThreadLocalRandom.current().nextInt(100)
                    >= DeviceConfig.getInt("profcollect_native_boot", "camera_trace_freq", 10)) {
                return;
            }
            BackgroundThread.get()
                    .getThreadHandler()
                    .postDelayed(
                            new ProfcollectForwardingService$$ExternalSyntheticLambda0(5, this),
                            1000L);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppLaunchObserver extends ActivityMetricsLaunchObserver {
        public AppLaunchObserver() {}

        @Override // com.android.server.wm.ActivityMetricsLaunchObserver
        public final void onIntentStarted(Intent intent, long j) {
            intent.getPackage();
            ProfcollectForwardingService profcollectForwardingService =
                    ProfcollectForwardingService.this;
            if (profcollectForwardingService.mIProfcollect == null) {
                return;
            }
            if (ThreadLocalRandom.current().nextInt(100)
                    < DeviceConfig.getInt("profcollect_native_boot", "applaunch_trace_freq", 2)) {
                BackgroundThread.get()
                        .getThreadHandler()
                        .post(
                                new ProfcollectForwardingService$$ExternalSyntheticLambda0(
                                        4, profcollectForwardingService));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ProfcollectBGJobService extends JobService {
        public static final ComponentName JOB_SERVICE_NAME =
                new ComponentName("android", ProfcollectBGJobService.class.getName());

        @Override // android.app.job.JobService
        public final boolean onStartJob(JobParameters jobParameters) {
            BackgroundThread.get()
                    .getThreadHandler()
                    .post(
                            new ProfcollectForwardingService$$ExternalSyntheticLambda0(
                                    1, ProfcollectForwardingService.sSelfService));
            jobFinished(jobParameters, false);
            return true;
        }

        @Override // android.app.job.JobService
        public final boolean onStopJob(JobParameters jobParameters) {
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProfcollectdDeathRecipient implements IBinder.DeathRecipient {
        public ProfcollectdDeathRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.w("ProfcollectForwardingService", "profcollectd has died");
            ProfcollectForwardingService profcollectForwardingService =
                    ProfcollectForwardingService.this;
            profcollectForwardingService.mIProfcollect = null;
            if (profcollectForwardingService.connectNativeService()) {
                return;
            }
            profcollectForwardingService.mHandler.sendEmptyMessageDelayed(0, 5000L);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProfcollectdHandler extends Handler {
        public ProfcollectdHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            ProfcollectForwardingService profcollectForwardingService =
                    ProfcollectForwardingService.this;
            if (i == 0) {
                profcollectForwardingService.connectNativeService();
                return;
            }
            if (i != 1) {
                throw new AssertionError("Unknown message: " + message);
            }
            profcollectForwardingService.getClass();
            BackgroundThread.get()
                    .getThreadHandler()
                    .post(
                            new ProfcollectForwardingService$$ExternalSyntheticLambda0(
                                    2, profcollectForwardingService));
            Context context = profcollectForwardingService.getContext();
            ComponentName componentName = ProfcollectBGJobService.JOB_SERVICE_NAME;
            ((JobScheduler) context.getSystemService(JobScheduler.class))
                    .schedule(
                            new JobInfo.Builder(260817, ProfcollectBGJobService.JOB_SERVICE_NAME)
                                    .setRequiresDeviceIdle(true)
                                    .setRequiresCharging(true)
                                    .setPeriodic(ProfcollectForwardingService.BG_PROCESS_INTERVAL)
                                    .setPriority(100)
                                    .build());
        }
    }

    public ProfcollectForwardingService(Context context) {
        super(context);
        this.mHandler = new ProfcollectdHandler(IoThread.getHandler().getLooper());
        this.mProviderStatusCallback = new AnonymousClass1();
        this.mBroadcastReceiver = new AnonymousClass2();
        this.mAppLaunchObserver = new AppLaunchObserver();
        if (sSelfService != null) {
            throw new AssertionError("only one service instance allowed");
        }
        sSelfService = this;
        try {
            this.mUsageSetting = Settings.Global.getInt(context.getContentResolver(), "multi_cb");
        } catch (Settings.SettingNotFoundException e) {
            Log.e("ProfcollectForwardingService", "Usage setting not found: " + e.getMessage());
            this.mUsageSetting = -1;
        }
        this.mUploadEnabled =
                context.getResources().getBoolean(R.bool.config_safe_sound_dosage_enabled);
        context.registerReceiver(
                this.mBroadcastReceiver,
                BatteryService$$ExternalSyntheticOutline0.m(
                        "com.android.server.profcollect.UPLOAD_PROFILES"),
                4);
    }

    public static boolean enabled() {
        return DeviceConfig.getBoolean("profcollect_native_boot", "enabled", false)
                || SystemProperties.getBoolean("persist.profcollectd.enabled_override", false);
    }

    public final boolean connectNativeService() {
        try {
            IProfCollectd asInterface =
                    IProfCollectd.Stub.asInterface(
                            ServiceManager.getServiceOrThrow("profcollectd"));
            ((IProfCollectd.Stub.Proxy) asInterface)
                    .mRemote.linkToDeath(new ProfcollectdDeathRecipient(), 0);
            this.mIProfcollect = asInterface;
            return true;
        } catch (ServiceManager.ServiceNotFoundException | RemoteException unused) {
            Log.w("ProfcollectForwardingService", "Failed to connect profcollectd binder service.");
            return false;
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i != 1000 || this.mIProfcollect == null) {
            return;
        }
        BackgroundThread.get()
                .getThreadHandler()
                .post(new ProfcollectForwardingService$$ExternalSyntheticLambda0(0, this));
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        connectNativeService();
    }
}
