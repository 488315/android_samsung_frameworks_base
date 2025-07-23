package com.android.systemui.media.controls.ui.controller;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.util.MediaUiEvent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaControlPanel f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda1(MediaControlPanel mediaControlPanel, int i) {
        this.$r8$classId = i;
        this.f$0 = mediaControlPanel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        InstanceId instanceId;
        switch (this.$r8$classId) {
            case 0:
                MediaControlPanel mediaControlPanel = this.f$0;
                String str = mediaControlPanel.mPackageName;
                if (str != null && (instanceId = mediaControlPanel.mInstanceId) != null) {
                    mediaControlPanel.mLogger.logger.logWithInstanceId(MediaUiEvent.ACTION_SEEK, mediaControlPanel.mUid, str, instanceId);
                }
                break;
            default:
                this.f$0.mMediaViewController.refreshState();
                break;
        }
        return Unit.INSTANCE;
    }
}
