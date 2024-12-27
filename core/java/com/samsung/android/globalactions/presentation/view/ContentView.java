package com.samsung.android.globalactions.presentation.view;

import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* loaded from: classes6.dex */
public interface ContentView {
    void dismiss();

    void forceRequestLayout();

    ViewAnimationState getAnimationState();

    void hideConfirm();

    void initAnimations();

    void initDimens();

    void initLayouts();

    void setAnimationState(ViewAnimationState viewAnimationState);

    void show();

    void showConfirm(ActionViewModel actionViewModel);

    void updateItemLists(SamsungGlobalActionsPresenter samsungGlobalActionsPresenter);

    default void registerRotationWatcher() {
    }

    default void onDismiss() {
    }

    default void notifyDataSetChanged() {
    }

    default void hideDialogOnSecureConfirm() {
    }

    default void setInterceptor() {
    }
}
