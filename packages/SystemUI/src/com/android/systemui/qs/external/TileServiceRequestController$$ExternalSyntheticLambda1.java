package com.android.systemui.qs.external;

import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class TileServiceRequestController$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ SystemUIDialog f$1;
    public final /* synthetic */ TileServiceRequestController f$2;

    public /* synthetic */ TileServiceRequestController$$ExternalSyntheticLambda1(String str, SystemUIDialog systemUIDialog, TileServiceRequestController tileServiceRequestController) {
        this.f$0 = str;
        this.f$1 = systemUIDialog;
        this.f$2 = tileServiceRequestController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = TileServiceRequestController.$r8$clinit;
        if (Intrinsics.areEqual(this.f$0, (String) obj)) {
            this.f$1.cancel();
        }
        this.f$2.dialogCanceller = null;
        return Unit.INSTANCE;
    }
}
