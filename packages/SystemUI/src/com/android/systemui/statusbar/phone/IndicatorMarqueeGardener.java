package com.android.systemui.statusbar.phone;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IndicatorMarqueeGardener {
    public final Context context;
    public boolean hasSomethingChanged;
    public final MarqueeModel marqueeModel;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public int lastOrientation = -1;
    public final IndicatorMarqueeGardener$wakefulnessLifecycleObserver$1 wakefulnessLifecycleObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.IndicatorMarqueeGardener$wakefulnessLifecycleObserver$1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            IndicatorMarqueeGardener.this.updateMarqueeValues();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MarqueeModel {
        public int direction = 1;
        public int horizontalShift;
        public final int maxShift;
        public int shiftBottom;
        public int shiftLeft;
        public int shiftRight;
        public int shiftTop;
        public int verticalShift;

        public MarqueeModel(int i) {
            this.maxShift = i;
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.phone.IndicatorMarqueeGardener$wakefulnessLifecycleObserver$1] */
    public IndicatorMarqueeGardener(Context context, WakefulnessLifecycle wakefulnessLifecycle) {
        this.context = context;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.marqueeModel = new MarqueeModel(context.getResources().getDimensionPixelSize(R.dimen.indicator_marquee_max_shift));
    }

    public final void updateMarqueeValues() {
        MarqueeModel marqueeModel = this.marqueeModel;
        int i = marqueeModel.horizontalShift;
        int i2 = marqueeModel.direction;
        int i3 = i + i2;
        marqueeModel.horizontalShift = i3;
        int i4 = marqueeModel.maxShift;
        if (i3 > i4 || i3 < (-i4)) {
            marqueeModel.direction = i2 * (-1);
        }
        int i5 = (marqueeModel.verticalShift + 1) % 4;
        marqueeModel.verticalShift = i5;
        int i6 = (i5 - 1) % 2;
        int i7 = i6 * (-1);
        MarqueeModel marqueeModel2 = new MarqueeModel(i4);
        marqueeModel2.shiftLeft = marqueeModel.shiftLeft;
        marqueeModel2.shiftTop = marqueeModel.shiftTop;
        marqueeModel2.shiftRight = marqueeModel.shiftRight;
        marqueeModel2.shiftBottom = marqueeModel.shiftBottom;
        int i8 = marqueeModel.horizontalShift;
        marqueeModel.shiftLeft = i8;
        marqueeModel.shiftTop = i6;
        marqueeModel.shiftRight = i8;
        marqueeModel.shiftBottom = i7;
        if (marqueeModel2.shiftLeft == i8 && marqueeModel2.shiftTop == i6 && marqueeModel2.shiftRight == i8 && marqueeModel2.shiftBottom == i7) {
            return;
        }
        this.hasSomethingChanged = true;
        marqueeModel.getClass();
    }
}
