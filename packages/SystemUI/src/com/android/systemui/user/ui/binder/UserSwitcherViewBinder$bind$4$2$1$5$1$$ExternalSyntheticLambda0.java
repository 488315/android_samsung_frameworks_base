package com.android.systemui.user.ui.binder;

import android.view.View;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final /* synthetic */ class UserSwitcherViewBinder$bind$4$2$1$5$1$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(Intrinsics.areEqual(((View) obj).getTag(), "user_view"));
    }
}
