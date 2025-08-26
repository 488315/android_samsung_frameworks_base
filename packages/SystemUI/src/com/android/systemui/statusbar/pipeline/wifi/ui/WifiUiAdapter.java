package com.android.systemui.statusbar.pipeline.wifi.ui;

import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.wifi.ui.model.WifiIcon;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.HomeWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.KeyguardWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.LocationBasedWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.QsWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.SubScreenQsWifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class WifiUiAdapter {
    public final StatusBarIconController iconController;
    public final StatusBarPipelineFlags statusBarPipelineFlags;
    public final WifiViewModel wifiViewModel;

    /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter$bindGroup$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ LocationBasedWifiViewModel $locationViewModel;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ WifiUiAdapter this$0;

        /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter$bindGroup$1$1, reason: invalid class name and collision with other inner class name */
        final class C05881 extends SuspendLambda implements Function2 {
            final /* synthetic */ LocationBasedWifiViewModel $locationViewModel;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ WifiUiAdapter this$0;

            /* renamed from: com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter$bindGroup$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C05891 extends SuspendLambda implements Function2 {
                final /* synthetic */ LocationBasedWifiViewModel $locationViewModel;
                int label;
                final /* synthetic */ WifiUiAdapter this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05891(LocationBasedWifiViewModel locationBasedWifiViewModel, WifiUiAdapter wifiUiAdapter, Continuation continuation) {
                    super(2, continuation);
                    this.$locationViewModel = locationBasedWifiViewModel;
                    this.this$0 = wifiUiAdapter;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05891(this.$locationViewModel, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05891) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        StateFlow wifiIcon = this.$locationViewModel.commonImpl.getWifiIcon();
                        final WifiUiAdapter wifiUiAdapter = this.this$0;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter.bindGroup.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                if (((WifiIcon) obj2) instanceof WifiIcon.Visible) {
                                    StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) wifiUiAdapter.iconController;
                                    String string = statusBarIconControllerImpl.mContext.getString(17043309);
                                    if (statusBarIconControllerImpl.mStatusBarIconList.getIconHolder(0, string) == null) {
                                        StatusBarIconHolder.Companion.getClass();
                                        StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
                                        statusBarIconHolder.type = 4;
                                        statusBarIconControllerImpl.setIcon(string, statusBarIconHolder);
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (wifiIcon.collect(flowCollector, this) == coroutineSingletons) {
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
            public C05881(LocationBasedWifiViewModel locationBasedWifiViewModel, WifiUiAdapter wifiUiAdapter, Continuation continuation) {
                super(2, continuation);
                this.$locationViewModel = locationBasedWifiViewModel;
                this.this$0 = wifiUiAdapter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C05881 c05881 = new C05881(this.$locationViewModel, this.this$0, continuation);
                c05881.L$0 = obj;
                return c05881;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05881) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C05891(this.$locationViewModel, this.this$0, null), 7);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LocationBasedWifiViewModel locationBasedWifiViewModel, WifiUiAdapter wifiUiAdapter, Continuation continuation) {
            super(3, continuation);
            this.$locationViewModel = locationBasedWifiViewModel;
            this.this$0 = wifiUiAdapter;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$locationViewModel, this.this$0, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C05881 c05881 = new C05881(this.$locationViewModel, this.this$0, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05881, this) == coroutineSingletons) {
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

    public WifiUiAdapter(StatusBarIconController statusBarIconController, WifiViewModel wifiViewModel, StatusBarPipelineFlags statusBarPipelineFlags) {
        this.iconController = statusBarIconController;
        this.wifiViewModel = wifiViewModel;
        this.statusBarPipelineFlags = statusBarPipelineFlags;
    }

    public final LocationBasedWifiViewModel bindGroup(ViewGroup viewGroup, StatusBarLocation statusBarLocation) {
        LocationBasedWifiViewModel homeWifiViewModel;
        LocationBasedWifiViewModel.Companion.getClass();
        int i = LocationBasedWifiViewModel.Companion.WhenMappings.$EnumSwitchMapping$0[statusBarLocation.ordinal()];
        WifiViewModel wifiViewModel = this.wifiViewModel;
        StatusBarPipelineFlags statusBarPipelineFlags = this.statusBarPipelineFlags;
        switch (i) {
            case 1:
                homeWifiViewModel = new HomeWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 2:
                homeWifiViewModel = new KeyguardWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 3:
                homeWifiViewModel = new QsWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 4:
                homeWifiViewModel = new SubScreenQsWifiViewModel(wifiViewModel, statusBarPipelineFlags, statusBarLocation);
                break;
            case 5:
                throw new IllegalArgumentException("invalid location for WifiViewModel: " + statusBarLocation);
            case 6:
                throw new IllegalArgumentException("invalid location for WifiViewModel: " + statusBarLocation);
            default:
                throw new NoWhenBranchMatchedException();
        }
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(homeWifiViewModel, this, null));
        return homeWifiViewModel;
    }
}
