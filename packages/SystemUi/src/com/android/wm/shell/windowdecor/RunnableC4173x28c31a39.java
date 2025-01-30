package com.android.wm.shell.windowdecor;

import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$SettingsObserver$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4173x28c31a39 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RunnableC4173x28c31a39(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MultitaskingWindowDecorViewModel.m2763$$Nest$mupdateColorThemeState(MultitaskingWindowDecorViewModel.this);
                break;
            case 1:
                MultitaskingWindowDecorViewModel.m2764$$Nest$mupdateFullscreenHandlerState(MultitaskingWindowDecorViewModel.this);
                break;
            default:
                ((MultitaskingWindowDecorViewModel.CaptionTouchEventListener) this.f$0).dismissPopup();
                break;
        }
    }
}
