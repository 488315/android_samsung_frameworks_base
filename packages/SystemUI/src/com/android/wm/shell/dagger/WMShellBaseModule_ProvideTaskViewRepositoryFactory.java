package com.android.wm.shell.dagger;

import com.android.wm.shell.taskview.TaskViewRepository;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideTaskViewRepositoryFactory implements Provider {
    public static TaskViewRepository provideTaskViewRepository() {
        return new TaskViewRepository();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TaskViewRepository();
    }
}
