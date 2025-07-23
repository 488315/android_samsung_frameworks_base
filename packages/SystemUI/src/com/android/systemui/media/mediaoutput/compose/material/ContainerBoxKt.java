package com.android.systemui.media.mediaoutput.compose.material;

import android.content.Context;
import android.graphics.Rect;
import android.util.SizeF;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.AnimationVector4D;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsets_androidKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpOffset;
import androidx.compose.ui.unit.DpRect;
import androidx.compose.ui.unit.DpSize;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.media.mediaoutput.compose.common.TransitionInfo;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.UnitExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ContainerBoxKt {
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0254, code lost:
    
        if (r3 != null) goto L138;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ActionButton(final kotlin.jvm.functions.Function1 r16, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel r17, com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel r18, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r19, boolean r20, androidx.compose.runtime.Composer r21, int r22) {
        /*
            Method dump skipped, instructions count: 740
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt.ActionButton(kotlin.jvm.functions.Function1, com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel, com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel, boolean, androidx.compose.runtime.Composer, int):void");
    }

    public static final void ContainerBox(boolean z, MediaSessionViewModel mediaSessionViewModel, MediaDeviceViewModel mediaDeviceViewModel, LabsViewModel labsViewModel, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        Object failure;
        MediaSessionViewModel mediaSessionViewModel2;
        Object failure2;
        Object failure3;
        LabsViewModel labsViewModel2;
        MediaDeviceViewModel mediaDeviceViewModel2;
        MediaDeviceViewModel mediaDeviceViewModel3;
        float f;
        DpRect dpRect;
        boolean z2;
        ComposableLambdaImpl composableLambdaImpl2;
        MediaDeviceViewModel mediaDeviceViewModel4;
        MediaSessionViewModel mediaSessionViewModel3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1717396094);
        if (((i | 1168) & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mediaSessionViewModel3 = mediaSessionViewModel;
            mediaDeviceViewModel4 = mediaDeviceViewModel;
            composableLambdaImpl2 = composableLambdaImpl;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i2 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                mediaSessionViewModel2 = (MediaSessionViewModel) viewModel;
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current2 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current2 == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras2 = current2 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current2).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    failure2 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th2) {
                    int i4 = Result.$r8$clinit;
                    failure2 = new Result.Failure(th2);
                }
                Throwable m3422exceptionOrNullimpl2 = Result.m3422exceptionOrNullimpl(failure2);
                if (m3422exceptionOrNullimpl2 != null) {
                    m3422exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                ViewModelProvider.Factory factory2 = (ViewModelProvider.Factory) failure2;
                if (factory2 == null) {
                    factory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, MediaDeviceViewModel.class, factory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                MediaDeviceViewModel mediaDeviceViewModel5 = (MediaDeviceViewModel) viewModel2;
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current3 = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current3 == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras3 = current3 instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current3).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    failure3 = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th3) {
                    int i5 = Result.$r8$clinit;
                    failure3 = new Result.Failure(th3);
                }
                Throwable m3422exceptionOrNullimpl3 = Result.m3422exceptionOrNullimpl(failure3);
                if (m3422exceptionOrNullimpl3 != null) {
                    m3422exceptionOrNullimpl3.printStackTrace();
                }
                if (failure3 instanceof Result.Failure) {
                    failure3 = null;
                }
                ViewModelProvider.Factory factory3 = (ViewModelProvider.Factory) failure3;
                if (factory3 == null) {
                    factory3 = ViewModelKt.createDaggerViewModelFactory(current3);
                }
                ViewModel viewModel3 = ViewModelKt.get(current3, LabsViewModel.class, factory3, defaultViewModelCreationExtras3);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel3;
                mediaDeviceViewModel2 = mediaDeviceViewModel5;
            } else {
                composerImpl.skipToGroupEnd();
                mediaSessionViewModel2 = mediaSessionViewModel;
                mediaDeviceViewModel2 = mediaDeviceViewModel;
                labsViewModel2 = labsViewModel;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ContainerBox (ContainerBox.kt:92)");
            }
            Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            MutableState collectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 48, 2);
            composerImpl.startReplaceGroup(461483289);
            MutableState collectAsState2 = ((Boolean) collectAsState.getValue()).booleanValue() ? SnapshotStateKt.collectAsState(mediaSessionViewModel2.sessionControllersFlow, EmptyList.INSTANCE, null, composerImpl, 48, 2) : SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(461489860);
            MutableState mutableStateOf$default = (feature.isFullScreen || !(z || ((Boolean) collectAsState.getValue()).booleanValue())) ? SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE) : SnapshotStateKt.collectAsState(mediaDeviceViewModel2.sessionControllersFlow, EmptyList.INSTANCE, null, composerImpl, 48, 2);
            composerImpl.end(false);
            Modifier.Companion companion = Modifier.Companion;
            Modifier fillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, fillMaxSize);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            labsViewModel = labsViewModel2;
            MediaSessionViewModel mediaSessionViewModel4 = mediaSessionViewModel2;
            long j = ((DpSize) composerImpl.consume(CompositionExtKt.LocalRootSize)).packedValue;
            SizeF containerFraction = containerFraction(feature.isFullScreen, composerImpl, 0);
            composerImpl.startReplaceGroup(-876158458);
            composerImpl.startReplaceGroup(-876158474);
            Feature.Companion.getClass();
            boolean z3 = feature.from == 10 || feature.isFullScreen || ConfigurationExtKt.isFold(composerImpl);
            composerImpl.end(false);
            boolean z4 = z3 || ConfigurationExtKt.isPortrait(composerImpl);
            composerImpl.end(false);
            float f2 = z4 ? 0 : 24;
            Dp.Companion companion2 = Dp.Companion;
            composerImpl.startReplaceGroup(-876154436);
            boolean z5 = !ConfigurationExtKt.isFold(composerImpl) && ConfigurationExtKt.isLandscape(composerImpl);
            composerImpl.end(false);
            float f3 = (!z5 || (((List) collectAsState2.getValue()).size() <= 1 && ((List) mutableStateOf$default.getValue()).isEmpty())) ? 0 : 58;
            composerImpl.startReplaceGroup(-876149581);
            composerImpl.startReplaceGroup(-876149598);
            boolean z6 = feature.isFullScreen || ConfigurationExtKt.isFold(composerImpl);
            composerImpl.end(false);
            boolean z7 = z6 || ConfigurationExtKt.isLandscape(composerImpl);
            composerImpl.end(false);
            float f4 = z7 ? 0 : 15;
            composerImpl.startReplaceGroup(-876146094);
            composerImpl.startReplaceGroup(-876146106);
            boolean z8 = feature.isFullScreen || ConfigurationExtKt.isPortrait(composerImpl);
            composerImpl.end(false);
            boolean z9 = z8 || ConfigurationExtKt.isFold(composerImpl);
            composerImpl.end(false);
            if (z9) {
                mediaDeviceViewModel3 = mediaDeviceViewModel2;
                f = 0;
            } else {
                mediaDeviceViewModel3 = mediaDeviceViewModel2;
                f = 10;
            }
            float f5 = 2;
            long m838DpSizeYgX7TsA = DpKt.m838DpSizeYgX7TsA((containerFraction.getWidth() * DpSize.m845getWidthD9Ej5fM(j)) - (f4 * f5), (((containerFraction.getHeight() * DpSize.m844getHeightD9Ej5fM(j)) - f2) - (f * f5)) - f3);
            float m845getWidthD9Ej5fM = (DpSize.m845getWidthD9Ej5fM(j) - DpSize.m845getWidthD9Ej5fM(m838DpSizeYgX7TsA)) / f5;
            composerImpl.startReplaceGroup(-876132390);
            boolean z10 = ConfigurationExtKt.isPortrait(composerImpl) || ConfigurationExtKt.isFold(composerImpl);
            composerImpl.end(false);
            long floatToRawIntBits = (Float.floatToRawIntBits(m845getWidthD9Ej5fM) << 32) | (Float.floatToRawIntBits(z10 ? (DpSize.m844getHeightD9Ej5fM(j) - DpSize.m844getHeightD9Ej5fM(m838DpSizeYgX7TsA)) / f5 : f + f2) & 4294967295L);
            int i6 = DpOffset.$r8$clinit;
            final MediaOutputState mediaOutputState = (MediaOutputState) composerImpl.consume(CompositionExtKt.LocalMediaOutputState);
            MediaOutputState.Companion.getClass();
            State rememberShownState = MediaOutputState.Companion.rememberShownState(composerImpl);
            composerImpl.startReplaceGroup(-1956660936);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.common.MediaOutputState.Companion.rememberEnterState (Feature.kt:140)");
            }
            State rememberMediaOutputState = MediaOutputState.Companion.rememberMediaOutputState(composerImpl);
            composerImpl.startReplaceGroup(-1261580723);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Object obj = Composer.Companion.Empty;
            if (rememberedValue == obj) {
                rememberedValue = SnapshotStateKt.derivedStateOf(new MediaOutputState$Companion$$ExternalSyntheticLambda0(rememberMediaOutputState, 1));
                composerImpl.updateRememberedValue(rememberedValue);
            }
            State state = (State) rememberedValue;
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            Rect rect = ((Feature) ((TransitionInfo) composerImpl.consume(CompositionExtKt.LocalTransitionInfo))).fromRect;
            composerImpl.startReplaceGroup(-876121300);
            if (rect == null) {
                z2 = false;
                dpRect = null;
            } else {
                composerImpl.startReplaceGroup(431806407);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.toDpRect (UnitExt.kt:25)");
                }
                Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                dpRect = new DpRect(density.mo54toDpu2uoSUM(rect.left), density.mo54toDpu2uoSUM(rect.top), density.mo54toDpu2uoSUM(rect.right), density.mo54toDpu2uoSUM(rect.bottom), null);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                z2 = false;
                composerImpl.end(false);
            }
            composerImpl.end(z2);
            if (((Boolean) state.getValue()).booleanValue() || dpRect == null) {
                dpRect = new DpRect(floatToRawIntBits, m838DpSizeYgX7TsA, null);
            }
            AnimationSpec snap$default = ((Boolean) rememberShownState.getValue()).booleanValue() ? AnimationSpecKt.snap$default() : !((Boolean) state.getValue()).booleanValue() ? AnimationSpecKt.spring$default(0.9f, 400.0f, null, 4) : AnimationSpecKt.spring$default(0.80829036f, 300.0f, null, 4);
            composerImpl.startReplaceGroup(-876106079);
            boolean changedInstance = composerImpl.changedInstance(mediaOutputState);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue2 == obj) {
                rememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        ((Feature) MediaOutputState.this).setState(MediaOutputState.StateInfo.Shown);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            Function1 function1 = (Function1) rememberedValue2;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1264146584);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.animateDpRectAsState (DpRectExt.kt:18)");
            }
            int i7 = DpRect.$r8$clinit;
            final int i8 = 0;
            final int i9 = 1;
            State animateValueAsState = AnimateAsStateKt.animateValueAsState(dpRect, VectorConvertersKt.TwoWayConverter(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    switch (i8) {
                        case 0:
                            DpRect dpRect2 = (DpRect) obj2;
                            return new AnimationVector4D(dpRect2.left, dpRect2.top, dpRect2.right, dpRect2.bottom);
                        default:
                            AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                            float f6 = animationVector4D.v1;
                            Dp.Companion companion3 = Dp.Companion;
                            return new DpRect(f6, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
                    }
                }
            }, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    switch (i9) {
                        case 0:
                            DpRect dpRect2 = (DpRect) obj2;
                            return new AnimationVector4D(dpRect2.left, dpRect2.top, dpRect2.right, dpRect2.bottom);
                        default:
                            AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                            float f6 = animationVector4D.v1;
                            Dp.Companion companion3 = Dp.Companion;
                            return new DpRect(f6, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
                    }
                }
            }), snap$default, null, "RectAnimation", function1, composerImpl, 0, 8);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            Modifier m114offsetVpY3zN4 = OffsetKt.m114offsetVpY3zN4(companion, ((DpRect) animateValueAsState.getValue()).left, ((DpRect) animateValueAsState.getValue()).top);
            DpRect dpRect2 = (DpRect) animateValueAsState.getValue();
            float f6 = dpRect2.right - dpRect2.left;
            DpRect dpRect3 = (DpRect) animateValueAsState.getValue();
            Modifier clip = ClipKt.clip(SizeKt.m142sizeInqDBjuR0$default(m114offsetVpY3zN4, 0.0f, 0.0f, f6, dpRect3.bottom - dpRect3.top, 3), RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(feature.isFullScreen ? 0 : 26));
            MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, clip);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            composableLambdaImpl2 = composableLambdaImpl;
            composableLambdaImpl2.invoke((Object) composerImpl, (Object) 6);
            composerImpl.end(true);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            mediaDeviceViewModel4 = mediaDeviceViewModel3;
            mediaSessionViewModel3 = mediaSessionViewModel4;
        }
        LabsViewModel labsViewModel3 = labsViewModel;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ContainerBoxKt$$ExternalSyntheticLambda1(z, mediaSessionViewModel3, mediaDeviceViewModel4, labsViewModel3, composableLambdaImpl2, i);
        }
    }

    public static final void SettingButton(final Function1 function1, final boolean z, Function0 function0, LabsViewModel labsViewModel, Composer composer, final int i, final int i2) {
        Function0 function02;
        int i3;
        Object failure;
        LabsViewModel labsViewModel2;
        final LabsViewModel labsViewModel3;
        final Function0 function03;
        Integer num;
        boolean z2 = false;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(938782776);
        int i4 = i | (composerImpl.changedInstance(function1) ? 4 : 2);
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 = i4 | 384;
            function02 = function0;
        } else {
            function02 = function0;
            i3 = i4 | (composerImpl.changedInstance(function02) ? 256 : 128);
        }
        if (((i3 | 1024) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            labsViewModel3 = labsViewModel;
            function03 = function02;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                if (i5 != 0) {
                    function02 = null;
                }
                composerImpl.startReplaceGroup(1487631618);
                LocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
                if (current == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
                }
                CreationExtras defaultViewModelCreationExtras = current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
                try {
                    int i6 = Result.$r8$clinit;
                    failure = (ViewModelProvider.Factory) composerImpl.consume(CompositionExtKt.LocalViewModelProviderFactory);
                } catch (Throwable th) {
                    int i7 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factory = (ViewModelProvider.Factory) failure;
                if (factory == null) {
                    factory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
                labsViewModel2 = labsViewModel;
            }
            final Function0 function04 = function02;
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.SettingButton (ContainerBox.kt:173)");
            }
            final Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Function0 function05 = (Function0) composerImpl.consume(CompositionExtKt.LocalLargeScreenHeaderHeight);
            int intValue = (function05 == null || (num = (Integer) function05.invoke()) == null) ? 0 : num.intValue();
            composerImpl.startReplaceGroup(-709095322);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new ContainerBoxKt$$ExternalSyntheticLambda2(0);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            composerImpl.end(false);
            MutableState CutoutInsets = ConfigurationExtKt.CutoutInsets(composerImpl, (Function1) rememberedValue);
            WindowInsets.Companion companion = WindowInsets.Companion;
            int top = WindowInsets_androidKt.getSafeDrawing(composerImpl).getTop((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity));
            Feature feature2 = feature.isWindow ? feature : null;
            composerImpl.startReplaceGroup(-709090035);
            Float valueOf = feature2 == null ? null : Float.valueOf(UnitExtKt.toDp(top, composerImpl));
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-709090953);
            if (valueOf == null) {
                int intValue2 = ((Number) CutoutInsets.getValue()).intValue() + intValue;
                Integer valueOf2 = Integer.valueOf(intValue2);
                if (intValue2 <= 0) {
                    valueOf2 = null;
                }
                Dp m835boximpl = valueOf2 == null ? null : Dp.m835boximpl(UnitExtKt.toDp(valueOf2.intValue(), composerImpl));
                valueOf = m835boximpl != null ? Float.valueOf(m835boximpl.value) : null;
            }
            composerImpl.end(false);
            final float floatValue = valueOf != null ? valueOf.floatValue() : 52.0f;
            DataStoreDebugLabsExt$special$$inlined$map$3 dataStoreDebugLabsExt$special$$inlined$map$3 = labsViewModel2.isSupportMultipleMediaSession;
            Boolean bool = Boolean.FALSE;
            MutableState collectAsState = SnapshotStateKt.collectAsState(dataStoreDebugLabsExt$special$$inlined$map$3, bool, null, composerImpl, 48, 2);
            final MutableState collectAsState2 = SnapshotStateKt.collectAsState(labsViewModel2.isActionOpenOutputSwitcher, bool, null, composerImpl, 48, 2);
            if (!feature.isFullScreen && (z || ((Boolean) collectAsState.getValue()).booleanValue())) {
                DeviceUtils.INSTANCE.getClass();
                if (DeviceUtils.getSupportCastSetting(context) || ((Boolean) DeviceUtils.supportMusicShare$delegate.getValue()).booleanValue()) {
                    z2 = true;
                }
            }
            AnimatedVisibilityKt.AnimatedVisibility(z2, null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(1560093968, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1
                /* JADX WARN: Code restructure failed: missing block: B:48:0x0168, code lost:
                
                    if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L61;
                 */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r18, java.lang.Object r19, java.lang.Object r20) {
                    /*
                        Method dump skipped, instructions count: 498
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 200064, 18);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            labsViewModel3 = labsViewModel2;
            function03 = function04;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(z, function03, labsViewModel3, i, i2) { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$$ExternalSyntheticLambda3
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ Function0 f$2;
                public final /* synthetic */ LabsViewModel f$3;
                public final /* synthetic */ int f$5;

                {
                    this.f$5 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    LabsViewModel labsViewModel4 = this.f$3;
                    int i8 = this.f$5;
                    ContainerBoxKt.SettingButton(Function1.this, this.f$1, this.f$2, labsViewModel4, (Composer) obj, updateChangedFlags, i8);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final SizeF containerFraction(boolean z, Composer composer, int i) {
        SizeF sizeF;
        float f;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(2091848469);
        boolean z2 = true;
        if ((i & 1) != 0) {
            z = false;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.containerFraction (ContainerBox.kt:158)");
        }
        if (z) {
            sizeF = new SizeF(1.0f, 1.0f);
        } else {
            composerImpl.startReplaceGroup(-2090292225);
            composerImpl.startReplaceGroup(-2090292137);
            boolean isLandscape = ConfigurationExtKt.isLandscape(composerImpl);
            composerImpl.end(false);
            if (isLandscape) {
                f = 0.6f;
            } else {
                composerImpl.startReplaceGroup(-2090291246);
                boolean isFold = ConfigurationExtKt.isFold(composerImpl);
                composerImpl.end(false);
                f = isFold ? 0.7f : 1.0f;
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-2090289982);
            if (!ConfigurationExtKt.isPortrait(composerImpl) && !ConfigurationExtKt.isFold(composerImpl)) {
                z2 = false;
            }
            composerImpl.end(false);
            sizeF = new SizeF(f, z2 ? 0.65f : 1.0f);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return sizeF;
    }
}
