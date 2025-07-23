package com.android.systemui.inputdevice.tutorial.ui.composable;

import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TutorialActionState$Companion$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TutorialActionState tutorialActionState = (TutorialActionState) obj2;
        TutorialActionState.Companion companion = TutorialActionState.Companion.$$INSTANCE;
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.put("class", tutorialActionState.getClass().getName());
        if (tutorialActionState instanceof TutorialActionState.Finished) {
            mapBuilder.put("animation", Integer.valueOf(((TutorialActionState.Finished) tutorialActionState).successAnimation));
        }
        return mapBuilder.build();
    }
}
