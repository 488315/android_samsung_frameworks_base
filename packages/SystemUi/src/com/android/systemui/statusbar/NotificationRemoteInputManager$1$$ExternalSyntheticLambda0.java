package com.android.systemui.statusbar;

import android.os.SystemClock;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationRemoteInputManager$1$$ExternalSyntheticLambda0 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((CentralSurfacesImpl) ((CentralSurfaces) obj)).wakeUpIfDozing(SystemClock.uptimeMillis(), "NOTIFICATION_CLICK", 4);
    }
}
