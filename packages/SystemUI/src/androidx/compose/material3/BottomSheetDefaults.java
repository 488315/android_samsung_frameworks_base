package androidx.compose.material3;

import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.foundation.layout.WindowInsets_androidKt;
import androidx.compose.material3.internal.Strings;
import androidx.compose.material3.internal.Strings_androidKt;
import androidx.compose.material3.tokens.SheetBottomTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class BottomSheetDefaults {
    public static final float Elevation;
    public static final BottomSheetDefaults INSTANCE = new BottomSheetDefaults();
    public static final float PositionalThreshold;
    public static final float SheetMaxWidth;
    public static final float VelocityThreshold;

    static {
        SheetBottomTokens.INSTANCE.getClass();
        Elevation = SheetBottomTokens.DockedModalContainerElevation;
        Dp.Companion companion = Dp.Companion;
        SheetMaxWidth = 640;
        PositionalThreshold = 56;
        VelocityThreshold = 125;
    }

    private BottomSheetDefaults() {
    }

    public static long getContainerColor(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-ContainerColor> (SheetDefaults.kt:381)");
        }
        SheetBottomTokens.INSTANCE.getClass();
        long value = ColorSchemeKt.getValue(SheetBottomTokens.DockedContainerColor, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static Shape getExpandedShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-ExpandedShape> (SheetDefaults.kt:377)");
        }
        SheetBottomTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(SheetBottomTokens.DockedContainerShape, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    public static WindowInsets getWindowInsets(ComposerImpl composerImpl) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-windowInsets> (SheetDefaults.kt:398)");
        }
        WindowInsets.Companion companion = WindowInsets.Companion;
        WindowInsets safeDrawing = WindowInsets_androidKt.getSafeDrawing(composerImpl);
        WindowInsetsSides.Companion.getClass();
        WindowInsets windowInsetsM149onlybOOhFvg = WindowInsetsKt.m149onlybOOhFvg(safeDrawing, WindowInsetsSides.Bottom);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return windowInsetsM149onlybOOhFvg;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x014a  */
    /* renamed from: DragHandle-lgZ2HuY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m250DragHandlelgZ2HuY(Modifier modifier, float f, float f2, Shape shape, long j, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        float f3;
        int i4;
        float f4;
        Shape shape2;
        long j2;
        final float f5;
        Shape shape3;
        long value;
        final float f6;
        boolean zChanged;
        ComposerImpl composerImpl;
        final float f7;
        final float f8;
        final Modifier modifier3;
        final Shape shape4;
        final long j3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1364277227);
        int i5 = i2 & 1;
        if (i5 != 0) {
            i3 = i | 6;
            modifier2 = modifier;
        } else if ((i & 6) == 0) {
            modifier2 = modifier;
            i3 = (composerImpl2.changed(modifier2) ? 4 : 2) | i;
        } else {
            modifier2 = modifier;
            i3 = i;
        }
        int i6 = i2 & 2;
        if (i6 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                f3 = f;
                i3 |= composerImpl2.changed(f3) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 != 0) {
                if ((i & 384) == 0) {
                    f4 = f2;
                    i3 |= composerImpl2.changed(f4) ? 256 : 128;
                }
                if ((i & 3072) == 0) {
                    if ((i2 & 8) == 0) {
                        shape2 = shape;
                        int i7 = composerImpl2.changed(shape2) ? 2048 : 1024;
                        i3 |= i7;
                    } else {
                        shape2 = shape;
                    }
                    i3 |= i7;
                } else {
                    shape2 = shape;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        j2 = j;
                        int i8 = composerImpl2.changed(j2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i8;
                    } else {
                        j2 = j;
                    }
                    i3 |= i8;
                } else {
                    j2 = j;
                }
                if ((i3 & 9363) == 9362 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    composerImpl = composerImpl2;
                    modifier3 = modifier2;
                    f7 = f3;
                    f8 = f4;
                    shape4 = shape2;
                    j3 = j2;
                } else {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                        if (i5 != 0) {
                            modifier2 = Modifier.Companion;
                        }
                        if (i6 != 0) {
                            SheetBottomTokens.INSTANCE.getClass();
                            f3 = SheetBottomTokens.DockedDragHandleWidth;
                        }
                        if (i4 != 0) {
                            SheetBottomTokens.INSTANCE.getClass();
                            f4 = SheetBottomTokens.DockedDragHandleHeight;
                        }
                        if ((i2 & 8) != 0) {
                            MaterialTheme.INSTANCE.getClass();
                            i3 &= -7169;
                            shape2 = MaterialTheme.getShapes(composerImpl2).extraLarge;
                        }
                        if ((i2 & 16) == 0) {
                            SheetBottomTokens.INSTANCE.getClass();
                            i3 &= -57345;
                            f5 = f4;
                            shape3 = shape2;
                            value = ColorSchemeKt.getValue(SheetBottomTokens.DockedDragHandleColor, composerImpl2);
                            f6 = f3;
                        }
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.DragHandle (SheetDefaults.kt:412)");
                        }
                        int i9 = Strings.$r8$clinit;
                        final String strM322getString2EP1pXo = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_drag_handle_description, composerImpl2);
                        Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(modifier2, 0.0f, SheetDefaultsKt.DragHandleVerticalPadding, 1);
                        zChanged = composerImpl2.changed(strM322getString2EP1pXo);
                        Object objRememberedValue = composerImpl2.rememberedValue();
                        if (!zChanged) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new Function1() { // from class: androidx.compose.material3.BottomSheetDefaults$DragHandle$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, strM322getString2EP1pXo);
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue);
                            }
                            int i10 = i3 >> 6;
                            composerImpl = composerImpl2;
                            SurfaceKt.m304SurfaceT9BRK9s(SemanticsModifierKt.semantics(modifierM127paddingVpY3zN4$default, false, (Function1) objRememberedValue), shape3, value, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1039573072, new Function2() { // from class: androidx.compose.material3.BottomSheetDefaults$DragHandle$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                                ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.DragHandle.<anonymous> (SheetDefaults.kt:422)");
                                            }
                                            BoxKt.Box(SizeKt.m141sizeVpY3zN4(Modifier.Companion, f6, f5), composer2, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl, (i10 & 112) | 12582912 | (i10 & 896), 120);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            f7 = f6;
                            f8 = f5;
                            modifier3 = modifier2;
                            shape4 = shape3;
                            j3 = value;
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                        if ((i2 & 8) != 0) {
                            i3 &= -7169;
                        }
                        if ((i2 & 16) != 0) {
                            i3 &= -57345;
                        }
                    }
                    f6 = f3;
                    f5 = f4;
                    shape3 = shape2;
                    value = j2;
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    int i92 = Strings.$r8$clinit;
                    final String strM322getString2EP1pXo2 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_drag_handle_description, composerImpl2);
                    Modifier modifierM127paddingVpY3zN4$default2 = PaddingKt.m127paddingVpY3zN4$default(modifier2, 0.0f, SheetDefaultsKt.DragHandleVerticalPadding, 1);
                    zChanged = composerImpl2.changed(strM322getString2EP1pXo2);
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    if (!zChanged) {
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.BottomSheetDefaults$DragHandle$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            this.$tmp1_rcvr.m250DragHandlelgZ2HuY(modifier3, f7, f8, shape4, j3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i3 |= 384;
            f4 = f2;
            if ((i & 3072) == 0) {
            }
            if ((i & 24576) == 0) {
            }
            if ((i3 & 9363) == 9362) {
                composerImpl2.startDefaults();
                if ((i & 1) != 0) {
                    if (i5 != 0) {
                    }
                    if (i6 != 0) {
                    }
                    if (i4 != 0) {
                    }
                    if ((i2 & 8) != 0) {
                    }
                    if ((i2 & 16) == 0) {
                        f6 = f3;
                        f5 = f4;
                        shape3 = shape2;
                        value = j2;
                    }
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    int i922 = Strings.$r8$clinit;
                    final String strM322getString2EP1pXo22 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_drag_handle_description, composerImpl2);
                    Modifier modifierM127paddingVpY3zN4$default22 = PaddingKt.m127paddingVpY3zN4$default(modifier2, 0.0f, SheetDefaultsKt.DragHandleVerticalPadding, 1);
                    zChanged = composerImpl2.changed(strM322getString2EP1pXo22);
                    Object objRememberedValue22 = composerImpl2.rememberedValue();
                    if (!zChanged) {
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        f3 = f;
        i4 = i2 & 4;
        if (i4 != 0) {
        }
        f4 = f2;
        if ((i & 3072) == 0) {
        }
        if ((i & 24576) == 0) {
        }
        if ((i3 & 9363) == 9362) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
