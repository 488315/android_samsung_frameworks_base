package com.android.compose.animation.scene;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SwipeDirection {
    public static final /* synthetic */ SwipeDirection[] $VALUES;
    public static final SwipeDirection Down;
    public static final SwipeDirection End;
    public static final SwipeDirection Left;
    public static final SwipeDirection Right;
    public static final SwipeDirection Start;
    public static final SwipeDirection Up;
    private final Function1 resolve;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Resolved {
        public static final /* synthetic */ Resolved[] $VALUES;
        public static final Resolved Down;
        public static final Resolved Left;
        public static final Resolved Right;
        public static final Resolved Up;
        private final Orientation orientation;

        static {
            Orientation orientation = Orientation.Vertical;
            Resolved resolved = new Resolved("Up", 0, orientation);
            Up = resolved;
            Resolved resolved2 = new Resolved("Down", 1, orientation);
            Down = resolved2;
            Orientation orientation2 = Orientation.Horizontal;
            Resolved resolved3 = new Resolved(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT, 2, orientation2);
            Left = resolved3;
            Resolved resolved4 = new Resolved(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT, 3, orientation2);
            Right = resolved4;
            Resolved[] resolvedArr = {resolved, resolved2, resolved3, resolved4};
            $VALUES = resolvedArr;
            EnumEntriesKt.enumEntries(resolvedArr);
        }

        private Resolved(String str, int i, Orientation orientation) {
            this.orientation = orientation;
        }

        public static Resolved valueOf(String str) {
            return (Resolved) Enum.valueOf(Resolved.class, str);
        }

        public static Resolved[] values() {
            return (Resolved[]) $VALUES.clone();
        }

        public final Orientation getOrientation() {
            return this.orientation;
        }
    }

    static {
        final int i = 0;
        SwipeDirection swipeDirection = new SwipeDirection("Up", 0, new Function1() { // from class: com.android.compose.animation.scene.SwipeDirection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i) {
                    case 0:
                        SwipeDirection swipeDirection2 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Up;
                    case 1:
                        SwipeDirection swipeDirection3 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Down;
                    case 2:
                        SwipeDirection swipeDirection4 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Left;
                    case 3:
                        SwipeDirection swipeDirection5 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Right;
                    case 4:
                        SwipeDirection swipeDirection6 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
                    default:
                        SwipeDirection swipeDirection7 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Right : SwipeDirection.Resolved.Left;
                }
            }
        });
        Up = swipeDirection;
        final int i2 = 1;
        SwipeDirection swipeDirection2 = new SwipeDirection("Down", 1, new Function1() { // from class: com.android.compose.animation.scene.SwipeDirection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i2) {
                    case 0:
                        SwipeDirection swipeDirection22 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Up;
                    case 1:
                        SwipeDirection swipeDirection3 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Down;
                    case 2:
                        SwipeDirection swipeDirection4 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Left;
                    case 3:
                        SwipeDirection swipeDirection5 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Right;
                    case 4:
                        SwipeDirection swipeDirection6 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
                    default:
                        SwipeDirection swipeDirection7 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Right : SwipeDirection.Resolved.Left;
                }
            }
        });
        Down = swipeDirection2;
        final int i3 = 2;
        SwipeDirection swipeDirection3 = new SwipeDirection(SystemUIAnalytics.DT_BOUNCER_POSITION_LEFT, 2, new Function1() { // from class: com.android.compose.animation.scene.SwipeDirection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i3) {
                    case 0:
                        SwipeDirection swipeDirection22 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Up;
                    case 1:
                        SwipeDirection swipeDirection32 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Down;
                    case 2:
                        SwipeDirection swipeDirection4 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Left;
                    case 3:
                        SwipeDirection swipeDirection5 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Right;
                    case 4:
                        SwipeDirection swipeDirection6 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
                    default:
                        SwipeDirection swipeDirection7 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Right : SwipeDirection.Resolved.Left;
                }
            }
        });
        Left = swipeDirection3;
        final int i4 = 3;
        SwipeDirection swipeDirection4 = new SwipeDirection(SystemUIAnalytics.DT_BOUNCER_POSITION_RIGHT, 3, new Function1() { // from class: com.android.compose.animation.scene.SwipeDirection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i4) {
                    case 0:
                        SwipeDirection swipeDirection22 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Up;
                    case 1:
                        SwipeDirection swipeDirection32 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Down;
                    case 2:
                        SwipeDirection swipeDirection42 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Left;
                    case 3:
                        SwipeDirection swipeDirection5 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Right;
                    case 4:
                        SwipeDirection swipeDirection6 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
                    default:
                        SwipeDirection swipeDirection7 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Right : SwipeDirection.Resolved.Left;
                }
            }
        });
        Right = swipeDirection4;
        final int i5 = 4;
        SwipeDirection swipeDirection5 = new SwipeDirection("Start", 4, new Function1() { // from class: com.android.compose.animation.scene.SwipeDirection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i5) {
                    case 0:
                        SwipeDirection swipeDirection22 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Up;
                    case 1:
                        SwipeDirection swipeDirection32 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Down;
                    case 2:
                        SwipeDirection swipeDirection42 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Left;
                    case 3:
                        SwipeDirection swipeDirection52 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Right;
                    case 4:
                        SwipeDirection swipeDirection6 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
                    default:
                        SwipeDirection swipeDirection7 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Right : SwipeDirection.Resolved.Left;
                }
            }
        });
        Start = swipeDirection5;
        final int i6 = 5;
        SwipeDirection swipeDirection6 = new SwipeDirection("End", 5, new Function1() { // from class: com.android.compose.animation.scene.SwipeDirection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LayoutDirection layoutDirection = (LayoutDirection) obj;
                switch (i6) {
                    case 0:
                        SwipeDirection swipeDirection22 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Up;
                    case 1:
                        SwipeDirection swipeDirection32 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Down;
                    case 2:
                        SwipeDirection swipeDirection42 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Left;
                    case 3:
                        SwipeDirection swipeDirection52 = SwipeDirection.Up;
                        return SwipeDirection.Resolved.Right;
                    case 4:
                        SwipeDirection swipeDirection62 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Left : SwipeDirection.Resolved.Right;
                    default:
                        SwipeDirection swipeDirection7 = SwipeDirection.Up;
                        return layoutDirection == LayoutDirection.Ltr ? SwipeDirection.Resolved.Right : SwipeDirection.Resolved.Left;
                }
            }
        });
        End = swipeDirection6;
        SwipeDirection[] swipeDirectionArr = {swipeDirection, swipeDirection2, swipeDirection3, swipeDirection4, swipeDirection5, swipeDirection6};
        $VALUES = swipeDirectionArr;
        EnumEntriesKt.enumEntries(swipeDirectionArr);
    }

    private SwipeDirection(String str, int i, Function1 function1) {
        this.resolve = function1;
    }

    public static SwipeDirection valueOf(String str) {
        return (SwipeDirection) Enum.valueOf(SwipeDirection.class, str);
    }

    public static SwipeDirection[] values() {
        return (SwipeDirection[]) $VALUES.clone();
    }

    public final Function1 getResolve$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout() {
        return this.resolve;
    }
}
