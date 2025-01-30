package com.android.systemui.plugins.omni;

import android.app.appsearch.GlobalSearchSession;
import android.content.Intent;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class AssistStateManager$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AssistStateManager f$0;

    public /* synthetic */ AssistStateManager$$ExternalSyntheticLambda0(AssistStateManager assistStateManager, int i) {
        this.$r8$classId = i;
        this.f$0 = assistStateManager;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$new$0((Intent) obj);
                break;
            default:
                this.f$0.lambda$closeGlobalSearchSession$1((GlobalSearchSession) obj);
                break;
        }
    }
}
