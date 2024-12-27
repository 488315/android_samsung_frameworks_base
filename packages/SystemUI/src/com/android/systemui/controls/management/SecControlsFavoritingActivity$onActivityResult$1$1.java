package com.android.systemui.controls.management;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
