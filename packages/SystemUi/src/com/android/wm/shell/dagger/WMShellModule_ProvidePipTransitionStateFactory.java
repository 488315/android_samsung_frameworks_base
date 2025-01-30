package com.android.wm.shell.dagger;

import com.android.wm.shell.pip.PipTransitionState;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipTransitionStateFactory implements Provider {
    public static PipTransitionState providePipTransitionState() {
        return new PipTransitionState();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipTransitionState();
    }
}
