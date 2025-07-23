package com.android.wm.shell.compatui.letterbox;

import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.compatui.letterbox.LetterboxControllerStrategy;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MixedLetterboxController implements LetterboxController {
    public final /* synthetic */ LetterboxUtilsKt$append$1 $$delegate_0;
    public final LetterboxControllerStrategy controllerStrategy;
    public final MultiSurfaceLetterboxController multipleSurfaceController;
    public final SingleSurfaceLetterboxController singleSurfaceController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LetterboxControllerStrategy.LetterboxMode.values().length];
            try {
                iArr[LetterboxControllerStrategy.LetterboxMode.SINGLE_SURFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LetterboxControllerStrategy.LetterboxMode.MULTIPLE_SURFACES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public MixedLetterboxController(SingleSurfaceLetterboxController singleSurfaceLetterboxController, MultiSurfaceLetterboxController multiSurfaceLetterboxController, LetterboxControllerStrategy letterboxControllerStrategy) {
        this.$$delegate_0 = new LetterboxUtilsKt$append$1(singleSurfaceLetterboxController, multiSurfaceLetterboxController);
        this.singleSurfaceController = singleSurfaceLetterboxController;
        this.multipleSurfaceController = multiSurfaceLetterboxController;
        this.controllerStrategy = letterboxControllerStrategy;
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void createLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        int i = WhenMappings.$EnumSwitchMapping$0[this.controllerStrategy.currentMode.ordinal()];
        if (i == 1) {
            this.multipleSurfaceController.destroyLetterboxSurface(letterboxKey, transaction);
            this.singleSurfaceController.createLetterboxSurface(letterboxKey, transaction, surfaceControl);
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            this.singleSurfaceController.destroyLetterboxSurface(letterboxKey, transaction);
            this.multipleSurfaceController.createLetterboxSurface(letterboxKey, transaction, surfaceControl);
        }
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void destroyLetterboxSurface(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction) {
        this.$$delegate_0.destroyLetterboxSurface(letterboxKey, transaction);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void dump() {
        this.$$delegate_0.dump();
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceBounds(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, Rect rect, Rect rect2) {
        this.$$delegate_0.updateLetterboxSurfaceBounds(letterboxKey, transaction, rect, rect2);
    }

    @Override // com.android.wm.shell.compatui.letterbox.LetterboxController
    public final void updateLetterboxSurfaceVisibility(LetterboxKey letterboxKey, SurfaceControl.Transaction transaction, boolean z) {
        this.$$delegate_0.updateLetterboxSurfaceVisibility(letterboxKey, transaction, z);
    }
}
