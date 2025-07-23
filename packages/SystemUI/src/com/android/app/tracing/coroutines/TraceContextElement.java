package com.android.app.tracing.coroutines;

import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CopyableThreadContextElement;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TraceContextElement extends CoroutineTraceName implements CopyableThreadContextElement {
    public final AtomicInteger childCoroutineCount;
    public final int childDepth;
    public final TraceData contextTraceData;
    public int continuationCount;
    public final String copyForChildTraceMessage;
    public final String coroutineTraceName;
    public final int currentId;
    public final boolean isRoot;
    public final String mergeForChildTraceMessage;
    public final Function1 shouldIgnoreClassName;
    public final boolean walkStackForDefaultNames;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Key extends AbstractCoroutineContextKey {
        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Key() {
            super(CoroutineTraceName.Key, new TraceContextElement$Key$$ExternalSyntheticLambda0());
        }
    }

    static {
        new Key(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r12v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r12v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.StringBuilder] */
    public TraceContextElement(String str, boolean z, boolean z2, boolean z3, Function1 function1, Integer num, String str2, int i) {
        super(str);
        TraceData traceData;
        String m;
        this.isRoot = z;
        this.walkStackForDefaultNames = z3;
        this.shouldIgnoreClassName = function1;
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        int nextInt = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        this.currentId = nextInt;
        String str3 = (z ? "ROOT-" : "") + str + ";c=" + nextInt + ";p=" + (num == 0 ? SignalSeverity.NONE : num);
        Trace.traceBegin(4096L, "TCE#init;" + str3);
        if (z) {
            traceData = null;
        } else {
            traceData = new TraceData(nextInt, str2 != null);
        }
        this.contextTraceData = traceData;
        int i2 = -1;
        if (str2 == null) {
            m = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("coroutine execution;", str3, i == -1 ? "" : MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ";d="), z2 ? ";n=" : "");
        } else {
            m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, str);
        }
        this.coroutineTraceName = m;
        this.continuationCount = z2 ? 0 : Integer.MIN_VALUE;
        if (str2 == null && i != -1) {
            i2 = i + 1;
        }
        this.childDepth = i2;
        this.childCoroutineCount = str2 != null ? new AtomicInteger(0) : null;
        this.copyForChildTraceMessage = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("TCE#copy;", str3);
        this.mergeForChildTraceMessage = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("TCE#merge;", str3);
        Trace.traceEnd(4096L);
    }

    public final TraceContextElement copyForChild() {
        try {
            Trace.traceBegin(4096L, this.copyForChildTraceMessage);
            return createChildContext(this.isRoot ? this.name : null);
        } finally {
            Trace.traceEnd(4096L);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0040, code lost:
    
        if (r10 == null) goto L14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.app.tracing.coroutines.TraceContextElement createChildContext(java.lang.String r10) {
        /*
            r9 = this;
            com.android.app.tracing.coroutines.TraceContextElement r0 = new com.android.app.tracing.coroutines.TraceContextElement
            java.lang.String r1 = ""
            if (r10 != 0) goto L40
            boolean r2 = r9.walkStackForDefaultNames
            if (r2 == 0) goto L40
            kotlin.jvm.functions.Function1 r10 = r9.shouldIgnoreClassName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r2 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            r2 = 4096(0x1000, double:2.0237E-320)
            java.lang.String r4 = "walkStackForClassName"
            android.os.Trace.traceBegin(r2, r4)
            kotlin.jvm.internal.Ref$ObjectRef r4 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            r4.<init>()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            r4.element = r1     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            java.lang.StackWalker r5 = java.lang.StackWalker.getInstance()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda0 r6 = new com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda0     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            r6.<init>()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Function$0 r10 = new com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Function$0     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            r10.<init>()     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            r5.walk(r10)     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            T r10 = r4.element     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            java.lang.String r10 = (java.lang.String) r10     // Catch: java.lang.Throwable -> L36 java.lang.Exception -> L3c
            android.os.Trace.traceEnd(r2)
            goto L43
        L36:
            r0 = move-exception
            r9 = r0
            android.os.Trace.traceEnd(r2)
            throw r9
        L3c:
            android.os.Trace.traceEnd(r2)
            goto L42
        L40:
            if (r10 != 0) goto L43
        L42:
            r10 = r1
        L43:
            int r2 = r9.continuationCount
            if (r2 < 0) goto L4a
            r2 = 1
        L48:
            r3 = r2
            goto L4c
        L4a:
            r2 = 0
            goto L48
        L4c:
            int r2 = r9.currentId
            java.lang.Integer r6 = java.lang.Integer.valueOf(r2)
            java.util.concurrent.atomic.AtomicInteger r2 = r9.childCoroutineCount
            if (r2 == 0) goto L82
            int r2 = r2.incrementAndGet()
            boolean r4 = r9.isRoot
            if (r4 == 0) goto L5f
            goto L6c
        L5f:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = r9.coroutineTraceName
            java.lang.String r5 = ":"
            java.lang.String r1 = androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0.m(r1, r4, r5)
        L6c:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            r4.append(r2)
            java.lang.String r1 = "^"
            r4.append(r1)
            java.lang.String r1 = r4.toString()
        L80:
            r7 = r1
            goto L84
        L82:
            r1 = 0
            goto L80
        L84:
            boolean r4 = r9.walkStackForDefaultNames
            kotlin.jvm.functions.Function1 r5 = r9.shouldIgnoreClassName
            r2 = 0
            int r8 = r9.childDepth
            r1 = r10
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.app.tracing.coroutines.TraceContextElement.createChildContext(java.lang.String):com.android.app.tracing.coroutines.TraceContextElement");
    }

    @Override // kotlinx.coroutines.ThreadContextElement
    public final void restoreThreadContext(Object obj) {
        TraceData traceData = (TraceData) obj;
        TraceStorage traceStorage = (TraceStorage) TraceContextElementKt.traceThreadLocal.get();
        if (traceStorage == null || traceStorage.data == traceData) {
            return;
        }
        traceStorage.data = traceData;
        int i = traceStorage.contIndex;
        traceStorage.contIndex = i - 1;
        if (i >= 0 && traceStorage.openSliceCount.length > i) {
            if (Trace.isTagEnabled(4096L)) {
                byte b = traceStorage.openSliceCount[i];
                for (int i2 = 0; i2 < b; i2++) {
                    TraceUtilsKt.endSlice();
                }
            }
            int[] iArr = traceStorage.continuationIds;
            if (iArr != null && i < iArr.length) {
                Integer.valueOf(iArr[i]);
            }
        }
        Trace.traceEnd(4096L);
    }

    @Override // kotlinx.coroutines.ThreadContextElement
    public final Object updateThreadContext(CoroutineContext coroutineContext) {
        int[] iArr;
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        TraceStorage traceStorage = (TraceStorage) traceDataThreadLocal.get();
        if (traceStorage == null) {
            return null;
        }
        TraceData traceData = traceStorage.data;
        TraceData traceData2 = this.contextTraceData;
        if (traceData != traceData2) {
            Trace.traceBegin(4096L, this.coroutineTraceName);
            int i = this.continuationCount;
            if (i >= 0) {
                this.continuationCount = i + 1;
            }
            traceStorage.data = traceData2;
            int i2 = traceStorage.contIndex + 1;
            traceStorage.contIndex = i2;
            if (i2 >= 0 && 512 > i2) {
                int length = traceStorage.openSliceCount.length;
                if (i2 >= length) {
                    int max = Math.max(length * 2, 512);
                    byte[] bArr = new byte[max];
                    ArraysKt___ArraysJvmKt.copyInto$default(traceStorage.openSliceCount, 0, 0, bArr, 14);
                    traceStorage.openSliceCount = bArr;
                    int[] iArr2 = traceStorage.continuationIds;
                    if (iArr2 != null) {
                        iArr = new int[max];
                        ArraysKt___ArraysJvmKt.copyInto$default(0, 0, 14, iArr2, iArr);
                    } else {
                        iArr = null;
                    }
                    traceStorage.continuationIds = iArr;
                }
                byte[] bArr2 = traceStorage.openSliceCount;
                TraceData traceData3 = traceStorage.data;
                if (traceData3 != null && Trace.isTagEnabled(4096L) && traceData3.strictMode) {
                    TraceStorage traceStorage2 = (TraceStorage) traceDataThreadLocal.get();
                    if ((traceStorage2 != null ? traceStorage2.data : null) != traceData3) {
                        throw new ConcurrentModificationException("TraceData should only be accessed using the ThreadLocal: CURRENT_TRACE.get(). Accessing TraceData by other means, such as through the TraceContextElement's property may lead to concurrent modification.");
                    }
                }
                bArr2[i2] = 0;
            }
        }
        return traceData;
    }
}
