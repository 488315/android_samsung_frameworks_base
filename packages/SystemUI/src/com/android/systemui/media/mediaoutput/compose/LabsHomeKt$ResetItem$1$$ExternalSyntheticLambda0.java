package com.android.systemui.media.mediaoutput.compose;

import android.content.Intent;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LabsViewModel f$0;

    public /* synthetic */ LabsHomeKt$ResetItem$1$$ExternalSyntheticLambda0(LabsViewModel labsViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = labsViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                LabsViewModel labsViewModel = this.f$0;
                labsViewModel.setShowLabsMenu(false);
                labsViewModel.setCloseOnTouchOutside(true);
                labsViewModel.setSupportVolumeInteraction(true);
                labsViewModel.setGroupSpeakerDefaultExpanded(true);
                labsViewModel.setSupportRecentGroupSpeaker(false);
                labsViewModel.setGrayscaleThumbnail(false);
                labsViewModel.setSupportMultipleMediaSession(false);
                labsViewModel.setSupportSelectableBudsTogether(false);
                labsViewModel.setSupportDisplayDeviceVolumeControl(false);
                labsViewModel.setSupportTransferableRoutesWhileConnecting(false);
                labsViewModel.setSupportForTransferDuringRouting(false);
                labsViewModel.setSupportDisplayOnlyRemoteDevice(true);
                labsViewModel.setSupportForUnsupportedTV(false);
                labsViewModel.setActionOpenOutputSwitcher(false);
                break;
            case 1:
                this.f$0.openActivity(true);
                break;
            case 2:
                this.f$0.openActivity(false);
                break;
            default:
                LabsViewModel labsViewModel2 = this.f$0;
                labsViewModel2.getClass();
                Intent intent = new Intent("com.samsung.android.mdx.quickboard.ACTION_OPEN_MEDIA_PANEL");
                intent.setPackage("com.samsung.android.mdx.quickboard");
                labsViewModel2.context.startForegroundService(intent);
                break;
        }
        return Unit.INSTANCE;
    }
}
