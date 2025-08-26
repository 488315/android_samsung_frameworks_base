package com.android.wm.shell.common;

import android.view.SurfaceControl;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class DisplayImeController$PerDisplay$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((SurfaceControl) obj).release();
    }
}
