package androidx.compose.material3;

import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class DividerKt {
    /* JADX WARN: Removed duplicated region for block: B:56:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d8  */
    /* renamed from: HorizontalDivider-9IZ8Weo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m263HorizontalDivider9IZ8Weo(Modifier modifier, final float f, final long j, Composer composer, final int i, final int i2) {
        int i3;
        boolean z;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(75144485);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i5 = i2 & 2;
        if (i5 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= ((i2 & 4) == 0 && composerImpl.changed(j)) ? 256 : 128;
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    modifier = Modifier.Companion;
                }
                if (i5 != 0) {
                    DividerDefaults.INSTANCE.getClass();
                    f = DividerDefaults.Thickness;
                }
                if ((i2 & 4) != 0) {
                    DividerDefaults.INSTANCE.getClass();
                    j = DividerDefaults.getColor(composerImpl);
                    i3 &= -897;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.HorizontalDivider (Divider.kt:54)");
                }
                Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(modifier, 1.0f), f);
                z = true;
                boolean z3 = (i3 & 112) != 32;
                if ((((i3 & 896) ^ 384) > 256 || !composerImpl.changed(j)) && (i3 & 384) != 256) {
                    z = false;
                }
                z2 = z3 | z;
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!z2) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: androidx.compose.material3.DividerKt$HorizontalDivider$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                float f2 = 2;
                                DrawScope.m537drawLineNGM6Ib0$default(drawScope, j, OffsetKt.Offset(0.0f, drawScope.mo58toPx0680j_4(f) / f2), OffsetKt.Offset(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc()), drawScope.mo58toPx0680j_4(f) / f2), drawScope.mo58toPx0680j_4(f), 0, 0.0f, 496);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    CanvasKt.Canvas(modifierM131height3ABfNKs, (Function1) objRememberedValue, composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                Modifier modifierM131height3ABfNKs2 = SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(modifier, 1.0f), f);
                z = true;
                if ((i3 & 112) != 32) {
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
        }
        final Modifier modifier2 = modifier;
        final float f2 = f;
        final long j2 = j;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.DividerKt$HorizontalDivider$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    DividerKt.m263HorizontalDivider9IZ8Weo(modifier2, f2, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d8  */
    /* renamed from: VerticalDivider-9IZ8Weo, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m264VerticalDivider9IZ8Weo(Modifier modifier, final float f, final long j, Composer composer, final int i, final int i2) {
        int i3;
        boolean z;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1534852205);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i5 = i2 & 2;
        if (i5 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= ((i2 & 4) == 0 && composerImpl.changed(j)) ? 256 : 128;
        }
        if ((i3 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    modifier = Modifier.Companion;
                }
                if (i5 != 0) {
                    DividerDefaults.INSTANCE.getClass();
                    f = DividerDefaults.Thickness;
                }
                if ((i2 & 4) != 0) {
                    DividerDefaults.INSTANCE.getClass();
                    j = DividerDefaults.getColor(composerImpl);
                    i3 &= -897;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.VerticalDivider (Divider.kt:83)");
                }
                Modifier modifierM144width3ABfNKs = SizeKt.m144width3ABfNKs(modifier.then(SizeKt.FillWholeMaxHeight), f);
                z = true;
                boolean z3 = (i3 & 112) != 32;
                if ((((i3 & 896) ^ 384) > 256 || !composerImpl.changed(j)) && (i3 & 384) != 256) {
                    z = false;
                }
                z2 = z3 | z;
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!z2) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: androidx.compose.material3.DividerKt$VerticalDivider$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                float f2 = 2;
                                DrawScope.m537drawLineNGM6Ib0$default(drawScope, j, OffsetKt.Offset(drawScope.mo58toPx0680j_4(f) / f2, 0.0f), OffsetKt.Offset(drawScope.mo58toPx0680j_4(f) / f2, Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc())), drawScope.mo58toPx0680j_4(f), 0, 0.0f, 496);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    CanvasKt.Canvas(modifierM144width3ABfNKs, (Function1) objRememberedValue, composerImpl, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 4) != 0) {
                    i3 &= -897;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                Modifier modifierM144width3ABfNKs2 = SizeKt.m144width3ABfNKs(modifier.then(SizeKt.FillWholeMaxHeight), f);
                z = true;
                if ((i3 & 112) != 32) {
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
        }
        final Modifier modifier2 = modifier;
        final float f2 = f;
        final long j2 = j;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.DividerKt$VerticalDivider$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    DividerKt.m264VerticalDivider9IZ8Weo(modifier2, f2, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
