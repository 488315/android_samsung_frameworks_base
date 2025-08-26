package com.android.systemui.samsung.quicksetting.data.datasource;

import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.samsung.quicksetting.domain.model.GridTileData;
import com.android.systemui.samsung.quicksetting.ui.panel.ScreenType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class PreferenceDataSourceImpl implements PreferenceDataSource {
    public final DataStore dataStore;
    public final Map defaultLayouts = MapsKt__MapsKt.mutableMapOf(new Pair(ScreenType.PORTRAIT, "[{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":0,\"spanY\":0,\"spec\":\"Wifi\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":2,\"spanY\":0,\"spec\":\"Bluetooth\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":1,\"spec\":\"\",\"type\":\"BrightBar\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":2,\"spec\":\"\",\"type\":\"VolumeBar\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":3,\"spec\":\"\",\"type\":\"MediaPlayer\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":0,\"spanY\":4,\"spec\":\"SoundMode\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":1,\"spanY\":4,\"spec\":\"RotationLock\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":2,\"spanY\":4,\"spec\":\"AirplaneMode\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":3,\"spanY\":4,\"spec\":\"Flashlight\",\"type\":\"QuickTile\"},{\"spanHeight\":2,\"spanWidth\":2,\"spanX\":2,\"spanY\":5,\"spec\":\"\",\"type\":\"Collapser\"},{\"spanHeight\":2,\"spanWidth\":2,\"spanX\":0,\"spanY\":5,\"spec\":\"Wifi,Bluetooth,SoundMode,RotationLock,Flashlight\",\"type\":\"QuickTileFolder\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":0,\"spanY\":7,\"spec\":\"NearByDevices\",\"type\":\"QuickButton\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":2,\"spanY\":7,\"spec\":\"SmartThings\",\"type\":\"QuickButton\"}]"), new Pair(ScreenType.LANDSCAPE, "[{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":0,\"spanY\":0,\"spec\":\"Wifi\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":2,\"spanY\":0,\"spec\":\"Bluetooth\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":4,\"spanY\":0,\"spec\":\"SoundMode\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":6,\"spanY\":0,\"spec\":\"RotationLock\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":1,\"spec\":\"\",\"type\":\"BrightBar\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":4,\"spanY\":1,\"spec\":\"\",\"type\":\"MediaPlayer\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":2,\"spec\":\"Wifi,Bluetooth,SoundMode,RotationLock,Flashlight\",\"type\":\"QuickTileFolder\"}]"));
    public final StandaloneCoroutine initJob;

    /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$1$1, reason: invalid class name and collision with other inner class name */
        final class C04301 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ PreferenceDataSourceImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04301(PreferenceDataSourceImpl preferenceDataSourceImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = preferenceDataSourceImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C04301 c04301 = new C04301(this.this$0, continuation);
                c04301.L$0 = obj;
                return c04301;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04301) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MutablePreferences mutablePreferences = (MutablePreferences) this.L$0;
                PreferenceDataSourceImpl preferenceDataSourceImpl = this.this$0;
                ScreenType screenType = ScreenType.PORTRAIT;
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDataSourceImpl.access$gridTilesPreferencesKey(preferenceDataSourceImpl, screenType), String.valueOf(((LinkedHashMap) this.this$0.defaultLayouts).get(screenType)));
                PreferenceDataSourceImpl preferenceDataSourceImpl2 = this.this$0;
                ScreenType screenType2 = ScreenType.LANDSCAPE;
                mutablePreferences.setUnchecked$datastore_preferences_core(PreferenceDataSourceImpl.access$gridTilesPreferencesKey(preferenceDataSourceImpl2, screenType2), String.valueOf(((LinkedHashMap) this.this$0.defaultLayouts).get(screenType2)));
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PreferenceDataSourceImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0052, code lost:
        
            if (androidx.datastore.preferences.core.PreferencesKt.edit(r1, r3, r5) == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow data = PreferenceDataSourceImpl.this.dataStore.getData();
                this.label = 1;
                obj = FlowKt.firstOrNull(data, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Log.i("PreferenceDataSource", "dataStore init port " + ((LinkedHashMap) PreferenceDataSourceImpl.this.defaultLayouts).get(ScreenType.PORTRAIT));
                Log.i("PreferenceDataSource", "dataStore init land " + ((LinkedHashMap) PreferenceDataSourceImpl.this.defaultLayouts).get(ScreenType.LANDSCAPE));
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            Preferences preferences = (Preferences) obj;
            if (preferences != null) {
            }
            PreferenceDataSourceImpl preferenceDataSourceImpl = PreferenceDataSourceImpl.this;
            DataStore dataStore = preferenceDataSourceImpl.dataStore;
            C04301 c04301 = new C04301(preferenceDataSourceImpl, null);
            this.label = 2;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$1, reason: invalid class name and case insensitive filesystem */
    final class C10211 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C10211(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PreferenceDataSourceImpl.this.loadGridTiles(null, this);
        }
    }

    static {
        new Companion(null);
    }

    public PreferenceDataSourceImpl(CoroutineScope coroutineScope, DataStore dataStore) {
        this.dataStore = dataStore;
        this.initJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    public static final Preferences.Key access$gridTilesPreferencesKey(PreferenceDataSourceImpl preferenceDataSourceImpl, ScreenType screenType) {
        preferenceDataSourceImpl.getClass();
        return new Preferences.Key("gridTiles_" + screenType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:
    
        if (r7 == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadGridTiles(final ScreenType screenType, ContinuationImpl continuationImpl) {
        C10211 c10211;
        if (continuationImpl instanceof C10211) {
            c10211 = (C10211) continuationImpl;
            int i = c10211.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c10211.label = i - Integer.MIN_VALUE;
            } else {
                c10211 = new C10211(continuationImpl);
            }
        }
        Object objFirst = c10211.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c10211.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            StandaloneCoroutine standaloneCoroutine = this.initJob;
            c10211.L$0 = this;
            c10211.L$1 = screenType;
            c10211.label = 1;
            if (standaloneCoroutine.join(c10211) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (PreferenceDataSourceImpl) c10211.L$0;
            ResultKt.throwOnFailure(objFirst);
            String str = (String) objFirst;
            if (str != null) {
                this.getClass();
                List list = (List) new Gson().fromJson(str, new TypeToken<List<? extends GridTileData>>() { // from class: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$toGridTileList$type$1
                }.getType());
                if (list != null) {
                    return list;
                }
            }
            return EmptyList.INSTANCE;
        }
        screenType = (ScreenType) c10211.L$1;
        this = (PreferenceDataSourceImpl) c10211.L$0;
        ResultKt.throwOnFailure(objFirst);
        final Flow data = this.dataStore.getData();
        Flow flow = new Flow() { // from class: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$$inlined$map$1

            /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ ScreenType $screen$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ PreferenceDataSourceImpl this$0;

                /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ScreenType screenType, PreferenceDataSourceImpl preferenceDataSourceImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$screen$inlined = screenType;
                    this.this$0 = preferenceDataSourceImpl;
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
                        Preferences preferences = (Preferences) obj;
                        PreferenceDataSourceImpl preferenceDataSourceImpl = this.this$0;
                        ScreenType screenType = this.$screen$inlined;
                        Log.i("PreferenceDataSource", "dataStore - loadGridTiles = " + screenType + " preferences " + preferences.get(PreferenceDataSourceImpl.access$gridTilesPreferencesKey(preferenceDataSourceImpl, screenType)));
                        Object obj3 = preferences.get(PreferenceDataSourceImpl.access$gridTilesPreferencesKey(preferenceDataSourceImpl, screenType));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = data.collect(new AnonymousClass2(flowCollector, screenType, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        c10211.L$0 = this;
        c10211.L$1 = null;
        c10211.label = 2;
        objFirst = FlowKt.first(flow, c10211);
    }
}
