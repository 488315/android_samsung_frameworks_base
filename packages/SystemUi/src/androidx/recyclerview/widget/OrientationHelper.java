package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class OrientationHelper {
    public int mLastTotalSpace;
    public final RecyclerView.LayoutManager mLayoutManager;
    public final Rect mTmpRect;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.OrientationHelper$1 */
    public final class C04511 extends OrientationHelper {
        public C04511(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager, null);
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedEnd(View view) {
            return this.mLayoutManager.getDecoratedRight(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).rightMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedMeasurement(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return RecyclerView.LayoutManager.getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedMeasurementInOther(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return RecyclerView.LayoutManager.getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedStart(View view) {
            return this.mLayoutManager.getDecoratedLeft(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).leftMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEnd() {
            return this.mLayoutManager.mWidth;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEndAfterPadding() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return layoutManager.mWidth - layoutManager.getPaddingRight();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEndPadding() {
            return this.mLayoutManager.getPaddingRight();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getMode() {
            return this.mLayoutManager.mWidthMode;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getModeInOther() {
            return this.mLayoutManager.mHeightMode;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingLeft();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTotalSpace() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return (layoutManager.mWidth - layoutManager.getPaddingLeft()) - layoutManager.getPaddingRight();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTransformedEndWithDecoration(View view) {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            Rect rect = this.mTmpRect;
            layoutManager.getTransformedBoundingBox(view, rect);
            return rect.right;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTransformedStartWithDecoration(View view) {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            Rect rect = this.mTmpRect;
            layoutManager.getTransformedBoundingBox(view, rect);
            return rect.left;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final void offsetChildren(int i) {
            this.mLayoutManager.offsetChildrenHorizontal(i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.recyclerview.widget.OrientationHelper$2 */
    public final class C04522 extends OrientationHelper {
        public C04522(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager, null);
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedEnd(View view) {
            return this.mLayoutManager.getDecoratedBottom(view) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedMeasurement(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return RecyclerView.LayoutManager.getDecoratedMeasuredHeight(view) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedMeasurementInOther(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return RecyclerView.LayoutManager.getDecoratedMeasuredWidth(view) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedStart(View view) {
            return this.mLayoutManager.getDecoratedTop(view) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view.getLayoutParams())).topMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEnd() {
            return this.mLayoutManager.mHeight;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEndAfterPadding() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return layoutManager.mHeight - layoutManager.getPaddingBottom();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEndPadding() {
            return this.mLayoutManager.getPaddingBottom();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getMode() {
            return this.mLayoutManager.mHeightMode;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getModeInOther() {
            return this.mLayoutManager.mWidthMode;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingTop();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTotalSpace() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return (layoutManager.mHeight - layoutManager.getPaddingTop()) - layoutManager.getPaddingBottom();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTransformedEndWithDecoration(View view) {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            Rect rect = this.mTmpRect;
            layoutManager.getTransformedBoundingBox(view, rect);
            return rect.bottom;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTransformedStartWithDecoration(View view) {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            Rect rect = this.mTmpRect;
            layoutManager.getTransformedBoundingBox(view, rect);
            return rect.top;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final void offsetChildren(int i) {
            this.mLayoutManager.offsetChildrenVertical(i);
        }
    }

    public /* synthetic */ OrientationHelper(RecyclerView.LayoutManager layoutManager, C04511 c04511) {
        this(layoutManager);
    }

    public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager layoutManager, int i) {
        if (i == 0) {
            return new C04511(layoutManager);
        }
        if (i == 1) {
            return new C04522(layoutManager);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedMeasurement(View view);

    public abstract int getDecoratedMeasurementInOther(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public final int getTotalSpaceChange() {
        if (Integer.MIN_VALUE == this.mLastTotalSpace) {
            return 0;
        }
        return getTotalSpace() - this.mLastTotalSpace;
    }

    public abstract int getTransformedEndWithDecoration(View view);

    public abstract int getTransformedStartWithDecoration(View view);

    public abstract void offsetChildren(int i);

    private OrientationHelper(RecyclerView.LayoutManager layoutManager) {
        this.mLastTotalSpace = VideoPlayer.MEDIA_ERROR_SYSTEM;
        this.mTmpRect = new Rect();
        this.mLayoutManager = layoutManager;
    }
}
