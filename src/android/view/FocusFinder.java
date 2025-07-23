package android.view;

import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.FocusFinder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class FocusFinder {
    private static final ThreadLocal<FocusFinder> tlFocusFinder = new ThreadLocal<FocusFinder>() { // from class: android.view.FocusFinder.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public FocusFinder initialValue() {
            return new FocusFinder();
        }
    };
    final Rect mBestCandidateRect;
    private final FocusSorter mFocusSorter;
    final Rect mFocusedRect;
    final Rect mOtherRect;
    private final ArrayList<View> mTempList;
    private final UserSpecifiedFocusComparator mUserSpecifiedClusterComparator;
    private final UserSpecifiedFocusComparator mUserSpecifiedFocusComparator;

    private static final boolean isValidId(int i) {
        return (i == 0 || i == -1) ? false : true;
    }

    long getWeightedDistanceFor(long j, long j2) {
        return (13 * j * j) + (j2 * j2);
    }

    public static FocusFinder getInstance() {
        return tlFocusFinder.get();
    }

    static /* synthetic */ View lambda$new$0(View view, View view2) {
        if (isValidId(view2.getNextFocusForwardId())) {
            return view2.findUserSetNextFocus(view, 2);
        }
        return null;
    }

    static /* synthetic */ View lambda$new$1(View view, View view2) {
        if (isValidId(view2.getNextClusterForwardId())) {
            return view2.findUserSetNextKeyboardNavigationCluster(view, 2);
        }
        return null;
    }

    private FocusFinder() {
        this.mFocusedRect = new Rect();
        this.mOtherRect = new Rect();
        this.mBestCandidateRect = new Rect();
        this.mUserSpecifiedFocusComparator = new UserSpecifiedFocusComparator(new UserSpecifiedFocusComparator.NextFocusGetter() { // from class: android.view.FocusFinder$$ExternalSyntheticLambda0
            @Override // android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter
            public final View get(View view, View view2) {
                return FocusFinder.lambda$new$0(view, view2);
            }
        });
        this.mUserSpecifiedClusterComparator = new UserSpecifiedFocusComparator(new UserSpecifiedFocusComparator.NextFocusGetter() { // from class: android.view.FocusFinder$$ExternalSyntheticLambda1
            @Override // android.view.FocusFinder.UserSpecifiedFocusComparator.NextFocusGetter
            public final View get(View view, View view2) {
                return FocusFinder.lambda$new$1(view, view2);
            }
        });
        this.mFocusSorter = new FocusSorter();
        this.mTempList = new ArrayList<>();
    }

    public final View findNextFocus(ViewGroup viewGroup, View view, int i) {
        return findNextFocus(viewGroup, view, null, i);
    }

    public View findNextFocusFromRect(ViewGroup viewGroup, Rect rect, int i) {
        this.mFocusedRect.set(rect);
        return findNextFocus(viewGroup, null, this.mFocusedRect, i);
    }

    private View findNextFocus(ViewGroup viewGroup, View view, Rect rect, int i) {
        ViewGroup effectiveRoot = getEffectiveRoot(viewGroup, view);
        View findNextUserSpecifiedFocus = view != null ? findNextUserSpecifiedFocus(effectiveRoot, view, i) : null;
        if (findNextUserSpecifiedFocus != null) {
            return findNextUserSpecifiedFocus;
        }
        ArrayList<View> arrayList = this.mTempList;
        try {
            arrayList.clear();
            effectiveRoot.addFocusables(arrayList, i);
            if (!arrayList.isEmpty()) {
                findNextUserSpecifiedFocus = findNextFocus(effectiveRoot, view, rect, i, arrayList);
            }
            return findNextUserSpecifiedFocus;
        } finally {
            arrayList.clear();
        }
    }

    private ViewGroup getEffectiveRoot(ViewGroup viewGroup, View view) {
        if (view != null && view != viewGroup) {
            ViewParent parent = view.getParent();
            ViewGroup viewGroup2 = null;
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    break;
                }
                if (parent != viewGroup) {
                    ViewGroup viewGroup3 = (ViewGroup) parent;
                    if (viewGroup3.getTouchscreenBlocksFocus() && view.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN) && viewGroup3.isKeyboardNavigationCluster()) {
                        viewGroup2 = viewGroup3;
                    }
                    parent = parent.getParent();
                } else if (viewGroup2 != null) {
                    return viewGroup2;
                }
            }
        }
        return viewGroup;
    }

    public View findNextKeyboardNavigationCluster(View view, View view2, int i) {
        View view3;
        if (view2 != null) {
            view3 = findNextUserSpecifiedKeyboardNavigationCluster(view, view2, i);
            if (view3 != null) {
                return view3;
            }
        } else {
            view3 = null;
        }
        ArrayList<View> arrayList = this.mTempList;
        try {
            arrayList.clear();
            view.addKeyboardNavigationClusters(arrayList, i);
            if (!arrayList.isEmpty()) {
                view3 = findNextKeyboardNavigationCluster(view, view2, arrayList, i);
            }
            return view3;
        } finally {
            arrayList.clear();
        }
    }

    private View findNextUserSpecifiedKeyboardNavigationCluster(View view, View view2, int i) {
        View findUserSetNextKeyboardNavigationCluster = view2.findUserSetNextKeyboardNavigationCluster(view, i);
        if (findUserSetNextKeyboardNavigationCluster == null || !findUserSetNextKeyboardNavigationCluster.hasFocusable()) {
            return null;
        }
        return findUserSetNextKeyboardNavigationCluster;
    }

    private View findNextUserSpecifiedFocus(ViewGroup viewGroup, View view, int i) {
        View findUserSetNextFocus = view.findUserSetNextFocus(viewGroup, i);
        boolean z = true;
        View view2 = findUserSetNextFocus;
        while (findUserSetNextFocus != null) {
            if (findUserSetNextFocus.isFocusable() && findUserSetNextFocus.getVisibility() == 0 && (!findUserSetNextFocus.isInTouchMode() || findUserSetNextFocus.isFocusableInTouchMode())) {
                return findUserSetNextFocus;
            }
            findUserSetNextFocus = findUserSetNextFocus.findUserSetNextFocus(viewGroup, i);
            boolean z2 = !z;
            if (!z && (view2 = view2.findUserSetNextFocus(viewGroup, i)) == findUserSetNextFocus) {
                return null;
            }
            z = z2;
        }
        return null;
    }

    private View findNextFocus(ViewGroup viewGroup, View view, Rect rect, int i, ArrayList<View> arrayList) {
        if (view != null) {
            if (rect == null) {
                rect = this.mFocusedRect;
            }
            view.getFocusedRect(rect);
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
        } else if (rect == null) {
            rect = this.mFocusedRect;
            if (i != 1) {
                if (i != 2) {
                    if (i == 17 || i == 33) {
                        setFocusBottomRight(viewGroup, rect);
                    } else if (i == 66 || i == 130) {
                        setFocusTopLeft(viewGroup, rect);
                    }
                } else if (viewGroup.isLayoutRtl()) {
                    setFocusBottomRight(viewGroup, rect);
                } else {
                    setFocusTopLeft(viewGroup, rect);
                }
            } else if (viewGroup.isLayoutRtl()) {
                setFocusTopLeft(viewGroup, rect);
            } else {
                setFocusBottomRight(viewGroup, rect);
            }
        }
        if (i == 1 || i == 2) {
            return findNextFocusInRelativeDirection(arrayList, viewGroup, view, rect, i);
        }
        if (i == 17 || i == 33 || i == 66 || i == 130) {
            return findNextFocusInAbsoluteDirection(arrayList, viewGroup, view, rect, i);
        }
        throw new IllegalArgumentException("Unknown direction: " + i);
    }

    private View findNextKeyboardNavigationCluster(View view, View view2, List<View> list, int i) {
        try {
            this.mUserSpecifiedClusterComparator.setFocusables(list, view);
            Collections.sort(list, this.mUserSpecifiedClusterComparator);
            this.mUserSpecifiedClusterComparator.recycle();
            int size = list.size();
            if (i != 1) {
                if (i != 2) {
                    if (i != 17 && i != 33) {
                        if (i != 66 && i != 130) {
                            throw new IllegalArgumentException("Unknown direction: " + i);
                        }
                    }
                }
                return getNextKeyboardNavigationCluster(view, view2, list, size);
            }
            return getPreviousKeyboardNavigationCluster(view, view2, list, size);
        } catch (Throwable th) {
            this.mUserSpecifiedClusterComparator.recycle();
            throw th;
        }
    }

    private View findNextFocusInRelativeDirection(ArrayList<View> arrayList, ViewGroup viewGroup, View view, Rect rect, int i) {
        try {
            this.mUserSpecifiedFocusComparator.setFocusables(arrayList, viewGroup);
            Collections.sort(arrayList, this.mUserSpecifiedFocusComparator);
            this.mUserSpecifiedFocusComparator.recycle();
            int size = arrayList.size();
            View view2 = null;
            if (size < 2) {
                return null;
            }
            boolean[] zArr = new boolean[1];
            if (i == 1) {
                view2 = getPreviousFocusable(view, arrayList, size, zArr);
            } else if (i == 2) {
                view2 = getNextFocusable(view, arrayList, size, zArr);
            }
            if (viewGroup != null && viewGroup.mAttachInfo != null && viewGroup == viewGroup.getRootView()) {
                viewGroup.mAttachInfo.mNextFocusLooped = zArr[0];
            }
            return view2 != null ? view2 : arrayList.get(size - 1);
        } catch (Throwable th) {
            this.mUserSpecifiedFocusComparator.recycle();
            throw th;
        }
    }

    private void setFocusBottomRight(ViewGroup viewGroup, Rect rect) {
        int scrollY = viewGroup.getScrollY() + viewGroup.getHeight();
        int scrollX = viewGroup.getScrollX() + viewGroup.getWidth();
        rect.set(scrollX, scrollY, scrollX, scrollY);
    }

    private void setFocusTopLeft(ViewGroup viewGroup, Rect rect) {
        int scrollY = viewGroup.getScrollY();
        int scrollX = viewGroup.getScrollX();
        rect.set(scrollX, scrollY, scrollX, scrollY);
    }

    View findNextFocusInAbsoluteDirection(ArrayList<View> arrayList, ViewGroup viewGroup, View view, Rect rect, int i) {
        this.mBestCandidateRect.set(rect);
        if (i == 17) {
            this.mBestCandidateRect.offset(rect.width() + 1, 0);
        } else if (i == 33) {
            this.mBestCandidateRect.offset(0, rect.height() + 1);
        } else if (i == 66) {
            this.mBestCandidateRect.offset(-(rect.width() + 1), 0);
        } else if (i == 130) {
            this.mBestCandidateRect.offset(0, -(rect.height() + 1));
        }
        int size = arrayList.size();
        View view2 = null;
        for (int i2 = 0; i2 < size; i2++) {
            View view3 = arrayList.get(i2);
            if (view3 != view && view3 != viewGroup) {
                view3.getFocusedRect(this.mOtherRect);
                viewGroup.offsetDescendantRectToMyCoords(view3, this.mOtherRect);
                if (isBetterCandidate(i, rect, this.mOtherRect, this.mBestCandidateRect)) {
                    this.mBestCandidateRect.set(this.mOtherRect);
                    view2 = view3;
                }
            }
        }
        return view2;
    }

    private static View getNextFocusable(View view, ArrayList<View> arrayList, int i, boolean[] zArr) {
        int lastIndexOf;
        int i2;
        if (i < 2) {
            return null;
        }
        if (view != null && (lastIndexOf = arrayList.lastIndexOf(view)) >= 0 && (i2 = lastIndexOf + 1) < i) {
            return arrayList.get(i2);
        }
        zArr[0] = true;
        return arrayList.get(0);
    }

    private static View getPreviousFocusable(View view, ArrayList<View> arrayList, int i, boolean[] zArr) {
        int indexOf;
        if (i < 2) {
            return null;
        }
        if (view != null && (indexOf = arrayList.indexOf(view)) > 0) {
            return arrayList.get(indexOf - 1);
        }
        zArr[0] = true;
        return arrayList.get(i - 1);
    }

    private static View getNextKeyboardNavigationCluster(View view, View view2, List<View> list, int i) {
        int i2;
        if (view2 == null) {
            return list.get(0);
        }
        int lastIndexOf = list.lastIndexOf(view2);
        return (lastIndexOf < 0 || (i2 = lastIndexOf + 1) >= i) ? view : list.get(i2);
    }

    private static View getPreviousKeyboardNavigationCluster(View view, View view2, List<View> list, int i) {
        if (view2 == null) {
            return list.get(i - 1);
        }
        int indexOf = list.indexOf(view2);
        return indexOf > 0 ? list.get(indexOf - 1) : view;
    }

    boolean isBetterCandidate(int i, Rect rect, Rect rect2, Rect rect3) {
        if (!isCandidate(rect, rect2, i)) {
            return false;
        }
        if (isCandidate(rect, rect3, i) && !beamBeats(i, rect, rect2, rect3)) {
            return !beamBeats(i, rect, rect3, rect2) && getWeightedDistanceFor((long) majorAxisDistance(i, rect, rect2), (long) minorAxisDistance(i, rect, rect2)) < getWeightedDistanceFor((long) majorAxisDistance(i, rect, rect3), (long) minorAxisDistance(i, rect, rect3));
        }
        return true;
    }

    boolean beamBeats(int i, Rect rect, Rect rect2, Rect rect3) {
        boolean beamsOverlap = beamsOverlap(i, rect, rect2);
        if (beamsOverlap(i, rect, rect3) || !beamsOverlap) {
            return false;
        }
        return !isToDirectionOf(i, rect, rect3) || i == 17 || i == 66 || majorAxisDistance(i, rect, rect2) < majorAxisDistanceToFarEdge(i, rect, rect3);
    }

    boolean isCandidate(Rect rect, Rect rect2, int i) {
        if (i == 17) {
            return (rect.right > rect2.right || rect.left >= rect2.right) && rect.left > rect2.left;
        }
        if (i == 33) {
            return (rect.bottom > rect2.bottom || rect.top >= rect2.bottom) && rect.top > rect2.top;
        }
        if (i == 66) {
            return (rect.left < rect2.left || rect.right <= rect2.left) && rect.right < rect2.right;
        }
        if (i == 130) {
            return (rect.top < rect2.top || rect.bottom <= rect2.top) && rect.bottom < rect2.bottom;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    boolean beamsOverlap(int i, Rect rect, Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return rect2.right > rect.left && rect2.left < rect.right;
        }
        return rect2.bottom > rect.top && rect2.top < rect.bottom;
    }

    boolean isToDirectionOf(int i, Rect rect, Rect rect2) {
        if (i == 17) {
            return rect.left >= rect2.right;
        }
        if (i == 33) {
            return rect.top >= rect2.bottom;
        }
        if (i == 66) {
            return rect.right <= rect2.left;
        }
        if (i == 130) {
            return rect.bottom <= rect2.top;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    static int majorAxisDistance(int i, Rect rect, Rect rect2) {
        return Math.max(0, majorAxisDistanceRaw(i, rect, rect2));
    }

    static int majorAxisDistanceRaw(int i, Rect rect, Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.right;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.bottom;
        } else if (i == 66) {
            i2 = rect2.left;
            i3 = rect.right;
        } else if (i == 130) {
            i2 = rect2.top;
            i3 = rect.bottom;
        } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return i2 - i3;
    }

    static int majorAxisDistanceToFarEdge(int i, Rect rect, Rect rect2) {
        return Math.max(1, majorAxisDistanceToFarEdgeRaw(i, rect, rect2));
    }

    static int majorAxisDistanceToFarEdgeRaw(int i, Rect rect, Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.left;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.top;
        } else if (i == 66) {
            i2 = rect2.right;
            i3 = rect.right;
        } else if (i == 130) {
            i2 = rect2.bottom;
            i3 = rect.bottom;
        } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return i2 - i3;
    }

    static int minorAxisDistance(int i, Rect rect, Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return Math.abs((rect.left + (rect.width() / 2)) - (rect2.left + (rect2.width() / 2)));
        }
        return Math.abs((rect.top + (rect.height() / 2)) - (rect2.top + (rect2.height() / 2)));
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View findNearestTouchable(android.view.ViewGroup r20, int r21, int r22, int r23, int[] r24) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            r3 = r22
            r4 = r23
            java.util.ArrayList r5 = r1.getTouchables()
            int r6 = r5.size()
            android.content.Context r7 = r1.mContext
            android.view.ViewConfiguration r7 = android.view.ViewConfiguration.get(r7)
            int r7 = r7.getScaledEdgeSlop()
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>()
            android.graphics.Rect r9 = r0.mOtherRect
            r11 = 0
            r13 = 0
            r14 = 2147483647(0x7fffffff, float:NaN)
        L28:
            if (r13 >= r6) goto La0
            java.lang.Object r15 = r5.get(r13)
            android.view.View r15 = (android.view.View) r15
            r15.getDrawingRect(r9)
            r10 = 1
            r1.offsetRectBetweenParentAndChild(r15, r9, r10, r10)
            boolean r16 = r0.isTouchCandidate(r2, r3, r9, r4)
            if (r16 != 0) goto L41
            r17 = 0
            goto L9d
        L41:
            r16 = r10
            r17 = 0
            r10 = 33
            r12 = 17
            if (r4 == r12) goto L64
            if (r4 == r10) goto L5f
            r10 = 66
            if (r4 == r10) goto L5c
            r10 = 130(0x82, float:1.82E-43)
            if (r4 == r10) goto L59
            r10 = 2147483647(0x7fffffff, float:NaN)
            goto L6a
        L59:
            int r10 = r9.top
            goto L6a
        L5c:
            int r10 = r9.left
            goto L6a
        L5f:
            int r10 = r9.bottom
            int r10 = r3 - r10
            goto L68
        L64:
            int r10 = r9.right
            int r10 = r2 - r10
        L68:
            int r10 = r10 + 1
        L6a:
            if (r10 >= r7) goto L9d
            if (r11 == 0) goto L7c
            boolean r18 = r8.contains(r9)
            if (r18 != 0) goto L7c
            boolean r18 = r9.contains(r8)
            if (r18 != 0) goto L9d
            if (r10 >= r14) goto L9d
        L7c:
            r8.set(r9)
            if (r4 == r12) goto L98
            r11 = 33
            if (r4 == r11) goto L94
            r11 = 66
            if (r4 == r11) goto L91
            r11 = 130(0x82, float:1.82E-43)
            if (r4 == r11) goto L8e
            goto L9b
        L8e:
            r24[r16] = r10
            goto L9b
        L91:
            r24[r17] = r10
            goto L9b
        L94:
            int r11 = -r10
            r24[r16] = r11
            goto L9b
        L98:
            int r11 = -r10
            r24[r17] = r11
        L9b:
            r14 = r10
            r11 = r15
        L9d:
            int r13 = r13 + 1
            goto L28
        La0:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.FocusFinder.findNearestTouchable(android.view.ViewGroup, int, int, int, int[]):android.view.View");
    }

    private boolean isTouchCandidate(int i, int i2, Rect rect, int i3) {
        if (i3 == 17) {
            return rect.left <= i && rect.top <= i2 && i2 <= rect.bottom;
        }
        if (i3 == 33) {
            return rect.top <= i2 && rect.left <= i && i <= rect.right;
        }
        if (i3 == 66) {
            return rect.left >= i && rect.top <= i2 && i2 <= rect.bottom;
        }
        if (i3 == 130) {
            return rect.top >= i2 && rect.left <= i && i <= rect.right;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    static final class FocusSorter {
        private int mLastPoolRect;
        private int mRtlMult;
        private ArrayList<Rect> mRectPool = new ArrayList<>();
        private HashMap<View, Rect> mRectByView = null;
        private Comparator<View> mTopsComparator = new Comparator() { // from class: android.view.FocusFinder$FocusSorter$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$new$0;
                lambda$new$0 = FocusFinder.FocusSorter.this.lambda$new$0((View) obj, (View) obj2);
                return lambda$new$0;
            }
        };
        private Comparator<View> mSidesComparator = new Comparator() { // from class: android.view.FocusFinder$FocusSorter$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$new$1;
                lambda$new$1 = FocusFinder.FocusSorter.this.lambda$new$1((View) obj, (View) obj2);
                return lambda$new$1;
            }
        };

        FocusSorter() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$new$0(View view, View view2) {
            if (view == view2) {
                return 0;
            }
            Rect rect = this.mRectByView.get(view);
            Rect rect2 = this.mRectByView.get(view2);
            int i = rect.top - rect2.top;
            return i == 0 ? rect.bottom - rect2.bottom : i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$new$1(View view, View view2) {
            if (view == view2) {
                return 0;
            }
            Rect rect = this.mRectByView.get(view);
            Rect rect2 = this.mRectByView.get(view2);
            int i = rect.left - rect2.left;
            if (i == 0) {
                return rect.right - rect2.right;
            }
            return this.mRtlMult * i;
        }

        public void sort(View[] viewArr, int i, int i2, ViewGroup viewGroup, boolean z) {
            int i3 = i2 - i;
            if (i3 < 2) {
                return;
            }
            if (this.mRectByView == null) {
                this.mRectByView = new HashMap<>();
            }
            this.mRtlMult = z ? -1 : 1;
            for (int size = this.mRectPool.size(); size < i3; size++) {
                this.mRectPool.add(new Rect());
            }
            for (int i4 = i; i4 < i2; i4++) {
                ArrayList<Rect> arrayList = this.mRectPool;
                int i5 = this.mLastPoolRect;
                this.mLastPoolRect = i5 + 1;
                Rect rect = arrayList.get(i5);
                viewArr[i4].getDrawingRect(rect);
                viewGroup.offsetDescendantRectToMyCoords(viewArr[i4], rect);
                this.mRectByView.put(viewArr[i4], rect);
            }
            Arrays.sort(viewArr, i, i3, this.mTopsComparator);
            int i6 = this.mRectByView.get(viewArr[i]).bottom;
            int i7 = i + 1;
            while (i7 < i2) {
                Rect rect2 = this.mRectByView.get(viewArr[i7]);
                if (rect2.top >= i6) {
                    if (i7 - i > 1) {
                        Arrays.sort(viewArr, i, i7, this.mSidesComparator);
                    }
                    i6 = rect2.bottom;
                    i = i7;
                } else {
                    i6 = Math.max(i6, rect2.bottom);
                }
                i7++;
            }
            if (i7 - i > 1) {
                Arrays.sort(viewArr, i, i7, this.mSidesComparator);
            }
            this.mLastPoolRect = 0;
            this.mRectByView.clear();
        }
    }

    public static void sort(View[] viewArr, int i, int i2, ViewGroup viewGroup, boolean z) {
        getInstance().mFocusSorter.sort(viewArr, i, i2, viewGroup, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class UserSpecifiedFocusComparator implements Comparator<View> {
        private final NextFocusGetter mNextFocusGetter;
        private View mRoot;
        private final ArrayMap<View, View> mNextFoci = new ArrayMap<>();
        private final ArraySet<View> mIsConnectedTo = new ArraySet<>();
        private final ArrayMap<View, View> mHeadsOfChains = new ArrayMap<>();
        private final ArrayMap<View, Integer> mOriginalOrdinal = new ArrayMap<>();

        public interface NextFocusGetter {
            View get(View view, View view2);
        }

        UserSpecifiedFocusComparator(NextFocusGetter nextFocusGetter) {
            this.mNextFocusGetter = nextFocusGetter;
        }

        public void recycle() {
            this.mRoot = null;
            this.mHeadsOfChains.clear();
            this.mIsConnectedTo.clear();
            this.mOriginalOrdinal.clear();
            this.mNextFoci.clear();
        }

        public void setFocusables(List<View> list, View view) {
            this.mRoot = view;
            for (int i = 0; i < list.size(); i++) {
                this.mOriginalOrdinal.put(list.get(i), Integer.valueOf(i));
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                View view2 = list.get(size);
                View view3 = this.mNextFocusGetter.get(this.mRoot, view2);
                if (view3 != null && this.mOriginalOrdinal.containsKey(view3)) {
                    this.mNextFoci.put(view2, view3);
                    this.mIsConnectedTo.add(view3);
                }
            }
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                View view4 = list.get(size2);
                if (this.mNextFoci.get(view4) != null && !this.mIsConnectedTo.contains(view4)) {
                    setHeadOfChain(view4);
                }
            }
        }

        private void setHeadOfChain(View view) {
            View view2 = view;
            while (view != null) {
                View view3 = this.mHeadsOfChains.get(view);
                if (view3 != null) {
                    if (view3 == view2) {
                        return;
                    }
                    view = view2;
                    view2 = view3;
                }
                this.mHeadsOfChains.put(view, view2);
                view = this.mNextFoci.get(view);
            }
        }

        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            boolean z;
            if (view == view2) {
                return 0;
            }
            View view3 = this.mHeadsOfChains.get(view);
            View view4 = this.mHeadsOfChains.get(view2);
            if (view3 == view4 && view3 != null) {
                if (view == view3) {
                    return -1;
                }
                return (view2 == view3 || this.mNextFoci.get(view) == null) ? 1 : -1;
            }
            if (view3 != null) {
                view = view3;
                z = true;
            } else {
                z = false;
            }
            if (view4 != null) {
                view2 = view4;
                z = true;
            }
            if (z) {
                return this.mOriginalOrdinal.get(view).intValue() < this.mOriginalOrdinal.get(view2).intValue() ? -1 : 1;
            }
            return 0;
        }
    }
}
