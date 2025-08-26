package android.view;

import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.ArraySet;
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
        View viewFindNextUserSpecifiedFocus = view != null ? findNextUserSpecifiedFocus(effectiveRoot, view, i) : null;
        if (viewFindNextUserSpecifiedFocus != null) {
            return viewFindNextUserSpecifiedFocus;
        }
        ArrayList<View> arrayList = this.mTempList;
        try {
            arrayList.clear();
            effectiveRoot.addFocusables(arrayList, i);
            if (!arrayList.isEmpty()) {
                viewFindNextUserSpecifiedFocus = findNextFocus(effectiveRoot, view, rect, i, arrayList);
            }
            return viewFindNextUserSpecifiedFocus;
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
        View viewFindNextKeyboardNavigationCluster;
        if (view2 != null) {
            viewFindNextKeyboardNavigationCluster = findNextUserSpecifiedKeyboardNavigationCluster(view, view2, i);
            if (viewFindNextKeyboardNavigationCluster != null) {
                return viewFindNextKeyboardNavigationCluster;
            }
        } else {
            viewFindNextKeyboardNavigationCluster = null;
        }
        ArrayList<View> arrayList = this.mTempList;
        try {
            arrayList.clear();
            view.addKeyboardNavigationClusters(arrayList, i);
            if (!arrayList.isEmpty()) {
                viewFindNextKeyboardNavigationCluster = findNextKeyboardNavigationCluster(view, view2, arrayList, i);
            }
            return viewFindNextKeyboardNavigationCluster;
        } finally {
            arrayList.clear();
        }
    }

    private View findNextUserSpecifiedKeyboardNavigationCluster(View view, View view2, int i) {
        View viewFindUserSetNextKeyboardNavigationCluster = view2.findUserSetNextKeyboardNavigationCluster(view, i);
        if (viewFindUserSetNextKeyboardNavigationCluster == null || !viewFindUserSetNextKeyboardNavigationCluster.hasFocusable()) {
            return null;
        }
        return viewFindUserSetNextKeyboardNavigationCluster;
    }

    private View findNextUserSpecifiedFocus(ViewGroup viewGroup, View view, int i) {
        View viewFindUserSetNextFocus = view.findUserSetNextFocus(viewGroup, i);
        boolean z = true;
        View viewFindUserSetNextFocus2 = viewFindUserSetNextFocus;
        while (viewFindUserSetNextFocus != null) {
            if (viewFindUserSetNextFocus.isFocusable() && viewFindUserSetNextFocus.getVisibility() == 0 && (!viewFindUserSetNextFocus.isInTouchMode() || viewFindUserSetNextFocus.isFocusableInTouchMode())) {
                return viewFindUserSetNextFocus;
            }
            viewFindUserSetNextFocus = viewFindUserSetNextFocus.findUserSetNextFocus(viewGroup, i);
            boolean z2 = !z;
            if (!z && (viewFindUserSetNextFocus2 = viewFindUserSetNextFocus2.findUserSetNextFocus(viewGroup, i)) == viewFindUserSetNextFocus) {
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
            View previousFocusable = null;
            if (size < 2) {
                return null;
            }
            boolean[] zArr = new boolean[1];
            if (i == 1) {
                previousFocusable = getPreviousFocusable(view, arrayList, size, zArr);
            } else if (i == 2) {
                previousFocusable = getNextFocusable(view, arrayList, size, zArr);
            }
            if (viewGroup != null && viewGroup.mAttachInfo != null && viewGroup == viewGroup.getRootView()) {
                viewGroup.mAttachInfo.mNextFocusLooped = zArr[0];
            }
            return previousFocusable != null ? previousFocusable : arrayList.get(size - 1);
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
        int iLastIndexOf;
        int i2;
        if (i < 2) {
            return null;
        }
        if (view != null && (iLastIndexOf = arrayList.lastIndexOf(view)) >= 0 && (i2 = iLastIndexOf + 1) < i) {
            return arrayList.get(i2);
        }
        zArr[0] = true;
        return arrayList.get(0);
    }

    private static View getPreviousFocusable(View view, ArrayList<View> arrayList, int i, boolean[] zArr) {
        int iIndexOf;
        if (i < 2) {
            return null;
        }
        if (view != null && (iIndexOf = arrayList.indexOf(view)) > 0) {
            return arrayList.get(iIndexOf - 1);
        }
        zArr[0] = true;
        return arrayList.get(i - 1);
    }

    private static View getNextKeyboardNavigationCluster(View view, View view2, List<View> list, int i) {
        int i2;
        if (view2 == null) {
            return list.get(0);
        }
        int iLastIndexOf = list.lastIndexOf(view2);
        return (iLastIndexOf < 0 || (i2 = iLastIndexOf + 1) >= i) ? view : list.get(i2);
    }

    private static View getPreviousKeyboardNavigationCluster(View view, View view2, List<View> list, int i) {
        if (view2 == null) {
            return list.get(i - 1);
        }
        int iIndexOf = list.indexOf(view2);
        return iIndexOf > 0 ? list.get(iIndexOf - 1) : view;
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
        boolean zBeamsOverlap = beamsOverlap(i, rect, rect2);
        if (beamsOverlap(i, rect, rect3) || !zBeamsOverlap) {
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

    public View findNearestTouchable(ViewGroup viewGroup, int i, int i2, int i3, int[] iArr) {
        int i4;
        int i5;
        ArrayList<View> touchables = viewGroup.getTouchables();
        int size = touchables.size();
        int scaledEdgeSlop = ViewConfiguration.get(viewGroup.mContext).getScaledEdgeSlop();
        Rect rect = new Rect();
        Rect rect2 = this.mOtherRect;
        View view = null;
        int i6 = Integer.MAX_VALUE;
        for (int i7 = 0; i7 < size; i7++) {
            View view2 = touchables.get(i7);
            view2.getDrawingRect(rect2);
            viewGroup.offsetRectBetweenParentAndChild(view2, rect2, true, true);
            if (isTouchCandidate(i, i2, rect2, i3)) {
                if (i3 == 17) {
                    i4 = i - rect2.right;
                } else if (i3 == 33) {
                    i4 = i2 - rect2.bottom;
                } else {
                    if (i3 == 66) {
                        i5 = rect2.left;
                    } else {
                        i5 = i3 != 130 ? Integer.MAX_VALUE : rect2.top;
                    }
                    if (i5 >= scaledEdgeSlop && (view == null || rect.contains(rect2) || (!rect2.contains(rect) && i5 < i6))) {
                        rect.set(rect2);
                        if (i3 == 17) {
                            iArr[0] = -i5;
                        } else if (i3 == 33) {
                            iArr[1] = -i5;
                        } else if (i3 == 66) {
                            iArr[0] = i5;
                        } else if (i3 == 130) {
                            iArr[1] = i5;
                        }
                        i6 = i5;
                        view = view2;
                    }
                }
                i5 = i4 + 1;
                if (i5 >= scaledEdgeSlop) {
                }
            }
        }
        return view;
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
                return this.f$0.lambda$new$0((View) obj, (View) obj2);
            }
        };
        private Comparator<View> mSidesComparator = new Comparator() { // from class: android.view.FocusFinder$FocusSorter$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.lambda$new$1((View) obj, (View) obj2);
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
            int iMax = this.mRectByView.get(viewArr[i]).bottom;
            int i6 = i + 1;
            while (i6 < i2) {
                Rect rect2 = this.mRectByView.get(viewArr[i6]);
                if (rect2.top >= iMax) {
                    if (i6 - i > 1) {
                        Arrays.sort(viewArr, i, i6, this.mSidesComparator);
                    }
                    iMax = rect2.bottom;
                    i = i6;
                } else {
                    iMax = Math.max(iMax, rect2.bottom);
                }
                i6++;
            }
            if (i6 - i > 1) {
                Arrays.sort(viewArr, i, i6, this.mSidesComparator);
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
