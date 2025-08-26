package com.android.systemui.education.data.repository;

import android.content.Context;
import android.hardware.input.InputManager;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.data.model.EduDeviceConnectionTime;
import com.android.systemui.education.data.model.GestureEduModel;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.time.Instant;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.NotNullVar;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class UserContextualEducationRepository implements ContextualEducationRepository {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final Context applicationContext;
    public CoroutineScope dataStoreScope;
    public final Provider dataStoreScopeProvider;
    public final StateFlowImpl datastore;
    public final InputManager inputManager;
    public final Flow keyboardShortcutTriggered;
    public final ChannelFlowTransformLatest prefData;
    public final NotNullVar userId$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UserContextualEducationRepository.this.updateEduDeviceConnectionTime(null, this);
        }
    }

    /* renamed from: com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $transform;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$transform = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = UserContextualEducationRepository.this.new AnonymousClass2(this.$transform, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
            EduDeviceConnectionTime eduDeviceConnectionTime = (EduDeviceConnectionTime) this.$transform.mo781invoke(UserContextualEducationRepository.access$getEduDeviceConnectionTime(UserContextualEducationRepository.this, mutablePreferences));
            UserContextualEducationRepository userContextualEducationRepository = UserContextualEducationRepository.this;
            Instant instant = eduDeviceConnectionTime.keyboardFirstConnectionTime;
            userContextualEducationRepository.getClass();
            UserContextualEducationRepository.access$setInstant(userContextualEducationRepository, mutablePreferences, instant, new Preferences.Key("KEYBOARD_FIRST_CONNECTION_TIME"));
            UserContextualEducationRepository userContextualEducationRepository2 = UserContextualEducationRepository.this;
            Instant instant2 = eduDeviceConnectionTime.touchpadFirstConnectionTime;
            userContextualEducationRepository2.getClass();
            UserContextualEducationRepository.access$setInstant(userContextualEducationRepository2, mutablePreferences, instant2, new Preferences.Key("TOUCHPAD_FIRST_CONNECTION_TIME"));
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$1, reason: invalid class name and case insensitive filesystem */
    final class C08631 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C08631(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return UserContextualEducationRepository.this.updateGestureEduModel(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$2, reason: invalid class name and case insensitive filesystem */
    final class C08642 extends SuspendLambda implements Function2 {
        final /* synthetic */ GestureType $gestureType;
        final /* synthetic */ Function1 $transform;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08642(GestureType gestureType, Function1 function1, Continuation continuation) {
            super(2, continuation);
            this.$gestureType = gestureType;
            this.$transform = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C08642 c08642 = UserContextualEducationRepository.this.new C08642(this.$gestureType, this.$transform, continuation);
            c08642.L$0 = obj;
            return c08642;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08642) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
            GestureEduModel gestureEduModel = (GestureEduModel) this.$transform.mo781invoke(UserContextualEducationRepository.access$getGestureEduModel(UserContextualEducationRepository.this, this.$gestureType, mutablePreferences));
            UserContextualEducationRepository userContextualEducationRepository = UserContextualEducationRepository.this;
            GestureType gestureType = this.$gestureType;
            userContextualEducationRepository.getClass();
            mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_SIGNAL_COUNT")), new Integer(gestureEduModel.signalCount));
            UserContextualEducationRepository userContextualEducationRepository2 = UserContextualEducationRepository.this;
            GestureType gestureType2 = this.$gestureType;
            userContextualEducationRepository2.getClass();
            mutablePreferences.setUnchecked$datastore_preferences_core(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType2.name(), "_NUMBER_OF_EDU_SHOWN")), new Integer(gestureEduModel.educationShownCount));
            UserContextualEducationRepository userContextualEducationRepository3 = UserContextualEducationRepository.this;
            Instant instant = gestureEduModel.lastShortcutTriggeredTime;
            GestureType gestureType3 = this.$gestureType;
            userContextualEducationRepository3.getClass();
            UserContextualEducationRepository.access$setInstant(userContextualEducationRepository3, mutablePreferences, instant, new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType3.name(), "_LAST_SHORTCUT_TRIGGERED_TIME")));
            UserContextualEducationRepository userContextualEducationRepository4 = UserContextualEducationRepository.this;
            Instant instant2 = gestureEduModel.usageSessionStartTime;
            GestureType gestureType4 = this.$gestureType;
            userContextualEducationRepository4.getClass();
            UserContextualEducationRepository.access$setInstant(userContextualEducationRepository4, mutablePreferences, instant2, new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType4.name(), "_USAGE_SESSION_START_TIME")));
            UserContextualEducationRepository userContextualEducationRepository5 = UserContextualEducationRepository.this;
            Instant instant3 = gestureEduModel.lastEducationTime;
            GestureType gestureType5 = this.$gestureType;
            userContextualEducationRepository5.getClass();
            UserContextualEducationRepository.access$setInstant(userContextualEducationRepository5, mutablePreferences, instant3, new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType5.name(), "_LAST_EDUCATION_TIME")));
            return Unit.INSTANCE;
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(UserContextualEducationRepository.class, "userId", "getUserId()I", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl};
        new Companion(null);
    }

    public UserContextualEducationRepository(Context context, Provider provider, InputManager inputManager, CoroutineDispatcher coroutineDispatcher) {
        this.applicationContext = context;
        this.dataStoreScopeProvider = provider;
        this.inputManager = inputManager;
        Delegates.INSTANCE.getClass();
        this.userId$delegate = new NotNullVar();
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.datastore = stateFlowImplMutableStateFlow;
        this.prefData = FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow), new UserContextualEducationRepository$special$$inlined$flatMapLatest$1(null));
        this.keyboardShortcutTriggered = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new UserContextualEducationRepository$keyboardShortcutTriggered$1(this, null)), coroutineDispatcher);
    }

    public static final EduDeviceConnectionTime access$getEduDeviceConnectionTime(UserContextualEducationRepository userContextualEducationRepository, Preferences preferences) {
        userContextualEducationRepository.getClass();
        Long l = (Long) preferences.get(new Preferences.Key("KEYBOARD_FIRST_CONNECTION_TIME"));
        Instant instantOfEpochSecond = l != null ? Instant.ofEpochSecond(l.longValue()) : null;
        Long l2 = (Long) preferences.get(new Preferences.Key("TOUCHPAD_FIRST_CONNECTION_TIME"));
        return new EduDeviceConnectionTime(instantOfEpochSecond, l2 != null ? Instant.ofEpochSecond(l2.longValue()) : null);
    }

    public static final GestureEduModel access$getGestureEduModel(UserContextualEducationRepository userContextualEducationRepository, GestureType gestureType, Preferences preferences) {
        userContextualEducationRepository.getClass();
        Integer num = (Integer) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_SIGNAL_COUNT")));
        int iIntValue = num != null ? num.intValue() : 0;
        Integer num2 = (Integer) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_NUMBER_OF_EDU_SHOWN")));
        int iIntValue2 = num2 != null ? num2.intValue() : 0;
        Long l = (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_LAST_SHORTCUT_TRIGGERED_TIME")));
        Instant instantOfEpochSecond = l != null ? Instant.ofEpochSecond(l.longValue()) : null;
        Long l2 = (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_USAGE_SESSION_START_TIME")));
        Instant instantOfEpochSecond2 = l2 != null ? Instant.ofEpochSecond(l2.longValue()) : null;
        Long l3 = (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_LAST_EDUCATION_TIME")));
        return new GestureEduModel(gestureType, iIntValue, iIntValue2, instantOfEpochSecond, instantOfEpochSecond2, l3 != null ? Instant.ofEpochSecond(l3.longValue()) : null, ((Number) userContextualEducationRepository.userId$delegate.getValue(userContextualEducationRepository, $$delegatedProperties[0])).intValue());
    }

    public static final void access$setInstant(UserContextualEducationRepository userContextualEducationRepository, MutablePreferences mutablePreferences, Instant instant, Preferences.Key key) {
        userContextualEducationRepository.getClass();
        if (instant != null) {
            mutablePreferences.setUnchecked$datastore_preferences_core(key, Long.valueOf(instant.getEpochSecond()));
        } else {
            mutablePreferences.checkNotFrozen$datastore_preferences_core();
            mutablePreferences.preferencesMap.remove(key);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0068, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit((androidx.datastore.core.DataStore) r7, r2, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateEduDeviceConnectionTime(Function1 function1, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objFirst = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(this.datastore);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = function1;
            anonymousClass1.label = 1;
            objFirst = FlowKt.first(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, anonymousClass1);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objFirst);
            return Unit.INSTANCE;
        }
        function1 = (Function1) anonymousClass1.L$1;
        this = (UserContextualEducationRepository) anonymousClass1.L$0;
        ResultKt.throwOnFailure(objFirst);
        AnonymousClass2 anonymousClass2 = this.new AnonymousClass2(function1, null);
        anonymousClass1.L$0 = null;
        anonymousClass1.L$1 = null;
        anonymousClass1.label = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0071, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit((androidx.datastore.core.DataStore) r8, r2, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateGestureEduModel(GestureType gestureType, Function1 function1, Continuation continuation) {
        C08631 c08631;
        if (continuation instanceof C08631) {
            c08631 = (C08631) continuation;
            int i = c08631.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08631.label = i - Integer.MIN_VALUE;
            } else {
                c08631 = new C08631(continuation);
            }
        }
        Object objFirst = c08631.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08631.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(this.datastore);
            c08631.L$0 = this;
            c08631.L$1 = gestureType;
            c08631.L$2 = function1;
            c08631.label = 1;
            objFirst = FlowKt.first(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, c08631);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objFirst);
            return Unit.INSTANCE;
        }
        function1 = (Function1) c08631.L$2;
        gestureType = (GestureType) c08631.L$1;
        this = (UserContextualEducationRepository) c08631.L$0;
        ResultKt.throwOnFailure(objFirst);
        C08642 c08642 = this.new C08642(gestureType, function1, null);
        c08631.L$0 = null;
        c08631.L$1 = null;
        c08631.L$2 = null;
        c08631.label = 2;
    }
}
