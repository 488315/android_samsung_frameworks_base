package com.android.systemui.media.mediaoutput.compose.common;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface MediaOutputState {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[StateInfo.values().length];
                try {
                    iArr[StateInfo.PreShow.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[StateInfo.Dismissing.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[StateInfo.Dismissed.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private Companion() {
        }

        public static State rememberMediaOutputState(Composer composer) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-835017493);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.common.MediaOutputState.Companion.rememberMediaOutputState (Feature.kt:137)");
            }
            MediaOutputState mediaOutputState = (MediaOutputState) composerImpl.consume(CompositionExtKt.LocalMediaOutputState);
            composerImpl.startReplaceGroup(1632614873);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = ((Feature) mediaOutputState).state;
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return state;
        }

        public static State rememberShownState(Composer composer) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-270656097);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.common.MediaOutputState.Companion.rememberShownState (Feature.kt:156)");
            }
            State rememberMediaOutputState = rememberMediaOutputState(composerImpl);
            composerImpl.startReplaceGroup(-1425750220);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = SnapshotStateKt.derivedStateOf(new MediaOutputState$Companion$$ExternalSyntheticLambda0(rememberMediaOutputState, 0));
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return state;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StateInfo {
        public static final /* synthetic */ StateInfo[] $VALUES;
        public static final StateInfo Dismissed;
        public static final StateInfo Dismissing;
        public static final StateInfo PreShow;
        public static final StateInfo Showing;
        public static final StateInfo Shown;

        static {
            StateInfo stateInfo = new StateInfo("PreShow", 0);
            PreShow = stateInfo;
            StateInfo stateInfo2 = new StateInfo("Showing", 1);
            Showing = stateInfo2;
            StateInfo stateInfo3 = new StateInfo("Shown", 2);
            Shown = stateInfo3;
            StateInfo stateInfo4 = new StateInfo("Dismissing", 3);
            Dismissing = stateInfo4;
            StateInfo stateInfo5 = new StateInfo("Dismissed", 4);
            Dismissed = stateInfo5;
            StateInfo[] stateInfoArr = {stateInfo, stateInfo2, stateInfo3, stateInfo4, stateInfo5};
            $VALUES = stateInfoArr;
            EnumEntriesKt.enumEntries(stateInfoArr);
        }

        private StateInfo(String str, int i) {
        }

        public static StateInfo valueOf(String str) {
            return (StateInfo) Enum.valueOf(StateInfo.class, str);
        }

        public static StateInfo[] values() {
            return (StateInfo[]) $VALUES.clone();
        }
    }
}
