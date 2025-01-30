package com.android.systemui.biometrics;

import android.content.Context;
import com.android.systemui.util.concurrency.Execution;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsDisplayMode implements UdfpsDisplayModeProvider {
    public final AuthController authController;
    public final Context context;
    public Request currentRequest;
    public final Execution execution;
    public final UdfpsLogger logger;

    public UdfpsDisplayMode(Context context, Execution execution, AuthController authController, UdfpsLogger udfpsLogger) {
        this.context = context;
        this.execution = execution;
        this.authController = authController;
        this.logger = udfpsLogger;
    }
}
