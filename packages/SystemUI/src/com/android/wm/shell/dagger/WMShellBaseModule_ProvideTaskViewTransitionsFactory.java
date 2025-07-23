package com.android.wm.shell.dagger;

import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.taskview.TaskViewRepository;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideTaskViewTransitionsFactory implements Provider {
    public final Provider organizerProvider;
    public final Provider repositoryProvider;
    public final Provider syncQueueProvider;
    public final Provider transitionsProvider;

    public WMShellBaseModule_ProvideTaskViewTransitionsFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.transitionsProvider = provider;
        this.repositoryProvider = provider2;
        this.organizerProvider = provider3;
        this.syncQueueProvider = provider4;
    }

    public static TaskViewTransitions provideTaskViewTransitions(Transitions transitions, TaskViewRepository taskViewRepository, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue) {
        return new TaskViewTransitions(transitions, taskViewRepository, shellTaskOrganizer, syncTransactionQueue);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TaskViewTransitions((Transitions) this.transitionsProvider.get(), (TaskViewRepository) this.repositoryProvider.get(), (ShellTaskOrganizer) this.organizerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get());
    }
}
