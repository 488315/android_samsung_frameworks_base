package com.android.systemui.recents;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import com.android.systemui.CoreStartable;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.shared.recents.ILauncherProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.systemui.multistar.MultiStarManager;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class Recents implements CoreStartable, ConfigurationController.ConfigurationListener, CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final RecentsImplementation mImpl;

    public Recents(Context context, RecentsImplementation recentsImplementation, CommandQueue commandQueue) {
        this.mContext = context;
        this.mImpl = recentsImplementation;
        this.mCommandQueue = commandQueue;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void appTransitionFinished(int i) {
        if (this.mContext.getDisplayId() == i) {
            this.mImpl.getClass();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void cancelPreloadRecentApps() {
        if (isUserSetup$1()) {
            this.mImpl.getClass();
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.mImpl.getClass();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void hideRecentApps(boolean z, boolean z2) {
        ILauncherProxy iLauncherProxy;
        if (isUserSetup$1() && (iLauncherProxy = ((OverviewProxyRecentsImpl) this.mImpl).mLauncherProxyService.mLauncherProxy) != null) {
            try {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onOverviewHidden(z, z2);
            } catch (RemoteException e) {
                Log.e("OverviewProxyRecentsImpl", "Failed to send overview hide event to launcher.", e);
            }
        }
    }

    public final boolean isUserSetup$1() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        return (Settings.Global.getInt(contentResolver, "device_provisioned", 0) == 0 || Settings.Secure.getInt(contentResolver, SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0) == 0) ? false : true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        this.mImpl.getClass();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        this.mImpl.getClass();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void preloadRecentApps() {
        if (isUserSetup$1()) {
            this.mImpl.getClass();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) {
        if (!isUserSetup$1()) {
            Log.d("ThreeFinger", "isUserSetup : false");
            return;
        }
        OverviewProxyRecentsImpl overviewProxyRecentsImpl = (OverviewProxyRecentsImpl) this.mImpl;
        if (overviewProxyRecentsImpl.mLauncherProxyService.mLauncherProxy != null) {
            new OverviewProxyRecentsImpl$$ExternalSyntheticLambda0(overviewProxyRecentsImpl, keyEvent, 0).run();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showRecentApps(boolean z) {
        ILauncherProxy iLauncherProxy;
        if (isUserSetup$1() && (iLauncherProxy = ((OverviewProxyRecentsImpl) this.mImpl).mLauncherProxyService.mLauncherProxy) != null) {
            try {
                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onOverviewShown(z);
            } catch (RemoteException e) {
                Log.e("OverviewProxyRecentsImpl", "Failed to send overview show event to launcher.", e);
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        OverviewProxyRecentsImpl overviewProxyRecentsImpl = (OverviewProxyRecentsImpl) this.mImpl;
        overviewProxyRecentsImpl.getClass();
        overviewProxyRecentsImpl.mHandler = new Handler();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void toggleRecentApps() {
        if (isUserSetup$1()) {
            if (MultiStarManager.sRecentKeyConsumed) {
                MultiStarManager.sRecentKeyConsumed = false;
                return;
            }
            final OverviewProxyRecentsImpl overviewProxyRecentsImpl = (OverviewProxyRecentsImpl) this.mImpl;
            if (overviewProxyRecentsImpl.mLauncherProxyService.mLauncherProxy != null) {
                Runnable runnable = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyRecentsImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        LauncherProxyService launcherProxyService = OverviewProxyRecentsImpl.this.mLauncherProxyService;
                        try {
                            ILauncherProxy iLauncherProxy = launcherProxyService.mLauncherProxy;
                            if (iLauncherProxy != null) {
                                ((ILauncherProxy.Stub.Proxy) iLauncherProxy).onOverviewToggle();
                                for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).onToggleRecentApps();
                                }
                            }
                        } catch (RemoteException e) {
                            Log.e("OverviewProxyRecentsImpl", "Cannot send toggle recents through proxy service.", e);
                        }
                    }
                };
                if (!((KeyguardStateControllerImpl) overviewProxyRecentsImpl.mKeyguardStateController).mShowing) {
                    runnable.run();
                } else {
                    overviewProxyRecentsImpl.mActivityStarter.executeRunnableDismissingKeyguard(new OverviewProxyRecentsImpl$$ExternalSyntheticLambda0(overviewProxyRecentsImpl, runnable, 1), null, true, false, true);
                }
            }
        }
    }
}
