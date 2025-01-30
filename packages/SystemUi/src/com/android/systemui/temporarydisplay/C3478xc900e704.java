package com.android.systemui.temporarydisplay;

import android.graphics.Rect;
import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.temporarydisplay.TemporaryViewDisplayController$inflateAndUpdateView$newViewController$1 */
/* loaded from: classes2.dex */
final /* synthetic */ class C3478xc900e704 extends FunctionReferenceImpl implements Function2 {
    public C3478xc900e704(Object obj) {
        super(2, obj, TemporaryViewDisplayController.class, "getTouchableRegion", "getTouchableRegion(Landroid/view/View;Landroid/graphics/Rect;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TemporaryViewDisplayController temporaryViewDisplayController = (TemporaryViewDisplayController) this.receiver;
        temporaryViewDisplayController.getTouchableRegion((Rect) obj2, (View) obj);
        return Unit.INSTANCE;
    }
}
