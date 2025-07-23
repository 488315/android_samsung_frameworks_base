package com.android.systemui.media.controls.ui.controller;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaViewLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int1, "size (", str1, "): ", " x ");
                m888m.append(int2);
                return m888m.toString();
            case 1:
                String str12 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                StringBuilder m888m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int12, "location (", str12, "): ", " -> ");
                m888m2.append(int22);
                return m888m2.toString();
            default:
                return "User visibility shade: " + logMessage.getBool1() + " media: " + logMessage.getBool2();
        }
    }
}
