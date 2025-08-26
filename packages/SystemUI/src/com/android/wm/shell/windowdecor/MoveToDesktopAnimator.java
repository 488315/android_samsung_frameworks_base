package com.android.wm.shell.windowdecor;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.VelocityTracker;
import com.android.systemui.R;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
public final class MoveToDesktopAnimator {
    public boolean allowSurfaceChangesOnMove;
    public final float cornerRadius;
    public final ValueAnimator dragToDesktopAnimator;
    public final PointF mostRecentInput;
    public final PointF position;
    public final Rect startBounds;
    public final SurfaceControl taskSurface;
    public final Function0 transactionFactory;
    public final VelocityTracker velocityTracker;

    /* renamed from: com.android.wm.shell.windowdecor.MoveToDesktopAnimator$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(0, SurfaceControl.Transaction.class, "<init>", "<init>()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new SurfaceControl.Transaction();
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MoveToDesktopAnimator(Context context, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        this(context, rect, runningTaskInfo, surfaceControl, null, 16, null);
    }

    public final void cancelAnimator() {
        this.velocityTracker.clear();
        this.dragToDesktopAnimator.cancel();
    }

    public final float getScale() {
        return ((Float) this.dragToDesktopAnimator.getAnimatedValue()).floatValue();
    }

    public final void setTaskPosition(float f, float f2) {
        this.position.x = f - ((((Float) this.dragToDesktopAnimator.getAnimatedValue()).floatValue() * this.startBounds.width()) / 2);
        this.position.y = f2;
    }

    public MoveToDesktopAnimator(Context context, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Function0 function0) {
        this.startBounds = rect;
        this.taskSurface = surfaceControl;
        this.transactionFactory = function0;
        this.mostRecentInput = new PointF();
        this.velocityTracker = VelocityTracker.obtain();
        ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.4f).setDuration(336L);
        final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MoveToDesktopAnimator$dragToDesktopAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (this.this$0.taskSurface.isValid()) {
                    MoveToDesktopAnimator moveToDesktopAnimator = this.this$0;
                    PointF pointF = moveToDesktopAnimator.mostRecentInput;
                    moveToDesktopAnimator.setTaskPosition(pointF.x, pointF.y);
                    SurfaceControl.Transaction transaction2 = transaction;
                    MoveToDesktopAnimator moveToDesktopAnimator2 = this.this$0;
                    SurfaceControl.Transaction scale = transaction2.setScale(moveToDesktopAnimator2.taskSurface, moveToDesktopAnimator2.getScale(), this.this$0.getScale());
                    MoveToDesktopAnimator moveToDesktopAnimator3 = this.this$0;
                    SurfaceControl.Transaction cornerRadius = scale.setCornerRadius(moveToDesktopAnimator3.taskSurface, moveToDesktopAnimator3.cornerRadius);
                    MoveToDesktopAnimator moveToDesktopAnimator4 = this.this$0;
                    SurfaceControl.Transaction frameTimeline = cornerRadius.setScale(moveToDesktopAnimator4.taskSurface, moveToDesktopAnimator4.getScale(), this.this$0.getScale()).setFrameTimeline(Choreographer.getInstance().getVsyncId());
                    MoveToDesktopAnimator moveToDesktopAnimator5 = this.this$0;
                    SurfaceControl surfaceControl2 = moveToDesktopAnimator5.taskSurface;
                    PointF pointF2 = moveToDesktopAnimator5.position;
                    frameTimeline.setPosition(surfaceControl2, pointF2.x, pointF2.y).apply();
                }
            }
        });
        this.dragToDesktopAnimator = duration;
        this.position = new PointF(0.0f, 0.0f);
        this.cornerRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_dragged_task_radius);
    }

    public /* synthetic */ MoveToDesktopAnimator(Context context, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, rect, runningTaskInfo, surfaceControl, (i & 16) != 0 ? AnonymousClass1.INSTANCE : function0);
    }
}
