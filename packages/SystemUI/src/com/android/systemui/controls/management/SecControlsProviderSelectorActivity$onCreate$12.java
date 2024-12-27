package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
