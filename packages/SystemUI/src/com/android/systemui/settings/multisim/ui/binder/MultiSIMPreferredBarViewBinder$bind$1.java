package com.android.systemui.settings.multisim.ui.binder;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Bar;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import com.android.systemui.settings.multisim.ui.viewmodel.SlotsView;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MultiSIMPreferredBarViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Bar $bar;
    final /* synthetic */ SlotsView $view;
    final /* synthetic */ MultiSIMViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ Bar $bar;
        final /* synthetic */ SlotsView $view;
        final /* synthetic */ MultiSIMViewModel $viewModel;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02841 extends SuspendLambda implements Function2 {
            final /* synthetic */ Bar $bar;
            final /* synthetic */ SlotsView $view;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02851 extends SuspendLambda implements Function2 {
                final /* synthetic */ Bar $bar;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                int label;

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C02861 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Bar $bar;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C02861(Bar bar, Continuation continuation) {
                        super(2, continuation);
                        this.$bar = bar;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C02861(this.$bar, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C02861) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                public C02851(MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = multiSIMViewModel;
                    this.$bar = bar;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02851(this.$viewModel, this.$bar, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02851) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = ((MultiSIMViewModelImpl) this.$viewModel).barUpdateEvents;
                        C02861 c02861 = new C02861(this.$bar, null);
                        this.label = 1;
                        if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, c02861, this) == coroutineSingletons) {
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ SlotsView $view;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                int label;

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$1$1$2$1, reason: invalid class name and collision with other inner class name */
                final class C02871 extends SuspendLambda implements Function2 {
                    final /* synthetic */ SlotsView $view;
                    /* synthetic */ boolean Z$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C02871(SlotsView slotsView, Continuation continuation) {
                        super(2, continuation);
                        this.$view = slotsView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        C02871 c02871 = new C02871(this.$view, continuation);
                        c02871.Z$0 = ((Boolean) obj).booleanValue();
                        return c02871;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        Boolean bool = (Boolean) obj;
                        bool.booleanValue();
                        return ((C02871) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = multiSIMViewModel;
                    this.$view = slotsView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$viewModel, this.$view, continuation);
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
                        StateFlowImpl stateFlowImpl = ((MultiSIMViewModelImpl) this.$viewModel).isBarShowing;
                        C02871 c02871 = new C02871(this.$view, null);
                        this.label = 1;
                        if (FlowKt.collectLatest(stateFlowImpl, c02871, this) == coroutineSingletons) {
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
            public C02841(MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = multiSIMViewModel;
                this.$bar = bar;
                this.$view = slotsView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02841 c02841 = new C02841(this.$viewModel, this.$bar, this.$view, continuation);
                c02841.L$0 = obj;
                return c02841;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02841) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new C02851(this.$viewModel, this.$bar, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$view, null), 3);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LifecycleOwner lifecycleOwner, MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModel = multiSIMViewModel;
            this.$bar = bar;
            this.$view = slotsView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$viewModel, this.$bar, this.$view, continuation);
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
                Lifecycle.State state = Lifecycle.State.CREATED;
                C02841 c02841 = new C02841(this.$viewModel, this.$bar, this.$view, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02841, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ SlotsView $view;
        final /* synthetic */ MultiSIMViewModel $viewModel;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ SlotsView $view;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C02881 extends SuspendLambda implements Function2 {
                final /* synthetic */ SlotsView $view;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                int label;

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bind$1$2$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C02891 extends SuspendLambda implements Function2 {
                    final /* synthetic */ SlotsView $view;
                    /* synthetic */ Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C02891(SlotsView slotsView, Continuation continuation) {
                        super(2, continuation);
                        this.$view = slotsView;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        C02891 c02891 = new C02891(this.$view, continuation);
                        c02891.L$0 = obj;
                        return c02891;
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C02891) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                public C02881(MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = multiSIMViewModel;
                    this.$view = slotsView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02881(this.$viewModel, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02881) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ReadonlyStateFlow readonlyStateFlow = ((MultiSIMViewModelImpl) this.$viewModel).slots;
                        C02891 c02891 = new C02891(this.$view, null);
                        this.label = 1;
                        if (FlowKt.collectLatest(readonlyStateFlow, c02891, this) == coroutineSingletons) {
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
            public AnonymousClass1(MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = multiSIMViewModel;
                this.$view = slotsView;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        ((MultiSIMViewModelImpl) this.$viewModel).register(this.$view);
                        BuildersKt.launch$default(coroutineScope, null, null, new C02881(this.$viewModel, this.$view, null), 3);
                        MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) this.$view;
                        multiSIMPreferredSlotView.getClass();
                        ArrayList arrayList = new ArrayList(multiSIMPreferredSlotView.mSlotButtons);
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj2 = arrayList.get(i2);
                            i2++;
                            MultiSIMViewModelImpl multiSIMViewModelImpl = (MultiSIMViewModelImpl) this.$viewModel;
                            multiSIMViewModelImpl.getClass();
                            MultiSIMPreferredSlotView.PrefferedSlotButton prefferedSlotButton = (MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj2);
                            prefferedSlotButton.setClickListener(multiSIMViewModelImpl);
                            MultiSIMPreferredBarViewBinder.access$bindButton(MultiSIMPreferredBarViewBinder.INSTANCE, coroutineScope, this.$viewModel, prefferedSlotButton);
                        }
                        String str = MultiSIMPreferredBarViewBinder.TAG;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        int size2 = arrayList.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            Object obj3 = arrayList.get(i3);
                            i3++;
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
                    MultiSIMPreferredSlotView multiSIMPreferredSlotView2 = (MultiSIMPreferredSlotView) this.$view;
                    multiSIMPreferredSlotView2.getClass();
                    ArrayList arrayList3 = new ArrayList(multiSIMPreferredSlotView2.mSlotButtons);
                    int size3 = arrayList3.size();
                    int i4 = 0;
                    while (i4 < size3) {
                        Object obj4 = arrayList3.get(i4);
                        i4++;
                        ((MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj4)).setClickListener(null);
                    }
                    MultiSIMViewModelImpl multiSIMViewModelImpl2 = (MultiSIMViewModelImpl) this.$viewModel;
                    multiSIMViewModelImpl2.bindedView = null;
                    SimInfoRepositoryImpl simInfoRepositoryImpl = (SimInfoRepositoryImpl) multiSIMViewModelImpl2.simInfoRepository;
                    simInfoRepositoryImpl.needUpdatePhoneNumber = null;
                    if (simInfoRepositoryImpl.simCardManagerService != null) {
                        try {
                            if (simInfoRepositoryImpl.simCardCallback != null && !SimCardManagerServiceProvider.mIsRemainCallbackCall) {
                                SimCardManagerServiceProvider.sSimCardManagerServiceCallback = null;
                            }
                            simInfoRepositoryImpl.simCardCallback = null;
                        } catch (Exception e) {
                            Log.d("MULTISIM-PROD-REPO", "Caught exception from unRegisterSimCardManagerCallback", e);
                        }
                    }
                    if (SimCardManagerServiceProvider.sServiceBindHelper != null) {
                        int i5 = (SimCardManagerServiceProvider.mIsRemainCallbackCall && SimCardManagerServiceProvider.isServiceRunningCheck(SimCardManagerServiceProvider.mContext)) ? VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS : 0;
                        SimCardManagerServiceProvider.AnonymousClass1 anonymousClass1 = SimCardManagerServiceProvider.mHandler;
                        if (anonymousClass1 != null) {
                            Log.d("SimCardManagerServiceProvider", "CloseService : mIsRemainCallbackCall = " + SimCardManagerServiceProvider.mIsRemainCallbackCall + ", delayTime = " + i5);
                            if (SimCardManagerServiceProvider.sServiceBindHelper.mServiceStatus == 0) {
                                Log.d("SimCardManagerServiceProvider", "CloseService : already disconnected so initial value");
                                SimCardManagerServiceProvider.sSimCardManagerServiceCallback = null;
                                SimCardManagerServiceProvider.sServiceBindHelper = null;
                                SimCardManagerServiceProvider.sInstance = null;
                                SimCardManagerServiceProvider.mIsServiceClose = true;
                                SimCardManagerServiceProvider.mIsRemainCallbackCall = false;
                            } else {
                                anonymousClass1.sendMessageDelayed(anonymousClass1.obtainMessage(0), i5);
                            }
                        }
                    }
                    simInfoRepositoryImpl.simCardManagerService = null;
                    Log.d("MULTISIM-PROD-REPO", "SimCardManagerCallback unregistered");
                    simInfoRepositoryImpl.isRegistered = false;
                    throw th;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(LifecycleOwner lifecycleOwner, MultiSIMViewModel multiSIMViewModel, SlotsView slotsView, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModel = multiSIMViewModel;
            this.$view = slotsView;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$$this$repeatWhenAttached, this.$viewModel, this.$view, continuation);
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
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$view, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
    public MultiSIMPreferredBarViewBinder$bind$1(MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = multiSIMViewModel;
        this.$bar = bar;
        this.$view = slotsView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MultiSIMPreferredBarViewBinder$bind$1 multiSIMPreferredBarViewBinder$bind$1 = new MultiSIMPreferredBarViewBinder$bind$1(this.$viewModel, this.$bar, this.$view, (Continuation) obj3);
        multiSIMPreferredBarViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return multiSIMPreferredBarViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$viewModel, this.$bar, this.$view, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$viewModel, this.$view, null), 3);
        return Unit.INSTANCE;
    }
}
