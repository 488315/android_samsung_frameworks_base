package com.android.systemui.user.ui.viewmodel;

import com.android.systemui.animation.Expandable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarUserChipViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ StatusBarUserChipViewModel f$0;

    public /* synthetic */ StatusBarUserChipViewModel$$ExternalSyntheticLambda0(StatusBarUserChipViewModel statusBarUserChipViewModel) {
        this.f$0 = statusBarUserChipViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        this.f$0.interactor.showUserSwitcher((Expandable) obj);
        return Unit.INSTANCE;
    }
}
