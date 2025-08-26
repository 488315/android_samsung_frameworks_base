package com.android.wm.shell.pip.phone;

import android.graphics.Rect;
import com.android.wm.shell.common.pip.PipBoundsState;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipMotionHelper$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipMotionHelper f$0;

    public /* synthetic */ PipMotionHelper$$ExternalSyntheticLambda0(PipMotionHelper pipMotionHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = pipMotionHelper;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        PipMotionHelper pipMotionHelper = this.f$0;
        switch (i) {
            case 0:
                pipMotionHelper.getClass();
                break;
            case 1:
                PhonePipMenuController phonePipMenuController = pipMotionHelper.mMenuController;
                if (phonePipMenuController.isMenuVisible()) {
                    phonePipMenuController.mPipMenuView.mPipMenuIconsAlgorithm.getClass();
                    break;
                }
                break;
            default:
                Rect rect = (Rect) obj;
                PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
                if (!pipBoundsState.getBounds().equals(rect)) {
                    PhonePipMenuController phonePipMenuController2 = pipMotionHelper.mMenuController;
                    if (phonePipMenuController2.isMenuVisible()) {
                        phonePipMenuController2.mPipMenuView.mPipMenuIconsAlgorithm.getClass();
                    }
                    pipBoundsState.setBounds(rect);
                    break;
                }
                break;
        }
    }
}
