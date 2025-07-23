package com.android.systemui.dextouchpad.data;

import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class GuideItems {
    public final ArrayList mItemList = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ItemInfo {
        public final int mContent;
        public final boolean mIsAnimation;
        public final int mTitle;
        public final int mView;

        public ItemInfo(int i, int i2, int i3) {
            this(i, i2, i3, false);
        }

        public ItemInfo(int i, int i2, int i3, boolean z) {
            this.mTitle = i;
            this.mView = i2;
            this.mIsAnimation = z;
            this.mContent = i3;
        }
    }
}
