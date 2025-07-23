package com.android.systemui.statusbar.notification.stack;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "removeTransientRow from NSSL: childKey: " + logMessage.getStr1() + "  -- call back : " + this.f$0.element;
            default:
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                Object obj2 = this.f$0.element;
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int1, "addTransientRow to NSSL: childKey: ", str1, " -- index: ", "  -- call back : ");
                m888m.append(obj2);
                return m888m.toString();
        }
    }
}
