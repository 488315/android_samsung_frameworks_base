package com.android.systemui.statusbar.phone.ongoingactivity;

import java.util.concurrent.CancellationException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AnimationCompleted extends CancellationException {
    private final String reason;

    public AnimationCompleted(String str) {
        super(str);
        this.reason = str;
    }
}
