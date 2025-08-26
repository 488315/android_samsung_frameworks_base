package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.provider.Settings;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$5;
import com.android.systemui.media.mediaoutput.common.PreferenceKeys;
import com.android.systemui.media.mediaoutput.entity.Configuration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public final class SettingViewModel extends ViewModel {
    public final Context context;
    public final DataStore dataStore;
    public final DataStoreExt$special$$inlined$map$2 isCastingPriority;
    public final DataStoreExt$special$$inlined$map$4 isShowMusicShareEnabled;
    public final DataStoreExt$special$$inlined$map$3 isSpotifyCastingPriority;
    public final SettingViewModel$special$$inlined$map$1 isSupportSpotifyMediaProvider;
    public final Lazy pref$delegate;
    public final Lazy router2Manager$delegate;

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

                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$1$1$1, reason: invalid class name and collision with other inner class name */
                    final class C03711 extends SuspendLambda implements Function2 {
                        /* synthetic */ Object L$0;
                        int label;
                        final /* synthetic */ SettingViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C03711(SettingViewModel settingViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = settingViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            C03711 c03711 = new C03711(this.this$0, continuation);
                            c03711.L$0 = obj;
                            return c03711;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C03711) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:24:0x007b  */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invokeSuspend(Object obj) {
                            boolean z = true;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (this.label != 0) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                            PreferenceKeys.INSTANCE.getClass();
                            Preferences.Key key = PreferenceKeys.MIRRORING_PRIORITY;
                            if (mutablePreferences.preferencesMap.containsKey(key)) {
                                return Unit.INSTANCE;
                            }
                            int i = Settings.System.getInt(this.this$0.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", 0);
                            Preferences.Key key2 = PreferenceKeys.CASTING_PRIORITY;
                            mutablePreferences.checkNotFrozen$datastore_preferences_core();
                            mutablePreferences.preferencesMap.remove(key2);
                            mutablePreferences.setUnchecked$datastore_preferences_core(key, Boolean.valueOf((i & 1) != 1));
                            mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceKeys.SPOTIFY_CASTING_PRIORITY, Boolean.valueOf((i & 2) != 2));
                            Preferences.Key key3 = PreferenceKeys.SHOW_MUSIC_SHARE;
                            Preferences.Key key4 = PreferenceKeys.SHOW_MUSIC_SHARE_ENABLED;
                            Integer num = (Integer) mutablePreferences.get(key4);
                            if (num != null) {
                                mutablePreferences.checkNotFrozen$datastore_preferences_core();
                                mutablePreferences.preferencesMap.remove(key4);
                                Unit unit = Unit.INSTANCE;
                                if (num.intValue() != 1) {
                                    z = false;
                                }
                            } else if ((i & 4) == 4) {
                            }
                            mutablePreferences.setUnchecked$datastore_preferences_core(key3, Boolean.valueOf(z));
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SettingViewModel settingViewModel2 = settingViewModel;
                        Object objEdit = PreferencesKt.edit(settingViewModel2.dataStore, new C03711(settingViewModel2, null), continuation);
                        return objEdit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEdit : Unit.INSTANCE;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setCastingPriority$1, reason: invalid class name and case insensitive filesystem */
    final class C09631 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $value;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setCastingPriority$1$1, reason: invalid class name and collision with other inner class name */
        final class C03721 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $value;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SettingViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03721(boolean z, SettingViewModel settingViewModel, Continuation continuation) {
                super(2, continuation);
                this.$value = z;
                this.this$0 = settingViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03721 c03721 = new C03721(this.$value, this.this$0, continuation);
                c03721.L$0 = obj;
                return c03721;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03721) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                SettingViewModel.access$updateSystemSettings(this.this$0, 17, !this.$value ? 16 : 17);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09631(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$value = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingViewModel.this.new C09631(this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09631) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SettingViewModel settingViewModel = SettingViewModel.this;
                DataStore dataStore = settingViewModel.dataStore;
                C03721 c03721 = new C03721(this.$value, settingViewModel, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03721, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ((SharedPreferences) SettingViewModel.this.pref$delegate.getValue()).edit().putString(SaEvent.WifiSpeakerPlaybackPreference.INSTANCE.id, this.$value ? "Casting" : "Mirroring").apply();
            List remoteSessions = ((MediaRouter2Manager) SettingViewModel.this.router2Manager$delegate.getValue()).getRemoteSessions();
            remoteSessions.getClass();
            List list = remoteSessions.isEmpty() ? null : remoteSessions;
            if (list != null) {
                SettingViewModel.access$releaseSession(SettingViewModel.this, list, this.$value);
            }
            MoSaLogging.send$default(MoSaLogging.INSTANCE, this.$value ? SaEvent.Casting.INSTANCE : SaEvent.Mirroring.INSTANCE);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setShowMusicShareEnabled$1, reason: invalid class name and case insensitive filesystem */
    final class C09641 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $value;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setShowMusicShareEnabled$1$1, reason: invalid class name and collision with other inner class name */
        final class C03731 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $value;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SettingViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03731(boolean z, SettingViewModel settingViewModel, Continuation continuation) {
                super(2, continuation);
                this.$value = z;
                this.this$0 = settingViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03731 c03731 = new C03731(this.$value, this.this$0, continuation);
                c03731.L$0 = obj;
                return c03731;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03731) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                SettingViewModel.access$updateSystemSettings(this.this$0, 68, this.$value ? 64 : 68);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09641(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$value = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingViewModel.this.new C09641(this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09641) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SettingViewModel settingViewModel = SettingViewModel.this;
                DataStore dataStore = settingViewModel.dataStore;
                C03731 c03731 = new C03731(this.$value, settingViewModel, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03731, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            SharedPreferences.Editor editorEdit = ((SharedPreferences) SettingViewModel.this.pref$delegate.getValue()).edit();
            SaEvent.ShowMusicShare showMusicShare = SaEvent.ShowMusicShare.INSTANCE;
            editorEdit.putBoolean(showMusicShare.id, this.$value).apply();
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.Value(this.$value ? "1" : "0")};
            moSaLogging.getClass();
            MoSaLogging.send(showMusicShare, saCustomArr);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setSpotifyCastingPriority$1, reason: invalid class name and case insensitive filesystem */
    final class C09651 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $value;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$setSpotifyCastingPriority$1$1, reason: invalid class name and collision with other inner class name */
        final class C03741 extends SuspendLambda implements Function2 {
            final /* synthetic */ boolean $value;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ SettingViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03741(boolean z, SettingViewModel settingViewModel, Continuation continuation) {
                super(2, continuation);
                this.$value = z;
                this.this$0 = settingViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C03741 c03741 = new C03741(this.$value, this.this$0, continuation);
                c03741.L$0 = obj;
                return c03741;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03741) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                SettingViewModel.access$updateSystemSettings(this.this$0, 34, this.$value ? 32 : 34);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09651(boolean z, Continuation continuation) {
            super(2, continuation);
            this.$value = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SettingViewModel.this.new C09651(this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09651) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SettingViewModel settingViewModel = SettingViewModel.this;
                DataStore dataStore = settingViewModel.dataStore;
                C03741 c03741 = new C03741(this.$value, settingViewModel, null);
                this.label = 1;
                if (PreferencesKt.edit(dataStore, c03741, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ((SharedPreferences) SettingViewModel.this.pref$delegate.getValue()).edit().putString(SaEvent.SpotifyPlaybackPreference.INSTANCE.id, this.$value ? "Casting" : "Mirroring").apply();
            Lazy lazy = LazyKt__LazyJVMKt.lazy(new SettingViewModel$$ExternalSyntheticLambda0(SettingViewModel.this, 2));
            List remoteSessions = ((MediaRouter2Manager) SettingViewModel.this.router2Manager$delegate.getValue()).getRemoteSessions();
            boolean z = this.$value;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : remoteSessions) {
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj2;
                if (z ? Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring") && ((String) lazy.getValue()).startsWith("com.spotify.music") : routingSessionInfo.getClientPackageName().startsWith("com.spotify.music")) {
                    arrayList.add(obj2);
                }
            }
            ArrayList arrayList2 = arrayList.isEmpty() ? null : arrayList;
            if (arrayList2 != null) {
                SettingViewModel.access$releaseSession(SettingViewModel.this, arrayList2, this.$value);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$special$$inlined$map$1] */
    public SettingViewModel(Context context, DataStore dataStore) {
        this.context = context;
        this.dataStore = dataStore;
        DataStoreExt.INSTANCE.getClass();
        this.isCastingPriority = DataStoreExt.isCastingPriority(dataStore);
        this.isSpotifyCastingPriority = new DataStoreExt$special$$inlined$map$3(dataStore.getData(), dataStore);
        this.isShowMusicShareEnabled = new DataStoreExt$special$$inlined$map$4(dataStore.getData());
        final DataStoreExt$special$$inlined$map$5 dataStoreExt$special$$inlined$map$5 = new DataStoreExt$special$$inlined$map$5(dataStore.getData());
        this.isSupportSpotifyMediaProvider = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Configuration) obj).getSupportSpotifyMediaProvider());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = dataStoreExt$special$$inlined$map$5.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
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
            boolean zAreEqual = false;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) next;
            if (z) {
                zAreEqual = Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring");
            } else if (!Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring")) {
                zAreEqual = true;
            }
            if (zAreEqual) {
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

    public static final void access$updateSystemSettings(SettingViewModel settingViewModel, int i, int i2) {
        int i3 = Settings.System.getInt(settingViewModel.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", 0);
        Settings.System.putInt(settingViewModel.context.getContentResolver(), "wifispeaker_chromecast_mode_enabled", (i3 - (i & i3)) + i2);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SettingViewModel", "onCleared()");
    }

    public final void setCastingPriority(boolean z) {
        Log.d("SettingViewModel", "setCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09631(z, null), 3);
    }

    public final void setShowMusicShareEnabled(boolean z) {
        Log.d("SettingViewModel", "setShowMusicShareEnabled() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09641(z, null), 3);
    }

    public final void setSpotifyCastingPriority(boolean z) {
        Log.d("SettingViewModel", "setSpotifyCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09651(z, null), 3);
    }
}
