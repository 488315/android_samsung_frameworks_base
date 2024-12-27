package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.icons.Icons$Action;
import com.android.systemui.media.mediaoutput.icons.action.CloseKt;
import com.android.systemui.media.mediaoutput.icons.action.ExpandLessKt;
import com.android.systemui.media.mediaoutput.icons.action.ExpandMoreKt;
import com.android.systemui.media.mediaoutput.icons.action.PlayPauseKt;
import com.android.systemui.media.mediaoutput.icons.action.RemoteKt;
import com.android.systemui.media.mediaoutput.icons.action.VolDownKt;
import com.android.systemui.media.mediaoutput.icons.action.VolMuteKt;
import com.android.systemui.media.mediaoutput.icons.action.VolUpKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

public final class ComposableSingletons$MediaCardKt {
    public static final ComposableSingletons$MediaCardKt INSTANCE = new ComposableSingletons$MediaCardKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f56lambda1 = new ComposableLambdaImpl(515879481, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Modifier.Companion companion = Modifier.Companion;
            float f = MediaCardKt.isProgressVisible(composer) ? 150 : 118;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m108height3ABfNKs = SizeKt.m108height3ABfNKs(companion, f);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
            ComposerImpl composerImpl2 = (ComposerImpl) composer;
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer, m108height3ABfNKs);
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
            Updater.m276setimpl(composer, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m276setimpl(composer, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
            }
            Updater.m276setimpl(composer, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            FillElement fillElement = SizeKt.FillWholeMaxSize;
            MediaCardKt.access$ThumbnailSection(fillElement, null, composer, 6, 2);
            MediaCardKt.access$MediaControlSection(fillElement, null, composer, 6, 2);
            composerImpl2.end(true);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f57lambda2 = new ComposableLambdaImpl(-1923428734, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            IconKt.m226Iconww6aTOc((ImageVector) RemoteKt.Remote$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f58lambda3 = new ComposableLambdaImpl(1217429569, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            IconKt.m226Iconww6aTOc((ImageVector) VolMuteKt.VolMute$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f59lambda4 = new ComposableLambdaImpl(-1893084955, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            IconKt.m226Iconww6aTOc((ImageVector) VolDownKt.VolDown$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f60lambda5 = new ComposableLambdaImpl(-890800804, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-5$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            IconKt.m226Iconww6aTOc((ImageVector) VolUpKt.VolUp$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static final ComposableLambdaImpl f61lambda6 = new ComposableLambdaImpl(1281477880, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-6$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            IconKt.m226Iconww6aTOc((ImageVector) PlayPauseKt.PlayPause$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-7, reason: not valid java name */
    public static final ComposableLambdaImpl f62lambda7 = new ComposableLambdaImpl(1448634504, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-7$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 11) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            Icons$Action icons$Action = Icons$Action.INSTANCE;
            IconKt.m226Iconww6aTOc((ImageVector) CloseKt.Close$delegate.getValue(), "", (Modifier) null, ColorKt.primaryColor(false, false, composer, 3), composer, 48, 4);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-8, reason: not valid java name */
    public static final ComposableLambdaImpl f63lambda8 = new ComposableLambdaImpl(-2144866710, false, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-8$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            ImageVector imageVector;
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            Composer composer = (Composer) obj3;
            ((Number) obj4).intValue();
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (booleanValue) {
                Icons$Action icons$Action = Icons$Action.INSTANCE;
                imageVector = (ImageVector) ExpandLessKt.ExpandLess$delegate.getValue();
            } else {
                Icons$Action icons$Action2 = Icons$Action.INSTANCE;
                imageVector = (ImageVector) ExpandMoreKt.ExpandMore$delegate.getValue();
            }
            IconKt.m226Iconww6aTOc(imageVector, "", (Modifier) null, ColorKt.primaryColor(false, false, composer, 3), composer, 48, 4);
            return Unit.INSTANCE;
        }
    });
}
