package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.hardware.display.DisplayManager;
import com.android.systemui.broadcast.BroadcastSender;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SemClipboardToastController {
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
