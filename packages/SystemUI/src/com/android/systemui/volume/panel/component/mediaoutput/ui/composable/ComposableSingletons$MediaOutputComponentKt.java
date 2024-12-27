package com.android.systemui.volume.panel.component.mediaoutput.ui.composable;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.ui.compose.ColorKt;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;

public final class ComposableSingletons$MediaOutputComponentKt {
    public static final ComposableSingletons$MediaOutputComponentKt INSTANCE = new ComposableSingletons$MediaOutputComponentKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f82lambda1 = new ComposableLambdaImpl(465697057, false, new Function4() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.ComposableSingletons$MediaOutputComponentKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            Composer composer = (Composer) obj3;
            ((Number) obj4).intValue();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion;
            FillElement fillElement = SizeKt.FillWholeMaxSize;
            companion.then(fillElement);
            long color = ColorKt.toColor(((DeviceIconViewModel) obj2).getBackgroundColor(), composer);
            Dp.Companion companion2 = Dp.Companion;
            SpacerKt.Spacer(composer, BackgroundKt.m24backgroundbw27NRU(fillElement, color, RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(12)));
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f83lambda2 = new ComposableLambdaImpl(1530310922, false, new Function4() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.ComposableSingletons$MediaOutputComponentKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            DeviceIconViewModel deviceIconViewModel = (DeviceIconViewModel) obj2;
            Composer composer = (Composer) obj3;
            ((Number) obj4).intValue();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icon icon = deviceIconViewModel.getIcon();
            long color = ColorKt.toColor(deviceIconViewModel.getIconColor(), composer);
            Dp.Companion companion = Dp.Companion;
            IconKt.m938IconFNF3uiM(icon, PaddingKt.m102padding3ABfNKs(Modifier.Companion, 12).then(SizeKt.FillWholeMaxSize), color, composer, 48, 0);
            return Unit.INSTANCE;
        }
    });
}
