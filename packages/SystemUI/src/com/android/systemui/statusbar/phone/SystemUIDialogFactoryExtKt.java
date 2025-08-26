package com.android.systemui.statusbar.phone;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsets_androidKt;
import androidx.compose.foundation.shape.CornerBasedShape;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.ui.ShortcutCustomizationDialogDelegate;
import com.android.systemui.util.Assert;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class SystemUIDialogFactoryExtKt {
    /* JADX WARN: Removed duplicated region for block: B:19:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void DragHandle(final Dialog dialog, Composer composer, final int i) {
        int i2 = 0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(590125960);
        if ((((composerImpl.changedInstance(dialog) ? 4 : 2) | i) & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.DragHandle (SystemUIDialogFactoryExt.kt:282)");
            }
            String strStringResource = StringResources_androidKt.stringResource(R.string.shortcut_helper_content_description_drag_handle, composerImpl);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, 0.0f, 16, 0.0f, 6, 5);
            composerImpl.startReplaceGroup(224537645);
            boolean zChanged = composerImpl.changed(strStringResource);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda0(strStringResource, i2);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM129paddingqDBjuR0$default, false, (Function1) objRememberedValue);
                composerImpl.startReplaceGroup(224542684);
                boolean zChangedInstance = composerImpl.changedInstance(dialog);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda1(dialog);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierSemantics, false, null, (Function0) objRememberedValue2, 7);
                    MaterialTheme.INSTANCE.getClass();
                    long j = MaterialTheme.getColorScheme(composerImpl).onSurfaceVariant;
                    CornerBasedShape cornerBasedShape = MaterialTheme.getShapes(composerImpl).extraLarge;
                    ComposableSingletons$SystemUIDialogFactoryExtKt.INSTANCE.getClass();
                    SurfaceKt.m304SurfaceT9BRK9s(modifierM35clickableXHw0xAI$default, cornerBasedShape, j, 0L, 0.0f, 0.0f, null, ComposableSingletons$SystemUIDialogFactoryExtKt.f108lambda1, composerImpl, 12582912, 120);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(dialog, i) { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda2
                public final /* synthetic */ Dialog f$0;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    SystemUIDialogFactoryExtKt.DragHandle(this.f$0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Modifier access$bottomSheetClickable(Modifier modifier, final Function0 function0, ComposerImpl composerImpl, int i) {
        composerImpl.startReplaceGroup(-853331142);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.bottomSheetClickable (SystemUIDialogFactoryExt.kt:279)");
        }
        composerImpl.startReplaceGroup(554929629);
        boolean z = (((i & 112) ^ 48) > 32 && composerImpl.changed(function0)) || (i & 48) == 32;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!z) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new PointerInputEventHandler() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1
                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                        Object objDetectTapGestures$default = TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, new SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda0(function0, 1), continuation, 7);
                        return objDetectTapGestures$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objDetectTapGestures$default : Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        Modifier modifierPointerInput = SuspendingPointerInputFilterKt.pointerInput(modifier, function0, (PointerInputEventHandler) objRememberedValue);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return modifierPointerInput;
    }

    public static final ComponentSystemUIDialog create(SystemUIDialogFactory systemUIDialogFactory, Context context, int i, DialogDelegate dialogDelegate, final ComposableLambdaImpl composableLambdaImpl) {
        systemUIDialogFactory.getClass();
        Assert.isMainThread();
        final ComponentSystemUIDialog componentSystemUIDialog = new ComponentSystemUIDialog(context, i, true, systemUIDialogFactory.dialogManager, systemUIDialogFactory.sysUiState, systemUIDialogFactory.broadcastDispatcher, systemUIDialogFactory.dialogTransitionAnimator, dialogDelegate);
        componentSystemUIDialog.create();
        ComposeView composeView = new ComposeView(context, null, 0, 6, null);
        composeView.setContent(new ComposableLambdaImpl(-716517866, true, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1
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
                            ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.create.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:242)");
                        }
                        final Function3 function3 = composableLambdaImpl;
                        final ComponentSystemUIDialog componentSystemUIDialog2 = componentSystemUIDialog;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(1991354508, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$2$1.1
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.create.<anonymous>.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:243)");
                                        }
                                        MaterialTheme.INSTANCE.getClass();
                                        ProvidedValue providedValueDefaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(MaterialTheme.getColorScheme(composer2).onSurfaceVariant));
                                        final Function3 function32 = function3;
                                        final ComponentSystemUIDialog componentSystemUIDialog3 = componentSystemUIDialog2;
                                        CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(955047244, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.create.2.1.1.1
                                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                            @Override // kotlin.jvm.functions.Function2
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke(Object obj5, Object obj6) {
                                                Composer composer3 = (Composer) obj5;
                                                if ((((Number) obj6).intValue() & 3) == 2) {
                                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                                    if (composerImpl3.getSkipping()) {
                                                        composerImpl3.skipToGroupEnd();
                                                    } else {
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.create.<anonymous>.<anonymous>.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:245)");
                                                        }
                                                        function32.invoke(componentSystemUIDialog3, composer3, 0);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composer2), composer2, 56);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer), composer, 48, 1);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }));
        componentSystemUIDialog.setContentView(composeView);
        return componentSystemUIDialog;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$create$1] */
    public static ComponentSystemUIDialog create$default(SystemUIDialogFactory systemUIDialogFactory, Context context, final Integer num, ShortcutCustomizationDialogDelegate shortcutCustomizationDialogDelegate, ComposableLambdaImpl composableLambdaImpl, int i) {
        int i2;
        if ((i & 1) != 0) {
            context = systemUIDialogFactory.applicationContext;
        }
        if ((i & 2) != 0) {
            int i3 = SystemUIDialog.$r8$clinit;
            i2 = R.style.Theme_SystemUI_Dialog;
        } else {
            i2 = R.style.Theme_VolumePanel_Popup;
        }
        if ((i & 8) != 0) {
            num = null;
        }
        ShortcutCustomizationDialogDelegate shortcutCustomizationDialogDelegate2 = shortcutCustomizationDialogDelegate;
        if ((i & 16) != 0) {
            shortcutCustomizationDialogDelegate2 = new DialogDelegate() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt.create.1
                @Override // com.android.systemui.statusbar.phone.DialogDelegate
                public final void onCreate(Dialog dialog, Bundle bundle) {
                    SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
                    Integer num2 = num;
                    if (num2 != null) {
                        int iIntValue = num2.intValue();
                        Window window = systemUIDialog.getWindow();
                        if (window != null) {
                            window.setGravity(iIntValue);
                        }
                    }
                }
            };
        }
        return create(systemUIDialogFactory, context, i2, shortcutCustomizationDialogDelegate2, composableLambdaImpl);
    }

    /* renamed from: createBottomSheet-6ZxE2Lo$default, reason: not valid java name */
    public static ComponentSystemUIDialog m3095createBottomSheet6ZxE2Lo$default(SystemUIDialogFactory systemUIDialogFactory, final ComposableLambdaImpl composableLambdaImpl, final boolean z, final float f, int i) {
        Context context = systemUIDialogFactory.applicationContext;
        if ((i & 16) != 0) {
            z = true;
        }
        return create(systemUIDialogFactory, context, R.style.Theme_SystemUI_BottomSheet, new EdgeToEdgeDialogDelegate(), new ComposableLambdaImpl(2126763161, true, new Function3() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$createBottomSheet$1
            /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
            @Override // kotlin.jvm.functions.Function3
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                final SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.createBottomSheet.<anonymous> (SystemUIDialogFactoryExt.kt:135)");
                }
                ComposerImpl composerImpl = (ComposerImpl) composer;
                composerImpl.startReplaceGroup(-1814693459);
                final boolean z2 = z;
                Composer.Companion companion = Composer.Companion;
                if (z2) {
                    Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -1814692942, companion);
                    if (objM == Composer.Companion.Empty) {
                        objM = InteractionSourceKt.MutableInteractionSource();
                        composerImpl.updateRememberedValue(objM);
                    }
                    composerImpl.end(false);
                }
                composerImpl.end(false);
                Modifier.Companion companion2 = Modifier.Companion;
                composerImpl.startReplaceGroup(-1814677921);
                boolean zChangedInstance = composerImpl.changedInstance(systemUIDialog);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda1(systemUIDialog);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                }
                composerImpl.end(false);
                Modifier modifierAccess$bottomSheetClickable = SystemUIDialogFactoryExtKt.access$bottomSheetClickable(companion2, (Function0) objRememberedValue, composerImpl, 6);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomCenter, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierAccess$bottomSheetClickable);
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
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.bottom_sheet_corner_radius, composerImpl);
                composerImpl.startReplaceGroup(154623750);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.bottomSheetPaddings (SystemUIDialogFactoryExt.kt:257)");
                }
                boolean z3 = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation == 1;
                Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                WindowInsets.Companion companion3 = WindowInsets.Companion;
                WindowInsets safeDrawing = WindowInsets_androidKt.getSafeDrawing(composerImpl);
                float f2 = z3 ? 0 : 48;
                Dp.Companion companion4 = Dp.Companion;
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalLayoutDirection;
                Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(companion2, density.mo55toDpu2uoSUM(safeDrawing.getLeft(density, (LayoutDirection) composerImpl.consume(staticProvidableCompositionLocal))) + f2, density.mo55toDpu2uoSUM(safeDrawing.getTop(density)), density.mo55toDpu2uoSUM(safeDrawing.getRight(density, (LayoutDirection) composerImpl.consume(staticProvidableCompositionLocal))) + f2, 0.0f, 8);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                composerImpl.startReplaceGroup(1771555372);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                companion.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new SystemUIDialogFactoryExtKt$createBottomSheet$1$$ExternalSyntheticLambda1();
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                Modifier modifierAccess$bottomSheetClickable2 = SystemUIDialogFactoryExtKt.access$bottomSheetClickable(modifierM129paddingqDBjuR0$default, (Function0) objRememberedValue2, composerImpl, 48);
                float f3 = f;
                if (Float.isNaN(f3)) {
                    DraggableBottomSheet.INSTANCE.getClass();
                    f3 = DraggableBottomSheet.MaxWidth;
                }
                Modifier modifierM146widthInVpY3zN4$default = SizeKt.m146widthInVpY3zN4$default(modifierAccess$bottomSheetClickable2, 0.0f, f3, 1);
                RoundedCornerShape roundedCornerShapeM189RoundedCornerShapea9UjIt4$default = RoundedCornerShapeKt.m189RoundedCornerShapea9UjIt4$default(fDimensionResource, fDimensionResource, 0.0f, 0.0f, 12);
                MaterialTheme.INSTANCE.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl).surfaceContainer;
                final Function3 function3 = composableLambdaImpl;
                SurfaceKt.m304SurfaceT9BRK9s(modifierM146widthInVpY3zN4$default, roundedCornerShapeM189RoundedCornerShapea9UjIt4$default, j, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(2072898904, new Function2() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$createBottomSheet$1$2$2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj4, Object obj5) {
                        Composer composer2 = (Composer) obj4;
                        if ((((Number) obj5).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.createBottomSheet.<anonymous>.<anonymous>.<anonymous> (SystemUIDialogFactoryExt.kt:193)");
                                }
                                Modifier.Companion companion5 = Modifier.Companion;
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(282285115);
                                Density density2 = (Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity);
                                WindowInsets.Companion companion6 = WindowInsets.Companion;
                                float fMo55toDpu2uoSUM = density2.mo55toDpu2uoSUM(WindowInsets_androidKt.getSafeDrawing(composerImpl3).getBottom(density2));
                                composerImpl3.end(false);
                                Modifier modifierM129paddingqDBjuR0$default2 = PaddingKt.m129paddingqDBjuR0$default(companion5, 0.0f, 0.0f, 0.0f, fMo55toDpu2uoSUM, 7);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl3, modifierM129paddingqDBjuR0$default2);
                                ComposeUiNode.Companion.getClass();
                                Function0 function02 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl3.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function02);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
                                Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy2, function22);
                                Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
                                Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl3, currentCompositeKeyHash2, function24);
                                }
                                Function2 function25 = ComposeUiNode.Companion.SetModifier;
                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier2, function25);
                                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                                boolean z4 = z2;
                                Function3 function32 = function3;
                                SystemUIDialog systemUIDialog2 = systemUIDialog;
                                if (z4) {
                                    composerImpl3.startReplaceGroup(1680260909);
                                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                    Modifier modifierWrapContentWidth$default = SizeKt.wrapContentWidth$default(companion5, horizontal, 2);
                                    Arrangement.INSTANCE.getClass();
                                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composerImpl3, 48);
                                    int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl3.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl3, modifierWrapContentWidth$default);
                                    composerImpl3.startReusableNode();
                                    if (composerImpl3.inserting) {
                                        composerImpl3.createNode(function02);
                                    } else {
                                        composerImpl3.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl3, columnMeasurePolicy, function22);
                                    Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope3, function23);
                                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl3, currentCompositeKeyHash3, function24);
                                    }
                                    Updater.m337setimpl(composerImpl3, modifierMaterializeModifier3, function25);
                                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                    SystemUIDialogFactoryExtKt.DragHandle(systemUIDialog2, composerImpl3, 0);
                                    function32.invoke(systemUIDialog2, composerImpl3, 0);
                                    composerImpl3.end(true);
                                    composerImpl3.end(false);
                                } else {
                                    composerImpl3.startReplaceGroup(1680650579);
                                    function32.invoke(systemUIDialog2, composerImpl3, 0);
                                    composerImpl3.end(false);
                                }
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 12582912, 120);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }));
    }
}
