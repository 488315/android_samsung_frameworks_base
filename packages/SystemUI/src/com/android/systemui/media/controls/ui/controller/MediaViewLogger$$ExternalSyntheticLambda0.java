package com.android.systemui.media.controls.ui.controller;

import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaViewLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int1, "size (", str1, "): ", " x ");
                sbM890m.append(int2);
                return sbM890m.toString();
            case 1:
                String str12 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                StringBuilder sbM890m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(int12, "location (", str12, "): ", " -> ");
                sbM890m2.append(int22);
                return sbM890m2.toString();
            default:
                return "User visibility shade: " + logMessage.getBool1() + " media: " + logMessage.getBool2();
        }
    }
}
