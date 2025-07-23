package com.android.systemui.user.ui.binder;

import android.view.View;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class UserSwitcherViewBinder$bind$4$2$1$5$1$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return Boolean.valueOf(Intrinsics.areEqual(((View) obj).getTag(), "user_view"));
    }
}
