package com.android.systemui;

import android.graphics.Path;
import android.graphics.Rect;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ScreenDecorations f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda3(ScreenDecorations screenDecorations, boolean z, int i) {
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
                if (!z) {
                    screenDecorations.hideCameraProtection();
                    break;
                } else {
                    screenDecorations.showCameraProtection(new Path(), new Rect());
                    break;
                }
            default:
                ScreenDecorations screenDecorations2 = this.f$0;
                boolean z2 = this.f$1;
                IndicatorGardenPresenter indicatorGardenPresenter = screenDecorations2.mIndicatorGardenPresenter;
                IndicatorCutoutUtil indicatorCutoutUtil = indicatorGardenPresenter.indicatorCutoutUtil;
                if (indicatorCutoutUtil.isMainDisplay()) {
                    indicatorCutoutUtil.isFrontCameraUsing = z2;
                    Iterator it = indicatorGardenPresenter.statusIconContainerCallbacks.iterator();
                    while (it.hasNext()) {
                        ((IndicatorGardenPresenter.StatusIconContainerCallback) it.next()).updateStatusIconContainer();
                    }
                    break;
                }
                break;
        }
    }
}
