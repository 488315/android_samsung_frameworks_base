package androidx.compose.ui.input.pointer;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntSize;
import java.util.ArrayList;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SuspendingPointerInputModifierNodeImpl extends Modifier.Node implements SuspendingPointerInputModifierNode, PointerInputScope, Density {
    public final Function2 _deprecatedPointerInputHandler;
    public PointerInputEventHandler _pointerInputEventHandler;
    public long boundsSize;
    public PointerEvent currentEvent;
    public final MutableVector dispatchingPointerHandlers;
    public Object key1;
    public Object key2;
    public Object[] keys;
    public PointerEvent lastPointerEvent;
    public final MutableVector pointerHandlers;
    public final MutableVector pointerHandlersLock;
    public StandaloneCoroutine pointerInputJob;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PointerEventHandlerCoroutine<R> implements AwaitPointerEventScope, Density, Continuation {
        public final /* synthetic */ SuspendingPointerInputModifierNodeImpl $$delegate_0;
        public final Continuation completion;
        public CancellableContinuationImpl pointerAwaiter;
        public PointerEventPass awaitPass = PointerEventPass.Main;
        public final EmptyCoroutineContext context = EmptyCoroutineContext.INSTANCE;

        public PointerEventHandlerCoroutine(Continuation continuation) {
            this.completion = continuation;
            this.$$delegate_0 = SuspendingPointerInputModifierNodeImpl.this;
        }

        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        public final Object awaitPointerEvent(PointerEventPass pointerEventPass, Continuation continuation) {
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
            cancellableContinuationImpl.initCancellability();
            this.awaitPass = pointerEventPass;
            this.pointerAwaiter = cancellableContinuationImpl;
            Object result = cancellableContinuationImpl.getResult();
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            return result;
        }

        @Override // kotlin.coroutines.Continuation
        public final CoroutineContext getContext() {
            return this.context;
        }

        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        public final PointerEvent getCurrentEvent() {
            return SuspendingPointerInputModifierNodeImpl.this.currentEvent;
        }

        @Override // androidx.compose.ui.unit.Density
        public final float getDensity() {
            return this.$$delegate_0.getDensity();
        }

        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        /* renamed from: getExtendedTouchPadding-NH-jbRc */
        public final long mo584getExtendedTouchPaddingNHjbRc() {
            SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImpl = SuspendingPointerInputModifierNodeImpl.this;
            suspendingPointerInputModifierNodeImpl.getClass();
            long mo58toSizeXkaWNTQ = suspendingPointerInputModifierNodeImpl.mo58toSizeXkaWNTQ(DelegatableNodeKt.requireLayoutNode(suspendingPointerInputModifierNodeImpl).viewConfiguration.mo643getMinimumTouchTargetSizeMYxV2XQ());
            long j = suspendingPointerInputModifierNodeImpl.boundsSize;
            float max = Math.max(0.0f, Float.intBitsToFloat((int) (mo58toSizeXkaWNTQ >> 32)) - ((int) (j >> 32))) / 2.0f;
            float max2 = Math.max(0.0f, Float.intBitsToFloat((int) (mo58toSizeXkaWNTQ & 4294967295L)) - ((int) (j & 4294967295L))) / 2.0f;
            long floatToRawIntBits = (Float.floatToRawIntBits(max) << 32) | (Float.floatToRawIntBits(max2) & 4294967295L);
            Size.Companion companion = Size.Companion;
            return floatToRawIntBits;
        }

        @Override // androidx.compose.ui.unit.FontScaling
        public final float getFontScale() {
            return this.$$delegate_0.getFontScale();
        }

        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        /* renamed from: getSize-YbymL2g */
        public final long mo585getSizeYbymL2g() {
            return SuspendingPointerInputModifierNodeImpl.this.boundsSize;
        }

        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        public final ViewConfiguration getViewConfiguration() {
            SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImpl = SuspendingPointerInputModifierNodeImpl.this;
            suspendingPointerInputModifierNodeImpl.getClass();
            return DelegatableNodeKt.requireLayoutNode(suspendingPointerInputModifierNodeImpl).viewConfiguration;
        }

        @Override // kotlin.coroutines.Continuation
        public final void resumeWith(Object obj) {
            SuspendingPointerInputModifierNodeImpl suspendingPointerInputModifierNodeImpl = SuspendingPointerInputModifierNodeImpl.this;
            synchronized (suspendingPointerInputModifierNodeImpl.pointerHandlersLock) {
                suspendingPointerInputModifierNodeImpl.pointerHandlers.remove(this);
                Unit unit = Unit.INSTANCE;
            }
            this.completion.resumeWith(obj);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: roundToPx-0680j_4 */
        public final int mo51roundToPx0680j_4(float f) {
            return this.$$delegate_0.mo51roundToPx0680j_4(f);
        }

        @Override // androidx.compose.ui.unit.FontScaling
        /* renamed from: toDp-GaN1DYA */
        public final float mo52toDpGaN1DYA(long j) {
            return this.$$delegate_0.mo52toDpGaN1DYA(j);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toDp-u2uoSUM */
        public final float mo53toDpu2uoSUM(float f) {
            return this.$$delegate_0.mo53toDpu2uoSUM(f);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toDpSize-k-rfVVM */
        public final long mo55toDpSizekrfVVM(long j) {
            return this.$$delegate_0.mo55toDpSizekrfVVM(j);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toPx--R2X_6o */
        public final float mo56toPxR2X_6o(long j) {
            return this.$$delegate_0.mo56toPxR2X_6o(j);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toPx-0680j_4 */
        public final float mo57toPx0680j_4(float f) {
            return this.$$delegate_0.getDensity() * f;
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toSize-XkaWNTQ */
        public final long mo58toSizeXkaWNTQ(long j) {
            return this.$$delegate_0.mo58toSizeXkaWNTQ(j);
        }

        @Override // androidx.compose.ui.unit.FontScaling
        /* renamed from: toSp-0xMU5do */
        public final long mo59toSp0xMU5do(float f) {
            return this.$$delegate_0.mo59toSp0xMU5do(f);
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toSp-kPz2Gy4 */
        public final long mo60toSpkPz2Gy4(float f) {
            return this.$$delegate_0.mo60toSpkPz2Gy4(f);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0035  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object withTimeout(long r8, kotlin.jvm.functions.Function2 r10, kotlin.coroutines.jvm.internal.BaseContinuationImpl r11) {
            /*
                r7 = this;
                boolean r0 = r11 instanceof androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$1
                if (r0 == 0) goto L13
                r0 = r11
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$1 r0 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$1 r0 = new androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$1
                r0.<init>(r7, r11)
            L18:
                java.lang.Object r11 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L35
                if (r2 != r3) goto L2d
                java.lang.Object r7 = r0.L$0
                kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
                kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L2b
                goto L6e
            L2b:
                r8 = move-exception
                goto L78
            L2d:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L35:
                kotlin.ResultKt.throwOnFailure(r11)
                r4 = 0
                int r11 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
                if (r11 > 0) goto L51
                kotlinx.coroutines.CancellableContinuationImpl r11 = r7.pointerAwaiter
                if (r11 == 0) goto L51
                int r2 = kotlin.Result.$r8$clinit
                androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException r2 = new androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException
                r2.<init>(r8)
                kotlin.Result$Failure r4 = new kotlin.Result$Failure
                r4.<init>(r2)
                r11.resumeWith(r4)
            L51:
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl r11 = androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.this
                kotlinx.coroutines.CoroutineScope r11 = r11.getCoroutineScope()
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$job$1 r2 = new androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeout$job$1
                r4 = 0
                r2.<init>(r8, r7, r4)
                r8 = 3
                kotlinx.coroutines.StandaloneCoroutine r8 = kotlinx.coroutines.BuildersKt.launch$default(r11, r4, r4, r2, r8)
                r0.L$0 = r8     // Catch: java.lang.Throwable -> L74
                r0.label = r3     // Catch: java.lang.Throwable -> L74
                java.lang.Object r11 = r10.invoke(r7, r0)     // Catch: java.lang.Throwable -> L74
                if (r11 != r1) goto L6d
                return r1
            L6d:
                r7 = r8
            L6e:
                androidx.compose.ui.input.pointer.CancelTimeoutCancellationException r8 = androidx.compose.ui.input.pointer.CancelTimeoutCancellationException.INSTANCE
                r7.cancel(r8)
                return r11
            L74:
                r7 = move-exception
                r6 = r8
                r8 = r7
                r7 = r6
            L78:
                androidx.compose.ui.input.pointer.CancelTimeoutCancellationException r9 = androidx.compose.ui.input.pointer.CancelTimeoutCancellationException.INSTANCE
                r7.cancel(r9)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine.withTimeout(long, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
        @Override // androidx.compose.ui.input.pointer.AwaitPointerEventScope
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object withTimeoutOrNull(long r5, kotlin.jvm.functions.Function2 r7, kotlin.coroutines.jvm.internal.BaseContinuationImpl r8) {
            /*
                r4 = this;
                boolean r0 = r8 instanceof androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeoutOrNull$1
                if (r0 == 0) goto L13
                r0 = r8
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeoutOrNull$1 r0 = (androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeoutOrNull$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeoutOrNull$1 r0 = new androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$PointerEventHandlerCoroutine$withTimeoutOrNull$1
                r0.<init>(r4, r8)
            L18:
                java.lang.Object r8 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L2f
                if (r2 != r3) goto L27
                kotlin.ResultKt.throwOnFailure(r8)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L3c
                return r8
            L27:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L2f:
                kotlin.ResultKt.throwOnFailure(r8)
                r0.label = r3     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L3c
                java.lang.Object r4 = r4.withTimeout(r5, r7, r0)     // Catch: androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException -> L3c
                if (r4 != r1) goto L3b
                return r1
            L3b:
                return r4
            L3c:
                r4 = 0
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine.withTimeoutOrNull(long, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.BaseContinuationImpl):java.lang.Object");
        }

        @Override // androidx.compose.ui.unit.Density
        /* renamed from: toDp-u2uoSUM */
        public final float mo54toDpu2uoSUM(int i) {
            return this.$$delegate_0.mo54toDpu2uoSUM(i);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PointerEventPass.values().length];
            try {
                iArr[PointerEventPass.Initial.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PointerEventPass.Final.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PointerEventPass.Main.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ SuspendingPointerInputModifierNodeImpl(Object obj, Object obj2, Object[] objArr, PointerInputEventHandler pointerInputEventHandler, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : obj, (i & 2) != 0 ? null : obj2, (i & 4) != 0 ? null : objArr, pointerInputEventHandler);
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputScope
    public final Object awaitPointerEventScope(Function2 function2, Continuation continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final PointerEventHandlerCoroutine pointerEventHandlerCoroutine = new PointerEventHandlerCoroutine(cancellableContinuationImpl);
        synchronized (this.pointerHandlersLock) {
            this.pointerHandlers.add(pointerEventHandlerCoroutine);
            SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(function2, pointerEventHandlerCoroutine, pointerEventHandlerCoroutine)), CoroutineSingletons.COROUTINE_SUSPENDED);
            int i = Result.$r8$clinit;
            safeContinuation.resumeWith(Unit.INSTANCE);
        }
        cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl$awaitPointerEventScope$2$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Throwable th = (Throwable) obj;
                SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine<Object> pointerEventHandlerCoroutine2 = pointerEventHandlerCoroutine;
                CancellableContinuationImpl cancellableContinuationImpl2 = pointerEventHandlerCoroutine2.pointerAwaiter;
                if (cancellableContinuationImpl2 != null) {
                    cancellableContinuationImpl2.cancel(th);
                }
                pointerEventHandlerCoroutine2.pointerAwaiter = null;
                return Unit.INSTANCE;
            }
        });
        return cancellableContinuationImpl.getResult();
    }

    public final void dispatchPointerEvent(PointerEvent pointerEvent, PointerEventPass pointerEventPass) {
        CancellableContinuationImpl cancellableContinuationImpl;
        CancellableContinuationImpl cancellableContinuationImpl2;
        synchronized (this.pointerHandlersLock) {
            MutableVector mutableVector = this.dispatchingPointerHandlers;
            mutableVector.addAll(mutableVector.size, this.pointerHandlers);
        }
        try {
            int i = WhenMappings.$EnumSwitchMapping$0[pointerEventPass.ordinal()];
            if (i == 1 || i == 2) {
                MutableVector mutableVector2 = this.dispatchingPointerHandlers;
                Object[] objArr = mutableVector2.content;
                int i2 = mutableVector2.size;
                for (int i3 = 0; i3 < i2; i3++) {
                    PointerEventHandlerCoroutine pointerEventHandlerCoroutine = (PointerEventHandlerCoroutine) objArr[i3];
                    if (pointerEventPass == pointerEventHandlerCoroutine.awaitPass && (cancellableContinuationImpl = pointerEventHandlerCoroutine.pointerAwaiter) != null) {
                        pointerEventHandlerCoroutine.pointerAwaiter = null;
                        int i4 = Result.$r8$clinit;
                        cancellableContinuationImpl.resumeWith(pointerEvent);
                    }
                }
            } else if (i == 3) {
                MutableVector mutableVector3 = this.dispatchingPointerHandlers;
                int i5 = mutableVector3.size - 1;
                Object[] objArr2 = mutableVector3.content;
                if (i5 < objArr2.length) {
                    while (i5 >= 0) {
                        PointerEventHandlerCoroutine pointerEventHandlerCoroutine2 = (PointerEventHandlerCoroutine) objArr2[i5];
                        if (pointerEventPass == pointerEventHandlerCoroutine2.awaitPass && (cancellableContinuationImpl2 = pointerEventHandlerCoroutine2.pointerAwaiter) != null) {
                            pointerEventHandlerCoroutine2.pointerAwaiter = null;
                            int i6 = Result.$r8$clinit;
                            cancellableContinuationImpl2.resumeWith(pointerEvent);
                        }
                        i5--;
                    }
                }
            }
        } finally {
            this.dispatchingPointerHandlers.clear();
        }
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return DelegatableNodeKt.requireLayoutNode(this).density.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return DelegatableNodeKt.requireLayoutNode(this).density.getFontScale();
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputScope
    /* renamed from: getSize-YbymL2g */
    public final long mo50getSizeYbymL2g() {
        return this.boundsSize;
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputScope
    public final ViewConfiguration getViewConfiguration() {
        return DelegatableNodeKt.requireLayoutNode(this).viewConfiguration;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onCancelPointerInput() {
        PointerEvent pointerEvent = this.lastPointerEvent;
        if (pointerEvent == null) {
            return;
        }
        List list = pointerEvent.changes;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (((PointerInputChange) list.get(i)).pressed) {
                List list2 = pointerEvent.changes;
                ArrayList arrayList = new ArrayList(list2.size());
                int size2 = list2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    PointerInputChange pointerInputChange = (PointerInputChange) list2.get(i2);
                    long j = pointerInputChange.id;
                    long j2 = pointerInputChange.uptimeMillis;
                    long j3 = pointerInputChange.position;
                    float f = pointerInputChange.pressure;
                    boolean z = pointerInputChange.pressed;
                    arrayList.add(new PointerInputChange(j, j2, j3, false, f, j2, j3, z, z, pointerInputChange.type, 0L, 1024, (DefaultConstructorMarker) null));
                }
                PointerEvent pointerEvent2 = new PointerEvent(arrayList);
                this.currentEvent = pointerEvent2;
                dispatchPointerEvent(pointerEvent2, PointerEventPass.Initial);
                dispatchPointerEvent(pointerEvent2, PointerEventPass.Main);
                dispatchPointerEvent(pointerEvent2, PointerEventPass.Final);
                this.lastPointerEvent = null;
                return;
            }
        }
    }

    @Override // androidx.compose.ui.node.DelegatableNode, androidx.compose.ui.node.PointerInputModifierNode
    public final void onDensityChange() {
        resetPointerInputHandler();
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        resetPointerInputHandler();
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    /* renamed from: onPointerEvent-H0pRuoY */
    public final void mo16onPointerEventH0pRuoY(PointerEvent pointerEvent, PointerEventPass pointerEventPass, long j) {
        this.boundsSize = j;
        if (pointerEventPass == PointerEventPass.Initial) {
            this.currentEvent = pointerEvent;
        }
        if (this.pointerInputJob == null) {
            this.pointerInputJob = BuildersKt.launch$default(getCoroutineScope(), null, CoroutineStart.UNDISPATCHED, new SuspendingPointerInputModifierNodeImpl$onPointerEvent$1(this, null), 1);
        }
        dispatchPointerEvent(pointerEvent, pointerEventPass);
        List list = pointerEvent.changes;
        int size = list.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                pointerEvent = null;
                break;
            } else if (!PointerEventKt.changedToUpIgnoreConsumed((PointerInputChange) list.get(i))) {
                break;
            } else {
                i++;
            }
        }
        this.lastPointerEvent = pointerEvent;
    }

    @Override // androidx.compose.ui.node.PointerInputModifierNode
    public final void onViewConfigurationChange() {
        resetPointerInputHandler();
    }

    public final void resetPointerInputHandler() {
        StandaloneCoroutine standaloneCoroutine = this.pointerInputJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancelInternal(new PointerInputResetException());
            this.pointerInputJob = null;
        }
    }

    public SuspendingPointerInputModifierNodeImpl(Object obj, Object obj2, Object[] objArr, PointerInputEventHandler pointerInputEventHandler) {
        this.key1 = obj;
        this.key2 = obj2;
        this.keys = objArr;
        this._pointerInputEventHandler = pointerInputEventHandler;
        this.currentEvent = SuspendingPointerInputFilterKt.EmptyPointerEvent;
        MutableVector mutableVector = new MutableVector(new PointerEventHandlerCoroutine[16], 0);
        this.pointerHandlers = mutableVector;
        this.pointerHandlersLock = mutableVector;
        this.dispatchingPointerHandlers = new MutableVector(new PointerEventHandlerCoroutine[16], 0);
        IntSize.Companion.getClass();
        this.boundsSize = 0L;
    }

    public SuspendingPointerInputModifierNodeImpl(Object obj, Object obj2, Object[] objArr, Function2 function2) {
        this(obj, obj2, objArr, new PointerInputEventHandler() { // from class: androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl.1
            @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
            public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                return Unit.INSTANCE;
            }
        });
        this._deprecatedPointerInputHandler = function2;
    }

    @Override // androidx.compose.ui.input.pointer.PointerInputScope
    public final void setInterceptOutOfBoundsChildEvents() {
    }
}
