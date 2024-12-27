package com.android.systemui.audio.soundcraft.view;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.LinearLayout;
import androidx.lifecycle.ViewModelStoreOwner;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftLocalViewModelStoreOwner;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftVMComponent;
import com.android.systemui.audio.soundcraft.di.vm.component.SoundCraftViewModelExt;
import com.android.systemui.audio.soundcraft.viewbinding.SoundCraftViewBinding;
import com.android.systemui.audio.soundcraft.viewmodel.SoundCraftViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SoundCraftDetailPageView extends LinearLayout implements SoundCraftVMComponent {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy keyguardManager$delegate;
    public SoundCraftViewBinding viewBinding;
    public final Lazy viewModel$delegate;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        if (((KeyguardManager) this.keyguardManager$delegate.getValue()).isKeyguardLocked() && Intrinsics.areEqual(getViewModel().isActionBarVisible.getValue(), Boolean.TRUE) && Intrinsics.areEqual(getViewModel().isFromNowBar.getValue(), Boolean.FALSE) && (windowInsetsController = getWindowInsetsController()) != null) {
            windowInsetsController.show(WindowInsets.Type.navigationBars());
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
        if (Result.m2527exceptionOrNullimpl(failure) != null) {
            Log.d("SoundCraft.SoundCraftDetailPageView", "updatePadding fail");
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        WindowInsetsController windowInsetsController;
        super.onDetachedFromWindow();
        if (((KeyguardManager) this.keyguardManager$delegate.getValue()).isKeyguardLocked() && Intrinsics.areEqual(getViewModel().isFromNowBar.getValue(), Boolean.FALSE) && (windowInsetsController = getWindowInsetsController()) != null) {
            windowInsetsController.hide(WindowInsets.Type.navigationBars());
        }
    }

    public final void updatePadding$1() {
        if (!Intrinsics.areEqual(getViewModel().isActionBarVisible.getValue(), Boolean.TRUE)) {
            setPadding(0, 0, 0, 0);
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.soundcraft_top_margin);
        layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.soundcraft_bottom_margin);
        SoundCraftViewBinding soundCraftViewBinding = this.viewBinding;
        if (soundCraftViewBinding == null) {
            soundCraftViewBinding = null;
        }
        soundCraftViewBinding.root.setLayoutParams(layoutParams);
        setPadding(getResources().getDimensionPixelSize(R.dimen.soundcraft_side_padding), 0, getResources().getDimensionPixelSize(R.dimen.soundcraft_side_padding), 0);
        SoundCraftViewBinding soundCraftViewBinding2 = this.viewBinding;
        (soundCraftViewBinding2 != null ? soundCraftViewBinding2 : null).actionBar.root.setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.layout_edit_action_min_height));
    }

    public SoundCraftDetailPageView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SoundCraftViewModelExt soundCraftViewModelExt = SoundCraftViewModelExt.INSTANCE;
        this.viewModel$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, new Function0() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$special$$inlined$lazyViewModel$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SoundCraftViewModelExt soundCraftViewModelExt2 = SoundCraftViewModelExt.INSTANCE;
                SoundCraftLocalViewModelStoreOwner.INSTANCE.getClass();
                ViewModelStoreOwner viewModelStoreOwner = SoundCraftLocalViewModelStoreOwner.current;
                if (viewModelStoreOwner == null) {
                    throw new IllegalStateException("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner".toString());
                }
                SoundCraftViewComponent soundCraftViewComponent = (SoundCraftViewComponent) viewModelStoreOwner;
                return SoundCraftDetailPageView$bind$2$$ExternalSyntheticOutline0.m(soundCraftViewModelExt2, soundCraftViewComponent, soundCraftViewComponent, SoundCraftViewModel.class);
            }
        });
        this.keyguardManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.view.SoundCraftDetailPageView$keyguardManager$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object systemService = context.getSystemService((Class<Object>) KeyguardManager.class);
                Intrinsics.checkNotNull(systemService);
                return (KeyguardManager) systemService;
            }
        });
        setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }
}
