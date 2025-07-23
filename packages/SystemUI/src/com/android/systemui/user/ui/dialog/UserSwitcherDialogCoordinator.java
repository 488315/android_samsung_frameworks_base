package com.android.systemui.user.ui.dialog;

import android.app.Dialog;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import dagger.Lazy;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class UserSwitcherDialogCoordinator implements CoreStartable {
    public final Lazy activityStarter;
    public final Lazy applicationScope;
    public final Lazy broadcastSender;
    public Dialog currentDialog;
    public final Lazy dialogTransitionAnimator;
    public final Lazy eventLogger;
    public final Lazy falsingCollector;
    public final Lazy falsingManager;
    public final Lazy interactor;
    public final Lazy shadeDialogContextInteractor;
    public final Provider userDetailAdapterProvider;
    public final Lazy userSwitcherViewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public UserSwitcherDialogCoordinator(Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Provider provider, Lazy lazy6, Lazy lazy7, Lazy lazy8, Lazy lazy9, Lazy lazy10) {
        this.applicationScope = lazy;
        this.falsingManager = lazy2;
        this.broadcastSender = lazy3;
        this.dialogTransitionAnimator = lazy4;
        this.interactor = lazy5;
        this.userDetailAdapterProvider = provider;
        this.eventLogger = lazy6;
        this.activityStarter = lazy7;
        this.falsingCollector = lazy8;
        this.userSwitcherViewModel = lazy9;
        this.shadeDialogContextInteractor = lazy10;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Lazy lazy = this.applicationScope;
        CoroutineTracingKt.launchTraced$default((CoroutineScope) lazy.get(), null, null, new UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1(this, null), 7);
        CoroutineTracingKt.launchTraced$default((CoroutineScope) lazy.get(), null, null, new UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1(this, null), 7);
    }
}
