package androidx.picker.helper;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import androidx.picker.loader.AppIconFlow;
import com.facebook.shimmer.ShimmerFrameLayout;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "androidx.picker.helper.ImageViewHelperKt$loadIcon$job$1", m277f = "ImageViewHelper.kt", m278l = {42}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ImageViewHelperKt$loadIcon$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CoroutineDispatcher $dispatcher;
    final /* synthetic */ AppIconFlow $iconFlow;
    final /* synthetic */ ShimmerFrameLayout $shimmerLayout;
    final /* synthetic */ ImageView $this_loadIcon;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageViewHelperKt$loadIcon$job$1(AppIconFlow appIconFlow, CoroutineDispatcher coroutineDispatcher, ImageView imageView, ShimmerFrameLayout shimmerFrameLayout, Continuation<? super ImageViewHelperKt$loadIcon$job$1> continuation) {
        super(2, continuation);
        this.$iconFlow = appIconFlow;
        this.$dispatcher = coroutineDispatcher;
        this.$this_loadIcon = imageView;
        this.$shimmerLayout = shimmerFrameLayout;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ImageViewHelperKt$loadIcon$job$1(this.$iconFlow, this.$dispatcher, this.$this_loadIcon, this.$shimmerLayout, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ImageViewHelperKt$loadIcon$job$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flowOn = FlowKt.flowOn(this.$iconFlow, this.$dispatcher);
            final ImageView imageView = this.$this_loadIcon;
            final ShimmerFrameLayout shimmerFrameLayout = this.$shimmerLayout;
            FlowCollector flowCollector = new FlowCollector() { // from class: androidx.picker.helper.ImageViewHelperKt$loadIcon$job$1.1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "androidx.picker.helper.ImageViewHelperKt$loadIcon$job$1$1$1", m277f = "ImageViewHelper.kt", m278l = {}, m279m = "invokeSuspend")
                /* renamed from: androidx.picker.helper.ImageViewHelperKt$loadIcon$job$1$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Drawable $drawable;
                    final /* synthetic */ ShimmerFrameLayout $shimmerLayout;
                    final /* synthetic */ ImageView $this_loadIcon;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(ImageView imageView, Drawable drawable, ShimmerFrameLayout shimmerFrameLayout, Continuation<? super AnonymousClass1> continuation) {
                        super(2, continuation);
                        this.$this_loadIcon = imageView;
                        this.$drawable = drawable;
                        this.$shimmerLayout = shimmerFrameLayout;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.$this_loadIcon, this.$drawable, this.$shimmerLayout, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        this.$this_loadIcon.setImageDrawable(this.$drawable);
                        this.$shimmerLayout.setVisibility(8);
                        this.$shimmerLayout.stopShimmer();
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    Object withContext = BuildersKt.withContext(MainDispatcherLoader.dispatcher, new AnonymousClass1(imageView, (Drawable) obj2, shimmerFrameLayout, null), continuation);
                    return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowOn.collect(flowCollector, this) == coroutineSingletons) {
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
