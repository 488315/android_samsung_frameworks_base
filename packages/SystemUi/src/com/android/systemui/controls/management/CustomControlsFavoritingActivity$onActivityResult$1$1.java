package com.android.systemui.controls.management;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsFavoritingActivity$onActivityResult$1$1 extends FunctionReferenceImpl implements Function0 {
    public CustomControlsFavoritingActivity$onActivityResult$1$1(Object obj) {
        super(0, obj, CustomControlsFavoritingActivity.class, "updateFavorites", "updateFavorites()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        CustomControlsFavoritingActivity customControlsFavoritingActivity = (CustomControlsFavoritingActivity) this.receiver;
        int i = CustomControlsFavoritingActivity.$r8$clinit;
        customControlsFavoritingActivity.updateFavorites();
        return Unit.INSTANCE;
    }
}
