package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$8;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.AuracastDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.ChromeCastDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.controller.device.DeviceController;
import com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.CoroutineExtKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
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
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                DataStoreExt$special$$inlined$map$2 dataStoreExt$special$$inlined$map$2 = new DataStoreExt$special$$inlined$map$2(new DataStoreExt$special$$inlined$map$1(dataStore.getData()));
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SessionAudioPathViewModel.this.isCastingPriority = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
                DataStoreExt$special$$inlined$map$3 dataStoreExt$special$$inlined$map$3 = new DataStoreExt$special$$inlined$map$3(dataStore.getData());
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SessionAudioPathViewModel.this.isSpotifyCastingPriority = ((Boolean) obj2).booleanValue();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        SessionAudioPathViewModel.this.isSupportDisplayOnlyRemoteDevice = ((Boolean) obj2).booleanValue();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8, reason: invalid class name */
        public final class AnonymousClass8 implements FlowCollector {
            public final /* synthetic */ SessionAudioPathViewModel this$0;

            public AnonymousClass8(SessionAudioPathViewModel sessionAudioPathViewModel) {
                this.this$0 = sessionAudioPathViewModel;
            }

            /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(java.util.List r7, kotlin.coroutines.Continuation r8) {
                /*
                    r6 = this;
                    boolean r0 = r8 instanceof com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8$emit$1
                    if (r0 == 0) goto L13
                    r0 = r8
                    com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8$emit$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8$emit$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$8$emit$1
                    r0.<init>(r6, r8)
                L18:
                    java.lang.Object r8 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    if (r2 == 0) goto L33
                    if (r2 != r3) goto L2b
                    java.lang.Object r6 = r0.L$0
                    java.lang.Iterable r6 = (java.lang.Iterable) r6
                    kotlin.ResultKt.throwOnFailure(r8)
                    goto L69
                L2b:
                    java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                    java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                    r6.<init>(r7)
                    throw r6
                L33:
                    kotlin.ResultKt.throwOnFailure(r8)
                    java.lang.String r8 = "SessionAudioPathViewModel"
                    java.lang.String r2 = "list updated"
                    android.util.Log.d(r8, r2)
                    java.lang.Iterable r7 = (java.lang.Iterable) r7
                    java.util.Iterator r2 = r7.iterator()
                L43:
                    boolean r4 = r2.hasNext()
                    if (r4 == 0) goto L55
                    java.lang.Object r4 = r2.next()
                    com.android.systemui.media.mediaoutput.entity.AudioDevice r4 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r4
                    java.lang.String r5 = "\t"
                    com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController$$ExternalSyntheticOutline0.m(r5, r4, r8)
                    goto L43
                L55:
                    r8 = r7
                    java.util.List r8 = (java.util.List) r8
                    com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel r6 = r6.this$0
                    kotlinx.coroutines.flow.StateFlowImpl r6 = r6._audioDevices
                    r0.L$0 = r7
                    r0.label = r3
                    r7 = 0
                    r6.updateState(r7, r8)
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    if (r6 != r1) goto L69
                    return r1
                L69:
                    kotlin.Unit r6 = kotlin.Unit.INSTANCE
                    return r6
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.AnonymousClass4.AnonymousClass8.emit(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
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

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new List[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
                MediaOutputConst.INSTANCE.getClass();
                final Flow m3462debounceHG0u8IE = FlowKt.m3462debounceHG0u8IE(flow, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final Flow flow2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                            /*
                                r9 = this;
                                boolean r0 = r11 instanceof com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r11
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r11)
                            L18:
                                java.lang.Object r11 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r11)
                                goto L7d
                            L27:
                                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                                r9.<init>(r10)
                                throw r9
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r11)
                                java.util.List[] r10 = (java.util.List[]) r10
                                java.util.ArrayList r11 = new java.util.ArrayList
                                int r2 = r10.length
                                r11.<init>(r2)
                                int r2 = r10.length
                                r4 = 0
                                r5 = r4
                            L3d:
                                if (r5 >= r2) goto L6c
                                r6 = r10[r5]
                                java.lang.Iterable r6 = (java.lang.Iterable) r6
                                java.util.ArrayList r7 = new java.util.ArrayList
                                r8 = 10
                                int r8 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r6, r8)
                                r7.<init>(r8)
                                java.util.Iterator r6 = r6.iterator()
                            L52:
                                boolean r8 = r6.hasNext()
                                if (r8 == 0) goto L66
                                java.lang.Object r8 = r6.next()
                                com.android.systemui.media.mediaoutput.entity.AudioDevice r8 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r8
                                com.android.systemui.media.mediaoutput.entity.AudioDevice r8 = r8.clone()
                                r7.add(r8)
                                goto L52
                            L66:
                                r11.add(r7)
                                int r5 = r5 + 1
                                goto L3d
                            L6c:
                                java.util.List[] r10 = new java.util.List[r4]
                                java.lang.Object[] r10 = r11.toArray(r10)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                                java.lang.Object r9 = r9.emit(r10, r0)
                                if (r9 != r1) goto L7d
                                return r1
                            L7d:
                                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                                return r9
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                final Flow flow3 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Code restructure failed: missing block: B:260:0x05a2, code lost:
                        
                            if (r3 != 0) goto L314;
                         */
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                        /* JADX WARN: Removed duplicated region for block: B:287:0x0788  */
                        /* JADX WARN: Removed duplicated region for block: B:289:0x078b  */
                        /* JADX WARN: Removed duplicated region for block: B:294:0x07b4  */
                        /* JADX WARN: Removed duplicated region for block: B:483:0x0b8a  */
                        /* JADX WARN: Removed duplicated region for block: B:524:0x0795  */
                        /* JADX WARN: Removed duplicated region for block: B:642:0x031e  */
                        /* JADX WARN: Removed duplicated region for block: B:647:0x032e  */
                        /* JADX WARN: Removed duplicated region for block: B:649:0x0321  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r34, kotlin.coroutines.Continuation r35) {
                            /*
                                Method dump skipped, instructions count: 3151
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, sessionAudioPathViewModel, coroutineScope), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final Flow flow4 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L48
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                r6 = r5
                                java.util.List r6 = (java.util.List) r6
                                java.util.Collection r6 = (java.util.Collection) r6
                                boolean r6 = r6.isEmpty()
                                if (r6 != 0) goto L48
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L48
                                return r1
                            L48:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final SessionAudioPathViewModel sessionAudioPathViewModel2 = SessionAudioPathViewModel.this;
                final Flow flow5 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L73
                            L27:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r7)
                                r7 = r6
                                java.util.List r7 = (java.util.List) r7
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel r2 = r5.this$0
                                boolean r2 = r2.isBroadcasting()
                                if (r2 != 0) goto L68
                                java.lang.Iterable r7 = (java.lang.Iterable) r7
                                boolean r2 = r7 instanceof java.util.Collection
                                if (r2 == 0) goto L4d
                                r2 = r7
                                java.util.Collection r2 = (java.util.Collection) r2
                                boolean r2 = r2.isEmpty()
                                if (r2 == 0) goto L4d
                                goto L73
                            L4d:
                                java.util.Iterator r7 = r7.iterator()
                            L51:
                                boolean r2 = r7.hasNext()
                                if (r2 == 0) goto L73
                                java.lang.Object r2 = r7.next()
                                com.android.systemui.media.mediaoutput.entity.AudioDevice r2 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r2
                                com.android.systemui.media.mediaoutput.entity.AudioDeviceExt r4 = com.android.systemui.media.mediaoutput.entity.AudioDeviceExt.INSTANCE
                                r4.getClass()
                                boolean r2 = com.android.systemui.media.mediaoutput.entity.AudioDeviceExt.isActive(r2)
                                if (r2 == 0) goto L51
                            L68:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r6, r0)
                                if (r5 != r1) goto L73
                                return r1
                            L73:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, sessionAudioPathViewModel2), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                Flow flow6 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                            /*
                                r7 = this;
                                boolean r0 = r9 instanceof com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r9
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3$2$1
                                r0.<init>(r9)
                            L18:
                                java.lang.Object r9 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L30
                                if (r2 != r3) goto L28
                                kotlin.ResultKt.throwOnFailure(r9)
                                goto Lc1
                            L28:
                                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                                r7.<init>(r8)
                                throw r7
                            L30:
                                kotlin.ResultKt.throwOnFailure(r9)
                                java.util.List r8 = (java.util.List) r8
                                java.lang.Iterable r8 = (java.lang.Iterable) r8
                                java.util.LinkedHashMap r9 = new java.util.LinkedHashMap
                                r9.<init>()
                                java.util.Iterator r2 = r8.iterator()
                            L40:
                                boolean r4 = r2.hasNext()
                                if (r4 == 0) goto L65
                                java.lang.Object r4 = r2.next()
                                r5 = r4
                                com.android.systemui.media.mediaoutput.entity.AudioDevice r5 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r5
                                java.lang.String r5 = r5.getId()
                                java.lang.Object r6 = r9.get(r5)
                                if (r6 != 0) goto L5f
                                java.util.ArrayList r6 = new java.util.ArrayList
                                r6.<init>()
                                r9.put(r5, r6)
                            L5f:
                                java.util.List r6 = (java.util.List) r6
                                r6.add(r4)
                                goto L40
                            L65:
                                java.util.Collection r9 = r9.values()
                                java.lang.Iterable r9 = (java.lang.Iterable) r9
                                java.util.ArrayList r2 = new java.util.ArrayList
                                r2.<init>()
                                java.util.Iterator r9 = r9.iterator()
                            L74:
                                boolean r4 = r9.hasNext()
                                if (r4 == 0) goto L8c
                                java.lang.Object r4 = r9.next()
                                java.util.List r4 = (java.util.List) r4
                                java.lang.Object r4 = kotlin.collections.CollectionsKt___CollectionsKt.firstOrNull(r4)
                                com.android.systemui.media.mediaoutput.entity.AudioDevice r4 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r4
                                if (r4 == 0) goto L74
                                r2.add(r4)
                                goto L74
                            L8c:
                                java.util.Set r9 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
                                java.lang.Iterable r9 = (java.lang.Iterable) r9
                                java.util.List r8 = kotlin.collections.CollectionsKt___CollectionsKt.minus(r8, r9)
                                r9 = r8
                                java.util.Collection r9 = (java.util.Collection) r9
                                boolean r9 = r9.isEmpty()
                                if (r9 != 0) goto La0
                                goto La1
                            La0:
                                r8 = 0
                            La1:
                                if (r8 == 0) goto Lb6
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                java.lang.String r4 = "remove duplicated devices - "
                                r9.<init>(r4)
                                r9.append(r8)
                                java.lang.String r8 = r9.toString()
                                java.lang.String r9 = "SessionAudioPathViewModel"
                                android.util.Log.d(r9, r8)
                            Lb6:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                                java.lang.Object r7 = r7.emit(r2, r0)
                                if (r7 != r1) goto Lc1
                                return r1
                            Lc1:
                                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                                return r7
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            Object obj2;
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
                    obj2 = null;
                    break;
                }
                obj2 = it.next();
                AudioDeviceExt.INSTANCE.getClass();
                if (AudioDeviceExt.isActive((AudioDevice) obj2)) {
                    break;
                }
            }
            AudioDevice audioDevice = (AudioDevice) obj2;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    BuiltInDevice copy$default = BuiltInDevice.copy$default((BuiltInDevice) audioDevice, null, null, 0, state, 383);
                    copy$default.deepCopy((BuiltInDevice) audioDevice);
                    musicShareDevice = copy$default;
                } else if (audioDevice instanceof BluetoothDevice) {
                    BluetoothDevice copy$default2 = BluetoothDevice.copy$default((BluetoothDevice) audioDevice, null, null, 0, state, false, 1919);
                    copy$default2.deepCopy((BluetoothDevice) audioDevice);
                    musicShareDevice = copy$default2;
                } else if (audioDevice instanceof MusicShareDevice) {
                    State state2 = state;
                    MusicShareDevice copy$default3 = MusicShareDevice.copy$default((MusicShareDevice) audioDevice, null, 0, state2, false, 1919);
                    state = state2;
                    copy$default3.deepCopy((MusicShareDevice) audioDevice);
                    musicShareDevice = copy$default3;
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

    /* JADX WARN: Code restructure failed: missing block: B:11:0x00a5, code lost:
    
        if ((r7.intValue() == 0 ? null : r7) == null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SessionAudioPathViewModel(android.content.Context r4, javax.inject.Provider r5, javax.inject.Provider r6, javax.inject.Provider r7, javax.inject.Provider r8, javax.inject.Provider r9, javax.inject.Provider r10, javax.inject.Provider r11, javax.inject.Provider r12, com.android.systemui.media.mediaoutput.controller.device.AuracastDeviceController r13, javax.inject.Provider r14, androidx.datastore.core.DataStore r15, javax.inject.Provider r16, javax.inject.Provider r17, androidx.lifecycle.SavedStateHandle r18) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.<init>(android.content.Context, javax.inject.Provider, javax.inject.Provider, javax.inject.Provider, javax.inject.Provider, javax.inject.Provider, javax.inject.Provider, javax.inject.Provider, javax.inject.Provider, com.android.systemui.media.mediaoutput.controller.device.AuracastDeviceController, javax.inject.Provider, androidx.datastore.core.DataStore, javax.inject.Provider, javax.inject.Provider, androidx.lifecycle.SavedStateHandle):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x012e, code lost:
    
        if (r7.select(r13, r0) == r1) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0147, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0145, code lost:
    
        if (r7.deselect(r15, r0) == r1) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$checkForBudsTogether(com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel r12, com.android.systemui.media.mediaoutput.entity.AudioDevice r13, boolean r14, kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.access$checkForBudsTogether(com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel, com.android.systemui.media.mediaoutput.entity.AudioDevice, boolean, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("SessionAudioPathViewModel", "adjustVolume() - " + audioDevice + ", " + i);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, null, new SessionAudioPathViewModel$adjustVolume$1(this, audioDevice, i, null), 2);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void cancel(AudioDevice audioDevice) {
        Log.d("SessionAudioPathViewModel", "cancel() - " + audioDevice);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, null, new SessionAudioPathViewModel$cancel$1(this, audioDevice, null), 2);
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
        this.eventJob = CoroutineExtKt.durationLaunch(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, new SessionAudioPathViewModel$deselect$4(this, audioDevice, null));
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final ReadonlyStateFlow getAudioDevices() {
        return this.audioDevices;
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void goToApp(Intent intent) {
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SessionAudioPathViewModel$goToApp$1(this, intent, null), 3);
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
        this.eventJob = CoroutineExtKt.durationLaunch(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, new SessionAudioPathViewModel$select$4(this, audioDevice, null));
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
        this.eventJob = CoroutineExtKt.durationLaunch(androidx.lifecycle.ViewModelKt.getViewModelScope(this), Dispatchers.Default, new SessionAudioPathViewModel$transfer$4(audioDevice, this, null));
    }
}
