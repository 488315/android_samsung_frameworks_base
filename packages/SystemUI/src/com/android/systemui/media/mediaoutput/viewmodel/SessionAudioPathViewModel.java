package com.android.systemui.media.mediaoutput.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$8;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$9;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.controller.device.AuracastDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.ChromeCastDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.controller.device.DeviceController;
import com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.RemoteDeviceController;
import com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
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
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SessionAudioPathViewModel extends ViewModel implements AudioPathInteraction {
    public static final Companion Companion = new Companion(null);
    public final MutableLiveData _audioDevices;
    public final MutableLiveData audioDevices;
    public final AuracastDeviceController auracastDeviceController;
    public final Map controllerMap;
    public final DataStore dataStore;
    public boolean isCastingPriority;
    public boolean isSupportDisplayOnlyRemoteDevice = true;
    public boolean isSupportSpotifyChromecast;
    public final String packageName;
    public Job transferJob;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = SessionAudioPathViewModel.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$8 dataStoreDebugLabsExt$special$$inlined$map$8 = new DataStoreDebugLabsExt$special$$inlined$map$8(dataStore.getData());
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SessionAudioPathViewModel.this.isSupportSpotifyChromecast = ((Boolean) obj2).booleanValue();
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                DataStoreDebugLabsExt$special$$inlined$map$9 dataStoreDebugLabsExt$special$$inlined$map$9 = new DataStoreDebugLabsExt$special$$inlined$map$9(dataStore.getData());
                final SessionAudioPathViewModel sessionAudioPathViewModel = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SessionAudioPathViewModel.this.isSupportDisplayOnlyRemoteDevice = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$9.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

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
                final Flow debounce = FlowKt.debounce(new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$combine$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new List[flowArr2.length];
                            }
                        }, new AnonymousClass3(null), flowCollector, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                }, 50L);
                final Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                final Flow flow2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ CoroutineScope $$this$launch$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ SessionAudioPathViewModel this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                        /* JADX WARN: Removed duplicated region for block: B:306:0x0b51  */
                        /* JADX WARN: Removed duplicated region for block: B:308:0x0b54  */
                        /* JADX WARN: Removed duplicated region for block: B:311:0x0b5d  */
                        /* JADX WARN: Removed duplicated region for block: B:322:0x0be2 A[RETURN] */
                        /* JADX WARN: Removed duplicated region for block: B:327:0x0b95  */
                        /* JADX WARN: Removed duplicated region for block: B:331:0x0bb7  */
                        /* JADX WARN: Removed duplicated region for block: B:679:0x032c  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r28, kotlin.coroutines.Continuation r29) {
                            /*
                                Method dump skipped, instructions count: 3046
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
                Flow flow3 = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel$4$invokeSuspend$$inlined$map$3$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                                goto Lc2
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
                                r9 = r9 ^ r3
                                if (r9 == 0) goto La1
                                goto La2
                            La1:
                                r8 = 0
                            La2:
                                if (r8 == 0) goto Lb7
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                java.lang.String r4 = "remove duplicated devices - "
                                r9.<init>(r4)
                                r9.append(r8)
                                java.lang.String r8 = r9.toString()
                                java.lang.String r9 = "SessionAudioPathViewModel"
                                android.util.Log.d(r9, r8)
                            Lb7:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                                java.lang.Object r7 = r7.emit(r2, r0)
                                if (r7 != r1) goto Lc2
                                return r1
                            Lc2:
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
                final SessionAudioPathViewModel sessionAudioPathViewModel2 = SessionAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel.4.6
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Log.d("SessionAudioPathViewModel", "list updated");
                        List list = (List) obj2;
                        Iterator it2 = list.iterator();
                        while (it2.hasNext()) {
                            AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) it2.next(), "SessionAudioPathViewModel");
                        }
                        SessionAudioPathViewModel.this._audioDevices.postValue(list);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow3.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static final List access$changeInActiveState(Companion companion, List list) {
            AudioDevice audioDevice;
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
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                AudioDevice audioDevice2 = (AudioDevice) it.next();
                if (audioDevice2 instanceof BuiltInDevice) {
                    BuiltInDevice copy$default = BuiltInDevice.copy$default((BuiltInDevice) audioDevice2, null, 0, state, 383);
                    copy$default.deepCopy$1(audioDevice2);
                    audioDevice = copy$default;
                } else if (audioDevice2 instanceof BluetoothDevice) {
                    BluetoothDevice copy$default2 = BluetoothDevice.copy$default((BluetoothDevice) audioDevice2, null, null, 0, state, false, 1919);
                    copy$default2.deepCopy(audioDevice2);
                    audioDevice = copy$default2;
                } else if (audioDevice2 instanceof MusicShareDevice) {
                    MusicShareDevice copy$default3 = MusicShareDevice.copy$default((MusicShareDevice) audioDevice2, 0, state, false, 1919);
                    copy$default3.deepCopy$2(audioDevice2);
                    audioDevice = copy$default3;
                } else {
                    arrayList2.add(audioDevice2);
                }
                audioDevice2 = audioDevice;
                arrayList2.add(audioDevice2);
            }
            return arrayList2;
        }

        public static final void access$updateStateWithRouteDevices(Companion companion, List list, List list2, List list3) {
            Object obj;
            ControllerType controllerType;
            Object obj2;
            companion.getClass();
            ArrayList arrayList = new ArrayList();
            for (Object obj3 : list) {
                if (obj3 instanceof RouteDevice) {
                    arrayList.add(obj3);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (((RouteDevice) next).getMediaRoute2Info().isSystemRoute()) {
                    arrayList2.add(next);
                }
            }
            if (!(!arrayList2.isEmpty())) {
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it2.next();
                    AudioDeviceExt.INSTANCE.getClass();
                    if (AudioDeviceExt.isActive((RouteDevice) obj)) {
                        break;
                    }
                }
                RouteDevice routeDevice = (RouteDevice) obj;
                if (routeDevice == null || (controllerType = routeDevice.getControllerType()) == null) {
                    return;
                }
                Companion companion2 = SessionAudioPathViewModel.Companion;
                ArrayList arrayList3 = new ArrayList();
                for (Object obj4 : list2) {
                    if (obj4 instanceof BuiltInDevice) {
                        arrayList3.add(obj4);
                    }
                }
                ArrayList arrayList4 = new ArrayList();
                Iterator it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    Object next2 = it3.next();
                    arrayList4.add(next2);
                }
                Iterator it4 = arrayList4.iterator();
                while (it4.hasNext()) {
                    AudioDevice audioDevice = (AudioDevice) it4.next();
                    int indexOf = list2.indexOf(audioDevice);
                    BuiltInDevice builtInDevice = (BuiltInDevice) audioDevice;
                    builtInDevice.routingControllerType = controllerType;
                    BuiltInDevice copy$default = BuiltInDevice.copy$default(builtInDevice, null, 0, State.CONNECTED, 383);
                    copy$default.deepCopy$1(builtInDevice);
                    list2.set(indexOf, copy$default);
                }
                Companion companion3 = SessionAudioPathViewModel.Companion;
                ArrayList arrayList5 = new ArrayList();
                for (Object obj5 : list3) {
                    if (obj5 instanceof BluetoothDevice) {
                        arrayList5.add(obj5);
                    }
                }
                ArrayList arrayList6 = new ArrayList();
                Iterator it5 = arrayList5.iterator();
                while (it5.hasNext()) {
                    Object next3 = it5.next();
                    arrayList6.add(next3);
                }
                Iterator it6 = arrayList6.iterator();
                while (it6.hasNext()) {
                    AudioDevice audioDevice2 = (AudioDevice) it6.next();
                    int indexOf2 = list3.indexOf(audioDevice2);
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) audioDevice2;
                    bluetoothDevice.routingControllerType = controllerType;
                    BluetoothDevice copy$default2 = BluetoothDevice.copy$default(bluetoothDevice, null, null, 0, State.CONNECTED, false, 1663);
                    copy$default2.deepCopy(bluetoothDevice);
                    list3.set(indexOf2, copy$default2);
                }
                return;
            }
            Iterator it7 = arrayList2.iterator();
            while (true) {
                if (it7.hasNext()) {
                    obj2 = it7.next();
                    if (((RouteDevice) obj2).getMediaRoute2Info().getType() == 2) {
                        break;
                    }
                } else {
                    obj2 = null;
                    break;
                }
            }
            RouteDevice routeDevice2 = (RouteDevice) obj2;
            if (routeDevice2 != null) {
                Companion companion4 = SessionAudioPathViewModel.Companion;
                ArrayList arrayList7 = new ArrayList();
                for (Object obj6 : list2) {
                    if (obj6 instanceof BuiltInDevice) {
                        arrayList7.add(obj6);
                    }
                }
                ArrayList arrayList8 = new ArrayList();
                Iterator it8 = arrayList7.iterator();
                while (it8.hasNext()) {
                    Object next4 = it8.next();
                    arrayList8.add(next4);
                }
                Iterator it9 = arrayList8.iterator();
                while (it9.hasNext()) {
                    AudioDevice audioDevice3 = (AudioDevice) it9.next();
                    int indexOf3 = list2.indexOf(audioDevice3);
                    BuiltInDevice builtInDevice2 = (BuiltInDevice) audioDevice3;
                    list.remove(routeDevice2);
                    builtInDevice2.routeDevice = routeDevice2;
                    BuiltInDevice copy$default3 = BuiltInDevice.copy$default(builtInDevice2, null, 0, State.CONNECTED, 383);
                    copy$default3.deepCopy$1(builtInDevice2);
                    list2.set(indexOf3, copy$default3);
                }
            }
            ArrayList arrayList9 = new ArrayList();
            Iterator it10 = arrayList2.iterator();
            while (it10.hasNext()) {
                Object next5 = it10.next();
                RouteDevice routeDevice3 = (RouteDevice) next5;
                routeDevice3.getClass();
                if (CollectionsKt__CollectionsKt.listOf(8, 23, 26).contains(Integer.valueOf(routeDevice3.getMediaRoute2Info().getType()))) {
                    arrayList9.add(next5);
                }
            }
            Iterator it11 = arrayList9.iterator();
            while (it11.hasNext()) {
                RouteDevice routeDevice4 = (RouteDevice) it11.next();
                Companion companion5 = SessionAudioPathViewModel.Companion;
                ArrayList arrayList10 = new ArrayList();
                for (Object obj7 : list3) {
                    if (obj7 instanceof BluetoothDevice) {
                        arrayList10.add(obj7);
                    }
                }
                ArrayList arrayList11 = new ArrayList();
                Iterator it12 = arrayList10.iterator();
                while (it12.hasNext()) {
                    Object next6 = it12.next();
                    if (Intrinsics.areEqual(routeDevice4.getMediaRoute2Info().getAddress(), ((BluetoothDevice) next6).id)) {
                        arrayList11.add(next6);
                    }
                }
                Iterator it13 = arrayList11.iterator();
                while (it13.hasNext()) {
                    AudioDevice audioDevice4 = (AudioDevice) it13.next();
                    int indexOf4 = list3.indexOf(audioDevice4);
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) audioDevice4;
                    list.remove(routeDevice4);
                    bluetoothDevice2.routeDevice = routeDevice4;
                    BluetoothDevice copy$default4 = BluetoothDevice.copy$default(bluetoothDevice2, null, null, 0, State.CONNECTED, false, 1663);
                    copy$default4.deepCopy(bluetoothDevice2);
                    list3.set(indexOf4, copy$default4);
                }
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SessionAudioPathViewModel(Context context, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, AuracastDeviceController auracastDeviceController, Provider provider9, DataStore dataStore, SavedStateHandle savedStateHandle) {
        this.auracastDeviceController = auracastDeviceController;
        this.dataStore = dataStore;
        Map mutableMapOf = MapsKt__MapsKt.mutableMapOf(new Pair(ControllerType.BuiltIn, provider.get()), new Pair(ControllerType.Disconnected, provider5.get()));
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            mutableMapOf.put(ControllerType.MusicShare, provider6.get());
        }
        WifiDisplayDeviceController wifiDisplayDeviceController = (WifiDisplayDeviceController) provider7.get();
        mutableMapOf.put(ControllerType.SmartView, wifiDisplayDeviceController);
        mutableMapOf.put(ControllerType.Dex, wifiDisplayDeviceController);
        BluetoothDeviceController bluetoothDeviceController = (BluetoothDeviceController) provider2.get();
        mutableMapOf.put(ControllerType.Bluetooth, bluetoothDeviceController);
        mutableMapOf.put(ControllerType.BluetoothGroup, bluetoothDeviceController);
        ChromeCastDeviceController chromeCastDeviceController = (ChromeCastDeviceController) provider3.get();
        ControllerType controllerType = ControllerType.ChromeCast;
        mutableMapOf.put(controllerType, chromeCastDeviceController);
        mutableMapOf.put(ControllerType.ChromeCastGroup, chromeCastDeviceController);
        AudioMirroringDeviceController audioMirroringDeviceController = (AudioMirroringDeviceController) provider4.get();
        ControllerType controllerType2 = ControllerType.AudioMirroring;
        mutableMapOf.put(controllerType2, audioMirroringDeviceController);
        mutableMapOf.put(ControllerType.AudioMirroringGroup, audioMirroringDeviceController);
        RemoteDeviceController remoteDeviceController = (RemoteDeviceController) provider9.get();
        ControllerType controllerType3 = ControllerType.Remote;
        mutableMapOf.put(controllerType3, remoteDeviceController);
        this.controllerMap = mutableMapOf;
        MutableLiveData mutableLiveData = new MutableLiveData();
        this._audioDevices = mutableLiveData;
        this.audioDevices = mutableLiveData;
        Log.d("SessionAudioPathViewModel", "init()");
        String str = (savedStateHandle == null || (str = (String) savedStateHandle.get("packageName")) == null) ? "" : str;
        if (!Intrinsics.areEqual(this.packageName, str)) {
            MWBixbyController$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "SessionAudioPathViewModel");
            this.packageName = str;
            LinkedHashMap linkedHashMap = (LinkedHashMap) mutableMapOf;
            DeviceController deviceController = (DeviceController) linkedHashMap.get(controllerType);
            ChromeCastDeviceController chromeCastDeviceController2 = deviceController instanceof ChromeCastDeviceController ? (ChromeCastDeviceController) deviceController : null;
            if (chromeCastDeviceController2 != null) {
                chromeCastDeviceController2.setPackageName(str);
            }
            DeviceController deviceController2 = (DeviceController) linkedHashMap.get(controllerType2);
            AudioMirroringDeviceController audioMirroringDeviceController2 = deviceController2 instanceof AudioMirroringDeviceController ? (AudioMirroringDeviceController) deviceController2 : null;
            if (audioMirroringDeviceController2 != null) {
                audioMirroringDeviceController2.setPackageName(str);
            }
            DeviceController deviceController3 = (DeviceController) linkedHashMap.get(controllerType3);
            RemoteDeviceController remoteDeviceController2 = deviceController3 instanceof RemoteDeviceController ? (RemoteDeviceController) deviceController3 : null;
            if (remoteDeviceController2 != null) {
                remoteDeviceController2.setPackageName(str);
            }
        }
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass3(null), 3);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass4(null), 3);
    }

    public static final boolean access$checkForBudsTogether(SessionAudioPathViewModel sessionAudioPathViewModel, AudioDevice audioDevice, boolean z) {
        List list;
        sessionAudioPathViewModel.getClass();
        if (!(audioDevice instanceof BluetoothDevice ? true : audioDevice instanceof MusicShareDevice) || (list = (List) sessionAudioPathViewModel._audioDevices.getValue()) == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            AudioDeviceExt.INSTANCE.getClass();
            int i = AudioDeviceExt.WhenMappings.$EnumSwitchMapping$0[((AudioDevice) obj).getState().ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof BluetoothDevice) {
                arrayList2.add(next);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next2 = it2.next();
            if (next2 instanceof MusicShareDevice) {
                arrayList3.add(next2);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        Boolean bool = null;
        if (!(((List) pair.getFirst()).size() == 1 && ((List) pair.getSecond()).size() == 1)) {
            pair = null;
        }
        if (pair == null) {
            return false;
        }
        Pair pair2 = new Pair(CollectionsKt___CollectionsKt.first((List) pair.getFirst()), CollectionsKt___CollectionsKt.first((List) pair.getSecond()));
        AudioDevice audioDevice2 = (BluetoothDevice) pair2.component1();
        AudioDevice audioDevice3 = (MusicShareDevice) pair2.component2();
        DeviceController deviceController = (DeviceController) ((LinkedHashMap) sessionAudioPathViewModel.controllerMap).get(audioDevice.getFinalControllerType());
        if (deviceController != null) {
            if (z) {
                deviceController.select(audioDevice);
            } else {
                if (Intrinsics.areEqual(audioDevice2, audioDevice)) {
                    audioDevice2 = audioDevice3;
                }
                deviceController.deselect(audioDevice2);
            }
        }
        DeviceController deviceController2 = (DeviceController) ((LinkedHashMap) sessionAudioPathViewModel.controllerMap).get(ControllerType.MusicShare);
        if (deviceController2 != null) {
            MusicShareDeviceController musicShareDeviceController = deviceController2 instanceof MusicShareDeviceController ? (MusicShareDeviceController) deviceController2 : null;
            if (musicShareDeviceController != null) {
                musicShareDeviceController.setAudioSharingEnabled(z);
                bool = Boolean.TRUE;
            }
        }
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("SessionAudioPathViewModel", "adjustVolume() - " + audioDevice + ", " + i);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SessionAudioPathViewModel$adjustVolume$1(this, audioDevice, i, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void cancel(AudioDevice audioDevice) {
        Log.d("SessionAudioPathViewModel", "cancel() - " + audioDevice);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SessionAudioPathViewModel$cancel$1(this, audioDevice, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void deselect(AudioDevice audioDevice) {
        Log.d("SessionAudioPathViewModel", "deselect() - " + audioDevice);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SessionAudioPathViewModel$deselect$1(this, audioDevice, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final MutableLiveData getAudioDevices() {
        return this.audioDevices;
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final boolean isBroadcasting() {
        return AudioManagerExtKt.isBroadcasting(this.auracastDeviceController.audioManager);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("SessionAudioPathViewModel", "onCleared()");
        Iterator it = CollectionsKt___CollectionsKt.toSet(this.controllerMap.values()).iterator();
        while (it.hasNext()) {
            ((DeviceController) it.next()).close();
        }
        this.auracastDeviceController.close();
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void select(AudioDevice audioDevice) {
        Log.d("SessionAudioPathViewModel", "select() - " + audioDevice);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SessionAudioPathViewModel$select$1(this, audioDevice, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void transfer(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "SessionAudioPathViewModel");
        Job job = this.transferJob;
        if (job != null) {
            if (!job.isActive()) {
                job = null;
            }
            if (job != null) {
                Log.e("SessionAudioPathViewModel", "transfer() - blocked");
                return;
            }
        }
        this.transferJob = BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new SessionAudioPathViewModel$transfer$3(this, audioDevice, null), 3);
    }
}
