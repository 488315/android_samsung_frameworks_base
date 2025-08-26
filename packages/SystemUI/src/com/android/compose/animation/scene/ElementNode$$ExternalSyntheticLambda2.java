package com.android.compose.animation.scene;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.node.TraversableNode;
import androidx.compose.ui.node.TraversableNode$Companion$TraverseDescendantsAction;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.ElementNode;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class ElementNode$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ElementNode.Companion companion = ElementNode.Companion;
                return Unit.INSTANCE;
            default:
                ElementNode.Companion companion2 = ElementNode.Companion;
                ElementNode elementNode = (ElementNode) ((TraversableNode) obj);
                if (elementNode._element != null) {
                    Element.State stateInContent = elementNode.getStateInContent();
                    Offset.Companion.getClass();
                    stateInContent.lastOffset = Offset.Unspecified;
                    Scale.Companion.getClass();
                    stateInContent.lastScale = Scale.Unspecified;
                    Element.Companion.getClass();
                    stateInContent.lastAlpha = Element.AlphaUnspecified;
                }
                return TraversableNode$Companion$TraverseDescendantsAction.ContinueTraversal;
        }
    }
}
