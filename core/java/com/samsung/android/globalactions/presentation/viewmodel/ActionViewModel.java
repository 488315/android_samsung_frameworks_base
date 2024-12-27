package com.samsung.android.globalactions.presentation.viewmodel;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public interface ActionViewModel {

    public enum ToggleState {
        on,
        off
    }

    ActionInfo getActionInfo();

    void onPress();

    void setActionInfo(ActionInfo actionInfo);

    default void setState(ToggleState state) {}

    default ToggleState getState() {
        return ToggleState.off;
    }

    default void setIcon(BitmapDrawable icon) {}

    default void setText(String text) {}

    default void setIntent(Intent intent) {}

    default void setIntentAction(int intentAction) {}

    default BitmapDrawable getIcon() {
        return null;
    }

    default String getText() {
        return null;
    }

    default void onLongPress() {}

    default boolean showBeforeProvisioning() {
        return false;
    }

    default void onPressSecureConfirm() {}

    default void updateState() {}

    default void showTipPopup(View parentView) {}

    default void dismissTipPopup() {}

    default boolean isAvailableShow() {
        return true;
    }
}
