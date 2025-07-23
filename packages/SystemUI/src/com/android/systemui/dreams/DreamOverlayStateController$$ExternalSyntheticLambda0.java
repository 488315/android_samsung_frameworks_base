package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamOverlayStateController.Callback callback = (DreamOverlayStateController.Callback) obj;
        switch (this.$r8$classId) {
            case 0:
                callback.onStateChanged();
                break;
            case 1:
                callback.onAvailableComplicationTypesChanged();
                break;
            case 2:
                callback.onExitLowLight();
                break;
            default:
                callback.onComplicationsChanged();
                break;
        }
    }
}
