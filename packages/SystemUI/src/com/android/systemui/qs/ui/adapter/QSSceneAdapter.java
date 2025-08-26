package com.android.systemui.qs.ui.adapter;

import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public interface QSSceneAdapter {

    public interface State {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class CLOSED implements State {
            public static final CLOSED INSTANCE = new CLOSED();
            public static final QSSceneAdapter$State$CLOSED$$ExternalSyntheticLambda0 expansion = new QSSceneAdapter$State$CLOSED$$ExternalSyntheticLambda0(0);
            public static final QSSceneAdapter$State$CLOSED$$ExternalSyntheticLambda0 squishiness = new QSSceneAdapter$State$CLOSED$$ExternalSyntheticLambda0(1);

            private CLOSED() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof CLOSED);
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final Function0 getExpansion() {
                return expansion;
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

        public final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();
            public static final Expanding QQS = null;
            public static final Expanding QS;

            static {
                new Expanding(new QSSceneAdapter$State$CLOSED$$ExternalSyntheticLambda0(2));
                QS = new Expanding(new QSSceneAdapter$State$CLOSED$$ExternalSyntheticLambda0(3));
            }

            private Companion() {
            }
        }

        public final class Expanding implements State {
            public final Function0 expansion;
            public final AnimatedBackgroundKt$$ExternalSyntheticLambda0 squishiness = new AnimatedBackgroundKt$$ExternalSyntheticLambda0();

            public Expanding(Function0 function0) {
                this.expansion = function0;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final Function0 getExpansion() {
                return this.expansion;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final Function0 getSquishiness() {
                return this.squishiness;
            }

            @Override // com.android.systemui.qs.ui.adapter.QSSceneAdapter.State
            public final boolean isVisible() {
                return true;
            }
        }

        Function0 getExpansion();

        Function0 getSquishiness();

        boolean isVisible();
    }
}
