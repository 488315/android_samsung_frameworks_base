package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ComposableSingletons$PinInputDisplayKt {
    public static final ComposableSingletons$PinInputDisplayKt INSTANCE = new ComposableSingletons$PinInputDisplayKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f27lambda1 = new ComposableLambdaImpl(216806034, false, new Function3() { // from class: com.android.systemui.bouncer.ui.composable.ComposableSingletons$PinInputDisplayKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.ComposableSingletons$PinInputDisplayKt.lambda-1.<anonymous> (PinInputDisplay.kt:334)");
                    }
                    Arrangement arrangement = Arrangement.INSTANCE;
                    Dp.Companion companion = Dp.Companion;
                    arrangement.getClass();
                    Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(10);
                    Alignment.Companion.getClass();
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Modifier.Companion companion2 = Modifier.Companion;
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, vertical, composer, 54);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer;
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer, companion2);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl2.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function0);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m337setimpl(composer, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composer, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composer, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Painter painterPainterResource = PainterResources_androidKt.painterResource(R.drawable.ic_no_sim, composer, 0);
                    ColorFilter.Companion companion3 = ColorFilter.Companion;
                    MaterialTheme.INSTANCE.getClass();
                    ImageKt.Image(painterPainterResource, null, null, null, null, 0.0f, ColorFilter.Companion.m465tintxETnrds$default(companion3, MaterialTheme.getColorScheme(composer).onSurface), composer, 48, 60);
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.disable_carrier_button_text, composer), null, MaterialTheme.getColorScheme(composer).onSurface, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).bodyMedium, composer, 0, 0, 65530);
                    composerImpl2.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
