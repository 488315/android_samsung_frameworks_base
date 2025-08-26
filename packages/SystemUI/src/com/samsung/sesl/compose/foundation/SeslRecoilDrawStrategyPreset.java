package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategyPreset;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public abstract class SeslRecoilDrawStrategyPreset implements SeslRecoilDrawStrategy {

    public final class FeedbackNonScaling extends SeslRecoilDrawStrategyPreset {
        public static final FeedbackNonScaling INSTANCE = new FeedbackNonScaling();

        private FeedbackNonScaling() {
            super(null);
        }

        @Override // com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy
        public final void draw(final LayoutNodeDrawScope layoutNodeDrawScope, SeslRecoilNode$$ExternalSyntheticLambda0 seslRecoilNode$$ExternalSyntheticLambda0, SeslRecoilNode$$ExternalSyntheticLambda2 seslRecoilNode$$ExternalSyntheticLambda2) {
            seslRecoilNode$$ExternalSyntheticLambda0.invoke(layoutNodeDrawScope, new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategyPreset$FeedbackNonScaling$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    SeslRecoilDrawStrategyPreset.FeedbackNonScaling feedbackNonScaling = SeslRecoilDrawStrategyPreset.FeedbackNonScaling.INSTANCE;
                    layoutNodeDrawScope.drawContent();
                    return Unit.INSTANCE;
                }
            });
            seslRecoilNode$$ExternalSyntheticLambda2.mo781invoke(layoutNodeDrawScope);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FeedbackNonScaling);
        }

        public final int hashCode() {
            return -1656498355;
        }

        public final String toString() {
            return "FeedbackNonScaling";
        }
    }

    public final class FeedbackScaling extends SeslRecoilDrawStrategyPreset {
        public static final FeedbackScaling INSTANCE = new FeedbackScaling();

        private FeedbackScaling() {
            super(null);
        }

        @Override // com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy
        public final void draw(final LayoutNodeDrawScope layoutNodeDrawScope, SeslRecoilNode$$ExternalSyntheticLambda0 seslRecoilNode$$ExternalSyntheticLambda0, final SeslRecoilNode$$ExternalSyntheticLambda2 seslRecoilNode$$ExternalSyntheticLambda2) {
            seslRecoilNode$$ExternalSyntheticLambda0.invoke(layoutNodeDrawScope, new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategyPreset$FeedbackScaling$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    SeslRecoilDrawStrategyPreset.FeedbackScaling feedbackScaling = SeslRecoilDrawStrategyPreset.FeedbackScaling.INSTANCE;
                    layoutNodeDrawScope.drawContent();
                    seslRecoilNode$$ExternalSyntheticLambda2.mo781invoke((DrawScope) obj);
                    return Unit.INSTANCE;
                }
            });
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FeedbackScaling);
        }

        public final int hashCode() {
            return -404343532;
        }

        public final String toString() {
            return "FeedbackScaling";
        }
    }

    public /* synthetic */ SeslRecoilDrawStrategyPreset(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private SeslRecoilDrawStrategyPreset() {
    }
}
