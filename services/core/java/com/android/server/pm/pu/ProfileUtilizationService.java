package com.android.server.pm.pu;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.Handler;
import android.util.Slog;

import com.android.internal.util.IndentingPrintWriter;
import com.android.server.ServiceThread;
import com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0;
import com.android.server.SystemService;
import com.android.server.art.model.DexoptResult;
import com.android.server.pm.DexOptHelper;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.dex.DexoptOptions;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public class ProfileUtilizationService extends SystemService {
    public static final String PU_VERSION = "1.0";
    public static final String TAG_PU = "PU";
    public final Context mContext;
    public long mFinishTimeMs;
    public int mStartReason;
    public long mStartTimeMs;
    public final ProfileUtilizationStorage mStorage;
    public final DexoptTrigger mTrigger;
    public final DeviceStatusWatcher mWatcher;
    public HotAppsWrapper mWrapper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class App {
        public DexoptResult mResult;
        public final String packageName;
        public final long weight;
        public State mState = State.CREATED;
        public CancellationSignal mCancellationSignal = new CancellationSignal();
        public long mOptimizedTimeMs = -1;
        public int mCancelCount = 0;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        final class State {
            public static final /* synthetic */ State[] $VALUES;
            public static final State CANCELLED;
            public static final State CREATED;
            public static final State OPTIMIZED;
            public static final State OPTIMIZING;
            public static final State REMOVED;

            static {
                State state = new State("CREATED", 0);
                CREATED = state;
                State state2 = new State("OPTIMIZING", 1);
                OPTIMIZING = state2;
                State state3 = new State("OPTIMIZED", 2);
                OPTIMIZED = state3;
                State state4 = new State("CANCELLED", 3);
                CANCELLED = state4;
                State state5 = new State("REMOVED", 4);
                REMOVED = state5;
                $VALUES = new State[] {state, state2, state3, state4, state5};
            }

            public static State valueOf(String str) {
                return (State) Enum.valueOf(State.class, str);
            }

            public static State[] values() {
                return (State[]) $VALUES.clone();
            }
        }

        public App(long j, String str) {
            this.packageName = str;
            this.weight = j;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.packageName);
            sb.append(", w=");
            sb.append(this.weight);
            sb.append(", s=");
            sb.append(this.mState);
            sb.append(", optTimeMs=");
            sb.append(this.mOptimizedTimeMs);
            sb.append(", cancelCount=");
            sb.append(this.mCancelCount);
            if (this.mResult != null) {
                sb.append(", dexopt=");
                sb.append(this.mResult.getFinalStatus());
                Iterator it = this.mResult.getPackageDexoptResults().iterator();
                while (it.hasNext()) {
                    for (DexoptResult.DexContainerFileDexoptResult dexContainerFileDexoptResult :
                            ((DexoptResult.PackageDexoptResult) it.next())
                                    .getDexContainerFileDexoptResults()) {
                        sb.append(", dex2oatTimeMs=");
                        sb.append(dexContainerFileDexoptResult.getDex2oatWallTimeMillis());
                        sb.append(", filter=");
                        sb.append(dexContainerFileDexoptResult.getActualCompilerFilter());
                    }
                }
            }
            return sb.toString();
        }
    }

    public ProfileUtilizationService(Context context) {
        super(context);
        this.mStartReason = -1;
        this.mStartTimeMs = -1L;
        this.mFinishTimeMs = -1L;
        this.mContext = context;
        DeviceStatusWatcher deviceStatusWatcher = new DeviceStatusWatcher(this);
        this.mWatcher = deviceStatusWatcher;
        this.mTrigger = new DexoptTrigger(deviceStatusWatcher);
        this.mStorage = new ProfileUtilizationStorage(context);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Profile utilization state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Current state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(toString());
        DeviceStatusWatcher deviceStatusWatcher = this.mWatcher;
        deviceStatusWatcher.getClass();
        indentingPrintWriter.println("PU_StatusWatcher:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(deviceStatusWatcher.toString());
        indentingPrintWriter.decreaseIndent();
        DexoptTrigger dexoptTrigger = this.mTrigger;
        dexoptTrigger.getClass();
        indentingPrintWriter.println("PU_DexoptTrigger:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(dexoptTrigger.toString());
        indentingPrintWriter.decreaseIndent();
        ProfileUtilizationStorage profileUtilizationStorage = this.mStorage;
        profileUtilizationStorage.getClass();
        indentingPrintWriter.println("PU_Storage:");
        indentingPrintWriter.increaseIndent();
        List<App> loadAppsList = profileUtilizationStorage.loadAppsList();
        if (!loadAppsList.isEmpty()) {
            indentingPrintWriter.println("Stored apps:");
            indentingPrintWriter.increaseIndent();
            for (App app : loadAppsList) {
                indentingPrintWriter.println(app.packageName + " " + app.weight);
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Stored PU state:");
        indentingPrintWriter.increaseIndent();
        for (String str : profileUtilizationStorage.mSharedPrefsDumps.getAll().keySet()) {
            indentingPrintWriter.println(str + ":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(
                    profileUtilizationStorage.mSharedPrefsDumps.getString(str, "no data stored"));
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public void onBootAfterUpdateIfNeeded(PackageManagerService packageManagerService) {
        int i;
        if (packageManagerService.mFirstBoot) {
            return;
        }
        if (packageManagerService.isDeviceUpgrading()) {
            i = 1;
        } else if (!DexOptHelper.hasBcpApexesChanged()) {
            return;
        } else {
            i = 13;
        }
        this.mStartReason = i;
        Slog.d(TAG_PU, "Device updated ".concat(DexoptOptions.convertToArtServiceDexoptReason(i)));
        File dataDirectory = Environment.getDataDirectory();
        if (dataDirectory.getUsableSpace() < (dataDirectory.getTotalSpace() / 100) * 5) {
            Slog.d(TAG_PU, "Skip profile utilization, low storage");
            return;
        }
        DeviceStatusWatcher deviceStatusWatcher = this.mWatcher;
        deviceStatusWatcher.getClass();
        ServiceThread serviceThread = new ServiceThread(10, "PU_StatusWatcher", false);
        deviceStatusWatcher.mHandlerThread = serviceThread;
        serviceThread.start();
        deviceStatusWatcher.mHandler =
                new Handler(deviceStatusWatcher.mHandlerThread.getLooper(), null, true);
        DeviceStatusWatcher.ReceiverController receiverController = deviceStatusWatcher.mController;
        DeviceStatusWatcher deviceStatusWatcher2 = DeviceStatusWatcher.this;
        deviceStatusWatcher2.mService.mContext.registerReceiver(
                receiverController.mStatusReceiver,
                receiverController.mStatusFilter,
                null,
                deviceStatusWatcher2.mHandler);
        DexOptHelper.getArtManagerLocal()
                .addDexoptDoneCallback(
                        false,
                        new SystemServerInitThreadPool$$ExternalSyntheticLambda0(),
                        deviceStatusWatcher.mDexoptDoneHandler);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        Slog.i(TAG_PU, "Profile utilization service started");
        DeviceStatusWatcher.ReceiverController receiverController = this.mWatcher.mController;
        DeviceStatusWatcher.this.mService.mContext.registerReceiver(
                receiverController.mShutdownReceiver, receiverController.mShutdownFilter);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Version: 1.0\nStart reason: ");
        int i = this.mStartReason;
        if (i == -1) {
            sb.append("boot-no-update");
        } else {
            sb.append(DexoptOptions.convertToArtServiceDexoptReason(i));
        }
        sb.append("\nStart time: ");
        if (this.mStartTimeMs == -1) {
            sb.append("not started");
        } else {
            sb.append(new Date(this.mStartTimeMs));
            sb.append(" ts=");
            sb.append(this.mStartTimeMs);
        }
        sb.append("\nFinish time: ");
        if (this.mFinishTimeMs == -1) {
            sb.append("not finished");
        } else {
            sb.append(new Date(this.mFinishTimeMs));
            sb.append(" ts=");
            sb.append(this.mFinishTimeMs);
            sb.append("\nTook time ");
            sb.append((this.mFinishTimeMs - this.mStartTimeMs) / 1000);
            sb.append("sec");
        }
        return sb.toString();
    }
}
