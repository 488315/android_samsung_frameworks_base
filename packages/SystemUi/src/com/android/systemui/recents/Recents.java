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
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.statusbar.CommandQueue;
import com.samsung.android.systemui.multistar.MultiStarManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Recents implements CoreStartable, CommandQueue.Callbacks {
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
        if (isUserSetup()) {
            this.mImpl.getClass();
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.mImpl.getClass();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void hideRecentApps(boolean z, boolean z2) {
        IOverviewProxy iOverviewProxy;
        if (isUserSetup() && (iOverviewProxy = ((OverviewProxyRecentsImpl) this.mImpl).mOverviewProxyService.mOverviewProxy) != null) {
            try {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onOverviewHidden(z, z2);
            } catch (RemoteException e) {
                Log.e("OverviewProxyRecentsImpl", "Failed to send overview hide event to launcher.", e);
            }
        }
    }

    public final boolean isUserSetup() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        return (Settings.Global.getInt(contentResolver, "device_provisioned", 0) == 0 || Settings.Secure.getInt(contentResolver, "user_setup_complete", 0) == 0) ? false : true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        this.mImpl.getClass();
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        this.mImpl.getClass();
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void preloadRecentApps() {
        if (isUserSetup()) {
            this.mImpl.getClass();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) {
        if (!isUserSetup()) {
            Log.d("ThreeFinger", "isUserSetup : false");
            return;
        }
        OverviewProxyRecentsImpl overviewProxyRecentsImpl = (OverviewProxyRecentsImpl) this.mImpl;
        if (overviewProxyRecentsImpl.mOverviewProxyService.mOverviewProxy != null) {
            new OverviewProxyRecentsImpl$$ExternalSyntheticLambda2(overviewProxyRecentsImpl, keyEvent, 1).run();
        }
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showRecentApps(boolean z) {
        IOverviewProxy iOverviewProxy;
        if (isUserSetup() && (iOverviewProxy = ((OverviewProxyRecentsImpl) this.mImpl).mOverviewProxyService.mOverviewProxy) != null) {
            try {
                ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onOverviewShown(z);
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
        if (isUserSetup()) {
            int i = 0;
            if (MultiStarManager.sRecentKeyConsumed) {
                MultiStarManager.sRecentKeyConsumed = false;
                return;
            }
            final OverviewProxyRecentsImpl overviewProxyRecentsImpl = (OverviewProxyRecentsImpl) this.mImpl;
            if (overviewProxyRecentsImpl.mOverviewProxyService.mOverviewProxy != null) {
                Runnable runnable = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyRecentsImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OverviewProxyService overviewProxyService = OverviewProxyRecentsImpl.this.mOverviewProxyService;
                        try {
                            IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
                            if (iOverviewProxy == null) {
                                return;
                            }
                            ((IOverviewProxy.Stub.Proxy) iOverviewProxy).onOverviewToggle();
                            List list = overviewProxyService.mConnectionCallbacks;
                            int size = ((ArrayList) list).size();
                            while (true) {
                                size--;
                                if (size < 0) {
                                    return;
                                } else {
                                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) list).get(size)).onToggleRecentApps();
                                }
                            }
                        } catch (RemoteException e) {
                            Log.e("OverviewProxyRecentsImpl", "Cannot send toggle recents through proxy service.", e);
                        }
                    }
                };
                if (((Boolean) ((Optional) overviewProxyRecentsImpl.mCentralSurfacesOptionalLazy.get()).map(new OverviewProxyRecentsImpl$$ExternalSyntheticLambda1()).orElse(Boolean.FALSE)).booleanValue()) {
                    overviewProxyRecentsImpl.mActivityStarter.executeRunnableDismissingKeyguard(new OverviewProxyRecentsImpl$$ExternalSyntheticLambda2(overviewProxyRecentsImpl, runnable, i), null, true, false, true);
                } else {
                    runnable.run();
                }
            }
        }
    }
}
