package com.android.settingslib.volume.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AudioRepositoryImpl$mode$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioRepositoryImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AudioRepositoryImpl$mode$1$$ExternalSyntheticLambda0(AudioRepositoryImpl audioRepositoryImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = audioRepositoryImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.audioManager.removeOnModeChangedListener((AudioRepositoryImpl$mode$1$listener$1) this.f$1);
                break;
            case 1:
                this.f$0.audioManager.removeOnCommunicationDeviceChangedListener((AudioRepositoryImpl$communicationDevice$1$listener$1) this.f$1);
                break;
            default:
                this.f$0.contentResolver.unregisterContentObserver((AudioRepositoryImpl$volumeSettingChanges$1$observer$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
