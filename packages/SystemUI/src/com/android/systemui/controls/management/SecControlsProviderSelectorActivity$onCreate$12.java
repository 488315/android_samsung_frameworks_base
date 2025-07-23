package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecControlsProviderSelectorActivity$onCreate$12 extends FunctionReferenceImpl implements Function2 {
    public SecControlsProviderSelectorActivity$onCreate$12(Object obj) {
        super(2, obj, SecControlsController.class, "setActiveFlag", "setActiveFlag(Landroid/content/ComponentName;Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ((ControlsControllerImpl) ((SecControlsController) this.receiver)).getClass();
        Favorites.INSTANCE.getClass();
        Favorites.setActiveFlag((ComponentName) obj, booleanValue);
        return Unit.INSTANCE;
    }
}
