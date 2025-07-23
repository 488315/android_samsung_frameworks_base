package com.android.systemui.screenshot;

import com.android.systemui.log.DebugLogger;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultScreenshotActionsProvider$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultScreenshotActionsProvider f$0;

    public /* synthetic */ DefaultScreenshotActionsProvider$$ExternalSyntheticLambda0(DefaultScreenshotActionsProvider defaultScreenshotActionsProvider, int i) {
        this.$r8$classId = i;
        this.f$0 = defaultScreenshotActionsProvider;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        DefaultScreenshotActionsProvider defaultScreenshotActionsProvider = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = DefaultScreenshotActionsProvider.$r8$clinit;
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(DefaultScreenshotActionsProvider.class).getSimpleName();
                defaultScreenshotActionsProvider.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_PREVIEW_TAPPED, 0, defaultScreenshotActionsProvider.request.getPackageNameString());
                defaultScreenshotActionsProvider.onDeferrableActionTapped(new DefaultScreenshotActionsProvider$1$2(defaultScreenshotActionsProvider, null));
                break;
            case 1:
                int i2 = DefaultScreenshotActionsProvider.$r8$clinit;
                DebugLogger debugLogger2 = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(DefaultScreenshotActionsProvider.class).getSimpleName();
                defaultScreenshotActionsProvider.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_SHARE_TAPPED, 0, defaultScreenshotActionsProvider.request.getPackageNameString());
                defaultScreenshotActionsProvider.onDeferrableActionTapped(new DefaultScreenshotActionsProvider$2$2(defaultScreenshotActionsProvider, null));
                break;
            case 2:
                int i3 = DefaultScreenshotActionsProvider.$r8$clinit;
                DebugLogger debugLogger3 = DebugLogger.INSTANCE;
                Reflection.getOrCreateKotlinClass(DefaultScreenshotActionsProvider.class).getSimpleName();
                defaultScreenshotActionsProvider.uiEventLogger.log(ScreenshotEvent.SCREENSHOT_EDIT_TAPPED, 0, defaultScreenshotActionsProvider.request.getPackageNameString());
                defaultScreenshotActionsProvider.onDeferrableActionTapped(new DefaultScreenshotActionsProvider$3$2(defaultScreenshotActionsProvider, null));
                break;
            default:
                LegacyScreenshotController$$ExternalSyntheticLambda15 legacyScreenshotController$$ExternalSyntheticLambda15 = defaultScreenshotActionsProvider.onScrollClick;
                if (legacyScreenshotController$$ExternalSyntheticLambda15 != null) {
                    legacyScreenshotController$$ExternalSyntheticLambda15.run();
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
