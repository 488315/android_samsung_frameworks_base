package com.android.systemui.common.data.repository;

import android.os.UserHandle;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class PackageUpdateLogger$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(logMessage.getInt1());
        String str1 = logMessage.getStr1();
        int int1 = logMessage.getInt1();
        String str2 = logMessage.getStr2();
        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int1, "Package ", str1, " (", ") ");
        sbM890m.append(str2);
        sbM890m.append(" on user ");
        sbM890m.append(userHandleForUid);
        return sbM890m.toString();
    }
}
