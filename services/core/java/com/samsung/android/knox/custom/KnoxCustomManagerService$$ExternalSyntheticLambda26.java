package com.samsung.android.knox.custom;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda26
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda26(
            KnoxCustomManagerService knoxCustomManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = knoxCustomManagerService;
    }

    public final Object getOrThrow() {
        int i = this.$r8$classId;
        KnoxCustomManagerService knoxCustomManagerService = this.f$0;
        switch (i) {
            case 0:
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setDeveloperOptionsHidden$35();
            case 1:
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getFavoriteAppsMaxCount$134();
            case 2:
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$startProKioskMode$28();
            case 3:
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getLockScreenHideOwnerInfo$151();
            case 4:
                String str5 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getHomeScreenMode$139();
            case 5:
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$removeLockScreen$54();
            case 6:
                String str7 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setSystemSoundsSilent$86();
            case 7:
                String str8 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getMobileNetworkType$106();
            case 8:
                String str9 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getAppsButtonState$130();
            case 9:
                String str10 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getZeroPageState$136();
            case 10:
                String str11 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$launchProkioskHomeActivity$153();
            case 11:
                String str12 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getEthernetState$64();
            case 12:
                String str13 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getLockScreenHiddenItems$73();
            case 13:
                String str14 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$startSmartView$51();
            case 14:
                String str15 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$clearDexLoadingLogo$6();
            case 15:
                String str16 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$powerOff$118();
            case 16:
                String str17 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getMacAddress$117();
            case 17:
                String str18 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getLoadingLogoPath$13();
            case 18:
                String str19 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$stopTcpDump$157();
            default:
                String str20 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$getUsbConnectionType$122();
        }
    }
}
