package androidx.compose.ui.platform;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.Latch;
import androidx.compose.runtime.MonotonicFrameClock;
import androidx.compose.runtime.PausableMonotonicFrameClock;
import androidx.compose.runtime.Recomposer;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.MotionDurationScale;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.platform.ViewCompositionStrategy;
import androidx.compose.ui.platform.WindowRecomposerFactory;
import androidx.customview.poolingcontainer.PoolingContainer;
import androidx.customview.poolingcontainer.PoolingContainerListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.android.HandlerDispatcherKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AbstractComposeView extends ViewGroup {
    public WeakReference cachedViewTreeCompositionContext;
    public Composition composition;
    public boolean creatingComposition;
    public Function0 disposeViewCompositionStrategy;
    public boolean isTransitionGroupSet;
    public ComposerImpl.CompositionContextImpl parentContext;
    public IBinder previousAttachedWindowToken;

    public AbstractComposeView(Context context) {
        this(context, null, 0, 6, null);
    }

    public abstract void Content(ComposerImpl composerImpl);

    @Override // android.view.ViewGroup
    public final void addView(View view) {
        checkAddView();
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public final boolean addViewInLayout(View view, int i, ViewGroup.LayoutParams layoutParams) {
        checkAddView();
        return super.addViewInLayout(view, i, layoutParams);
    }

    public final void checkAddView() {
        if (this.creatingComposition) {
            return;
        }
        throw new UnsupportedOperationException("Cannot add views to " + getClass().getSimpleName() + "; only Compose content is supported");
    }

    public final void createComposition() {
        if (this.parentContext == null && !isAttachedToWindow()) {
            throw new IllegalStateException("createComposition requires either a parent reference or the View to be attachedto a window. Attach the View or call setParentCompositionReference.");
        }
        ensureCompositionCreated();
    }

    public final void disposeComposition() {
        Composition composition = this.composition;
        if (composition != null) {
            composition.dispose();
        }
        this.composition = null;
        requestLayout();
    }

    public final void ensureCompositionCreated() {
        if (this.composition == null) {
            try {
                this.creatingComposition = true;
                this.composition = Wrapper_androidKt.setContent(this, resolveParentCompositionContext(), new ComposableLambdaImpl(-656146368, true, new Function2() { // from class: androidx.compose.ui.platform.AbstractComposeView$ensureCompositionCreated$1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer = (Composer) obj;
                        int intValue = ((Number) obj2).intValue();
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.shouldExecute(intValue & 1, (intValue & 3) != 2)) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.ui.platform.AbstractComposeView.ensureCompositionCreated.<anonymous> (ComposeView.android.kt:249)");
                            }
                            AbstractComposeView.this.Content(composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }));
            } finally {
                this.creatingComposition = false;
            }
        }
    }

    public boolean getShouldCreateCompositionOnAttachedToWindow() {
        return true;
    }

    public void internalOnLayout$ui_release(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null) {
            childAt.layout(getPaddingLeft(), getPaddingTop(), (i3 - i) - getPaddingRight(), (i4 - i2) - getPaddingBottom());
        }
    }

    public void internalOnMeasure$ui_release(int i, int i2) {
        View childAt = getChildAt(0);
        if (childAt == null) {
            super.onMeasure(i, i2);
            return;
        }
        childAt.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight()), View.MeasureSpec.getMode(i)), View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i2) - getPaddingTop()) - getPaddingBottom()), View.MeasureSpec.getMode(i2)));
        setMeasuredDimension(getPaddingRight() + getPaddingLeft() + childAt.getMeasuredWidth(), getPaddingBottom() + getPaddingTop() + childAt.getMeasuredHeight());
    }

    @Override // android.view.ViewGroup
    public final boolean isTransitionGroup() {
        return !this.isTransitionGroupSet || super.isTransitionGroup();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        IBinder windowToken = getWindowToken();
        if (this.previousAttachedWindowToken != windowToken) {
            this.previousAttachedWindowToken = windowToken;
            this.cachedViewTreeCompositionContext = null;
        }
        if (getShouldCreateCompositionOnAttachedToWindow()) {
            ensureCompositionCreated();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        internalOnLayout$ui_release(z, i, i2, i3, i4);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        ensureCompositionCreated();
        internalOnMeasure$ui_release(i, i2);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        View childAt = getChildAt(0);
        if (childAt == null) {
            return;
        }
        childAt.setLayoutDirection(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v13, types: [T, androidx.compose.ui.platform.MotionDurationScaleImpl] */
    public final CompositionContext resolveParentCompositionContext() {
        final Recomposer recomposer;
        CoroutineContext coroutineContext;
        final PausableMonotonicFrameClock pausableMonotonicFrameClock;
        CompositionContext compositionContext = this.parentContext;
        if (compositionContext == null) {
            compositionContext = WindowRecomposer_androidKt.getCompositionContext(this);
            if (compositionContext == null) {
                for (ViewParent parent = getParent(); compositionContext == null && (parent instanceof View); parent = parent.getParent()) {
                    compositionContext = WindowRecomposer_androidKt.getCompositionContext((View) parent);
                }
            }
            if (compositionContext != null) {
                CompositionContext compositionContext2 = (!(compositionContext instanceof Recomposer) || ((Recomposer.State) ((Recomposer) compositionContext)._state.getValue()).compareTo(Recomposer.State.ShuttingDown) > 0) ? compositionContext : null;
                if (compositionContext2 != null) {
                    this.cachedViewTreeCompositionContext = new WeakReference(compositionContext2);
                }
            } else {
                compositionContext = null;
            }
            if (compositionContext == null) {
                WeakReference weakReference = this.cachedViewTreeCompositionContext;
                if (weakReference == null || (compositionContext = (CompositionContext) weakReference.get()) == null || ((compositionContext instanceof Recomposer) && ((Recomposer.State) ((Recomposer) compositionContext)._state.getValue()).compareTo(Recomposer.State.ShuttingDown) <= 0)) {
                    compositionContext = null;
                }
                if (compositionContext == null) {
                    if (!isAttachedToWindow()) {
                        InlineClassHelperKt.throwIllegalStateException("Cannot locate windowRecomposer; View " + this + " is not attached to a window");
                    }
                    Object parent2 = getParent();
                    final View view = this;
                    while (parent2 instanceof View) {
                        View view2 = (View) parent2;
                        if (view2.getId() == 16908290) {
                            break;
                        }
                        view = view2;
                        parent2 = view2.getParent();
                    }
                    CompositionContext compositionContext3 = WindowRecomposer_androidKt.getCompositionContext(view);
                    if (compositionContext3 == null) {
                        WindowRecomposerPolicy.INSTANCE.getClass();
                        ((WindowRecomposerFactory$Companion$$ExternalSyntheticLambda0) ((WindowRecomposerFactory) WindowRecomposerPolicy.factory.get())).getClass();
                        WindowRecomposerFactory.Companion companion = WindowRecomposerFactory.Companion.$$INSTANCE;
                        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
                        ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
                        emptyCoroutineContext.getClass();
                        AndroidUiDispatcher.Companion.getClass();
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            coroutineContext = (CoroutineContext) AndroidUiDispatcher.Main$delegate.getValue();
                        } else {
                            coroutineContext = AndroidUiDispatcher.currentThread.get();
                            if (coroutineContext == null) {
                                throw new IllegalStateException("no AndroidUiDispatcher for this thread");
                            }
                        }
                        CoroutineContext plus = coroutineContext.plus(emptyCoroutineContext);
                        MonotonicFrameClock monotonicFrameClock = (MonotonicFrameClock) plus.get(MonotonicFrameClock.Key);
                        if (monotonicFrameClock != null) {
                            PausableMonotonicFrameClock pausableMonotonicFrameClock2 = new PausableMonotonicFrameClock(monotonicFrameClock);
                            Latch latch = pausableMonotonicFrameClock2.latch;
                            synchronized (latch.lock) {
                                latch._isOpen = false;
                                Unit unit = Unit.INSTANCE;
                                pausableMonotonicFrameClock = pausableMonotonicFrameClock2;
                            }
                        } else {
                            pausableMonotonicFrameClock = 0;
                        }
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        MotionDurationScale motionDurationScale = (MotionDurationScale) plus.get(MotionDurationScale.Key);
                        MotionDurationScale motionDurationScale2 = motionDurationScale;
                        if (motionDurationScale == null) {
                            ?? motionDurationScaleImpl = new MotionDurationScaleImpl();
                            ref$ObjectRef.element = motionDurationScaleImpl;
                            motionDurationScale2 = motionDurationScaleImpl;
                        }
                        if (pausableMonotonicFrameClock != 0) {
                            emptyCoroutineContext = pausableMonotonicFrameClock;
                        }
                        CoroutineContext plus2 = plus.plus(emptyCoroutineContext).plus(motionDurationScale2);
                        recomposer = new Recomposer(plus2);
                        synchronized (recomposer.stateLock) {
                            recomposer.frameClockPaused = true;
                            Unit unit2 = Unit.INSTANCE;
                        }
                        final ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(plus2);
                        LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(view);
                        Lifecycle lifecycle = lifecycleOwner != null ? lifecycleOwner.getLifecycle() : null;
                        if (lifecycle == null) {
                            InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("ViewTreeLifecycleOwner not found from " + view);
                            throw new KotlinNothingValueException();
                        }
                        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$1
                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewDetachedFromWindow(View view3) {
                                view.removeOnAttachStateChangeListener(this);
                                recomposer.cancel();
                            }

                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewAttachedToWindow(View view3) {
                            }
                        });
                        lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.compose.ui.platform.WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            public abstract /* synthetic */ class WhenMappings {
                                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                static {
                                    int[] iArr = new int[Lifecycle.Event.values().length];
                                    try {
                                        iArr[Lifecycle.Event.ON_CREATE.ordinal()] = 1;
                                    } catch (NoSuchFieldError unused) {
                                    }
                                    try {
                                        iArr[Lifecycle.Event.ON_START.ordinal()] = 2;
                                    } catch (NoSuchFieldError unused2) {
                                    }
                                    try {
                                        iArr[Lifecycle.Event.ON_STOP.ordinal()] = 3;
                                    } catch (NoSuchFieldError unused3) {
                                    }
                                    try {
                                        iArr[Lifecycle.Event.ON_DESTROY.ordinal()] = 4;
                                    } catch (NoSuchFieldError unused4) {
                                    }
                                    try {
                                        iArr[Lifecycle.Event.ON_PAUSE.ordinal()] = 5;
                                    } catch (NoSuchFieldError unused5) {
                                    }
                                    try {
                                        iArr[Lifecycle.Event.ON_RESUME.ordinal()] = 6;
                                    } catch (NoSuchFieldError unused6) {
                                    }
                                    try {
                                        iArr[Lifecycle.Event.ON_ANY.ordinal()] = 7;
                                    } catch (NoSuchFieldError unused7) {
                                    }
                                    $EnumSwitchMapping$0 = iArr;
                                }
                            }

                            @Override // androidx.lifecycle.LifecycleEventObserver
                            public final void onStateChanged(LifecycleOwner lifecycleOwner2, Lifecycle.Event event) {
                                boolean z;
                                int i = WhenMappings.$EnumSwitchMapping$0[event.ordinal()];
                                CancellableContinuation cancellableContinuation = null;
                                if (i == 1) {
                                    BuildersKt.launch$default(CoroutineScope.this, null, CoroutineStart.UNDISPATCHED, new WindowRecomposer_androidKt$createLifecycleAwareWindowRecomposer$2$onStateChanged$1(ref$ObjectRef, recomposer, lifecycleOwner2, this, view, null), 1);
                                    return;
                                }
                                if (i != 2) {
                                    if (i != 3) {
                                        if (i != 4) {
                                            return;
                                        }
                                        recomposer.cancel();
                                        return;
                                    } else {
                                        Recomposer recomposer2 = recomposer;
                                        synchronized (recomposer2.stateLock) {
                                            recomposer2.frameClockPaused = true;
                                            Unit unit3 = Unit.INSTANCE;
                                        }
                                        return;
                                    }
                                }
                                PausableMonotonicFrameClock pausableMonotonicFrameClock3 = pausableMonotonicFrameClock;
                                if (pausableMonotonicFrameClock3 != null) {
                                    Latch latch2 = pausableMonotonicFrameClock3.latch;
                                    synchronized (latch2.lock) {
                                        try {
                                            synchronized (latch2.lock) {
                                                z = latch2._isOpen;
                                            }
                                            if (!z) {
                                                List list = latch2.awaiters;
                                                latch2.awaiters = latch2.spareList;
                                                latch2.spareList = list;
                                                latch2._isOpen = true;
                                                ArrayList arrayList = (ArrayList) list;
                                                int size = arrayList.size();
                                                for (int i2 = 0; i2 < size; i2++) {
                                                    Continuation continuation = (Continuation) arrayList.get(i2);
                                                    int i3 = Result.$r8$clinit;
                                                    continuation.resumeWith(Unit.INSTANCE);
                                                }
                                                arrayList.clear();
                                                Unit unit4 = Unit.INSTANCE;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                                Recomposer recomposer3 = recomposer;
                                synchronized (recomposer3.stateLock) {
                                    if (recomposer3.frameClockPaused) {
                                        recomposer3.frameClockPaused = false;
                                        cancellableContinuation = recomposer3.deriveStateLocked();
                                    }
                                }
                                if (cancellableContinuation != null) {
                                    int i4 = Result.$r8$clinit;
                                    ((CancellableContinuationImpl) cancellableContinuation).resumeWith(Unit.INSTANCE);
                                }
                            }
                        });
                        view.setTag(R.id.androidx_compose_ui_view_composition_context, recomposer);
                        GlobalScope globalScope = GlobalScope.INSTANCE;
                        Handler handler = view.getHandler();
                        int i = HandlerDispatcherKt.$r8$clinit;
                        final StandaloneCoroutine launch$default = BuildersKt.launch$default(globalScope, new HandlerContext(handler, "windowRecomposer cleanup").immediate, null, new WindowRecomposerPolicy$createAndInstallWindowRecomposer$unsetJob$1(recomposer, view, null), 2);
                        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.compose.ui.platform.WindowRecomposerPolicy$createAndInstallWindowRecomposer$1
                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewDetachedFromWindow(View view3) {
                                view3.removeOnAttachStateChangeListener(this);
                                Job.this.cancel(null);
                            }

                            @Override // android.view.View.OnAttachStateChangeListener
                            public final void onViewAttachedToWindow(View view3) {
                            }
                        });
                    } else {
                        if (!(compositionContext3 instanceof Recomposer)) {
                            throw new IllegalStateException("root viewTreeParentCompositionContext is not a Recomposer");
                        }
                        recomposer = (Recomposer) compositionContext3;
                    }
                    Recomposer recomposer2 = ((Recomposer.State) recomposer._state.getValue()).compareTo(Recomposer.State.ShuttingDown) > 0 ? recomposer : null;
                    if (recomposer2 != null) {
                        this.cachedViewTreeCompositionContext = new WeakReference(recomposer2);
                    }
                    return recomposer;
                }
            }
        }
        return compositionContext;
    }

    public final void setParentCompositionContext(ComposerImpl.CompositionContextImpl compositionContextImpl) {
        if (this.parentContext != compositionContextImpl) {
            this.parentContext = compositionContextImpl;
            if (compositionContextImpl != null) {
                this.cachedViewTreeCompositionContext = null;
            }
            Composition composition = this.composition;
            if (composition != null) {
                ((WrappedComposition) composition).dispose();
                this.composition = null;
                if (isAttachedToWindow()) {
                    ensureCompositionCreated();
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public final void setTransitionGroup(boolean z) {
        super.setTransitionGroup(z);
        this.isTransitionGroupSet = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.view.View$OnAttachStateChangeListener, androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed$installFor$listener$1] */
    /* JADX WARN: Type inference failed for: r1v0, types: [T, androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed$installFor$1] */
    public final void setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed disposeOnViewTreeLifecycleDestroyed) {
        Function0 function0;
        Function0 function02 = this.disposeViewCompositionStrategy;
        if (function02 != null) {
            function02.invoke();
        }
        disposeOnViewTreeLifecycleDestroyed.getClass();
        if (isAttachedToWindow()) {
            LifecycleOwner lifecycleOwner = ViewTreeLifecycleOwner.get(this);
            if (lifecycleOwner == null) {
                InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("View tree for " + this + " has no ViewTreeLifecycleOwner");
                throw new KotlinNothingValueException();
            }
            function0 = ViewCompositionStrategy_androidKt.access$installForLifecycle(this, lifecycleOwner.getLifecycle());
        } else {
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            final ?? r0 = new View.OnAttachStateChangeListener() { // from class: androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed$installFor$listener$1
                /* JADX WARN: Type inference failed for: r3v6, types: [T, kotlin.jvm.functions.Function0] */
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    LifecycleOwner lifecycleOwner2 = ViewTreeLifecycleOwner.get(AbstractComposeView.this);
                    AbstractComposeView abstractComposeView = AbstractComposeView.this;
                    if (lifecycleOwner2 != null) {
                        ref$ObjectRef.element = ViewCompositionStrategy_androidKt.access$installForLifecycle(abstractComposeView, lifecycleOwner2.getLifecycle());
                        AbstractComposeView.this.removeOnAttachStateChangeListener(this);
                    } else {
                        InlineClassHelperKt.throwIllegalStateExceptionForNullCheck("View tree for " + abstractComposeView + " has no ViewTreeLifecycleOwner");
                        throw new KotlinNothingValueException();
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            };
            addOnAttachStateChangeListener(r0);
            ref$ObjectRef.element = new Function0() { // from class: androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed$installFor$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AbstractComposeView.this.removeOnAttachStateChangeListener(r0);
                    return Unit.INSTANCE;
                }
            };
            function0 = new Function0() { // from class: androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnViewTreeLifecycleDestroyed$installFor$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ref$ObjectRef.element.invoke();
                    return Unit.INSTANCE;
                }
            };
        }
        this.disposeViewCompositionStrategy = function0;
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    public AbstractComposeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ AbstractComposeView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i) {
        checkAddView();
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup
    public final boolean addViewInLayout(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        checkAddView();
        return super.addViewInLayout(view, i, layoutParams, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.view.View$OnAttachStateChangeListener, androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnDetachedFromWindowOrReleasedFromPool$installFor$listener$1] */
    public AbstractComposeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setClipChildren(false);
        setClipToPadding(false);
        setImportantForAccessibility(1);
        ViewCompositionStrategy.Companion.getClass();
        ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool.INSTANCE.getClass();
        final ?? r1 = new View.OnAttachStateChangeListener() { // from class: androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnDetachedFromWindowOrReleasedFromPool$installFor$listener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                if (PoolingContainer.isWithinPoolingContainer(AbstractComposeView.this)) {
                    return;
                }
                AbstractComposeView.this.disposeComposition();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
            }
        };
        addOnAttachStateChangeListener(r1);
        final PoolingContainerListener poolingContainerListener = new PoolingContainerListener() { // from class: androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnDetachedFromWindowOrReleasedFromPool$$ExternalSyntheticLambda0
            @Override // androidx.customview.poolingcontainer.PoolingContainerListener
            public final void onRelease() {
                ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool disposeOnDetachedFromWindowOrReleasedFromPool = ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool.INSTANCE;
                AbstractComposeView.this.disposeComposition();
            }
        };
        PoolingContainer.addPoolingContainerListener(this, poolingContainerListener);
        this.disposeViewCompositionStrategy = new Function0() { // from class: androidx.compose.ui.platform.ViewCompositionStrategy$DisposeOnDetachedFromWindowOrReleasedFromPool$installFor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                AbstractComposeView.this.removeOnAttachStateChangeListener(r1);
                PoolingContainer.removePoolingContainerListener(AbstractComposeView.this, poolingContainerListener);
                return Unit.INSTANCE;
            }
        };
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, int i2) {
        checkAddView();
        super.addView(view, i, i2);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        checkAddView();
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        checkAddView();
        super.addView(view, i, layoutParams);
    }
}
