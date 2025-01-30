package com.android.wm.shell.dagger;

import android.content.Context;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.sysui.ShellInit;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellBaseModule_ProvideFullscreenTaskListenerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider dexWindowDecorViewModelOptionalProvider;
    public final Provider fullscreenTaskListenerProvider;
    public final Provider optionalSplitScreenControllerProvider;
    public final Provider recentTasksOptionalProvider;
    public final Provider shellInitProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider syncQueueProvider;
    public final Provider windowDecorViewModelOptionalProvider;

    public WMShellBaseModule_ProvideFullscreenTaskListenerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.fullscreenTaskListenerProvider = provider;
        this.shellInitProvider = provider2;
        this.shellTaskOrganizerProvider = provider3;
        this.syncQueueProvider = provider4;
        this.recentTasksOptionalProvider = provider5;
        this.windowDecorViewModelOptionalProvider = provider6;
        this.dexWindowDecorViewModelOptionalProvider = provider7;
        this.optionalSplitScreenControllerProvider = provider8;
        this.contextProvider = provider9;
    }

    public static FullscreenTaskListener provideFullscreenTaskListener(Optional optional, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, SyncTransactionQueue syncTransactionQueue, Optional optional2, Optional optional3, Optional optional4, Optional optional5, Context context) {
        FullscreenTaskListener fullscreenTaskListener = optional.isPresent() ? (FullscreenTaskListener) optional.get() : new FullscreenTaskListener(shellInit, shellTaskOrganizer, syncTransactionQueue, optional2, optional3, optional4, optional5, context);
        Preconditions.checkNotNullFromProvides(fullscreenTaskListener);
        return fullscreenTaskListener;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideFullscreenTaskListener((Optional) this.fullscreenTaskListenerProvider.get(), (ShellInit) this.shellInitProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (SyncTransactionQueue) this.syncQueueProvider.get(), (Optional) this.recentTasksOptionalProvider.get(), (Optional) this.windowDecorViewModelOptionalProvider.get(), (Optional) this.dexWindowDecorViewModelOptionalProvider.get(), (Optional) this.optionalSplitScreenControllerProvider.get(), (Context) this.contextProvider.get());
    }
}
