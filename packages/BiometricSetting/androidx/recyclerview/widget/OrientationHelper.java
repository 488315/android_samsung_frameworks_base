package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public abstract class OrientationHelper {
    protected final RecyclerView.LayoutManager mLayoutManager;
    private int mLastTotalSpace = Integer.MIN_VALUE;
    final Rect mTmpRect = new Rect();

    /* renamed from: androidx.recyclerview.widget.OrientationHelper$1 */
    final class C01581 extends OrientationHelper {
        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedEnd(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return view.getRight() + ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedStart(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return (view.getLeft() - ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEndAfterPadding() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return layoutManager.getWidth() - layoutManager.getPaddingRight();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingLeft();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTotalSpace() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return (layoutManager.getWidth() - layoutManager.getPaddingLeft()) - layoutManager.getPaddingRight();
        }
    }

    /* renamed from: androidx.recyclerview.widget.OrientationHelper$2 */
    final class C01592 extends OrientationHelper {
        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedEnd(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return view.getBottom() + ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getDecoratedStart(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            this.mLayoutManager.getClass();
            return (view.getTop() - ((RecyclerView.LayoutParams) view.getLayoutParams()).mDecorInsets.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getEndAfterPadding() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return layoutManager.getHeight() - layoutManager.getPaddingBottom();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingTop();
        }

        @Override // androidx.recyclerview.widget.OrientationHelper
        public final int getTotalSpace() {
            RecyclerView.LayoutManager layoutManager = this.mLayoutManager;
            return (layoutManager.getHeight() - layoutManager.getPaddingTop()) - layoutManager.getPaddingBottom();
        }
    }

    OrientationHelper(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager layoutManager, int i) {
        if (i == 0) {
            return new C01581(layoutManager);
        }
        if (i == 1) {
            return new C01592(layoutManager);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEndAfterPadding();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();
}
