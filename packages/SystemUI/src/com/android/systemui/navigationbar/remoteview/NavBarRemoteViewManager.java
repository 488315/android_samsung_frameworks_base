package com.android.systemui.navigationbar.remoteview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shared.navigationbar.KeyButtonRipple;
import com.android.systemui.util.SettingsHelper;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NavBarRemoteViewManager {
    public int adaptivePosition;
    public final NavBarRemoteViewManager$comparator$1 comparator;
    public final Context context;
    public float darkIntensity;
    public LinearLayout leftContainer;
    public final PriorityQueue leftViewList;
    public NavBarStore navBarStore;
    public LinearLayout rightContainer;
    public final PriorityQueue rightViewList;
    private final SettingsHelper settingsHelper;
    public boolean showInGestureMode;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NavBarRemoteViewManager(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
        NavBarRemoteViewManager$comparator$1 navBarRemoteViewManager$comparator$1 = new Comparator() { // from class: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager$comparator$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int i = ((NavBarRemoteView) obj).priority;
                int i2 = ((NavBarRemoteView) obj2).priority;
                if (i < i2) {
                    return 1;
                }
                return i > i2 ? -1 : 0;
            }
        };
        PriorityQueue priorityQueue = new PriorityQueue(navBarRemoteViewManager$comparator$1);
        this.leftViewList = priorityQueue;
        PriorityQueue priorityQueue2 = new PriorityQueue(navBarRemoteViewManager$comparator$1);
        this.rightViewList = priorityQueue2;
        priorityQueue.clear();
        priorityQueue2.clear();
    }

    public final void applyTint(View view) {
        boolean z;
        boolean z2 = true;
        if (view instanceof ImageView) {
            ((ImageView) view).setColorFilter(((Integer) ArgbEvaluator.sInstance.evaluate(this.darkIntensity, Integer.valueOf(this.context.getColor(R.color.navbar_remote_icon_color_light)), Integer.valueOf(this.context.getColor(R.color.navbar_remote_icon_color_dark)))).intValue());
            z = true;
        } else {
            z = false;
        }
        if (view.getBackground() instanceof KeyButtonRipple) {
            KeyButtonRipple keyButtonRipple = (KeyButtonRipple) view.getBackground();
            if (keyButtonRipple != null) {
                keyButtonRipple.setDarkIntensity(this.darkIntensity);
            }
        } else {
            z2 = z;
        }
        if (z2) {
            view.invalidate();
        }
    }

    public final NavBarRemoteView getRemoteView(int i) {
        if (i == 0) {
            return (NavBarRemoteView) this.leftViewList.peek();
        }
        if (i != 1) {
            return null;
        }
        return (NavBarRemoteView) this.rightViewList.peek();
    }

    public final boolean isExist(int i, String str) {
        if (i == 0) {
            PriorityQueue priorityQueue = this.leftViewList;
            if ((priorityQueue instanceof Collection) && priorityQueue.isEmpty()) {
                return false;
            }
            Iterator it = priorityQueue.iterator();
            while (it.hasNext()) {
                if (StringsKt__StringsJVMKt.equals(((NavBarRemoteView) it.next()).requestClass, str, false)) {
                    return true;
                }
            }
            return false;
        }
        if (i != 1) {
            return false;
        }
        PriorityQueue priorityQueue2 = this.rightViewList;
        if ((priorityQueue2 instanceof Collection) && priorityQueue2.isEmpty()) {
            return false;
        }
        Iterator it2 = priorityQueue2.iterator();
        while (it2.hasNext()) {
            if (StringsKt__StringsJVMKt.equals(((NavBarRemoteView) it2.next()).requestClass, str, false)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSetMultimodalButton() {
        /*
            r4 = this;
            java.util.PriorityQueue r0 = r4.leftViewList
            java.lang.Object r0 = r0.peek()
            com.android.systemui.navigationbar.remoteview.NavBarRemoteView r0 = (com.android.systemui.navigationbar.remoteview.NavBarRemoteView) r0
            java.lang.String r1 = "honeyboard"
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L23
            java.lang.String r0 = r0.requestClass
            if (r0 == 0) goto L1b
            boolean r0 = kotlin.text.StringsKt__StringsKt.contains(r0, r1, r3)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            goto L1c
        L1b:
            r0 = r2
        L1c:
            if (r0 == 0) goto L23
            boolean r0 = r0.booleanValue()
            goto L24
        L23:
            r0 = r3
        L24:
            if (r0 != 0) goto L46
            java.util.PriorityQueue r4 = r4.rightViewList
            java.lang.Object r4 = r4.peek()
            com.android.systemui.navigationbar.remoteview.NavBarRemoteView r4 = (com.android.systemui.navigationbar.remoteview.NavBarRemoteView) r4
            if (r4 == 0) goto L43
            java.lang.String r4 = r4.requestClass
            if (r4 == 0) goto L3c
            boolean r4 = kotlin.text.StringsKt__StringsKt.contains(r4, r1, r3)
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r4)
        L3c:
            if (r2 == 0) goto L43
            boolean r4 = r2.booleanValue()
            goto L44
        L43:
            r4 = r3
        L44:
            if (r4 == 0) goto L47
        L46:
            r3 = 1
        L47:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.remoteview.NavBarRemoteViewManager.isSetMultimodalButton():boolean");
    }

    public final void removeRemoteView(int i, String str) {
        Object obj;
        Object obj2 = null;
        if (str != null && StringsKt__StringsKt.contains(str, "honeyboard", false)) {
            PriorityQueue priorityQueue = this.leftViewList;
            Iterator it = priorityQueue.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it.next();
                    if (str.equals(((NavBarRemoteView) obj).requestClass)) {
                        break;
                    }
                }
            }
            priorityQueue.remove(obj);
            PriorityQueue priorityQueue2 = this.rightViewList;
            Iterator it2 = priorityQueue2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next = it2.next();
                if (str.equals(((NavBarRemoteView) next).requestClass)) {
                    obj2 = next;
                    break;
                }
            }
            priorityQueue2.remove(obj2);
            this.showInGestureMode = false;
            return;
        }
        if (i == 0) {
            PriorityQueue priorityQueue3 = this.leftViewList;
            Iterator it3 = priorityQueue3.iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                Object next2 = it3.next();
                if (Intrinsics.areEqual(str, ((NavBarRemoteView) next2).requestClass)) {
                    obj2 = next2;
                    break;
                }
            }
            priorityQueue3.remove(obj2);
            return;
        }
        if (i == 1) {
            PriorityQueue priorityQueue4 = this.rightViewList;
            Iterator it4 = priorityQueue4.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    break;
                }
                Object next3 = it4.next();
                if (Intrinsics.areEqual(str, ((NavBarRemoteView) next3).requestClass)) {
                    obj2 = next3;
                    break;
                }
            }
            priorityQueue4.remove(obj2);
        }
    }

    public final void setRemoteView(NavBarStoreAction.RemoteViewShortcut remoteViewShortcut, int i) {
        RemoteViews remoteViews = remoteViewShortcut.remoteViews;
        int i2 = remoteViewShortcut.position;
        String str = remoteViewShortcut.requestClass;
        if (remoteViews == null) {
            removeRemoteView(i2, str);
            return;
        }
        NavBarRemoteView navBarRemoteView = new NavBarRemoteView(this.context, str, remoteViews, remoteViewShortcut.priority);
        NavBarStore navBarStore = this.navBarStore;
        if (navBarStore == null) {
            navBarStore = null;
        }
        NavBarStateManager navStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(i);
        String str2 = navBarRemoteView.requestClass;
        removeRemoteView(i2, str2);
        if (str2 != null && StringsKt__StringsKt.contains(str2, "honeyboard", false)) {
            if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && i == 1) {
                if (!this.settingsHelper.isNavBarButtonOrderDefault()) {
                    NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navStateManager;
                    if ((navBarStateManagerImpl.states.rotation == 0 && !NavBarStateManager.isSideAndBottomGestureMode$default(navStateManager)) || (navBarStateManagerImpl.states.rotation != 0 && !navBarStateManagerImpl.isGestureMode())) {
                        i2 = 1;
                    }
                }
                i2 = 0;
            }
            this.showInGestureMode = true;
            this.adaptivePosition = i2;
        }
        if (i2 == 0) {
            this.leftViewList.add(navBarRemoteView);
        } else if (i2 == 1) {
            this.rightViewList.add(navBarRemoteView);
        }
        applyTint(navBarRemoteView.view);
    }

    public final void setRemoteViewPadding(View view, int i, int i2, int i3) {
        NavBarStore navBarStore = this.navBarStore;
        if (navBarStore == null) {
            navBarStore = null;
        }
        if (!((NavBarStateManagerImpl) ((NavBarStoreImpl) navBarStore).getNavStateManager(i3)).states.canMove || i == 0 || i == 2) {
            view.setPadding(0, i2, 0, i2);
        } else if (i == 1 || i == 3) {
            view.setPadding(i2, 0, i2, 0);
        }
    }

    public final void updateRemoteViewContainer(int i, LinearLayout linearLayout, LinearLayout linearLayout2, int i2) {
        NavBarStore navBarStore = this.navBarStore;
        if (navBarStore == null) {
            navBarStore = null;
        }
        NavBarStateManager navStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(i2);
        if (linearLayout == null || linearLayout2 == null) {
            return;
        }
        this.leftContainer = linearLayout;
        this.rightContainer = linearLayout2;
        this.showInGestureMode = isSetMultimodalButton();
        NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navStateManager;
        boolean z = navBarStateManagerImpl.states.canMove && i == 1;
        NavBarRemoteView remoteView = z ? getRemoteView(1) : getRemoteView(0);
        NavBarRemoteView remoteView2 = z ? getRemoteView(0) : getRemoteView(1);
        int dimensionPixelOffset = this.context.getResources().getDimensionPixelOffset((BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && i2 == 1) ? R.dimen.samsung_navigation_bar_remoteview_padding_for_large_cover_screen : R.dimen.samsung_navigation_bar_remoteview_padding);
        if (remoteView == null || (navBarStateManagerImpl.isGestureMode() && !(this.adaptivePosition == 0 && isSetMultimodalButton()))) {
            LinearLayout linearLayout3 = this.leftContainer;
            if (linearLayout3 != null) {
                linearLayout3.removeAllViews();
            }
            LinearLayout linearLayout4 = this.leftContainer;
            if (linearLayout4 != null) {
                linearLayout4.setVisibility(4);
            }
        } else {
            setRemoteViewPadding(remoteView.view, i, dimensionPixelOffset, i2);
            LinearLayout linearLayout5 = this.leftContainer;
            Intrinsics.checkNotNull(linearLayout5);
            ViewGroup viewGroup = (ViewGroup) remoteView.view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(remoteView.view);
            }
            linearLayout5.setVisibility(0);
            linearLayout5.removeAllViews();
            linearLayout5.addView(remoteView.view);
        }
        if (remoteView2 == null || (navBarStateManagerImpl.isGestureMode() && !(this.adaptivePosition == 1 && isSetMultimodalButton()))) {
            LinearLayout linearLayout6 = this.rightContainer;
            if (linearLayout6 != null) {
                linearLayout6.removeAllViews();
            }
            LinearLayout linearLayout7 = this.rightContainer;
            if (linearLayout7 == null) {
                return;
            }
            linearLayout7.setVisibility(4);
            return;
        }
        setRemoteViewPadding(remoteView2.view, i, dimensionPixelOffset, i2);
        LinearLayout linearLayout8 = this.rightContainer;
        Intrinsics.checkNotNull(linearLayout8);
        ViewGroup viewGroup2 = (ViewGroup) remoteView2.view.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(remoteView2.view);
        }
        linearLayout8.setVisibility(0);
        linearLayout8.removeAllViews();
        linearLayout8.addView(remoteView2.view);
    }
}
