package com.android.systemui.shade;

import android.view.MotionEvent;
import android.view.View;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.qs.bar.QSMediaPlayerBar;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQsMediaTouchHelper implements SecMediaHost.MediaPanelVisibilityListener {
    public boolean actionDownStartInMediaPlayer;
    public final Runnable clearVelocityTrackerRunnable;
    public final DoubleSupplier currentQsVelocitySupplier;
    public final Runnable initVelocityTrackerRunnable;
    public final DoubleConsumer initialTouchXConsumer;
    public final DoubleSupplier initialTouchXSupplier;
    public final DoubleConsumer initialTouchYConsumer;
    public final DoubleSupplier initialTouchYSupplier;
    public float mediaDraggedHeight;
    public final SecMediaHost mediaHost;
    public boolean mediaPlayerExpanding;
    public boolean mediaPlayerScrolling;
    public final NotificationStackScrollLayoutController notificationStackScrollLayoutController;
    public final Lazy panelLogger$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecQsMediaTouchHelper$panelLogger$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelLogger) Dependency.get(SecPanelLogger.class);
        }
    });
    public final BooleanSupplier qsExpandedSupplier;
    public QSMediaPlayerBar qsMediaPlayerBar;
    public final Supplier qsSupplier;
    public final Consumer trackMovementConsumer;
    public final IntConsumer trackingPointerConsumer;
    public final IntSupplier trackingPointerSupplier;

    public SecQsMediaTouchHelper(Runnable runnable, DoubleSupplier doubleSupplier, DoubleConsumer doubleConsumer, DoubleSupplier doubleSupplier2, DoubleConsumer doubleConsumer2, DoubleSupplier doubleSupplier3, Runnable runnable2, SecMediaHost secMediaHost, NotificationStackScrollLayoutController notificationStackScrollLayoutController, BooleanSupplier booleanSupplier, Supplier<InterfaceC1922QS> supplier, Consumer<MotionEvent> consumer, IntConsumer intConsumer, IntSupplier intSupplier) {
        this.clearVelocityTrackerRunnable = runnable;
        this.currentQsVelocitySupplier = doubleSupplier;
        this.initialTouchXConsumer = doubleConsumer;
        this.initialTouchXSupplier = doubleSupplier2;
        this.initialTouchYConsumer = doubleConsumer2;
        this.initialTouchYSupplier = doubleSupplier3;
        this.initVelocityTrackerRunnable = runnable2;
        this.mediaHost = secMediaHost;
        this.notificationStackScrollLayoutController = notificationStackScrollLayoutController;
        this.qsExpandedSupplier = booleanSupplier;
        this.qsSupplier = supplier;
        this.trackMovementConsumer = consumer;
        this.trackingPointerConsumer = intConsumer;
        this.trackingPointerSupplier = intSupplier;
    }

    public static void log(MotionEvent motionEvent, Function3 function3, String str) {
        if (motionEvent.getAction() != 0) {
            return;
        }
        function3.invoke(motionEvent, "QpRune.QUICK_BAR_MEDIA(ACTION_DOWN) ".concat(str), Boolean.TRUE);
    }

    public final boolean isInMediaPlayer(float f, float f2) {
        if (((InterfaceC1922QS) this.qsSupplier.get()) == null) {
            return false;
        }
        QSMediaPlayerBar qSMediaPlayerBar = this.qsMediaPlayerBar;
        View view = qSMediaPlayerBar != null ? qSMediaPlayerBar.mBarRootView : null;
        if (view == null) {
            return false;
        }
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        float f3 = iArr[0];
        float f4 = iArr[1];
        if (this.qsExpandedSupplier.getAsBoolean()) {
            return false;
        }
        if ((this.notificationStackScrollLayoutController.mView.mOwnScrollY > 0) || f < f3 || f > f3 + view.getWidth() || f2 < f4) {
            return false;
        }
        QSMediaPlayerBar qSMediaPlayerBar2 = this.qsMediaPlayerBar;
        return f2 <= f4 + ((float) (qSMediaPlayerBar2 != null ? qSMediaPlayerBar2.getBarHeight() : 0));
    }

    @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
    public final void onMediaVisibilityChanged(boolean z) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.notificationStackScrollLayoutController;
        if (notificationStackScrollLayoutController.mMediaPlayerVisible != z) {
            notificationStackScrollLayoutController.mMediaPlayerVisible = z;
            notificationStackScrollLayoutController.updateShowEmptyShadeView();
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onMediaVisibilityChanged :  ", z, "Stackscroller");
        if (z) {
            return;
        }
        notificationStackScrollLayoutController.mView.mAnimateNextTopPaddingChange = true;
    }

    public final int preparePointerIndex(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.trackingPointerSupplier.getAsInt());
        if (findPointerIndex >= 0) {
            return findPointerIndex;
        }
        this.trackingPointerConsumer.accept(motionEvent.getPointerId(0));
        return 0;
    }

    public final void updateInitialTouchPosition(float f, float f2) {
        this.initialTouchXConsumer.accept(f);
        this.initialTouchYConsumer.accept(f2);
    }

    public final void updateMediaPlayerBar(MotionEvent motionEvent, SecQsMediaTouchHelper$onTouch$1 secQsMediaTouchHelper$onTouch$1) {
        int action = motionEvent.getAction();
        SecMediaHost secMediaHost = this.mediaHost;
        if (action != 1 && motionEvent.getAction() != 3) {
            if (isInMediaPlayer(motionEvent.getX(), motionEvent.getY())) {
                secMediaHost.mPlayerBarExpandHelper.userTouch = true;
                return;
            }
            return;
        }
        if (this.mediaPlayerExpanding) {
            secMediaHost.mPlayerBarExpandHelper.setTracking((float) this.currentQsVelocitySupplier.getAsDouble(), false);
        }
        secMediaHost.mPlayerBarExpandHelper.userTouch = false;
        if (!this.mediaPlayerScrolling || secQsMediaTouchHelper$onTouch$1 == null) {
            return;
        }
        secQsMediaTouchHelper$onTouch$1.run();
    }
}
