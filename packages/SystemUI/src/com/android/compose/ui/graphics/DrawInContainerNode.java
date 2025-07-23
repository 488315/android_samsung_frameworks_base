package com.android.compose.ui.graphics;

import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.graphics.layer.GraphicsLayerKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.modifier.ModifierLocalModifierNode;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.DrawModifierNode;
import androidx.compose.ui.node.LayoutAwareModifierNode;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.IntSizeKt;
import java.util.Comparator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DrawInContainerNode extends Modifier.Node implements LayoutAwareModifierNode, DrawModifierNode, ModifierLocalModifierNode {
    public Function2 clipPath;
    public Function0 enabled;
    public final MutableState lastOffsetInWindow$delegate;
    public LayerWithRenderer layerWithRenderer;
    public ContainerState state;
    public final MutableFloatState zIndex$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LayerWithRenderer {
        public final GraphicsLayer layer;

        public LayerWithRenderer(GraphicsLayer graphicsLayer) {
            this.layer = graphicsLayer;
        }
    }

    public /* synthetic */ DrawInContainerNode(ContainerState containerState, Function0 function0, float f, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(containerState, (i & 2) != 0 ? new DrawInContainerNode$$ExternalSyntheticLambda1() : function0, (i & 4) != 0 ? 0.0f : f, (i & 8) != 0 ? new Function2() { // from class: com.android.compose.ui.graphics.DrawInContainerNode.2
            @Override // kotlin.jvm.functions.Function2
            public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return null;
            }
        } : function2);
    }

    @Override // androidx.compose.ui.node.DrawModifierNode
    public final void draw(final LayoutNodeDrawScope layoutNodeDrawScope) {
        LayerWithRenderer layerWithRenderer = this.layerWithRenderer;
        GraphicsLayer graphicsLayer = layerWithRenderer != null ? layerWithRenderer.layer : null;
        if (graphicsLayer == null) {
            throw new IllegalArgumentException("Error: layer never initialized");
        }
        layoutNodeDrawScope.m646recordJVtK1S4(IntSizeKt.m863toIntSizeuvyYCjk(layoutNodeDrawScope.canvasDrawScope.mo545getSizeNHjbRc()), graphicsLayer, new Function1() { // from class: com.android.compose.ui.graphics.DrawInContainerNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutNodeDrawScope.this.drawContent();
                return Unit.INSTANCE;
            }
        });
        if (((Boolean) this.enabled.invoke()).booleanValue()) {
            return;
        }
        GraphicsLayerKt.drawLayer(layoutNodeDrawScope, graphicsLayer);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        LayerWithRenderer layerWithRenderer = new LayerWithRenderer(DelegatableNodeKt.requireGraphicsContext(this).createGraphicsLayer());
        SnapshotStateList snapshotStateList = this.state.renderers;
        snapshotStateList.add(layerWithRenderer);
        if (snapshotStateList.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(snapshotStateList, new Comparator() { // from class: com.android.compose.ui.graphics.ContainerState$onLayerRendererAttached$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Float.valueOf(((SnapshotMutableFloatStateImpl) DrawInContainerNode.this.zIndex$delegate).getFloatValue()), Float.valueOf(((SnapshotMutableFloatStateImpl) DrawInContainerNode.this.zIndex$delegate).getFloatValue()));
                }
            });
        }
        this.layerWithRenderer = layerWithRenderer;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        LayerWithRenderer layerWithRenderer = this.layerWithRenderer;
        if (layerWithRenderer != null) {
            this.state.renderers.remove(layerWithRenderer);
            DelegatableNodeKt.requireGraphicsContext(this).releaseGraphicsLayer(layerWithRenderer.layer);
        }
    }

    @Override // androidx.compose.ui.node.LayoutAwareModifierNode
    public final void onPlaced(LayoutCoordinates layoutCoordinates) {
        long positionInWindow = LayoutCoordinatesKt.positionInWindow(layoutCoordinates);
        ((SnapshotMutableStateImpl) this.lastOffsetInWindow$delegate).setValue(Offset.m393boximpl(positionInWindow));
    }

    public DrawInContainerNode(ContainerState containerState, Function0 function0, float f, Function2 function2) {
        this.state = containerState;
        this.enabled = function0;
        this.clipPath = function2;
        Offset.Companion.getClass();
        this.lastOffsetInWindow$delegate = SnapshotStateKt.mutableStateOf$default(Offset.m393boximpl(0L));
        this.zIndex$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
    }
}
