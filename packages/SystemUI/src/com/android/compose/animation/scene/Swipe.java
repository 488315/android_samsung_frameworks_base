package com.android.compose.animation.scene;

import androidx.compose.ui.input.pointer.PointerType;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.SwipeDirection;
import com.android.compose.animation.scene.SwipeSource;
import com.android.compose.animation.scene.UserAction;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Swipe extends UserAction {
    public static final Companion Companion = new Companion(null);
    public static final Swipe Down;
    public static final Swipe End;
    public static final Swipe Up;
    public final SwipeDirection direction;
    public final SwipeSource fromSource;
    public final int pointerCount;
    public final PointerType pointerType;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: Down-loWS4t8$default, reason: not valid java name */
        public static Swipe m928DownloWS4t8$default(Companion companion, int i, SwipeSource swipeSource, int i2) {
            if ((i2 & 1) != 0) {
                i = 1;
            }
            int i3 = i;
            if ((i2 & 4) != 0) {
                swipeSource = null;
            }
            companion.getClass();
            return new Swipe(SwipeDirection.Down, i3, null, swipeSource, null);
        }

        private Companion() {
        }
    }

    public final class Resolved extends UserAction.Resolved {
        public final SwipeDirection.Resolved direction;
        public final SwipeSource.Resolved fromSource;
        public final int pointerCount;
        public final PointerType pointerType;

        public /* synthetic */ Resolved(SwipeDirection.Resolved resolved, int i, SwipeSource.Resolved resolved2, PointerType pointerType, DefaultConstructorMarker defaultConstructorMarker) {
            this(resolved, i, resolved2, pointerType);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Resolved)) {
                return false;
            }
            Resolved resolved = (Resolved) obj;
            return this.direction == resolved.direction && this.pointerCount == resolved.pointerCount && Intrinsics.areEqual(this.fromSource, resolved.fromSource) && Intrinsics.areEqual(this.pointerType, resolved.pointerType);
        }

        public final int hashCode() {
            int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.pointerCount, this.direction.hashCode() * 31, 31);
            SwipeSource.Resolved resolved = this.fromSource;
            int iHashCode = (iM + (resolved == null ? 0 : resolved.hashCode())) * 31;
            PointerType pointerType = this.pointerType;
            return iHashCode + (pointerType != null ? Integer.hashCode(pointerType.value) : 0);
        }

        public final String toString() {
            return "Resolved(direction=" + this.direction + ", pointerCount=" + this.pointerCount + ", fromSource=" + this.fromSource + ", pointerType=" + this.pointerType + ")";
        }

        private Resolved(SwipeDirection.Resolved resolved, int i, SwipeSource.Resolved resolved2, PointerType pointerType) {
            super(null);
            this.direction = resolved;
            this.pointerCount = i;
            this.fromSource = resolved2;
            this.pointerType = pointerType;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        new Swipe(SwipeDirection.Left, 0, null, null, 14, null);
        Up = new Swipe(SwipeDirection.Up, 0, null, null, 14, null);
        int i = 14;
        int i2 = 0;
        PointerType pointerType = null;
        SwipeSource swipeSource = null;
        new Swipe(SwipeDirection.Right, i2, pointerType, swipeSource, i, 0 == true ? 1 : 0);
        int i3 = 14;
        int i4 = 0;
        PointerType pointerType2 = null;
        SwipeSource swipeSource2 = null;
        Down = new Swipe(SwipeDirection.Down, i4, pointerType2, swipeSource2, i3, 0 == true ? 1 : 0);
        new Swipe(SwipeDirection.Start, i2, pointerType, swipeSource, i, 0 == true ? 1 : 0);
        End = new Swipe(SwipeDirection.End, i4, pointerType2, swipeSource2, i3, 0 == true ? 1 : 0);
    }

    public /* synthetic */ Swipe(SwipeDirection swipeDirection, int i, PointerType pointerType, SwipeSource swipeSource, DefaultConstructorMarker defaultConstructorMarker) {
        this(swipeDirection, i, pointerType, swipeSource);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Swipe)) {
            return false;
        }
        Swipe swipe = (Swipe) obj;
        return this.direction == swipe.direction && this.pointerCount == swipe.pointerCount && Intrinsics.areEqual(this.pointerType, swipe.pointerType) && Intrinsics.areEqual(this.fromSource, swipe.fromSource);
    }

    public final int hashCode() {
        int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.pointerCount, this.direction.hashCode() * 31, 31);
        PointerType pointerType = this.pointerType;
        int iHashCode = (iM + (pointerType == null ? 0 : Integer.hashCode(pointerType.value))) * 31;
        SwipeSource swipeSource = this.fromSource;
        return iHashCode + (swipeSource != null ? swipeSource.hashCode() : 0);
    }

    @Override // com.android.compose.animation.scene.UserAction
    public final UserAction.Resolved resolve$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(LayoutDirection layoutDirection) {
        SwipeDirection.Resolved resolved = (SwipeDirection.Resolved) this.direction.getResolve$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout().mo781invoke(layoutDirection);
        SwipeSource swipeSource = this.fromSource;
        return new Resolved(resolved, this.pointerCount, swipeSource != null ? swipeSource.resolve(layoutDirection) : null, this.pointerType, null);
    }

    public final String toString() {
        return "Swipe(direction=" + this.direction + ", pointerCount=" + this.pointerCount + ", pointerType=" + this.pointerType + ", fromSource=" + this.fromSource + ")";
    }

    public /* synthetic */ Swipe(SwipeDirection swipeDirection, int i, PointerType pointerType, SwipeSource swipeSource, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(swipeDirection, (i2 & 2) != 0 ? 1 : i, (i2 & 4) != 0 ? null : pointerType, (i2 & 8) != 0 ? null : swipeSource);
    }

    private Swipe(SwipeDirection swipeDirection, int i, PointerType pointerType, SwipeSource swipeSource) {
        super(null);
        this.direction = swipeDirection;
        this.pointerCount = i;
        this.pointerType = pointerType;
        this.fromSource = swipeSource;
    }
}
