package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.CardKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

public abstract class ListsKt {
    public static final void EmptyListItem(final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1067275542);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Dp.Companion companion = Dp.Companion;
            float f = 18;
            float f2 = 14;
            Modifier m105paddingqDBjuR0 = PaddingKt.m105paddingqDBjuR0(SizeKt.m110heightInVpY3zN4$default(ClickableKt.m30clickableO2vRcR0$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), null, null, false, null, new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$EmptyListItem$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }, 28), 56, 0.0f, 2), f, f2, f, f2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Center$1, Alignment.Companion.CenterVertically, composerImpl2, 54);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, m105paddingqDBjuR0);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl2.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m276setimpl(composerImpl2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl2, i3, function2);
            }
            Updater.m276setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            composerImpl2.startReplaceGroup(869356492);
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secRegular = TypeKt.getSecRegular();
            long Color = ColorKt.Color(4291611854L);
            TextUnitType.Companion.getClass();
            TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secRegular, Color, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
            composerImpl2.end(false);
            TextKt.m257Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default, composerImpl2, i2 & 14, 0, 65534);
            composerImpl = composerImpl2;
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$EmptyListItem$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ListsKt.EmptyListItem(str, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.media.mediaoutput.compose.widget.ListsKt$ListItemContainer$1, kotlin.jvm.internal.Lambda] */
    public static final void ListItemContainer(final Function3 function3, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1776855776);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function3) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            Dp.Companion companion = Dp.Companion;
            RoundedCornerShape m151RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(26);
            CardDefaults cardDefaults = CardDefaults.INSTANCE;
            long cardBackground = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.cardBackground(composerImpl);
            cardDefaults.getClass();
            CardKt.Card(fillMaxWidth, m151RoundedCornerShape0680j_4, CardDefaults.m211cardColorsro_MJ88(cardBackground, 0L, composerImpl, 0, 14), null, null, ComposableLambdaKt.rememberComposableLambda(1862725166, composerImpl, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$ListItemContainer$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ColumnScope columnScope = (ColumnScope) obj;
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 14) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(columnScope) ? 4 : 2;
                    }
                    if ((intValue & 91) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    Function3.this.invoke(columnScope, composer2, Integer.valueOf(intValue & 14));
                    return Unit.INSTANCE;
                }
            }), composerImpl, 196614, 24);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$ListItemContainer$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ListsKt.ListItemContainer(Function3.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x007b, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecIconListItem(kotlin.jvm.functions.Function0 r21, final java.lang.String r22, java.lang.String r23, final androidx.compose.ui.graphics.painter.Painter r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecIconListItem(kotlin.jvm.functions.Function0, java.lang.String, java.lang.String, androidx.compose.ui.graphics.painter.Painter, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a8, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L52;
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecListItem(final kotlin.jvm.functions.Function0 r15, final java.lang.String r16, java.lang.String r17, androidx.compose.runtime.Composer r18, final int r19, final int r20) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecListItem(kotlin.jvm.functions.Function0, java.lang.String, java.lang.String, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c4, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L63;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x006c  */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.media.mediaoutput.compose.widget.ListsKt$SecRadioListItem$2$1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecRadioListItem(final kotlin.jvm.functions.Function0 r17, final java.lang.String r18, java.lang.String r19, final java.lang.Boolean r20, androidx.compose.runtime.Composer r21, final int r22, final int r23) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecRadioListItem(kotlin.jvm.functions.Function0, java.lang.String, java.lang.String, java.lang.Boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void SecSubHeader(final String str, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1207641572);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Dp.Companion companion = Dp.Companion;
            float f = 18;
            Modifier m105paddingqDBjuR0 = PaddingKt.m105paddingqDBjuR0(SizeKt.m110heightInVpY3zN4$default(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 35, 0.0f, 2), f, 13, f, 5);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i3 = composerImpl2.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, m105paddingqDBjuR0);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (!(composerImpl2.applier instanceof Applier)) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m276setimpl(composerImpl2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composerImpl2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(i3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i3, composerImpl2, i3, function2);
            }
            Updater.m276setimpl(composerImpl2, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl2.startReplaceGroup(-777711954);
            TextStyle.Companion companion2 = TextStyle.Companion;
            TextStyle secSemiBold = TypeKt.getSecSemiBold();
            long mediaPrimaryColor = com.android.systemui.media.mediaoutput.compose.theme.ColorKt.mediaPrimaryColor(composerImpl2);
            TextUnitType.Companion.getClass();
            TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secSemiBold, mediaPrimaryColor, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
            composerImpl2.end(false);
            TextKt.m257Text4IGK_g(str, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, m651copyp1EtxEg$default, composerImpl2, i2 & 14, 0, 65534);
            composerImpl = composerImpl2;
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ListsKt$SecSubHeader$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    ListsKt.SecSubHeader(str, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00d1, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x019d, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01e2, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L103;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecSwitchListItem(final kotlin.jvm.functions.Function1 r21, final java.lang.String r22, java.lang.String r23, final java.lang.Boolean r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 550
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.SecSwitchListItem(kotlin.jvm.functions.Function1, java.lang.String, java.lang.String, java.lang.Boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TwoLineListItem(final java.lang.String r34, androidx.compose.ui.Modifier r35, java.lang.String r36, androidx.compose.runtime.Composer r37, final int r38, final int r39) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.ListsKt.TwoLineListItem(java.lang.String, androidx.compose.ui.Modifier, java.lang.String, androidx.compose.runtime.Composer, int, int):void");
    }
}
