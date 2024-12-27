package com.android.systemui.media.mediaoutput.compose.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.compose.foundation.ImageKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ImageExtKt {
    public static final void ImageExt(final Painter painter, final String str, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2005580573);
        Modifier modifier2 = (i2 & 4) != 0 ? Modifier.Companion : modifier;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (painter instanceof ImageVectorConverterPainter) {
            composerImpl.startReplaceGroup(-2089589954);
            ImageKt.Image(((ImageVectorConverterPainter) painter).imageVector, str, modifier2, composerImpl, i & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS);
            composerImpl.end(false);
        } else if (painter instanceof DrawableResourceConverterPainter) {
            composerImpl.startReplaceGroup(-2089589716);
            Drawable drawable = ((Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext)).getDrawable(((DrawableResourceConverterPainter) painter).resId);
            if (drawable == null) {
                composerImpl.end(false);
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    final Modifier modifier3 = modifier2;
                    endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ImageExtKt$ImageExt$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ImageExtKt.ImageExt(Painter.this, str, modifier3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            ImageKt.Image(new DrawablePainter(drawable), str, modifier2, null, null, 0.0f, null, composerImpl, 8 | (i & 112) | (i & 896), 120);
            composerImpl.end(false);
        } else if (painter instanceof TintDrawablePainter) {
            composerImpl.startReplaceGroup(-2089589440);
            ImageKt.Image(new DrawablePainter(((TintDrawablePainter) painter).drawable), str, modifier2, null, null, 0.0f, null, composerImpl, 8 | (i & 112) | (i & 896), 120);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-2089589223);
            ImageKt.Image(painter, str, modifier2, null, null, 0.0f, null, composerImpl, (i & 112) | 8 | (i & 896), 120);
            composerImpl.end(false);
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            final Modifier modifier4 = modifier2;
            endRestartGroup2.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ImageExtKt$ImageExt$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ImageExtKt.ImageExt(Painter.this, str, modifier4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
