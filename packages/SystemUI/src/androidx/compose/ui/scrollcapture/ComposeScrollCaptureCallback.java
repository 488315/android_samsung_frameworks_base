package androidx.compose.ui.scrollcapture;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.view.ScrollCaptureCallback;
import android.view.ScrollCaptureSession;
import android.view.View;
import androidx.compose.runtime.MonotonicFrameClockKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import androidx.compose.ui.semantics.SemanticsNode;
import androidx.compose.ui.unit.IntRect;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.NonCancellable;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes.dex */
public final class ComposeScrollCaptureCallback implements ScrollCaptureCallback {
    public final View composeView;
    public final ContextScope coroutineScope;
    public final ScrollCaptureSessionListener listener;
    public final SemanticsNode node;
    public final RelativeScroller scrollTracker;
    public final IntRect viewportBoundsInWindow;

    public interface ScrollCaptureSessionListener {
    }

    /* renamed from: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback$onScrollCaptureEnd$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Runnable $onReady;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$onReady = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ComposeScrollCaptureCallback.this.new AnonymousClass1(this.$onReady, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                RelativeScroller relativeScroller = ComposeScrollCaptureCallback.this.scrollTracker;
                this.label = 1;
                Object objScrollBy = relativeScroller.scrollBy(0.0f - relativeScroller.scrollAmount, this);
                if (objScrollBy != coroutineSingletons) {
                    objScrollBy = Unit.INSTANCE;
                }
                if (objScrollBy == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ((SnapshotMutableStateImpl) ((ScrollCapture) ComposeScrollCaptureCallback.this.listener).scrollCaptureInProgress$delegate).setValue(Boolean.FALSE);
            this.$onReady.run();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback$onScrollCaptureImageRequest$1, reason: invalid class name and case insensitive filesystem */
    final class C07571 extends SuspendLambda implements Function2 {
        final /* synthetic */ Rect $captureArea;
        final /* synthetic */ Consumer<Rect> $onComplete;
        final /* synthetic */ ScrollCaptureSession $session;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07571(ScrollCaptureSession scrollCaptureSession, Rect rect, Consumer<Rect> consumer, Continuation continuation) {
            super(2, continuation);
            this.$session = scrollCaptureSession;
            this.$captureArea = rect;
            this.$onComplete = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ComposeScrollCaptureCallback.this.new C07571(this.$session, this.$captureArea, this.$onComplete, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07571) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ComposeScrollCaptureCallback composeScrollCaptureCallback = ComposeScrollCaptureCallback.this;
                ScrollCaptureSession scrollCaptureSession = this.$session;
                Rect rect = this.$captureArea;
                IntRect intRect = new IntRect(rect.left, rect.top, rect.right, rect.bottom);
                this.label = 1;
                obj = ComposeScrollCaptureCallback.access$onScrollCaptureImageRequest(composeScrollCaptureCallback, scrollCaptureSession, intRect, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            this.$onComplete.accept(RectHelper_androidKt.toAndroidRect((IntRect) obj));
            return Unit.INSTANCE;
        }
    }

