package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.Favorites;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsProviderSelectorActivity$onCreate$12 extends FunctionReferenceImpl implements Function2 {
    public CustomControlsProviderSelectorActivity$onCreate$12(Object obj) {
        super(2, obj, CustomControlsController.class, "setActiveFlag", "setActiveFlag(Landroid/content/ComponentName;Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ((ControlsControllerImpl) ((CustomControlsController) this.receiver)).getClass();
        Favorites.INSTANCE.getClass();
        Favorites.setActiveFlag((ComponentName) obj, booleanValue);
        return Unit.INSTANCE;
    }
}
