package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.drawscope.DrawScope;
import com.samsung.sesl.compose.foundation.interaction.SeslInteractionState;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes4.dex */
public final /* synthetic */ class SeslRecoilNode$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeslRecoilNode f$0;

    public /* synthetic */ SeslRecoilNode$$ExternalSyntheticLambda2(SeslRecoilNode seslRecoilNode, int i) {
        this.$r8$classId = i;
        this.f$0 = seslRecoilNode;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SeslInteractionState seslInteractionState = (SeslInteractionState) obj;
                SeslRecoilNode seslRecoilNode = this.f$0;
                boolean zAreEqual = Intrinsics.areEqual(seslRecoilNode.interactionState, seslInteractionState);
                seslRecoilNode.interactionState = seslInteractionState;
                if (!zAreEqual && seslRecoilNode.node.isAttached && seslRecoilNode.enabled) {
                    BuildersKt.launch$default(seslRecoilNode.getCoroutineScope(), null, null, new SeslRecoilNode$updateAnimation$1(seslRecoilNode, null), 3);
                }
                break;
            default:
                DrawScope drawScope = (DrawScope) obj;
                SeslFeedbackNode seslFeedbackNode = this.f$0.feedbackNode;
                if (seslFeedbackNode != null) {
                    seslFeedbackNode.drawFeedback$sesl8_compose_core_release(drawScope);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
