package androidx.compose.ui.platform;

import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.collection.MutableObjectIntMap;
import androidx.collection.MutableScatterMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ObjectIntMapKt;
import androidx.collection.ScatterMapKt;
import androidx.collection.ScatterSetKt;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusInteropUtils_androidKt;
import androidx.compose.ui.focus.TwoDimensionalFocusSearchKt;
import androidx.compose.ui.graphics.RectHelper_androidKt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FocusFinderCompat {
    public static final Companion Companion = new Companion(null);
    public static final FocusFinderCompat$Companion$FocusFinderThreadLocal$1 FocusFinderThreadLocal = new ThreadLocal<FocusFinderCompat>() { // from class: androidx.compose.ui.platform.FocusFinderCompat$Companion$FocusFinderThreadLocal$1
        @Override // java.lang.ThreadLocal
        public final FocusFinderCompat initialValue() {
            return new FocusFinderCompat();
        }
    };
    public final Rect cachedFocusedRect = new Rect();
    public final Rect bestCandidateRect = new Rect();
    public final Rect otherRect = new Rect();
    public final UserSpecifiedFocusComparator userSpecifiedFocusComparator = new UserSpecifiedFocusComparator(new UserSpecifiedFocusComparator.NextFocusGetter() { // from class: androidx.compose.ui.platform.FocusFinderCompat$$ExternalSyntheticLambda0
    });
    public final ArrayList tmpList = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class UserSpecifiedFocusComparator implements Comparator<View> {
        public final NextFocusGetter mNextFocusGetter;
        public View root;
        public final MutableScatterMap nextFoci = ScatterMapKt.mutableScatterMapOf();
        public final MutableScatterSet isConnectedTo = ScatterSetKt.mutableScatterSetOf();
        public final MutableScatterMap headsOfChains = ScatterMapKt.mutableScatterMapOf();
        public final MutableObjectIntMap originalOrdinal = ObjectIntMapKt.mutableObjectIntMapOf();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public interface NextFocusGetter {
        }

        public UserSpecifiedFocusComparator(NextFocusGetter nextFocusGetter) {
            this.mNextFocusGetter = nextFocusGetter;
        }

        @Override // java.util.Comparator
        public final int compare(View view, View view2) {
            View view3 = view;
            View view4 = view2;
            if (view3 == view4) {
                return 0;
            }
            if (view3 == null) {
                return -1;
            }
            if (view4 == null) {
                return 1;
            }
            View view5 = (View) this.headsOfChains.get(view3);
            View view6 = (View) this.headsOfChains.get(view4);
            if (view5 == view6 && view5 != null) {
                if (view3 == view5) {
                    return -1;
                }
                return (view4 == view5 || this.nextFoci.get(view3) == null) ? 1 : -1;
            }
            if (view5 != null) {
                view3 = view5;
            }
            if (view6 != null) {
                view4 = view6;
            }
            if (view5 == null && view6 == null) {
                return 0;
            }
            return this.originalOrdinal.get(view3) < this.originalOrdinal.get(view4) ? -1 : 1;
        }

        public final void setFocusables(ArrayList arrayList, View view) {
            this.root = view;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.originalOrdinal.set(i, (View) arrayList.get(i));
            }
            int size2 = arrayList.size() - 1;
            if (size2 >= 0) {
                while (true) {
                    int i2 = size2 - 1;
                    View view2 = (View) arrayList.get(size2);
                    ((FocusFinderCompat$$ExternalSyntheticLambda0) this.mNextFocusGetter).getClass();
                    Companion companion = FocusFinderCompat.Companion;
                    int nextFocusForwardId = view2.getNextFocusForwardId();
                    View access$findUserSetNextFocus = (nextFocusForwardId == 0 || nextFocusForwardId == -1) ? null : FocusFinderCompat_androidKt.access$findUserSetNextFocus(view2, view, 2);
                    if (access$findUserSetNextFocus != null && this.originalOrdinal.findKeyIndex(access$findUserSetNextFocus) >= 0) {
                        this.nextFoci.set(view2, access$findUserSetNextFocus);
                        this.isConnectedTo.add(access$findUserSetNextFocus);
                    }
                    if (i2 < 0) {
                        break;
                    } else {
                        size2 = i2;
                    }
                }
            }
            int size3 = arrayList.size() - 1;
            if (size3 < 0) {
                return;
            }
            while (true) {
                int i3 = size3 - 1;
                View view3 = (View) arrayList.get(size3);
                if (((View) this.nextFoci.get(view3)) != null && !this.isConnectedTo.contains(view3)) {
                    View view4 = view3;
                    while (view3 != null) {
                        View view5 = (View) this.headsOfChains.get(view3);
                        if (view5 != null) {
                            if (view5 == view4) {
                                break;
                            }
                            view3 = view4;
                            view4 = view5;
                        }
                        this.headsOfChains.set(view3, view4);
                        view3 = (View) this.nextFoci.get(view3);
                    }
                }
                if (i3 < 0) {
                    return;
                } else {
                    size3 = i3;
                }
            }
        }
    }

    public static void setFocusBottomRight(ViewGroup viewGroup, Rect rect) {
        int height = viewGroup.getHeight() + viewGroup.getScrollY();
        int width = viewGroup.getWidth() + viewGroup.getScrollX();
        rect.set(width, height, width, height);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final View findNextFocus(int i, View view, ViewGroup viewGroup) {
        ViewGroup viewGroup2;
        View view2 = null;
        if (view != null && view != viewGroup) {
            ViewParent parent = view.getParent();
            ViewGroup viewGroup3 = null;
            while (true) {
                if (!(parent instanceof ViewGroup)) {
                    break;
                }
                if (parent != viewGroup) {
                    ViewGroup viewGroup4 = (ViewGroup) parent;
                    if (viewGroup4.getTouchscreenBlocksFocus() && view.getContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen")) {
                        viewGroup3 = viewGroup4;
                    }
                    parent = viewGroup4.getParent();
                } else if (viewGroup3 != null) {
                    viewGroup2 = viewGroup3;
                }
            }
        }
        viewGroup2 = viewGroup;
        View access$findUserSetNextFocus = FocusFinderCompat_androidKt.access$findUserSetNextFocus(view, viewGroup2, i);
        int i2 = true;
        View view3 = access$findUserSetNextFocus;
        while (access$findUserSetNextFocus != null) {
            if (access$findUserSetNextFocus.isFocusable() && access$findUserSetNextFocus.getVisibility() == 0 && (!access$findUserSetNextFocus.isInTouchMode() || access$findUserSetNextFocus.isFocusableInTouchMode())) {
                view2 = access$findUserSetNextFocus;
                break;
            }
            access$findUserSetNextFocus = FocusFinderCompat_androidKt.access$findUserSetNextFocus(access$findUserSetNextFocus, viewGroup2, i);
            int i3 = i2 ^ true;
            if (i2 == false) {
                view3 = view3 != null ? FocusFinderCompat_androidKt.access$findUserSetNextFocus(view3, viewGroup2, i) : null;
                if (view3 == access$findUserSetNextFocus) {
                    break;
                }
            }
            i2 = i3;
        }
        if (view2 != null) {
            return view2;
        }
        ArrayList<View> arrayList = this.tmpList;
        try {
            arrayList.clear();
            viewGroup2.addFocusables(arrayList, i, viewGroup2.isInTouchMode() ? 1 : 0);
            if (!arrayList.isEmpty()) {
                view2 = findNextFocus(i, null, view, viewGroup2, arrayList);
            }
            arrayList.clear();
            return view2;
        } catch (Throwable th) {
            arrayList.clear();
            throw th;
        }
    }

    public final View findNextFocusInAbsoluteDirection(int i, Rect rect, View view, ViewGroup viewGroup, ArrayList arrayList) {
        int i2;
        this.bestCandidateRect.set(rect);
        if (i == 17) {
            this.bestCandidateRect.offset(rect.width() + 1, 0);
        } else if (i == 33) {
            this.bestCandidateRect.offset(0, rect.height() + 1);
        } else if (i == 66) {
            this.bestCandidateRect.offset((-rect.width()) - 1, 0);
        } else if (i == 130) {
            this.bestCandidateRect.offset(0, (-rect.height()) - 1);
        }
        int size = arrayList.size();
        View view2 = null;
        for (int i3 = 0; i3 < size; i3++) {
            View view3 = (View) arrayList.get(i3);
            if (!Intrinsics.areEqual(view3, view) && !Intrinsics.areEqual(view3, viewGroup)) {
                view3.getFocusedRect(this.otherRect);
                viewGroup.offsetDescendantRectToMyCoords(view3, this.otherRect);
                androidx.compose.ui.geometry.Rect composeRect = RectHelper_androidKt.toComposeRect(this.otherRect);
                androidx.compose.ui.geometry.Rect composeRect2 = RectHelper_androidKt.toComposeRect(this.bestCandidateRect);
                androidx.compose.ui.geometry.Rect composeRect3 = RectHelper_androidKt.toComposeRect(rect);
                FocusDirection focusDirection = FocusInteropUtils_androidKt.toFocusDirection(i);
                if (focusDirection != null) {
                    i2 = focusDirection.value;
                } else {
                    FocusDirection.Companion.getClass();
                    i2 = FocusDirection.Next;
                }
                if (TwoDimensionalFocusSearchKt.m388isBetterCandidateI7lrPNg(composeRect, composeRect2, composeRect3, i2)) {
                    this.bestCandidateRect.set(this.otherRect);
                    view2 = view3;
                }
            }
        }
        return view2;
    }

    public final View findNextFocus(int i, Rect rect, View view, ViewGroup viewGroup, ArrayList arrayList) {
        int indexOf;
        int lastIndexOf;
        int i2;
        Rect rect2 = this.cachedFocusedRect;
        if (view != null) {
            view.getFocusedRect(rect2);
            viewGroup.offsetDescendantRectToMyCoords(view, rect2);
        } else if (rect != null) {
            rect2.set(rect);
        } else if (i != 1) {
            if (i != 2) {
                if (i == 17 || i == 33) {
                    setFocusBottomRight(viewGroup, rect2);
                } else if (i == 66 || i == 130) {
                    int scrollY = viewGroup.getScrollY();
                    int scrollX = viewGroup.getScrollX();
                    rect2.set(scrollX, scrollY, scrollX, scrollY);
                }
            } else if (viewGroup.getLayoutDirection() == 1) {
                setFocusBottomRight(viewGroup, rect2);
            } else {
                int scrollY2 = viewGroup.getScrollY();
                int scrollX2 = viewGroup.getScrollX();
                rect2.set(scrollX2, scrollY2, scrollX2, scrollY2);
            }
        } else if (viewGroup.getLayoutDirection() == 1) {
            int scrollY3 = viewGroup.getScrollY();
            int scrollX3 = viewGroup.getScrollX();
            rect2.set(scrollX3, scrollY3, scrollX3, scrollY3);
        } else {
            setFocusBottomRight(viewGroup, rect2);
        }
        if (i != 1 && i != 2) {
            if (i != 17 && i != 33 && i != 66 && i != 130) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown direction: "));
            }
            return findNextFocusInAbsoluteDirection(i, rect2, view, viewGroup, arrayList);
        }
        UserSpecifiedFocusComparator userSpecifiedFocusComparator = this.userSpecifiedFocusComparator;
        try {
            userSpecifiedFocusComparator.setFocusables(arrayList, viewGroup);
            Collections.sort(arrayList, userSpecifiedFocusComparator);
            userSpecifiedFocusComparator.headsOfChains.clear();
            userSpecifiedFocusComparator.isConnectedTo.clear();
            userSpecifiedFocusComparator.originalOrdinal.clear();
            userSpecifiedFocusComparator.nextFoci.clear();
            int size = arrayList.size();
            View view2 = null;
            if (size < 2) {
                return null;
            }
            if (i != 1) {
                if (i != 2) {
                    if (i == 17 || i == 33 || i == 66 || i == 130) {
                        view2 = findNextFocusInAbsoluteDirection(i, this.cachedFocusedRect, view, viewGroup, arrayList);
                    }
                } else if (size >= 2) {
                    if (view != null && (lastIndexOf = arrayList.lastIndexOf(view)) >= 0 && (i2 = lastIndexOf + 1) < size) {
                        view2 = (View) arrayList.get(i2);
                    } else {
                        view2 = (View) arrayList.get(0);
                    }
                }
            } else if (size >= 2) {
                if (view != null && (indexOf = arrayList.indexOf(view)) > 0) {
                    view2 = (View) arrayList.get(indexOf - 1);
                } else {
                    view2 = (View) arrayList.get(size - 1);
                }
            }
            return view2 == null ? (View) arrayList.get(size - 1) : view2;
        } catch (Throwable th) {
            userSpecifiedFocusComparator.headsOfChains.clear();
            userSpecifiedFocusComparator.isConnectedTo.clear();
            userSpecifiedFocusComparator.originalOrdinal.clear();
            userSpecifiedFocusComparator.nextFoci.clear();
            throw th;
        }
    }
}
