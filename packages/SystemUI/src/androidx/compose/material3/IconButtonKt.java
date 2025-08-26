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
import androidx.compose.material3.tokens.SmallIconButtonTokens;
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

/* loaded from: classes.dex */
public abstract class IconButtonKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:136:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void FilledIconButton(final int i, final int i2, MutableInteractionSource mutableInteractionSource, IconButtonColors iconButtonColors, Composer composer, Modifier modifier, Shape shape, final Function0 function0, final Function2 function2, boolean z) {
        Function0 function02;
        int i3;
        Modifier modifier2;
        int i4;
        boolean z2;
        Shape shape2;
        IconButtonColors iconButtonColors2;
        int i5;
        MutableInteractionSource mutableInteractionSource2;
        Modifier modifier3;
        IconButtonColors iconButtonColors3;
        int i6;
        MutableInteractionSource mutableInteractionSource3;
        boolean z3;
        Shape shape3;
        ComposerImpl composerImpl;
        final Modifier modifier4;
        final boolean z4;
        final Shape shape4;
        final IconButtonColors iconButtonColors4;
        final MutableInteractionSource mutableInteractionSource4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1594730011);
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
        int i7 = i2 & 2;
        if (i7 != 0) {
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
                        int i8 = composerImpl2.changed(shape2) ? 2048 : 1024;
                        i3 |= i8;
                    } else {
                        shape2 = shape;
                    }
                    i3 |= i8;
                } else {
                    shape2 = shape;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        iconButtonColors2 = iconButtonColors;
                        int i9 = composerImpl2.changed(iconButtonColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i9;
                    } else {
                        iconButtonColors2 = iconButtonColors;
                    }
                    i3 |= i9;
                } else {
                    iconButtonColors2 = iconButtonColors;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                } else {
                    if ((i & 196608) == 0) {
                        mutableInteractionSource2 = mutableInteractionSource;
                        i3 |= composerImpl2.changed(mutableInteractionSource2) ? 131072 : 65536;
                    }
                    if ((i2 & 64) != 0) {
                        if ((i & 1572864) == 0) {
                            i3 |= composerImpl2.changedInstance(function2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        if ((599187 & i3) == 599186 && composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            composerImpl = composerImpl2;
                            modifier4 = modifier2;
                            z4 = z2;
                            shape4 = shape2;
                            iconButtonColors4 = iconButtonColors2;
                            mutableInteractionSource4 = mutableInteractionSource2;
                        } else {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                Modifier modifier5 = i7 == 0 ? Modifier.Companion : modifier2;
                                boolean z5 = i4 == 0 ? true : z2;
                                if ((i2 & 8) != 0) {
                                    IconButtonDefaults.INSTANCE.getClass();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.<get-filledShape> (IconButtonDefaults.kt:859)");
                                    }
                                    SmallIconButtonTokens.INSTANCE.getClass();
                                    Shape value = ShapesKt.getValue(SmallIconButtonTokens.ContainerShapeRound, composerImpl2);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    i3 &= -7169;
                                    shape2 = value;
                                }
                                if ((i2 & 16) != 0) {
                                    IconButtonDefaults.INSTANCE.getClass();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.filledIconButtonColors (IconButtonDefaults.kt:306)");
                                    }
                                    MaterialTheme.INSTANCE.getClass();
                                    IconButtonColors defaultFilledIconButtonColors$material3_release = IconButtonDefaults.getDefaultFilledIconButtonColors$material3_release(MaterialTheme.getColorScheme(composerImpl2));
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    i3 &= -57345;
                                    iconButtonColors2 = defaultFilledIconButtonColors$material3_release;
                                }
                                if (i5 == 0) {
                                    modifier3 = modifier5;
                                    mutableInteractionSource3 = null;
                                    shape3 = shape2;
                                    iconButtonColors3 = iconButtonColors2;
                                    i6 = 196608;
                                    z3 = z5;
                                    composerImpl2.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.FilledIconButton (IconButton.kt:521)");
                                    }
                                    int i10 = i6 | (i3 & 14) | (i3 & 112) | (i3 & 896) | (i3 & 7168) | (57344 & i3);
                                    int i11 = i3 << 3;
                                    composerImpl = composerImpl2;
                                    SurfaceIconButton(function02, modifier3, z3, shape3, iconButtonColors3, null, mutableInteractionSource3, function2, composerImpl, i10 | (3670016 & i11) | (i11 & 29360128));
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    modifier4 = modifier3;
                                    z4 = z3;
                                    shape4 = shape3;
                                    iconButtonColors4 = iconButtonColors3;
                                    mutableInteractionSource4 = mutableInteractionSource3;
                                } else {
                                    modifier3 = modifier5;
                                    iconButtonColors3 = iconButtonColors2;
                                    i6 = 196608;
                                    mutableInteractionSource3 = mutableInteractionSource2;
                                    z3 = z5;
                                }
                            } else {
                                composerImpl2.skipToGroupEnd();
                                if ((i2 & 8) != 0) {
                                    i3 &= -7169;
                                }
                                if ((i2 & 16) != 0) {
                                    i3 &= -57345;
                                }
                                modifier3 = modifier2;
                                iconButtonColors3 = iconButtonColors2;
                                i6 = 196608;
                                mutableInteractionSource3 = mutableInteractionSource2;
                                z3 = z2;
                            }
                            shape3 = shape2;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            int i102 = i6 | (i3 & 14) | (i3 & 112) | (i3 & 896) | (i3 & 7168) | (57344 & i3);
                            int i112 = i3 << 3;
                            composerImpl = composerImpl2;
                            SurfaceIconButton(function02, modifier3, z3, shape3, iconButtonColors3, null, mutableInteractionSource3, function2, composerImpl, i102 | (3670016 & i112) | (i112 & 29360128));
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            modifier4 = modifier3;
                            z4 = z3;
                            shape4 = shape3;
                            iconButtonColors4 = iconButtonColors3;
                            mutableInteractionSource4 = mutableInteractionSource3;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconButtonKt.FilledIconButton.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    Function0 function03 = function0;
                                    Modifier modifier6 = modifier4;
                                    boolean z6 = z4;
                                    Shape shape5 = shape4;
                                    IconButtonColors iconButtonColors5 = iconButtonColors4;
                                    MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource4;
                                    IconButtonKt.FilledIconButton(RecomposeScopeImplKt.updateChangedFlags(i | 1), i2, mutableInteractionSource5, iconButtonColors5, (Composer) obj, modifier6, shape5, function03, function2, z6);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 1572864;
                    if ((599187 & i3) == 599186) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0) {
                            if (i7 == 0) {
                            }
                            if (i4 == 0) {
                            }
                            if ((i2 & 8) != 0) {
                            }
                            if ((i2 & 16) != 0) {
                            }
                            if (i5 == 0) {
                            }
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                mutableInteractionSource2 = mutableInteractionSource;
                if ((i2 & 64) != 0) {
                }
                if ((599187 & i3) == 599186) {
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
            mutableInteractionSource2 = mutableInteractionSource;
            if ((i2 & 64) != 0) {
            }
            if ((599187 & i3) == 599186) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
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
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        mutableInteractionSource2 = mutableInteractionSource;
        if ((i2 & 64) != 0) {
        }
        if ((599187 & i3) == 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:128:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void IconButton(final int i, final int i2, MutableInteractionSource mutableInteractionSource, IconButtonColors iconButtonColors, Composer composer, Modifier modifier, Shape shape, final Function0 function0, final Function2 function2, boolean z) {
        Function0 function02;
        int i3;
        Modifier modifier2;
        int i4;
        boolean z2;
        IconButtonColors iconButtonColors2;
        int i5;
        MutableInteractionSource mutableInteractionSource2;
        Shape shape2;
        Function2 function22;
        IconButtonColors iconButtonColors3;
        MutableInteractionSource mutableInteractionSource3;
        Shape shape3;
        Modifier modifier3;
        boolean z3;
        ComposerImpl composerImpl;
        final Modifier modifier4;
        final boolean z4;
        final Shape shape4;
        final IconButtonColors iconButtonColors4;
        final MutableInteractionSource mutableInteractionSource4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1409666215);
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
        int i6 = i2 & 2;
        if (i6 != 0) {
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
                        iconButtonColors2 = iconButtonColors;
                        int i7 = composerImpl2.changed(iconButtonColors2) ? 2048 : 1024;
                        i3 |= i7;
                    } else {
                        iconButtonColors2 = iconButtonColors;
                    }
                    i3 |= i7;
                } else {
                    iconButtonColors2 = iconButtonColors;
                }
                i5 = i2 & 16;
                if (i5 == 0) {
                    if ((i & 24576) == 0) {
                        mutableInteractionSource2 = mutableInteractionSource;
                        i3 |= composerImpl2.changed(mutableInteractionSource2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    if ((196608 & i) != 0) {
                        if ((i2 & 32) == 0) {
                            shape2 = shape;
                            int i8 = composerImpl2.changed(shape2) ? 131072 : 65536;
                            i3 |= i8;
                        } else {
                            shape2 = shape;
                        }
                        i3 |= i8;
                    } else {
                        shape2 = shape;
                    }
                    if ((i2 & 64) == 0) {
                        i3 |= 1572864;
                        function22 = function2;
                    } else {
                        function22 = function2;
                        if ((i & 1572864) == 0) {
                            i3 |= composerImpl2.changedInstance(function22) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                    }
                    if ((599187 & i3) == 599186 || !composerImpl2.getSkipping()) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            Modifier modifier5 = i6 == 0 ? Modifier.Companion : modifier2;
                            boolean z5 = i4 == 0 ? true : z2;
                            if ((i2 & 8) != 0) {
                                IconButtonDefaults.INSTANCE.getClass();
                                i3 &= -7169;
                                iconButtonColors2 = IconButtonDefaults.iconButtonColors(composerImpl2);
                            }
                            if (i5 != 0) {
                                mutableInteractionSource2 = null;
                            }
                            if ((i2 & 32) == 0) {
                                IconButtonDefaults.INSTANCE.getClass();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.<get-standardShape> (IconButtonDefaults.kt:855)");
                                }
                                SmallIconButtonTokens.INSTANCE.getClass();
                                Shape value = ShapesKt.getValue(SmallIconButtonTokens.ContainerShapeRound, composerImpl2);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                i3 &= -458753;
                                z3 = z5;
                                shape3 = value;
                                iconButtonColors3 = iconButtonColors2;
                                mutableInteractionSource3 = mutableInteractionSource2;
                                modifier3 = modifier5;
                            } else {
                                iconButtonColors3 = iconButtonColors2;
                                mutableInteractionSource3 = mutableInteractionSource2;
                                shape3 = shape2;
                                modifier3 = modifier5;
                                z3 = z5;
                            }
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i2 & 8) != 0) {
                                i3 &= -7169;
                            }
                            if ((i2 & 32) != 0) {
                                i3 &= -458753;
                            }
                            iconButtonColors3 = iconButtonColors2;
                            mutableInteractionSource3 = mutableInteractionSource2;
                            shape3 = shape2;
                            modifier3 = modifier2;
                            z3 = z2;
                        }
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.IconButton (IconButton.kt:169)");
                        }
                        int i9 = i3 << 3;
                        composerImpl = composerImpl2;
                        IconButtonImpl(modifier3, function02, z3, shape3, iconButtonColors3, mutableInteractionSource3, function22, composerImpl, ((i3 >> 3) & 14) | (i9 & 112) | (i3 & 896) | ((i3 >> 6) & 7168) | (57344 & i9) | (i9 & 458752) | (i3 & 3670016));
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        modifier4 = modifier3;
                        z4 = z3;
                        shape4 = shape3;
                        iconButtonColors4 = iconButtonColors3;
                        mutableInteractionSource4 = mutableInteractionSource3;
                    } else {
                        composerImpl2.skipToGroupEnd();
                        composerImpl = composerImpl2;
                        modifier4 = modifier2;
                        z4 = z2;
                        iconButtonColors4 = iconButtonColors2;
                        mutableInteractionSource4 = mutableInteractionSource2;
                        shape4 = shape2;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconButtonKt.IconButton.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                Function0 function03 = function0;
                                Modifier modifier6 = modifier4;
                                boolean z6 = z4;
                                IconButtonColors iconButtonColors5 = iconButtonColors4;
                                MutableInteractionSource mutableInteractionSource5 = mutableInteractionSource4;
                                IconButtonKt.IconButton(RecomposeScopeImplKt.updateChangedFlags(i | 1), i2, mutableInteractionSource5, iconButtonColors5, (Composer) obj, modifier6, shape4, function03, function2, z6);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 24576;
                mutableInteractionSource2 = mutableInteractionSource;
                if ((196608 & i) != 0) {
                }
                if ((i2 & 64) == 0) {
                }
                if ((599187 & i3) == 599186) {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0) {
                        if (i6 == 0) {
                        }
                        if (i4 == 0) {
                        }
                        if ((i2 & 8) != 0) {
                        }
                        if (i5 != 0) {
                        }
                        if ((i2 & 32) == 0) {
                        }
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        int i92 = i3 << 3;
                        composerImpl = composerImpl2;
                        IconButtonImpl(modifier3, function02, z3, shape3, iconButtonColors3, mutableInteractionSource3, function22, composerImpl, ((i3 >> 3) & 14) | (i92 & 112) | (i3 & 896) | ((i3 >> 6) & 7168) | (57344 & i92) | (i92 & 458752) | (i3 & 3670016));
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        modifier4 = modifier3;
                        z4 = z3;
                        shape4 = shape3;
                        iconButtonColors4 = iconButtonColors3;
                        mutableInteractionSource4 = mutableInteractionSource3;
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            if ((i & 3072) == 0) {
            }
            i5 = i2 & 16;
            if (i5 == 0) {
            }
            mutableInteractionSource2 = mutableInteractionSource;
            if ((196608 & i) != 0) {
            }
            if ((i2 & 64) == 0) {
            }
            if ((599187 & i3) == 599186) {
            }
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
        i5 = i2 & 16;
        if (i5 == 0) {
        }
        mutableInteractionSource2 = mutableInteractionSource;
        if ((196608 & i) != 0) {
        }
        if ((i2 & 64) == 0) {
        }
        if ((599187 & i3) == 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final void IconButtonImpl(final Modifier modifier, final Function0 function0, final boolean z, final Shape shape, final IconButtonColors iconButtonColors, final MutableInteractionSource mutableInteractionSource, final Function2 function2, Composer composer, final int i) {
        int i2;
        MutableInteractionSource mutableInteractionSource2;
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
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(1135597258);
                composerImpl.end(false);
                mutableInteractionSource2 = mutableInteractionSource;
            }
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
            Modifier modifierThen = modifier.then(MinimumInteractiveModifier.INSTANCE);
            long jM268smallContainerSizeNwlBFI$default = IconButtonDefaults.m268smallContainerSizeNwlBFI$default(IconButtonDefaults.INSTANCE);
            FillElement fillElement = SizeKt.FillWholeMaxWidth;
            Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(ClipKt.clip(SizeKt.m141sizeVpY3zN4(modifierThen, DpSize.m847getWidthD9Ej5fM(jM268smallContainerSizeNwlBFI$default), DpSize.m846getHeightD9Ej5fM(jM268smallContainerSizeNwlBFI$default)), shape), z ? iconButtonColors.containerColor : iconButtonColors.disabledContainerColor, shape);
            Role.Companion.getClass();
            MutableInteractionSource MutableInteractionSource = mutableInteractionSource2;
            Modifier modifierThen2 = ClickableKt.m34clickableO2vRcR0$default(modifierM26backgroundbw27NRU, MutableInteractionSource, RippleKt.m281rippleH2RKhps$default(0.0f, false, 7), z, null, Role.m715boximpl(0), function0, 8).then(new ChildSemanticsNodeElement(new Function1() { // from class: androidx.compose.material3.internal.ChildParentSemanticsKt$childSemantics$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj3) {
                    return Unit.INSTANCE;
                }
            }));
            if (MutableInteractionSource == null) {
                MutableInteractionSource = InteractionSourceKt.MutableInteractionSource();
            }
            Modifier modifierThen3 = modifierThen2.then(new InteractionSourceModifierElement(MutableInteractionSource));
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen3);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(z ? iconButtonColors.contentColor : iconButtonColors.disabledContentColor)), function2, composerImpl, ((i3 >> 15) & 112) | 8);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconButtonKt.IconButtonImpl.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    IconButtonKt.IconButtonImpl(modifier, function0, z, shape, iconButtonColors, mutableInteractionSource, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
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
            SurfaceKt.m305Surfaceo_FOJdg(function02, SemanticsModifierKt.semantics(modifier, false, new Function1() { // from class: androidx.compose.material3.IconButtonKt.SurfaceIconButton.1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Role.Companion.getClass();
                    SemanticsPropertiesKt.m719setRolekuIjeqM((SemanticsPropertyReceiver) obj, 0);
                    return Unit.INSTANCE;
                }
            }), z, shape2, z ? iconButtonColors.containerColor : iconButtonColors.disabledContainerColor, z ? iconButtonColors.contentColor : iconButtonColors.disabledContentColor, 0.0f, borderStroke, mutableInteractionSource, ComposableLambdaKt.rememberComposableLambda(524891765, new Function2() { // from class: androidx.compose.material3.IconButtonKt.SurfaceIconButton.2
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
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.SurfaceIconButton.<anonymous> (IconButton.kt:1205)");
                            }
                            Modifier.Companion companion = Modifier.Companion;
                            long jM268smallContainerSizeNwlBFI$default = IconButtonDefaults.m268smallContainerSizeNwlBFI$default(IconButtonDefaults.INSTANCE);
                            FillElement fillElement = SizeKt.FillWholeMaxWidth;
                            Modifier modifierM141sizeVpY3zN4 = SizeKt.m141sizeVpY3zN4(companion, DpSize.m847getWidthD9Ej5fM(jM268smallContainerSizeNwlBFI$default), DpSize.m846getHeightD9Ej5fM(jM268smallContainerSizeNwlBFI$default));
                            Alignment.Companion.getClass();
                            BiasAlignment biasAlignment = Alignment.Companion.Center;
                            Function2 function22 = function2;
                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM141sizeVpY3zN4);
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
                            Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                            }
                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                            function22.invoke(composer2, 0);
                            composerImpl3.end(true);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, i3 | (i4 & 234881024) | (i4 & 1879048192), 192);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.IconButtonKt.SurfaceIconButton.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    IconButtonKt.SurfaceIconButton(function0, modifier, z, shape, iconButtonColors, borderStroke, mutableInteractionSource, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
