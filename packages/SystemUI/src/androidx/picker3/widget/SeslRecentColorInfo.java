package androidx.picker3.widget;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class SeslRecentColorInfo {
    public Integer mSelectedColor = null;
    public Integer mCurrentColor = null;
    public Integer mNewColor = null;
    public final ArrayList mRecentColorInfo = new ArrayList();

    public final void initRecentColorInfo(int[] iArr) {
        if (iArr != null) {
            int i = 0;
            if (iArr.length > SeslColorPicker.RECENT_COLOR_SLOT_COUNT) {
                while (i < SeslColorPicker.RECENT_COLOR_SLOT_COUNT) {
                    this.mRecentColorInfo.add(Integer.valueOf(iArr[i]));
                    i++;
                }
            } else {
                int length = iArr.length;
                while (i < length) {
                    this.mRecentColorInfo.add(Integer.valueOf(iArr[i]));
                    i++;
                }
            }
        }
    }
}
