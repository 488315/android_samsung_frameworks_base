package com.android.systemui.statusbar.layout;

import com.android.systemui.R;
import com.android.systemui.StatusBarInsetsCommand;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarContentInsetsProviderImpl$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarContentInsetsProviderImpl f$0;

    public /* synthetic */ StatusBarContentInsetsProviderImpl$$ExternalSyntheticLambda0(StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarContentInsetsProviderImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(this.f$0.context.getResources().getBoolean(R.bool.config_enablePrivacyDot));
            default:
                return new StatusBarInsetsCommand(new StatusBarContentInsetsProviderImpl$start$1$1(this.f$0));
        }
    }
}
