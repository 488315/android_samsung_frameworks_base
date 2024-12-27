package com.android.systemui.accessibility;

import android.graphics.Rect;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.view.TooltipPopup;
import java.util.Collections;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationModeSwitch$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationModeSwitch f$0;

    public /* synthetic */ MagnificationModeSwitch$$ExternalSyntheticLambda3(MagnificationModeSwitch magnificationModeSwitch, int i) {
        this.$r8$classId = i;
        this.f$0 = magnificationModeSwitch;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MagnificationModeSwitch magnificationModeSwitch = this.f$0;
        switch (i) {
            case 0:
                Rect rect = magnificationModeSwitch.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.ime()).toRect();
                InputMethodManager inputMethodManager = (InputMethodManager) magnificationModeSwitch.mContext.getSystemService(InputMethodManager.class);
                if (!rect.equals(magnificationModeSwitch.mImeInsetsRect)) {
                    if (inputMethodManager.semIsInputMethodShown()) {
                        magnificationModeSwitch.mImeInsetsRect.set(rect);
                    } else {
                        magnificationModeSwitch.mImeInsetsRect.setEmpty();
                        magnificationModeSwitch.removeButton();
                        magnificationModeSwitch.showButton(magnificationModeSwitch.mMagnificationMode, true);
                    }
                }
                Rect draggableWindowBounds = magnificationModeSwitch.getDraggableWindowBounds();
                if (!magnificationModeSwitch.mDraggableWindowBounds.equals(draggableWindowBounds)) {
                    magnificationModeSwitch.mDraggableWindowBounds.set(draggableWindowBounds);
                    magnificationModeSwitch.stickToScreenEdge(magnificationModeSwitch.mToLeftScreenEdge);
                    break;
                }
                break;
            case 1:
                magnificationModeSwitch.mImageView.animate().alpha(0.0f).setDuration(300L).withEndAction(new MagnificationModeSwitch$$ExternalSyntheticLambda3(magnificationModeSwitch, 3)).start();
                magnificationModeSwitch.mIsFadeOutAnimating = true;
                break;
            case 2:
                magnificationModeSwitch.mImageView.setSystemGestureExclusionRects(Collections.singletonList(new Rect(0, 0, magnificationModeSwitch.mImageView.getWidth(), magnificationModeSwitch.mImageView.getHeight())));
                break;
            case 3:
                magnificationModeSwitch.removeButton();
                break;
            case 4:
                TooltipPopup tooltipPopup = magnificationModeSwitch.mTooltipPopup;
                if (tooltipPopup != null && tooltipPopup.isShowing()) {
                    magnificationModeSwitch.mTooltipPopup.hide();
                    break;
                }
                break;
            default:
                magnificationModeSwitch.mImageView.animate().alpha(1.0f).setDuration(300L).start();
                break;
        }
    }
}
