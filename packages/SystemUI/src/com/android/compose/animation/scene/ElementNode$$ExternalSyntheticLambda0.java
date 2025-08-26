package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LookaheadLayoutCoordinates;
import androidx.compose.ui.layout.LookaheadScope;
import androidx.compose.ui.layout.LookaheadScopeKt;
import androidx.compose.ui.layout.Placeable;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.ElementNode;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final /* synthetic */ class ElementNode$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Placeable f$0;
    public final /* synthetic */ ElementNode f$1;

    public /* synthetic */ ElementNode$$ExternalSyntheticLambda0(Placeable placeable, ElementNode elementNode, int i) {
        this.$r8$classId = i;
        this.f$0 = placeable;
        this.f$1 = elementNode;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Placeable placeable = this.f$0;
        ElementNode elementNode = this.f$1;
        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
        switch (this.$r8$classId) {
            case 0:
                ElementNode.Companion companion = ElementNode.Companion;
                LayoutCoordinates coordinates = placementScope.getCoordinates();
                if (coordinates != null) {
                    LookaheadScope lookaheadScope = elementNode.layoutImpl._lookaheadScope;
                    lookaheadScope.getClass();
                    Element.State stateInContent = elementNode.getStateInContent();
                    LayoutCoordinates lookaheadScopeCoordinates = lookaheadScope.getLookaheadScopeCoordinates();
                    Offset.Companion.getClass();
                    boolean z = (6 & 4) != 0;
                    lookaheadScope.getClass();
                    Function2 function2 = LookaheadScopeKt.defaultPlacementApproachInProgress;
                    LayoutCoordinates lookaheadCoordinates = lookaheadScope.toLookaheadCoordinates(lookaheadScopeCoordinates);
                    LayoutCoordinates lookaheadCoordinates2 = lookaheadScope.toLookaheadCoordinates(coordinates);
                    ((SnapshotMutableStateImpl) stateInContent.targetOffset$delegate).setValue(Offset.m395boximpl(lookaheadCoordinates instanceof LookaheadLayoutCoordinates ? ((LookaheadLayoutCoordinates) lookaheadCoordinates).mo614localPositionOfS_NoaFU(lookaheadCoordinates2, 0L, z) : lookaheadCoordinates2 instanceof LookaheadLayoutCoordinates ? ((LookaheadLayoutCoordinates) lookaheadCoordinates2).mo614localPositionOfS_NoaFU(lookaheadCoordinates, 0L, z) ^ (-9223372034707292160L) : lookaheadCoordinates.mo614localPositionOfS_NoaFU(lookaheadCoordinates, 0L, z)));
                }
                placementScope.place(placeable, 0, 0, 0.0f);
                break;
            default:
                ElementNode.Companion companion2 = ElementNode.Companion;
                LayoutCoordinates coordinates2 = placementScope.getCoordinates();
                if (coordinates2 != null) {
                    LookaheadScope lookaheadScope2 = elementNode.layoutImpl._lookaheadScope;
                    lookaheadScope2.getClass();
                    Element.State stateInContent2 = elementNode.getStateInContent();
                    LayoutCoordinates lookaheadScopeCoordinates2 = lookaheadScope2.getLookaheadScopeCoordinates();
                    Offset.Companion.getClass();
                    stateInContent2.lastOffset = lookaheadScopeCoordinates2.mo613localPositionOfR5De75A(coordinates2, 0L);
                }
                placementScope.place(placeable, 0, 0, 0.0f);
                break;
        }
        return Unit.INSTANCE;
    }
}
