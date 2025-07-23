package com.android.compose.animation.scene;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class UserActionResult {
    public static final Companion Companion = new Companion(null);
    public final boolean requiresFullDistanceSwipe;
    public final TransitionKey transitionKey;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ChangeScene extends UserActionResult {
        public final boolean requiresFullDistanceSwipe;
        public final SceneKey toScene;
        public final TransitionKey transitionKey;

        public /* synthetic */ ChangeScene(SceneKey sceneKey, TransitionKey transitionKey, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(sceneKey, (i & 2) != 0 ? null : transitionKey, (i & 4) != 0 ? false : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ChangeScene)) {
                return false;
            }
            ChangeScene changeScene = (ChangeScene) obj;
            return Intrinsics.areEqual(this.toScene, changeScene.toScene) && Intrinsics.areEqual(this.transitionKey, changeScene.transitionKey) && this.requiresFullDistanceSwipe == changeScene.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final boolean getRequiresFullDistanceSwipe() {
            return this.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final TransitionKey getTransitionKey() {
            return this.transitionKey;
        }

        public final int hashCode() {
            int hashCode = this.toScene.identity.hashCode() * 31;
            TransitionKey transitionKey = this.transitionKey;
            return Boolean.hashCode(this.requiresFullDistanceSwipe) + ((hashCode + (transitionKey == null ? 0 : transitionKey.identity.hashCode())) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ChangeScene(toScene=");
            sb.append(this.toScene);
            sb.append(", transitionKey=");
            sb.append(this.transitionKey);
            sb.append(", requiresFullDistanceSwipe=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.requiresFullDistanceSwipe, ")");
        }

        public ChangeScene(SceneKey sceneKey, TransitionKey transitionKey, boolean z) {
            super(transitionKey, z, null);
            this.toScene = sceneKey;
            this.transitionKey = transitionKey;
            this.requiresFullDistanceSwipe = z;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ChangeScene invoke$default(Companion companion, SceneKey sceneKey, TransitionKey transitionKey, int i) {
            if ((i & 2) != 0) {
                transitionKey = null;
            }
            companion.getClass();
            return new ChangeScene(sceneKey, transitionKey, false);
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HideOverlay extends UserActionResult {
        public final OverlayKey overlay;
        public final boolean requiresFullDistanceSwipe;
        public final TransitionKey transitionKey;

        public /* synthetic */ HideOverlay(OverlayKey overlayKey, TransitionKey transitionKey, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(overlayKey, (i & 2) != 0 ? null : transitionKey, (i & 4) != 0 ? false : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HideOverlay)) {
                return false;
            }
            HideOverlay hideOverlay = (HideOverlay) obj;
            return Intrinsics.areEqual(this.overlay, hideOverlay.overlay) && Intrinsics.areEqual(this.transitionKey, hideOverlay.transitionKey) && this.requiresFullDistanceSwipe == hideOverlay.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final boolean getRequiresFullDistanceSwipe() {
            return this.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final TransitionKey getTransitionKey() {
            return this.transitionKey;
        }

        public final int hashCode() {
            int hashCode = this.overlay.identity.hashCode() * 31;
            TransitionKey transitionKey = this.transitionKey;
            return Boolean.hashCode(this.requiresFullDistanceSwipe) + ((hashCode + (transitionKey == null ? 0 : transitionKey.identity.hashCode())) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("HideOverlay(overlay=");
            sb.append(this.overlay);
            sb.append(", transitionKey=");
            sb.append(this.transitionKey);
            sb.append(", requiresFullDistanceSwipe=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.requiresFullDistanceSwipe, ")");
        }

        public HideOverlay(OverlayKey overlayKey, TransitionKey transitionKey, boolean z) {
            super(transitionKey, z, null);
            this.overlay = overlayKey;
            this.transitionKey = transitionKey;
            this.requiresFullDistanceSwipe = z;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReplaceByOverlay extends UserActionResult {
        public final OverlayKey overlay;
        public final boolean requiresFullDistanceSwipe;
        public final TransitionKey transitionKey;

        public /* synthetic */ ReplaceByOverlay(OverlayKey overlayKey, TransitionKey transitionKey, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(overlayKey, (i & 2) != 0 ? null : transitionKey, (i & 4) != 0 ? false : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ReplaceByOverlay)) {
                return false;
            }
            ReplaceByOverlay replaceByOverlay = (ReplaceByOverlay) obj;
            return Intrinsics.areEqual(this.overlay, replaceByOverlay.overlay) && Intrinsics.areEqual(this.transitionKey, replaceByOverlay.transitionKey) && this.requiresFullDistanceSwipe == replaceByOverlay.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final boolean getRequiresFullDistanceSwipe() {
            return this.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final TransitionKey getTransitionKey() {
            return this.transitionKey;
        }

        public final int hashCode() {
            int hashCode = this.overlay.identity.hashCode() * 31;
            TransitionKey transitionKey = this.transitionKey;
            return Boolean.hashCode(this.requiresFullDistanceSwipe) + ((hashCode + (transitionKey == null ? 0 : transitionKey.identity.hashCode())) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ReplaceByOverlay(overlay=");
            sb.append(this.overlay);
            sb.append(", transitionKey=");
            sb.append(this.transitionKey);
            sb.append(", requiresFullDistanceSwipe=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.requiresFullDistanceSwipe, ")");
        }

        public ReplaceByOverlay(OverlayKey overlayKey, TransitionKey transitionKey, boolean z) {
            super(transitionKey, z, null);
            this.overlay = overlayKey;
            this.transitionKey = transitionKey;
            this.requiresFullDistanceSwipe = z;
        }
    }

    public /* synthetic */ UserActionResult(TransitionKey transitionKey, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionKey, z);
    }

    public boolean getRequiresFullDistanceSwipe() {
        return this.requiresFullDistanceSwipe;
    }

    public TransitionKey getTransitionKey() {
        return this.transitionKey;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ShowOverlay extends UserActionResult {
        public final HideCurrentOverlays hideCurrentOverlays;
        public final OverlayKey overlay;
        public final boolean requiresFullDistanceSwipe;
        public final TransitionKey transitionKey;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class HideCurrentOverlays {

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class All extends HideCurrentOverlays {
                public static final All INSTANCE = new All();

                private All() {
                    super(null);
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class None extends HideCurrentOverlays {
                public static final None INSTANCE = new None();

                private None() {
                    super(null);
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class Some extends HideCurrentOverlays {
                public final Set overlays;

                public Some(Set<OverlayKey> set) {
                    super(null);
                    this.overlays = set;
                }

                public Some(OverlayKey... overlayKeyArr) {
                    this((Set<OverlayKey>) ArraysKt___ArraysKt.toSet(overlayKeyArr));
                }
            }

            public /* synthetic */ HideCurrentOverlays(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private HideCurrentOverlays() {
            }
        }

        public /* synthetic */ ShowOverlay(OverlayKey overlayKey, TransitionKey transitionKey, boolean z, HideCurrentOverlays hideCurrentOverlays, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(overlayKey, (i & 2) != 0 ? null : transitionKey, (i & 4) != 0 ? false : z, (i & 8) != 0 ? HideCurrentOverlays.None.INSTANCE : hideCurrentOverlays);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShowOverlay)) {
                return false;
            }
            ShowOverlay showOverlay = (ShowOverlay) obj;
            return Intrinsics.areEqual(this.overlay, showOverlay.overlay) && Intrinsics.areEqual(this.transitionKey, showOverlay.transitionKey) && this.requiresFullDistanceSwipe == showOverlay.requiresFullDistanceSwipe && Intrinsics.areEqual(this.hideCurrentOverlays, showOverlay.hideCurrentOverlays);
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final boolean getRequiresFullDistanceSwipe() {
            return this.requiresFullDistanceSwipe;
        }

        @Override // com.android.compose.animation.scene.UserActionResult
        public final TransitionKey getTransitionKey() {
            return this.transitionKey;
        }

        public final int hashCode() {
            int hashCode = this.overlay.identity.hashCode() * 31;
            TransitionKey transitionKey = this.transitionKey;
            return this.hideCurrentOverlays.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode + (transitionKey == null ? 0 : transitionKey.identity.hashCode())) * 31, 31, this.requiresFullDistanceSwipe);
        }

        public final String toString() {
            return "ShowOverlay(overlay=" + this.overlay + ", transitionKey=" + this.transitionKey + ", requiresFullDistanceSwipe=" + this.requiresFullDistanceSwipe + ", hideCurrentOverlays=" + this.hideCurrentOverlays + ")";
        }

        public ShowOverlay(OverlayKey overlayKey, TransitionKey transitionKey, boolean z, HideCurrentOverlays hideCurrentOverlays) {
            super(transitionKey, z, null);
            this.overlay = overlayKey;
            this.transitionKey = transitionKey;
            this.requiresFullDistanceSwipe = z;
            this.hideCurrentOverlays = hideCurrentOverlays;
        }
    }

    private UserActionResult(TransitionKey transitionKey, boolean z) {
        this.transitionKey = transitionKey;
        this.requiresFullDistanceSwipe = z;
    }

    public /* synthetic */ UserActionResult(TransitionKey transitionKey, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : transitionKey, z, null);
    }
}
