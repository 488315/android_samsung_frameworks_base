package com.android.systemui.communal.ui.viewmodel;

import androidx.compose.foundation.gestures.DraggableAnchorsConfig;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ResizeableItemFrameViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        DraggableAnchorsConfig draggableAnchorsConfig = (DraggableAnchorsConfig) obj;
        switch (this.$r8$classId) {
            case 0:
                draggableAnchorsConfig.at(0, 0.0f);
                break;
            case 1:
                draggableAnchorsConfig.at(0, 0.0f);
                break;
            default:
                draggableAnchorsConfig.at(0, 0.0f);
                break;
        }
        return Unit.INSTANCE;
    }
}
