package com.android.systemui.plugins.omni;

import android.app.appsearch.AppSearchResult;
import android.app.appsearch.GlobalSearchSession;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class AssistStateManager$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AssistStateManager f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ AssistStateManager$$ExternalSyntheticLambda1(AssistStateManager assistStateManager, Consumer consumer, int i) {
        this.$r8$classId = i;
        this.f$0 = assistStateManager;
        this.f$1 = consumer;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$updateIsOmniAvailableFromAppSearch$4(this.f$1, (GlobalSearchSession) obj);
                break;
            default:
                this.f$0.lambda$initGlobalSearchSession$2(this.f$1, (AppSearchResult) obj);
                break;
        }
    }
}
