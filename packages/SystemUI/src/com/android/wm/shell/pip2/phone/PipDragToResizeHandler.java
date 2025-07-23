package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipDragToResizeHandler {
    public final Context mContext;
    public int mDelta;
    public final Function mMovementBoundsSupplier;
    public final PhonePipMenuController mPhonePipMenuController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipResizeGestureHandler mPipResizeGestureHandler;
    public final PipScheduler mPipScheduler;
    public final Region mTmpRegion = new Region();
    public final Rect mDragCornerSize = new Rect();
    public final Rect mTmpTopLeftCorner = new Rect();
    public final Rect mTmpTopRightCorner = new Rect();
    public final Rect mTmpBottomLeftCorner = new Rect();
    public final Rect mTmpBottomRightCorner = new Rect();
    public final Rect mDisplayBounds = new Rect();

    public PipDragToResizeHandler(Context context, PipResizeGestureHandler pipResizeGestureHandler, PipBoundsState pipBoundsState, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipScheduler pipScheduler, Function<Rect, Rect> function) {
        this.mContext = context;
        this.mPipResizeGestureHandler = pipResizeGestureHandler;
        this.mPipBoundsState = pipBoundsState;
        this.mPhonePipMenuController = phonePipMenuController;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipScheduler = pipScheduler;
        this.mMovementBoundsSupplier = function;
    }

    public final boolean isWithinDragResizeRegion(int i, int i2) {
        Rect bounds = this.mPipBoundsState.getBounds();
        Rect rect = this.mDragCornerSize;
        int i3 = this.mDelta;
        rect.set(0, 0, i3, i3);
        this.mTmpTopLeftCorner.set(this.mDragCornerSize);
        this.mTmpTopRightCorner.set(this.mDragCornerSize);
        this.mTmpBottomLeftCorner.set(this.mDragCornerSize);
        this.mTmpBottomRightCorner.set(this.mDragCornerSize);
        Rect rect2 = this.mTmpTopLeftCorner;
        int i4 = bounds.left;
        int i5 = this.mDelta;
        rect2.offset(i4 - (i5 / 2), bounds.top - (i5 / 2));
        Rect rect3 = this.mTmpTopRightCorner;
        int i6 = bounds.right;
        int i7 = this.mDelta;
        rect3.offset(i6 - (i7 / 2), bounds.top - (i7 / 2));
        Rect rect4 = this.mTmpBottomLeftCorner;
        int i8 = bounds.left;
        int i9 = this.mDelta;
        rect4.offset(i8 - (i9 / 2), bounds.bottom - (i9 / 2));
        Rect rect5 = this.mTmpBottomRightCorner;
        int i10 = bounds.right;
        int i11 = this.mDelta;
        rect5.offset(i10 - (i11 / 2), bounds.bottom - (i11 / 2));
        this.mTmpRegion.setEmpty();
        Region region = this.mTmpRegion;
        Rect rect6 = this.mTmpTopLeftCorner;
        Region.Op op = Region.Op.UNION;
        region.op(rect6, op);
        this.mTmpRegion.op(this.mTmpTopRightCorner, op);
        this.mTmpRegion.op(this.mTmpBottomLeftCorner, op);
        this.mTmpRegion.op(this.mTmpBottomRightCorner, op);
        return this.mTmpRegion.contains(i, i2);
    }
}
