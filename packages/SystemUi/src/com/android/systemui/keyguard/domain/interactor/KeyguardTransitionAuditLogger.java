package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTransitionAuditLogger {
    public final KeyguardTransitionInteractor interactor;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardLogger logger;
    public final CoroutineScope scope;

    public KeyguardTransitionAuditLogger(CoroutineScope coroutineScope, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, KeyguardLogger keyguardLogger) {
        this.scope = coroutineScope;
        this.interactor = keyguardTransitionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.logger = keyguardLogger;
    }
}
