package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class LabsHomeKt$Labs$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LabsViewModel f$0;

    public /* synthetic */ LabsHomeKt$Labs$1$$ExternalSyntheticLambda0(LabsViewModel labsViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = labsViewModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Boolean bool = (Boolean) obj;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setCloseOnTouchOutside(bool.booleanValue());
                break;
            case 1:
                this.f$0.setActionOpenOutputSwitcher(bool.booleanValue());
                break;
            case 2:
                this.f$0.setSupportTransferableRoutesWhileConnecting(bool.booleanValue());
                break;
            case 3:
                this.f$0.setSupportForTransferDuringRouting(bool.booleanValue());
                break;
            case 4:
                this.f$0.setSupportDisplayOnlyRemoteDevice(bool.booleanValue());
                break;
            case 5:
                this.f$0.setGrayscaleThumbnail(bool.booleanValue());
                break;
            case 6:
                this.f$0.setSupportMultipleMediaSession(bool.booleanValue());
                break;
            case 7:
                this.f$0.setSupportSelectableBudsTogether(bool.booleanValue());
                break;
            case 8:
                this.f$0.setSupportDisplayDeviceVolumeControl(bool.booleanValue());
                break;
            case 9:
                this.f$0.setSupportVolumeInteraction(bool.booleanValue());
                break;
            case 10:
                this.f$0.setGroupSpeakerDefaultExpanded(bool.booleanValue());
                break;
            case 11:
                this.f$0.setSupportRecentGroupSpeaker(bool.booleanValue());
                break;
            case 12:
                bool.booleanValue();
                this.f$0.setShowLabsMenu(true);
                break;
            default:
                this.f$0.setSupportForUnsupportedTV(bool.booleanValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
