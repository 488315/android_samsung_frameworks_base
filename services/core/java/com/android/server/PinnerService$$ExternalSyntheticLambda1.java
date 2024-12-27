package com.android.server;

import android.app.ActivityManager;

import java.util.function.BiConsumer;

public final /* synthetic */ class PinnerService$$ExternalSyntheticLambda1 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((PinnerService) obj).pinAppsInternal(((Integer) obj2).intValue(), false);
                return;
            case 1:
                ((PinnerService) obj).pinAppsInternal(((Integer) obj2).intValue(), true);
                return;
            case 2:
                PinnerService pinnerService = (PinnerService) obj;
                Integer num = (Integer) obj2;
                int intValue = num.intValue();
                int i = PinnerService.PAGE_SIZE;
                pinnerService.updateActiveState(intValue, false);
                synchronized (pinnerService) {
                    try {
                        int intValue2 =
                                ((Integer) pinnerService.mPendingRepin.getOrDefault(num, -1))
                                        .intValue();
                        if (intValue2 == -1) {
                            return;
                        }
                        pinnerService.mPendingRepin.remove(num);
                        pinnerService.pinApp(intValue2, ActivityManager.getCurrentUser(), false);
                        return;
                    } finally {
                    }
                }
            default:
                int intValue3 = ((Integer) obj2).intValue();
                int i2 = PinnerService.PAGE_SIZE;
                ((PinnerService) obj).updateActiveState(intValue3, true);
                return;
        }
    }
}
