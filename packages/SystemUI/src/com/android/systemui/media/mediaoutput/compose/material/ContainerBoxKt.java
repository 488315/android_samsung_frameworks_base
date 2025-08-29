package com.android.systemui.media.mediaoutput.compose.material;

import android.content.Context;
import android.content.Intent;
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
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.OffsetKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsets_androidKt;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Applier;
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
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.focus.FocusPropertiesKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpOffset;
import androidx.compose.ui.unit.DpRect;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState;
import com.android.systemui.media.mediaoutput.compose.common.MediaOutputState$Companion$$ExternalSyntheticLambda0;
import com.android.systemui.media.mediaoutput.compose.common.TransitionInfo;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.UnitExtKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelKt;
import com.samsung.sesl.compose.component.IconButtonKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public abstract class ContainerBoxKt {
    /* JADX WARN: Removed duplicated region for block: B:128:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ActionButton(final Function1 function1, MediaSessionViewModel mediaSessionViewModel, MediaDeviceViewModel mediaDeviceViewModel, LabsViewModel labsViewModel, boolean z, Composer composer, int i) {
        Object failure;
        Object failure2;
        Object failure3;
        LabsViewModel labsViewModel2;
        MediaSessionViewModel mediaSessionViewModel2;
        MediaDeviceViewModel mediaDeviceViewModel2;
        final String str;
        MediaSessionViewModel mediaSessionViewModel3;
        MediaDeviceViewModel mediaDeviceViewModel3;
        LabsViewModel labsViewModel3;
        boolean z2 = true;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1418447888);
        if (((i | (composerImpl.changedInstance(function1) ? 4 : 2) | 1168) & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mediaSessionViewModel3 = mediaSessionViewModel;
            mediaDeviceViewModel3 = mediaDeviceViewModel;
            labsViewModel3 = labsViewModel;
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
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    thM3441exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory = (ViewModelProvider.Factory) failure;
                if (factoryCreateDaggerViewModelFactory == null) {
                    factoryCreateDaggerViewModelFactory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                MediaSessionViewModel mediaSessionViewModel4 = (MediaSessionViewModel) viewModel;
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
                Throwable thM3441exceptionOrNullimpl2 = Result.m3441exceptionOrNullimpl(failure2);
                if (thM3441exceptionOrNullimpl2 != null) {
                    thM3441exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) failure2;
                if (factoryCreateDaggerViewModelFactory2 == null) {
                    factoryCreateDaggerViewModelFactory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, MediaDeviceViewModel.class, factoryCreateDaggerViewModelFactory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                MediaDeviceViewModel mediaDeviceViewModel4 = (MediaDeviceViewModel) viewModel2;
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
                Throwable thM3441exceptionOrNullimpl3 = Result.m3441exceptionOrNullimpl(failure3);
                if (thM3441exceptionOrNullimpl3 != null) {
                    thM3441exceptionOrNullimpl3.printStackTrace();
                }
                if (failure3 instanceof Result.Failure) {
                    failure3 = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory3 = (ViewModelProvider.Factory) failure3;
                if (factoryCreateDaggerViewModelFactory3 == null) {
                    factoryCreateDaggerViewModelFactory3 = ViewModelKt.createDaggerViewModelFactory(current3);
                }
                ViewModel viewModel3 = ViewModelKt.get(current3, LabsViewModel.class, factoryCreateDaggerViewModelFactory3, defaultViewModelCreationExtras3);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel3;
                mediaSessionViewModel2 = mediaSessionViewModel4;
                mediaDeviceViewModel2 = mediaDeviceViewModel4;
            } else {
                composerImpl.skipToGroupEnd();
                mediaSessionViewModel2 = mediaSessionViewModel;
                mediaDeviceViewModel2 = mediaDeviceViewModel;
                labsViewModel2 = labsViewModel;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ActionButton (ContainerBox.kt:260)");
            }
            Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 48, 2);
            composerImpl.startReplaceGroup(-1389342121);
            MutableState mutableStateCollectAsState2 = ((Boolean) mutableStateCollectAsState.getValue()).booleanValue() ? SnapshotStateKt.collectAsState(mediaSessionViewModel2.sessionControllersFlow, EmptyList.INSTANCE, null, composerImpl, 48, 2) : SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1389335602);
            final MutableState mutableStateCollectAsState3 = !feature.isFullScreen ? SnapshotStateKt.collectAsState(mediaDeviceViewModel2.sessionControllersFlow, EmptyList.INSTANCE, null, composerImpl, 48, 2) : SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-1389328904);
            if (((Boolean) mutableStateCollectAsState.getValue()).booleanValue()) {
                List list = (List) mutableStateCollectAsState2.getValue();
                if (list.size() <= 1 && ((List) mutableStateCollectAsState3.getValue()).isEmpty()) {
                    list = null;
                }
                str = list != null ? "Control other media" : null;
            } else {
                DeviceSession deviceSession = (DeviceSession) CollectionsKt___CollectionsKt.firstOrNull((List) mutableStateCollectAsState3.getValue());
                ReadonlyStateFlow readonlyStateFlow = deviceSession != null ? ((DeviceSessionController) deviceSession).artistFlow : null;
                composerImpl.startReplaceGroup(-1389322145);
                MutableState mutableStateCollectAsState4 = readonlyStateFlow == null ? null : SnapshotStateKt.collectAsState(readonlyStateFlow, null, null, composerImpl, 48, 2);
                composerImpl.end(false);
                if (mutableStateCollectAsState4 == null) {
                    mutableStateCollectAsState4 = SnapshotStateKt.mutableStateOf$default(null);
                }
                List list2 = (List) mutableStateCollectAsState3.getValue();
                if (list2.size() <= 1) {
                    list2 = null;
                }
                String strStringResource = list2 == null ? null : StringResources_androidKt.stringResource(R.string.control_other_devices, composerImpl);
                str = strStringResource == null ? (String) mutableStateCollectAsState4.getValue() : strStringResource;
            }
            composerImpl.end(false);
            final String id = "";
            if (((Boolean) mutableStateCollectAsState.getValue()).booleanValue()) {
                if (str == null) {
                    id = null;
                }
                if (str != null || (!z && !((Boolean) mutableStateCollectAsState.getValue()).booleanValue())) {
                    z2 = false;
                }
                AnimatedVisibilityKt.AnimatedVisibility(z2, null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(1221255400, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt.ActionButton.1
                    /* JADX WARN: Removed duplicated region for block: B:52:0x0166  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        float f;
                        Composer composer2 = (Composer) obj2;
                        ((Number) obj3).intValue();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ActionButton.<anonymous> (ContainerBox.kt:292)");
                        }
                        String str2 = str;
                        if (str2 != null) {
                            final String str3 = id;
                            if (str3 != null) {
                                Modifier.Companion companion = Modifier.Companion;
                                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                composerImpl2.startReplaceGroup(-1164060407);
                                composerImpl2.startReplaceGroup(-1164060371);
                                boolean zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl2);
                                composerImpl2.end(false);
                                if (zIsPopupMode) {
                                    f = 10;
                                } else {
                                    composerImpl2.startReplaceGroup(-1726102348);
                                    composerImpl2.startReplaceGroup(-1164059398);
                                    boolean z3 = ConfigurationExtKt.isPortrait(composerImpl2) || ConfigurationExtKt.isTablet(composerImpl2);
                                    composerImpl2.end(false);
                                    boolean z4 = z3 || ConfigurationExtKt.isFolderOpened(composerImpl2);
                                    composerImpl2.end(false);
                                    f = z4 ? 68 : 10;
                                }
                                Dp.Companion companion2 = Dp.Companion;
                                float f2 = f;
                                composerImpl2.end(false);
                                Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifierFillMaxSize, 0.0f, 0.0f, 0.0f, f2, 7);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomCenter, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM129paddingqDBjuR0$default);
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
                                Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                                }
                                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function24);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
                                CornerSize CornerSize = CornerSizeKt.CornerSize(100);
                                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.m133heightInVpY3zN4$default(SizeKt.m145widthInVpY3zN4(BackgroundKt.m26backgroundbw27NRU(ClipKt.clip(companion, new RoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize)), ColorKt.cardBackground(composerImpl2), RectangleShapeKt.RectangleShape), 160, DpSize.m847getWidthD9Ej5fM(((DpSize) composerImpl2.consume(CompositionExtKt.LocalRootSize)).packedValue) * 0.8f), 52, 0.0f, 2), 16, 0.0f, 2);
                                composerImpl2.startReplaceGroup(-427347893);
                                final State state = mutableStateCollectAsState3;
                                boolean zChanged = composerImpl2.changed(state) | composerImpl2.changed(str3);
                                final Function1 function12 = function1;
                                boolean zChanged2 = zChanged | composerImpl2.changed(function12);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                if (!zChanged2) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$ActionButton$1$$ExternalSyntheticLambda0
                                            /* JADX WARN: Removed duplicated region for block: B:20:0x004a  */
                                            @Override // kotlin.jvm.functions.Function0
                                            /*
                                                Code decompiled incorrectly, please refer to instructions dump.
                                            */
                                            public final Object invoke() {
                                                Object next;
                                                String strCreateRoute;
                                                String id2;
                                                Iterator it = ((List) state.getValue()).iterator();
                                                while (true) {
                                                    if (!it.hasNext()) {
                                                        next = null;
                                                        break;
                                                    }
                                                    next = it.next();
                                                    if (Intrinsics.areEqual(((DeviceSessionController) ((DeviceSession) next)).getId(), str3)) {
                                                        break;
                                                    }
                                                }
                                                DeviceSession deviceSession2 = (DeviceSession) next;
                                                if (deviceSession2 == null || (id2 = ((DeviceSessionController) deviceSession2).getId()) == null) {
                                                    strCreateRoute = Screen.Selector.INSTANCE.createRoute("");
                                                } else {
                                                    String str4 = StringsKt__StringsKt.isBlank(id2) ? null : id2;
                                                    if (str4 == null || (strCreateRoute = Screen.TV.INSTANCE.createRoute(str4)) == null) {
                                                    }
                                                }
                                                function12.mo781invoke(strCreateRoute);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    }
                                    composerImpl2.end(false);
                                    Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM127paddingVpY3zN4$default, false, null, (Function0) objRememberedValue, 7);
                                    Arrangement arrangement = Arrangement.INSTANCE;
                                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                    arrangement.getClass();
                                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m93spacedByD5KLDUw(10, horizontal), Alignment.Companion.CenterVertically, composerImpl2, 54);
                                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM35clickableXHw0xAI$default);
                                    composerImpl2.startReusableNode();
                                    if (composerImpl2.inserting) {
                                        composerImpl2.createNode(function0);
                                    } else {
                                        composerImpl2.useNode();
                                    }
                                    Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function2);
                                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
                                    }
                                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function24);
                                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                    Modifier modifierFocusable$default = FocusableKt.focusable$default(BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63), false, null, 3);
                                    TextAlign.Companion.getClass();
                                    TextAlign textAlignM807boximpl = TextAlign.m807boximpl(TextAlign.Center);
                                    TextOverflow.Companion.getClass();
                                    int i6 = TextOverflow.Ellipsis;
                                    composerImpl2.startReplaceGroup(-285679791);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.DeviceListItemText (Type.kt:38)");
                                    }
                                    TextStyle.Companion companion3 = TextStyle.Companion;
                                    TextStyle secSemiBold = TypeKt.getSecSemiBold();
                                    long jMediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl2);
                                    TextUnitType.Companion.getClass();
                                    TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secSemiBold, jMediaPrimaryColor, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl2.end(false);
                                    TextKt.m317Text4IGK_g(str2, modifierFocusable$default, 0L, 0L, null, null, null, 0L, null, textAlignM807boximpl, 0L, i6, false, 1, 0, null, textStyleM756copyp1EtxEg$default, composerImpl2, 48, 3120, 54780);
                                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, true, true)) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            } else if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 200064, 18);
                composerImpl = composerImpl;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                mediaSessionViewModel3 = mediaSessionViewModel2;
                mediaDeviceViewModel3 = mediaDeviceViewModel2;
                labsViewModel3 = labsViewModel2;
            } else {
                List list3 = (List) mutableStateCollectAsState3.getValue();
                if (list3.size() <= 1) {
                    list3 = null;
                }
                if (list3 == null) {
                    DeviceSession deviceSession2 = (DeviceSession) CollectionsKt___CollectionsKt.firstOrNull((List) mutableStateCollectAsState3.getValue());
                    if (deviceSession2 != null) {
                        id = ((DeviceSessionController) deviceSession2).getId();
                    }
                }
                if (str != null) {
                    z2 = false;
                    AnimatedVisibilityKt.AnimatedVisibility(z2, null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(1221255400, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt.ActionButton.1
                        /* JADX WARN: Removed duplicated region for block: B:52:0x0166  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            float f;
                            Composer composer2 = (Composer) obj2;
                            ((Number) obj3).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ActionButton.<anonymous> (ContainerBox.kt:292)");
                            }
                            String str2 = str;
                            if (str2 != null) {
                                final String str3 = id;
                                if (str3 != null) {
                                    Modifier.Companion companion = Modifier.Companion;
                                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    composerImpl2.startReplaceGroup(-1164060407);
                                    composerImpl2.startReplaceGroup(-1164060371);
                                    boolean zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl2);
                                    composerImpl2.end(false);
                                    if (zIsPopupMode) {
                                        f = 10;
                                    } else {
                                        composerImpl2.startReplaceGroup(-1726102348);
                                        composerImpl2.startReplaceGroup(-1164059398);
                                        boolean z3 = ConfigurationExtKt.isPortrait(composerImpl2) || ConfigurationExtKt.isTablet(composerImpl2);
                                        composerImpl2.end(false);
                                        boolean z4 = z3 || ConfigurationExtKt.isFolderOpened(composerImpl2);
                                        composerImpl2.end(false);
                                        f = z4 ? 68 : 10;
                                    }
                                    Dp.Companion companion2 = Dp.Companion;
                                    float f2 = f;
                                    composerImpl2.end(false);
                                    Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifierFillMaxSize, 0.0f, 0.0f, 0.0f, f2, 7);
                                    Alignment.Companion.getClass();
                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.BottomCenter, false);
                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM129paddingqDBjuR0$default);
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
                                    Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                                    }
                                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function24);
                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                    RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
                                    CornerSize CornerSize = CornerSizeKt.CornerSize(100);
                                    Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(SizeKt.m133heightInVpY3zN4$default(SizeKt.m145widthInVpY3zN4(BackgroundKt.m26backgroundbw27NRU(ClipKt.clip(companion, new RoundedCornerShape(CornerSize, CornerSize, CornerSize, CornerSize)), ColorKt.cardBackground(composerImpl2), RectangleShapeKt.RectangleShape), 160, DpSize.m847getWidthD9Ej5fM(((DpSize) composerImpl2.consume(CompositionExtKt.LocalRootSize)).packedValue) * 0.8f), 52, 0.0f, 2), 16, 0.0f, 2);
                                    composerImpl2.startReplaceGroup(-427347893);
                                    final State state = mutableStateCollectAsState3;
                                    boolean zChanged = composerImpl2.changed(state) | composerImpl2.changed(str3);
                                    final Function1 function12 = function1;
                                    boolean zChanged2 = zChanged | composerImpl2.changed(function12);
                                    Object objRememberedValue = composerImpl2.rememberedValue();
                                    if (!zChanged2) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$ActionButton$1$$ExternalSyntheticLambda0
                                                /* JADX WARN: Removed duplicated region for block: B:20:0x004a  */
                                                @Override // kotlin.jvm.functions.Function0
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke() {
                                                    Object next;
                                                    String strCreateRoute;
                                                    String id2;
                                                    Iterator it = ((List) state.getValue()).iterator();
                                                    while (true) {
                                                        if (!it.hasNext()) {
                                                            next = null;
                                                            break;
                                                        }
                                                        next = it.next();
                                                        if (Intrinsics.areEqual(((DeviceSessionController) ((DeviceSession) next)).getId(), str3)) {
                                                            break;
                                                        }
                                                    }
                                                    DeviceSession deviceSession22 = (DeviceSession) next;
                                                    if (deviceSession22 == null || (id2 = ((DeviceSessionController) deviceSession22).getId()) == null) {
                                                        strCreateRoute = Screen.Selector.INSTANCE.createRoute("");
                                                    } else {
                                                        String str4 = StringsKt__StringsKt.isBlank(id2) ? null : id2;
                                                        if (str4 == null || (strCreateRoute = Screen.TV.INSTANCE.createRoute(str4)) == null) {
                                                        }
                                                    }
                                                    function12.mo781invoke(strCreateRoute);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        composerImpl2.end(false);
                                        Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierM127paddingVpY3zN4$default, false, null, (Function0) objRememberedValue, 7);
                                        Arrangement arrangement = Arrangement.INSTANCE;
                                        BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                        arrangement.getClass();
                                        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m93spacedByD5KLDUw(10, horizontal), Alignment.Companion.CenterVertically, composerImpl2, 54);
                                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM35clickableXHw0xAI$default);
                                        composerImpl2.startReusableNode();
                                        if (composerImpl2.inserting) {
                                            composerImpl2.createNode(function0);
                                        } else {
                                            composerImpl2.useNode();
                                        }
                                        Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function2);
                                        Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                                        if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
                                        }
                                        Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function24);
                                        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                        Modifier modifierFocusable$default = FocusableKt.focusable$default(BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63), false, null, 3);
                                        TextAlign.Companion.getClass();
                                        TextAlign textAlignM807boximpl = TextAlign.m807boximpl(TextAlign.Center);
                                        TextOverflow.Companion.getClass();
                                        int i6 = TextOverflow.Ellipsis;
                                        composerImpl2.startReplaceGroup(-285679791);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.DeviceListItemText (Type.kt:38)");
                                        }
                                        TextStyle.Companion companion3 = TextStyle.Companion;
                                        TextStyle secSemiBold = TypeKt.getSecSemiBold();
                                        long jMediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl2);
                                        TextUnitType.Companion.getClass();
                                        TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(secSemiBold, jMediaPrimaryColor, TextUnitKt.pack(17.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl2.end(false);
                                        TextKt.m317Text4IGK_g(str2, modifierFocusable$default, 0L, 0L, null, null, null, 0L, null, textAlignM807boximpl, 0L, i6, false, 1, 0, null, textStyleM756copyp1EtxEg$default, composerImpl2, 48, 3120, 54780);
                                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, true, true)) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                } else if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            } else if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl), composerImpl, 200064, 18);
                    composerImpl = composerImpl;
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    mediaSessionViewModel3 = mediaSessionViewModel2;
                    mediaDeviceViewModel3 = mediaDeviceViewModel2;
                    labsViewModel3 = labsViewModel2;
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ContainerBoxKt$$ExternalSyntheticLambda1(function1, mediaSessionViewModel3, mediaDeviceViewModel3, labsViewModel3, z, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02b5  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x030b  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0375  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0380  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0490  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x04be  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0580  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x05b7  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x0610  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0612  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0631  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x068f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ContainerBox(boolean z, MediaSessionViewModel mediaSessionViewModel, MediaDeviceViewModel mediaDeviceViewModel, LabsViewModel labsViewModel, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i) {
        Object failure;
        MediaSessionViewModel mediaSessionViewModel2;
        Object failure2;
        Object failure3;
        LabsViewModel labsViewModel2;
        MediaDeviceViewModel mediaDeviceViewModel2;
        boolean z2;
        float f;
        boolean z3;
        float f2;
        boolean zIsPopupMode;
        boolean z4;
        float f3;
        boolean z5;
        float f4;
        float f5;
        long jM840DpSizeYgX7TsA;
        long jFloatToRawIntBits;
        final MediaOutputState mediaOutputState;
        Object objRememberedValue;
        Object obj;
        State state;
        State state2;
        Rect rect;
        DpRect dpRect;
        MediaSessionViewModel mediaSessionViewModel3;
        boolean z6;
        boolean zChangedInstance;
        Object objRememberedValue2;
        ComposableLambdaImpl composableLambdaImpl2;
        LabsViewModel labsViewModel3;
        MediaSessionViewModel mediaSessionViewModel4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1717396094);
        if (((i | 1168) & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            mediaSessionViewModel4 = mediaSessionViewModel;
            labsViewModel3 = labsViewModel;
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
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    thM3441exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory = (ViewModelProvider.Factory) failure;
                if (factoryCreateDaggerViewModelFactory == null) {
                    factoryCreateDaggerViewModelFactory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, MediaSessionViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
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
                Throwable thM3441exceptionOrNullimpl2 = Result.m3441exceptionOrNullimpl(failure2);
                if (thM3441exceptionOrNullimpl2 != null) {
                    thM3441exceptionOrNullimpl2.printStackTrace();
                }
                if (failure2 instanceof Result.Failure) {
                    failure2 = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory2 = (ViewModelProvider.Factory) failure2;
                if (factoryCreateDaggerViewModelFactory2 == null) {
                    factoryCreateDaggerViewModelFactory2 = ViewModelKt.createDaggerViewModelFactory(current2);
                }
                ViewModel viewModel2 = ViewModelKt.get(current2, MediaDeviceViewModel.class, factoryCreateDaggerViewModelFactory2, defaultViewModelCreationExtras2);
                composerImpl.end(false);
                MediaDeviceViewModel mediaDeviceViewModel3 = (MediaDeviceViewModel) viewModel2;
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
                Throwable thM3441exceptionOrNullimpl3 = Result.m3441exceptionOrNullimpl(failure3);
                if (thM3441exceptionOrNullimpl3 != null) {
                    thM3441exceptionOrNullimpl3.printStackTrace();
                }
                if (failure3 instanceof Result.Failure) {
                    failure3 = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory3 = (ViewModelProvider.Factory) failure3;
                if (factoryCreateDaggerViewModelFactory3 == null) {
                    factoryCreateDaggerViewModelFactory3 = ViewModelKt.createDaggerViewModelFactory(current3);
                }
                ViewModel viewModel3 = ViewModelKt.get(current3, LabsViewModel.class, factoryCreateDaggerViewModelFactory3, defaultViewModelCreationExtras3);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel3;
                mediaDeviceViewModel2 = mediaDeviceViewModel3;
            } else {
                composerImpl.skipToGroupEnd();
                mediaSessionViewModel2 = mediaSessionViewModel;
                mediaDeviceViewModel2 = mediaDeviceViewModel;
                labsViewModel2 = labsViewModel;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.ContainerBox (ContainerBox.kt:96)");
            }
            Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(labsViewModel2.isSupportMultipleMediaSession, Boolean.FALSE, null, composerImpl, 48, 2);
            composerImpl.startReplaceGroup(461492281);
            MutableState mutableStateCollectAsState2 = ((Boolean) mutableStateCollectAsState.getValue()).booleanValue() ? SnapshotStateKt.collectAsState(mediaSessionViewModel2.sessionControllersFlow, EmptyList.INSTANCE, null, composerImpl, 48, 2) : SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(461498852);
            MutableState mutableStateMutableStateOf$default = (feature.isFullScreen || !(z || ((Boolean) mutableStateCollectAsState.getValue()).booleanValue())) ? SnapshotStateKt.mutableStateOf$default(EmptyList.INSTANCE) : SnapshotStateKt.collectAsState(mediaDeviceViewModel2.sessionControllersFlow, EmptyList.INSTANCE, null, composerImpl, 48, 2);
            composerImpl.end(false);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion, 1.0f);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxSize);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            Applier applier = composerImpl.applier;
            if (applier == null) {
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting) {
                mediaDeviceViewModel = mediaDeviceViewModel2;
            } else {
                mediaDeviceViewModel = mediaDeviceViewModel2;
                if (!Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                long j = ((DpSize) composerImpl.consume(CompositionExtKt.LocalRootSize)).packedValue;
                SizeF sizeFContainerFraction = containerFraction(feature.isFullScreen, composerImpl, 0);
                composerImpl.startReplaceGroup(-876149444);
                composerImpl.startReplaceGroup(-876149460);
                composerImpl.startReplaceGroup(-876149480);
                Feature.Companion.getClass();
                boolean z7 = feature.from == 10 || feature.isFullScreen || ConfigurationExtKt.isTablet(composerImpl);
                composerImpl.end(false);
                boolean z8 = !z7 || ConfigurationExtKt.isFolderOpened(composerImpl);
                composerImpl.end(false);
                boolean z9 = !z8 || ConfigurationExtKt.isPortrait(composerImpl);
                composerImpl.end(false);
                float f6 = !z9 ? 0 : 24;
                Dp.Companion companion2 = Dp.Companion;
                composerImpl.startReplaceGroup(-876144716);
                composerImpl.startReplaceGroup(-876144672);
                boolean z10 = !ConfigurationExtKt.isTablet(composerImpl) || ConfigurationExtKt.isFolderOpened(composerImpl);
                composerImpl.end(false);
                z2 = z10 && ConfigurationExtKt.isLandscape(composerImpl);
                composerImpl.end(false);
                if (z2) {
                    f = f6;
                } else {
                    f = f6;
                    if (((List) mutableStateCollectAsState2.getValue()).size() > 1 || !((List) mutableStateMutableStateOf$default.getValue()).isEmpty()) {
                        f2 = 58;
                        z3 = false;
                    }
                    composerImpl.startReplaceGroup(-876139172);
                    composerImpl.startReplaceGroup(-876139153);
                    zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl);
                    composerImpl.end(z3);
                    if (zIsPopupMode) {
                        f3 = 18;
                        z4 = false;
                    } else {
                        composerImpl.startReplaceGroup(-1390478407);
                        composerImpl.startReplaceGroup(-876138152);
                        composerImpl.startReplaceGroup(-876138172);
                        boolean z11 = feature.isFullScreen || ConfigurationExtKt.isTablet(composerImpl);
                        z4 = false;
                        composerImpl.end(false);
                        boolean z12 = z11 || ConfigurationExtKt.isFolderOpened(composerImpl);
                        composerImpl.end(false);
                        boolean z13 = z12 || ConfigurationExtKt.isLandscape(composerImpl);
                        composerImpl.end(false);
                        f3 = z13 ? 0 : 15;
                    }
                    composerImpl.end(z4);
                    composerImpl.startReplaceGroup(-876133944);
                    composerImpl.startReplaceGroup(-876133964);
                    composerImpl.startReplaceGroup(-876133978);
                    boolean z14 = feature.isFullScreen || ConfigurationExtKt.isPortrait(composerImpl);
                    composerImpl.end(false);
                    boolean z15 = !z14 || ConfigurationExtKt.isTablet(composerImpl);
                    composerImpl.end(false);
                    z5 = !z15 || ConfigurationExtKt.isFolderOpened(composerImpl);
                    composerImpl.end(false);
                    if (z5) {
                        f4 = f2;
                        f5 = 10;
                    } else {
                        f4 = f2;
                        f5 = 0;
                    }
                    float f7 = 2;
                    float f8 = f5;
                    jM840DpSizeYgX7TsA = DpKt.m840DpSizeYgX7TsA((sizeFContainerFraction.getWidth() * DpSize.m847getWidthD9Ej5fM(j)) - (f3 * f7), (((sizeFContainerFraction.getHeight() * DpSize.m846getHeightD9Ej5fM(j)) - f) - (f5 * f7)) - f4);
                    float fM847getWidthD9Ej5fM = (DpSize.m847getWidthD9Ej5fM(j) - DpSize.m847getWidthD9Ej5fM(jM840DpSizeYgX7TsA)) / f7;
                    composerImpl.startReplaceGroup(-876119536);
                    composerImpl.startReplaceGroup(-876119556);
                    boolean z16 = !ConfigurationExtKt.isPortrait(composerImpl) || ConfigurationExtKt.isTablet(composerImpl);
                    composerImpl.end(false);
                    boolean z17 = !z16 || ConfigurationExtKt.isFolderOpened(composerImpl);
                    composerImpl.end(false);
                    float fM846getHeightD9Ej5fM = !z17 ? (DpSize.m846getHeightD9Ej5fM(j) - DpSize.m846getHeightD9Ej5fM(jM840DpSizeYgX7TsA)) / f7 : f + f8;
                    LabsViewModel labsViewModel4 = labsViewModel2;
                    jFloatToRawIntBits = (Float.floatToRawIntBits(fM847getWidthD9Ej5fM) << 32) | (Float.floatToRawIntBits(fM846getHeightD9Ej5fM) & 4294967295L);
                    int i6 = DpOffset.$r8$clinit;
                    mediaOutputState = (MediaOutputState) composerImpl.consume(CompositionExtKt.LocalMediaOutputState);
                    MediaOutputState.Companion.getClass();
                    State stateRememberShownState = MediaOutputState.Companion.rememberShownState(composerImpl);
                    composerImpl.startReplaceGroup(-1956660936);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.common.MediaOutputState.Companion.rememberEnterState (Feature.kt:140)");
                    }
                    State stateRememberMediaOutputState = MediaOutputState.Companion.rememberMediaOutputState(composerImpl);
                    composerImpl.startReplaceGroup(-1261580723);
                    objRememberedValue = composerImpl.rememberedValue();
                    Composer.Companion.getClass();
                    obj = Composer.Companion.Empty;
                    if (objRememberedValue != obj) {
                        state = stateRememberShownState;
                        objRememberedValue = SnapshotStateKt.derivedStateOf(new MediaOutputState$Companion$$ExternalSyntheticLambda0(stateRememberMediaOutputState, 1));
                        composerImpl.updateRememberedValue(objRememberedValue);
                    } else {
                        state = stateRememberShownState;
                    }
                    state2 = (State) objRememberedValue;
                    composerImpl.end(false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    rect = ((Feature) ((TransitionInfo) composerImpl.consume(CompositionExtKt.LocalTransitionInfo))).fromRect;
                    composerImpl.startReplaceGroup(-876107764);
                    if (rect != null) {
                        mediaSessionViewModel3 = mediaSessionViewModel2;
                        z6 = false;
                        dpRect = null;
                    } else {
                        composerImpl.startReplaceGroup(431806407);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.toDpRect (UnitExt.kt:25)");
                        }
                        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
                        mediaSessionViewModel3 = mediaSessionViewModel2;
                        dpRect = new DpRect(density.mo55toDpu2uoSUM(rect.left), density.mo55toDpu2uoSUM(rect.top), density.mo55toDpu2uoSUM(rect.right), density.mo55toDpu2uoSUM(rect.bottom), null);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        z6 = false;
                        composerImpl.end(false);
                    }
                    composerImpl.end(z6);
                    if (!((Boolean) state2.getValue()).booleanValue() || dpRect == null) {
                        dpRect = new DpRect(jFloatToRawIntBits, jM840DpSizeYgX7TsA, null);
                    }
                    AnimationSpec animationSpecSnap$default = !((Boolean) state.getValue()).booleanValue() ? AnimationSpecKt.snap$default() : !((Boolean) state2.getValue()).booleanValue() ? AnimationSpecKt.spring$default(0.9f, 400.0f, null, 4) : AnimationSpecKt.spring$default(0.80829036f, 300.0f, null, 4);
                    composerImpl.startReplaceGroup(-876092543);
                    zChangedInstance = composerImpl.changedInstance(mediaOutputState);
                    objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance || objRememberedValue2 == obj) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                ((Feature) mediaOutputState).setState(MediaOutputState.StateInfo.Shown);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    Function1 function1 = (Function1) objRememberedValue2;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-1264146584);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.animateDpRectAsState (DpRectExt.kt:18)");
                    }
                    int i7 = DpRect.$r8$clinit;
                    final int i8 = 0;
                    final int i9 = 1;
                    State stateAnimateValueAsState = AnimateAsStateKt.animateValueAsState(dpRect, VectorConvertersKt.TwoWayConverter(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            switch (i8) {
                                case 0:
                                    DpRect dpRect2 = (DpRect) obj2;
                                    return new AnimationVector4D(dpRect2.left, dpRect2.top, dpRect2.right, dpRect2.bottom);
                                default:
                                    AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                                    float f9 = animationVector4D.v1;
                                    Dp.Companion companion3 = Dp.Companion;
                                    return new DpRect(f9, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
                            }
                        }
                    }, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            switch (i9) {
                                case 0:
                                    DpRect dpRect2 = (DpRect) obj2;
                                    return new AnimationVector4D(dpRect2.left, dpRect2.top, dpRect2.right, dpRect2.bottom);
                                default:
                                    AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                                    float f9 = animationVector4D.v1;
                                    Dp.Companion companion3 = Dp.Companion;
                                    return new DpRect(f9, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
                            }
                        }
                    }), animationSpecSnap$default, null, "RectAnimation", function1, composerImpl, 0, 8);
                    composerImpl = composerImpl;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    System.out.println((Object) AbstractResolvableFuture$$ExternalSyntheticOutline0.m(DpSize.m848toStringimpl(j), ", ", DpSize.m848toStringimpl(ConfigurationExtKt.screenSizeDp(composerImpl))));
                    Modifier modifierM115offsetVpY3zN4 = OffsetKt.m115offsetVpY3zN4(companion, ((DpRect) stateAnimateValueAsState.getValue()).left, ((DpRect) stateAnimateValueAsState.getValue()).top);
                    DpRect dpRect2 = (DpRect) stateAnimateValueAsState.getValue();
                    float f9 = dpRect2.right - dpRect2.left;
                    DpRect dpRect3 = (DpRect) stateAnimateValueAsState.getValue();
                    Modifier modifierClip = ClipKt.clip(SizeKt.m143sizeInqDBjuR0$default(modifierM115offsetVpY3zN4, 0.0f, 0.0f, f9, dpRect3.bottom - dpRect3.top, 3), RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(!feature.isFullScreen ? 0 : 26));
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierClip);
                    if (applier != null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function0);
                    } else {
                        composerImpl.useNode();
                    }
                    Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                    composableLambdaImpl2 = composableLambdaImpl;
                    composableLambdaImpl2.invoke((Object) composerImpl, (Object) 6);
                    composerImpl.end(true);
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    labsViewModel3 = labsViewModel4;
                    mediaSessionViewModel4 = mediaSessionViewModel3;
                }
                z3 = false;
                f2 = 0;
                composerImpl.startReplaceGroup(-876139172);
                composerImpl.startReplaceGroup(-876139153);
                zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl);
                composerImpl.end(z3);
                if (zIsPopupMode) {
                }
                composerImpl.end(z4);
                composerImpl.startReplaceGroup(-876133944);
                composerImpl.startReplaceGroup(-876133964);
                composerImpl.startReplaceGroup(-876133978);
                if (feature.isFullScreen) {
                    composerImpl.end(false);
                    if (z14) {
                        composerImpl.end(false);
                        if (z15) {
                            composerImpl.end(false);
                            if (z5) {
                            }
                            float f72 = 2;
                            float f82 = f5;
                            jM840DpSizeYgX7TsA = DpKt.m840DpSizeYgX7TsA((sizeFContainerFraction.getWidth() * DpSize.m847getWidthD9Ej5fM(j)) - (f3 * f72), (((sizeFContainerFraction.getHeight() * DpSize.m846getHeightD9Ej5fM(j)) - f) - (f5 * f72)) - f4);
                            float fM847getWidthD9Ej5fM2 = (DpSize.m847getWidthD9Ej5fM(j) - DpSize.m847getWidthD9Ej5fM(jM840DpSizeYgX7TsA)) / f72;
                            composerImpl.startReplaceGroup(-876119536);
                            composerImpl.startReplaceGroup(-876119556);
                            if (ConfigurationExtKt.isPortrait(composerImpl)) {
                                composerImpl.end(false);
                                if (z16) {
                                    composerImpl.end(false);
                                    if (!z17) {
                                    }
                                    LabsViewModel labsViewModel42 = labsViewModel2;
                                    jFloatToRawIntBits = (Float.floatToRawIntBits(fM847getWidthD9Ej5fM2) << 32) | (Float.floatToRawIntBits(fM846getHeightD9Ej5fM) & 4294967295L);
                                    int i62 = DpOffset.$r8$clinit;
                                    mediaOutputState = (MediaOutputState) composerImpl.consume(CompositionExtKt.LocalMediaOutputState);
                                    MediaOutputState.Companion.getClass();
                                    State stateRememberShownState2 = MediaOutputState.Companion.rememberShownState(composerImpl);
                                    composerImpl.startReplaceGroup(-1956660936);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    State stateRememberMediaOutputState2 = MediaOutputState.Companion.rememberMediaOutputState(composerImpl);
                                    composerImpl.startReplaceGroup(-1261580723);
                                    objRememberedValue = composerImpl.rememberedValue();
                                    Composer.Companion.getClass();
                                    obj = Composer.Companion.Empty;
                                    if (objRememberedValue != obj) {
                                    }
                                    state2 = (State) objRememberedValue;
                                    composerImpl.end(false);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl.end(false);
                                    rect = ((Feature) ((TransitionInfo) composerImpl.consume(CompositionExtKt.LocalTransitionInfo))).fromRect;
                                    composerImpl.startReplaceGroup(-876107764);
                                    if (rect != null) {
                                    }
                                    composerImpl.end(z6);
                                    if (!((Boolean) state2.getValue()).booleanValue()) {
                                        dpRect = new DpRect(jFloatToRawIntBits, jM840DpSizeYgX7TsA, null);
                                        if (!((Boolean) state.getValue()).booleanValue()) {
                                        }
                                        composerImpl.startReplaceGroup(-876092543);
                                        zChangedInstance = composerImpl.changedInstance(mediaOutputState);
                                        objRememberedValue2 = composerImpl.rememberedValue();
                                        if (!zChangedInstance) {
                                            objRememberedValue2 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    ((Feature) mediaOutputState).setState(MediaOutputState.StateInfo.Shown);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl.updateRememberedValue(objRememberedValue2);
                                            Function1 function12 = (Function1) objRememberedValue2;
                                            composerImpl.end(false);
                                            composerImpl.startReplaceGroup(-1264146584);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            int i72 = DpRect.$r8$clinit;
                                            final int i82 = 0;
                                            final int i92 = 1;
                                            State stateAnimateValueAsState2 = AnimateAsStateKt.animateValueAsState(dpRect, VectorConvertersKt.TwoWayConverter(new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    switch (i82) {
                                                        case 0:
                                                            DpRect dpRect22 = (DpRect) obj2;
                                                            return new AnimationVector4D(dpRect22.left, dpRect22.top, dpRect22.right, dpRect22.bottom);
                                                        default:
                                                            AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                                                            float f92 = animationVector4D.v1;
                                                            Dp.Companion companion3 = Dp.Companion;
                                                            return new DpRect(f92, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
                                                    }
                                                }
                                            }, new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.ext.DpRectExtKt$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    switch (i92) {
                                                        case 0:
                                                            DpRect dpRect22 = (DpRect) obj2;
                                                            return new AnimationVector4D(dpRect22.left, dpRect22.top, dpRect22.right, dpRect22.bottom);
                                                        default:
                                                            AnimationVector4D animationVector4D = (AnimationVector4D) obj2;
                                                            float f92 = animationVector4D.v1;
                                                            Dp.Companion companion3 = Dp.Companion;
                                                            return new DpRect(f92, animationVector4D.v2, animationVector4D.v3, animationVector4D.v4, null);
                                                    }
                                                }
                                            }), animationSpecSnap$default, null, "RectAnimation", function12, composerImpl, 0, 8);
                                            composerImpl = composerImpl;
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl.end(false);
                                            System.out.println((Object) AbstractResolvableFuture$$ExternalSyntheticOutline0.m(DpSize.m848toStringimpl(j), ", ", DpSize.m848toStringimpl(ConfigurationExtKt.screenSizeDp(composerImpl))));
                                            Modifier modifierM115offsetVpY3zN42 = OffsetKt.m115offsetVpY3zN4(companion, ((DpRect) stateAnimateValueAsState2.getValue()).left, ((DpRect) stateAnimateValueAsState2.getValue()).top);
                                            DpRect dpRect22 = (DpRect) stateAnimateValueAsState2.getValue();
                                            float f92 = dpRect22.right - dpRect22.left;
                                            DpRect dpRect32 = (DpRect) stateAnimateValueAsState2.getValue();
                                            Modifier modifierClip2 = ClipKt.clip(SizeKt.m143sizeInqDBjuR0$default(modifierM115offsetVpY3zN42, 0.0f, 0.0f, f92, dpRect32.bottom - dpRect32.top, 3), RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(!feature.isFullScreen ? 0 : 26));
                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy22 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                                            int currentCompositeKeyHash22 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl, modifierClip2);
                                            if (applier != null) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            Function2 function242 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function242);
            BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
            long j2 = ((DpSize) composerImpl.consume(CompositionExtKt.LocalRootSize)).packedValue;
            SizeF sizeFContainerFraction2 = containerFraction(feature.isFullScreen, composerImpl, 0);
            composerImpl.startReplaceGroup(-876149444);
            composerImpl.startReplaceGroup(-876149460);
            composerImpl.startReplaceGroup(-876149480);
            Feature.Companion.getClass();
            if (feature.from == 10) {
                composerImpl.end(false);
                if (z7) {
                    composerImpl.end(false);
                    if (z8) {
                        composerImpl.end(false);
                        if (!z9) {
                        }
                        Dp.Companion companion22 = Dp.Companion;
                        composerImpl.startReplaceGroup(-876144716);
                        composerImpl.startReplaceGroup(-876144672);
                        if (ConfigurationExtKt.isTablet(composerImpl)) {
                            composerImpl.end(false);
                            if (z10) {
                                composerImpl.end(false);
                                if (z2) {
                                }
                                z3 = false;
                                f2 = 0;
                                composerImpl.startReplaceGroup(-876139172);
                                composerImpl.startReplaceGroup(-876139153);
                                zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl);
                                composerImpl.end(z3);
                                if (zIsPopupMode) {
                                }
                                composerImpl.end(z4);
                                composerImpl.startReplaceGroup(-876133944);
                                composerImpl.startReplaceGroup(-876133964);
                                composerImpl.startReplaceGroup(-876133978);
                                if (feature.isFullScreen) {
                                }
                            }
                        }
                    }
                }
            }
        }
        MediaDeviceViewModel mediaDeviceViewModel4 = mediaDeviceViewModel;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ContainerBoxKt$$ExternalSyntheticLambda1(z, mediaSessionViewModel4, mediaDeviceViewModel4, labsViewModel3, composableLambdaImpl2, i);
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
                Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(failure);
                if (thM3441exceptionOrNullimpl != null) {
                    thM3441exceptionOrNullimpl.printStackTrace();
                }
                if (failure instanceof Result.Failure) {
                    failure = null;
                }
                ViewModelProvider.Factory factoryCreateDaggerViewModelFactory = (ViewModelProvider.Factory) failure;
                if (factoryCreateDaggerViewModelFactory == null) {
                    factoryCreateDaggerViewModelFactory = ViewModelKt.createDaggerViewModelFactory(current);
                }
                ViewModel viewModel = ViewModelKt.get(current, LabsViewModel.class, factoryCreateDaggerViewModelFactory, defaultViewModelCreationExtras);
                composerImpl.end(false);
                labsViewModel2 = (LabsViewModel) viewModel;
            } else {
                composerImpl.skipToGroupEnd();
                labsViewModel2 = labsViewModel;
            }
            final Function0 function04 = function02;
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.SettingButton (ContainerBox.kt:182)");
            }
            final Feature feature = (Feature) composerImpl.consume(CompositionExtKt.LocalFeature);
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            Function0 function05 = (Function0) composerImpl.consume(CompositionExtKt.LocalLargeScreenHeaderHeight);
            int iIntValue = (function05 == null || (num = (Integer) function05.invoke()) == null) ? 0 : num.intValue();
            composerImpl.startReplaceGroup(-709075098);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new ContainerBoxKt$$ExternalSyntheticLambda2(0);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            MutableState mutableStateCutoutInsets = ConfigurationExtKt.CutoutInsets(composerImpl, (Function1) objRememberedValue);
            WindowInsets.Companion companion = WindowInsets.Companion;
            int top = WindowInsets_androidKt.getSafeDrawing(composerImpl).getTop((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity));
            Feature feature2 = feature.isWindow ? feature : null;
            composerImpl.startReplaceGroup(-709069811);
            Float fValueOf = feature2 == null ? null : Float.valueOf(UnitExtKt.toDp(top, composerImpl));
            composerImpl.end(false);
            composerImpl.startReplaceGroup(-709070729);
            if (fValueOf == null) {
                int iIntValue2 = ((Number) mutableStateCutoutInsets.getValue()).intValue() + iIntValue;
                Integer numValueOf = Integer.valueOf(iIntValue2);
                if (iIntValue2 <= 0) {
                    numValueOf = null;
                }
                Dp dpM837boximpl = numValueOf == null ? null : Dp.m837boximpl(UnitExtKt.toDp(numValueOf.intValue(), composerImpl));
                fValueOf = dpM837boximpl != null ? Float.valueOf(dpM837boximpl.value) : null;
            }
            composerImpl.end(false);
            final float fFloatValue = fValueOf != null ? fValueOf.floatValue() : 52.0f;
            DataStoreDebugLabsExt$special$$inlined$map$3 dataStoreDebugLabsExt$special$$inlined$map$3 = labsViewModel2.isSupportMultipleMediaSession;
            Boolean bool = Boolean.FALSE;
            MutableState mutableStateCollectAsState = SnapshotStateKt.collectAsState(dataStoreDebugLabsExt$special$$inlined$map$3, bool, null, composerImpl, 48, 2);
            final MutableState mutableStateCollectAsState2 = SnapshotStateKt.collectAsState(labsViewModel2.isActionOpenOutputSwitcher, bool, null, composerImpl, 48, 2);
            if (!feature.isFullScreen && (z || ((Boolean) mutableStateCollectAsState.getValue()).booleanValue())) {
                DeviceUtils.INSTANCE.getClass();
                if (DeviceUtils.getSupportCastSetting(context) || ((Boolean) DeviceUtils.supportMusicShare$delegate.getValue()).booleanValue()) {
                    z2 = true;
                }
            }
            AnimatedVisibilityKt.AnimatedVisibility(z2, null, EnterExitTransitionKt.fadeIn$default(null, 3), EnterExitTransitionKt.fadeOut$default(null, 3), null, ComposableLambdaKt.rememberComposableLambda(1560093968, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt.SettingButton.1
                /* JADX WARN: Removed duplicated region for block: B:36:0x00b0  */
                /* JADX WARN: Removed duplicated region for block: B:82:0x01ca  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    float f;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.SettingButton.<anonymous> (ContainerBox.kt:200)");
                    }
                    Feature.Companion.getClass();
                    float f2 = feature.from == 10 ? 0 : 24;
                    Dp.Companion companion2 = Dp.Companion;
                    Modifier.Companion companion3 = Modifier.Companion;
                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion3, 1.0f);
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    composerImpl2.startReplaceGroup(1425010989);
                    composerImpl2.startReplaceGroup(1425010997);
                    boolean zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl2);
                    composerImpl2.end(false);
                    if (zIsPopupMode) {
                        f2 = 4;
                    } else {
                        composerImpl2.startReplaceGroup(1225705676);
                        composerImpl2.startReplaceGroup(1425012194);
                        boolean z3 = ConfigurationExtKt.isPortrait(composerImpl2) || ConfigurationExtKt.isTablet(composerImpl2);
                        composerImpl2.end(false);
                        boolean z4 = z3 || ConfigurationExtKt.isFolderOpened(composerImpl2);
                        composerImpl2.end(false);
                        if (z4) {
                            f2 = (fFloatValue + 20.0f) - 12.0f;
                        }
                    }
                    float f3 = f2;
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(1425016132);
                    composerImpl2.startReplaceGroup(1425016149);
                    boolean zIsPopupMode2 = ConfigurationExtKt.isPopupMode(composerImpl2);
                    composerImpl2.end(false);
                    if (zIsPopupMode2) {
                        f = 12;
                    } else {
                        composerImpl2.startReplaceGroup(1225865388);
                        composerImpl2.startReplaceGroup(1425017346);
                        boolean z5 = ConfigurationExtKt.isPortrait(composerImpl2) || ConfigurationExtKt.isTablet(composerImpl2);
                        composerImpl2.end(false);
                        boolean z6 = z5 || ConfigurationExtKt.isFolderOpened(composerImpl2);
                        composerImpl2.end(false);
                        if (!z6) {
                            f = 60;
                        }
                    }
                    float f4 = f;
                    composerImpl2.end(false);
                    Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(modifierFillMaxSize, 0.0f, f3, f4, 0.0f, 9);
                    Alignment.Companion.getClass();
                    BiasAlignment biasAlignment = Alignment.Companion.TopEnd;
                    final Context context2 = context;
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM129paddingqDBjuR0$default);
                    ComposeUiNode.Companion.getClass();
                    Function0 function06 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl2.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function06);
                    } else {
                        composerImpl2.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function24);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Arrangement.INSTANCE.getClass();
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl2, 48);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, companion3);
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function06);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function2);
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function24);
                    RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                    composerImpl2.startReplaceGroup(-994657853);
                    boolean zBooleanValue = ((Boolean) mutableStateCollectAsState2.getValue()).booleanValue();
                    Composer.Companion companion4 = Composer.Companion;
                    if (zBooleanValue) {
                        composerImpl2.startReplaceGroup(-994654981);
                        boolean zChangedInstance = composerImpl2.changedInstance(context2);
                        final Function0 function07 = function04;
                        boolean zChanged = zChangedInstance | composerImpl2.changed(function07);
                        Object objRememberedValue2 = composerImpl2.rememberedValue();
                        if (!zChanged) {
                            companion4.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        String str;
                                        Context context3 = context2;
                                        Intent intent = new Intent("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG");
                                        intent.setPackage(context3.getPackageName());
                                        Function0 function08 = function07;
                                        if (function08 == null || (str = (String) function08.invoke()) == null) {
                                            str = "";
                                        }
                                        intent.putExtra("package_name", str);
                                        context3.sendBroadcast(intent);
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue2);
                            }
                            composerImpl2.end(false);
                            ComposableSingletons$ContainerBoxKt.INSTANCE.getClass();
                            IconButtonKt.SeslIconButton((Function0) objRememberedValue2, (Modifier) null, false, (IconButtonColors) null, (MutableInteractionSource) null, (Function2) ComposableSingletons$ContainerBoxKt.f82lambda1, (Composer) composerImpl2, 196608, 30);
                        }
                    }
                    composerImpl2.end(false);
                    composerImpl2.startReplaceGroup(-994628262);
                    Object objRememberedValue3 = composerImpl2.rememberedValue();
                    companion4.getClass();
                    Object obj4 = Composer.Companion.Empty;
                    if (objRememberedValue3 == obj4) {
                        objRememberedValue3 = new ContainerBoxKt$$ExternalSyntheticLambda2(1);
                        composerImpl2.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl2.end(false);
                    Modifier modifierFocusProperties = FocusPropertiesKt.focusProperties(companion3, (Function1) objRememberedValue3);
                    composerImpl2.startReplaceGroup(-994626470);
                    final Function1 function12 = function1;
                    boolean zChanged2 = composerImpl2.changed(function12);
                    Object objRememberedValue4 = composerImpl2.rememberedValue();
                    if (zChanged2 || objRememberedValue4 == obj4) {
                        objRememberedValue4 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$SettingButton$1$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.Setting.INSTANCE);
                                function12.mo781invoke(Screen.SettingHome.INSTANCE.createRoute(""));
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl2.updateRememberedValue(objRememberedValue4);
                    }
                    composerImpl2.end(false);
                    ComposableSingletons$ContainerBoxKt.INSTANCE.getClass();
                    IconButtonKt.SeslIconButton((Function0) objRememberedValue4, modifierFocusProperties, false, (IconButtonColors) null, (MutableInteractionSource) null, (Function2) ComposableSingletons$ContainerBoxKt.f83lambda2, (Composer) composerImpl2, 196608, 28);
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, true, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 200064, 18);
            composerImpl = composerImpl;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            labsViewModel3 = labsViewModel2;
            function03 = function04;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, function03, labsViewModel3, i, i2) { // from class: com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt$$ExternalSyntheticLambda3
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
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    LabsViewModel labsViewModel4 = this.f$3;
                    int i8 = this.f$5;
                    ContainerBoxKt.SettingButton(this.f$0, this.f$1, this.f$2, labsViewModel4, (Composer) obj, iUpdateChangedFlags, i8);
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
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.material.containerFraction (ContainerBox.kt:165)");
        }
        if (z) {
            sizeF = new SizeF(1.0f, 1.0f);
        } else {
            composerImpl.startReplaceGroup(-2090276041);
            boolean zIsPopupMode = ConfigurationExtKt.isPopupMode(composerImpl);
            composerImpl.end(false);
            if (zIsPopupMode) {
                sizeF = new SizeF(1.0f, 0.75f);
            } else {
                composerImpl.startReplaceGroup(-373990510);
                composerImpl.startReplaceGroup(-2090273387);
                composerImpl.startReplaceGroup(-2090273321);
                boolean zIsLandscape = ConfigurationExtKt.isLandscape(composerImpl);
                composerImpl.end(false);
                if (zIsLandscape) {
                    f = 0.6f;
                } else {
                    composerImpl.startReplaceGroup(-373935206);
                    boolean z3 = ConfigurationExtKt.isTablet(composerImpl) || ConfigurationExtKt.isFolderOpened(composerImpl);
                    composerImpl.end(false);
                    f = z3 ? 0.7f : 1.0f;
                }
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-2090270440);
                composerImpl.startReplaceGroup(-2090270460);
                boolean z4 = ConfigurationExtKt.isPortrait(composerImpl) || ConfigurationExtKt.isTablet(composerImpl);
                composerImpl.end(false);
                if (!z4 && !ConfigurationExtKt.isFolderOpened(composerImpl)) {
                    z2 = false;
                }
                composerImpl.end(false);
                sizeF = new SizeF(f, z2 ? 0.65f : 1.0f);
                composerImpl.end(false);
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return sizeF;
    }
}
