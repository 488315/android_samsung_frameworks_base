package com.samsung.android.globalactions.presentation.view;

import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* loaded from: classes6.dex */
public interface ContentView {
    void dismiss();

    void forceRequestLayout();

    ViewAnimationState getAnimationState();

    void hideConfirm();

    default void hideDialogOnSecureConfirm() {
    }

    void initAnimations();

    void initDimens();

    void initLayouts();

    default void notifyDataSetChanged() {
    }

    default void onDismiss() {
    }

    default void registerRotationWatcher() {
    }

    void setAnimationState(ViewAnimationState viewAnimationState);

    default void setInterceptor() {
    }

    void show();

    void showConfirm(ActionViewModel actionViewModel);

    void updateItemLists(SamsungGlobalActionsPresenter samsungGlobalActionsPresenter);
}
