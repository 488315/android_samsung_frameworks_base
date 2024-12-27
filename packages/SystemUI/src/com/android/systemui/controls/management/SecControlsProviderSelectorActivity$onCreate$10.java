package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final /* synthetic */ class SecControlsProviderSelectorActivity$onCreate$10 extends FunctionReferenceImpl implements Function1 {
    public SecControlsProviderSelectorActivity$onCreate$10(Object obj) {
        super(1, obj, ControlsController.class, "countFavoritesForComponent", "countFavoritesForComponent(Landroid/content/ComponentName;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((ControlsControllerImpl) ((ControlsController) this.receiver)).getClass();
        Favorites.INSTANCE.getClass();
        return Integer.valueOf(((ArrayList) Favorites.getControlsForComponent((ComponentName) obj)).size());
    }
}
