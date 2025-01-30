package com.samsung.android.multiwindow;

import android.content.Context;
import android.graphics.Rect;
import com.samsung.android.core.CompatUtils;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.util.InterpolatorUtils;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class DexSizeCompatResizeGuide extends SizeCompatResizeGuide {
    private static final int DELTA_ADJUSTMENT = 4;
    private static final int MAX_SIZE_THRESHOLD = 200;
    private static final int MIN_SIZE_THRESHOLD = 200;
    private static final boolean PROVIDE_SNAP_TO_FULLSCREEN = false;
    private final Rect mTmpRect;

    public DexSizeCompatResizeGuide(Context context, String packageName) {
        super(context, packageName);
        this.mTmpRect = new Rect();
    }

    @Override // com.samsung.android.multiwindow.SizeCompatResizeGuide
    public void adjustBounds(SizeCompatInfo sizeCompatInfo, int ctrlType, Rect repositionTaskBounds, Rect taskBoundsAtDragStart, boolean update, Consumer<Rect> adjustTaskBoundsWithCaptionHeight) {
        int deltaX;
        int delta;
        int displayOrientation;
        boolean isRotatable = SizeCompatInfo.isDragDexSizeCompatRotatable(sizeCompatInfo);
        int left = repositionTaskBounds.left - taskBoundsAtDragStart.left;
        int top = repositionTaskBounds.top - taskBoundsAtDragStart.top;
        int right = taskBoundsAtDragStart.right - repositionTaskBounds.right;
        int bottom = taskBoundsAtDragStart.bottom - repositionTaskBounds.bottom;
        int displayWidth = sizeCompatInfo.getDisplayWidth();
        int displayHeight = sizeCompatInfo.getDisplayHeight();
        int displayOrientation2 = CompatUtils.getConfigurationOrientation(displayWidth, displayHeight);
        int taskOrientation = CompatUtils.getConfigurationOrientation(taskBoundsAtDragStart.width(), taskBoundsAtDragStart.height());
        boolean isRotated = isRotatable && displayOrientation2 != taskOrientation;
        if (isRotatable) {
            if (displayOrientation2 == 1) {
                delta = calculateDelta(top, bottom) * 2;
                deltaX = (isRotated || delta > 0) ? 0 : delta;
            } else {
                int deltaY = calculateDelta(left, right);
                int delta2 = deltaY * 2;
                delta = (isRotated || delta2 > 0) ? 0 : delta2;
                deltaX = delta2;
            }
        } else {
            deltaX = calculateDelta(calculateDelta(top, bottom), calculateDelta(left, right));
            delta = deltaX;
        }
        repositionTaskBounds.set(taskBoundsAtDragStart);
        repositionTaskBounds.inset(applyDeltaAdjustment(deltaX), applyDeltaAdjustment(delta));
        if (isRotated && taskBoundsAtDragStart.contains(repositionTaskBounds)) {
            repositionTaskBounds.set(taskBoundsAtDragStart);
            return;
        }
        int maxWidth = sizeCompatInfo.getMaxWidth();
        boolean isMinSize = false;
        int maxHeight = sizeCompatInfo.getMaxHeight();
        int top2 = maxWidth + 0;
        int right2 = maxHeight + 0;
        boolean isMaxSize = repositionTaskBounds.width() >= top2 && repositionTaskBounds.height() >= right2;
        if (isMaxSize) {
            repositionTaskBounds.set(0, 0, maxWidth, maxHeight);
            displayOrientation = 0;
        } else {
            boolean isMaxSize2 = isMaxSize;
            int minWidth = sizeCompatInfo.getMinWidth();
            int minHeight = sizeCompatInfo.getMinHeight();
            if (isRotatable) {
                if (isRotated) {
                    int startWidth = taskBoundsAtDragStart.width();
                    int startHeight = taskBoundsAtDragStart.height();
                    int repositionWidth = repositionTaskBounds.width();
                    int repositionHeight = repositionTaskBounds.height();
                    int minWidthThreshold = startWidth + 400;
                    int deltaY2 = startHeight + 400;
                    isMinSize = repositionWidth >= minWidthThreshold || repositionHeight >= deltaY2;
                    if (isMinSize) {
                        int repositionWidth2 = ((repositionWidth - startWidth) / 2) + startWidth;
                        int startWidth2 = ((repositionHeight - startHeight) / 2) + startHeight;
                        if (repositionWidth2 > top2 || startWidth2 > right2) {
                            isMaxSize2 = true;
                            isMinSize = false;
                            repositionTaskBounds.set(0, 0, maxWidth, maxHeight);
                        }
                    }
                } else {
                    int minWidthThreshold2 = minWidth + 400;
                    int minHeightThreshold = minHeight + 400;
                    isMinSize = repositionTaskBounds.width() <= minWidthThreshold2 && repositionTaskBounds.height() <= minHeightThreshold;
                }
            } else {
                isMinSize = repositionTaskBounds.width() <= minWidth || repositionTaskBounds.height() <= minHeight;
            }
            if (isMinSize) {
                repositionTaskBounds.set(0, 0, minWidth, minHeight);
                displayOrientation = 0;
            } else if (!isRotatable) {
                this.mTmpRect.set(0, 0, maxWidth, maxHeight);
                float repositionScale = CompatUtils.adjustRoundScale(CompatUtils.getCompatScale(this.mTmpRect, repositionTaskBounds));
                if (repositionScale <= 0.0f) {
                    repositionTaskBounds.set(taskBoundsAtDragStart);
                    return;
                } else {
                    displayOrientation = 0;
                    repositionTaskBounds.set(0, 0, maxWidth, maxHeight);
                    CompatUtils.getScaledBounds(repositionTaskBounds, repositionScale);
                }
            } else {
                if (update && !isMaxSize2) {
                    cancelAnimation(repositionTaskBounds, taskBoundsAtDragStart, adjustTaskBoundsWithCaptionHeight);
                    return;
                }
                displayOrientation = 0;
            }
        }
        if (isRotatable || ctrlType == 0 || (repositionTaskBounds.width() == taskBoundsAtDragStart.width() && repositionTaskBounds.height() == taskBoundsAtDragStart.height())) {
            CompatUtils.adjustBoundsToCenter(taskBoundsAtDragStart, repositionTaskBounds);
        } else {
            int i = (ctrlType & 1) != 0 ? 1 : displayOrientation;
            int i2 = (ctrlType & 4) != 0 ? 1 : displayOrientation;
            repositionTaskBounds.offsetTo(taskBoundsAtDragStart.left, taskBoundsAtDragStart.top);
            int dx = i != 0 ? taskBoundsAtDragStart.width() - repositionTaskBounds.width() : displayOrientation;
            if (i2 != 0) {
                displayOrientation = taskBoundsAtDragStart.height() - repositionTaskBounds.height();
            }
            if (dx != 0 || displayOrientation != 0) {
                repositionTaskBounds.offset(dx, displayOrientation);
            }
        }
        if (isRotatable && isMinSize) {
            snapToBounds(250L);
        }
    }

    @Override // com.samsung.android.multiwindow.SizeCompatResizeGuide
    public void cancelAnimation(Rect repositionTaskBounds, Rect taskBoundsAtDragStart, Consumer<Rect> adjustTaskBoundsWithCaptionHeight) {
        this.mTmpRect.set(repositionTaskBounds);
        if (adjustTaskBoundsWithCaptionHeight != null) {
            adjustTaskBoundsWithCaptionHeight.accept(this.mTmpRect);
        }
        show(this.mTmpRect);
        snapToBounds(null, 100L, InterpolatorUtils.LINEAR, 100, 0, true);
        this.mTmpRect.set(taskBoundsAtDragStart);
        if (adjustTaskBoundsWithCaptionHeight != null) {
            adjustTaskBoundsWithCaptionHeight.accept(this.mTmpRect);
        }
        show(this.mTmpRect);
        repositionTaskBounds.set(taskBoundsAtDragStart);
    }

    private static int calculateDelta(int delta1, int delta2) {
        if (delta1 >= 0 && delta2 >= 0) {
            int delta = Math.max(delta1, delta2);
            return delta;
        }
        if (delta1 > 0 || delta2 > 0) {
            return 0;
        }
        int delta3 = Math.min(delta1, delta2);
        return delta3;
    }

    private static int applyDeltaAdjustment(float delta) {
        if (delta != 0.0f) {
            delta /= 4.0f;
        }
        return (int) (0.5f + delta);
    }
}
