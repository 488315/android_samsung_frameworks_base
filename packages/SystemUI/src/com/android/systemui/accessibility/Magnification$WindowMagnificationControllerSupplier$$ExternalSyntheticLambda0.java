package com.android.systemui.accessibility;

import android.content.Context;
import android.view.Display;
import android.view.SurfaceControlViewHost;
import android.view.accessibility.A11yRune;
import android.window.InputTransferToken;
import com.android.systemui.Flags;
import com.android.systemui.accessibility.Magnification;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayIdIndexSupplier f$0;
    public final /* synthetic */ Display f$1;

    public /* synthetic */ Magnification$WindowMagnificationControllerSupplier$$ExternalSyntheticLambda0(DisplayIdIndexSupplier displayIdIndexSupplier, Display display, int i) {
        this.$r8$classId = i;
        this.f$0 = displayIdIndexSupplier;
        this.f$1 = display;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Magnification.WindowMagnificationControllerSupplier windowMagnificationControllerSupplier = (Magnification.WindowMagnificationControllerSupplier) this.f$0;
                Display display = this.f$1;
                windowMagnificationControllerSupplier.getClass();
                Flags.createWindowlessWindowMagnifier();
                boolean z = A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP;
                Context createDisplayContext = z ? windowMagnificationControllerSupplier.mContext.createDisplayContext(display) : windowMagnificationControllerSupplier.mContext;
                if (!z) {
                    display = windowMagnificationControllerSupplier.mContext.getDisplay();
                }
                return new SurfaceControlViewHost(createDisplayContext, display, new InputTransferToken(), "Magnification");
            default:
                Magnification.FullscreenMagnificationControllerSupplier fullscreenMagnificationControllerSupplier = (Magnification.FullscreenMagnificationControllerSupplier) this.f$0;
                Display display2 = this.f$1;
                fullscreenMagnificationControllerSupplier.getClass();
                boolean z2 = A11yRune.A11Y_COMMON_BOOL_SUPPORT_LARGE_COVER_SCREEN_FLIP;
                Context createDisplayContext2 = z2 ? fullscreenMagnificationControllerSupplier.mContext.createDisplayContext(display2) : fullscreenMagnificationControllerSupplier.mContext;
                if (!z2) {
                    display2 = fullscreenMagnificationControllerSupplier.mContext.getDisplay();
                }
                return new SurfaceControlViewHost(createDisplayContext2, display2, new InputTransferToken(), "Magnification");
        }
    }
}
