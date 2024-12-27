package com.android.systemui.accessibility;

import android.view.WindowManagerGlobal;
import java.util.function.Supplier;

public final /* synthetic */ class Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda1 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return WindowManagerGlobal.getWindowSession();
    }
}
