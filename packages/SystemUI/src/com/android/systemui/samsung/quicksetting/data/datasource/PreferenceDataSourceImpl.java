package com.android.systemui.samsung.quicksetting.data.datasource;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.samsung.quicksetting.ui.panel.ScreenType;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PreferenceDataSourceImpl implements PreferenceDataSource {
    public final DataStore dataStore;
    public final Map defaultLayouts = MapsKt__MapsKt.mutableMapOf(new Pair(ScreenType.PORTRAIT, "[{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":0,\"spanY\":0,\"spec\":\"Wifi\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":2,\"spanY\":0,\"spec\":\"Bluetooth\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":1,\"spec\":\"\",\"type\":\"BrightBar\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":2,\"spec\":\"\",\"type\":\"VolumeBar\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":3,\"spec\":\"\",\"type\":\"MediaPlayer\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":0,\"spanY\":4,\"spec\":\"SoundMode\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":1,\"spanY\":4,\"spec\":\"RotationLock\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":2,\"spanY\":4,\"spec\":\"AirplaneMode\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":1,\"spanX\":3,\"spanY\":4,\"spec\":\"Flashlight\",\"type\":\"QuickTile\"},{\"spanHeight\":2,\"spanWidth\":2,\"spanX\":2,\"spanY\":5,\"spec\":\"\",\"type\":\"Collapser\"},{\"spanHeight\":2,\"spanWidth\":2,\"spanX\":0,\"spanY\":5,\"spec\":\"Wifi,Bluetooth,SoundMode,RotationLock,Flashlight\",\"type\":\"QuickTileFolder\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":0,\"spanY\":7,\"spec\":\"NearByDevices\",\"type\":\"QuickButton\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":2,\"spanY\":7,\"spec\":\"SmartThings\",\"type\":\"QuickButton\"}]"), new Pair(ScreenType.LANDSCAPE, "[{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":0,\"spanY\":0,\"spec\":\"Wifi\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":2,\"spanY\":0,\"spec\":\"Bluetooth\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":4,\"spanY\":0,\"spec\":\"SoundMode\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":2,\"spanX\":6,\"spanY\":0,\"spec\":\"RotationLock\",\"type\":\"QuickTile\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":1,\"spec\":\"\",\"type\":\"BrightBar\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":4,\"spanY\":1,\"spec\":\"\",\"type\":\"MediaPlayer\"},{\"spanHeight\":1,\"spanWidth\":4,\"spanX\":0,\"spanY\":2,\"spec\":\"Wifi,Bluetooth,SoundMode,RotationLock,Flashlight\",\"type\":\"QuickTileFolder\"}]"));
    public final StandaloneCoroutine initJob;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$1$1, reason: invalid class name and collision with other inner class name */
        final class C02711 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ PreferenceDataSourceImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02711(PreferenceDataSourceImpl preferenceDataSourceImpl, Continuation continuation) {
                super(2, continuation);
                this.this$0 = preferenceDataSourceImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02711 c02711 = new C02711(this.this$0, continuation);
                c02711.L$0 = obj;
                return c02711;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02711) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0052, code lost:
        
            if (androidx.datastore.preferences.core.PreferencesKt.edit(r1, r3, r5) == r0) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0054, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x002d, code lost:
        
            if (r6 == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1c
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r6)
                goto L55
            L10:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L18:
                kotlin.ResultKt.throwOnFailure(r6)
                goto L30
            L1c:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r6 = com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.this
                androidx.datastore.core.DataStore r6 = r6.dataStore
                kotlinx.coroutines.flow.Flow r6 = r6.getData()
                r5.label = r3
                java.lang.Object r6 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r6, r5)
                if (r6 != r0) goto L30
                goto L54
            L30:
                androidx.datastore.preferences.core.Preferences r6 = (androidx.datastore.preferences.core.Preferences) r6
                if (r6 == 0) goto L42
                com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r1 = com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.this
                com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r3 = com.android.systemui.samsung.quicksetting.ui.panel.ScreenType.PORTRAIT
                androidx.datastore.preferences.core.Preferences$Key r1 = com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.access$gridTilesPreferencesKey(r1, r3)
                java.lang.Object r6 = r6.get(r1)
                java.lang.String r6 = (java.lang.String) r6
            L42:
                com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r6 = com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.this
                androidx.datastore.core.DataStore r1 = r6.dataStore
                com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$1$1 r3 = new com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$1$1
                r4 = 0
                r3.<init>(r6, r4)
                r5.label = r2
                java.lang.Object r6 = androidx.datastore.preferences.core.PreferencesKt.edit(r1, r3, r5)
                if (r6 != r0) goto L55
            L54:
                return r0
            L55:
                com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r6 = com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.this
                java.util.Map r6 = r6.defaultLayouts
                com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r0 = com.android.systemui.samsung.quicksetting.ui.panel.ScreenType.PORTRAIT
                java.util.LinkedHashMap r6 = (java.util.LinkedHashMap) r6
                java.lang.Object r6 = r6.get(r0)
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "dataStore init port "
                r0.<init>(r1)
                r0.append(r6)
                java.lang.String r6 = r0.toString()
                java.lang.String r0 = "PreferenceDataSource"
                android.util.Log.i(r0, r6)
                com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r5 = com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.this
                java.util.Map r5 = r5.defaultLayouts
                com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r6 = com.android.systemui.samsung.quicksetting.ui.panel.ScreenType.LANDSCAPE
                java.util.LinkedHashMap r5 = (java.util.LinkedHashMap) r5
                java.lang.Object r5 = r5.get(r6)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                java.lang.String r1 = "dataStore init land "
                r6.<init>(r1)
                r6.append(r5)
                java.lang.String r5 = r6.toString()
                android.util.Log.i(r0, r5)
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
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

    public PreferenceDataSourceImpl(CoroutineScope coroutineScope, DataStore dataStore) {
        this.dataStore = dataStore;
        this.initJob = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
    }

    public static final Preferences.Key access$gridTilesPreferencesKey(PreferenceDataSourceImpl preferenceDataSourceImpl, ScreenType screenType) {
        preferenceDataSourceImpl.getClass();
        return new Preferences.Key("gridTiles_" + screenType);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x006b, code lost:
    
        if (r7 != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006d, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0052, code lost:
    
        if (r7.join(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadGridTiles(final com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$1 r0 = (com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$1 r0 = new com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r5 = (com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6e
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.systemui.samsung.quicksetting.ui.panel.ScreenType r6 = (com.android.systemui.samsung.quicksetting.ui.panel.ScreenType) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl r5 = (com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L55
        L43:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.StandaloneCoroutine r7 = r5.initJob
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r7 = r7.join(r0)
            if (r7 != r1) goto L55
            goto L6d
        L55:
            androidx.datastore.core.DataStore r7 = r5.dataStore
            kotlinx.coroutines.flow.Flow r7 = r7.getData()
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$$inlined$map$1 r2 = new com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$loadGridTiles$$inlined$map$1
            r2.<init>()
            r0.L$0 = r5
            r6 = 0
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = kotlinx.coroutines.flow.FlowKt.first(r2, r0)
            if (r7 != r1) goto L6e
        L6d:
            return r1
        L6e:
            java.lang.String r7 = (java.lang.String) r7
            if (r7 == 0) goto L8c
            r5.getClass()
            com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$toGridTileList$type$1 r5 = new com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl$toGridTileList$type$1
            r5.<init>()
            java.lang.reflect.Type r5 = r5.getType()
            com.google.gson.Gson r6 = new com.google.gson.Gson
            r6.<init>()
            java.lang.Object r5 = r6.fromJson(r7, r5)
            java.util.List r5 = (java.util.List) r5
            if (r5 == 0) goto L8c
            return r5
        L8c:
            kotlin.collections.EmptyList r5 = kotlin.collections.EmptyList.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.samsung.quicksetting.data.datasource.PreferenceDataSourceImpl.loadGridTiles(com.android.systemui.samsung.quicksetting.ui.panel.ScreenType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
