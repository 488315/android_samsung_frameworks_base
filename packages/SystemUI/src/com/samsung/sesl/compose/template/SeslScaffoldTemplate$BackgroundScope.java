package com.samsung.sesl.compose.template;

import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.unit.DensityKt;
import com.samsung.sesl.compose.foundation.shape.RoundedCornerShapeKt;
import com.samsung.sesl.compose.foundation.shape.SeslRoundedCornerShape;
import com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.ui.token.DimensionTokens;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslScaffoldTemplate$BackgroundScope {
    public static final Companion Companion = new Companion(null);
    public static final SeslScaffoldTemplate$BackgroundScope instance = new SeslScaffoldTemplate$BackgroundScope();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private SeslScaffoldTemplate$BackgroundScope() {
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d2  */
    /* renamed from: Background-FNF3uiM, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m3355BackgroundFNF3uiM(final PaddingValues paddingValues, Modifier modifier, long j, Composer composer, final int i, final int i2) {
        int i3;
        final long j2;
        boolean z;
        boolean z2;
        final long j3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2113654266);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(paddingValues) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= ((i2 & 4) == 0 && composerImpl.changed(j)) ? 256 : 128;
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            j3 = j;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    modifier = Modifier.Companion;
                }
                if ((i2 & 4) != 0) {
                    SeslTheme.INSTANCE.getClass();
                    i3 &= -897;
                    j2 = SeslTheme.getColorScheme(composerImpl).roundedCorner;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.template.SeslScaffoldTemplate.BackgroundScope.Background (ScaffoldTemplate.kt:30)");
                }
                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
                composerImpl.startReplaceGroup(-1284556100);
                z = true;
                boolean z3 = (i3 & 14) != 4;
                if ((((i3 & 896) ^ 384) > 256 || !composerImpl.changed(j2)) && (i3 & 384) != 256) {
                    z = false;
                }
                z2 = z3 | z;
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!z2) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                SeslScaffoldTemplate$BackgroundScope.Companion companion = SeslScaffoldTemplate$BackgroundScope.Companion;
                                PaddingValues paddingValues2 = paddingValues;
                                float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(paddingValues2.mo113calculateTopPaddingD9Ej5fM());
                                Offset.Companion.getClass();
                                long jSize = androidx.compose.ui.geometry.SizeKt.Size(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), fMo58toPx0680j_4);
                                long j4 = j2;
                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j4, 0L, jSize, 0.0f, null, null, 0, 120);
                                float fMo58toPx0680j_42 = drawScope.mo58toPx0680j_4(paddingValues2.mo111calculateLeftPaddingu2uoSUM(drawScope.getLayoutDirection()));
                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j4, 0L, androidx.compose.ui.geometry.SizeKt.Size(fMo58toPx0680j_42, 0.0f), 0.0f, null, null, 0, 120);
                                float fMo58toPx0680j_43 = drawScope.mo58toPx0680j_4(paddingValues2.mo111calculateLeftPaddingu2uoSUM(drawScope.getLayoutDirection()));
                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j4, OffsetKt.Offset(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()) - fMo58toPx0680j_43, 0.0f), androidx.compose.ui.geometry.SizeKt.Size(fMo58toPx0680j_43, 0.0f), 0.0f, null, null, 0, 120);
                                float fMo58toPx0680j_44 = drawScope.mo58toPx0680j_4(paddingValues2.mo110calculateBottomPaddingD9Ej5fM());
                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j4, OffsetKt.Offset(0.0f, Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc()) - fMo58toPx0680j_44), androidx.compose.ui.geometry.SizeKt.Size(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), fMo58toPx0680j_44), 0.0f, null, null, 0, 120);
                                drawScope.getDrawContext().transform.translate(fMo58toPx0680j_42, fMo58toPx0680j_4);
                                try {
                                    DimensionTokens.INSTANCE.getClass();
                                    CornerSize cornerSizeM186CornerSize0680j_4 = CornerSizeKt.m186CornerSize0680j_4(DimensionTokens.roundedCornerRadius);
                                    SeslRoundedCornerShape seslRoundedCornerShape = RoundedCornerShapeKt.SeslCircleShape;
                                    OutlineKt.m492drawOutlinewDX37Ww$default(drawScope, new SeslRoundedCornerShape(cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, cornerSizeM186CornerSize0680j_4, true).mo41createOutlinePq9zytI(androidx.compose.ui.geometry.SizeKt.Size((Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()) - fMo58toPx0680j_42) - fMo58toPx0680j_43, (Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc()) - fMo58toPx0680j_4) - fMo58toPx0680j_44), drawScope.getLayoutDirection(), DensityKt.Density$default(drawScope.getDensity())), j4, 0.0f, Fill.INSTANCE, 52);
                                    drawScope.getDrawContext().transform.translate(-fMo58toPx0680j_42, -fMo58toPx0680j_4);
                                    return Unit.INSTANCE;
                                } catch (Throwable th) {
                                    drawScope.getDrawContext().transform.translate(-fMo58toPx0680j_42, -fMo58toPx0680j_4);
                                    throw th;
                                }
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    BoxKt.Box(DrawModifierKt.drawBehind(modifierFillMaxSize, (Function1) objRememberedValue), composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    j3 = j2;
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                }
            }
            j2 = j;
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
            }
            Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(modifier, 1.0f);
            composerImpl.startReplaceGroup(-1284556100);
            z = true;
            if ((i3 & 14) != 4) {
            }
            if (((i3 & 896) ^ 384) > 256) {
                z = false;
                z2 = z3 | z;
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!z2) {
                }
            } else {
                z = false;
                z2 = z3 | z;
                Object objRememberedValue22 = composerImpl.rememberedValue();
                if (!z2) {
                }
            }
        }
        final Modifier modifier2 = modifier;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.template.SeslScaffoldTemplate$BackgroundScope$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    SeslScaffoldTemplate$BackgroundScope.Companion companion = SeslScaffoldTemplate$BackgroundScope.Companion;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.m3355BackgroundFNF3uiM(paddingValues, modifier2, j3, composer2, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
