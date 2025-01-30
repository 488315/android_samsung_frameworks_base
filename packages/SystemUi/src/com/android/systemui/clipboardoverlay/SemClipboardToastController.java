package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.systemui.broadcast.BroadcastSender;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
}
