package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SettingsProxy$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ SettingsProxy f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ ContentObserver f$2;

    public /* synthetic */ SettingsProxy$$ExternalSyntheticLambda2(SettingsProxy settingsProxy, Uri uri, ContentObserver contentObserver) {
        this.f$0 = settingsProxy;
        this.f$1 = uri;
        this.f$2 = contentObserver;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Unit registerContentObserver$lambda$1;
        Unit registerContentObserver$lambda$2;
        switch (this.$r8$classId) {
            case 0:
                registerContentObserver$lambda$1 = SettingsProxy.registerContentObserver$lambda$1(this.f$0, (String) this.f$1, this.f$2);
                return registerContentObserver$lambda$1;
            default:
                registerContentObserver$lambda$2 = SettingsProxy.registerContentObserver$lambda$2(this.f$0, (Uri) this.f$1, this.f$2);
                return registerContentObserver$lambda$2;
        }
    }

    public /* synthetic */ SettingsProxy$$ExternalSyntheticLambda2(SettingsProxy settingsProxy, String str, ContentObserver contentObserver) {
        this.f$0 = settingsProxy;
        this.f$1 = str;
        this.f$2 = contentObserver;
    }
}
