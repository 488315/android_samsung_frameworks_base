package com.android.systemui.common.data.repository;

import android.os.UserHandle;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class PackageUpdateLogger$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(logMessage.getInt1());
        String str1 = logMessage.getStr1();
        int int1 = logMessage.getInt1();
        String str2 = logMessage.getStr2();
        StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int1, "Package ", str1, " (", ") ");
        m888m.append(str2);
        m888m.append(" on user ");
        m888m.append(userHandleForUid);
        return m888m.toString();
    }
}
