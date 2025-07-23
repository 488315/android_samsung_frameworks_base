package com.android.wm.shell.windowdecor;

import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.HandleMenu;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class HandleMenu$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ HandleMenu$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                HandleMenu.Companion companion = HandleMenu.Companion;
                ((HandleMenu) obj).closeMenuPopupImmediately();
                break;
            case 1:
                HandleMenu handleMenu = (HandleMenu) obj;
                AdditionalViewContainer additionalViewContainer = handleMenu.handleMenuViewContainer;
                if (additionalViewContainer != null) {
                    additionalViewContainer.releaseView();
                }
                handleMenu.handleMenuViewContainer = null;
                break;
            default:
                DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener desktopModeTouchEventListener = (DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener) obj;
                HandleMenu.Companion companion2 = HandleMenu.Companion;
                if (desktopModeTouchEventListener != null) {
                    desktopModeTouchEventListener.schedulePopupDismiss();
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
