package androidx.slice.widget;

import androidx.slice.Slice;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SliceContent {
    public SliceItem mColorItem;
    public SliceItem mContentDescr;
    public SliceItem mLayoutDirItem;
    public final int mRowIndex;
    public SliceItem mSliceItem;

    public SliceContent(Slice slice) {
        if (slice == null) {
            return;
        }
        init(new SliceItem(slice, "slice", (String) null, (List<String>) Arrays.asList(slice.mHints)));
        this.mRowIndex = -1;
    }

    public int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        return 0;
    }

    public final int getLayoutDir() {
        SliceItem sliceItem = this.mLayoutDirItem;
        if (sliceItem == null) {
            return -1;
        }
        int i = sliceItem.getInt();
        if (i == 2 || i == 3 || i == 1 || i == 0) {
            return i;
        }
        return -1;
    }

    public final void init(SliceItem sliceItem) {
        this.mSliceItem = sliceItem;
        if ("slice".equals(sliceItem.mFormat) || "action".equals(sliceItem.mFormat)) {
            this.mColorItem = SliceQuery.findTopLevelItem(sliceItem.getSlice(), "int", "color", null);
            this.mLayoutDirItem = SliceQuery.findTopLevelItem(sliceItem.getSlice(), "int", "layout_direction", null);
        }
        this.mContentDescr = SliceQuery.findSubtype(sliceItem, "text", "content_description");
    }

    public SliceContent(SliceItem sliceItem, int i) {
        if (sliceItem == null) {
            return;
        }
        init(sliceItem);
        this.mRowIndex = i;
    }
}
