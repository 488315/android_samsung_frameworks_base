package com.android.wm.shell.dagger;

import android.content.Context;
import android.os.Handler;
import android.window.DesktopModeFlags;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.Transitions;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellModule_ProvideDesktopTasksLimiterFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desksOrganizerProvider;
    public final Provider desktopConfigProvider;
    public final Provider desktopStateProvider;
    public final Provider desktopUserRepositoriesProvider;
    public final Provider displayControllerProvider;
    public final Provider handlerProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider mainExecutorProvider;
    public final Provider shellTaskOrganizerProvider;
    public final Provider transitionsProvider;

    public WMShellModule_ProvideDesktopTasksLimiterFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11) {
        this.contextProvider = provider;
        this.transitionsProvider = provider2;
        this.desktopUserRepositoriesProvider = provider3;
        this.shellTaskOrganizerProvider = provider4;
        this.desksOrganizerProvider = provider5;
        this.interactionJankMonitorProvider = provider6;
        this.handlerProvider = provider7;
        this.displayControllerProvider = provider8;
        this.desktopConfigProvider = provider9;
        this.desktopStateProvider = provider10;
        this.mainExecutorProvider = provider11;
    }

    public static Optional provideDesktopTasksLimiter(Context context, Transitions transitions, DesktopUserRepositories desktopUserRepositories, ShellTaskOrganizer shellTaskOrganizer, DesksOrganizer desksOrganizer, InteractionJankMonitor interactionJankMonitor, Handler handler, DisplayController displayController, DesktopConfig desktopConfig, DesktopState desktopState, ShellExecutor shellExecutor) {
        Optional empty;
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_TASK_LIMIT.isTrue()) {
            int i = ((DesktopConfigImpl) desktopConfig).maxTaskLimit;
            empty = Optional.of(new DesktopTasksLimiter(transitions, desktopUserRepositories, shellTaskOrganizer, desksOrganizer, i <= 0 ? null : Integer.valueOf(i), interactionJankMonitor, context, handler, displayController, desktopConfig, shellExecutor));
        } else {
            empty = Optional.empty();
        }
        empty.getClass();
        return empty;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopTasksLimiter((Context) this.contextProvider.get(), (Transitions) this.transitionsProvider.get(), (DesktopUserRepositories) this.desktopUserRepositoriesProvider.get(), (ShellTaskOrganizer) this.shellTaskOrganizerProvider.get(), (DesksOrganizer) this.desksOrganizerProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (Handler) this.handlerProvider.get(), (DisplayController) this.displayControllerProvider.get(), (DesktopConfig) this.desktopConfigProvider.get(), (DesktopState) this.desktopStateProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
