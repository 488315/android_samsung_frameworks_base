package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyDslKt;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ComposableSingletons$LabsHomeKt {
    public static final ComposableSingletons$LabsHomeKt INSTANCE = new ComposableSingletons$LabsHomeKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f50lambda1 = new ComposableLambdaImpl(583169551, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LabsHomeKt.access$Labs(null, composer, 0, 1);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f51lambda2 = new ComposableLambdaImpl(-864192584, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LabsHomeKt.access$DebugLabs(null, composer, 0, 1);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f52lambda3 = new ComposableLambdaImpl(-1901202985, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LabsHomeKt.access$ChromecastLabs(null, composer, 0, 1);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f53lambda4 = new ComposableLambdaImpl(1356753910, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-4$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LabsHomeKt.access$SmartThingsLabs(null, composer, 0, 1);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f54lambda5 = new ComposableLambdaImpl(319743509, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-5$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 81) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LabsHomeKt.access$ActionLabs(null, composer, 0, 1);
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static final ComposableLambdaImpl f55lambda6 = new ComposableLambdaImpl(496924195, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-6$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            PaddingValues paddingValues = (PaddingValues) obj;
            Composer composer = (Composer) obj2;
            int intValue = ((Number) obj3).intValue();
            if ((intValue & 14) == 0) {
                intValue |= ((ComposerImpl) composer).changed(paddingValues) ? 4 : 2;
            }
            if ((intValue & 91) == 18) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LayoutDirection layoutDirection = LayoutDirection.Ltr;
            PaddingValuesImpl m101PaddingValuesa9UjIt4$default = PaddingKt.m101PaddingValuesa9UjIt4$default(PaddingKt.calculateStartPadding(paddingValues, layoutDirection), 0.0f, PaddingKt.calculateEndPadding(paddingValues, layoutDirection), paddingValues.mo89calculateBottomPaddingD9Ej5fM(), 2);
            Modifier.Companion companion = Modifier.Companion;
            FillElement fillElement = SizeKt.FillWholeMaxSize;
            companion.then(fillElement);
            LazyDslKt.LazyColumn(PaddingKt.m106paddingqDBjuR0$default(fillElement, 0.0f, paddingValues.mo92calculateTopPaddingD9Ej5fM(), 0.0f, 0.0f, 13), null, m101PaddingValuesa9UjIt4$default, false, null, null, null, false, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$LabsHomeKt$lambda-6$1.1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj4) {
                    LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) obj4;
                    ComposableSingletons$LabsHomeKt.INSTANCE.getClass();
                    LazyListIntervalContent.item$default(lazyListIntervalContent, ComposableSingletons$LabsHomeKt.f50lambda1);
                    LazyListIntervalContent.item$default(lazyListIntervalContent, ComposableSingletons$LabsHomeKt.f51lambda2);
                    LazyListIntervalContent.item$default(lazyListIntervalContent, ComposableSingletons$LabsHomeKt.f52lambda3);
                    LazyListIntervalContent.item$default(lazyListIntervalContent, ComposableSingletons$LabsHomeKt.f53lambda4);
                    LazyListIntervalContent.item$default(lazyListIntervalContent, ComposableSingletons$LabsHomeKt.f54lambda5);
                    return Unit.INSTANCE;
                }
            }, composer, 100663296, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            return Unit.INSTANCE;
        }
    });
}
