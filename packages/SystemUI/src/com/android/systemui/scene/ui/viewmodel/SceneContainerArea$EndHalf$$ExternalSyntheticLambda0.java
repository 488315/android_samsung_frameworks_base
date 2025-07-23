package com.android.systemui.scene.ui.viewmodel;

import androidx.compose.ui.unit.LayoutDirection;
import com.android.systemui.scene.ui.viewmodel.SceneContainerArea;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SceneContainerArea$EndHalf$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LayoutDirection layoutDirection = (LayoutDirection) obj;
        switch (this.$r8$classId) {
            case 0:
                SceneContainerArea.EndHalf endHalf = SceneContainerArea.EndHalf.INSTANCE;
                return layoutDirection == LayoutDirection.Ltr ? SceneContainerArea.Resolved.RightHalf.INSTANCE : SceneContainerArea.Resolved.LeftHalf.INSTANCE;
            case 1:
                SceneContainerArea.TopEdgeEndHalf topEdgeEndHalf = SceneContainerArea.TopEdgeEndHalf.INSTANCE;
                return layoutDirection == LayoutDirection.Ltr ? SceneContainerArea.Resolved.TopEdgeRightHalf.INSTANCE : SceneContainerArea.Resolved.TopEdgeLeftHalf.INSTANCE;
            default:
                SceneContainerArea.TopEdgeStartHalf topEdgeStartHalf = SceneContainerArea.TopEdgeStartHalf.INSTANCE;
                return layoutDirection == LayoutDirection.Ltr ? SceneContainerArea.Resolved.TopEdgeLeftHalf.INSTANCE : SceneContainerArea.Resolved.TopEdgeRightHalf.INSTANCE;
        }
    }
}
