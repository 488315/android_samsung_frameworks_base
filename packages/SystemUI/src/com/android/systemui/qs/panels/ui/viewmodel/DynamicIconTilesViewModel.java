package com.android.systemui.qs.panels.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.qs.panels.domain.interactor.DynamicIconTilesInteractor;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class DynamicIconTilesViewModel extends ExclusiveActivatable implements IconTilesViewModel {
    public final /* synthetic */ IconTilesViewModel $$delegate_0;
    public final Hydrator hydrator;
    public final DynamicIconTilesInteractor interactor;
    public final State largeTilesSpanState;
    public final State largeTilesState;

    public interface Factory {
        DynamicIconTilesViewModel create();
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DynamicIconTilesViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DynamicIconTilesViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(DynamicIconTilesViewModel dynamicIconTilesViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = dynamicIconTilesViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.this$0, continuation);
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
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C03932 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ DynamicIconTilesViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03932(DynamicIconTilesViewModel dynamicIconTilesViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = dynamicIconTilesViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03932(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03932) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    DynamicIconTilesInteractor dynamicIconTilesInteractor = this.this$0.interactor;
                    this.label = 1;
                    if (dynamicIconTilesInteractor.activate(this) == coroutineSingletons) {
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = DynamicIconTilesViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(DynamicIconTilesViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new C03932(DynamicIconTilesViewModel.this, null), 3);
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
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DynamicIconTilesViewModel(DynamicIconTilesInteractor.Factory factory, IconTilesViewModel iconTilesViewModel) {
        this.$$delegate_0 = iconTilesViewModel;
        Hydrator hydrator = new Hydrator("DynamicIconTilesViewModel", null, 2, 0 == true ? 1 : 0);
        this.hydrator = hydrator;
        this.interactor = factory.create();
        this.largeTilesState = hydrator.hydratedStateOf(iconTilesViewModel.getLargeTiles(), "largeTiles");
        this.largeTilesSpanState = hydrator.hydratedStateOf(iconTilesViewModel.getLargeTilesSpan(), "largeTilesSpan");
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final StateFlow getLargeTiles() {
        return this.$$delegate_0.getLargeTiles();
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final StateFlow getLargeTilesSpan() {
        return this.$$delegate_0.getLargeTilesSpan();
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final boolean isIconTile(TileSpec tileSpec) {
        return this.$$delegate_0.isIconTile(tileSpec);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    @Override // com.android.systemui.qs.panels.ui.viewmodel.IconTilesViewModel
    public final void resize(TileSpec tileSpec, boolean z) {
        this.$$delegate_0.resize(tileSpec, z);
    }
}
