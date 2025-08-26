package com.android.systemui.statusbar.notification.headsup;

import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class AvalancheController$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AvalancheController f$0;

    public /* synthetic */ AvalancheController$$ExternalSyntheticLambda1(int i, AvalancheController avalancheController) {
        this.$r8$classId = i;
        this.f$0 = avalancheController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry = (HeadsUpManagerImpl.HeadsUpEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                break;
            case 1:
                this.f$0.getClass();
                break;
            default:
                this.f$0.getClass();
                break;
        }
        return AvalancheController.getKey(headsUpEntry);
    }
}
