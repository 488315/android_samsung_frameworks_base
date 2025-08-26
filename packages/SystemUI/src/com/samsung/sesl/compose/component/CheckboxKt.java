package com.samsung.sesl.compose.component;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import androidx.compose.animation.CrossfadeKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.selection.ToggleableKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.state.ToggleableState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.SeslCheckboxDrawableSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import com.samsung.sesl.compose.foundation.BasicCheckboxKt;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.foundation.SeslBasicCheckboxColors;
import com.samsung.sesl.compose.foundation.SeslRecoilNodeFactory;
import com.samsung.sesl.compose.foundation.SeslRecoilPreset;
import com.samsung.sesl.compose.foundation.shape.RoundedCornerShapeKt;
import com.samsung.sesl.compose.foundation.theme.BasicDrawableSchemeKt;
import com.samsung.sesl.compose.phone.resources.DrawableResourcesKt;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes4.dex */
public abstract class CheckboxKt {
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x016e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SeslCheckbox(final boolean z, final Function1 function1, final Modifier modifier, final boolean z2, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        boolean z3;
        SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-894231181);
        int i2 = 4;
        int i3 = i | (composerImpl.changed(z) ? 4 : 2) | (composerImpl.changedInstance(function1) ? 32 : 16) | (composerImpl.changed(modifier) ? 256 : 128) | (composerImpl.changed(z2) ? 2048 : 1024) | (composerImpl.changed(mutableInteractionSource) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
        if ((i3 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslCheckbox (Checkbox.kt:61)");
            }
            SeslRecoilNodeFactory seslRecoilNodeFactoryM3352seslRecoilIndicationbw27NRU$default = RecoilKt.m3352seslRecoilIndicationbw27NRU$default(SeslRecoilPreset.Button, RoundedCornerShapeKt.SeslCircleShape, 2);
            composerImpl.startReplaceGroup(1338403464);
            boolean z4 = false;
            if (((Boolean) composerImpl.consume(ThemeKt.LocalOneUiOpenTheme)).booleanValue()) {
                Drawable drawable = BasicDrawableSchemeKt.toDrawable(SeslCheckboxDrawableSchemeKeyTokens.Selected, composerImpl);
                Drawable drawable2 = BasicDrawableSchemeKt.toDrawable(SeslCheckboxDrawableSchemeKeyTokens.Unselected, composerImpl);
                Drawable drawable3 = BasicDrawableSchemeKt.toDrawable(SeslCheckboxDrawableSchemeKeyTokens.DisabledOn, composerImpl);
                Drawable drawable4 = BasicDrawableSchemeKt.toDrawable(SeslCheckboxDrawableSchemeKeyTokens.DisabledOff, composerImpl);
                Drawable[] drawableArr = {drawable, drawable2, drawable3, drawable4};
                SeslDrawableTokens.Companion.getClass();
                int i4 = 0;
                while (i4 < i2) {
                    Drawable drawable5 = drawableArr[i4];
                    SeslDrawableTokens.Companion.getClass();
                    if ((drawable5 instanceof BitmapDrawable) || (drawable5 instanceof NinePatchDrawable)) {
                        seslOpenCheckboxResourceSet = new SeslOpenCheckboxResourceSet(DrawableResourcesKt.seslToPainter(drawable), DrawableResourcesKt.seslToPainter(drawable2), DrawableResourcesKt.seslToPainter(drawable3), DrawableResourcesKt.seslToPainter(drawable4));
                        z3 = false;
                        break;
                    } else {
                        i4++;
                        i2 = 4;
                        z4 = false;
                    }
                }
                z3 = z4;
                seslOpenCheckboxResourceSet = null;
                composerImpl.end(z3);
                if (seslOpenCheckboxResourceSet == null) {
                    composerImpl.startReplaceGroup(-1458151936);
                    int i5 = (i3 & 14) | ((i3 << 3) & 896);
                    int i6 = i3 << 6;
                    SeslOpenThemeCheckbox(z, seslOpenCheckboxResourceSet, function1, seslRecoilNodeFactoryM3352seslRecoilIndicationbw27NRU$default, modifier, z2, mutableInteractionSource, composerImpl, (458752 & i6) | i5 | (57344 & i6) | (i6 & 3670016));
                    composerImpl.end(false);
                } else {
                    composerImpl.startReplaceGroup(-1457817043);
                    SeslTheme.INSTANCE.getClass();
                    SeslBasicCheckboxColors seslBasicCheckboxColors = new SeslBasicCheckboxColors(SeslTheme.getColorScheme(composerImpl).primary, SeslTheme.getColorScheme(composerImpl).controlNormal, null);
                    SeslCheckboxDefaults.INSTANCE.getClass();
                    int i7 = i3 << 3;
                    int i8 = i3 << 9;
                    BasicCheckboxKt.m3346SeslBasicCheckboxaA_HZ9I(seslBasicCheckboxColors, z, function1, SeslCheckboxDefaults.checkboxSize, seslRecoilNodeFactoryM3352seslRecoilIndicationbw27NRU$default, modifier, z2, mutableInteractionSource, composerImpl, (458752 & i8) | (i7 & 896) | (i7 & 112) | 3072 | (3670016 & i8) | (i8 & 29360128));
                    composerImpl = composerImpl;
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                z3 = z4;
                seslOpenCheckboxResourceSet = null;
                composerImpl.end(z3);
                if (seslOpenCheckboxResourceSet == null) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, function1, modifier, z2, mutableInteractionSource, i) { // from class: com.samsung.sesl.compose.component.CheckboxKt$$ExternalSyntheticLambda1
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ Function1 f$1;
                public final /* synthetic */ Modifier f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ MutableInteractionSource f$4;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z5 = this.f$3;
                    MutableInteractionSource mutableInteractionSource2 = this.f$4;
                    CheckboxKt.SeslCheckbox(this.f$0, this.f$1, this.f$2, z5, mutableInteractionSource2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SeslOpenThemeCheckbox(final boolean z, final SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet, final Function1 function1, final SeslRecoilNodeFactory seslRecoilNodeFactory, final Modifier modifier, final boolean z2, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        Function0 function0;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1454928250);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(z) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(seslOpenCheckboxResourceSet) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(seslRecoilNodeFactory) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(z2) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i2 |= composerImpl2.changed(mutableInteractionSource) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i2) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeCheckbox (Checkbox.kt:135)");
            }
            ToggleableState toggleableState = z ? ToggleableState.On : ToggleableState.Off;
            composerImpl2.startReplaceGroup(1363386524);
            if (function1 != null) {
                composerImpl2.startReplaceGroup(1363387813);
                boolean z3 = ((i2 & 896) == 256) | ((i2 & 14) == 4);
                Object objRememberedValue = composerImpl2.rememberedValue();
                if (!z3) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function0() { // from class: com.samsung.sesl.compose.component.CheckboxKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                function1.mo781invoke(Boolean.valueOf(!z));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    function0 = (Function0) objRememberedValue;
                    composerImpl2.end(false);
                }
            } else {
                function0 = null;
            }
            Function0 function02 = function0;
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            SeslOpenThemeCheckboxInner(toggleableState, seslOpenCheckboxResourceSet, function02, seslRecoilNodeFactory, modifier, z2, mutableInteractionSource, composerImpl, i2 & 4193392);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.CheckboxKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet2 = seslOpenCheckboxResourceSet;
                    SeslRecoilNodeFactory seslRecoilNodeFactory2 = seslRecoilNodeFactory;
                    boolean z4 = z2;
                    MutableInteractionSource mutableInteractionSource2 = mutableInteractionSource;
                    CheckboxKt.SeslOpenThemeCheckbox(z, seslOpenCheckboxResourceSet2, function1, seslRecoilNodeFactory2, modifier, z4, mutableInteractionSource2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SeslOpenThemeCheckboxInner(ToggleableState toggleableState, final SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet, final Function0 function0, final SeslRecoilNodeFactory seslRecoilNodeFactory, final Modifier modifier, final boolean z, final MutableInteractionSource mutableInteractionSource, Composer composer, final int i) {
        int i2;
        SeslRecoilNodeFactory seslRecoilNodeFactory2;
        Modifier modifier2;
        MutableInteractionSource mutableInteractionSource2;
        Modifier modifierM140size3ABfNKs;
        ComposerImpl composerImpl;
        final ToggleableState toggleableState2 = toggleableState;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-728331112);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(toggleableState2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(seslOpenCheckboxResourceSet) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            seslRecoilNodeFactory2 = seslRecoilNodeFactory;
            i2 |= composerImpl2.changed(seslRecoilNodeFactory2) ? 2048 : 1024;
        } else {
            seslRecoilNodeFactory2 = seslRecoilNodeFactory;
        }
        if ((i & 24576) == 0) {
            modifier2 = modifier;
            i2 |= composerImpl2.changed(modifier2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        } else {
            modifier2 = modifier;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl2.changed(z) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            mutableInteractionSource2 = mutableInteractionSource;
            i2 |= composerImpl2.changed(mutableInteractionSource2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            mutableInteractionSource2 = mutableInteractionSource;
        }
        int i3 = i2;
        if ((599187 & i3) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeCheckboxInner (Checkbox.kt:161)");
            }
            if (function0 != null) {
                Role.Companion.getClass();
                Modifier modifierM183triStateToggleableO2vRcR0 = ToggleableKt.m183triStateToggleableO2vRcR0(modifier2, toggleableState2, mutableInteractionSource2, seslRecoilNodeFactory2, z, Role.m715boximpl(Role.Checkbox), function0);
                SeslCheckboxDefaults.INSTANCE.getClass();
                modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(modifierM183triStateToggleableO2vRcR0, SeslCheckboxDefaults.checkboxSize);
            } else {
                modifierM140size3ABfNKs = modifier;
            }
            if (z) {
                composerImpl2.startReplaceGroup(1682502622);
                composerImpl = composerImpl2;
                CrossfadeKt.Crossfade(toggleableState, modifierM140size3ABfNKs, (FiniteAnimationSpec) null, "", ComposableLambdaKt.rememberComposableLambda(-330653373, new Function3() { // from class: com.samsung.sesl.compose.component.CheckboxKt.SeslOpenThemeCheckboxInner.2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0035  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        ToggleableState toggleableState3 = (ToggleableState) obj;
                        Composer composer2 = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changed(toggleableState3) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeCheckboxInner.<anonymous> (Checkbox.kt:182)");
                                }
                                ToggleableState toggleableState4 = ToggleableState.On;
                                SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet2 = seslOpenCheckboxResourceSet;
                                if (toggleableState3 == toggleableState4) {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(2116369011);
                                    ImageKt.Image(seslOpenCheckboxResourceSet2.checkboxSelected, null, null, null, null, 0.0f, null, composerImpl4, 48, 124);
                                    composerImpl4.end(false);
                                } else {
                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                    composerImpl5.startReplaceGroup(2116535729);
                                    ImageKt.Image(seslOpenCheckboxResourceSet2.checkboxUnselected, null, null, null, null, 0.0f, null, composerImpl5, 48, 124);
                                    composerImpl5.end(false);
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i3 & 14) | 27648, 4);
                toggleableState2 = toggleableState;
                composerImpl.end(false);
            } else {
                toggleableState2 = toggleableState;
                composerImpl = composerImpl2;
                composerImpl.startReplaceGroup(1683028134);
                ImageKt.Image(toggleableState2 == ToggleableState.On ? seslOpenCheckboxResourceSet.checkboxDisabledOn : seslOpenCheckboxResourceSet.checkboxDisabledOff, null, null, null, null, 0.0f, null, composerImpl, 48, 124);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.CheckboxKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    SeslOpenCheckboxResourceSet seslOpenCheckboxResourceSet2 = seslOpenCheckboxResourceSet;
                    SeslRecoilNodeFactory seslRecoilNodeFactory3 = seslRecoilNodeFactory;
                    boolean z2 = z;
                    MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource;
                    CheckboxKt.SeslOpenThemeCheckboxInner(toggleableState2, seslOpenCheckboxResourceSet2, function0, seslRecoilNodeFactory3, modifier, z2, mutableInteractionSource3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
