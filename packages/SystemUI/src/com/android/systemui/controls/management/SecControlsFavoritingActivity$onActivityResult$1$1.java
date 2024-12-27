package com.android.systemui.controls.management;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class SecControlsFavoritingActivity$onActivityResult$1$1 extends FunctionReferenceImpl implements Function0 {
    public SecControlsFavoritingActivity$onActivityResult$1$1(Object obj) {
        super(0, obj, SecControlsFavoritingActivity.class, "updateFavorites", "updateFavorites()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        SecControlsFavoritingActivity secControlsFavoritingActivity = (SecControlsFavoritingActivity) this.receiver;
        int i = SecControlsFavoritingActivity.$r8$clinit;
        secControlsFavoritingActivity.updateFavorites();
        return Unit.INSTANCE;
    }
}
