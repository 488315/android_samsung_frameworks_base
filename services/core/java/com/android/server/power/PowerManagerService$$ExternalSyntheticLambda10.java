package com.android.server.power;

import com.android.server.power.PowerManagerService;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerManagerService$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PowerManagerService$$ExternalSyntheticLambda10(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                PowerManagerService powerManagerService = (PowerManagerService) obj2;
                PowerManagerService.HdrBrightnessLimitLock hdrBrightnessLimitLock = (PowerManagerService.HdrBrightnessLimitLock) obj;
                SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
                powerManagerService.getClass();
                powerManagerService.mHdrBrightnessUpperLimit = hdrBrightnessLimitLock.mUpperLimit;
                powerManagerService.mHdrBrightnessLimitPeriod = hdrBrightnessLimitLock.mBrightnessLimitPeriod;
                break;
            case 1:
                PowerManagerService.AnonymousClass1 anonymousClass1 = (PowerManagerService.AnonymousClass1) obj2;
                anonymousClass1.getClass();
                PowerManagerService.m796$$Nest$mdisableAbusiveWakeLockInternal(PowerManagerService.this, ((PowerManagerService.WakeLock) obj).mLock, false);
                break;
            default:
                PowerManagerService.AnonymousClass1 anonymousClass12 = (PowerManagerService.AnonymousClass1) obj2;
                anonymousClass12.getClass();
                PowerManagerService.m796$$Nest$mdisableAbusiveWakeLockInternal(PowerManagerService.this, ((PowerManagerService.WakeLock) obj).mLock, true);
                break;
        }
    }
}
