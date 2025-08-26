package com.android.app.tracing.coroutines;

import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.lang.StackWalker;
import java.util.ConcurrentModificationException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CopyableThreadContextElement;

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
        TraceData traceData;
        String strM;
        super(str);
        this.isRoot = z;
        this.walkStackForDefaultNames = z3;
        this.shouldIgnoreClassName = function1;
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        int iNextInt = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        this.currentId = iNextInt;
        String str3 = (z ? "ROOT-" : "") + str + ";c=" + iNextInt + ";p=" + (num == 0 ? SignalSeverity.NONE : num);
        Trace.traceBegin(4096L, "TCE#init;" + str3);
        if (z) {
            traceData = null;
        } else {
            traceData = new TraceData(iNextInt, str2 != null);
        }
        this.contextTraceData = traceData;
        int i2 = -1;
        if (str2 == null) {
            strM = AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("coroutine execution;", str3, i == -1 ? "" : MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ";d="), z2 ? ";n=" : "");
        } else {
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, str);
        }
        this.coroutineTraceName = strM;
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

    /* JADX WARN: Multi-variable type inference failed */
    public final TraceContextElement createChildContext(String str) {
        String str2;
        if (str == null && this.walkStackForDefaultNames) {
            final Function1 function1 = this.shouldIgnoreClassName;
            TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
            Trace.traceBegin(4096L, "walkStackForClassName");
            try {
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                ref$ObjectRef.element = "";
                StackWalker stackWalker = StackWalker.getInstance();
                final Function1 function12 = new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final Function1 function13 = function1;
                        final int i = 0;
                        final Function1 function14 = new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda1
                            /* JADX WARN: Type inference failed for: r4v4, types: [T, java.lang.String] */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                Function1 function15;
                                StackWalker.StackFrame stackFrame = (StackWalker.StackFrame) obj2;
                                switch (i) {
                                    case 0:
                                        String className = stackFrame.getClassName();
                                        className.getClass();
                                        return Boolean.valueOf(className.startsWith("kotlin") || className.startsWith("com.android.app.tracing.") || ((function15 = (Function1) function13) != null && ((Boolean) function15.mo781invoke(className)).booleanValue()));
                                    default:
                                        String className2 = stackFrame.getClassName();
                                        int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(className2, ".", 6);
                                        if (iLastIndexOf$default != -1) {
                                            className2 = className2.substring(1 + iLastIndexOf$default, className2.length());
                                        }
                                        ((Ref$ObjectRef) function13).element = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(className2, ".", stackFrame.getMethodName());
                                        return Unit.INSTANCE;
                                }
                            }
                        };
                        Optional optionalFindFirst = ((Stream) obj).dropWhile(new Predicate() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Predicate$0
                            @Override // java.util.function.Predicate
                            public final /* synthetic */ boolean test(Object obj2) {
                                return ((Boolean) function14.mo781invoke(obj2)).booleanValue();
                            }
                        }).findFirst();
                        final Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                        final int i2 = 1;
                        final Function1 function15 = new Function1() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$$ExternalSyntheticLambda1
                            /* JADX WARN: Type inference failed for: r4v4, types: [T, java.lang.String] */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                Function1 function152;
                                StackWalker.StackFrame stackFrame = (StackWalker.StackFrame) obj2;
                                switch (i2) {
                                    case 0:
                                        String className = stackFrame.getClassName();
                                        className.getClass();
                                        return Boolean.valueOf(className.startsWith("kotlin") || className.startsWith("com.android.app.tracing.") || ((function152 = (Function1) ref$ObjectRef2) != null && ((Boolean) function152.mo781invoke(className)).booleanValue()));
                                    default:
                                        String className2 = stackFrame.getClassName();
                                        int iLastIndexOf$default = StringsKt__StringsKt.lastIndexOf$default(className2, ".", 6);
                                        if (iLastIndexOf$default != -1) {
                                            className2 = className2.substring(1 + iLastIndexOf$default, className2.length());
                                        }
                                        ((Ref$ObjectRef) ref$ObjectRef2).element = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(className2, ".", stackFrame.getMethodName());
                                        return Unit.INSTANCE;
                                }
                            }
                        };
                        optionalFindFirst.ifPresent(new Consumer() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Consumer$0
                            @Override // java.util.function.Consumer
                            public final /* synthetic */ void accept(Object obj2) {
                                function15.mo781invoke(obj2);
                            }
                        });
                        return Unit.INSTANCE;
                    }
                };
                stackWalker.walk(new Function() { // from class: com.android.app.tracing.coroutines.TraceContextElementKt$sam$java_util_function_Function$0
                    @Override // java.util.function.Function
                    public final /* synthetic */ Object apply(Object obj) {
                        return function12.mo781invoke(obj);
                    }
                });
                str = (String) ref$ObjectRef.element;
            } catch (Exception unused) {
            } finally {
                Trace.traceEnd(4096L);
            }
        } else if (str == null) {
            str = "";
        }
        boolean z = this.continuationCount >= 0;
        Integer numValueOf = Integer.valueOf(this.currentId);
        AtomicInteger atomicInteger = this.childCoroutineCount;
        if (atomicInteger != null) {
            int iIncrementAndGet = atomicInteger.incrementAndGet();
            str2 = (this.isRoot ? "" : TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.coroutineTraceName, ":")) + iIncrementAndGet + "^";
        } else {
            str2 = null;
        }
        return new TraceContextElement(str, false, z, this.walkStackForDefaultNames, this.shouldIgnoreClassName, numValueOf, str2, this.childDepth);
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
                    int iMax = Math.max(length * 2, 512);
                    byte[] bArr = new byte[iMax];
                    ArraysKt___ArraysJvmKt.copyInto$default(traceStorage.openSliceCount, 0, 0, bArr, 14);
                    traceStorage.openSliceCount = bArr;
                    int[] iArr2 = traceStorage.continuationIds;
                    if (iArr2 != null) {
                        iArr = new int[iMax];
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
