package com.android.systemui.people.p012ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.R;
import com.android.systemui.people.data.repository.PeopleWidgetRepositoryImpl;
import com.android.systemui.people.p012ui.viewmodel.PeopleTileViewModel;
import com.android.systemui.people.p012ui.viewmodel.PeopleViewModel;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2", m277f = "PeopleViewBinder.kt", m278l = {91}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PeopleViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LifecycleOwner $lifecycleOwner;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ PeopleViewModel $viewModel;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1", m277f = "PeopleViewBinder.kt", m278l = {98}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1 */
    final class C19101 extends SuspendLambda implements Function2 {
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ PeopleViewModel $viewModel;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$1", m277f = "PeopleViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
                anonymousClass1.L$0 = (List) obj;
                anonymousClass1.L$1 = (List) obj2;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return new Pair((List) this.L$0, (List) this.L$1);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C19101(PeopleViewModel peopleViewModel, ViewGroup viewGroup, Continuation<? super C19101> continuation) {
            super(2, continuation);
            this.$viewModel = peopleViewModel;
            this.$view = viewGroup;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C19101(this.$viewModel, this.$view, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C19101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PeopleViewModel peopleViewModel = this.$viewModel;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(peopleViewModel.priorityTiles, peopleViewModel.recentTiles, new AnonymousClass1(null));
                final ViewGroup viewGroup = this.$view;
                final PeopleViewModel peopleViewModel2 = this.$viewModel;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder.bind.2.1.2

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$2$1, reason: invalid class name */
                    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
                        public AnonymousClass1(Object obj) {
                            super(1, obj, PeopleViewModel.class, "onTileClicked", "onTileClicked(Lcom/android/systemui/people/ui/viewmodel/PeopleTileViewModel;)V", 0);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            PeopleViewModel peopleViewModel = (PeopleViewModel) this.receiver;
                            ((PeopleWidgetRepositoryImpl) peopleViewModel.widgetRepository).peopleSpaceWidgetManager.addNewWidget(((Number) peopleViewModel._appWidgetId.getValue()).intValue(), ((PeopleTileViewModel) obj).key);
                            Intent intent = new Intent();
                            intent.putExtra("appWidgetId", ((Number) peopleViewModel.appWidgetId.getValue()).intValue());
                            peopleViewModel._result.setValue(new PeopleViewModel.Result.Success(intent));
                            return Unit.INSTANCE;
                        }
                    }

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    /* renamed from: com.android.systemui.people.ui.view.PeopleViewBinder$bind$2$1$2$2, reason: invalid class name and collision with other inner class name */
                    final /* synthetic */ class C48752 extends FunctionReferenceImpl implements Function0 {
                        public C48752(Object obj) {
                            super(0, obj, PeopleViewModel.class, "onUserJourneyCancelled", "onUserJourneyCancelled()V", 0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            ((PeopleViewModel) this.receiver)._result.setValue(PeopleViewModel.Result.Cancelled.INSTANCE);
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        List list = (List) pair.component1();
                        List list2 = (List) pair.component2();
                        boolean z = !list.isEmpty();
                        PeopleViewModel peopleViewModel3 = peopleViewModel2;
                        ViewGroup viewGroup2 = viewGroup;
                        if (z || (!list2.isEmpty())) {
                            PeopleViewBinder peopleViewBinder = PeopleViewBinder.INSTANCE;
                            AnonymousClass1 anonymousClass1 = new AnonymousClass1(peopleViewModel3);
                            peopleViewBinder.getClass();
                            if (viewGroup2.getChildCount() > 1) {
                                throw new IllegalStateException(("view has " + viewGroup2.getChildCount() + " children, it should have maximum 1").toString());
                            }
                            if (viewGroup2.findViewById(R.id.top_level_with_conversations) == null) {
                                if (viewGroup2.getChildCount() == 1) {
                                    viewGroup2.removeViewAt(0);
                                }
                                LayoutInflater.from(viewGroup2.getContext()).inflate(R.layout.people_space_activity_with_conversations, viewGroup2);
                            }
                            View requireViewById = viewGroup2.requireViewById(R.id.top_level_with_conversations);
                            PeopleViewBinder.setTileViews(requireViewById, R.id.priority, R.id.priority_tiles, list, anonymousClass1);
                            PeopleViewBinder.setTileViews(requireViewById, R.id.recent, R.id.recent_tiles, list2, anonymousClass1);
                        } else {
                            PeopleViewBinder peopleViewBinder2 = PeopleViewBinder.INSTANCE;
                            final C48752 c48752 = new C48752(peopleViewModel3);
                            peopleViewBinder2.getClass();
                            if (viewGroup2.getChildCount() > 1) {
                                throw new IllegalStateException(("view has " + viewGroup2.getChildCount() + " children, it should have maximum 1").toString());
                            }
                            if (viewGroup2.findViewById(R.id.top_level_no_conversations) == null) {
                                if (viewGroup2.getChildCount() == 1) {
                                    viewGroup2.removeViewAt(0);
                                }
                                Context context = viewGroup2.getContext();
                                View inflate = LayoutInflater.from(context).inflate(R.layout.people_space_activity_no_conversations, viewGroup2);
                                inflate.findViewById(R.id.got_it_button).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.people.ui.view.PeopleViewBinder$setNoConversationsContent$1
                                    @Override // android.view.View.OnClickListener
                                    public final void onClick(View view) {
                                        Function0.this.invoke();
                                    }
                                });
                                GradientDrawable gradientDrawable = (GradientDrawable) ((LinearLayout) inflate.findViewById(android.R.id.background)).getBackground();
                                TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.^attr-private.colorSurface});
                                gradientDrawable.setColor(obtainStyledAttributes.getColor(0, -1));
                                obtainStyledAttributes.recycle();
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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
    public PeopleViewBinder$bind$2(LifecycleOwner lifecycleOwner, PeopleViewModel peopleViewModel, ViewGroup viewGroup, Continuation<? super PeopleViewBinder$bind$2> continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$viewModel = peopleViewModel;
        this.$view = viewGroup;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PeopleViewBinder$bind$2(this.$lifecycleOwner, this.$viewModel, this.$view, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PeopleViewBinder$bind$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = this.$lifecycleOwner;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C19101 c19101 = new C19101(this.$viewModel, this.$view, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c19101, this) == coroutineSingletons) {
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
