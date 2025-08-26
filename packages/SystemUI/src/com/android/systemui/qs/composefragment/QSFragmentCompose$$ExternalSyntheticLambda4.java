package com.android.systemui.qs.composefragment;

import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.unit.IntSize;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementKey$Companion$withIdentity$1;
import com.android.compose.animation.scene.SceneTransitionLayoutImpl$updateContents$1;
import com.android.compose.animation.scene.SceneTransitionsBuilderImpl;
import com.android.compose.animation.scene.TransitionBuilderImpl;
import com.android.compose.animation.scene.transformation.AnchoredTranslate;
import com.android.compose.animation.scene.transformation.SharedElementTransformation;
import com.android.compose.animation.scene.transformation.TransformationRange;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import com.android.systemui.qs.shared.ui.ElementKeys;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSFragmentCompose f$0;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda4(QSFragmentCompose qSFragmentCompose, int i) {
        this.$r8$classId = i;
        this.f$0 = qSFragmentCompose;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        char c = 1;
        final QSFragmentCompose qSFragmentCompose = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = (SceneTransitionsBuilderImpl) obj;
                int i = QSFragmentCompose.$r8$clinit;
                SceneKeys.INSTANCE.getClass();
                SceneTransitionsBuilderImpl.from$default(sceneTransitionsBuilderImpl, SceneKeys.QuickQuickSettings, SceneKeys.QuickSettings, null, null, null, new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose, c == true ? 1 : 0), 60);
                sceneTransitionsBuilderImpl.transition(null, SceneKeys.EditMode, (30 & 2) != 0 ? null : null, (30 & 4) != 0 ? null : null, null, new QSFragmentCompose$$ExternalSyntheticLambda9(1));
                break;
            case 1:
                TransitionBuilderImpl transitionBuilderImpl = (TransitionBuilderImpl) obj;
                int i2 = QSFragmentCompose.$r8$clinit;
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose.viewModel;
                if (qSFragmentComposeViewModel == null) {
                    qSFragmentComposeViewModel = null;
                }
                QSFragmentCompose$CollapsableQuickSettingsSTL$sceneState$2$1$1$1 qSFragmentCompose$CollapsableQuickSettingsSTL$sceneState$2$1$1$1 = new QSFragmentCompose$CollapsableQuickSettingsSTL$sceneState$2$1$1$1(new PropertyReference0Impl(qSFragmentComposeViewModel) { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$sceneState$2$1$1$2
                    @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                    public final Object get() {
                        QSFragmentComposeViewModel qSFragmentComposeViewModel2 = (QSFragmentComposeViewModel) this.receiver;
                        return Boolean.valueOf(qSFragmentComposeViewModel2.inFirstPageViewModel.inFirstPage && (qSFragmentComposeViewModel2.qqsMediaInRowViewModel.getShouldMediaShowInRow() || !qSFragmentComposeViewModel2.qsMediaInRowViewModel.getShouldMediaShowInRow()));
                    }
                });
                Float fValueOf = Float.valueOf(0.5f);
                if ((6 & 1) != 0) {
                    fValueOf = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = (6 & 4) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl2 = transitionBuilderImpl;
                transitionBuilderImpl2.getClass();
                transitionBuilderImpl2.range = new TransformationRange(fValueOf, (Float) null, easingKt$$ExternalSyntheticLambda0);
                ElementKeys.INSTANCE.getClass();
                transitionBuilderImpl2.fade(ElementKeys.QuickSettingsContent);
                Unit unit = Unit.INSTANCE;
                transitionBuilderImpl2.range = null;
                Float fValueOf2 = Float.valueOf(0.9f);
                if ((6 & 1) != 0) {
                    fValueOf2 = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda02 = (6 & 4) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl3 = transitionBuilderImpl;
                transitionBuilderImpl3.getClass();
                transitionBuilderImpl3.range = new TransformationRange(fValueOf2, (Float) null, easingKt$$ExternalSyntheticLambda02);
                ElementKeys.INSTANCE.getClass();
                transitionBuilderImpl3.fade(ElementKeys.FooterActions);
                Unit unit2 = Unit.INSTANCE;
                transitionBuilderImpl3.range = null;
                ElementKeys.INSTANCE.getClass();
                ElementKey elementKey = ElementKeys.QuickSettingsContent;
                ElementKey elementKey2 = ElementKeys.GridAnchor;
                TransitionBuilderImpl transitionBuilderImpl4 = transitionBuilderImpl;
                transitionBuilderImpl4.getClass();
                transitionBuilderImpl4.addTransformation(elementKey, new AnchoredTranslate.Factory(elementKey2));
                ElementKey$Companion$withIdentity$1 elementKey$Companion$withIdentity$1 = ElementKeys.TileElementMatcher;
                boolean zBooleanValue = (4 & 2) == 0 ? ((Boolean) qSFragmentCompose$CollapsableQuickSettingsSTL$sceneState$2$1$1$1.invoke()).booleanValue() : true;
                transitionBuilderImpl.getClass();
                transitionBuilderImpl.addTransformation(elementKey$Companion$withIdentity$1, new SharedElementTransformation.Factory(elementKey$Companion$withIdentity$1, zBooleanValue, null));
                Float fValueOf3 = Float.valueOf(0.6f);
                if ((5 & 2) != 0) {
                    fValueOf3 = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda03 = (4 & 5) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl5 = transitionBuilderImpl;
                transitionBuilderImpl5.getClass();
                transitionBuilderImpl5.range = new TransformationRange((Float) null, fValueOf3, easingKt$$ExternalSyntheticLambda03);
                SceneKeys.INSTANCE.getClass();
                transitionBuilderImpl5.fade(SceneKeys.QqsTileElementMatcher);
                Unit unit3 = Unit.INSTANCE;
                transitionBuilderImpl5.range = null;
                SceneKeys.INSTANCE.getClass();
                transitionBuilderImpl4.addTransformation(SceneKeys.QqsTileElementMatcher, new AnchoredTranslate.Factory(elementKey2));
                break;
            case 2:
                BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(qSFragmentCompose), null, null, new QSFragmentCompose$QuickSettingsElement$1$1$1$1$1(qSFragmentCompose, null), 3);
                break;
            case 3:
                qSFragmentCompose.qqsVisible.updateState(null, Boolean.TRUE);
                break;
            case 4:
                SceneTransitionLayoutImpl$updateContents$1 sceneTransitionLayoutImpl$updateContents$1 = (SceneTransitionLayoutImpl$updateContents$1) obj;
                int i3 = QSFragmentCompose.$r8$clinit;
                SceneKeys.INSTANCE.getClass();
                SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, SceneKeys.QuickSettings, null, null, new ComposableLambdaImpl(-1771293803, true, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x005b  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        final ContentScope contentScope = (ContentScope) obj2;
                        Composer composer = (Composer) obj3;
                        int iIntValue = ((Number) obj4).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer).changed(contentScope) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:352)");
                                }
                                Unit unit4 = Unit.INSTANCE;
                                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                                composerImpl2.startReplaceGroup(1111031163);
                                final QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                boolean zChangedInstance = composerImpl2.changedInstance(qSFragmentCompose2);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                if (!zChangedInstance) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1$1$1(qSFragmentCompose2, null);
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    }
                                    composerImpl2.end(false);
                                    EffectsKt.LaunchedEffect(composerImpl2, unit4, (Function2) objRememberedValue);
                                    SceneKeys.INSTANCE.getClass();
                                    contentScope.Element(SceneKeys.QuickSettings.rootElementKey, Modifier.Companion, ComposableLambdaKt.rememberComposableLambda(12681389, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1.2
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            Composer composer2 = (Composer) obj6;
                                            if ((((Number) obj7).intValue() & 17) == 16) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:353)");
                                                    }
                                                    int i4 = QSFragmentCompose.$r8$clinit;
                                                    qSFragmentCompose2.QuickSettingsElement(contentScope, null, composer2, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2), composerImpl2, ((iIntValue << 9) & 7168) | 432);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }), 6);
                SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, SceneKeys.QuickQuickSettings, null, null, new ComposableLambdaImpl(2092391116, true, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x005b  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        final ContentScope contentScope = (ContentScope) obj2;
                        Composer composer = (Composer) obj3;
                        int iIntValue = ((Number) obj4).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer).changed(contentScope) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:357)");
                                }
                                Unit unit4 = Unit.INSTANCE;
                                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                                composerImpl2.startReplaceGroup(1111038812);
                                final QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                boolean zChangedInstance = composerImpl2.changedInstance(qSFragmentCompose2);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                if (!zChangedInstance) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2$1$1(qSFragmentCompose2, null);
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    }
                                    composerImpl2.end(false);
                                    EffectsKt.LaunchedEffect(composerImpl2, unit4, (Function2) objRememberedValue);
                                    SceneKeys.INSTANCE.getClass();
                                    contentScope.Element(SceneKeys.QuickQuickSettings.rootElementKey, Modifier.Companion, ComposableLambdaKt.rememberComposableLambda(-1494367772, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2.2
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                            Composer composer2 = (Composer) obj6;
                                            if ((((Number) obj7).intValue() & 17) == 16) {
                                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                if (composerImpl3.getSkipping()) {
                                                    composerImpl3.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:360)");
                                                    }
                                                    int i4 = QSFragmentCompose.$r8$clinit;
                                                    qSFragmentCompose2.QuickQuickSettingsElement(contentScope, null, composer2, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl2), composerImpl2, ((iIntValue << 9) & 7168) | 432);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }), 6);
                SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, SceneKeys.EditMode, null, null, new ComposableLambdaImpl(-56009715, true, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$3
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        ContentScope contentScope = (ContentScope) obj2;
                        Composer composer = (Composer) obj3;
                        int iIntValue = ((Number) obj4).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer).changed(contentScope) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:364)");
                                }
                                SceneKeys.INSTANCE.getClass();
                                ElementKey elementKey3 = SceneKeys.EditMode.rootElementKey;
                                Modifier.Companion companion = Modifier.Companion;
                                final QSFragmentCompose qSFragmentCompose2 = qSFragmentCompose;
                                contentScope.Element(elementKey3, companion, ComposableLambdaKt.rememberComposableLambda(652198693, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$3.1
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                        Composer composer2 = (Composer) obj6;
                                        if ((((Number) obj7).intValue() & 17) == 16) {
                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                            if (composerImpl2.getSkipping()) {
                                                composerImpl2.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:364)");
                                                }
                                                int i4 = QSFragmentCompose.$r8$clinit;
                                                qSFragmentCompose2.EditModeElement(null, composer2, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer), composer, ((iIntValue << 9) & 7168) | 432);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }), 14);
                break;
            case 5:
                GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj;
                QSFragmentComposeViewModel qSFragmentComposeViewModel2 = qSFragmentCompose.viewModel;
                ((ReusableGraphicsLayerScope) graphicsLayerScope).setAlpha(((Number) (qSFragmentComposeViewModel2 != null ? qSFragmentComposeViewModel2 : null).viewAlpha$delegate.getValue()).floatValue());
                break;
            case 6:
                Offset.Companion.getClass();
                long jMo616localToScreenMKHz9U = ((LayoutCoordinates) obj).mo616localToScreenMKHz9U(0L);
                float fIntBitsToFloat = Float.intBitsToFloat((int) (jMo616localToScreenMKHz9U >> 32));
                float fMo612getSizeYbymL2g = ((int) (r15.mo612getSizeYbymL2g() >> 32)) + fIntBitsToFloat;
                float fIntBitsToFloat2 = Float.intBitsToFloat((int) (jMo616localToScreenMKHz9U & 4294967295L));
                float fMo612getSizeYbymL2g2 = ((int) (4294967295L & r15.mo612getSizeYbymL2g())) + fIntBitsToFloat2;
                QSFragmentComposeViewModel qSFragmentComposeViewModel3 = qSFragmentCompose.viewModel;
                QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentComposeViewModel3 != null ? qSFragmentComposeViewModel3 : null;
                if (qSFragmentComposeViewModel4.usingMedia) {
                    qSFragmentComposeViewModel4.qsMediaHost.currentClipping.set((int) fIntBitsToFloat, (int) fIntBitsToFloat2, (int) fMo612getSizeYbymL2g, (int) fMo612getSizeYbymL2g2);
                }
                break;
            case 7:
                break;
            case 8:
                IntSize intSize = (IntSize) obj;
                QSFragmentComposeViewModel qSFragmentComposeViewModel5 = qSFragmentCompose.viewModel;
                ((SnapshotMutableStateImpl) (qSFragmentComposeViewModel5 != null ? qSFragmentComposeViewModel5 : null).qsScrollHeight$delegate).setValue(Integer.valueOf((int) (intSize.packedValue & 4294967295L)));
                break;
            default:
                UniqueObjectHostView uniqueObjectHostView = (UniqueObjectHostView) obj;
                QSFragmentComposeViewModel qSFragmentComposeViewModel6 = qSFragmentCompose.viewModel;
                uniqueObjectHostView.setTranslationY(((Number) (qSFragmentComposeViewModel6 != null ? qSFragmentComposeViewModel6 : null).qsMediaTranslationY$delegate.getValue()).floatValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
