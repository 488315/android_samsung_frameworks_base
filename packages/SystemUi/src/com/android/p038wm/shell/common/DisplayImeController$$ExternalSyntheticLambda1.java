package com.android.p038wm.shell.common;

import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayImeController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Slog.e("DisplayImeController", "Failed to remove IME surface.", (RemoteException) obj);
                break;
            case 1:
                ((SurfaceControl) obj).release();
                break;
            default:
                ((SurfaceControl) obj).release();
                break;
        }
    }
}
