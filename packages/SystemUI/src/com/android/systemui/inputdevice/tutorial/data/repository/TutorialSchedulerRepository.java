package com.android.systemui.inputdevice.tutorial.data.repository;

import android.content.Context;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.PreferenceDataStoreDelegateKt$$ExternalSyntheticLambda0;
import androidx.datastore.preferences.PreferenceDataStoreSingletonDelegate;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import com.android.systemui.inputdevice.tutorial.data.model.DeviceSchedulerInfo;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class TutorialSchedulerRepository {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final Context applicationContext;
    public final PreferenceDataStoreSingletonDelegate dataStore$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$clear$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
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
            mutablePreferences.checkNotFrozen$datastore_preferences_core();
            mutablePreferences.preferencesMap.clear();
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$getFirstConnectionTime$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return TutorialSchedulerRepository.this.getFirstConnectionTime(null, this);
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$getNotifiedTime$1, reason: invalid class name and case insensitive filesystem */
    final class C08701 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08701(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TutorialSchedulerRepository.this.getNotifiedTime(null, this);
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$getScheduledTutorialLaunchTime$1, reason: invalid class name and case insensitive filesystem */
    final class C08711 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08711(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TutorialSchedulerRepository.this.getScheduledTutorialLaunchTime(null, this);
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$isNotified$1, reason: invalid class name and case insensitive filesystem */
    final class C08721 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08721(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TutorialSchedulerRepository.this.isNotified(null, this);
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$updateData$2, reason: invalid class name and case insensitive filesystem */
    final class C08732 extends SuspendLambda implements Function2 {
        final /* synthetic */ Preferences.Key $key;
        final /* synthetic */ Object $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08732(Preferences.Key key, Object obj, Continuation continuation) {
            super(2, continuation);
            this.$key = key;
            this.$value = obj;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C08732 c08732 = new C08732(this.$key, this.$value, continuation);
            c08732.L$0 = obj;
            return c08732;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08732) create((MutablePreferences) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).setUnchecked$datastore_preferences_core(this.$key, this.$value);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$wasEverConnected$1, reason: invalid class name and case insensitive filesystem */
    final class C08741 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C08741(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TutorialSchedulerRepository.this.wasEverConnected(null, this);
        }
    }

    static {
        PropertyReference2Impl propertyReference2Impl = new PropertyReference2Impl(TutorialSchedulerRepository.class, "dataStore", "getDataStore(Landroid/content/Context;)Landroidx/datastore/core/DataStore;", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference2Impl};
        new Companion(null);
    }

    public TutorialSchedulerRepository(Context context, CoroutineScope coroutineScope, String str) {
        this.applicationContext = context;
        this.dataStore$delegate = new PreferenceDataStoreSingletonDelegate(str, new ReplaceFileCorruptionHandler(new TutorialSchedulerRepository$$ExternalSyntheticLambda0()), new PreferenceDataStoreDelegateKt$$ExternalSyntheticLambda0(), coroutineScope);
    }

    public static DeviceSchedulerInfo getDeviceSchedulerInfo(Preferences preferences, DeviceType deviceType) {
        return new DeviceSchedulerInfo((Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType.name(), "_LAUNCHED_TIME"))), (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType.name(), "_CONNECTED_TIME"))), (Long) preferences.get(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType.name(), "_NOTIFIED_TIME"))));
    }

    public final Object clear(Continuation continuation) {
        Object objEdit = PreferencesKt.edit((DataStore) this.dataStore$delegate.getValue(this.applicationContext, $$delegatedProperties[0]), new AnonymousClass2(null), continuation);
        return objEdit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEdit : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getFirstConnectionTime(DeviceType deviceType, ContinuationImpl continuationImpl) {
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
        Object objLoadData = anonymousClass1.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadData);
            anonymousClass1.L$0 = deviceType;
            anonymousClass1.label = 1;
            objLoadData = loadData(anonymousClass1);
            if (objLoadData == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            deviceType = (DeviceType) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objLoadData);
        }
        Object obj2 = ((Map) objLoadData).get(deviceType);
        obj2.getClass();
        return ((DeviceSchedulerInfo) obj2).firstConnectionTime;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getNotifiedTime(DeviceType deviceType, ContinuationImpl continuationImpl) {
        C08701 c08701;
        if (continuationImpl instanceof C08701) {
            c08701 = (C08701) continuationImpl;
            int i = c08701.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08701.label = i - Integer.MIN_VALUE;
            } else {
                c08701 = new C08701(continuationImpl);
            }
        }
        Object objLoadData = c08701.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08701.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadData);
            c08701.L$0 = deviceType;
            c08701.label = 1;
            objLoadData = loadData(c08701);
            if (objLoadData == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            deviceType = (DeviceType) c08701.L$0;
            ResultKt.throwOnFailure(objLoadData);
        }
        Object obj2 = ((Map) objLoadData).get(deviceType);
        obj2.getClass();
        return ((DeviceSchedulerInfo) obj2).notifiedTime;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getScheduledTutorialLaunchTime(DeviceType deviceType, ContinuationImpl continuationImpl) {
        C08711 c08711;
        if (continuationImpl instanceof C08711) {
            c08711 = (C08711) continuationImpl;
            int i = c08711.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08711.label = i - Integer.MIN_VALUE;
            } else {
                c08711 = new C08711(continuationImpl);
            }
        }
        Object objLoadData = c08711.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08711.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadData);
            c08711.L$0 = deviceType;
            c08711.label = 1;
            objLoadData = loadData(c08711);
            if (objLoadData == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            deviceType = (DeviceType) c08711.L$0;
            ResultKt.throwOnFailure(objLoadData);
        }
        Object obj2 = ((Map) objLoadData).get(deviceType);
        obj2.getClass();
        return ((DeviceSchedulerInfo) obj2).launchedTime;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isNotified(DeviceType deviceType, ContinuationImpl continuationImpl) {
        C08721 c08721;
        if (continuationImpl instanceof C08721) {
            c08721 = (C08721) continuationImpl;
            int i = c08721.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08721.label = i - Integer.MIN_VALUE;
            } else {
                c08721 = new C08721(continuationImpl);
            }
        }
        Object objLoadData = c08721.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08721.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadData);
            c08721.L$0 = deviceType;
            c08721.label = 1;
            objLoadData = loadData(c08721);
            if (objLoadData == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            deviceType = (DeviceType) c08721.L$0;
            ResultKt.throwOnFailure(objLoadData);
        }
        Object obj2 = ((Map) objLoadData).get(deviceType);
        obj2.getClass();
        return Boolean.valueOf(((DeviceSchedulerInfo) obj2).notifiedTime != null);
    }

    public final Object loadData(ContinuationImpl continuationImpl) {
        final Flow data = ((DataStore) this.dataStore$delegate.getValue(this.applicationContext, $$delegatedProperties[0])).getData();
        return FlowKt.first(new Flow() { // from class: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$loadData$$inlined$map$1

            /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$loadData$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TutorialSchedulerRepository this$0;

                /* renamed from: com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository$loadData$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, TutorialSchedulerRepository tutorialSchedulerRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = tutorialSchedulerRepository;
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
                        KProperty[] kPropertyArr = TutorialSchedulerRepository.$$delegatedProperties;
                        this.this$0.getClass();
                        DeviceType deviceType = DeviceType.KEYBOARD;
                        Pair pair = new Pair(deviceType, TutorialSchedulerRepository.getDeviceSchedulerInfo(preferences, deviceType));
                        DeviceType deviceType2 = DeviceType.TOUCHPAD;
                        Map mapMapOf = MapsKt__MapsKt.mapOf(pair, new Pair(deviceType2, TutorialSchedulerRepository.getDeviceSchedulerInfo(preferences, deviceType2)));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mapMapOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = data.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, continuationImpl);
    }

    public final Object updateData(Preferences.Key key, Object obj, ContinuationImpl continuationImpl) {
        Object objEdit = PreferencesKt.edit((DataStore) this.dataStore$delegate.getValue(this.applicationContext, $$delegatedProperties[0]), new C08732(key, obj, null), continuationImpl);
        return objEdit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEdit : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object wasEverConnected(DeviceType deviceType, ContinuationImpl continuationImpl) {
        C08741 c08741;
        if (continuationImpl instanceof C08741) {
            c08741 = (C08741) continuationImpl;
            int i = c08741.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08741.label = i - Integer.MIN_VALUE;
            } else {
                c08741 = new C08741(continuationImpl);
            }
        }
        Object objLoadData = c08741.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08741.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLoadData);
            c08741.L$0 = deviceType;
            c08741.label = 1;
            objLoadData = loadData(c08741);
            if (objLoadData == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            deviceType = (DeviceType) c08741.L$0;
            ResultKt.throwOnFailure(objLoadData);
        }
        Object obj2 = ((Map) objLoadData).get(deviceType);
        obj2.getClass();
        return Boolean.valueOf(((DeviceSchedulerInfo) obj2).firstConnectionTime != null);
    }

    public TutorialSchedulerRepository(Context context, CoroutineScope coroutineScope) {
        this(context, coroutineScope, "TutorialScheduler");
    }
}
