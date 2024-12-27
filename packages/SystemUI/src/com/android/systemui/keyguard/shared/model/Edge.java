package com.android.systemui.keyguard.shared.model;

import android.util.Log;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class Edge {
    public static final Companion Companion = new Companion(null);
    public static final StateToState INVALID = null;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static StateToState create$default(Companion companion, KeyguardState keyguardState, KeyguardState keyguardState2, int i) {
            if ((i & 1) != 0) {
                keyguardState = null;
            }
            if ((i & 2) != 0) {
                keyguardState2 = null;
            }
            return KeyguardTransitionInteractor$2$1$$ExternalSyntheticOutline0.m(companion, keyguardState, keyguardState2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static StateToScene create$default(Companion companion, SceneKey sceneKey) {
            companion.getClass();
            return new StateToScene(null, sceneKey);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SceneToState extends Edge {
        public final SceneKey from;
        public final KeyguardState to;

        public SceneToState(SceneKey sceneKey, KeyguardState keyguardState) {
            super(null);
            this.from = sceneKey;
            this.to = keyguardState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SceneToState)) {
                return false;
            }
            SceneToState sceneToState = (SceneToState) obj;
            return Intrinsics.areEqual(this.from, sceneToState.from) && this.to == sceneToState.to;
        }

        public final int hashCode() {
            int hashCode = this.from.identity.hashCode() * 31;
            KeyguardState keyguardState = this.to;
            return hashCode + (keyguardState == null ? 0 : keyguardState.hashCode());
        }

        public final String toString() {
            return "SceneToState(from=" + this.from + ", to=" + this.to + ")";
        }

        public /* synthetic */ SceneToState(SceneKey sceneKey, KeyguardState keyguardState, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(sceneKey, (i & 2) != 0 ? null : keyguardState);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StateToScene extends Edge {
        public final KeyguardState from;
        public final SceneKey to;

        public StateToScene(KeyguardState keyguardState, SceneKey sceneKey) {
            super(null);
            this.from = keyguardState;
            this.to = sceneKey;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StateToScene)) {
                return false;
            }
            StateToScene stateToScene = (StateToScene) obj;
            return this.from == stateToScene.from && Intrinsics.areEqual(this.to, stateToScene.to);
        }

        public final int hashCode() {
            KeyguardState keyguardState = this.from;
            return this.to.identity.hashCode() + ((keyguardState == null ? 0 : keyguardState.hashCode()) * 31);
        }

        public final String toString() {
            return "StateToScene(from=" + this.from + ", to=" + this.to + ")";
        }

        public /* synthetic */ StateToScene(KeyguardState keyguardState, SceneKey sceneKey, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : keyguardState, sceneKey);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StateToState extends Edge {
        public final KeyguardState from;
        public final KeyguardState to;

        public StateToState(KeyguardState keyguardState, KeyguardState keyguardState2) {
            super(null);
            this.from = keyguardState;
            this.to = keyguardState2;
            if (keyguardState == null && keyguardState2 == null) {
                throw new IllegalStateException("to and from can't both be null".toString());
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
            int hashCode = (keyguardState == null ? 0 : keyguardState.hashCode()) * 31;
            KeyguardState keyguardState2 = this.to;
            return hashCode + (keyguardState2 != null ? keyguardState2.hashCode() : 0);
        }

        public final String toString() {
            return "StateToState(from=" + this.from + ", to=" + this.to + ")";
        }
    }

    static {
        KeyguardState keyguardState = KeyguardState.UNDEFINED;
        new StateToState(keyguardState, keyguardState);
    }

    private Edge() {
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
                case 12:
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
                case 12:
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        Flags.sceneContainer();
        KeyguardState keyguardState3 = KeyguardState.UNDEFINED;
        if (keyguardState == keyguardState3 || keyguardState2 == keyguardState3) {
            Log.e("Edge", "UNDEFINED should not be used when scene container is disabled");
        }
    }

    public /* synthetic */ Edge(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
