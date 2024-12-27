package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
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
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.ext.ImageString;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

public abstract class TextExtKt {
    /* renamed from: TextExt-JKOsDoc, reason: not valid java name */
    public static final void m1981TextExtJKOsDoc(final CharSequence charSequence, Modifier modifier, int i, int i2, TextStyle textStyle, Composer composer, final int i3, final int i4) {
        int i5;
        TextStyle textStyle2;
        int i6;
        Modifier modifier2;
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(920543016);
        Modifier modifier3 = (i4 & 2) != 0 ? Modifier.Companion : modifier;
        if ((i4 & 4) != 0) {
            TextOverflow.Companion.getClass();
            i5 = TextOverflow.Ellipsis;
        } else {
            i5 = i;
        }
        int i7 = (i4 & 8) != 0 ? 1 : i2;
        if ((i4 & 16) != 0) {
            i6 = i3 & (-57345);
            textStyle2 = (TextStyle) composerImpl.consume(TextKt.LocalTextStyle);
        } else {
            textStyle2 = textStyle;
            i6 = i3;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (charSequence instanceof ImageString) {
            composerImpl.startReplaceGroup(-904587479);
            ImageString imageString = (ImageString) charSequence;
            List split$default = StringsKt__StringsKt.split$default(StringResources_androidKt.stringResource(imageString.resId, composerImpl), new String[]{imageString.selection}, 0, 6);
            List list = split$default.size() == 2 ? split$default : null;
            if (list == null) {
                z = false;
                modifier2 = modifier3;
            } else {
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                int i8 = i6 >> 3;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                int i9 = composerImpl.compoundKeyHash;
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier3);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (!(composerImpl.applier instanceof Applier)) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m276setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i9))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(i9, composerImpl, i9, function2);
                }
                Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                int i10 = (i8 & 112) | (i6 & 7168) | ((i6 << 6) & 3670016);
                modifier2 = modifier3;
                TextKt.m257Text4IGK_g((String) list.get(0), null, 0L, 0L, null, null, null, 0L, null, null, 0L, i5, false, i7, 0, null, textStyle2, composerImpl, 0, i10, 55294);
                ImageVector imageVector = imageString.image;
                Dp.Companion companion = Dp.Companion;
                ImageKt.Image(imageVector, "", SizeKt.m115size3ABfNKs(Modifier.Companion, 18), composerImpl, 432);
                TextKt.m257Text4IGK_g((String) list.get(1), null, 0L, 0L, null, null, null, 0L, null, null, 0L, i5, false, i7, 0, null, textStyle2, composerImpl, 0, i10, 55294);
                composerImpl.end(true);
                z = false;
            }
            composerImpl.end(z);
        } else {
            modifier2 = modifier3;
            if (charSequence instanceof ResourceString ? true : charSequence instanceof MultiSequenceString) {
                composerImpl.startReplaceGroup(-904586867);
                TextKt.m257Text4IGK_g(CharSequenceExtKt.text(charSequence, composerImpl), modifier2, 0L, 0L, null, null, null, 0L, null, null, 0L, i5, false, 1, 0, null, textStyle2, composerImpl, i6 & 112, ((i6 >> 3) & 112) | 3072 | ((i6 << 6) & 3670016), 55292);
                composerImpl.end(false);
            } else if (charSequence instanceof AnnotatedString) {
                composerImpl.startReplaceGroup(-904586716);
                TextKt.m258TextIbK3jfQ((AnnotatedString) charSequence, modifier2, 0L, 0L, null, null, null, 0L, null, null, 0L, i5, false, 1, 0, null, null, textStyle2, composerImpl, i6 & 112, ((i6 >> 3) & 112) | 3072 | ((i6 << 9) & 29360128), 120828);
                composerImpl.end(false);
            } else if (charSequence instanceof String) {
                composerImpl.startReplaceGroup(-904586581);
                TextKt.m257Text4IGK_g((String) charSequence, modifier2, 0L, 0L, null, null, null, 0L, null, null, 0L, i5, false, 1, 0, null, textStyle2, composerImpl, i6 & 112, ((i6 >> 3) & 112) | 3072 | ((i6 << 6) & 3670016), 55292);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-904586477);
                composerImpl.end(false);
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Modifier modifier4 = modifier2;
            final int i11 = i5;
            final int i12 = i7;
            final TextStyle textStyle3 = textStyle2;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.TextExtKt$TextExt$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextExtKt.m1981TextExtJKOsDoc(charSequence, modifier4, i11, i12, textStyle3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i3 | 1), i4);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
