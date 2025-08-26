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
import com.android.systemui.navigationbar.store.NavBarStoreAction;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.shared.navigationbar.KeyButtonRipple;
import com.android.systemui.util.SettingsHelper;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final class NavBarRemoteViewManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int adaptivePosition;
    public final NavBarRemoteViewManager$comparator$1 comparator;
    public final Context context;
    public float darkIntensity;
    public LinearLayout leftContainer;
    public final PriorityQueue leftViewList;
    public NavBarStoreImpl navBarStore;
    public LinearLayout rightContainer;
    public final PriorityQueue rightViewList;
    private final SettingsHelper settingsHelper;
    public boolean showInGestureMode;
    public boolean useAltBack;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        this.comparator = navBarRemoteViewManager$comparator$1;
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
        PriorityQueue priorityQueue;
        if (i != 0) {
            if (i == 1 && ((priorityQueue = this.rightViewList) == null || !priorityQueue.isEmpty())) {
                Iterator it = priorityQueue.iterator();
                while (it.hasNext()) {
                    if (StringsKt__StringsJVMKt.equals(((NavBarRemoteView) it.next()).requestClass, str, false)) {
                        return true;
                    }
                }
            }
            return false;
        }
        PriorityQueue priorityQueue2 = this.leftViewList;
        if (priorityQueue2 == null || !priorityQueue2.isEmpty()) {
            Iterator it2 = priorityQueue2.iterator();
            while (it2.hasNext()) {
                if (StringsKt__StringsJVMKt.equals(((NavBarRemoteView) it2.next()).requestClass, str, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isSetMultimodalButton() {
        boolean zBooleanValue;
        boolean zBooleanValue2;
        NavBarRemoteView navBarRemoteView = (NavBarRemoteView) this.leftViewList.peek();
        if (navBarRemoteView == null) {
            zBooleanValue = false;
        } else {
            String str = navBarRemoteView.requestClass;
            Boolean boolValueOf = str != null ? Boolean.valueOf(StringsKt__StringsKt.contains(str, "honeyboard", false)) : null;
            if (boolValueOf != null) {
                zBooleanValue = boolValueOf.booleanValue();
            }
        }
        if (zBooleanValue) {
            return true;
        }
        NavBarRemoteView navBarRemoteView2 = (NavBarRemoteView) this.rightViewList.peek();
        if (navBarRemoteView2 == null) {
            zBooleanValue2 = false;
        } else {
            String str2 = navBarRemoteView2.requestClass;
            Boolean boolValueOf2 = str2 != null ? Boolean.valueOf(StringsKt__StringsKt.contains(str2, "honeyboard", false)) : null;
            if (boolValueOf2 != null) {
                zBooleanValue2 = boolValueOf2.booleanValue();
            }
        }
        return zBooleanValue2;
    }

    public final void removeRemoteView(int i, String str) {
        Object next;
        Object obj = null;
        if (str != null && StringsKt__StringsKt.contains(str, "honeyboard", false)) {
            PriorityQueue priorityQueue = this.leftViewList;
            Iterator it = priorityQueue.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (str.equals(((NavBarRemoteView) next).requestClass)) {
                        break;
                    }
                }
            }
            priorityQueue.remove(next);
            PriorityQueue priorityQueue2 = this.rightViewList;
            Iterator it2 = priorityQueue2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next2 = it2.next();
                if (str.equals(((NavBarRemoteView) next2).requestClass)) {
                    obj = next2;
                    break;
                }
            }
            priorityQueue2.remove(obj);
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
                Object next3 = it3.next();
                if (Intrinsics.areEqual(str, ((NavBarRemoteView) next3).requestClass)) {
                    obj = next3;
                    break;
                }
            }
            priorityQueue3.remove(obj);
            return;
        }
        if (i == 1) {
            PriorityQueue priorityQueue4 = this.rightViewList;
            Iterator it4 = priorityQueue4.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    break;
                }
                Object next4 = it4.next();
                if (Intrinsics.areEqual(str, ((NavBarRemoteView) next4).requestClass)) {
                    obj = next4;
                    break;
                }
            }
            priorityQueue4.remove(obj);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setRemoteView(NavBarStoreAction.RemoteViewShortcut remoteViewShortcut, int i) {
        RemoteViews remoteViews = remoteViewShortcut.remoteViews;
        int i2 = remoteViewShortcut.position;
        String str = remoteViewShortcut.requestClass;
        if (remoteViews == null) {
            removeRemoteView(i2, str);
            return;
        }
        NavBarRemoteView navBarRemoteView = new NavBarRemoteView(this.context, str, remoteViews, remoteViewShortcut.priority);
        NavBarStoreImpl navBarStoreImpl = this.navBarStore;
        if (navBarStoreImpl == null) {
            navBarStoreImpl = null;
        }
        NavBarStateManager navStateManager = navBarStoreImpl.getNavStateManager(i);
        String str2 = navBarRemoteView.requestClass;
        removeRemoteView(i2, str2);
        if (str2 != null && StringsKt__StringsKt.contains(str2, "honeyboard", false)) {
            if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && i == 1) {
                if (!this.settingsHelper.isNavBarButtonOrderDefault()) {
                    NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navStateManager;
                    i2 = ((navBarStateManagerImpl.states.rotation != 0 || NavBarStateManager.isSideAndBottomGestureMode$default(navStateManager)) && (navBarStateManagerImpl.states.rotation == 0 || navBarStateManagerImpl.isGestureMode())) ? 0 : 1;
                }
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
        NavBarStoreImpl navBarStoreImpl = this.navBarStore;
        if (navBarStoreImpl == null) {
            navBarStoreImpl = null;
        }
        if (!((NavBarStateManagerImpl) navBarStoreImpl.getNavStateManager(i3)).states.canMove || i == 0 || i == 2) {
            view.setPadding(0, i2, 0, i2);
        } else if (i == 1 || i == 3) {
            view.setPadding(i2, 0, i2, 0);
        }
    }

    public final void updateRemoteViewContainer(int i, LinearLayout linearLayout, LinearLayout linearLayout2, int i2) {
        NavBarStoreImpl navBarStoreImpl = this.navBarStore;
        if (navBarStoreImpl == null) {
            navBarStoreImpl = null;
        }
        NavBarStateManager navStateManager = navBarStoreImpl.getNavStateManager(i2);
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
        if (remoteView != null && (!navBarStateManagerImpl.isGestureMode() || (this.adaptivePosition == 0 && isSetMultimodalButton()))) {
            setRemoteViewPadding(remoteView.view, i, dimensionPixelOffset, i2);
            LinearLayout linearLayout3 = this.leftContainer;
            linearLayout3.getClass();
            ViewGroup viewGroup = (ViewGroup) remoteView.view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(remoteView.view);
            }
            linearLayout3.setVisibility(0);
            linearLayout3.removeAllViews();
            linearLayout3.addView(remoteView.view);
        } else if (!this.useAltBack) {
            LinearLayout linearLayout4 = this.leftContainer;
            if (linearLayout4 != null) {
                linearLayout4.removeAllViews();
            }
            LinearLayout linearLayout5 = this.leftContainer;
            if (linearLayout5 != null) {
                linearLayout5.setVisibility(4);
            }
        }
        if (remoteView2 == null || (navBarStateManagerImpl.isGestureMode() && !(this.adaptivePosition == 1 && isSetMultimodalButton()))) {
            if (this.useAltBack) {
                return;
            }
            LinearLayout linearLayout6 = this.rightContainer;
            if (linearLayout6 != null) {
                linearLayout6.removeAllViews();
            }
            LinearLayout linearLayout7 = this.rightContainer;
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(4);
                return;
            }
            return;
        }
        setRemoteViewPadding(remoteView2.view, i, dimensionPixelOffset, i2);
        LinearLayout linearLayout8 = this.rightContainer;
        linearLayout8.getClass();
        ViewGroup viewGroup2 = (ViewGroup) remoteView2.view.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeView(remoteView2.view);
        }
        linearLayout8.setVisibility(0);
        linearLayout8.removeAllViews();
        linearLayout8.addView(remoteView2.view);
    }
}
