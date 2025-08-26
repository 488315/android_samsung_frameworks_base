package com.android.wm.shell.splitscreen;

import com.android.wm.shell.splitscreen.SplitScreenProxyService;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = SplitScreenProxyService.MessageHandler.$r8$clinit;
        ((SplitScreenController) obj).toggleSplitScreen(1);
    }
}
