package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo;

import com.android.systemui.kairos.util.Either;
import com.android.systemui.statusbar.pipeline.mobile.data.model.DataConnectionState;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel;
import com.android.systemui.statusbar.pipeline.shared.data.model.DataActivityModelKt;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DemoMobileConnectionRepositoryKairos$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i;
        String str;
        Integer num;
        String str2 = "Carrier Merged Network";
        boolean z = true;
        int iIntValue = 0;
        int iIntValue2 = 0;
        int iIntValue3 = 0;
        switch (this.$r8$classId) {
            case 0:
                Either either = (Either) obj2;
                int i2 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                if (either instanceof Either.First) {
                    Integer num2 = ((FakeNetworkEventModel.Mobile) ((Either.First) either).value).level;
                    if (num2 != null) {
                        iIntValue = num2.intValue();
                    }
                } else {
                    if (!(either instanceof Either.Second)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    iIntValue = ((FakeWifiEventModel.CarrierMerged) ((Either.Second) either).value).level;
                }
                return Integer.valueOf(iIntValue);
            case 1:
                Either either2 = (Either) obj2;
                int i3 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                if (either2 instanceof Either.First) {
                    Integer num3 = ((FakeNetworkEventModel.Mobile) ((Either.First) either2).value).activity;
                    if (num3 != null) {
                        iIntValue3 = num3.intValue();
                    }
                } else {
                    if (!(either2 instanceof Either.Second)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    iIntValue3 = ((FakeWifiEventModel.CarrierMerged) ((Either.Second) either2).value).activity;
                }
                return DataActivityModelKt.toMobileDataActivityModel(iIntValue3);
            case 2:
                Either either3 = (Either) obj2;
                int i4 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile = (FakeNetworkEventModel.Mobile) (either3 instanceof Either.First ? ((Either.First) either3).value : null);
                return Boolean.valueOf(mobile != null ? mobile.carrierNetworkChange : false);
            case 3:
                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                int i5 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                if (zBooleanValue) {
                    MobileConnectionRepository.Companion.getClass();
                    i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS + 1;
                } else {
                    MobileConnectionRepository.Companion.getClass();
                    i = MobileConnectionRepository.Companion.DEFAULT_NUM_LEVELS;
                }
                return Integer.valueOf(i);
            case 4:
                Either either4 = (Either) obj2;
                int i6 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile2 = (FakeNetworkEventModel.Mobile) (either4 instanceof Either.First ? ((Either.First) either4).value : null);
                return Boolean.valueOf(mobile2 != null ? mobile2.roaming : false);
            case 5:
                Either either5 = (Either) obj2;
                int i7 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile3 = (FakeNetworkEventModel.Mobile) (either5 instanceof Either.First ? ((Either.First) either5).value : null);
                if (mobile3 != null && (str = mobile3.name) != null) {
                    str2 = str;
                }
                return new NetworkNameModel.IntentDerived(str2);
            case 6:
                Either either6 = (Either) obj2;
                int i8 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile4 = (FakeNetworkEventModel.Mobile) (either6 instanceof Either.First ? ((Either.First) either6).value : null);
                if (mobile4 != null) {
                    String str3 = mobile4.name + " " + mobile4.subId;
                    if (str3 != null) {
                        str2 = str3;
                    }
                }
                return new NetworkNameModel.SubscriptionDerived(str2);
            case 7:
                int i9 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                return Boolean.valueOf(((Either) obj2) instanceof Either.Second);
            case 8:
                Either either7 = (Either) obj2;
                int i10 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile5 = (FakeNetworkEventModel.Mobile) (either7 instanceof Either.First ? ((Either.First) either7).value : null);
                return Boolean.valueOf(mobile5 != null ? mobile5.slice : false);
            case 9:
                Either either8 = (Either) obj2;
                int i11 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile6 = (FakeNetworkEventModel.Mobile) (either8 instanceof Either.First ? ((Either.First) either8).value : null);
                return Integer.valueOf((mobile6 == null || (num = mobile6.carrierId) == null) ? -1 : num.intValue());
            case 10:
                Either either9 = (Either) obj2;
                int i12 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile7 = (FakeNetworkEventModel.Mobile) (either9 instanceof Either.First ? ((Either.First) either9).value : null);
                return Boolean.valueOf(mobile7 != null ? mobile7.roaming : false);
            case 11:
                Either either10 = (Either) obj2;
                int i13 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                FakeNetworkEventModel.Mobile mobile8 = (FakeNetworkEventModel.Mobile) (either10 instanceof Either.First ? ((Either.First) either10).value : null);
                if (mobile8 != null) {
                    return mobile8.name;
                }
                return null;
            case 12:
                Either either11 = (Either) obj2;
                int i14 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                if (either11 instanceof Either.First) {
                    Integer num4 = ((FakeNetworkEventModel.Mobile) ((Either.First) either11).value).level;
                    if (num4 == null || num4.intValue() <= 0) {
                        z = false;
                    }
                } else if (!(either11 instanceof Either.Second)) {
                    throw new NoWhenBranchMatchedException();
                }
                return Boolean.valueOf(z);
            case 13:
                int i15 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                return DataConnectionState.Connected;
            case 14:
                int i16 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                return Boolean.valueOf(((FakeNetworkEventModel.Mobile) obj2).inflateStrength);
            case 15:
                int i17 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                return Either.First.m2586boximpl((FakeNetworkEventModel.Mobile) obj2);
            case 16:
                int i18 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                return Either.Second.m2587boximpl((FakeWifiEventModel.CarrierMerged) obj2);
            case 17:
                int i19 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                return Boolean.valueOf(((FakeNetworkEventModel.Mobile) obj2).ntn);
            default:
                Either either12 = (Either) obj2;
                int i20 = DemoMobileConnectionRepositoryKairos.$r8$clinit;
                if (either12 instanceof Either.First) {
                    Integer num5 = ((FakeNetworkEventModel.Mobile) ((Either.First) either12).value).level;
                    if (num5 != null) {
                        iIntValue2 = num5.intValue();
                    }
                } else {
                    if (!(either12 instanceof Either.Second)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    iIntValue2 = ((FakeWifiEventModel.CarrierMerged) ((Either.Second) either12).value).level;
                }
                return Integer.valueOf(iIntValue2);
        }
    }
}
