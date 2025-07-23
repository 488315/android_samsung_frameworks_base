package androidx.compose.material3.internal;

import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.tokens.SmallIconButtonTokens;
import androidx.compose.material3.tokens.TypeScaleTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldImplKt {
    public static final float AboveLabelBottomPadding;
    public static final float AboveLabelHorizontalPadding;
    public static final float MinFocusedLabelLineHeight;
    public static final float MinSupportingTextLineHeight;
    public static final float MinTextLineHeight;
    public static final float PrefixSuffixTextPadding;
    public static final float SupportingTopPadding;
    public static final float TextFieldPadding;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TextFieldType.values().length];
            try {
                iArr[TextFieldType.Filled.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TextFieldType.Outlined.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[InputPhase.values().length];
            try {
                iArr2[InputPhase.Focused.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[InputPhase.UnfocusedEmpty.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[InputPhase.UnfocusedNotEmpty.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        float f = 16;
        Dp.Companion companion = Dp.Companion;
        TextFieldPadding = f;
        float f2 = 4;
        AboveLabelHorizontalPadding = f2;
        AboveLabelBottomPadding = f2;
        SupportingTopPadding = f2;
        PrefixSuffixTextPadding = 2;
        MinTextLineHeight = 24;
        MinFocusedLabelLineHeight = f;
        MinSupportingTextLineHeight = f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:258:0x04ea, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L300;
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x05bd, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L332;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x0464, code lost:
    
        if (r46 != false) goto L283;
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x03bf, code lost:
    
        if (r46 != false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x037c, code lost:
    
        if (r46 != false) goto L223;
     */
    /* JADX WARN: Removed duplicated region for block: B:174:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x04e5  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x051a  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0525  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0542  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x054e  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0598  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x05ec  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0609  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0612  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0640  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x064e  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x06a0  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x06cf  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x070e  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0725  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x073b  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0771  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0782  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x07b5  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x07c6  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x07ec  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x07fd  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0823  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0834  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0860  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x0973  */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0912  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x083f  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0826  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x080a  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x07ef  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x07d3  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x07b8  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0774  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x0712  */
    /* JADX WARN: Removed duplicated region for block: B:399:0x06a6  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x05c0  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x051d  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0467  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CommonDecorationBox(final androidx.compose.material3.internal.TextFieldType r49, final java.lang.CharSequence r50, final kotlin.jvm.functions.Function2 r51, final androidx.compose.material3.TextFieldLabelPosition r52, final kotlin.jvm.functions.Function3 r53, final kotlin.jvm.functions.Function2 r54, final kotlin.jvm.functions.Function2 r55, final kotlin.jvm.functions.Function2 r56, final kotlin.jvm.functions.Function2 r57, final kotlin.jvm.functions.Function2 r58, final kotlin.jvm.functions.Function2 r59, final boolean r60, final boolean r61, final boolean r62, final androidx.compose.foundation.interaction.InteractionSource r63, final androidx.compose.foundation.layout.PaddingValues r64, final androidx.compose.material3.TextFieldColors r65, final kotlin.jvm.functions.Function2 r66, androidx.compose.runtime.Composer r67, final int r68, final int r69) {
        /*
            Method dump skipped, instructions count: 2481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.internal.TextFieldImplKt.CommonDecorationBox(androidx.compose.material3.internal.TextFieldType, java.lang.CharSequence, kotlin.jvm.functions.Function2, androidx.compose.material3.TextFieldLabelPosition, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, boolean, boolean, boolean, androidx.compose.foundation.interaction.InteractionSource, androidx.compose.foundation.layout.PaddingValues, androidx.compose.material3.TextFieldColors, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: access$Decoration-3J-VO9M, reason: not valid java name */
    public static final void m322access$Decoration3JVO9M(long j, TextStyle textStyle, Function2 function2, Composer composer, final int i) {
        int i2;
        final long j2;
        final TextStyle textStyle2;
        final Function2 function22;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1208685580);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(textStyle) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function22 = function2;
            textStyle2 = textStyle;
            j2 = j;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.Decoration (TextFieldImpl.kt:325)");
            }
            ProvideContentColorTextStyleKt.m320ProvideContentColorTextStyle3JVO9M(j, textStyle, function2, composerImpl, i2 & 1022);
            j2 = j;
            textStyle2 = textStyle;
            function22 = function2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$Decoration$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldImplKt.m322access$Decoration3JVO9M(j2, textStyle2, function22, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$Decoration-Iv8Zu3U, reason: not valid java name */
    public static final void m323access$DecorationIv8Zu3U(final long j, final Function2 function2, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(660142980);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.Decoration (TextFieldImpl.kt:330)");
            }
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(j)), function2, composerImpl, (i2 & 112) | 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$Decoration$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldImplKt.m323access$DecorationIv8Zu3U(j, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Modifier defaultErrorSemantics(Modifier modifier, boolean z, final String str) {
        return z ? SemanticsModifierKt.semantics(modifier, false, new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$defaultErrorSemantics$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                String str2 = str;
                KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                SemanticsProperties.INSTANCE.getClass();
                ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj)).set(SemanticsProperties.Error, str2);
                return Unit.INSTANCE;
            }
        }) : modifier;
    }

    public static final Alignment.Horizontal getExpandedAlignment(TextFieldLabelPosition textFieldLabelPosition) {
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Above) {
            return ((TextFieldLabelPosition.Above) textFieldLabelPosition).alignment;
        }
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Attached) {
            return ((TextFieldLabelPosition.Attached) textFieldLabelPosition).expandedAlignment;
        }
        throw new IllegalArgumentException("Unknown position: " + textFieldLabelPosition);
    }

    public static final Alignment.Horizontal getMinimizedAlignment(TextFieldLabelPosition textFieldLabelPosition) {
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Above) {
            return ((TextFieldLabelPosition.Above) textFieldLabelPosition).alignment;
        }
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Attached) {
            return ((TextFieldLabelPosition.Attached) textFieldLabelPosition).minimizedAlignment;
        }
        throw new IllegalArgumentException("Unknown position: " + textFieldLabelPosition);
    }

    public static final float minimizedLabelHalfHeight(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.internal.minimizedLabelHalfHeight (TextFieldImpl.kt:530)");
        }
        MaterialTheme.INSTANCE.getClass();
        long j = MaterialTheme.getTypography(composer).bodySmall.paragraphStyle.lineHeight;
        TypeScaleTokens.INSTANCE.getClass();
        long j2 = TypeScaleTokens.BodySmallLineHeight;
        if ((1095216660480L & j) != 4294967296L) {
            j = j2;
        }
        float mo52toDpGaN1DYA = ((Density) ((ComposerImpl) composer).consume(CompositionLocalsKt.LocalDensity)).mo52toDpGaN1DYA(j) / 2;
        Dp.Companion companion = Dp.Companion;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mo52toDpGaN1DYA;
    }

    public static final Modifier textFieldBackground(Modifier modifier, final ColorProducer colorProducer, final Shape shape) {
        return DrawModifierKt.drawWithCache(modifier, new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$textFieldBackground$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                CacheDrawScope cacheDrawScope = (CacheDrawScope) obj;
                final Outline mo40createOutlinePq9zytI = Shape.this.mo40createOutlinePq9zytI(cacheDrawScope.cacheParams.mo360getSizeNHjbRc(), cacheDrawScope.cacheParams.getLayoutDirection(), cacheDrawScope);
                final ColorProducer colorProducer2 = colorProducer;
                return cacheDrawScope.onDrawBehind(new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$textFieldBackground$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        OutlineKt.m490drawOutlinewDX37Ww$default((DrawScope) obj2, Outline.this, colorProducer2.mo261invoke0d7_KjU(), 0.0f, null, 60);
                        return Unit.INSTANCE;
                    }
                });
            }
        });
    }

    public static final float textFieldHorizontalIconPadding(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.internal.textFieldHorizontalIconPadding (TextFieldImpl.kt:523)");
        }
        float f = ((Dp) ((ComposerImpl) composer).consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value;
        if (Float.isNaN(f)) {
            f = 0;
        }
        SmallIconButtonTokens.INSTANCE.getClass();
        float f2 = (f - SmallIconButtonTokens.IconSize) / 2;
        float f3 = 0;
        if (f2 < f3) {
            f2 = f3;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return f2;
    }

    public static final Modifier textFieldLabelMinHeight(Modifier modifier, final Function0 function0) {
        return LayoutModifierKt.layout(modifier, new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$textFieldLabelMinHeight$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                MeasureResult layout$1;
                MeasureScope measureScope = (MeasureScope) obj;
                Measurable measurable = (Measurable) obj2;
                long j = ((Constraints) obj3).value;
                float f = ((Dp) Function0.this.invoke()).value;
                Dp.Companion.getClass();
                final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, 0, 0, ConstraintsKt.m831constrainHeightK40F9xA(!Dp.m836equalsimpl0(f, Dp.Unspecified) ? measureScope.mo51roundToPx0680j_4(f) : 0, j), 0, 11));
                layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$textFieldLabelMinHeight$1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj4) {
                        ((Placeable.PlacementScope) obj4).place(Placeable.this, 0, 0, 0.0f);
                        return Unit.INSTANCE;
                    }
                });
                return layout$1;
            }
        });
    }
}
