package com.android.server.timezonedetector.location;

import android.util.IndentingPrintWriter;

import java.time.Duration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisabledLocationTimeZoneProvider extends LocationTimeZoneProvider {
    @Override // com.android.server.timezonedetector.Dumpable
    public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("{DisabledLocationTimeZoneProvider}");
            indentingPrintWriter.println("mProviderName=" + this.mProviderName);
            indentingPrintWriter.println("mCurrentState=" + this.mCurrentState);
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final void onDestroy() {}

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final boolean onInitialize() {
        return false;
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final void onStartUpdates(Duration duration, Duration duration2) {
        throw new UnsupportedOperationException("Provider is disabled");
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final void onStopUpdates() {
        throw new UnsupportedOperationException("Provider is disabled");
    }

    public final String toString() {
        String str;
        synchronized (this.mSharedLock) {
            str =
                    "DisabledLocationTimeZoneProvider{mProviderName="
                            + this.mProviderName
                            + ", mCurrentState="
                            + this.mCurrentState
                            + '}';
        }
        return str;
    }
}
