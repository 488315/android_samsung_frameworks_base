package com.android.systemui.media.mediaoutput.viewmodel;

import android.media.MediaRouter2Manager;
import com.android.systemui.media.mediaoutput.ext.AudioMirroringExtKt;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.functions.Function0;

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
            case 0:
                return MediaRouter2Manager.getInstance(this.f$0.context);
            case 1:
                return this.f$0.context.getSharedPreferences(SystemUIAnalytics.MEDIA_OUTPUT_PREF_NAME, 0);
            default:
                return AudioMirroringExtKt.getAudioMirroringPackageName(this.f$0.context);
        }
    }
}
