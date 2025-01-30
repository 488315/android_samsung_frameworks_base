package com.android.wm.shell.pip.phone;

import android.graphics.Rect;
import com.android.wm.shell.pip.PipBoundsState;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipMotionHelper$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipMotionHelper f$0;

    public /* synthetic */ PipMotionHelper$$ExternalSyntheticLambda0(PipMotionHelper pipMotionHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = pipMotionHelper;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PhonePipMenuController phonePipMenuController = this.f$0.mMenuController;
                if (phonePipMenuController.isMenuVisible()) {
                    phonePipMenuController.mPipMenuView.mPipMenuIconsAlgorithm.getClass();
                    break;
                }
                break;
            default:
                PipMotionHelper pipMotionHelper = this.f$0;
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
