package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaOutputController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ MediaDevice f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MediaOutputController$$ExternalSyntheticLambda4(MediaDevice mediaDevice, int i) {
        this.f$0 = mediaDevice;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.requestSetVolume(this.f$1);
    }
}
