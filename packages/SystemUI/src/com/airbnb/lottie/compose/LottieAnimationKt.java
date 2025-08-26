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
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.ScaleFactor;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSizeKt;
import com.airbnb.lottie.AsyncUpdates;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.RenderMode;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.concurrent.ThreadPoolExecutor;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.math.MathKt__MathJVMKt;

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
        Object objRememberedValue = composerImpl2.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = new LottieDrawable();
            composerImpl2.updateRememberedValue(objRememberedValue);
        }
        composerImpl2.end(false);
        final LottieDrawable lottieDrawable = (LottieDrawable) objRememberedValue;
        composerImpl2.startReplaceableGroup(-3687241);
        Object objRememberedValue2 = composerImpl2.rememberedValue();
        if (objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new Matrix();
            composerImpl2.updateRememberedValue(objRememberedValue2);
        }
        final LottieDynamicProperties lottieDynamicProperties3 = lottieDynamicProperties2;
        composerImpl2.end(false);
        final Matrix matrix = (Matrix) objRememberedValue2;
        composerImpl2.startReplaceableGroup(-3687241);
        Object objRememberedValue3 = composerImpl2.rememberedValue();
        if (objRememberedValue3 == composer$Companion$Empty$1) {
            objRememberedValue3 = SnapshotStateKt.mutableStateOf$default(null);
            composerImpl2.updateRememberedValue(objRememberedValue3);
        }
        composerImpl2.end(false);
        final MutableState mutableState = (MutableState) objRememberedValue3;
        composerImpl2.startReplaceableGroup(185151250);
        if (lottieComposition == null || lottieComposition.getDuration() == 0.0f) {
            final Modifier modifier3 = modifier2;
            final boolean z11 = z6;
            final Alignment alignment3 = alignment2;
            final ContentScale contentScale3 = contentScale2;
            final boolean z12 = z10;
            composerImpl2.end(false);
            RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                composerImpl = composerImpl2;
            } else {
                composerImpl = composerImpl2;
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.airbnb.lottie.compose.LottieAnimationKt.LottieAnimation.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        LottieAnimationKt.LottieAnimation(lottieComposition, function0, modifier3, z11, z7, z8, renderMode2, z9, lottieDynamicProperties3, alignment3, contentScale3, z12, (Composer) obj, i | 1, i2, i3);
                        return Unit.INSTANCE;
                    }
                };
            }
            BoxKt.Box(modifier3, composerImpl, (i >> 6) & 14);
            return;
        }
        composerImpl2.end(false);
        float fDpScale = Utils.dpScale();
        Dp.Companion companion = Dp.Companion;
        Modifier modifierM141sizeVpY3zN4 = SizeKt.m141sizeVpY3zN4(modifier2, lottieComposition.bounds.width() / fDpScale, lottieComposition.bounds.height() / fDpScale);
        final boolean z13 = z7;
        final Modifier modifier4 = modifier2;
        final ContentScale contentScale4 = contentScale2;
        final boolean z14 = z10;
        final Alignment alignment4 = alignment2;
        final boolean z15 = z9;
        Function1 function1 = new Function1() { // from class: com.airbnb.lottie.compose.LottieAnimationKt.LottieAnimation.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DrawScope drawScope = (DrawScope) obj;
                LottieComposition lottieComposition2 = lottieComposition;
                ContentScale contentScale5 = contentScale4;
                Alignment alignment5 = alignment4;
                Matrix matrix2 = matrix;
                LottieDrawable lottieDrawable2 = lottieDrawable;
                boolean z16 = z8;
                RenderMode renderMode3 = renderMode2;
                LottieDynamicProperties lottieDynamicProperties4 = lottieDynamicProperties3;
                boolean z17 = z6;
                boolean z18 = z13;
                boolean z19 = z15;
                boolean z20 = z14;
                Function0 function02 = function0;
                MutableState<LottieDynamicProperties> mutableState2 = mutableState;
                Canvas canvas = drawScope.getDrawContext().getCanvas();
                long jSize = androidx.compose.ui.geometry.SizeKt.Size(lottieComposition2.bounds.width(), lottieComposition2.bounds.height());
                long jIntSize = IntSizeKt.IntSize(MathKt__MathJVMKt.roundToInt(Size.m419getWidthimpl(drawScope.mo547getSizeNHjbRc())), MathKt__MathJVMKt.roundToInt(Size.m417getHeightimpl(drawScope.mo547getSizeNHjbRc())));
                long jMo608computeScaleFactorH7hwNQA = contentScale5.mo608computeScaleFactorH7hwNQA(jSize, drawScope.mo547getSizeNHjbRc());
                float fM419getWidthimpl = Size.m419getWidthimpl(jSize);
                int i4 = ScaleFactor.$r8$clinit;
                int i5 = (int) (jMo608computeScaleFactorH7hwNQA >> 32);
                int i6 = (int) (jMo608computeScaleFactorH7hwNQA & 4294967295L);
                long jMo353alignKFBX0sM = alignment5.mo353alignKFBX0sM(IntSizeKt.IntSize((int) (Float.intBitsToFloat(i5) * fM419getWidthimpl), (int) (Float.intBitsToFloat(i6) * Size.m417getHeightimpl(jSize))), jIntSize, drawScope.getLayoutDirection());
                matrix2.reset();
                IntOffset.Companion companion2 = IntOffset.Companion;
                matrix2.preTranslate((int) (jMo353alignKFBX0sM >> 32), (int) (jMo353alignKFBX0sM & 4294967295L));
                matrix2.preScale(Float.intBitsToFloat(i5), Float.intBitsToFloat(i6));
                if (lottieDrawable2.enableMergePaths != z16) {
                    lottieDrawable2.enableMergePaths = z16;
                    if (lottieDrawable2.composition != null) {
                        lottieDrawable2.buildCompositionLayer();
                    }
                }
                lottieDrawable2.renderMode = renderMode3;
                lottieDrawable2.computeRenderMode();
                lottieDrawable2.setComposition(lottieComposition2);
                if (lottieDynamicProperties4 != ((LottieDynamicProperties) mutableState2.getValue())) {
                    LottieDynamicProperties lottieDynamicProperties5 = (LottieDynamicProperties) mutableState2.getValue();
                    if (lottieDynamicProperties5 != null) {
                        for (LottieDynamicProperty lottieDynamicProperty : lottieDynamicProperties5.intProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty.keyPath, lottieDynamicProperty.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty2 : lottieDynamicProperties5.pointFProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty2.keyPath, lottieDynamicProperty2.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty3 : lottieDynamicProperties5.floatProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty3.keyPath, lottieDynamicProperty3.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty4 : lottieDynamicProperties5.scaleProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty4.keyPath, lottieDynamicProperty4.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty5 : lottieDynamicProperties5.colorFilterProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty5.keyPath, lottieDynamicProperty5.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty6 : lottieDynamicProperties5.intArrayProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty6.keyPath, lottieDynamicProperty6.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty7 : lottieDynamicProperties5.typefaceProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty7.keyPath, lottieDynamicProperty7.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty8 : lottieDynamicProperties5.bitmapProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty8.keyPath, lottieDynamicProperty8.property, null);
                        }
                        for (LottieDynamicProperty lottieDynamicProperty9 : lottieDynamicProperties5.charSequenceProperties) {
                            lottieDrawable2.addValueCallback(lottieDynamicProperty9.keyPath, lottieDynamicProperty9.property, null);
                        }
                    }
                    if (lottieDynamicProperties4 != null) {
                        for (LottieDynamicProperty lottieDynamicProperty10 : lottieDynamicProperties4.intProperties) {
                            KeyPath keyPath = lottieDynamicProperty10.keyPath;
                            final Function1 function12 = lottieDynamicProperty10.callback;
                            lottieDrawable2.addValueCallback(keyPath, lottieDynamicProperty10.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function12.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty11 : lottieDynamicProperties4.pointFProperties) {
                            KeyPath keyPath2 = lottieDynamicProperty11.keyPath;
                            final Function1 function13 = lottieDynamicProperty11.callback;
                            lottieDrawable2.addValueCallback(keyPath2, lottieDynamicProperty11.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function13.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty12 : lottieDynamicProperties4.floatProperties) {
                            KeyPath keyPath3 = lottieDynamicProperty12.keyPath;
                            final Function1 function14 = lottieDynamicProperty12.callback;
                            lottieDrawable2.addValueCallback(keyPath3, lottieDynamicProperty12.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function14.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty13 : lottieDynamicProperties4.scaleProperties) {
                            KeyPath keyPath4 = lottieDynamicProperty13.keyPath;
                            final Function1 function15 = lottieDynamicProperty13.callback;
                            lottieDrawable2.addValueCallback(keyPath4, lottieDynamicProperty13.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function15.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty14 : lottieDynamicProperties4.colorFilterProperties) {
                            KeyPath keyPath5 = lottieDynamicProperty14.keyPath;
                            final Function1 function16 = lottieDynamicProperty14.callback;
                            lottieDrawable2.addValueCallback(keyPath5, lottieDynamicProperty14.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function16.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty15 : lottieDynamicProperties4.intArrayProperties) {
                            KeyPath keyPath6 = lottieDynamicProperty15.keyPath;
                            final Function1 function17 = lottieDynamicProperty15.callback;
                            lottieDrawable2.addValueCallback(keyPath6, lottieDynamicProperty15.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function17.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty16 : lottieDynamicProperties4.typefaceProperties) {
                            KeyPath keyPath7 = lottieDynamicProperty16.keyPath;
                            final Function1 function18 = lottieDynamicProperty16.callback;
                            lottieDrawable2.addValueCallback(keyPath7, lottieDynamicProperty16.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function18.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty17 : lottieDynamicProperties4.bitmapProperties) {
                            KeyPath keyPath8 = lottieDynamicProperty17.keyPath;
                            final Function1 function19 = lottieDynamicProperty17.callback;
                            lottieDrawable2.addValueCallback(keyPath8, lottieDynamicProperty17.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function19.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                        for (LottieDynamicProperty lottieDynamicProperty18 : lottieDynamicProperties4.charSequenceProperties) {
                            KeyPath keyPath9 = lottieDynamicProperty18.keyPath;
                            final Function1 function110 = lottieDynamicProperty18.callback;
                            lottieDrawable2.addValueCallback(keyPath9, lottieDynamicProperty18.property, new LottieValueCallback() { // from class: com.airbnb.lottie.compose.LottieDynamicPropertiesKt$toValueCallback$1
                                @Override // com.airbnb.lottie.value.LottieValueCallback
                                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                                    return function110.mo781invoke(lottieFrameInfo);
                                }
                            });
                        }
                    }
                    mutableState2.setValue(lottieDynamicProperties4);
                }
                if (lottieDrawable2.outlineMasksAndMattes != z17) {
                    lottieDrawable2.outlineMasksAndMattes = z17;
                    CompositionLayer compositionLayer = lottieDrawable2.compositionLayer;
                    if (compositionLayer != null) {
                        compositionLayer.setOutlineMasksAndMattes(z17);
                    }
                }
                lottieDrawable2.isApplyingOpacityToLayersEnabled = z18;
                lottieDrawable2.maintainOriginalImageBounds = z19;
                if (z20 != lottieDrawable2.clipToCompositionBounds) {
                    lottieDrawable2.clipToCompositionBounds = z20;
                    CompositionLayer compositionLayer2 = lottieDrawable2.compositionLayer;
                    if (compositionLayer2 != null) {
                        compositionLayer2.clipToCompositionBounds = z20;
                    }
                    lottieDrawable2.invalidateSelf();
                }
                lottieDrawable2.setProgress(((Number) function02.invoke()).floatValue());
                lottieDrawable2.setBounds(0, 0, lottieComposition2.bounds.width(), lottieComposition2.bounds.height());
                android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
                android.graphics.Canvas canvas3 = ((AndroidCanvas) canvas).internalCanvas;
                CompositionLayer compositionLayer3 = lottieDrawable2.compositionLayer;
                LottieComposition lottieComposition3 = lottieDrawable2.composition;
                if (compositionLayer3 != null && lottieComposition3 != null) {
                    boolean z21 = lottieDrawable2.asyncUpdates == AsyncUpdates.ENABLED;
                    if (z21) {
                        try {
                            lottieDrawable2.setProgressDrawLock.acquire();
                            if (lottieDrawable2.shouldSetProgressBeforeDrawing()) {
                                lottieDrawable2.setProgress(lottieDrawable2.animator.getAnimatedValueAbsolute());
                            }
                        } catch (InterruptedException unused) {
                            if (z21) {
                                lottieDrawable2.setProgressDrawLock.release();
                                if (compositionLayer3.progress != lottieDrawable2.animator.getAnimatedValueAbsolute()) {
                                }
                            }
                        } catch (Throwable th) {
                            if (z21) {
                                lottieDrawable2.setProgressDrawLock.release();
                                if (compositionLayer3.progress != lottieDrawable2.animator.getAnimatedValueAbsolute()) {
                                    ((ThreadPoolExecutor) LottieDrawable.setProgressExecutor).execute(lottieDrawable2.updateProgressRunnable);
                                }
                            }
                            throw th;
                        }
                    }
                    if (lottieDrawable2.useSoftwareRendering) {
                        canvas3.save();
                        canvas3.concat(matrix2);
                        lottieDrawable2.renderAndDrawAsBitmap(canvas3, compositionLayer3);
                        canvas3.restore();
                    } else {
                        compositionLayer3.draw(canvas3, matrix2, lottieDrawable2.alpha);
                    }
                    lottieDrawable2.isDirty = false;
                    if (z21) {
                        lottieDrawable2.setProgressDrawLock.release();
                        if (compositionLayer3.progress != lottieDrawable2.animator.getAnimatedValueAbsolute()) {
                            ((ThreadPoolExecutor) LottieDrawable.setProgressExecutor).execute(lottieDrawable2.updateProgressRunnable);
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        };
        final boolean z16 = z6;
        CanvasKt.Canvas(modifierM141sizeVpY3zN4, function1, composerImpl2, 0);
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup2 = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup2 == null) {
            return;
        }
        recomposeScopeImplEndRestartGroup2.block = new Function2() { // from class: com.airbnb.lottie.compose.LottieAnimationKt.LottieAnimation.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Number) obj2).intValue();
                LottieAnimationKt.LottieAnimation(lottieComposition, function0, modifier4, z16, z13, z8, renderMode2, z15, lottieDynamicProperties3, alignment4, contentScale4, z14, (Composer) obj, i | 1, i2, i3);
                return Unit.INSTANCE;
            }
        };
    }
}
