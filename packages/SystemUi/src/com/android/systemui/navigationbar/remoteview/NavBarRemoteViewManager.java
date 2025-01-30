package com.android.systemui.navigationbar.remoteview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.navigationbar.buttons.KeyButtonRipple;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.util.SettingsHelper;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final SettingsHelper settingsHelper;
    public boolean showInGestureMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.comparator = navBarRemoteViewManager$comparator$1;
        PriorityQueue priorityQueue = new PriorityQueue(navBarRemoteViewManager$comparator$1);
        this.leftViewList = priorityQueue;
        PriorityQueue priorityQueue2 = new PriorityQueue(navBarRemoteViewManager$comparator$1);
        this.rightViewList = priorityQueue2;
        priorityQueue.clear();
        priorityQueue2.clear();
    }

    public static boolean isMultiModalButton(NavBarRemoteView navBarRemoteView) {
        String str = navBarRemoteView.requestClass;
        if (str != null) {
            return StringsKt__StringsKt.contains(str, "honeyboard", false);
        }
        return false;
    }

    public final void applyTint(View view) {
        boolean z;
        boolean z2 = true;
        if (view instanceof ImageView) {
            Context context = this.context;
            ((ImageView) view).setColorFilter(((Integer) ArgbEvaluator.sInstance.evaluate(this.darkIntensity, Integer.valueOf(context.getColor(R.color.navbar_remote_icon_color_light)), Integer.valueOf(context.getColor(R.color.navbar_remote_icon_color_dark)))).intValue());
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

    public final NavBarStore getNavBarStore() {
        NavBarStore navBarStore = this.navBarStore;
        if (navBarStore != null) {
            return navBarStore;
        }
        return null;
    }

    public final NavBarRemoteView getRemoteView(int i, int i2) {
        NavBarRemoteView navBarRemoteView;
        boolean z = BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER;
        PriorityQueue priorityQueue = this.rightViewList;
        PriorityQueue priorityQueue2 = this.leftViewList;
        if (!z || i2 != 1) {
            if (i == 0) {
                return (NavBarRemoteView) priorityQueue2.peek();
            }
            if (i != 1) {
                return null;
            }
            return (NavBarRemoteView) priorityQueue.peek();
        }
        if (i != 0) {
            if (i == 1 && (navBarRemoteView = (NavBarRemoteView) priorityQueue.peek()) != null && isMultiModalButton(navBarRemoteView)) {
                return navBarRemoteView;
            }
            return null;
        }
        NavBarRemoteView navBarRemoteView2 = (NavBarRemoteView) priorityQueue2.peek();
        if (navBarRemoteView2 == null || !isMultiModalButton(navBarRemoteView2)) {
            return null;
        }
        return navBarRemoteView2;
    }

    public final boolean isExist(int i, String str) {
        if (i == 0) {
            PriorityQueue priorityQueue = this.leftViewList;
            if (!(priorityQueue instanceof Collection) || !priorityQueue.isEmpty()) {
                Iterator it = priorityQueue.iterator();
                while (it.hasNext()) {
                    if (StringsKt__StringsJVMKt.equals(((NavBarRemoteView) it.next()).requestClass, str, false)) {
                        return true;
                    }
                }
            }
            return false;
        }
        if (i != 1) {
            return false;
        }
        PriorityQueue priorityQueue2 = this.rightViewList;
        if (!(priorityQueue2 instanceof Collection) || !priorityQueue2.isEmpty()) {
            Iterator it2 = priorityQueue2.iterator();
            while (it2.hasNext()) {
                if (StringsKt__StringsJVMKt.equals(((NavBarRemoteView) it2.next()).requestClass, str, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isSetMultimodalButton() {
        NavBarRemoteView navBarRemoteView = (NavBarRemoteView) this.leftViewList.peek();
        if (!(navBarRemoteView != null ? isMultiModalButton(navBarRemoteView) : false)) {
            NavBarRemoteView navBarRemoteView2 = (NavBarRemoteView) this.rightViewList.peek();
            if (!(navBarRemoteView2 != null ? isMultiModalButton(navBarRemoteView2) : false)) {
                return false;
            }
        }
        return true;
    }

    public final void removeRemoteView(int i, String str) {
        Object obj;
        boolean z = str != null && StringsKt__StringsKt.contains(str, "honeyboard", false);
        PriorityQueue priorityQueue = this.rightViewList;
        PriorityQueue priorityQueue2 = this.leftViewList;
        Object obj2 = null;
        if (z) {
            Iterator it = priorityQueue2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it.next();
                    if (Intrinsics.areEqual(str, ((NavBarRemoteView) obj).requestClass)) {
                        break;
                    }
                }
            }
            priorityQueue2.remove(obj);
            Iterator it2 = priorityQueue.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Object next = it2.next();
                if (Intrinsics.areEqual(str, ((NavBarRemoteView) next).requestClass)) {
                    obj2 = next;
                    break;
                }
            }
            priorityQueue.remove(obj2);
            this.showInGestureMode = false;
            return;
        }
        if (i == 0) {
            Iterator it3 = priorityQueue2.iterator();
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
            priorityQueue2.remove(obj2);
            return;
        }
        if (i == 1) {
            Iterator it4 = priorityQueue.iterator();
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
            priorityQueue.remove(obj2);
        }
    }

    public final void setRemoteViewPadding(View view, int i, int i2, int i3) {
        if (!((NavBarStoreImpl) getNavBarStore()).getNavStateManager(i3).states.canMove || i == 0 || i == 2) {
            view.setPadding(0, i2, 0, i2);
        } else if (i == 1 || i == 3) {
            view.setPadding(i2, 0, i2, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateRemoteViewContainer(int i, LinearLayout linearLayout, LinearLayout linearLayout2, int i2) {
        LinearLayout linearLayout3;
        LinearLayout linearLayout4;
        NavBarStateManager navStateManager = ((NavBarStoreImpl) getNavBarStore()).getNavStateManager(i2);
        if (linearLayout == null || linearLayout2 == null) {
            return;
        }
        this.leftContainer = linearLayout;
        this.rightContainer = linearLayout2;
        this.showInGestureMode = isSetMultimodalButton();
        boolean z = true;
        boolean z2 = navStateManager.states.canMove && i == 1;
        NavBarRemoteView remoteView = z2 ? getRemoteView(1, i2) : getRemoteView(0, i2);
        NavBarRemoteView remoteView2 = z2 ? getRemoteView(0, i2) : getRemoteView(1, i2);
        int dimensionPixelOffset = this.context.getResources().getDimensionPixelOffset((BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && i2 == 1) ? R.dimen.samsung_navigation_bar_remoteview_padding_for_large_cover_screen : R.dimen.samsung_navigation_bar_remoteview_padding);
        if (remoteView != null) {
            if (!navStateManager.isGestureMode() || (this.adaptivePosition == 0 && isSetMultimodalButton())) {
                View view = remoteView.view;
                setRemoteViewPadding(view, i, dimensionPixelOffset, i2);
                LinearLayout linearLayout5 = this.leftContainer;
                Intrinsics.checkNotNull(linearLayout5);
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(view);
                }
                linearLayout5.setVisibility(0);
                linearLayout5.removeAllViews();
                linearLayout5.addView(view);
                if (remoteView2 != null) {
                    if (navStateManager.isGestureMode() && (this.adaptivePosition != 1 || !isSetMultimodalButton())) {
                        z = false;
                    }
                    if (z) {
                        View view2 = remoteView2.view;
                        setRemoteViewPadding(view2, i, dimensionPixelOffset, i2);
                        LinearLayout linearLayout6 = this.rightContainer;
                        Intrinsics.checkNotNull(linearLayout6);
                        ViewGroup viewGroup2 = (ViewGroup) view2.getParent();
                        if (viewGroup2 != null) {
                            viewGroup2.removeView(view2);
                        }
                        linearLayout6.setVisibility(0);
                        linearLayout6.removeAllViews();
                        linearLayout6.addView(view2);
                        return;
                    }
                }
                linearLayout3 = this.rightContainer;
                if (linearLayout3 != null) {
                    linearLayout3.removeAllViews();
                }
                linearLayout4 = this.rightContainer;
                if (linearLayout4 != null) {
                    return;
                }
                linearLayout4.setVisibility(4);
                return;
            }
        }
        LinearLayout linearLayout7 = this.leftContainer;
        if (linearLayout7 != null) {
            linearLayout7.removeAllViews();
        }
        LinearLayout linearLayout8 = this.leftContainer;
        if (linearLayout8 != null) {
            linearLayout8.setVisibility(4);
        }
        if (remoteView2 != null) {
        }
        linearLayout3 = this.rightContainer;
        if (linearLayout3 != null) {
        }
        linearLayout4 = this.rightContainer;
        if (linearLayout4 != null) {
        }
    }
}
