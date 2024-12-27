package com.android.systemui.keyboard.shortcut.ui.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Insets;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import androidx.activity.BackEventCompat;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LifecycleKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.domain.interactor.ShortcutHelperInteractor;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperKt;
import com.android.systemui.keyboard.shortcut.ui.viewmodel.ShortcutHelperViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.BuildersKt;

public final class ShortcutHelperActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ShortcutHelperViewModel viewModel;

    public ShortcutHelperActivity(ShortcutHelperViewModel shortcutHelperViewModel) {
        this.viewModel = shortcutHelperViewModel;
    }

    public final BottomSheetBehavior getBottomSheetBehavior() {
        ViewGroup.LayoutParams layoutParams = requireViewById(R.id.shortcut_helper_sheet).getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior;
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        getWindow().setDecorFitsSystemWindows(false);
        super.onCreate(bundle);
        setContentView(R.layout.activity_keyboard_shortcut_helper);
        getBottomSheetBehavior().maxWidth = (int) (getResources().getFloat(R.dimen.shortcut_helper_screen_width_fraction) * getResources().getDisplayMetrics().widthPixels);
        getBottomSheetBehavior().setState$1(3);
        getBottomSheetBehavior().skipCollapsed = true;
        requireViewById(R.id.shortcut_helper_sheet_container).setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpInsets$1
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Insets max = Insets.max(windowInsets.getInsets(WindowInsets.Type.systemBars()), windowInsets.getInsets(WindowInsets.Type.displayCutout()));
                ShortcutHelperActivity shortcutHelperActivity = ShortcutHelperActivity.this;
                int i = ShortcutHelperActivity.$r8$clinit;
                shortcutHelperActivity.getBottomSheetBehavior().maxHeight = ShortcutHelperActivity.this.getResources().getDisplayMetrics().heightPixels - max.top;
                View requireViewById = ShortcutHelperActivity.this.requireViewById(R.id.shortcut_helper_sheet);
                requireViewById.setPadding(max.left, requireViewById.getPaddingTop(), max.right, max.bottom);
                ShortcutHelperActivity shortcutHelperActivity2 = ShortcutHelperActivity.this;
                shortcutHelperActivity2.getBottomSheetBehavior().setState$1(3);
                shortcutHelperActivity2.getBottomSheetBehavior().skipCollapsed = true;
                return WindowInsets.CONSUMED;
            }
        });
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpPredictiveBack$onBackPressedCallback$1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackCancelled() {
                int i = ShortcutHelperActivity.$r8$clinit;
                ShortcutHelperActivity.this.getBottomSheetBehavior().cancelBackProgress();
            }

            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackPressed() {
                int i = ShortcutHelperActivity.$r8$clinit;
                ShortcutHelperActivity.this.getBottomSheetBehavior().handleBackInvoked();
            }

            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackProgressed(BackEventCompat backEventCompat) {
                int i = ShortcutHelperActivity.$r8$clinit;
                ShortcutHelperActivity.this.getBottomSheetBehavior().updateBackProgress(backEventCompat);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public final void handleOnBackStarted(BackEventCompat backEventCompat) {
                int i = ShortcutHelperActivity.$r8$clinit;
                ShortcutHelperActivity.this.getBottomSheetBehavior().startBackProgress(backEventCompat);
            }
        });
        BottomSheetBehavior bottomSheetBehavior = getBottomSheetBehavior();
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpSheetDismissListener$1
            @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public final void onStateChanged(int i) {
                if (i == 5) {
                    ShortcutHelperActivity.this.finish();
                }
            }
        };
        if (!bottomSheetBehavior.callbacks.contains(bottomSheetCallback)) {
            bottomSheetBehavior.callbacks.add(bottomSheetCallback);
        }
        requireViewById(R.id.shortcut_helper_sheet_container).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpDismissOnTouchOutside$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShortcutHelperActivity.this.finish();
            }
        });
        ((ComposeView) requireViewById(R.id.shortcut_helper_compose_container)).setContent(new ComposableLambdaImpl(912237203, true, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpComposeView$1$1
            {
                super(2);
            }

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
                final ShortcutHelperActivity shortcutHelperActivity = ShortcutHelperActivity.this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-1735153527, composer, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpComposeView$1$1.1

                    /* renamed from: com.android.systemui.keyboard.shortcut.ui.view.ShortcutHelperActivity$setUpComposeView$1$1$1$1, reason: invalid class name and collision with other inner class name */
                    final /* synthetic */ class C01121 extends FunctionReferenceImpl implements Function0 {
                        public C01121(Object obj) {
                            super(0, obj, ShortcutHelperActivity.class, "onKeyboardSettingsClicked", "onKeyboardSettingsClicked()V", 0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            ShortcutHelperActivity shortcutHelperActivity = (ShortcutHelperActivity) this.receiver;
                            int i = ShortcutHelperActivity.$r8$clinit;
                            shortcutHelperActivity.getClass();
                            try {
                                shortcutHelperActivity.startActivity(new Intent("android.settings.HARD_KEYBOARD_SETTINGS"));
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 11) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        ShortcutHelperKt.ShortcutHelper(new C01121(ShortcutHelperActivity.this), null, null, null, composer2, 0, 14);
                        return Unit.INSTANCE;
                    }
                }), composer, 48, 1);
                return Unit.INSTANCE;
            }
        }));
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new ShortcutHelperActivity$observeFinishRequired$1(this, null), 3);
        this.viewModel.interactor.setSysUiStateFlagEnabled(true);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ShortcutHelperInteractor shortcutHelperInteractor = this.viewModel.interactor;
            shortcutHelperInteractor.repository.state.setValue(ShortcutHelperState.Inactive.INSTANCE);
            shortcutHelperInteractor.setSysUiStateFlagEnabled(false);
        }
    }
}
