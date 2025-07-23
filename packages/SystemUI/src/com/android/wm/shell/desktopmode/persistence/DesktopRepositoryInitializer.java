package com.android.wm.shell.desktopmode.persistence;

import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DesktopRepositoryInitializer {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DeskActivationFactory {
        Object activeDesk(int i, Continuation continuation);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DeskRecreationFactory {
        Object recreateDesk(int i, int i2, int i3, Continuation continuation);
    }
}
