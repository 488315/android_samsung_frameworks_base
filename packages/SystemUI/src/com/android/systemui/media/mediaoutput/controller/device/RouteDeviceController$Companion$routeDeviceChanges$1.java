package com.android.systemui.media.mediaoutput.controller.device;

import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.concurrent.Executors;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class RouteDeviceController$Companion$routeDeviceChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaRouter2Manager $this_routeDeviceChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RouteDeviceController$Companion$routeDeviceChanges$1(MediaRouter2Manager mediaRouter2Manager, Continuation continuation) {
        super(2, continuation);
        this.$this_routeDeviceChanges = mediaRouter2Manager;
    }

    public static final Job invokeSuspend$updateRoutes(ProducerScope producerScope, MediaRouter2Manager mediaRouter2Manager) {
        return BuildersKt.launch$default(producerScope, null, null, new RouteDeviceController$Companion$routeDeviceChanges$1$updateRoutes$1(producerScope, mediaRouter2Manager, null), 3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RouteDeviceController$Companion$routeDeviceChanges$1 routeDeviceController$Companion$routeDeviceChanges$1 = new RouteDeviceController$Companion$routeDeviceChanges$1(this.$this_routeDeviceChanges, continuation);
        routeDeviceController$Companion$routeDeviceChanges$1.L$0 = obj;
        return routeDeviceController$Companion$routeDeviceChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RouteDeviceController$Companion$routeDeviceChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.MediaRouter2Manager$Callback, com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$Companion$routeDeviceChanges$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaRouter2Manager mediaRouter2Manager = this.$this_routeDeviceChanges;
            final ?? r1 = new MediaRouter2Manager.Callback() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$Companion$routeDeviceChanges$1$callback$1
                public final void onPreferredFeaturesChanged(String str, List list) {
                    Log.d("RouteDeviceController", "onPreferredFeaturesChanged() - " + str + ", " + list);
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onRequestFailed(int i2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onRequestFailed() - ", "RouteDeviceController");
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onRouteListingPreferenceUpdated(String str, RouteListingPreference routeListingPreference) {
                    Log.d("RouteDeviceController", "onRouteListingPreferenceUpdated() - " + str + ", " + routeListingPreference);
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onRoutesUpdated() {
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onSessionReleased(RoutingSessionInfo routingSessionInfo) {
                    Log.d("RouteDeviceController", "onSessionReleased() - " + routingSessionInfo);
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
                    Log.d("RouteDeviceController", "onSessionUpdated() - " + routingSessionInfo);
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
                    Log.d("RouteDeviceController", "onTransferFailed() - " + routingSessionInfo + " - " + mediaRoute2Info);
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }

                public final void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
                    Log.d("RouteDeviceController", "onTransferred() - " + routingSessionInfo2);
                    RouteDeviceController$Companion$routeDeviceChanges$1.invokeSuspend$updateRoutes(ProducerScope.this, mediaRouter2Manager);
                }
            };
            invokeSuspend$updateRoutes(producerScope, this.$this_routeDeviceChanges);
            this.$this_routeDeviceChanges.registerCallback(Executors.newSingleThreadExecutor(), (MediaRouter2Manager.Callback) r1);
            this.$this_routeDeviceChanges.registerScanRequest();
            final MediaRouter2Manager mediaRouter2Manager2 = this.$this_routeDeviceChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$Companion$routeDeviceChanges$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("RouteDeviceController", "unregisterScanRequest()");
                    mediaRouter2Manager2.unregisterScanRequest();
                    mediaRouter2Manager2.unregisterCallback(r1);
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
