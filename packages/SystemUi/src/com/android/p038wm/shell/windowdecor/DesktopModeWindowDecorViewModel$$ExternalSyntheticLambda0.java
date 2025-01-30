package com.android.p038wm.shell.windowdecor;

import android.provider.Settings;
import com.android.p038wm.shell.desktopmode.DesktopModeController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Settings.System.putInt(((DesktopModeController) obj).mContext.getContentResolver(), "desktop_mode", 1);
                break;
            case 1:
                Settings.System.putInt(((DesktopModeController) obj).mContext.getContentResolver(), "desktop_mode", 1);
                break;
            default:
                Settings.System.putInt(((DesktopModeController) obj).mContext.getContentResolver(), "desktop_mode", 0);
                break;
        }
    }
}
