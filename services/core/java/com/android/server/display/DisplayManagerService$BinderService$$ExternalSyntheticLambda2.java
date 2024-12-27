package com.android.server.display;

import android.hardware.display.BrightnessConfiguration;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayManagerService$BinderService$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayManagerService.BinderService f$0;
    public final /* synthetic */ BrightnessConfiguration f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ DisplayManagerService$BinderService$$ExternalSyntheticLambda2(
            DisplayManagerService.BinderService binderService,
            BrightnessConfiguration brightnessConfiguration,
            int i,
            String str,
            int i2) {
        this.$r8$classId = i2;
        this.f$0 = binderService;
        this.f$1 = brightnessConfiguration;
        this.f$2 = i;
        this.f$3 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DisplayManagerService.BinderService binderService = this.f$0;
                BrightnessConfiguration brightnessConfiguration = this.f$1;
                int i = this.f$2;
                String str = this.f$3;
                LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                int i2 = DisplayManagerService.BinderService.$r8$clinit;
                binderService.getClass();
                if (logicalDisplay.getDisplayInfoLocked().type == 1) {
                    DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
                    DisplayManagerService displayManagerService = DisplayManagerService.this;
                    String str2 = displayDevice.mUniqueId;
                    boolean z = DisplayManagerService.DEBUG;
                    displayManagerService.setBrightnessConfigurationForDisplayInternal(
                            brightnessConfiguration, str2, i, str);
                    break;
                }
                break;
            default:
                DisplayManagerService.BinderService binderService2 = this.f$0;
                BrightnessConfiguration brightnessConfiguration2 = this.f$1;
                int i3 = this.f$2;
                String str3 = this.f$3;
                LogicalDisplay logicalDisplay2 = (LogicalDisplay) obj;
                int i4 = DisplayManagerService.BinderService.$r8$clinit;
                binderService2.getClass();
                if (logicalDisplay2.getDisplayInfoLocked().type == 1) {
                    String str4 = logicalDisplay2.mPrimaryDisplayDevice.mUniqueId;
                    DisplayManagerService displayManagerService2 = DisplayManagerService.this;
                    boolean z2 = DisplayManagerService.DEBUG;
                    displayManagerService2.setBrightnessConfigurationForDisplayInternal(
                            brightnessConfiguration2, str4, i3, str3);
                    break;
                }
                break;
        }
    }
}
