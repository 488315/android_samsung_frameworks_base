package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Context;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.PreferencesKt;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$4;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SettingViewModel extends ViewModel {
    public final Context context;
    public final DataStore dataStore;
    public final DataStoreExt$special$$inlined$map$2 isCastingPriority;
    public final DataStoreExt$special$$inlined$map$4 isShowMusicShareEnabled;
    public final DataStoreExt$special$$inlined$map$3 isSpotifyCastingPriority;
    public final Lazy pref$delegate;
    public final Lazy router2Manager$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow data = SettingViewModel.this.dataStore.getData();
                final SettingViewModel settingViewModel = SettingViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.1.1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C02341 extends SuspendLambda implements Function2 {
                        /* synthetic */ Object L$0;
                        int label;
                        final /* synthetic */ SettingViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C02341(SettingViewModel settingViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = settingViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            C02341 c02341 = new C02341(this.this$0, continuation);
                            c02341.L$0 = obj;
                            return c02341;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C02341) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:17:0x0073, code lost:
                        
                            if (r3.intValue() == 1) goto L25;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:18:0x007b, code lost:
                        
                            r1 = false;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:19:0x007c, code lost:
                        
                            r7.setUnchecked$datastore_preferences_core(r0, java.lang.Boolean.valueOf(r1));
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:20:0x0085, code lost:
                        
                            return kotlin.Unit.INSTANCE;
                         */
                        /* JADX WARN: Code restructure failed: missing block: B:22:0x0078, code lost:
                        
                            if ((r6 & 4) != 4) goto L25;
                         */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
                            /*
                                r6 = this;
                                r0 = 2
                                r1 = 1
                                kotlin.coroutines.intrinsics.CoroutineSingletons r2 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r6.label
                                if (r2 != 0) goto L86
                                kotlin.ResultKt.throwOnFailure(r7)
                                java.lang.Object r7 = r6.L$0
                                androidx.datastore.preferences.core.MutablePreferences r7 = (androidx.datastore.preferences.core.MutablePreferences) r7
                                com.android.systemui.media.mediaoutput.common.PreferenceKeys r2 = com.android.systemui.media.mediaoutput.common.PreferenceKeys.INSTANCE
                                r2.getClass()
                                androidx.datastore.preferences.core.Preferences$Key r2 = com.android.systemui.media.mediaoutput.common.PreferenceKeys.MIRRORING_PRIORITY
                                java.util.Map r3 = r7.preferencesMap
                                boolean r3 = r3.containsKey(r2)
                                if (r3 == 0) goto L21
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            L21:
                                com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel r6 = r6.this$0
                                android.content.Context r6 = r6.context
                                android.content.ContentResolver r6 = r6.getContentResolver()
                                java.lang.String r3 = "wifispeaker_chromecast_mode_enabled"
                                r4 = 0
                                int r6 = android.provider.Settings.System.getInt(r6, r3, r4)
                                androidx.datastore.preferences.core.Preferences$Key r3 = com.android.systemui.media.mediaoutput.common.PreferenceKeys.CASTING_PRIORITY
                                r7.checkNotFrozen$datastore_preferences_core()
                                java.util.Map r5 = r7.preferencesMap
                                r5.remove(r3)
                                r3 = r6 & 1
                                if (r3 == r1) goto L41
                                r3 = r1
                                goto L42
                            L41:
                                r3 = r4
                            L42:
                                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
                                r7.setUnchecked$datastore_preferences_core(r2, r3)
                                androidx.datastore.preferences.core.Preferences$Key r2 = com.android.systemui.media.mediaoutput.common.PreferenceKeys.SPOTIFY_CASTING_PRIORITY
                                r3 = r6 & 2
                                if (r3 == r0) goto L51
                                r0 = r1
                                goto L52
                            L51:
                                r0 = r4
                            L52:
                                java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                                r7.setUnchecked$datastore_preferences_core(r2, r0)
                                androidx.datastore.preferences.core.Preferences$Key r0 = com.android.systemui.media.mediaoutput.common.PreferenceKeys.SHOW_MUSIC_SHARE
                                androidx.datastore.preferences.core.Preferences$Key r2 = com.android.systemui.media.mediaoutput.common.PreferenceKeys.SHOW_MUSIC_SHARE_ENABLED
                                java.lang.Object r3 = r7.get(r2)
                                java.lang.Integer r3 = (java.lang.Integer) r3
                                if (r3 == 0) goto L76
                                r7.checkNotFrozen$datastore_preferences_core()
                                java.util.Map r6 = r7.preferencesMap
                                r6.remove(r2)
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                int r6 = r3.intValue()
                                if (r6 != r1) goto L7b
                                goto L7c
                            L76:
                                r2 = 4
                                r6 = r6 & r2
                                if (r6 == r2) goto L7b
                                goto L7c
                            L7b:
                                r1 = r4
                            L7c:
                                java.lang.Boolean r6 = java.lang.Boolean.valueOf(r1)
                                r7.setUnchecked$datastore_preferences_core(r0, r6)
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            L86:
                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                r6.<init>(r7)
                                throw r6
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel.AnonymousClass1.C02331.C02341.invokeSuspend(java.lang.Object):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SettingViewModel settingViewModel2 = SettingViewModel.this;
                        Object edit = PreferencesKt.edit(settingViewModel2.dataStore, new C02341(settingViewModel2, null), continuation);
                        return edit == CoroutineSingletons.COROUTINE_SUSPENDED ? edit : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (data.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SettingViewModel(Context context, DataStore dataStore) {
        this.context = context;
        this.dataStore = dataStore;
        DataStoreExt.INSTANCE.getClass();
        this.isCastingPriority = new DataStoreExt$special$$inlined$map$2(new DataStoreExt$special$$inlined$map$1(dataStore.getData()));
        this.isSpotifyCastingPriority = new DataStoreExt$special$$inlined$map$3(dataStore.getData());
        this.isShowMusicShareEnabled = new DataStoreExt$special$$inlined$map$4(dataStore.getData());
        this.router2Manager$delegate = LazyKt__LazyJVMKt.lazy(new SettingViewModel$$ExternalSyntheticLambda0(this, 0));
        this.pref$delegate = LazyKt__LazyJVMKt.lazy(new SettingViewModel$$ExternalSyntheticLambda0(this, 1));
        Log.d("SettingViewModel", "init()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    public static final void access$releaseSession(SettingViewModel settingViewModel, List list, boolean z) {
        int i;
        settingViewModel.getClass();
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (true) {
            i = 0;
            boolean z2 = false;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) next;
            if (z) {
                z2 = Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring");
            } else if (!Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring")) {
                z2 = true;
            }
            if (z2) {
                arrayList.add(next);
            }
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((MediaRouter2Manager) settingViewModel.router2Manager$delegate.getValue()).releaseSession((RoutingSessionInfo) obj);
        }
    }

    public static final Object access$updateSystemSettings(SettingViewModel settingViewModel, SuspendLambda suspendLambda) {
        settingViewModel.getClass();
        Object edit = PreferencesKt.edit(settingViewModel.dataStore, new SettingViewModel$updateSystemSettings$2(settingViewModel, null), suspendLambda);
        return edit == CoroutineSingletons.COROUTINE_SUSPENDED ? edit : Unit.INSTANCE;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SettingViewModel", "onCleared()");
    }

    public final void setCastingPriority(boolean z) {
        Log.d("SettingViewModel", "setCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SettingViewModel$setCastingPriority$1(this, z, null), 3);
    }

    public final void setShowMusicShareEnabled(boolean z) {
        Log.d("SettingViewModel", "setShowMusicShareEnabled() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SettingViewModel$setShowMusicShareEnabled$1(this, z, null), 3);
    }

    public final void setSpotifyCastingPriority(boolean z) {
        Log.d("SettingViewModel", "setSpotifyCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SettingViewModel$setSpotifyCastingPriority$1(this, z, null), 3);
    }
}
