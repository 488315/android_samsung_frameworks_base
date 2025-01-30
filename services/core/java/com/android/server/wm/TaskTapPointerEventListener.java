package com.android.server.wm;

import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManagerGlobal;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;

/* loaded from: classes3.dex */
public class TaskTapPointerEventListener implements WindowManagerPolicyConstants.PointerEventListener {
    public final DisplayContent mDisplayContent;
    public final WindowManagerService mService;
    public final Region mTouchExcludeRegion = new Region();
    public final Rect mTmpRect = new Rect();
    public int mPointerIconType = 1;

    public TaskTapPointerEventListener(WindowManagerService windowManagerService, DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
    }

    public final void restorePointerIcon(int i, int i2) {
        if (this.mPointerIconType != 1) {
            this.mPointerIconType = 1;
            this.mService.f1749mH.removeMessages(55);
            this.mService.f1749mH.obtainMessage(55, i, i2, this.mDisplayContent).sendToTarget();
        }
    }

    public void onPointerEvent(MotionEvent motionEvent) {
        try {
            onPointerEventInner(motionEvent);
        } catch (Exception e) {
            Slog.w(StartingSurfaceController.TAG, e.toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x006e, code lost:
    
        if (r8 > r2.bottom) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x007d, code lost:
    
        if (r8 > r2.bottom) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPointerEventInner(MotionEvent motionEvent) {
        int x;
        float y;
        int i;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (motionEvent.getSource() == 8194) {
                x = (int) motionEvent.getXCursorPosition();
                y = motionEvent.getYCursorPosition();
            } else {
                x = (int) motionEvent.getX();
                y = motionEvent.getY();
            }
            int i2 = (int) y;
            synchronized (this) {
                if (this.mDisplayContent.isDexMode() && (motionEvent.getFlags() & 268435456) != 0 && (motionEvent.getButtonState() & 1) == 0) {
                    return;
                }
                if (!this.mTouchExcludeRegion.contains(x, i2)) {
                    this.mService.mTaskPositioningController.handleTapOutsideTask(this.mDisplayContent, x, i2);
                }
                return;
            }
        }
        if (actionMasked != 7 && actionMasked != 9) {
            if (actionMasked != 10) {
                return;
            }
            restorePointerIcon((int) motionEvent.getX(), (int) motionEvent.getY());
            return;
        }
        int x2 = (int) motionEvent.getX();
        int y2 = (int) motionEvent.getY();
        synchronized (this) {
            if (this.mTouchExcludeRegion.contains(x2, y2)) {
                restorePointerIcon(x2, y2);
            } else {
                Task findTaskForResizePoint = this.mDisplayContent.findTaskForResizePoint(x2, y2);
                if (findTaskForResizePoint != null) {
                    findTaskForResizePoint.getDimBounds(this.mTmpRect);
                    if (!this.mTmpRect.isEmpty() && !this.mTmpRect.contains(x2, y2)) {
                        Rect rect = this.mTmpRect;
                        i = 1014;
                        if (x2 < rect.left) {
                            if (y2 >= rect.top) {
                            }
                            i = 1017;
                            if (this.mPointerIconType != i) {
                                this.mPointerIconType = i;
                                if (i == 1) {
                                    this.mService.f1749mH.removeMessages(55);
                                    this.mService.f1749mH.obtainMessage(55, x2, y2, this.mDisplayContent).sendToTarget();
                                } else {
                                    InputManagerGlobal.getInstance().setPointerIconType(this.mPointerIconType);
                                }
                            }
                        } else {
                            if (x2 > rect.right) {
                                if (y2 >= rect.top) {
                                }
                                i = 1016;
                            } else if (y2 < rect.top || y2 > rect.bottom) {
                                i = 1015;
                            }
                            if (this.mPointerIconType != i) {
                            }
                        }
                    }
                }
                i = 1;
                if (this.mPointerIconType != i) {
                }
            }
        }
    }

    public void setTouchExcludeRegion(Region region) {
        synchronized (this) {
            this.mTouchExcludeRegion.set(region);
        }
    }
}
