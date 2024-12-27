package com.android.systemui.qs.bar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ColoredBGHelper$updateBGColor$1 implements Runnable {
    public final /* synthetic */ ColoredBGHelper this$0;

    public ColoredBGHelper$updateBGColor$1(ColoredBGHelper coloredBGHelper) {
        this.this$0 = coloredBGHelper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ColoredBGHelper coloredBGHelper = this.this$0;
        int i = ColoredBGHelper.$r8$clinit;
        coloredBGHelper.updateBGColor(false);
    }
}
