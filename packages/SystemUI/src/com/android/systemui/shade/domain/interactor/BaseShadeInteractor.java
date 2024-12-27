package com.android.systemui.shade.domain.interactor;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
