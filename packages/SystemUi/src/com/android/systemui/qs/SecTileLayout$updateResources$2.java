package com.android.systemui.qs;

import android.content.Context;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
final /* synthetic */ class SecTileLayout$updateResources$2 extends FunctionReferenceImpl implements Function2 {
    public static final SecTileLayout$updateResources$2 INSTANCE = new SecTileLayout$updateResources$2();

    public SecTileLayout$updateResources$2() {
        super(2, SecQSPanelResourcePicker.class, "getTileExpandedHeight", "getTileExpandedHeight(Landroid/content/Context;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((SecQSPanelResourcePicker) obj).getClass();
        return Integer.valueOf(SecQSPanelResourcePicker.getTileExpandedHeight((Context) obj2));
    }
}
