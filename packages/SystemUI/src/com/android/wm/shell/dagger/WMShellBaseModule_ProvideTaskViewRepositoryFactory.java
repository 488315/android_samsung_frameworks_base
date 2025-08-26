package com.android.wm.shell.dagger;

import com.android.wm.shell.taskview.TaskViewRepository;
import dagger.internal.Provider;

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
