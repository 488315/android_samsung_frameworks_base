package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsProviderSelectorActivity$onCreate$11 extends FunctionReferenceImpl implements Function1 {
    public CustomControlsProviderSelectorActivity$onCreate$11(Object obj) {
        super(1, obj, CustomControlsController.class, "getActiveFlag", "getActiveFlag(Landroid/content/ComponentName;)Z", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return Boolean.valueOf(((ControlsControllerImpl) ((CustomControlsController) this.receiver)).getActiveFlag((ComponentName) obj));
    }
}
