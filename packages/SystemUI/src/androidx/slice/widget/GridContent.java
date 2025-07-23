package androidx.slice.widget;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.ArrayUtils;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceActionImpl;
import androidx.slice.core.SliceQuery;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class GridContent extends SliceContent {
    public boolean mAllImages;
    public IconCompat mFirstImage;
    public Point mFirstImageSize;
    public final ArrayList mGridContent;
    public boolean mIsLastIndex;
    public int mLargestImageMode;
    public int mMaxCellLineCount;
    public final SliceItem mPrimaryAction;
    public final SliceItem mSeeMoreItem;
    public SliceItem mTitleItem;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CellContent {
        public final ArrayList mCellItems;
        public SliceItem mContentDescr;
        public final SliceItem mContentIntent;
        public IconCompat mImage;
        public int mImageCount;
        public int mImageMode;
        public SliceItem mOverlayItem;
        public SliceItem mPicker;
        public int mTextCount;
        public SliceItem mTitleItem;
        public final SliceItem mToggleItem;

        public CellContent(SliceItem sliceItem) {
            List list;
            ArrayList arrayList = new ArrayList();
            this.mCellItems = arrayList;
            this.mImageMode = -1;
            String str = sliceItem.mFormat;
            if (ArrayUtils.contains(sliceItem.mHints, "shortcut") || !("slice".equals(str) || "action".equals(str))) {
                String str2 = sliceItem.mFormat;
                if (!"content_description".equals(sliceItem.mSubType) && !sliceItem.hasAnyHints("keywords", "ttl", "last_updated") && ("text".equals(str2) || "long".equals(str2) || "image".equals(str2))) {
                    arrayList.add(sliceItem);
                }
            } else {
                List asList = Arrays.asList(sliceItem.getSlice().mItems);
                Iterator it = asList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        list = null;
                        break;
                    }
                    SliceItem sliceItem2 = (SliceItem) it.next();
                    if ("action".equals(sliceItem2.mFormat) || "slice".equals(sliceItem2.mFormat)) {
                        if (!"date_picker".equals(sliceItem2.mSubType) && !"time_picker".equals(sliceItem2.mSubType)) {
                            list = Arrays.asList(sliceItem2.getSlice().mItems);
                            if (new SliceActionImpl(sliceItem2).isToggle()) {
                                this.mToggleItem = sliceItem2;
                            } else {
                                this.mContentIntent = (SliceItem) asList.get(0);
                            }
                        }
                    }
                }
                if ("action".equals(str)) {
                    this.mContentIntent = sliceItem;
                }
                this.mTextCount = 0;
                this.mImageCount = 0;
                fillCellItems(asList);
                if (this.mTextCount == 0 && this.mImageCount == 0 && list != null) {
                    fillCellItems(list);
                }
            }
            if (this.mPicker != null || this.mCellItems.size() <= 0) {
                return;
            }
            this.mCellItems.size();
        }

        public final void fillCellItems(List list) {
            for (int i = 0; i < list.size(); i++) {
                SliceItem sliceItem = (SliceItem) list.get(i);
                String str = sliceItem.mFormat;
                if (this.mPicker == null && ("date_picker".equals(sliceItem.mSubType) || "time_picker".equals(sliceItem.mSubType))) {
                    this.mPicker = sliceItem;
                } else if ("content_description".equals(sliceItem.mSubType)) {
                    this.mContentDescr = sliceItem;
                } else if (this.mTextCount < 2 && ("text".equals(str) || "long".equals(str))) {
                    SliceItem sliceItem2 = this.mTitleItem;
                    if (sliceItem2 == null || (!ArrayUtils.contains(sliceItem2.mHints, UniversalCredentialUtil.AGENT_TITLE) && ArrayUtils.contains(sliceItem.mHints, UniversalCredentialUtil.AGENT_TITLE))) {
                        this.mTitleItem = sliceItem;
                    }
                    if (!ArrayUtils.contains(sliceItem.mHints, "overlay")) {
                        this.mTextCount++;
                        this.mCellItems.add(sliceItem);
                    } else if (this.mOverlayItem == null) {
                        this.mOverlayItem = sliceItem;
                    }
                } else if (this.mImageCount < 1 && "image".equals(sliceItem.mFormat)) {
                    this.mImageMode = SliceActionImpl.parseImageMode(sliceItem);
                    this.mImageCount++;
                    this.mImage = (IconCompat) sliceItem.mObj;
                    this.mCellItems.add(sliceItem);
                }
            }
        }
    }

    public GridContent(SliceItem sliceItem, int i) {
        super(sliceItem, i);
        List asList;
        this.mGridContent = new ArrayList();
        this.mLargestImageMode = 5;
        this.mFirstImage = null;
        this.mFirstImageSize = null;
        SliceItem find = SliceQuery.find(sliceItem, (String) null, "see_more");
        this.mSeeMoreItem = find;
        if (find != null && "slice".equals(find.mFormat) && (asList = Arrays.asList(this.mSeeMoreItem.getSlice().mItems)) != null && asList.size() > 0) {
            this.mSeeMoreItem = (SliceItem) asList.get(0);
        }
        this.mPrimaryAction = SliceQuery.find(sliceItem, "slice", new String[]{"shortcut", UniversalCredentialUtil.AGENT_TITLE}, new String[]{SystemUIAnalytics.QPNE_VID_ACTIONS});
        this.mAllImages = true;
        if ("slice".equals(sliceItem.mFormat)) {
            List asList2 = Arrays.asList(sliceItem.getSlice().mItems);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < asList2.size(); i2++) {
                SliceItem sliceItem2 = (SliceItem) asList2.get(i2);
                boolean z = SliceQuery.find(sliceItem2, (String) null, "see_more") != null || sliceItem2.hasAnyHints("shortcut", "see_more", "keywords", "ttl", "last_updated", "overlay");
                if ("content_description".equals(sliceItem2.mSubType)) {
                    this.mContentDescr = sliceItem2;
                } else if (!z) {
                    arrayList.add(sliceItem2);
                }
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                SliceItem sliceItem3 = (SliceItem) arrayList.get(i3);
                if (!"content_description".equals(sliceItem3.mSubType)) {
                    processContent(new CellContent(sliceItem3));
                }
            }
        } else {
            processContent(new CellContent(sliceItem));
        }
        isValid();
    }

    public final Point getFirstImageSize(Context context) {
        IconCompat iconCompat = this.mFirstImage;
        if (iconCompat == null) {
            return new Point(-1, -1);
        }
        if (this.mFirstImageSize == null) {
            Drawable loadDrawable = iconCompat.loadDrawable(context);
            this.mFirstImageSize = new Point(loadDrawable.getIntrinsicWidth(), loadDrawable.getIntrinsicHeight());
        }
        return this.mFirstImageSize;
    }

    @Override // androidx.slice.widget.SliceContent
    public final int getHeight(SliceStyle sliceStyle, SliceViewPolicy sliceViewPolicy) {
        sliceStyle.getClass();
        sliceViewPolicy.getClass();
        int i = 0;
        if (!isValid()) {
            return 0;
        }
        int i2 = this.mLargestImageMode;
        boolean z = this.mAllImages;
        int i3 = sliceStyle.mGridMinHeight;
        if (!z) {
            boolean z2 = this.mMaxCellLineCount > 1;
            boolean z3 = this.mFirstImage != null;
            boolean z4 = i2 == 0 || i2 == 5;
            if (i2 == 4) {
                i3 = ((z2 ? 2 : 1) * sliceStyle.mGridRawImageTextHeight) + getFirstImageSize(sliceStyle.mContext).y;
            } else if (z2) {
                if (z3) {
                    i3 = sliceStyle.mGridMaxHeight;
                }
            } else if (!z4) {
                i3 = sliceStyle.mGridImageTextHeight;
            }
        } else if (this.mGridContent.size() == 1) {
            i3 = sliceStyle.mGridBigPicMaxHeight;
        } else if (i2 != 0) {
            i3 = i2 == 4 ? getFirstImageSize(sliceStyle.mContext).y : sliceStyle.mGridAllImagesHeight;
        }
        boolean z5 = this.mAllImages;
        int i4 = (z5 && this.mRowIndex == 0) ? sliceStyle.mGridTopPadding : 0;
        if (z5 && this.mIsLastIndex) {
            i = sliceStyle.mGridBottomPadding;
        }
        return i3 + i4 + i;
    }

    public final boolean isValid() {
        return this.mSliceItem != null && this.mGridContent.size() > 0;
    }

    public final void processContent(CellContent cellContent) {
        IconCompat iconCompat;
        SliceItem sliceItem;
        if (cellContent.mPicker != null || (cellContent.mCellItems.size() > 0 && cellContent.mCellItems.size() <= 3)) {
            if (this.mTitleItem == null && (sliceItem = cellContent.mTitleItem) != null) {
                this.mTitleItem = sliceItem;
            }
            this.mGridContent.add(cellContent);
            if (cellContent.mCellItems.size() != 1 || !"image".equals(((SliceItem) cellContent.mCellItems.get(0)).mFormat)) {
                this.mAllImages = false;
            }
            this.mMaxCellLineCount = Math.max(this.mMaxCellLineCount, cellContent.mTextCount);
            if (this.mFirstImage == null && (iconCompat = cellContent.mImage) != null) {
                this.mFirstImage = iconCompat;
            }
            int i = this.mLargestImageMode;
            this.mLargestImageMode = i == 5 ? cellContent.mImageMode : Math.max(i, cellContent.mImageMode);
        }
    }
}
