package com.android.systemui.util.concurrency;

import android.view.Choreographer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

final class SysUIConcurrencyModule$provideBackPanelUiThreadContext$1 extends Lambda implements Function0 {
    public static final SysUIConcurrencyModule$provideBackPanelUiThreadContext$1 INSTANCE = new SysUIConcurrencyModule$provideBackPanelUiThreadContext$1();

    public SysUIConcurrencyModule$provideBackPanelUiThreadContext$1() {
        super(0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Choreographer invoke() {
        return Choreographer.getInstance();
    }
}
