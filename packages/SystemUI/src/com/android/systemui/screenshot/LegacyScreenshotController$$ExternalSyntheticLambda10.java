package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ImageExporter;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LegacyScreenshotController$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LegacyScreenshotController$$ExternalSyntheticLambda10(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                LegacyScreenshotController legacyScreenshotController = (LegacyScreenshotController) obj2;
                legacyScreenshotController.getClass();
                if (((ImageExporter.Result) obj).uri != null) {
                    legacyScreenshotController.mScreenshotHandler.post(new LegacyScreenshotController$$ExternalSyntheticLambda0(legacyScreenshotController, 1));
                    break;
                }
                break;
            default:
                ((ScreenshotShelfViewProxy) obj2).view.announceForAccessibility((String) obj);
                break;
        }
    }
}
