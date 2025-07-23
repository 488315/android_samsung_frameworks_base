package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import java.util.ArrayList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecControlsProviderSelectorActivity$onCreate$10 extends FunctionReferenceImpl implements Function1 {
    public SecControlsProviderSelectorActivity$onCreate$10(Object obj) {
        super(1, obj, ControlsController.class, "countFavoritesForComponent", "countFavoritesForComponent(Landroid/content/ComponentName;)I", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((ControlsControllerImpl) ((ControlsController) this.receiver)).getClass();
        Favorites.INSTANCE.getClass();
        return Integer.valueOf(((ArrayList) Favorites.getControlsForComponent((ComponentName) obj)).size());
    }
}
