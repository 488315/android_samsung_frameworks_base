package com.samsung.android.globalactions.presentation.viewmodel;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

/* loaded from: classes6.dex */
public interface ActionViewModel {

    public enum ToggleState {
        on,
        off
    }

    default void dismissTipPopup() {
    }

    ActionInfo getActionInfo();

    default BitmapDrawable getIcon() {
        return null;
    }

    default String getText() {
        return null;
    }

    default boolean isAvailableShow() {
        return true;
    }

    default void onLongPress() {
    }

    void onPress();

    default void onPressSecureConfirm() {
    }

    void setActionInfo(ActionInfo actionInfo);

    default void setIcon(BitmapDrawable bitmapDrawable) {
    }

    default void setIntent(Intent intent) {
    }

    default void setIntentAction(int i) {
    }

    default void setState(ToggleState toggleState) {
    }

    default void setText(String str) {
    }

    default boolean showBeforeProvisioning() {
        return false;
    }

    default void showTipPopup(View view) {
    }

    default void updateState() {
    }

    default ToggleState getState() {
        return ToggleState.off;
    }
}
