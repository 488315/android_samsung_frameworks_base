package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.internal.CloseableCoroutineScope;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$8;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.AuracastDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.ChromeCastDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.controller.device.DeviceController;
import com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.AudioMirroringDevice;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.ChromeCastDevice;
import com.android.systemui.media.mediaoutput.entity.DexDevice;
import com.android.systemui.media.mediaoutput.entity.DisconnectedDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.entity.RemoteDevice;
import com.android.systemui.media.mediaoutput.entity.SmartMirroringDevice;
import com.android.systemui.media.mediaoutput.entity.SmartViewDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt;
import com.android.systemui.media.mediaoutput.ext.CoroutineExtKt;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.android.systemui.plugins.ActivityStarter;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class SessionAudioPathViewModel extends ViewModel implements AudioPathInteraction {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _audioDevices;
    public final Provider activityStarterProvider;
    public final ReadonlyStateFlow audioDevices;
    public final AuracastDeviceController auracastDeviceController;
    public final Context context;
    public final Map controllerMap;
    public final DataStore dataStore;
    public StandaloneCoroutine eventJob;
    public boolean isCastingPriority;
    public boolean isSpotifyCastingPriority;
    public boolean isSupportDisplayOnlyRemoteDevice = true;
    public int numOfAudioOutputChanges;
    public String packageName;
    public final Provider pluginAODManagerProvider;
    public final SavedStateHandle savedStateHandle;

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SessionAudioPathViewModel.this.new AnonymousClass1(continuation);
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
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = SessionAudioPathViewModel.this.dataStore;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$2 dataStoreExt$special$$inlined$map$2IsCastingPriority = DataStoreExt.isCastingPriority(dataStore);
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        sessionAudioPathViewModel.isCastingPriority = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$2IsCastingPriority.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SessionAudioPathViewModel.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = SessionAudioPathViewModel.this.dataStore;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$3 dataStoreExt$special$$inlined$map$3 = new DataStoreExt$special$$inlined$map$3(dataStore.getData(), dataStore);
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        sessionAudioPathViewModel.isSpotifyCastingPriority = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$3.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SessionAudioPathViewModel.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = SessionAudioPathViewModel.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$8 dataStoreDebugLabsExt$special$$inlined$map$8 = new DataStoreDebugLabsExt$special$$inlined$map$8(dataStore.getData());
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        sessionAudioPathViewModel.isSupportDisplayOnlyRemoteDevice = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$8.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8, reason: invalid class name */
        public final class AnonymousClass8 implements FlowCollector {
            public final /* synthetic */ SessionAudioPathViewModel this$0;

            public AnonymousClass8(SessionAudioPathViewModel sessionAudioPathViewModel) {
                this.this$0 = sessionAudioPathViewModel;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(List list, Continuation continuation) {
                SessionAudioPathViewModel$4$8$emit$1 sessionAudioPathViewModel$4$8$emit$1;
                if (continuation instanceof SessionAudioPathViewModel$4$8$emit$1) {
                    sessionAudioPathViewModel$4$8$emit$1 = (SessionAudioPathViewModel$4$8$emit$1) continuation;
                    int i = sessionAudioPathViewModel$4$8$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        sessionAudioPathViewModel$4$8$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        sessionAudioPathViewModel$4$8$emit$1 = new SessionAudioPathViewModel$4$8$emit$1(this, continuation);
                    }
                }
                Object obj = sessionAudioPathViewModel$4$8$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = sessionAudioPathViewModel$4$8$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Log.d("SessionAudioPathViewModel", "list updated");
                    List list2 = list;
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) it.next(), "SessionAudioPathViewModel");
                    }
                    StateFlowImpl stateFlowImpl = this.this$0._audioDevices;
                    sessionAudioPathViewModel$4$8$emit$1.L$0 = list2;
                    sessionAudioPathViewModel$4$8$emit$1.label = 1;
                    stateFlowImpl.updateState(null, list2);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass4 anonymousClass4 = SessionAudioPathViewModel.this.new AnonymousClass4(continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Set set = CollectionsKt___CollectionsKt.toSet(((LinkedHashMap) SessionAudioPathViewModel.this.controllerMap).values());
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    arrayList.add(((DeviceController) it.next()).audioDevicesFlow);
                }
                final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
                Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$combine$1

                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$combine$1$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;

                        public AnonymousClass3(Continuation continuation) {
                            super(3, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                FlowCollector flowCollector = (FlowCollector) this.L$0;
                                List[] listArr = (List[]) ((Object[]) this.L$1);
                                this.label = 1;
                                if (flowCollector.emit(listArr, this) == coroutineSingletons) {
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

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new List[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector, continuation);
                        return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
                    }
                };
                MediaOutputConst.INSTANCE.getClass();
                final Flow flowM3482debounceHG0u8IE = FlowKt.m3482debounceHG0u8IE(flow, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final Flow flow2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                List[] listArr = (List[]) obj;
                                ArrayList arrayList = new ArrayList(listArr.length);
                                for (List list : listArr) {
                                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                                    Iterator it = list.iterator();
                                    while (it.hasNext()) {
                                        arrayList2.add(((AudioDevice) it.next()).clone());
                                    }
                                    arrayList.add(arrayList2);
                                }
                                Object[] array = arrayList.toArray(new List[0]);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(array, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = flowM3482debounceHG0u8IE.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                final Flow flow3 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2

                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ CoroutineScope $$this$launch$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ SessionAudioPathViewModel this$0;

                        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, SessionAudioPathViewModel sessionAudioPathViewModel, CoroutineScope coroutineScope) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = sessionAudioPathViewModel;
                            this.$$this$launch$inlined = coroutineScope;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:189:0x0318  */
                        /* JADX WARN: Removed duplicated region for block: B:190:0x031b  */
                        /* JADX WARN: Removed duplicated region for block: B:192:0x031e  */
                        /* JADX WARN: Removed duplicated region for block: B:193:0x0321  */
                        /* JADX WARN: Removed duplicated region for block: B:195:0x0324  */
                        /* JADX WARN: Removed duplicated region for block: B:198:0x032b  */
                        /* JADX WARN: Removed duplicated region for block: B:200:0x032e  */
                        /* JADX WARN: Removed duplicated region for block: B:296:0x0552  */
                        /* JADX WARN: Removed duplicated region for block: B:305:0x057b  */
                        /* JADX WARN: Removed duplicated region for block: B:314:0x05a4  */
                        /* JADX WARN: Removed duplicated region for block: B:329:0x0631  */
                        /* JADX WARN: Removed duplicated region for block: B:395:0x0778  */
                        /* JADX WARN: Removed duplicated region for block: B:399:0x0788  */
                        /* JADX WARN: Removed duplicated region for block: B:401:0x078b  */
                        /* JADX WARN: Removed duplicated region for block: B:404:0x0793  */
                        /* JADX WARN: Removed duplicated region for block: B:405:0x0795  */
                        /* JADX WARN: Removed duplicated region for block: B:411:0x07b4  */
                        /* JADX WARN: Removed duplicated region for block: B:530:0x0b8a  */
                        /* JADX WARN: Removed duplicated region for block: B:550:0x0bf8  */
                        /* JADX WARN: Removed duplicated region for block: B:551:0x0bfa  */
                        /* JADX WARN: Removed duplicated region for block: B:553:0x0bfd  */
                        /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            List list;
                            List list2;
                            List list3;
                            int i;
                            List list4;
                            boolean z;
                            List list5;
                            List list6;
                            ArrayList arrayList;
                            ArrayList arrayList2;
                            List list7;
                            List list8;
                            List arrayList3;
                            int i2;
                            int i3;
                            CoroutineSingletons coroutineSingletons;
                            int i4;
                            DeviceController deviceController;
                            List list9;
                            List listPlus;
                            boolean z2;
                            boolean z3;
                            ArrayList arrayList4;
                            List listPlus2;
                            int i5;
                            Object obj2;
                            ControllerType controllerType;
                            Object obj3;
                            int i6;
                            int i7;
                            boolean z4;
                            List listSingletonList;
                            AudioDeviceInfo audioDeviceInfo;
                            CharSequence charSequence;
                            MusicShareDevice musicShareDevice;
                            CharSequence multiSequenceString;
                            AudioDeviceInfo audioDeviceInfo2;
                            CharSequence charSequence2;
                            BluetoothDevice bluetoothDevice;
                            CharSequence multiSequenceString2;
                            boolean z5 = true;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i8 = anonymousClass1.label;
                                if ((i8 & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i8 - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj4 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i9 = anonymousClass1.label;
                            if (i9 == 0) {
                                ResultKt.throwOnFailure(obj4);
                                List[] listArr = (List[]) obj;
                                ArrayList arrayList5 = new ArrayList(CollectionsKt___CollectionsKt.toSet(CollectionsKt__IterablesKt.flatten(ArraysKt___ArraysKt.toList(listArr))));
                                AudioDeviceExt audioDeviceExt = AudioDeviceExt.INSTANCE;
                                int length = listArr.length;
                                int i10 = 0;
                                loop0: while (true) {
                                    if (i10 >= length) {
                                        list = null;
                                        break;
                                    }
                                    list = listArr[i10];
                                    List list10 = list;
                                    if (!(list10 instanceof Collection) || !list10.isEmpty()) {
                                        Iterator it = list10.iterator();
                                        while (it.hasNext()) {
                                            if (((AudioDevice) it.next()) instanceof BuiltInDevice) {
                                                break loop0;
                                            }
                                        }
                                    }
                                    i10++;
                                }
                                ArrayList arrayList6 = list != null ? new ArrayList(list) : new ArrayList();
                                arrayList5.removeAll(arrayList6);
                                if (arrayList6.isEmpty()) {
                                    arrayList6 = null;
                                }
                                if (arrayList6 == null) {
                                    arrayList3 = EmptyList.INSTANCE;
                                    i5 = 1;
                                    coroutineSingletons = coroutineSingletons2;
                                } else {
                                    AudioDeviceExt audioDeviceExt2 = AudioDeviceExt.INSTANCE;
                                    ControllerType[] controllerTypeArr = {ControllerType.Bluetooth};
                                    audioDeviceExt2.getClass();
                                    List listFilteredByType = AudioDeviceExt.filteredByType(listArr, controllerTypeArr);
                                    arrayList5.removeAll(listFilteredByType);
                                    int length2 = listArr.length;
                                    int i11 = 0;
                                    loop2: while (true) {
                                        if (i11 >= length2) {
                                            list2 = null;
                                            break;
                                        }
                                        list2 = listArr[i11];
                                        List list11 = list2;
                                        if (!(list11 instanceof Collection) || !list11.isEmpty()) {
                                            Iterator it2 = list11.iterator();
                                            while (it2.hasNext()) {
                                                if (((AudioDevice) it2.next()) instanceof ChromeCastDevice) {
                                                    break loop2;
                                                }
                                            }
                                        }
                                        i11++;
                                    }
                                    ArrayList arrayList7 = list2 != null ? new ArrayList(list2) : new ArrayList();
                                    arrayList5.removeAll(arrayList7);
                                    AudioDeviceExt audioDeviceExt3 = AudioDeviceExt.INSTANCE;
                                    int length3 = listArr.length;
                                    int i12 = 0;
                                    loop4: while (true) {
                                        if (i12 >= length3) {
                                            list3 = null;
                                            break;
                                        }
                                        list3 = listArr[i12];
                                        List list12 = list3;
                                        if (!(list12 instanceof Collection) || !list12.isEmpty()) {
                                            Iterator it3 = list12.iterator();
                                            while (it3.hasNext()) {
                                                if (((AudioDevice) it3.next()) instanceof AudioMirroringDevice) {
                                                    break loop4;
                                                }
                                            }
                                        }
                                        i12++;
                                    }
                                    ArrayList arrayList8 = list3 != null ? new ArrayList(list3) : new ArrayList();
                                    arrayList5.removeAll(arrayList8);
                                    AudioDeviceExt audioDeviceExt4 = AudioDeviceExt.INSTANCE;
                                    ControllerType[] controllerTypeArr2 = {ControllerType.MusicShare};
                                    audioDeviceExt4.getClass();
                                    List listFilteredByType2 = AudioDeviceExt.filteredByType(listArr, controllerTypeArr2);
                                    arrayList5.removeAll(listFilteredByType2);
                                    int length4 = listArr.length;
                                    int i13 = 0;
                                    loop6: while (true) {
                                        if (i13 >= length4) {
                                            i = 0;
                                            list4 = null;
                                            break;
                                        }
                                        list4 = listArr[i13];
                                        List list13 = list4;
                                        i = 0;
                                        if (!(list13 instanceof Collection) || !list13.isEmpty()) {
                                            Iterator it4 = list13.iterator();
                                            while (it4.hasNext()) {
                                                if (((AudioDevice) it4.next()) instanceof SmartViewDevice) {
                                                    break loop6;
                                                }
                                            }
                                        }
                                        i13++;
                                    }
                                    ArrayList arrayList9 = list4 != null ? new ArrayList(list4) : new ArrayList();
                                    arrayList5.removeAll(arrayList9);
                                    AudioDeviceExt audioDeviceExt5 = AudioDeviceExt.INSTANCE;
                                    int length5 = listArr.length;
                                    int i14 = i;
                                    loop8: while (true) {
                                        if (i14 >= length5) {
                                            z = z5;
                                            list5 = null;
                                            break;
                                        }
                                        list5 = listArr[i14];
                                        List list14 = list5;
                                        z = z5;
                                        if (!(list14 instanceof Collection) || !list14.isEmpty()) {
                                            Iterator it5 = list14.iterator();
                                            while (it5.hasNext()) {
                                                if (((AudioDevice) it5.next()) instanceof DexDevice) {
                                                    break loop8;
                                                }
                                            }
                                        }
                                        i14++;
                                        z5 = z;
                                    }
                                    ArrayList arrayList10 = list5 != null ? new ArrayList(list5) : new ArrayList();
                                    arrayList5.removeAll(arrayList10);
                                    AudioDeviceExt audioDeviceExt6 = AudioDeviceExt.INSTANCE;
                                    int length6 = listArr.length;
                                    int i15 = i;
                                    loop10: while (true) {
                                        if (i15 >= length6) {
                                            list6 = null;
                                            break;
                                        }
                                        list6 = listArr[i15];
                                        List list15 = list6;
                                        int i16 = length6;
                                        if (!(list15 instanceof Collection) || !list15.isEmpty()) {
                                            Iterator it6 = list15.iterator();
                                            while (it6.hasNext()) {
                                                if (((AudioDevice) it6.next()) instanceof SmartMirroringDevice) {
                                                    break loop10;
                                                }
                                            }
                                        }
                                        i15++;
                                        length6 = i16;
                                    }
                                    ArrayList arrayList11 = list6 != null ? new ArrayList(list6) : new ArrayList();
                                    arrayList5.removeAll(arrayList11);
                                    AudioDeviceExt audioDeviceExt7 = AudioDeviceExt.INSTANCE;
                                    int length7 = listArr.length;
                                    int i17 = i;
                                    loop12: while (true) {
                                        if (i17 >= length7) {
                                            arrayList = arrayList11;
                                            arrayList2 = arrayList8;
                                            list7 = null;
                                            break;
                                        }
                                        list7 = listArr[i17];
                                        arrayList = arrayList11;
                                        List list16 = list7;
                                        arrayList2 = arrayList8;
                                        if (!(list16 instanceof Collection) || !list16.isEmpty()) {
                                            Iterator it7 = list16.iterator();
                                            while (it7.hasNext()) {
                                                if (((AudioDevice) it7.next()) instanceof DisconnectedDevice) {
                                                    break loop12;
                                                }
                                            }
                                        }
                                        i17++;
                                        arrayList11 = arrayList;
                                        arrayList8 = arrayList2;
                                    }
                                    ArrayList arrayList12 = list7 != null ? new ArrayList(list7) : new ArrayList();
                                    arrayList5.removeAll(arrayList12);
                                    AudioDeviceExt audioDeviceExt8 = AudioDeviceExt.INSTANCE;
                                    int length8 = listArr.length;
                                    int i18 = i;
                                    loop14: while (true) {
                                        if (i18 >= length8) {
                                            list8 = null;
                                            break;
                                        }
                                        list8 = listArr[i18];
                                        List list17 = list8;
                                        List[] listArr2 = listArr;
                                        if (!(list17 instanceof Collection) || !list17.isEmpty()) {
                                            Iterator it8 = list17.iterator();
                                            while (it8.hasNext()) {
                                                if (((AudioDevice) it8.next()) instanceof RemoteDevice) {
                                                    break loop14;
                                                }
                                            }
                                        }
                                        i18++;
                                        listArr = listArr2;
                                    }
                                    arrayList3 = list8 != null ? new ArrayList(list8) : new ArrayList();
                                    arrayList5.removeAll(arrayList3);
                                    List list18 = CollectionsKt___CollectionsKt.toList(arrayList5);
                                    SessionAudioPathViewModel sessionAudioPathViewModel = this.this$0;
                                    String str = sessionAudioPathViewModel.packageName;
                                    if (str != null && !StringsKt__StringsKt.isBlank(str)) {
                                        str = null;
                                    }
                                    if (str != null) {
                                        arrayList7 = new ArrayList();
                                    } else {
                                        String str2 = sessionAudioPathViewModel.packageName;
                                        if ((str2 == null || str2.startsWith("com.spotify.music") != z) ? sessionAudioPathViewModel.isCastingPriority : sessionAudioPathViewModel.isSpotifyCastingPriority) {
                                            i2 = 1;
                                            if ((i2 != 0 ? this.$$this$launch$inlined : null) == null || arrayList7.isEmpty()) {
                                                arrayList7 = null;
                                            }
                                            if (arrayList7 == null) {
                                                arrayList7 = arrayList2;
                                            }
                                        } else if (arrayList7.isEmpty()) {
                                            i3 = i;
                                            if (i3 != 0) {
                                                i2 = i;
                                            }
                                            if ((i2 != 0 ? this.$$this$launch$inlined : null) == null) {
                                                arrayList7 = null;
                                                if (arrayList7 == null) {
                                                }
                                            }
                                        } else {
                                            int size = arrayList7.size();
                                            int i19 = i;
                                            while (i19 < size) {
                                                Object obj5 = arrayList7.get(i19);
                                                i19++;
                                                AudioDeviceExt.INSTANCE.getClass();
                                                if (AudioDeviceExt.isActive((AudioDevice) obj5)) {
                                                    i3 = 1;
                                                    break;
                                                }
                                            }
                                            i3 = i;
                                            if (i3 != 0) {
                                            }
                                            if ((i2 != 0 ? this.$$this$launch$inlined : null) == null) {
                                            }
                                        }
                                    }
                                    ArrayList arrayList13 = new ArrayList();
                                    ArrayList arrayList14 = (ArrayList) listFilteredByType;
                                    int size2 = arrayList14.size();
                                    int i20 = i;
                                    while (i20 < size2) {
                                        int i21 = size2;
                                        Object obj6 = arrayList14.get(i20);
                                        int i22 = i20 + 1;
                                        AudioDevice audioDevice = (AudioDevice) obj6;
                                        CoroutineSingletons coroutineSingletons3 = coroutineSingletons2;
                                        if ((((audioDevice instanceof BluetoothDevice) || (audioDevice instanceof MusicShareDevice)) ? 1 : i) != 0) {
                                            arrayList13.add(obj6);
                                        }
                                        size2 = i21;
                                        i20 = i22;
                                        coroutineSingletons2 = coroutineSingletons3;
                                    }
                                    coroutineSingletons = coroutineSingletons2;
                                    int size3 = arrayList13.size();
                                    int i23 = i;
                                    while (i23 < size3) {
                                        Object obj7 = arrayList13.get(i23);
                                        int i24 = i23 + 1;
                                        AudioDevice audioDevice2 = (AudioDevice) obj7;
                                        int i25 = size3;
                                        boolean z6 = audioDevice2 instanceof BluetoothDevice;
                                        if (z6) {
                                            z4 = z6;
                                            CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothDevice) audioDevice2).cachedBluetoothDevice;
                                            if (cachedBluetoothDevice == null) {
                                                cachedBluetoothDevice = null;
                                            }
                                            listSingletonList = CachedBluetoothDeviceExtKt.getAllAddresses(cachedBluetoothDevice);
                                        } else {
                                            z4 = z6;
                                            if (audioDevice2 instanceof MusicShareDevice) {
                                                CachedBluetoothDevice cachedBluetoothDevice2 = ((MusicShareDevice) audioDevice2).cachedBluetoothDevice;
                                                listSingletonList = cachedBluetoothDevice2 != null ? CachedBluetoothDeviceExtKt.getAllAddresses(cachedBluetoothDevice2) : EmptyList.INSTANCE;
                                            } else {
                                                listSingletonList = Collections.singletonList(audioDevice2.getId());
                                            }
                                        }
                                        ArrayList arrayList15 = arrayList13;
                                        ArrayList arrayList16 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
                                        int size4 = arrayList6.size();
                                        for (int i26 = i; i26 < size4; i26++) {
                                            arrayList16.add((BuiltInDevice) ((AudioDevice) arrayList6.get(i26)));
                                        }
                                        ArrayList arrayList17 = new ArrayList();
                                        int size5 = arrayList16.size();
                                        AnonymousClass1 anonymousClass12 = anonymousClass1;
                                        int i27 = i;
                                        while (i27 < size5) {
                                            int i28 = size5;
                                            Object obj8 = arrayList16.get(i27);
                                            int i29 = i27 + 1;
                                            AudioDeviceInfo audioDeviceInfo3 = ((BuiltInDevice) obj8).audioDeviceInfo;
                                            if (audioDeviceInfo3 == null) {
                                                audioDeviceInfo3 = null;
                                            }
                                            if (listSingletonList.contains(audioDeviceInfo3.getAddress())) {
                                                arrayList17.add(obj8);
                                            }
                                            size5 = i28;
                                            i27 = i29;
                                        }
                                        if (arrayList17.isEmpty()) {
                                            arrayList17 = null;
                                        }
                                        if (arrayList17 != null) {
                                            if (z4) {
                                                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) audioDevice2;
                                                BuiltInDevice builtInDevice = (BuiltInDevice) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList17);
                                                if (builtInDevice == null || (audioDeviceInfo2 = builtInDevice.audioDeviceInfo) == null) {
                                                    audioDeviceInfo2 = null;
                                                }
                                                bluetoothDevice2.audioDeviceInfo = audioDeviceInfo2;
                                                BuiltInDevice builtInDevice2 = (BuiltInDevice) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList17);
                                                if (builtInDevice2 != null && (charSequence2 = builtInDevice2.multiSoundInfo) != null) {
                                                    int iIndexOf = arrayList14.indexOf(audioDevice2);
                                                    CharSequence charSequence3 = bluetoothDevice2.description;
                                                    if (charSequence3 != null) {
                                                        bluetoothDevice = bluetoothDevice2;
                                                        CharSequence[] charSequenceArr = new CharSequence[2];
                                                        charSequenceArr[i] = charSequence3;
                                                        charSequenceArr[1] = charSequence2;
                                                        multiSequenceString2 = new MultiSequenceString(Arrays.asList(charSequenceArr), "\n");
                                                    } else {
                                                        bluetoothDevice = bluetoothDevice2;
                                                        multiSequenceString2 = charSequence2;
                                                    }
                                                    BluetoothDevice bluetoothDeviceCopy$default = BluetoothDevice.copy$default(bluetoothDevice, multiSequenceString2, null, 0, null, false, 2043);
                                                    bluetoothDeviceCopy$default.deepCopy(bluetoothDevice);
                                                    arrayList14.set(iIndexOf, bluetoothDeviceCopy$default);
                                                    Unit unit = Unit.INSTANCE;
                                                }
                                            } else if (audioDevice2 instanceof MusicShareDevice) {
                                                MusicShareDevice musicShareDevice2 = (MusicShareDevice) audioDevice2;
                                                BuiltInDevice builtInDevice3 = (BuiltInDevice) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList17);
                                                if (builtInDevice3 == null || (audioDeviceInfo = builtInDevice3.audioDeviceInfo) == null) {
                                                    audioDeviceInfo = null;
                                                }
                                                musicShareDevice2.audioDeviceInfo = audioDeviceInfo;
                                                BuiltInDevice builtInDevice4 = (BuiltInDevice) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList17);
                                                if (builtInDevice4 != null && (charSequence = builtInDevice4.multiSoundInfo) != null) {
                                                    int iIndexOf2 = arrayList14.indexOf(audioDevice2);
                                                    CharSequence charSequence4 = musicShareDevice2.description;
                                                    if (charSequence4 != null) {
                                                        musicShareDevice = musicShareDevice2;
                                                        CharSequence[] charSequenceArr2 = new CharSequence[2];
                                                        charSequenceArr2[i] = charSequence4;
                                                        charSequenceArr2[1] = charSequence;
                                                        multiSequenceString = new MultiSequenceString(Arrays.asList(charSequenceArr2), "\n");
                                                    } else {
                                                        musicShareDevice = musicShareDevice2;
                                                        multiSequenceString = charSequence;
                                                    }
                                                    MusicShareDevice musicShareDeviceCopy$default = MusicShareDevice.copy$default(musicShareDevice, multiSequenceString, 0, null, false, 2043);
                                                    musicShareDeviceCopy$default.deepCopy(musicShareDevice);
                                                    arrayList14.set(iIndexOf2, musicShareDeviceCopy$default);
                                                    Unit unit2 = Unit.INSTANCE;
                                                }
                                            }
                                            arrayList6.removeAll(arrayList17);
                                        }
                                        size3 = i25;
                                        i23 = i24;
                                        arrayList13 = arrayList15;
                                        anonymousClass1 = anonymousClass12;
                                    }
                                    AnonymousClass1 anonymousClass13 = anonymousClass1;
                                    ArrayList arrayList18 = (ArrayList) listFilteredByType2;
                                    int size6 = arrayList18.size();
                                    int i30 = i;
                                    while (i30 < size6) {
                                        Object obj9 = arrayList18.get(i30);
                                        i30++;
                                        final AudioDevice audioDevice3 = (AudioDevice) obj9;
                                        final Function1 function1 = 
                                        /*  JADX ERROR: Method code generation error
                                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0510: CONSTRUCTOR (r13v73 'function1' kotlin.jvm.functions.Function1) = (r6v85 'audioDevice3' com.android.systemui.media.mediaoutput.entity.AudioDevice A[DONT_INLINE]) A[DECLARE_VAR, MD:(com.android.systemui.media.mediaoutput.entity.AudioDevice):void (m)] (LINE:1297) call: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$4$3$1.<init>(com.android.systemui.media.mediaoutput.entity.AudioDevice):void type: CONSTRUCTOR in method: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:226)
                                            	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:171)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$4$3$1, state: NOT_LOADED
                                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                            	... 33 more
                                            */
                                        /*
                                            Method dump skipped, instructions count: 3151
                                            To view this dump add '--comments-level debug' option
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                    Object objCollect = flow2.collect(new AnonymousClass2(flowCollector, sessionAudioPathViewModel, coroutineScope), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            };
                            final Flow flow4 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1

                                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                                    public final class AnonymousClass1 extends ContinuationImpl {
                                        Object L$0;
                                        Object L$1;
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
                                            if (!((List) obj).isEmpty()) {
                                                anonymousClass1.label = 1;
                                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
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
                                    Object objCollect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            };
                            final SessionAudioPathViewModel sessionAudioPathViewModel2 = SessionAudioPathViewModel.this;
                            final Flow flow5 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2

                                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                    public final /* synthetic */ SessionAudioPathViewModel this$0;

                                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2$2$1, reason: invalid class name */
                                    public final class AnonymousClass1 extends ContinuationImpl {
                                        Object L$0;
                                        Object L$1;
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

                                    public AnonymousClass2(FlowCollector flowCollector, SessionAudioPathViewModel sessionAudioPathViewModel) {
                                        this.$this_unsafeFlow = flowCollector;
                                        this.this$0 = sessionAudioPathViewModel;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:28:0x0072 A[RETURN] */
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
                                            List list = (List) obj;
                                            if (this.this$0.isBroadcasting()) {
                                                anonymousClass1.label = 1;
                                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                }
                                            } else {
                                                List<AudioDevice> list2 = list;
                                                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                                                    for (AudioDevice audioDevice : list2) {
                                                        AudioDeviceExt.INSTANCE.getClass();
                                                        if (AudioDeviceExt.isActive(audioDevice)) {
                                                            anonymousClass1.label = 1;
                                                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                                return coroutineSingletons;
                                                            }
                                                        }
                                                    }
                                                }
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
                                    Object objCollect = flow4.collect(new AnonymousClass2(flowCollector, sessionAudioPathViewModel2), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            };
                            Flow flow6 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3

                                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3$2$1, reason: invalid class name */
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
                                            List list = (List) obj;
                                            LinkedHashMap linkedHashMap = new LinkedHashMap();
                                            for (Object obj3 : list) {
                                                String id = ((AudioDevice) obj3).getId();
                                                Object arrayList = linkedHashMap.get(id);
                                                if (arrayList == null) {
                                                    arrayList = new ArrayList();
                                                    linkedHashMap.put(id, arrayList);
                                                }
                                                ((List) arrayList).add(obj3);
                                            }
                                            Collection collectionValues = linkedHashMap.values();
                                            ArrayList arrayList2 = new ArrayList();
                                            Iterator it = collectionValues.iterator();
                                            while (it.hasNext()) {
                                                AudioDevice audioDevice = (AudioDevice) CollectionsKt___CollectionsKt.firstOrNull((List) it.next());
                                                if (audioDevice != null) {
                                                    arrayList2.add(audioDevice);
                                                }
                                            }
                                            List listMinus = CollectionsKt___CollectionsKt.minus((Iterable) list, (Iterable) CollectionsKt___CollectionsKt.toSet(arrayList2));
                                            if (listMinus.isEmpty()) {
                                                listMinus = null;
                                            }
                                            if (listMinus != null) {
                                                Log.d("SessionAudioPathViewModel", "remove duplicated devices - " + listMinus);
                                            }
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(arrayList2, anonymousClass1) == coroutineSingletons) {
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
                                    Object objCollect = flow5.collect(new AnonymousClass2(flowCollector), continuation);
                                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                                }
                            };
                            AnonymousClass8 anonymousClass8 = new AnonymousClass8(SessionAudioPathViewModel.this);
                            this.label = 1;
                            if (flow6.collect(anonymousClass8, this) == coroutineSingletons) {
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

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$5, reason: invalid class name */
                final class AnonymousClass5 extends SuspendLambda implements Function2 {
                    int label;

                    public AnonymousClass5(Continuation continuation) {
                        super(2, continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return SessionAudioPathViewModel.this.new AnonymousClass5(continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        Object next;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            this.label = 1;
                            if (DelayKt.delay(1000L, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        List list = (List) SessionAudioPathViewModel.this._audioDevices.getValue();
                        MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
                        SaEvent.OutputDevice outputDevice = SaEvent.OutputDevice.INSTANCE;
                        SaCustom[] saCustomArr = {new SaCustom.Number(list.size())};
                        moSaLogging.getClass();
                        MoSaLogging.send(outputDevice, saCustomArr);
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            AudioDeviceExt.INSTANCE.getClass();
                            if (AudioDeviceExt.isActive((AudioDevice) next)) {
                                break;
                            }
                        }
                        AudioDevice audioDevice = (AudioDevice) next;
                        if (audioDevice != null) {
                            MoSaLogging moSaLogging2 = MoSaLogging.INSTANCE;
                            SaEvent.ActiveOutputDevice activeOutputDevice = SaEvent.ActiveOutputDevice.INSTANCE;
                            SaCustom[] saCustomArr2 = {new SaCustom.Type(audioDevice.getControllerType().name())};
                            moSaLogging2.getClass();
                            MoSaLogging.send(activeOutputDevice, saCustomArr2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                public final class Companion {
                    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                        this();
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    public static final List access$changeInActiveState(Companion companion, List list) {
                        MusicShareDevice musicShareDevice;
                        companion.getClass();
                        State state = State.CONNECTED;
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : list) {
                            AudioDeviceExt.INSTANCE.getClass();
                            if (AudioDeviceExt.isActive((AudioDevice) obj)) {
                                arrayList.add(obj);
                            }
                        }
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            int i2 = i + 1;
                            AudioDevice audioDevice = (AudioDevice) arrayList.get(i);
                            if (audioDevice instanceof BuiltInDevice) {
                                BuiltInDevice builtInDeviceCopy$default = BuiltInDevice.copy$default((BuiltInDevice) audioDevice, null, null, 0, state, 383);
                                builtInDeviceCopy$default.deepCopy((BuiltInDevice) audioDevice);
                                musicShareDevice = builtInDeviceCopy$default;
                            } else if (audioDevice instanceof BluetoothDevice) {
                                BluetoothDevice bluetoothDeviceCopy$default = BluetoothDevice.copy$default((BluetoothDevice) audioDevice, null, null, 0, state, false, 1919);
                                bluetoothDeviceCopy$default.deepCopy((BluetoothDevice) audioDevice);
                                musicShareDevice = bluetoothDeviceCopy$default;
                            } else if (audioDevice instanceof MusicShareDevice) {
                                State state2 = state;
                                MusicShareDevice musicShareDeviceCopy$default = MusicShareDevice.copy$default((MusicShareDevice) audioDevice, null, 0, state2, false, 1919);
                                state = state2;
                                musicShareDeviceCopy$default.deepCopy((MusicShareDevice) audioDevice);
                                musicShareDevice = musicShareDeviceCopy$default;
                            } else {
                                arrayList2.add(audioDevice);
                                i = i2;
                            }
                            audioDevice = musicShareDevice;
                            arrayList2.add(audioDevice);
                            i = i2;
                        }
                        return arrayList2;
                    }

                    private Companion() {
                    }
                }

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$adjustVolume$1, reason: invalid class name and case insensitive filesystem */
                final class C09571 extends SuspendLambda implements Function2 {
                    final /* synthetic */ AudioDevice $device;
                    final /* synthetic */ int $volume;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C09571(AudioDevice audioDevice, int i, Continuation continuation) {
                        super(2, continuation);
                        this.$device = audioDevice;
                        this.$volume = i;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return SessionAudioPathViewModel.this.new C09571(this.$device, this.$volume, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C09571) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            DeviceController deviceController = (DeviceController) ((LinkedHashMap) SessionAudioPathViewModel.this.controllerMap).get(this.$device.getFinalControllerType());
                            if (deviceController != null) {
                                AudioDevice audioDevice = this.$device;
                                int i2 = this.$volume;
                                this.label = 1;
                                if (deviceController.adjustVolume(audioDevice, i2) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$cancel$1, reason: invalid class name and case insensitive filesystem */
                final class C09581 extends SuspendLambda implements Function2 {
                    final /* synthetic */ AudioDevice $device;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C09581(AudioDevice audioDevice, Continuation continuation) {
                        super(2, continuation);
                        this.$device = audioDevice;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return SessionAudioPathViewModel.this.new C09581(this.$device, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C09581) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            DeviceController deviceController = (DeviceController) ((LinkedHashMap) SessionAudioPathViewModel.this.controllerMap).get(this.$device.getFinalControllerType());
                            if (deviceController != null) {
                                AudioDevice audioDevice = this.$device;
                                this.label = 1;
                                if (deviceController.cancel(audioDevice) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$deselect$4, reason: invalid class name and case insensitive filesystem */
                final class C09594 extends SuspendLambda implements Function1 {
                    final /* synthetic */ AudioDevice $device;
                    Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C09594(AudioDevice audioDevice, Continuation continuation) {
                        super(1, continuation);
                        this.$device = audioDevice;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Continuation continuation) {
                        return SessionAudioPathViewModel.this.new C09594(this.$device, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        return ((C09594) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:23:0x0061, code lost:
                    
                        if (r5.deselect(r1, r4) == r0) goto L24;
                     */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invokeSuspend(Object obj) {
                        SessionAudioPathViewModel sessionAudioPathViewModel;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                            AudioDevice audioDevice = this.$device;
                            this.L$0 = sessionAudioPathViewModel;
                            this.label = 1;
                            obj = SessionAudioPathViewModel.access$checkForBudsTogether(sessionAudioPathViewModel, audioDevice, false, this);
                            if (obj != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        if (i != 1) {
                            if (i != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            return Unit.INSTANCE;
                        }
                        sessionAudioPathViewModel = (SessionAudioPathViewModel) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (((Boolean) obj).booleanValue()) {
                            sessionAudioPathViewModel = null;
                        }
                        if (sessionAudioPathViewModel == null) {
                            return Unit.INSTANCE;
                        }
                        DeviceController deviceController = (DeviceController) ((LinkedHashMap) SessionAudioPathViewModel.this.controllerMap).get(this.$device.getFinalControllerType());
                        if (deviceController != null) {
                            AudioDevice audioDevice2 = this.$device;
                            this.L$0 = null;
                            this.label = 2;
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$goToApp$1, reason: invalid class name and case insensitive filesystem */
                final class C09601 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Intent $intent;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C09601(Intent intent, Continuation continuation) {
                        super(2, continuation);
                        this.$intent = intent;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return SessionAudioPathViewModel.this.new C09601(this.$intent, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C09601) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        PendingIntent activity = PendingIntent.getActivity(SessionAudioPathViewModel.this.context, 0, this.$intent, 67108864);
                        SavedStateHandle savedStateHandle = SessionAudioPathViewModel.this.savedStateHandle;
                        if (savedStateHandle != null ? Intrinsics.areEqual(savedStateHandle.get("isCover"), Boolean.TRUE) : false) {
                            ((PluginAODManager) SessionAudioPathViewModel.this.pluginAODManagerProvider.get()).showCoverToast(activity, this.$intent);
                        } else {
                            ((ActivityStarter) SessionAudioPathViewModel.this.activityStarterProvider.get()).postStartActivityDismissingKeyguard(activity, true);
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$select$4, reason: invalid class name and case insensitive filesystem */
                final class C09614 extends SuspendLambda implements Function1 {
                    final /* synthetic */ AudioDevice $device;
                    Object L$0;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C09614(AudioDevice audioDevice, Continuation continuation) {
                        super(1, continuation);
                        this.$device = audioDevice;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Continuation continuation) {
                        return SessionAudioPathViewModel.this.new C09614(this.$device, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        return ((C09614) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:23:0x0060, code lost:
                    
                        if (r5.select(r1, r4) == r0) goto L24;
                     */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invokeSuspend(Object obj) {
                        SessionAudioPathViewModel sessionAudioPathViewModel;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                            AudioDevice audioDevice = this.$device;
                            this.L$0 = sessionAudioPathViewModel;
                            this.label = 1;
                            obj = SessionAudioPathViewModel.access$checkForBudsTogether(sessionAudioPathViewModel, audioDevice, true, this);
                            if (obj != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        if (i != 1) {
                            if (i != 2) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                            return Unit.INSTANCE;
                        }
                        sessionAudioPathViewModel = (SessionAudioPathViewModel) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        if (((Boolean) obj).booleanValue()) {
                            sessionAudioPathViewModel = null;
                        }
                        if (sessionAudioPathViewModel == null) {
                            return Unit.INSTANCE;
                        }
                        DeviceController deviceController = (DeviceController) ((LinkedHashMap) SessionAudioPathViewModel.this.controllerMap).get(this.$device.getFinalControllerType());
                        if (deviceController != null) {
                            AudioDevice audioDevice2 = this.$device;
                            this.L$0 = null;
                            this.label = 2;
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$transfer$4, reason: invalid class name and case insensitive filesystem */
                final class C09624 extends SuspendLambda implements Function1 {
                    final /* synthetic */ AudioDevice $device;
                    int label;
                    final /* synthetic */ SessionAudioPathViewModel this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C09624(AudioDevice audioDevice, SessionAudioPathViewModel sessionAudioPathViewModel, Continuation continuation) {
                        super(1, continuation);
                        this.$device = audioDevice;
                        this.this$0 = sessionAudioPathViewModel;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Continuation continuation) {
                        return new C09624(this.$device, this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        return ((C09624) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        SaEvent saEvent;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
                            SaEvent.ChangeAudioOutput changeAudioOutput = SaEvent.ChangeAudioOutput.INSTANCE;
                            String strName = this.$device.getFinalControllerType().name();
                            Long l = new Long(this.this$0.numOfAudioOutputChanges);
                            moSaLogging.getClass();
                            MoSaLogging.send(changeAudioOutput, strName, l);
                            SessionAudioPathViewModel sessionAudioPathViewModel = this.this$0;
                            sessionAudioPathViewModel.numOfAudioOutputChanges++;
                            DeviceController deviceController = (DeviceController) ((LinkedHashMap) sessionAudioPathViewModel.controllerMap).get(this.$device.getFinalControllerType());
                            if (deviceController != null) {
                                AudioDevice audioDevice = this.$device;
                                this.label = 1;
                                if (deviceController.transfer(audioDevice, this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            }
                        } else {
                            if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj);
                        }
                        DeviceController deviceController2 = (DeviceController) ((LinkedHashMap) this.this$0.controllerMap).get(ControllerType.BuiltIn);
                        if (deviceController2 != null) {
                            if (this.$device instanceof BluetoothDevice) {
                                Iterable<AudioDevice> iterable = (Iterable) this.this$0._audioDevices.getValue();
                                if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                                    for (AudioDevice audioDevice2 : iterable) {
                                        if (audioDevice2 instanceof BluetoothDevice) {
                                            AudioDeviceExt.INSTANCE.getClass();
                                            if (AudioDeviceExt.isActive(audioDevice2)) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        AudioDeviceExt audioDeviceExt = AudioDeviceExt.INSTANCE;
                        AudioDevice audioDevice3 = this.$device;
                        audioDeviceExt.getClass();
                        if (audioDevice3 instanceof BuiltInDevice) {
                            saEvent = SaEvent.PhoneSpeaker.INSTANCE;
                        } else if (audioDevice3 instanceof BluetoothDevice) {
                            CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothDevice) audioDevice3).cachedBluetoothDevice;
                            saEvent = CachedBluetoothDeviceExtKt.isBudsDevice(cachedBluetoothDevice != null ? cachedBluetoothDevice : null) ? SaEvent.ConnectedBuds.INSTANCE : SaEvent.ConnectedBt.INSTANCE;
                        } else {
                            saEvent = audioDevice3 instanceof DisconnectedDevice ? SaEvent.DisconnectedBt.INSTANCE : audioDevice3 instanceof ChromeCastDevice ? SaEvent.WifiSpeaker.INSTANCE : audioDevice3 instanceof GroupDevice ? SaEvent.GroupWifiSpeaker.INSTANCE : null;
                        }
                        if (saEvent != null) {
                            MoSaLogging.send$default(MoSaLogging.INSTANCE, saEvent);
                        }
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: Removed duplicated region for block: B:14:0x00a7  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public SessionAudioPathViewModel(Context context, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, AuracastDeviceController auracastDeviceController, Provider provider9, DataStore dataStore, Provider provider10, Provider provider11, SavedStateHandle savedStateHandle) {
                    String str;
                    Integer num;
                    this.context = context;
                    this.auracastDeviceController = auracastDeviceController;
                    this.dataStore = dataStore;
                    this.activityStarterProvider = provider10;
                    this.pluginAODManagerProvider = provider11;
                    this.savedStateHandle = savedStateHandle;
                    Map mapMutableMapOf = MapsKt__MapsKt.mutableMapOf(new Pair(ControllerType.BuiltIn, provider.get()), new Pair(ControllerType.Disconnected, provider5.get()));
                    if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
                        mapMutableMapOf.put(ControllerType.MusicShare, provider6.get());
                    }
                    WifiDisplayDeviceController wifiDisplayDeviceController = (WifiDisplayDeviceController) provider7.get();
                    mapMutableMapOf.put(ControllerType.SmartView, wifiDisplayDeviceController);
                    mapMutableMapOf.put(ControllerType.Dex, wifiDisplayDeviceController);
                    BluetoothDeviceController bluetoothDeviceController = (BluetoothDeviceController) provider2.get();
                    mapMutableMapOf.put(ControllerType.Bluetooth, bluetoothDeviceController);
                    mapMutableMapOf.put(ControllerType.BluetoothGroup, bluetoothDeviceController);
                    ChromeCastDeviceController chromeCastDeviceController = (ChromeCastDeviceController) provider3.get();
                    mapMutableMapOf.put(ControllerType.ChromeCast, chromeCastDeviceController);
                    mapMutableMapOf.put(ControllerType.ChromeCastGroup, chromeCastDeviceController);
                    AudioMirroringDeviceController audioMirroringDeviceController = (AudioMirroringDeviceController) provider4.get();
                    mapMutableMapOf.put(ControllerType.AudioMirroring, audioMirroringDeviceController);
                    mapMutableMapOf.put(ControllerType.AudioMirroringGroup, audioMirroringDeviceController);
                    mapMutableMapOf.put(ControllerType.Remote, (RemoteDeviceController) provider9.get());
                    if (savedStateHandle == null || (num = (Integer) savedStateHandle.get("displayId")) == null) {
                        mapMutableMapOf.put(ControllerType.SmartMirroring, (SmartMirroringDeviceController) provider8.get());
                    } else {
                        if ((num.intValue() == 0 ? null : num) == null) {
                        }
                    }
                    this.controllerMap = mapMutableMapOf;
                    StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
                    this._audioDevices = stateFlowImplMutableStateFlow;
                    this.audioDevices = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
                    this.numOfAudioOutputChanges = 1;
                    Log.d("SessionAudioPathViewModel", "init()");
                    setPackageName((savedStateHandle == null || (str = (String) savedStateHandle.get("packageName")) == null) ? "" : str);
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 3);
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass3(null), 3);
                    CloseableCoroutineScope viewModelScope = androidx.lifecycle.ViewModelKt.getViewModelScope(this);
                    DefaultScheduler defaultScheduler = Dispatchers.Default;
                    BuildersKt.launch$default(viewModelScope, defaultScheduler, null, new AnonymousClass4(null), 2);
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), defaultScheduler, null, new AnonymousClass5(null), 2);
                }

                /* JADX WARN: Code restructure failed: missing block: B:47:0x012e, code lost:
                
                    if (r7.select(r13, r0) == r1) goto L55;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:54:0x0145, code lost:
                
                    if (r7.deselect(r15, r0) == r1) goto L55;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:55:0x0147, code lost:
                
                    return r1;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public static final Object access$checkForBudsTogether(SessionAudioPathViewModel sessionAudioPathViewModel, AudioDevice audioDevice, boolean z, ContinuationImpl continuationImpl) {
                    SessionAudioPathViewModel$checkForBudsTogether$1 sessionAudioPathViewModel$checkForBudsTogether$1;
                    sessionAudioPathViewModel.getClass();
                    if (continuationImpl instanceof SessionAudioPathViewModel$checkForBudsTogether$1) {
                        sessionAudioPathViewModel$checkForBudsTogether$1 = (SessionAudioPathViewModel$checkForBudsTogether$1) continuationImpl;
                        int i = sessionAudioPathViewModel$checkForBudsTogether$1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            sessionAudioPathViewModel$checkForBudsTogether$1.label = i - Integer.MIN_VALUE;
                        } else {
                            sessionAudioPathViewModel$checkForBudsTogether$1 = new SessionAudioPathViewModel$checkForBudsTogether$1(sessionAudioPathViewModel, continuationImpl);
                        }
                    }
                    Object obj = sessionAudioPathViewModel$checkForBudsTogether$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = sessionAudioPathViewModel$checkForBudsTogether$1.label;
                    Boolean bool = null;
                    boolean zBooleanValue = false;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj);
                        Log.d("SessionAudioPathViewModel", "checkForBudsTogether() - " + audioDevice + ", actionSelect - " + z);
                        if ((audioDevice instanceof BluetoothDevice) || (audioDevice instanceof MusicShareDevice)) {
                            Iterable iterable = (Iterable) sessionAudioPathViewModel._audioDevices.getValue();
                            ArrayList arrayList = new ArrayList();
                            for (Object obj2 : iterable) {
                                AudioDeviceExt.INSTANCE.getClass();
                                if (AudioDeviceExt.isConnected((AudioDevice) obj2)) {
                                    arrayList.add(obj2);
                                }
                            }
                            ArrayList arrayList2 = new ArrayList();
                            int size = arrayList.size();
                            int i3 = 0;
                            while (i3 < size) {
                                Object obj3 = arrayList.get(i3);
                                i3++;
                                if (obj3 instanceof BluetoothDevice) {
                                    arrayList2.add(obj3);
                                }
                            }
                            ArrayList arrayList3 = new ArrayList();
                            int size2 = arrayList.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj4 = arrayList.get(i4);
                                i4++;
                                if (obj4 instanceof MusicShareDevice) {
                                    arrayList3.add(obj4);
                                }
                            }
                            Pair pair = new Pair(arrayList2, arrayList3);
                            if (((List) pair.getFirst()).size() != 1 || ((List) pair.getSecond()).size() != 1) {
                                pair = null;
                            }
                            if (pair != null) {
                                Pair pair2 = new Pair(CollectionsKt___CollectionsKt.first((List) pair.getFirst()), CollectionsKt___CollectionsKt.first((List) pair.getSecond()));
                                AudioDevice audioDevice2 = (BluetoothDevice) pair2.component1();
                                AudioDevice audioDevice3 = (MusicShareDevice) pair2.component2();
                                DeviceController deviceController = (DeviceController) ((LinkedHashMap) sessionAudioPathViewModel.controllerMap).get(audioDevice.getFinalControllerType());
                                if (deviceController != null) {
                                    if (z) {
                                        sessionAudioPathViewModel$checkForBudsTogether$1.L$0 = sessionAudioPathViewModel;
                                        sessionAudioPathViewModel$checkForBudsTogether$1.L$1 = deviceController;
                                        sessionAudioPathViewModel$checkForBudsTogether$1.Z$0 = z;
                                        sessionAudioPathViewModel$checkForBudsTogether$1.label = 1;
                                    } else {
                                        if (!Intrinsics.areEqual(audioDevice2, audioDevice)) {
                                            audioDevice2 = audioDevice3;
                                        }
                                        sessionAudioPathViewModel$checkForBudsTogether$1.L$0 = sessionAudioPathViewModel;
                                        sessionAudioPathViewModel$checkForBudsTogether$1.L$1 = deviceController;
                                        sessionAudioPathViewModel$checkForBudsTogether$1.Z$0 = z;
                                        sessionAudioPathViewModel$checkForBudsTogether$1.label = 2;
                                    }
                                }
                            }
                        }
                        return Boolean.valueOf(zBooleanValue);
                    }
                    if (i2 != 1 && i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    z = sessionAudioPathViewModel$checkForBudsTogether$1.Z$0;
                    sessionAudioPathViewModel = (SessionAudioPathViewModel) sessionAudioPathViewModel$checkForBudsTogether$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    DeviceController deviceController2 = (DeviceController) ((LinkedHashMap) sessionAudioPathViewModel.controllerMap).get(ControllerType.MusicShare);
                    if (deviceController2 != null) {
                        MusicShareDeviceController musicShareDeviceController = deviceController2 instanceof MusicShareDeviceController ? (MusicShareDeviceController) deviceController2 : null;
                        if (musicShareDeviceController != null) {
                            musicShareDeviceController.setAudioSharingEnabled(z);
                            bool = Boolean.TRUE;
                        }
                    }
                    if (bool != null) {
                        zBooleanValue = bool.booleanValue();
                    }
                    return Boolean.valueOf(zBooleanValue);
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final void adjustVolume(AudioDevice audioDevice, int i) {
                    Log.d("SessionAudioPathViewModel", "adjustVolume() - " + audioDevice + ", " + i);
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, null, new C09571(audioDevice, i, null), 2);
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final void cancel(AudioDevice audioDevice) {
                    Log.d("SessionAudioPathViewModel", "cancel() - " + audioDevice);
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, null, new C09581(audioDevice, null), 2);
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final void deselect(AudioDevice audioDevice) {
                    AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("deselect() - ", audioDevice, "SessionAudioPathViewModel");
                    StandaloneCoroutine standaloneCoroutine = this.eventJob;
                    if (standaloneCoroutine != null) {
                        if (!standaloneCoroutine.isActive()) {
                            standaloneCoroutine = null;
                        }
                        if (standaloneCoroutine != null) {
                            Log.e("SessionAudioPathViewModel", "deselect() - blocked");
                            return;
                        }
                    }
                    this.eventJob = CoroutineExtKt.durationLaunch(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, new C09594(audioDevice, null));
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final ReadonlyStateFlow getAudioDevices() {
                    return this.audioDevices;
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final void goToApp(Intent intent) {
                    BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new C09601(intent, null), 3);
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final boolean isBroadcasting() {
                    return AudioManagerExtKt.isBroadcasting(this.auracastDeviceController.audioManager);
                }

                @Override // androidx.lifecycle.ViewModel
                public final void onCleared() {
                    Log.d("SessionAudioPathViewModel", "onCleared()");
                    Iterator it = CollectionsKt___CollectionsKt.toSet(((LinkedHashMap) this.controllerMap).values()).iterator();
                    while (it.hasNext()) {
                        ((DeviceController) it.next()).close();
                    }
                    this.auracastDeviceController.close();
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final void select(AudioDevice audioDevice) {
                    AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "SessionAudioPathViewModel");
                    StandaloneCoroutine standaloneCoroutine = this.eventJob;
                    if (standaloneCoroutine != null) {
                        if (!standaloneCoroutine.isActive()) {
                            standaloneCoroutine = null;
                        }
                        if (standaloneCoroutine != null) {
                            Log.e("SessionAudioPathViewModel", "select() - blocked");
                            return;
                        }
                    }
                    this.eventJob = CoroutineExtKt.durationLaunch(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, new C09614(audioDevice, null));
                }

                public final void setPackageName(String str) {
                    if (Intrinsics.areEqual(this.packageName, str)) {
                        return;
                    }
                    MediaSessions$H$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "SessionAudioPathViewModel");
                    this.packageName = str;
                    DeviceController deviceController = (DeviceController) ((LinkedHashMap) this.controllerMap).get(ControllerType.ChromeCast);
                    ChromeCastDeviceController chromeCastDeviceController = deviceController instanceof ChromeCastDeviceController ? (ChromeCastDeviceController) deviceController : null;
                    if (chromeCastDeviceController != null) {
                        chromeCastDeviceController.setPackageName(str == null ? "" : str);
                    }
                    DeviceController deviceController2 = (DeviceController) ((LinkedHashMap) this.controllerMap).get(ControllerType.AudioMirroring);
                    AudioMirroringDeviceController audioMirroringDeviceController = deviceController2 instanceof AudioMirroringDeviceController ? (AudioMirroringDeviceController) deviceController2 : null;
                    if (audioMirroringDeviceController != null) {
                        audioMirroringDeviceController.setPackageName(str == null ? "" : str);
                    }
                    DeviceController deviceController3 = (DeviceController) ((LinkedHashMap) this.controllerMap).get(ControllerType.Remote);
                    RemoteDeviceController remoteDeviceController = deviceController3 instanceof RemoteDeviceController ? (RemoteDeviceController) deviceController3 : null;
                    if (remoteDeviceController != null) {
                        if (str == null) {
                            str = "";
                        }
                        remoteDeviceController.setPackageName(str);
                    }
                }

                @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
                public final void transfer(AudioDevice audioDevice) {
                    AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "SessionAudioPathViewModel");
                    StandaloneCoroutine standaloneCoroutine = this.eventJob;
                    if (standaloneCoroutine != null) {
                        if (!standaloneCoroutine.isActive()) {
                            standaloneCoroutine = null;
                        }
                        if (standaloneCoroutine != null) {
                            Log.e("SessionAudioPathViewModel", "transfer() - blocked");
                            return;
                        }
                    }
                    this.eventJob = CoroutineExtKt.durationLaunch(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, new C09624(audioDevice, this, null));
                }
            }
