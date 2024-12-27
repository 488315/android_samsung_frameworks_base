package com.android.systemui.media.mediaoutput.compose.common;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;

public interface MediaOutputState {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public static State rememberButtonVisibleState(Composer composer) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(2001161520);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final State rememberMediaOutputState = rememberMediaOutputState(composerImpl);
            composerImpl.startReplaceGroup(-956797961);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.common.MediaOutputState$Companion$rememberButtonVisibleState$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        State state = State.this;
                        MediaOutputState.Companion companion = MediaOutputState.Companion.$$INSTANCE;
                        return Boolean.valueOf(((MediaOutputState.StateInfo) state.getValue()) == MediaOutputState.StateInfo.Shown);
                    }
                });
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
            return state;
        }

        public static State rememberDismissState(Composer composer) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(1404299270);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final State rememberMediaOutputState = rememberMediaOutputState(composerImpl);
            composerImpl.startReplaceGroup(1441979893);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.common.MediaOutputState$Companion$rememberDismissState$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        State state = State.this;
                        MediaOutputState.Companion companion = MediaOutputState.Companion.$$INSTANCE;
                        return Boolean.valueOf(((MediaOutputState.StateInfo) state.getValue()) == MediaOutputState.StateInfo.Dismissed);
                    }
                });
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
            return state;
        }

        public static State rememberEnterState(Composer composer) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-1956660936);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final State rememberMediaOutputState = rememberMediaOutputState(composerImpl);
            composerImpl.startReplaceGroup(374945301);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.common.MediaOutputState$Companion$rememberEnterState$1$1

                    public abstract /* synthetic */ class WhenMappings {
                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                        static {
                            int[] iArr = new int[MediaOutputState.StateInfo.values().length];
                            try {
                                iArr[MediaOutputState.StateInfo.PreShow.ordinal()] = 1;
                            } catch (NoSuchFieldError unused) {
                            }
                            try {
                                iArr[MediaOutputState.StateInfo.Dismissing.ordinal()] = 2;
                            } catch (NoSuchFieldError unused2) {
                            }
                            try {
                                iArr[MediaOutputState.StateInfo.Dismissed.ordinal()] = 3;
                            } catch (NoSuchFieldError unused3) {
                            }
                            $EnumSwitchMapping$0 = iArr;
                        }
                    }

                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        State state = State.this;
                        MediaOutputState.Companion companion = MediaOutputState.Companion.$$INSTANCE;
                        int i = WhenMappings.$EnumSwitchMapping$0[((MediaOutputState.StateInfo) state.getValue()).ordinal()];
                        return Boolean.valueOf((i == 1 || i == 2 || i == 3) ? false : true);
                    }
                });
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
            return state;
        }

        public static State rememberMediaOutputState(Composer composer) {
            ComposerImpl composerImpl = (ComposerImpl) composer;
            composerImpl.startReplaceGroup(-835017493);
            OpaqueKey opaqueKey = ComposerKt.invocation;
            MediaOutputState mediaOutputState = (MediaOutputState) composerImpl.consume(CompositionExtKt.LocalMediaOutputState);
            composerImpl.startReplaceGroup(-640072193);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = ((Feature) mediaOutputState).state;
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            composerImpl.end(false);
            return state;
        }
    }

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
