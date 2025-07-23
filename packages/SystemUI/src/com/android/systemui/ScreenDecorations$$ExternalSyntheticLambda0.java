package com.android.systemui;

import android.util.Size;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.decor.CameraProtectionSubCommand;
import com.android.systemui.decor.CutoutDecorProviderFactory;
import com.android.systemui.decor.DebugRoundedCornerDelegate;
import com.android.systemui.decor.DebugRoundedCornerModel;
import com.android.systemui.decor.PathDrawable;
import com.android.systemui.decor.RoundedCornerSubCommand;
import com.android.systemui.decor.ScreenDecorCommand;
import com.android.systemui.statusbar.commandline.OptionalSubCommand;
import com.android.systemui.statusbar.commandline.SingleArgParamOptional;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$$ExternalSyntheticLambda0 implements ScreenDecorCommand.Callback {
    public final /* synthetic */ ScreenDecorations f$0;

    public /* synthetic */ ScreenDecorations$$ExternalSyntheticLambda0(ScreenDecorations screenDecorations) {
        this.f$0 = screenDecorations;
    }

    public final void onExecute(final ScreenDecorCommand screenDecorCommand) {
        DebugRoundedCornerModel debugRoundedCornerModel;
        char c;
        final int i = 1;
        final int i2 = 0;
        final int i3 = 2;
        boolean z = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
        final ScreenDecorations screenDecorations = this.f$0;
        screenDecorCommand.getClass();
        KProperty[] kPropertyArr = ScreenDecorCommand.$$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        SingleArgParamOptional singleArgParamOptional = screenDecorCommand.debug$delegate;
        if (((Boolean) singleArgParamOptional.getValue(screenDecorCommand, kProperty)) != null && !((Boolean) singleArgParamOptional.getValue(screenDecorCommand, kPropertyArr[0])).booleanValue()) {
            screenDecorations.setDebug(false);
            return;
        }
        screenDecorations.setDebug(true);
        Integer color = screenDecorCommand.getColor();
        DelayableExecutor delayableExecutor = screenDecorations.mExecutor;
        if (color != null) {
            screenDecorations.mDebugColor = screenDecorCommand.getColor().intValue();
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda18
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
                            ScreenDecorations.AnonymousClass7 anonymousClass7 = screenDecorations2.mColorInversionSetting;
                            screenDecorations2.updateColorInversion(anonymousClass7 != null ? anonymousClass7.getValue() : 0);
                            break;
                        case 1:
                            ScreenDecorations screenDecorations3 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand3 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer2 = screenDecorations3.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer2 != null) {
                                screenDecorCommand3.getClass();
                                screenDecorHwcLayer2.debugTransparentRegion = ((Boolean) screenDecorCommand3.hwcDebugTransparentRegion$delegate.getValue(screenDecorCommand3, ScreenDecorCommand.$$delegatedProperties[2])).booleanValue();
                                screenDecorHwcLayer2.invalidate();
                                break;
                            }
                            break;
                        default:
                            ScreenDecorations screenDecorations4 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand4 = screenDecorCommand;
                            boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            screenDecorCommand4.getClass();
                            int intValue2 = ((Integer) screenDecorCommand4.faceAuthScreen$delegate.getValue(screenDecorCommand4, ScreenDecorCommand.$$delegatedProperties[5])).intValue();
                            ScreenDecorations.DisplayCutoutView displayCutoutView = (ScreenDecorations.DisplayCutoutView) screenDecorations4.getOverlayView(screenDecorations4.mFaceScanningViewId);
                            if (displayCutoutView != null) {
                                displayCutoutView.mDebug = true;
                                CameraAvailabilityListener cameraAvailabilityListener = screenDecorations4.mCameraListener;
                                List list = cameraAvailabilityListener.cameraProtectionInfoList;
                                CameraProtectionInfo cameraProtectionInfo = list != null ? (CameraProtectionInfo) CollectionsKt___CollectionsKt.getOrNull(intValue2, list) : null;
                                if (cameraProtectionInfo != null) {
                                    cameraAvailabilityListener.notifyCameraActive(cameraProtectionInfo);
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
        }
        if (((Boolean) screenDecorCommand.hwcDebugTransparentRegion$delegate.getValue(screenDecorCommand, kPropertyArr[2])) != null) {
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda18
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
                            ScreenDecorations.AnonymousClass7 anonymousClass7 = screenDecorations2.mColorInversionSetting;
                            screenDecorations2.updateColorInversion(anonymousClass7 != null ? anonymousClass7.getValue() : 0);
                            break;
                        case 1:
                            ScreenDecorations screenDecorations3 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand3 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer2 = screenDecorations3.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer2 != null) {
                                screenDecorCommand3.getClass();
                                screenDecorHwcLayer2.debugTransparentRegion = ((Boolean) screenDecorCommand3.hwcDebugTransparentRegion$delegate.getValue(screenDecorCommand3, ScreenDecorCommand.$$delegatedProperties[2])).booleanValue();
                                screenDecorHwcLayer2.invalidate();
                                break;
                            }
                            break;
                        default:
                            ScreenDecorations screenDecorations4 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand4 = screenDecorCommand;
                            boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            screenDecorCommand4.getClass();
                            int intValue2 = ((Integer) screenDecorCommand4.faceAuthScreen$delegate.getValue(screenDecorCommand4, ScreenDecorCommand.$$delegatedProperties[5])).intValue();
                            ScreenDecorations.DisplayCutoutView displayCutoutView = (ScreenDecorations.DisplayCutoutView) screenDecorations4.getOverlayView(screenDecorations4.mFaceScanningViewId);
                            if (displayCutoutView != null) {
                                displayCutoutView.mDebug = true;
                                CameraAvailabilityListener cameraAvailabilityListener = screenDecorations4.mCameraListener;
                                List list = cameraAvailabilityListener.cameraProtectionInfoList;
                                CameraProtectionInfo cameraProtectionInfo = list != null ? (CameraProtectionInfo) CollectionsKt___CollectionsKt.getOrNull(intValue2, list) : null;
                                if (cameraProtectionInfo != null) {
                                    cameraAvailabilityListener.notifyCameraActive(cameraProtectionInfo);
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
        }
        KProperty kProperty2 = kPropertyArr[3];
        OptionalSubCommand optionalSubCommand = screenDecorCommand.roundedTop$delegate;
        DebugRoundedCornerModel debugRoundedCornerModel2 = null;
        if (((RoundedCornerSubCommand) optionalSubCommand.getValue()) != null) {
            KProperty kProperty3 = kPropertyArr[3];
            debugRoundedCornerModel = ((RoundedCornerSubCommand) optionalSubCommand.getValue()).toRoundedCornerDebugModel();
        } else {
            debugRoundedCornerModel = null;
        }
        KProperty kProperty4 = kPropertyArr[4];
        OptionalSubCommand optionalSubCommand2 = screenDecorCommand.roundedBottom$delegate;
        if (((RoundedCornerSubCommand) optionalSubCommand2.getValue()) != null) {
            KProperty kProperty5 = kPropertyArr[4];
            debugRoundedCornerModel2 = ((RoundedCornerSubCommand) optionalSubCommand2.getValue()).toRoundedCornerDebugModel();
        }
        if (debugRoundedCornerModel == null && debugRoundedCornerModel2 == null) {
            c = 1;
        } else {
            DebugRoundedCornerDelegate debugRoundedCornerDelegate = screenDecorations.mDebugRoundedCornerDelegate;
            if (debugRoundedCornerModel != null) {
                debugRoundedCornerDelegate.hasTop = true;
                debugRoundedCornerDelegate.topRoundedDrawable = new PathDrawable(debugRoundedCornerModel.path, debugRoundedCornerModel.width, debugRoundedCornerModel.height, debugRoundedCornerModel.scaleX, debugRoundedCornerModel.scaleY, debugRoundedCornerDelegate.paint);
                debugRoundedCornerDelegate.topRoundedSize = new Size(debugRoundedCornerModel.width, debugRoundedCornerModel.height);
            }
            if (debugRoundedCornerModel2 != null) {
                debugRoundedCornerDelegate.hasBottom = true;
                c = 1;
                debugRoundedCornerDelegate.bottomRoundedDrawable = new PathDrawable(debugRoundedCornerModel2.path, debugRoundedCornerModel2.width, debugRoundedCornerModel2.height, debugRoundedCornerModel2.scaleX, debugRoundedCornerModel2.scaleY, debugRoundedCornerDelegate.paint);
                debugRoundedCornerDelegate.bottomRoundedSize = new Size(debugRoundedCornerModel2.width, debugRoundedCornerModel2.height);
            } else {
                c = 1;
                debugRoundedCornerDelegate.getClass();
            }
            delayableExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda5(screenDecorations, 2));
        }
        if (((Integer) screenDecorCommand.faceAuthScreen$delegate.getValue(screenDecorCommand, kPropertyArr[5])) != null) {
            delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i3) {
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
                            ScreenDecorations.AnonymousClass7 anonymousClass7 = screenDecorations2.mColorInversionSetting;
                            screenDecorations2.updateColorInversion(anonymousClass7 != null ? anonymousClass7.getValue() : 0);
                            break;
                        case 1:
                            ScreenDecorations screenDecorations3 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand3 = screenDecorCommand;
                            ScreenDecorHwcLayer screenDecorHwcLayer2 = screenDecorations3.mScreenDecorHwcLayer;
                            if (screenDecorHwcLayer2 != null) {
                                screenDecorCommand3.getClass();
                                screenDecorHwcLayer2.debugTransparentRegion = ((Boolean) screenDecorCommand3.hwcDebugTransparentRegion$delegate.getValue(screenDecorCommand3, ScreenDecorCommand.$$delegatedProperties[2])).booleanValue();
                                screenDecorHwcLayer2.invalidate();
                                break;
                            }
                            break;
                        default:
                            ScreenDecorations screenDecorations4 = screenDecorations;
                            ScreenDecorCommand screenDecorCommand4 = screenDecorCommand;
                            boolean z2 = ScreenDecorations.DEBUG_DISABLE_SCREEN_DECORATIONS;
                            screenDecorCommand4.getClass();
                            int intValue2 = ((Integer) screenDecorCommand4.faceAuthScreen$delegate.getValue(screenDecorCommand4, ScreenDecorCommand.$$delegatedProperties[5])).intValue();
                            ScreenDecorations.DisplayCutoutView displayCutoutView = (ScreenDecorations.DisplayCutoutView) screenDecorations4.getOverlayView(screenDecorations4.mFaceScanningViewId);
                            if (displayCutoutView != null) {
                                displayCutoutView.mDebug = true;
                                CameraAvailabilityListener cameraAvailabilityListener = screenDecorations4.mCameraListener;
                                List list = cameraAvailabilityListener.cameraProtectionInfoList;
                                CameraProtectionInfo cameraProtectionInfo = list != null ? (CameraProtectionInfo) CollectionsKt___CollectionsKt.getOrNull(intValue2, list) : null;
                                if (cameraProtectionInfo != null) {
                                    cameraAvailabilityListener.notifyCameraActive(cameraProtectionInfo);
                                    break;
                                }
                            }
                            break;
                    }
                }
            });
        }
        if (screenDecorCommand.getCameraProtection() != null) {
            CameraProtectionSubCommand cameraProtection = screenDecorCommand.getCameraProtection();
            cameraProtection.getClass();
            KProperty[] kPropertyArr2 = CameraProtectionSubCommand.$$delegatedProperties;
            if (((Boolean) cameraProtection.enabled$delegate.getValue(cameraProtection, kPropertyArr2[0])) != null) {
                CutoutDecorProviderFactory cutoutDecorProviderFactory = screenDecorations.mDebugCutoutFactory;
                CameraProtectionSubCommand cameraProtection2 = screenDecorCommand.getCameraProtection();
                cameraProtection2.getClass();
                cutoutDecorProviderFactory.isCameraProtectionEnabled = ((Boolean) cameraProtection2.enabled$delegate.getValue(cameraProtection2, kPropertyArr2[0])).booleanValue();
                screenDecorations.setupCameraListener();
            }
            CameraProtectionSubCommand cameraProtection3 = screenDecorCommand.getCameraProtection();
            cameraProtection3.getClass();
            if (((Integer) cameraProtection3.strokeWidth$delegate.getValue(cameraProtection3, kPropertyArr2[c])) != null) {
                CutoutDecorProviderFactory cutoutDecorProviderFactory2 = screenDecorations.mDebugCutoutFactory;
                CameraProtectionSubCommand cameraProtection4 = screenDecorCommand.getCameraProtection();
                cameraProtection4.getClass();
                cutoutDecorProviderFactory2.cameraProtectionStrokeWidth = ((Integer) cameraProtection4.strokeWidth$delegate.getValue(cameraProtection4, kPropertyArr2[c])).intValue();
            }
            delayableExecutor.execute(new ScreenDecorations$$ExternalSyntheticLambda5(screenDecorations, 3));
        }
    }
}
