package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((DreamOverlayStateController.Callback) obj).onExitLowLight();
                break;
            case 1:
                ((DreamOverlayStateController.Callback) obj).onStateChanged();
                break;
            default:
                ((DreamOverlayStateController.Callback) obj).onAvailableComplicationTypesChanged();
                break;
        }
    }
}
