package com.android.systemui.qs.composefragment;

import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.EasingKt$$ExternalSyntheticLambda0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
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
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSFragmentCompose f$0;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda4(QSFragmentCompose qSFragmentCompose, int i) {
        this.$r8$classId = i;
        this.f$0 = qSFragmentCompose;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean z = true;
        final QSFragmentCompose qSFragmentCompose = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = (SceneTransitionsBuilderImpl) obj;
                int i = QSFragmentCompose.$r8$clinit;
                SceneKeys.INSTANCE.getClass();
                SceneTransitionsBuilderImpl.from$default(sceneTransitionsBuilderImpl, SceneKeys.QuickQuickSettings, SceneKeys.QuickSettings, null, null, null, new QSFragmentCompose$$ExternalSyntheticLambda4(qSFragmentCompose, z ? 1 : 0), 60);
                sceneTransitionsBuilderImpl.transition(null, SceneKeys.EditMode, (r14 & 2) != 0 ? null : null, (r14 & 4) != 0 ? null : null, null, new QSFragmentCompose$$ExternalSyntheticLambda9(1));
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
                Float valueOf = Float.valueOf(0.5f);
                if ((6 & 1) != 0) {
                    valueOf = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda0 = (6 & 4) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl2 = transitionBuilderImpl;
                transitionBuilderImpl2.getClass();
                transitionBuilderImpl2.range = new TransformationRange(valueOf, (Float) null, easingKt$$ExternalSyntheticLambda0);
                ElementKeys.INSTANCE.getClass();
                transitionBuilderImpl2.fade(ElementKeys.QuickSettingsContent);
                Unit unit = Unit.INSTANCE;
                transitionBuilderImpl2.range = null;
                Float valueOf2 = Float.valueOf(0.9f);
                if ((6 & 1) != 0) {
                    valueOf2 = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda02 = (6 & 4) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl3 = transitionBuilderImpl;
                transitionBuilderImpl3.getClass();
                transitionBuilderImpl3.range = new TransformationRange(valueOf2, (Float) null, easingKt$$ExternalSyntheticLambda02);
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
                boolean booleanValue = (4 & 2) == 0 ? ((Boolean) qSFragmentCompose$CollapsableQuickSettingsSTL$sceneState$2$1$1$1.invoke()).booleanValue() : true;
                transitionBuilderImpl.getClass();
                transitionBuilderImpl.addTransformation(elementKey$Companion$withIdentity$1, new SharedElementTransformation.Factory(elementKey$Companion$withIdentity$1, booleanValue, null));
                Float valueOf3 = Float.valueOf(0.6f);
                if ((5 & 2) != 0) {
                    valueOf3 = null;
                }
                EasingKt$$ExternalSyntheticLambda0 easingKt$$ExternalSyntheticLambda03 = (4 & 5) != 0 ? EasingKt.LinearEasing : null;
                TransitionBuilderImpl transitionBuilderImpl5 = transitionBuilderImpl;
                transitionBuilderImpl5.getClass();
                transitionBuilderImpl5.range = new TransformationRange((Float) null, valueOf3, easingKt$$ExternalSyntheticLambda03);
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
                    /* JADX WARN: Code restructure failed: missing block: B:22:0x0059, code lost:
                    
                        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
                     */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9) {
                        /*
                            r6 = this;
                            r0 = r7
                            com.android.compose.animation.scene.ContentScope r0 = (com.android.compose.animation.scene.ContentScope) r0
                            androidx.compose.runtime.Composer r8 = (androidx.compose.runtime.Composer) r8
                            java.lang.Number r9 = (java.lang.Number) r9
                            int r7 = r9.intValue()
                            r9 = r7 & 6
                            if (r9 != 0) goto L1c
                            r9 = r8
                            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                            boolean r9 = r9.changed(r0)
                            if (r9 == 0) goto L1a
                            r9 = 4
                            goto L1b
                        L1a:
                            r9 = 2
                        L1b:
                            r7 = r7 | r9
                        L1c:
                            r9 = r7 & 19
                            r1 = 18
                            if (r9 != r1) goto L30
                            r9 = r8
                            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                            boolean r1 = r9.getSkipping()
                            if (r1 != 0) goto L2c
                            goto L30
                        L2c:
                            r9.skipToGroupEnd()
                            goto L96
                        L30:
                            boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r9 == 0) goto L3b
                            java.lang.String r9 = "com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:352)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r9)
                        L3b:
                            kotlin.Unit r9 = kotlin.Unit.INSTANCE
                            r4 = r8
                            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                            r8 = 1111031163(0x4238fd7b, float:46.24754)
                            r4.startReplaceGroup(r8)
                            com.android.systemui.qs.composefragment.QSFragmentCompose r6 = com.android.systemui.qs.composefragment.QSFragmentCompose.this
                            boolean r8 = r4.changedInstance(r6)
                            java.lang.Object r1 = r4.rememberedValue()
                            if (r8 != 0) goto L5b
                            androidx.compose.runtime.Composer$Companion r8 = androidx.compose.runtime.Composer.Companion
                            r8.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r1 != r8) goto L64
                        L5b:
                            com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1$1$1 r1 = new com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1$1$1
                            r8 = 0
                            r1.<init>(r6, r8)
                            r4.updateRememberedValue(r1)
                        L64:
                            kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
                            r8 = 0
                            r4.end(r8)
                            androidx.compose.runtime.EffectsKt.LaunchedEffect(r4, r9, r1)
                            com.android.systemui.qs.composefragment.SceneKeys r8 = com.android.systemui.qs.composefragment.SceneKeys.INSTANCE
                            r8.getClass()
                            com.android.compose.animation.scene.SceneKey r8 = com.android.systemui.qs.composefragment.SceneKeys.QuickSettings
                            com.android.compose.animation.scene.ElementKey r1 = r8.rootElementKey
                            androidx.compose.ui.Modifier$Companion r2 = androidx.compose.ui.Modifier.Companion
                            com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1$2 r8 = new com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1$2
                            r8.<init>()
                            r6 = 12681389(0xc180ad, float:1.7770411E-38)
                            androidx.compose.runtime.internal.ComposableLambdaImpl r3 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r6, r8, r4)
                            int r6 = r7 << 9
                            r6 = r6 & 7168(0x1c00, float:1.0045E-41)
                            r5 = r6 | 432(0x1b0, float:6.05E-43)
                            r0.Element(r1, r2, r3, r4, r5)
                            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r6 == 0) goto L96
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L96:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), 6);
                SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, SceneKeys.QuickQuickSettings, null, null, new ComposableLambdaImpl(2092391116, true, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2
                    /* JADX WARN: Code restructure failed: missing block: B:22:0x0059, code lost:
                    
                        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
                     */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9) {
                        /*
                            r6 = this;
                            r0 = r7
                            com.android.compose.animation.scene.ContentScope r0 = (com.android.compose.animation.scene.ContentScope) r0
                            androidx.compose.runtime.Composer r8 = (androidx.compose.runtime.Composer) r8
                            java.lang.Number r9 = (java.lang.Number) r9
                            int r7 = r9.intValue()
                            r9 = r7 & 6
                            if (r9 != 0) goto L1c
                            r9 = r8
                            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                            boolean r9 = r9.changed(r0)
                            if (r9 == 0) goto L1a
                            r9 = 4
                            goto L1b
                        L1a:
                            r9 = 2
                        L1b:
                            r7 = r7 | r9
                        L1c:
                            r9 = r7 & 19
                            r1 = 18
                            if (r9 != r1) goto L30
                            r9 = r8
                            androidx.compose.runtime.ComposerImpl r9 = (androidx.compose.runtime.ComposerImpl) r9
                            boolean r1 = r9.getSkipping()
                            if (r1 != 0) goto L2c
                            goto L30
                        L2c:
                            r9.skipToGroupEnd()
                            goto L96
                        L30:
                            boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r9 == 0) goto L3b
                            java.lang.String r9 = "com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:357)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r9)
                        L3b:
                            kotlin.Unit r9 = kotlin.Unit.INSTANCE
                            r4 = r8
                            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                            r8 = 1111038812(0x42391b5c, float:46.27672)
                            r4.startReplaceGroup(r8)
                            com.android.systemui.qs.composefragment.QSFragmentCompose r6 = com.android.systemui.qs.composefragment.QSFragmentCompose.this
                            boolean r8 = r4.changedInstance(r6)
                            java.lang.Object r1 = r4.rememberedValue()
                            if (r8 != 0) goto L5b
                            androidx.compose.runtime.Composer$Companion r8 = androidx.compose.runtime.Composer.Companion
                            r8.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r1 != r8) goto L64
                        L5b:
                            com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2$1$1 r1 = new com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2$1$1
                            r8 = 0
                            r1.<init>(r6, r8)
                            r4.updateRememberedValue(r1)
                        L64:
                            kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
                            r8 = 0
                            r4.end(r8)
                            androidx.compose.runtime.EffectsKt.LaunchedEffect(r4, r9, r1)
                            com.android.systemui.qs.composefragment.SceneKeys r8 = com.android.systemui.qs.composefragment.SceneKeys.INSTANCE
                            r8.getClass()
                            com.android.compose.animation.scene.SceneKey r8 = com.android.systemui.qs.composefragment.SceneKeys.QuickQuickSettings
                            com.android.compose.animation.scene.ElementKey r1 = r8.rootElementKey
                            androidx.compose.ui.Modifier$Companion r2 = androidx.compose.ui.Modifier.Companion
                            com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2$2 r8 = new com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2$2
                            r8.<init>()
                            r6 = -1494367772(0xffffffffa6edc1e4, float:-1.6497733E-15)
                            androidx.compose.runtime.internal.ComposableLambdaImpl r3 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r6, r8, r4)
                            int r6 = r7 << 9
                            r6 = r6 & 7168(0x1c00, float:1.0045E-41)
                            r5 = r6 | 432(0x1b0, float:6.05E-43)
                            r0.Element(r1, r2, r3, r4, r5)
                            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r6 == 0) goto L96
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L96:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }), 6);
                SceneTransitionLayoutImpl$updateContents$1.scene$default(sceneTransitionLayoutImpl$updateContents$1, SceneKeys.EditMode, null, null, new ComposableLambdaImpl(-56009715, true, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$3
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                        ContentScope contentScope = (ContentScope) obj2;
                        Composer composer = (Composer) obj3;
                        int intValue = ((Number) obj4).intValue();
                        if ((intValue & 6) == 0) {
                            intValue |= ((ComposerImpl) composer).changed(contentScope) ? 4 : 2;
                        }
                        if ((intValue & 19) == 18) {
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:364)");
                        }
                        SceneKeys.INSTANCE.getClass();
                        ElementKey elementKey3 = SceneKeys.EditMode.rootElementKey;
                        Modifier.Companion companion = Modifier.Companion;
                        final QSFragmentCompose qSFragmentCompose2 = QSFragmentCompose.this;
                        contentScope.Element(elementKey3, companion, ComposableLambdaKt.rememberComposableLambda(652198693, new Function3() { // from class: com.android.systemui.qs.composefragment.QSFragmentCompose$CollapsableQuickSettingsSTL$2$1$3.1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                Composer composer2 = (Composer) obj6;
                                if ((((Number) obj7).intValue() & 17) == 16) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QSFragmentCompose.CollapsableQuickSettingsSTL.<anonymous>.<anonymous>.<anonymous>.<anonymous> (QSFragmentCompose.kt:364)");
                                }
                                int i4 = QSFragmentCompose.$r8$clinit;
                                QSFragmentCompose.this.EditModeElement(null, composer2, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer), composer, ((intValue << 9) & 7168) | 432);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
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
                long mo614localToScreenMKHz9U = ((LayoutCoordinates) obj).mo614localToScreenMKHz9U(0L);
                float intBitsToFloat = Float.intBitsToFloat((int) (mo614localToScreenMKHz9U >> 32));
                float mo610getSizeYbymL2g = ((int) (r15.mo610getSizeYbymL2g() >> 32)) + intBitsToFloat;
                float intBitsToFloat2 = Float.intBitsToFloat((int) (mo614localToScreenMKHz9U & 4294967295L));
                float mo610getSizeYbymL2g2 = ((int) (4294967295L & r15.mo610getSizeYbymL2g())) + intBitsToFloat2;
                QSFragmentComposeViewModel qSFragmentComposeViewModel3 = qSFragmentCompose.viewModel;
                QSFragmentComposeViewModel qSFragmentComposeViewModel4 = qSFragmentComposeViewModel3 != null ? qSFragmentComposeViewModel3 : null;
                if (qSFragmentComposeViewModel4.usingMedia) {
                    qSFragmentComposeViewModel4.qsMediaHost.currentClipping.set((int) intBitsToFloat, (int) intBitsToFloat2, (int) mo610getSizeYbymL2g, (int) mo610getSizeYbymL2g2);
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
