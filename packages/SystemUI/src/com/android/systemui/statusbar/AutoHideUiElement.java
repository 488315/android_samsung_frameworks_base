package com.android.systemui.statusbar;

public interface AutoHideUiElement {
    void hide();

    boolean isVisible();

    default boolean shouldHideOnTouch() {
        return true;
    }

    void synchronizeState();
}
