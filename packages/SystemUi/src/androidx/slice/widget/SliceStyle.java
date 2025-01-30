package androidx.slice.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.slice.SliceItem;
import androidx.slice.view.R$styleable;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceStyle {
    public final Context mContext;
    public final int mDefaultRowStyleRes;
    public final boolean mExpandToAvailableHeight;
    public final int mGridAllImagesHeight;
    public final int mGridBigPicMaxHeight;
    public final int mGridBigPicMinHeight;
    public final int mGridBottomPadding;
    public final int mGridImageTextHeight;
    public final int mGridMaxHeight;
    public final int mGridMinHeight;
    public final int mGridRawImageTextHeight;
    public final int mGridSubtitleSize;
    public final int mGridTitleSize;
    public final int mGridTopPadding;
    public final int mHeaderSubtitleSize;
    public final int mHeaderTitleSize;
    public final boolean mHideHeaderRow;
    public final float mImageCornerRadius;
    public final int mListLargeHeight;
    public final int mListMinScrollHeight;
    public final SparseArray mResourceToRowStyle = new SparseArray();
    public final int mRowInlineRangeHeight;
    public final int mRowMaxHeight;
    public final int mRowMinHeight;
    public final int mRowRangeHeight;
    public final int mRowSelectionHeight;
    public final int mRowSingleTextWithRangeHeight;
    public final int mRowSingleTextWithSelectionHeight;
    public final int mRowTextWithRangeHeight;
    public final int mRowTextWithSelectionHeight;
    public final int mSubtitleColor;
    public final int mSubtitleSize;
    public final int mTintColor;
    public final int mTitleColor;
    public final int mTitleSize;
    public final int mVerticalGridTextPadding;
    public final int mVerticalHeaderTextPadding;
    public final int mVerticalTextPadding;

    public SliceStyle(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mTintColor = -1;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.SliceView, i, i2);
        try {
            int color = obtainStyledAttributes.getColor(20, -1);
            this.mTintColor = color != -1 ? color : -1;
            this.mTitleColor = obtainStyledAttributes.getColor(21, 0);
            this.mSubtitleColor = obtainStyledAttributes.getColor(17, 0);
            this.mHeaderTitleSize = (int) obtainStyledAttributes.getDimension(8, 0.0f);
            this.mHeaderSubtitleSize = (int) obtainStyledAttributes.getDimension(6, 0.0f);
            this.mVerticalHeaderTextPadding = (int) obtainStyledAttributes.getDimension(7, 0.0f);
            this.mTitleSize = (int) obtainStyledAttributes.getDimension(22, 0.0f);
            this.mSubtitleSize = (int) obtainStyledAttributes.getDimension(18, 0.0f);
            this.mVerticalTextPadding = (int) obtainStyledAttributes.getDimension(19, 0.0f);
            this.mGridTitleSize = (int) obtainStyledAttributes.getDimension(4, 0.0f);
            this.mGridSubtitleSize = (int) obtainStyledAttributes.getDimension(2, 0.0f);
            this.mVerticalGridTextPadding = (int) obtainStyledAttributes.getDimension(3, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_grid_text_inner_padding));
            this.mGridTopPadding = (int) obtainStyledAttributes.getDimension(5, 0.0f);
            this.mGridBottomPadding = (int) obtainStyledAttributes.getDimension(1, 0.0f);
            this.mDefaultRowStyleRes = obtainStyledAttributes.getResourceId(16, 0);
            this.mRowMinHeight = (int) obtainStyledAttributes.getDimension(13, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_min_height));
            this.mRowMaxHeight = (int) obtainStyledAttributes.getDimension(12, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_max_height));
            this.mRowRangeHeight = (int) obtainStyledAttributes.getDimension(14, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_range_height));
            this.mRowSingleTextWithRangeHeight = (int) obtainStyledAttributes.getDimension(15, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_range_single_text_height));
            this.mRowInlineRangeHeight = (int) obtainStyledAttributes.getDimension(11, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_range_inline_height));
            this.mExpandToAvailableHeight = obtainStyledAttributes.getBoolean(0, false);
            this.mHideHeaderRow = obtainStyledAttributes.getBoolean(9, false);
            this.mContext = context;
            this.mImageCornerRadius = obtainStyledAttributes.getDimension(10, 0.0f);
            obtainStyledAttributes.recycle();
            Resources resources = context.getResources();
            this.mRowTextWithRangeHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_range_multi_text_height);
            this.mRowSelectionHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_selection_height);
            this.mRowTextWithSelectionHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_selection_multi_text_height);
            this.mRowSingleTextWithSelectionHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_selection_single_text_height);
            this.mGridBigPicMinHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_big_pic_min_height);
            this.mGridBigPicMaxHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_big_pic_max_height);
            this.mGridAllImagesHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_image_only_height);
            this.mGridImageTextHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_image_text_height);
            this.mGridRawImageTextHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_raw_image_text_offset);
            this.mGridMinHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_min_height);
            this.mGridMaxHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_max_height);
            this.mListMinScrollHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_min_height);
            this.mListLargeHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_large_height);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final DisplayedListItems getListItemsForNonScrollingList(ListContent listContent, int i, SliceViewPolicy sliceViewPolicy) {
        int i2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = listContent.mRowItems;
        if (arrayList2 == null || arrayList2.size() == 0) {
            return new DisplayedListItems(arrayList, 0);
        }
        ArrayList arrayList3 = listContent.mRowItems;
        boolean shouldSkipFirstListItem = shouldSkipFirstListItem(arrayList3);
        int size = arrayList3.size();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= size) {
                i2 = 0;
                break;
            }
            SliceContent sliceContent = (SliceContent) arrayList3.get(i3);
            if (i3 != 0 || !shouldSkipFirstListItem) {
                int height = sliceContent.getHeight(this, sliceViewPolicy);
                if (i > 0 && i4 + height > i) {
                    i2 = size - i3;
                    break;
                }
                i4 += height;
                arrayList.add(sliceContent);
            }
            i3++;
        }
        int i5 = shouldSkipFirstListItem ? 1 : 2;
        if (listContent.mSeeMoreContent != null && arrayList.size() >= i5 && i2 > 0) {
            int height2 = listContent.mSeeMoreContent.getHeight(this, sliceViewPolicy) + i4;
            while (height2 > i && arrayList.size() >= i5) {
                int size2 = arrayList.size() - 1;
                height2 -= ((SliceContent) arrayList.get(size2)).getHeight(this, sliceViewPolicy);
                arrayList.remove(size2);
                i2++;
            }
            if (arrayList.size() >= i5) {
                arrayList.add(listContent.mSeeMoreContent);
            }
        }
        if (arrayList.size() == 0) {
            arrayList.add((SliceContent) arrayList3.get(0));
        }
        return new DisplayedListItems(arrayList, i2);
    }

    public final int getListItemsHeight(List list, SliceViewPolicy sliceViewPolicy) {
        if (list == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            SliceContent sliceContent = (SliceContent) list.get(i2);
            if (i2 != 0 || !shouldSkipFirstListItem(list)) {
                i = sliceContent.getHeight(this, sliceViewPolicy) + i;
            }
        }
        return i;
    }

    public final RowStyle getRowStyle(SliceItem sliceItem) {
        Context context = this.mContext;
        int i = this.mDefaultRowStyleRes;
        if (i == 0) {
            return new RowStyle(context, this);
        }
        SparseArray sparseArray = this.mResourceToRowStyle;
        RowStyle rowStyle = (RowStyle) sparseArray.get(i);
        if (rowStyle != null) {
            return rowStyle;
        }
        RowStyle rowStyle2 = new RowStyle(context, i, this);
        sparseArray.put(i, rowStyle2);
        return rowStyle2;
    }

    public final boolean shouldSkipFirstListItem(List list) {
        return this.mHideHeaderRow && list.size() > 1 && (list.get(0) instanceof RowContent) && ((RowContent) list.get(0)).mIsHeader;
    }
}
