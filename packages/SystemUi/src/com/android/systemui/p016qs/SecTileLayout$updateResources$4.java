package com.android.systemui.p016qs;

import android.content.Context;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class SecTileLayout$updateResources$4 extends FunctionReferenceImpl implements Function2 {
    public static final SecTileLayout$updateResources$4 INSTANCE = new SecTileLayout$updateResources$4();

    public SecTileLayout$updateResources$4() {
        super(2, SecQSPanelResourcePicker.class, "getQsTileColumn", "getQsTileColumn(Landroid/content/Context;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return Integer.valueOf(((SecQSPanelResourcePicker) obj).getQsTileColumn((Context) obj2));
    }
}
