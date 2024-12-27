package com.android.systemui.qs.bar;

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
