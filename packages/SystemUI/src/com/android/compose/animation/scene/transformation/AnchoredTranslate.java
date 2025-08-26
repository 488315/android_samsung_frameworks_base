package com.android.compose.animation.scene.transformation;

import androidx.compose.ui.geometry.Offset;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementStateScopeImpl;
import com.android.compose.animation.scene.PropertyTransformationScopeImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.PropertyTransformation;
import com.android.compose.animation.scene.transformation.Transformation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: classes.dex */
public final class AnchoredTranslate implements InterpolatedPropertyTransformation {
    public final ElementKey anchor;
    public final PropertyTransformation.Property.Offset property;

    public final class Factory implements Transformation.Factory {
        public final ElementKey anchor;

        public Factory(ElementKey elementKey) {
            this.anchor = elementKey;
        }

        @Override // com.android.compose.animation.scene.transformation.Transformation.Factory
        public final Transformation create() {
            return new AnchoredTranslate(this.anchor, null);
        }
    }

    public /* synthetic */ AnchoredTranslate(ElementKey elementKey, DefaultConstructorMarker defaultConstructorMarker) {
        this(elementKey);
    }

    public static final void transform_ahi2Abc$throwException(AnchoredTranslate anchoredTranslate, ContentKey contentKey) {
        throw new IllegalStateException(StringsKt__IndentKt.trimIndent("\n        Anchor " + anchoredTranslate.anchor.debugName + " does not have a target state in content " + (contentKey != null ? contentKey.debugName : null) + ".\n        This either means that it was not composed at all during the transition or that it was\n        composed too late, for instance during layout/subcomposition. To avoid flickers in\n        AnchoredTranslate, you should make sure that the composition and layout of anchor is *not*\n        deferred, for instance by moving it out of lazy layouts.\n    ").toString());
    }

    @Override // com.android.compose.animation.scene.transformation.PropertyTransformation
    public final PropertyTransformation.Property getProperty() {
        return this.property;
    }

    @Override // com.android.compose.animation.scene.transformation.InterpolatedPropertyTransformation
    public final Object transform(PropertyTransformationScopeImpl propertyTransformationScopeImpl, ContentKey contentKey, ElementKey elementKey, TransitionState.Transition transition, Object obj) {
        long jFloatToRawIntBits;
        long j;
        long j2 = ((Offset) obj).packedValue;
        ElementStateScopeImpl elementStateScopeImpl = propertyTransformationScopeImpl.$$delegate_0;
        ElementKey elementKey2 = this.anchor;
        ContentKey contentKey2 = transition.fromContent;
        Offset offsetM924targetOffsetGcwITfU = elementStateScopeImpl.m924targetOffsetGcwITfU(contentKey2, elementKey2);
        if (offsetM924targetOffsetGcwITfU == null) {
            transform_ahi2Abc$throwException(this, contentKey2);
            throw null;
        }
        ElementStateScopeImpl elementStateScopeImpl2 = propertyTransformationScopeImpl.$$delegate_0;
        ContentKey contentKey3 = transition.toContent;
        Offset offsetM924targetOffsetGcwITfU2 = elementStateScopeImpl2.m924targetOffsetGcwITfU(contentKey3, elementKey2);
        if (offsetM924targetOffsetGcwITfU2 == null) {
            transform_ahi2Abc$throwException(this, contentKey3);
            throw null;
        }
        long jM402minusMKHz9U = Offset.m402minusMKHz9U(offsetM924targetOffsetGcwITfU2.packedValue, offsetM924targetOffsetGcwITfU.packedValue);
        if (Intrinsics.areEqual(contentKey, contentKey3)) {
            float fIntBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32)) - Float.intBitsToFloat((int) (jM402minusMKHz9U >> 32));
            float fIntBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L)) - Float.intBitsToFloat((int) (jM402minusMKHz9U & 4294967295L));
            long jFloatToRawIntBits2 = Float.floatToRawIntBits(fIntBitsToFloat);
            jFloatToRawIntBits = Float.floatToRawIntBits(fIntBitsToFloat2);
            j = jFloatToRawIntBits2 << 32;
        } else {
            float fIntBitsToFloat3 = Float.intBitsToFloat((int) (jM402minusMKHz9U >> 32)) + Float.intBitsToFloat((int) (j2 >> 32));
            float fIntBitsToFloat4 = Float.intBitsToFloat((int) (jM402minusMKHz9U & 4294967295L)) + Float.intBitsToFloat((int) (j2 & 4294967295L));
            long jFloatToRawIntBits3 = Float.floatToRawIntBits(fIntBitsToFloat3);
            jFloatToRawIntBits = Float.floatToRawIntBits(fIntBitsToFloat4);
            j = jFloatToRawIntBits3 << 32;
        }
        return Offset.m395boximpl(j | (jFloatToRawIntBits & 4294967295L));
    }

    private AnchoredTranslate(ElementKey elementKey) {
        this.anchor = elementKey;
        this.property = PropertyTransformation.Property.Offset.INSTANCE;
    }
}
