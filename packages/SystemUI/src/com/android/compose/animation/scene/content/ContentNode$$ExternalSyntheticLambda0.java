package com.android.compose.animation.scene.content;

import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.ui.layout.Placeable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class ContentNode$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContentNode f$0;
    public final /* synthetic */ Placeable f$1;

    public /* synthetic */ ContentNode$$ExternalSyntheticLambda0(ContentNode contentNode, Placeable placeable, int i) {
        this.$r8$classId = i;
        this.f$0 = contentNode;
        this.f$1 = placeable;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
        switch (this.$r8$classId) {
            case 0:
                ContentNode contentNode = this.f$0;
                if (!contentNode.isInvisible) {
                    placementScope.place(this.f$1, 0, 0, ((SnapshotMutableFloatStateImpl) contentNode.content.zIndex$delegate).getFloatValue());
                }
                break;
            default:
                ContentNode contentNode2 = this.f$0;
                if (!contentNode2.isInvisible) {
                    placementScope.place(this.f$1, 0, 0, ((SnapshotMutableFloatStateImpl) contentNode2.content.zIndex$delegate).getFloatValue());
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