    /* renamed from: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback$onScrollCaptureImageRequest$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ComposeScrollCaptureCallback.access$onScrollCaptureImageRequest(ComposeScrollCaptureCallback.this, null, null, this);
        }
    }

    public ComposeScrollCaptureCallback(SemanticsNode semanticsNode, IntRect intRect, CoroutineScope coroutineScope, ScrollCaptureSessionListener scrollCaptureSessionListener, View view) {
        this.node = semanticsNode;
        this.viewportBoundsInWindow = intRect;
        this.listener = scrollCaptureSessionListener;
        this.composeView = view;
        this.coroutineScope = new ContextScope(coroutineScope.getCoroutineContext().plus(DisableAnimationMotionDurationScale.INSTANCE));
        this.scrollTracker = new RelativeScroller(intRect.getHeight(), new ComposeScrollCaptureCallback$scrollTracker$1(this, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$onScrollCaptureImageRequest(ComposeScrollCaptureCallback composeScrollCaptureCallback, ScrollCaptureSession scrollCaptureSession, IntRect intRect, ContinuationImpl continuationImpl) {
        AnonymousClass2 anonymousClass2;
        int i;
        int i2;
        Object objScrollBy;
        ComposeScrollCaptureCallback composeScrollCaptureCallback2;
        ScrollCaptureSession scrollCaptureSession2;
        int i3;
        int i4;
        int iCoerceIn;
        int iCoerceIn2;
        composeScrollCaptureCallback.getClass();
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i5 = anonymousClass2.label;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i5 - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = composeScrollCaptureCallback.new AnonymousClass2(continuationImpl);
            }
        }
        Object obj = anonymousClass2.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i6 = anonymousClass2.label;
        if (i6 == 0) {
            ResultKt.throwOnFailure(obj);
            i = intRect.top;
            RelativeScroller relativeScroller = composeScrollCaptureCallback.scrollTracker;
            anonymousClass2.L$0 = composeScrollCaptureCallback;
            anonymousClass2.L$1 = scrollCaptureSession;
            anonymousClass2.L$2 = intRect;
            anonymousClass2.I$0 = i;
            i2 = intRect.bottom;
            anonymousClass2.I$1 = i2;
            anonymousClass2.label = 1;
            if (i > i2) {
                relativeScroller.getClass();
                throw new IllegalArgumentException(("Expected min=" + i + " ≤ max=" + i2).toString());
            }
            int i7 = i2 - i;
            int i8 = relativeScroller.viewportSize;
            if (i7 > i8) {
                throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i7, i8, "Expected range (", ") to be ≤ viewportSize=").toString());
            }
            float f = i;
            float f2 = relativeScroller.scrollAmount;
            if (f < f2 || i2 > i8 + f2) {
                objScrollBy = relativeScroller.scrollBy((f < f2 ? i : i2 - i8) - f2, anonymousClass2);
                if (objScrollBy != coroutineSingletons) {
                    objScrollBy = Unit.INSTANCE;
                }
                if (objScrollBy != coroutineSingletons) {
                    objScrollBy = Unit.INSTANCE;
                }
            } else {
                objScrollBy = Unit.INSTANCE;
            }
            if (objScrollBy != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i6 != 1) {
            if (i6 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i4 = anonymousClass2.I$1;
            i3 = anonymousClass2.I$0;
            intRect = (IntRect) anonymousClass2.L$2;
            scrollCaptureSession2 = (ScrollCaptureSession) anonymousClass2.L$1;
            composeScrollCaptureCallback2 = (ComposeScrollCaptureCallback) anonymousClass2.L$0;
            ResultKt.throwOnFailure(obj);
            RelativeScroller relativeScroller2 = composeScrollCaptureCallback2.scrollTracker;
            iCoerceIn = RangesKt___RangesKt.coerceIn(i3 - MathKt__MathJVMKt.roundToInt(relativeScroller2.scrollAmount), 0, relativeScroller2.viewportSize);
            RelativeScroller relativeScroller3 = composeScrollCaptureCallback2.scrollTracker;
            iCoerceIn2 = RangesKt___RangesKt.coerceIn(i4 - MathKt__MathJVMKt.roundToInt(relativeScroller3.scrollAmount), 0, relativeScroller3.viewportSize);
            IntRect intRect2 = new IntRect(intRect.left, iCoerceIn, intRect.right, iCoerceIn2);
            int i9 = intRect2.top;
            if (iCoerceIn != iCoerceIn2) {
                IntRect.Companion.getClass();
                return IntRect.Zero;
            }
            Canvas canvasLockHardwareCanvas = scrollCaptureSession2.getSurface().lockHardwareCanvas();
            try {
                canvasLockHardwareCanvas.save();
                int i10 = intRect2.left;
                canvasLockHardwareCanvas.translate(-i10, -i9);
                IntRect intRect3 = composeScrollCaptureCallback2.viewportBoundsInWindow;
                canvasLockHardwareCanvas.translate(-intRect3.left, -intRect3.top);
                composeScrollCaptureCallback2.composeView.getRootView().draw(canvasLockHardwareCanvas);
                scrollCaptureSession2.getSurface().unlockCanvasAndPost(canvasLockHardwareCanvas);
                int iRoundToInt = MathKt__MathJVMKt.roundToInt(composeScrollCaptureCallback2.scrollTracker.scrollAmount);
                return new IntRect(i10, i9 + iRoundToInt, intRect2.right, intRect2.bottom + iRoundToInt);
            } catch (Throwable th) {
                scrollCaptureSession2.getSurface().unlockCanvasAndPost(canvasLockHardwareCanvas);
                throw th;
            }
        }
        int i11 = anonymousClass2.I$1;
        int i12 = anonymousClass2.I$0;
        intRect = (IntRect) anonymousClass2.L$2;
        ScrollCaptureSession scrollCaptureSession3 = (ScrollCaptureSession) anonymousClass2.L$1;
        ComposeScrollCaptureCallback composeScrollCaptureCallback3 = (ComposeScrollCaptureCallback) anonymousClass2.L$0;
        ResultKt.throwOnFailure(obj);
        i2 = i11;
        i = i12;
        scrollCaptureSession = scrollCaptureSession3;
        composeScrollCaptureCallback = composeScrollCaptureCallback3;
        AnonymousClass3 anonymousClass3 = new Function1() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback.onScrollCaptureImageRequest.3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                ((Number) obj2).longValue();
                return Unit.INSTANCE;
            }
        };
        anonymousClass2.L$0 = composeScrollCaptureCallback;
        anonymousClass2.L$1 = scrollCaptureSession;
        anonymousClass2.L$2 = intRect;
        anonymousClass2.I$0 = i;
        anonymousClass2.I$1 = i2;
        anonymousClass2.label = 2;
        if (MonotonicFrameClockKt.getMonotonicFrameClock(anonymousClass2.getContext()).withFrameNanos(anonymousClass3, anonymousClass2) != coroutineSingletons) {
            composeScrollCaptureCallback2 = composeScrollCaptureCallback;
            scrollCaptureSession2 = scrollCaptureSession;
            i3 = i;
            i4 = i2;
            RelativeScroller relativeScroller22 = composeScrollCaptureCallback2.scrollTracker;
            iCoerceIn = RangesKt___RangesKt.coerceIn(i3 - MathKt__MathJVMKt.roundToInt(relativeScroller22.scrollAmount), 0, relativeScroller22.viewportSize);
            RelativeScroller relativeScroller32 = composeScrollCaptureCallback2.scrollTracker;
            iCoerceIn2 = RangesKt___RangesKt.coerceIn(i4 - MathKt__MathJVMKt.roundToInt(relativeScroller32.scrollAmount), 0, relativeScroller32.viewportSize);
            IntRect intRect22 = new IntRect(intRect.left, iCoerceIn, intRect.right, iCoerceIn2);
            int i92 = intRect22.top;
            if (iCoerceIn != iCoerceIn2) {
            }
        }
        return coroutineSingletons;
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureEnd(Runnable runnable) {
        BuildersKt.launch$default(this.coroutineScope, NonCancellable.INSTANCE, null, new AnonymousClass1(runnable, null), 2);
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureImageRequest(ScrollCaptureSession scrollCaptureSession, final CancellationSignal cancellationSignal, Rect rect, Consumer consumer) {
        final StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(this.coroutineScope, null, null, new C07571(scrollCaptureSession, rect, consumer, null), 3);
        standaloneCoroutineLaunch$default.invokeOnCompletion(new Function1() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback_androidKt$launchWithCancellationSignal$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                if (((Throwable) obj) != null) {
                    cancellationSignal.cancel();
                }
                return Unit.INSTANCE;
            }
        });
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.compose.ui.scrollcapture.ComposeScrollCaptureCallback_androidKt$$ExternalSyntheticLambda0
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                standaloneCoroutineLaunch$default.cancel(null);
            }
        });
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureSearch(CancellationSignal cancellationSignal, Consumer consumer) {
        consumer.accept(RectHelper_androidKt.toAndroidRect(this.viewportBoundsInWindow));
    }

    @Override // android.view.ScrollCaptureCallback
    public final void onScrollCaptureStart(ScrollCaptureSession scrollCaptureSession, CancellationSignal cancellationSignal, Runnable runnable) {
        this.scrollTracker.scrollAmount = 0.0f;
        ((SnapshotMutableStateImpl) ((ScrollCapture) this.listener).scrollCaptureInProgress$delegate).setValue(Boolean.TRUE);
        runnable.run();
    }
}
