package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class ChildHelper {
    final Callback mCallback;
    final Bucket mBucket = new Bucket();
    final List<View> mHiddenViews = new ArrayList();

    static class Bucket {
        long mData = 0;
        Bucket mNext;

        Bucket() {
        }

        private void ensureNext() {
            if (this.mNext == null) {
                this.mNext = new Bucket();
            }
        }

        final int countOnesBefore(int i) {
            Bucket bucket = this.mNext;
            if (bucket == null) {
                if (i >= 64) {
                    return Long.bitCount(this.mData);
                }
                return Long.bitCount(((1 << i) - 1) & this.mData);
            }
            if (i < 64) {
                return Long.bitCount(((1 << i) - 1) & this.mData);
            }
            return Long.bitCount(this.mData) + bucket.countOnesBefore(i - 64);
        }

        final boolean get(int i) {
            if (i < 64) {
                return ((1 << i) & this.mData) != 0;
            }
            ensureNext();
            return this.mNext.get(i - 64);
        }

        final boolean remove(int i) {
            if (i >= 64) {
                ensureNext();
                return this.mNext.remove(i - 64);
            }
            long j = 1 << i;
            long j2 = this.mData;
            boolean z = (j2 & j) != 0;
            long j3 = j2 & (~j);
            this.mData = j3;
            long j4 = j - 1;
            this.mData = (j3 & j4) | Long.rotateRight((~j4) & j3, 1);
            Bucket bucket = this.mNext;
            if (bucket != null) {
                if (bucket.get(0)) {
                    set(63);
                }
                this.mNext.remove(0);
            }
            return z;
        }

        final void reset() {
            this.mData = 0L;
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.reset();
            }
        }

        final void set(int i) {
            if (i < 64) {
                this.mData |= 1 << i;
            } else {
                ensureNext();
                this.mNext.set(i - 64);
            }
        }

        public final String toString() {
            if (this.mNext == null) {
                return Long.toBinaryString(this.mData);
            }
            return this.mNext.toString() + "xx" + Long.toBinaryString(this.mData);
        }
    }

    interface Callback {
    }

    ChildHelper(RecyclerView.C01635 c01635) {
        this.mCallback = c01635;
    }

    private int getOffset(int i) {
        if (i < 0) {
            return -1;
        }
        int childCount = ((RecyclerView.C01635) this.mCallback).getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            Bucket bucket = this.mBucket;
            int countOnesBefore = i - (i2 - bucket.countOnesBefore(i2));
            if (countOnesBefore == 0) {
                while (bucket.get(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += countOnesBefore;
        }
        return -1;
    }

    private void unhideViewInternal(View view) {
        if (((ArrayList) this.mHiddenViews).remove(view)) {
            RecyclerView.C01635 c01635 = (RecyclerView.C01635) this.mCallback;
            c01635.getClass();
            RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt != null) {
                childViewHolderInt.onLeftHiddenState(RecyclerView.this);
            }
        }
    }

    final View getChildAt(int i) {
        return RecyclerView.this.getChildAt(getOffset(i));
    }

    final int getChildCount() {
        return ((RecyclerView.C01635) this.mCallback).getChildCount() - ((ArrayList) this.mHiddenViews).size();
    }

    final View getUnfilteredChildAt(int i) {
        return RecyclerView.this.getChildAt(i);
    }

    final int getUnfilteredChildCount() {
        return ((RecyclerView.C01635) this.mCallback).getChildCount();
    }

    final boolean removeViewIfHidden() {
        RecyclerView.C01635 c01635 = (RecyclerView.C01635) this.mCallback;
        int indexOfChild = RecyclerView.this.indexOfChild(null);
        if (indexOfChild == -1) {
            unhideViewInternal(null);
            return true;
        }
        Bucket bucket = this.mBucket;
        if (!bucket.get(indexOfChild)) {
            return false;
        }
        bucket.remove(indexOfChild);
        unhideViewInternal(null);
        c01635.removeViewAt(indexOfChild);
        return true;
    }

    public final String toString() {
        return this.mBucket.toString() + ", hidden list:" + ((ArrayList) this.mHiddenViews).size();
    }
}
