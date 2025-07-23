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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.datastore = MutableStateFlow;
        this.prefData = FlowKt.transformLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MutableStateFlow), new UserContextualEducationRepository$special$$inlined$flatMapLatest$1(null));
        this.keyboardShortcutTriggered = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new UserContextualEducationRepository$keyboardShortcutTriggered$1(this, null)), coroutineDispatcher);
    }

    public static final EduDeviceConnectionTime access$getEduDeviceConnectionTime(UserContextualEducationRepository userContextualEducationRepository, Preferences preferences) {
        userContextualEducationRepository.getClass();
        Long l = (Long) preferences.get(new Preferences.Key("KEYBOARD_FIRST_CONNECTION_TIME"));
        Instant ofEpochSecond = l != null ? Instant.ofEpochSecond(l.longValue()) : null;
        Long l2 = (Long) preferences.get(new Preferences.Key("TOUCHPAD_FIRST_CONNECTION_TIME"));
        return new EduDeviceConnectionTime(ofEpochSecond, l2 != null ? Instant.ofEpochSecond(l2.longValue()) : null);
    }

    public static final GestureEduModel access$getGestureEduModel(UserContextualEducationRepository userContextualEducationRepository, GestureType gestureType, Preferences preferences) {
        userContextualEducationRepository.getClass();
        Integer num = (Integer) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_SIGNAL_COUNT")));
        int intValue = num != null ? num.intValue() : 0;
        Integer num2 = (Integer) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_NUMBER_OF_EDU_SHOWN")));
        int intValue2 = num2 != null ? num2.intValue() : 0;
        Long l = (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_LAST_SHORTCUT_TRIGGERED_TIME")));
        Instant ofEpochSecond = l != null ? Instant.ofEpochSecond(l.longValue()) : null;
        Long l2 = (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_USAGE_SESSION_START_TIME")));
        Instant ofEpochSecond2 = l2 != null ? Instant.ofEpochSecond(l2.longValue()) : null;
        Long l3 = (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(gestureType.name(), "_LAST_EDUCATION_TIME")));
        return new GestureEduModel(gestureType, intValue, intValue2, ofEpochSecond, ofEpochSecond2, l3 != null ? Instant.ofEpochSecond(l3.longValue()) : null, ((Number) userContextualEducationRepository.userId$delegate.getValue(userContextualEducationRepository, $$delegatedProperties[0])).intValue());
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

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0068, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit((androidx.datastore.core.DataStore) r7, r2, r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x006a, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        if (r7 == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateEduDeviceConnectionTime(kotlin.jvm.functions.Function1 r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$1 r0 = (com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$1 r0 = new com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3f
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6b
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.education.data.repository.UserContextualEducationRepository r5 = (com.android.systemui.education.data.repository.UserContextualEducationRepository) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L56
        L3f:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 r7 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
            kotlinx.coroutines.flow.StateFlowImpl r2 = r5.datastore
            r7.<init>(r2)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r7, r0)
            if (r7 != r1) goto L56
            goto L6a
        L56:
            androidx.datastore.core.DataStore r7 = (androidx.datastore.core.DataStore) r7
            com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$2 r2 = new com.android.systemui.education.data.repository.UserContextualEducationRepository$updateEduDeviceConnectionTime$2
            r4 = 0
            r2.<init>(r5, r6, r4)
            r0.L$0 = r4
            r0.L$1 = r4
            r0.label = r3
            java.lang.Object r5 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r2, r0)
            if (r5 != r1) goto L6b
        L6a:
            return r1
        L6b:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.data.repository.UserContextualEducationRepository.updateEduDeviceConnectionTime(kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0071, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit((androidx.datastore.core.DataStore) r8, r2, r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0073, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
    
        if (r8 == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateGestureEduModel(com.android.systemui.contextualeducation.GestureType r6, kotlin.jvm.functions.Function1 r7, kotlin.coroutines.Continuation r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$1 r0 = (com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$1 r0 = new com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L44
            if (r2 == r4) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r8)
            goto L74
        L2a:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L32:
            java.lang.Object r5 = r0.L$2
            r7 = r5
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.systemui.contextualeducation.GestureType r6 = (com.android.systemui.contextualeducation.GestureType) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.education.data.repository.UserContextualEducationRepository r5 = (com.android.systemui.education.data.repository.UserContextualEducationRepository) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5d
        L44:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 r8 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1
            kotlinx.coroutines.flow.StateFlowImpl r2 = r5.datastore
            r8.<init>(r2)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r7
            r0.label = r4
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.first(r8, r0)
            if (r8 != r1) goto L5d
            goto L73
        L5d:
            androidx.datastore.core.DataStore r8 = (androidx.datastore.core.DataStore) r8
            com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$2 r2 = new com.android.systemui.education.data.repository.UserContextualEducationRepository$updateGestureEduModel$2
            r4 = 0
            r2.<init>(r5, r6, r7, r4)
            r0.L$0 = r4
            r0.L$1 = r4
            r0.L$2 = r4
            r0.label = r3
            java.lang.Object r5 = androidx.datastore.preferences.core.PreferencesKt.edit(r8, r2, r0)
            if (r5 != r1) goto L74
        L73:
            return r1
        L74:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.education.data.repository.UserContextualEducationRepository.updateGestureEduModel(com.android.systemui.contextualeducation.GestureType, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
