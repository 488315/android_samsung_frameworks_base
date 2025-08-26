package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class TextFieldKt {
    public static final float TextFieldWithLabelVerticalPadding;

    static {
        Dp.Companion companion = Dp.Companion;
        TextFieldWithLabelVerticalPadding = 8;
    }

    /* JADX WARN: Removed duplicated region for block: B:141:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x04ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TextFieldLayout(final Modifier modifier, Function2 function2, final Function2 function22, final Function3 function3, final Function2 function23, final Function2 function24, final Function2 function25, final Function2 function26, final boolean z, TextFieldLabelPosition textFieldLabelPosition, FloatProducer floatProducer, final Function2 function27, Function2 function28, PaddingValues paddingValues, Composer composer, final int i, final int i2) {
        int i3;
        boolean z2;
        int i4;
        PaddingValues paddingValues2;
        Object textFieldMeasurePolicy;
        int i5;
        ComposerImpl composerImpl;
        int i6;
        PaddingValues paddingValues3;
        int i7;
        Function2 function29;
        float f;
        TextFieldLabelPosition textFieldLabelPosition2;
        Modifier modifierM129paddingqDBjuR0$default;
        final FloatProducer floatProducer2;
        int i8;
        float f2;
        float f3;
        Function2 function210;
        Function2 function211;
        boolean z3;
        boolean z4;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(395129232);
        if ((i & 6) == 0) {
            i3 = i | (composerImpl2.changed(modifier) ? 4 : 2);
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changedInstance(function2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changedInstance(function22) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl2.changedInstance(function3) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl2.changedInstance(function23) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function24) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function25) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function26) ? 8388608 : 4194304;
        }
        if ((100663296 & i) == 0) {
            z2 = z;
            i3 |= composerImpl2.changed(z2) ? 67108864 : 33554432;
        } else {
            z2 = z;
        }
        if ((i & 805306368) == 0) {
            i3 |= composerImpl2.changed(textFieldLabelPosition) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((i2 & 6) == 0) {
            i4 = i2 | ((i2 & 8) == 0 ? composerImpl2.changed(floatProducer) : composerImpl2.changedInstance(floatProducer) ? 4 : 2);
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= composerImpl2.changedInstance(function27) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerImpl2.changedInstance(function28) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            paddingValues2 = paddingValues;
            i4 |= composerImpl2.changed(paddingValues2) ? 2048 : 1024;
        } else {
            paddingValues2 = paddingValues;
        }
        int i9 = i4;
        if ((i3 & 306783379) == 306783378 && (i9 & 1171) == 1170 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            function210 = function2;
            function211 = function28;
            paddingValues3 = paddingValues2;
            composerImpl = composerImpl2;
            floatProducer2 = floatProducer;
            textFieldLabelPosition2 = textFieldLabelPosition;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.TextFieldLayout (TextField.kt:674)");
            }
            float fMinimizedLabelHalfHeight = TextFieldImplKt.minimizedLabelHalfHeight(composerImpl2);
            int i10 = i9 & 14;
            boolean zChanged = ((i9 & 7168) == 2048) | ((i3 & 1879048192) == 536870912) | ((i3 & 234881024) == 67108864) | (i10 == 4 || ((i9 & 8) != 0 && composerImpl2.changed(floatProducer))) | composerImpl2.changed(fMinimizedLabelHalfHeight);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    i5 = i10;
                    composerImpl = composerImpl2;
                    boolean z5 = z2;
                    i6 = 3;
                    paddingValues3 = paddingValues2;
                    textFieldMeasurePolicy = new TextFieldMeasurePolicy(z5, textFieldLabelPosition, floatProducer, paddingValues3, fMinimizedLabelHalfHeight, null);
                    composerImpl.updateRememberedValue(textFieldMeasurePolicy);
                } else {
                    i5 = i10;
                    paddingValues3 = paddingValues2;
                    composerImpl = composerImpl2;
                    textFieldMeasurePolicy = objRememberedValue;
                    i6 = 3;
                }
                TextFieldMeasurePolicy textFieldMeasurePolicy2 = (TextFieldMeasurePolicy) textFieldMeasurePolicy;
                LayoutDirection layoutDirection = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                int i11 = i6;
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                Applier applier = composerImpl.applier;
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Function2 function212 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl, textFieldMeasurePolicy2, function212);
                Function2 function213 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function213);
                Function2 function214 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function214);
                }
                Function2 function215 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function215);
                function27.invoke(composerImpl, Integer.valueOf((i9 >> 3) & 14));
                if (function23 != null) {
                    composerImpl.startReplaceGroup(-1349505616);
                    Modifier modifierLayoutId = LayoutIdKt.layoutId(Modifier.Companion, "Leading");
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                    Modifier modifierThen = modifierLayoutId.then(MinimumInteractiveModifier.INSTANCE);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function215);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 12) & 14, function23, composerImpl, true, false);
                } else {
                    composerImpl.startReplaceGroup(-1349260561);
                    composerImpl.end(false);
                }
                if (function24 != null) {
                    composerImpl.startReplaceGroup(-1349217874);
                    Modifier modifierLayoutId2 = LayoutIdKt.layoutId(Modifier.Companion, "Trailing");
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                    Modifier modifierThen2 = modifierLayoutId2.then(MinimumInteractiveModifier.INSTANCE);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen2);
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function215);
                    BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                    i7 = 0;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 15) & 14, function24, composerImpl, true, false);
                } else {
                    i7 = 0;
                    composerImpl.startReplaceGroup(-1348970897);
                    composerImpl.end(false);
                }
                float fCalculateStartPadding = PaddingKt.calculateStartPadding(paddingValues3, layoutDirection);
                float fCalculateEndPadding = PaddingKt.calculateEndPadding(paddingValues3, layoutDirection);
                float fTextFieldHorizontalIconPadding = TextFieldImplKt.textFieldHorizontalIconPadding(composerImpl);
                if (function23 != null) {
                    fCalculateStartPadding -= fTextFieldHorizontalIconPadding;
                    float f4 = i7;
                    if (fCalculateStartPadding < f4) {
                        fCalculateStartPadding = f4;
                    }
                }
                float f5 = fCalculateStartPadding;
                if (function24 != null) {
                    fCalculateEndPadding -= fTextFieldHorizontalIconPadding;
                    float f6 = i7;
                    if (fCalculateEndPadding < f6) {
                        fCalculateEndPadding = f6;
                    }
                }
                if (function25 != null) {
                    composerImpl.startReplaceGroup(-1348194502);
                    Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(LayoutIdKt.layoutId(Modifier.Companion, "Prefix"), TextFieldImplKt.MinTextLineHeight, 0.0f, 2), i11), f5, 0.0f, TextFieldImplKt.PrefixSuffixTextPadding, 0.0f, 10);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default2);
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function215);
                    BoxScopeInstance boxScopeInstance3 = BoxScopeInstance.INSTANCE;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 18) & 14, function25, composerImpl, true, false);
                } else {
                    composerImpl.startReplaceGroup(-1347866801);
                    composerImpl.end(false);
                }
                if (function26 != null) {
                    composerImpl.startReplaceGroup(-1347823556);
                    float f7 = fCalculateEndPadding;
                    Modifier modifierM129paddingqDBjuR0$default3 = PaddingKt.m129paddingqDBjuR0$default(SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(LayoutIdKt.layoutId(Modifier.Companion, "Suffix"), TextFieldImplKt.MinTextLineHeight, 0.0f, 2), 3), TextFieldImplKt.PrefixSuffixTextPadding, 0.0f, f7, 0.0f, 10);
                    f = f7;
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy4 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default3);
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy4, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope5, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier5, function215);
                    BoxScopeInstance boxScopeInstance4 = BoxScopeInstance.INSTANCE;
                    function29 = function26;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 21) & 14, function29, composerImpl, true, false);
                } else {
                    function29 = function26;
                    f = fCalculateEndPadding;
                    composerImpl.startReplaceGroup(-1347497777);
                    composerImpl.end(false);
                }
                textFieldLabelPosition2 = textFieldLabelPosition;
                if (textFieldLabelPosition2 instanceof TextFieldLabelPosition.Above) {
                    Modifier.Companion companion2 = Modifier.Companion;
                    float f8 = TextFieldImplKt.AboveLabelHorizontalPadding;
                    modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(companion2, f8, 0.0f, f8, TextFieldImplKt.AboveLabelBottomPadding, 2);
                } else {
                    modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, f5, 0.0f, f, 0.0f, 10);
                }
                if (function22 != null) {
                    composerImpl.startReplaceGroup(-1346997964);
                    Modifier modifierLayoutId3 = LayoutIdKt.layoutId(Modifier.Companion, "Label");
                    if (i5 != 4) {
                        if ((i9 & 8) != 0) {
                            floatProducer2 = floatProducer;
                            if (composerImpl.changedInstance(floatProducer2)) {
                            }
                            Object objRememberedValue2 = composerImpl.rememberedValue();
                            if (!z4) {
                                companion.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    objRememberedValue2 = new Function0() { // from class: androidx.compose.material3.TextFieldKt$TextFieldLayout$1$5$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return Dp.m837boximpl(MathHelpersKt.lerp(TextFieldImplKt.MinTextLineHeight, TextFieldImplKt.MinFocusedLabelLineHeight, floatProducer2.invoke()));
                                        }
                                    };
                                    composerImpl.updateRememberedValue(objRememberedValue2);
                                }
                                Modifier modifierThen3 = SizeKt.wrapContentHeight$default(TextFieldImplKt.textFieldLabelMinHeight(modifierLayoutId3, (Function0) objRememberedValue2), 3).then(modifierM129paddingqDBjuR0$default);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy5 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash6 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope6 = composerImpl.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier6 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen3);
                                if (applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl.startReusableNode();
                                if (composerImpl.inserting) {
                                    composerImpl.createNode(function0);
                                } else {
                                    composerImpl.useNode();
                                }
                                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy5, function212);
                                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope6, function213);
                                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash6))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash6, composerImpl, currentCompositeKeyHash6, function214);
                                }
                                Updater.m337setimpl(composerImpl, modifierMaterializeModifier6, function215);
                                BoxScopeInstance boxScopeInstance5 = BoxScopeInstance.INSTANCE;
                                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 6) & 14, function22, composerImpl, true, false);
                            }
                        } else {
                            floatProducer2 = floatProducer;
                        }
                        z4 = false;
                        Object objRememberedValue22 = composerImpl.rememberedValue();
                        if (!z4) {
                        }
                    } else {
                        floatProducer2 = floatProducer;
                    }
                    z4 = true;
                    Object objRememberedValue222 = composerImpl.rememberedValue();
                    if (!z4) {
                    }
                } else {
                    floatProducer2 = floatProducer;
                    composerImpl.startReplaceGroup(-1346602993);
                    composerImpl.end(false);
                }
                Modifier.Companion companion3 = Modifier.Companion;
                Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(companion3, TextFieldImplKt.MinTextLineHeight, 0.0f, 2), 3);
                if (function25 == null) {
                    f2 = f5;
                    i8 = 0;
                } else {
                    i8 = 0;
                    Dp.Companion companion4 = Dp.Companion;
                    f2 = 0;
                }
                if (function29 == null) {
                    f3 = f;
                } else {
                    Dp.Companion companion5 = Dp.Companion;
                    f3 = i8;
                }
                Modifier modifierM129paddingqDBjuR0$default4 = PaddingKt.m129paddingqDBjuR0$default(modifierWrapContentHeight$default, f2, 0.0f, f3, 0.0f, 10);
                if (function3 != null) {
                    composerImpl.startReplaceGroup(-1346233008);
                    function3.invoke(LayoutIdKt.layoutId(companion3, "Hint").then(modifierM129paddingqDBjuR0$default4), composerImpl, Integer.valueOf((i3 >> 6) & 112));
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(-1346141713);
                    composerImpl.end(false);
                }
                Modifier modifierThen4 = LayoutIdKt.layoutId(companion3, "TextField").then(modifierM129paddingqDBjuR0$default4);
                Alignment.Companion.getClass();
                BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy6 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, true);
                int currentCompositeKeyHash7 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope7 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier7 = ComposedModifierKt.materializeModifier(composerImpl, modifierThen4);
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy6, function212);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope7, function213);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash7))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash7, composerImpl, currentCompositeKeyHash7, function214);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier7, function215);
                BoxScopeInstance boxScopeInstance6 = BoxScopeInstance.INSTANCE;
                function210 = function2;
                function210.invoke(composerImpl, Integer.valueOf((i3 >> 3) & 14));
                composerImpl.end(true);
                if (function28 != null) {
                    composerImpl.startReplaceGroup(-1345893062);
                    Modifier modifierPadding = PaddingKt.padding(SizeKt.wrapContentHeight$default(SizeKt.m133heightInVpY3zN4$default(LayoutIdKt.layoutId(companion3, "Supporting"), TextFieldImplKt.MinSupportingTextLineHeight, 0.0f, 2), 3), TextFieldDefaults.m314supportingTextPaddinga9UjIt4$material3_release$default(TextFieldDefaults.INSTANCE));
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy7 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash8 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope8 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier8 = ComposedModifierKt.materializeModifier(composerImpl, modifierPadding);
                    if (applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy7, function212);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope8, function213);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash8))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash8, composerImpl, currentCompositeKeyHash8, function214);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier8, function215);
                    function211 = function28;
                    z3 = true;
                    SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i9 >> 6) & 14, function211, composerImpl, true, false);
                } else {
                    function211 = function28;
                    z3 = true;
                    composerImpl.startReplaceGroup(-1345503857);
                    composerImpl.end(false);
                }
                composerImpl.end(z3);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function2 function216 = function210;
            final TextFieldLabelPosition textFieldLabelPosition3 = textFieldLabelPosition2;
            final FloatProducer floatProducer3 = floatProducer2;
            final PaddingValues paddingValues4 = paddingValues3;
            final Function2 function217 = function211;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextFieldKt.TextFieldLayout.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldKt.TextFieldLayout(modifier, function216, function22, function3, function23, function24, function25, function26, z, textFieldLabelPosition3, floatProducer3, function27, function217, paddingValues4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
