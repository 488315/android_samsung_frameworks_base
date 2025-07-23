package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper$registerDisplayVolumeListener$1;
import com.android.systemui.volume.util.SystemServiceExtension;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WifiDisplayDeviceController$Companion$activeVolumeChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DisplayManagerWrapper $this_activeVolumeChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Consumer<Boolean> $listener;
        final /* synthetic */ DisplayManagerWrapper $this_activeVolumeChanges;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(DisplayManagerWrapper displayManagerWrapper, Consumer<Boolean> consumer, Continuation continuation) {
            super(2, continuation);
            this.$this_activeVolumeChanges = displayManagerWrapper;
            this.$listener = consumer;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$this_activeVolumeChanges, this.$listener, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            DisplayManagerWrapper displayManagerWrapper = this.$this_activeVolumeChanges;
            Consumer<Boolean> consumer = this.$listener;
            displayManagerWrapper.getClass();
            DisplayManagerWrapper$registerDisplayVolumeListener$1 displayManagerWrapper$registerDisplayVolumeListener$1 = new DisplayManagerWrapper$registerDisplayVolumeListener$1(displayManagerWrapper, consumer);
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = displayManagerWrapper.context;
            systemServiceExtension.getClass();
            SystemServiceExtension.getDisplayManager(context).semRegisterDisplayVolumeListener(displayManagerWrapper$registerDisplayVolumeListener$1, new Handler());
            displayManagerWrapper.displayVolumeListener = displayManagerWrapper$registerDisplayVolumeListener$1;
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0055, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r4, r8) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0057, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0043, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r5, r6, r8) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r9)
            goto L58
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            java.lang.Object r1 = r8.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r9)
            goto L46
        L21:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            r1 = r9
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$listener$1 r9 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$listener$1
            com.android.systemui.volume.util.DisplayManagerWrapper r5 = r8.$this_activeVolumeChanges
            r9.<init>()
            kotlinx.coroutines.scheduling.DefaultScheduler r5 = kotlinx.coroutines.Dispatchers.Default
            kotlinx.coroutines.android.HandlerContext r5 = kotlinx.coroutines.internal.MainDispatcherLoader.dispatcher
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$1 r6 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$1
            com.android.systemui.volume.util.DisplayManagerWrapper r7 = r8.$this_activeVolumeChanges
            r6.<init>(r7, r9, r2)
            r8.L$0 = r1
            r8.label = r4
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r8)
            if (r9 != r0) goto L46
            goto L57
        L46:
            com.android.systemui.volume.util.DisplayManagerWrapper r9 = r8.$this_activeVolumeChanges
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1$$ExternalSyntheticLambda0
            r4.<init>()
            r8.L$0 = r2
            r8.label = r3
            java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r4, r8)
            if (r8 != r0) goto L58
        L57:
            return r0
        L58:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeVolumeChanges$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
