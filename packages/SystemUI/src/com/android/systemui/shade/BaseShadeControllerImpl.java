package com.android.systemui.shade;

import com.android.keyguard.KeyguardViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public abstract class BaseShadeControllerImpl implements ShadeController {
    public final Lazy assistManagerLazy;
    public final CommandQueue commandQueue;
    public final KeyguardViewController keyguardViewController;
    public NotificationPresenter notifPresenter;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public final ArrayList postCollapseActions = new ArrayList();

    public BaseShadeControllerImpl(CommandQueue commandQueue, KeyguardViewController keyguardViewController, NotificationShadeWindowController notificationShadeWindowController, Lazy lazy) {
        this.commandQueue = commandQueue;
        this.keyguardViewController = keyguardViewController;
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
        this.keyguardViewController.readyForKeyguardDone();
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
    }
}
