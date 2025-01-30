package com.android.systemui.accessibility;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.accessibility.IWindowMagnificationConnectionCallback;
import com.android.internal.view.TooltipPopup;
import com.android.systemui.R;
import com.samsung.android.widget.SemTipPopup;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnificationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowMagnificationController f$0;

    public /* synthetic */ WindowMagnificationController$$ExternalSyntheticLambda0(WindowMagnificationController windowMagnificationController, int i) {
        this.$r8$classId = i;
        this.f$0 = windowMagnificationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IWindowMagnificationConnectionCallback iWindowMagnificationConnectionCallback;
        boolean z = true;
        switch (this.$r8$classId) {
            case 0:
                WindowMagnificationController windowMagnificationController = this.f$0;
                windowMagnificationController.getClass();
                SemTipPopup semTipPopup = new SemTipPopup(windowMagnificationController.mDragView);
                windowMagnificationController.mTipPopup = semTipPopup;
                semTipPopup.setExpanded(true);
                windowMagnificationController.mTipPopup.setMessage(windowMagnificationController.mContext.getString(R.string.accessibility_magnification_tooltip_description));
                windowMagnificationController.mTipPopup.show(windowMagnificationController.mMirrorView.getLayoutDirection() == 0 ? 3 : 2);
                windowMagnificationController.mTipPopupCnt++;
                break;
            case 1:
                this.f$0.getClass();
                break;
            case 2:
                WindowMagnificationController windowMagnificationController2 = this.f$0;
                TooltipPopup tooltipPopup = windowMagnificationController2.mTooltipPopup;
                if (tooltipPopup != null && tooltipPopup.isShowing()) {
                    windowMagnificationController2.mTooltipPopup.hide();
                    break;
                }
                break;
            case 3:
                WindowMagnificationController windowMagnificationController3 = this.f$0;
                if (windowMagnificationController3.mMirrorView != null) {
                    Rect rect = new Rect(windowMagnificationController3.mMirrorViewBounds);
                    windowMagnificationController3.mMirrorView.getBoundsOnScreen(windowMagnificationController3.mMirrorViewBounds);
                    if (rect.width() != windowMagnificationController3.mMirrorViewBounds.width() || rect.height() != windowMagnificationController3.mMirrorViewBounds.height()) {
                        windowMagnificationController3.mMirrorView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, windowMagnificationController3.mMirrorViewBounds.width(), windowMagnificationController3.mMirrorViewBounds.height())));
                    }
                    windowMagnificationController3.updateSysUIState(false);
                    WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController3.mWindowMagnifierCallback;
                    int i = windowMagnificationController3.mDisplayId;
                    Rect rect2 = windowMagnificationController3.mMirrorViewBounds;
                    WindowMagnificationConnectionImpl windowMagnificationConnectionImpl = WindowMagnification.this.mWindowMagnificationConnectionImpl;
                    if (windowMagnificationConnectionImpl != null && (iWindowMagnificationConnectionCallback = windowMagnificationConnectionImpl.mConnectionCallback) != null) {
                        try {
                            iWindowMagnificationConnectionCallback.onWindowMagnifierBoundsChanged(i, rect2);
                            break;
                        } catch (RemoteException e) {
                            Log.e("WindowMagnificationConnectionImpl", "Failed to inform bounds changed", e);
                            return;
                        }
                    }
                }
                break;
            case 4:
                WindowMagnificationController windowMagnificationController4 = this.f$0;
                if (windowMagnificationController4.isActivated()) {
                    View view = windowMagnificationController4.mMirrorView;
                    float f = windowMagnificationController4.mScale;
                    Locale locale = windowMagnificationController4.mContext.getResources().getConfiguration().getLocales().get(0);
                    if (!locale.equals(windowMagnificationController4.mLocale)) {
                        windowMagnificationController4.mLocale = locale;
                        windowMagnificationController4.mPercentFormat = NumberFormat.getPercentInstance(locale);
                    }
                    view.setStateDescription(windowMagnificationController4.mPercentFormat.format(f));
                    break;
                }
                break;
            case 5:
                WindowMagnificationController windowMagnificationController5 = this.f$0;
                WindowMetrics currentWindowMetrics = windowMagnificationController5.mWm.getCurrentWindowMetrics();
                Insets insets = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
                int i2 = insets.bottom != 0 ? currentWindowMetrics.getBounds().bottom - insets.bottom : -1;
                if (i2 != windowMagnificationController5.mSystemGestureTop) {
                    windowMagnificationController5.mSystemGestureTop = i2;
                } else {
                    z = false;
                }
                if (z) {
                    windowMagnificationController5.updateSysUIState(false);
                    break;
                }
                break;
            default:
                this.f$0.applyTapExcludeRegion();
                break;
        }
    }
}
