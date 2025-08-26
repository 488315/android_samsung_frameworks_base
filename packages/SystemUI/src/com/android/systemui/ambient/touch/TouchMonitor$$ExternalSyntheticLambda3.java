package com.android.systemui.ambient.touch;

import android.graphics.Rect;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.log.core.LogMessage;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class TouchMonitor$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TouchMonitor$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                ((LogMessage) obj).setStr1((String) ((TouchMonitor) obj2).mActiveTouchSessions.stream().map(new TouchMonitor$$ExternalSyntheticLambda7(0)).map(new TouchMonitor$$ExternalSyntheticLambda7(1)).collect(Collectors.joining(",")));
                break;
            case 1:
                ((LogMessage) obj).setInt1(((TouchMonitor.TouchSessionImpl) obj2).hashCode());
                break;
            default:
                int i = TouchMonitor.AnonymousClass2.$r8$clinit;
                ((LogMessage) obj).setStr1(((Rect) obj2).toString());
                break;
        }
        return Unit.INSTANCE;
    }
}
