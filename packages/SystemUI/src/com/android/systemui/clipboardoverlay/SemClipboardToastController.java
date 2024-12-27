package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.SystemProperties;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SemClipboardToastController {
    public long lastCopiedTime;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final SemRemoteServiceStateManager mRemoteServiceStateManager;

    public SemClipboardToastController(Context context, BroadcastSender broadcastSender) {
        this.mRemoteServiceStateManager = new SemRemoteServiceStateManager(context, broadcastSender);
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        this.mDisplayManager = displayManager;
        this.mContext = context;
    }

    public final String getRemoteSenderToastString() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet") ? this.mContext.getResources().getString(R.string.clipboard_mcf_copied_toast_on_this_tablet_or_connected_devices) : this.mContext.getResources().getString(R.string.clipboard_mcf_copied_toast_on_this_phone_or_connected_devices);
    }
}
