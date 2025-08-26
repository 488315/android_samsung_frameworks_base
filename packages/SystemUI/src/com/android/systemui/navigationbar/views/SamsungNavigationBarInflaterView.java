package com.android.systemui.navigationbar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.views.buttons.KeyButtonView;
import com.android.systemui.navigationbar.views.buttons.ReverseLinearLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SamsungNavigationBarInflaterView extends NavigationBarInflaterView {
    public final int displayId;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public static final Companion Companion = new Companion(null);
    public static final String space = "space";
    public static final String leftstr = "left";
    public static final String rightstr = "right";
    public static final String pin = "pin";
    public static final String navkey = "navkey";
    public static final String keymargin = "gap";
    public static final String leftGestureHint = "hint_left";
    public static final String centerGestureHint = "hint_center";
    public static final String rightGestureHint = "hint_right";
    public static final String leftRemoteView = "left_remote_view";
    public static final String rightRemoteView = "right_remote_view";
    public static final String taskStack = "task_stack";
    public static final String buttonSpace = "button_space";

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SamsungNavigationBarInflaterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int displayId = context.getDisplayId();
        this.displayId = displayId;
        NavBarStore navBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
        this.navBarStore = navBarStore;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
    }

    public final void addSidePadding(View view, boolean z) {
        int iIntValue = ((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetNavBarSidePadding(z), this.displayId, 0)).intValue();
        view.setPadding(iIntValue, 0, iIntValue, 0);
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarInflaterView
    public final View createView(String str, ViewGroup viewGroup, LayoutInflater layoutInflater) {
        View viewCreateView;
        Integer numValueOf;
        String strExtractButton = NavigationBarInflaterView.extractButton(str);
        if ((!BasicRune.NAVBAR_MULTI_MODAL_ICON || (!Intrinsics.areEqual(strExtractButton, leftstr) && !Intrinsics.areEqual(strExtractButton, rightstr))) && (viewCreateView = super.createView(str, viewGroup, layoutInflater)) != null) {
            return viewCreateView;
        }
        boolean zCanShowKeyboardButtonOnLeft = ((NavBarStateManagerImpl) this.navBarStateManager).canShowKeyboardButtonOnLeft();
        boolean zIsGestureMode = ((NavBarStateManagerImpl) this.navBarStateManager).isGestureMode();
        boolean zAreEqual = Intrinsics.areEqual(strExtractButton, leftstr);
        int i = R.layout.contextual_a11y;
        if (zAreEqual) {
            if (zIsGestureMode && zCanShowKeyboardButtonOnLeft) {
                i = R.layout.ime_switcher;
            } else if (zIsGestureMode && !zCanShowKeyboardButtonOnLeft) {
                i = R.layout.back;
            } else if (!zIsGestureMode && zCanShowKeyboardButtonOnLeft) {
                i = R.layout.contextual_ime;
            }
            numValueOf = Integer.valueOf(i);
        } else if (Intrinsics.areEqual(strExtractButton, rightstr)) {
            if (zIsGestureMode && zCanShowKeyboardButtonOnLeft) {
                i = R.layout.back;
            } else if (zIsGestureMode && !zCanShowKeyboardButtonOnLeft) {
                i = R.layout.ime_switcher;
            } else if (zIsGestureMode || !zCanShowKeyboardButtonOnLeft) {
                i = R.layout.contextual_ime;
            }
            numValueOf = Integer.valueOf(i);
        } else if (Intrinsics.areEqual(strExtractButton, leftGestureHint)) {
            numValueOf = Integer.valueOf(R.layout.hint_left);
        } else if (Intrinsics.areEqual(strExtractButton, centerGestureHint)) {
            numValueOf = Integer.valueOf(R.layout.hint_center);
        } else if (Intrinsics.areEqual(strExtractButton, rightGestureHint)) {
            numValueOf = Integer.valueOf(R.layout.hint_right);
        } else if (Intrinsics.areEqual(strExtractButton, pin)) {
            numValueOf = Integer.valueOf(R.layout.navbar_pin);
        } else if (Intrinsics.areEqual(strExtractButton, keymargin)) {
            numValueOf = Integer.valueOf(R.layout.navbar_key_distance);
        } else if (Intrinsics.areEqual(strExtractButton, taskStack)) {
            numValueOf = Integer.valueOf(R.layout.navbar_task_stack);
        } else if (Intrinsics.areEqual(strExtractButton, buttonSpace)) {
            numValueOf = Integer.valueOf(R.layout.navbar_key_distance);
        } else {
            strExtractButton.getClass();
            numValueOf = strExtractButton.startsWith(navkey) ? Integer.valueOf(R.layout.navbar_custom) : null;
        }
        if (numValueOf == null) {
            return null;
        }
        View viewInflate = layoutInflater.inflate(numValueOf.intValue(), viewGroup, false);
        strExtractButton.getClass();
        if (strExtractButton.startsWith(navkey)) {
            KeyButtonView keyButtonView = (KeyButtonView) viewInflate;
            keyButtonView.setId(Integer.parseInt(NavigationBarInflaterView.extractImage(strExtractButton)));
            keyButtonView.mCode = NavigationBarInflaterView.extractKeycode(strExtractButton);
        }
        return viewInflate;
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarInflaterView
    public final String getDefaultLayout() {
        return (String) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetDefaultLayout(false, 1, null), this.displayId, ((FrameLayout) this).mContext.getString(R.string.config_navBarLayout));
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarInflaterView
    public final void inflateButton(String str, ViewGroup viewGroup, boolean z, boolean z2) throws NumberFormatException {
        LayoutInflater layoutInflater = z ? this.mLandscapeInflater : this.mLayoutInflater;
        layoutInflater.getClass();
        View viewCreateView = createView(str, viewGroup, layoutInflater);
        if (viewCreateView == null) {
            return;
        }
        View viewApplySize = applySize(viewCreateView, str, z, z2);
        viewApplySize.getLayoutParams().width = ((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateButtonWidth(NavigationBarInflaterView.extractButton(str), z), this.displayId, Integer.valueOf(getContext().getResources().getDimensionPixelSize(R.dimen.navigation_key_width)))).intValue();
        if (viewGroup != null) {
            viewGroup.addView(viewApplySize);
        }
        addToDispatchers(viewApplySize);
        View view = z ? this.mLastLandscape : this.mLastPortrait;
        if (viewApplySize instanceof ReverseLinearLayout.ReverseRelativeLayout) {
            viewApplySize = ((ReverseLinearLayout.ReverseRelativeLayout) viewApplySize).getChildAt(0);
        }
        if (view != null) {
            viewApplySize.getClass();
            viewApplySize.setAccessibilityTraversalAfter(view.getId());
        }
        if (z) {
            this.mLastLandscape = viewApplySize;
        } else {
            this.mLastPortrait = viewApplySize;
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarInflaterView
    public final void inflateChildren() {
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.mLayoutInflater.inflate(((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateLayoutID(false), this.displayId, Integer.valueOf(R.layout.navigation_layout))).intValue(), (ViewGroup) this, false);
        this.mHorizontal = frameLayout;
        addView(frameLayout);
        FrameLayout frameLayout2 = (FrameLayout) this.mLayoutInflater.inflate(((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateLayoutID(true), this.displayId, Integer.valueOf(R.layout.navigation_layout_vertical))).intValue(), (ViewGroup) this, false);
        this.mVertical = frameLayout2;
        addView(frameLayout2);
        updateAlternativeOrder();
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarInflaterView
    public final void inflateLayout(String str) throws NumberFormatException {
        super.inflateLayout(str);
        if (BasicRune.NAVBAR_REMOTEVIEW) {
            if (this.mVertical.findViewById(R.id.nav_bar_widget) != null && this.mHorizontal.findViewById(R.id.nav_bar_widget) != null) {
                if (BasicRune.NAVBAR_MULTI_MODAL_ICON_LARGE_COVER && this.displayId == 1 && ((NavBarStateManagerImpl) this.navBarStateManager).states.rotation == 0) {
                    ViewGroup viewGroup = (ViewGroup) this.mHorizontal.requireViewById(R.id.nav_bar_widget);
                    String str2 = space;
                    inflateButton(str2, viewGroup, false, true);
                    String str3 = leftRemoteView;
                    inflateRemoteViewButtons(str3, viewGroup, false);
                    String str4 = keymargin;
                    inflateButton(str4, viewGroup, false, true);
                    String str5 = rightRemoteView;
                    inflateRemoteViewButtons(str5, viewGroup, false);
                    inflateButton(str2, viewGroup, false, true);
                    ViewGroup viewGroup2 = (ViewGroup) this.mVertical.requireViewById(R.id.nav_bar_widget);
                    inflateButton(str2, viewGroup2, true, true);
                    inflateRemoteViewButtons(str3, viewGroup2, false);
                    inflateButton(str4, viewGroup2, true, true);
                    inflateRemoteViewButtons(str5, viewGroup2, false);
                    inflateButton(str2, viewGroup2, true, true);
                } else {
                    String str6 = leftRemoteView;
                    inflateRemoteViewButtons(str6, (ViewGroup) this.mHorizontal.requireViewById(R.id.nav_bar_widget), false);
                    addGravitySpacer((LinearLayout) this.mHorizontal.findViewById(R.id.nav_bar_widget));
                    String str7 = rightRemoteView;
                    inflateRemoteViewButtons(str7, (ViewGroup) this.mHorizontal.requireViewById(R.id.nav_bar_widget), false);
                    inflateRemoteViewButtons(str6, (ViewGroup) this.mVertical.requireViewById(R.id.nav_bar_widget), true);
                    addGravitySpacer((LinearLayout) this.mVertical.findViewById(R.id.nav_bar_widget));
                    inflateRemoteViewButtons(str7, (ViewGroup) this.mVertical.requireViewById(R.id.nav_bar_widget), true);
                }
            }
            addSidePadding(this.mHorizontal.requireViewById(R.id.nav_bar_widget), false);
            addSidePadding(this.mVertical.requireViewById(R.id.nav_bar_widget), true);
        }
        if (BasicRune.NAVBAR_STABLE_LAYOUT) {
            addSidePadding(this.mHorizontal.requireViewById(R.id.ends_group), false);
            addSidePadding(this.mVertical.requireViewById(R.id.ends_group), true);
        }
    }

    public final void inflateRemoteViewButtons(String str, ViewGroup viewGroup, boolean z) {
        LayoutInflater layoutInflater = z ? this.mLandscapeInflater : this.mLayoutInflater;
        View viewInflate = Intrinsics.areEqual(leftRemoteView, str) ? layoutInflater.inflate(R.layout.navbar_remoteview_left, viewGroup, false) : Intrinsics.areEqual(rightRemoteView, str) ? layoutInflater.inflate(R.layout.navbar_remoteview_right, viewGroup, false) : null;
        if (viewInflate != null) {
            viewInflate.getLayoutParams().width = ((Number) ((NavBarStoreImpl) this.navBarStore).handleEvent(this, new EventTypeFactory.EventType.GetInflateButtonWidth(str, z), this.displayId, Integer.valueOf(getContext().getResources().getDimensionPixelSize(R.dimen.navigation_key_width)))).intValue();
            viewGroup.addView(viewInflate);
            View view = z ? this.mLastLandscape : this.mLastPortrait;
            if (viewInflate instanceof ReverseLinearLayout.ReverseRelativeLayout) {
                viewInflate = ((ReverseLinearLayout.ReverseRelativeLayout) viewInflate).getChildAt(0);
            }
            if (view != null) {
                viewInflate.setAccessibilityTraversalAfter(view.getId());
            }
            if (z) {
                this.mLastLandscape = viewInflate;
            } else {
                this.mLastPortrait = viewInflate;
            }
        }
    }

    @Override // com.android.systemui.navigationbar.views.NavigationBarInflaterView
    public final void updateLayoutProviderView() throws NumberFormatException {
        inflateChildren();
        if (getParent() instanceof NavigationBarView) {
            ((NavigationBarView) getParent()).updateOrientationViews();
        }
        clearViews();
        inflateLayout(getDefaultLayout());
    }
}
