package com.samsung.sesl.compose.component;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import androidx.compose.animation.CrossfadeKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.selection.SelectableKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.Role;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import com.samsung.sesl.compose.component.tokens.SeslRadioButtonDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.BasicRadioButtonKt;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.foundation.SeslBasicRadioButtonColors;
import com.samsung.sesl.compose.foundation.SeslRecoilNodeFactory;
import com.samsung.sesl.compose.foundation.SeslRecoilPreset;
import com.samsung.sesl.compose.foundation.shape.RoundedCornerShapeKt;
import com.samsung.sesl.compose.foundation.theme.BasicDrawableSchemeKt;
import com.samsung.sesl.compose.phone.resources.DrawableResourcesKt;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes4.dex */
public abstract class RadioButtonKt {
    public static final void SeslOpenThemeRadioButton(final boolean z, final SeslOpenThemeRadioButtonResourceSet seslOpenThemeRadioButtonResourceSet, final Function0 function0, final SeslRecoilNodeFactory seslRecoilNodeFactory, final Modifier modifier, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        Modifier modifierM182selectableO2vRcR0;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1735005576);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(seslOpenThemeRadioButtonResourceSet) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(seslRecoilNodeFactory) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(true) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(mutableInteractionSource) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        int i3 = i2;
        if ((599187 & i3) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeRadioButton (RadioButton.kt:135)");
            }
            if (function0 != null) {
                Role.Companion.getClass();
                modifierM182selectableO2vRcR0 = SelectableKt.m182selectableO2vRcR0(modifier, z, mutableInteractionSource, seslRecoilNodeFactory, true, Role.m715boximpl(Role.RadioButton), function0);
            } else {
                modifierM182selectableO2vRcR0 = modifier;
            }
            composerImpl2.startReplaceGroup(472658087);
            Boolean boolValueOf = Boolean.valueOf(z);
            SeslRadioButtonDefaults.INSTANCE.getClass();
            composerImpl = composerImpl2;
            CrossfadeKt.Crossfade(boolValueOf, SizeKt.m140size3ABfNKs(modifierM182selectableO2vRcR0, SeslRadioButtonDefaults.radioButtonSize), (FiniteAnimationSpec) null, (String) null, ComposableLambdaKt.rememberComposableLambda(775934306, new Function3() { // from class: com.samsung.sesl.compose.component.RadioButtonKt.SeslOpenThemeRadioButton.2
                /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    boolean zBooleanValue = ((Boolean) obj).booleanValue();
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(zBooleanValue) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeRadioButton.<anonymous> (RadioButton.kt:155)");
                            }
                            SeslOpenThemeRadioButtonResourceSet seslOpenThemeRadioButtonResourceSet2 = seslOpenThemeRadioButtonResourceSet;
                            if (zBooleanValue) {
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(-416801770);
                                ImageKt.Image(seslOpenThemeRadioButtonResourceSet2.radioButtonSelected, null, null, null, null, 0.0f, null, composerImpl4, 48, 124);
                                composerImpl4.end(false);
                            } else {
                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                composerImpl5.startReplaceGroup(-416632076);
                                ImageKt.Image(seslOpenThemeRadioButtonResourceSet2.radioButtonUnselected, null, null, null, null, 0.0f, null, composerImpl5, 48, 124);
                                composerImpl5.end(false);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2), composerImpl, (i3 & 14) | 24576, 12);
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.RadioButtonKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslOpenThemeRadioButtonResourceSet seslOpenThemeRadioButtonResourceSet2 = seslOpenThemeRadioButtonResourceSet;
                    SeslRecoilNodeFactory seslRecoilNodeFactory2 = seslRecoilNodeFactory;
                    Modifier modifier2 = modifier;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    RadioButtonKt.SeslOpenThemeRadioButton(z, seslOpenThemeRadioButtonResourceSet2, function0, seslRecoilNodeFactory2, modifier2, mutableInteractionSource2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslRadioButton(final boolean z, final Function0 function0, Modifier.Companion companion, boolean z2, MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        boolean z3;
        int i2;
        SeslOpenThemeRadioButtonResourceSet seslOpenThemeRadioButtonResourceSet;
        final Modifier.Companion companion2;
        final MutableInteractionSource mutableInteractionSource2;
        final boolean z4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(407538050);
        if ((i & 6) == 0) {
            z3 = z;
            i2 = i | (composerImpl.changed(z3) ? 4 : 2);
        } else {
            z3 = z;
            i2 = i;
        }
        int i3 = i2 | (composerImpl.changedInstance(function0) ? 32 : 16) | 28032;
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
            z4 = z2;
            mutableInteractionSource2 = mutableInteractionSource;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            composerImpl.startReplaceGroup(1249217370);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableInteractionSource mutableInteractionSource3 = (MutableInteractionSource) objRememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslRadioButton (RadioButton.kt:61)");
            }
            SeslRecoilNodeFactory seslRecoilNodeFactoryM3352seslRecoilIndicationbw27NRU$default = RecoilKt.m3352seslRecoilIndicationbw27NRU$default(SeslRecoilPreset.Button, RoundedCornerShapeKt.SeslCircleShape, 2);
            composerImpl.startReplaceGroup(1249224730);
            if (((Boolean) composerImpl.consume(ThemeKt.LocalOneUiOpenTheme)).booleanValue()) {
                Drawable drawable = BasicDrawableSchemeKt.toDrawable(SeslRadioButtonDrawableSchemeKeyTokens.Selected, composerImpl);
                Drawable drawable2 = BasicDrawableSchemeKt.toDrawable(SeslRadioButtonDrawableSchemeKeyTokens.Unselected, composerImpl);
                Drawable drawable3 = BasicDrawableSchemeKt.toDrawable(SeslRadioButtonDrawableSchemeKeyTokens.DisabledOn, composerImpl);
                Drawable drawable4 = BasicDrawableSchemeKt.toDrawable(SeslRadioButtonDrawableSchemeKeyTokens.DisabledOff, composerImpl);
                Drawable[] drawableArr = {drawable, drawable2, drawable3, drawable4};
                SeslDrawableTokens.Companion.getClass();
                int i4 = 0;
                for (int i5 = 4; i4 < i5; i5 = 4) {
                    Drawable drawable5 = drawableArr[i4];
                    SeslDrawableTokens.Companion.getClass();
                    if ((drawable5 instanceof BitmapDrawable) || (drawable5 instanceof NinePatchDrawable)) {
                        seslOpenThemeRadioButtonResourceSet = new SeslOpenThemeRadioButtonResourceSet(DrawableResourcesKt.seslToPainter(drawable), DrawableResourcesKt.seslToPainter(drawable2), DrawableResourcesKt.seslToPainter(drawable3), DrawableResourcesKt.seslToPainter(drawable4));
                        break;
                    }
                    i4++;
                }
            }
            seslOpenThemeRadioButtonResourceSet = null;
            composerImpl.end(false);
            if (seslOpenThemeRadioButtonResourceSet != null) {
                composerImpl.startReplaceGroup(72270421);
                SeslOpenThemeRadioButton(z3, seslOpenThemeRadioButtonResourceSet, function0, seslRecoilNodeFactoryM3352seslRecoilIndicationbw27NRU$default, companion3, mutableInteractionSource3, composerImpl, (i3 & 14) | ((i3 << 3) & 896) | 1794048);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(72593751);
                SeslTheme.INSTANCE.getClass();
                SeslBasicRadioButtonColors seslBasicRadioButtonColors = new SeslBasicRadioButtonColors(SeslTheme.getColorScheme(composerImpl).primary, SeslTheme.getColorScheme(composerImpl).controlNormal, null);
                SeslRadioButtonDefaults.INSTANCE.getClass();
                int i6 = i3 << 3;
                BasicRadioButtonKt.m3349SeslBasicRadioButtonaA_HZ9I(seslBasicRadioButtonColors, z, function0, SeslRadioButtonDefaults.radioButtonSize, seslRecoilNodeFactoryM3352seslRecoilIndicationbw27NRU$default, companion3, mutableInteractionSource3, composerImpl, (i6 & 896) | (i6 & 112) | 3072 | 14352384);
                companion3 = companion3;
                mutableInteractionSource3 = mutableInteractionSource3;
                composerImpl = composerImpl;
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
            mutableInteractionSource2 = mutableInteractionSource3;
            z4 = true;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.RadioButtonKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    boolean z5 = z4;
                    MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource2;
                    RadioButtonKt.SeslRadioButton(z, function0, companion2, z5, mutableInteractionSource4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
