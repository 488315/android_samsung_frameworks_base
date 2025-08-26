package com.android.systemui.keyguard.shared.model;

import android.util.Log;
import com.android.compose.animation.scene.ContentKey;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class Edge {
    public static final Companion Companion = new Companion(null);
    public static final StateToState INVALID;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static StateToState create$default(Companion companion, KeyguardState keyguardState, KeyguardState keyguardState2, int i) {
            if ((i & 1) != 0) {
                keyguardState = null;
            }
            if ((i & 2) != 0) {
                keyguardState2 = null;
            }
            return KeyguardInteractor$$ExternalSyntheticOutline0.m(companion, keyguardState, keyguardState2);
        }

        private Companion() {
        }

        public static StateToContent create$default(Companion companion, ContentKey contentKey) {
            companion.getClass();
            return new StateToContent(null, contentKey);
        }
    }

    public final class ContentToState extends Edge {
        public final ContentKey from;
        public final KeyguardState to;

        public ContentToState(ContentKey contentKey, KeyguardState keyguardState) {
            super(null);
            this.from = contentKey;
            this.to = keyguardState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ContentToState)) {
                return false;
            }
            ContentToState contentToState = (ContentToState) obj;
            return Intrinsics.areEqual(this.from, contentToState.from) && this.to == contentToState.to;
        }

        public final int hashCode() {
            int iHashCode = this.from.identity.hashCode() * 31;
            KeyguardState keyguardState = this.to;
            return iHashCode + (keyguardState == null ? 0 : keyguardState.hashCode());
        }

        public final String toString() {
            return "ContentToState(from=" + this.from + ", to=" + this.to + ")";
        }

        public /* synthetic */ ContentToState(ContentKey contentKey, KeyguardState keyguardState, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(contentKey, (i & 2) != 0 ? null : keyguardState);
        }
    }

    public final class StateToContent extends Edge {
        public final KeyguardState from;
        public final ContentKey to;

        public StateToContent(KeyguardState keyguardState, ContentKey contentKey) {
            super(null);
            this.from = keyguardState;
            this.to = contentKey;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StateToContent)) {
                return false;
            }
            StateToContent stateToContent = (StateToContent) obj;
            return this.from == stateToContent.from && Intrinsics.areEqual(this.to, stateToContent.to);
        }

        public final int hashCode() {
            KeyguardState keyguardState = this.from;
            return this.to.identity.hashCode() + ((keyguardState == null ? 0 : keyguardState.hashCode()) * 31);
        }

        public final String toString() {
            return "StateToContent(from=" + this.from + ", to=" + this.to + ")";
        }

        public /* synthetic */ StateToContent(KeyguardState keyguardState, ContentKey contentKey, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : keyguardState, contentKey);
        }
    }

    public final class StateToState extends Edge {
        public final KeyguardState from;
        public final KeyguardState to;

        public StateToState(KeyguardState keyguardState, KeyguardState keyguardState2) {
            super(null);
            this.from = keyguardState;
            this.to = keyguardState2;
            if (keyguardState == null && keyguardState2 == null) {
                throw new IllegalStateException("to and from can't both be null");
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StateToState)) {
                return false;
            }
            StateToState stateToState = (StateToState) obj;
            return this.from == stateToState.from && this.to == stateToState.to;
        }

        public final int hashCode() {
            KeyguardState keyguardState = this.from;
            int iHashCode = (keyguardState == null ? 0 : keyguardState.hashCode()) * 31;
            KeyguardState keyguardState2 = this.to;
            return iHashCode + (keyguardState2 != null ? keyguardState2.hashCode() : 0);
        }

        public final String toString() {
            return "StateToState(from=" + this.from + ", to=" + this.to + ")";
        }
    }

    static {
        KeyguardState keyguardState = KeyguardState.UNDEFINED;
        INVALID = new StateToState(keyguardState, keyguardState);
    }

    public /* synthetic */ Edge(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static void verifyValidKeyguardStates(KeyguardState keyguardState, KeyguardState keyguardState2) {
        if (keyguardState != null) {
            switch (KeyguardState.WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        if (keyguardState2 != null) {
            switch (KeyguardState.WhenMappings.$EnumSwitchMapping$0[keyguardState2.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        KeyguardState keyguardState3 = KeyguardState.UNDEFINED;
        if (keyguardState == keyguardState3 || keyguardState2 == keyguardState3) {
            Log.e("Edge", "UNDEFINED should not be used when scene container is disabled");
        }
    }

    private Edge() {
    }
}
