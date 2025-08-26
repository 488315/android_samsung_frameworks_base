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

/* loaded from: classes.dex */
public final class EdgeTranslate implements InterpolatedPropertyTransformation {
    public final Edge edge;
    public final PropertyTransformation.Property.Offset property;
    public final boolean startsOutsideLayoutBounds;

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
        long jFloatToRawIntBits = ((Offset) obj).packedValue;
        IntSize intSizeM925targetSizeGG5KONw = propertyTransformationScopeImpl.$$delegate_0.m925targetSizeGG5KONw(contentKey);
        if (intSizeM925targetSizeGG5KONw == null) {
            throw new IllegalStateException(("Content " + contentKey.debugName + " does not have a target size").toString());
        }
        Element element = (Element) propertyTransformationScopeImpl.$$delegate_0.layoutImpl.elements.get(elementKey);
        IntSize intSizeM861boximpl = (element == null || (snapshotStateMap = element.stateByContent) == null || (state = (Element.State) snapshotStateMap.get(contentKey)) == null) ? null : IntSize.m861boximpl(state.m923getTargetSizeYbymL2g());
        Element.Companion.getClass();
        IntSize intSize = intSizeM861boximpl == null ? false : IntSize.m863equalsimpl0(intSizeM861boximpl.packedValue, Element.SizeUnspecified) ? null : intSizeM861boximpl;
        if (intSize != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.edge.resolve(propertyTransformationScopeImpl.layoutImpl.layoutDirection).ordinal()];
            boolean z = this.startsOutsideLayoutBounds;
            long j = intSize.packedValue;
            if (i != 1) {
                if (i != 2) {
                    long j2 = intSizeM925targetSizeGG5KONw.packedValue;
                    if (i != 3) {
                        if (i != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        if (z) {
                            jFloatToRawIntBits = (Float.floatToRawIntBits((int) (j2 >> 32)) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L))) & 4294967295L);
                            Offset.Companion companion = Offset.Companion;
                        } else {
                            jFloatToRawIntBits = (Float.floatToRawIntBits(((int) (j2 >> 32)) - ((int) (j >> 32))) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L))) & 4294967295L);
                            Offset.Companion companion2 = Offset.Companion;
                        }
                    } else if (z) {
                        jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32))) << 32) | (Float.floatToRawIntBits((int) (j2 & 4294967295L)) & 4294967295L);
                        Offset.Companion companion3 = Offset.Companion;
                    } else {
                        jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32))) << 32) | (Float.floatToRawIntBits(((int) (j2 & 4294967295L)) - ((int) (j & 4294967295L))) & 4294967295L);
                        Offset.Companion companion4 = Offset.Companion;
                    }
                } else if (z) {
                    jFloatToRawIntBits = (Float.floatToRawIntBits(-((int) (j >> 32))) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L))) & 4294967295L);
                    Offset.Companion companion5 = Offset.Companion;
                } else {
                    jFloatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits & 4294967295L))) & 4294967295L);
                    Offset.Companion companion6 = Offset.Companion;
                }
            } else if (z) {
                jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32))) << 32) | (Float.floatToRawIntBits(-((int) (j & 4294967295L))) & 4294967295L);
                Offset.Companion companion7 = Offset.Companion;
            } else {
                jFloatToRawIntBits = (Float.floatToRawIntBits(Float.intBitsToFloat((int) (jFloatToRawIntBits >> 32))) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
                Offset.Companion companion8 = Offset.Companion;
            }
        }
        return Offset.m395boximpl(jFloatToRawIntBits);
    }

    private EdgeTranslate(Edge edge, boolean z) {
        this.edge = edge;
        this.startsOutsideLayoutBounds = z;
        this.property = PropertyTransformation.Property.Offset.INSTANCE;
    }
}
