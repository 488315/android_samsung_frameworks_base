package com.android.server.desktopmode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArrayMap;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.IDesktopModeListener;
import com.samsung.android.desktopmode.SemDesktopModeState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class SemDesktopModeStateNotifier {
    static final int REGISTER_LIMIT = 20;
    public final Context mContext;
    public final Injector mInjector;
    public final Object mInnerLock = new Object();
    public final Map mListeners = new ArrayMap();

    public final class ListenerInfo implements IBinder.DeathRecipient {
        public final IDesktopModeListener listener;
        public final String name;
        public final int pid;
        public final int uid;

        public ListenerInfo(IDesktopModeListener iDesktopModeListener, String str, int i, int i2) {
            this.name = str;
            this.pid = i;
            this.uid = i2;
            this.listener = iDesktopModeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            if (DesktopModeFeature.DEBUG) {
                Log.v("[DMS]SemDesktopModeStateNotifier", "binderDied(): " + this);
            }
            synchronized (SemDesktopModeStateNotifier.this.mInnerLock) {
                ((ArrayMap) SemDesktopModeStateNotifier.this.mListeners)
                        .remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(ListenerInfo.class.getSimpleName());
            sb.append("(name=");
            sb.append(this.name);
            sb.append(", pid=");
            sb.append(this.pid);
            sb.append(", uid=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")");
        }
    }

    public SemDesktopModeStateNotifier(Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
    }

    public static void enforceRegisterLimitLocked(ListenerInfo listenerInfo, Map map) {
        if (listenerInfo.uid == 1000) {
            return;
        }
        int i = 0;
        for (ListenerInfo listenerInfo2 : ((ArrayMap) map).values()) {
            if (listenerInfo2.pid == listenerInfo.pid
                    && listenerInfo2.uid == listenerInfo.uid
                    && (i = i + 1) == 20) {
                throw new SecurityException(
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Only 20 listener", "s", " per pid/uid is allowed"));
            }
        }
    }

    public final void notifyDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
        ArrayList arrayList;
        synchronized (this.mInnerLock) {
            arrayList = new ArrayList(((ArrayMap) this.mListeners).values());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((ListenerInfo) it.next()).listener.onDesktopModeStateChanged(semDesktopModeState);
            } catch (RemoteException e) {
                Log.e(
                        "[DMS]SemDesktopModeStateNotifier",
                        "Failed to notify DesktopModeListener",
                        e);
            }
        }
        if (semDesktopModeState.state == 40) {
            boolean z = semDesktopModeState.enabled == 4;
            Context context = this.mContext;
            Intent addFlags =
                    new Intent(
                                    z
                                            ? UiModeManager.SEM_ACTION_ENTER_DESKTOP_MODE
                                            : UiModeManager.SEM_ACTION_EXIT_DESKTOP_MODE)
                            .putExtra(
                                    "android.app.extra.DISPLAY_TYPE",
                                    semDesktopModeState.getDisplayType())
                            .addFlags(16777216);
            UserHandle userHandle = UserHandle.ALL;
            context.sendBroadcastAsUser(addFlags, userHandle);
            this.mContext.sendBroadcastAsUser(
                    new Intent(
                                    z
                                            ? "android.app.action.ENTER_KNOX_DESKTOP_MODE"
                                            : "android.app.action.EXIT_KNOX_DESKTOP_MODE")
                            .putExtra(
                                    "android.app.extra.DISPLAY_TYPE",
                                    semDesktopModeState.getDisplayType())
                            .addFlags(16777216),
                    userHandle);
        }
    }
}
