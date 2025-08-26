package androidx.customview.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.customview.widget.FocusStrategy;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    public static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final AnonymousClass1 NODE_ADAPTER = new FocusStrategy.BoundsAdapter() { // from class: androidx.customview.widget.ExploreByTouchHelper.1
    };
    public static final AnonymousClass2 SPARSE_VALUES_ADAPTER = new Object() { // from class: androidx.customview.widget.ExploreByTouchHelper.2
    };
    public final View mHost;
    public final AccessibilityManager mManager;
    public MyNodeProvider mNodeProvider;
    public final Rect mTempScreenRect = new Rect();
    public final Rect mTempParentRect = new Rect();
    public final Rect mTempVisibleRect = new Rect();
    public final int[] mTempGlobalRect = new int[2];
    public int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    public int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    public int mHoveredVirtualViewId = Integer.MIN_VALUE;

    public class MyNodeProvider extends AccessibilityNodeProviderCompat {
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
            if (i == -1) {
                View view = exploreByTouchHelper.mHost;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return view.performAccessibilityAction(i2, bundle);
            }
            if (i2 == 1) {
                return exploreByTouchHelper.requestKeyboardFocusForVirtualView(i);
            }
            if (i2 == 2) {
                return exploreByTouchHelper.clearKeyboardFocusForVirtualView(i);
            }
            if (i2 != 64) {
                if (i2 != 128) {
                    return exploreByTouchHelper.onPerformActionForVirtualView(i, i2, bundle);
                }
                if (exploreByTouchHelper.mAccessibilityFocusedVirtualViewId != i) {
                    return false;
                }
                exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
                exploreByTouchHelper.mHost.invalidate();
                exploreByTouchHelper.sendEventForVirtualView(i, 65536);
                return true;
            }
            if (!exploreByTouchHelper.mManager.isEnabled() || !exploreByTouchHelper.mManager.isTouchExplorationEnabled() || (i3 = exploreByTouchHelper.mAccessibilityFocusedVirtualViewId) == i) {
                return false;
            }
            if (i3 != Integer.MIN_VALUE) {
                exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
                exploreByTouchHelper.mHost.invalidate();
                exploreByTouchHelper.sendEventForVirtualView(i3, 65536);
            }
            exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = i;
            exploreByTouchHelper.mHost.invalidate();
            exploreByTouchHelper.sendEventForVirtualView(i, NetworkAnalyticsConstants.DataPoints.FLAG_UID);
            return true;
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
        if (view.getImportantForAccessibility() == 0) {
            view.setImportantForAccessibility(1);
        }
    }

    public final boolean clearKeyboardFocusForVirtualView(int i) {
        if (this.mKeyboardFocusedVirtualViewId != i) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(i, false);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final AccessibilityEvent createEvent$1(int i, int i2) {
        if (i == -1) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
            this.mHost.onInitializeAccessibilityEvent(accessibilityEventObtain);
            return accessibilityEventObtain;
        }
        AccessibilityEvent accessibilityEventObtain2 = AccessibilityEvent.obtain(i2);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(i);
        accessibilityEventObtain2.getText().add(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.getText());
        accessibilityEventObtain2.setContentDescription(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.mInfo.getContentDescription());
        accessibilityEventObtain2.setScrollable(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.mInfo.isScrollable());
        accessibilityEventObtain2.setPassword(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.mInfo.isPassword());
        accessibilityEventObtain2.setEnabled(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.mInfo.isEnabled());
        accessibilityEventObtain2.setChecked(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.mInfo.isChecked());
        onPopulateEventForVirtualView(i, accessibilityEventObtain2);
        if (accessibilityEventObtain2.getText().isEmpty() && accessibilityEventObtain2.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        accessibilityEventObtain2.setClassName(accessibilityNodeInfoCompatObtainAccessibilityNodeInfo.mInfo.getClassName());
        accessibilityEventObtain2.setSource(this.mHost, i);
        accessibilityEventObtain2.setPackageName(this.mHost.getContext().getPackageName());
        return accessibilityEventObtain2;
    }

    public final AccessibilityNodeInfoCompat createNodeForChild(int i) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain();
        accessibilityNodeInfoCompatObtain.mInfo.setEnabled(true);
        accessibilityNodeInfoCompatObtain.mInfo.setFocusable(true);
        accessibilityNodeInfoCompatObtain.setClassName("android.view.View");
        Rect rect = INVALID_PARENT_BOUNDS;
        accessibilityNodeInfoCompatObtain.setBoundsInParent(rect);
        accessibilityNodeInfoCompatObtain.setBoundsInScreen(rect);
        View view = this.mHost;
        accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId = -1;
        accessibilityNodeInfoCompatObtain.mInfo.setParent(view);
        onPopulateNodeForVirtualView(i, accessibilityNodeInfoCompatObtain);
        if (accessibilityNodeInfoCompatObtain.getText() == null && accessibilityNodeInfoCompatObtain.mInfo.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompatObtain.getBoundsInParent(this.mTempParentRect);
        if (this.mTempParentRect.equals(rect)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = accessibilityNodeInfoCompatObtain.mInfo.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        accessibilityNodeInfoCompatObtain.mInfo.setPackageName(this.mHost.getContext().getPackageName());
        View view2 = this.mHost;
        accessibilityNodeInfoCompatObtain.mVirtualDescendantId = i;
        accessibilityNodeInfoCompatObtain.mInfo.setSource(view2, i);
        if (this.mAccessibilityFocusedVirtualViewId == i) {
            accessibilityNodeInfoCompatObtain.mInfo.setAccessibilityFocused(true);
            accessibilityNodeInfoCompatObtain.addAction(128);
        } else {
            accessibilityNodeInfoCompatObtain.mInfo.setAccessibilityFocused(false);
            accessibilityNodeInfoCompatObtain.addAction(64);
        }
        boolean z = this.mKeyboardFocusedVirtualViewId == i;
        if (z) {
            accessibilityNodeInfoCompatObtain.addAction(2);
        } else if (accessibilityNodeInfoCompatObtain.mInfo.isFocusable()) {
            accessibilityNodeInfoCompatObtain.addAction(1);
        }
        accessibilityNodeInfoCompatObtain.mInfo.setFocused(z);
        View view3 = this.mHost;
        int[] iArr = this.mTempGlobalRect;
        view3.getLocationOnScreen(iArr);
        accessibilityNodeInfoCompatObtain.mInfo.getBoundsInScreen(this.mTempScreenRect);
        if (this.mTempScreenRect.equals(rect)) {
            accessibilityNodeInfoCompatObtain.getBoundsInParent(this.mTempScreenRect);
            if (accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId != -1) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain2 = AccessibilityNodeInfoCompat.obtain();
                for (int i2 = accessibilityNodeInfoCompatObtain.mParentVirtualDescendantId; i2 != -1; i2 = accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId) {
                    View view4 = this.mHost;
                    accessibilityNodeInfoCompatObtain2.mParentVirtualDescendantId = -1;
                    accessibilityNodeInfoCompatObtain2.mInfo.setParent(view4, -1);
                    accessibilityNodeInfoCompatObtain2.setBoundsInParent(INVALID_PARENT_BOUNDS);
                    onPopulateNodeForVirtualView(i2, accessibilityNodeInfoCompatObtain2);
                    accessibilityNodeInfoCompatObtain2.getBoundsInParent(this.mTempParentRect);
                    Rect rect2 = this.mTempScreenRect;
                    Rect rect3 = this.mTempParentRect;
                    rect2.offset(rect3.left, rect3.top);
                }
            }
            this.mTempScreenRect.offset(iArr[0] - this.mHost.getScrollX(), iArr[1] - this.mHost.getScrollY());
        }
        if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
            this.mTempVisibleRect.offset(iArr[0] - this.mHost.getScrollX(), iArr[1] - this.mHost.getScrollY());
            if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                accessibilityNodeInfoCompatObtain.setBoundsInScreen(this.mTempScreenRect);
                Rect rect4 = this.mTempScreenRect;
                if (rect4 != null && !rect4.isEmpty() && this.mHost.getWindowVisibility() == 0) {
                    Object parent = this.mHost.getParent();
                    while (true) {
                        if (parent instanceof View) {
                            View view5 = (View) parent;
                            if (view5.getAlpha() <= 0.0f || view5.getVisibility() != 0) {
                                break;
                            }
                            parent = view5.getParent();
                        } else if (parent != null) {
                            accessibilityNodeInfoCompatObtain.mInfo.setVisibleToUser(true);
                        }
                    }
                }
            }
        }
        return accessibilityNodeInfoCompatObtain;
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
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
            if (virtualViewAt == Integer.MIN_VALUE) {
                return false;
            }
        } else {
            if (action != 10 || (i = this.mHoveredVirtualViewId) == Integer.MIN_VALUE) {
                return false;
            }
            if (i != Integer.MIN_VALUE) {
                this.mHoveredVirtualViewId = Integer.MIN_VALUE;
                sendEventForVirtualView(i, 256);
                return true;
            }
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
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return;
        }
        AccessibilityEvent accessibilityEventCreateEvent$1 = createEvent$1(i, 2048);
        accessibilityEventCreateEvent$1.setContentChangeTypes(0);
        parent.requestSendAccessibilityEvent(this.mHost, accessibilityEventCreateEvent$1);
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean moveFocus(int i, Rect rect) {
        int i2;
        Object obj;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat;
        int iKeyAt;
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            sparseArrayCompat.put(((Integer) arrayList.get(i3)).intValue(), createNodeForChild(((Integer) arrayList.get(i3)).intValue()));
        }
        int i4 = this.mKeyboardFocusedVirtualViewId;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = i4 == Integer.MIN_VALUE ? null : (AccessibilityNodeInfoCompat) sparseArrayCompat.get(i4);
        AnonymousClass1 anonymousClass1 = NODE_ADAPTER;
        AnonymousClass2 anonymousClass2 = SPARSE_VALUES_ADAPTER;
        int i5 = -1;
        if (i == 1 || i == 2) {
            i2 = 0;
            View view = this.mHost;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            boolean z = view.getLayoutDirection() == 1;
            anonymousClass2.getClass();
            int size = sparseArrayCompat.size();
            ArrayList arrayList2 = new ArrayList(size);
            for (int i6 = 0; i6 < size; i6++) {
                arrayList2.add((AccessibilityNodeInfoCompat) sparseArrayCompat.valueAt(i6));
            }
            Collections.sort(arrayList2, new FocusStrategy.SequentialComparator(z, anonymousClass1));
            if (i == 1) {
                int size2 = arrayList2.size();
                if (accessibilityNodeInfoCompat2 != null) {
                    size2 = arrayList2.indexOf(accessibilityNodeInfoCompat2);
                }
                int i7 = size2 - 1;
                if (i7 >= 0) {
                    obj = arrayList2.get(i7);
                }
                accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}.");
                }
                int size3 = arrayList2.size();
                int iLastIndexOf = (accessibilityNodeInfoCompat2 == null ? -1 : arrayList2.lastIndexOf(accessibilityNodeInfoCompat2)) + 1;
                obj = iLastIndexOf < size3 ? arrayList2.get(iLastIndexOf) : null;
                accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
            }
        } else {
            if (i != 17 && i != 33 && i != 66 && i != 130) {
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            Rect rect2 = new Rect();
            int i8 = this.mKeyboardFocusedVirtualViewId;
            if (i8 != Integer.MIN_VALUE) {
                obtainAccessibilityNodeInfo(i8).getBoundsInParent(rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                View view2 = this.mHost;
                int width = view2.getWidth();
                int height = view2.getHeight();
                if (i == 17) {
                    rect2.set(width, 0, width, height);
                } else if (i == 33) {
                    rect2.set(0, height, width, height);
                } else if (i == 66) {
                    rect2.set(-1, 0, -1, height);
                } else {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                    rect2.set(0, -1, width, -1);
                }
            }
            Rect rect3 = new Rect(rect2);
            if (i == 17) {
                i2 = 0;
                rect3.offset(rect2.width() + 1, 0);
            } else if (i == 33) {
                i2 = 0;
                rect3.offset(0, rect2.height() + 1);
            } else if (i == 66) {
                i2 = 0;
                rect3.offset(-(rect2.width() + 1), 0);
            } else {
                if (i != 130) {
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                i2 = 0;
                rect3.offset(0, -(rect2.height() + 1));
            }
            anonymousClass2.getClass();
            int size4 = sparseArrayCompat.size();
            Rect rect4 = new Rect();
            accessibilityNodeInfoCompat = null;
            for (int i9 = i2; i9 < size4; i9++) {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompat3 = (AccessibilityNodeInfoCompat) sparseArrayCompat.valueAt(i9);
                if (accessibilityNodeInfoCompat3 != accessibilityNodeInfoCompat2) {
                    anonymousClass1.getClass();
                    accessibilityNodeInfoCompat3.getBoundsInParent(rect4);
                    if (FocusStrategy.isCandidate(i, rect2, rect4)) {
                        if (FocusStrategy.isCandidate(i, rect2, rect3) && !FocusStrategy.beamBeats(rect2, rect4, rect3, i)) {
                            if (!FocusStrategy.beamBeats(rect2, rect3, rect4, i)) {
                                int iMajorAxisDistance = FocusStrategy.majorAxisDistance(i, rect2, rect4);
                                int iMinorAxisDistance = FocusStrategy.minorAxisDistance(i, rect2, rect4);
                                int i10 = (iMinorAxisDistance * iMinorAxisDistance) + (iMajorAxisDistance * 13 * iMajorAxisDistance);
                                int iMajorAxisDistance2 = FocusStrategy.majorAxisDistance(i, rect2, rect3);
                                int iMinorAxisDistance2 = FocusStrategy.minorAxisDistance(i, rect2, rect3);
                                if (i10 < (iMinorAxisDistance2 * iMinorAxisDistance2) + (iMajorAxisDistance2 * 13 * iMajorAxisDistance2)) {
                                    rect3.set(rect4);
                                    accessibilityNodeInfoCompat = accessibilityNodeInfoCompat3;
                                }
                            }
                        }
                    }
                }
            }
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat4 = accessibilityNodeInfoCompat;
        if (accessibilityNodeInfoCompat4 == null) {
            iKeyAt = Integer.MIN_VALUE;
        } else {
            if (sparseArrayCompat.garbage) {
                SparseArrayCompatKt.access$gc(sparseArrayCompat);
            }
            int i11 = sparseArrayCompat.size;
            int i12 = i2;
            while (true) {
                if (i12 >= i11) {
                    break;
                }
                if (sparseArrayCompat.values[i12] == accessibilityNodeInfoCompat4) {
                    i5 = i12;
                    break;
                }
                i12++;
            }
            iKeyAt = sparseArrayCompat.keyAt(i5);
        }
        return requestKeyboardFocusForVirtualView(iKeyAt);
    }

    public final AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int i) {
        if (i != -1) {
            return createNodeForChild(i);
        }
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(this.mHost);
        View view = this.mHost;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        view.onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompatObtain.mInfo);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (accessibilityNodeInfoCompatObtain.mInfo.getChildCount() > 0 && arrayList.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            accessibilityNodeInfoCompatObtain.mInfo.addChild(this.mHost, ((Integer) arrayList.get(i2)).intValue());
        }
        return accessibilityNodeInfoCompatObtain;
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
        if ((!this.mHost.isFocused() && !this.mHost.requestFocus()) || (i2 = this.mKeyboardFocusedVirtualViewId) == i) {
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
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return;
        }
        parent.requestSendAccessibilityEvent(this.mHost, createEvent$1(i, i2));
    }

    public void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
    }

    public void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
    }
}
