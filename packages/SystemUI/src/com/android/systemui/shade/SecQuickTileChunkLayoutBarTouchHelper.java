package com.android.systemui.shade;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.TileChunkLayoutBarExpandHelper;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public final class SecQuickTileChunkLayoutBarTouchHelper {
    public boolean actionDownStartInChunkBar;
    public final Runnable clearVelocityTrackerRunnable;
    public final DoubleSupplier currentQsVelocitySupplier;
    public float draggedHeight;
    public TileChunkLayoutBarExpandHelper expandHelper;
    public final Runnable initVelocityTrackerRunnable;
    public final DoubleSupplier initialTouchXSupplier;
    public final DoubleSupplier initialTouchYSupplier;
    public boolean isExpanding;
    public final BooleanSupplier qsExpandedSupplier;
    public final Supplier qsSupplier;
    public final SecQuickTileChunkLayoutBarTouchHelper$scrollIndicatorAccessibilityDelegate$1 scrollIndicatorAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.shade.SecQuickTileChunkLayoutBarTouchHelper$scrollIndicatorAccessibilityDelegate$1
        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(16);
            accessibilityNodeInfo.setClassName("android.widget.Button");
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (i != 16) {
                return super.performAccessibilityAction(view, i, bundle);
            }
            TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper = SecQuickTileChunkLayoutBarTouchHelper.this.expandHelper;
            if (tileChunkLayoutBarExpandHelper == null) {
                return true;
            }
            tileChunkLayoutBarExpandHelper.forceToggleBar();
            return true;
        }
    };
    public TileChunkLayoutBar tileChunkLayoutBar;
    public final Consumer trackMovementConsumer;
    public final IntConsumer trackingPointerConsumer;
    public final IntSupplier trackingPointerSupplier;

    public SecQuickTileChunkLayoutBarTouchHelper(Context context, Runnable runnable, DoubleSupplier doubleSupplier, DoubleConsumer doubleConsumer, DoubleSupplier doubleSupplier2, DoubleConsumer doubleConsumer2, DoubleSupplier doubleSupplier3, Runnable runnable2, BooleanSupplier booleanSupplier, Supplier<QS> supplier, Consumer<MotionEvent> consumer, IntConsumer intConsumer, IntSupplier intSupplier) {
        this.clearVelocityTrackerRunnable = runnable;
        this.currentQsVelocitySupplier = doubleSupplier;
        this.initialTouchXSupplier = doubleSupplier2;
        this.initialTouchYSupplier = doubleSupplier3;
        this.initVelocityTrackerRunnable = runnable2;
        this.qsExpandedSupplier = booleanSupplier;
        this.qsSupplier = supplier;
        this.trackMovementConsumer = consumer;
        this.trackingPointerConsumer = intConsumer;
        this.trackingPointerSupplier = intSupplier;
    }

    public final int preparePointerIndex(MotionEvent motionEvent) {
        Integer valueOf = Integer.valueOf(motionEvent.findPointerIndex(this.trackingPointerSupplier.getAsInt()));
        if (valueOf.intValue() < 0) {
            valueOf = null;
        }
        if (valueOf != null) {
            return valueOf.intValue();
        }
        this.trackingPointerConsumer.accept(motionEvent.getPointerId(0));
        return 0;
    }

    public final void updateChunkLayoutBar(MotionEvent motionEvent) {
        TileChunkLayoutBarExpandHelper tileChunkLayoutBarExpandHelper;
        if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && this.isExpanding && (tileChunkLayoutBarExpandHelper = this.expandHelper) != null) {
            tileChunkLayoutBarExpandHelper.setTracking((float) this.currentQsVelocitySupplier.getAsDouble(), false);
        }
    }
}
