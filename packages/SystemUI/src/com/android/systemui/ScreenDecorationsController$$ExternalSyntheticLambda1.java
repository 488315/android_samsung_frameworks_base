package com.android.systemui;

import android.util.Log;
import com.android.systemui.decor.ScreenDecorCommand;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorationsController$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        final ScreenDecorations screenDecorations = (ScreenDecorations) obj;
        int i = ScreenDecorationsController.$r8$clinit;
        screenDecorations.getClass();
        if (ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS) {
            Log.i("ScreenDecorations", "ScreenDecorations is disabled");
            return;
        }
        screenDecorations.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda5(screenDecorations, 1));
        if (screenDecorations.isCoverDisplay().booleanValue()) {
            return;
        }
        screenDecorations.mCommandRegistry.registerCommand("screen-decor", new Function0() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                ScreenDecorations screenDecorations2 = screenDecorations;
                screenDecorations2.getClass();
                return new ScreenDecorCommand(screenDecorations2.mScreenDecorCommandCallback);
            }
        });
    }
}
