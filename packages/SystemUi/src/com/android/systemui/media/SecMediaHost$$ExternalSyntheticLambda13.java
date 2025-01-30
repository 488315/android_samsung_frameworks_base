package com.android.systemui.media;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda13(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj;
                secMediaControlPanel.mFullyExpanded = this.f$0;
                secMediaControlPanel.updateActionButtonEnabled(secMediaControlPanel.mFraction);
                secMediaControlPanel.updateSeekBarVisibility();
                break;
            default:
                ((SecMediaControlPanel) obj).setListening(this.f$0);
                break;
        }
    }
}
