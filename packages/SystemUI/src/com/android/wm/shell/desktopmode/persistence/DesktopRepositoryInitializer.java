package com.android.wm.shell.desktopmode.persistence;

import kotlin.coroutines.Continuation;

/* loaded from: classes3.dex */
public interface DesktopRepositoryInitializer {

    public interface DeskActivationFactory {
        Object activeDesk(int i, Continuation continuation);
    }

    public interface DeskRecreationFactory {
        Object recreateDesk(int i, int i2, int i3, Continuation continuation);
    }
}
