package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.media.mediaoutput.ext.AudioMirroringExtKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingViewModel$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingViewModel f$0;

    public /* synthetic */ SettingViewModel$$ExternalSyntheticLambda0(SettingViewModel settingViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = settingViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
        }
        return AudioMirroringExtKt.getAudioMirroringPackageName(this.f$0.context);
    }
}
