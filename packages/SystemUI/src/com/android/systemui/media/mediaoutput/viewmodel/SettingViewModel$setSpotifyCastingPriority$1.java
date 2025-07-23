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
final class SettingViewModel$setSpotifyCastingPriority$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $value;
    int label;
    final /* synthetic */ SettingViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setSpotifyCastingPriority$1$1, reason: invalid class name */
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
            mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceKeys.SPOTIFY_CASTING_PRIORITY, Boolean.valueOf(this.$value));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$setSpotifyCastingPriority$1(SettingViewModel settingViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = settingViewModel;
        this.$value = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingViewModel$setSpotifyCastingPriority$1(this.this$0, this.$value, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$setSpotifyCastingPriority$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d2, code lost:
    
        if (com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$updateSystemSettings(r12, r11) == r0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00d4, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0032, code lost:
    
        if (androidx.datastore.preferences.core.PreferencesKt.edit(r12, r1, r11) == r0) goto L38;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 1
            r3 = 0
            r4 = 2
            if (r1 == 0) goto L1e
            if (r1 == r2) goto L1a
            if (r1 != r4) goto L12
            kotlin.ResultKt.throwOnFailure(r12)
            goto Ld5
        L12:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1a:
            kotlin.ResultKt.throwOnFailure(r12)
            goto L36
        L1e:
            kotlin.ResultKt.throwOnFailure(r12)
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r12 = r11.this$0
            androidx.datastore.core.DataStore r12 = r12.dataStore
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setSpotifyCastingPriority$1$1 r1 = new com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setSpotifyCastingPriority$1$1
            boolean r5 = r11.$value
            r1.<init>(r5, r3)
            r11.label = r2
            java.lang.Object r12 = androidx.datastore.preferences.core.PreferencesKt.edit(r12, r1, r11)
            if (r12 != r0) goto L36
            goto Ld4
        L36:
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r12 = r11.this$0
            kotlin.Lazy r12 = r12.pref$delegate
            java.lang.Object r12 = r12.getValue()
            android.content.SharedPreferences r12 = (android.content.SharedPreferences) r12
            android.content.SharedPreferences$Editor r12 = r12.edit()
            com.android.systemui.media.mediaoutput.analytics.SaEvent$SpotifyPlaybackPreference r1 = com.android.systemui.media.mediaoutput.analytics.SaEvent.SpotifyPlaybackPreference.INSTANCE
            java.lang.String r1 = r1.id
            boolean r5 = r11.$value
            if (r5 == 0) goto L4f
            java.lang.String r5 = "Casting"
            goto L51
        L4f:
            java.lang.String r5 = "Mirroring"
        L51:
            android.content.SharedPreferences$Editor r12 = r12.putString(r1, r5)
            r12.apply()
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r12 = r11.this$0
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$$ExternalSyntheticLambda0 r1 = new com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$$ExternalSyntheticLambda0
            r5 = 3
            r1.<init>(r12, r5)
            kotlin.Lazy r12 = kotlin.LazyKt__LazyJVMKt.lazy(r1)
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r1 = r11.this$0
            kotlin.Lazy r1 = r1.router2Manager$delegate
            java.lang.Object r1 = r1.getValue()
            android.media.MediaRouter2Manager r1 = (android.media.MediaRouter2Manager) r1
            java.util.List r1 = r1.getRemoteSessions()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            boolean r5 = r11.$value
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.Iterator r1 = r1.iterator()
        L7f:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto Lba
            java.lang.Object r7 = r1.next()
            r8 = r7
            android.media.RoutingSessionInfo r8 = (android.media.RoutingSessionInfo) r8
            java.lang.String r9 = "com.spotify.music"
            if (r5 == 0) goto Lac
            java.lang.String r8 = r8.getClientPackageName()
            java.lang.String r10 = "com.samsung.android.audiomirroring"
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r8, r10)
            if (r8 == 0) goto Laa
            java.lang.Object r8 = r12.getValue()
            java.lang.String r8 = (java.lang.String) r8
            boolean r8 = r8.startsWith(r9)
            if (r8 == 0) goto Laa
            r8 = r2
            goto Lb4
        Laa:
            r8 = 0
            goto Lb4
        Lac:
            java.lang.String r8 = r8.getClientPackageName()
            boolean r8 = r8.startsWith(r9)
        Lb4:
            if (r8 == 0) goto L7f
            r6.add(r7)
            goto L7f
        Lba:
            boolean r12 = r6.isEmpty()
            if (r12 != 0) goto Lc1
            r3 = r6
        Lc1:
            if (r3 == 0) goto Lca
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r12 = r11.this$0
            boolean r1 = r11.$value
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$releaseSession(r12, r3, r1)
        Lca:
            com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r12 = r11.this$0
            r11.label = r4
            java.lang.Object r11 = com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.access$updateSystemSettings(r12, r11)
            if (r11 != r0) goto Ld5
        Ld4:
            return r0
        Ld5:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setSpotifyCastingPriority$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
