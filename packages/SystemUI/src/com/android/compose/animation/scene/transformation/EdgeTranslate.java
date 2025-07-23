package com.android.compose.animation.scene.transformation;

import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntSize;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.Element;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.PropertyTransformationScopeImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.PropertyTransformation;
import com.android.compose.animation.scene.transformation.Transformation;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EdgeTranslate implements InterpolatedPropertyTransformation {
    public final Edge edge;
    public final PropertyTransformation.Property.Offset property;
    public final boolean startsOutsideLayoutBounds;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory implements Transformation.Factory {
        public final Edge edge;
        public final boolean startsOutsideLayoutBounds;

        public Factory(Edge edge, boolean z) {
            this.edge = edge;
            this.startsOutsideLayoutBounds = z;
        }

        @Override // com.android.compose.animation.scene.transformation.Transformation.Factory
        public final Transformation create() {
            return new EdgeTranslate(this.edge, this.startsOutsideLayoutBounds, null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Edge.Resolved.values().length];
            try {
                iArr[Edge.Resolved.Top.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Edge.Resolved.Left.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Edge.Resolved.Bottom.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Edge.Resolved.Right.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ EdgeTranslate(Edge edge, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(edge, z);
    }

    @Override // com.android.compose.animation.scene.transformation.PropertyTransformation
    public final PropertyTransformation.Property getProperty() {
        return this.property;
    }

    @Override // com.android.compose.animation.scene.transformation.InterpolatedPropertyTransformation
    public final Object transform(PropertyTransformationScopeImpl propertyTransformationScopeImpl, ContentKey contentKey, ElementKey elementKey, TransitionState.Transition transition, Object obj) {
        SnapshotStateMap snapshotStateMap;
        Element.State state;
        long j = ((Offset) obj).packedValue;
        IntSize m923targetSizeGG5KONw = propertyTransformationScopeImpl.$$delegate_0.m923targetSizeGG5KONw(contentKey);
        if (m923targetSizeGG5KONw == null) {
            throw new IllegalStateException(("Content " + contentKey.debugName + " does not have a target size").toString());
        }
        Element element = (Element) propertyTransformationScopeImpl.$$delegate_0.layoutImpl.elements.get(elementKey);
        IntSize m859boximpl = (element == null || (snapshotStateMap = element.stateByContent) == null || (state = (Element.State) snapshotStateMap.get(contentKey)) == null) ? null : IntSize.m859boximpl(state.m921getTargetSizeYbymL2g());
        Element.Companion.getClass();
        IntSize intSize = m859boximpl == null ? false : IntSize.m861equalsimpl0(m859boximpl.packedValue, Element.SizeUnspecified) ? null : m859boximpl;
        if (intSize != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.edge.resolve(propertyTransformationScopeImpl.layoutImpl.layoutDirection).ordinal()];
            boolean z = this.startsOutsideLayoutBounds;
            long j2 = intSize.packedValue;
            if (i != 1) {
                if (i != 2) {
                    long j3 = m923targetSizeGG5KONw.packedValue;
                    if (i != 3) {
                        if (i != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (z) {
                            j = (Float.floatToRawIntBits((int) (j3 >> 32)) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L);
                            Offset.Companion companion = Offset.Companion;
                        } else {
                            j = (Float.floatToRawIntBits(((int) (j3 >> 32)) - ((int) (j2 >> 32))) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L);
                            Offset.Companion companion2 = Offset.Companion;
                        }
                    } else if (z) {
                        j = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) << 32) | (Float.floatToRawIntBits((int) (j3 & 4294967295L)) & 4294967295L);
                        Offset.Companion companion3 = Offset.Companion;
                    } else {
                        j = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) << 32) | (Float.floatToRawIntBits(((int) (j3 & 4294967295L)) - ((int) (j2 & 4294967295L))) & 4294967295L);
                        Offset.Companion companion4 = Offset.Companion;
                    }
                } else if (z) {
                    j = (Float.floatToRawIntBits(-((int) (j2 >> 32))) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L);
                    Offset.Companion companion5 = Offset.Companion;
                } else {
                    j = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j & 4294967295L))) & 4294967295L);
                    Offset.Companion companion6 = Offset.Companion;
                }
            } else if (z) {
                j = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) << 32) | (Float.floatToRawIntBits(-((int) (j2 & 4294967295L))) & 4294967295L);
                Offset.Companion companion7 = Offset.Companion;
            } else {
                j = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (j >> 32))) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                Offset.Companion companion8 = Offset.Companion;
            }
        }
        return Offset.m393boximpl(j);
    }

    private EdgeTranslate(Edge edge, boolean z) {
        this.edge = edge;
        this.startsOutsideLayoutBounds = z;
        this.property = PropertyTransformation.Property.Offset.INSTANCE;
    }
}
