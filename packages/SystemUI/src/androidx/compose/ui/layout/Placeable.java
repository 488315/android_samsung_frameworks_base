package androidx.compose.ui.layout;

import androidx.compose.ui.graphics.layer.GraphicsLayer;
import androidx.compose.ui.node.MotionReferencePlacementDelegate;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.ElementNode$$ExternalSyntheticLambda3;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Placeable implements Measured {
    public long apparentToRealOffset;
    public int height;
    public long measuredSize;
    public long measurementConstraints;
    public int width;

    public Placeable() {
        long j = 0;
        IntSize.Companion companion = IntSize.Companion;
        this.measuredSize = (j & 4294967295L) | (j << 32);
        this.measurementConstraints = PlaceableKt.DefaultConstraints;
        IntOffset.Companion.getClass();
        this.apparentToRealOffset = 0L;
    }

    public int getMeasuredHeight() {
        return (int) (this.measuredSize & 4294967295L);
    }

    public int getMeasuredWidth() {
        return (int) (this.measuredSize >> 32);
    }

    public final void onMeasuredSizeChanged() {
        this.width = RangesKt___RangesKt.coerceIn((int) (this.measuredSize >> 32), Constraints.m823getMinWidthimpl(this.measurementConstraints), Constraints.m821getMaxWidthimpl(this.measurementConstraints));
        this.height = RangesKt___RangesKt.coerceIn((int) (this.measuredSize & 4294967295L), Constraints.m822getMinHeightimpl(this.measurementConstraints), Constraints.m820getMaxHeightimpl(this.measurementConstraints));
        int i = this.width;
        long j = this.measuredSize;
        IntOffset.Companion companion = IntOffset.Companion;
        this.apparentToRealOffset = (((i - ((int) (j >> 32))) / 2) << 32) | (4294967295L & ((r0 - ((int) (j & 4294967295L))) / 2));
    }

    /* renamed from: placeAt-f8xVGno, reason: not valid java name */
    public void mo623placeAtf8xVGno(long j, float f, GraphicsLayer graphicsLayer) {
        mo609placeAtf8xVGno(j, f, (Function1) null);
    }

    /* renamed from: placeAt-f8xVGno */
    public abstract void mo609placeAtf8xVGno(long j, float f, Function1 function1);

    /* renamed from: setMeasuredSize-ozmzZPI, reason: not valid java name */
    public final void m624setMeasuredSizeozmzZPI(long j) {
        if (IntSize.m861equalsimpl0(this.measuredSize, j)) {
            return;
        }
        this.measuredSize = j;
        onMeasuredSizeChanged();
    }

    /* renamed from: setMeasurementConstraints-BRTryo0, reason: not valid java name */
    public final void m625setMeasurementConstraintsBRTryo0(long j) {
        if (Constraints.m815equalsimpl0(this.measurementConstraints, j)) {
            return;
        }
        this.measurementConstraints = j;
        onMeasuredSizeChanged();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class PlacementScope {
        public boolean motionFrameOfReferencePlacement;

        /* JADX WARN: Multi-variable type inference failed */
        public static final void access$handleMotionFrameOfReferencePlacement(PlacementScope placementScope, Placeable placeable) {
            placementScope.getClass();
            if (placeable instanceof MotionReferencePlacementDelegate) {
                ((MotionReferencePlacementDelegate) placeable).updatePlacedUnderMotionFrameOfReference(placementScope.motionFrameOfReferencePlacement);
            }
        }

        /* renamed from: place-70tqf50$default, reason: not valid java name */
        public static void m626place70tqf50$default(PlacementScope placementScope, Placeable placeable, long j) {
            placementScope.getClass();
            access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
            placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(j, placeable.apparentToRealOffset), 0.0f, (Function1) null);
        }

        public static void placeRelativeWithLayer$default(PlacementScope placementScope, Placeable placeable, int i, int i2) {
            Function1 function1 = PlaceableKt.DefaultLayerBlock;
            placementScope.getClass();
            long j = (i << 32) | (i2 & 4294967295L);
            IntOffset.Companion companion = IntOffset.Companion;
            if (placementScope.getParentLayoutDirection() == LayoutDirection.Ltr || placementScope.getParentWidth() == 0) {
                access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(j, placeable.apparentToRealOffset), 0.0f, function1);
            } else {
                int parentWidth = (placementScope.getParentWidth() - placeable.width) - ((int) (j >> 32));
                access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY((parentWidth << 32) | (((int) (j & 4294967295L)) & 4294967295L), placeable.apparentToRealOffset), 0.0f, function1);
            }
        }

        /* renamed from: placeRelativeWithLayer-aW-9-wM$default, reason: not valid java name */
        public static void m628placeRelativeWithLayeraW9wM$default(PlacementScope placementScope, Placeable placeable, long j, GraphicsLayer graphicsLayer) {
            if (placementScope.getParentLayoutDirection() == LayoutDirection.Ltr || placementScope.getParentWidth() == 0) {
                access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                placeable.mo623placeAtf8xVGno(IntOffset.m851plusqkQi6aY(j, placeable.apparentToRealOffset), 0.0f, graphicsLayer);
                return;
            }
            int parentWidth = placementScope.getParentWidth() - placeable.width;
            IntOffset.Companion companion = IntOffset.Companion;
            access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
            placeable.mo623placeAtf8xVGno(IntOffset.m851plusqkQi6aY((((int) (j & 4294967295L)) & 4294967295L) | ((parentWidth - ((int) (j >> 32))) << 32), placeable.apparentToRealOffset), 0.0f, graphicsLayer);
        }

        public static void placeWithLayer$default(PlacementScope placementScope, Placeable placeable, int i, int i2, Function1 function1, int i3) {
            if ((i3 & 8) != 0) {
                function1 = PlaceableKt.DefaultLayerBlock;
            }
            placementScope.getClass();
            IntOffset.Companion companion = IntOffset.Companion;
            access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
            placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY((i2 & 4294967295L) | (i << 32), placeable.apparentToRealOffset), 0.0f, function1);
        }

        /* renamed from: placeWithLayer-aW-9-wM$default, reason: not valid java name */
        public static void m629placeWithLayeraW9wM$default(PlacementScope placementScope, Placeable placeable, long j, ElementNode$$ExternalSyntheticLambda3 elementNode$$ExternalSyntheticLambda3, int i) {
            Function1 function1 = elementNode$$ExternalSyntheticLambda3;
            if ((i & 4) != 0) {
                function1 = PlaceableKt.DefaultLayerBlock;
            }
            placementScope.getClass();
            access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
            placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(j, placeable.apparentToRealOffset), 0.0f, function1);
        }

        public LayoutCoordinates getCoordinates() {
            return null;
        }

        public abstract LayoutDirection getParentLayoutDirection();

        public abstract int getParentWidth();

        public final void place(Placeable placeable, int i, int i2, float f) {
            IntOffset.Companion companion = IntOffset.Companion;
            access$handleMotionFrameOfReferencePlacement(this, placeable);
            placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY((i2 & 4294967295L) | (i << 32), placeable.apparentToRealOffset), f, (Function1) null);
        }

        public final void placeRelative(Placeable placeable, int i, int i2, float f) {
            long j = (i << 32) | (i2 & 4294967295L);
            IntOffset.Companion companion = IntOffset.Companion;
            if (getParentLayoutDirection() == LayoutDirection.Ltr || getParentWidth() == 0) {
                access$handleMotionFrameOfReferencePlacement(this, placeable);
                placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(j, placeable.apparentToRealOffset), f, (Function1) null);
            } else {
                int parentWidth = (getParentWidth() - placeable.width) - ((int) (j >> 32));
                access$handleMotionFrameOfReferencePlacement(this, placeable);
                placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY((parentWidth << 32) | (((int) (j & 4294967295L)) & 4294967295L), placeable.apparentToRealOffset), f, (Function1) null);
            }
        }

        /* renamed from: placeRelativeWithLayer-aW-9-wM$default, reason: not valid java name */
        public static void m627placeRelativeWithLayeraW9wM$default(PlacementScope placementScope, Placeable placeable, long j) {
            Function1 function1 = PlaceableKt.DefaultLayerBlock;
            if (placementScope.getParentLayoutDirection() != LayoutDirection.Ltr && placementScope.getParentWidth() != 0) {
                int parentWidth = placementScope.getParentWidth() - placeable.width;
                IntOffset.Companion companion = IntOffset.Companion;
                access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
                placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY((((int) (j & 4294967295L)) & 4294967295L) | ((parentWidth - ((int) (j >> 32))) << 32), placeable.apparentToRealOffset), 0.0f, function1);
                return;
            }
            access$handleMotionFrameOfReferencePlacement(placementScope, placeable);
            placeable.mo609placeAtf8xVGno(IntOffset.m851plusqkQi6aY(j, placeable.apparentToRealOffset), 0.0f, function1);
        }
    }
}
