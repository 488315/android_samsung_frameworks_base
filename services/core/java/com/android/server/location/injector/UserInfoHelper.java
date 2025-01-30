package com.android.server.location.injector;

import android.app.IUidObserver;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.server.location.eventlog.LocationEventLog;
import java.io.FileDescriptor;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class UserInfoHelper {
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public interface UserListener {
        void onUserChanged(int i, int i2);
    }

    public abstract void dump(FileDescriptor fileDescriptor, IndentingPrintWriter indentingPrintWriter, String[] strArr);

    public abstract int getCurrentUserId();

    public abstract int[] getProfileIds(int i);

    public abstract int[] getRunningUserIds();

    public abstract boolean isCurrentUserId(int i);

    public abstract boolean isVisibleUserId(int i);

    public abstract void registerUidObserver(IUidObserver iUidObserver);

    public final void addListener(UserListener userListener) {
        this.mListeners.add(userListener);
    }

    public final void removeListener(UserListener userListener) {
        this.mListeners.remove(userListener);
    }

    public final void dispatchOnUserStarted(int i) {
        Log.d("LocationManagerService", "u" + i + " started");
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((UserListener) it.next()).onUserChanged(i, 2);
        }
    }

    public final void dispatchOnUserStopped(int i) {
        Log.d("LocationManagerService", "u" + i + " stopped");
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((UserListener) it.next()).onUserChanged(i, 3);
        }
    }

    public final void dispatchOnCurrentUserChanged(int i, int i2) {
        int[] profileIds = getProfileIds(i);
        int[] profileIds2 = getProfileIds(i2);
        Log.d("LocationManagerService", "current user changed from u" + Arrays.toString(profileIds) + " to u" + Arrays.toString(profileIds2));
        LocationEventLog.EVENT_LOG.logUserSwitched(i, i2);
        Iterator it = this.mListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            UserListener userListener = (UserListener) it.next();
            for (int i3 : profileIds) {
                userListener.onUserChanged(i3, 1);
            }
        }
        Iterator it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            UserListener userListener2 = (UserListener) it2.next();
            for (int i4 : profileIds2) {
                userListener2.onUserChanged(i4, 1);
            }
        }
    }

    public final void dispatchOnVisibleUserChanged(int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("visibility of u");
        sb.append(i);
        sb.append(" changed to ");
        sb.append(z ? "visible" : "invisible");
        Log.d("LocationManagerService", sb.toString());
        LocationEventLog.EVENT_LOG.logUserVisibilityChanged(i, z);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((UserListener) it.next()).onUserChanged(i, 4);
        }
    }
}
