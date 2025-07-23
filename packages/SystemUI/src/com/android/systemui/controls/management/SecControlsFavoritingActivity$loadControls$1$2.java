package com.android.systemui.controls.management;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
