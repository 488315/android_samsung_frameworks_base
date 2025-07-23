package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.compose.Screen;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingHomeKt$SettingHome$1$2$1$1$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function1 f$0;

    public /* synthetic */ SettingHomeKt$SettingHome$1$2$1$1$1$$ExternalSyntheticLambda0(Function1 function1, int i) {
        this.$r8$classId = i;
        this.f$0 = function1;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.WifiSpeakerPlaybackPreference.INSTANCE);
                this.f$0.mo779invoke(Screen.CastSetting.INSTANCE);
                break;
            case 1:
                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.WifiSpeakerPlaybackPreference.INSTANCE);
                this.f$0.mo779invoke(Screen.SpotifyCastSetting.INSTANCE);
                break;
            default:
                this.f$0.mo779invoke(Screen.LabsHome.INSTANCE);
                break;
        }
        return Unit.INSTANCE;
    }
}
