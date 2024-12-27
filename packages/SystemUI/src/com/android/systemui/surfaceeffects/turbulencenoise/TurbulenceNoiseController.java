package com.android.systemui.surfaceeffects.turbulencenoise;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TurbulenceNoiseController {
    public Companion.AnimationState state = Companion.AnimationState.NOT_PLAYING;
    public final TurbulenceNoiseView turbulenceNoiseView;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class AnimationState {
            public static final /* synthetic */ AnimationState[] $VALUES;
            public static final AnimationState EASE_IN;
            public static final AnimationState EASE_OUT;
            public static final AnimationState MAIN;
            public static final AnimationState NOT_PLAYING;

            static {
                AnimationState animationState = new AnimationState("EASE_IN", 0);
                EASE_IN = animationState;
                AnimationState animationState2 = new AnimationState("MAIN", 1);
                MAIN = animationState2;
                AnimationState animationState3 = new AnimationState("EASE_OUT", 2);
                EASE_OUT = animationState3;
                AnimationState animationState4 = new AnimationState("NOT_PLAYING", 3);
                NOT_PLAYING = animationState4;
                AnimationState[] animationStateArr = {animationState, animationState2, animationState3, animationState4};
                $VALUES = animationStateArr;
                EnumEntriesKt.enumEntries(animationStateArr);
            }

            private AnimationState(String str, int i) {
            }

            public static AnimationState valueOf(String str) {
                return (AnimationState) Enum.valueOf(AnimationState.class, str);
            }

            public static AnimationState[] values() {
                return (AnimationState[]) $VALUES.clone();
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TurbulenceNoiseController(TurbulenceNoiseView turbulenceNoiseView) {
        this.turbulenceNoiseView = turbulenceNoiseView;
        turbulenceNoiseView.setVisibility(4);
    }

    public final void setState(Companion.AnimationState animationState) {
        this.state = animationState;
        Companion.AnimationState animationState2 = Companion.AnimationState.NOT_PLAYING;
        TurbulenceNoiseView turbulenceNoiseView = this.turbulenceNoiseView;
        if (animationState != animationState2) {
            turbulenceNoiseView.setVisibility(0);
        } else {
            turbulenceNoiseView.setVisibility(4);
            turbulenceNoiseView.noiseConfig = null;
        }
    }

    public static /* synthetic */ void getState$annotations() {
    }
}
