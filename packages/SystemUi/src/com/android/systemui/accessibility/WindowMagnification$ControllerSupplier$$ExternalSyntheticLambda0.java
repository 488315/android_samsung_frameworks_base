package com.android.systemui.accessibility;

import android.view.WindowManagerGlobal;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class WindowMagnification$ControllerSupplier$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return WindowManagerGlobal.getWindowSession();
    }
}
