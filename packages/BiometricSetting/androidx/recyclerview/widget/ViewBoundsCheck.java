package androidx.recyclerview.widget;

import android.view.View;

/* loaded from: classes.dex */
final class ViewBoundsCheck {
    BoundFlags mBoundFlags = new BoundFlags();
    final Callback mCallback;

    static class BoundFlags {
        int mBoundFlags = 0;
        int mChildEnd;
        int mChildStart;
        int mRvEnd;
        int mRvStart;

        BoundFlags() {
        }

        final boolean boundsMatch() {
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

    interface Callback {
        View getChildAt(int i);

        int getChildEnd(View view);

        int getChildStart(View view);

        int getParentEnd();

        int getParentStart();
    }

    ViewBoundsCheck(Callback callback) {
        this.mCallback = callback;
    }

    final View findOneViewWithinBoundFlags(int i, int i2, int i3, int i4) {
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
                BoundFlags boundFlags2 = this.mBoundFlags;
                boundFlags2.mBoundFlags = i4 | 0;
                if (boundFlags2.boundsMatch()) {
                    view = childAt;
                }
            }
            i += i5;
        }
        return view;
    }
}
