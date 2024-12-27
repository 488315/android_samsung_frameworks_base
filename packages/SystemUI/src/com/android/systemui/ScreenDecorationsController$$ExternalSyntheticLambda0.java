package com.android.systemui;

import android.os.Handler;
import android.util.Log;
import com.android.systemui.decor.ScreenDecorCommand;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScreenDecorationsController$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        final ScreenDecorations screenDecorations = (ScreenDecorations) obj;
        if (ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS) {
            screenDecorations.getClass();
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
            return;
        }
        ThreadFactory threadFactory = screenDecorations.mThreadFactory;
        Handler buildHandlerOnNewThread = threadFactory.buildHandlerOnNewThread("ScreenDecorations");
        screenDecorations.mHandler = buildHandlerOnNewThread;
        DelayableExecutor buildDelayableExecutorOnHandler = threadFactory.buildDelayableExecutorOnHandler(buildHandlerOnNewThread);
        screenDecorations.mExecutor = buildDelayableExecutorOnHandler;
        buildDelayableExecutorOnHandler.execute(new ScreenDecorations$$ExternalSyntheticLambda3(screenDecorations, 1));
        screenDecorations.mDotViewController.uiExecutor = screenDecorations.mExecutor;
        if (screenDecorations.isCoverDisplay().booleanValue()) {
            return;
        }
        screenDecorations.mCommandRegistry.registerCommand("screen-decor", new Function0() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ScreenDecorations screenDecorations2 = ScreenDecorations.this;
                screenDecorations2.getClass();
                return new ScreenDecorCommand(screenDecorations2.mScreenDecorCommandCallback);
            }
        });
    }
}
