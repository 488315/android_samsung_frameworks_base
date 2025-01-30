package androidx.recyclerview.widget;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewBoundsCheck {
    public final BoundFlags mBoundFlags = new BoundFlags();
    public final Callback mCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BoundFlags {
        public int mBoundFlags = 0;
        public int mChildEnd;
        public int mChildStart;
        public int mRvEnd;
        public int mRvStart;

        public final boolean boundsMatch() {
            int i = this.mBoundFlags;
            int i2 = 2;
            if ((i & 7) != 0) {
                int i3 = this.mChildStart;
                int i4 = this.mRvStart;
                if ((((i3 > i4 ? 1 : i3 == i4 ? 2 : 4) << 0) & i) == 0) {
                    return false;
                }
            }
            if ((i & 112) != 0) {
                int i5 = this.mChildStart;
                int i6 = this.mRvEnd;
                if ((((i5 > i6 ? 1 : i5 == i6 ? 2 : 4) << 4) & i) == 0) {
                    return false;
                }
            }
            if ((i & 1792) != 0) {
                int i7 = this.mChildEnd;
                int i8 = this.mRvStart;
                if ((((i7 > i8 ? 1 : i7 == i8 ? 2 : 4) << 8) & i) == 0) {
                    return false;
                }
            }
            if ((i & 28672) != 0) {
                int i9 = this.mChildEnd;
                int i10 = this.mRvEnd;
                if (i9 > i10) {
                    i2 = 1;
                } else if (i9 != i10) {
                    i2 = 4;
                }
                if (((i2 << 12) & i) == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        View getChildAt(int i);

        int getChildEnd(View view);

        int getChildStart(View view);

        int getParentEnd();

        int getParentStart();
    }

    public ViewBoundsCheck(Callback callback) {
        this.mCallback = callback;
    }

    public final View findOneViewWithinBoundFlags(int i, int i2, int i3, int i4) {
        Callback callback = this.mCallback;
        int parentStart = callback.getParentStart();
        int parentEnd = callback.getParentEnd();
        int i5 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View childAt = callback.getChildAt(i);
            int childStart = callback.getChildStart(childAt);
            int childEnd = callback.getChildEnd(childAt);
            BoundFlags boundFlags = this.mBoundFlags;
            boundFlags.mRvStart = parentStart;
            boundFlags.mRvEnd = parentEnd;
            boundFlags.mChildStart = childStart;
            boundFlags.mChildEnd = childEnd;
            if (i3 != 0) {
                boundFlags.mBoundFlags = i3 | 0;
                if (boundFlags.boundsMatch()) {
                    return childAt;
                }
            }
            if (i4 != 0) {
                boundFlags.mBoundFlags = i4 | 0;
                if (boundFlags.boundsMatch()) {
                    view = childAt;
                }
            }
            i += i5;
        }
        return view;
    }

    public final boolean isViewWithinBoundFlags(View view) {
        Callback callback = this.mCallback;
        int parentStart = callback.getParentStart();
        int parentEnd = callback.getParentEnd();
        int childStart = callback.getChildStart(view);
        int childEnd = callback.getChildEnd(view);
        BoundFlags boundFlags = this.mBoundFlags;
        boundFlags.mRvStart = parentStart;
        boundFlags.mRvEnd = parentEnd;
        boundFlags.mChildStart = childStart;
        boundFlags.mChildEnd = childEnd;
        boundFlags.mBoundFlags = 24579 | 0;
        return boundFlags.boundsMatch();
    }
}
