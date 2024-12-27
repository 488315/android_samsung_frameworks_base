package com.android.systemui.controls.management;

import java.util.function.Consumer;

public final class SecControlsFavoritingActivity$loadControls$1$2 implements Consumer {
    public final /* synthetic */ SecControlsFavoritingActivity this$0;

    public SecControlsFavoritingActivity$loadControls$1$2(SecControlsFavoritingActivity secControlsFavoritingActivity) {
        this.this$0 = secControlsFavoritingActivity;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.this$0.cancelLoadRunnable = (Runnable) obj;
    }
}
