package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper$registerDisplayVolumeListener$1;
import com.android.systemui.volume.util.SystemServiceExtension;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

final class WifiDisplayDeviceController$Companion$activeVolumeChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DisplayManagerWrapper $this_activeVolumeChanges;
    private /* synthetic */ Object L$0;
    int label;

    public WifiDisplayDeviceController$Companion$activeVolumeChanges$1(DisplayManagerWrapper displayManagerWrapper, Continuation continuation) {
        super(2, continuation);
        this.$this_activeVolumeChanges = displayManagerWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiDisplayDeviceController$Companion$activeVolumeChanges$1 wifiDisplayDeviceController$Companion$activeVolumeChanges$1 = new WifiDisplayDeviceController$Companion$activeVolumeChanges$1(this.$this_activeVolumeChanges, continuation);
        wifiDisplayDeviceController$Companion$activeVolumeChanges$1.L$0 = obj;
        return wifiDisplayDeviceController$Companion$activeVolumeChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiDisplayDeviceController$Companion$activeVolumeChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DisplayManagerWrapper displayManagerWrapper = this.$this_activeVolumeChanges;
            Consumer consumer = new Consumer() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$listener$1

                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$listener$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    final /* synthetic */ ProducerScope $$this$callbackFlow;
                    int label;

                    public AnonymousClass1(ProducerScope producerScope, Continuation continuation) {
                        super(2, continuation);
                        this.$$this$callbackFlow = producerScope;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.$$this$callbackFlow, continuation);
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
                            SendChannel sendChannel = this.$$this$callbackFlow;
                            Unit unit = Unit.INSTANCE;
                            this.label = 1;
                            if (((ChannelCoroutine) sendChannel)._channel.send(unit, this) == coroutineSingletons) {
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

                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    DisplayManagerWrapper displayManagerWrapper2 = DisplayManagerWrapper.this;
                    SuggestionsAdapter$$ExternalSyntheticOutline0.m(displayManagerWrapper2.displayCurrentVolume, displayManagerWrapper2.getDisplayMaxVolume(), "volume = ", ", volumeMax = ", "WifiDisplayDeviceController");
                    ProducerScope producerScope2 = producerScope;
                    BuildersKt.launch$default(producerScope2, null, null, new AnonymousClass1(producerScope2, null), 3);
                }
            };
            DisplayManagerWrapper displayManagerWrapper2 = this.$this_activeVolumeChanges;
            displayManagerWrapper2.getClass();
            DisplayManagerWrapper$registerDisplayVolumeListener$1 displayManagerWrapper$registerDisplayVolumeListener$1 = new DisplayManagerWrapper$registerDisplayVolumeListener$1(displayManagerWrapper2, consumer);
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = displayManagerWrapper2.context;
            systemServiceExtension.getClass();
            SystemServiceExtension.getDisplayManager(context).semRegisterDisplayVolumeListener(displayManagerWrapper$registerDisplayVolumeListener$1, new Handler());
            displayManagerWrapper2.displayVolumeListener = displayManagerWrapper$registerDisplayVolumeListener$1;
            final DisplayManagerWrapper displayManagerWrapper3 = this.$this_activeVolumeChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("WifiDisplayDeviceController", "unregisterDisplayVolumeListener");
                    DisplayManagerWrapper displayManagerWrapper4 = DisplayManagerWrapper.this;
                    if (displayManagerWrapper4.displayVolumeListener != null) {
                        SystemServiceExtension systemServiceExtension2 = SystemServiceExtension.INSTANCE;
                        Context context2 = displayManagerWrapper4.context;
                        systemServiceExtension2.getClass();
                        SystemServiceExtension.getDisplayManager(context2).semUnregisterDisplayVolumeListener(displayManagerWrapper4.displayVolumeListener);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
