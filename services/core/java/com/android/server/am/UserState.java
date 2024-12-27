package com.android.server.am;

import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.util.ProgressReporter;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UserState {
    public final UserHandle mHandle;
    public final ProgressReporter mUnlockProgress;
    public boolean switching;
    public final ArrayList mStopCallbacks = new ArrayList();
    public final ArrayList mKeyEvictedCallbacks = new ArrayList();
    public int state = 0;
    public int lastState = 0;
    public final ArrayMap mProviderLastReportedFg = new ArrayMap();

    public UserState(UserHandle userHandle) {
        this.mHandle = userHandle;
        this.mUnlockProgress = new ProgressReporter(userHandle.getIdentifier());
    }

    public static String stateToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 4
                                                ? i != 5 ? Integer.toString(i) : "SHUTDOWN"
                                                : "STOPPING"
                                        : "RUNNING_UNLOCKED"
                                : "RUNNING_UNLOCKING"
                        : "RUNNING_LOCKED"
                : "BOOTING";
    }

    public final void setState(int i) {
        if (i == this.state) {
            return;
        }
        int identifier = this.mHandle.getIdentifier();
        if (this.state != 0) {
            Trace.asyncTraceEnd(64L, stateToString(this.state) + " " + identifier, identifier);
        }
        if (i != 5) {
            Trace.asyncTraceBegin(64L, stateToString(i) + " " + identifier, identifier);
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        identifier, "User ", " state changed from ");
        m.append(stateToString(this.state));
        m.append(" to ");
        m.append(stateToString(i));
        Slog.i("ActivityManager", m.toString());
        EventLog.writeEvent(30051, Integer.valueOf(identifier), Integer.valueOf(i));
        this.lastState = this.state;
        this.state = i;
    }

    public final boolean setState(int i, int i2) {
        if (this.state == i) {
            setState(i2);
            return true;
        }
        Slog.w(
                "ActivityManager",
                "Expected user "
                        + this.mHandle.getIdentifier()
                        + " in state "
                        + stateToString(i)
                        + " but was in state "
                        + stateToString(this.state));
        Slog.i("ActivityManager", "!@Boot: setStateFail, finishUserUnlocking");
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[UserState: id=");
        sb.append(this.mHandle.getIdentifier());
        sb.append(", state=");
        sb.append(stateToString(this.state));
        sb.append(", lastState=");
        sb.append(stateToString(this.lastState));
        sb.append(", switching=");
        return OptionalBool$$ExternalSyntheticOutline0.m("]", sb, this.switching);
    }
}
