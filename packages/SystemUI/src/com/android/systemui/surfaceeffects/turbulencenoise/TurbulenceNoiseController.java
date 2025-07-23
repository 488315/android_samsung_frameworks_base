package com.android.systemui.surfaceeffects.turbulencenoise;

import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TurbulenceNoiseController {
    public final Companion.AnimationState state = Companion.AnimationState.NOT_PLAYING;
    public final TurbulenceNoiseView turbulenceNoiseView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class AnimationState {
            public static final /* synthetic */ AnimationState[] $VALUES;
            public static final AnimationState EASE_IN = null;
            public static final AnimationState EASE_OUT = null;
            public static final AnimationState MAIN = null;
            public static final AnimationState NOT_PLAYING;

            static {
                AnimationState animationState = new AnimationState("EASE_IN", 0);
                AnimationState animationState2 = new AnimationState("MAIN", 1);
                AnimationState animationState3 = new AnimationState("EASE_OUT", 2);
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

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TurbulenceNoiseController(TurbulenceNoiseView turbulenceNoiseView) {
        this.turbulenceNoiseView = turbulenceNoiseView;
        turbulenceNoiseView.setVisibility(4);
    }

    public static /* synthetic */ void getState$annotations() {
    }
}
