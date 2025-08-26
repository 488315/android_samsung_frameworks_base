package com.android.systemui.volume.panel.component.mediaoutput.ui.composable;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.ColorKt;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
public final class ComposableSingletons$MediaOutputComponentKt {
    public static final ComposableSingletons$MediaOutputComponentKt INSTANCE = new ComposableSingletons$MediaOutputComponentKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f115lambda1 = new ComposableLambdaImpl(465697057, false, new Function4() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.ComposableSingletons$MediaOutputComponentKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            DeviceIconViewModel deviceIconViewModel = (DeviceIconViewModel) obj2;
            Composer composer = (Composer) obj3;
            ((Number) obj4).intValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.ComposableSingletons$MediaOutputComponentKt.lambda-1.<anonymous> (MediaOutputComponent.kt:166)");
            }
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
            long color = ColorKt.toColor(deviceIconViewModel.getBackgroundColor(), composer);
            Dp.Companion companion = Dp.Companion;
            SpacerKt.Spacer(composer, BackgroundKt.m26backgroundbw27NRU(modifierFillMaxSize, color, RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(12)));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f116lambda2 = new ComposableLambdaImpl(1530310922, false, new Function4() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.ComposableSingletons$MediaOutputComponentKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            DeviceIconViewModel deviceIconViewModel = (DeviceIconViewModel) obj2;
            Composer composer = (Composer) obj3;
            ((Number) obj4).intValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.ComposableSingletons$MediaOutputComponentKt.lambda-2.<anonymous> (MediaOutputComponent.kt:193)");
            }
            Icon icon = deviceIconViewModel.getIcon();
            long color = ColorKt.toColor(deviceIconViewModel.getIconColor(), composer);
            Dp.Companion companion = Dp.Companion;
            IconKt.m1074IconFNF3uiM(icon, SizeKt.fillMaxSize(PaddingKt.m125padding3ABfNKs(Modifier.Companion, 12), 1.0f), color, composer, 48, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
