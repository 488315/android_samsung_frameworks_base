package com.android.server.stats.pull;

import android.telephony.SubscriptionInfo;

import com.android.server.stats.pull.netstats.NetworkStatsExt;
import com.android.server.stats.pull.netstats.SubInfo;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatsPullAtomService$$ExternalSyntheticLambda15
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StatsPullAtomService$$ExternalSyntheticLambda15(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                NetworkStatsExt networkStatsExt = (NetworkStatsExt) obj2;
                NetworkStatsExt networkStatsExt2 = (NetworkStatsExt) obj;
                int i2 = StatsPullAtomService.RANDOM_SEED;
                networkStatsExt2.getClass();
                if (Arrays.equals(networkStatsExt2.transports, networkStatsExt.transports)
                        && networkStatsExt2.slicedByFgbg == networkStatsExt.slicedByFgbg
                        && networkStatsExt2.slicedByTag == networkStatsExt.slicedByTag
                        && networkStatsExt2.slicedByMetered == networkStatsExt.slicedByMetered
                        && networkStatsExt2.ratType == networkStatsExt.ratType
                        && Objects.equals(networkStatsExt2.subInfo, networkStatsExt.subInfo)
                        && networkStatsExt2.oemManaged == networkStatsExt.oemManaged
                        && networkStatsExt2.isTypeProxy == networkStatsExt.isTypeProxy) {
                    break;
                }
                break;
            default:
                if (((SubInfo) obj).subId == ((SubscriptionInfo) obj2).getSubscriptionId()) {
                    break;
                }
                break;
        }
        return true;
    }
}
