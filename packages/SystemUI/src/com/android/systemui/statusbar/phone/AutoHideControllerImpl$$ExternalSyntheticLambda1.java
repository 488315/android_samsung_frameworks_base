package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.AutoHideUiElement;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class AutoHideControllerImpl$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AutoHideControllerImpl$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                AutoHideControllerImpl autoHideControllerImpl = (AutoHideControllerImpl) obj2;
                AutoHideUiElement autoHideUiElement = (AutoHideUiElement) obj;
                autoHideControllerImpl.getClass();
                if (autoHideUiElement != null && autoHideUiElement.isVisible()) {
                    autoHideControllerImpl.mIsVisible = true;
                    break;
                }
                break;
            case 1:
                AutoHideControllerImpl autoHideControllerImpl2 = (AutoHideControllerImpl) obj2;
                Runnable checkBarModesRunnable = autoHideControllerImpl2.getCheckBarModesRunnable((AutoHideUiElement) obj);
                if (checkBarModesRunnable != null) {
                    autoHideControllerImpl2.mHandler.post(checkBarModesRunnable);
                    break;
                }
                break;
            case 2:
                AutoHideControllerImpl autoHideControllerImpl3 = (AutoHideControllerImpl) obj2;
                Runnable checkBarModesRunnable2 = autoHideControllerImpl3.getCheckBarModesRunnable((AutoHideUiElement) obj);
                if (checkBarModesRunnable2 != null) {
                    autoHideControllerImpl3.mHandler.postDelayed(checkBarModesRunnable2, 500L);
                    break;
                }
                break;
            case 3:
                AutoHideControllerImpl autoHideControllerImpl4 = (AutoHideControllerImpl) obj2;
                Runnable checkBarModesRunnable3 = autoHideControllerImpl4.getCheckBarModesRunnable((AutoHideUiElement) obj);
                if (checkBarModesRunnable3 != null) {
                    autoHideControllerImpl4.mHandler.removeCallbacks(checkBarModesRunnable3);
                    break;
                }
                break;
            case 4:
                AutoHideControllerImpl autoHideControllerImpl5 = (AutoHideControllerImpl) obj2;
                AutoHideUiElement autoHideUiElement2 = (AutoHideUiElement) obj;
                if (autoHideUiElement2 == null) {
                    autoHideControllerImpl5.getClass();
                    break;
                } else {
                    autoHideControllerImpl5.mShouldHide = autoHideUiElement2.shouldHideOnTouch() & autoHideControllerImpl5.mShouldHide;
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
