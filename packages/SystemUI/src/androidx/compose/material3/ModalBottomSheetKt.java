package androidx.compose.material3;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.TransformOriginKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.util.MathHelpersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ModalBottomSheetKt {
    public static final long PredictiveBackChildTransformOrigin;
    public static final float PredictiveBackMaxScaleXDistance;
    public static final float PredictiveBackMaxScaleYDistance;

    static {
        Dp.Companion companion = Dp.Companion;
        PredictiveBackMaxScaleXDistance = 48;
        PredictiveBackMaxScaleYDistance = 24;
        PredictiveBackChildTransformOrigin = TransformOriginKt.TransformOrigin(0.5f, 0.0f);
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x03c4, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L262;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x037d  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x03e0  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x03f7  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x041b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0431  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0443  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x044e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0462  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0499 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x04e0  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0523  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0513  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* renamed from: ModalBottomSheet-YbuCTN8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m271ModalBottomSheetYbuCTN8(final kotlin.jvm.functions.Function0 r53, androidx.compose.ui.Modifier r54, androidx.compose.material3.SheetState r55, float r56, boolean r57, androidx.compose.ui.graphics.Shape r58, long r59, long r61, float r63, long r64, kotlin.jvm.functions.Function2 r66, kotlin.jvm.functions.Function2 r67, androidx.compose.material3.ModalBottomSheetProperties r68, final kotlin.jvm.functions.Function3 r69, androidx.compose.runtime.Composer r70, final int r71, final int r72, final int r73) {
        /*
            Method dump skipped, instructions count: 1369
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt.m271ModalBottomSheetYbuCTN8(kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, androidx.compose.material3.SheetState, float, boolean, androidx.compose.ui.graphics.Shape, long, long, float, long, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, androidx.compose.material3.ModalBottomSheetProperties, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:125:0x0383, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x03dc, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L270;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0424, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L289;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0452, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L294;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0494, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L316;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0160, code lost:
    
        if (r7.changed(r55) != false) goto L117;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0409  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x041f  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x047a  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x04f0  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x040c  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x03d0  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0509  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x023f  */
    /* renamed from: ModalBottomSheetContent-7---e2Q, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m272ModalBottomSheetContent7e2Q(final androidx.compose.foundation.layout.BoxScope r45, final androidx.compose.animation.core.Animatable r46, final kotlinx.coroutines.CoroutineScope r47, final kotlin.jvm.functions.Function0 r48, final kotlin.jvm.functions.Function1 r49, androidx.compose.ui.Modifier r50, androidx.compose.material3.SheetState r51, float r52, boolean r53, androidx.compose.ui.graphics.Shape r54, long r55, long r57, float r59, kotlin.jvm.functions.Function2 r60, kotlin.jvm.functions.Function2 r61, final kotlin.jvm.functions.Function3 r62, androidx.compose.runtime.Composer r63, final int r64, final int r65, final int r66) {
        /*
            Method dump skipped, instructions count: 1318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt.m272ModalBottomSheetContent7e2Q(androidx.compose.foundation.layout.BoxScope, androidx.compose.animation.core.Animatable, kotlinx.coroutines.CoroutineScope, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.material3.SheetState, float, boolean, androidx.compose.ui.graphics.Shape, long, long, float, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00b3, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d9, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x011d, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L69;
     */
    /* renamed from: access$Scrim-3J-VO9M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m273access$Scrim3JVO9M(final long r17, final kotlin.jvm.functions.Function0 r19, final boolean r20, androidx.compose.runtime.Composer r21, final int r22) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt.m273access$Scrim3JVO9M(long, kotlin.jvm.functions.Function0, boolean, androidx.compose.runtime.Composer, int):void");
    }

    public static final float access$calculatePredictiveBackScaleX(GraphicsLayerScope graphicsLayerScope, float f) {
        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
        float m417getWidthimpl = Size.m417getWidthimpl(reusableGraphicsLayerScope.size);
        if (Float.isNaN(m417getWidthimpl) || m417getWidthimpl == 0.0f) {
            return 1.0f;
        }
        return 1.0f - (MathHelpersKt.lerp(0.0f, Math.min(reusableGraphicsLayerScope.graphicsDensity.getDensity() * PredictiveBackMaxScaleXDistance, m417getWidthimpl), f) / m417getWidthimpl);
    }

    public static final float access$calculatePredictiveBackScaleY(GraphicsLayerScope graphicsLayerScope, float f) {
        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
        float m415getHeightimpl = Size.m415getHeightimpl(reusableGraphicsLayerScope.size);
        if (Float.isNaN(m415getHeightimpl) || m415getHeightimpl == 0.0f) {
            return 1.0f;
        }
        return 1.0f - (MathHelpersKt.lerp(0.0f, Math.min(reusableGraphicsLayerScope.graphicsDensity.getDensity() * PredictiveBackMaxScaleYDistance, m415getHeightimpl), f) / m415getHeightimpl);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0046, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0067, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00b6, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.material3.SheetState rememberModalBottomSheetState(androidx.compose.runtime.Composer r15) {
        /*
            androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1 r4 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1
                static {
                    /*
                        androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1 r0 = new androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1) androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1.INSTANCE androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ java.lang.Object mo779invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        androidx.compose.material3.SheetValue r1 = (androidx.compose.material3.SheetValue) r1
                        java.lang.Boolean r0 = java.lang.Boolean.TRUE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt$rememberModalBottomSheetState$1.mo779invoke(java.lang.Object):java.lang.Object");
                }
            }
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Ld
            java.lang.String r0 = "androidx.compose.material3.rememberModalBottomSheetState (ModalBottomSheet.kt:502)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Ld:
            androidx.compose.material3.SheetValue r6 = androidx.compose.material3.SheetValue.Hidden
            float r0 = androidx.compose.material3.SheetDefaultsKt.DragHandleVerticalPadding
            androidx.compose.material3.BottomSheetDefaults r0 = androidx.compose.material3.BottomSheetDefaults.INSTANCE
            r0.getClass()
            float r0 = androidx.compose.material3.BottomSheetDefaults.PositionalThreshold
            float r1 = androidx.compose.material3.BottomSheetDefaults.VelocityThreshold
            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r2 == 0) goto L25
            java.lang.String r2 = "androidx.compose.material3.rememberSheetState (SheetDefaults.kt:495)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r2)
        L25:
            androidx.compose.runtime.StaticProvidableCompositionLocal r2 = androidx.compose.ui.platform.CompositionLocalsKt.LocalDensity
            r11 = r15
            androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
            java.lang.Object r15 = r11.consume(r2)
            androidx.compose.ui.unit.Density r15 = (androidx.compose.ui.unit.Density) r15
            boolean r2 = r11.changed(r15)
            boolean r3 = r11.changed(r0)
            r2 = r2 | r3
            java.lang.Object r3 = r11.rememberedValue()
            androidx.compose.runtime.Composer$Companion r7 = androidx.compose.runtime.Composer.Companion
            if (r2 != 0) goto L48
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L50
        L48:
            androidx.compose.material3.SheetDefaultsKt$rememberSheetState$positionalThresholdToPx$1$1 r3 = new androidx.compose.material3.SheetDefaultsKt$rememberSheetState$positionalThresholdToPx$1$1
            r3.<init>()
            r11.updateRememberedValue(r3)
        L50:
            r2 = r3
            kotlin.jvm.functions.Function0 r2 = (kotlin.jvm.functions.Function0) r2
            boolean r0 = r11.changed(r15)
            boolean r3 = r11.changed(r1)
            r0 = r0 | r3
            java.lang.Object r3 = r11.rememberedValue()
            if (r0 != 0) goto L69
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r0) goto L71
        L69:
            androidx.compose.material3.SheetDefaultsKt$rememberSheetState$velocityThresholdToPx$1$1 r3 = new androidx.compose.material3.SheetDefaultsKt$rememberSheetState$velocityThresholdToPx$1$1
            r3.<init>()
            r11.updateRememberedValue(r3)
        L71:
            kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
            r1 = 0
            java.lang.Boolean r15 = java.lang.Boolean.valueOf(r1)
            java.lang.Boolean r0 = java.lang.Boolean.FALSE
            java.lang.Object[] r15 = new java.lang.Object[]{r15, r4, r0}
            androidx.compose.material3.SheetState$Companion r0 = androidx.compose.material3.SheetState.Companion
            r0.getClass()
            androidx.compose.material3.SheetState$Companion$Saver$1 r8 = new kotlin.jvm.functions.Function2() { // from class: androidx.compose.material3.SheetState$Companion$Saver$1
                static {
                    /*
                        androidx.compose.material3.SheetState$Companion$Saver$1 r0 = new androidx.compose.material3.SheetState$Companion$Saver$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.material3.SheetState$Companion$Saver$1) androidx.compose.material3.SheetState$Companion$Saver$1.INSTANCE androidx.compose.material3.SheetState$Companion$Saver$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SheetState$Companion$Saver$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 2
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SheetState$Companion$Saver$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2) {
                    /*
                        r0 = this;
                        androidx.compose.runtime.saveable.SaverScope r1 = (androidx.compose.runtime.saveable.SaverScope) r1
                        androidx.compose.material3.SheetState r2 = (androidx.compose.material3.SheetState) r2
                        androidx.compose.material3.internal.AnchoredDraggableState r0 = r2.anchoredDraggableState
                        androidx.compose.runtime.MutableState r0 = r0.currentValue$delegate
                        androidx.compose.runtime.SnapshotMutableStateImpl r0 = (androidx.compose.runtime.SnapshotMutableStateImpl) r0
                        java.lang.Object r0 = r0.getValue()
                        androidx.compose.material3.SheetValue r0 = (androidx.compose.material3.SheetValue) r0
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SheetState$Companion$Saver$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }
            androidx.compose.material3.SheetState$Companion$Saver$2 r0 = new androidx.compose.material3.SheetState$Companion$Saver$2
            r5 = 0
            r0.<init>()
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r9 = androidx.compose.runtime.saveable.SaverKt.AutoSaver
            r9 = r8
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r8 = new androidx.compose.runtime.saveable.SaverKt$Saver$1
            r8.<init>(r9, r0)
            boolean r0 = r11.changed(r1)
            boolean r9 = r11.changed(r2)
            r0 = r0 | r9
            boolean r9 = r11.changed(r3)
            r0 = r0 | r9
            boolean r9 = r11.changed(r4)
            r0 = r0 | r9
            boolean r9 = r11.changed(r5)
            r0 = r0 | r9
            java.lang.Object r9 = r11.rememberedValue()
            if (r0 != 0) goto Lb8
            r7.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r9 != r0) goto Lc5
        Lb8:
            androidx.compose.material3.SheetDefaultsKt$rememberSheetState$2$1 r0 = new androidx.compose.material3.SheetDefaultsKt$rememberSheetState$2$1
            r14 = r5
            r5 = r4
            r4 = r6
            r6 = r14
            r0.<init>()
            r11.updateRememberedValue(r0)
            r9 = r0
        Lc5:
            r10 = r9
            kotlin.jvm.functions.Function0 r10 = (kotlin.jvm.functions.Function0) r10
            r9 = 0
            r12 = 0
            r13 = 4
            r7 = r15
            java.lang.Object r15 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r7, r8, r9, r10, r11, r12, r13)
            androidx.compose.material3.SheetState r15 = (androidx.compose.material3.SheetState) r15
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Ldb
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Ldb:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Le4
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Le4:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ModalBottomSheetKt.rememberModalBottomSheetState(androidx.compose.runtime.Composer):androidx.compose.material3.SheetState");
    }
}
