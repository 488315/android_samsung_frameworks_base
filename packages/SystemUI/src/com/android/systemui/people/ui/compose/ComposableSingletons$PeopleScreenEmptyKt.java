package com.android.systemui.people.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$PeopleScreenEmptyKt {
    public static final ComposableSingletons$PeopleScreenEmptyKt INSTANCE = new ComposableSingletons$PeopleScreenEmptyKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f90lambda1 = new ComposableLambdaImpl(-1657267797, false, new Function3() { // from class: com.android.systemui.people.ui.compose.ComposableSingletons$PeopleScreenEmptyKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.ComposableSingletons$PeopleScreenEmptyKt.lambda-1.<anonymous> (PeopleScreenEmpty.kt:70)");
            }
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.got_it, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f91lambda2 = new ComposableLambdaImpl(-1032132772, false, new Function2() { // from class: com.android.systemui.people.ui.compose.ComposableSingletons$PeopleScreenEmptyKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.people.ui.compose.ComposableSingletons$PeopleScreenEmptyKt.lambda-2.<anonymous> (PeopleScreenEmpty.kt:82)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m125paddingVpY3zN4 = PaddingKt.m125paddingVpY3zN4(companion, 16, 20);
            Alignment.Companion.getClass();
            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
            Arrangement.INSTANCE.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer, 48);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer, m125paddingVpY3zN4);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composer, rowMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composer, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composer, materializeModifier, function24);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, Alignment.Companion.CenterHorizontally, composer, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composer, companion);
            composerImpl2.startReusableNode();
            if (composerImpl2.inserting) {
                composerImpl2.createNode(function0);
            } else {
                composerImpl2.useNode();
            }
            Updater.m336setimpl(composer, columnMeasurePolicy, function2);
            Updater.m336setimpl(composer, currentCompositionLocalScope2, function22);
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composer, materializeModifier2, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            ImageKt.Image(PainterResources_androidKt.painterResource(R.drawable.ic_avatar_with_badge, composer, 0), null, SizeKt.m139size3ABfNKs(companion, 40), null, null, 0.0f, null, composer, 432, 120);
            SpacerKt.Spacer(composer, SizeKt.m130height3ABfNKs(companion, 2));
            String stringResource = StringResources_androidKt.stringResource(R.string.empty_user_name, composer);
            MaterialTheme.INSTANCE.getClass();
            TextStyle textStyle = MaterialTheme.getTypography(composer).labelMedium;
            TextOverflow.Companion.getClass();
            int i = TextOverflow.Ellipsis;
            TextKt.m316Text4IGK_g(stringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, i, false, 1, 0, null, textStyle, composer, 0, 3120, 55294);
            composerImpl2.end(true);
            SpacerKt.Spacer(composer, SizeKt.m143width3ABfNKs(companion, 24));
            TextKt.m316Text4IGK_g(StringResources_androidKt.stringResource(R.string.empty_status, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, i, false, 1, 0, null, MaterialTheme.getTypography(composer).labelMedium, composer, 0, 3120, 55294);
            composerImpl2.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
