package androidx.compose.material3;

import androidx.compose.material3.tokens.TypographyTokensKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.text.TextStyle;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextKt {
    public static final DynamicProvidableCompositionLocal LocalTextStyle = new DynamicProvidableCompositionLocal(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.material3.TextKt$LocalTextStyle$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return TypographyTokensKt.DefaultTextStyle;
        }
    });

    public static final void ProvideTextStyle(final TextStyle textStyle, final Function2 function2, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-460300127);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(textStyle) ? 4 : 2) | i;
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
                ComposerKt.traceEventStart("androidx.compose.material3.ProvideTextStyle (Text.kt:345)");
            }
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = LocalTextStyle;
            CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(((TextStyle) composerImpl.consume(dynamicProvidableCompositionLocal)).merge(textStyle)), function2, composerImpl, (i2 & 112) | 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TextKt$ProvideTextStyle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextKt.ProvideTextStyle(TextStyle.this, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x032a  */
    /* renamed from: Text--4IGK_g, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m316Text4IGK_g(final java.lang.String r41, androidx.compose.ui.Modifier r42, long r43, long r45, androidx.compose.ui.text.font.FontStyle r47, androidx.compose.ui.text.font.FontWeight r48, androidx.compose.ui.text.font.FontFamily r49, long r50, androidx.compose.ui.text.style.TextDecoration r52, androidx.compose.ui.text.style.TextAlign r53, long r54, int r56, boolean r57, int r58, int r59, kotlin.jvm.functions.Function1 r60, androidx.compose.ui.text.TextStyle r61, androidx.compose.runtime.Composer r62, final int r63, final int r64, final int r65) {
        /*
            Method dump skipped, instructions count: 1024
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextKt.m316Text4IGK_g(java.lang.String, androidx.compose.ui.Modifier, long, long, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontFamily, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.text.style.TextAlign, long, int, boolean, int, int, kotlin.jvm.functions.Function1, androidx.compose.ui.text.TextStyle, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x030f  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x031c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0325  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0321  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0313  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0414  */
    /* renamed from: Text-IbK3jfQ, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m317TextIbK3jfQ(final androidx.compose.ui.text.AnnotatedString r43, androidx.compose.ui.Modifier r44, long r45, long r47, androidx.compose.ui.text.font.FontStyle r49, androidx.compose.ui.text.font.FontWeight r50, androidx.compose.ui.text.font.FontFamily r51, long r52, androidx.compose.ui.text.style.TextDecoration r54, androidx.compose.ui.text.style.TextAlign r55, long r56, int r58, boolean r59, int r60, int r61, java.util.Map r62, kotlin.jvm.functions.Function1 r63, androidx.compose.ui.text.TextStyle r64, androidx.compose.runtime.Composer r65, final int r66, final int r67, final int r68) {
        /*
            Method dump skipped, instructions count: 1102
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextKt.m317TextIbK3jfQ(androidx.compose.ui.text.AnnotatedString, androidx.compose.ui.Modifier, long, long, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontFamily, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.text.style.TextAlign, long, int, boolean, int, int, java.util.Map, kotlin.jvm.functions.Function1, androidx.compose.ui.text.TextStyle, androidx.compose.runtime.Composer, int, int, int):void");
    }
}
