package androidx.picker.decorator;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewGroupKt$children$1;
import androidx.picker.adapter.viewholder.AppListItemViewHolder;
import androidx.picker.adapter.viewholder.FrameViewHolder;
import androidx.picker.adapter.viewholder.GroupTitleViewHolder;
import androidx.picker.helper.ContextHelperKt;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ListDividerItemDecoration extends RecyclerView.ItemDecoration {
    public final Drawable divider;
    public final int dividerPaddingStart;
    public final int iconFrameWidth;
    public final int leftFrameWidth;

    public ListDividerItemDecoration(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.listDivider});
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        this.divider = drawable;
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.dividerPaddingStart = ContextHelperKt.isRTL(context) ? rect.right : rect.left;
        this.iconFrameWidth = (int) context.getResources().getDimension(com.android.systemui.R.dimen.picker_app_list_icon_frame_width);
        this.leftFrameWidth = (int) context.getResources().getDimension(com.android.systemui.R.dimen.picker_app_list_left_frame_width);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void seslOnDispatchDraw(Canvas canvas, RecyclerView recyclerView) {
        int i;
        Drawable drawable = this.divider;
        if (drawable == null) {
            return;
        }
        int i2 = 0;
        for (Object obj : SequencesKt___SequencesKt.toList(new ViewGroupKt$children$1(recyclerView)).subList(0, Math.max(recyclerView.getChildCount() - 1, 0))) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            View view = (View) obj;
            RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(view);
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(RecyclerView.getChildAdapterPosition(view) + 1);
            if (!(childViewHolder instanceof FrameViewHolder) && !(childViewHolder instanceof GroupTitleViewHolder) && !(findViewHolderForAdapterPosition instanceof GroupTitleViewHolder)) {
                if (childViewHolder instanceof AppListItemViewHolder) {
                    AppListItemViewHolder appListItemViewHolder = (AppListItemViewHolder) childViewHolder;
                    i = ((view.getPaddingStart() + (appListItemViewHolder.composableType.getLeftFrame() != null ? this.leftFrameWidth : 0)) + (appListItemViewHolder.composableType.getIconFrame() != null ? this.iconFrameWidth : 0)) - this.dividerPaddingStart;
                } else {
                    i = 0;
                }
                int i4 = i + 0;
                int left = view.getLeft();
                int right = recyclerView.getRight();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                RecyclerView.LayoutParams layoutParams2 = layoutParams instanceof RecyclerView.LayoutParams ? (RecyclerView.LayoutParams) layoutParams : null;
                int roundToInt = MathKt__MathJVMKt.roundToInt(view.getTranslationY()) + view.getBottom() + (layoutParams2 != null ? ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin : 0);
                int intrinsicHeight = drawable.getIntrinsicHeight() + roundToInt;
                if (ContextHelperKt.isRTL(view.getContext())) {
                    drawable.setBounds(left, roundToInt, right - i4, intrinsicHeight);
                } else {
                    drawable.setBounds(left + i4, roundToInt, right, intrinsicHeight);
                }
                drawable.draw(canvas);
            }
            i2 = i3;
        }
    }
}
