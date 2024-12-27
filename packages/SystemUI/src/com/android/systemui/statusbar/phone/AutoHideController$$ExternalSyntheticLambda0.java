package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.AutoHideUiElement;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class AutoHideController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AutoHideController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                AutoHideController autoHideController = (AutoHideController) obj2;
                AutoHideUiElement autoHideUiElement = (AutoHideUiElement) obj;
                autoHideController.getClass();
                if (autoHideUiElement != null && autoHideUiElement.isVisible()) {
                    autoHideController.mIsVisible = true;
                    break;
                }
                break;
            case 1:
                AutoHideController autoHideController2 = (AutoHideController) obj2;
                Runnable checkBarModesRunnable = autoHideController2.getCheckBarModesRunnable((AutoHideUiElement) obj);
                if (checkBarModesRunnable != null) {
                    autoHideController2.mHandler.post(checkBarModesRunnable);
                    break;
                }
                break;
            case 2:
                AutoHideController autoHideController3 = (AutoHideController) obj2;
                Runnable checkBarModesRunnable2 = autoHideController3.getCheckBarModesRunnable((AutoHideUiElement) obj);
                if (checkBarModesRunnable2 != null) {
                    autoHideController3.mHandler.postDelayed(checkBarModesRunnable2, 500L);
                    break;
                }
                break;
            case 3:
                AutoHideController autoHideController4 = (AutoHideController) obj2;
                Runnable checkBarModesRunnable3 = autoHideController4.getCheckBarModesRunnable((AutoHideUiElement) obj);
                if (checkBarModesRunnable3 != null) {
                    autoHideController4.mHandler.removeCallbacks(checkBarModesRunnable3);
                    break;
                }
                break;
            case 4:
                AutoHideController autoHideController5 = (AutoHideController) obj2;
                AutoHideUiElement autoHideUiElement2 = (AutoHideUiElement) obj;
                if (autoHideUiElement2 == null) {
                    autoHideController5.getClass();
                    break;
                } else {
                    autoHideController5.mShouldHide = autoHideUiElement2.shouldHideOnTouch() & autoHideController5.mShouldHide;
                    break;
                }
            default:
                Consumer consumer = (Consumer) obj2;
                AutoHideUiElement autoHideUiElement3 = (AutoHideUiElement) obj;
                if (autoHideUiElement3 != null) {
                    consumer.accept(autoHideUiElement3);
                    break;
                }
                break;
        }
    }
}
