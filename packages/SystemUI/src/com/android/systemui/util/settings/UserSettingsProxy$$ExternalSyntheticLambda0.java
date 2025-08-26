package com.android.systemui.util.settings;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class UserSettingsProxy$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ UserSettingsProxy f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ ContentObserver f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ UserSettingsProxy$$ExternalSyntheticLambda0(UserSettingsProxy userSettingsProxy, Uri uri, boolean z, ContentObserver contentObserver, int i) {
        this.f$0 = userSettingsProxy;
        this.f$1 = uri;
        this.f$2 = z;
        this.f$3 = contentObserver;
        this.f$4 = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ContentObserver contentObserver = this.f$3;
                return UserSettingsProxy.registerContentObserverForUser$lambda$4(this.f$0, (String) this.f$1, this.f$2, contentObserver, this.f$4);
            default:
                return UserSettingsProxy.registerContentObserverForUser$lambda$7(this.f$0, (Uri) this.f$1, this.f$2, this.f$3, this.f$4);
        }
    }

    public /* synthetic */ UserSettingsProxy$$ExternalSyntheticLambda0(UserSettingsProxy userSettingsProxy, String str, boolean z, ContentObserver contentObserver, int i) {
        this.f$0 = userSettingsProxy;
        this.f$1 = str;
        this.f$2 = z;
        this.f$3 = contentObserver;
        this.f$4 = i;
    }
}
