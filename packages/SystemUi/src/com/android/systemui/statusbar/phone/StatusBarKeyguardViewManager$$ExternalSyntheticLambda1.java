package com.android.systemui.statusbar.phone;

import android.view.View;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StatusBarKeyguardViewManager$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((View) obj).animate().alpha(0.0f).setDuration(125L).start();
                break;
            default:
                ((View) obj).animate().alpha(1.0f).setDuration(125L).start();
                break;
        }
    }
}
