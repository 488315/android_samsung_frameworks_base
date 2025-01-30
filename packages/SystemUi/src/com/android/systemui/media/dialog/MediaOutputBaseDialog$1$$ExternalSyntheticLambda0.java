package com.android.systemui.media.dialog;

import com.android.systemui.media.dialog.MediaOutputBaseDialog;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaOutputBaseDialog$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MediaOutputBaseDialog.C18201 f$0;

    public /* synthetic */ MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(MediaOutputBaseDialog.C18201 c18201, int i) {
        this.$r8$classId = i;
        this.f$0 = c18201;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.this$0.handleLeBroadcastStopped();
                break;
            case 1:
                this.f$0.this$0.handleLeBroadcastStarted();
                break;
            case 2:
                this.f$0.this$0.handleLeBroadcastStartFailed();
                break;
            case 3:
                this.f$0.this$0.handleLeBroadcastStopFailed();
                break;
            case 4:
                this.f$0.this$0.handleLeBroadcastUpdateFailed();
                break;
            case 5:
                this.f$0.this$0.handleLeBroadcastUpdated();
                break;
            default:
                this.f$0.this$0.handleLeBroadcastMetadataChanged();
                break;
        }
    }
}
