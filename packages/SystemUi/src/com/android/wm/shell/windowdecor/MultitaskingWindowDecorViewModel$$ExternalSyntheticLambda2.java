package com.android.wm.shell.windowdecor;

import com.android.wm.shell.windowdecor.widget.HandleView;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((MultitaskingWindowDecoration) obj).onDisplayAdded(true);
                break;
            case 1:
                MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) obj;
                WindowMenuCaptionPresenter windowMenuCaptionPresenter = multitaskingWindowDecoration.mCaptionMenuPresenter;
                if (windowMenuCaptionPresenter == null) {
                    HandleView handleView = multitaskingWindowDecoration.getHandleView();
                    if (handleView != null) {
                        handleView.updateHandleViewColor();
                        break;
                    }
                } else {
                    windowMenuCaptionPresenter.updateButtonColor();
                    break;
                }
                break;
            default:
                MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) obj;
                multitaskingWindowDecoration2.mIsAdditionalDisplayAdded = false;
                WindowMenuCaptionPresenter windowMenuCaptionPresenter2 = multitaskingWindowDecoration2.mCaptionMenuPresenter;
                if (windowMenuCaptionPresenter2 != null) {
                    windowMenuCaptionPresenter2.mIsDisplayAdded = false;
                    windowMenuCaptionPresenter2.setupAddDisplayButton(false);
                }
                multitaskingWindowDecoration2.relayout(multitaskingWindowDecoration2.mTaskInfo, false);
                break;
        }
    }
}
