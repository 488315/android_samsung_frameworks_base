package com.android.systemui.media;

import android.app.KeyguardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.ViewTreeOnBackPressedDispatcherOwner;
import androidx.compose.foundation.layout.BoxWithConstraintsKt;
import androidx.compose.foundation.layout.BoxWithConstraintsScopeImpl;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class CustomComposeView extends FrameLayout implements LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner, OnBackPressedDispatcherOwner, HasDefaultViewModelProviderFactory {
    public final Lazy _viewModelStore$delegate;
    public final Lazy contentView$delegate;
    public Job job;
    public final Lazy keyguardManager$delegate;
    public final LifecycleRegistry lifecycleRegistry;
    public final SavedStateRegistryController savedStateRegistryController;
    public final ViewModelFactory viewModelProviderFactory;

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

    public CustomComposeView(final Context context, final AttributeSet attributeSet, ViewModelFactory viewModelFactory) {
        super(context, attributeSet);
        Object failure;
        this.viewModelProviderFactory = viewModelFactory;
        this.keyguardManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CustomComposeView$keyguardManager$2
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
        this.contentView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CustomComposeView$contentView$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                FrameLayout.LayoutParams generateDefaultLayoutParams;
                ComposeView composeView = new ComposeView(context, attributeSet, 0, 4, null);
                final CustomComposeView customComposeView = this;
                generateDefaultLayoutParams = customComposeView.generateDefaultLayoutParams();
                composeView.setLayoutParams(generateDefaultLayoutParams);
                if (ViewTreeLifecycleOwner.get(composeView) == null) {
                    ViewTreeLifecycleOwner.set(composeView, customComposeView);
                }
                if (ViewTreeViewModelStoreOwner.get(composeView) == null) {
                    ViewTreeViewModelStoreOwner.set(composeView, customComposeView);
                }
                if (ViewTreeSavedStateRegistryOwner.get(composeView) == null) {
                    ViewTreeSavedStateRegistryOwner.set(composeView, customComposeView);
                }
                if (ViewTreeOnBackPressedDispatcherOwner.get(composeView) == null) {
                    ViewTreeOnBackPressedDispatcherOwner.set(composeView, customComposeView);
                }
                composeView.setContent(new ComposableLambdaImpl(5984946, true, new Function2() { // from class: com.android.systemui.media.CustomComposeView$contentView$2$1$1
                    {
                        super(2);
                    }

                    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.systemui.media.CustomComposeView$contentView$2$1$1$1, kotlin.jvm.internal.Lambda] */
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
                        final CustomComposeView customComposeView2 = CustomComposeView.this;
                        BoxWithConstraintsKt.BoxWithConstraints(null, null, false, ComposableLambdaKt.rememberComposableLambda(-1248983140, composer, new Function3() { // from class: com.android.systemui.media.CustomComposeView$contentView$2$1$1.1
                            {
                                super(3);
                            }

                            /* JADX WARN: Type inference failed for: r7v9, types: [com.android.systemui.media.CustomComposeView$contentView$2$1$1$1$1, kotlin.jvm.internal.Lambda] */
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                float f;
                                float f2;
                                BoxWithConstraintsScopeImpl boxWithConstraintsScopeImpl = (BoxWithConstraintsScopeImpl) obj3;
                                Composer composer2 = (Composer) obj4;
                                int intValue = ((Number) obj5).intValue();
                                if ((intValue & 14) == 0) {
                                    intValue |= ((ComposerImpl) composer2).changed(boxWithConstraintsScopeImpl) ? 4 : 2;
                                }
                                if ((intValue & 91) == 18) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionExtKt.LocalRootSize;
                                long j = boxWithConstraintsScopeImpl.constraints;
                                boolean m720getHasBoundedWidthimpl = Constraints.m720getHasBoundedWidthimpl(j);
                                Density density = boxWithConstraintsScopeImpl.density;
                                if (m720getHasBoundedWidthimpl) {
                                    f = density.mo58toDpu2uoSUM(Constraints.m724getMaxWidthimpl(j));
                                } else {
                                    Dp.Companion.getClass();
                                    f = Dp.Infinity;
                                }
                                long j2 = boxWithConstraintsScopeImpl.constraints;
                                if (Constraints.m719getHasBoundedHeightimpl(j2)) {
                                    f2 = density.mo58toDpu2uoSUM(Constraints.m723getMaxHeightimpl(j2));
                                } else {
                                    Dp.Companion.getClass();
                                    f2 = Dp.Infinity;
                                }
                                ProvidedValue[] providedValueArr = {staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DpSize.m746boximpl(DpKt.m742DpSizeYgX7TsA(f, f2))), CompositionExtKt.LocalViewModelProviderFactory.defaultProvidedValue$runtime_release(CustomComposeView.this.viewModelProviderFactory)};
                                final CustomComposeView customComposeView3 = CustomComposeView.this;
                                CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(2019392220, composer2, new Function2() { // from class: com.android.systemui.media.CustomComposeView.contentView.2.1.1.1.1
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj6, Object obj7) {
                                        Composer composer3 = (Composer) obj6;
                                        if ((((Number) obj7).intValue() & 11) == 2) {
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                            if (composerImpl3.getSkipping()) {
                                                composerImpl3.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                        CustomComposeView.this.ContentView(8, composer3);
                                        return Unit.INSTANCE;
                                    }
                                }), composer2, 56);
                                return Unit.INSTANCE;
                            }
                        }), composer, 3072, 7);
                        return Unit.INSTANCE;
                    }
                }));
                return composeView;
            }
        });
        LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: com.android.systemui.media.CustomComposeView$lifecycleObserver$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Lifecycle.Event.values().length];
                    try {
                        iArr[Lifecycle.Event.ON_START.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Lifecycle.Event.ON_STOP.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[Lifecycle.Event.ON_DESTROY.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                int i = WhenMappings.$EnumSwitchMapping$0[event.ordinal()];
                CustomComposeView customComposeView = CustomComposeView.this;
                if (i == 1) {
                    customComposeView.removeView((ComposeView) customComposeView.contentView$delegate.getValue());
                    customComposeView.addView((ComposeView) customComposeView.contentView$delegate.getValue());
                } else if (i == 2) {
                    customComposeView.removeView((ComposeView) customComposeView.contentView$delegate.getValue());
                    customComposeView.getViewModelStore().clear();
                } else {
                    if (i != 3) {
                        return;
                    }
                    customComposeView.lifecycleRegistry.removeObserver(this);
                }
            }
        };
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.lifecycleRegistry = lifecycleRegistry;
        SavedStateRegistryController.Companion.getClass();
        SavedStateRegistryController create = SavedStateRegistryController.Companion.create(this);
        this.savedStateRegistryController = create;
        this._viewModelStore$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CustomComposeView$_viewModelStore$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new ViewModelStore();
            }
        });
        lifecycleRegistry.addObserver(lifecycleEventObserver);
        try {
            int i = Result.$r8$clinit;
            create.performRestore(null);
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(failure);
        if (m2527exceptionOrNullimpl != null) {
            m2527exceptionOrNullimpl.printStackTrace();
        }
    }

    public abstract void ContentView(int i, Composer composer);

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return this.viewModelProviderFactory;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    @Override // androidx.activity.OnBackPressedDispatcherOwner
    public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
        OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(null, 1, null);
        OnBackInvokedDispatcher findOnBackInvokedDispatcher = getRootView().findOnBackInvokedDispatcher();
        if (findOnBackInvokedDispatcher != null) {
            onBackPressedDispatcher.setOnBackInvokedDispatcher(findOnBackInvokedDispatcher);
        }
        return onBackPressedDispatcher;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.savedStateRegistryController.savedStateRegistry;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    public final ViewModelStore getViewModelStore() {
        return (ViewModelStore) this._viewModelStore$delegate.getValue();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        Log.d("CustomComposeView", "onAttachedToWindow()");
        super.onAttachedToWindow();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.job = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new CustomComposeView$onAttachedToWindow$1(this, null), 3);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Log.d("CustomComposeView", "onDetachedFromWindow()");
        super.onDetachedFromWindow();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.viewModelProviderFactory.getClass();
        Job job = this.job;
        if (job != null) {
            job.cancel(null);
        }
    }
}
