package com.android.systemui.media.mediaoutput.compose.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.compose.foundation.ImageKt;
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
import com.samsung.android.knox.EnterpriseContainerCallback;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ImageExtKt {
    public static final void ImageExt(final Painter painter, final String str, final Modifier modifier, Composer composer, final int i) {
        int i2;
        Modifier modifier2;
        int i3;
        String str2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2005580573);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(painter) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(str) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            str2 = str;
            modifier2 = modifier;
            i3 = i;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.ImageExt (ImageExt.kt:17)");
            }
            if (painter instanceof ImageVectorConverterPainter) {
                composerImpl.startReplaceGroup(1948765507);
                ImageKt.Image(((ImageVectorConverterPainter) painter).imageVector, str, modifier, composerImpl, i2 & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS);
                composerImpl.end(false);
                str2 = str;
                modifier2 = modifier;
                i3 = i;
            } else if (painter instanceof DrawableResourceConverterPainter) {
                composerImpl.startReplaceGroup(1949003184);
                Drawable drawable = ((Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext)).getDrawable(((DrawableResourceConverterPainter) painter).resId);
                if (drawable == null) {
                    composerImpl.end(false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                    if (endRestartGroup != null) {
                        final int i4 = 0;
                        endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ImageExtKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                int i5 = i4;
                                Composer composer2 = (Composer) obj;
                                ((Integer) obj2).getClass();
                                switch (i5) {
                                    case 0:
                                        int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                        ImageExtKt.ImageExt(painter, str, modifier, composer2, updateChangedFlags);
                                        break;
                                    default:
                                        int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                                        ImageExtKt.ImageExt(painter, str, modifier, composer2, updateChangedFlags2);
                                        break;
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 = i;
                modifier2 = modifier;
                ImageKt.Image(new DrawablePainter(drawable), str, modifier2, null, null, 0.0f, null, composerImpl, (i2 & 112) | 8 | (i2 & 896), 120);
                composerImpl.end(false);
                str2 = str;
            } else {
                modifier2 = modifier;
                i3 = i;
                if (painter instanceof TintDrawablePainter) {
                    composerImpl.startReplaceGroup(1949275705);
                    str2 = str;
                    ImageKt.Image(new DrawablePainter(((TintDrawablePainter) painter).drawable), str2, modifier2, null, null, 0.0f, null, composerImpl, (i2 & 112) | 8 | (i2 & 896), 120);
                    composerImpl.end(false);
                } else {
                    str2 = str;
                    composerImpl.startReplaceGroup(1949490163);
                    ImageKt.Image(painter, str2, modifier2, null, null, 0.0f, null, composerImpl, i2 & 1022, 120);
                    composerImpl.end(false);
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final String str3 = str2;
            final int i5 = 1;
            final int i6 = i3;
            final Modifier modifier3 = modifier2;
            endRestartGroup2.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ImageExtKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    int i52 = i5;
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    switch (i52) {
                        case 0:
                            int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i6 | 1);
                            ImageExtKt.ImageExt(painter, str3, modifier3, composer2, updateChangedFlags);
                            break;
                        default:
                            int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i6 | 1);
                            ImageExtKt.ImageExt(painter, str3, modifier3, composer2, updateChangedFlags2);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
