package com.android.systemui.statusbar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AutoHideUiElement {
    void hide();

    boolean isVisible();

    default boolean shouldHideOnTouch() {
        return true;
    }

    void synchronizeState();
}
