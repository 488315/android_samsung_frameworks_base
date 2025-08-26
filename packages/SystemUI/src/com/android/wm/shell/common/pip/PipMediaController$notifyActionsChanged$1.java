package com.android.wm.shell.common.pip;

import android.app.RemoteAction;
import com.android.wm.shell.common.pip.PipMediaController;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class PipMediaController$notifyActionsChanged$1 implements Consumer {
    public final /* synthetic */ List $actions;

    public PipMediaController$notifyActionsChanged$1(List<RemoteAction> list) {
        this.$actions = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PipMediaController.ActionListener) obj).onMediaActionsChanged(this.$actions);
    }
}
