package androidx.slice.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import androidx.slice.view.R$styleable;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class SliceStyle {
    public final Context mContext;
    public final int mDefaultRowStyleRes;
    public final boolean mExpandToAvailableHeight;
    public final int mGridAllImagesHeight;
    public final int mGridBigPicMaxHeight;
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

    public SliceStyle(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        this.mTintColor = -1;
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.SliceView, i, i2);
        try {
            int color = typedArrayObtainStyledAttributes.getColor(20, -1);
            this.mTintColor = color != -1 ? color : -1;
            this.mTitleColor = typedArrayObtainStyledAttributes.getColor(21, 0);
            this.mSubtitleColor = typedArrayObtainStyledAttributes.getColor(17, 0);
            this.mHeaderTitleSize = (int) typedArrayObtainStyledAttributes.getDimension(8, 0.0f);
            this.mHeaderSubtitleSize = (int) typedArrayObtainStyledAttributes.getDimension(6, 0.0f);
            this.mVerticalHeaderTextPadding = (int) typedArrayObtainStyledAttributes.getDimension(7, 0.0f);
            this.mTitleSize = (int) typedArrayObtainStyledAttributes.getDimension(22, 0.0f);
            this.mSubtitleSize = (int) typedArrayObtainStyledAttributes.getDimension(18, 0.0f);
            this.mVerticalTextPadding = (int) typedArrayObtainStyledAttributes.getDimension(19, 0.0f);
            this.mGridTitleSize = (int) typedArrayObtainStyledAttributes.getDimension(4, 0.0f);
            this.mGridSubtitleSize = (int) typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
            this.mVerticalGridTextPadding = (int) typedArrayObtainStyledAttributes.getDimension(3, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_grid_text_inner_padding));
            this.mGridTopPadding = (int) typedArrayObtainStyledAttributes.getDimension(5, 0.0f);
            this.mGridBottomPadding = (int) typedArrayObtainStyledAttributes.getDimension(1, 0.0f);
            this.mDefaultRowStyleRes = typedArrayObtainStyledAttributes.getResourceId(16, 0);
            this.mRowMinHeight = (int) typedArrayObtainStyledAttributes.getDimension(13, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_min_height));
            this.mRowMaxHeight = (int) typedArrayObtainStyledAttributes.getDimension(12, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_max_height));
            this.mRowRangeHeight = (int) typedArrayObtainStyledAttributes.getDimension(14, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_range_height));
            this.mRowSingleTextWithRangeHeight = (int) typedArrayObtainStyledAttributes.getDimension(15, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_range_single_text_height));
            this.mRowInlineRangeHeight = (int) typedArrayObtainStyledAttributes.getDimension(11, context.getResources().getDimensionPixelSize(R.dimen.abc_slice_row_range_inline_height));
            this.mExpandToAvailableHeight = typedArrayObtainStyledAttributes.getBoolean(0, false);
            this.mHideHeaderRow = typedArrayObtainStyledAttributes.getBoolean(9, false);
            this.mContext = context;
            this.mImageCornerRadius = typedArrayObtainStyledAttributes.getDimension(10, 0.0f);
            typedArrayObtainStyledAttributes.recycle();
            Resources resources = context.getResources();
            this.mRowTextWithRangeHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_range_multi_text_height);
            this.mRowSelectionHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_selection_height);
            this.mRowTextWithSelectionHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_selection_multi_text_height);
            this.mRowSingleTextWithSelectionHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_selection_single_text_height);
            resources.getDimensionPixelSize(R.dimen.abc_slice_big_pic_min_height);
            this.mGridBigPicMaxHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_big_pic_max_height);
            this.mGridAllImagesHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_image_only_height);
            this.mGridImageTextHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_image_text_height);
            this.mGridRawImageTextHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_raw_image_text_offset);
            this.mGridMinHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_min_height);
            this.mGridMaxHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_grid_max_height);
            this.mListMinScrollHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_row_min_height);
            this.mListLargeHeight = resources.getDimensionPixelSize(R.dimen.abc_slice_large_height);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
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
        boolean zShouldSkipFirstListItem = shouldSkipFirstListItem(listContent.mRowItems);
        int size = listContent.mRowItems.size();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= size) {
                i2 = 0;
                break;
            }
            SliceContent sliceContent = (SliceContent) listContent.mRowItems.get(i3);
            if (i3 != 0 || !zShouldSkipFirstListItem) {
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
        int i5 = zShouldSkipFirstListItem ? 1 : 2;
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
            arrayList.add((SliceContent) listContent.mRowItems.get(0));
        }
        return new DisplayedListItems(arrayList, i2);
    }

    public final int getListItemsHeight(List list, SliceViewPolicy sliceViewPolicy) {
        if (list == null) {
            return 0;
        }
        int height = 0;
        for (int i = 0; i < list.size(); i++) {
            SliceContent sliceContent = (SliceContent) list.get(i);
            if (i != 0 || !shouldSkipFirstListItem(list)) {
                height = sliceContent.getHeight(this, sliceViewPolicy) + height;
            }
        }
        return height;
    }

    public final RowStyle getRowStyle() {
        int i = this.mDefaultRowStyleRes;
        if (i == 0) {
            return new RowStyle(this.mContext, this);
        }
        RowStyle rowStyle = (RowStyle) this.mResourceToRowStyle.get(i);
        if (rowStyle != null) {
            return rowStyle;
        }
        RowStyle rowStyle2 = new RowStyle(this.mContext, i, this);
        this.mResourceToRowStyle.put(i, rowStyle2);
        return rowStyle2;
    }

    public final boolean shouldSkipFirstListItem(List list) {
        return this.mHideHeaderRow && list.size() > 1 && (list.get(0) instanceof RowContent) && ((RowContent) list.get(0)).mIsHeader;
    }
}
