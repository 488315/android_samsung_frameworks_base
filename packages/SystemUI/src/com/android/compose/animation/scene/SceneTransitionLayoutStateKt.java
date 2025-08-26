package com.android.compose.animation.scene;

import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.MotionScheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class SceneTransitionLayoutStateKt {
    public static final MutableSceneTransitionLayoutStateImpl rememberMutableSceneTransitionLayoutState(SceneKey sceneKey, final SceneTransitions sceneTransitions, Function1 function1, Function1 function12, Function2 function2, Function1 function13, Function1 function14, Composer composer, int i, int i2) {
        Function1 function15;
        Function1 function16;
        Function2 function22;
        Function1 function17;
        Function1 function18;
        Object obj;
        MotionScheme motionScheme;
        boolean z;
        final MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1321255149);
        EmptySet emptySet = EmptySet.INSTANCE;
        int i3 = i2 & 8;
        Composer.Companion companion = Composer.Companion;
        if (i3 != 0) {
            Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 531687251, companion);
            if (objM == Composer.Companion.Empty) {
                objM = new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(0);
                composerImpl.updateRememberedValue(objM);
            }
            composerImpl.end(false);
            function15 = (Function1) objM;
        } else {
            function15 = function1;
        }
        if ((i2 & 16) != 0) {
            Object objM2 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 531689043, companion);
            if (objM2 == Composer.Companion.Empty) {
                objM2 = new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(1);
                composerImpl.updateRememberedValue(objM2);
            }
            composerImpl.end(false);
            function16 = (Function1) objM2;
        } else {
            function16 = function12;
        }
        Object objM3 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 531690835, companion);
        Object obj2 = Composer.Companion.Empty;
        if (objM3 == obj2) {
            objM3 = new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda0(1);
            composerImpl.updateRememberedValue(objM3);
        }
        final Function1 function19 = (Function1) objM3;
        composerImpl.end(false);
        if ((i2 & 64) != 0) {
            Object objM4 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 531693435, companion);
            if (objM4 == obj2) {
                objM4 = new MutableSceneTransitionLayoutStateImpl$$ExternalSyntheticLambda2();
                composerImpl.updateRememberedValue(objM4);
            }
            composerImpl.end(false);
            function22 = (Function2) objM4;
        } else {
            function22 = function2;
        }
        if ((i2 & 128) != 0) {
            Object objM5 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 531695981, companion);
            if (objM5 == obj2) {
                final int i4 = 0;
                objM5 = new Function1() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutStateKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        switch (i4) {
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objM5);
            }
            composerImpl.end(false);
            function17 = (Function1) objM5;
        } else {
            function17 = function13;
        }
        if ((i2 & 256) != 0) {
            Object objM6 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 531698029, companion);
            if (objM6 == obj2) {
                final int i5 = 1;
                objM6 = new Function1() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutStateKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj3) {
                        switch (i5) {
                        }
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objM6);
            }
            composerImpl.end(false);
            function18 = (Function1) objM6;
        } else {
            function18 = function14;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.animation.scene.rememberMutableSceneTransitionLayoutState (SceneTransitionLayoutState.kt:285)");
        }
        MaterialTheme.INSTANCE.getClass();
        MotionScheme motionScheme2 = MaterialTheme.getMotionScheme(composerImpl);
        composerImpl.startReplaceGroup(531706903);
        Object objRememberedValue = composerImpl.rememberedValue();
        companion.getClass();
        if (objRememberedValue == obj2) {
            motionScheme = motionScheme2;
            z = false;
            Object mutableSceneTransitionLayoutStateImpl2 = new MutableSceneTransitionLayoutStateImpl(sceneKey, motionScheme, sceneTransitions, emptySet, function15, function16, function19, function22, function17, function18, false);
            composerImpl.updateRememberedValue(mutableSceneTransitionLayoutStateImpl2);
            obj = obj2;
            objRememberedValue = mutableSceneTransitionLayoutStateImpl2;
        } else {
            obj = obj2;
            motionScheme = motionScheme2;
            z = false;
        }
        MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl3 = (MutableSceneTransitionLayoutStateImpl) objRememberedValue;
        composerImpl.end(z);
        composerImpl.startReplaceGroup(531726172);
        boolean zChangedInstance = ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(function15)) || (i & 3072) == 2048) | composerImpl.changedInstance(sceneTransitions) | composerImpl.changed(motionScheme) | ((((57344 & i) ^ 24576) > 16384 && composerImpl.changed(function16)) || (i & 24576) == 16384) | ((((458752 & i) ^ 196608) > 131072 && composerImpl.changed(function19)) || (i & 196608) == 131072) | ((((3670016 & i) ^ 1572864) > 1048576 && composerImpl.changed(function22)) || (i & 1572864) == 1048576) | ((((29360128 & i) ^ 12582912) > 8388608 && composerImpl.changed(function17)) || (i & 12582912) == 8388608) | ((((234881024 & i) ^ 100663296) > 67108864 && composerImpl.changed(function18)) || (i & 100663296) == 67108864) | ((((1879048192 & i) ^ 805306368) > 536870912 && composerImpl.changed(false)) || (i & 805306368) == 536870912);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == obj) {
            final Function1 function110 = function17;
            final Function1 function111 = function15;
            final Function2 function23 = function22;
            final Function1 function112 = function18;
            final MotionScheme motionScheme3 = motionScheme;
            final Function1 function113 = function16;
            mutableSceneTransitionLayoutStateImpl = mutableSceneTransitionLayoutStateImpl3;
            Object obj3 = new Function0() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutStateKt$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SceneTransitions sceneTransitions2 = sceneTransitions;
                    MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl4 = mutableSceneTransitionLayoutStateImpl;
                    mutableSceneTransitionLayoutStateImpl4.transitions = sceneTransitions2;
                    mutableSceneTransitionLayoutStateImpl4.motionScheme = motionScheme3;
                    mutableSceneTransitionLayoutStateImpl4.canChangeScene = function111;
                    mutableSceneTransitionLayoutStateImpl4.canShowOverlay = function113;
                    mutableSceneTransitionLayoutStateImpl4.canHideOverlay = function19;
                    mutableSceneTransitionLayoutStateImpl4.canReplaceOverlay = function23;
                    mutableSceneTransitionLayoutStateImpl4.onTransitionStart = function110;
                    mutableSceneTransitionLayoutStateImpl4.onTransitionEnd = function112;
                    mutableSceneTransitionLayoutStateImpl4.deferTransitionProgress = false;
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(obj3);
            objRememberedValue2 = obj3;
        } else {
            mutableSceneTransitionLayoutStateImpl = mutableSceneTransitionLayoutStateImpl3;
        }
        composerImpl.end(false);
        EffectsKt.SideEffect((Function0) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableSceneTransitionLayoutStateImpl;
    }
}
