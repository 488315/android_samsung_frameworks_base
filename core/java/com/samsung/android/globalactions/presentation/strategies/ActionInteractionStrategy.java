package com.samsung.android.globalactions.presentation.strategies;

public interface ActionInteractionStrategy {
    default boolean onPressPowerAction() {
        return false;
    }

    default boolean onLongPressPowerAction() {
        return false;
    }

    default boolean onPressRestartAction() {
        return false;
    }

    default boolean onPressEmergencyModeAction() {
        return false;
    }

    default boolean onPressDataModeAction() {
        return false;
    }
}
