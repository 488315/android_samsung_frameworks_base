package com.android.systemui.media.mediaoutput.compose.common;

import androidx.compose.runtime.State;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputState$Companion$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ State f$0;

    public /* synthetic */ MediaOutputState$Companion$$ExternalSyntheticLambda0(State state, int i) {
        this.$r8$classId = i;
        this.f$0 = state;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        State state = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                MediaOutputState.Companion companion = MediaOutputState.Companion.$$INSTANCE;
                return Boolean.valueOf(((MediaOutputState.StateInfo) state.getValue()) == MediaOutputState.StateInfo.Shown);
            case 1:
                MediaOutputState.Companion companion2 = MediaOutputState.Companion.$$INSTANCE;
                int i = MediaOutputState.Companion.WhenMappings.$EnumSwitchMapping$0[((MediaOutputState.StateInfo) state.getValue()).ordinal()];
                if (i != 1 && i != 2 && i != 3) {
                    z = true;
                }
                return Boolean.valueOf(z);
            default:
                MediaOutputState.Companion companion3 = MediaOutputState.Companion.$$INSTANCE;
                return Boolean.valueOf(((MediaOutputState.StateInfo) state.getValue()) == MediaOutputState.StateInfo.Dismissed);
        }
    }
}
