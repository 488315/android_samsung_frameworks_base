package androidx.compose.material3;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OutlinedTextFieldKt {
    public static final float OutlinedTextFieldInnerPadding;

    static {
        Dp.Companion companion = Dp.Companion;
        OutlinedTextFieldInnerPadding = 4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:112:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x046a  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x048b  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x036e  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x038e  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x03b6  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03f3  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0410  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x03d6  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x039d  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02e6  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x023f  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02ce  */
    /* JADX WARN: Type inference failed for: r5v34 */
    /* JADX WARN: Type inference failed for: r5v35, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v42 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void OutlinedTextField(final java.lang.String r65, final kotlin.jvm.functions.Function1 r66, androidx.compose.ui.Modifier r67, boolean r68, boolean r69, androidx.compose.ui.text.TextStyle r70, kotlin.jvm.functions.Function2 r71, kotlin.jvm.functions.Function2 r72, kotlin.jvm.functions.Function2 r73, kotlin.jvm.functions.Function2 r74, kotlin.jvm.functions.Function2 r75, kotlin.jvm.functions.Function2 r76, kotlin.jvm.functions.Function2 r77, boolean r78, androidx.compose.ui.text.input.VisualTransformation r79, androidx.compose.foundation.text.KeyboardOptions r80, androidx.compose.foundation.text.KeyboardActions r81, boolean r82, int r83, int r84, androidx.compose.foundation.interaction.MutableInteractionSource r85, androidx.compose.ui.graphics.Shape r86, androidx.compose.material3.TextFieldColors r87, androidx.compose.runtime.Composer r88, final int r89, final int r90, final int r91, final int r92) {
        /*
            Method dump skipped, instructions count: 1382
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextField(java.lang.String, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, boolean, boolean, androidx.compose.ui.text.TextStyle, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, boolean, androidx.compose.ui.text.input.VisualTransformation, androidx.compose.foundation.text.KeyboardOptions, androidx.compose.foundation.text.KeyboardActions, boolean, int, int, androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.ui.graphics.Shape, androidx.compose.material3.TextFieldColors, androidx.compose.runtime.Composer, int, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:229:0x057a, code lost:
    
        if (r4.changedInstance(r6) != false) goto L270;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x058f, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L275;
     */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0230  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x058a  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x05c1  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x05f9  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x06ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void OutlinedTextFieldLayout(final androidx.compose.ui.Modifier r43, final kotlin.jvm.functions.Function2 r44, final kotlin.jvm.functions.Function3 r45, kotlin.jvm.functions.Function2 r46, final kotlin.jvm.functions.Function2 r47, final kotlin.jvm.functions.Function2 r48, final kotlin.jvm.functions.Function2 r49, kotlin.jvm.functions.Function2 r50, final boolean r51, androidx.compose.material3.TextFieldLabelPosition r52, final androidx.compose.material3.internal.FloatProducer r53, final kotlin.jvm.functions.Function1 r54, final kotlin.jvm.functions.Function2 r55, kotlin.jvm.functions.Function2 r56, androidx.compose.foundation.layout.PaddingValues r57, androidx.compose.runtime.Composer r58, final int r59, final int r60) {
        /*
            Method dump skipped, instructions count: 1742
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.OutlinedTextFieldKt.OutlinedTextFieldLayout(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, boolean, androidx.compose.material3.TextFieldLabelPosition, androidx.compose.material3.internal.FloatProducer, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, androidx.compose.foundation.layout.PaddingValues, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final Modifier outlineCutout(Modifier modifier, final Function0 function0, final Alignment.Horizontal horizontal, final PaddingValues paddingValues) {
        return DrawModifierKt.drawWithContent(modifier, new Function1() { // from class: androidx.compose.material3.OutlinedTextFieldKt$outlineCutout$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ContentDrawScope contentDrawScope = (ContentDrawScope) obj;
                long j = ((Size) Function0.this.invoke()).packedValue;
                float m417getWidthimpl = Size.m417getWidthimpl(j);
                if (m417getWidthimpl > 0.0f) {
                    LayoutNodeDrawScope layoutNodeDrawScope = (LayoutNodeDrawScope) contentDrawScope;
                    float mo57toPx0680j_4 = layoutNodeDrawScope.mo57toPx0680j_4(OutlinedTextFieldKt.OutlinedTextFieldInnerPadding);
                    float mo57toPx0680j_42 = layoutNodeDrawScope.mo57toPx0680j_4(paddingValues.mo110calculateLeftPaddingu2uoSUM(layoutNodeDrawScope.getLayoutDirection()));
                    float mo57toPx0680j_43 = layoutNodeDrawScope.mo57toPx0680j_4(paddingValues.mo111calculateRightPaddingu2uoSUM(layoutNodeDrawScope.getLayoutDirection()));
                    Alignment.Horizontal horizontal2 = horizontal;
                    int roundToInt = MathKt__MathJVMKt.roundToInt(m417getWidthimpl);
                    CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                    float align = horizontal2.align(roundToInt, MathKt__MathJVMKt.roundToInt((Size.m417getWidthimpl(canvasDrawScope.mo545getSizeNHjbRc()) - mo57toPx0680j_42) - mo57toPx0680j_43), layoutNodeDrawScope.getLayoutDirection()) + mo57toPx0680j_42;
                    float f = 2;
                    float f2 = m417getWidthimpl / f;
                    float f3 = align + f2;
                    float f4 = (f3 - f2) - mo57toPx0680j_4;
                    float f5 = f4 < 0.0f ? 0.0f : f4;
                    float f6 = f3 + f2 + mo57toPx0680j_4;
                    float m417getWidthimpl2 = Size.m417getWidthimpl(canvasDrawScope.mo545getSizeNHjbRc());
                    float f7 = f6 > m417getWidthimpl2 ? m417getWidthimpl2 : f6;
                    float m415getHeightimpl = Size.m415getHeightimpl(j);
                    float f8 = (-m415getHeightimpl) / f;
                    float f9 = m415getHeightimpl / f;
                    ClipOp.Companion.getClass();
                    CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                    long m526getSizeNHjbRc = canvasDrawScope$drawContext$1.m526getSizeNHjbRc();
                    canvasDrawScope$drawContext$1.getCanvas().save();
                    try {
                        canvasDrawScope$drawContext$1.transform.m528clipRectN_I0leg(f5, f8, f7, f9, 0);
                        layoutNodeDrawScope.drawContent();
                    } finally {
                        BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, m526getSizeNHjbRc);
                    }
                } else {
                    ((LayoutNodeDrawScope) contentDrawScope).drawContent();
                }
                return Unit.INSTANCE;
            }
        });
    }
}
