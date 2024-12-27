package com.android.server.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Slog;
import android.util.SparseIntArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayManagerService;
import com.android.server.display.ExternalDisplayStatsService;
import com.android.server.display.utils.DebugUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExternalDisplayStatsService {
    public static final boolean DEBUG = DebugUtils.isDebuggable("ExternalDisplayStatsService");
    public final Injector mInjector;
    public int mInteractiveExternalDisplays;
    public boolean mIsExternalDisplayUsedForAudio;
    public boolean mIsInitialized;
    public final SparseIntArray mExternalDisplayStates = new SparseIntArray();
    public final AnonymousClass1 mAudioPlaybackCallback = new AnonymousClass1();
    public final AnonymousClass2 mInteractivityReceiver = new BroadcastReceiver() { // from class: com.android.server.display.ExternalDisplayStatsService.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            synchronized (ExternalDisplayStatsService.this.mExternalDisplayStates) {
                try {
                    if (ExternalDisplayStatsService.this.mExternalDisplayStates.size() == 0) {
                        return;
                    }
                    int i = 0;
                    int i2 = 0;
                    for (int i3 = 0; i3 < ExternalDisplayStatsService.this.mExternalDisplayStates.size(); i3++) {
                        ExternalDisplayStatsService externalDisplayStatsService = ExternalDisplayStatsService.this;
                        Injector injector = externalDisplayStatsService.mInjector;
                        int keyAt = externalDisplayStatsService.mExternalDisplayStates.keyAt(i3);
                        if (injector.mPowerManager == null) {
                            injector.mPowerManager = (PowerManager) injector.mContext.getSystemService(PowerManager.class);
                        }
                        PowerManager powerManager = injector.mPowerManager;
                        if (powerManager == null || powerManager.isInteractive(keyAt)) {
                            i2++;
                        }
                    }
                    ExternalDisplayStatsService externalDisplayStatsService2 = ExternalDisplayStatsService.this;
                    int i4 = externalDisplayStatsService2.mInteractiveExternalDisplays;
                    if (i4 == i2) {
                        return;
                    }
                    if (i2 == 0) {
                        synchronized (externalDisplayStatsService2.mExternalDisplayStates) {
                            while (i < externalDisplayStatsService2.mExternalDisplayStates.size()) {
                                try {
                                    Injector injector2 = externalDisplayStatsService2.mInjector;
                                    int i5 = i + 1;
                                    boolean z = externalDisplayStatsService2.mIsExternalDisplayUsedForAudio;
                                    injector2.getClass();
                                    FrameworkStatsLog.write(FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 4, i5, z);
                                    if (ExternalDisplayStatsService.DEBUG) {
                                        int keyAt2 = externalDisplayStatsService2.mExternalDisplayStates.keyAt(i);
                                        Slog.d("ExternalDisplayStatsService", "logExternalDisplayIdleStarted displayId=" + keyAt2 + " currentState=" + externalDisplayStatsService2.mExternalDisplayStates.get(keyAt2, 1) + " countOfExternalDisplays=" + i5 + " state=4 mIsExternalDisplayUsedForAudio=" + externalDisplayStatsService2.mIsExternalDisplayUsedForAudio);
                                    }
                                    i = i5;
                                } finally {
                                }
                            }
                        }
                    } else if (-1 != i4) {
                        synchronized (externalDisplayStatsService2.mExternalDisplayStates) {
                            while (true) {
                                try {
                                    if (i >= externalDisplayStatsService2.mExternalDisplayStates.size()) {
                                        break;
                                    }
                                    int keyAt3 = externalDisplayStatsService2.mExternalDisplayStates.keyAt(i);
                                    int i6 = externalDisplayStatsService2.mExternalDisplayStates.get(keyAt3, 1);
                                    if (i6 == 1) {
                                        break;
                                    }
                                    Injector injector3 = externalDisplayStatsService2.mInjector;
                                    i++;
                                    boolean z2 = externalDisplayStatsService2.mIsExternalDisplayUsedForAudio;
                                    injector3.getClass();
                                    FrameworkStatsLog.write(FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, i6, i, z2);
                                    if (ExternalDisplayStatsService.DEBUG) {
                                        Slog.d("ExternalDisplayStatsService", "logExternalDisplayIdleEnded displayId=" + keyAt3 + " state=" + i6 + " countOfExternalDisplays=" + i + " mIsExternalDisplayUsedForAudio=" + externalDisplayStatsService2.mIsExternalDisplayUsedForAudio);
                                    }
                                } finally {
                                }
                            }
                        }
                    }
                    ExternalDisplayStatsService.this.mInteractiveExternalDisplays = i2;
                } finally {
                }
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.display.ExternalDisplayStatsService$1, reason: invalid class name */
    public final class AnonymousClass1 extends AudioManager.AudioPlaybackCallback {
        public final ExternalDisplayStatsService$1$$ExternalSyntheticLambda0 mLogStateAfterAudioSinkDisabled;
        public final ExternalDisplayStatsService$1$$ExternalSyntheticLambda0 mLogStateAfterAudioSinkEnabled;

        /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.display.ExternalDisplayStatsService$1$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.display.ExternalDisplayStatsService$1$$ExternalSyntheticLambda0] */
        public AnonymousClass1() {
            final int i = 0;
            this.mLogStateAfterAudioSinkEnabled = new Runnable(this) { // from class: com.android.server.display.ExternalDisplayStatsService$1$$ExternalSyntheticLambda0
                public final /* synthetic */ ExternalDisplayStatsService.AnonymousClass1 f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = i;
                    ExternalDisplayStatsService.AnonymousClass1 anonymousClass1 = this.f$0;
                    switch (i2) {
                        case 0:
                            ExternalDisplayStatsService.m461$$Nest$mlogStateAfterAudioSinkChanged(ExternalDisplayStatsService.this, true);
                            break;
                        default:
                            ExternalDisplayStatsService.m461$$Nest$mlogStateAfterAudioSinkChanged(ExternalDisplayStatsService.this, false);
                            break;
                    }
                }
            };
            final int i2 = 1;
            this.mLogStateAfterAudioSinkDisabled = new Runnable(this) { // from class: com.android.server.display.ExternalDisplayStatsService$1$$ExternalSyntheticLambda0
                public final /* synthetic */ ExternalDisplayStatsService.AnonymousClass1 f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i22 = i2;
                    ExternalDisplayStatsService.AnonymousClass1 anonymousClass1 = this.f$0;
                    switch (i22) {
                        case 0:
                            ExternalDisplayStatsService.m461$$Nest$mlogStateAfterAudioSinkChanged(ExternalDisplayStatsService.this, true);
                            break;
                        default:
                            ExternalDisplayStatsService.m461$$Nest$mlogStateAfterAudioSinkChanged(ExternalDisplayStatsService.this, false);
                            break;
                    }
                }
            };
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x00c0  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x00f6  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00fd  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0109  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00f9  */
        @Override // android.media.AudioManager.AudioPlaybackCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onPlaybackConfigChanged(java.util.List r9) {
            /*
                Method dump skipped, instructions count: 275
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.ExternalDisplayStatsService.AnonymousClass1.onPlaybackConfigChanged(java.util.List):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {
        public AudioManager mAudioManager;
        public final Context mContext;
        public final Handler mHandler;
        public PowerManager mPowerManager;

        public Injector(Context context, DisplayManagerService.DisplayManagerHandler displayManagerHandler) {
            this.mContext = context;
            this.mHandler = displayManagerHandler;
        }
    }

    /* renamed from: -$$Nest$mlogStateAfterAudioSinkChanged, reason: not valid java name */
    public static void m461$$Nest$mlogStateAfterAudioSinkChanged(ExternalDisplayStatsService externalDisplayStatsService, boolean z) {
        int size;
        if (externalDisplayStatsService.mIsExternalDisplayUsedForAudio == z) {
            return;
        }
        externalDisplayStatsService.mIsExternalDisplayUsedForAudio = z;
        synchronized (externalDisplayStatsService.mExternalDisplayStates) {
            size = externalDisplayStatsService.mExternalDisplayStates.size();
        }
        Injector injector = externalDisplayStatsService.mInjector;
        boolean z2 = externalDisplayStatsService.mIsExternalDisplayUsedForAudio;
        injector.getClass();
        FrameworkStatsLog.write(FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, 10, size, z2);
        if (DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m("ExternalDisplayStatsService", BatteryService$$ExternalSyntheticOutline0.m(size, "logStateAfterAudioSinkChanged countOfExternalDisplays)=", " mIsExternalDisplayUsedForAudio="), externalDisplayStatsService.mIsExternalDisplayUsedForAudio);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.display.ExternalDisplayStatsService$2] */
    public ExternalDisplayStatsService(Injector injector) {
        this.mInjector = injector;
    }

    public boolean isExternalDisplayUsedForAudio() {
        return this.mIsExternalDisplayUsedForAudio;
    }

    public boolean isInteractiveExternalDisplays() {
        return this.mInteractiveExternalDisplays != 0;
    }

    public final void logExternalDisplayError(int i) {
        int size;
        synchronized (this.mExternalDisplayStates) {
            size = this.mExternalDisplayStates.size();
        }
        Injector injector = this.mInjector;
        boolean z = this.mIsExternalDisplayUsedForAudio;
        injector.getClass();
        FrameworkStatsLog.write(FrameworkStatsLog.EXTERNAL_DISPLAY_STATE_CHANGED, i, size, z);
        if (DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m("ExternalDisplayStatsService", ArrayUtils$$ExternalSyntheticOutline0.m(size, i, "logExternalDisplayError countOfExternalDisplays=", " errorType=", " mIsExternalDisplayUsedForAudio="), this.mIsExternalDisplayUsedForAudio);
        }
    }
}
