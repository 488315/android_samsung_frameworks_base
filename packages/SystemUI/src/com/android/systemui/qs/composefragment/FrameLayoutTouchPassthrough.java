package com.android.systemui.qs.composefragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.qs.composefragment.ui.NotificationScrimClipParams;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class FrameLayoutTouchPassthrough extends FrameLayout {
    public final Function0 canScrollForwardQs;
    public final Function0 clippingEnabledProvider;
    public final Flow clippingParams;
    public NotificationScrimClipParams currentClipParams;
    public final Path currentClippingPath;
    public float downY;
    public final Function0 emitMotionEventForFalsing;
    public int lastWidth;
    public boolean preventingIntercept;
    public final int touchSlop;

    /* renamed from: com.android.systemui.qs.composefragment.FrameLayoutTouchPassthrough$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.composefragment.FrameLayoutTouchPassthrough$1$1, reason: invalid class name and collision with other inner class name */
        final class C03801 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ FrameLayoutTouchPassthrough this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03801(FrameLayoutTouchPassthrough frameLayoutTouchPassthrough, Continuation continuation) {
                super(2, continuation);
                this.this$0 = frameLayoutTouchPassthrough;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03801(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03801) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final FrameLayoutTouchPassthrough frameLayoutTouchPassthrough = this.this$0;
                    Flow flow = frameLayoutTouchPassthrough.clippingParams;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.composefragment.FrameLayoutTouchPassthrough.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            NotificationScrimClipParams notificationScrimClipParams = (NotificationScrimClipParams) obj2;
                            FrameLayoutTouchPassthrough frameLayoutTouchPassthrough2 = frameLayoutTouchPassthrough;
                            if (!Intrinsics.areEqual(frameLayoutTouchPassthrough2.currentClipParams, notificationScrimClipParams)) {
                                frameLayoutTouchPassthrough2.currentClipParams = notificationScrimClipParams;
                                frameLayoutTouchPassthrough2.updateClippingPath();
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (flow.collect(flowCollector, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = FrameLayoutTouchPassthrough.this.new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C03801 c03801 = new C03801(FrameLayoutTouchPassthrough.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c03801, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public FrameLayoutTouchPassthrough(Context context, Function0 function0, Flow flow, Function0 function02, Function0 function03) {
        super(context);
        this.clippingEnabledProvider = function0;
        this.clippingParams = flow;
        this.canScrollForwardQs = function02;
        this.emitMotionEventForFalsing = function03;
        RepeatWhenAttachedKt.repeatWhenAttached(this, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(null));
        this.currentClippingPath = new Path();
        this.lastWidth = -1;
        this.currentClipParams = new NotificationScrimClipParams(0, 0, 0, 0, 0, 31, null);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (!this.currentClippingPath.isEmpty()) {
            canvas.clipOutPath(this.currentClippingPath);
        }
        super.dispatchDraw(canvas);
    }

    public final boolean isTransformedTouchPointInView(float f, float f2, View view, PointF pointF) {
        if (!((Boolean) this.clippingEnabledProvider.invoke()).booleanValue() || getTranslationY() + f2 <= this.currentClipParams.top) {
            return super.isTransformedTouchPointInView(f, f2, view, pointF);
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.preventingIntercept = false;
            if (((Boolean) this.canScrollForwardQs.invoke()).booleanValue()) {
                this.preventingIntercept = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            this.downY = motionEvent.getY();
        } else if (actionMasked == 2 && ((int) motionEvent.getY()) - this.downY < (-this.touchSlop) && !((Boolean) this.canScrollForwardQs.invoke()).booleanValue()) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = i3 - i;
        if (this.lastWidth != i5) {
            this.lastWidth = i5;
            updateClippingPath();
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.preventingIntercept = false;
            if (canScrollVertically(1)) {
                this.preventingIntercept = true;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            } else if (!canScrollVertically(-1)) {
                return false;
            }
        } else if (actionMasked == 1 && this.preventingIntercept) {
            this.emitMotionEventForFalsing.invoke();
        }
        return super.onTouchEvent(motionEvent);
    }

    public final void updateClippingPath() {
        this.currentClippingPath.rewind();
        if (((Boolean) this.clippingEnabledProvider.invoke()).booleanValue()) {
            int width = getWidth();
            NotificationScrimClipParams notificationScrimClipParams = this.currentClipParams;
            int i = width + notificationScrimClipParams.rightInset;
            int i2 = -notificationScrimClipParams.leftInset;
            int i3 = notificationScrimClipParams.top;
            int i4 = notificationScrimClipParams.bottom;
            int i5 = notificationScrimClipParams.radius;
            this.currentClippingPath.addRoundRect(i2, i3, i, i4, i5, i5, Path.Direction.CW);
        }
        invalidate();
    }
}
