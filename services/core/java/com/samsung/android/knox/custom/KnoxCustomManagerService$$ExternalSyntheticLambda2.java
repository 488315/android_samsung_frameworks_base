package com.samsung.android.knox.custom;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class KnoxCustomManagerService$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingSupplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KnoxCustomManagerService f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda2(
            KnoxCustomManagerService knoxCustomManagerService, Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = obj;
        this.f$2 = obj2;
    }

    public /* synthetic */ KnoxCustomManagerService$$ExternalSyntheticLambda2(
            KnoxCustomManagerService knoxCustomManagerService,
            String str,
            ComponentName componentName) {
        this.$r8$classId = 3;
        this.f$0 = knoxCustomManagerService;
        this.f$1 = str;
        this.f$2 = componentName;
    }

    public final Object getOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                KnoxCustomManagerService knoxCustomManagerService = this.f$0;
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$1;
                ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) this.f$2;
                String str = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService.lambda$setShuttingDownAnimation$102(
                        parcelFileDescriptor, parcelFileDescriptor2);
            case 1:
                KnoxCustomManagerService knoxCustomManagerService2 = this.f$0;
                ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) this.f$1;
                String str2 = (String) this.f$2;
                String str3 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService2.lambda$setShuttingDownAnimationSub$103(
                        parcelFileDescriptor3, str2);
            case 2:
                KnoxCustomManagerService knoxCustomManagerService3 = this.f$0;
                String str4 = (String) this.f$1;
                String str5 = (String) this.f$2;
                String str6 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService3.lambda$setLockscreenWallpaper$75(str4, str5);
            case 3:
                KnoxCustomManagerService knoxCustomManagerService4 = this.f$0;
                String str7 = (String) this.f$1;
                ComponentName componentName = (ComponentName) this.f$2;
                String str8 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService4.lambda$removeDexURLShortcut$2(str7, componentName);
            default:
                KnoxCustomManagerService knoxCustomManagerService5 = this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                String str9 = (String) this.f$2;
                String str10 = KnoxCustomManagerService.TAG;
                return knoxCustomManagerService5.lambda$getScreenTimeoutForDesktopMode$155(
                        bundle, str9);
        }
    }
}
