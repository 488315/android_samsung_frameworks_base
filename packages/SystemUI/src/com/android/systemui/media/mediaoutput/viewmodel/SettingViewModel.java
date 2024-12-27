package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Context;
import android.media.MediaRouter2Manager;
import android.provider.Settings;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import androidx.lifecycle.CoroutineLiveData;
import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.PreferenceKeys;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public final class SettingViewModel extends ViewModel {
    public final Context context;
    public final DataStore dataStore;
    public final CoroutineLiveData isCastingPriority;
    public final CoroutineLiveData isShowMusicShareEnabled;
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
                    final class C01551 extends SuspendLambda implements Function2 {
                        /* synthetic */ Object L$0;
                        int label;
                        final /* synthetic */ SettingViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public C01551(SettingViewModel settingViewModel, Continuation continuation) {
                            super(2, continuation);
                            this.this$0 = settingViewModel;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation create(Object obj, Continuation continuation) {
                            C01551 c01551 = new C01551(this.this$0, continuation);
                            c01551.L$0 = obj;
                            return c01551;
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            return ((C01551) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                            Preferences.Key key = PreferenceKeys.CASTING_PRIORITY;
                            mutablePreferences.setUnchecked$datastore_preferences_core(key, new Integer(Settings.System.getInt(this.this$0.context.getContentResolver(), key.name, 0)));
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SettingViewModel settingViewModel2 = SettingViewModel.this;
                        Object edit = PreferencesKt.edit(settingViewModel2.dataStore, new C01551(settingViewModel2, null), continuation);
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SettingViewModel(Context context, DataStore dataStore) {
        this.context = context;
        this.dataStore = dataStore;
        DataStoreExt.INSTANCE.getClass();
        this.isCastingPriority = FlowLiveDataConversions.asLiveData$default(new DataStoreExt$special$$inlined$map$2(new DataStoreExt$special$$inlined$map$1(dataStore.getData())));
        this.isShowMusicShareEnabled = FlowLiveDataConversions.asLiveData$default(new DataStoreExt$special$$inlined$map$4(new DataStoreExt$special$$inlined$map$3(dataStore.getData())));
        this.router2Manager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SettingViewModel$router2Manager$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MediaRouter2Manager.getInstance(SettingViewModel.this.context);
            }
        });
        Log.d("SettingViewModel", "init()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SettingViewModel", "onCleared()");
    }

    public final void setCastingPriority(boolean z) {
        Log.d("SettingViewModel", "setCastingPriority() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SettingViewModel$setCastingPriority$1(z, this, null), 3);
    }

    public final void setShowMusicShareEnabled(boolean z) {
        Log.d("SettingViewModel", "setShowMusicShareEnabled() - " + z);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SettingViewModel$setShowMusicShareEnabled$1(z, this, null), 3);
    }
}
