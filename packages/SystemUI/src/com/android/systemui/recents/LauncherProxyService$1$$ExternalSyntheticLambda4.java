package com.android.systemui.recents;

import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final /* synthetic */ class LauncherProxyService$1$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LauncherProxyService.AnonymousClass1 f$0;

    public /* synthetic */ LauncherProxyService$1$$ExternalSyntheticLambda4(LauncherProxyService.AnonymousClass1 anonymousClass1, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        int i = this.$r8$classId;
        LauncherProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
        switch (i) {
            case 0:
                AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(LauncherProxyService.this.mContext);
                LauncherProxyService.this.mDisplayTracker.getClass();
                accessibilityManager.notifyAccessibilityButtonLongClicked(0);
                return;
            case 1:
                ((FgsManagerControllerImpl) LauncherProxyService.this.mFgsManagerController).showDialog$2();
                return;
            case 2:
                LauncherProxyService.this.mCommandQueue.handleSystemKey(new KeyEvent(0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub));
                return;
            case 3:
                LauncherProxyService.this.mSearcleManager.startSearcleByHomeKey(false, true);
                return;
            case 4:
                LauncherProxyService.this.mQsCustomizerContoller.close();
                return;
            case 5:
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onTaskbarSPluginButtonClicked();
                }
                return;
            case 6:
                LauncherProxyService.this.mSearcleManager.startSearcleByHomeKey(false, false);
                return;
            case 7:
                LauncherProxyService.this.mSearcleManager.startSearcleByHomeKey(true, false);
                return;
            case 8:
                LauncherProxyService.this.mCommandQueue.toggleNotificationsPanel();
                return;
            case 9:
                CommandQueue commandQueue = LauncherProxyService.this.mCommandQueue;
                synchronized (commandQueue.mLock) {
                    commandQueue.mHandler.removeMessages(5373952);
                    commandQueue.mHandler.obtainMessage(5373952, 0, 0).sendToTarget();
                }
                return;
            case 10:
                LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                for (int size2 = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size2)).onInitializedTaskbarNavigationBar();
                }
                return;
            default:
                for (int size3 = ((ArrayList) LauncherProxyService.this.mConnectionCallbacks).size() - 1; size3 >= 0; size3--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) LauncherProxyService.this.mConnectionCallbacks).get(size3)).onOverviewShown();
                }
                return;
        }
    }

    public /* synthetic */ LauncherProxyService$1$$ExternalSyntheticLambda4(LauncherProxyService.AnonymousClass1 anonymousClass1, boolean z) {
        this.$r8$classId = 11;
        this.f$0 = anonymousClass1;
    }
}
