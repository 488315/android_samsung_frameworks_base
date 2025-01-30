package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryHelp$2", m277f = "PromptViewModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class PromptViewModel$showTemporaryHelp$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $message;
    final /* synthetic */ String $messageAfterHelp;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryHelp$2$1", m277f = "PromptViewModel.kt", m278l = {322}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryHelp$2$1 */
    final class C11161 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $messageAfterHelp;
        int label;
        final /* synthetic */ PromptViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11161(PromptViewModel promptViewModel, String str, Continuation<? super C11161> continuation) {
            super(2, continuation);
            this.this$0 = promptViewModel;
            this.$messageAfterHelp = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C11161(this.this$0, this.$messageAfterHelp, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11161) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(2000L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            PromptViewModel.showAuthenticating$default(this.this$0, this.$messageAfterHelp, false, 2);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$showTemporaryHelp$2(PromptViewModel promptViewModel, String str, String str2, Continuation<? super PromptViewModel$showTemporaryHelp$2> continuation) {
        super(2, continuation);
        this.this$0 = promptViewModel;
        this.$message = str;
        this.$messageAfterHelp = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PromptViewModel$showTemporaryHelp$2 promptViewModel$showTemporaryHelp$2 = new PromptViewModel$showTemporaryHelp$2(this.this$0, this.$message, this.$messageAfterHelp, continuation);
        promptViewModel$showTemporaryHelp$2.L$0 = obj;
        return promptViewModel$showTemporaryHelp$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PromptViewModel$showTemporaryHelp$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        if (((PromptAuthState) this.this$0._isAuthenticated.getValue()).isAuthenticated) {
            return Unit.INSTANCE;
        }
        this.this$0._isAuthenticating.setValue(Boolean.FALSE);
        this.this$0._isAuthenticated.setValue(new PromptAuthState(false, null, false, 0L, 14, null));
        this.this$0._message.setValue(StringsKt__StringsJVMKt.isBlank(this.$message) ^ true ? new PromptMessage.Help(this.$message) : PromptMessage.Empty.INSTANCE);
        this.this$0._forceMediumSize.setValue(Boolean.TRUE);
        this.this$0._legacyState.setValue(new Integer(3));
        Job job = this.this$0.messageJob;
        if (job != null) {
            job.cancel(null);
        }
        PromptViewModel promptViewModel = this.this$0;
        promptViewModel.messageJob = BuildersKt.launch$default(coroutineScope, null, null, new C11161(promptViewModel, this.$messageAfterHelp, null), 3);
        return Unit.INSTANCE;
    }
}
