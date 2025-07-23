package com.android.systemui.media.controls.shared;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = MediaLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("removing media ", logMessage.getStr1(), ", reason: ", logMessage.getStr2());
            case 1:
                int i2 = MediaLogger.$r8$clinit;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str2 = logMessage.getStr2();
                StringBuilder m = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("add media ", str1, ", active: ", ", reason: ", bool1);
                m.append(str2);
                return m.toString();
            case 2:
                int i3 = MediaLogger.$r8$clinit;
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                String str22 = logMessage.getStr2();
                StringBuilder m2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("set media active ", str12, ", active: ", ", callStack: ", bool12);
                m2.append(str22);
                return m2.toString();
            case 3:
                int i4 = MediaLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("duplicate media notification ", logMessage.getStr1(), " posted");
            case 4:
                int i5 = MediaLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("adding media card ", logMessage.getStr1(), " to carousel");
            default:
                int i6 = MediaLogger.$r8$clinit;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("removing media card ", logMessage.getStr1(), " from carousel");
        }
    }
}
