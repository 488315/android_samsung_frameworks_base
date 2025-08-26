package androidx.compose.foundation;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.PainterModifierKt;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.BitmapPainter;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.ContentScale$Companion$Crop$1;
import androidx.compose.ui.layout.ContentScale$Companion$Fit$1;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ImageKt {
    public static final void Image(ImageVector imageVector, String str, Modifier modifier, Composer composer, int i) {
        Alignment.Companion.getClass();
        BiasAlignment biasAlignment = Alignment.Companion.Center;
        ContentScale.Companion.getClass();
        ContentScale$Companion$Fit$1 contentScale$Companion$Fit$1 = ContentScale.Companion.Fit;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.Image (Image.kt:202)");
        }
        Image(VectorPainterKt.rememberVectorPainter(imageVector, composer), str, modifier, biasAlignment, contentScale$Companion$Fit$1, 1.0f, null, composer, (i & 112) | 8 | (i & 896) | (i & 7168) | (57344 & i) | (458752 & i) | (i & 3670016), 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003f  */
    /* renamed from: Image-5h-nEew, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m42Image5hnEew(ImageBitmap imageBitmap, String str, Modifier modifier, ContentScale$Companion$Crop$1 contentScale$Companion$Crop$1, Composer composer, int i, int i2) {
        ContentScale contentScale;
        Alignment.Companion.getClass();
        BiasAlignment biasAlignment = Alignment.Companion.Center;
        if ((i2 & 16) != 0) {
            ContentScale.Companion.getClass();
            contentScale = ContentScale.Companion.Fit;
        } else {
            contentScale = contentScale$Companion$Crop$1;
        }
        DrawScope.Companion.getClass();
        int i3 = DrawScope.Companion.DefaultFilterQuality;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.Image (Image.kt:156)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        boolean zChanged = composerImpl.changed(imageBitmap);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                IntOffset.Companion.getClass();
                int width = ((AndroidImageBitmap) imageBitmap).bitmap.getWidth();
                long height = r0.bitmap.getHeight() & 4294967295L;
                IntSize.Companion companion = IntSize.Companion;
                BitmapPainter bitmapPainter = new BitmapPainter(imageBitmap, 0L, height | (width << 32), null);
                bitmapPainter.filterQuality = i3;
                composerImpl.updateRememberedValue(bitmapPainter);
                objRememberedValue = bitmapPainter;
            }
        }
        Image((BitmapPainter) objRememberedValue, str, modifier, biasAlignment, contentScale, 1.0f, null, composerImpl, i & 4194288, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:137:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Image(final Painter painter, final String str, Modifier modifier, Alignment alignment, ContentScale contentScale, float f, ColorFilter colorFilter, Composer composer, final int i, final int i2) {
        Painter painter2;
        int i3;
        Modifier modifier2;
        int i4;
        Alignment alignment2;
        int i5;
        int i6;
        float f2;
        int i7;
        int i8;
        final Modifier modifier3;
        final Alignment alignment3;
        final ContentScale contentScale2;
        final ColorFilter colorFilter2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Alignment alignment4;
        int i9;
        ContentScale contentScale3;
        Modifier modifierSemantics;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1142754848);
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
        int i10 = i2 & 4;
        if (i10 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            i4 = i2 & 8;
            if (i4 == 0) {
                i3 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    alignment2 = alignment;
                    i3 |= composerImpl.changed(alignment2) ? 2048 : 1024;
                }
                i5 = i2 & 16;
                if (i5 != 0) {
                    i3 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        i3 |= composerImpl.changed(contentScale) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i6 = i2 & 32;
                    if (i6 == 0) {
                        i3 |= 196608;
                    } else {
                        if ((196608 & i) == 0) {
                            f2 = f;
                            i3 |= composerImpl.changed(f2) ? 131072 : 65536;
                        }
                        i7 = i2 & 64;
                        if (i7 == 0) {
                            if ((1572864 & i) == 0) {
                                i3 |= composerImpl.changed(colorFilter) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                            i8 = i3;
                            if (!composerImpl.shouldExecute(i8 & 1, (i3 & 599187) == 599186)) {
                                Modifier modifier4 = i10 != 0 ? Modifier.Companion : modifier2;
                                if (i4 != 0) {
                                    Alignment.Companion.getClass();
                                    alignment4 = Alignment.Companion.Center;
                                } else {
                                    alignment4 = alignment2;
                                }
                                if (i5 != 0) {
                                    ContentScale.Companion.getClass();
                                    contentScale3 = ContentScale.Companion.Fit;
                                    i9 = i6;
                                } else {
                                    i9 = i6;
                                    contentScale3 = contentScale;
                                }
                                if (i9 != 0) {
                                    f2 = 1.0f;
                                }
                                ColorFilter colorFilter3 = i7 != 0 ? null : colorFilter;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.Image (Image.kt:247)");
                                }
                                if (str != null) {
                                    composerImpl.startReplaceGroup(1040425059);
                                    Modifier.Companion companion = Modifier.Companion;
                                    boolean z = (i8 & 112) == 32;
                                    Object objRememberedValue = composerImpl.rememberedValue();
                                    if (!z) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new Function1() { // from class: androidx.compose.foundation.ImageKt$Image$semantics$1$1
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
                                            composerImpl.updateRememberedValue(objRememberedValue);
                                        }
                                        modifierSemantics = SemanticsModifierKt.semantics(companion, false, (Function1) objRememberedValue);
                                        composerImpl.end(false);
                                    }
                                } else {
                                    composerImpl.startReplaceGroup(1040583841);
                                    composerImpl.end(false);
                                    modifierSemantics = Modifier.Companion;
                                }
                                Modifier modifierPaint$default = PainterModifierKt.paint$default(ClipKt.clipToBounds(modifier4.then(modifierSemantics)), painter2, alignment4, contentScale3, f2, colorFilter3, 2);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierPaint$default);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl.applier != null) {
                                    composerImpl.startReusableNode();
                                    if (composerImpl.inserting) {
                                        composerImpl.createNode(function0);
                                    } else {
                                        composerImpl.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl, new MeasurePolicy() { // from class: androidx.compose.foundation.ImageKt.Image.1
                                        @Override // androidx.compose.ui.layout.MeasurePolicy
                                        /* renamed from: measure-3p2s80s */
                                        public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                                            return measureScope.layout$1(Constraints.m825getMinWidthimpl(j), Constraints.m824getMinHeightimpl(j), MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.ImageKt.Image.1.1
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                                                    return Unit.INSTANCE;
                                                }
                                            });
                                        }
                                    }, ComposeUiNode.Companion.SetMeasurePolicy);
                                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                                    }
                                    composerImpl.end(true);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    modifier3 = modifier4;
                                    alignment3 = alignment4;
                                    contentScale2 = contentScale3;
                                    colorFilter2 = colorFilter3;
                                } else {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                            } else {
                                composerImpl.skipToGroupEnd();
                                modifier3 = modifier2;
                                alignment3 = alignment2;
                                contentScale2 = contentScale;
                                colorFilter2 = colorFilter;
                            }
                            final float f3 = f2;
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.ImageKt.Image.2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        ImageKt.Image(painter, str, modifier3, alignment3, contentScale2, f3, colorFilter2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 1572864;
                        i8 = i3;
                        if (!composerImpl.shouldExecute(i8 & 1, (i3 & 599187) == 599186)) {
                        }
                        final float f32 = f2;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    f2 = f;
                    i7 = i2 & 64;
                    if (i7 == 0) {
                    }
                    i8 = i3;
                    if (!composerImpl.shouldExecute(i8 & 1, (i3 & 599187) == 599186)) {
                    }
                    final float f322 = f2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                i6 = i2 & 32;
                if (i6 == 0) {
                }
                f2 = f;
                i7 = i2 & 64;
                if (i7 == 0) {
                }
                i8 = i3;
                if (!composerImpl.shouldExecute(i8 & 1, (i3 & 599187) == 599186)) {
                }
                final float f3222 = f2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            alignment2 = alignment;
            i5 = i2 & 16;
            if (i5 != 0) {
            }
            i6 = i2 & 32;
            if (i6 == 0) {
            }
            f2 = f;
            i7 = i2 & 64;
            if (i7 == 0) {
            }
            i8 = i3;
            if (!composerImpl.shouldExecute(i8 & 1, (i3 & 599187) == 599186)) {
            }
            final float f32222 = f2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 8;
        if (i4 == 0) {
        }
        alignment2 = alignment;
        i5 = i2 & 16;
        if (i5 != 0) {
        }
        i6 = i2 & 32;
        if (i6 == 0) {
        }
        f2 = f;
        i7 = i2 & 64;
        if (i7 == 0) {
        }
        i8 = i3;
        if (!composerImpl.shouldExecute(i8 & 1, (i3 & 599187) == 599186)) {
        }
        final float f322222 = f2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
