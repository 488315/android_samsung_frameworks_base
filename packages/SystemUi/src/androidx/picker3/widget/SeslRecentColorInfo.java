package androidx.picker3.widget;

import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslRecentColorInfo {
    public Integer mSelectedColor = null;
    public Integer mCurrentColor = null;
    public Integer mNewColor = null;
    public final ArrayList mRecentColorInfo = new ArrayList();

    public final void initRecentColorInfo(int[] iArr) {
        if (iArr != null) {
            int length = iArr.length;
            int i = SeslColorPicker.RECENT_COLOR_SLOT_COUNT;
            ArrayList arrayList = this.mRecentColorInfo;
            int i2 = 0;
            if (length > i) {
                while (i2 < SeslColorPicker.RECENT_COLOR_SLOT_COUNT) {
                    arrayList.add(Integer.valueOf(iArr[i2]));
                    i2++;
                }
            } else {
                int length2 = iArr.length;
                while (i2 < length2) {
                    arrayList.add(Integer.valueOf(iArr[i2]));
                    i2++;
                }
            }
        }
    }
}
