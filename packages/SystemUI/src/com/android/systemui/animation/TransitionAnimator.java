package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.animation.Interpolator;
import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.shared.Flags;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class TransitionAnimator {
    public static final Companion Companion = new Companion(null);
    public static final PorterDuffXfermode SRC_MODE = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    public final Interpolators interpolators;
    public final Executor mainExecutor;
    public final Timings timings;
    public final int[] transitionContainerLocation = new int[2];
    public final float[] cornerRadii = new float[8];

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static float getProgress(Timings timings, float f, long j, long j2) {
            return MathUtils.constrain(((f * timings.totalDuration) - j) / j2, 0.0f, 1.0f);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class State {
        public int bottom;
        public float bottomCornerRadius;
        public int left;
        public int right;
        public int top;
        public float topCornerRadius;
        public boolean visible;

        public State() {
            this(0, 0, 0, 0, 0.0f, 0.0f, 63, null);
        }

        public final int getHeight() {
            return this.bottom - this.top;
        }

        public final int getWidth() {
            return this.right - this.left;
        }

        public State(int i, int i2, int i3, int i4, float f, float f2) {
            this.top = i;
            this.bottom = i2;
            this.left = i3;
            this.right = i4;
            this.topCornerRadius = f;
            this.bottomCornerRadius = f2;
            this.visible = true;
        }

        public /* synthetic */ State(int i, int i2, int i3, int i4, float f, float f2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4, (i5 & 16) != 0 ? 0.0f : f, (i5 & 32) != 0 ? 0.0f : f2);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Timings {
        public final long contentAfterFadeInDelay;
        public final long contentAfterFadeInDuration;
        public final long contentBeforeFadeOutDelay;
        public final long contentBeforeFadeOutDuration;
        public final long totalDuration;

        public Timings(long j, long j2, long j3, long j4, long j5) {
            this.totalDuration = j;
            this.contentBeforeFadeOutDelay = j2;
            this.contentBeforeFadeOutDuration = j3;
            this.contentAfterFadeInDelay = j4;
            this.contentAfterFadeInDuration = j5;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Timings)) {
                return false;
            }
            Timings timings = (Timings) obj;
            return this.totalDuration == timings.totalDuration && this.contentBeforeFadeOutDelay == timings.contentBeforeFadeOutDelay && this.contentBeforeFadeOutDuration == timings.contentBeforeFadeOutDuration && this.contentAfterFadeInDelay == timings.contentAfterFadeInDelay && this.contentAfterFadeInDuration == timings.contentAfterFadeInDuration;
        }

        public final int hashCode() {
            return Long.hashCode(this.contentAfterFadeInDuration) + Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Scale$$ExternalSyntheticOutline0.m(Long.hashCode(this.totalDuration) * 31, 31, this.contentBeforeFadeOutDelay), 31, this.contentBeforeFadeOutDuration), 31, this.contentAfterFadeInDelay);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Timings(totalDuration=");
            sb.append(this.totalDuration);
            sb.append(", contentBeforeFadeOutDelay=");
            sb.append(this.contentBeforeFadeOutDelay);
            sb.append(", contentBeforeFadeOutDuration=");
            sb.append(this.contentBeforeFadeOutDuration);
            sb.append(", contentAfterFadeInDelay=");
            sb.append(this.contentAfterFadeInDelay);
            sb.append(", contentAfterFadeInDuration=");
            return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.contentAfterFadeInDuration, ")", sb);
        }
    }

    public TransitionAnimator(Executor executor, Timings timings, Interpolators interpolators) {
        this.mainExecutor = executor;
        this.timings = timings;
        this.interpolators = interpolators;
    }

    public final ValueAnimator createAnimator(final Controller controller, final State state, final GradientDrawable gradientDrawable, final boolean z, final boolean z2) {
        final State createAnimatorState = controller.createAnimatorState();
        final int i = createAnimatorState.top;
        final int i2 = createAnimatorState.bottom;
        int i3 = createAnimatorState.left;
        final float f = (i3 + r2) / 2.0f;
        final int i4 = createAnimatorState.right - i3;
        final float f2 = createAnimatorState.topCornerRadius;
        final float f3 = createAnimatorState.bottomCornerRadius;
        final Ref$IntRef ref$IntRef = new Ref$IntRef();
        ref$IntRef.element = state.top;
        final Ref$IntRef ref$IntRef2 = new Ref$IntRef();
        ref$IntRef2.element = state.bottom;
        final Ref$IntRef ref$IntRef3 = new Ref$IntRef();
        ref$IntRef3.element = state.left;
        final Ref$IntRef ref$IntRef4 = new Ref$IntRef();
        ref$IntRef4.element = state.right;
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        ref$FloatRef.element = (ref$IntRef3.element + ref$IntRef4.element) / 2.0f;
        final Ref$IntRef ref$IntRef5 = new Ref$IntRef();
        ref$IntRef5.element = ref$IntRef4.element - ref$IntRef3.element;
        final float f4 = state.topCornerRadius;
        final float f5 = state.bottomCornerRadius;
        final ViewGroup transitionContainer = controller.getTransitionContainer();
        final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(transitionContainer, state);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(this.timings.totalDuration);
        ofFloat.setInterpolator(com.android.app.animation.Interpolators.LINEAR);
        final View openingWindowSyncView = controller.getOpeningWindowSyncView();
        final ViewOverlay overlay = openingWindowSyncView != null ? openingWindowSyncView.getOverlay() : null;
        final boolean z3 = (openingWindowSyncView == null || Intrinsics.areEqual(openingWindowSyncView.getViewRootImpl(), controller.getTransitionContainer().getViewRootImpl())) ? false : true;
        final ViewGroupOverlay overlay2 = transitionContainer.getOverlay();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TransitionAnimator$createAnimator$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ViewOverlay viewOverlay;
                TransitionAnimator.Controller.this.onTransitionAnimationEnd(isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib);
                overlay2.remove(gradientDrawable);
                if (z3 && TransitionAnimator.Controller.this.isLaunching() && (viewOverlay = overlay) != null) {
                    viewOverlay.remove(gradientDrawable);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator, boolean z4) {
                ViewOverlay viewOverlay;
                TransitionAnimator.Controller.this.onTransitionAnimationStart(isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib);
                if (TransitionAnimator.Controller.this.isLaunching() || (viewOverlay = overlay) == null) {
                    overlay2.add(gradientDrawable);
                } else {
                    viewOverlay.add(gradientDrawable);
                }
            }
        });
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.TransitionAnimator$createAnimator$2
            /* JADX WARN: Code restructure failed: missing block: B:12:0x00e4, code lost:
            
                if (com.android.systemui.animation.TransitionAnimator.Companion.getProgress(r11, r1, r13, r11.contentBeforeFadeOutDuration) < 1.0f) goto L15;
             */
            /* JADX WARN: Code restructure failed: missing block: B:13:0x00e6, code lost:
            
                r4 = true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:56:0x00e8, code lost:
            
                r4 = false;
             */
            /* JADX WARN: Code restructure failed: missing block: B:58:0x0100, code lost:
            
                if (com.android.systemui.animation.TransitionAnimator.Companion.getProgress(r11, r1, r13, r11.contentAfterFadeInDuration) > 0.0f) goto L15;
             */
            /* JADX WARN: Removed duplicated region for block: B:25:0x017e  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x01ed  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x022e  */
            /* JADX WARN: Removed duplicated region for block: B:46:0x0184  */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onAnimationUpdate(android.animation.ValueAnimator r21) {
                /*
                    Method dump skipped, instructions count: 628
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.TransitionAnimator$createAnimator$2.onAnimationUpdate(android.animation.ValueAnimator):void");
            }
        });
        return ofFloat;
    }

    public final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(View view, State state) {
        int[] iArr = this.transitionContainerLocation;
        view.getLocationOnScreen(iArr);
        int i = state.top;
        int i2 = iArr[1];
        if (i <= i2 && state.bottom >= view.getHeight() + i2) {
            int i3 = state.left;
            int i4 = iArr[0];
            if (i3 <= i4 && state.right >= view.getWidth() + i4) {
                return true;
            }
        }
        return false;
    }

    public final TransitionAnimator$startAnimation$1 startAnimation(Controller controller, State state, int i, boolean z, boolean z2) {
        if (!controller.isLaunching()) {
            Companion.getClass();
            Flags.returnAnimationFrameworkLibrary();
            throw new IllegalStateException("isLaunching cannot be false when the returnAnimationFrameworkLibrary flag is disabled".toString());
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setAlpha(0);
        ValueAnimator createAnimator = createAnimator(controller, state, gradientDrawable, z, z2);
        createAnimator.start();
        return new TransitionAnimator$startAnimation$1(createAnimator);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Interpolators {
        public final Interpolator contentAfterFadeInInterpolator;
        public final Interpolator contentBeforeFadeOutInterpolator;
        public final Interpolator positionInterpolator;
        public final Interpolator positionXInterpolator;

        public Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            this.positionInterpolator = interpolator;
            this.positionXInterpolator = interpolator2;
            this.contentBeforeFadeOutInterpolator = interpolator3;
            this.contentAfterFadeInInterpolator = interpolator4;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Interpolators)) {
                return false;
            }
            Interpolators interpolators = (Interpolators) obj;
            return Intrinsics.areEqual(this.positionInterpolator, interpolators.positionInterpolator) && Intrinsics.areEqual(this.positionXInterpolator, interpolators.positionXInterpolator) && Intrinsics.areEqual(this.contentBeforeFadeOutInterpolator, interpolators.contentBeforeFadeOutInterpolator) && Intrinsics.areEqual(this.contentAfterFadeInInterpolator, interpolators.contentAfterFadeInInterpolator);
        }

        public final int hashCode() {
            return this.contentAfterFadeInInterpolator.hashCode() + ((this.contentBeforeFadeOutInterpolator.hashCode() + ((this.positionXInterpolator.hashCode() + (this.positionInterpolator.hashCode() * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "Interpolators(positionInterpolator=" + this.positionInterpolator + ", positionXInterpolator=" + this.positionXInterpolator + ", contentBeforeFadeOutInterpolator=" + this.contentBeforeFadeOutInterpolator + ", contentAfterFadeInInterpolator=" + this.contentAfterFadeInInterpolator + ")";
        }

        public /* synthetic */ Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(interpolator, (i & 2) != 0 ? interpolator : interpolator2, interpolator3, interpolator4);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Controller {
        State createAnimatorState();

        default View getOpeningWindowSyncView() {
            return null;
        }

        ViewGroup getTransitionContainer();

        boolean isLaunching();

        void setTransitionContainer(ViewGroup viewGroup);

        default void onTransitionAnimationEnd(boolean z) {
        }

        default void onTransitionAnimationStart(boolean z) {
        }

        default void onTransitionAnimationProgress(State state, float f, float f2) {
        }
    }
}
