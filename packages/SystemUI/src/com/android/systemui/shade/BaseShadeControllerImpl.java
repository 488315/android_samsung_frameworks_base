package com.android.systemui.shade;

import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class BaseShadeControllerImpl implements ShadeController {
    public final Lazy assistManagerLazy;
    public final CommandQueue commandQueue;
    public NotificationPresenter notifPresenter;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public final ArrayList postCollapseActions = new ArrayList();
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;

    public BaseShadeControllerImpl(CommandQueue commandQueue, StatusBarKeyguardViewManager statusBarKeyguardViewManager, NotificationShadeWindowController notificationShadeWindowController, Lazy lazy) {
        this.commandQueue = commandQueue;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.assistManagerLazy = lazy;
    }

    public final void animateExpandQs() {
        if (isShadeEnabled()) {
            expandToQs();
        }
    }

    public final void animateExpandShade() {
        if (isShadeEnabled()) {
            expandToNotifications();
        }
    }

    public abstract void expandToNotifications();

    public abstract void expandToQs();

    public final void onClosingFinished$1() {
        runPostCollapseActions();
        NotificationPresenter notificationPresenter = this.notifPresenter;
        if (notificationPresenter == null) {
            notificationPresenter = null;
        }
        if (((StatusBarNotificationPresenter) notificationPresenter).mPanelExpansionInteractor.isFullyCollapsed()) {
            return;
        }
        ((NotificationShadeWindowControllerImpl) this.notificationShadeWindowController).setNotificationShadeFocusable(true);
    }

    public final void runPostCollapseActions() {
        ArrayList arrayList = new ArrayList(this.postCollapseActions);
        this.postCollapseActions.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.statusBarKeyguardViewManager.readyForKeyguardDone();
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
    }
}
