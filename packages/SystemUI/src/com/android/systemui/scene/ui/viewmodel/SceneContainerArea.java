package com.android.systemui.scene.ui.viewmodel;

import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.SwipeSource;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class SceneContainerArea implements SwipeSource {
    public final Function1 resolveArea;

    public final class EndHalf extends SceneContainerArea {
        public static final EndHalf INSTANCE = new EndHalf();

        private EndHalf() {
            super(new SceneContainerArea$EndHalf$$ExternalSyntheticLambda0(0), null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof EndHalf);
        }

        @Override // com.android.compose.animation.scene.SwipeSource
        public final int hashCode() {
            return 798643269;
        }

        public final String toString() {
            return "EndHalf";
        }
    }

    public interface Resolved extends SwipeSource.Resolved {

        public final class BottomEdge implements Resolved {
            public static final BottomEdge INSTANCE = new BottomEdge();

            private BottomEdge() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof BottomEdge);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return 357717109;
            }

            public final String toString() {
                return "BottomEdge";
            }
        }

        public final class LeftEdge implements Resolved {
            public static final LeftEdge INSTANCE = new LeftEdge();

            private LeftEdge() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LeftEdge);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return 1836295057;
            }

            public final String toString() {
                return "LeftEdge";
            }
        }

        public final class LeftHalf implements Resolved {
            public static final LeftHalf INSTANCE = new LeftHalf();

            private LeftHalf() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LeftHalf);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return 1836381703;
            }

            public final String toString() {
                return "LeftHalf";
            }
        }

        public final class RightEdge implements Resolved {
            public static final RightEdge INSTANCE = new RightEdge();

            private RightEdge() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof RightEdge);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return 2098486892;
            }

            public final String toString() {
                return "RightEdge";
            }
        }

        public final class RightHalf implements Resolved {
            public static final RightHalf INSTANCE = new RightHalf();

            private RightHalf() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof RightHalf);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return 2098573538;
            }

            public final String toString() {
                return "RightHalf";
            }
        }

        public final class TopEdgeLeftHalf implements Resolved {
            public static final TopEdgeLeftHalf INSTANCE = new TopEdgeLeftHalf();

            private TopEdgeLeftHalf() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TopEdgeLeftHalf);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return 79675871;
            }

            public final String toString() {
                return "TopEdgeLeftHalf";
            }
        }

        public final class TopEdgeRightHalf implements Resolved {
            public static final TopEdgeRightHalf INSTANCE = new TopEdgeRightHalf();

            private TopEdgeRightHalf() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TopEdgeRightHalf);
            }

            @Override // com.android.compose.animation.scene.SwipeSource.Resolved
            public final int hashCode() {
                return -819699702;
            }

            public final String toString() {
                return "TopEdgeRightHalf";
            }
        }
    }

    public final class TopEdgeEndHalf extends SceneContainerArea {
        public static final TopEdgeEndHalf INSTANCE = new TopEdgeEndHalf();

        private TopEdgeEndHalf() {
            super(new SceneContainerArea$EndHalf$$ExternalSyntheticLambda0(1), null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TopEdgeEndHalf);
        }

        @Override // com.android.compose.animation.scene.SwipeSource
        public final int hashCode() {
            return 750988837;
        }

        public final String toString() {
            return "TopEdgeEndHalf";
        }
    }

    public final class TopEdgeStartHalf extends SceneContainerArea {
        public static final TopEdgeStartHalf INSTANCE = new TopEdgeStartHalf();

        private TopEdgeStartHalf() {
            super(new SceneContainerArea$EndHalf$$ExternalSyntheticLambda0(2), null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TopEdgeStartHalf);
        }

        @Override // com.android.compose.animation.scene.SwipeSource
        public final int hashCode() {
            return 1034521708;
        }

        public final String toString() {
            return "TopEdgeStartHalf";
        }
    }

    public /* synthetic */ SceneContainerArea(Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1);
    }

    @Override // com.android.compose.animation.scene.SwipeSource
    public final SwipeSource.Resolved resolve(LayoutDirection layoutDirection) {
        return (Resolved) this.resolveArea.mo781invoke(layoutDirection);
    }

    private SceneContainerArea(Function1 function1) {
        this.resolveArea = function1;
    }
}
