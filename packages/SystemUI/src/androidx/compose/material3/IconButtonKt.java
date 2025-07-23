package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.internal.ChildSemanticsNodeElement;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.DpSize;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class IconButtonKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void FilledIconButton(final int r19, final int r20, androidx.compose.foundation.interaction.MutableInteractionSource r21, androidx.compose.material3.IconButtonColors r22, androidx.compose.runtime.Composer r23, androidx.compose.ui.Modifier r24, androidx.compose.ui.graphics.Shape r25, final kotlin.jvm.functions.Function0 r26, final kotlin.jvm.functions.Function2 r27, boolean r28) {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.IconButtonKt.FilledIconButton(int, int, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.material3.IconButtonColors, androidx.compose.runtime.Composer, androidx.compose.ui.Modifier, androidx.compose.ui.graphics.Shape, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void IconButton(final int r18, final int r19, androidx.compose.foundation.interaction.MutableInteractionSource r20, androidx.compose.material3.IconButtonColors r21, androidx.compose.runtime.Composer r22, androidx.compose.ui.Modifier r23, androidx.compose.ui.graphics.Shape r24, final kotlin.jvm.functions.Function0 r25, final kotlin.jvm.functions.Function2 r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.IconButtonKt.IconButton(int, int, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.material3.IconButtonColors, androidx.compose.runtime.Composer, androidx.compose.ui.Modifier, androidx.compose.ui.graphics.Shape, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function2, boolean):void");
    }

    public static final void IconButtonImpl(final Modifier modifier, final Function0 function0, final boolean z, final Shape shape, final IconButtonColors iconButtonColors, final MutableInteractionSource mutableInteractionSource, final Function2 function2, Composer composer, final int i) {
        int i2;
        MutableInteractionSource mutableInteractionSource2;
        Modifier then;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1119228543);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(shape) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(iconButtonColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i3 = i2;
        if ((599187 & i3) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.IconButtonImpl (IconButton.kt:246)");
            }
            if (mutableInteractionSource == null) {
                composerImpl.startReplaceGroup(843796813);
                Object rememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (rememberedValue == Composer.Companion.Empty) {
                    rememberedValue = InteractionSourceKt.MutableInteractionSource();
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                mutableInteractionSource2 = (MutableInteractionSource) rememberedValue;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1135597258);
                composerImpl.end(false);
                mutableInteractionSource2 = mutableInteractionSource;
            }
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
            Modifier then2 = modifier.then(MinimumInteractiveModifier.INSTANCE);
            long m267smallContainerSizeNwlBFI$default = IconButtonDefaults.m267smallContainerSizeNwlBFI$default(IconButtonDefaults.INSTANCE);
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            Modifier m26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(ClipKt.clip(SizeKt.m140sizeVpY3zN4(then2, DpSize.m845getWidthD9Ej5fM(m267smallContainerSizeNwlBFI$default), DpSize.m844getHeightD9Ej5fM(m267smallContainerSizeNwlBFI$default)), shape), z ? iconButtonColors.containerColor : iconButtonColors.disabledContainerColor, shape);
            Role.Companion.getClass();
            MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource2;
            then = ClickableKt.m34clickableO2vRcR0$default(m26backgroundbw27NRU, mutableInteractionSource3, RippleKt.m280rippleH2RKhps$default(0.0f, false, 7), z, null, Role.m713boximpl(0), function0, 8).then(new ChildSemanticsNodeElement(new Function1() { // from class: androidx.compose.material3.internal.ChildParentSemanticsKt$childSemantics$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj3) {
                    return Unit.INSTANCE;
                }
            }));
            if (mutableInteractionSource3 == null) {
                mutableInteractionSource3 = InteractionSourceKt.MutableInteractionSource();
            }
            Modifier then3 = then.then(new InteractionSourceModifierElement(mutableInteractionSource3));
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then3);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function02);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(z ? iconButtonColors.contentColor : iconButtonColors.disabledContentColor)), function2, composerImpl, ((i3 >> 15) & 112) | 8);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconButtonKt$IconButtonImpl$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    IconButtonKt.IconButtonImpl(Modifier.this, function0, z, shape, iconButtonColors, mutableInteractionSource, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SurfaceIconButton(final Function0 function0, final Modifier modifier, final boolean z, final Shape shape, final IconButtonColors iconButtonColors, final BorderStroke borderStroke, final MutableInteractionSource mutableInteractionSource, final Function2 function2, Composer composer, final int i) {
        Function0 function02;
        int i2;
        Shape shape2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1142501472);
        if ((i & 6) == 0) {
            function02 = function0;
            i2 = (composerImpl.changedInstance(function02) ? 4 : 2) | i;
        } else {
            function02 = function0;
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            shape2 = shape;
            i2 |= composerImpl.changed(shape2) ? 2048 : 1024;
        } else {
            shape2 = shape;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(iconButtonColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(borderStroke) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 8388608 : 4194304;
        }
        if ((4793491 & i2) == 4793490 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SurfaceIconButton (IconButton.kt:1195)");
            }
            int i3 = i2 & 8078;
            int i4 = i2 << 9;
            SurfaceKt.m304Surfaceo_FOJdg(function02, SemanticsModifierKt.semantics(modifier, false, new Function1() { // from class: androidx.compose.material3.IconButtonKt$SurfaceIconButton$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    Role.Companion.getClass();
                    SemanticsPropertiesKt.m717setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                    return Unit.INSTANCE;
                }
            }), z, shape2, z ? iconButtonColors.containerColor : iconButtonColors.disabledContainerColor, z ? iconButtonColors.contentColor : iconButtonColors.disabledContentColor, 0.0f, borderStroke, mutableInteractionSource, ComposableLambdaKt.rememberComposableLambda(524891765, new Function2() { // from class: androidx.compose.material3.IconButtonKt$SurfaceIconButton$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.SurfaceIconButton.<anonymous> (IconButton.kt:1205)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    long m267smallContainerSizeNwlBFI$default = IconButtonDefaults.m267smallContainerSizeNwlBFI$default(IconButtonDefaults.INSTANCE);
                    FillElement fillElement = SizeKt.FillWholeMaxWidth;
                    Modifier m140sizeVpY3zN4 = SizeKt.m140sizeVpY3zN4(companion, DpSize.m845getWidthD9Ej5fM(m267smallContainerSizeNwlBFI$default), DpSize.m844getHeightD9Ej5fM(m267smallContainerSizeNwlBFI$default));
                    Alignment.Companion.getClass();
                    BiasAlignment biasAlignment = Alignment.Companion.Center;
                    Function2 function22 = Function2.this;
                    MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m140sizeVpY3zN4);
                    ComposeUiNode.Companion.getClass();
                    Function0 function03 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl3.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function03);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                    }
                    Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    function22.invoke(composer2, 0);
                    composerImpl3.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, i3 | (i4 & 234881024) | (i4 & 1879048192), 192);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconButtonKt$SurfaceIconButton$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    IconButtonKt.SurfaceIconButton(Function0.this, modifier, z, shape, iconButtonColors, borderStroke, mutableInteractionSource, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
