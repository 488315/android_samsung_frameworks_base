package com.android.systemui.accessibility;

import android.content.Context;
import android.view.Display;
import android.view.SurfaceControlViewHost;
import android.view.accessibility.A11yRune;
import android.window.InputTransferToken;
import com.android.systemui.accessibility.MagnificationImpl;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayIdIndexSupplier f$0;
    public final /* synthetic */ Display f$1;

    public /* synthetic */ MagnificationImpl$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0(DisplayIdIndexSupplier displayIdIndexSupplier, Display display, int i) {
        this.$r8$classId = i;
        this.f$0 = displayIdIndexSupplier;
        this.f$1 = display;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                MagnificationImpl.WindowMagnificationControllerSupplier windowMagnificationControllerSupplier = (MagnificationImpl.WindowMagnificationControllerSupplier) this.f$0;
                Display display = this.f$1;
                windowMagnificationControllerSupplier.getClass();
                boolean z = A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP;
                Context contextCreateDisplayContext = z ? windowMagnificationControllerSupplier.mContext.createDisplayContext(display) : windowMagnificationControllerSupplier.mContext;
                if (!z) {
                    display = windowMagnificationControllerSupplier.mContext.getDisplay();
                }
                return new SurfaceControlViewHost(contextCreateDisplayContext, display, new InputTransferToken(), "Magnification");
            default:
                MagnificationImpl.FullscreenMagnificationControllerSupplier fullscreenMagnificationControllerSupplier = (MagnificationImpl.FullscreenMagnificationControllerSupplier) this.f$0;
                Display display2 = this.f$1;
                fullscreenMagnificationControllerSupplier.getClass();
                boolean z2 = A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP;
                Context contextCreateDisplayContext2 = z2 ? fullscreenMagnificationControllerSupplier.mContext.createDisplayContext(display2) : fullscreenMagnificationControllerSupplier.mContext;
                if (!z2) {
                    display2 = fullscreenMagnificationControllerSupplier.mContext.getDisplay();
                }
                return new SurfaceControlViewHost(contextCreateDisplayContext2, display2, new InputTransferToken(), "Magnification");
        }
    }
}
