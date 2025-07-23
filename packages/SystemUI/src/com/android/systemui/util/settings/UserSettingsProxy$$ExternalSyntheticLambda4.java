package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UserSettingsProxy$$ExternalSyntheticLambda4 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ UserSettingsProxy f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ ContentObserver f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ UserSettingsProxy$$ExternalSyntheticLambda4(UserSettingsProxy userSettingsProxy, Uri uri, ContentObserver contentObserver, int i) {
        this.f$0 = userSettingsProxy;
        this.f$1 = uri;
        this.f$2 = contentObserver;
        this.f$3 = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Unit registerContentObserverForUser$lambda$2;
        Unit registerContentObserverForUser$lambda$3;
        switch (this.$r8$classId) {
            case 0:
                registerContentObserverForUser$lambda$2 = UserSettingsProxy.registerContentObserverForUser$lambda$2(this.f$0, (String) this.f$1, this.f$2, this.f$3);
                return registerContentObserverForUser$lambda$2;
            default:
                registerContentObserverForUser$lambda$3 = UserSettingsProxy.registerContentObserverForUser$lambda$3(this.f$0, (Uri) this.f$1, this.f$2, this.f$3);
                return registerContentObserverForUser$lambda$3;
        }
    }

    public /* synthetic */ UserSettingsProxy$$ExternalSyntheticLambda4(UserSettingsProxy userSettingsProxy, String str, ContentObserver contentObserver, int i) {
        this.f$0 = userSettingsProxy;
        this.f$1 = str;
        this.f$2 = contentObserver;
        this.f$3 = i;
    }
}
