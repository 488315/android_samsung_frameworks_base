package com.android.server.timedetector;

import android.app.time.UnixEpochTime;
import android.os.ShellCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NetworkTimeSuggestion {
    public ArrayList mDebugInfo;
    public final int mUncertaintyMillis;
    public final UnixEpochTime mUnixEpochTime;

    public NetworkTimeSuggestion(UnixEpochTime unixEpochTime, int i) {
        Objects.requireNonNull(unixEpochTime);
        this.mUnixEpochTime = unixEpochTime;
        if (i < 0) {
            throw new IllegalArgumentException("uncertaintyMillis < 0");
        }
        this.mUncertaintyMillis = i;
    }

    public static NetworkTimeSuggestion parseCommandLineArg(ShellCommand shellCommand) {
        Long l = null;
        Long l2 = null;
        Integer num = null;
        while (true) {
            String nextArg = shellCommand.getNextArg();
            if (nextArg == null) {
                if (l == null) {
                    throw new IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (l2 == null) {
                    throw new IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                if (num == null) {
                    throw new IllegalArgumentException("No uncertaintyMillis specified.");
                }
                NetworkTimeSuggestion networkTimeSuggestion =
                        new NetworkTimeSuggestion(
                                new UnixEpochTime(l.longValue(), l2.longValue()), num.intValue());
                networkTimeSuggestion.addDebugInfo("Command line injection");
                return networkTimeSuggestion;
            }
            switch (nextArg) {
                case "--reference_time":
                case "--elapsed_realtime":
                    l = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--unix_epoch_time":
                    l2 = Long.valueOf(Long.parseLong(shellCommand.getNextArgRequired()));
                    break;
                case "--uncertainty_millis":
                    num = Integer.valueOf(Integer.parseInt(shellCommand.getNextArgRequired()));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option: ".concat(nextArg));
            }
        }
    }

    public final void addDebugInfo(String... strArr) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new ArrayList();
        }
        this.mDebugInfo.addAll(Arrays.asList(strArr));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NetworkTimeSuggestion)) {
            return false;
        }
        NetworkTimeSuggestion networkTimeSuggestion = (NetworkTimeSuggestion) obj;
        return this.mUnixEpochTime.equals(networkTimeSuggestion.mUnixEpochTime)
                && this.mUncertaintyMillis == networkTimeSuggestion.mUncertaintyMillis;
    }

    public final int hashCode() {
        return Objects.hash(this.mUnixEpochTime, Integer.valueOf(this.mUncertaintyMillis));
    }

    public final String toString() {
        return "NetworkTimeSuggestion{mUnixEpochTime="
                + this.mUnixEpochTime
                + ", mUncertaintyMillis="
                + this.mUncertaintyMillis
                + ", mDebugInfo="
                + this.mDebugInfo
                + '}';
    }
}
