package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.DelegatableNode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslRecoilNodeFactory implements IndicationNodeFactory {
    public final ColorProducer colorProducer;
    public final SeslRecoilDrawStrategy drawStrategy;
    public final Function0 scale;
    public final Function0 shape;

    public /* synthetic */ SeslRecoilNodeFactory(long j, float f, Shape shape, SeslRecoilDrawStrategy seslRecoilDrawStrategy, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, f, shape, seslRecoilDrawStrategy);
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final DelegatableNode create(InteractionSource interactionSource) {
        return new DelegatingConfigurationAwareRecoilNode(interactionSource, this.colorProducer, this.scale, this.shape, this.drawStrategy);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslRecoilNodeFactory)) {
            return false;
        }
        SeslRecoilNodeFactory seslRecoilNodeFactory = (SeslRecoilNodeFactory) obj;
        return Intrinsics.areEqual(this.scale, seslRecoilNodeFactory.scale) && Intrinsics.areEqual(this.shape, seslRecoilNodeFactory.shape) && Intrinsics.areEqual(this.colorProducer, seslRecoilNodeFactory.colorProducer);
    }

    @Override // androidx.compose.foundation.IndicationNodeFactory
    public final int hashCode() {
        return this.colorProducer.hashCode() + ((this.shape.hashCode() + (this.scale.hashCode() * 31)) * 31);
    }

    private SeslRecoilNodeFactory(ColorProducer colorProducer, Function0 function0, Function0 function02, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        this.colorProducer = colorProducer;
        this.scale = function0;
        this.shape = function02;
        this.drawStrategy = seslRecoilDrawStrategy;
    }

    private SeslRecoilNodeFactory(final long j, final float f, Shape shape, SeslRecoilDrawStrategy seslRecoilDrawStrategy) {
        this(new ColorProducer() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilNodeFactory.1
            @Override // androidx.compose.ui.graphics.ColorProducer
            /* renamed from: invoke-0d7_KjU */
            public final long mo261invoke0d7_KjU() {
                return j;
            }
        }, new Function0() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilNodeFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(f);
            }
        }, new SeslRecoilNode$$ExternalSyntheticLambda3(shape, 2), seslRecoilDrawStrategy);
    }
}
