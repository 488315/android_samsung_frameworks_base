package com.android.systemui.inputdevice.tutorial.ui.composable;

import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class TutorialActionState$Companion$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Map map = (Map) obj;
        TutorialActionState.Companion companion = TutorialActionState.Companion.$$INSTANCE;
        Object obj2 = map.get("class");
        String str = obj2 instanceof String ? (String) obj2 : null;
        if (Intrinsics.areEqual(str, TutorialActionState.NotStarted.class.getName()) || Intrinsics.areEqual(str, TutorialActionState.InProgress.class.getName())) {
            return TutorialActionState.NotStarted.INSTANCE;
        }
        if (Intrinsics.areEqual(str, TutorialActionState.Error.class.getName()) || Intrinsics.areEqual(str, TutorialActionState.InProgressAfterError.class.getName())) {
            return TutorialActionState.Error.INSTANCE;
        }
        if (!Intrinsics.areEqual(str, TutorialActionState.Finished.class.getName())) {
            return TutorialActionState.NotStarted.INSTANCE;
        }
        Object obj3 = map.get("animation");
        obj3.getClass();
        return new TutorialActionState.Finished(((Integer) obj3).intValue());
    }
}
