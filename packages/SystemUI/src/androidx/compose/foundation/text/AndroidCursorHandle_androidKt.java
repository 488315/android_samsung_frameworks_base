package androidx.compose.foundation.text;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidCursorHandle_androidKt {
    public static final float CursorHandleHeight;
    public static final float CursorHandleWidth;

    static {
        float f = 25;
        Dp.Companion companion = Dp.Companion;
        CursorHandleHeight = f;
        CursorHandleWidth = (f * 2.0f) / 2.4142137f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x007a, code lost:
    
        if ((r14 & 4) != 0) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b6, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L67;
     */
    /* renamed from: CursorHandle-USBMPiE, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m189CursorHandleUSBMPiE(final androidx.compose.foundation.text.selection.OffsetProvider r8, final androidx.compose.ui.Modifier r9, final long r10, androidx.compose.runtime.Composer r12, final int r13, final int r14) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.AndroidCursorHandle_androidKt.m189CursorHandleUSBMPiE(androidx.compose.foundation.text.selection.OffsetProvider, androidx.compose.ui.Modifier, long, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void access$DefaultCursorHandle(final Modifier modifier, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(694251107);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 3) != 2)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.text.DefaultCursorHandle (AndroidCursorHandle.android.kt:82)");
            }
            SpacerKt.Spacer(composerImpl, ComposedModifierKt.composed(SizeKt.m140sizeVpY3zN4(modifier, CursorHandleWidth, CursorHandleHeight), InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1
                /* JADX WARN: Code restructure failed: missing block: B:7:0x0039, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r4, java.lang.Object r5, java.lang.Object r6) {
                    /*
                        r3 = this;
                        androidx.compose.ui.Modifier r4 = (androidx.compose.ui.Modifier) r4
                        androidx.compose.runtime.Composer r5 = (androidx.compose.runtime.Composer) r5
                        java.lang.Number r6 = (java.lang.Number) r6
                        r6.intValue()
                        androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
                        r3 = -2126899193(0xffffffff813a1807, float:-3.4180043E-38)
                        r5.startReplaceGroup(r3)
                        boolean r3 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r3 == 0) goto L1c
                        java.lang.String r3 = "androidx.compose.foundation.text.drawCursorHandle.<anonymous> (AndroidCursorHandle.android.kt:87)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r3)
                    L1c:
                        androidx.compose.runtime.DynamicProvidableCompositionLocal r3 = androidx.compose.foundation.text.selection.TextSelectionColorsKt.LocalTextSelectionColors
                        java.lang.Object r3 = r5.consume(r3)
                        androidx.compose.foundation.text.selection.TextSelectionColors r3 = (androidx.compose.foundation.text.selection.TextSelectionColors) r3
                        long r0 = r3.handleColor
                        androidx.compose.ui.Modifier$Companion r3 = androidx.compose.ui.Modifier.Companion
                        boolean r6 = r5.changed(r0)
                        java.lang.Object r2 = r5.rememberedValue()
                        if (r6 != 0) goto L3b
                        androidx.compose.runtime.Composer$Companion r6 = androidx.compose.runtime.Composer.Companion
                        r6.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r6 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r2 != r6) goto L43
                    L3b:
                        androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1$1$1 r2 = new androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1$1$1
                        r2.<init>()
                        r5.updateRememberedValue(r2)
                    L43:
                        kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
                        androidx.compose.ui.Modifier r3 = androidx.compose.ui.draw.DrawModifierKt.drawWithCache(r3, r2)
                        androidx.compose.ui.Modifier r3 = r4.then(r3)
                        boolean r4 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r4 == 0) goto L56
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                    L56:
                        r4 = 0
                        r5.end(r4)
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$drawCursorHandle$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
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
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.text.AndroidCursorHandle_androidKt$DefaultCursorHandle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    AndroidCursorHandle_androidKt.access$DefaultCursorHandle(Modifier.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
