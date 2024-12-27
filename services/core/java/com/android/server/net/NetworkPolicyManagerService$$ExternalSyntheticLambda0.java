package com.android.server.net;

import java.util.function.IntConsumer;

public final /* synthetic */ class NetworkPolicyManagerService$$ExternalSyntheticLambda0
        implements IntConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NetworkPolicyManagerService$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                ((NetworkPolicyManagerService) obj).updateRulesForDataUsageRestrictionsUL(i);
                return;
            case 1:
                NetworkPolicyManagerService networkPolicyManagerService =
                        (NetworkPolicyManagerService) obj;
                synchronized (networkPolicyManagerService.mUidRulesFirstLock) {
                    try {
                        int i3 =
                                (networkPolicyManagerService
                                                                .updateBlockedReasonsForRestrictedModeUL(
                                                                        i)
                                                        & 8)
                                                != 0
                                        ? 0
                                        : 1;
                        if (i3 != 0) {
                            networkPolicyManagerService.mUidFirewallRestrictedModeRules.append(
                                    i, i3);
                        }
                    } finally {
                    }
                }
                return;
            case 2:
                NetworkPolicyManagerService networkPolicyManagerService2 =
                        (NetworkPolicyManagerService) obj;
                synchronized (networkPolicyManagerService2.mUidRulesFirstLock) {
                    networkPolicyManagerService2.updateRulesForPowerRestrictionsUL(i, -1);
                }
                return;
            case 3:
                ((NetworkPolicyManagerService) obj).updateRulesForPowerRestrictionsUL(i, -1);
                return;
            default:
                ((NetworkPolicyManagerService.NetworkPolicyManagerInternalImpl) obj)
                        .this$0.updateRulesForPowerRestrictionsUL(i, -1);
                return;
        }
    }
}
