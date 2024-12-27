package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda12 implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda12(KnoxCustomManagerService knoxCustomManagerService, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = z;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                boolean z = this.f$1;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setDeviceSpeakerEnabledState$65(z);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                boolean z2 = this.f$1;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setMultiWindowState$76(z2);
            case 2:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                boolean z3 = this.f$1;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setHardKeyIntentState$137(z3);
            case 3:
                KnoxCustomManagerService knoxCustomManagerService4 = this.f$0;
                boolean z4 = this.f$1;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService4.lambda$setDisplayMirroringState$66(z4);
            case 4:
                KnoxCustomManagerService knoxCustomManagerService5 = this.f$0;
                boolean z5 = this.f$1;
                String str5 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService5.lambda$setMobileDataRoamingState$38(z5);
            case 5:
                KnoxCustomManagerService knoxCustomManagerService6 = this.f$0;
                boolean z6 = this.f$1;
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService6.lambda$setLcdBacklightState$71(z6);
            case 6:
                KnoxCustomManagerService knoxCustomManagerService7 = this.f$0;
                boolean z7 = this.f$1;
                String str7 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService7.lambda$setMobileDataState$39(z7);
            case 7:
                KnoxCustomManagerService knoxCustomManagerService8 = this.f$0;
                boolean z8 = this.f$1;
                String str8 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService8.lambda$setWifiConnectionMonitorState$46(z8);
            case 8:
                KnoxCustomManagerService knoxCustomManagerService9 = this.f$0;
                boolean z9 = this.f$1;
                String str9 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService9.lambda$setBluetoothState$33(z9);
            case 9:
                KnoxCustomManagerService knoxCustomManagerService10 = this.f$0;
                boolean z10 = this.f$1;
                String str10 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService10.lambda$setTorchOnVolumeButtonsState$91(z10);
            case 10:
                KnoxCustomManagerService knoxCustomManagerService11 = this.f$0;
                boolean z11 = this.f$1;
                String str11 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService11.lambda$setChargingLEDState$34(z11);
            case 11:
                KnoxCustomManagerService knoxCustomManagerService12 = this.f$0;
                boolean z12 = this.f$1;
                String str12 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService12.lambda$setPowerDialogCustomItemsStateLocal$145(z12);
            case 12:
                KnoxCustomManagerService knoxCustomManagerService13 = this.f$0;
                boolean z13 = this.f$1;
                String str13 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService13.lambda$setAdbState$29(z13);
            case 13:
                KnoxCustomManagerService knoxCustomManagerService14 = this.f$0;
                boolean z14 = this.f$1;
                String str14 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService14.lambda$setChargerConnectionSoundEnabledState$62(z14);
            default:
                KnoxCustomManagerService knoxCustomManagerService15 = this.f$0;
                boolean z15 = this.f$1;
                String str15 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService15.lambda$setStayAwakeState$44(z15);
        }
    }
}
