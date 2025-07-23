package androidx.compose.foundation;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.VectorPainterKt;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.ContentScale$Companion$Fit$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003d, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L13;
     */
    /* renamed from: Image-5h-nEew, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m41Image5hnEew(androidx.compose.ui.graphics.ImageBitmap r13, java.lang.String r14, androidx.compose.ui.Modifier r15, androidx.compose.ui.layout.ContentScale$Companion$Crop$1 r16, androidx.compose.runtime.Composer r17, int r18, int r19) {
        /*
            androidx.compose.ui.Alignment$Companion r0 = androidx.compose.ui.Alignment.Companion
            r0.getClass()
            androidx.compose.ui.BiasAlignment r7 = androidx.compose.ui.Alignment.Companion.Center
            r0 = r19 & 16
            if (r0 == 0) goto L14
            androidx.compose.ui.layout.ContentScale$Companion r0 = androidx.compose.ui.layout.ContentScale.Companion
            r0.getClass()
            androidx.compose.ui.layout.ContentScale$Companion$Fit$1 r0 = androidx.compose.ui.layout.ContentScale.Companion.Fit
            r8 = r0
            goto L16
        L14:
            r8 = r16
        L16:
            androidx.compose.ui.graphics.drawscope.DrawScope$Companion r0 = androidx.compose.ui.graphics.drawscope.DrawScope.Companion
            r0.getClass()
            int r9 = androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L28
            java.lang.String r0 = "androidx.compose.foundation.Image (Image.kt:156)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L28:
            r10 = r17
            androidx.compose.runtime.ComposerImpl r10 = (androidx.compose.runtime.ComposerImpl) r10
            boolean r0 = r10.changed(r13)
            java.lang.Object r2 = r10.rememberedValue()
            if (r0 != 0) goto L3f
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r0) goto L70
        L3f:
            androidx.compose.ui.unit.IntOffset$Companion r0 = androidx.compose.ui.unit.IntOffset.Companion
            r0.getClass()
            r0 = r13
            androidx.compose.ui.graphics.AndroidImageBitmap r0 = (androidx.compose.ui.graphics.AndroidImageBitmap) r0
            android.graphics.Bitmap r2 = r0.bitmap
            int r2 = r2.getWidth()
            android.graphics.Bitmap r0 = r0.bitmap
            int r0 = r0.getHeight()
            long r2 = (long) r2
            r4 = 32
            long r2 = r2 << r4
            long r4 = (long) r0
            r11 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r4 = r4 & r11
            long r4 = r4 | r2
            androidx.compose.ui.unit.IntSize$Companion r0 = androidx.compose.ui.unit.IntSize.Companion
            androidx.compose.ui.graphics.painter.BitmapPainter r0 = new androidx.compose.ui.graphics.painter.BitmapPainter
            r6 = 0
            r2 = 0
            r1 = r13
            r0.<init>(r1, r2, r4, r6)
            r0.filterQuality = r9
            r10.updateRememberedValue(r0)
            r2 = r0
        L70:
            r1 = r2
            androidx.compose.ui.graphics.painter.BitmapPainter r1 = (androidx.compose.ui.graphics.painter.BitmapPainter) r1
            r0 = 4194288(0x3ffff0, float:5.87745E-39)
            r9 = r18 & r0
            r5 = r8
            r8 = r10
            r10 = 0
            r6 = 1065353216(0x3f800000, float:1.0)
            r4 = r7
            r7 = 0
            r2 = r14
            r3 = r15
            Image(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L8d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L8d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.ImageKt.m41Image5hnEew(androidx.compose.ui.graphics.ImageBitmap, java.lang.String, androidx.compose.ui.Modifier, androidx.compose.ui.layout.ContentScale$Companion$Crop$1, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x013e, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L111;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Image(final androidx.compose.ui.graphics.painter.Painter r19, final java.lang.String r20, androidx.compose.ui.Modifier r21, androidx.compose.ui.Alignment r22, androidx.compose.ui.layout.ContentScale r23, float r24, androidx.compose.ui.graphics.ColorFilter r25, androidx.compose.runtime.Composer r26, final int r27, final int r28) {
        /*
            Method dump skipped, instructions count: 490
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.ImageKt.Image(androidx.compose.ui.graphics.painter.Painter, java.lang.String, androidx.compose.ui.Modifier, androidx.compose.ui.Alignment, androidx.compose.ui.layout.ContentScale, float, androidx.compose.ui.graphics.ColorFilter, androidx.compose.runtime.Composer, int, int):void");
    }
}
