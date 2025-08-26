package com.android.systemui.qs.panels.ui.viewmodel.toolbar;

import android.content.Context;
import android.view.ContextThemeWrapper;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.development.ui.viewmodel.BuildNumberViewModel;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$powerButtonViewModel$$inlined$map$1;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import javax.inject.Provider;
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
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class ToolbarViewModel extends ExclusiveActivatable {
    public final BuildNumberViewModel.Factory buildNumberViewModelFactory;
    public final EditModeButtonViewModel editModeButtonViewModel;
    public final FalsingInteractor falsingInteractor;
    public final FooterActionsInteractor footerActionsInteractor;
    public final MutableState globalActionsDialogLite$delegate;
    public final Provider globalActionsDialogLiteProvider;
    public final Hydrator hydrator;
    public final State powerButtonViewModel$delegate;
    public final FooterActionsButtonViewModel settingsButtonViewModel;
    public final State userSwitcherViewModel$delegate;

    public interface Factory {
        ToolbarViewModel create();
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$1, reason: invalid class name */
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
            return ToolbarViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ToolbarViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(ToolbarViewModel toolbarViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = toolbarViewModel;
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
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        ToolbarViewModel toolbarViewModel = this.this$0;
                        ((SnapshotMutableStateImpl) toolbarViewModel.globalActionsDialogLite$delegate).setValue((GlobalActionsDialogLite) toolbarViewModel.globalActionsDialogLiteProvider.get());
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
                    GlobalActionsDialogLite globalActionsDialogLite = (GlobalActionsDialogLite) ((SnapshotMutableStateImpl) this.this$0.globalActionsDialogLite$delegate).getValue();
                    if (globalActionsDialogLite != null) {
                        globalActionsDialogLite.destroy();
                    }
                    throw th;
                }
            }
        }

        /* renamed from: com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C03972 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ ToolbarViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03972(ToolbarViewModel toolbarViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = toolbarViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03972(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03972) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ToolbarViewModel.this.new AnonymousClass2(continuation);
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
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(ToolbarViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new C03972(ToolbarViewModel.this, null), 3);
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

    public ToolbarViewModel(EditModeButtonViewModel.Factory factory, BuildNumberViewModel.Factory factory2, FooterActionsInteractor footerActionsInteractor, Provider provider, FalsingInteractor falsingInteractor, ShadeModeInteractor shadeModeInteractor, Context context) {
        this.buildNumberViewModelFactory = factory2;
        this.footerActionsInteractor = footerActionsInteractor;
        this.globalActionsDialogLiteProvider = provider;
        this.falsingInteractor = falsingInteractor;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings);
        Hydrator hydrator = new Hydrator("ToolbarViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.powerButtonViewModel$delegate = hydrator.hydratedStateOf("powerButtonViewModel", null, new FooterActionsViewModelKt$powerButtonViewModel$$inlined$map$1(((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode, contextThemeWrapper, new ToolbarViewModel$powerButtonViewModel$2(this)));
        this.settingsButtonViewModel = FooterActionsViewModelKt.settingsButtonViewModel(contextThemeWrapper, new ToolbarViewModel$settingsButtonViewModel$1(this));
        this.userSwitcherViewModel$delegate = hydrator.hydratedStateOf("userSwitcherViewModel", null, FlowKt.distinctUntilChanged(new FooterActionsViewModelKt$userSwitcherViewModel$$inlined$map$1(((FooterActionsInteractorImpl) footerActionsInteractor).userSwitcherStatus, contextThemeWrapper, new ToolbarViewModel$userSwitcherViewModel$2(this))));
        this.editModeButtonViewModel = factory.create();
        this.globalActionsDialogLite$delegate = SnapshotStateKt.mutableStateOf$default(null);
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
}
