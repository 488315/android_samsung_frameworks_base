package com.android.systemui.volume.view.warnings;

import android.view.Display;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.ToastWrapper;
import com.android.systemui.volume.view.context.ViewContext;
import com.android.systemui.volume.view.warnings.VolumeWarningCameraViewPresentation;
import com.android.systemui.volume.view.warnings.VolumeWarningSideViewDialog;
import com.android.systemui.volume.view.warnings.VolumeWarningWalletMiniDialog;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WarningDialogController {
    public final Lazy displayManagerWrapper$delegate;
    public final Lazy toastWrapper$delegate;
    public final ViewContext viewContext;

    public WarningDialogController(ViewContext viewContext) {
        this.viewContext = viewContext;
        final int i = 0;
        this.displayManagerWrapper$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.volume.view.warnings.WarningDialogController$$ExternalSyntheticLambda0
            public final /* synthetic */ WarningDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return (DisplayManagerWrapper) ((VolumeDependency) this.f$0.viewContext.getVolDeps()).get(DisplayManagerWrapper.class);
                    default:
                        return (ToastWrapper) ((VolumeDependency) this.f$0.viewContext.getVolDeps()).get(ToastWrapper.class);
                }
            }
        });
        final int i2 = 1;
        this.toastWrapper$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.volume.view.warnings.WarningDialogController$$ExternalSyntheticLambda0
            public final /* synthetic */ WarningDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return (DisplayManagerWrapper) ((VolumeDependency) this.f$0.viewContext.getVolDeps()).get(DisplayManagerWrapper.class);
                    default:
                        return (ToastWrapper) ((VolumeDependency) this.f$0.viewContext.getVolDeps()).get(ToastWrapper.class);
                }
            }
        });
    }

    public final void showVolumeCSD100WarningDialog() {
        ViewContext viewContext = this.viewContext;
        VolumePanelState panelState$1 = viewContext.getPanelState$1();
        if (panelState$1.getCoverType() == 8 || !panelState$1.isCoverClosed()) {
            VolumeCSD100WarningDialog volumeCSD100WarningDialog = new VolumeCSD100WarningDialog(viewContext.getContext());
            VolumePanelStore store$1 = viewContext.getStore$1();
            StoreInteractor storeInteractor = volumeCSD100WarningDialog.storeInteractor;
            storeInteractor.store = store$1;
            storeInteractor.observeStore();
            boolean isCoverClosed = panelState$1.isCoverClosed();
            volumeCSD100WarningDialog.getWindow().setGravity(80);
            if (isCoverClosed) {
                volumeCSD100WarningDialog.getWindow().getDecorView().post(new VolumeWarningDialog$initWindow$1(volumeCSD100WarningDialog));
            }
            volumeCSD100WarningDialog.show();
            return;
        }
        switch (panelState$1.getCoverType()) {
            case 15:
                VolumeWarningSideViewDialog volumeWarningSideViewDialog = new VolumeWarningSideViewDialog(viewContext.getContext(), VolumeWarningSideViewDialog.WarningDialogType.VOLUME_CSD_100_WARNING);
                VolumePanelStore store$12 = viewContext.getStore$1();
                StoreInteractor storeInteractor2 = volumeWarningSideViewDialog.storeInteractor;
                storeInteractor2.store = store$12;
                storeInteractor2.observeStore();
                volumeWarningSideViewDialog.show();
                break;
            case 16:
                VolumeWarningWalletMiniDialog volumeWarningWalletMiniDialog = new VolumeWarningWalletMiniDialog(viewContext.getContext(), VolumeWarningWalletMiniDialog.WarningDialogType.VOLUME_CSD_100_WARNING);
                VolumePanelStore store$13 = viewContext.getStore$1();
                StoreInteractor storeInteractor3 = volumeWarningWalletMiniDialog.storeInteractor;
                storeInteractor3.store = store$13;
                storeInteractor3.observeStore();
                volumeWarningWalletMiniDialog.show();
                break;
            case 17:
                Display frontCameraDisplay = ((DisplayManagerWrapper) this.displayManagerWrapper$delegate.getValue()).getFrontCameraDisplay();
                if (frontCameraDisplay != null) {
                    VolumeWarningCameraViewPresentation volumeWarningCameraViewPresentation = new VolumeWarningCameraViewPresentation(viewContext.getContext(), frontCameraDisplay, VolumeWarningCameraViewPresentation.WarningDialogType.VOLUME_CSD_100_WARNING);
                    VolumePanelStore store$14 = viewContext.getStore$1();
                    StoreInteractor storeInteractor4 = volumeWarningCameraViewPresentation.storeInteractor;
                    storeInteractor4.store = store$14;
                    storeInteractor4.observeStore();
                    volumeWarningCameraViewPresentation.show();
                    break;
                }
                break;
        }
    }

    public final void showVolumeLimiterDialog() {
        ViewContext viewContext = this.viewContext;
        VolumePanelState panelState$1 = viewContext.getPanelState$1();
        if (panelState$1.getCoverType() == 8 || !panelState$1.isCoverClosed()) {
            VolumeLimiterWarningDialog volumeLimiterWarningDialog = new VolumeLimiterWarningDialog(viewContext.getContext());
            VolumePanelStore store$1 = viewContext.getStore$1();
            ToastWrapper toastWrapper = (ToastWrapper) this.toastWrapper$delegate.getValue();
            StoreInteractor storeInteractor = volumeLimiterWarningDialog.storeInteractor;
            storeInteractor.store = store$1;
            storeInteractor.observeStore();
            volumeLimiterWarningDialog.toastWrapper = toastWrapper;
            volumeLimiterWarningDialog.initButtons();
            volumeLimiterWarningDialog.show();
            return;
        }
        switch (panelState$1.getCoverType()) {
            case 15:
                VolumeWarningSideViewDialog volumeWarningSideViewDialog = new VolumeWarningSideViewDialog(viewContext.getContext(), VolumeWarningSideViewDialog.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING);
                VolumePanelStore store$12 = viewContext.getStore$1();
                StoreInteractor storeInteractor2 = volumeWarningSideViewDialog.storeInteractor;
                storeInteractor2.store = store$12;
                storeInteractor2.observeStore();
                volumeWarningSideViewDialog.show();
                break;
            case 16:
                VolumeWarningWalletMiniDialog volumeWarningWalletMiniDialog = new VolumeWarningWalletMiniDialog(viewContext.getContext(), VolumeWarningWalletMiniDialog.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING);
                VolumePanelStore store$13 = viewContext.getStore$1();
                StoreInteractor storeInteractor3 = volumeWarningWalletMiniDialog.storeInteractor;
                storeInteractor3.store = store$13;
                storeInteractor3.observeStore();
                volumeWarningWalletMiniDialog.show();
                break;
            case 17:
                Display frontCameraDisplay = ((DisplayManagerWrapper) this.displayManagerWrapper$delegate.getValue()).getFrontCameraDisplay();
                if (frontCameraDisplay != null) {
                    VolumeWarningCameraViewPresentation volumeWarningCameraViewPresentation = new VolumeWarningCameraViewPresentation(viewContext.getContext(), frontCameraDisplay, VolumeWarningCameraViewPresentation.WarningDialogType.MEDIA_VOLUME_LIMITER_WARNING);
                    VolumePanelStore store$14 = viewContext.getStore$1();
                    StoreInteractor storeInteractor4 = volumeWarningCameraViewPresentation.storeInteractor;
                    storeInteractor4.store = store$14;
                    storeInteractor4.observeStore();
                    volumeWarningCameraViewPresentation.show();
                    break;
                }
                break;
        }
    }

    public final void showVolumeSafetyWarningDialog() {
        ViewContext viewContext = this.viewContext;
        VolumePanelState panelState$1 = viewContext.getPanelState$1();
        if (panelState$1.getCoverType() == 8 || !panelState$1.isCoverClosed()) {
            VolumeSafetyWarningDialog volumeSafetyWarningDialog = new VolumeSafetyWarningDialog(viewContext.getContext());
            VolumePanelStore store$1 = viewContext.getStore$1();
            StoreInteractor storeInteractor = volumeSafetyWarningDialog.storeInteractor;
            storeInteractor.store = store$1;
            storeInteractor.observeStore();
            boolean isCoverClosed = panelState$1.isCoverClosed();
            volumeSafetyWarningDialog.getWindow().setGravity(80);
            if (isCoverClosed) {
                volumeSafetyWarningDialog.getWindow().getDecorView().post(new VolumeWarningDialog$initWindow$1(volumeSafetyWarningDialog));
            }
            volumeSafetyWarningDialog.initButtons();
            volumeSafetyWarningDialog.show();
            return;
        }
        switch (panelState$1.getCoverType()) {
            case 15:
                VolumeWarningSideViewDialog volumeWarningSideViewDialog = new VolumeWarningSideViewDialog(viewContext.getContext(), VolumeWarningSideViewDialog.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING);
                VolumePanelStore store$12 = viewContext.getStore$1();
                StoreInteractor storeInteractor2 = volumeWarningSideViewDialog.storeInteractor;
                storeInteractor2.store = store$12;
                storeInteractor2.observeStore();
                volumeWarningSideViewDialog.show();
                break;
            case 16:
                VolumeWarningWalletMiniDialog volumeWarningWalletMiniDialog = new VolumeWarningWalletMiniDialog(viewContext.getContext(), VolumeWarningWalletMiniDialog.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING);
                VolumePanelStore store$13 = viewContext.getStore$1();
                StoreInteractor storeInteractor3 = volumeWarningWalletMiniDialog.storeInteractor;
                storeInteractor3.store = store$13;
                storeInteractor3.observeStore();
                volumeWarningWalletMiniDialog.show();
                break;
            case 17:
                Display frontCameraDisplay = ((DisplayManagerWrapper) this.displayManagerWrapper$delegate.getValue()).getFrontCameraDisplay();
                if (frontCameraDisplay != null) {
                    VolumeWarningCameraViewPresentation volumeWarningCameraViewPresentation = new VolumeWarningCameraViewPresentation(viewContext.getContext(), frontCameraDisplay, VolumeWarningCameraViewPresentation.WarningDialogType.DEFAULT_SAFETY_VOLUME_WARNING);
                    VolumePanelStore store$14 = viewContext.getStore$1();
                    StoreInteractor storeInteractor4 = volumeWarningCameraViewPresentation.storeInteractor;
                    storeInteractor4.store = store$14;
                    storeInteractor4.observeStore();
                    volumeWarningCameraViewPresentation.show();
                    break;
                }
                break;
        }
    }
}
