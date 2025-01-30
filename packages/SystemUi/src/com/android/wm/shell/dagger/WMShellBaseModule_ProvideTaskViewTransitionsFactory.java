package com.android.wm.shell.dagger;

import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideTaskViewTransitionsFactory implements Provider {
    public final Provider transitionsProvider;

    public WMShellBaseModule_ProvideTaskViewTransitionsFactory(Provider provider) {
        this.transitionsProvider = provider;
    }

    public static TaskViewTransitions provideTaskViewTransitions(Transitions transitions) {
        return new TaskViewTransitions(transitions);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TaskViewTransitions((Transitions) this.transitionsProvider.get());
    }
}
