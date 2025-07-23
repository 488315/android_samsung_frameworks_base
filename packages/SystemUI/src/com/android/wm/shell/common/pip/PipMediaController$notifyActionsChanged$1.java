package com.android.wm.shell.common.pip;

import android.app.RemoteAction;
import com.android.wm.shell.common.pip.PipMediaController;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
