package com.android.server.media;

import android.util.Slog;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda1(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                MediaRoute2ProviderServiceProxy.Connection connection =
                        (MediaRoute2ProviderServiceProxy.Connection) obj;
                MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy =
                        MediaRoute2ProviderServiceProxy.this;
                if (mediaRoute2ProviderServiceProxy.mActiveConnection == connection) {
                    if (MediaRoute2ProviderServiceProxy.DEBUG) {
                        Slog.d(
                                "MR2ProviderSvcProxy",
                                mediaRoute2ProviderServiceProxy + ": Service connection died");
                    }
                    mediaRoute2ProviderServiceProxy.disconnect();
                    break;
                }
                break;
            case 1:
                MediaRoute2ProviderServiceProxy.Connection connection2 =
                        (MediaRoute2ProviderServiceProxy.Connection) obj;
                MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy2 =
                        MediaRoute2ProviderServiceProxy.this;
                if (mediaRoute2ProviderServiceProxy2.mActiveConnection == connection2) {
                    mediaRoute2ProviderServiceProxy2.mConnectionReady = true;
                    if (mediaRoute2ProviderServiceProxy2.mLastDiscoveryPreference != null) {
                        mediaRoute2ProviderServiceProxy2.updateDiscoveryPreference(
                                mediaRoute2ProviderServiceProxy2
                                                .mLastDiscoveryPreferenceIncludesThisPackage
                                        ? Set.of(
                                                mediaRoute2ProviderServiceProxy2.mComponentName
                                                        .getPackageName())
                                        : Set.of(),
                                mediaRoute2ProviderServiceProxy2.mLastDiscoveryPreference);
                        break;
                    }
                }
                break;
            default:
                MediaRoute2ProviderServiceProxy.m651$$Nest$monServiceDisconnectedInternal(
                        MediaRoute2ProviderServiceProxy.this);
                break;
        }
    }
}
