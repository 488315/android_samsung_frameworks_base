package com.android.systemui.temporarydisplay;

import android.graphics.Rect;
import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class TemporaryViewDisplayController$inflateAndUpdateView$newViewController$1 extends FunctionReferenceImpl implements Function2 {
    public TemporaryViewDisplayController$inflateAndUpdateView$newViewController$1(Object obj) {
        super(2, obj, TemporaryViewDisplayController.class, "getTouchableRegion", "getTouchableRegion(Landroid/view/View;Landroid/graphics/Rect;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TemporaryViewDisplayController temporaryViewDisplayController = (TemporaryViewDisplayController) this.receiver;
        temporaryViewDisplayController.getTouchableRegion((Rect) obj2, (View) obj);
        return Unit.INSTANCE;
    }
}
