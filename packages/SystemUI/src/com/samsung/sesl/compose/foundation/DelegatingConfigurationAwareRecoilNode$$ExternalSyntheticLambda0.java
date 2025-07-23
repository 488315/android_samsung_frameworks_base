package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslCommonTokens;
import com.samsung.sesl.compose.foundation.SeslFeedbackAlpha;
import com.samsung.sesl.compose.foundation.theme.BasicThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class DelegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DelegatingConfigurationAwareRecoilNode f$0;

    public /* synthetic */ DelegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0(DelegatingConfigurationAwareRecoilNode delegatingConfigurationAwareRecoilNode, int i) {
        this.$r8$classId = i;
        this.f$0 = delegatingConfigurationAwareRecoilNode;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final DelegatingConfigurationAwareRecoilNode delegatingConfigurationAwareRecoilNode = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                if (((SeslRecoilConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(delegatingConfigurationAwareRecoilNode, RecoilKt.LocalSeslRecoilConfiguration)) != null) {
                    ColorProducer colorProducer = new ColorProducer() { // from class: com.samsung.sesl.compose.foundation.DelegatingConfigurationAwareRecoilNode$attachNewRecoil$calculateColor$1
                        @Override // androidx.compose.ui.graphics.ColorProducer
                        /* renamed from: invoke-0d7_KjU */
                        public final long mo261invoke0d7_KjU() {
                            DelegatingConfigurationAwareRecoilNode delegatingConfigurationAwareRecoilNode2 = DelegatingConfigurationAwareRecoilNode.this;
                            long mo261invoke0d7_KjU = delegatingConfigurationAwareRecoilNode2.color.mo261invoke0d7_KjU();
                            if (mo261invoke0d7_KjU != 16) {
                                return mo261invoke0d7_KjU;
                            }
                            SeslRecoilConfiguration seslRecoilConfiguration = (SeslRecoilConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(delegatingConfigurationAwareRecoilNode2, RecoilKt.LocalSeslRecoilConfiguration);
                            if (seslRecoilConfiguration != null) {
                                long j = seslRecoilConfiguration.color;
                                if (j != 16) {
                                    return j;
                                }
                            }
                            boolean booleanValue = ((Boolean) CompositionLocalConsumerModifierNodeKt.currentValueOf(delegatingConfigurationAwareRecoilNode2, BasicThemeKt.LocalSeslInDarkTheme)).booleanValue();
                            SeslRecoilDefaults.INSTANCE.getClass();
                            if (booleanValue) {
                                SeslCommonTokens.Companion.getClass();
                                return SeslCommonTokens.darkCommonTokens.rippleColor;
                            }
                            SeslCommonTokens.Companion.getClass();
                            return SeslCommonTokens.lightCommonTokens.rippleColor;
                        }
                    };
                    DelegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0 delegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0 = new DelegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0(delegatingConfigurationAwareRecoilNode, 1);
                    Shape shape = (Shape) delegatingConfigurationAwareRecoilNode.shape.invoke();
                    float floatValue = ((Number) delegatingConfigurationAwareRecoilNode.scale.invoke()).floatValue();
                    Dp.Companion companion = Dp.Companion;
                    delegatingConfigurationAwareRecoilNode.delegate(new SeslRecoilNode(delegatingConfigurationAwareRecoilNode.interactionSource, true, floatValue, shape, colorProducer, PaddingKt.m119PaddingValues0680j_4(0), delegatingConfigurationAwareRecoilNode$$ExternalSyntheticLambda0, delegatingConfigurationAwareRecoilNode.drawStrategy));
                }
                return Unit.INSTANCE;
            default:
                SeslRecoilConfiguration seslRecoilConfiguration = (SeslRecoilConfiguration) CompositionLocalConsumerModifierNodeKt.currentValueOf(delegatingConfigurationAwareRecoilNode, RecoilKt.LocalSeslRecoilConfiguration);
                SeslFeedbackAlpha.Companion companion2 = SeslFeedbackAlpha.Companion;
                SeslFeedbackAlpha seslFeedbackAlpha = seslRecoilConfiguration != null ? seslRecoilConfiguration.feedbackAlpha : null;
                companion2.getClass();
                return SeslFeedbackAlpha.Companion.takeOrDefault(seslFeedbackAlpha);
        }
    }
}
