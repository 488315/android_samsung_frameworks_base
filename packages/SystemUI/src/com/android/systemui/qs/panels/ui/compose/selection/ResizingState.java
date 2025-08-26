package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.foundation.gestures.AnchoredDraggableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.qs.panels.ui.compose.selection.ResizingState;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ResizingState {
    public final AnchoredDraggableState anchoredDraggableState;
    public final State bounds$delegate;
    public final State finalResizeOperation$delegate;
    public final State temporaryResizeOperation$delegate;

    public abstract class ResizeOperation {
        public final TileSpec spec;
        public final boolean toIcon;

        public final class FinalResizeOperation extends ResizeOperation {
            public FinalResizeOperation(TileSpec tileSpec, boolean z) {
                super(tileSpec, z, null);
            }
        }

        public final class TemporaryResizeOperation extends ResizeOperation {
            public TemporaryResizeOperation(TileSpec tileSpec, boolean z) {
                super(tileSpec, z, null);
            }
        }

        public /* synthetic */ ResizeOperation(TileSpec tileSpec, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSpec, z);
        }

        private ResizeOperation(TileSpec tileSpec, boolean z) {
            this.spec = tileSpec;
            this.toIcon = z;
        }
    }

    public ResizingState(final TileSpec tileSpec, boolean z) {
        this.anchoredDraggableState = new AnchoredDraggableState(z ? QSDragAnchor.Icon : QSDragAnchor.Large);
        this.bounds$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.ResizingState$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ResizingState resizingState = this.f$0;
                float fMinPosition = resizingState.anchoredDraggableState.getAnchors().minPosition();
                Float fValueOf = Float.valueOf(fMinPosition);
                if (Float.isNaN(fMinPosition)) {
                    fValueOf = null;
                }
                float fMaxPosition = resizingState.anchoredDraggableState.getAnchors().maxPosition();
                return new Pair(fValueOf, Float.isNaN(fMaxPosition) ? null : Float.valueOf(fMaxPosition));
            }
        });
        final int i = 0;
        this.temporaryResizeOperation$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.ResizingState$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return new ResizingState.ResizeOperation.TemporaryResizeOperation(tileSpec, ((SnapshotMutableStateImpl) this.anchoredDraggableState.currentValue$delegate).getValue() == QSDragAnchor.Icon);
                    default:
                        return new ResizingState.ResizeOperation.FinalResizeOperation(tileSpec, ((SnapshotMutableStateImpl) this.anchoredDraggableState.settledValue$delegate).getValue() == QSDragAnchor.Icon);
                }
            }
        });
        final int i2 = 1;
        this.finalResizeOperation$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.selection.ResizingState$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return new ResizingState.ResizeOperation.TemporaryResizeOperation(tileSpec, ((SnapshotMutableStateImpl) this.anchoredDraggableState.currentValue$delegate).getValue() == QSDragAnchor.Icon);
                    default:
                        return new ResizingState.ResizeOperation.FinalResizeOperation(tileSpec, ((SnapshotMutableStateImpl) this.anchoredDraggableState.settledValue$delegate).getValue() == QSDragAnchor.Icon);
                }
            }
        });
    }
}
