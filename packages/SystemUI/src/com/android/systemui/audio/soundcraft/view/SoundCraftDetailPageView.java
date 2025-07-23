package com.android.systemui.audio.soundcraft.view;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.LinearLayout;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.utils.LayoutHelperUtil;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.volume.util.ContextUtils;
import java.util.WeakHashMap;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SoundCraftDetailPageView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy keyguardManager$delegate;
    public SoundCraftViewBinding viewBinding;
    public final Lazy viewModel$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ SoundCraftDetailPageView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public final SoundCraftViewModel getViewModel() {
        return (SoundCraftViewModel) this.viewModel$delegate.getValue();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        WindowInsetsController windowInsetsController;
        super.onAttachedToWindow();
        if (((KeyguardManager) this.keyguardManager$delegate.getValue()).isKeyguardLocked() && Intrinsics.areEqual(getViewModel().isActionBarVisible.getValue(), Boolean.TRUE)) {
            Object value = getViewModel().isFromNowBar.getValue();
            Boolean bool = Boolean.FALSE;
            if (Intrinsics.areEqual(value, bool) && Intrinsics.areEqual(getViewModel().isFromCover.getValue(), bool) && (windowInsetsController = getWindowInsetsController()) != null) {
                windowInsetsController.show(WindowInsets.Type.navigationBars());
            }
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        Object failure;
        super.onConfigurationChanged(configuration);
        try {
            int i = Result.$r8$clinit;
            updatePadding$1();
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (Result.m3422exceptionOrNullimpl(failure) != null) {
            Log.d("SoundCraft.SoundCraftDetailPageView", "updatePadding fail");
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        WindowInsetsController windowInsetsController;
        super.onDetachedFromWindow();
        if (((KeyguardManager) this.keyguardManager$delegate.getValue()).isKeyguardLocked()) {
            Object value = getViewModel().isFromNowBar.getValue();
            Boolean bool = Boolean.FALSE;
            if (Intrinsics.areEqual(value, bool) && Intrinsics.areEqual(getViewModel().isFromCover.getValue(), bool) && (windowInsetsController = getWindowInsetsController()) != null) {
                windowInsetsController.hide(WindowInsets.Type.navigationBars());
            }
        }
    }

    public final void updatePadding$1() {
        int i;
        DisplayCutout displayCutout;
        boolean isScreenWideMobileDevice = ContextUtils.isScreenWideMobileDevice(getContext());
        Object value = getViewModel().isActionBarVisible.getValue();
        Boolean bool = Boolean.TRUE;
        if (!Intrinsics.areEqual(value, bool) && !Intrinsics.areEqual(getViewModel().isFromCover.getValue(), bool)) {
            setPadding(0, 0, 0, 0);
            return;
        }
        SoundCraftViewBinding soundCraftViewBinding = this.viewBinding;
        if (soundCraftViewBinding == null) {
            soundCraftViewBinding = null;
        }
        ViewGroup viewGroup = soundCraftViewBinding.root;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        if (Intrinsics.areEqual(getViewModel().isFromCover.getValue(), bool)) {
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        } else {
            layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.soundcraft_top_margin);
            layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.soundcraft_bottom_margin);
        }
        viewGroup.setLayoutParams(layoutParams);
        if (isScreenWideMobileDevice) {
            getContext();
            LayoutHelperUtil layoutHelperUtil = LayoutHelperUtil.INSTANCE;
            Context context = getContext();
            layoutHelperUtil.getClass();
            getLayoutParams().width = (int) (DeviceState.getDisplayWidth(context) * (getContext().getResources().getConfiguration().orientation == 1 ? 0.7f : 0.6204f));
        } else {
            int dimensionPixelSize = getResources().getDimensionPixelSize(DeviceType.isTablet() ? R.dimen.soundcraft_tablet_side_padding : R.dimen.soundcraft_side_padding);
            if (Intrinsics.areEqual(getViewModel().isFromCover.getValue(), bool)) {
                dimensionPixelSize /= 3;
                WindowInsets rootWindowInsets = getRootView().getRootWindowInsets();
                if (rootWindowInsets != null && (displayCutout = rootWindowInsets.getDisplayCutout()) != null) {
                    i = displayCutout.getSafeInsetTop();
                    SoundCraftViewBinding soundCraftViewBinding2 = this.viewBinding;
                    if (soundCraftViewBinding2 == null) {
                        soundCraftViewBinding2 = null;
                    }
                    soundCraftViewBinding2.scrollView.setPadding(0, 0, 0, displayCutout.getSafeInsetBottom() + 30);
                    SoundCraftViewBinding soundCraftViewBinding3 = this.viewBinding;
                    if (soundCraftViewBinding3 == null) {
                        soundCraftViewBinding3 = null;
                    }
                    soundCraftViewBinding3.scrollView.setClipToPadding(false);
                    setPadding(dimensionPixelSize, i, dimensionPixelSize, 0);
                }
            }
            i = 0;
            setPadding(dimensionPixelSize, i, dimensionPixelSize, 0);
        }
        SoundCraftViewBinding soundCraftViewBinding4 = this.viewBinding;
        (soundCraftViewBinding4 != null ? soundCraftViewBinding4 : null).actionBar.root.setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height));
    }

    public SoundCraftDetailPageView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0(this) { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$special$$inlined$lazyViewModel$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                SoundCraftViewComponent soundCraftViewComponent = SoundCraftLocalViewModelStoreOwner.current;
                if (soundCraftViewComponent != null) {
                    return SoundCraftDetailPageView$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, SoundCraftViewModel.class);
                }
                throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner");
            }
        });
        this.keyguardManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                int i = SoundCraftDetailPageView.$r8$clinit;
                Object systemService = context2.getSystemService((Class<Object>) KeyguardManager.class);
                systemService.getClass();
                return (KeyguardManager) systemService;
            }
        });
        setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                Object failure;
                SoundCraftDetailPageView soundCraftDetailPageView = SoundCraftDetailPageView.this;
                try {
                    int i = Result.$r8$clinit;
                    int i2 = SoundCraftDetailPageView.$r8$clinit;
                    soundCraftDetailPageView.updatePadding$1();
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i3 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                if (Result.m3422exceptionOrNullimpl(failure) != null) {
                    Log.d("SoundCraft.SoundCraftDetailPageView", "updatePadding fail");
                }
                return windowInsetsCompat;
            }
        };
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, onApplyWindowInsetsListener);
    }
}
