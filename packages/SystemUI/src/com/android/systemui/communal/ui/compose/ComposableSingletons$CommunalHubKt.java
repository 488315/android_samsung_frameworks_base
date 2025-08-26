package com.android.systemui.communal.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.AddKt;
import androidx.compose.material.icons.filled.CheckKt;
import androidx.compose.material.icons.filled.CloseKt;
import androidx.compose.material.icons.filled.ExpandMoreKt$$ExternalSyntheticOutline0;
import androidx.compose.material.icons.outlined.EditKt;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0;
import androidx.compose.material3.IconKt;
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
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.android.systemui.R;
import com.android.systemui.communal.util.DensityUtils;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ComposableSingletons$CommunalHubKt {
    public static final ComposableSingletons$CommunalHubKt INSTANCE = new ComposableSingletons$CommunalHubKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f29lambda1 = new ComposableLambdaImpl(878073318, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-1.<anonymous> (CommunalHub.kt:578)");
                    }
                    String strStringResource = StringResources_androidKt.stringResource(R.string.communal_widgets_disclaimer_button, composer);
                    MaterialTheme.INSTANCE.getClass();
                    TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).labelLarge, composer, 0, 0, 65534);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f30lambda2 = new ComposableLambdaImpl(1931407786, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-2$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-2.<anonymous> (CommunalHub.kt:1100)");
                    }
                    Icons.INSTANCE.getClass();
                    ImageVector add = AddKt.getAdd();
                    Modifier.Companion companion = Modifier.Companion;
                    Dp.Companion companion2 = Dp.Companion;
                    IconKt.m271Iconww6aTOc(add, (String) null, SizeKt.m140size3ABfNKs(companion, 24), 0L, composer, 432, 8);
                    ButtonDefaults.INSTANCE.getClass();
                    SpacerKt.Spacer(composer, SizeKt.m144width3ABfNKs(companion, ButtonDefaults.IconSpacing));
                    String strStringResource = StringResources_androidKt.stringResource(R.string.label_for_button_in_empty_state_cta, composer);
                    MaterialTheme.INSTANCE.getClass();
                    TextKt.m317Text4IGK_g(strStringResource, null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composer).titleSmall, composer, 0, 0, 65534);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f31lambda3 = new ComposableLambdaImpl(1260877735, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-3$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-3.<anonymous> (CommunalHub.kt:1191)");
                    }
                    Arrangement arrangement = Arrangement.INSTANCE;
                    ButtonDefaults.INSTANCE.getClass();
                    float f = ButtonDefaults.IconSpacing;
                    Alignment.Companion.getClass();
                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                    arrangement.getClass();
                    Arrangement.SpacedAligned spacedAlignedM93spacedByD5KLDUw = Arrangement.m93spacedByD5KLDUw(f, horizontal);
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Modifier.Companion companion = Modifier.Companion;
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM93spacedByD5KLDUw, vertical, composer, 48);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer;
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer, companion);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    DefaultConstructorMarker defaultConstructorMarker = null;
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
                    Updater.m337setimpl(composer, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m337setimpl(composer, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                    }
                    Updater.m337setimpl(composer, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    Icons.INSTANCE.getClass();
                    ImageVector imageVectorBuild = CloseKt._close;
                    if (imageVectorBuild == null) {
                        Dp.Companion companion2 = Dp.Companion;
                        ImageVector.Builder builder = new ImageVector.Builder("Filled.Close", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                        EmptyList emptyList = VectorKt.EmptyPath;
                        Color.Companion.getClass();
                        SolidColor solidColor = new SolidColor(Color.Black, defaultConstructorMarker);
                        StrokeCap.Companion.getClass();
                        StrokeJoin.Companion.getClass();
                        int i = StrokeJoin.Bevel;
                        PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(19.0f, 6.41f, 17.59f, 5.0f);
                        pathBuilderM.lineTo(12.0f, 10.59f);
                        pathBuilderM.lineTo(6.41f, 5.0f);
                        pathBuilderM.lineTo(5.0f, 6.41f);
                        pathBuilderM.lineTo(10.59f, 12.0f);
                        pathBuilderM.lineTo(5.0f, 17.59f);
                        pathBuilderM.lineTo(6.41f, 19.0f);
                        pathBuilderM.lineTo(12.0f, 13.41f);
                        pathBuilderM.lineTo(17.59f, 19.0f);
                        ComposableSingletons$SnackbarKt$lambda1$1$$ExternalSyntheticOutline0.m(pathBuilderM, 19.0f, 17.59f, 13.41f, 12.0f);
                        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                        imageVectorBuild = builder.build();
                        CloseKt._close = imageVectorBuild;
                    }
                    IconKt.m271Iconww6aTOc(imageVectorBuild, (String) null, (Modifier) null, 0L, composer, 48, 12);
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.button_to_remove_widget, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
                    composerImpl2.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-4, reason: not valid java name */
    public static final ComposableLambdaImpl f32lambda4 = new ComposableLambdaImpl(-763302154, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-4$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-4.<anonymous> (CommunalHub.kt:1214)");
                    }
                    Icons.INSTANCE.getClass();
                    ImageVector imageVectorBuild = CheckKt._check;
                    if (imageVectorBuild == null) {
                        Dp.Companion companion = Dp.Companion;
                        ImageVector.Builder builder = new ImageVector.Builder("Filled.Check", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                        EmptyList emptyList = VectorKt.EmptyPath;
                        Color.Companion.getClass();
                        SolidColor solidColor = new SolidColor(Color.Black, null);
                        StrokeCap.Companion.getClass();
                        StrokeJoin.Companion.getClass();
                        int i = StrokeJoin.Bevel;
                        PathBuilder pathBuilderM = ExpandMoreKt$$ExternalSyntheticOutline0.m(9.0f, 16.17f, 4.83f, 12.0f);
                        pathBuilderM.lineToRelative(-1.42f, 1.41f);
                        pathBuilderM.lineTo(9.0f, 19.0f);
                        pathBuilderM.lineTo(21.0f, 7.0f);
                        pathBuilderM.lineToRelative(-1.41f, -1.41f);
                        pathBuilderM.close();
                        builder.m567addPathoIyEayM("", pathBuilderM._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                        imageVectorBuild = builder.build();
                        CheckKt._check = imageVectorBuild;
                    }
                    IconKt.m271Iconww6aTOc(imageVectorBuild, (String) null, (Modifier) null, 0L, composer, 48, 12);
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.hub_mode_editing_exit_button_text, composer), null, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 0, 0, 131070);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-5, reason: not valid java name */
    public static final ComposableLambdaImpl f33lambda5 = new ComposableLambdaImpl(-1263281864, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-5$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-5.<anonymous> (CommunalHub.kt:1406)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.cta_tile_button_to_dismiss, composer), null, 0L, TextUnitKt.getSp(14), null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 3072, 0, 131062);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-6, reason: not valid java name */
    public static final ComposableLambdaImpl f34lambda6 = new ComposableLambdaImpl(-1707479242, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-6$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-6.<anonymous> (CommunalHub.kt:1421)");
                    }
                    TextKt.m317Text4IGK_g(StringResources_androidKt.stringResource(R.string.cta_tile_button_to_open_widget_editor, composer), null, 0L, TextUnitKt.getSp(14), null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, null, composer, 3072, 0, 131062);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-7, reason: not valid java name */
    public static final ComposableLambdaImpl f35lambda7 = new ComposableLambdaImpl(-1962818462, false, new Function2() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-7$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
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
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-7.<anonymous> (CommunalHub.kt:1626)");
                    }
                    Icons.Outlined outlined = Icons.Outlined.INSTANCE;
                    ImageVector imageVectorBuild = EditKt._edit;
                    if (imageVectorBuild == null) {
                        Dp.Companion companion = Dp.Companion;
                        ImageVector.Builder builder = new ImageVector.Builder("Outlined.Edit", 24.0f, 24.0f, 24.0f, 24.0f, 0L, 0, false, 96, null);
                        EmptyList emptyList = VectorKt.EmptyPath;
                        Color.Companion.getClass();
                        SolidColor solidColor = new SolidColor(Color.Black, null);
                        StrokeCap.Companion.getClass();
                        StrokeJoin.Companion.getClass();
                        int i = StrokeJoin.Bevel;
                        PathBuilder pathBuilder = new PathBuilder();
                        pathBuilder.moveTo(14.06f, 9.02f);
                        pathBuilder.lineToRelative(0.92f, 0.92f);
                        pathBuilder.lineTo(5.92f, 19.0f);
                        pathBuilder.lineTo(5.0f, 19.0f);
                        pathBuilder.verticalLineToRelative(-0.92f);
                        pathBuilder.lineToRelative(9.06f, -9.06f);
                        pathBuilder.moveTo(17.66f, 3.0f);
                        pathBuilder.curveToRelative(-0.25f, 0.0f, -0.51f, 0.1f, -0.7f, 0.29f);
                        pathBuilder.lineToRelative(-1.83f, 1.83f);
                        pathBuilder.lineToRelative(3.75f, 3.75f);
                        pathBuilder.lineToRelative(1.83f, -1.83f);
                        pathBuilder.curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f);
                        pathBuilder.lineToRelative(-2.34f, -2.34f);
                        pathBuilder.curveToRelative(-0.2f, -0.2f, -0.45f, -0.29f, -0.71f, -0.29f);
                        pathBuilder.close();
                        pathBuilder.moveTo(14.06f, 6.19f);
                        pathBuilder.lineTo(3.0f, 17.25f);
                        pathBuilder.lineTo(3.0f, 21.0f);
                        pathBuilder.horizontalLineToRelative(3.75f);
                        pathBuilder.lineTo(17.81f, 9.94f);
                        pathBuilder.lineToRelative(-3.75f, -3.75f);
                        pathBuilder.close();
                        builder.m567addPathoIyEayM("", pathBuilder._nodes, 0, solidColor, 1.0f, null, 1.0f, 1.0f, 0, i, 1.0f, 0.0f, 1.0f, 0.0f);
                        imageVectorBuild = builder.build();
                        EditKt._edit = imageVectorBuild;
                    }
                    String strStringResource = StringResources_androidKt.stringResource(R.string.edit_widget, composer);
                    Modifier.Companion companion2 = Modifier.Companion;
                    DensityUtils.Companion.getClass();
                    IconKt.m271Iconww6aTOc(imageVectorBuild, strStringResource, PaddingKt.m125padding3ABfNKs(companion2, DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(12)), 0L, composer, 0, 8);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-8, reason: not valid java name */
    public static final ComposableLambdaImpl f36lambda8 = new ComposableLambdaImpl(-1769929298, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt$lambda-8$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalHubKt.lambda-8.<anonymous> (CommunalHub.kt:1733)");
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
