package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsProviderSelectorActivity$onCreate$10 extends FunctionReferenceImpl implements Function1 {
    public CustomControlsProviderSelectorActivity$onCreate$10(Object obj) {
        super(1, obj, ControlsController.class, "countFavoritesForComponent", "countFavoritesForComponent(Landroid/content/ComponentName;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((ControlsControllerImpl) ((ControlsController) this.receiver)).getClass();
        Favorites.INSTANCE.getClass();
        return Integer.valueOf(((ArrayList) Favorites.getControlsForComponent((ComponentName) obj)).size());
    }
}
