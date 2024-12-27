package com.android.systemui.qs.ui.adapter;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface QSSceneAdapter {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface State {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class CLOSED implements State {
            public static final CLOSED INSTANCE = new CLOSED();
            public static final Function0 squishiness = new Function0() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapter$State$CLOSED$squishiness$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(1.0f);
                }
            };

            private CLOSED() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof CLOSED);
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final float getExpansion() {
                return 0.0f;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final Function0 getSquishiness() {
                return squishiness;
            }

            public final int hashCode() {
                return 2128253062;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final boolean isVisible() {
                return false;
            }

            public final String toString() {
                return "CLOSED";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();
            public static final Expanding QQS = null;
            public static final Expanding QS;

            static {
                new Expanding(0.0f);
                QS = new Expanding(1.0f);
            }

            private Companion() {
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Expanding implements State {
            public final float expansion;
            public final boolean isVisible = true;
            public final Function0 squishiness = new Function0() { // from class: com.android.systemui.qs.ui.adapter.QSSceneAdapter$State$Expanding$squishiness$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(1.0f);
                }
            };

            public Expanding(float f) {
                this.expansion = f;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Expanding) && Float.compare(this.expansion, ((Expanding) obj).expansion) == 0;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final float getExpansion() {
                return this.expansion;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final Function0 getSquishiness() {
                return this.squishiness;
            }

            public final int hashCode() {
                return Float.hashCode(this.expansion);
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final boolean isVisible() {
                return this.isVisible;
            }

            public final String toString() {
                return "Expanding(expansion=" + this.expansion + ")";
            }
        }

        float getExpansion();

        Function0 getSquishiness();

        boolean isVisible();
    }
}
