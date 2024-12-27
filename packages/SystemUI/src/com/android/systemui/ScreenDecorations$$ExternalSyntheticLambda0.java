package com.android.systemui;

import com.android.systemui.ScreenDecorations;
import com.android.systemui.decor.CameraProtectionSubCommand;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DebugRoundedCornerModel;
import com.android.systemui.decor.RoundedCornerSubCommand;
import com.android.systemui.decor.ScreenDecorCommand;
import com.android.systemui.statusbar.commandline.OptionalSubCommand;
import com.android.systemui.statusbar.commandline.SingleArgParamOptional;
import kotlin.reflect.KProperty;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda0 implements ScreenDecorCommand.Callback {
    public final /* synthetic */ ScreenDecorations f$0;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda0(ScreenDecorations screenDecorations) {
        this.f$0 = screenDecorations;
    }

    public final void onExecute(final ScreenDecorCommand screenDecorCommand) {
        DebugRoundedCornerModel debugRoundedCornerModel;
        final ScreenDecorations screenDecorations = this.f$0;
        screenDecorations.getClass();
        screenDecorCommand.getClass();
        KProperty[] kPropertyArr = ScreenDecorCommand.$$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        SingleArgParamOptional singleArgParamOptional = screenDecorCommand.debug$delegate;
        if (((Boolean) singleArgParamOptional.getValue()) != null) {
            KProperty kProperty2 = kPropertyArr[0];
            if (!((Boolean) singleArgParamOptional.getValue()).booleanValue()) {
                screenDecorations.setDebug(false);
                return;
            }
        }
        screenDecorations.setDebug(true);
        if (screenDecorCommand.getColor() != null) {
            screenDecorations.mDebugColor = screenDecorCommand.getColor().intValue();
            final int i = 0;
            screenDecorations.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            ScreenDecorations screenDecorations2 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand2 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer = screenDecorations2.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer != null) {
                                int intValue = screenDecorCommand2.getColor().intValue();
                                if (screenDecorHwcLayer.debug && screenDecorHwcLayer.color != intValue) {
                                    screenDecorHwcLayer.color = intValue;
                                    screenDecorHwcLayer.paint.setColor(intValue);
                                    screenDecorHwcLayer.updateColors();
                                    screenDecorHwcLayer.invalidate();
                                }
                            }
                            ScreenDecorations.AnonymousClass6 anonymousClass6 = screenDecorations2.mColorInversionSetting;
                            screenDecorations2.updateColorInversion(anonymousClass6 != null ? anonymousClass6.getValue() : 0);
                            break;
                        default:
                            ScreenDecorations screenDecorations3 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand3 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer2 = screenDecorations3.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer2 != null) {
                                screenDecorCommand3.getClass();
                                KProperty kProperty3 = ScreenDecorCommand.$$delegatedProperties[2];
                                screenDecorHwcLayer2.debugTransparentRegion = ((Boolean) screenDecorCommand3.hwcDebugTransparentRegion$delegate.getValue()).booleanValue();
                                screenDecorHwcLayer2.invalidate();
                                break;
                            }
                            break;
                    }
                }
            });
        }
        KProperty kProperty3 = kPropertyArr[2];
        if (((Boolean) screenDecorCommand.hwcDebugTransparentRegion$delegate.getValue()) != null) {
            final int i2 = 1;
            screenDecorations.mExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            ScreenDecorations screenDecorations2 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand2 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer = screenDecorations2.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer != null) {
                                int intValue = screenDecorCommand2.getColor().intValue();
                                if (screenDecorHwcLayer.debug && screenDecorHwcLayer.color != intValue) {
                                    screenDecorHwcLayer.color = intValue;
                                    screenDecorHwcLayer.paint.setColor(intValue);
                                    screenDecorHwcLayer.updateColors();
                                    screenDecorHwcLayer.invalidate();
                                }
                            }
                            ScreenDecorations.AnonymousClass6 anonymousClass6 = screenDecorations2.mColorInversionSetting;
                            screenDecorations2.updateColorInversion(anonymousClass6 != null ? anonymousClass6.getValue() : 0);
                            break;
                        default:
                            ScreenDecorations screenDecorations3 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand3 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer2 = screenDecorations3.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer2 != null) {
                                screenDecorCommand3.getClass();
                                KProperty kProperty32 = ScreenDecorCommand.$$delegatedProperties[2];
                                screenDecorHwcLayer2.debugTransparentRegion = ((Boolean) screenDecorCommand3.hwcDebugTransparentRegion$delegate.getValue()).booleanValue();
                                screenDecorHwcLayer2.invalidate();
                                break;
                            }
                            break;
                    }
                }
            });
        }
        KProperty kProperty4 = kPropertyArr[3];
        OptionalSubCommand optionalSubCommand = screenDecorCommand.roundedTop$delegate;
        boolean z = optionalSubCommand.isPresent;
        DebugRoundedCornerModel debugRoundedCornerModel2 = null;
        if (((RoundedCornerSubCommand) (z ? optionalSubCommand.cmd : null)) != null) {
            debugRoundedCornerModel = ((RoundedCornerSubCommand) (z ? optionalSubCommand.cmd : null)).toRoundedCornerDebugModel();
        } else {
            debugRoundedCornerModel = null;
        }
        KProperty kProperty5 = kPropertyArr[4];
        OptionalSubCommand optionalSubCommand2 = screenDecorCommand.roundedBottom$delegate;
        boolean z2 = optionalSubCommand2.isPresent;
        if (((RoundedCornerSubCommand) (z2 ? optionalSubCommand2.cmd : null)) != null) {
            debugRoundedCornerModel2 = ((RoundedCornerSubCommand) (z2 ? optionalSubCommand2.cmd : null)).toRoundedCornerDebugModel();
        }
        if (debugRoundedCornerModel != null || debugRoundedCornerModel2 != null) {
            screenDecorations.mDebugRoundedCornerDelegate.applyNewDebugCorners(debugRoundedCornerModel, debugRoundedCornerModel2);
            screenDecorations.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(screenDecorations, 2));
        }
        if (screenDecorCommand.getCameraProtection() != null) {
            CameraProtectionSubCommand cameraProtection = screenDecorCommand.getCameraProtection();
            cameraProtection.getClass();
            KProperty[] kPropertyArr2 = CameraProtectionSubCommand.$$delegatedProperties;
            KProperty kProperty6 = kPropertyArr2[0];
            if (((Boolean) cameraProtection.enabled$delegate.getValue()) != null) {
                CutoutDecorProviderFactory cutoutDecorProviderFactory = screenDecorations.mDebugCutoutFactory;
                CameraProtectionSubCommand cameraProtection2 = screenDecorCommand.getCameraProtection();
                cameraProtection2.getClass();
                KProperty kProperty7 = kPropertyArr2[0];
                cutoutDecorProviderFactory.isCameraProtectionEnabled = ((Boolean) cameraProtection2.enabled$delegate.getValue()).booleanValue();
                screenDecorations.setupCameraListener();
            }
            CameraProtectionSubCommand cameraProtection3 = screenDecorCommand.getCameraProtection();
            cameraProtection3.getClass();
            KProperty kProperty8 = kPropertyArr2[1];
            if (((Integer) cameraProtection3.strokeWidth$delegate.getValue()) != null) {
                CutoutDecorProviderFactory cutoutDecorProviderFactory2 = screenDecorations.mDebugCutoutFactory;
                CameraProtectionSubCommand cameraProtection4 = screenDecorCommand.getCameraProtection();
                cameraProtection4.getClass();
                KProperty kProperty9 = kPropertyArr2[1];
                cutoutDecorProviderFactory2.cameraProtectionStrokeWidth = ((Integer) cameraProtection4.strokeWidth$delegate.getValue()).intValue();
            }
            screenDecorations.mExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda3(screenDecorations, 3));
        }
    }
}
