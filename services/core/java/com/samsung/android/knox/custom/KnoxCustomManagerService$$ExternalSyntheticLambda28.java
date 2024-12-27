package com.samsung.android.knox.custom;

import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda28
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda28(
            int i, int i2, Object obj, Object obj2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$2 = i;
        this.f$1 = obj2;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda28(
            KnoxCustomManagerService knoxCustomManagerService, int i, String[] strArr) {
        this.$r8$classId = 4;
        this.f$0 = knoxCustomManagerService;
        this.f$2 = i;
        this.f$1 = strArr;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda28(
            KnoxCustomManagerService knoxCustomManagerService, Bundle bundle, int i) {
        this.$r8$classId = 0;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = bundle;
        this.f$2 = i;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda28(
            KnoxCustomManagerService knoxCustomManagerService,
            ParcelFileDescriptor parcelFileDescriptor,
            int i) {
        this.$r8$classId = 3;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = parcelFileDescriptor;
        this.f$2 = i;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService =
                        (KnoxCustomManagerService) this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                int i = this.f$2;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setQuickPanelItemsInternal$110(bundle, i);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 =
                        (KnoxCustomManagerService) this.f$0;
                int i2 = this.f$2;
                StringBuffer stringBuffer = (StringBuffer) this.f$1;
                String str2 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setQuickPanelItems$109(i2, stringBuffer);
            case 2:
                return KnoxCustomManagerService.lambda$setUsbDeviceDefaultPackage$45(
                        (UsbDevice) this.f$0, this.f$2, (String) this.f$1);
            case 3:
                KnoxCustomManagerService knoxCustomManagerService3 =
                        (KnoxCustomManagerService) this.f$0;
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$1;
                int i3 = this.f$2;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setDexLoadingLogo$5(
                        parcelFileDescriptor, i3);
            default:
                KnoxCustomManagerService knoxCustomManagerService4 =
                        (KnoxCustomManagerService) this.f$0;
                int i4 = this.f$2;
                String[] strArr = (String[]) this.f$1;
                String str4 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService4.lambda$setLockScreenShortcut$120(i4, strArr);
        }
    }
}
