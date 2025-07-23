package android.media.metrics;

import android.os.Bundle;

/* loaded from: classes3.dex */
public abstract class Event {
    Bundle mMetricsBundle;
    final long mTimeSinceCreatedMillis;

    Event() {
        this.mMetricsBundle = new Bundle();
        this.mTimeSinceCreatedMillis = -1L;
    }

    Event(long j, Bundle bundle) {
        new Bundle();
        this.mTimeSinceCreatedMillis = j;
        this.mMetricsBundle = bundle;
    }

    public long getTimeSinceCreatedMillis() {
        return this.mTimeSinceCreatedMillis;
    }

    public Bundle getMetricsBundle() {
        return this.mMetricsBundle;
    }
}
