package com.airbnb.lottie.compose;

import android.graphics.Matrix;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.unit.Dp;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.RenderMode;
import com.airbnb.lottie.utils.Utils;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LottieAnimationKt {
    public static final void LottieAnimation(final LottieComposition lottieComposition, final Function0 function0, Modifier modifier, boolean z, boolean z2, boolean z3, RenderMode renderMode, boolean z4, LottieDynamicProperties lottieDynamicProperties, Alignment alignment, ContentScale contentScale, boolean z5, Composer composer, final int i, final int i2, final int i3) {
        Alignment alignment2;
        ContentScale contentScale2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(185150517);
        Modifier modifier2 = (i3 & 4) != 0 ? Modifier.Companion : modifier;
        final boolean z6 = (i3 & 8) != 0 ? false : z;
        final boolean z7 = (i3 & 16) != 0 ? false : z2;
        final boolean z8 = (i3 & 32) != 0 ? false : z3;
        final RenderMode renderMode2 = (i3 & 64) != 0 ? RenderMode.AUTOMATIC : renderMode;
        final boolean z9 = (i3 & 128) != 0 ? false : z4;
        LottieDynamicProperties lottieDynamicProperties2 = (i3 & 256) != 0 ? null : lottieDynamicProperties;
        if ((i3 & 512) != 0) {
            Alignment.Companion.getClass();
            alignment2 = Alignment.Companion.Center;
        } else {
            alignment2 = alignment;
        }
        if ((i3 & 1024) != 0) {
            ContentScale.Companion.getClass();
            contentScale2 = ContentScale.Companion.Fit;
        } else {
            contentScale2 = contentScale;
        }
        boolean z10 = (i3 & 2048) != 0 ? true : z5;
        composerImpl2.startReplaceableGroup(-3687241);
        Object rememberedValue = composerImpl2.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = new LottieDrawable();
            composerImpl2.updateRememberedValue(rememberedValue);
        }
        composerImpl2.end(false);
        final LottieDrawable lottieDrawable = (LottieDrawable) rememberedValue;
        composerImpl2.startReplaceableGroup(-3687241);
        Object rememberedValue2 = composerImpl2.rememberedValue();
        if (rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new Matrix();
            composerImpl2.updateRememberedValue(rememberedValue2);
        }
        final LottieDynamicProperties lottieDynamicProperties3 = lottieDynamicProperties2;
        composerImpl2.end(false);
        final Matrix matrix = (Matrix) rememberedValue2;
        composerImpl2.startReplaceableGroup(-3687241);
        Object rememberedValue3 = composerImpl2.rememberedValue();
        if (rememberedValue3 == composer$Companion$Empty$1) {
            rememberedValue3 = SnapshotStateKt.mutableStateOf$default(null);
            composerImpl2.updateRememberedValue(rememberedValue3);
        }
        composerImpl2.end(false);
        final MutableState mutableState = (MutableState) rememberedValue3;
        composerImpl2.startReplaceableGroup(185151250);
        if (lottieComposition == null || lottieComposition.getDuration() == 0.0f) {
            final Modifier modifier3 = modifier2;
            final boolean z11 = z6;
            final Alignment alignment3 = alignment2;
            final ContentScale contentScale3 = contentScale2;
            final boolean z12 = z10;
            composerImpl2.end(false);
            RecomposeScopeImpl endRestartGroup = composerImpl2.endRestartGroup();
            if (endRestartGroup == null) {
                composerImpl = composerImpl2;
            } else {
                composerImpl = composerImpl2;
                endRestartGroup.block = new Function2() { // from class: com.airbnb.lottie.compose.LottieAnimationKt$LottieAnimation$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        LottieAnimationKt.LottieAnimation(LottieComposition.this, function0, modifier3, z11, z7, z8, renderMode2, z9, lottieDynamicProperties3, alignment3, contentScale3, z12, (Composer) obj, i | 1, i2, i3);
                        return Unit.INSTANCE;
                    }
                };
            }
            BoxKt.Box(modifier3, composerImpl, (i >> 6) & 14);
            return;
        }
        composerImpl2.end(false);
        float dpScale = Utils.dpScale();
        Dp.Companion companion = Dp.Companion;
        Modifier m140sizeVpY3zN4 = SizeKt.m140sizeVpY3zN4(modifier2, lottieComposition.bounds.width() / dpScale, lottieComposition.bounds.height() / dpScale);
        final boolean z13 = z7;
        final Modifier modifier4 = modifier2;
        final ContentScale contentScale4 = contentScale2;
        final boolean z14 = z10;
        final Alignment alignment4 = alignment2;
        final boolean z15 = z9;
        Function1 function1 = new Function1() { // from class: com.airbnb.lottie.compose.LottieAnimationKt$LottieAnimation$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code restructure failed: missing block: B:126:0x03ce, code lost:
            
                if (r1.progress != r11.animator.getAnimatedValueAbsolute()) goto L122;
             */
            /* JADX WARN: Code restructure failed: missing block: B:127:0x03d0, code lost:
            
                ((java.util.concurrent.ThreadPoolExecutor) com.airbnb.lottie.LottieDrawable.setProgressExecutor).execute(r11.updateProgressRunnable);
             */
            /* JADX WARN: Code restructure failed: missing block: B:131:0x0408, code lost:
            
                if (r1.progress != r11.animator.getAnimatedValueAbsolute()) goto L122;
             */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object mo779invoke(java.lang.Object r27) {
                /*
                    Method dump skipped, instructions count: 1038
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.compose.LottieAnimationKt$LottieAnimation$2.mo779invoke(java.lang.Object):java.lang.Object");
            }
        };
        final boolean z16 = z6;
        CanvasKt.Canvas(m140sizeVpY3zN4, function1, composerImpl2, 0);
        RecomposeScopeImpl endRestartGroup2 = composerImpl2.endRestartGroup();
        if (endRestartGroup2 == null) {
            return;
        }
        endRestartGroup2.block = new Function2() { // from class: com.airbnb.lottie.compose.LottieAnimationKt$LottieAnimation$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Number) obj2).intValue();
                LottieAnimationKt.LottieAnimation(LottieComposition.this, function0, modifier4, z16, z13, z8, renderMode2, z15, lottieDynamicProperties3, alignment4, contentScale4, z14, (Composer) obj, i | 1, i2, i3);
                return Unit.INSTANCE;
            }
        };
    }
}
