package com.android.systemui.accessibility;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.accessibility.IMagnificationConnectionCallback;
import com.android.internal.view.TooltipPopup;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.samsung.android.widget.SemTipPopup;
import java.text.NumberFormat;
import java.util.Locale;

public final /* synthetic */ class WindowMagnificationController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WindowMagnificationController f$0;

    public /* synthetic */ WindowMagnificationController$$ExternalSyntheticLambda0(WindowMagnificationController windowMagnificationController, int i) {
        this.$r8$classId = i;
        this.f$0 = windowMagnificationController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IMagnificationConnectionCallback iMagnificationConnectionCallback;
        int i = this.$r8$classId;
        WindowMagnificationController windowMagnificationController = this.f$0;
        switch (i) {
            case 0:
                windowMagnificationController.getClass();
                SemTipPopup semTipPopup = new SemTipPopup(windowMagnificationController.mDragView);
                windowMagnificationController.mTipPopup = semTipPopup;
                semTipPopup.setExpanded(true);
                windowMagnificationController.mTipPopup.setMessage(windowMagnificationController.mContext.getString(R.string.accessibility_magnification_tooltip_description));
                if (windowMagnificationController.isActivated()) {
                    windowMagnificationController.mTipPopup.show(windowMagnificationController.mMirrorView.getLayoutDirection() == 0 ? 3 : 2);
                    windowMagnificationController.mTipPopupCnt++;
                    break;
                }
                break;
            case 1:
                TooltipPopup tooltipPopup = windowMagnificationController.mTooltipPopup;
                if (tooltipPopup != null && tooltipPopup.isShowing()) {
                    windowMagnificationController.mTooltipPopup.hide();
                    break;
                }
                break;
            case 2:
                windowMagnificationController.getClass();
                Flags.createWindowlessWindowMagnifier();
                WindowMagnifierCallback windowMagnifierCallback = windowMagnificationController.mWindowMagnifierCallback;
                int i2 = windowMagnificationController.mDisplayId;
                Rect rect = windowMagnificationController.mSourceBounds;
                MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
                if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                    try {
                        iMagnificationConnectionCallback.onSourceBoundsChanged(i2, rect);
                        break;
                    } catch (RemoteException e) {
                        Log.e("WindowMagnificationConnectionImpl", "Failed to inform source bounds changed", e);
                        return;
                    }
                }
                break;
            case 3:
                WindowMetrics currentWindowMetrics = windowMagnificationController.mWm.getCurrentWindowMetrics();
                Insets insets = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
                int i3 = insets.bottom != 0 ? currentWindowMetrics.getBounds().bottom - insets.bottom : -1;
                if (i3 != windowMagnificationController.mSystemGestureTop) {
                    windowMagnificationController.mSystemGestureTop = i3;
                    windowMagnificationController.updateSysUIState(false);
                    break;
                }
                break;
            case 4:
                if (windowMagnificationController.isActivated()) {
                    View view = windowMagnificationController.mMirrorView;
                    float f = windowMagnificationController.mScale;
                    Locale locale = windowMagnificationController.mContext.getResources().getConfiguration().getLocales().get(0);
                    if (!locale.equals(windowMagnificationController.mLocale)) {
                        windowMagnificationController.mLocale = locale;
                        windowMagnificationController.mPercentFormat = NumberFormat.getPercentInstance(locale);
                    }
                    view.setStateDescription(windowMagnificationController.mPercentFormat.format(f));
                    break;
                }
                break;
            case 5:
                windowMagnificationController.getClass();
                break;
            default:
                windowMagnificationController.applyTapExcludeRegion();
                break;
        }
    }
}
