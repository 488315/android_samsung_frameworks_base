package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.pip.PipParamsChangedForwarder;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellModule_ProvidePipParamsChangedForwarderFactory implements Provider {
    public static PipParamsChangedForwarder providePipParamsChangedForwarder() {
        return new PipParamsChangedForwarder();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new PipParamsChangedForwarder();
    }
}
