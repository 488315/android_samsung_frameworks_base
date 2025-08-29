package com.android.systemui.media.mediaoutput.compose.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import com.android.compose.ui.graphics.painter.DrawablePainter;
import com.android.systemui.media.mediaoutput.compose.ext.DrawableResourceConverterPainter;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class IconExtKt {
    /* JADX WARN: Removed duplicated region for block: B:28:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
    /* renamed from: IconExt-ww6aTOc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2633IconExtww6aTOc(final Painter painter, final String str, Modifier modifier, final long j, Composer composer, final int i, final int i2) {
        String str2;
        Modifier modifier2;
        int i3;
        Modifier modifier3;
        final Modifier modifier4;
        final Modifier modifier5;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1923818022);
        int i4 = (composerImpl.changedInstance(painter) ? 4 : 2) | i;
        if ((i & 48) == 0) {
            str2 = str;
            i4 |= composerImpl.changed(str2) ? 32 : 16;
        } else {
            str2 = str;
        }
        int i5 = i2 & 4;
        if (i5 == 0) {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i4 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            i3 = i4 | (!composerImpl.changed(j) ? 2048 : 1024);
            if ((i3 & 1171) == 1170 || !composerImpl.getSkipping()) {
                composerImpl.startDefaults();
                if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                    modifier3 = i5 == 0 ? Modifier.Companion : modifier2;
                } else {
                    composerImpl.skipToGroupEnd();
                    modifier3 = modifier2;
                }
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.IconExt (IconExt.kt:20)");
                }
                if (painter instanceof ImageVectorConverterPainter) {
                    modifier4 = modifier3;
                    if (painter instanceof DrawableResourceConverterPainter) {
                        composerImpl.startReplaceGroup(220891787);
                        Drawable drawable = ((Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext)).getDrawable(((DrawableResourceConverterPainter) painter).resId);
                        if (drawable == null) {
                            composerImpl.end(false);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup2 != null) {
                                final int i6 = 0;
                                recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.IconExtKt$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        switch (i6) {
                                            case 0:
                                                ((Integer) obj2).getClass();
                                                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                                long j2 = j;
                                                IconExtKt.m2633IconExtww6aTOc(painter, str, modifier4, j2, (Composer) obj, iUpdateChangedFlags, i2);
                                                break;
                                            default:
                                                ((Integer) obj2).getClass();
                                                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                                long j3 = j;
                                                IconExtKt.m2633IconExtww6aTOc(painter, str, modifier4, j3, (Composer) obj, iUpdateChangedFlags2, i2);
                                                break;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        IconKt.m270Iconww6aTOc(new DrawablePainter(drawable), str, modifier4, j, composerImpl, (i3 & 112) | 8 | (i3 & 896) | (i3 & 7168), 0);
                        composerImpl.end(false);
                    } else if (painter instanceof TintDrawablePainter) {
                        composerImpl.startReplaceGroup(221191092);
                        IconKt.m270Iconww6aTOc(new DrawablePainter(((TintDrawablePainter) painter).drawable), str, modifier4, j, composerImpl, (i3 & 112) | 8 | (i3 & 896) | (i3 & 7168), 0);
                        composerImpl.end(false);
                    } else {
                        composerImpl.startReplaceGroup(221432334);
                        IconKt.m270Iconww6aTOc(painter, str, modifier4, j, composerImpl, i3 & 8190, 0);
                        composerImpl.end(false);
                    }
                } else {
                    composerImpl.startReplaceGroup(220627326);
                    IconKt.m271Iconww6aTOc(((ImageVectorConverterPainter) painter).imageVector, str2, modifier3, j, composerImpl, i3 & 8176, 0);
                    modifier4 = modifier3;
                    composerImpl.end(false);
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                modifier5 = modifier4;
            } else {
                composerImpl.skipToGroupEnd();
                modifier5 = modifier2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                final int i7 = 1;
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.IconExtKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        switch (i7) {
                            case 0:
                                ((Integer) obj2).getClass();
                                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                long j2 = j;
                                IconExtKt.m2633IconExtww6aTOc(painter, str, modifier5, j2, (Composer) obj, iUpdateChangedFlags, i2);
                                break;
                            default:
                                ((Integer) obj2).getClass();
                                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                long j3 = j;
                                IconExtKt.m2633IconExtww6aTOc(painter, str, modifier5, j3, (Composer) obj, iUpdateChangedFlags2, i2);
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i4 |= 384;
        modifier2 = modifier;
        i3 = i4 | (!composerImpl.changed(j) ? 2048 : 1024);
        if ((i3 & 1171) == 1170) {
            composerImpl.startDefaults();
            if ((i & 1) != 0) {
                modifier3 = i5 == 0 ? Modifier.Companion : modifier2;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (painter instanceof ImageVectorConverterPainter) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                modifier5 = modifier4;
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
