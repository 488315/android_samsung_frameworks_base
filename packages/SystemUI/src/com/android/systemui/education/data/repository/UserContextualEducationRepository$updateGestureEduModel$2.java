package com.android.systemui.education.data.repository;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.data.model.GestureEduModel;
import java.time.Instant;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class UserContextualEducationRepository$updateGestureEduModel$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ GestureType $gestureType;
    final /* synthetic */ Function1 $transform;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserContextualEducationRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserContextualEducationRepository$updateGestureEduModel$2(UserContextualEducationRepository userContextualEducationRepository, GestureType gestureType, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.this$0 = userContextualEducationRepository;
        this.$gestureType = gestureType;
        this.$transform = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserContextualEducationRepository$updateGestureEduModel$2 userContextualEducationRepository$updateGestureEduModel$2 = new UserContextualEducationRepository$updateGestureEduModel$2(this.this$0, this.$gestureType, this.$transform, continuation);
        userContextualEducationRepository$updateGestureEduModel$2.L$0 = obj;
        return userContextualEducationRepository$updateGestureEduModel$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserContextualEducationRepository$updateGestureEduModel$2) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
        GestureEduModel gestureEduModel = (GestureEduModel) this.$transform.mo779invoke(UserContextualEducationRepository.access$getGestureEduModel(this.this$0, this.$gestureType, mutablePreferences));
        UserContextualEducationRepository userContextualEducationRepository = this.this$0;
        GestureType gestureType = this.$gestureType;
        userContextualEducationRepository.getClass();
        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_SIGNAL_COUNT")), new Integer(gestureEduModel.signalCount));
        UserContextualEducationRepository userContextualEducationRepository2 = this.this$0;
        GestureType gestureType2 = this.$gestureType;
        userContextualEducationRepository2.getClass();
        mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType2.name(), "_NUMBER_OF_EDU_SHOWN")), new Integer(gestureEduModel.educationShownCount));
        UserContextualEducationRepository userContextualEducationRepository3 = this.this$0;
        Instant instant = gestureEduModel.lastShortcutTriggeredTime;
        GestureType gestureType3 = this.$gestureType;
        userContextualEducationRepository3.getClass();
        UserContextualEducationRepository.access$setInstant(userContextualEducationRepository3, mutablePreferences, instant, new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType3.name(), "_LAST_SHORTCUT_TRIGGERED_TIME")));
        UserContextualEducationRepository userContextualEducationRepository4 = this.this$0;
        Instant instant2 = gestureEduModel.usageSessionStartTime;
        GestureType gestureType4 = this.$gestureType;
        userContextualEducationRepository4.getClass();
        UserContextualEducationRepository.access$setInstant(userContextualEducationRepository4, mutablePreferences, instant2, new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType4.name(), "_USAGE_SESSION_START_TIME")));
        UserContextualEducationRepository userContextualEducationRepository5 = this.this$0;
        Instant instant3 = gestureEduModel.lastEducationTime;
        GestureType gestureType5 = this.$gestureType;
        userContextualEducationRepository5.getClass();
        UserContextualEducationRepository.access$setInstant(userContextualEducationRepository5, mutablePreferences, instant3, new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType5.name(), "_LAST_EDUCATION_TIME")));
        return Unit.INSTANCE;
    }
}
