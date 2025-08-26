package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import java.util.Iterator;

/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenDecorations f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda6(ScreenDecorations screenDecorations, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = screenDecorations;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScreenDecorations screenDecorations = this.f$0;
                boolean z = this.f$1;
                boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                screenDecorations.setupDecorations();
                if (z) {
                    screenDecorations.showCameraProtection(new Path(), new Rect());
                    return;
                } else {
                    screenDecorations.hideCameraProtection();
                    return;
                }
            default:
                ScreenDecorations screenDecorations2 = this.f$0;
                boolean z3 = this.f$1;
                IndicatorGardenPresenter indicatorGardenPresenter = screenDecorations2.mIndicatorGardenPresenter;
                IndicatorCutoutUtil indicatorCutoutUtil = indicatorGardenPresenter.indicatorCutoutUtil;
                if (indicatorCutoutUtil.isMainDisplay()) {
                    indicatorCutoutUtil.isFrontCameraUsing = z3;
                    Iterator it = indicatorGardenPresenter.statusIconContainerCallbacks.iterator();
                    if (it.hasNext()) {
                        throw FragmentManager$$ExternalSyntheticOutline0.m(it);
                    }
                    return;
                }
                return;
        }
    }
}
