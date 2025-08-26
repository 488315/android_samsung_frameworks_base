package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.Choreographer;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.widget.ImageView;
import android.window.InputTransferToken;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.DynamicTonalPaletteKt;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.Theme;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class ResizeVeil {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SurfaceControl backgroundSurface;
    public final CoroutineScope bgScope;
    public final Context context;
    public final ColorScheme darkColors;
    public final DecorThemeUtil decorThemeUtil;
    public Display display;
    public final DisplayController displayController;
    public ValueAnimator iconAnimator;
    public int iconSize;
    public SurfaceControl iconSurface;
    public ImageView iconView;
    public boolean isVisible;
    public final ColorScheme lightColors;
    public StandaloneCoroutine loadAppInfoJob;
    public final CoroutineDispatcher mainDispatcher;
    public final ResizeVeil$onDisplaysChangedListener$1 onDisplaysChangedListener;
    public SurfaceControl parentSurface;
    public final SurfaceControlBuilderFactory surfaceControlBuilderFactory;
    public final Supplier surfaceControlTransactionSupplier;
    public final WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory;
    public final WindowDecorTaskResourceLoader taskResourceLoader;
    public ValueAnimator veilAnimator;
    public SurfaceControl veilSurface;
    public SurfaceControlViewHost viewHost;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface SurfaceControlBuilderFactory {
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Theme.values().length];
            try {
                iArr[Theme.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Theme.DARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.wm.shell.windowdecor.ResizeVeil$setupResizeVeil$1, reason: invalid class name and case insensitive filesystem */
    final class C12161 extends SuspendLambda implements Function2 {
        final /* synthetic */ ActivityManager.RunningTaskInfo $taskInfo;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.wm.shell.windowdecor.ResizeVeil$setupResizeVeil$1$1, reason: invalid class name and collision with other inner class name */
        final class C06601 extends SuspendLambda implements Function2 {
            final /* synthetic */ Bitmap $icon;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ ResizeVeil this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C06601(ResizeVeil resizeVeil, Bitmap bitmap, Continuation continuation) {
                super(2, continuation);
                this.this$0 = resizeVeil;
                this.$icon = bitmap;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C06601 c06601 = new C06601(this.this$0, this.$icon, continuation);
                c06601.L$0 = obj;
                return c06601;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C06601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                    return Unit.INSTANCE;
                }
                ImageView imageView = this.this$0.iconView;
                if (imageView == null) {
                    imageView = null;
                }
                imageView.setImageBitmap(this.$icon);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C12161(ActivityManager.RunningTaskInfo runningTaskInfo, Continuation continuation) {
            super(2, continuation);
            this.$taskInfo = runningTaskInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C12161 c12161 = ResizeVeil.this.new C12161(this.$taskInfo, continuation);
            c12161.L$0 = obj;
            return c12161;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C12161) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x00b1 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Bitmap bitmapCreateScaledBitmap;
            CoroutineDispatcher coroutineDispatcher;
            C06601 c06601;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (!CoroutineScopeKt.isActive((CoroutineScope) this.L$0)) {
                    return Unit.INSTANCE;
                }
                WindowDecorTaskResourceLoader windowDecorTaskResourceLoader = ResizeVeil.this.taskResourceLoader;
                ActivityManager.RunningTaskInfo runningTaskInfo = this.$taskInfo;
                windowDecorTaskResourceLoader.getClass();
                boolean z = CoreRune.MW_CAPTION_BUG_FIX;
                if (z) {
                    if (!windowDecorTaskResourceLoader.existingTasks.contains(Integer.valueOf(runningTaskInfo.taskId))) {
                        bitmapCreateScaledBitmap = windowDecorTaskResourceLoader.veilIconFactory.createScaledBitmap(windowDecorTaskResourceLoader.userProfilesContexts.getOrCreate(runningTaskInfo.userId).getPackageManager().getDefaultActivityIcon(), 0);
                    }
                    ResizeVeil resizeVeil = ResizeVeil.this;
                    coroutineDispatcher = resizeVeil.mainDispatcher;
                    c06601 = new C06601(resizeVeil, bitmapCreateScaledBitmap, null);
                    this.label = 1;
                    if (BuildersKt.withContext(coroutineDispatcher, c06601, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    windowDecorTaskResourceLoader.checkWindowDecorExists(runningTaskInfo);
                }
                WindowDecorTaskResourceLoader.AppResources appResources = (WindowDecorTaskResourceLoader.AppResources) windowDecorTaskResourceLoader.taskToResourceCache.get(Integer.valueOf(runningTaskInfo.taskId));
                if (appResources != null) {
                    bitmapCreateScaledBitmap = appResources.veilIcon;
                } else {
                    WindowDecorTaskResourceLoader.AppResources appResourcesLoadAppResources = windowDecorTaskResourceLoader.loadAppResources(runningTaskInfo);
                    windowDecorTaskResourceLoader.localeListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), runningTaskInfo.getConfiguration().getLocales());
                    if (z) {
                        windowDecorTaskResourceLoader.densityListOnCache.put(Integer.valueOf(runningTaskInfo.taskId), Integer.valueOf(runningTaskInfo.getConfiguration().densityDpi));
                    }
                    bitmapCreateScaledBitmap = appResourcesLoadAppResources.veilIcon;
                }
                ResizeVeil resizeVeil2 = ResizeVeil.this;
                coroutineDispatcher = resizeVeil2.mainDispatcher;
                c06601 = new C06601(resizeVeil2, bitmapCreateScaledBitmap, null);
                this.label = 1;
                if (BuildersKt.withContext(coroutineDispatcher, c06601, this) == coroutineSingletons) {
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public ResizeVeil(Context context, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, SurfaceControl surfaceControl, Supplier<SurfaceControl.Transaction> supplier, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this(context, displayController, windowDecorTaskResourceLoader, coroutineDispatcher, coroutineScope, surfaceControl, supplier, null, null, runningTaskInfo, 384, null);
    }

    public final void cancelAnimation() {
        ValueAnimator valueAnimator = this.veilAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
        }
        ValueAnimator valueAnimator2 = this.veilAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        this.veilAnimator = null;
        ValueAnimator valueAnimator3 = this.iconAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.removeAllUpdateListeners();
        }
        ValueAnimator valueAnimator4 = this.iconAnimator;
        if (valueAnimator4 != null) {
            valueAnimator4.cancel();
        }
        this.iconAnimator = null;
    }

    public final void dispose() {
        cancelAnimation();
        this.isVisible = false;
        StandaloneCoroutine standaloneCoroutine = this.loadAppInfoJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        SurfaceControlViewHost surfaceControlViewHost = this.viewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
        }
        this.viewHost = null;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.surfaceControlTransactionSupplier.get();
        SurfaceControl surfaceControl = this.backgroundSurface;
        if (surfaceControl != null) {
            transaction.remove(surfaceControl);
        }
        this.backgroundSurface = null;
        SurfaceControl surfaceControl2 = this.iconSurface;
        if (surfaceControl2 != null) {
            transaction.remove(surfaceControl2);
        }
        this.iconSurface = null;
        SurfaceControl surfaceControl3 = this.veilSurface;
        if (surfaceControl3 != null) {
            transaction.remove(surfaceControl3);
        }
        this.veilSurface = null;
        transaction.apply();
        this.displayController.removeDisplayWindowListener(this.onDisplaysChangedListener);
    }

    public final void hideVeil() {
        if (this.isVisible) {
            cancelAnimation();
            final SurfaceControl surfaceControl = this.backgroundSurface;
            final SurfaceControl surfaceControl2 = this.iconSurface;
            if (surfaceControl == null || surfaceControl2 == null) {
                return;
            }
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat.setDuration(200L);
            valueAnimatorOfFloat.setStartDelay(33L);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$hideVeil$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((SurfaceControl.Transaction) this.this$0.surfaceControlTransactionSupplier.get()).setAlpha(surfaceControl, ((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue()).apply();
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$hideVeil$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ((SurfaceControl.Transaction) this.this$0.surfaceControlTransactionSupplier.get()).hide(surfaceControl).apply();
                }
            });
            this.veilAnimator = valueAnimatorOfFloat;
            final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat2.setDuration(50L);
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$hideVeil$2$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((SurfaceControl.Transaction) this.this$0.surfaceControlTransactionSupplier.get()).setAlpha(surfaceControl2, ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue()).apply();
                }
            });
            valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$hideVeil$2$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ((SurfaceControl.Transaction) this.this$0.surfaceControlTransactionSupplier.get()).hide(surfaceControl2).apply();
                }
            });
            this.iconAnimator = valueAnimatorOfFloat2;
            ValueAnimator valueAnimator = this.veilAnimator;
            if (valueAnimator != null) {
                valueAnimator.start();
            }
            ValueAnimator valueAnimator2 = this.iconAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.start();
            }
            this.isVisible = false;
        }
    }

    public final void relayout(Rect rect, SurfaceControl.Transaction transaction) {
        float f = 2;
        PointF pointF = new PointF((rect.width() / f) - (this.iconSize / f), (rect.height() / f) - (this.iconSize / f));
        SurfaceControl surfaceControl = this.veilSurface;
        SurfaceControl surfaceControl2 = this.iconSurface;
        if (surfaceControl == null || surfaceControl2 == null) {
            return;
        }
        transaction.setWindowCrop(surfaceControl, rect.width(), rect.height()).setPosition(surfaceControl2, pointF.x, pointF.y).setPosition(this.parentSurface, rect.left, rect.top).setWindowCrop(this.parentSurface, rect.width(), rect.height()).setFrameTimeline(Choreographer.getInstance().getVsyncId());
    }

    public final void setupResizeVeil(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.displayId;
        DisplayController displayController = this.displayController;
        Display display = displayController.mDisplayManager.getDisplay(i);
        this.display = display;
        if (display == null) {
            displayController.addDisplayWindowListener(this.onDisplaysChangedListener, -1);
            return;
        }
        Trace.beginSection("ResizeVeil#setupResizeVeil");
        String strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "Resize veil of Task=");
        this.surfaceControlBuilderFactory.getClass();
        this.veilSurface = new SurfaceControl.Builder().setName(strM).setContainerLayer().setHidden(true).setParent(this.parentSurface).setCallsite("ResizeVeil#setupResizeVeil").build();
        this.backgroundSurface = new SurfaceControl.Builder().setName(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "Resize veil background of Task=")).setColorLayer().setHidden(true).setParent(this.veilSurface).setCallsite("ResizeVeil#setupResizeVeil").build();
        this.iconSurface = new SurfaceControl.Builder().setName(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "Resize veil icon of Task=")).setContainerLayer().setHidden(true).setParent(this.veilSurface).setCallsite("ResizeVeil#setupResizeVeil").build();
        this.iconSize = this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_resize_veil_icon_size);
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.desktop_mode_resize_veil, (ViewGroup) null);
        this.iconView = (ImageView) viewInflate.requireViewById(R.id.veil_application_icon);
        int i2 = this.iconSize;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(i2, i2, 2, 8, -2);
        layoutParams.setTitle("Resize veil icon window of Task=" + runningTaskInfo.taskId);
        layoutParams.inputFeatures = 1;
        layoutParams.setTrustedOverlay();
        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(runningTaskInfo.configuration, this.iconSurface, (InputTransferToken) null);
        Context context = this.context;
        Display display2 = this.display;
        this.surfaceControlViewHostFactory.getClass();
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, display2, windowlessWindowManager, "ResizeVeil");
        this.viewHost = surfaceControlViewHost;
        surfaceControlViewHost.setView(viewInflate, layoutParams);
        this.loadAppInfoJob = BuildersKt.launch$default(this.bgScope, null, null, new C12161(runningTaskInfo, null), 3);
        Trace.endSection();
    }

    public final void showVeil(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        if (this.viewHost == null || this.isVisible) {
            transaction.apply();
            return;
        }
        final SurfaceControl surfaceControl2 = this.backgroundSurface;
        final SurfaceControl surfaceControl3 = this.iconSurface;
        if (surfaceControl2 == null || surfaceControl3 == null) {
            return;
        }
        updateTransactionWithShowVeil(transaction, surfaceControl, rect, runningTaskInfo, z);
        if (!z) {
            transaction.apply();
            return;
        }
        cancelAnimation();
        final SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) this.surfaceControlTransactionSupplier.get();
        final SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) this.surfaceControlTransactionSupplier.get();
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(50L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$showVeil$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                transaction2.setAlpha(surfaceControl2, ((Float) valueAnimatorOfFloat.getAnimatedValue()).floatValue()).apply();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$showVeil$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                transaction2.setAlpha(surfaceControl2, 1.0f).apply();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                transaction2.show(surfaceControl2).setAlpha(surfaceControl2, 0.0f).apply();
            }
        });
        this.veilAnimator = valueAnimatorOfFloat;
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat2.setDuration(50L);
        valueAnimatorOfFloat2.setStartDelay(33L);
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$showVeil$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                transaction3.setAlpha(surfaceControl3, ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue()).apply();
            }
        });
        valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$showVeil$2$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                transaction3.setAlpha(surfaceControl3, 1.0f).apply();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                transaction3.show(surfaceControl3).setAlpha(surfaceControl3, 0.0f).apply();
            }
        });
        this.iconAnimator = valueAnimatorOfFloat2;
        transaction.hide(surfaceControl3).hide(surfaceControl2).apply();
        ValueAnimator valueAnimator = this.veilAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ValueAnimator valueAnimator2 = this.iconAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.start();
        }
    }

    public final void updateTransactionWithShowVeil(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        long j;
        if (this.viewHost == null || this.isVisible) {
            return;
        }
        this.isVisible = true;
        SurfaceControl surfaceControl2 = this.backgroundSurface;
        SurfaceControl surfaceControl3 = this.iconSurface;
        SurfaceControl surfaceControl4 = this.veilSurface;
        if (surfaceControl2 == null || surfaceControl3 == null || surfaceControl4 == null) {
            return;
        }
        if (!Intrinsics.areEqual(surfaceControl, this.parentSurface)) {
            transaction.reparent(surfaceControl4, surfaceControl);
            this.parentSurface = surfaceControl;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.decorThemeUtil.getAppTheme(runningTaskInfo).ordinal()];
        if (i == 1) {
            j = this.lightColors.surfaceContainer;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            j = this.darkColors.surfaceContainer;
        }
        transaction.show(surfaceControl4).setLayer(surfaceControl4, VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS).setLayer(surfaceControl3, 1).setLayer(surfaceControl2, 0).setColor(surfaceControl2, Color.valueOf(ColorKt.m469toArgb8_81llA(j)).getComponents());
        relayout(rect, transaction);
        if (z) {
            return;
        }
        transaction.show(surfaceControl3).show(surfaceControl2).setAlpha(surfaceControl3, 1.0f).setAlpha(surfaceControl2, 1.0f);
    }

    public ResizeVeil(Context context, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, SurfaceControl surfaceControl, Supplier<SurfaceControl.Transaction> supplier, SurfaceControlBuilderFactory surfaceControlBuilderFactory, ActivityManager.RunningTaskInfo runningTaskInfo) {
        this(context, displayController, windowDecorTaskResourceLoader, coroutineDispatcher, coroutineScope, surfaceControl, supplier, surfaceControlBuilderFactory, null, runningTaskInfo, 256, null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.wm.shell.windowdecor.ResizeVeil$onDisplaysChangedListener$1] */
    public ResizeVeil(Context context, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, SurfaceControl surfaceControl, Supplier<SurfaceControl.Transaction> supplier, SurfaceControlBuilderFactory surfaceControlBuilderFactory, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, final ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.context = context;
        this.displayController = displayController;
        this.taskResourceLoader = windowDecorTaskResourceLoader;
        this.mainDispatcher = coroutineDispatcher;
        this.bgScope = coroutineScope;
        this.parentSurface = surfaceControl;
        this.surfaceControlTransactionSupplier = supplier;
        this.surfaceControlBuilderFactory = surfaceControlBuilderFactory;
        this.surfaceControlViewHostFactory = surfaceControlViewHostFactory;
        this.decorThemeUtil = new DecorThemeUtil(context);
        this.lightColors = DynamicTonalPaletteKt.dynamicLightColorScheme(context);
        this.darkColors = DynamicTonalPaletteKt.dynamicDarkColorScheme(context);
        this.onDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.windowdecor.ResizeVeil$onDisplaysChangedListener$1
            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayAdded(int i) {
                if (runningTaskInfo.displayId != i) {
                    return;
                }
                ResizeVeil resizeVeil = this;
                resizeVeil.displayController.removeDisplayWindowListener(this);
                resizeVeil.setupResizeVeil(runningTaskInfo);
            }
        };
        setupResizeVeil(runningTaskInfo);
    }

    public /* synthetic */ ResizeVeil(Context context, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, SurfaceControl surfaceControl, Supplier supplier, SurfaceControlBuilderFactory surfaceControlBuilderFactory, WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory, ActivityManager.RunningTaskInfo runningTaskInfo, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, displayController, windowDecorTaskResourceLoader, coroutineDispatcher, coroutineScope, surfaceControl, supplier, (i & 128) != 0 ? new SurfaceControlBuilderFactory() { // from class: com.android.wm.shell.windowdecor.ResizeVeil.1
        } : surfaceControlBuilderFactory, (i & 256) != 0 ? new WindowDecoration.SurfaceControlViewHostFactory() { // from class: com.android.wm.shell.windowdecor.ResizeVeil.2
        } : surfaceControlViewHostFactory, runningTaskInfo);
    }

    public static /* synthetic */ void getIconView$annotations() {
    }
}
