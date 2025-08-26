package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.compose.Screen;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class SelectorScreenKt$SelectorScreen$1$1$1$1$3$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function1 f$0;

    public /* synthetic */ SelectorScreenKt$SelectorScreen$1$1$1$1$3$1$$ExternalSyntheticLambda0(Function1 function1, int i) {
        this.$r8$classId = i;
        this.f$0 = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        String str = (String) obj;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mo781invoke(Screen.Phone.INSTANCE.createRoute(str));
                break;
            default:
                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.ChooseADeviceAction.INSTANCE);
                this.f$0.mo781invoke(Screen.TV.INSTANCE.createRoute(str));
                break;
        }
        return Unit.INSTANCE;
    }
}
