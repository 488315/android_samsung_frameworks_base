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
final class SettingViewModel$setCastingPriority$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $value;
    int label;
    final /* synthetic */ SettingViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setCastingPriority$1$1, reason: invalid class name */
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
            mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceKeys.MIRRORING_PRIORITY, Boolean.valueOf(!this.$value));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$setCastingPriority$1(SettingViewModel settingViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingViewModel;
        this.$value = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingViewModel$setCastingPriority$1(this.this$0, this.$value, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$setCastingPriority$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00cf, code lost:
    
        if (com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$updateSystemSettings(r11, r10) == r0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00d1, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0032, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit(r11, r1, r10) == r0) goto L37;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 1
            r3 = 0
            r4 = 2
            if (r1 == 0) goto L1e
            if (r1 == r2) goto L1a
            if (r1 != r4) goto L12
            kotlin.ResultKt.throwOnFailure(r11)
            goto Ld2
        L12:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L1a:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L36
        L1e:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r11 = r10.this$0
            androidx.datastore.core.DataStore r11 = r11.dataStore
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setCastingPriority$1$1 r1 = new com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setCastingPriority$1$1
            boolean r5 = r10.$value
            r1.<init>(r5, r3)
            r10.label = r2
            java.lang.Object r11 = androidx.datastore.preferences.core.PreferencesKt.edit(r11, r1, r10)
            if (r11 != r0) goto L36
            goto Ld1
        L36:
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r11 = r10.this$0
            kotlin.Lazy r11 = r11.pref$delegate
            java.lang.Object r11 = r11.getValue()
            android.content.SharedPreferences r11 = (android.content.SharedPreferences) r11
            android.content.SharedPreferences$Editor r11 = r11.edit()
            com.android.systemui.media.mediaoutput.analytics.SaEvent$WifiSpeakerPlaybackPreference r1 = com.android.systemui.media.mediaoutput.analytics.SaEvent.WifiSpeakerPlaybackPreference.INSTANCE
            java.lang.String r1 = r1.id
            boolean r2 = r10.$value
            if (r2 == 0) goto L4f
            java.lang.String r2 = "Casting"
            goto L51
        L4f:
            java.lang.String r2 = "Mirroring"
        L51:
            android.content.SharedPreferences$Editor r11 = r11.putString(r1, r2)
            r11.apply()
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r11 = r10.this$0
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$$ExternalSyntheticLambda0 r1 = new com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$$ExternalSyntheticLambda0
            r2 = 2
            r1.<init>(r11, r2)
            kotlin.Lazy r11 = kotlin.LazyKt__LazyJVMKt.lazy(r1)
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r1 = r10.this$0
            kotlin.Lazy r1 = r1.router2Manager$delegate
            java.lang.Object r1 = r1.getValue()
            android.media.MediaRouter2Manager r1 = (android.media.MediaRouter2Manager) r1
            java.util.List r1 = r1.getRemoteSessions()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            boolean r2 = r10.$value
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r1 = r1.iterator()
        L7f:
            boolean r6 = r1.hasNext()
            if (r6 == 0) goto Lb7
            java.lang.Object r6 = r1.next()
            r7 = r6
            android.media.RoutingSessionInfo r7 = (android.media.RoutingSessionInfo) r7
            java.lang.String r8 = "com.spotify.music"
            if (r2 == 0) goto La9
            java.lang.String r7 = r7.getClientPackageName()
            java.lang.String r9 = "com.samsung.android.audiomirroring"
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r9)
            if (r7 == 0) goto L7f
            java.lang.Object r7 = r11.getValue()
            java.lang.String r7 = (java.lang.String) r7
            boolean r7 = r7.startsWith(r8)
            if (r7 != 0) goto L7f
            goto Lb3
        La9:
            java.lang.String r7 = r7.getClientPackageName()
            boolean r7 = r7.startsWith(r8)
            if (r7 != 0) goto L7f
        Lb3:
            r5.add(r6)
            goto L7f
        Lb7:
            boolean r11 = r5.isEmpty()
            if (r11 != 0) goto Lbe
            r3 = r5
        Lbe:
            if (r3 == 0) goto Lc7
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r11 = r10.this$0
            boolean r1 = r10.$value
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$releaseSession(r11, r3, r1)
        Lc7:
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r11 = r10.this$0
            r10.label = r4
            java.lang.Object r11 = com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$updateSystemSettings(r11, r10)
            if (r11 != r0) goto Ld2
        Ld1:
            return r0
        Ld2:
            com.android.systemui.media.mediaoutput.analytics.MoSaLogging r11 = com.android.systemui.media.mediaoutput.analytics.MoSaLogging.INSTANCE
            boolean r10 = r10.$value
            if (r10 == 0) goto Ldb
            com.android.systemui.media.mediaoutput.analytics.SaEvent$Casting r10 = com.android.systemui.media.mediaoutput.analytics.SaEvent.Casting.INSTANCE
            goto Ldd
        Ldb:
            com.android.systemui.media.mediaoutput.analytics.SaEvent$Mirroring r10 = com.android.systemui.media.mediaoutput.analytics.SaEvent.Mirroring.INSTANCE
        Ldd:
            com.android.systemui.media.mediaoutput.analytics.MoSaLogging.send$default(r11, r10)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setCastingPriority$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
