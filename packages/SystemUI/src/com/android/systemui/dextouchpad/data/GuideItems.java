package com.android.systemui.dextouchpad.data;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public abstract class GuideItems {
    public final ArrayList mItemList = new ArrayList();

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
