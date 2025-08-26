package com.android.compose.animation.scene;

import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.Edge;
import com.android.compose.animation.scene.SwipeSource;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class Edge implements SwipeSource {
    public static final /* synthetic */ Edge[] $VALUES;
    public static final Edge Bottom;
    public static final Edge End;
    public static final Edge Top;
    private final Function1 resolveEdge;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Resolved implements SwipeSource.Resolved {
        public static final /* synthetic */ Resolved[] $VALUES;
        public static final Resolved Bottom;
        public static final Resolved Left;
        public static final Resolved Right;
        public static final Resolved Top;

        static {
            Resolved resolved = new Resolved(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT, 0);
            Left = resolved;
            Resolved resolved2 = new Resolved(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT, 1);
            Right = resolved2;
            Resolved resolved3 = new Resolved("Top", 2);
            Top = resolved3;
            Resolved resolved4 = new Resolved("Bottom", 3);
            Bottom = resolved4;
            Resolved[] resolvedArr = {resolved, resolved2, resolved3, resolved4};
            $VALUES = resolvedArr;
            EnumEntriesKt.enumEntries(resolvedArr);
        }

        private Resolved(String str, int i) {
        }

        public static Resolved valueOf(String str) {
            return (Resolved) Enum.valueOf(Resolved.class, str);
        }

        public static Resolved[] values() {
            return (Resolved[]) $VALUES.clone();
        }
    }

    static {
        final int i = 0;
        Edge edge = new Edge("Top", 0, new Function1() { // from class: com.android.compose.animation.scene.Edge$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i) {
                    case 0:
                        Edge edge2 = Edge.Top;
                        return Edge.Resolved.Top;
                    case 1:
                        Edge edge3 = Edge.Top;
                        return Edge.Resolved.Bottom;
                    case 2:
                        Edge edge4 = Edge.Top;
                        return Edge.Resolved.Left;
                    case 3:
                        Edge edge5 = Edge.Top;
                        return Edge.Resolved.Right;
                    case 4:
                        Edge edge6 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Left : Edge.Resolved.Right;
                    default:
                        Edge edge7 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Right : Edge.Resolved.Left;
                }
            }
        });
        Top = edge;
        final int i2 = 1;
        Edge edge2 = new Edge("Bottom", 1, new Function1() { // from class: com.android.compose.animation.scene.Edge$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i2) {
                    case 0:
                        Edge edge22 = Edge.Top;
                        return Edge.Resolved.Top;
                    case 1:
                        Edge edge3 = Edge.Top;
                        return Edge.Resolved.Bottom;
                    case 2:
                        Edge edge4 = Edge.Top;
                        return Edge.Resolved.Left;
                    case 3:
                        Edge edge5 = Edge.Top;
                        return Edge.Resolved.Right;
                    case 4:
                        Edge edge6 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Left : Edge.Resolved.Right;
                    default:
                        Edge edge7 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Right : Edge.Resolved.Left;
                }
            }
        });
        Bottom = edge2;
        final int i3 = 2;
        Edge edge3 = new Edge(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT, 2, new Function1() { // from class: com.android.compose.animation.scene.Edge$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i3) {
                    case 0:
                        Edge edge22 = Edge.Top;
                        return Edge.Resolved.Top;
                    case 1:
                        Edge edge32 = Edge.Top;
                        return Edge.Resolved.Bottom;
                    case 2:
                        Edge edge4 = Edge.Top;
                        return Edge.Resolved.Left;
                    case 3:
                        Edge edge5 = Edge.Top;
                        return Edge.Resolved.Right;
                    case 4:
                        Edge edge6 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Left : Edge.Resolved.Right;
                    default:
                        Edge edge7 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Right : Edge.Resolved.Left;
                }
            }
        });
        final int i4 = 3;
        Edge edge4 = new Edge(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT, 3, new Function1() { // from class: com.android.compose.animation.scene.Edge$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i4) {
                    case 0:
                        Edge edge22 = Edge.Top;
                        return Edge.Resolved.Top;
                    case 1:
                        Edge edge32 = Edge.Top;
                        return Edge.Resolved.Bottom;
                    case 2:
                        Edge edge42 = Edge.Top;
                        return Edge.Resolved.Left;
                    case 3:
                        Edge edge5 = Edge.Top;
                        return Edge.Resolved.Right;
                    case 4:
                        Edge edge6 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Left : Edge.Resolved.Right;
                    default:
                        Edge edge7 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Right : Edge.Resolved.Left;
                }
            }
        });
        final int i5 = 4;
        Edge edge5 = new Edge("Start", 4, new Function1() { // from class: com.android.compose.animation.scene.Edge$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i5) {
                    case 0:
                        Edge edge22 = Edge.Top;
                        return Edge.Resolved.Top;
                    case 1:
                        Edge edge32 = Edge.Top;
                        return Edge.Resolved.Bottom;
                    case 2:
                        Edge edge42 = Edge.Top;
                        return Edge.Resolved.Left;
                    case 3:
                        Edge edge52 = Edge.Top;
                        return Edge.Resolved.Right;
                    case 4:
                        Edge edge6 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Left : Edge.Resolved.Right;
                    default:
                        Edge edge7 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Right : Edge.Resolved.Left;
                }
            }
        });
        final int i6 = 5;
        Edge edge6 = new Edge("End", 5, new Function1() { // from class: com.android.compose.animation.scene.Edge$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i6) {
                    case 0:
                        Edge edge22 = Edge.Top;
                        return Edge.Resolved.Top;
                    case 1:
                        Edge edge32 = Edge.Top;
                        return Edge.Resolved.Bottom;
                    case 2:
                        Edge edge42 = Edge.Top;
                        return Edge.Resolved.Left;
                    case 3:
                        Edge edge52 = Edge.Top;
                        return Edge.Resolved.Right;
                    case 4:
                        Edge edge62 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Left : Edge.Resolved.Right;
                    default:
                        Edge edge7 = Edge.Top;
                        return layoutDirection == LayoutDirection.Ltr ? Edge.Resolved.Right : Edge.Resolved.Left;
                }
            }
        });
        End = edge6;
        Edge[] edgeArr = {edge, edge2, edge3, edge4, edge5, edge6};
        $VALUES = edgeArr;
        EnumEntriesKt.enumEntries(edgeArr);
    }

    private Edge(String str, int i, Function1 function1) {
        this.resolveEdge = function1;
    }

    public static Edge valueOf(String str) {
        return (Edge) Enum.valueOf(Edge.class, str);
    }

    public static Edge[] values() {
        return (Edge[]) $VALUES.clone();
    }

    @Override // com.android.compose.animation.scene.SwipeSource
    public final Resolved resolve(LayoutDirection layoutDirection) {
        return (Resolved) this.resolveEdge.mo781invoke(layoutDirection);
    }
}
