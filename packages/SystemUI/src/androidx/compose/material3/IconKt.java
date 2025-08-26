package androidx.compose.material3;

import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.tokens.SmallIconButtonTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.PainterModifierKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.painter.BitmapPainter;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class IconKt {
    public static final Modifier DefaultIconSizeModifier;

    static {
        Modifier.Companion companion = Modifier.Companion;
        SmallIconButtonTokens.INSTANCE.getClass();
        DefaultIconSizeModifier = SizeKt.m140size3ABfNKs(companion, SmallIconButtonTokens.IconSize);
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00cd  */
    /* renamed from: Icon-ww6aTOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m271Iconww6aTOc(final ImageVector imageVector, String str, Modifier modifier, long j, Composer composer, final int i, final int i2) {
        int i3;
        final String str2;
        final Modifier modifier2;
        final long j2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-126890956);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(imageVector) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(str) ? 32 : 16;
        }
        int i4 = i2 & 4;
        if (i4 != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= ((i2 & 8) == 0 && composerImpl.changed(j)) ? 2048 : 1024;
        }
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            j2 = j;
            modifier2 = modifier;
            str2 = str;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i4 != 0) {
                    modifier = Modifier.Companion;
                }
                if ((i2 & 8) != 0) {
                    j = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
                    i3 &= -7169;
                }
                Modifier modifier3 = modifier;
                long j3 = j;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.Icon (Icon.kt:70)");
                }
                m270Iconww6aTOc(VectorPainterKt.rememberVectorPainter(imageVector, composerImpl), str, modifier3, j3, composerImpl, (i3 & 112) | 8 | (i3 & 896) | (i3 & 7168), 0);
                str2 = str;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier2 = modifier3;
                j2 = j3;
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 8) != 0) {
                    i3 &= -7169;
                }
                Modifier modifier32 = modifier;
                long j32 = j;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                m270Iconww6aTOc(VectorPainterKt.rememberVectorPainter(imageVector, composerImpl), str, modifier32, j32, composerImpl, (i3 & 112) | 8 | (i3 & 896) | (i3 & 7168), 0);
                str2 = str;
                if (ComposerKt.isTraceInProgress()) {
                }
                modifier2 = modifier32;
                j2 = j32;
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconKt$Icon$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    IconKt.m271Iconww6aTOc(imageVector, str2, modifier2, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /* renamed from: Icon-ww6aTOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m269Iconww6aTOc(final ImageBitmap imageBitmap, final String str, Modifier modifier, long j, Composer composer, final int i, final int i2) {
        int i3;
        String str2;
        final Modifier modifier2;
        final long j2;
        int i4;
        Modifier modifier3;
        long j3;
        boolean zChanged;
        ComposerImpl composerImpl;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1092052280);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(imageBitmap) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
            str2 = str;
        } else {
            str2 = str;
            if ((i & 48) == 0) {
                i3 |= composerImpl2.changed(str2) ? 32 : 16;
            }
        }
        int i5 = i2 & 4;
        if (i5 == 0) {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 256 : 128;
            }
            if ((i & 3072) != 0) {
                if ((i2 & 8) == 0) {
                    j2 = j;
                    int i6 = composerImpl2.changed(j2) ? 2048 : 1024;
                    i3 |= i6;
                } else {
                    j2 = j;
                }
                i3 |= i6;
            } else {
                j2 = j;
            }
            if ((i3 & 1171) != 1170 && composerImpl2.getSkipping()) {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
            } else {
                composerImpl2.startDefaults();
                if ((i & 1) == 0 && !composerImpl2.getDefaultsInvalid()) {
                    composerImpl2.skipToGroupEnd();
                    if ((i2 & 8) != 0) {
                        i3 &= -7169;
                    }
                    i4 = i3;
                    modifier3 = modifier2;
                } else {
                    Modifier modifier4 = i5 == 0 ? Modifier.Companion : modifier2;
                    if ((i2 & 8) == 0) {
                        i4 = i3 & (-7169);
                        modifier3 = modifier4;
                        j3 = ((Color) composerImpl2.consume(ContentColorKt.LocalContentColor)).value;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.Icon (Icon.kt:106)");
                        }
                        zChanged = composerImpl2.changed(imageBitmap);
                        Object objRememberedValue = composerImpl2.rememberedValue();
                        if (zChanged) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                BitmapPainter bitmapPainter = new BitmapPainter(imageBitmap, 0L, 0L, 6, null);
                                composerImpl2.updateRememberedValue(bitmapPainter);
                                objRememberedValue = bitmapPainter;
                            }
                            Modifier modifier5 = modifier3;
                            long j4 = j3;
                            m270Iconww6aTOc((BitmapPainter) objRememberedValue, str2, modifier5, j4, composerImpl2, i4 & 8176, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl = composerImpl2;
                            j2 = j4;
                            modifier2 = modifier5;
                        }
                    } else {
                        i4 = i3;
                        modifier3 = modifier4;
                    }
                }
                j3 = j2;
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                zChanged = composerImpl2.changed(imageBitmap);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (zChanged) {
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconKt$Icon$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        IconKt.m269Iconww6aTOc(imageBitmap, str, modifier2, j2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 384;
        modifier2 = modifier;
        if ((i & 3072) != 0) {
        }
        if ((i3 & 1171) != 1170) {
            composerImpl2.startDefaults();
            if ((i & 1) == 0) {
                if (i5 == 0) {
                }
                if ((i2 & 8) == 0) {
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0129  */
    /* renamed from: Icon-ww6aTOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m270Iconww6aTOc(final Painter painter, final String str, Modifier modifier, long j, Composer composer, final int i, final int i2) {
        Painter painter2;
        int i3;
        Modifier modifier2;
        long j2;
        Modifier modifier3;
        boolean z;
        Modifier modifierSemantics;
        Modifier modifier4;
        final long j3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2142239481);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            painter2 = painter;
        } else {
            painter2 = painter;
            if ((i & 6) == 0) {
                i3 = (composerImpl.changedInstance(painter2) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(str) ? 32 : 16;
        }
        int i4 = i2 & 4;
        if (i4 == 0) {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            if ((i & 3072) != 0) {
                j2 = j;
                i3 |= ((i2 & 8) == 0 && composerImpl.changed(j2)) ? 2048 : 1024;
            } else {
                j2 = j;
            }
            if ((i3 & 1171) != 1170 && composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                modifier3 = modifier2;
                j3 = j2;
            } else {
                composerImpl.startDefaults();
                if ((i & 1) == 0 && !composerImpl.getDefaultsInvalid()) {
                    composerImpl.skipToGroupEnd();
                    if ((i2 & 8) != 0) {
                        i3 &= -7169;
                    }
                    modifier3 = modifier2;
                } else {
                    modifier3 = i4 == 0 ? Modifier.Companion : modifier2;
                    if ((i2 & 8) != 0) {
                        j2 = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
                        i3 &= -7169;
                    }
                }
                long j4 = j2;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.Icon (Icon.kt:143)");
                }
                z = (((i3 & 7168) ^ 3072) <= 2048 && composerImpl.changed(j4)) || (i3 & 3072) == 2048;
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion = Composer.Companion;
                if (!z) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        Color.Companion.getClass();
                        objRememberedValue = ULong.m3447equalsimpl0(j4, Color.Unspecified) ? null : ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, j4);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    ColorFilter colorFilter = (ColorFilter) objRememberedValue;
                    if (str != null) {
                        composerImpl.startReplaceGroup(-2067089176);
                        Modifier.Companion companion2 = Modifier.Companion;
                        boolean z2 = (i3 & 112) == 32;
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (!z2) {
                            companion.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.IconKt$Icon$semantics$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                                        Role.Companion.getClass();
                                        SemanticsPropertiesKt.m719setRolekuIjeqM(semanticsPropertyReceiver, Role.Image);
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            modifierSemantics = SemanticsModifierKt.semantics(companion2, false, (Function1) objRememberedValue2);
                            composerImpl.end(false);
                        }
                    } else {
                        composerImpl.startReplaceGroup(-2066930394);
                        composerImpl.end(false);
                        modifierSemantics = Modifier.Companion;
                    }
                    Function1 function1 = InspectableValueKt.NoInspectorInfo;
                    long jMo563getIntrinsicSizeNHjbRc = painter2.mo563getIntrinsicSizeNHjbRc();
                    Size.Companion.getClass();
                    if (!Size.m416equalsimpl0(jMo563getIntrinsicSizeNHjbRc, Size.Unspecified)) {
                        long jMo563getIntrinsicSizeNHjbRc2 = painter2.mo563getIntrinsicSizeNHjbRc();
                        if (!Float.isInfinite(Size.m419getWidthimpl(jMo563getIntrinsicSizeNHjbRc2)) || !Float.isInfinite(Size.m417getHeightimpl(jMo563getIntrinsicSizeNHjbRc2))) {
                            modifier4 = Modifier.Companion;
                        } else {
                            modifier4 = DefaultIconSizeModifier;
                        }
                        Modifier modifierThen = modifier3.then(modifier4);
                        ContentScale.Companion.getClass();
                        BoxKt.Box(PainterModifierKt.paint$default(modifierThen, painter2, null, ContentScale.Companion.Fit, 0.0f, colorFilter, 22).then(modifierSemantics), composerImpl, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        j3 = j4;
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                final Modifier modifier5 = modifier3;
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconKt$Icon$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        IconKt.m270Iconww6aTOc(painter, str, modifier5, j3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 384;
        modifier2 = modifier;
        if ((i & 3072) != 0) {
        }
        if ((i3 & 1171) != 1170) {
            composerImpl.startDefaults();
            if ((i & 1) == 0) {
                if (i4 == 0) {
                }
                if ((i2 & 8) != 0) {
                }
                long j42 = j2;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (((i3 & 7168) ^ 3072) <= 2048) {
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    Composer.Companion companion3 = Composer.Companion;
                    if (!z) {
                    }
                } else {
                    Object objRememberedValue32 = composerImpl.rememberedValue();
                    Composer.Companion companion32 = Composer.Companion;
                    if (!z) {
                    }
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
