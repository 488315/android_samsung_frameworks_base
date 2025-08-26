package com.android.systemui.statusbar.pipeline.battery.data.repository;

import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class BatteryRepository$batteryState$1$callback$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ BatteryRepository$batteryState$1$callback$1$$ExternalSyntheticLambda0(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return BatteryCallbackState.copy$default((BatteryCallbackState) obj, null, false, this.f$0, false, 27);
            case 1:
                return this.f$0 ? new BatteryCallbackState(null, false, false, false, true, 15, null) : BatteryCallbackState.copy$default((BatteryCallbackState) obj, null, false, false, false, 15);
            default:
                return BatteryCallbackState.copy$default((BatteryCallbackState) obj, null, false, false, this.f$0, 23);
        }
    }
}
