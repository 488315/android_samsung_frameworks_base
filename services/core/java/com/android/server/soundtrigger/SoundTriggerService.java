package com.android.server.soundtrigger;

import android.app.ActivityThread;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.Identity;
import android.media.permission.IdentityContext;
import android.media.permission.PermissionUtil;
import android.media.permission.SafeCloseable;
import android.media.soundtrigger.ISoundTriggerDetectionService;
import android.media.soundtrigger.ISoundTriggerDetectionServiceClient;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;
import android.media.soundtrigger_middleware.ISoundTriggerMiddlewareService;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.app.ISoundTriggerService;
import com.android.internal.app.ISoundTriggerSession;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;
import com.android.server.voiceinteraction.HotwordDetectionConnection;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerService extends SystemService {
    public AppOpsManager mAppOpsManager;
    public final Context mContext;
    public SoundTriggerDbHelper mDbHelper;
    public final Deque mDetachedSessionEventLoggers;
    public final EventLogger mDeviceEventLogger;
    public final DeviceStateHandler mDeviceStateHandler;
    public final Executor mDeviceStateHandlerExecutor;
    public final LocalSoundTriggerService mLocalSoundTriggerService;
    public final Object mLock;
    public ISoundTriggerMiddlewareService mMiddlewareService;
    public final ArrayMap mNumOpsPerPackage;
    public PackageManager mPackageManager;
    public final EventLogger mServiceEventLogger;
    public final SoundTriggerServiceStub mServiceStub;
    public final Set mSessionEventLoggers;
    public final AtomicInteger mSessionIdCounter;
    public final SoundModelStatTracker mSoundModelStatTracker;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalSoundTriggerService {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class SessionImpl {
            public final MyAppOpsListener mAppOpsListener;
            public final IBinder mClient;
            public final EventLogger mEventLogger;
            public final SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                    mListener;
            public final SparseArray mModelUuid = new SparseArray(1);
            public final Identity mOriginatorIdentity;
            public final SoundTriggerHelper mSoundTriggerHelper;

            public SessionImpl(
                    SoundTriggerHelper soundTriggerHelper,
                    IBinder iBinder,
                    EventLogger eventLogger,
                    Identity identity) {
                this.mSoundTriggerHelper = soundTriggerHelper;
                this.mClient = iBinder;
                this.mOriginatorIdentity = identity;
                this.mEventLogger = eventLogger;
                SoundTriggerService.this.mSessionEventLoggers.add(eventLogger);
                try {
                    iBinder.linkToDeath(
                            new SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda0(
                                    1, this),
                            0);
                } catch (RemoteException unused) {
                    clientDied();
                }
                SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                        soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1 =
                                new SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1(
                                        1, this);
                this.mListener =
                        soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1;
                Identity identity2 = this.mOriginatorIdentity;
                SoundTriggerHelper soundTriggerHelper2 = this.mSoundTriggerHelper;
                Objects.requireNonNull(soundTriggerHelper2);
                SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2
                        soundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2 =
                                new SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2(
                                        soundTriggerHelper2);
                SoundTriggerService soundTriggerService = SoundTriggerService.this;
                MyAppOpsListener myAppOpsListener =
                        soundTriggerService
                        .new MyAppOpsListener(
                                identity2,
                                soundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2);
                this.mAppOpsListener = myAppOpsListener;
                myAppOpsListener.onOpChanged(
                        "android:record_audio", myAppOpsListener.mOriginatorIdentity.packageName);
                soundTriggerService.mAppOpsManager.startWatchingMode(
                        "android:record_audio",
                        this.mOriginatorIdentity.packageName,
                        1,
                        myAppOpsListener);
                soundTriggerService.mDeviceStateHandler.registerListener(
                        soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1);
            }

            public final void clientDied() {
                EventLogger eventLogger = SoundTriggerService.this.mServiceEventLogger;
                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                        new SoundTriggerEvent$ServiceEvent(
                                SoundTriggerEvent$ServiceEvent.Type.DETACH,
                                this.mOriginatorIdentity.packageName,
                                "Client died");
                soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                detachInternal();
            }

            public final void detachInternal() {
                LocalSoundTriggerService localSoundTriggerService = LocalSoundTriggerService.this;
                MyAppOpsListener myAppOpsListener = this.mAppOpsListener;
                if (myAppOpsListener != null) {
                    SoundTriggerService.this.mAppOpsManager.stopWatchingMode(myAppOpsListener);
                }
                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                        new SoundTriggerEvent$ServiceEvent(
                                SoundTriggerEvent$SessionEvent$Type.DETACH,
                                (UUID) null,
                                (String) null);
                EventLogger eventLogger = this.mEventLogger;
                eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
                SoundTriggerService soundTriggerService = SoundTriggerService.this;
                if (soundTriggerService.mSessionEventLoggers.remove(eventLogger)) {
                    while (!((LinkedBlockingDeque) soundTriggerService.mDetachedSessionEventLoggers)
                            .offerFirst(eventLogger)) {
                        ((LinkedBlockingDeque) soundTriggerService.mDetachedSessionEventLoggers)
                                .pollLast();
                    }
                }
                SoundTriggerService.this.mDeviceStateHandler.mCallbackSet.remove(this.mListener);
                this.mSoundTriggerHelper.detach();
            }

            public final int setParameter(int i, int i2, int i3) {
                int parameterLocked;
                this.mEventLogger.enqueue(
                        new SoundTriggerEvent$ServiceEvent(
                                SoundTriggerEvent$SessionEvent$Type.SET_PARAMETER,
                                (UUID) this.mModelUuid.get(i),
                                (String) null));
                SoundTriggerHelper soundTriggerHelper = this.mSoundTriggerHelper;
                synchronized (soundTriggerHelper.mLock) {
                    try {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        parameterLocked =
                                soundTriggerHelper.setParameterLocked(
                                        soundTriggerHelper.getKeyphraseModelDataLocked(i), i2, i3);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return parameterLocked;
            }

            public final int startRecognition(
                    int i,
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel,
                    HotwordDetectionConnection.SoundTriggerCallback soundTriggerCallback,
                    SoundTrigger.RecognitionConfig recognitionConfig,
                    boolean z) {
                int i2;
                SoundTriggerHelper.ModelData modelData;
                this.mModelUuid.put(i, keyphraseSoundModel.getUuid());
                SoundTriggerHelper.ModelData modelData2 = null;
                this.mEventLogger.enqueue(
                        new SoundTriggerEvent$ServiceEvent(
                                SoundTriggerEvent$SessionEvent$Type.START_RECOGNITION,
                                keyphraseSoundModel.getUuid(),
                                (String) null));
                SoundTriggerHelper soundTriggerHelper = this.mSoundTriggerHelper;
                synchronized (soundTriggerHelper.mLock) {
                    try {
                        MetricsLogger.count(
                                soundTriggerHelper.mContext, "sth_start_recognition", 1);
                        i2 = Integer.MIN_VALUE;
                        if (soundTriggerCallback != null && recognitionConfig != null) {
                            if (soundTriggerHelper.mIsDetached) {
                                throw new IllegalStateException(
                                        "SoundTriggerHelper has been detached");
                            }
                            SoundTriggerHelper.ModelData keyphraseModelDataLocked =
                                    soundTriggerHelper.getKeyphraseModelDataLocked(i);
                            if (keyphraseModelDataLocked == null
                                    || keyphraseModelDataLocked.isKeyphraseModel()) {
                                if (keyphraseModelDataLocked == null
                                        || keyphraseModelDataLocked
                                                .getModelId()
                                                .equals(keyphraseSoundModel.getUuid())) {
                                    modelData2 = keyphraseModelDataLocked;
                                } else {
                                    i2 =
                                            soundTriggerHelper.tryStopAndUnloadLocked(
                                                    keyphraseModelDataLocked, true, true);
                                    if (i2 != 0) {
                                        Slog.w(
                                                "SoundTriggerHelper",
                                                "Unable to stop or unload previous model: "
                                                        + keyphraseModelDataLocked.toString());
                                    }
                                    if (i2 == 0) {
                                        UUID uuid =
                                                (UUID)
                                                        soundTriggerHelper.mKeyphraseUuidMap.get(
                                                                Integer.valueOf(i));
                                        if (uuid != null) {
                                            soundTriggerHelper.mModelDataMap.remove(uuid);
                                            soundTriggerHelper.mKeyphraseUuidMap.remove(
                                                    Integer.valueOf(i));
                                        }
                                    }
                                }
                                if (modelData2 == null) {
                                    UUID uuid2 = keyphraseSoundModel.getUuid();
                                    soundTriggerHelper.mKeyphraseUuidMap.remove(Integer.valueOf(i));
                                    soundTriggerHelper.mModelDataMap.remove(uuid2);
                                    soundTriggerHelper.mKeyphraseUuidMap.put(
                                            Integer.valueOf(i), uuid2);
                                    SoundTriggerHelper.ModelData modelData3 =
                                            new SoundTriggerHelper.ModelData(uuid2, 0);
                                    soundTriggerHelper.mModelDataMap.put(uuid2, modelData3);
                                    modelData = modelData3;
                                } else {
                                    modelData = modelData2;
                                }
                                i2 =
                                        soundTriggerHelper.startRecognition(
                                                keyphraseSoundModel,
                                                modelData,
                                                soundTriggerCallback,
                                                recognitionConfig,
                                                z);
                            } else {
                                Slog.e(
                                        "SoundTriggerHelper",
                                        "Generic model with same UUID exists.");
                            }
                        }
                    } finally {
                    }
                }
                return i2;
            }
        }

        public LocalSoundTriggerService() {}

        public final SessionImpl attach(
                IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties, boolean z) {
            Identity nonNull = IdentityContext.getNonNull();
            SoundTriggerService soundTriggerService = SoundTriggerService.this;
            int andIncrement = soundTriggerService.mSessionIdCounter.getAndIncrement();
            soundTriggerService.mServiceEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$ServiceEvent.Type.ATTACH,
                            nonNull.packageName + "#" + andIncrement,
                            (String) null));
            StringBuilder sb = new StringBuilder("LocalSoundTriggerEventLogger for package: ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    andIncrement, nonNull.packageName, "#", " - ", sb);
            sb.append(nonNull.uid);
            sb.append("|");
            sb.append(nonNull.pid);
            EventLogger eventLogger = new EventLogger(128, sb.toString());
            return new SessionImpl(
                    soundTriggerService.newSoundTriggerHelper(moduleProperties, eventLogger, z),
                    iBinder,
                    eventLogger,
                    nonNull);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyAppOpsListener implements AppOpsManager.OnOpChangedListener {
        public final Consumer mOnOpModeChanged;
        public final Identity mOriginatorIdentity;

        public MyAppOpsListener(Identity identity, Consumer consumer) {
            Objects.requireNonNull(identity);
            this.mOriginatorIdentity = identity;
            this.mOnOpModeChanged = consumer;
            try {
                int packageUid =
                        SoundTriggerService.this.mPackageManager.getPackageUid(
                                identity.packageName, PackageManager.PackageInfoFlags.of(4194304L));
                if (UserHandle.isSameApp(packageUid, identity.uid)) {
                    return;
                }
                throw new SecurityException(
                        "Uid "
                                + identity.uid
                                + " attempted to spoof package name "
                                + identity.packageName
                                + " with uid: "
                                + packageUid);
            } catch (PackageManager.NameNotFoundException unused) {
                throw new SecurityException(
                        "Package name not found: " + this.mOriginatorIdentity.packageName);
            }
        }

        @Override // android.app.AppOpsManager.OnOpChangedListener
        public final void onOpChanged(String str, String str2) {
            if (Objects.equals(str, "android:record_audio")) {
                AppOpsManager appOpsManager = SoundTriggerService.this.mAppOpsManager;
                Identity identity = this.mOriginatorIdentity;
                this.mOnOpModeChanged.accept(
                        Boolean.valueOf(
                                appOpsManager.checkOpNoThrow(
                                                "android:record_audio",
                                                identity.uid,
                                                identity.packageName)
                                        == 0));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NumOps {
        public long mLastOpsHourSinceBoot;
        public final Object mLock = new Object();
        public final int[] mNumOps = new int[24];
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Operation {
        public final Runnable mDropOp;
        public final ExecuteOp mExecuteOp;
        public final Runnable mSetupOp;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public interface ExecuteOp {
            void run(int i, ISoundTriggerDetectionService iSoundTriggerDetectionService);
        }

        public Operation(
                Runnable runnable,
                ExecuteOp executeOp,
                SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda2
                        soundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda2) {
            this.mSetupOp = runnable;
            this.mExecuteOp = executeOp;
            this.mDropOp =
                    soundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundModelStatTracker {
        public final TreeMap mModelStats = new TreeMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class SoundModelStat {
            public boolean mIsStarted;
            public long mLastStartTimestampMsec;
            public long mLastStopTimestampMsec;
            public long mStartCount;
            public long mTotalTimeMsec;
        }

        public final synchronized void onStart(UUID uuid) {
            try {
                SoundModelStat soundModelStat = (SoundModelStat) this.mModelStats.get(uuid);
                if (soundModelStat == null) {
                    soundModelStat = new SoundModelStat();
                    soundModelStat.mStartCount = 0L;
                    soundModelStat.mTotalTimeMsec = 0L;
                    soundModelStat.mLastStartTimestampMsec = 0L;
                    soundModelStat.mLastStopTimestampMsec = 0L;
                    soundModelStat.mIsStarted = false;
                    this.mModelStats.put(uuid, soundModelStat);
                }
                if (!soundModelStat.mIsStarted) {
                    soundModelStat.mStartCount++;
                    soundModelStat.mLastStartTimestampMsec = SystemClock.elapsedRealtime();
                    soundModelStat.mIsStarted = true;
                } else {
                    Slog.w(
                            "SoundTriggerService",
                            "error onStart(): Model " + uuid + " already started");
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final synchronized void onStop(UUID uuid) {
            SoundModelStat soundModelStat = (SoundModelStat) this.mModelStats.get(uuid);
            if (soundModelStat == null) {
                Slog.i(
                        "SoundTriggerService",
                        "error onStop(): Model " + uuid + " has no stats available");
                return;
            }
            if (!soundModelStat.mIsStarted) {
                Slog.w("SoundTriggerService", "error onStop(): Model " + uuid + " already stopped");
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            soundModelStat.mLastStopTimestampMsec = elapsedRealtime;
            soundModelStat.mTotalTimeMsec =
                    (elapsedRealtime - soundModelStat.mLastStartTimestampMsec)
                            + soundModelStat.mTotalTimeMsec;
            soundModelStat.mIsStarted = false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundTriggerServiceStub extends ISoundTriggerService.Stub {
        public SoundTriggerServiceStub() {}

        public final ISoundTriggerSession attachAsMiddleman(
                Identity identity,
                Identity identity2,
                SoundTrigger.ModuleProperties moduleProperties,
                IBinder iBinder) {
            int andIncrement = SoundTriggerService.this.mSessionIdCounter.getAndIncrement();
            SoundTriggerService.this.mServiceEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$ServiceEvent.Type.ATTACH,
                            identity.packageName + "#" + andIncrement,
                            (String) null));
            SafeCloseable establishIdentityIndirect =
                    PermissionUtil.establishIdentityIndirect(
                            SoundTriggerService.this.mContext,
                            "android.permission.SOUNDTRIGGER_DELEGATE_IDENTITY",
                            identity2,
                            identity);
            try {
                StringBuilder sb = new StringBuilder("SoundTriggerSessionLogs for package: ");
                String str = identity.packageName;
                Objects.requireNonNull(str);
                sb.append(str);
                sb.append("#");
                sb.append(andIncrement);
                sb.append(" - ");
                sb.append(identity.uid);
                sb.append("|");
                sb.append(identity.pid);
                EventLogger eventLogger = new EventLogger(128, sb.toString());
                SoundTriggerService soundTriggerService = SoundTriggerService.this;
                SoundTriggerSessionStub soundTriggerSessionStub =
                        soundTriggerService
                        .new SoundTriggerSessionStub(
                                iBinder,
                                soundTriggerService.newSoundTriggerHelper(
                                        moduleProperties, eventLogger, false),
                                eventLogger);
                if (establishIdentityIndirect != null) {
                    establishIdentityIndirect.close();
                }
                return soundTriggerSessionStub;
            } catch (Throwable th) {
                if (establishIdentityIndirect != null) {
                    try {
                        establishIdentityIndirect.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final ISoundTriggerSession attachAsOriginator(
                Identity identity,
                SoundTrigger.ModuleProperties moduleProperties,
                IBinder iBinder) {
            int andIncrement = SoundTriggerService.this.mSessionIdCounter.getAndIncrement();
            SoundTriggerService.this.mServiceEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$ServiceEvent.Type.ATTACH,
                            identity.packageName + "#" + andIncrement,
                            (String) null));
            SafeCloseable establishIdentityDirect =
                    PermissionUtil.establishIdentityDirect(identity);
            try {
                StringBuilder sb = new StringBuilder("SoundTriggerSessionLogs for package: ");
                String str = identity.packageName;
                Objects.requireNonNull(str);
                sb.append(str);
                sb.append("#");
                sb.append(andIncrement);
                sb.append(" - ");
                sb.append(identity.uid);
                sb.append("|");
                sb.append(identity.pid);
                EventLogger eventLogger = new EventLogger(128, sb.toString());
                SoundTriggerService soundTriggerService = SoundTriggerService.this;
                SoundTriggerSessionStub soundTriggerSessionStub =
                        soundTriggerService
                        .new SoundTriggerSessionStub(
                                iBinder,
                                soundTriggerService.newSoundTriggerHelper(
                                        moduleProperties, eventLogger, false),
                                eventLogger);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return soundTriggerSessionStub;
            } catch (Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void attachInjection(ISoundTriggerInjection iSoundTriggerInjection) {
            if (PermissionChecker.checkCallingPermissionForPreflight(
                            SoundTriggerService.this.mContext,
                            "android.permission.MANAGE_SOUND_TRIGGER",
                            (String) null)
                    != 0) {
                throw new SecurityException();
            }
            try {
                ISoundTriggerMiddlewareService.Stub.asInterface(
                                ServiceManager.waitForService("soundtrigger_middleware"))
                        .attachFakeHalInjection(iSoundTriggerInjection);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    SoundTriggerService.this.mContext, "SoundTriggerService", printWriter)) {
                printWriter.println("##Service-Wide logs:");
                SoundTriggerService.this.mServiceEventLogger.dump(printWriter, "  ");
                printWriter.println("\n##Device state logs:");
                DeviceStateHandler deviceStateHandler =
                        SoundTriggerService.this.mDeviceStateHandler;
                synchronized (deviceStateHandler.mLock) {
                    printWriter.println(
                            "DeviceState: " + deviceStateHandler.mSoundTriggerDeviceState.name());
                    printWriter.println("PhoneState: " + deviceStateHandler.mIsPhoneCallOngoing);
                    printWriter.println(
                            "PowerSaveMode: " + deviceStateHandler.mSoundTriggerPowerSaveMode);
                }
                SoundTriggerService.this.mDeviceEventLogger.dump(printWriter, "  ");
                printWriter.println("\n##Active Session dumps:\n");
                Iterator it = SoundTriggerService.this.mSessionEventLoggers.iterator();
                while (it.hasNext()) {
                    ((EventLogger) it.next()).dump(printWriter, "  ");
                    printWriter.println("");
                }
                printWriter.println("##Detached Session dumps:\n");
                Iterator it2 =
                        ((LinkedBlockingDeque)
                                        SoundTriggerService.this.mDetachedSessionEventLoggers)
                                .iterator();
                while (it2.hasNext()) {
                    ((EventLogger) it2.next()).dump(printWriter, "  ");
                    printWriter.println("");
                }
                printWriter.println("##Enrolled db dump:\n");
                SoundTriggerDbHelper soundTriggerDbHelper = SoundTriggerService.this.mDbHelper;
                synchronized (soundTriggerDbHelper) {
                    SQLiteDatabase readableDatabase = soundTriggerDbHelper.getReadableDatabase();
                    Cursor rawQuery =
                            readableDatabase.rawQuery("SELECT  * FROM st_sound_model", null);
                    try {
                        printWriter.println("  Enrolled GenericSoundModels:");
                        if (rawQuery.moveToFirst()) {
                            String[] columnNames = rawQuery.getColumnNames();
                            do {
                                for (String str : columnNames) {
                                    int columnIndex = rawQuery.getColumnIndex(str);
                                    int type = rawQuery.getType(columnIndex);
                                    if (type == 0) {
                                        printWriter.printf("    %s: null\n", str);
                                    } else if (type == 1) {
                                        printWriter.printf(
                                                "    %s: %d\n",
                                                str, Integer.valueOf(rawQuery.getInt(columnIndex)));
                                    } else if (type == 2) {
                                        printWriter.printf(
                                                "    %s: %f\n",
                                                str, Float.valueOf(rawQuery.getFloat(columnIndex)));
                                    } else if (type == 3) {
                                        printWriter.printf(
                                                "    %s: %s\n",
                                                str, rawQuery.getString(columnIndex));
                                    } else if (type == 4) {
                                        printWriter.printf("    %s: data blob\n", str);
                                    }
                                }
                                printWriter.println();
                            } while (rawQuery.moveToNext());
                        }
                        rawQuery.close();
                        readableDatabase.close();
                    } catch (Throwable th) {
                        rawQuery.close();
                        readableDatabase.close();
                        throw th;
                    }
                }
                printWriter.println("\n##Sound Model Stats dump:\n");
                SoundModelStatTracker soundModelStatTracker =
                        SoundTriggerService.this.mSoundModelStatTracker;
                synchronized (soundModelStatTracker) {
                    try {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        printWriter.println("Model Stats:");
                        for (Map.Entry entry : soundModelStatTracker.mModelStats.entrySet()) {
                            UUID uuid = (UUID) entry.getKey();
                            SoundModelStatTracker.SoundModelStat soundModelStat =
                                    (SoundModelStatTracker.SoundModelStat) entry.getValue();
                            long j = soundModelStat.mTotalTimeMsec;
                            if (soundModelStat.mIsStarted) {
                                j += elapsedRealtime - soundModelStat.mLastStartTimestampMsec;
                            }
                            printWriter.println(
                                    uuid
                                            + ", total_time(msec)="
                                            + j
                                            + ", total_count="
                                            + soundModelStat.mStartCount
                                            + ", last_start="
                                            + soundModelStat.mLastStartTimestampMsec
                                            + ", last_stop="
                                            + soundModelStat.mLastStopTimestampMsec);
                        }
                    } finally {
                    }
                }
            }
        }

        public final List listModuleProperties(Identity identity) {
            SoundTriggerService.this.mServiceEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$ServiceEvent.Type.LIST_MODULE,
                            identity.packageName,
                            (String) null));
            SafeCloseable establishIdentityDirect =
                    PermissionUtil.establishIdentityDirect(identity);
            try {
                List listUnderlyingModuleProperties =
                        SoundTriggerService.this.listUnderlyingModuleProperties(identity);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return listUnderlyingModuleProperties;
            } catch (Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void setInPhoneCallState(boolean z) {
            Slog.i("SoundTriggerService", "Overriding phone call state: " + z);
            SoundTriggerService.this.mDeviceStateHandler.onPhoneCallStateChanged(z);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundTriggerSessionStub extends ISoundTriggerSession.Stub {
        public final MyAppOpsListener mAppOpsListener;
        public final IBinder mClient;
        public final EventLogger mEventLogger;
        public final SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                mListener;
        public final SoundTriggerHelper mSoundTriggerHelper;
        public final TreeMap mLoadedModels = new TreeMap();
        public final Object mCallbacksLock = new Object();
        public final TreeMap mCallbacks = new TreeMap();
        public final Identity mOriginatorIdentity = IdentityContext.getNonNull();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class RemoteSoundTriggerDetectionService
                extends IRecognitionStatusCallback.Stub implements ServiceConnection {
            public final AnonymousClass1 mClient;
            public boolean mDestroyOnceRunningOpsDone;
            public boolean mIsBound;
            public boolean mIsDestroyed;
            public final NumOps mNumOps;
            public int mNumTotalOpsPerformed;
            public final Bundle mParams;
            public final ParcelUuid mPuuid;
            public final SoundTrigger.RecognitionConfig mRecognitionConfig;
            public final PowerManager.WakeLock mRemoteServiceWakeLock;
            public ISoundTriggerDetectionService mService;
            public final ComponentName mServiceName;
            public final UserHandle mUser;
            public final Object mRemoteServiceLock = new Object();
            public final ArrayList mPendingOps = new ArrayList();
            public final ArraySet mRunningOpIds = new ArraySet();
            public final Handler mHandler = new Handler(Looper.getMainLooper());

            /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$1] */
            public RemoteSoundTriggerDetectionService(
                    UUID uuid,
                    Bundle bundle,
                    ComponentName componentName,
                    UserHandle userHandle,
                    SoundTrigger.RecognitionConfig recognitionConfig) {
                this.mPuuid = new ParcelUuid(uuid);
                this.mParams = bundle;
                this.mServiceName = componentName;
                this.mUser = userHandle;
                this.mRecognitionConfig = recognitionConfig;
                this.mRemoteServiceWakeLock =
                        ((PowerManager) SoundTriggerService.this.mContext.getSystemService("power"))
                                .newWakeLock(
                                        1,
                                        "RemoteSoundTriggerDetectionService "
                                                + componentName.getPackageName()
                                                + ":"
                                                + componentName.getClassName());
                synchronized (SoundTriggerService.this.mLock) {
                    try {
                        NumOps numOps =
                                (NumOps)
                                        SoundTriggerService.this.mNumOpsPerPackage.get(
                                                componentName.getPackageName());
                        if (numOps == null) {
                            numOps = new NumOps();
                            SoundTriggerService.this.mNumOpsPerPackage.put(
                                    componentName.getPackageName(), numOps);
                        }
                        this.mNumOps = numOps;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.mClient =
                        new ISoundTriggerDetectionServiceClient
                                .Stub() { // from class:
                                          // com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.1
                            public final void onOpFinished(int i) {
                                long clearCallingIdentity = Binder.clearCallingIdentity();
                                try {
                                    synchronized (
                                            RemoteSoundTriggerDetectionService.this
                                                    .mRemoteServiceLock) {
                                        try {
                                            RemoteSoundTriggerDetectionService.this.mRunningOpIds
                                                    .remove(Integer.valueOf(i));
                                            if (RemoteSoundTriggerDetectionService.this
                                                            .mRunningOpIds.isEmpty()
                                                    && RemoteSoundTriggerDetectionService.this
                                                            .mPendingOps.isEmpty()) {
                                                RemoteSoundTriggerDetectionService
                                                        remoteSoundTriggerDetectionService =
                                                                RemoteSoundTriggerDetectionService
                                                                        .this;
                                                if (remoteSoundTriggerDetectionService
                                                        .mDestroyOnceRunningOpsDone) {
                                                    remoteSoundTriggerDetectionService.destroy();
                                                } else {
                                                    remoteSoundTriggerDetectionService
                                                            .disconnectLocked();
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                } finally {
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                }
                            }
                        };
            }

            public final void bind() {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Intent intent = new Intent();
                    intent.setComponent(this.mServiceName);
                    ResolveInfo resolveServiceAsUser =
                            SoundTriggerService.this
                                    .mContext
                                    .getPackageManager()
                                    .resolveServiceAsUser(
                                            intent, 268435588, this.mUser.getIdentifier());
                    if (resolveServiceAsUser == null) {
                        Slog.w(
                                "SoundTriggerService",
                                this.mPuuid + ": " + this.mServiceName + " not found");
                        SoundTriggerSessionStub.this.mEventLogger.enqueue(
                                new EventLogger.StringEvent(
                                        this.mPuuid + ": " + this.mServiceName + " not found"));
                        return;
                    }
                    if (!"android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE"
                            .equals(resolveServiceAsUser.serviceInfo.permission)) {
                        Slog.w(
                                "SoundTriggerService",
                                this.mPuuid
                                        + ": "
                                        + this.mServiceName
                                        + " does not require"
                                        + " android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE");
                        SoundTriggerSessionStub.this.mEventLogger.enqueue(
                                new EventLogger.StringEvent(
                                        this.mPuuid
                                                + ": "
                                                + this.mServiceName
                                                + " does not require"
                                                + " android.permission.BIND_SOUND_TRIGGER_DETECTION_SERVICE"));
                        return;
                    }
                    boolean bindServiceAsUser =
                            SoundTriggerService.this.mContext.bindServiceAsUser(
                                    intent, this, 67112961, this.mUser);
                    this.mIsBound = bindServiceAsUser;
                    if (bindServiceAsUser) {
                        this.mRemoteServiceWakeLock.acquire();
                    } else {
                        Slog.w(
                                "SoundTriggerService",
                                this.mPuuid + ": Could not bind to " + this.mServiceName);
                        SoundTriggerSessionStub.this.mEventLogger.enqueue(
                                new EventLogger.StringEvent(
                                        this.mPuuid + ": Could not bind to " + this.mServiceName));
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final AudioRecord createAudioRecordForEvent(
                    SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
                AudioAttributes.Builder builder = new AudioAttributes.Builder();
                builder.setInternalCapturePreset(1999);
                AudioAttributes build = builder.build();
                AudioFormat captureFormat = genericRecognitionEvent.getCaptureFormat();
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent("createAudioRecordForEvent"));
                return new AudioRecord.Builder()
                        .setAudioAttributes(build)
                        .setAudioFormat(
                                new AudioFormat.Builder()
                                        .setChannelMask(captureFormat.getChannelMask())
                                        .setEncoding(captureFormat.getEncoding())
                                        .setSampleRate(captureFormat.getSampleRate())
                                        .build())
                        .setSessionId(genericRecognitionEvent.getCaptureSession())
                        .build();
            }

            public final void destroy() {
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent(this.mPuuid + ": destroy"));
                synchronized (this.mRemoteServiceLock) {
                    disconnectLocked();
                    this.mIsDestroyed = true;
                }
                if (this.mDestroyOnceRunningOpsDone) {
                    return;
                }
                synchronized (SoundTriggerSessionStub.this.mCallbacksLock) {
                    SoundTriggerSessionStub.this.mCallbacks.remove(this.mPuuid.getUuid());
                }
            }

            public final void disconnectLocked() {
                ISoundTriggerDetectionService iSoundTriggerDetectionService = this.mService;
                if (iSoundTriggerDetectionService != null) {
                    try {
                        iSoundTriggerDetectionService.removeClient(this.mPuuid);
                    } catch (Exception e) {
                        Slog.e("SoundTriggerService", this.mPuuid + ": Cannot remove client", e);
                        SoundTriggerSessionStub.this.mEventLogger.enqueue(
                                new EventLogger.StringEvent(
                                        this.mPuuid + ": Cannot remove client"));
                    }
                    this.mService = null;
                }
                if (this.mIsBound) {
                    SoundTriggerService.this.mContext.unbindService(this);
                    this.mIsBound = false;
                    synchronized (SoundTriggerSessionStub.this.mCallbacksLock) {
                        this.mRemoteServiceWakeLock.release();
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public final void onBindingDied(ComponentName componentName) {
                Slog.v("SoundTriggerService", this.mPuuid + ": onBindingDied");
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent(this.mPuuid + ": onBindingDied"));
                synchronized (this.mRemoteServiceLock) {
                    destroy();
                }
            }

            public final void onError(final int i) {
                Slog.v("SoundTriggerService", this.mPuuid + ": onError: " + i);
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent(this.mPuuid + ": onError: " + i));
                runOrAddOperation(
                        new Operation(
                                new SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda0(
                                        this, 1),
                                new Operation
                                        .ExecuteOp() { // from class:
                                                       // com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda4
                                    @Override // com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp
                                    public final void run(
                                            int i2,
                                            ISoundTriggerDetectionService
                                                    iSoundTriggerDetectionService) {
                                        iSoundTriggerDetectionService.onError(
                                                SoundTriggerService.SoundTriggerSessionStub
                                                        .RemoteSoundTriggerDetectionService.this
                                                        .mPuuid,
                                                i2,
                                                i);
                                    }
                                },
                                null));
            }

            /* JADX WARN: Type inference failed for: r3v0, types: [com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda2] */
            public final void onGenericSoundTriggerDetected(
                    final SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) {
                runOrAddOperation(
                        new Operation(
                                new SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda0(
                                        this, 0),
                                new Operation.ExecuteOp() { // from class:
                                    // com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda1
                                    @Override // com.android.server.soundtrigger.SoundTriggerService.Operation.ExecuteOp
                                    public final void run(
                                            int i,
                                            ISoundTriggerDetectionService
                                                    iSoundTriggerDetectionService) {
                                        iSoundTriggerDetectionService.onGenericRecognitionEvent(
                                                SoundTriggerService.SoundTriggerSessionStub
                                                        .RemoteSoundTriggerDetectionService.this
                                                        .mPuuid,
                                                i,
                                                genericRecognitionEvent);
                                    }
                                },
                                new Runnable() { // from class:
                                    // com.android.server.soundtrigger.SoundTriggerService$SoundTriggerSessionStub$RemoteSoundTriggerDetectionService$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SoundTriggerService.SoundTriggerSessionStub
                                                        .RemoteSoundTriggerDetectionService
                                                remoteSoundTriggerDetectionService =
                                                        SoundTriggerService.SoundTriggerSessionStub
                                                                .RemoteSoundTriggerDetectionService
                                                                .this;
                                        SoundTrigger.GenericRecognitionEvent
                                                genericRecognitionEvent2 = genericRecognitionEvent;
                                        remoteSoundTriggerDetectionService.getClass();
                                        if (genericRecognitionEvent2.isCaptureAvailable()) {
                                            try {
                                                AudioRecord createAudioRecordForEvent =
                                                        remoteSoundTriggerDetectionService
                                                                .createAudioRecordForEvent(
                                                                        genericRecognitionEvent2);
                                                createAudioRecordForEvent.startRecording();
                                                createAudioRecordForEvent.release();
                                            } catch (IllegalArgumentException
                                                    | UnsupportedOperationException unused) {
                                                Slog.w(
                                                        "SoundTriggerService",
                                                        remoteSoundTriggerDetectionService.mPuuid
                                                                + ": createAudioRecordForEvent("
                                                                + genericRecognitionEvent2
                                                                + "), failed to create"
                                                                + " AudioRecord");
                                            }
                                        }
                                    }
                                }));
            }

            public final void onKeyphraseDetected(
                    SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) {}

            public final void onModuleDied() {
                Slog.v("SoundTriggerService", this.mPuuid + ": onModuleDied");
                onError(SoundTrigger.STATUS_DEAD_OBJECT);
            }

            @Override // android.content.ServiceConnection
            public final void onNullBinding(ComponentName componentName) {
                Slog.w(
                        "SoundTriggerService",
                        componentName + " for model " + this.mPuuid + " returned a null binding");
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent(
                                componentName
                                        + " for model "
                                        + this.mPuuid
                                        + " returned a null binding"));
                synchronized (this.mRemoteServiceLock) {
                    disconnectLocked();
                }
            }

            public final void onPauseFailed(int i) {
                Slog.v("SoundTriggerService", this.mPuuid + ": onPauseFailed: " + i);
                onError(i);
            }

            public final void onPreempted() {
                Slog.v("SoundTriggerService", this.mPuuid + ": onPreempted");
                onError(Integer.MIN_VALUE);
            }

            public final void onRecognitionPaused() {}

            public final void onRecognitionResumed() {}

            public final void onResumeFailed(int i) {
                Slog.v("SoundTriggerService", this.mPuuid + ": onResumeFailed: " + i);
                onError(i);
            }

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Slog.v(
                        "SoundTriggerService",
                        this.mPuuid + ": onServiceConnected(" + iBinder + ")");
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent(
                                this.mPuuid + ": onServiceConnected(" + iBinder + ")"));
                synchronized (this.mRemoteServiceLock) {
                    try {
                        ISoundTriggerDetectionService asInterface =
                                ISoundTriggerDetectionService.Stub.asInterface(iBinder);
                        this.mService = asInterface;
                        try {
                            asInterface.setClient(this.mPuuid, this.mParams, this.mClient);
                            while (!this.mPendingOps.isEmpty()) {
                                runOrAddOperation((Operation) this.mPendingOps.remove(0));
                            }
                        } catch (Exception e) {
                            Slog.e(
                                    "SoundTriggerService",
                                    this.mPuuid + ": Could not init " + this.mServiceName,
                                    e);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                Slog.v("SoundTriggerService", this.mPuuid + ": onServiceDisconnected");
                SoundTriggerSessionStub.this.mEventLogger.enqueue(
                        new EventLogger.StringEvent(this.mPuuid + ": onServiceDisconnected"));
                synchronized (this.mRemoteServiceLock) {
                    this.mService = null;
                }
            }

            public final boolean pingBinder() {
                return (this.mIsDestroyed || this.mDestroyOnceRunningOpsDone) ? false : true;
            }

            /* JADX WARN: Code restructure failed: missing block: B:63:0x0104, code lost:

               r0 = move-exception;
            */
            /* JADX WARN: Code restructure failed: missing block: B:64:0x0105, code lost:

               android.util.Slog.e("SoundTriggerService", r19.mPuuid + ": Could not run operation " + r3, r0);
               r19.this$1.mEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(r19.mPuuid + ": Could not run operation " + r3));
            */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void runOrAddOperation(
                    com.android.server.soundtrigger.SoundTriggerService.Operation r20) {
                /*
                    Method dump skipped, instructions count: 477
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.soundtrigger.SoundTriggerService.SoundTriggerSessionStub.RemoteSoundTriggerDetectionService.runOrAddOperation(com.android.server.soundtrigger.SoundTriggerService$Operation):void");
            }
        }

        public SoundTriggerSessionStub(
                IBinder iBinder, SoundTriggerHelper soundTriggerHelper, EventLogger eventLogger) {
            this.mSoundTriggerHelper = soundTriggerHelper;
            this.mClient = iBinder;
            this.mEventLogger = eventLogger;
            SoundTriggerService.this.mSessionEventLoggers.add(eventLogger);
            try {
                iBinder.linkToDeath(
                        new SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda0(
                                0, this),
                        0);
            } catch (RemoteException unused) {
                clientDied();
            }
            SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1
                    soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1 =
                            new SoundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1(
                                    0, this);
            this.mListener = soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1;
            Identity identity = this.mOriginatorIdentity;
            SoundTriggerHelper soundTriggerHelper2 = this.mSoundTriggerHelper;
            Objects.requireNonNull(soundTriggerHelper2);
            MyAppOpsListener myAppOpsListener =
                    SoundTriggerService.this
                    .new MyAppOpsListener(
                            identity,
                            new SoundTriggerService$LocalSoundTriggerService$SessionImpl$$ExternalSyntheticLambda2(
                                    soundTriggerHelper2));
            this.mAppOpsListener = myAppOpsListener;
            myAppOpsListener.onOpChanged(
                    "android:record_audio", myAppOpsListener.mOriginatorIdentity.packageName);
            SoundTriggerService.this.mAppOpsManager.startWatchingMode(
                    "android:record_audio",
                    this.mOriginatorIdentity.packageName,
                    1,
                    myAppOpsListener);
            SoundTriggerService.this.mDeviceStateHandler.registerListener(
                    soundTriggerService$SoundTriggerSessionStub$$ExternalSyntheticLambda1);
        }

        public static UUID getUuid(SoundTrigger.SoundModel soundModel) {
            if (soundModel != null) {
                return soundModel.getUuid();
            }
            return null;
        }

        public static UUID getUuid(ParcelUuid parcelUuid) {
            if (parcelUuid != null) {
                return parcelUuid.getUuid();
            }
            return null;
        }

        public final void clientDied() {
            this.mEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$SessionEvent$Type.DETACH,
                            (UUID) null,
                            (String) null));
            EventLogger eventLogger = SoundTriggerService.this.mServiceEventLogger;
            SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$ServiceEvent.Type.DETACH,
                            this.mOriginatorIdentity.packageName,
                            "Client died");
            soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
            eventLogger.enqueue(soundTriggerEvent$ServiceEvent);
            MyAppOpsListener myAppOpsListener = this.mAppOpsListener;
            if (myAppOpsListener != null) {
                SoundTriggerService.this.mAppOpsManager.stopWatchingMode(myAppOpsListener);
            }
            DeviceStateHandler deviceStateHandler = SoundTriggerService.this.mDeviceStateHandler;
            deviceStateHandler.mCallbackSet.remove(this.mListener);
            this.mSoundTriggerHelper.detach();
            SoundTriggerService soundTriggerService = SoundTriggerService.this;
            EventLogger eventLogger2 = this.mEventLogger;
            if (soundTriggerService.mSessionEventLoggers.remove(eventLogger2)) {
                while (!((LinkedBlockingDeque) soundTriggerService.mDetachedSessionEventLoggers)
                        .offerFirst(eventLogger2)) {
                    ((LinkedBlockingDeque) soundTriggerService.mDetachedSessionEventLoggers)
                            .pollLast();
                }
            }
        }

        public final void deleteSoundModel(ParcelUuid parcelUuid) {
            this.mEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$SessionEvent$Type.DELETE_MODEL,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                this.mSoundTriggerHelper.unloadGenericSoundModel(parcelUuid.getUuid());
                SoundTriggerService.this.mSoundModelStatTracker.onStop(parcelUuid.getUuid());
                SoundTriggerService.this.mDbHelper.deleteGenericSoundModel(parcelUuid.getUuid());
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void enforceCallingPermission(String str) {
            if (PermissionUtil.checkPermissionForPreflight(
                            SoundTriggerService.this.mContext, this.mOriginatorIdentity, str)
                    == 0) {
                return;
            }
            throw new SecurityException(
                    "Identity " + this.mOriginatorIdentity + " does not have permission " + str);
        }

        public final void enforceDetectionPermissions(ComponentName componentName) {
            if (SoundTriggerService.this.mPackageManager.checkPermission(
                            "android.permission.CAPTURE_AUDIO_HOTWORD",
                            componentName.getPackageName())
                    == 0) {
                return;
            }
            throw new SecurityException(
                    componentName.getPackageName()
                            + " does not have permission android.permission.CAPTURE_AUDIO_HOTWORD");
        }

        public final int getModelState(ParcelUuid parcelUuid) {
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.GET_MODEL_STATE;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.SoundModel soundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    int i = Integer.MIN_VALUE;
                    if (soundModel == null) {
                        EventLogger eventLogger2 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model is not loaded");
                        soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                        eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    if (soundModel.getType() != 1) {
                        EventLogger eventLogger3 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Unsupported model type");
                        soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerService");
                        eventLogger3.enqueue(soundTriggerEvent$ServiceEvent2);
                    } else {
                        i = this.mSoundTriggerHelper.getGenericModelState(soundModel.getUuid());
                    }
                    if (create != null) {
                        create.close();
                    }
                    return i;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final SoundTrigger.ModuleProperties getModuleProperties() {
            SoundTrigger.ModuleProperties moduleProperties;
            this.mEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$SessionEvent$Type.GET_MODULE_PROPERTIES,
                            (UUID) null,
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    moduleProperties = this.mSoundTriggerHelper.getModuleProperties();
                }
                if (create != null) {
                    create.close();
                }
                return moduleProperties;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int getParameter(ParcelUuid parcelUuid, int i) {
            int parameterLocked;
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.SoundModel soundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        throw new IllegalArgumentException("sound model is not loaded");
                    }
                    SoundTriggerHelper soundTriggerHelper = this.mSoundTriggerHelper;
                    UUID uuid = soundModel.getUuid();
                    synchronized (soundTriggerHelper.mLock) {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        parameterLocked =
                                soundTriggerHelper.getParameterLocked(
                                        (SoundTriggerHelper.ModelData)
                                                soundTriggerHelper.mModelDataMap.get(uuid),
                                        i);
                    }
                }
                if (create != null) {
                    create.close();
                }
                return parameterLocked;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelUuid) {
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                SoundTrigger.GenericSoundModel genericSoundModel =
                        SoundTriggerService.this.mDbHelper.getGenericSoundModel(
                                parcelUuid.getUuid());
                if (create != null) {
                    create.close();
                }
                return genericSoundModel;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final boolean isRecognitionActive(ParcelUuid parcelUuid) {
            boolean z;
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (this.mCallbacksLock) {
                    boolean z2 = false;
                    if (((IRecognitionStatusCallback) this.mCallbacks.get(parcelUuid.getUuid()))
                            == null) {
                        if (create != null) {
                            create.close();
                        }
                        return false;
                    }
                    SoundTriggerHelper soundTriggerHelper = this.mSoundTriggerHelper;
                    UUID uuid = parcelUuid.getUuid();
                    synchronized (soundTriggerHelper.mLock) {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        SoundTriggerHelper.ModelData modelData =
                                (SoundTriggerHelper.ModelData)
                                        soundTriggerHelper.mModelDataMap.get(uuid);
                        if (modelData != null) {
                            synchronized (modelData) {
                                z = modelData.mRequested;
                            }
                            if (z) {
                                z2 = true;
                            }
                        }
                    }
                    if (create != null) {
                        create.close();
                    }
                    return z2;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) {
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.LOAD_MODEL;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid((SoundTrigger.SoundModel) genericSoundModel),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                if (genericSoundModel != null && genericSoundModel.getUuid() != null) {
                    synchronized (SoundTriggerService.this.mLock) {
                        try {
                            SoundTrigger.SoundModel soundModel =
                                    (SoundTrigger.SoundModel)
                                            this.mLoadedModels.get(genericSoundModel.getUuid());
                            if (soundModel != null && !soundModel.equals(genericSoundModel)) {
                                this.mSoundTriggerHelper.unloadGenericSoundModel(
                                        genericSoundModel.getUuid());
                                synchronized (this.mCallbacksLock) {
                                    this.mCallbacks.remove(genericSoundModel.getUuid());
                                }
                            }
                            this.mLoadedModels.put(genericSoundModel.getUuid(), genericSoundModel);
                        } finally {
                        }
                    }
                    if (create == null) {
                        return 0;
                    }
                    create.close();
                    return 0;
                }
                EventLogger eventLogger2 = this.mEventLogger;
                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                        new SoundTriggerEvent$ServiceEvent(
                                soundTriggerEvent$SessionEvent$Type,
                                getUuid((SoundTrigger.SoundModel) genericSoundModel),
                                "Invalid sound model");
                soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                if (create == null) {
                    return Integer.MIN_VALUE;
                }
                create.close();
                return Integer.MIN_VALUE;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int loadKeyphraseSoundModel(
                SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.LOAD_MODEL;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid((SoundTrigger.SoundModel) keyphraseSoundModel),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                if (keyphraseSoundModel != null && keyphraseSoundModel.getUuid() != null) {
                    if (keyphraseSoundModel.getKeyphrases() != null
                            && keyphraseSoundModel.getKeyphrases().length == 1) {
                        synchronized (SoundTriggerService.this.mLock) {
                            try {
                                SoundTrigger.SoundModel soundModel =
                                        (SoundTrigger.SoundModel)
                                                this.mLoadedModels.get(
                                                        keyphraseSoundModel.getUuid());
                                if (soundModel != null && !soundModel.equals(keyphraseSoundModel)) {
                                    this.mSoundTriggerHelper.unloadKeyphraseSoundModel(
                                            keyphraseSoundModel.getKeyphrases()[0].getId());
                                    synchronized (this.mCallbacksLock) {
                                        this.mCallbacks.remove(keyphraseSoundModel.getUuid());
                                    }
                                }
                                this.mLoadedModels.put(
                                        keyphraseSoundModel.getUuid(), keyphraseSoundModel);
                            } finally {
                            }
                        }
                        if (create != null) {
                            create.close();
                        }
                        return 0;
                    }
                    EventLogger eventLogger2 = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    getUuid((SoundTrigger.SoundModel) keyphraseSoundModel),
                                    "Only one keyphrase supported");
                    soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                    eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                    if (create != null) {
                        create.close();
                    }
                    return Integer.MIN_VALUE;
                }
                EventLogger eventLogger3 = this.mEventLogger;
                SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                        new SoundTriggerEvent$ServiceEvent(
                                soundTriggerEvent$SessionEvent$Type,
                                getUuid((SoundTrigger.SoundModel) keyphraseSoundModel),
                                "Invalid sound model");
                soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerService");
                eventLogger3.enqueue(soundTriggerEvent$ServiceEvent2);
                if (create != null) {
                    create.close();
                }
                return Integer.MIN_VALUE;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final SoundTrigger.ModelParamRange queryParameter(ParcelUuid parcelUuid, int i) {
            SoundTrigger.ModelParamRange queryParameterLocked;
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.SoundModel soundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        if (create == null) {
                            return null;
                        }
                        create.close();
                        return null;
                    }
                    SoundTriggerHelper soundTriggerHelper = this.mSoundTriggerHelper;
                    UUID uuid = soundModel.getUuid();
                    synchronized (soundTriggerHelper.mLock) {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        queryParameterLocked =
                                soundTriggerHelper.queryParameterLocked(
                                        (SoundTriggerHelper.ModelData)
                                                soundTriggerHelper.mModelDataMap.get(uuid),
                                        i);
                    }
                    if (create != null) {
                        create.close();
                    }
                    return queryParameterLocked;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int setParameter(ParcelUuid parcelUuid, int i, int i2) {
            int parameterLocked;
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.SET_PARAMETER;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.SoundModel soundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        EventLogger eventLogger2 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model not loaded");
                        soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                        eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                        int i3 = SoundTrigger.STATUS_BAD_VALUE;
                        if (create != null) {
                            create.close();
                        }
                        return i3;
                    }
                    SoundTriggerHelper soundTriggerHelper = this.mSoundTriggerHelper;
                    UUID uuid = soundModel.getUuid();
                    synchronized (soundTriggerHelper.mLock) {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        parameterLocked =
                                soundTriggerHelper.setParameterLocked(
                                        (SoundTriggerHelper.ModelData)
                                                soundTriggerHelper.mModelDataMap.get(uuid),
                                        i,
                                        i2);
                    }
                    if (create != null) {
                        create.close();
                    }
                    return parameterLocked;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int startRecognition(
                SoundTrigger.GenericSoundModel genericSoundModel,
                IRecognitionStatusCallback iRecognitionStatusCallback,
                SoundTrigger.RecognitionConfig recognitionConfig,
                boolean z) {
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.START_RECOGNITION;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid((SoundTrigger.SoundModel) genericSoundModel),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                if (genericSoundModel == null) {
                    EventLogger eventLogger2 = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    getUuid((SoundTrigger.SoundModel) genericSoundModel),
                                    "Invalid sound model");
                    soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                    eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                    if (create == null) {
                        return Integer.MIN_VALUE;
                    }
                    create.close();
                    return Integer.MIN_VALUE;
                }
                if (z) {
                    enforceCallingPermission(
                            "android.permission.SOUND_TRIGGER_RUN_IN_BATTERY_SAVER");
                }
                int startGenericRecognition =
                        this.mSoundTriggerHelper.startGenericRecognition(
                                genericSoundModel.getUuid(),
                                genericSoundModel,
                                iRecognitionStatusCallback,
                                recognitionConfig,
                                z);
                if (startGenericRecognition == 0) {
                    SoundTriggerService.this.mSoundModelStatTracker.onStart(
                            genericSoundModel.getUuid());
                }
                if (create != null) {
                    create.close();
                }
                return startGenericRecognition;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int startRecognitionForService(
                ParcelUuid parcelUuid,
                Bundle bundle,
                ComponentName componentName,
                SoundTrigger.RecognitionConfig recognitionConfig) {
            IRecognitionStatusCallback iRecognitionStatusCallback;
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.START_RECOGNITION_SERVICE;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                Objects.requireNonNull(parcelUuid);
                Objects.requireNonNull(componentName);
                Objects.requireNonNull(recognitionConfig);
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                enforceDetectionPermissions(componentName);
                IRecognitionStatusCallback remoteSoundTriggerDetectionService =
                        new RemoteSoundTriggerDetectionService(
                                parcelUuid.getUuid(),
                                bundle,
                                componentName,
                                Binder.getCallingUserHandle(),
                                recognitionConfig);
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.GenericSoundModel genericSoundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (genericSoundModel == null) {
                        EventLogger eventLogger2 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model not loaded");
                        soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                        eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    synchronized (this.mCallbacksLock) {
                        iRecognitionStatusCallback =
                                (IRecognitionStatusCallback)
                                        this.mCallbacks.get(parcelUuid.getUuid());
                    }
                    if (iRecognitionStatusCallback != null) {
                        EventLogger eventLogger3 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model already running");
                        soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerService");
                        eventLogger3.enqueue(soundTriggerEvent$ServiceEvent2);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    if (genericSoundModel.getType() != 1) {
                        EventLogger eventLogger4 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent3 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Unsupported model type");
                        soundTriggerEvent$ServiceEvent3.printLog(2, "SoundTriggerService");
                        eventLogger4.enqueue(soundTriggerEvent$ServiceEvent3);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    int startGenericRecognition =
                            this.mSoundTriggerHelper.startGenericRecognition(
                                    genericSoundModel.getUuid(),
                                    genericSoundModel,
                                    remoteSoundTriggerDetectionService,
                                    recognitionConfig,
                                    false);
                    if (startGenericRecognition != 0) {
                        EventLogger eventLogger5 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent4 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model start fail");
                        soundTriggerEvent$ServiceEvent4.printLog(2, "SoundTriggerService");
                        eventLogger5.enqueue(soundTriggerEvent$ServiceEvent4);
                        if (create != null) {
                            create.close();
                        }
                        return startGenericRecognition;
                    }
                    synchronized (this.mCallbacksLock) {
                        this.mCallbacks.put(
                                parcelUuid.getUuid(), remoteSoundTriggerDetectionService);
                    }
                    SoundTriggerService.this.mSoundModelStatTracker.onStart(parcelUuid.getUuid());
                    if (create == null) {
                        return 0;
                    }
                    create.close();
                    return 0;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int stopRecognition(
                ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) {
            this.mEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$SessionEvent$Type.STOP_RECOGNITION,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                int stopGenericRecognition =
                        this.mSoundTriggerHelper.stopGenericRecognition(
                                parcelUuid.getUuid(), iRecognitionStatusCallback);
                if (stopGenericRecognition == 0) {
                    SoundTriggerService.this.mSoundModelStatTracker.onStop(parcelUuid.getUuid());
                }
                if (create != null) {
                    create.close();
                }
                return stopGenericRecognition;
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int stopRecognitionForService(ParcelUuid parcelUuid) {
            IRecognitionStatusCallback iRecognitionStatusCallback;
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.STOP_RECOGNITION_SERVICE;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.SoundModel soundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (soundModel == null) {
                        EventLogger eventLogger2 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model not loaded");
                        soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                        eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    synchronized (this.mCallbacksLock) {
                        iRecognitionStatusCallback =
                                (IRecognitionStatusCallback)
                                        this.mCallbacks.get(parcelUuid.getUuid());
                    }
                    if (iRecognitionStatusCallback == null) {
                        EventLogger eventLogger3 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model not running");
                        soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerService");
                        eventLogger3.enqueue(soundTriggerEvent$ServiceEvent2);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    if (soundModel.getType() != 1) {
                        EventLogger eventLogger4 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent3 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Unknown model type");
                        soundTriggerEvent$ServiceEvent3.printLog(2, "SoundTriggerService");
                        eventLogger4.enqueue(soundTriggerEvent$ServiceEvent3);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    int stopGenericRecognition =
                            this.mSoundTriggerHelper.stopGenericRecognition(
                                    soundModel.getUuid(), iRecognitionStatusCallback);
                    if (stopGenericRecognition != 0) {
                        EventLogger eventLogger5 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent4 =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Failed to stop model");
                        soundTriggerEvent$ServiceEvent4.printLog(2, "SoundTriggerService");
                        eventLogger5.enqueue(soundTriggerEvent$ServiceEvent4);
                        if (create != null) {
                            create.close();
                        }
                        return stopGenericRecognition;
                    }
                    synchronized (this.mCallbacksLock) {
                        this.mCallbacks.remove(parcelUuid.getUuid());
                    }
                    SoundTriggerService.this.mSoundModelStatTracker.onStop(parcelUuid.getUuid());
                    if (create == null) {
                        return 0;
                    }
                    create.close();
                    return 0;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int unloadSoundModel(ParcelUuid parcelUuid) {
            int unloadKeyphraseSoundModel;
            EventLogger eventLogger = this.mEventLogger;
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type =
                    SoundTriggerEvent$SessionEvent$Type.UNLOAD_MODEL;
            eventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            soundTriggerEvent$SessionEvent$Type,
                            getUuid(parcelUuid),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                synchronized (SoundTriggerService.this.mLock) {
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel =
                            (SoundTrigger.SoundModel) this.mLoadedModels.get(parcelUuid.getUuid());
                    if (keyphraseSoundModel == null) {
                        EventLogger eventLogger2 = this.mEventLogger;
                        SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent =
                                new SoundTriggerEvent$ServiceEvent(
                                        soundTriggerEvent$SessionEvent$Type,
                                        getUuid(parcelUuid),
                                        "Model not loaded");
                        soundTriggerEvent$ServiceEvent.printLog(2, "SoundTriggerService");
                        eventLogger2.enqueue(soundTriggerEvent$ServiceEvent);
                        if (create != null) {
                            create.close();
                        }
                        return Integer.MIN_VALUE;
                    }
                    int type = keyphraseSoundModel.getType();
                    if (type == 0) {
                        unloadKeyphraseSoundModel =
                                this.mSoundTriggerHelper.unloadKeyphraseSoundModel(
                                        keyphraseSoundModel.getKeyphrases()[0].getId());
                    } else {
                        if (type != 1) {
                            EventLogger eventLogger3 = this.mEventLogger;
                            SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent2 =
                                    new SoundTriggerEvent$ServiceEvent(
                                            soundTriggerEvent$SessionEvent$Type,
                                            getUuid(parcelUuid),
                                            "Unknown model type");
                            soundTriggerEvent$ServiceEvent2.printLog(2, "SoundTriggerService");
                            eventLogger3.enqueue(soundTriggerEvent$ServiceEvent2);
                            if (create != null) {
                                create.close();
                            }
                            return Integer.MIN_VALUE;
                        }
                        unloadKeyphraseSoundModel =
                                this.mSoundTriggerHelper.unloadGenericSoundModel(
                                        keyphraseSoundModel.getUuid());
                    }
                    if (unloadKeyphraseSoundModel == 0) {
                        this.mLoadedModels.remove(parcelUuid.getUuid());
                        if (create != null) {
                            create.close();
                        }
                        return 0;
                    }
                    EventLogger eventLogger4 = this.mEventLogger;
                    SoundTriggerEvent$ServiceEvent soundTriggerEvent$ServiceEvent3 =
                            new SoundTriggerEvent$ServiceEvent(
                                    soundTriggerEvent$SessionEvent$Type,
                                    getUuid(parcelUuid),
                                    "Failed to unload model");
                    soundTriggerEvent$ServiceEvent3.printLog(2, "SoundTriggerService");
                    eventLogger4.enqueue(soundTriggerEvent$ServiceEvent3);
                    if (create != null) {
                        create.close();
                    }
                    return unloadKeyphraseSoundModel;
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) {
            this.mEventLogger.enqueue(
                    new SoundTriggerEvent$ServiceEvent(
                            SoundTriggerEvent$SessionEvent$Type.UPDATE_MODEL,
                            getUuid((SoundTrigger.SoundModel) genericSoundModel),
                            (String) null));
            SafeCloseable create = ClearCallingIdentityContext.create();
            try {
                enforceCallingPermission("android.permission.MANAGE_SOUND_TRIGGER");
                SoundTriggerService.this.mDbHelper.updateGenericSoundModel(genericSoundModel);
                if (create != null) {
                    create.close();
                }
            } catch (Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public SoundTriggerService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mServiceEventLogger = new EventLogger(256, "Service");
        EventLogger eventLogger = new EventLogger(256, "Device Event");
        this.mDeviceEventLogger = eventLogger;
        this.mSessionEventLoggers = ConcurrentHashMap.newKeySet(4);
        this.mDetachedSessionEventLoggers = new LinkedBlockingDeque(4);
        this.mSessionIdCounter = new AtomicInteger(0);
        this.mNumOpsPerPackage = new ArrayMap();
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        this.mContext = context;
        this.mServiceStub = new SoundTriggerServiceStub();
        this.mLocalSoundTriggerService = new LocalSoundTriggerService();
        this.mSoundModelStatTracker = new SoundModelStatTracker();
        this.mDeviceStateHandler = new DeviceStateHandler(newSingleThreadExecutor, eventLogger);
    }

    public final List listUnderlyingModuleProperties(Identity identity) {
        Identity identity2 = new Identity();
        identity2.packageName = ActivityThread.currentOpPackageName();
        try {
            return (List)
                    Arrays.stream(
                                    this.mMiddlewareService.listModulesAsMiddleman(
                                            identity2, identity))
                            .map(new SoundTriggerService$$ExternalSyntheticLambda0())
                            .collect(Collectors.toList());
        } catch (RemoteException unused) {
            throw new ServiceSpecificException(SoundTrigger.STATUS_DEAD_OBJECT);
        }
    }

    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.server.soundtrigger.SoundTriggerService$$ExternalSyntheticLambda2] */
    public final SoundTriggerHelper newSoundTriggerHelper(
            SoundTrigger.ModuleProperties moduleProperties, EventLogger eventLogger, boolean z) {
        Identity identity = new Identity();
        identity.packageName = ActivityThread.currentOpPackageName();
        final Identity nonNull = IdentityContext.getNonNull();
        List listUnderlyingModuleProperties = listUnderlyingModuleProperties(nonNull);
        int id = moduleProperties != null ? moduleProperties.getId() : -1;
        if (id == -1 || listUnderlyingModuleProperties.contains(moduleProperties)) {
            return new SoundTriggerHelper(
                    this.mContext,
                    eventLogger,
                    new SoundTriggerService$$ExternalSyntheticLambda1(
                            this, id, identity, nonNull, z),
                    id,
                    new Supplier() { // from class:
                                     // com.android.server.soundtrigger.SoundTriggerService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return SoundTriggerService.this.listUnderlyingModuleProperties(nonNull);
                        }
                    });
        }
        throw new IllegalArgumentException("Invalid module properties");
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "onBootPhase: ", " : ");
        m.append(isSafeMode());
        Slog.d("SoundTriggerService", m.toString());
        if (600 == i) {
            this.mDbHelper = new SoundTriggerDbHelper(this.mContext, "st_sound_model.db", null, 2);
            this.mAppOpsManager =
                    (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
            this.mPackageManager = this.mContext.getPackageManager();
            final PowerManager powerManager =
                    (PowerManager) this.mContext.getSystemService(PowerManager.class);
            this.mContext.registerReceiver(
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.soundtrigger.SoundTriggerService.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if ("android.os.action.POWER_SAVE_MODE_CHANGED"
                                    .equals(intent.getAction())) {
                                SoundTriggerService.this.mDeviceStateHandler.onPowerModeChanged(
                                        powerManager.getSoundTriggerPowerSaveMode());
                            }
                        }
                    },
                    new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED"));
            int soundTriggerPowerSaveMode = powerManager.getSoundTriggerPowerSaveMode();
            DeviceStateHandler deviceStateHandler = this.mDeviceStateHandler;
            deviceStateHandler.onPowerModeChanged(soundTriggerPowerSaveMode);
            if (this.mContext
                    .getPackageManager()
                    .hasSystemFeature("android.hardware.telephony.calling")) {
                new PhoneCallStateHandler(
                        (SubscriptionManager)
                                this.mContext.getSystemService(SubscriptionManager.class),
                        (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class),
                        deviceStateHandler);
            }
        }
        this.mMiddlewareService =
                ISoundTriggerMiddlewareService.Stub.asInterface(
                        ServiceManager.waitForService("soundtrigger_middleware"));
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("soundtrigger", this.mServiceStub);
        publishLocalService(LocalSoundTriggerService.class, this.mLocalSoundTriggerService);
    }
}
