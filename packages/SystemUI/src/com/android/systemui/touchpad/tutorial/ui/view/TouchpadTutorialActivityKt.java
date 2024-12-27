package com.android.systemui.touchpad.tutorial.ui.view;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner;
import androidx.lifecycle.viewmodel.compose.ViewModelKt;
import com.android.systemui.touchpad.tutorial.ui.BackGestureTutorialViewModel;
import com.android.systemui.touchpad.tutorial.ui.GestureViewModelFactory;
import com.android.systemui.touchpad.tutorial.ui.HomeGestureTutorialViewModel;
import com.android.systemui.touchpad.tutorial.ui.Screen;
import com.android.systemui.touchpad.tutorial.ui.TouchpadTutorialViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class TouchpadTutorialActivityKt {
    public static final void BackGestureTutorialScreen(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-638918083);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            GestureViewModelFactory gestureViewModelFactory = new GestureViewModelFactory();
            composerImpl.startReplaceableGroup(1729797275);
            LocalViewModelStoreOwner.INSTANCE.getClass();
            ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
            if (current == null) {
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
            }
            ViewModel viewModel = ViewModelKt.viewModel(Reflection.getOrCreateKotlinClass(BackGestureTutorialViewModel.class), current, gestureViewModelFactory, current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE, composerImpl);
            composerImpl.end(false);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$BackGestureTutorialScreen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TouchpadTutorialActivityKt.BackGestureTutorialScreen(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void HomeGestureTutorialScreen(final int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(612934469);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            GestureViewModelFactory gestureViewModelFactory = new GestureViewModelFactory();
            composerImpl.startReplaceableGroup(1729797275);
            LocalViewModelStoreOwner.INSTANCE.getClass();
            ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
            if (current == null) {
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
            }
            ViewModel viewModel = ViewModelKt.viewModel(Reflection.getOrCreateKotlinClass(HomeGestureTutorialViewModel.class), current, gestureViewModelFactory, current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE, composerImpl);
            composerImpl.end(false);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$HomeGestureTutorialScreen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TouchpadTutorialActivityKt.HomeGestureTutorialScreen(RecomposeScopeImplKt.updateChangedFlags(i | 1), (Composer) obj);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void TouchpadTutorialScreen(final ViewModelProvider.Factory factory, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-417065939);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceableGroup(1729797275);
        LocalViewModelStoreOwner.INSTANCE.getClass();
        ViewModelStoreOwner current = LocalViewModelStoreOwner.getCurrent(composerImpl);
        if (current == null) {
            throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
        }
        ViewModel viewModel = ViewModelKt.viewModel(Reflection.getOrCreateKotlinClass(TouchpadTutorialViewModel.class), current, factory, current instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) current).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE, composerImpl);
        composerImpl.end(false);
        final TouchpadTutorialViewModel touchpadTutorialViewModel = (TouchpadTutorialViewModel) viewModel;
        Enum r1 = (Enum) FlowExtKt.collectAsStateWithLifecycle(touchpadTutorialViewModel.screen, Lifecycle.State.STARTED, composerImpl, 56).getValue();
        if (r1 == Screen.TUTORIAL_SELECTION) {
            composerImpl.startReplaceGroup(391198205);
            TutorialSelectionScreenKt.TutorialSelectionScreen(new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$TouchpadTutorialScreen$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TouchpadTutorialViewModel touchpadTutorialViewModel2 = TouchpadTutorialViewModel.this;
                    touchpadTutorialViewModel2._screen.updateState(null, Screen.BACK_GESTURE);
                    return Unit.INSTANCE;
                }
            }, new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$TouchpadTutorialScreen$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TouchpadTutorialViewModel touchpadTutorialViewModel2 = TouchpadTutorialViewModel.this;
                    touchpadTutorialViewModel2._screen.updateState(null, Screen.HOME_GESTURE);
                    return Unit.INSTANCE;
                }
            }, new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$TouchpadTutorialScreen$3
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }, function0, composerImpl, ((i << 6) & 7168) | 384);
            composerImpl.end(false);
        } else if (r1 == Screen.BACK_GESTURE) {
            composerImpl.startReplaceGroup(391198503);
            BackGestureTutorialScreen(0, composerImpl);
            composerImpl.end(false);
        } else if (r1 == Screen.HOME_GESTURE) {
            composerImpl.startReplaceGroup(391198555);
            HomeGestureTutorialScreen(0, composerImpl);
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(391198588);
            composerImpl.end(false);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$TouchpadTutorialScreen$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TouchpadTutorialActivityKt.TouchpadTutorialScreen(ViewModelProvider.Factory.this, function0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
