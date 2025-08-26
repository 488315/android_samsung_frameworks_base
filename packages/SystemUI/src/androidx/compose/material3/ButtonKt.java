package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.BorderStrokeKt;
import androidx.compose.foundation.interaction.FocusInteraction$Focus;
import androidx.compose.foundation.interaction.HoverInteraction$Enter;
import androidx.compose.foundation.interaction.Interaction;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.internal.ProvideContentColorTextStyleKt;
import androidx.compose.material3.tokens.ButtonSmallTokens;
import androidx.compose.material3.tokens.FilledButtonTokens;
import androidx.compose.material3.tokens.OutlinedButtonTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ButtonKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0214  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0242  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:246:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x010a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Button(final Function0 function0, Modifier modifier, boolean z, Shape shape, ButtonColors buttonColors, ButtonElevation buttonElevation, BorderStroke borderStroke, PaddingValues paddingValues, MutableInteractionSource mutableInteractionSource, final Function3 function3, Composer composer, final int i, final int i2) {
        Function0 function02;
        int i3;
        Modifier modifier2;
        int i4;
        boolean z2;
        Shape shape2;
        ButtonColors buttonColors2;
        boolean z3;
        final ButtonElevation buttonElevation2;
        int i5;
        BorderStroke borderStroke2;
        int i6;
        int i7;
        final PaddingValues paddingValues2;
        Shape shape3;
        BorderStroke borderStroke3;
        MutableInteractionSource mutableInteractionSource2;
        MutableInteractionSource mutableInteractionSource3;
        Modifier modifier3;
        long j;
        ButtonColors buttonColors3;
        long j2;
        Animatable animatable;
        AnimationState animationState;
        boolean z4;
        float f;
        ComposerImpl composerImpl;
        final PaddingValues paddingValues3;
        final Modifier modifier4;
        final Shape shape4;
        final BorderStroke borderStroke4;
        final MutableInteractionSource mutableInteractionSource4;
        final ButtonColors buttonColors4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(650121315);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            function02 = function0;
        } else {
            function02 = function0;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(function02) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        int i8 = 2 & i2;
        if (i8 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    z2 = z;
                    i3 |= composerImpl2.changed(z2) ? 256 : 128;
                }
                if ((i & 3072) == 0) {
                    if ((i2 & 8) == 0) {
                        shape2 = shape;
                        int i9 = composerImpl2.changed(shape2) ? 2048 : 1024;
                        i3 |= i9;
                    } else {
                        shape2 = shape;
                    }
                    i3 |= i9;
                } else {
                    shape2 = shape;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        buttonColors2 = buttonColors;
                        int i10 = composerImpl2.changed(buttonColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i10;
                    } else {
                        buttonColors2 = buttonColors;
                    }
                    i3 |= i10;
                } else {
                    buttonColors2 = buttonColors;
                }
                if ((i & 196608) == 0) {
                    z3 = true;
                    buttonElevation2 = buttonElevation;
                    i3 |= ((i2 & 32) == 0 && composerImpl2.changed(buttonElevation2)) ? 131072 : 65536;
                } else {
                    z3 = true;
                    buttonElevation2 = buttonElevation;
                }
                i5 = i2 & 64;
                if (i5 != 0) {
                    i3 |= 1572864;
                    borderStroke2 = borderStroke;
                } else {
                    borderStroke2 = borderStroke;
                    if ((i & 1572864) == 0) {
                        i3 |= composerImpl2.changed(borderStroke2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                }
                i6 = 128 & i2;
                if (i6 != 0) {
                    i3 |= 12582912;
                } else if ((i & 12582912) == 0) {
                    i3 |= composerImpl2.changed(paddingValues) ? 8388608 : 4194304;
                }
                i7 = i2 & 256;
                if (i7 == 0) {
                    if ((100663296 & i) == 0) {
                        i3 |= composerImpl2.changed(mutableInteractionSource) ? 67108864 : 33554432;
                    }
                    if ((i2 & 512) == 0) {
                        i3 |= 805306368;
                    } else if ((i & 805306368) == 0) {
                        i3 |= composerImpl2.changedInstance(function3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if ((306783379 & i3) == 306783378 || !composerImpl2.getSkipping()) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i8 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if (i4 != 0) {
                                z2 = z3;
                            }
                            if ((i2 & 8) != 0) {
                                ButtonDefaults.INSTANCE.getClass();
                                i3 &= -7169;
                                shape2 = ButtonDefaults.getShape(composerImpl2);
                            }
                            if ((i2 & 16) != 0) {
                                ButtonDefaults.INSTANCE.getClass();
                                i3 &= -57345;
                                buttonColors2 = ButtonDefaults.buttonColors(composerImpl2);
                            }
                            if ((i2 & 32) != 0) {
                                ButtonDefaults.INSTANCE.getClass();
                                FilledButtonTokens.INSTANCE.getClass();
                                float f2 = FilledButtonTokens.ContainerElevation;
                                float f3 = FilledButtonTokens.PressedContainerElevation;
                                float f4 = FilledButtonTokens.FocusedContainerElevation;
                                float f5 = FilledButtonTokens.HoveredContainerElevation;
                                float f6 = FilledButtonTokens.DisabledContainerElevation;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.buttonElevation (Button.kt:1455)");
                                }
                                ButtonElevation buttonElevation3 = new ButtonElevation(f2, f3, f4, f5, f6, null);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                i3 &= -458753;
                                buttonElevation2 = buttonElevation3;
                            }
                            if (i5 != 0) {
                                borderStroke2 = null;
                            }
                            if (i6 == 0) {
                                ButtonDefaults.INSTANCE.getClass();
                                paddingValues2 = ButtonDefaults.ContentPadding;
                            } else {
                                paddingValues2 = paddingValues;
                            }
                            if (i7 == 0) {
                                shape3 = shape2;
                                borderStroke3 = borderStroke2;
                                mutableInteractionSource2 = null;
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.Button (Button.kt:152)");
                            }
                            Composer.Companion companion = Composer.Companion;
                            if (mutableInteractionSource2 == null) {
                                composerImpl2.startReplaceGroup(1177946566);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                                    composerImpl2.updateRememberedValue(objRememberedValue);
                                }
                                mutableInteractionSource3 = (MutableInteractionSource) objRememberedValue;
                                composerImpl2.end(false);
                            } else {
                                composerImpl2.startReplaceGroup(-239097039);
                                composerImpl2.end(false);
                                mutableInteractionSource3 = mutableInteractionSource2;
                            }
                            if (z2) {
                                modifier3 = modifier2;
                                j = buttonColors2.containerColor;
                            } else {
                                modifier3 = modifier2;
                                j = buttonColors2.disabledContainerColor;
                            }
                            Modifier modifier5 = modifier3;
                            MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource2;
                            final long j3 = z2 ? buttonColors2.contentColor : buttonColors2.disabledContentColor;
                            if (buttonElevation2 == null) {
                                composerImpl2.startReplaceGroup(1178130209);
                                composerImpl2.end(false);
                                buttonColors3 = buttonColors2;
                                j2 = j;
                                animationState = null;
                            } else {
                                composerImpl2.startReplaceGroup(-239090464);
                                int i11 = ((i3 >> 6) & 14) | ((i3 >> 9) & 896);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ButtonElevation.shadowElevation (Button.kt:1583)");
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ButtonElevation.animateElevation (Button.kt:1591)");
                                }
                                buttonColors3 = buttonColors2;
                                Object objRememberedValue2 = composerImpl2.rememberedValue();
                                companion.getClass();
                                j2 = j;
                                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                                if (objRememberedValue2 == composer$Companion$Empty$1) {
                                    objRememberedValue2 = new SnapshotStateList();
                                    composerImpl2.updateRememberedValue(objRememberedValue2);
                                }
                                SnapshotStateList snapshotStateList = (SnapshotStateList) objRememberedValue2;
                                boolean zChanged = composerImpl2.changed(mutableInteractionSource3);
                                Object objRememberedValue3 = composerImpl2.rememberedValue();
                                if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                                    objRememberedValue3 = new ButtonElevation$animateElevation$1$1(mutableInteractionSource3, snapshotStateList, null);
                                    composerImpl2.updateRememberedValue(objRememberedValue3);
                                }
                                EffectsKt.LaunchedEffect(composerImpl2, mutableInteractionSource3, (Function2) objRememberedValue3);
                                Interaction interaction = (Interaction) CollectionsKt___CollectionsKt.lastOrNull(snapshotStateList);
                                float f7 = !z2 ? buttonElevation2.disabledElevation : interaction instanceof PressInteraction$Press ? buttonElevation2.pressedElevation : interaction instanceof HoverInteraction$Enter ? buttonElevation2.hoveredElevation : interaction instanceof FocusInteraction$Focus ? buttonElevation2.focusedElevation : buttonElevation2.defaultElevation;
                                Object objRememberedValue4 = composerImpl2.rememberedValue();
                                if (objRememberedValue4 == composer$Companion$Empty$1) {
                                    objRememberedValue4 = new Animatable(Dp.m837boximpl(f7), VectorConvertersKt.DpToVector, null, null, 12, null);
                                    composerImpl2.updateRememberedValue(objRememberedValue4);
                                }
                                Animatable animatable2 = (Animatable) objRememberedValue4;
                                Dp dpM837boximpl = Dp.m837boximpl(f7);
                                float f8 = f7;
                                boolean zChangedInstance = composerImpl2.changedInstance(animatable2) | composerImpl2.changed(f7) | (((((i11 & 14) ^ 6) <= 4 || !composerImpl2.changed(z2)) && (i11 & 6) != 4) ? false : z3) | (((((i11 & 896) ^ 384) <= 256 || !composerImpl2.changed(buttonElevation2)) && (i11 & 384) != 256) ? false : z3) | composerImpl2.changedInstance(interaction);
                                Object objRememberedValue5 = composerImpl2.rememberedValue();
                                if (zChangedInstance || objRememberedValue5 == composer$Companion$Empty$1) {
                                    objRememberedValue5 = new ButtonElevation$animateElevation$2$1(animatable2, f8, z2, buttonElevation2, interaction, null);
                                    animatable = animatable2;
                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                } else {
                                    animatable = animatable2;
                                }
                                EffectsKt.LaunchedEffect(composerImpl2, dpM837boximpl, (Function2) objRememberedValue5);
                                animationState = animatable.internalState;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl2.end(false);
                            }
                            if (animationState != null) {
                                f = ((Dp) ((SnapshotMutableStateImpl) animationState.value$delegate).getValue()).value;
                                z4 = false;
                            } else {
                                z4 = false;
                                Dp.Companion companion2 = Dp.Companion;
                                f = 0;
                            }
                            composerImpl = composerImpl2;
                            SurfaceKt.m305Surfaceo_FOJdg(function02, SemanticsModifierKt.semantics(modifier5, z4, new Function1() { // from class: androidx.compose.material3.ButtonKt.Button.1
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    Role.Companion.getClass();
                                    SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                                    return Unit.INSTANCE;
                                }
                            }), z2, shape3, j2, j3, f, borderStroke3, mutableInteractionSource3, ComposableLambdaKt.rememberComposableLambda(956488494, new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.Button.<anonymous> (Button.kt:169)");
                                            }
                                            long j4 = j3;
                                            MaterialTheme.INSTANCE.getClass();
                                            TextStyle textStyle = MaterialTheme.getTypography(composer2).labelLarge;
                                            final PaddingValues paddingValues4 = paddingValues2;
                                            final Function3 function32 = function3;
                                            ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j4, textStyle, ComposableLambdaKt.rememberComposableLambda(1327513942, new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.2.1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj3, Object obj4) {
                                                    Composer composer3 = (Composer) obj3;
                                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.Button.<anonymous>.<anonymous> (Button.kt:173)");
                                                            }
                                                            Modifier.Companion companion3 = Modifier.Companion;
                                                            ButtonDefaults.INSTANCE.getClass();
                                                            Modifier modifierPadding = PaddingKt.padding(SizeKt.m130defaultMinSizeVpY3zN4(companion3, ButtonDefaults.MinWidth, ButtonDefaults.MinHeight), paddingValues4);
                                                            Arrangement.INSTANCE.getClass();
                                                            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                                                            Alignment.Companion.getClass();
                                                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                                            Function3 function33 = function32;
                                                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, vertical, composer3, 54);
                                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierPadding);
                                                            ComposeUiNode.Companion.getClass();
                                                            Function0 function03 = ComposeUiNode.Companion.Constructor;
                                                            if (composerImpl5.applier == null) {
                                                                ComposablesKt.invalidApplier();
                                                                throw null;
                                                            }
                                                            composerImpl5.startReusableNode();
                                                            if (composerImpl5.inserting) {
                                                                composerImpl5.createNode(function03);
                                                            } else {
                                                                composerImpl5.useNode();
                                                            }
                                                            Updater.m337setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                            if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                                                            }
                                                            Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                            function33.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                                                            composerImpl5.end(true);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer2), composer2, 384);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl, (i3 & 8078) | ((i3 << 6) & 234881024), 64);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            paddingValues3 = paddingValues2;
                            modifier4 = modifier5;
                            shape4 = shape3;
                            borderStroke4 = borderStroke3;
                            mutableInteractionSource4 = mutableInteractionSource5;
                            buttonColors4 = buttonColors3;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i2 & 8) != 0) {
                                i3 &= -7169;
                            }
                            if ((i2 & 16) != 0) {
                                i3 &= -57345;
                            }
                            if ((i2 & 32) != 0) {
                                i3 &= -458753;
                            }
                            paddingValues2 = paddingValues;
                        }
                        mutableInteractionSource2 = mutableInteractionSource;
                        shape3 = shape2;
                        borderStroke3 = borderStroke2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        Composer.Companion companion3 = Composer.Companion;
                        if (mutableInteractionSource2 == null) {
                        }
                        if (z2) {
                        }
                        Modifier modifier52 = modifier3;
                        MutableInteractionSource mutableInteractionSource52 = mutableInteractionSource2;
                        if (z2) {
                        }
                        if (buttonElevation2 == null) {
                        }
                        if (animationState != null) {
                        }
                        composerImpl = composerImpl2;
                        SurfaceKt.m305Surfaceo_FOJdg(function02, SemanticsModifierKt.semantics(modifier52, z4, new Function1() { // from class: androidx.compose.material3.ButtonKt.Button.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Role.Companion.getClass();
                                SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                                return Unit.INSTANCE;
                            }
                        }), z2, shape3, j2, j3, f, borderStroke3, mutableInteractionSource3, ComposableLambdaKt.rememberComposableLambda(956488494, new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.Button.<anonymous> (Button.kt:169)");
                                        }
                                        long j4 = j3;
                                        MaterialTheme.INSTANCE.getClass();
                                        TextStyle textStyle = MaterialTheme.getTypography(composer2).labelLarge;
                                        final PaddingValues paddingValues4 = paddingValues2;
                                        final Function3 function32 = function3;
                                        ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j4, textStyle, ComposableLambdaKt.rememberComposableLambda(1327513942, new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.2.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                            @Override // kotlin.jvm.functions.Function2
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj3, Object obj4) {
                                                Composer composer3 = (Composer) obj3;
                                                if ((((Number) obj4).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                    if (composerImpl4.getSkipping()) {
                                                        composerImpl4.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.compose.material3.Button.<anonymous>.<anonymous> (Button.kt:173)");
                                                        }
                                                        Modifier.Companion companion32 = Modifier.Companion;
                                                        ButtonDefaults.INSTANCE.getClass();
                                                        Modifier modifierPadding = PaddingKt.padding(SizeKt.m130defaultMinSizeVpY3zN4(companion32, ButtonDefaults.MinWidth, ButtonDefaults.MinHeight), paddingValues4);
                                                        Arrangement.INSTANCE.getClass();
                                                        Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                                                        Alignment.Companion.getClass();
                                                        BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                                        Function3 function33 = function32;
                                                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, vertical, composer3, 54);
                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierPadding);
                                                        ComposeUiNode.Companion.getClass();
                                                        Function0 function03 = ComposeUiNode.Companion.Constructor;
                                                        if (composerImpl5.applier == null) {
                                                            ComposablesKt.invalidApplier();
                                                            throw null;
                                                        }
                                                        composerImpl5.startReusableNode();
                                                        if (composerImpl5.inserting) {
                                                            composerImpl5.createNode(function03);
                                                        } else {
                                                            composerImpl5.useNode();
                                                        }
                                                        Updater.m337setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                        Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                                                        }
                                                        Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                        function33.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                                                        composerImpl5.end(true);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composer2), composer2, 384);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl2), composerImpl, (i3 & 8078) | ((i3 << 6) & 234881024), 64);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        paddingValues3 = paddingValues2;
                        modifier4 = modifier52;
                        shape4 = shape3;
                        borderStroke4 = borderStroke3;
                        mutableInteractionSource4 = mutableInteractionSource52;
                        buttonColors4 = buttonColors3;
                    } else {
                        composerImpl2.skipToGroupEnd();
                        composerImpl = composerImpl2;
                        borderStroke4 = borderStroke2;
                        modifier4 = modifier2;
                        paddingValues3 = paddingValues;
                        mutableInteractionSource4 = mutableInteractionSource;
                        buttonColors4 = buttonColors2;
                        shape4 = shape2;
                    }
                    final boolean z5 = z2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.3
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                ButtonKt.Button(function0, modifier4, z5, shape4, buttonColors4, buttonElevation2, borderStroke4, paddingValues3, mutableInteractionSource4, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 100663296;
                if ((i2 & 512) == 0) {
                }
                if ((306783379 & i3) == 306783378) {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0) {
                        if (i8 != 0) {
                        }
                        if (i4 != 0) {
                        }
                        if ((i2 & 8) != 0) {
                        }
                        if ((i2 & 16) != 0) {
                        }
                        if ((i2 & 32) != 0) {
                        }
                        if (i5 != 0) {
                        }
                        if (i6 == 0) {
                        }
                        if (i7 == 0) {
                            mutableInteractionSource2 = mutableInteractionSource;
                            shape3 = shape2;
                            borderStroke3 = borderStroke2;
                        }
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        Composer.Companion companion32 = Composer.Companion;
                        if (mutableInteractionSource2 == null) {
                        }
                        if (z2) {
                        }
                        Modifier modifier522 = modifier3;
                        MutableInteractionSource mutableInteractionSource522 = mutableInteractionSource2;
                        if (z2) {
                        }
                        if (buttonElevation2 == null) {
                        }
                        if (animationState != null) {
                        }
                        composerImpl = composerImpl2;
                        SurfaceKt.m305Surfaceo_FOJdg(function02, SemanticsModifierKt.semantics(modifier522, z4, new Function1() { // from class: androidx.compose.material3.ButtonKt.Button.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                Role.Companion.getClass();
                                SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                                return Unit.INSTANCE;
                            }
                        }), z2, shape3, j2, j3, f, borderStroke3, mutableInteractionSource3, ComposableLambdaKt.rememberComposableLambda(956488494, new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.Button.<anonymous> (Button.kt:169)");
                                        }
                                        long j4 = j3;
                                        MaterialTheme.INSTANCE.getClass();
                                        TextStyle textStyle = MaterialTheme.getTypography(composer2).labelLarge;
                                        final PaddingValues paddingValues4 = paddingValues2;
                                        final Function3 function32 = function3;
                                        ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j4, textStyle, ComposableLambdaKt.rememberComposableLambda(1327513942, new Function2() { // from class: androidx.compose.material3.ButtonKt.Button.2.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                            @Override // kotlin.jvm.functions.Function2
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj3, Object obj4) {
                                                Composer composer3 = (Composer) obj3;
                                                if ((((Number) obj4).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                    if (composerImpl4.getSkipping()) {
                                                        composerImpl4.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.compose.material3.Button.<anonymous>.<anonymous> (Button.kt:173)");
                                                        }
                                                        Modifier.Companion companion322 = Modifier.Companion;
                                                        ButtonDefaults.INSTANCE.getClass();
                                                        Modifier modifierPadding = PaddingKt.padding(SizeKt.m130defaultMinSizeVpY3zN4(companion322, ButtonDefaults.MinWidth, ButtonDefaults.MinHeight), paddingValues4);
                                                        Arrangement.INSTANCE.getClass();
                                                        Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                                                        Alignment.Companion.getClass();
                                                        BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                                        Function3 function33 = function32;
                                                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, vertical, composer3, 54);
                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierPadding);
                                                        ComposeUiNode.Companion.getClass();
                                                        Function0 function03 = ComposeUiNode.Companion.Constructor;
                                                        if (composerImpl5.applier == null) {
                                                            ComposablesKt.invalidApplier();
                                                            throw null;
                                                        }
                                                        composerImpl5.startReusableNode();
                                                        if (composerImpl5.inserting) {
                                                            composerImpl5.createNode(function03);
                                                        } else {
                                                            composerImpl5.useNode();
                                                        }
                                                        Updater.m337setimpl(composer3, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                        Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function2);
                                                        }
                                                        Updater.m337setimpl(composer3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                        function33.invoke(RowScopeInstance.INSTANCE, composer3, 6);
                                                        composerImpl5.end(true);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composer2), composer2, 384);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl2), composerImpl, (i3 & 8078) | ((i3 << 6) & 234881024), 64);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        paddingValues3 = paddingValues2;
                        modifier4 = modifier522;
                        shape4 = shape3;
                        borderStroke4 = borderStroke3;
                        mutableInteractionSource4 = mutableInteractionSource522;
                        buttonColors4 = buttonColors3;
                    }
                }
                final boolean z52 = z2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            if ((i & 3072) == 0) {
            }
            if ((i & 24576) == 0) {
            }
            if ((i & 196608) == 0) {
            }
            i5 = i2 & 64;
            if (i5 != 0) {
            }
            i6 = 128 & i2;
            if (i6 != 0) {
            }
            i7 = i2 & 256;
            if (i7 == 0) {
            }
            if ((i2 & 512) == 0) {
            }
            if ((306783379 & i3) == 306783378) {
            }
            final boolean z522 = z2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        z2 = z;
        if ((i & 3072) == 0) {
        }
        if ((i & 24576) == 0) {
        }
        if ((i & 196608) == 0) {
        }
        i5 = i2 & 64;
        if (i5 != 0) {
        }
        i6 = 128 & i2;
        if (i6 != 0) {
        }
        i7 = i2 & 256;
        if (i7 == 0) {
        }
        if ((i2 & 512) == 0) {
        }
        if ((306783379 & i3) == 306783378) {
        }
        final boolean z5222 = z2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x011b A[PHI: r18
      0x011b: PHI (r18v8 int) = (r18v0 int), (r18v2 int), (r18v3 int) binds: [B:102:0x0119, B:110:0x012f, B:109:0x012c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:193:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void OutlinedButton(final Function0 function0, Modifier modifier, boolean z, Shape shape, ButtonColors buttonColors, ButtonElevation buttonElevation, BorderStroke borderStroke, PaddingValues paddingValues, MutableInteractionSource mutableInteractionSource, final Function3 function3, Composer composer, final int i, final int i2) {
        Function0 function02;
        int i3;
        int i4;
        boolean z2;
        Shape shape2;
        ButtonColors buttonColors2;
        int i5;
        ButtonElevation buttonElevation2;
        BorderStroke borderStroke2;
        int i6;
        PaddingValues paddingValues2;
        int i7;
        int i8;
        int i9;
        int i10;
        Shape value;
        ButtonColors defaultOutlinedButtonColors$material3_release;
        BorderStroke borderStrokeM31BorderStrokecXLIe8U;
        PaddingValues paddingValues3;
        MutableInteractionSource mutableInteractionSource2;
        Modifier modifier2;
        BorderStroke borderStroke3;
        boolean z3;
        Shape shape3;
        ButtonColors buttonColors3;
        PaddingValues paddingValues4;
        ButtonElevation buttonElevation3;
        int i11;
        long jColor;
        float f;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        final boolean z4;
        final Shape shape4;
        final ButtonColors buttonColors4;
        final ButtonElevation buttonElevation4;
        final BorderStroke borderStroke4;
        final PaddingValues paddingValues5;
        final MutableInteractionSource mutableInteractionSource3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i12;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1694808287);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            function02 = function0;
        } else {
            function02 = function0;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(function02) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        int i13 = i2 & 2;
        if (i13 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                i3 |= composerImpl2.changed(modifier) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    z2 = z;
                    i3 |= composerImpl2.changed(z2) ? 256 : 128;
                }
                if ((i & 3072) == 0) {
                    if ((i2 & 8) == 0) {
                        shape2 = shape;
                        int i14 = composerImpl2.changed(shape2) ? 2048 : 1024;
                        i3 |= i14;
                    } else {
                        shape2 = shape;
                    }
                    i3 |= i14;
                } else {
                    shape2 = shape;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        buttonColors2 = buttonColors;
                        int i15 = composerImpl2.changed(buttonColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i15;
                    } else {
                        buttonColors2 = buttonColors;
                    }
                    i3 |= i15;
                } else {
                    buttonColors2 = buttonColors;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                } else {
                    if ((196608 & i) == 0) {
                        buttonElevation2 = buttonElevation;
                        i3 |= composerImpl2.changed(buttonElevation2) ? 131072 : 65536;
                    }
                    if ((1572864 & i) != 0) {
                        if ((i2 & 64) == 0) {
                            borderStroke2 = borderStroke;
                            if (composerImpl2.changed(borderStroke2)) {
                                i12 = 1048576;
                            }
                            i3 |= i12;
                        } else {
                            borderStroke2 = borderStroke;
                        }
                        i12 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        i3 |= i12;
                    } else {
                        borderStroke2 = borderStroke;
                    }
                    i6 = i2 & 128;
                    if (i6 != 0) {
                        if ((12582912 & i) == 0) {
                            paddingValues2 = paddingValues;
                            i3 |= composerImpl2.changed(paddingValues2) ? 8388608 : 4194304;
                        }
                        int i16 = i3;
                        i7 = i2 & 256;
                        if (i7 != 0) {
                            i9 = i16 | 100663296;
                            i8 = i7;
                        } else if ((i & 100663296) == 0) {
                            i8 = i7;
                            i9 = i16 | (composerImpl2.changed(mutableInteractionSource) ? 67108864 : 33554432);
                        } else {
                            i8 = i7;
                            i9 = i16;
                        }
                        int i17 = 805306368;
                        if ((i2 & 512) != 0) {
                            i9 |= i17;
                        } else if ((i & 805306368) == 0) {
                            i17 = composerImpl2.changedInstance(function3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            i9 |= i17;
                        }
                        i10 = i9;
                        if ((i10 & 306783379) == 306783378 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            modifier3 = modifier;
                            composerImpl = composerImpl2;
                            z4 = z2;
                            shape4 = shape2;
                            buttonColors4 = buttonColors2;
                            buttonElevation4 = buttonElevation2;
                            borderStroke4 = borderStroke2;
                            paddingValues5 = paddingValues2;
                            mutableInteractionSource3 = mutableInteractionSource;
                        } else {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                Modifier modifier4 = i13 == 0 ? Modifier.Companion : modifier;
                                boolean z5 = i4 == 0 ? true : z2;
                                if ((i2 & 8) == 0) {
                                    ButtonDefaults.INSTANCE.getClass();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.<get-outlinedShape> (Button.kt:1188)");
                                    }
                                    ButtonSmallTokens.INSTANCE.getClass();
                                    value = ShapesKt.getValue(ButtonSmallTokens.ContainerShapeRound, composerImpl2);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    i10 &= -7169;
                                } else {
                                    value = shape2;
                                }
                                if ((i2 & 16) == 0) {
                                    ButtonDefaults.INSTANCE.getClass();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.outlinedButtonColors (Button.kt:1353)");
                                    }
                                    MaterialTheme.INSTANCE.getClass();
                                    defaultOutlinedButtonColors$material3_release = ButtonDefaults.getDefaultOutlinedButtonColors$material3_release(MaterialTheme.getColorScheme(composerImpl2));
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    i10 &= -57345;
                                } else {
                                    defaultOutlinedButtonColors$material3_release = buttonColors2;
                                }
                                if (i5 != 0) {
                                    buttonElevation2 = null;
                                }
                                if ((i2 & 64) == 0) {
                                    ButtonDefaults.INSTANCE.getClass();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.outlinedButtonBorder (Button.kt:1542)");
                                    }
                                    ButtonSmallTokens.INSTANCE.getClass();
                                    float f2 = ButtonSmallTokens.OutlinedOutlineWidth;
                                    if (z5) {
                                        i11 = -3670017;
                                        composerImpl2.startReplaceGroup(-824185076);
                                        OutlinedButtonTokens.INSTANCE.getClass();
                                        jColor = ColorSchemeKt.getValue(OutlinedButtonTokens.OutlineColor, composerImpl2);
                                        composerImpl2.end(false);
                                        f = f2;
                                    } else {
                                        i11 = -3670017;
                                        composerImpl2.startReplaceGroup(-824097470);
                                        OutlinedButtonTokens.INSTANCE.getClass();
                                        long value2 = ColorSchemeKt.getValue(OutlinedButtonTokens.OutlineColor, composerImpl2);
                                        jColor = ColorKt.Color(Color.m463getRedimpl(value2), Color.m462getGreenimpl(value2), Color.m460getBlueimpl(value2), OutlinedButtonTokens.DisabledContainerOpacity, Color.m461getColorSpaceimpl(value2));
                                        composerImpl2.end(false);
                                        f = f2;
                                    }
                                    borderStrokeM31BorderStrokecXLIe8U = BorderStrokeKt.m31BorderStrokecXLIe8U(f, jColor);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    i10 &= i11;
                                } else {
                                    borderStrokeM31BorderStrokecXLIe8U = borderStroke2;
                                }
                                if (i6 == 0) {
                                    ButtonDefaults.INSTANCE.getClass();
                                    paddingValues3 = ButtonDefaults.ContentPadding;
                                } else {
                                    paddingValues3 = paddingValues2;
                                }
                                if (i8 == 0) {
                                    modifier2 = modifier4;
                                    borderStroke3 = borderStrokeM31BorderStrokecXLIe8U;
                                    z3 = z5;
                                    shape3 = value;
                                    buttonColors3 = defaultOutlinedButtonColors$material3_release;
                                    paddingValues4 = paddingValues3;
                                    buttonElevation3 = buttonElevation2;
                                    mutableInteractionSource2 = null;
                                } else {
                                    mutableInteractionSource2 = mutableInteractionSource;
                                    modifier2 = modifier4;
                                    borderStroke3 = borderStrokeM31BorderStrokecXLIe8U;
                                    z3 = z5;
                                    shape3 = value;
                                    buttonColors3 = defaultOutlinedButtonColors$material3_release;
                                    paddingValues4 = paddingValues3;
                                    buttonElevation3 = buttonElevation2;
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i2 & 8) != 0) {
                                    i10 &= -7169;
                                }
                                if ((i2 & 16) != 0) {
                                    i10 &= -57345;
                                }
                                if ((i2 & 64) != 0) {
                                    i10 &= -3670017;
                                }
                                modifier2 = modifier;
                                mutableInteractionSource2 = mutableInteractionSource;
                                shape3 = shape2;
                                buttonColors3 = buttonColors2;
                                buttonElevation3 = buttonElevation2;
                                borderStroke3 = borderStroke2;
                                paddingValues4 = paddingValues2;
                                z3 = z2;
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.OutlinedButton (Button.kt:682)");
                            }
                            composerImpl = composerImpl2;
                            Button(function02, modifier2, z3, shape3, buttonColors3, buttonElevation3, borderStroke3, paddingValues4, mutableInteractionSource2, function3, composerImpl, i10 & 2147483646, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier3 = modifier2;
                            z4 = z3;
                            shape4 = shape3;
                            buttonColors4 = buttonColors3;
                            buttonElevation4 = buttonElevation3;
                            borderStroke4 = borderStroke3;
                            paddingValues5 = paddingValues4;
                            mutableInteractionSource3 = mutableInteractionSource2;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ButtonKt.OutlinedButton.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    ButtonKt.OutlinedButton(function0, modifier3, z4, shape4, buttonColors4, buttonElevation4, borderStroke4, paddingValues5, mutableInteractionSource3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 12582912;
                    paddingValues2 = paddingValues;
                    int i162 = i3;
                    i7 = i2 & 256;
                    if (i7 != 0) {
                    }
                    int i172 = 805306368;
                    if ((i2 & 512) != 0) {
                    }
                    i10 = i9;
                    if ((i10 & 306783379) == 306783378) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0) {
                            if (i13 == 0) {
                            }
                            if (i4 == 0) {
                            }
                            if ((i2 & 8) == 0) {
                            }
                            if ((i2 & 16) == 0) {
                            }
                            if (i5 != 0) {
                            }
                            if ((i2 & 64) == 0) {
                            }
                            if (i6 == 0) {
                            }
                            if (i8 == 0) {
                            }
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            composerImpl = composerImpl2;
                            Button(function02, modifier2, z3, shape3, buttonColors3, buttonElevation3, borderStroke3, paddingValues4, mutableInteractionSource2, function3, composerImpl, i10 & 2147483646, 0);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            modifier3 = modifier2;
                            z4 = z3;
                            shape4 = shape3;
                            buttonColors4 = buttonColors3;
                            buttonElevation4 = buttonElevation3;
                            borderStroke4 = borderStroke3;
                            paddingValues5 = paddingValues4;
                            mutableInteractionSource3 = mutableInteractionSource2;
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                buttonElevation2 = buttonElevation;
                if ((1572864 & i) != 0) {
                }
                i6 = i2 & 128;
                if (i6 != 0) {
                }
                paddingValues2 = paddingValues;
                int i1622 = i3;
                i7 = i2 & 256;
                if (i7 != 0) {
                }
                int i1722 = 805306368;
                if ((i2 & 512) != 0) {
                }
                i10 = i9;
                if ((i10 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            z2 = z;
            if ((i & 3072) == 0) {
            }
            if ((i & 24576) == 0) {
            }
            i5 = i2 & 32;
            if (i5 != 0) {
            }
            buttonElevation2 = buttonElevation;
            if ((1572864 & i) != 0) {
            }
            i6 = i2 & 128;
            if (i6 != 0) {
            }
            paddingValues2 = paddingValues;
            int i16222 = i3;
            i7 = i2 & 256;
            if (i7 != 0) {
            }
            int i17222 = 805306368;
            if ((i2 & 512) != 0) {
            }
            i10 = i9;
            if ((i10 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        z2 = z;
        if ((i & 3072) == 0) {
        }
        if ((i & 24576) == 0) {
        }
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        buttonElevation2 = buttonElevation;
        if ((1572864 & i) != 0) {
        }
        i6 = i2 & 128;
        if (i6 != 0) {
        }
        paddingValues2 = paddingValues;
        int i162222 = i3;
        i7 = i2 & 256;
        if (i7 != 0) {
        }
        int i172222 = 805306368;
        if ((i2 & 512) != 0) {
        }
        i10 = i9;
        if ((i10 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0118 A[PHI: r18
      0x0118: PHI (r18v11 int) = (r18v4 int), (r18v6 int), (r18v7 int) binds: [B:102:0x0116, B:110:0x012c, B:109:0x0129] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:179:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TextButton(final Function0 function0, Modifier modifier, boolean z, Shape shape, ButtonColors buttonColors, ButtonElevation buttonElevation, BorderStroke borderStroke, PaddingValues paddingValues, MutableInteractionSource mutableInteractionSource, final Function3 function3, Composer composer, final int i, final int i2) {
        Function0 function02;
        int i3;
        int i4;
        boolean z2;
        Shape shape2;
        ButtonColors buttonColors2;
        int i5;
        ButtonElevation buttonElevation2;
        int i6;
        BorderStroke borderStroke2;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        Shape value;
        ButtonColors defaultTextButtonColors$material3_release;
        PaddingValues paddingValues2;
        MutableInteractionSource mutableInteractionSource2;
        boolean z3;
        Shape shape3;
        ButtonColors buttonColors3;
        PaddingValues paddingValues3;
        ButtonElevation buttonElevation3;
        BorderStroke borderStroke3;
        Modifier modifier2;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        final boolean z4;
        final Shape shape4;
        final ButtonColors buttonColors4;
        final ButtonElevation buttonElevation4;
        final BorderStroke borderStroke4;
        final PaddingValues paddingValues4;
        final MutableInteractionSource mutableInteractionSource3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2106428362);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            function02 = function0;
        } else {
            function02 = function0;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(function02) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        int i12 = i2 & 2;
        if (i12 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                i3 |= composerImpl2.changed(modifier) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    z2 = z;
                    i3 |= composerImpl2.changed(z2) ? 256 : 128;
                }
                if ((i & 3072) == 0) {
                    if ((i2 & 8) == 0) {
                        shape2 = shape;
                        int i13 = composerImpl2.changed(shape2) ? 2048 : 1024;
                        i3 |= i13;
                    } else {
                        shape2 = shape;
                    }
                    i3 |= i13;
                } else {
                    shape2 = shape;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        buttonColors2 = buttonColors;
                        int i14 = composerImpl2.changed(buttonColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i14;
                    } else {
                        buttonColors2 = buttonColors;
                    }
                    i3 |= i14;
                } else {
                    buttonColors2 = buttonColors;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                } else {
                    if ((196608 & i) == 0) {
                        buttonElevation2 = buttonElevation;
                        i3 |= composerImpl2.changed(buttonElevation2) ? 131072 : 65536;
                    }
                    i6 = i2 & 64;
                    if (i6 == 0) {
                        i3 |= 1572864;
                    } else {
                        if ((1572864 & i) == 0) {
                            borderStroke2 = borderStroke;
                            i3 |= composerImpl2.changed(borderStroke2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        i7 = i2 & 128;
                        if (i7 != 0) {
                            i8 = i3 | 12582912;
                        } else {
                            int i15 = i3;
                            if ((i & 12582912) == 0) {
                                i8 = i15 | (composerImpl2.changed(paddingValues) ? 8388608 : 4194304);
                            } else {
                                i8 = i15;
                            }
                        }
                        i9 = i2 & 256;
                        if (i9 == 0) {
                            if ((i & 100663296) == 0) {
                                i10 = i9;
                                i8 |= composerImpl2.changed(mutableInteractionSource) ? 67108864 : 33554432;
                            }
                            int i16 = 805306368;
                            if ((i2 & 512) != 0) {
                                i8 |= i16;
                            } else if ((i & 805306368) == 0) {
                                i16 = composerImpl2.changedInstance(function3) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                                i8 |= i16;
                            }
                            i11 = i8;
                            if ((i11 & 306783379) == 306783378 || !composerImpl2.getSkipping()) {
                                composerImpl2.startDefaults();
                                if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                    Modifier modifier4 = i12 == 0 ? Modifier.Companion : modifier;
                                    boolean z5 = i4 == 0 ? true : z2;
                                    if ((i2 & 8) == 0) {
                                        ButtonDefaults.INSTANCE.getClass();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.<get-textShape> (Button.kt:1192)");
                                        }
                                        ButtonSmallTokens.INSTANCE.getClass();
                                        value = ShapesKt.getValue(ButtonSmallTokens.ContainerShapeRound, composerImpl2);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        i11 &= -7169;
                                    } else {
                                        value = shape2;
                                    }
                                    if ((i2 & 16) == 0) {
                                        ButtonDefaults.INSTANCE.getClass();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.textButtonColors (Button.kt:1396)");
                                        }
                                        MaterialTheme.INSTANCE.getClass();
                                        defaultTextButtonColors$material3_release = ButtonDefaults.getDefaultTextButtonColors$material3_release(MaterialTheme.getColorScheme(composerImpl2));
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        i11 &= -57345;
                                    } else {
                                        defaultTextButtonColors$material3_release = buttonColors2;
                                    }
                                    if (i5 != 0) {
                                        buttonElevation2 = null;
                                    }
                                    if (i6 != 0) {
                                        borderStroke2 = null;
                                    }
                                    if (i7 == 0) {
                                        ButtonDefaults.INSTANCE.getClass();
                                        paddingValues2 = ButtonDefaults.TextButtonContentPadding;
                                    } else {
                                        paddingValues2 = paddingValues;
                                    }
                                    mutableInteractionSource2 = i10 == 0 ? null : mutableInteractionSource;
                                    z3 = z5;
                                    shape3 = value;
                                    buttonColors3 = defaultTextButtonColors$material3_release;
                                    paddingValues3 = paddingValues2;
                                    buttonElevation3 = buttonElevation2;
                                    borderStroke3 = borderStroke2;
                                    modifier2 = modifier4;
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                    if ((i2 & 8) != 0) {
                                        i11 &= -7169;
                                    }
                                    if ((i2 & 16) != 0) {
                                        i11 &= -57345;
                                    }
                                    paddingValues3 = paddingValues;
                                    mutableInteractionSource2 = mutableInteractionSource;
                                    z3 = z2;
                                    shape3 = shape2;
                                    buttonColors3 = buttonColors2;
                                    buttonElevation3 = buttonElevation2;
                                    borderStroke3 = borderStroke2;
                                    modifier2 = modifier;
                                }
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.TextButton (Button.kt:834)");
                                }
                                composerImpl = composerImpl2;
                                Button(function02, modifier2, z3, shape3, buttonColors3, buttonElevation3, borderStroke3, paddingValues3, mutableInteractionSource2, function3, composerImpl, i11 & 2147483646, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                modifier3 = modifier2;
                                z4 = z3;
                                shape4 = shape3;
                                buttonColors4 = buttonColors3;
                                buttonElevation4 = buttonElevation3;
                                borderStroke4 = borderStroke3;
                                paddingValues4 = paddingValues3;
                                mutableInteractionSource3 = mutableInteractionSource2;
                            } else {
                                composerImpl2.skipToGroupEnd();
                                modifier3 = modifier;
                                paddingValues4 = paddingValues;
                                composerImpl = composerImpl2;
                                z4 = z2;
                                shape4 = shape2;
                                buttonColors4 = buttonColors2;
                                buttonElevation4 = buttonElevation2;
                                borderStroke4 = borderStroke2;
                                mutableInteractionSource3 = mutableInteractionSource;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ButtonKt.TextButton.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        ButtonKt.TextButton(function0, modifier3, z4, shape4, buttonColors4, buttonElevation4, borderStroke4, paddingValues4, mutableInteractionSource3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i8 |= 100663296;
                        i10 = i9;
                        int i162 = 805306368;
                        if ((i2 & 512) != 0) {
                        }
                        i11 = i8;
                        if ((i11 & 306783379) == 306783378) {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0) {
                                if (i12 == 0) {
                                }
                                if (i4 == 0) {
                                }
                                if ((i2 & 8) == 0) {
                                }
                                if ((i2 & 16) == 0) {
                                }
                                if (i5 != 0) {
                                }
                                if (i6 != 0) {
                                }
                                if (i7 == 0) {
                                }
                                if (i10 == 0) {
                                }
                                z3 = z5;
                                shape3 = value;
                                buttonColors3 = defaultTextButtonColors$material3_release;
                                paddingValues3 = paddingValues2;
                                buttonElevation3 = buttonElevation2;
                                borderStroke3 = borderStroke2;
                                modifier2 = modifier4;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl = composerImpl2;
                                Button(function02, modifier2, z3, shape3, buttonColors3, buttonElevation3, borderStroke3, paddingValues3, mutableInteractionSource2, function3, composerImpl, i11 & 2147483646, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                modifier3 = modifier2;
                                z4 = z3;
                                shape4 = shape3;
                                buttonColors4 = buttonColors3;
                                buttonElevation4 = buttonElevation3;
                                borderStroke4 = borderStroke3;
                                paddingValues4 = paddingValues3;
                                mutableInteractionSource3 = mutableInteractionSource2;
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    borderStroke2 = borderStroke;
                    i7 = i2 & 128;
                    if (i7 != 0) {
                    }
                    i9 = i2 & 256;
                    if (i9 == 0) {
                    }
                    i10 = i9;
                    int i1622 = 805306368;
                    if ((i2 & 512) != 0) {
                    }
                    i11 = i8;
                    if ((i11 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                buttonElevation2 = buttonElevation;
                i6 = i2 & 64;
                if (i6 == 0) {
                }
                borderStroke2 = borderStroke;
                i7 = i2 & 128;
                if (i7 != 0) {
                }
                i9 = i2 & 256;
                if (i9 == 0) {
                }
                i10 = i9;
                int i16222 = 805306368;
                if ((i2 & 512) != 0) {
                }
                i11 = i8;
                if ((i11 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            if ((i & 3072) == 0) {
            }
            if ((i & 24576) == 0) {
            }
            i5 = i2 & 32;
            if (i5 != 0) {
            }
            buttonElevation2 = buttonElevation;
            i6 = i2 & 64;
            if (i6 == 0) {
            }
            borderStroke2 = borderStroke;
            i7 = i2 & 128;
            if (i7 != 0) {
            }
            i9 = i2 & 256;
            if (i9 == 0) {
            }
            i10 = i9;
            int i162222 = 805306368;
            if ((i2 & 512) != 0) {
            }
            i11 = i8;
            if ((i11 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        z2 = z;
        if ((i & 3072) == 0) {
        }
        if ((i & 24576) == 0) {
        }
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        buttonElevation2 = buttonElevation;
        i6 = i2 & 64;
        if (i6 == 0) {
        }
        borderStroke2 = borderStroke;
        i7 = i2 & 128;
        if (i7 != 0) {
        }
        i9 = i2 & 256;
        if (i9 == 0) {
        }
        i10 = i9;
        int i1622222 = 805306368;
        if ((i2 & 512) != 0) {
        }
        i11 = i8;
        if ((i11 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
