package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.PreferencesKt;
import com.android.systemui.media.mediaoutput.common.PreferenceLabsKeys;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class LabsViewModel$setCloseOnTouchOutside$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $enabled;
    int label;
    final /* synthetic */ LabsViewModel this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel$setCloseOnTouchOutside$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $enabled;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$enabled = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$enabled, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
            PreferenceLabsKeys.INSTANCE.getClass();
            mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceLabsKeys.CLOSE_ON_TOUCH_OUTSIDE, Boolean.valueOf(this.$enabled));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LabsViewModel$setCloseOnTouchOutside$1(LabsViewModel labsViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = labsViewModel;
        this.$enabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LabsViewModel$setCloseOnTouchOutside$1(this.this$0, this.$enabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LabsViewModel$setCloseOnTouchOutside$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DataStore dataStore = this.this$0.dataStore;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$enabled, null);
            this.label = 1;
            if (PreferencesKt.edit(dataStore, anonymousClass1, this) == coroutineSingletons) {
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
