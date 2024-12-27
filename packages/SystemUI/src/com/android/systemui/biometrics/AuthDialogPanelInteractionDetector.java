package com.android.systemui.biometrics;

import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class AuthDialogPanelInteractionDetector {
    public final Job shadeExpansionCollectorJob;
    public final Lazy shadeInteractorLazy;

    public AuthDialogPanelInteractionDetector(CoroutineScope coroutineScope, Lazy lazy) {
    }
}
