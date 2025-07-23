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
import androidx.compose.foundation.layout.BoxWithConstraintsScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import androidx.core.util.Consumer;
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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class CustomComposeView extends FrameLayout implements LifecycleOwner, SavedStateRegistryOwner, ViewModelStoreOwner, OnBackPressedDispatcherOwner, HasDefaultViewModelProviderFactory {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy _viewModelStore$delegate;
    public final Lazy contentView$delegate;
    public final Lazy keyguardManager$delegate;
    public final LifecycleRegistry lifecycleRegistry;
    public final Lazy onBackPressedDispatcher$delegate;
    public final SavedStateRegistryController savedStateRegistryController;
    public final ViewModelFactory viewModelProviderFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public static ComposeView $r8$lambda$vBRf2LS73a2V0xNO2Aqq_HOGqWY(Context context, AttributeSet attributeSet, final CustomComposeView customComposeView) {
        ComposeView composeView = new ComposeView(context, attributeSet, 0, 4, null);
        composeView.setLayoutParams(customComposeView.generateDefaultLayoutParams());
        if (ViewTreeLifecycleOwner.get(composeView) == null) {
            composeView.setTag(R.id.view_tree_lifecycle_owner, customComposeView);
        }
        if (ViewTreeViewModelStoreOwner.get(composeView) == null) {
            composeView.setTag(R.id.view_tree_view_model_store_owner, customComposeView);
        }
        if (ViewTreeSavedStateRegistryOwner.get(composeView) == null) {
            composeView.setTag(R.id.view_tree_saved_state_registry_owner, customComposeView);
        }
        if (ViewTreeOnBackPressedDispatcherOwner.get(composeView) == null) {
            composeView.setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, customComposeView);
        }
        composeView.setContent(new ComposableLambdaImpl(5984946, true, new Function2() { // from class: com.android.systemui.media.CustomComposeView$contentView$2$1$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.CustomComposeView.contentView$delegate.<anonymous>.<anonymous>.<anonymous> (CustomComposeView.kt:71)");
                }
                final CustomComposeView customComposeView2 = CustomComposeView.this;
                BoxWithConstraintsKt.BoxWithConstraints(null, null, false, ComposableLambdaKt.rememberComposableLambda(-1248983140, new Function3() { // from class: com.android.systemui.media.CustomComposeView$contentView$2$1$1.1
                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                        BoxWithConstraintsScope boxWithConstraintsScope = (BoxWithConstraintsScope) obj3;
                        Composer composer2 = (Composer) obj4;
                        int intValue = ((Number) obj5).intValue();
                        if ((intValue & 6) == 0) {
                            intValue |= ((ComposerImpl) composer2).changed(boxWithConstraintsScope) ? 4 : 2;
                        }
                        if ((intValue & 19) == 18) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.media.CustomComposeView.contentView$delegate.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CustomComposeView.kt:72)");
                        }
                        ProvidedValue defaultProvidedValue$runtime_release = CompositionExtKt.LocalRootSize.defaultProvidedValue$runtime_release(DpSize.m842boximpl(DpKt.m838DpSizeYgX7TsA(boxWithConstraintsScope.mo101getMaxWidthD9Ej5fM(), boxWithConstraintsScope.mo100getMaxHeightD9Ej5fM())));
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionExtKt.LocalViewModelProviderFactory;
                        final CustomComposeView customComposeView3 = CustomComposeView.this;
                        CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{defaultProvidedValue$runtime_release, staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(customComposeView3.viewModelProviderFactory)}, ComposableLambdaKt.rememberComposableLambda(2019392220, new Function2() { // from class: com.android.systemui.media.CustomComposeView.contentView.2.1.1.1.1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj6, Object obj7) {
                                Composer composer3 = (Composer) obj6;
                                if ((((Number) obj7).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                    if (composerImpl3.getSkipping()) {
                                        composerImpl3.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.CustomComposeView.contentView$delegate.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CustomComposeView.kt:76)");
                                }
                                CustomComposeView.this.ContentView(composer3);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer2), composer2, 56);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composer), composer, 3072, 7);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }));
        return composeView;
    }

    static {
        new Companion(null);
    }

    public CustomComposeView(final Context context, final AttributeSet attributeSet, ViewModelFactory viewModelFactory) {
        super(context, attributeSet);
        Object failure;
        this.viewModelProviderFactory = viewModelFactory;
        final int i = 0;
        this.keyguardManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CustomComposeView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = context;
                switch (i) {
                    case 0:
                        int i2 = CustomComposeView.$r8$clinit;
                        Object systemService = ((Context) obj).getSystemService((Class<Object>) KeyguardManager.class);
                        systemService.getClass();
                        return (KeyguardManager) systemService;
                    default:
                        int i3 = CustomComposeView.$r8$clinit;
                        OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable() { // from class: com.android.systemui.media.CustomComposeView$onBackPressedDispatcher$2$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Log.d("CustomComposeView", "fallbackOnBackPressed()");
                            }
                        }, new Consumer() { // from class: com.android.systemui.media.CustomComposeView$onBackPressedDispatcher$2$2
                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj2) {
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("onHasEnabledCallbacksChanged() - ", "CustomComposeView", ((Boolean) obj2).booleanValue());
                            }
                        });
                        OnBackInvokedDispatcher findOnBackInvokedDispatcher = ((CustomComposeView) obj).getRootView().findOnBackInvokedDispatcher();
                        if (findOnBackInvokedDispatcher != null) {
                            onBackPressedDispatcher.setOnBackInvokedDispatcher(findOnBackInvokedDispatcher);
                        }
                        return onBackPressedDispatcher;
                }
            }
        });
        this.contentView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CustomComposeView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CustomComposeView.$r8$lambda$vBRf2LS73a2V0xNO2Aqq_HOGqWY(context, attributeSet, this);
            }
        });
        LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: com.android.systemui.media.CustomComposeView$lifecycleObserver$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                int i2 = WhenMappings.$EnumSwitchMapping$0[event.ordinal()];
                CustomComposeView customComposeView = CustomComposeView.this;
                if (i2 == 1) {
                    customComposeView.removeView((ComposeView) customComposeView.contentView$delegate.getValue());
                    customComposeView.addView((ComposeView) customComposeView.contentView$delegate.getValue());
                } else if (i2 != 2) {
                    if (i2 != 3) {
                        return;
                    }
                    customComposeView.lifecycleRegistry.removeObserver(this);
                } else {
                    customComposeView.removeView((ComposeView) customComposeView.contentView$delegate.getValue());
                    customComposeView.getViewModelStore().clear();
                    customComposeView.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
                }
            }
        };
        LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
        this.lifecycleRegistry = lifecycleRegistry;
        SavedStateRegistryController.Companion.getClass();
        SavedStateRegistryController create = SavedStateRegistryController.Companion.create(this);
        this.savedStateRegistryController = create;
        this._viewModelStore$delegate = LazyKt__LazyJVMKt.lazy(new CustomComposeView$$ExternalSyntheticLambda2());
        final int i2 = 1;
        this.onBackPressedDispatcher$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.CustomComposeView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = this;
                switch (i2) {
                    case 0:
                        int i22 = CustomComposeView.$r8$clinit;
                        Object systemService = ((Context) obj).getSystemService((Class<Object>) KeyguardManager.class);
                        systemService.getClass();
                        return (KeyguardManager) systemService;
                    default:
                        int i3 = CustomComposeView.$r8$clinit;
                        OnBackPressedDispatcher onBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable() { // from class: com.android.systemui.media.CustomComposeView$onBackPressedDispatcher$2$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Log.d("CustomComposeView", "fallbackOnBackPressed()");
                            }
                        }, new Consumer() { // from class: com.android.systemui.media.CustomComposeView$onBackPressedDispatcher$2$2
                            @Override // androidx.core.util.Consumer
                            public final void accept(Object obj2) {
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("onHasEnabledCallbacksChanged() - ", "CustomComposeView", ((Boolean) obj2).booleanValue());
                            }
                        });
                        OnBackInvokedDispatcher findOnBackInvokedDispatcher = ((CustomComposeView) obj).getRootView().findOnBackInvokedDispatcher();
                        if (findOnBackInvokedDispatcher != null) {
                            onBackPressedDispatcher.setOnBackInvokedDispatcher(findOnBackInvokedDispatcher);
                        }
                        return onBackPressedDispatcher;
                }
            }
        });
        lifecycleRegistry.addObserver(lifecycleEventObserver);
        try {
            int i3 = Result.$r8$clinit;
            create.performRestore(null);
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i4 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            m3422exceptionOrNullimpl.printStackTrace();
        }
    }

    public abstract void ContentView(Composer composer);

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
        return (OnBackPressedDispatcher) this.onBackPressedDispatcher$delegate.getValue();
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
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Log.d("CustomComposeView", "onDetachedFromWindow()");
        super.onDetachedFromWindow();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.viewModelProviderFactory.getClass();
    }
}
