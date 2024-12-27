package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenDecorations f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda4(ScreenDecorations screenDecorations, boolean z, int i) {
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
                boolean z2 = this.f$1;
                IndicatorGardenPresenter indicatorGardenPresenter = screenDecorations2.mIndicatorGardenPresenter;
                IndicatorCutoutUtil indicatorCutoutUtil = indicatorGardenPresenter.indicatorCutoutUtil;
                if (indicatorCutoutUtil.isMainDisplay()) {
                    indicatorCutoutUtil.isFrontCameraUsing = z2;
                    Iterator it = indicatorGardenPresenter.statusIconContainerCallbacks.iterator();
                    if (it.hasNext()) {
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                        throw null;
                    }
                    return;
                }
                return;
        }
    }
}
