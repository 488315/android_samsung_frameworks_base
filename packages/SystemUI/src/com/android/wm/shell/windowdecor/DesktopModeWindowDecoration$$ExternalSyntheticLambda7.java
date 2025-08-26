package com.android.wm.shell.windowdecor;

import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecoration$$ExternalSyntheticLambda7 implements Supplier {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new SurfaceControl.Builder();
            case 1:
                return new WindowContainerTransaction();
            default:
                return new SurfaceControl();
        }
    }
}
