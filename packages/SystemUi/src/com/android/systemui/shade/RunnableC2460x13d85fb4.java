package com.android.systemui.shade;

import com.android.systemui.QpRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.shade.SecTabletHorizontalPanelPositionHelper$setNextUpdateHorizontalPosition$1 */
/* loaded from: classes2.dex */
public final class RunnableC2460x13d85fb4 implements Runnable {

    /* renamed from: $x */
    public final /* synthetic */ float f343$x;
    public final /* synthetic */ SecTabletHorizontalPanelPositionHelper this$0;

    public RunnableC2460x13d85fb4(SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper, float f) {
        this.this$0 = secTabletHorizontalPanelPositionHelper;
        this.f343$x = f;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (QpRune.QUICK_TABLET_HORIZONTAL_PANEL_POSITION) {
            SecTabletHorizontalPanelPositionHelper secTabletHorizontalPanelPositionHelper = this.this$0;
            float f = this.f343$x;
            int i = SecTabletHorizontalPanelPositionHelper.$r8$clinit;
            secTabletHorizontalPanelPositionHelper.updateTabletHorizontalPanelPosition(f);
        }
    }
}
