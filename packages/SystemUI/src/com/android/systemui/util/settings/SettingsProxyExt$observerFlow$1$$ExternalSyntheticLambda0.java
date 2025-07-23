package com.android.systemui.util.settings;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ SettingsProxy f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0(SettingsProxy settingsProxy, SettingsProxyExt$observerFlow$2$observer$1 settingsProxyExt$observerFlow$2$observer$1) {
        this.f$0 = settingsProxy;
        this.f$1 = settingsProxyExt$observerFlow$2$observer$1;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Unit invokeSuspend$lambda$1;
        Unit invokeSuspend$lambda$12;
        switch (this.$r8$classId) {
            case 0:
                invokeSuspend$lambda$1 = SettingsProxyExt$observerFlow$1.invokeSuspend$lambda$1((UserSettingsProxy) this.f$0, (SettingsProxyExt$observerFlow$1$observer$1) this.f$1);
                return invokeSuspend$lambda$1;
            default:
                invokeSuspend$lambda$12 = SettingsProxyExt$observerFlow$2.invokeSuspend$lambda$1(this.f$0, (SettingsProxyExt$observerFlow$2$observer$1) this.f$1);
                return invokeSuspend$lambda$12;
        }
    }

    public /* synthetic */ SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0(UserSettingsProxy userSettingsProxy, SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$1) {
        this.f$0 = userSettingsProxy;
        this.f$1 = settingsProxyExt$observerFlow$1$observer$1;
    }
}
