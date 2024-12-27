package com.android.systemui.accessibility;

import android.view.WindowManagerGlobal;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda1 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return WindowManagerGlobal.getWindowSession();
    }
}
