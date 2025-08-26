package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class ChildHelper {
    public final Callback mCallback;
    public View mViewInRemoveView;
    public int mRemoveStatus = 0;
    public final Bucket mBucket = new Bucket();
    public final List mHiddenViews = new ArrayList();

    public class Bucket {
        public long mData = 0;
        public Bucket mNext;

        public final void clear(int i) {
            if (i < 64) {
                this.mData &= ~(1 << i);
                return;
            }
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.clear(i - 64);
            }
        }

        public final int countOnesBefore(int i) {
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

        public final void ensureNext() {
            if (this.mNext == null) {
                this.mNext = new Bucket();
            }
        }

        public final boolean get(int i) {
            if (i < 64) {
                return ((1 << i) & this.mData) != 0;
            }
            ensureNext();
            return this.mNext.get(i - 64);
        }

        public final void insert(int i, boolean z) {
            if (i >= 64) {
                ensureNext();
                this.mNext.insert(i - 64, z);
                return;
            }
            long j = this.mData;
            boolean z2 = (Long.MIN_VALUE & j) != 0;
            long j2 = (1 << i) - 1;
            this.mData = ((j & (~j2)) << 1) | (j & j2);
            if (z) {
                set(i);
            } else {
                clear(i);
            }
            if (z2 || this.mNext != null) {
                ensureNext();
                this.mNext.insert(0, z2);
            }
        }

        public final boolean remove(int i) {
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

        public final void reset() {
            this.mData = 0L;
            Bucket bucket = this.mNext;
            if (bucket != null) {
                bucket.reset();
            }
        }

        public final void set(int i) {
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

    public interface Callback {
    }

    public ChildHelper(Callback callback) {
        this.mCallback = callback;
    }

    public final void addView(View view, int i, boolean z) {
        Callback callback = this.mCallback;
        int childCount = i < 0 ? RecyclerView.this.getChildCount() : getOffset(i);
        this.mBucket.insert(childCount, z);
        if (z) {
            hideViewInternal(view);
        }
        RecyclerView recyclerView = RecyclerView.this;
        recyclerView.addView(view, childCount);
        RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
        RecyclerView.Adapter adapter = recyclerView.mAdapter;
        if (adapter != null && childViewHolderInt != null) {
            adapter.onViewAttachedToWindow(childViewHolderInt);
        }
        List list = recyclerView.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = ((ArrayList) list).size() - 1; size >= 0; size--) {
                ((RecyclerView.OnChildAttachStateChangeListener) ((ArrayList) recyclerView.mOnChildAttachStateListeners).get(size)).onChildViewAttachedToWindow(view);
            }
        }
    }

    public final void attachViewToParent(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        Callback callback = this.mCallback;
        int childCount = i < 0 ? RecyclerView.this.getChildCount() : getOffset(i);
        this.mBucket.insert(childCount, z);
        if (z) {
            hideViewInternal(view);
        }
        RecyclerView.AnonymousClass10 anonymousClass10 = (RecyclerView.AnonymousClass10) callback;
        anonymousClass10.getClass();
        RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
        RecyclerView recyclerView = RecyclerView.this;
        if (childViewHolderInt != null) {
            if (!childViewHolderInt.isTmpDetached() && !childViewHolderInt.shouldIgnore()) {
                throw new IllegalArgumentException("Called attach on a child which is not detached: " + childViewHolderInt + recyclerView.exceptionLabel());
            }
            childViewHolderInt.mFlags &= -257;
        }
        recyclerView.attachViewToParent(view, childCount, layoutParams);
    }

    public final void detachViewFromParent(int i) {
        int offset = getOffset(i);
        this.mBucket.remove(offset);
        RecyclerView recyclerView = RecyclerView.this;
        View childAt = recyclerView.getChildAt(offset);
        if (childAt != null) {
            RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(childAt);
            if (childViewHolderInt != null) {
                if (childViewHolderInt.isTmpDetached() && !childViewHolderInt.shouldIgnore()) {
                    throw new IllegalArgumentException("called detach on an already detached child " + childViewHolderInt + recyclerView.exceptionLabel());
                }
                childViewHolderInt.addFlags(256);
            }
        } else {
            int[] iArr = RecyclerView.NESTED_SCROLLING_ATTRS;
        }
        recyclerView.detachViewFromParent(offset);
    }

    public final View getChildAt(int i) {
        return RecyclerView.this.getChildAt(getOffset(i));
    }

    public final int getChildCount() {
        return RecyclerView.this.getChildCount() - ((ArrayList) this.mHiddenViews).size();
    }

    public final int getOffset(int i) {
        if (i < 0) {
            return -1;
        }
        int childCount = RecyclerView.this.getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            Bucket bucket = this.mBucket;
            int iCountOnesBefore = i - (i2 - bucket.countOnesBefore(i2));
            if (iCountOnesBefore == 0) {
                while (bucket.get(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += iCountOnesBefore;
        }
        return -1;
    }

    public final View getUnfilteredChildAt(int i) {
        return RecyclerView.this.getChildAt(i);
    }

    public final int getUnfilteredChildCount() {
        return RecyclerView.this.getChildCount();
    }

    public final void hideViewInternal(View view) {
        ((ArrayList) this.mHiddenViews).add(view);
        RecyclerView.AnonymousClass10 anonymousClass10 = (RecyclerView.AnonymousClass10) this.mCallback;
        anonymousClass10.getClass();
        RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            int i = childViewHolderInt.mPendingAccessibilityState;
            if (i != -1) {
                childViewHolderInt.mWasImportantForAccessibilityBeforeHidden = i;
            } else {
                View view2 = childViewHolderInt.itemView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                childViewHolderInt.mWasImportantForAccessibilityBeforeHidden = view2.getImportantForAccessibility();
            }
            RecyclerView.this.setChildImportantForAccessibilityInternal(childViewHolderInt, 4);
        }
    }

    public final int indexOfChild(View view) {
        int iIndexOfChild = RecyclerView.this.indexOfChild(view);
        if (iIndexOfChild == -1) {
            return -1;
        }
        Bucket bucket = this.mBucket;
        if (bucket.get(iIndexOfChild)) {
            return -1;
        }
        return iIndexOfChild - bucket.countOnesBefore(iIndexOfChild);
    }

    public final boolean isHidden(View view) {
        return ((ArrayList) this.mHiddenViews).contains(view);
    }

    public final String toString() {
        return this.mBucket.toString() + ", hidden list:" + ((ArrayList) this.mHiddenViews).size();
    }

    public final void unhideViewInternal(View view) {
        if (((ArrayList) this.mHiddenViews).remove(view)) {
            RecyclerView.AnonymousClass10 anonymousClass10 = (RecyclerView.AnonymousClass10) this.mCallback;
            anonymousClass10.getClass();
            RecyclerView.ViewHolder childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt != null) {
                RecyclerView.this.setChildImportantForAccessibilityInternal(childViewHolderInt, childViewHolderInt.mWasImportantForAccessibilityBeforeHidden);
                childViewHolderInt.mWasImportantForAccessibilityBeforeHidden = 0;
            }
        }
    }
}
