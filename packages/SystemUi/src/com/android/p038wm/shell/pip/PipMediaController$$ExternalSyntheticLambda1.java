package com.android.p038wm.shell.pip;

import android.media.session.MediaSession;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.p038wm.shell.pip.PipMediaController;
import com.android.p038wm.shell.pip.p039tv.TvPipNotificationController;
import com.android.p038wm.shell.pip.p039tv.TvPipNotificationController$$ExternalSyntheticLambda0;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipMediaController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipMediaController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
                throw null;
            case 1:
                MediaSession.Token token = (MediaSession.Token) this.f$0;
                TvPipNotificationController tvPipNotificationController = ((TvPipNotificationController$$ExternalSyntheticLambda0) obj).f$0;
                tvPipNotificationController.mMediaSessionToken = token;
                tvPipNotificationController.updateNotificationContent();
                return;
            default:
                ((PipMediaController.ActionListener) obj).onMediaActionsChanged((List) this.f$0);
                return;
        }
    }
}
