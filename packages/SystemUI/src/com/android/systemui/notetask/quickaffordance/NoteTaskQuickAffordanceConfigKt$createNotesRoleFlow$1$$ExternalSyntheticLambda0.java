package com.android.systemui.notetask.quickaffordance;

import android.app.role.RoleManager;
import android.os.UserHandle;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.stylus.StylusManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class NoteTaskQuickAffordanceConfigKt$createNotesRoleFlow$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ NoteTaskQuickAffordanceConfigKt$createNotesRoleFlow$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((RoleManager) this.f$0).removeOnRoleHoldersChangedListenerAsUser((NoteTaskQuickAffordanceConfigKt$createNotesRoleFlow$1$callback$1) this.f$1, UserHandle.ALL);
                break;
            case 1:
                ((StylusManager) this.f$0).stylusCallbacks.remove((NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1$callback$1) this.f$1);
                break;
            default:
                ((KeyguardUpdateMonitor) this.f$0).removeCallback((NoteTaskQuickAffordanceConfigKt$createUserUnlockedFlow$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
