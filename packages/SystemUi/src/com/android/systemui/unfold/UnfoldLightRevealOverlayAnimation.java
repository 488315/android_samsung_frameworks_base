package com.android.systemui.unfold;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.os.Looper;
import android.os.Trace;
import android.view.Display;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.util.concurrency.PendingTasksContainer$registerTask$1;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldLightRevealOverlayAnimation {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ContentResolver contentResolver;
    public int currentRotation;
    public final Executor executor;
    public final FeatureFlags featureFlags;
    public boolean isTouchBlocked;
    public boolean isUnfoldHandled;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum AddOverlayReason {
        FOLD,
        UNFOLD
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RotationWatcher implements RotationChangeProvider.RotationListener {
        public RotationWatcher() {
        }

        @Override // com.android.systemui.unfold.updates.RotationChangeProvider.RotationListener
        public final void onRotationChanged(final int i) {
            final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$RotationWatcher$onRotationChanged$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    int i2 = i;
                    if (Trace.isTagEnabled(4096L)) {
                        Trace.traceBegin(4096L, "UnfoldLightRevealOverlayAnimation#onRotationChanged");
                        try {
                            if (unfoldLightRevealOverlayAnimation2.currentRotation != i2) {
                                unfoldLightRevealOverlayAnimation2.currentRotation = i2;
                            }
                            Unit unit = Unit.INSTANCE;
                        } finally {
                            Trace.traceEnd(4096L);
                        }
                    } else if (unfoldLightRevealOverlayAnimation2.currentRotation != i2) {
                        unfoldLightRevealOverlayAnimation2.currentRotation = i2;
                    }
                    return Unit.INSTANCE;
                }
            };
            int i2 = UnfoldLightRevealOverlayAnimation.$r8$clinit;
            unfoldLightRevealOverlayAnimation.getClass();
            Looper.myLooper();
            throw null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransitionListener implements UnfoldTransitionProgressProvider.TransitionProgressListener {
        public TransitionListener() {
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionFinished() {
            final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$TransitionListener$onTransitionFinished$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
                    unfoldLightRevealOverlayAnimation2.getClass();
                    Looper.myLooper();
                    throw null;
                }
            };
            int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
            unfoldLightRevealOverlayAnimation.getClass();
            Looper.myLooper();
            throw null;
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionProgress(final float f) {
            final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$TransitionListener$onTransitionProgress$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldLightRevealOverlayAnimation.this.getClass();
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    float f2 = f;
                    unfoldLightRevealOverlayAnimation2.getClass();
                    boolean z = UnfoldLightRevealOverlayAnimation.AddOverlayReason.UNFOLD != null || f2 < 0.8f;
                    if (unfoldLightRevealOverlayAnimation2.isTouchBlocked != z) {
                        unfoldLightRevealOverlayAnimation2.isTouchBlocked = z;
                        if (Trace.isTagEnabled(4096L)) {
                            Trace.traceBegin(4096L, "UnfoldLightRevealOverlayAnimation#relayoutToUpdateTouch");
                            Trace.traceEnd(4096L);
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
            unfoldLightRevealOverlayAnimation.getClass();
            Looper.myLooper();
            throw null;
        }

        @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
        public final void onTransitionStarted() {
            final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
            unfoldLightRevealOverlayAnimation.getClass();
            new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$TransitionListener$onTransitionStarted$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    UnfoldLightRevealOverlayAnimation.AddOverlayReason addOverlayReason = UnfoldLightRevealOverlayAnimation.AddOverlayReason.FOLD;
                    int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
                    unfoldLightRevealOverlayAnimation2.getClass();
                    return Unit.INSTANCE;
                }
            };
            Looper.myLooper();
            throw null;
        }
    }

    static {
        new Companion(null);
    }

    public UnfoldLightRevealOverlayAnimation(Context context, FeatureFlags featureFlags, DeviceStateManager deviceStateManager, ContentResolver contentResolver, DisplayManager displayManager, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, Optional<Object> optional, Executor executor, ThreadFactory threadFactory, RotationChangeProvider rotationChangeProvider, DisplayTracker displayTracker) {
        this.featureFlags = featureFlags;
        this.contentResolver = contentResolver;
        this.executor = executor;
        new TransitionListener();
        new RotationWatcher();
        this.isUnfoldHandled = true;
        this.isTouchBlocked = true;
        Display display = context.getDisplay();
        Intrinsics.checkNotNull(display);
        this.currentRotation = display.getRotation();
    }

    public final void onScreenTurningOn(final PendingTasksContainer$registerTask$1 pendingTasksContainer$registerTask$1) {
        new Function0() { // from class: com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation$onScreenTurningOn$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Trace.beginSection("UnfoldLightRevealOverlayAnimation#onScreenTurningOn");
                try {
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
                    int i = UnfoldLightRevealOverlayAnimation.$r8$clinit;
                    unfoldLightRevealOverlayAnimation.getClass();
                    UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation2 = UnfoldLightRevealOverlayAnimation.this;
                    if (!unfoldLightRevealOverlayAnimation2.isUnfoldHandled) {
                        ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
                        ContentResolver contentResolver = unfoldLightRevealOverlayAnimation2.contentResolver;
                        companion.getClass();
                        if (ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver)) {
                            UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation3 = UnfoldLightRevealOverlayAnimation.this;
                            Runnable runnable = pendingTasksContainer$registerTask$1;
                            UnfoldLightRevealOverlayAnimation.AddOverlayReason addOverlayReason = UnfoldLightRevealOverlayAnimation.AddOverlayReason.FOLD;
                            unfoldLightRevealOverlayAnimation3.getClass();
                            if (runnable != null) {
                                runnable.run();
                            }
                            UnfoldLightRevealOverlayAnimation.this.isUnfoldHandled = true;
                            Trace.endSection();
                            return Unit.INSTANCE;
                        }
                    }
                    UnfoldLightRevealOverlayAnimation.this.getClass();
                    Looper.myLooper();
                    throw null;
                } catch (Throwable th) {
                    Trace.endSection();
                    throw th;
                }
            }
        };
        Looper.myLooper();
        throw null;
    }
}
