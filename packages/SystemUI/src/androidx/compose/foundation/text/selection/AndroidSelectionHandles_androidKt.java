package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.CanvasKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ImageBitmap;
import androidx.compose.ui.graphics.ImageBitmapConfig;
import androidx.compose.ui.graphics.ImageBitmapKt;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidSelectionHandles_androidKt {
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0091, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void HandlePopup(final androidx.compose.foundation.text.selection.OffsetProvider r19, final androidx.compose.ui.Alignment r20, final kotlin.jvm.functions.Function2 r21, androidx.compose.runtime.Composer r22, final int r23) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt.HandlePopup(androidx.compose.foundation.text.selection.OffsetProvider, androidx.compose.ui.Alignment, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00dd, code lost:
    
        if ((r29 & 16) != 0) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0103, code lost:
    
        if (r22 == false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x011f, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0159, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0109, code lost:
    
        if (r22 != false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x011d, code lost:
    
        if (((r21 == androidx.compose.ui.text.style.ResolvedTextDirection.Ltr && !r22) || (r21 == androidx.compose.ui.text.style.ResolvedTextDirection.Rtl && r22)) == false) goto L103;
     */
    /* renamed from: SelectionHandle-wLIcFTc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m232SelectionHandlewLIcFTc(final androidx.compose.foundation.text.selection.OffsetProvider r19, final boolean r20, final androidx.compose.ui.text.style.ResolvedTextDirection r21, final boolean r22, long r23, final float r25, final androidx.compose.ui.Modifier r26, androidx.compose.runtime.Composer r27, final int r28, final int r29) {
        /*
            Method dump skipped, instructions count: 433
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt.m232SelectionHandlewLIcFTc(androidx.compose.foundation.text.selection.OffsetProvider, boolean, androidx.compose.ui.text.style.ResolvedTextDirection, boolean, long, float, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void SelectionHandleIcon(final int i, Composer composer, final Modifier modifier, final Function0 function0, final boolean z) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2111672474);
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
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 147) != 146)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.selection.SelectionHandleIcon (AndroidSelectionHandles.android.kt:127)");
            }
            SpacerKt.Spacer(composerImpl, ComposedModifierKt.composed(SizeKt.m140sizeVpY3zN4(modifier, SelectionHandlesKt.HandleWidth, SelectionHandlesKt.HandleHeight), InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                /* JADX WARN: Code restructure failed: missing block: B:7:0x0049, code lost:
                
                    if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r5, java.lang.Object r6, java.lang.Object r7) {
                    /*
                        r4 = this;
                        androidx.compose.ui.Modifier r5 = (androidx.compose.ui.Modifier) r5
                        androidx.compose.runtime.Composer r6 = (androidx.compose.runtime.Composer) r6
                        java.lang.Number r7 = (java.lang.Number) r7
                        r7.intValue()
                        androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
                        r7 = -196777734(0xfffffffff44568fa, float:-6.2561747E31)
                        r6.startReplaceGroup(r7)
                        boolean r7 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r7 == 0) goto L1c
                        java.lang.String r7 = "androidx.compose.foundation.text.selection.drawSelectionHandle.<anonymous> (AndroidSelectionHandles.android.kt:133)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r7)
                    L1c:
                        androidx.compose.runtime.DynamicProvidableCompositionLocal r7 = androidx.compose.foundation.text.selection.TextSelectionColorsKt.LocalTextSelectionColors
                        java.lang.Object r7 = r6.consume(r7)
                        androidx.compose.foundation.text.selection.TextSelectionColors r7 = (androidx.compose.foundation.text.selection.TextSelectionColors) r7
                        long r0 = r7.handleColor
                        boolean r7 = r6.changed(r0)
                        kotlin.jvm.functions.Function0 r2 = kotlin.jvm.functions.Function0.this
                        boolean r2 = r6.changed(r2)
                        r7 = r7 | r2
                        boolean r2 = r2
                        boolean r2 = r6.changed(r2)
                        r7 = r7 | r2
                        kotlin.jvm.functions.Function0 r2 = kotlin.jvm.functions.Function0.this
                        boolean r4 = r2
                        java.lang.Object r3 = r6.rememberedValue()
                        if (r7 != 0) goto L4b
                        androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
                        r7.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r7 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r3 != r7) goto L53
                    L4b:
                        androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1$1$1 r3 = new androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1$1$1
                        r3.<init>()
                        r6.updateRememberedValue(r3)
                    L53:
                        kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
                        androidx.compose.ui.Modifier r4 = androidx.compose.ui.draw.DrawModifierKt.drawWithCache(r5, r3)
                        boolean r5 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r5 == 0) goto L62
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L62:
                        r5 = 0
                        r6.end(r5)
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$drawSelectionHandle$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.selection.AndroidSelectionHandles_androidKt$SelectionHandleIcon$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    Modifier modifier2 = Modifier.this;
                    Function0 function02 = function0;
                    boolean z2 = z;
                    AndroidSelectionHandles_androidKt.SelectionHandleIcon(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj, modifier2, function02, z2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final ImageBitmap createHandleImage(CacheDrawScope cacheDrawScope, float f) {
        int ceil = ((int) Math.ceil(f)) * 2;
        HandleImageCache.INSTANCE.getClass();
        AndroidImageBitmap androidImageBitmap = HandleImageCache.imageBitmap;
        AndroidCanvas androidCanvas = HandleImageCache.canvas;
        CanvasDrawScope canvasDrawScope = HandleImageCache.canvasDrawScope;
        if (androidImageBitmap == null || androidCanvas == null || ceil > androidImageBitmap.bitmap.getWidth() || ceil > androidImageBitmap.bitmap.getHeight()) {
            ImageBitmapConfig.Companion.getClass();
            androidImageBitmap = ImageBitmapKt.m479ImageBitmapx__hDU$default(ceil, ceil, ImageBitmapConfig.Alpha8);
            HandleImageCache.imageBitmap = androidImageBitmap;
            androidCanvas = CanvasKt.Canvas(androidImageBitmap);
            HandleImageCache.canvas = androidCanvas;
        }
        AndroidImageBitmap androidImageBitmap2 = androidImageBitmap;
        AndroidCanvas androidCanvas2 = androidCanvas;
        if (canvasDrawScope == null) {
            canvasDrawScope = new CanvasDrawScope();
            HandleImageCache.canvasDrawScope = canvasDrawScope;
        }
        CanvasDrawScope canvasDrawScope2 = canvasDrawScope;
        LayoutDirection layoutDirection = cacheDrawScope.cacheParams.getLayoutDirection();
        float width = androidImageBitmap2.bitmap.getWidth();
        float height = androidImageBitmap2.bitmap.getHeight();
        Size.Companion companion = Size.Companion;
        CanvasDrawScope.DrawParams drawParams = canvasDrawScope2.drawParams;
        Density density = drawParams.density;
        LayoutDirection layoutDirection2 = drawParams.layoutDirection;
        Canvas canvas = drawParams.canvas;
        long j = drawParams.size;
        drawParams.density = cacheDrawScope;
        drawParams.layoutDirection = layoutDirection;
        drawParams.canvas = androidCanvas2;
        drawParams.size = (Float.floatToRawIntBits(width) << 32) | (Float.floatToRawIntBits(height) & 4294967295L);
        androidCanvas2.save();
        Color.Companion.getClass();
        long j2 = Color.Black;
        long mo545getSizeNHjbRc = canvasDrawScope2.mo545getSizeNHjbRc();
        BlendMode.Companion.getClass();
        DrawScope.m539drawRectnJ9OG0$default(canvasDrawScope2, j2, 0L, mo545getSizeNHjbRc, 0.0f, null, null, 0, 58);
        long Color = ColorKt.Color(4278190080L);
        Offset.Companion.getClass();
        DrawScope.m539drawRectnJ9OG0$default(canvasDrawScope2, Color, 0L, (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L), 0.0f, null, null, 0, 120);
        DrawScope.m532drawCircleVaOC9Bg$default(canvasDrawScope2, ColorKt.Color(4278190080L), f, (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L), 0.0f, null, 0, 120);
        androidCanvas2.restore();
        drawParams.density = density;
        drawParams.layoutDirection = layoutDirection2;
        drawParams.canvas = canvas;
        drawParams.size = j;
        return androidImageBitmap2;
    }
}
