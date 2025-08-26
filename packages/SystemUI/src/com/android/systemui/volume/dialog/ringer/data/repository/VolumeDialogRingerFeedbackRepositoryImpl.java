package com.android.systemui.volume.dialog.ringer.data.repository;

import android.content.Context;
import com.android.systemui.Prefs;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class VolumeDialogRingerFeedbackRepositoryImpl implements VolumeDialogRingerFeedbackRepository {
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;

    /* renamed from: com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl$getToastCount$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogRingerFeedbackRepositoryImpl.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return new Integer(Prefs.getInt(VolumeDialogRingerFeedbackRepositoryImpl.this.applicationContext, "RingerGuidanceCount", 0));
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepositoryImpl$updateToastCount$2, reason: invalid class name and case insensitive filesystem */
    final class C11862 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $toastCount;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C11862(int i, Continuation continuation) {
            super(2, continuation);
            this.$toastCount = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogRingerFeedbackRepositoryImpl.this.new C11862(this.$toastCount, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C11862) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Prefs.putInt(VolumeDialogRingerFeedbackRepositoryImpl.this.applicationContext, "RingerGuidanceCount", this.$toastCount + 1);
            return Unit.INSTANCE;
        }
    }

    public VolumeDialogRingerFeedbackRepositoryImpl(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object getToastCount(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(null), continuation);
    }

    public final Object updateToastCount(int i, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C11862(i, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
