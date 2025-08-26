package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
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
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public abstract class TextExtKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:106:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01d8  */
    /* renamed from: TextExt-JKOsDoc, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2637TextExtJKOsDoc(final CharSequence charSequence, Modifier modifier, int i, int i2, final TextStyle textStyle, Composer composer, final int i3, final int i4) {
        int i5;
        Modifier modifier2;
        int i6;
        int i7;
        int i8;
        int i9;
        Modifier modifier3;
        int i10;
        Modifier modifier4;
        final Modifier modifier5;
        final int i11;
        final int i12;
        Modifier modifier6;
        boolean z;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(920543016);
        if ((i3 & 6) == 0) {
            i5 = (composerImpl.changedInstance(charSequence) ? 4 : 2) | i3;
        } else {
            i5 = i3;
        }
        int i13 = i4 & 2;
        if (i13 != 0) {
            i5 |= 48;
        } else {
            if ((i3 & 48) == 0) {
                modifier2 = modifier;
                i5 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i6 = i5 | 384;
            i7 = i4 & 8;
            if (i7 != 0) {
                if ((i3 & 3072) == 0) {
                    i8 = i2;
                    i6 |= composerImpl.changed(i8) ? 2048 : 1024;
                }
                if ((i3 & 24576) == 0) {
                    i6 |= composerImpl.changed(textStyle) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i6 & 9363) == 9362 && composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    i11 = i;
                    i12 = i8;
                    modifier5 = modifier2;
                } else {
                    composerImpl.startDefaults();
                    if ((i3 & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                        Modifier modifier7 = i13 == 0 ? Modifier.Companion : modifier2;
                        TextOverflow.Companion.getClass();
                        int i14 = TextOverflow.Ellipsis;
                        if (i7 != 0) {
                            i8 = 1;
                        }
                        i9 = i8;
                        modifier3 = modifier7;
                        i10 = i14;
                    } else {
                        composerImpl.skipToGroupEnd();
                        i10 = i;
                        i9 = i8;
                        modifier3 = modifier2;
                    }
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.TextExt (TextExt.kt:27)");
                    }
                    if (charSequence instanceof ImageString) {
                        Modifier modifier8 = modifier3;
                        if ((charSequence instanceof ResourceString) || (charSequence instanceof MultiSequenceString)) {
                            modifier4 = modifier8;
                            composerImpl.startReplaceGroup(-1724536692);
                            TextKt.m317Text4IGK_g(CharSequenceExtKt.text(charSequence, composerImpl), modifier4, 0L, 0L, null, null, null, 0L, null, null, 0L, i10, false, i9, 0, null, textStyle, composerImpl, i6 & 112, ((i6 >> 3) & 112) | (i6 & 7168) | ((i6 << 6) & 3670016), 55292);
                            composerImpl.end(false);
                        } else if (charSequence instanceof AnnotatedString) {
                            composerImpl.startReplaceGroup(-1724380173);
                            modifier4 = modifier8;
                            TextKt.m318TextIbK3jfQ((AnnotatedString) charSequence, modifier4, 0L, 0L, null, null, null, 0L, null, null, 0L, i10, false, i9, 0, null, null, textStyle, composerImpl, i6 & 112, ((i6 >> 3) & 112) | (i6 & 7168) | ((i6 << 9) & 29360128), 120828);
                            composerImpl.end(false);
                        } else {
                            modifier4 = modifier8;
                            if (charSequence instanceof String) {
                                composerImpl.startReplaceGroup(-1724239309);
                                TextKt.m317Text4IGK_g((String) charSequence, modifier4, 0L, 0L, null, null, null, 0L, null, null, 0L, i10, false, i9, 0, null, textStyle, composerImpl, i6 & 112, ((i6 >> 3) & 112) | (i6 & 7168) | ((i6 << 6) & 3670016), 55292);
                                composerImpl.end(false);
                            } else {
                                composerImpl.startReplaceGroup(-1724118998);
                                composerImpl.end(false);
                            }
                        }
                    } else {
                        composerImpl.startReplaceGroup(2022556575);
                        ImageString imageString = (ImageString) charSequence;
                        List listSplit$default = StringsKt__StringsKt.split$default(StringResources_androidKt.stringResource(imageString.resId, composerImpl), new String[]{imageString.selection}, 0, 6);
                        List list = listSplit$default.size() == 2 ? listSplit$default : null;
                        if (list != null) {
                            Alignment.Companion.getClass();
                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                            int i15 = i6 >> 3;
                            Arrangement.INSTANCE.getClass();
                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier3);
                            ComposeUiNode.Companion.getClass();
                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                            if (composerImpl.applier == null) {
                                ComposablesKt.invalidApplier();
                                throw null;
                            }
                            composerImpl.startReusableNode();
                            if (composerImpl.inserting) {
                                composerImpl.createNode(function0);
                            } else {
                                composerImpl.useNode();
                            }
                            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                            }
                            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                            int i16 = (i15 & 112) | (i6 & 7168) | ((i6 << 6) & 3670016);
                            z = false;
                            modifier6 = modifier3;
                            TextKt.m317Text4IGK_g((String) list.get(0), null, 0L, 0L, null, null, null, 0L, null, null, 0L, i10, false, i9, 0, null, textStyle, composerImpl, 0, i16, 55294);
                            ImageVector imageVector = imageString.image;
                            Dp.Companion companion = Dp.Companion;
                            ImageKt.Image(imageVector, "", SizeKt.m140size3ABfNKs(Modifier.Companion, 18), composerImpl, 432);
                            TextKt.m317Text4IGK_g((String) list.get(1), null, 0L, 0L, null, null, null, 0L, null, null, 0L, i10, false, i9, 0, null, textStyle, composerImpl, 0, i16, 55294);
                            composerImpl.end(true);
                        } else {
                            modifier6 = modifier3;
                            z = false;
                        }
                        composerImpl.end(z);
                        modifier4 = modifier6;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    modifier5 = modifier4;
                    i11 = i10;
                    i12 = i9;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.TextExtKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i3 | 1);
                            TextStyle textStyle2 = textStyle;
                            TextExtKt.m2637TextExtJKOsDoc(charSequence, modifier5, i11, i12, textStyle2, (Composer) obj, iUpdateChangedFlags, i4);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i6 = i5 | 3456;
            i8 = i2;
            if ((i3 & 24576) == 0) {
            }
            if ((i6 & 9363) == 9362) {
                composerImpl.startDefaults();
                if ((i3 & 1) != 0) {
                    if (i13 == 0) {
                    }
                    TextOverflow.Companion.getClass();
                    int i142 = TextOverflow.Ellipsis;
                    if (i7 != 0) {
                    }
                    i9 = i8;
                    modifier3 = modifier7;
                    i10 = i142;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    if (charSequence instanceof ImageString) {
                    }
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    modifier5 = modifier4;
                    i11 = i10;
                    i12 = i9;
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i6 = i5 | 384;
        i7 = i4 & 8;
        if (i7 != 0) {
        }
        i8 = i2;
        if ((i3 & 24576) == 0) {
        }
        if ((i6 & 9363) == 9362) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
