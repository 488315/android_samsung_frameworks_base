package com.android.systemui.development.ui.compose;

import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.hapticfeedback.HapticFeedbackType;
import androidx.compose.ui.hapticfeedback.PlatformHapticFeedbackType;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.systemui.development.ui.viewmodel.BuildNumberViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.BufferedChannel;

/* loaded from: classes2.dex */
public final /* synthetic */ class BuildNumberKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ BuildNumberViewModel f$1;

    public /* synthetic */ BuildNumberKt$$ExternalSyntheticLambda1(Object obj, BuildNumberViewModel buildNumberViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = buildNumberViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SemanticsPropertiesKt.onLongClick((SemanticsPropertyReceiver) obj, (String) this.f$0, new BuildNumberKt$$ExternalSyntheticLambda0(this.f$1, 1));
                return Unit.INSTANCE;
            default:
                HapticFeedbackType.Companion.getClass();
                PlatformHapticFeedbackType.INSTANCE.getClass();
                ((HapticFeedback) this.f$0).mo572performHapticFeedbackCdsT49E(0);
                BufferedChannel bufferedChannel = this.f$1.copyRequests;
                Unit unit = Unit.INSTANCE;
                bufferedChannel.mo3475trySendJP2dKIU(unit);
                return unit;
        }
    }
}
