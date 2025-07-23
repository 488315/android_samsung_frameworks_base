package com.samsung.sesl.compose.foundation;

import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SeslRecoilDrawStrategy {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FeedbackNonScaling extends SeslRecoilDrawStrategy {
        public static final FeedbackNonScaling INSTANCE = new FeedbackNonScaling();

        private FeedbackNonScaling() {
            super(null);
        }

        @Override // com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy
        public final void draw$foundation_release(final LayoutNodeDrawScope layoutNodeDrawScope, SeslRecoilNode$$ExternalSyntheticLambda0 seslRecoilNode$$ExternalSyntheticLambda0, SeslRecoilNode$$ExternalSyntheticLambda2 seslRecoilNode$$ExternalSyntheticLambda2) {
            seslRecoilNode$$ExternalSyntheticLambda2.mo779invoke(layoutNodeDrawScope);
            seslRecoilNode$$ExternalSyntheticLambda0.invoke(layoutNodeDrawScope, new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy$FeedbackNonScaling$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    SeslRecoilDrawStrategy.FeedbackNonScaling feedbackNonScaling = SeslRecoilDrawStrategy.FeedbackNonScaling.INSTANCE;
                    LayoutNodeDrawScope.this.drawContent();
                    return Unit.INSTANCE;
                }
            });
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FeedbackNonScaling);
        }

        public final int hashCode() {
            return -1442093076;
        }

        public final String toString() {
            return "FeedbackNonScaling";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FeedbackScaling extends SeslRecoilDrawStrategy {
        public static final FeedbackScaling INSTANCE = new FeedbackScaling();

        private FeedbackScaling() {
            super(null);
        }

        @Override // com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy
        public final void draw$foundation_release(final LayoutNodeDrawScope layoutNodeDrawScope, SeslRecoilNode$$ExternalSyntheticLambda0 seslRecoilNode$$ExternalSyntheticLambda0, final SeslRecoilNode$$ExternalSyntheticLambda2 seslRecoilNode$$ExternalSyntheticLambda2) {
            seslRecoilNode$$ExternalSyntheticLambda0.invoke(layoutNodeDrawScope, new Function1() { // from class: com.samsung.sesl.compose.foundation.SeslRecoilDrawStrategy$FeedbackScaling$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    SeslRecoilDrawStrategy.FeedbackScaling feedbackScaling = SeslRecoilDrawStrategy.FeedbackScaling.INSTANCE;
                    SeslRecoilNode$$ExternalSyntheticLambda2.this.mo779invoke((DrawScope) obj);
                    layoutNodeDrawScope.drawContent();
                    return Unit.INSTANCE;
                }
            });
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FeedbackScaling);
        }

        public final int hashCode() {
            return -594640683;
        }

        public final String toString() {
            return "FeedbackScaling";
        }
    }

    public /* synthetic */ SeslRecoilDrawStrategy(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void draw$foundation_release(LayoutNodeDrawScope layoutNodeDrawScope, SeslRecoilNode$$ExternalSyntheticLambda0 seslRecoilNode$$ExternalSyntheticLambda0, SeslRecoilNode$$ExternalSyntheticLambda2 seslRecoilNode$$ExternalSyntheticLambda2);

    private SeslRecoilDrawStrategy() {
    }
}
