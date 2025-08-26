package com.android.compose.animation.scene.content;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.layout.ApproachLayoutModifierNode;
import androidx.compose.ui.layout.ApproachMeasureScope;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.DelegatingNode;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.Element;
import com.android.compose.ui.graphics.ContainerNode;
import kotlin.collections.MapsKt__MapsKt;

/* loaded from: classes.dex */
public final class ContentNode extends DelegatingNode implements ApproachLayoutModifierNode {
    public ContainerNode containerDelegate;
    public Content content;
    public boolean isElevationPossible;
    public boolean isInvisible;

    public ContentNode(Content content, boolean z, boolean z2) {
        ContainerNode containerNode;
        this.content = content;
        this.isElevationPossible = z;
        this.isInvisible = z2;
        if (z) {
            containerNode = new ContainerNode(content.containerState);
            delegate(containerNode);
        } else {
            containerNode = null;
        }
        this.containerDelegate = containerNode;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: approachMeasure-3p2s80s */
    public final MeasureResult mo605approachMeasure3p2s80s(ApproachMeasureScope approachMeasureScope, Measurable measurable, long j) {
        Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        return approachMeasureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new ContentNode$$ExternalSyntheticLambda0(this, placeableMo610measureBRTryo0, 1));
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode
    /* renamed from: isMeasurementApproachInProgress-ozmzZPI */
    public final boolean mo606isMeasurementApproachInProgressozmzZPI(long j) {
        return false;
    }

    @Override // androidx.compose.ui.layout.ApproachLayoutModifierNode, androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        if (!measureScope.isLookingAhead()) {
            throw new IllegalStateException("Check failed.");
        }
        Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        IntSize.Companion companion = IntSize.Companion;
        ((SnapshotMutableStateImpl) this.content.targetSize$delegate).setValue(IntSize.m861boximpl((placeableMo610measureBRTryo0.width << 32) | (placeableMo610measureBRTryo0.height & 4294967295L)));
        return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new ContentNode$$ExternalSyntheticLambda0(this, placeableMo610measureBRTryo0, 0));
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        Content content = this.content;
        Element.Companion.getClass();
        long j = Element.SizeUnspecified;
        ((SnapshotMutableStateImpl) content.targetSize$delegate).setValue(IntSize.m861boximpl(j));
    }
}
