package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.jvm.functions.Function0;

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
        switch (this.$r8$classId) {
            case 0:
                return SettingsProxy.registerContentObserver$lambda$1(this.f$0, (String) this.f$1, this.f$2);
            default:
                return SettingsProxy.registerContentObserver$lambda$2(this.f$0, (Uri) this.f$1, this.f$2);
        }
    }

    public /* synthetic */ SettingsProxy$$ExternalSyntheticLambda2(SettingsProxy settingsProxy, String str, ContentObserver contentObserver) {
        this.f$0 = settingsProxy;
        this.f$1 = str;
        this.f$2 = contentObserver;
    }
}
