package androidx.slice.widget;

import android.text.TextUtils;
import android.util.Log;
import androidx.slice.ArrayUtils;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RowContent extends SliceContent {
    public final ArrayList mEndItems;
    public boolean mIsHeader;
    public int mLineCount;
    public SliceItem mPrimaryAction;
    public SliceItem mRange;
    public SliceItem mSelection;
    public boolean mShowActionDivider;
    public boolean mShowBottomDivider;
    public boolean mShowTitleItems;
    public SliceItem mStartItem;
    public SliceItem mSubtitleItem;
    public SliceItem mSummaryItem;
    public SliceItem mTitleItem;
    public final ArrayList mToggleItems;

    public RowContent(SliceItem sliceItem, int i) {
        super(sliceItem, i);
        boolean z;
        this.mEndItems = new ArrayList();
        this.mToggleItems = new ArrayList();
        this.mLineCount = 0;
        boolean z2 = i == 0;
        if (sliceItem.hasHint("end_of_section")) {
            this.mShowBottomDivider = true;
        }
        this.mIsHeader = z2;
        if (!isValidRow(sliceItem)) {
            Log.w("RowContent", "Provided SliceItem is invalid for RowContent");
            return;
        }
        ArrayList arrayList = (ArrayList) SliceQuery.findAll(sliceItem, null, new String[]{UniversalCredentialUtil.AGENT_TITLE}, new String[]{null});
        if (arrayList.size() > 0) {
            String str = ((SliceItem) arrayList.get(0)).mFormat;
            if (("action".equals(str) && SliceQuery.find((SliceItem) arrayList.get(0), "image", (String[]) null, (String[]) null) != null) || "slice".equals(str) || "long".equals(str) || "image".equals(str)) {
                this.mStartItem = (SliceItem) arrayList.get(0);
            }
        }
        String[] strArr = {"shortcut", UniversalCredentialUtil.AGENT_TITLE};
        ArrayList arrayList2 = (ArrayList) SliceQuery.findAll(sliceItem, "slice", strArr, null);
        arrayList2.addAll(SliceQuery.findAll(sliceItem, "action", strArr, null));
        if (arrayList2.isEmpty() && "action".equals(sliceItem.mFormat) && sliceItem.getSlice().getItems().size() == 1) {
            this.mPrimaryAction = sliceItem;
        } else if (this.mStartItem != null && arrayList2.size() > 1 && arrayList2.get(0) == this.mStartItem) {
            this.mPrimaryAction = (SliceItem) arrayList2.get(1);
        } else if (arrayList2.size() > 0) {
            this.mPrimaryAction = (SliceItem) arrayList2.get(0);
        }
        ArrayList filterInvalidItems = filterInvalidItems(sliceItem);
        if (filterInvalidItems.size() == 1 && (("action".equals(((SliceItem) filterInvalidItems.get(0)).mFormat) || "slice".equals(((SliceItem) filterInvalidItems.get(0)).mFormat)) && !((SliceItem) filterInvalidItems.get(0)).hasAnyHints("shortcut", UniversalCredentialUtil.AGENT_TITLE) && isValidRow((SliceItem) filterInvalidItems.get(0)))) {
            sliceItem = (SliceItem) filterInvalidItems.get(0);
            filterInvalidItems = filterInvalidItems(sliceItem);
            z = true;
        } else {
            z = false;
        }
        if ("range".equals(sliceItem.mSubType)) {
            if (SliceQuery.findSubtype(sliceItem, "action", "range") == null || z) {
                this.mRange = sliceItem;
            } else {
                filterInvalidItems.remove(this.mStartItem);
                if (filterInvalidItems.size() != 1) {
                    SliceItem findSubtype = SliceQuery.findSubtype(sliceItem, "action", "range");
                    this.mRange = findSubtype;
                    ArrayList filterInvalidItems2 = filterInvalidItems(findSubtype);
                    filterInvalidItems2.remove(getInputRangeThumb());
                    filterInvalidItems.remove(this.mRange);
                    filterInvalidItems.addAll(filterInvalidItems2);
                } else if (isValidRow((SliceItem) filterInvalidItems.get(0))) {
                    sliceItem = (SliceItem) filterInvalidItems.get(0);
                    filterInvalidItems = filterInvalidItems(sliceItem);
                    this.mRange = sliceItem;
                    filterInvalidItems.remove(getInputRangeThumb());
                }
            }
        }
        if ("selection".equals(sliceItem.mSubType)) {
            this.mSelection = sliceItem;
        }
        if (filterInvalidItems.size() > 0) {
            SliceItem sliceItem2 = this.mStartItem;
            if (sliceItem2 != null) {
                filterInvalidItems.remove(sliceItem2);
            }
            SliceItem sliceItem3 = this.mPrimaryAction;
            if (sliceItem3 != null) {
                filterInvalidItems.remove(sliceItem3);
            }
            ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < filterInvalidItems.size(); i2++) {
                SliceItem sliceItem4 = (SliceItem) filterInvalidItems.get(i2);
                if ("text".equals(sliceItem4.mFormat)) {
                    SliceItem sliceItem5 = this.mTitleItem;
                    if ((sliceItem5 == null || !sliceItem5.hasHint(UniversalCredentialUtil.AGENT_TITLE)) && sliceItem4.hasHint(UniversalCredentialUtil.AGENT_TITLE) && !sliceItem4.hasHint(UniversalCredentialUtil.AGENT_SUMMARY)) {
                        this.mTitleItem = sliceItem4;
                    } else if (this.mSubtitleItem == null && !sliceItem4.hasHint(UniversalCredentialUtil.AGENT_SUMMARY)) {
                        this.mSubtitleItem = sliceItem4;
                    } else if (this.mSummaryItem == null && sliceItem4.hasHint(UniversalCredentialUtil.AGENT_SUMMARY)) {
                        this.mSummaryItem = sliceItem4;
                    }
                } else {
                    arrayList3.add(sliceItem4);
                }
            }
            SliceItem sliceItem6 = this.mTitleItem;
            if (sliceItem6 != null && (sliceItem6.hasHint("partial") || !TextUtils.isEmpty((CharSequence) sliceItem6.mObj))) {
                this.mLineCount++;
            }
            SliceItem sliceItem7 = this.mSubtitleItem;
            if (sliceItem7 != null && (sliceItem7.hasHint("partial") || !TextUtils.isEmpty((CharSequence) sliceItem7.mObj))) {
                this.mLineCount++;
            }
            SliceItem sliceItem8 = this.mStartItem;
            boolean z3 = sliceItem8 != null && "long".equals(sliceItem8.mFormat);
            for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                SliceItem sliceItem9 = (SliceItem) arrayList3.get(i3);
                boolean z4 = SliceQuery.find(sliceItem9, "action", (String[]) null, (String[]) null) != null;
                boolean equals = "long".equals(sliceItem9.mFormat);
                ArrayList arrayList4 = this.mEndItems;
                if (!equals) {
                    if (z4) {
                        SliceActionImpl sliceActionImpl = new SliceActionImpl(sliceItem9);
                        if (sliceActionImpl.isToggle()) {
                            this.mToggleItems.add(sliceActionImpl);
                        }
                    }
                    arrayList4.add(sliceItem9);
                } else if (!z3) {
                    arrayList4.add(sliceItem9);
                    z3 = true;
                }
            }
        }
        isValid();
    }

    public static ArrayList filterInvalidItems(SliceItem sliceItem) {
        ArrayList arrayList = new ArrayList();
        for (SliceItem sliceItem2 : sliceItem.getSlice().getItems()) {
            if (isValidRowContent(sliceItem, sliceItem2)) {
                arrayList.add(sliceItem2);
            }
        }
        return arrayList;
    }

    public static boolean isValidRow(SliceItem sliceItem) {
        if (sliceItem == null) {
            return false;
        }
        if ("slice".equals(sliceItem.mFormat) || "action".equals(sliceItem.mFormat)) {
            List items = sliceItem.getSlice().getItems();
            if (sliceItem.hasHint("see_more") && items.isEmpty()) {
                return true;
            }
            for (int i = 0; i < items.size(); i++) {
                if (isValidRowContent(sliceItem, (SliceItem) items.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidRowContent(SliceItem sliceItem, SliceItem sliceItem2) {
        if (sliceItem2.hasAnyHints("keywords", "ttl", "last_updated", "horizontal") || "content_description".equals(sliceItem2.mSubType) || "selection_option_key".equals(sliceItem2.mSubType) || "selection_option_value".equals(sliceItem2.mSubType)) {
            return false;
        }
        String str = sliceItem2.mFormat;
        return "image".equals(str) || "text".equals(str) || "long".equals(str) || "action".equals(str) || "input".equals(str) || "slice".equals(str) || ("int".equals(str) && "range".equals(sliceItem.mSubType));
    }

    @Override // androidx.slice.widget.SliceContent
    public final int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        int i;
        int i2;
        sliceStyle.getClass();
        int i3 = sliceViewPolicy.mMaxSmallHeight;
        if (i3 <= 0) {
            i3 = sliceStyle.mRowMaxHeight;
        }
        SliceItem sliceItem = this.mRange;
        if (sliceItem == null && this.mSelection == null && sliceViewPolicy.mMode != 2) {
            return i3;
        }
        if (sliceItem != null) {
            if (((!this.mIsHeader || this.mShowTitleItems) ? this.mStartItem : null) != null) {
                return sliceStyle.mRowInlineRangeHeight;
            }
            int i4 = this.mLineCount;
            i = i4 == 0 ? 0 : i4 > 1 ? sliceStyle.mRowTextWithRangeHeight : sliceStyle.mRowSingleTextWithRangeHeight;
            i2 = sliceStyle.mRowRangeHeight;
        } else {
            if (this.mSelection == null) {
                return (this.mLineCount > 1 || this.mIsHeader) ? i3 : sliceStyle.mRowMinHeight;
            }
            i = this.mLineCount > 1 ? sliceStyle.mRowTextWithSelectionHeight : sliceStyle.mRowSingleTextWithSelectionHeight;
            i2 = sliceStyle.mRowSelectionHeight;
        }
        return i + i2;
    }

    public final SliceItem getInputRangeThumb() {
        SliceItem sliceItem = this.mRange;
        if (sliceItem == null) {
            return null;
        }
        List items = sliceItem.getSlice().getItems();
        for (int i = 0; i < items.size(); i++) {
            if ("image".equals(((SliceItem) items.get(i)).mFormat)) {
                return (SliceItem) items.get(i);
            }
        }
        return null;
    }

    public final boolean isDefaultSeeMore() {
        return "action".equals(this.mSliceItem.mFormat) && ArrayUtils.contains(this.mSliceItem.getSlice().mHints, "see_more") && this.mSliceItem.getSlice().getItems().isEmpty();
    }

    public final boolean isValid() {
        return (this.mSliceItem != null) && !(this.mStartItem == null && this.mPrimaryAction == null && this.mTitleItem == null && this.mSubtitleItem == null && this.mEndItems.size() <= 0 && this.mRange == null && this.mSelection == null && !isDefaultSeeMore());
    }
}
