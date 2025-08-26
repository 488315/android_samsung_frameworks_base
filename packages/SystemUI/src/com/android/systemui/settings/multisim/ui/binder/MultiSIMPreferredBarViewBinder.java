package com.android.systemui.settings.multisim.ui.binder;

import android.util.Log;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Bar;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.ButtonType;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelKt;
import com.android.systemui.settings.multisim.ui.viewmodel.SlotsView;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class MultiSIMPreferredBarViewBinder {
    public static final MultiSIMPreferredBarViewBinder INSTANCE = new MultiSIMPreferredBarViewBinder();
    public static final String TAG = "MULTISIM-BIND";

    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ Bar $bar;
        final /* synthetic */ Ref$BooleanRef $isStateStarted;
        final /* synthetic */ SlotsView $view;
        final /* synthetic */ MultiSIMViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C04451 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ Bar $bar;
            final /* synthetic */ Ref$BooleanRef $isStateStarted;
            final /* synthetic */ SlotsView $view;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            int label;

            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C04461 extends SuspendLambda implements Function2 {
                final /* synthetic */ Bar $bar;
                final /* synthetic */ Ref$BooleanRef $isStateStarted;
                final /* synthetic */ SlotsView $view;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;

                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C04471 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Bar $bar;
                    final /* synthetic */ MultiSIMViewModel $viewModel;
                    int label;

                    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C04481 extends SuspendLambda implements Function2 {
                        final /* synthetic */ Bar $bar;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C04481(Bar bar, Continuation continuation) {
                            super(2, continuation);
                            this.$bar = bar;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C04481(this.$bar, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C04481) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            Log.d(MultiSIMPreferredBarViewBinder.TAG, "Bar updated");
                            ((MultiSIMPreferredSlotBar) this.$bar).updateBarVisibilities();
                            return Unit.INSTANCE;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C04471(MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = multiSIMViewModel;
                        this.$bar = bar;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C04471(this.$viewModel, this.$bar, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C04471) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = ((MultiSIMViewModelImpl) this.$viewModel).barUpdateEvents;
                            C04481 c04481 = new C04481(this.$bar, null);
                            this.label = 1;
                            if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, c04481, this) == coroutineSingletons) {
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

                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$2, reason: invalid class name */
                final class AnonymousClass2 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Ref$BooleanRef $isStateStarted;
                    final /* synthetic */ SlotsView $view;
                    final /* synthetic */ MultiSIMViewModel $viewModel;
                    int label;

                    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$2$1, reason: invalid class name and collision with other inner class name */
                    final class C04491 extends SuspendLambda implements Function2 {
                        final /* synthetic */ Ref$BooleanRef $isStateStarted;
                        final /* synthetic */ SlotsView $view;
                        final /* synthetic */ MultiSIMViewModel $viewModel;
                        /* synthetic */ boolean Z$0;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C04491(SlotsView slotsView, Ref$BooleanRef ref$BooleanRef, MultiSIMViewModel multiSIMViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.$view = slotsView;
                            this.$isStateStarted = ref$BooleanRef;
                            this.$viewModel = multiSIMViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            C04491 c04491 = new C04491(this.$view, this.$isStateStarted, this.$viewModel, continuation);
                            c04491.Z$0 = ((Boolean) obj).booleanValue();
                            return c04491;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Boolean bool = (Boolean) obj;
                            bool.booleanValue();
                            return ((C04491) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            boolean z = this.Z$0;
                            EmergencyButtonController$$ExternalSyntheticOutline0.m("Bar shows: ", MultiSIMPreferredBarViewBinder.TAG, z);
                            MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) this.$view;
                            if (z) {
                                multiSIMPreferredSlotView.getClass();
                            } else {
                                MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow;
                                if (prefferedSlotPopupWindow != null) {
                                    prefferedSlotPopupWindow.dismiss();
                                    multiSIMPreferredSlotView.mPopupWindow = null;
                                }
                            }
                            if (this.$isStateStarted.element) {
                                ((MultiSIMViewModelImpl) this.$viewModel).startUpdating(z);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass2(MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = multiSIMViewModel;
                        this.$view = slotsView;
                        this.$isStateStarted = ref$BooleanRef;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass2(this.$viewModel, this.$view, this.$isStateStarted, continuation);
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
                            MultiSIMViewModel multiSIMViewModel = this.$viewModel;
                            StateFlowImpl stateFlowImpl = ((MultiSIMViewModelImpl) multiSIMViewModel).isBarShowing;
                            C04491 c04491 = new C04491(this.$view, this.$isStateStarted, multiSIMViewModel, null);
                            this.label = 1;
                            if (FlowKt.collectLatest(stateFlowImpl, c04491, this) == coroutineSingletons) {
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
                public C04461(MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = multiSIMViewModel;
                    this.$bar = bar;
                    this.$view = slotsView;
                    this.$isStateStarted = ref$BooleanRef;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04461 c04461 = new C04461(this.$viewModel, this.$bar, this.$view, this.$isStateStarted, continuation);
                    c04461.L$0 = obj;
                    return c04461;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04461) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    BuildersKt.launch$default(coroutineScope, null, null, new C04471(this.$viewModel, this.$bar, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, this.$isStateStarted, null), 3);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04451(LifecycleOwner lifecycleOwner, MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
                super(2, continuation);
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$viewModel = multiSIMViewModel;
                this.$bar = bar;
                this.$view = slotsView;
                this.$isStateStarted = ref$BooleanRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04451(this.$$this$repeatWhenAttached, this.$viewModel, this.$bar, this.$view, this.$isStateStarted, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04451) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                    Lifecycle.State state = Lifecycle.State.CREATED;
                    C04461 c04461 = new C04461(this.$viewModel, this.$bar, this.$view, this.$isStateStarted, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c04461, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ Ref$BooleanRef $isStateStarted;
            final /* synthetic */ SlotsView $view;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            int label;

            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C04501 extends SuspendLambda implements Function2 {
                final /* synthetic */ Ref$BooleanRef $isStateStarted;
                final /* synthetic */ SlotsView $view;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;

                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2$1$1, reason: invalid class name and collision with other inner class name */
                final class C04511 extends SuspendLambda implements Function2 {
                    final /* synthetic */ SlotsView $view;
                    final /* synthetic */ MultiSIMViewModel $viewModel;
                    int label;

                    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C04521 extends SuspendLambda implements Function2 {
                        final /* synthetic */ SlotsView $view;
                        /* synthetic */ Object L$0;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C04521(SlotsView slotsView, Continuation continuation) {
                            super(2, continuation);
                            this.$view = slotsView;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            C04521 c04521 = new C04521(this.$view, continuation);
                            c04521.L$0 = obj;
                            return c04521;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C04521) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            List list = (List) this.L$0;
                            MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow = ((MultiSIMPreferredSlotView) this.$view).mPopupWindow;
                            if (prefferedSlotPopupWindow != null) {
                                prefferedSlotPopupWindow.updateSlotListPopupContents(list);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C04511(MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = multiSIMViewModel;
                        this.$view = slotsView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C04511(this.$viewModel, this.$view, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C04511) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            ReadonlyStateFlow readonlyStateFlow = ((MultiSIMViewModelImpl) this.$viewModel).slots;
                            C04521 c04521 = new C04521(this.$view, null);
                            this.label = 1;
                            if (FlowKt.collectLatest(readonlyStateFlow, c04521, this) == coroutineSingletons) {
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
                public C04501(Ref$BooleanRef ref$BooleanRef, MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
                    super(2, continuation);
                    this.$isStateStarted = ref$BooleanRef;
                    this.$viewModel = multiSIMViewModel;
                    this.$view = slotsView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04501 c04501 = new C04501(this.$isStateStarted, this.$viewModel, this.$view, continuation);
                    c04501.L$0 = obj;
                    return c04501;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04501) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    int i2 = 0;
                    try {
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                            this.$isStateStarted.element = true;
                            MultiSIMViewModel multiSIMViewModel = this.$viewModel;
                            SlotsView slotsView = this.$view;
                            ((MultiSIMViewModelImpl) multiSIMViewModel).bindedView = slotsView;
                            BuildersKt.launch$default(coroutineScope, null, null, new C04511(multiSIMViewModel, slotsView, null), 3);
                            MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) this.$view;
                            multiSIMPreferredSlotView.getClass();
                            ArrayList arrayList = new ArrayList(multiSIMPreferredSlotView.mSlotButtons);
                            int size = arrayList.size();
                            int i3 = 0;
                            while (i3 < size) {
                                Object obj2 = arrayList.get(i3);
                                i3++;
                                MultiSIMViewModelImpl multiSIMViewModelImpl = (MultiSIMViewModelImpl) this.$viewModel;
                                multiSIMViewModelImpl.getClass();
                                MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = (MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj2);
                                prefferedSlotButton.setClickListener(multiSIMViewModelImpl);
                                MultiSIMPreferredBarViewBinder.access$bindButton(MultiSIMPreferredBarViewBinder.INSTANCE, coroutineScope, this.$viewModel, prefferedSlotButton);
                            }
                            String str = MultiSIMPreferredBarViewBinder.TAG;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                            int size2 = arrayList.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj3 = arrayList.get(i4);
                                i4++;
                                arrayList2.add(((MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj3)).mType);
                            }
                            Log.d(str, "Bind " + arrayList2);
                            this.label = 1;
                            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    } catch (Throwable th) {
                        this.$isStateStarted.element = false;
                        ((MultiSIMViewModelImpl) this.$viewModel).bindedView = null;
                        MultiSIMPreferredSlotView multiSIMPreferredSlotView2 = (MultiSIMPreferredSlotView) this.$view;
                        multiSIMPreferredSlotView2.getClass();
                        ArrayList arrayList3 = new ArrayList(multiSIMPreferredSlotView2.mSlotButtons);
                        int size3 = arrayList3.size();
                        while (i2 < size3) {
                            Object obj4 = arrayList3.get(i2);
                            i2++;
                            ((MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj4)).setClickListener(null);
                        }
                        throw th;
                    }
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(LifecycleOwner lifecycleOwner, Ref$BooleanRef ref$BooleanRef, MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
                super(2, continuation);
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$isStateStarted = ref$BooleanRef;
                this.$viewModel = multiSIMViewModel;
                this.$view = slotsView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$$this$repeatWhenAttached, this.$isStateStarted, this.$viewModel, this.$view, continuation);
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
                    LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                    Lifecycle.State state = Lifecycle.State.STARTED;
                    C04501 c04501 = new C04501(this.$isStateStarted, this.$viewModel, this.$view, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c04501, this) == coroutineSingletons) {
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
        public AnonymousClass1(MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = multiSIMViewModel;
            this.$bar = bar;
            this.$view = slotsView;
            this.$isStateStarted = ref$BooleanRef;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$bar, this.$view, this.$isStateStarted, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new C04451(lifecycleOwner, this.$viewModel, this.$bar, this.$view, this.$isStateStarted, null), 3);
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$isStateStarted, this.$viewModel, this.$view, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1, reason: invalid class name and case insensitive filesystem */
    final class C10341 extends SuspendLambda implements Function3 {
        final /* synthetic */ Bar $bar;
        final /* synthetic */ SlotsView $view;
        final /* synthetic */ MultiSIMViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1, reason: invalid class name and collision with other inner class name */
        final class C04531 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ Bar $bar;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            int label;

            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C04541 extends SuspendLambda implements Function2 {
                final /* synthetic */ Bar $bar;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;

                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C04551 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Bar $bar;
                    final /* synthetic */ MultiSIMViewModel $viewModel;
                    int label;

                    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C04561 extends SuspendLambda implements Function2 {
                        final /* synthetic */ Bar $bar;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C04561(Bar bar, Continuation continuation) {
                            super(2, continuation);
                            this.$bar = bar;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            return new C04561(this.$bar, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C04561) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            Log.d(MultiSIMPreferredBarViewBinder.TAG, "CloneBar updated");
                            ((MultiSIMPreferredSlotBar) this.$bar).updateBarVisibilities();
                            return Unit.INSTANCE;
                        }
                    }

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C04551(MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                        super(2, continuation);
                        this.$viewModel = multiSIMViewModel;
                        this.$bar = bar;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C04551(this.$viewModel, this.$bar, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C04551) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = ((MultiSIMViewModelImpl) this.$viewModel).barUpdateEvents;
                            C04561 c04561 = new C04561(this.$bar, null);
                            this.label = 1;
                            if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, c04561, this) == coroutineSingletons) {
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
                public C04541(MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = multiSIMViewModel;
                    this.$bar = bar;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04541 c04541 = new C04541(this.$viewModel, this.$bar, continuation);
                    c04541.L$0 = obj;
                    return c04541;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04541) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new C04551(this.$viewModel, this.$bar, null), 3);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04531(LifecycleOwner lifecycleOwner, MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                super(2, continuation);
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$viewModel = multiSIMViewModel;
                this.$bar = bar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04531(this.$$this$repeatWhenAttached, this.$viewModel, this.$bar, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04531) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                    Lifecycle.State state = Lifecycle.State.CREATED;
                    C04541 c04541 = new C04541(this.$viewModel, this.$bar, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c04541, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
            final /* synthetic */ SlotsView $view;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            int label;

            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$2$1, reason: invalid class name and collision with other inner class name */
            final class C04571 extends SuspendLambda implements Function2 {
                final /* synthetic */ SlotsView $view;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                private /* synthetic */ Object L$0;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C04571(SlotsView slotsView, MultiSIMViewModel multiSIMViewModel, Continuation continuation) {
                    super(2, continuation);
                    this.$view = slotsView;
                    this.$viewModel = multiSIMViewModel;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    C04571 c04571 = new C04571(this.$view, this.$viewModel, continuation);
                    c04571.L$0 = obj;
                    return c04571;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04571) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    try {
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                            MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) this.$view;
                            multiSIMPreferredSlotView.getClass();
                            ArrayList arrayList = new ArrayList(multiSIMPreferredSlotView.mSlotButtons);
                            int size = arrayList.size();
                            int i2 = 0;
                            int i3 = 0;
                            while (i3 < size) {
                                Object obj2 = arrayList.get(i3);
                                i3++;
                                MultiSIMPreferredBarViewBinder.access$bindButton(MultiSIMPreferredBarViewBinder.INSTANCE, coroutineScope, this.$viewModel, (Button) obj2);
                            }
                            String str = MultiSIMPreferredBarViewBinder.TAG;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                            int size2 = arrayList.size();
                            while (i2 < size2) {
                                Object obj3 = arrayList.get(i2);
                                i2++;
                                arrayList2.add(((MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj3)).mType);
                            }
                            Log.d(str, "bind " + arrayList2);
                            this.label = 1;
                            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        throw new KotlinNothingValueException();
                    } catch (Throwable th) {
                        Log.d(MultiSIMPreferredBarViewBinder.TAG, "unbind");
                        throw th;
                    }
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(LifecycleOwner lifecycleOwner, SlotsView slotsView, MultiSIMViewModel multiSIMViewModel, Continuation continuation) {
                super(2, continuation);
                this.$$this$repeatWhenAttached = lifecycleOwner;
                this.$view = slotsView;
                this.$viewModel = multiSIMViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$$this$repeatWhenAttached, this.$view, this.$viewModel, continuation);
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
                    LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                    Lifecycle.State state = Lifecycle.State.STARTED;
                    C04571 c04571 = new C04571(this.$view, this.$viewModel, null);
                    this.label = 1;
                    if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c04571, this) == coroutineSingletons) {
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
        public C10341(MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = multiSIMViewModel;
            this.$bar = bar;
            this.$view = slotsView;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C10341 c10341 = new C10341(this.$viewModel, this.$bar, this.$view, (Continuation) obj3);
            c10341.L$0 = (LifecycleOwner) obj;
            return c10341.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new C04531(lifecycleOwner, this.$viewModel, this.$bar, null), 3);
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$view, this.$viewModel, null), 3);
            return Unit.INSTANCE;
        }
    }

    private MultiSIMPreferredBarViewBinder() {
    }

    public static final void access$bindButton(MultiSIMPreferredBarViewBinder multiSIMPreferredBarViewBinder, CoroutineScope coroutineScope, MultiSIMViewModel multiSIMViewModel, Button button) {
        multiSIMPreferredBarViewBinder.getClass();
        ButtonType buttonType = ((MultiSIMPreferredSlotView.PrefferedSlotButton) button).mType;
        if (buttonType == ButtonType.VOICE || buttonType == ButtonType.DATA) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$1(multiSIMViewModel, buttonType, button, null), 3);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$2(multiSIMViewModel, buttonType, button, null), 3);
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$3(multiSIMViewModel, buttonType, button, null), 3);
        } else {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$4(multiSIMViewModel, buttonType, button, null), 3);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$5(multiSIMViewModel, buttonType, button, null), 3);
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$6(multiSIMViewModel, buttonType, button, null), 3);
        } else {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$7(multiSIMViewModel, buttonType, button, null), 3);
        }
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$8(multiSIMViewModel, button, null), 3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void bind(MultiSIMPreferredSlotBar multiSIMPreferredSlotBar, MultiSIMViewModel multiSIMViewModel) {
        View view = multiSIMPreferredSlotBar.mBarRootView;
        if (view == 0) {
            return;
        }
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(multiSIMViewModel, multiSIMPreferredSlotBar, (SlotsView) view, new Ref$BooleanRef(), null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void bindClone(MultiSIMPreferredSlotBar multiSIMPreferredSlotBar, MultiSIMViewModel multiSIMViewModel) {
        View view = multiSIMPreferredSlotBar.mClonedBarView;
        if (view == 0) {
            return;
        }
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new C10341(multiSIMViewModel, multiSIMPreferredSlotBar, (SlotsView) view, null));
    }
}
