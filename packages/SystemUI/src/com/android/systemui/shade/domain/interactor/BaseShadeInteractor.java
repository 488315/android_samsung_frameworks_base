package com.android.systemui.shade.domain.interactor;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

public interface BaseShadeInteractor {
    StateFlow getAnyExpansion();

    StateFlow getQsExpansion();

    StateFlow getShadeExpansion();

    StateFlow getShadeMode();

    StateFlow isAnyExpanded();

    Flow isQsBypassingShade();

    StateFlow isQsExpanded();

    Flow isUserInteractingWithQs();

    Flow isUserInteractingWithShade();
}
