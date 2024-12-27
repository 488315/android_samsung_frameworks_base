package com.android.server.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.util.ArrayMap;
import com.samsung.android.desktopmode.IDesktopModeBlocker;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BlockerManager {
    public final Map mBlockers = new ArrayMap();
    public final DefaultBlocker mDefaultBlocker;
    public final Injector mInjector;
    public final IStateManager mStateManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DesktopModeBlockerInfo implements IBinder.DeathRecipient {
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
        public final void binderDied() {
            Log.v("[DMS]BlockerManager", "binderDied(): " + this);
            synchronized (BlockerManager.this.mBlockers) {
                ((ArrayMap) BlockerManager.this.mBlockers).remove(this.blocker.asBinder());
            }
            this.blocker.asBinder().unlinkToDeath(this, 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(DesktopModeBlockerInfo.class.getSimpleName());
            sb.append("(name=");
            sb.append(this.name);
            sb.append(", pid=");
            sb.append(this.pid);
            sb.append(", uid=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")");
        }
    }

    public BlockerManager(Context context, IStateManager iStateManager, ActivityManager activityManager, DisplayManager displayManager, Injector injector) {
        this.mDefaultBlocker = new DefaultBlocker(context, activityManager, displayManager);
        this.mStateManager = iStateManager;
        this.mInjector = injector;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00fe A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.desktopmode.BlockerManager.DesktopModeBlockerInfo getDefaultBlockerInfo(com.android.server.desktopmode.StateManager.InternalState r12) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.desktopmode.BlockerManager.getDefaultBlockerInfo(com.android.server.desktopmode.StateManager$InternalState):com.android.server.desktopmode.BlockerManager$DesktopModeBlockerInfo");
    }
}
