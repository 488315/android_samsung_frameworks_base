package com.android.compose.animation.scene.transformation;

import androidx.compose.ui.geometry.Offset;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.PropertyTransformationScopeImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.PropertyTransformation;
import com.android.compose.animation.scene.transformation.Transformation;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Translate implements InterpolatedPropertyTransformation {
    public final PropertyTransformation.Property.Offset property;
    public final float x;
    public final float y;

    public final class Factory implements Transformation.Factory {
        public final float x;
        public final float y;

        public /* synthetic */ Factory(float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, f2);
        }

        @Override // com.android.compose.animation.scene.transformation.Transformation.Factory
        public final Transformation create() {
            return new Translate(this.x, this.y, null);
        }

        private Factory(float f, float f2) {
            this.x = f;
            this.y = f2;
        }
    }

    public /* synthetic */ Translate(float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2);
    }

    @Override // com.android.compose.animation.scene.transformation.PropertyTransformation
    public final PropertyTransformation.Property getProperty() {
        return this.property;
    }

    @Override // com.android.compose.animation.scene.transformation.InterpolatedPropertyTransformation
    public final Object transform(PropertyTransformationScopeImpl propertyTransformationScopeImpl, ContentKey contentKey, ElementKey elementKey, TransitionState.Transition transition, Object obj) {
        long j = ((Offset) obj).packedValue;
        float density = (propertyTransformationScopeImpl.getDensity() * this.x) + Float.intBitsToFloat((int) (j >> 32));
        float density2 = (propertyTransformationScopeImpl.getDensity() * this.y) + Float.intBitsToFloat((int) (j & 4294967295L));
        return Offset.m395boximpl((Float.floatToRawIntBits(density2) & 4294967295L) | (Float.floatToRawIntBits(density) << 32));
    }

    private Translate(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.property = PropertyTransformation.Property.Offset.INSTANCE;
    }
}
