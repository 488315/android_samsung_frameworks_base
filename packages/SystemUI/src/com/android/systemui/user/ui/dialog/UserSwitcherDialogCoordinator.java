package com.android.systemui.user.ui.dialog;

import android.app.Dialog;
import com.android.systemui.CoreStartable;
import dagger.Lazy;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserSwitcherDialogCoordinator implements CoreStartable {
    public final Lazy activityStarter;
    public final Lazy applicationScope;
    public final Lazy broadcastSender;
    public final Lazy context;
    public Dialog currentDialog;
    public final Lazy dialogTransitionAnimator;
    public final Lazy eventLogger;
    public final Lazy falsingCollector;
    public final Lazy falsingManager;
    public final Lazy interactor;
    public final Provider userDetailAdapterProvider;
    public final Lazy userSwitcherViewModel;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public UserSwitcherDialogCoordinator(Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Provider provider, Lazy lazy7, Lazy lazy8, Lazy lazy9, Lazy lazy10) {
        this.context = lazy;
        this.applicationScope = lazy2;
        this.falsingManager = lazy3;
        this.broadcastSender = lazy4;
        this.dialogTransitionAnimator = lazy5;
        this.interactor = lazy6;
        this.userDetailAdapterProvider = provider;
        this.eventLogger = lazy7;
        this.activityStarter = lazy8;
        this.falsingCollector = lazy9;
        this.userSwitcherViewModel = lazy10;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Lazy lazy = this.applicationScope;
        BuildersKt.launch$default((CoroutineScope) lazy.get(), null, null, new UserSwitcherDialogCoordinator$startHandlingDialogShowRequests$1(this, null), 3);
        BuildersKt.launch$default((CoroutineScope) lazy.get(), null, null, new UserSwitcherDialogCoordinator$startHandlingDialogDismissRequests$1(this, null), 3);
    }
}
