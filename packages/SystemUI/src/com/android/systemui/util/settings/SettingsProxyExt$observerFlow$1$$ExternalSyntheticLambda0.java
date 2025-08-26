package com.android.systemui.util.settings;

import com.android.systemui.util.settings.SettingsProxyExt;
import kotlin.jvm.functions.Function0;

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
        switch (this.$r8$classId) {
            case 0:
                return SettingsProxyExt.AnonymousClass1.invokeSuspend$lambda$1((UserSettingsProxy) this.f$0, (SettingsProxyExt$observerFlow$1$observer$1) this.f$1);
            default:
                return SettingsProxyExt.AnonymousClass2.invokeSuspend$lambda$1(this.f$0, (SettingsProxyExt$observerFlow$2$observer$1) this.f$1);
        }
    }

    public /* synthetic */ SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0(UserSettingsProxy userSettingsProxy, SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$1) {
        this.f$0 = userSettingsProxy;
        this.f$1 = settingsProxyExt$observerFlow$1$observer$1;
    }
}
