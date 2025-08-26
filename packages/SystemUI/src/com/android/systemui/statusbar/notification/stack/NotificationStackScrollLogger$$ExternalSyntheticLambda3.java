package com.android.systemui.statusbar.notification.stack;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLogger$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Ref$ObjectRef f$0;

    public /* synthetic */ NotificationStackScrollLogger$$ExternalSyntheticLambda3(Ref$ObjectRef ref$ObjectRef, int i) {
        this.$r8$classId = i;
        this.f$0 = ref$ObjectRef;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "removeTransientRow from NSSL: childKey: " + logMessage.getStr1() + "  -- call back : " + this.f$0.element;
            default:
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                Object obj2 = this.f$0.element;
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int1, "addTransientRow to NSSL: childKey: ", str1, " -- index: ", "  -- call back : ");
                sbM890m.append(obj2);
                return sbM890m.toString();
        }
    }
}
