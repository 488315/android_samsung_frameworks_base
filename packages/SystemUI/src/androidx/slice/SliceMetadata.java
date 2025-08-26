package androidx.slice;

import android.content.Context;
import android.os.Bundle;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class SliceMetadata {
    public final long mExpiry;
    public final long mLastUpdated;
    public final ListContent mListContent;
    public final Slice mSlice;
    public final List mSliceActions;

    /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private SliceMetadata(Context context, Slice slice) {
        this.mSlice = slice;
        SliceItem sliceItemFind = SliceQuery.find(slice, "long", "ttl");
        if (sliceItemFind != null) {
            this.mExpiry = sliceItemFind.getLong();
        }
        SliceItem sliceItemFind2 = SliceQuery.find(slice, "long", "last_updated");
        if (sliceItemFind2 != null) {
            this.mLastUpdated = sliceItemFind2.getLong();
        }
        SliceItem sliceItemFindSubtype = SliceQuery.findSubtype(slice, "bundle", "host_extras");
        if (sliceItemFindSubtype != null) {
            Object obj = sliceItemFindSubtype.mObj;
            if (obj instanceof Bundle) {
            } else {
                Bundle bundle = Bundle.EMPTY;
            }
        }
        ListContent listContent = new ListContent(slice);
        this.mListContent = listContent;
        RowContent rowContent = listContent.mHeaderContent;
        ListContent.getRowType(rowContent, true, listContent.mSliceActions);
        listContent.getShortcut(context);
        List list = listContent.mSliceActions;
        this.mSliceActions = list;
        if (list == null && rowContent != null && SliceQuery.hasHints(rowContent.mSliceItem, "list_item")) {
            ArrayList arrayList = rowContent.mEndItems;
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                if (SliceQuery.find((SliceItem) arrayList.get(i), "action", (String[]) null, (String[]) null) != null) {
                    arrayList2.add(new SliceActionImpl((SliceItem) arrayList.get(i)));
                }
            }
            if (arrayList2.size() > 0) {
                this.mSliceActions = arrayList2;
            }
        }
    }

    public static SliceMetadata from(Context context, Slice slice) {
        return new SliceMetadata(context, slice);
    }

    public final int getLoadingState() {
        boolean z = SliceQuery.find(this.mSlice, (String) null, "partial") != null;
        if (this.mListContent.isValid()) {
            return z ? 1 : 2;
        }
        return 0;
    }

    public final boolean isExpired() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = this.mExpiry;
        return (j == 0 || j == -1 || jCurrentTimeMillis <= j) ? false : true;
    }
}
