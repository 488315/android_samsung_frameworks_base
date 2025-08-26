package com.android.systemui.people;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.people.ui.compose.PeopleScreenKt;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class PeopleSpaceActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PeopleViewModel.Factory viewModelFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.people.PeopleSpaceActivity$onCreate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ PeopleViewModel $viewModel;
        int label;

        /* renamed from: com.android.systemui.people.PeopleSpaceActivity$onCreate$1$1, reason: invalid class name and collision with other inner class name */
        final class C03771 extends SuspendLambda implements Function2 {
            final /* synthetic */ PeopleViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03771(PeopleViewModel peopleViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = peopleViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03771(this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03771) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$viewModel.onTileRefreshRequested.invoke();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PeopleViewModel peopleViewModel, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = peopleViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PeopleSpaceActivity.this.new AnonymousClass1(this.$viewModel, continuation);
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
                PeopleSpaceActivity peopleSpaceActivity = PeopleSpaceActivity.this;
                Lifecycle.State state = Lifecycle.State.RESUMED;
                C03771 c03771 = new C03771(this.$viewModel, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(peopleSpaceActivity, state, c03771, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public PeopleSpaceActivity(PeopleViewModel.Factory factory) {
        this.viewModelFactory = factory;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable$default(this);
        setResult(0);
        final PeopleViewModel peopleViewModel = (PeopleViewModel) new ViewModelProvider(this, this.viewModelFactory).get(PeopleViewModel.class);
        peopleViewModel.onWidgetIdChanged.mo781invoke(Integer.valueOf(getIntent().getIntExtra("appWidgetId", 0)));
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(this.lifecycleRegistry), null, null, new AnonymousClass1(peopleViewModel, null), 7);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(1140881722, true, new Function2() { // from class: com.android.systemui.people.PeopleSpaceActivity.onCreate.2
            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.people.PeopleSpaceActivity.onCreate.<anonymous> (PeopleSpaceActivity.kt:64)");
                        }
                        final PeopleViewModel peopleViewModel2 = peopleViewModel;
                        final PeopleSpaceActivity peopleSpaceActivity = this;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-566938192, new Function2() { // from class: com.android.systemui.people.PeopleSpaceActivity.onCreate.2.1
                            /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.people.PeopleSpaceActivity.onCreate.<anonymous>.<anonymous> (PeopleSpaceActivity.kt:64)");
                                        }
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        composerImpl3.startReplaceGroup(-620053449);
                                        final PeopleSpaceActivity peopleSpaceActivity2 = peopleSpaceActivity;
                                        boolean zChangedInstance = composerImpl3.changedInstance(peopleSpaceActivity2);
                                        Object objRememberedValue = composerImpl3.rememberedValue();
                                        if (!zChangedInstance) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new Function1() { // from class: com.android.systemui.people.PeopleSpaceActivity$onCreate$2$1$$ExternalSyntheticLambda0
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj5) {
                                                        PeopleViewModel.Result result = (PeopleViewModel.Result) obj5;
                                                        int i = PeopleSpaceActivity.$r8$clinit;
                                                        PeopleSpaceActivity peopleSpaceActivity3 = peopleSpaceActivity2;
                                                        peopleSpaceActivity3.getClass();
                                                        if (result instanceof PeopleViewModel.Result.Success) {
                                                            peopleSpaceActivity3.setResult(-1, ((PeopleViewModel.Result.Success) result).data);
                                                        } else {
                                                            peopleSpaceActivity3.setResult(0);
                                                        }
                                                        peopleSpaceActivity3.finish();
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl3.updateRememberedValue(objRememberedValue);
                                            }
                                            composerImpl3.end(false);
                                            PeopleScreenKt.PeopleScreen(peopleViewModel2, (Function1) objRememberedValue, null, composerImpl3, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer), composer, 48, 1);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }));
    }
}
