package com.android.server.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.android.server.desktopmode.DefaultBlocker;
import com.samsung.android.desktopmode.IDesktopModeBlocker;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class BlockerManager {
    public static final String TAG = "[DMS]" + BlockerManager.class.getSimpleName();
    public final Map mBlockers = new ArrayMap();
    public final DefaultBlocker mDefaultBlocker;
    public final Injector mInjector;
    public final IStateManager mStateManager;

    public class DesktopModeBlockerInfo implements IBinder.DeathRecipient {
        public final IDesktopModeBlocker blocker;
        public final String name;
        public final int pid;
        public final int uid;

        public DesktopModeBlockerInfo(IDesktopModeBlocker iDesktopModeBlocker, String str, int i, int i2) {
            this.blocker = iDesktopModeBlocker;
            this.name = str;
            this.pid = i;
            this.uid = i2;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.m11v(BlockerManager.TAG, "binderDied(): " + this);
            synchronized (BlockerManager.this.mBlockers) {
                BlockerManager.this.mBlockers.remove(this.blocker.asBinder());
            }
            this.blocker.asBinder().unlinkToDeath(this, 0);
        }

        public String toString() {
            return DesktopModeBlockerInfo.class.getSimpleName() + "(name=" + this.name + ", pid=" + this.pid + ", uid=" + this.uid + ")";
        }
    }

    public BlockerManager(Context context, IStateManager iStateManager, ActivityManager activityManager, DisplayManager displayManager, Injector injector) {
        this.mDefaultBlocker = new DefaultBlocker(context, activityManager, displayManager);
        this.mStateManager = iStateManager;
        this.mInjector = injector;
    }

    public boolean registerBlocker(IDesktopModeBlocker iDesktopModeBlocker, String str) {
        synchronized (this.mBlockers) {
            IBinder asBinder = iDesktopModeBlocker.asBinder();
            try {
                DesktopModeBlockerInfo desktopModeBlockerInfo = new DesktopModeBlockerInfo(iDesktopModeBlocker, str, this.mInjector.binderGetCallingPid(), this.mInjector.binderGetCallingUid());
                asBinder.linkToDeath(desktopModeBlockerInfo, 0);
                this.mBlockers.put(asBinder, desktopModeBlockerInfo);
                this.mStateManager.notifyScheduleUpdateDesktopMode(false);
            } catch (RemoteException unused) {
                return false;
            }
        }
        return true;
    }

    public boolean unregisterBlocker(IDesktopModeBlocker iDesktopModeBlocker) {
        synchronized (this.mBlockers) {
            DesktopModeBlockerInfo desktopModeBlockerInfo = (DesktopModeBlockerInfo) this.mBlockers.remove(iDesktopModeBlocker.asBinder());
            if (desktopModeBlockerInfo == null) {
                return false;
            }
            desktopModeBlockerInfo.blocker.asBinder().unlinkToDeath(desktopModeBlockerInfo, 0);
            if (this.mBlockers.isEmpty()) {
                this.mStateManager.notifyScheduleUpdateDesktopMode(true);
            }
            return true;
        }
    }

    public final DesktopModeBlockerInfo getDefaultBlockerInfo(State state) {
        DefaultBlocker.BlockerImpl blocker = this.mDefaultBlocker.getBlocker(state);
        if (blocker != null) {
            return new DesktopModeBlockerInfo(blocker, DefaultBlocker.reasonToString(blocker.reasonCode), this.mInjector.binderGetCallingPid(), this.mInjector.binderGetCallingUid());
        }
        return null;
    }

    public DesktopModeBlockerInfo getBlocker(State state) {
        DesktopModeBlockerInfo defaultBlockerInfo = getDefaultBlockerInfo(state);
        if (defaultBlockerInfo != null) {
            return defaultBlockerInfo;
        }
        synchronized (this.mBlockers) {
            for (DesktopModeBlockerInfo desktopModeBlockerInfo : this.mBlockers.values()) {
                if (desktopModeBlockerInfo != null) {
                    return desktopModeBlockerInfo;
                }
            }
            return null;
        }
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + BlockerManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("DefaultBlocker=" + getDefaultBlockerInfo(this.mStateManager.getState()));
        synchronized (this.mBlockers) {
            indentingPrintWriter.println("mBlockers (" + this.mBlockers.size() + "):");
            indentingPrintWriter.increaseIndent();
            Iterator it = this.mBlockers.values().iterator();
            while (it.hasNext()) {
                indentingPrintWriter.println((DesktopModeBlockerInfo) it.next());
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }
}
