package com.android.systemui.blur.data.repository;

import com.android.systemui.util.SettingsHelper;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class SecBlurSettingsRepositoryImpl$blurReduced$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SettingsHelper f$0;
    public final /* synthetic */ SettingsHelper.OnChangedCallback f$1;

    public /* synthetic */ SecBlurSettingsRepositoryImpl$blurReduced$1$$ExternalSyntheticLambda0(SettingsHelper settingsHelper, SettingsHelper.OnChangedCallback onChangedCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = settingsHelper;
        this.f$1 = onChangedCallback;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.unregisterCallback((SecBlurSettingsRepositoryImpl$blurReduced$1$settingsCallback$1) this.f$1);
                break;
            default:
                this.f$0.unregisterCallback((SecBlurSettingsRepositoryImpl$minimalBatteryUse$1$settingsCallback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
