package com.android.compose.animation;

import android.view.View;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.systemui.animation.ComposableControllerFactory;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class ExpandableControllerKt {
    /* JADX WARN: Removed duplicated region for block: B:28:0x0090 A[PHI: r5
      0x0090: PHI (r5v7 kotlin.jvm.functions.Function0) = (r5v4 kotlin.jvm.functions.Function0), (r5v8 kotlin.jvm.functions.Function0) binds: [B:27:0x008e, B:23:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c0 A[PHI: r8
      0x00c0: PHI (r8v8 androidx.compose.foundation.shape.RoundedCornerShape) = 
      (r8v6 androidx.compose.foundation.shape.RoundedCornerShape)
      (r8v9 androidx.compose.foundation.shape.RoundedCornerShape)
     binds: [B:45:0x00be, B:41:0x00b7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00f5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0120 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0138  */
    /* renamed from: rememberExpandableController-T042LqI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ExpandableControllerImpl m910rememberExpandableControllerT042LqI(Function0 function0, RoundedCornerShape roundedCornerShape, long j, BorderStroke borderStroke, ComposableControllerFactory composableControllerFactory, Composer composer, int i, int i2) {
        long j2;
        Function0 function02;
        boolean z;
        RoundedCornerShape roundedCornerShape2;
        boolean z2;
        boolean zChanged;
        Object objRememberedValue;
        boolean z3;
        final ExpandableControllerImpl expandableControllerImpl;
        boolean zChanged2;
        Object objRememberedValue2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1466730014);
        if ((i2 & 4) != 0) {
            Color.Companion.getClass();
            j2 = Color.Unspecified;
        } else {
            j2 = j;
        }
        BorderStroke borderStroke2 = (i2 & 8) != 0 ? null : borderStroke;
        ComposableControllerFactory composableControllerFactory2 = (i2 & 16) != 0 ? null : composableControllerFactory;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.animation.rememberExpandableController (ExpandableController.kt:98)");
        }
        View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
        composerImpl.startReplaceGroup(1184942640);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue3 == composer$Companion$Empty$1) {
            objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(Boolean.TRUE);
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        final MutableState mutableState = (MutableState) objRememberedValue3;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1184945222);
        if (((i & 14) ^ 6) > 4) {
            function02 = function0;
            if (composerImpl.changed(function02)) {
                z = true;
            }
            boolean z4 = ((((i & 896) ^ 384) <= 256 && composerImpl.changed(j2)) || (i & 384) == 256) | z;
            if (((i & 112) ^ 48) <= 32) {
                roundedCornerShape2 = roundedCornerShape;
                if (composerImpl.changed(roundedCornerShape2)) {
                    z2 = true;
                }
                zChanged = z4 | z2 | ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(borderStroke2)) || (i & 3072) == 2048) | composerImpl.changed(view) | composerImpl.changed(density) | composerImpl.changed(layoutDirection) | composerImpl.changed(composableControllerFactory2);
                objRememberedValue = composerImpl.rememberedValue();
                if (zChanged || objRememberedValue == composer$Companion$Empty$1) {
                    z3 = false;
                    objRememberedValue = new ExpandableControllerImpl(function02, j2, roundedCornerShape2, borderStroke2, view, density, composableControllerFactory2, layoutDirection, new Function0() { // from class: com.android.compose.animation.ExpandableControllerKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            Boolean bool = (Boolean) mutableState.getValue();
                            bool.getClass();
                            return bool;
                        }
                    }, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                } else {
                    z3 = false;
                }
                expandableControllerImpl = (ExpandableControllerImpl) objRememberedValue;
                composerImpl.end(z3);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(1184963912);
                zChanged2 = composerImpl.changed(expandableControllerImpl);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (zChanged2 || objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new Function1() { // from class: com.android.compose.animation.ExpandableControllerKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            final ExpandableControllerImpl expandableControllerImpl2 = expandableControllerImpl;
                            final MutableState mutableState2 = mutableState;
                            return new DisposableEffectResult() { // from class: com.android.compose.animation.ExpandableControllerKt$rememberExpandableController_T042LqI$lambda$8$lambda$7$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                    mutableState2.setValue(Boolean.FALSE);
                                    TransitionAnimator.Companion.getClass();
                                    ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                    ExpandableControllerImpl$activityController$1 expandableControllerImpl$activityController$1 = expandableControllerImpl3.activityControllerForDisposal;
                                    expandableControllerImpl3.activityControllerForDisposal = null;
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(z3);
                EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue2, composerImpl);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(z3);
                return expandableControllerImpl;
            }
            roundedCornerShape2 = roundedCornerShape;
            if ((i & 48) == 32) {
                z2 = false;
            }
            zChanged = z4 | z2 | ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(borderStroke2)) || (i & 3072) == 2048) | composerImpl.changed(view) | composerImpl.changed(density) | composerImpl.changed(layoutDirection) | composerImpl.changed(composableControllerFactory2);
            objRememberedValue = composerImpl.rememberedValue();
            if (zChanged) {
                z3 = false;
                objRememberedValue = new ExpandableControllerImpl(function02, j2, roundedCornerShape2, borderStroke2, view, density, composableControllerFactory2, layoutDirection, new Function0() { // from class: com.android.compose.animation.ExpandableControllerKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Boolean bool = (Boolean) mutableState.getValue();
                        bool.getClass();
                        return bool;
                    }
                }, null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            expandableControllerImpl = (ExpandableControllerImpl) objRememberedValue;
            composerImpl.end(z3);
            Unit unit2 = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1184963912);
            zChanged2 = composerImpl.changed(expandableControllerImpl);
            objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged2) {
                objRememberedValue2 = new Function1() { // from class: com.android.compose.animation.ExpandableControllerKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final ExpandableControllerImpl expandableControllerImpl2 = expandableControllerImpl;
                        final MutableState mutableState2 = mutableState;
                        return new DisposableEffectResult() { // from class: com.android.compose.animation.ExpandableControllerKt$rememberExpandableController_T042LqI$lambda$8$lambda$7$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                mutableState2.setValue(Boolean.FALSE);
                                TransitionAnimator.Companion.getClass();
                                ExpandableControllerImpl expandableControllerImpl3 = expandableControllerImpl2;
                                ExpandableControllerImpl$activityController$1 expandableControllerImpl$activityController$1 = expandableControllerImpl3.activityControllerForDisposal;
                                expandableControllerImpl3.activityControllerForDisposal = null;
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(z3);
            EffectsKt.DisposableEffect(unit2, (Function1) objRememberedValue2, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl.end(z3);
            return expandableControllerImpl;
        }
        function02 = function0;
        if ((i & 6) != 4) {
            z = false;
        }
        boolean z42 = ((((i & 896) ^ 384) <= 256 && composerImpl.changed(j2)) || (i & 384) == 256) | z;
        if (((i & 112) ^ 48) <= 32) {
        }
        if ((i & 48) == 32) {
        }
        zChanged = z42 | z2 | ((((i & 7168) ^ 3072) > 2048 && composerImpl.changed(borderStroke2)) || (i & 3072) == 2048) | composerImpl.changed(view) | composerImpl.changed(density) | composerImpl.changed(layoutDirection) | composerImpl.changed(composableControllerFactory2);
        objRememberedValue = composerImpl.rememberedValue();
        if (zChanged) {
        }
        expandableControllerImpl = (ExpandableControllerImpl) objRememberedValue;
        composerImpl.end(z3);
        Unit unit22 = Unit.INSTANCE;
        composerImpl.startReplaceGroup(1184963912);
        zChanged2 = composerImpl.changed(expandableControllerImpl);
        objRememberedValue2 = composerImpl.rememberedValue();
        if (zChanged2) {
        }
        composerImpl.end(z3);
        EffectsKt.DisposableEffect(unit22, (Function1) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
        }
        composerImpl.end(z3);
        return expandableControllerImpl;
    }
}
