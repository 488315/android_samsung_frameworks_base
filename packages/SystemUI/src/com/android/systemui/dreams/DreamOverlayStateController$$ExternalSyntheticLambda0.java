package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamOverlayStateController.Callback callback = (DreamOverlayStateController.Callback) obj;
        switch (this.$r8$classId) {
            case 0:
                callback.onExitLowLight();
                break;
            case 1:
                callback.onStateChanged();
                break;
            default:
                callback.onAvailableComplicationTypesChanged();
                break;
        }
    }
}
