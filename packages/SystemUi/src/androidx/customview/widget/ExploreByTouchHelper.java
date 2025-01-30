package androidx.customview.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.customview.widget.FocusStrategy;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    public static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, VideoPlayer.MEDIA_ERROR_SYSTEM, VideoPlayer.MEDIA_ERROR_SYSTEM);
    public static final C01731 NODE_ADAPTER = new FocusStrategy.BoundsAdapter() { // from class: androidx.customview.widget.ExploreByTouchHelper.1
    };
    public static final C01742 SPARSE_VALUES_ADAPTER = new Object() { // from class: androidx.customview.widget.ExploreByTouchHelper.2
    };
    public final View mHost;
    public final AccessibilityManager mManager;
    public MyNodeProvider mNodeProvider;
    public final Rect mTempScreenRect = new Rect();
    public final Rect mTempParentRect = new Rect();
    public final Rect mTempVisibleRect = new Rect();
    public final int[] mTempGlobalRect = new int[2];
    public int mAccessibilityFocusedVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;
    public int mKeyboardFocusedVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;
    public int mHoveredVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyNodeProvider extends AccessibilityNodeProviderCompat {
        public MyNodeProvider() {
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public final AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(i));
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public final AccessibilityNodeInfoCompat findFocus(int i) {
            ExploreByTouchHelper exploreByTouchHelper = ExploreByTouchHelper.this;
            int i2 = i == 2 ? exploreByTouchHelper.mAccessibilityFocusedVirtualViewId : exploreByTouchHelper.mKeyboardFocusedVirtualViewId;
            if (i2 == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(i2);
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public final boolean performAction(int i, int i2, Bundle bundle) {
            int i3;
            ExploreByTouchHelper exploreByTouchHelper = ExploreByTouchHelper.this;
            View view = exploreByTouchHelper.mHost;
            if (i == -1) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return ViewCompat.Api16Impl.performAccessibilityAction(view, i2, bundle);
            }
            boolean z = true;
            if (i2 == 1) {
                return exploreByTouchHelper.requestKeyboardFocusForVirtualView(i);
            }
            if (i2 == 2) {
                return exploreByTouchHelper.clearKeyboardFocusForVirtualView(i);
            }
            if (i2 == 64) {
                AccessibilityManager accessibilityManager = exploreByTouchHelper.mManager;
                if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled() && (i3 = exploreByTouchHelper.mAccessibilityFocusedVirtualViewId) != i) {
                    if (i3 != Integer.MIN_VALUE) {
                        exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;
                        exploreByTouchHelper.mHost.invalidate();
                        exploreByTouchHelper.sendEventForVirtualView(i3, 65536);
                    }
                    exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = i;
                    view.invalidate();
                    exploreByTouchHelper.sendEventForVirtualView(i, 32768);
                }
                z = false;
            } else {
                if (i2 != 128) {
                    return exploreByTouchHelper.onPerformActionForVirtualView(i, i2, bundle);
                }
                if (exploreByTouchHelper.mAccessibilityFocusedVirtualViewId == i) {
                    exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;
                    view.invalidate();
                    exploreByTouchHelper.sendEventForVirtualView(i, 65536);
                }
                z = false;
            }
            return z;
        }
    }

    public ExploreByTouchHelper(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.mHost = view;
        this.mManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        view.setFocusable(true);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api16Impl.getImportantForAccessibility(view) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
        }
    }

    public final boolean clearKeyboardFocusForVirtualView(int i) {
        if (this.mKeyboardFocusedVirtualViewId != i) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;
        onVirtualViewKeyboardFocusChanged(i, false);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final AccessibilityEvent createEvent(int i, int i2) {
        View view = this.mHost;
        if (i == -1) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
            view.onInitializeAccessibilityEvent(obtain);
            return obtain;
        }
        AccessibilityEvent obtain2 = AccessibilityEvent.obtain(i2);
        AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(i);
        obtain2.getText().add(obtainAccessibilityNodeInfo.getText());
        AccessibilityNodeInfo accessibilityNodeInfo = obtainAccessibilityNodeInfo.mInfo;
        obtain2.setContentDescription(accessibilityNodeInfo.getContentDescription());
        obtain2.setScrollable(accessibilityNodeInfo.isScrollable());
        obtain2.setPassword(accessibilityNodeInfo.isPassword());
        obtain2.setEnabled(accessibilityNodeInfo.isEnabled());
        obtain2.setChecked(accessibilityNodeInfo.isChecked());
        onPopulateEventForVirtualView(obtain2, i);
        if (obtain2.getText().isEmpty() && obtain2.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        obtain2.setClassName(accessibilityNodeInfo.getClassName());
        obtain2.setSource(view, i);
        obtain2.setPackageName(view.getContext().getPackageName());
        return obtain2;
    }

    public final AccessibilityNodeInfoCompat createNodeForChild(int i) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain();
        AccessibilityNodeInfo accessibilityNodeInfo2 = obtain.mInfo;
        accessibilityNodeInfo2.setEnabled(true);
        accessibilityNodeInfo2.setFocusable(true);
        obtain.setClassName("android.view.View");
        Rect rect = INVALID_PARENT_BOUNDS;
        obtain.setBoundsInParent(rect);
        accessibilityNodeInfo2.setBoundsInScreen(rect);
        obtain.mParentVirtualDescendantId = -1;
        View view = this.mHost;
        accessibilityNodeInfo2.setParent(view);
        onPopulateNodeForVirtualView(i, obtain);
        if (obtain.getText() == null && accessibilityNodeInfo2.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        Rect rect2 = this.mTempParentRect;
        obtain.getBoundsInParent(rect2);
        if (rect2.equals(rect)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = accessibilityNodeInfo2.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfo2.setPackageName(view.getContext().getPackageName());
        obtain.mVirtualDescendantId = i;
        accessibilityNodeInfo2.setSource(view, i);
        boolean z = false;
        if (this.mAccessibilityFocusedVirtualViewId == i) {
            accessibilityNodeInfo2.setAccessibilityFocused(true);
            obtain.addAction(128);
        } else {
            accessibilityNodeInfo2.setAccessibilityFocused(false);
            obtain.addAction(64);
        }
        boolean z2 = this.mKeyboardFocusedVirtualViewId == i;
        if (z2) {
            obtain.addAction(2);
        } else if (accessibilityNodeInfo2.isFocusable()) {
            obtain.addAction(1);
        }
        accessibilityNodeInfo2.setFocused(z2);
        int[] iArr = this.mTempGlobalRect;
        view.getLocationOnScreen(iArr);
        Rect rect3 = this.mTempScreenRect;
        accessibilityNodeInfo2.getBoundsInScreen(rect3);
        if (rect3.equals(rect)) {
            obtain.getBoundsInParent(rect3);
            if (obtain.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat obtain2 = AccessibilityNodeInfoCompat.obtain();
                int i2 = obtain.mParentVirtualDescendantId;
                while (true) {
                    accessibilityNodeInfo = obtain2.mInfo;
                    if (i2 == -1) {
                        break;
                    }
                    obtain2.mParentVirtualDescendantId = -1;
                    accessibilityNodeInfo.setParent(view, -1);
                    obtain2.setBoundsInParent(rect);
                    onPopulateNodeForVirtualView(i2, obtain2);
                    obtain2.getBoundsInParent(rect2);
                    rect3.offset(rect2.left, rect2.top);
                    i2 = obtain2.mParentVirtualDescendantId;
                }
                accessibilityNodeInfo.recycle();
            }
            rect3.offset(iArr[0] - view.getScrollX(), iArr[1] - view.getScrollY());
        }
        Rect rect4 = this.mTempVisibleRect;
        if (view.getLocalVisibleRect(rect4)) {
            rect4.offset(iArr[0] - view.getScrollX(), iArr[1] - view.getScrollY());
            if (rect3.intersect(rect4)) {
                accessibilityNodeInfo2.setBoundsInScreen(rect3);
                if (!rect3.isEmpty() && view.getWindowVisibility() == 0) {
                    Object parent = view.getParent();
                    while (true) {
                        if (parent instanceof View) {
                            View view2 = (View) parent;
                            if (view2.getAlpha() <= 0.0f || view2.getVisibility() != 0) {
                                break;
                            }
                            parent = view2.getParent();
                        } else if (parent != null) {
                            z = true;
                        }
                    }
                }
                if (z) {
                    accessibilityNodeInfo2.setVisibleToUser(true);
                }
            }
        }
        return obtain;
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        AccessibilityManager accessibilityManager = this.mManager;
        if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
            int i2 = this.mHoveredVirtualViewId;
            if (i2 != virtualViewAt) {
                this.mHoveredVirtualViewId = virtualViewAt;
                sendEventForVirtualView(virtualViewAt, 128);
                sendEventForVirtualView(i2, 256);
            }
            return virtualViewAt != Integer.MIN_VALUE;
        }
        if (action != 10 || (i = this.mHoveredVirtualViewId) == Integer.MIN_VALUE) {
            return false;
        }
        if (i != Integer.MIN_VALUE) {
            this.mHoveredVirtualViewId = VideoPlayer.MEDIA_ERROR_SYSTEM;
            sendEventForVirtualView(VideoPlayer.MEDIA_ERROR_SYSTEM, 128);
            sendEventForVirtualView(i, 256);
        }
        return true;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider();
        }
        return this.mNodeProvider;
    }

    public abstract int getVirtualViewAt(float f, float f2);

    public abstract void getVisibleVirtualViews(List list);

    public final void invalidateVirtualView(int i) {
        View view;
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = (view = this.mHost).getParent()) == null) {
            return;
        }
        AccessibilityEvent createEvent = createEvent(i, 2048);
        createEvent.setContentChangeTypes(0);
        parent.requestSendAccessibilityEvent(view, createEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0146, code lost:
    
        if (r14 < ((r15 * r15) + ((r13 * 13) * r13))) goto L68;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0152 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00e5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean moveFocus(int i, Rect rect) {
        int i2;
        int i3;
        Object obj;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat;
        int i4;
        int i5;
        int size;
        int i6;
        int i7;
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
        for (int i8 = 0; i8 < arrayList.size(); i8++) {
            sparseArrayCompat.put(((Integer) arrayList.get(i8)).intValue(), createNodeForChild(((Integer) arrayList.get(i8)).intValue()));
        }
        int i9 = this.mKeyboardFocusedVirtualViewId;
        int i10 = VideoPlayer.MEDIA_ERROR_SYSTEM;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = i9 == Integer.MIN_VALUE ? null : (AccessibilityNodeInfoCompat) sparseArrayCompat.get(i9);
        C01731 c01731 = NODE_ADAPTER;
        C01742 c01742 = SPARSE_VALUES_ADAPTER;
        View view = this.mHost;
        if (i == 1 || i == 2) {
            i2 = 0;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z = ViewCompat.Api17Impl.getLayoutDirection(view) == 1;
            c01742.getClass();
            int size2 = sparseArrayCompat.size();
            ArrayList arrayList2 = new ArrayList(size2);
            for (int i11 = 0; i11 < size2; i11++) {
                arrayList2.add((AccessibilityNodeInfoCompat) sparseArrayCompat.valueAt(i11));
            }
            Collections.sort(arrayList2, new FocusStrategy.SequentialComparator(z, c01731));
            if (i == 1) {
                int size3 = arrayList2.size();
                if (accessibilityNodeInfoCompat2 != null) {
                    size3 = arrayList2.indexOf(accessibilityNodeInfoCompat2);
                }
                i3 = -1;
                int i12 = size3 - 1;
                obj = i12 >= 0 ? arrayList2.get(i12) : null;
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
                }
                int size4 = arrayList2.size();
                int lastIndexOf = (accessibilityNodeInfoCompat2 == null ? -1 : arrayList2.lastIndexOf(accessibilityNodeInfoCompat2)) + 1;
                i3 = -1;
                obj = lastIndexOf < size4 ? arrayList2.get(lastIndexOf) : null;
            }
            accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
        } else {
            if (i != 17 && i != 33 && i != 66 && i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            Rect rect2 = new Rect();
            int i13 = this.mKeyboardFocusedVirtualViewId;
            if (i13 != Integer.MIN_VALUE) {
                obtainAccessibilityNodeInfo(i13).getBoundsInParent(rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                int width = view.getWidth();
                int height = view.getHeight();
                if (i == 17) {
                    i5 = 0;
                    rect2.set(width, 0, width, height);
                } else if (i == 33) {
                    i5 = 0;
                    rect2.set(0, height, width, height);
                } else if (i == 66) {
                    rect2.set(-1, 0, -1, height);
                    i5 = 0;
                } else {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    rect2.set(0, -1, width, -1);
                    i5 = 0;
                }
                Rect rect3 = new Rect(rect2);
                if (i != 17) {
                    rect3.offset(rect2.width() + 1, i5);
                } else if (i == 33) {
                    rect3.offset(i5, rect2.height() + 1);
                } else if (i == 66) {
                    rect3.offset(-(rect2.width() + 1), i5);
                } else {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    rect3.offset(i5, -(rect2.height() + 1));
                }
                c01742.getClass();
                size = sparseArrayCompat.size();
                Rect rect4 = new Rect();
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat3 = null;
                for (i6 = i5; i6 < size; i6++) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat4 = (AccessibilityNodeInfoCompat) sparseArrayCompat.valueAt(i6);
                    if (accessibilityNodeInfoCompat4 != accessibilityNodeInfoCompat2) {
                        c01731.getClass();
                        accessibilityNodeInfoCompat4.getBoundsInParent(rect4);
                        if (FocusStrategy.isCandidate(i, rect2, rect4)) {
                            if (FocusStrategy.isCandidate(i, rect2, rect3) && !FocusStrategy.beamBeats(rect2, rect4, rect3, i)) {
                                if (!FocusStrategy.beamBeats(rect2, rect3, rect4, i)) {
                                    int majorAxisDistance = FocusStrategy.majorAxisDistance(i, rect2, rect4);
                                    int minorAxisDistance = FocusStrategy.minorAxisDistance(i, rect2, rect4);
                                    int i14 = (minorAxisDistance * minorAxisDistance) + (majorAxisDistance * 13 * majorAxisDistance);
                                    int majorAxisDistance2 = FocusStrategy.majorAxisDistance(i, rect2, rect3);
                                    int minorAxisDistance2 = FocusStrategy.minorAxisDistance(i, rect2, rect3);
                                }
                            }
                            i7 = 1;
                            if (i7 == 0) {
                                rect3.set(rect4);
                                accessibilityNodeInfoCompat3 = accessibilityNodeInfoCompat4;
                            }
                        }
                        i7 = i5;
                        if (i7 == 0) {
                        }
                    }
                }
                i2 = i5;
                accessibilityNodeInfoCompat = accessibilityNodeInfoCompat3;
                i3 = -1;
            }
            i5 = 0;
            Rect rect32 = new Rect(rect2);
            if (i != 17) {
            }
            c01742.getClass();
            size = sparseArrayCompat.size();
            Rect rect42 = new Rect();
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat32 = null;
            while (i6 < size) {
            }
            i2 = i5;
            accessibilityNodeInfoCompat = accessibilityNodeInfoCompat32;
            i3 = -1;
        }
        if (accessibilityNodeInfoCompat != null) {
            if (sparseArrayCompat.garbage) {
                SparseArrayCompatKt.access$gc(sparseArrayCompat);
            }
            int i15 = sparseArrayCompat.size;
            while (true) {
                if (i2 >= i15) {
                    i4 = i3;
                    break;
                }
                if (sparseArrayCompat.values[i2] == accessibilityNodeInfoCompat) {
                    i4 = i2;
                    break;
                }
                i2++;
            }
            if (sparseArrayCompat.garbage) {
                SparseArrayCompatKt.access$gc(sparseArrayCompat);
            }
            i10 = sparseArrayCompat.keys[i4];
        }
        return requestKeyboardFocusForVirtualView(i10);
    }

    public final AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int i) {
        if (i != -1) {
            return createNodeForChild(i);
        }
        View view = this.mHost;
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(view);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        AccessibilityNodeInfo accessibilityNodeInfo = obtain.mInfo;
        view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (accessibilityNodeInfo.getChildCount() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            accessibilityNodeInfo.addChild(view, ((Integer) arrayList.get(i2)).intValue());
        }
        return obtain;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
        onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    public abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

    public abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    public final boolean requestKeyboardFocusForVirtualView(int i) {
        int i2;
        View view = this.mHost;
        if ((!view.isFocused() && !view.requestFocus()) || (i2 = this.mKeyboardFocusedVirtualViewId) == i) {
            return false;
        }
        if (i2 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i2);
        }
        if (i == Integer.MIN_VALUE) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = i;
        onVirtualViewKeyboardFocusChanged(i, true);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final void sendEventForVirtualView(int i, int i2) {
        View view;
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = (view = this.mHost).getParent()) == null) {
            return;
        }
        parent.requestSendAccessibilityEvent(view, createEvent(i, i2));
    }

    public void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public void onPopulateEventForVirtualView(AccessibilityEvent accessibilityEvent, int i) {
    }

    public void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
    }
}
