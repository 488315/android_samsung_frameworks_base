package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.datastore.preferences.core.MutablePreferences;
import com.android.systemui.media.mediaoutput.common.PreferenceKeys;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SettingViewModel$setShowMusicShareEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $value;
    int label;
    final /* synthetic */ SettingViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setShowMusicShareEnabled$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$value = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$value, continuation);
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
            PreferenceKeys.INSTANCE.getClass();
            mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceKeys.SHOW_MUSIC_SHARE, Boolean.valueOf(this.$value));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$setShowMusicShareEnabled$1(SettingViewModel settingViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingViewModel;
        this.$value = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingViewModel$setShowMusicShareEnabled$1(this.this$0, this.$value, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$setShowMusicShareEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0057, code lost:
    
        if (com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$updateSystemSettings(r7, r6) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0031, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit(r7, r1, r6) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5a
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L34
        L1c:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r7 = r6.this$0
            androidx.datastore.core.DataStore r7 = r7.dataStore
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setShowMusicShareEnabled$1$1 r1 = new com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setShowMusicShareEnabled$1$1
            boolean r4 = r6.$value
            r5 = 0
            r1.<init>(r4, r5)
            r6.label = r3
            java.lang.Object r7 = androidx.datastore.preferences.core.PreferencesKt.edit(r7, r1, r6)
            if (r7 != r0) goto L34
            goto L59
        L34:
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r7 = r6.this$0
            kotlin.Lazy r7 = r7.pref$delegate
            java.lang.Object r7 = r7.getValue()
            android.content.SharedPreferences r7 = (android.content.SharedPreferences) r7
            android.content.SharedPreferences$Editor r7 = r7.edit()
            com.android.systemui.media.mediaoutput.analytics.SaEvent$ShowMusicShare r1 = com.android.systemui.media.mediaoutput.analytics.SaEvent.ShowMusicShare.INSTANCE
            java.lang.String r1 = r1.id
            boolean r3 = r6.$value
            android.content.SharedPreferences$Editor r7 = r7.putBoolean(r1, r3)
            r7.apply()
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r7 = r6.this$0
            r6.label = r2
            java.lang.Object r7 = com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$updateSystemSettings(r7, r6)
            if (r7 != r0) goto L5a
        L59:
            return r0
        L5a:
            com.android.systemui.media.mediaoutput.analytics.MoSaLogging r7 = com.android.systemui.media.mediaoutput.analytics.MoSaLogging.INSTANCE
            com.android.systemui.media.mediaoutput.analytics.SaEvent$ShowMusicShare r0 = com.android.systemui.media.mediaoutput.analytics.SaEvent.ShowMusicShare.INSTANCE
            com.android.systemui.media.mediaoutput.analytics.SaCustom$Value r1 = new com.android.systemui.media.mediaoutput.analytics.SaCustom$Value
            boolean r6 = r6.$value
            if (r6 == 0) goto L67
            java.lang.String r6 = "1"
            goto L69
        L67:
            java.lang.String r6 = "0"
        L69:
            r1.<init>(r6)
            com.android.systemui.media.mediaoutput.analytics.SaCustom[] r6 = new com.android.systemui.media.mediaoutput.analytics.SaCustom[]{r1}
            r7.getClass()
            com.android.systemui.media.mediaoutput.analytics.MoSaLogging.send(r0, r6)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setShowMusicShareEnabled$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
