package com.android.systemui.screenshot.ui.binder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;
import com.android.systemui.screenshot.ui.ScreenshotShelfView;
import com.android.systemui.screenshot.ui.SwipeGestureListener;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import com.android.systemui.screenshot.ui.viewmodel.AnimationState;
import com.android.systemui.screenshot.ui.viewmodel.PreviewAction;
import com.android.systemui.screenshot.ui.viewmodel.ScreenshotViewModel;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class ScreenshotShelfViewBinder {
    public final ActionButtonViewBinder buttonViewBinder;

    /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function3 {
        final /* synthetic */ LinearLayout $actionsContainer;
        final /* synthetic */ ImageView $badgeView;
        final /* synthetic */ LayoutInflater $layoutInflater;
        final /* synthetic */ View $previewBorder;
        final /* synthetic */ ImageView $previewView;
        final /* synthetic */ ImageView $previewViewBlur;
        final /* synthetic */ ImageView $scrollablePreview;
        final /* synthetic */ ImageView $scrollingScrim;
        final /* synthetic */ ScreenshotShelfView $view;
        final /* synthetic */ ScreenshotViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ ScreenshotShelfViewBinder this$0;

        /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ LinearLayout $actionsContainer;
            final /* synthetic */ ImageView $badgeView;
            final /* synthetic */ LayoutInflater $layoutInflater;
            final /* synthetic */ View $previewBorder;
            final /* synthetic */ ImageView $previewView;
            final /* synthetic */ ImageView $previewViewBlur;
            final /* synthetic */ ImageView $scrollablePreview;
            final /* synthetic */ ImageView $scrollingScrim;
            final /* synthetic */ ScreenshotShelfView $view;
            final /* synthetic */ ScreenshotViewModel $viewModel;
            int label;
            final /* synthetic */ ScreenshotShelfViewBinder this$0;

            /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1, reason: invalid class name and collision with other inner class name */
            final class C04331 extends SuspendLambda implements Function2 {
                final /* synthetic */ LinearLayout $actionsContainer;
                final /* synthetic */ ImageView $badgeView;
                final /* synthetic */ LayoutInflater $layoutInflater;
                final /* synthetic */ View $previewBorder;
                final /* synthetic */ ImageView $previewView;
                final /* synthetic */ ImageView $previewViewBlur;
                final /* synthetic */ ImageView $scrollablePreview;
                final /* synthetic */ ImageView $scrollingScrim;
                final /* synthetic */ ScreenshotShelfView $view;
                final /* synthetic */ ScreenshotViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;
                final /* synthetic */ ScreenshotShelfViewBinder this$0;

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C04341 extends SuspendLambda implements Function2 {
                    final /* synthetic */ View $previewBorder;
                    final /* synthetic */ ImageView $previewView;
                    final /* synthetic */ ImageView $previewViewBlur;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;
                    final /* synthetic */ ScreenshotShelfViewBinder this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C04341(ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, ImageView imageView2, View view, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.this$0 = screenshotShelfViewBinder;
                        this.$previewView = imageView;
                        this.$previewViewBlur = imageView2;
                        this.$previewBorder = view;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C04341(this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C04341) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            StateFlowImpl stateFlowImpl = this.$viewModel.preview;
                            final ScreenshotShelfViewBinder screenshotShelfViewBinder = this.this$0;
                            final ImageView imageView = this.$previewView;
                            final ImageView imageView2 = this.$previewViewBlur;
                            final View view = this.$previewBorder;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.1.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                                    Bitmap bitmap = (Bitmap) obj2;
                                    if (bitmap != null) {
                                        ImageView imageView3 = imageView;
                                        ScreenshotShelfViewBinder screenshotShelfViewBinder2 = screenshotShelfViewBinder;
                                        ScreenshotShelfViewBinder.access$setScreenshotBitmap(screenshotShelfViewBinder2, imageView3, bitmap);
                                        ScreenshotShelfViewBinder.access$setScreenshotBitmap(screenshotShelfViewBinder2, imageView2, bitmap);
                                        imageView.setVisibility(0);
                                        view.setVisibility(0);
                                    } else {
                                        imageView.setVisibility(8);
                                        view.setVisibility(8);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$2, reason: invalid class name */
                final class AnonymousClass2 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ImageView $scrollingScrim;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass2(ScreenshotViewModel screenshotViewModel, ImageView imageView, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.$scrollingScrim = imageView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass2(this.$viewModel, this.$scrollingScrim, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            StateFlowImpl stateFlowImpl = this.$viewModel.scrollingScrim;
                            final ImageView imageView = this.$scrollingScrim;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.2.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    Bitmap bitmap = (Bitmap) obj2;
                                    if (bitmap != null) {
                                        imageView.setImageBitmap(bitmap);
                                        imageView.setVisibility(0);
                                    } else {
                                        imageView.setVisibility(8);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$3, reason: invalid class name and collision with other inner class name */
                final class C04373 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ImageView $scrollablePreview;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;
                    final /* synthetic */ ScreenshotShelfViewBinder this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C04373(ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.this$0 = screenshotShelfViewBinder;
                        this.$scrollablePreview = imageView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C04373(this.$viewModel, this.this$0, this.$scrollablePreview, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C04373) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            final ScreenshotViewModel screenshotViewModel = this.$viewModel;
                            StateFlowImpl stateFlowImpl = screenshotViewModel.scrollableRect;
                            final ScreenshotShelfViewBinder screenshotShelfViewBinder = this.this$0;
                            final ImageView imageView = this.$scrollablePreview;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.3.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    if (((Rect) obj2) != null) {
                                        ImageView imageView2 = imageView;
                                        Bitmap bitmap = (Bitmap) screenshotViewModel.preview.getValue();
                                        screenshotShelfViewBinder.getClass();
                                        if (bitmap != null) {
                                            float dimensionPixelSize = imageView2.getResources().getDimensionPixelSize(R.dimen.overlay_x_scale) / (imageView2.getResources().getConfiguration().orientation == 1 ? bitmap.getWidth() : bitmap.getHeight());
                                            ViewGroup.LayoutParams layoutParams = imageView2.getLayoutParams();
                                            layoutParams.width = (int) (r6.width() * dimensionPixelSize);
                                            layoutParams.height = (int) (r6.height() * dimensionPixelSize);
                                            Matrix matrix = new Matrix();
                                            matrix.setScale(dimensionPixelSize, dimensionPixelSize);
                                            matrix.postTranslate((-r6.left) * dimensionPixelSize, (-r6.top) * dimensionPixelSize);
                                            imageView2.setTranslationX((imageView2.getLayoutDirection() == 0 ? r6.left : r6.right - ((View) imageView2.getParent()).getWidth()) * dimensionPixelSize);
                                            imageView2.setTranslationY(dimensionPixelSize * r6.top);
                                            imageView2.setImageMatrix(matrix);
                                            imageView2.setImageBitmap(bitmap);
                                            imageView2.setVisibility(0);
                                        }
                                    } else {
                                        imageView.setVisibility(8);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$4, reason: invalid class name */
                final class AnonymousClass4 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ImageView $badgeView;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass4(ScreenshotViewModel screenshotViewModel, ImageView imageView, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.$badgeView = imageView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass4(this.$viewModel, this.$badgeView, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            StateFlowImpl stateFlowImpl = this.$viewModel.badge;
                            final ImageView imageView = this.$badgeView;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.4.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    Drawable drawable = (Drawable) obj2;
                                    imageView.setImageDrawable(drawable);
                                    imageView.setVisibility(drawable != null ? 0 : 8);
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$5, reason: invalid class name */
                final class AnonymousClass5 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ImageView $previewView;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass5(ScreenshotViewModel screenshotViewModel, ImageView imageView, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.$previewView = imageView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass5(this.$viewModel, this.$previewView, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            StateFlowImpl stateFlowImpl = this.$viewModel.previewAction;
                            final ImageView imageView = this.$previewView;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.5.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    final PreviewAction previewAction = (PreviewAction) obj2;
                                    imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.5.1.1
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            Function0 function0;
                                            PreviewAction previewAction2 = previewAction;
                                            if (previewAction2 == null || (function0 = previewAction2.onClick) == null) {
                                                return;
                                            }
                                            function0.invoke();
                                        }
                                    });
                                    imageView.setContentDescription(previewAction != null ? previewAction.contentDescription : null);
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$6, reason: invalid class name */
                final class AnonymousClass6 extends SuspendLambda implements Function2 {
                    final /* synthetic */ LinearLayout $actionsContainer;
                    final /* synthetic */ ImageView $previewView;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass6(ScreenshotViewModel screenshotViewModel, ImageView imageView, LinearLayout linearLayout, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.$previewView = imageView;
                        this.$actionsContainer = linearLayout;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass6(this.$viewModel, this.$previewView, this.$actionsContainer, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            StateFlowImpl stateFlowImpl = this.$viewModel.isAnimating;
                            final ImageView imageView = this.$previewView;
                            final LinearLayout linearLayout = this.$actionsContainer;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.6.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    boolean z = !((Boolean) obj2).booleanValue();
                                    imageView.setClickable(z);
                                    Iterator it = ConvenienceExtensionsKt.getChildren(linearLayout).iterator();
                                    while (it.hasNext()) {
                                        ((View) it.next()).setClickable(z);
                                    }
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$7, reason: invalid class name */
                final class AnonymousClass7 extends SuspendLambda implements Function2 {
                    final /* synthetic */ LayoutInflater $layoutInflater;
                    final /* synthetic */ ScreenshotShelfView $view;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;
                    final /* synthetic */ ScreenshotShelfViewBinder this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass7(ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.this$0 = screenshotShelfViewBinder;
                        this.$view = screenshotShelfView;
                        this.$layoutInflater = layoutInflater;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass7(this.$viewModel, this.this$0, this.$view, this.$layoutInflater, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            final ScreenshotViewModel screenshotViewModel = this.$viewModel;
                            StateFlowImpl stateFlowImpl = screenshotViewModel.actions;
                            final ScreenshotShelfViewBinder screenshotShelfViewBinder = this.this$0;
                            final ScreenshotShelfView screenshotShelfView = this.$view;
                            final LayoutInflater layoutInflater = this.$layoutInflater;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.7.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    AnimationState animationState = (AnimationState) screenshotViewModel.animationState.getValue();
                                    LayoutInflater layoutInflater2 = layoutInflater;
                                    ScreenshotShelfViewBinder.access$updateActions(screenshotShelfViewBinder, (List) obj2, animationState, screenshotShelfView, layoutInflater2);
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* renamed from: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$bind$3$1$1$8, reason: invalid class name */
                final class AnonymousClass8 extends SuspendLambda implements Function2 {
                    final /* synthetic */ LayoutInflater $layoutInflater;
                    final /* synthetic */ ScreenshotShelfView $view;
                    final /* synthetic */ ScreenshotViewModel $viewModel;
                    int label;
                    final /* synthetic */ ScreenshotShelfViewBinder this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass8(ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = screenshotViewModel;
                        this.this$0 = screenshotShelfViewBinder;
                        this.$view = screenshotShelfView;
                        this.$layoutInflater = layoutInflater;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass8(this.$viewModel, this.this$0, this.$view, this.$layoutInflater, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            final ScreenshotViewModel screenshotViewModel = this.$viewModel;
                            StateFlowImpl stateFlowImpl = screenshotViewModel.animationState;
                            final ScreenshotShelfViewBinder screenshotShelfViewBinder = this.this$0;
                            final ScreenshotShelfView screenshotShelfView = this.$view;
                            final LayoutInflater layoutInflater = this.$layoutInflater;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.3.1.1.8.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) {
                                    List list = (List) screenshotViewModel.actions.getValue();
                                    LayoutInflater layoutInflater2 = layoutInflater;
                                    ScreenshotShelfViewBinder.access$updateActions(screenshotShelfViewBinder, list, (AnimationState) obj2, screenshotShelfView, layoutInflater2);
                                    return Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04331(ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, ImageView imageView2, View view, ImageView imageView3, ImageView imageView4, ImageView imageView5, LinearLayout linearLayout, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = screenshotViewModel;
                    this.this$0 = screenshotShelfViewBinder;
                    this.$previewView = imageView;
                    this.$previewViewBlur = imageView2;
                    this.$previewBorder = view;
                    this.$scrollingScrim = imageView3;
                    this.$scrollablePreview = imageView4;
                    this.$badgeView = imageView5;
                    this.$actionsContainer = linearLayout;
                    this.$view = screenshotShelfView;
                    this.$layoutInflater = layoutInflater;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04331 c04331 = new C04331(this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, this.$scrollingScrim, this.$scrollablePreview, this.$badgeView, this.$actionsContainer, this.$view, this.$layoutInflater, continuation);
                    c04331.L$0 = obj;
                    return c04331;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04331) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C04341(this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$scrollingScrim, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C04373(this.$viewModel, this.this$0, this.$scrollablePreview, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$badgeView, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.$viewModel, this.$previewView, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.$viewModel, this.$previewView, this.$actionsContainer, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.$viewModel, this.this$0, this.$view, this.$layoutInflater, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.$viewModel, this.this$0, this.$view, this.$layoutInflater, null), 7);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(LifecycleOwner lifecycleOwner, ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, ImageView imageView2, View view, ImageView imageView3, ImageView imageView4, ImageView imageView5, LinearLayout linearLayout, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater, Continuation continuation) {
                super(2, continuation);
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$viewModel = screenshotViewModel;
                this.this$0 = screenshotShelfViewBinder;
                this.$previewView = imageView;
                this.$previewViewBlur = imageView2;
                this.$previewBorder = view;
                this.$scrollingScrim = imageView3;
                this.$scrollablePreview = imageView4;
                this.$badgeView = imageView5;
                this.$actionsContainer = linearLayout;
                this.$view = screenshotShelfView;
                this.$layoutInflater = layoutInflater;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, this.$scrollingScrim, this.$scrollablePreview, this.$badgeView, this.$actionsContainer, this.$view, this.$layoutInflater, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                    Lifecycle.State state = Lifecycle.State.STARTED;
                    C04331 c04331 = new C04331(this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, this.$scrollingScrim, this.$scrollablePreview, this.$badgeView, this.$actionsContainer, this.$view, this.$layoutInflater, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c04331, this) == coroutineSingletons) {
                        return coroutineSingletons;
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ScreenshotViewModel screenshotViewModel, ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, ImageView imageView2, View view, ImageView imageView3, ImageView imageView4, ImageView imageView5, LinearLayout linearLayout, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = screenshotViewModel;
            this.this$0 = screenshotShelfViewBinder;
            this.$previewView = imageView;
            this.$previewViewBlur = imageView2;
            this.$previewBorder = view;
            this.$scrollingScrim = imageView3;
            this.$scrollablePreview = imageView4;
            this.$badgeView = imageView5;
            this.$actionsContainer = linearLayout;
            this.$view = screenshotShelfView;
            this.$layoutInflater = layoutInflater;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, this.$scrollingScrim, this.$scrollablePreview, this.$badgeView, this.$actionsContainer, this.$view, this.$layoutInflater, (Continuation) obj3);
            anonymousClass3.L$0 = (LifecycleOwner) obj;
            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$viewModel, this.this$0, this.$previewView, this.$previewViewBlur, this.$previewBorder, this.$scrollingScrim, this.$scrollablePreview, this.$badgeView, this.$actionsContainer, this.$view, this.$layoutInflater, null), 7);
            return Unit.INSTANCE;
        }
    }

    public ScreenshotShelfViewBinder(ActionButtonViewBinder actionButtonViewBinder) {
        this.buttonViewBinder = actionButtonViewBinder;
    }

    public static final void access$setScreenshotBitmap(ScreenshotShelfViewBinder screenshotShelfViewBinder, ImageView imageView, Bitmap bitmap) throws Resources.NotFoundException {
        screenshotShelfViewBinder.getClass();
        imageView.setImageBitmap(bitmap);
        boolean z = bitmap.getWidth() < bitmap.getHeight();
        int dimensionPixelSize = imageView.getResources().getDimensionPixelSize(R.dimen.overlay_x_scale);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (z) {
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = -2;
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            layoutParams.width = -2;
            layoutParams.height = dimensionPixelSize;
            imageView.setScaleType(ImageView.ScaleType.FIT_END);
        }
        imageView.setLayoutParams(layoutParams);
        imageView.requestLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$updateActions(ScreenshotShelfViewBinder screenshotShelfViewBinder, List list, AnimationState animationState, ScreenshotShelfView screenshotShelfView, LayoutInflater layoutInflater) {
        screenshotShelfViewBinder.getClass();
        LinearLayout linearLayout = (LinearLayout) screenshotShelfView.requireViewById(R.id.screenshot_actions);
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            ActionButtonViewModel actionButtonViewModel = (ActionButtonViewModel) obj;
            if (actionButtonViewModel.visible && (animationState == AnimationState.ENTRANCE_COMPLETE || animationState == AnimationState.ENTRANCE_REVEAL || actionButtonViewModel.showDuringEntrance)) {
                arrayList.add(obj);
            }
        }
        if (!arrayList.isEmpty()) {
            screenshotShelfView.requireViewById(R.id.actions_container_background).setVisibility(0);
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            arrayList2.add(Integer.valueOf(((ActionButtonViewModel) obj2).id));
        }
        for (View view : SequencesKt___SequencesKt.toList(ConvenienceExtensionsKt.getChildren(linearLayout))) {
            if (!CollectionsKt___CollectionsKt.contains(arrayList2, view.getTag())) {
                linearLayout.removeView(view);
            }
        }
        Iterator it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int i3 = i2 + 1;
            ActionButtonViewModel actionButtonViewModel2 = (ActionButtonViewModel) it.next();
            View childAt = linearLayout.getChildAt(i2);
            ActionButtonViewBinder actionButtonViewBinder = screenshotShelfViewBinder.buttonViewBinder;
            if (childAt != null) {
                int i4 = actionButtonViewModel2.id;
                Object tag = childAt.getTag();
                if ((tag instanceof Integer) && i4 == ((Number) tag).intValue()) {
                    actionButtonViewBinder.getClass();
                    ActionButtonViewBinder.bind(childAt, actionButtonViewModel2);
                } else {
                    View viewInflate = layoutInflater.inflate(R.layout.shelf_action_chip, (ViewGroup) linearLayout, false);
                    linearLayout.addView(viewInflate, i2);
                    viewInflate.getClass();
                    actionButtonViewBinder.getClass();
                    ActionButtonViewBinder.bind(viewInflate, actionButtonViewModel2);
                }
            }
            i2 = i3;
        }
    }

    public final void bind(ScreenshotShelfView screenshotShelfView, ScreenshotViewModel screenshotViewModel, final ScreenshotAnimationController screenshotAnimationController, LayoutInflater layoutInflater, final ScreenshotShelfViewProxy$$ExternalSyntheticLambda0 screenshotShelfViewProxy$$ExternalSyntheticLambda0, ScreenshotShelfViewProxy$$ExternalSyntheticLambda1 screenshotShelfViewProxy$$ExternalSyntheticLambda1) {
        screenshotShelfView.onTouchInterceptListener = new ScreenshotShelfViewBinder$$ExternalSyntheticLambda0(new SwipeGestureListener(screenshotShelfView, new ScreenshotShelfViewBinder$$ExternalSyntheticLambda0(screenshotShelfViewProxy$$ExternalSyntheticLambda0, 0), new Function0() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final ScreenshotAnimationController screenshotAnimationController2 = screenshotAnimationController;
                Animator animator = screenshotAnimationController2.animator;
                if (animator != null) {
                    animator.cancel();
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(screenshotAnimationController2.view.getTranslationX(), 0.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$getSwipeReturnAnimation$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        screenshotAnimationController2.view.setTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                screenshotAnimationController2.animator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.start();
                return Unit.INSTANCE;
            }
        }), 1);
        screenshotShelfView.userInteractionCallback = screenshotShelfViewProxy$$ExternalSyntheticLambda1;
        ImageView imageView = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_preview);
        ImageView imageView2 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_preview_blur);
        View viewRequireViewById = screenshotShelfView.requireViewById(R.id.screenshot_preview_border);
        imageView.setClipToOutline(true);
        imageView2.setClipToOutline(true);
        LinearLayout linearLayout = (LinearLayout) screenshotShelfView.requireViewById(R.id.screenshot_actions);
        View viewRequireViewById2 = screenshotShelfView.requireViewById(R.id.screenshot_dismiss_button);
        viewRequireViewById2.setVisibility(screenshotViewModel.accessibilityManager.isEnabled() ? 0 : 8);
        viewRequireViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ui.binder.ScreenshotShelfViewBinder.bind.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                screenshotShelfViewProxy$$ExternalSyntheticLambda0.invoke(ScreenshotEvent.SCREENSHOT_EXPLICIT_DISMISSAL, null);
            }
        });
        ImageView imageView3 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_scrolling_scrim);
        ImageView imageView4 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_scrollable_preview);
        ImageView imageView5 = (ImageView) screenshotShelfView.requireViewById(R.id.screenshot_badge);
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        RepeatWhenAttachedKt.repeatWhenAttached(screenshotShelfView, MainDispatcherLoader.dispatcher.immediate, new AnonymousClass3(screenshotViewModel, this, imageView, imageView2, viewRequireViewById, imageView3, imageView4, imageView5, linearLayout, screenshotShelfView, layoutInflater, null));
    }
}
