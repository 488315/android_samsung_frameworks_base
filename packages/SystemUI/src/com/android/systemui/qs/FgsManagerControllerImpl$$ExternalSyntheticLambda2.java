package com.android.systemui.qs;

import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class FgsManagerControllerImpl$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FgsManagerControllerImpl f$0;

    public /* synthetic */ FgsManagerControllerImpl$$ExternalSyntheticLambda2(FgsManagerControllerImpl fgsManagerControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = fgsManagerControllerImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.dialog = (SystemUIDialog) obj;
                break;
            default:
                this.f$0.newChangesSinceDialogWasDismissed = ((Boolean) obj).booleanValue();
                break;
        }
        return Unit.INSTANCE;
    }
}
