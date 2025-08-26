package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
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
import com.android.systemui.media.mediaoutput.compose.widget.AnimatedPlayingIconKt;
import com.android.systemui.media.mediaoutput.icons.Icons;
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

/* loaded from: classes2.dex */
public final class ComposableSingletons$MediaCardKt {
    public static final ComposableSingletons$MediaCardKt INSTANCE = new ComposableSingletons$MediaCardKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f66lambda1 = new ComposableLambdaImpl(2091917431, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-1.<anonymous> (MediaCard.kt:163)");
                    }
                    Modifier.Companion companion = Modifier.Companion;
                    float f = MediaCardKt.isProgressVisible(composer) ? 150 : 118;
                    Dp.Companion companion2 = Dp.Companion;
                    Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(companion, f);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer;
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer, modifierM131height3ABfNKs);
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
                    Updater.m337setimpl(composer, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composer, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composer, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    MediaCardKt.ThumbnailSection(SizeKt.fillMaxSize(companion, 1.0f), composer, 6);
                    MediaCardKt.MediaControlSection(SizeKt.fillMaxSize(companion, 1.0f), composer, 6);
                    composerImpl2.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f67lambda2 = new ComposableLambdaImpl(-96874105, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-2$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-2.<anonymous> (MediaCard.kt:246)");
                    }
                    Icons.Action action = Icons.Action.INSTANCE;
                    IconKt.m271Iconww6aTOc((ImageVector) RemoteKt.Remote$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f68lambda3 = new ComposableLambdaImpl(1224079252, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-3$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-3.<anonymous> (MediaCard.kt:468)");
                    }
                    Icons.Action action = Icons.Action.INSTANCE;
                    IconKt.m271Iconww6aTOc((ImageVector) VolMuteKt.VolMute$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f69lambda4 = new ComposableLambdaImpl(1176088028, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-4$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-4.<anonymous> (MediaCard.kt:482)");
                    }
                    Icons.Action action = Icons.Action.INSTANCE;
                    IconKt.m271Iconww6aTOc((ImageVector) VolDownKt.VolDown$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f70lambda5 = new ComposableLambdaImpl(-894342381, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-5$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-5.<anonymous> (MediaCard.kt:495)");
                    }
                    Icons.Action action = Icons.Action.INSTANCE;
                    IconKt.m271Iconww6aTOc((ImageVector) VolUpKt.VolUp$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static final ComposableLambdaImpl f71lambda6 = new ComposableLambdaImpl(288011787, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-6$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-6.<anonymous> (MediaCard.kt:503)");
                    }
                    Icons.Action action = Icons.Action.INSTANCE;
                    IconKt.m271Iconww6aTOc((ImageVector) PlayPauseKt.PlayPause$delegate.getValue(), "", (Modifier) null, 0L, composer, 48, 12);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-7, reason: not valid java name */
    public static final ComposableLambdaImpl f72lambda7 = new ComposableLambdaImpl(-1207856448, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-7$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-7.<anonymous> (MediaCard.kt:715)");
                    }
                    AnimatedPlayingIconKt.EqualizerPlayingIcon(null, 0L, composer, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-8, reason: not valid java name */
    public static final ComposableLambdaImpl f73lambda8 = new ComposableLambdaImpl(1574484839, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-8$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-8.<anonymous> (MediaCard.kt:732)");
                    }
                    Icons.Action action = Icons.Action.INSTANCE;
                    IconKt.m271Iconww6aTOc((ImageVector) CloseKt.Close$delegate.getValue(), "", (Modifier) null, ColorKt.primaryColor(false, false, composer, 3), composer, 48, 4);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-9, reason: not valid java name */
    public static final ComposableLambdaImpl f74lambda9 = new ComposableLambdaImpl(-2019016375, false, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt$lambda-9$1
        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            ImageVector imageVector;
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            Composer composer = (Composer) obj3;
            ((Number) obj4).intValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$MediaCardKt.lambda-9.<anonymous> (MediaCard.kt:744)");
            }
            if (zBooleanValue) {
                Icons.Action action = Icons.Action.INSTANCE;
                imageVector = (ImageVector) ExpandLessKt.ExpandLess$delegate.getValue();
            } else {
                Icons.Action action2 = Icons.Action.INSTANCE;
                imageVector = (ImageVector) ExpandMoreKt.ExpandMore$delegate.getValue();
            }
            IconKt.m271Iconww6aTOc(imageVector, "", (Modifier) null, ColorKt.primaryColor(false, false, composer, 3), composer, 48, 4);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
