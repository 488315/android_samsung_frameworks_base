package androidx.compose.ui.text;

import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.text.TextInclusionStrategy;

/* loaded from: classes.dex */
public interface TextInclusionStrategy {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final TextInclusionStrategy$Companion$$ExternalSyntheticLambda0 AnyOverlap;
        public static final TextInclusionStrategy$Companion$$ExternalSyntheticLambda0 ContainsCenter;

        /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.ui.text.TextInclusionStrategy$Companion$$ExternalSyntheticLambda0] */
        /* JADX WARN: Type inference failed for: r0v2, types: [androidx.compose.ui.text.TextInclusionStrategy$Companion$$ExternalSyntheticLambda0] */
        static {
            final int i = 0;
            AnyOverlap = new TextInclusionStrategy() { // from class: androidx.compose.ui.text.TextInclusionStrategy$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.text.TextInclusionStrategy
                public final boolean isIncluded(Rect rect, Rect rect2) {
                    switch (i) {
                        case 0:
                            TextInclusionStrategy.Companion companion = TextInclusionStrategy.Companion.$$INSTANCE;
                            return rect.overlaps(rect2);
                        default:
                            TextInclusionStrategy.Companion companion2 = TextInclusionStrategy.Companion.$$INSTANCE;
                            return rect2.m407containsk4lQ0M(rect.m409getCenterF1C5BW0());
                    }
                }
            };
            final int i2 = 1;
            ContainsCenter = new TextInclusionStrategy() { // from class: androidx.compose.ui.text.TextInclusionStrategy$Companion$$ExternalSyntheticLambda0
                @Override // androidx.compose.ui.text.TextInclusionStrategy
                public final boolean isIncluded(Rect rect, Rect rect2) {
                    switch (i2) {
                        case 0:
                            TextInclusionStrategy.Companion companion = TextInclusionStrategy.Companion.$$INSTANCE;
                            return rect.overlaps(rect2);
                        default:
                            TextInclusionStrategy.Companion companion2 = TextInclusionStrategy.Companion.$$INSTANCE;
                            return rect2.m407containsk4lQ0M(rect.m409getCenterF1C5BW0());
                    }
                }
            };
        }

        private Companion() {
        }
    }

    boolean isIncluded(Rect rect, Rect rect2);
}
