package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class SettingsProxy$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ SettingsProxy f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ ContentObserver f$3;

    public /* synthetic */ SettingsProxy$$ExternalSyntheticLambda1(SettingsProxy settingsProxy, Uri uri, boolean z, ContentObserver contentObserver) {
        this.f$0 = settingsProxy;
        this.f$1 = uri;
        this.f$2 = z;
        this.f$3 = contentObserver;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return SettingsProxy.registerContentObserver$lambda$3(this.f$0, (String) this.f$1, this.f$2, this.f$3);
            default:
                return SettingsProxy.registerContentObserver$lambda$6(this.f$0, (Uri) this.f$1, this.f$2, this.f$3);
        }
    }

    public /* synthetic */ SettingsProxy$$ExternalSyntheticLambda1(SettingsProxy settingsProxy, String str, boolean z, ContentObserver contentObserver) {
        this.f$0 = settingsProxy;
        this.f$1 = str;
        this.f$2 = z;
        this.f$3 = contentObserver;
    }
}
