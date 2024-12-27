package com.android.systemui.uithreadmonitor;

import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.util.SparseArray;
import android.view.Choreographer;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.Rune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.BiConsumer;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class LooperSlowLogControllerImpl implements Dumpable, LooperSlowLogController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher bgDispatcher;
    public final Lazy choreographerLazy;
    public boolean curChoreographerOnly;
    public Function2 curLogHandler;
    public long curSlowDeliveryMs;
    public long curSlowDispatchMs;
    public final Looper mainLooper;
    public final CoroutineScope scope;
    public final boolean debug = Log.isLoggable("LooperSlow", 3);
    public final SparseArray types = new SparseArray(10);
    public final Job[] jobs = new Job[10];
    public final LooperSlowLogControllerImpl$debugCallback$1 debugCallback = new BiConsumer() { // from class: com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$debugCallback$1
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            String str = (String) obj;
            String str2 = (String) obj2;
            LooperSlowLogControllerImpl looperSlowLogControllerImpl = LooperSlowLogControllerImpl.this;
            Function2 function2 = looperSlowLogControllerImpl.curLogHandler;
            if (function2 != null) {
                function2.invoke(str, str2);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            Log.w("LooperSlow", str);
            BuildersKt.launch$default(looperSlowLogControllerImpl.scope, looperSlowLogControllerImpl.bgDispatcher, null, new LooperSlowLogControllerImpl$updateSlowDispatchOnChoreographer$2(looperSlowLogControllerImpl, currentTimeMillis, str, str2, null), 2);
        }
    };
    public final kotlin.Lazy dumpBuf$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$dumpBuf$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new ArrayDeque();
        }
    });
    public final kotlin.Lazy choreographerCallStackCnt$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$choreographerCallStackCnt$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(SystemProperties.getInt("debug.sysui.looper_slow.choreographer.callstack", 25));
        }
    });
    public final kotlin.Lazy choreographerSlowDispatchMs$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$choreographerSlowDispatchMs$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(SystemProperties.getInt("debug.sysui.looper_slow.choreographer.slow_dispatch", 30));
        }
    });

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getTYPE_MAX$annotations() {
        }
    }

    public final class LogType {
        public boolean choreographerOnly;
        public long deliveryTime;
        public long dispatchTime;
        public long lastEnabledTime;
        public Function2 logHandler;
        public final int type;

        public LogType() {
            this(0, 0L, 0L, 0L, false, null, 63, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LogType)) {
                return false;
            }
            LogType logType = (LogType) obj;
            return this.type == logType.type && this.dispatchTime == logType.dispatchTime && this.deliveryTime == logType.deliveryTime && this.lastEnabledTime == logType.lastEnabledTime && this.choreographerOnly == logType.choreographerOnly && Intrinsics.areEqual(this.logHandler, logType.logHandler);
        }

        public final int hashCode() {
            int m = TransitionData$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Integer.hashCode(this.type) * 31, 31, this.dispatchTime), 31, this.deliveryTime), 31, this.lastEnabledTime), 31, this.choreographerOnly);
            Function2 function2 = this.logHandler;
            return m + (function2 == null ? 0 : function2.hashCode());
        }

        public final String toString() {
            return "LogType(type=" + this.type + ", dispatchTime=" + this.dispatchTime + ", deliveryTime=" + this.deliveryTime + ", lastEnabledTime=" + this.lastEnabledTime + ", choreographerOnly=" + this.choreographerOnly + ", logHandler=" + this.logHandler + ")";
        }

        public LogType(int i, long j, long j2, long j3, boolean z, Function2 function2) {
            this.type = i;
            this.dispatchTime = j;
            this.deliveryTime = j2;
            this.lastEnabledTime = j3;
            this.choreographerOnly = z;
            this.logHandler = function2;
        }

        public /* synthetic */ LogType(int i, long j, long j2, long j3, boolean z, Function2 function2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? -1 : i, (i2 & 2) != 0 ? 0L : j, (i2 & 4) != 0 ? 0L : j2, (i2 & 8) == 0 ? j3 : 0L, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? null : function2);
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$debugCallback$1] */
    public LooperSlowLogControllerImpl(Looper looper, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Lazy lazy, DumpManager dumpManager) {
        this.mainLooper = looper;
        this.scope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.choreographerLazy = lazy;
        if (Rune.SYSUI_UI_THREAD_MONITOR) {
            dumpManager.registerNormalDumpable("LooperSlowLogController", this);
        }
    }

    public final boolean disable(int i) {
        if (i < 0 || i >= 10) {
            return false;
        }
        synchronized (this.types) {
            try {
                LogType logType = (LogType) this.types.get(i);
                if (logType != null) {
                    logType.dispatchTime = 0L;
                    logType.deliveryTime = 0L;
                    logType.lastEnabledTime = 0L;
                    logType.choreographerOnly = false;
                    logType.logHandler = null;
                }
                Job job = this.jobs[i];
                if (job != null) {
                    if (job.isActive()) {
                        job.cancel(null);
                    }
                    this.jobs[i] = null;
                }
                update$5$1();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Iterator it = ((ArrayDeque) this.dumpBuf$delegate.getValue()).iterator();
        while (it.hasNext()) {
            printWriter.println((String) it.next());
        }
    }

    public final boolean enable(int i, long j, long j2, long j3, boolean z, Function2 function2) {
        SparseArray sparseArray;
        if (!Rune.SYSUI_UI_THREAD_MONITOR || i < 0 || i >= 10) {
            return false;
        }
        if (j <= 0 && j2 <= 0) {
            disable(i);
            return false;
        }
        String str = "enable type=" + i + " dispatch=" + j + " delivery=" + j2 + " dur=" + j3 + ", cOnly=" + z + ", hasLogHandler=" + (function2 != null);
        if (this.debug) {
            Log.d("LooperSlow", str);
        }
        SparseArray sparseArray2 = this.types;
        synchronized (sparseArray2) {
            try {
                sparseArray = sparseArray2;
                try {
                    LogType logType = (LogType) this.types.get(i, new LogType(i, 0L, 0L, 0L, false, null, 62, null));
                    logType.dispatchTime = Math.max(j, 0L);
                    logType.deliveryTime = Math.max(j2, 0L);
                    logType.lastEnabledTime = SystemClock.elapsedRealtime();
                    logType.choreographerOnly = z;
                    logType.logHandler = function2;
                    this.types.set(i, logType);
                    String str2 = "enable " + this.types.get(i);
                    if (this.debug) {
                        Log.d("LooperSlow", str2);
                    }
                    Job job = this.jobs[i];
                    if (job != null) {
                        if (job.isActive()) {
                            job.cancel(null);
                        }
                        this.jobs[i] = null;
                    }
                    if (j3 > 0) {
                        this.jobs[i] = BuildersKt.launch$default(this.scope, null, null, new LooperSlowLogControllerImpl$enable$1$3(j3, this, i, null), 3);
                    }
                    update$5$1();
                    Unit unit = Unit.INSTANCE;
                    return true;
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                sparseArray = sparseArray2;
            }
        }
    }

    public final boolean isEnabled() {
        return this.curSlowDispatchMs > 0 || this.curSlowDeliveryMs > 0;
    }

    public final void update$5$1() {
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.types)), new Function1() { // from class: com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl$update$foundType$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LooperSlowLogControllerImpl.LogType logType = (LooperSlowLogControllerImpl.LogType) obj;
                return Boolean.valueOf(logType.dispatchTime > 0 || logType.deliveryTime > 0);
            }
        }));
        LogType logType = null;
        while (filteringSequence$iterator$1.hasNext()) {
            LogType logType2 = (LogType) filteringSequence$iterator$1.next();
            if (logType == null || logType.lastEnabledTime < logType2.lastEnabledTime) {
                logType = logType2;
            }
        }
        long j = logType != null ? logType.dispatchTime : 0L;
        this.curSlowDispatchMs = j;
        long j2 = logType != null ? logType.deliveryTime : 0L;
        this.curSlowDeliveryMs = j2;
        boolean z = logType != null ? logType.choreographerOnly : false;
        this.curChoreographerOnly = z;
        Function2 function2 = logType != null ? logType.logHandler : null;
        this.curLogHandler = function2;
        int i = logType != null ? logType.type : -1;
        boolean z2 = function2 != null;
        StringBuilder sb = new StringBuilder("update t=");
        sb.append(i);
        sb.append(" disp=");
        sb.append(j);
        sb.append(" deli=");
        sb.append(j2);
        sb.append(" cOnly=");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(sb, z, " hasLogHandler=", z2, "LooperSlow");
        boolean z3 = this.curChoreographerOnly;
        LooperSlowLogControllerImpl$debugCallback$1 looperSlowLogControllerImpl$debugCallback$1 = this.debugCallback;
        Lazy lazy = this.choreographerLazy;
        if (z3) {
            this.mainLooper.setSlowLogThresholdMs(0L, 0L);
            ((Choreographer) lazy.get()).setEnabledDebugCallback(true, looperSlowLogControllerImpl$debugCallback$1, 30, 1);
        } else {
            this.mainLooper.setSlowLogThresholdMs(this.curSlowDispatchMs, this.curSlowDeliveryMs);
            ((Choreographer) lazy.get()).setEnabledDebugCallback(this.curSlowDispatchMs > 0, looperSlowLogControllerImpl$debugCallback$1, ((Number) this.choreographerCallStackCnt$delegate.getValue()).intValue(), ((Number) this.choreographerSlowDispatchMs$delegate.getValue()).intValue());
        }
    }

    public static /* synthetic */ void getCurSlowDeliveryMs$annotations() {
    }

    public static /* synthetic */ void getCurSlowDispatchMs$annotations() {
    }
}
