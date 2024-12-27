package com.android.systemui.util.concurrency;

import android.view.Choreographer;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
