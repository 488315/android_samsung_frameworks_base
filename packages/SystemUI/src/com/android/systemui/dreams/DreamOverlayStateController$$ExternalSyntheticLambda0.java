package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

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
