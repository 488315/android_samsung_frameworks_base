package com.android.systemui.education.domain.interactor;

import android.content.Context;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.PreferenceDataStoreFile;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import com.android.systemui.education.data.repository.UserContextualEducationRepository;
import com.android.systemui.education.data.repository.UserContextualEducationRepository$$ExternalSyntheticLambda0;
import java.util.Arrays;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ContextualEducationInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ContextualEducationInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.education.domain.interactor.ContextualEducationInteractor$start$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ int I$0;
        int label;
        final /* synthetic */ ContextualEducationInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ContextualEducationInteractor contextualEducationInteractor, Continuation continuation) {
            super(2, continuation);
            this.this$0 = contextualEducationInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.I$0 = ((Number) obj).intValue();
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final int i = this.I$0;
            final UserContextualEducationRepository userContextualEducationRepository = (UserContextualEducationRepository) this.this$0.repository;
            CoroutineScope coroutineScope = userContextualEducationRepository.dataStoreScope;
            if (coroutineScope != null) {
                CoroutineScopeKt.cancel(coroutineScope, null);
            }
            CoroutineScope coroutineScope2 = (CoroutineScope) userContextualEducationRepository.dataStoreScopeProvider.get();
            KProperty kProperty = UserContextualEducationRepository.$$delegatedProperties[0];
            userContextualEducationRepository.userId$delegate.value = Integer.valueOf(i);
            PreferenceDataStoreFactory preferenceDataStoreFactory = PreferenceDataStoreFactory.INSTANCE;
            ReplaceFileCorruptionHandler replaceFileCorruptionHandler = new ReplaceFileCorruptionHandler(new UserContextualEducationRepository$$ExternalSyntheticLambda0());
            coroutineScope2.getClass();
            userContextualEducationRepository.datastore.updateState(null, PreferenceDataStoreFactory.create$default(preferenceDataStoreFactory, replaceFileCorruptionHandler, coroutineScope2, new Function0() { // from class: com.android.systemui.education.data.repository.UserContextualEducationRepository$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Context context = UserContextualEducationRepository.this.applicationContext;
                    int i2 = StringCompanionObject.$r8$clinit;
                    return PreferenceDataStoreFile.preferencesDataStoreFile(context, String.format("education/USER%s_ContextualEducation", Arrays.copyOf(new Object[]{Integer.valueOf(i)}, 1)));
                }
            }));
            userContextualEducationRepository.dataStoreScope = coroutineScope2;
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextualEducationInteractor$start$1(ContextualEducationInteractor contextualEducationInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = contextualEducationInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ContextualEducationInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ContextualEducationInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ContextualEducationInteractor contextualEducationInteractor = this.this$0;
            Flow flow = contextualEducationInteractor.selectedUserInteractor.selectedUser;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(contextualEducationInteractor, null);
            this.label = 1;
            if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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
