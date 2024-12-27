package com.android.systemui.media.nearby;

import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NearbyMediaDevicesManager implements CoreStartable {
    public final CommandQueue commandQueue;
    public final NearbyMediaDevicesLogger logger;
    public final List providers = new ArrayList();
    public final List activeCallbacks = new ArrayList();
    public final NearbyMediaDevicesManager$commandQueueCallbacks$1 commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$commandQueueCallbacks$1
        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
            NearbyMediaDevicesManager nearbyMediaDevicesManager = NearbyMediaDevicesManager.this;
            if (((ArrayList) nearbyMediaDevicesManager.providers).contains(iNearbyMediaDevicesProvider)) {
                return;
            }
            Iterator it = nearbyMediaDevicesManager.activeCallbacks.iterator();
            while (it.hasNext()) {
                iNearbyMediaDevicesProvider.registerNearbyDevicesCallback((INearbyMediaDevicesUpdateCallback) it.next());
            }
            ((ArrayList) nearbyMediaDevicesManager.providers).add(iNearbyMediaDevicesProvider);
            NearbyMediaDevicesLogger nearbyMediaDevicesLogger = nearbyMediaDevicesManager.logger;
            int size = ((ArrayList) nearbyMediaDevicesManager.providers).size();
            nearbyMediaDevicesLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            NearbyMediaDevicesLogger$logProviderRegistered$2 nearbyMediaDevicesLogger$logProviderRegistered$2 = new Function1() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderRegistered$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Provider registered; total providers = ");
                }
            };
            LogBuffer logBuffer = nearbyMediaDevicesLogger.buffer;
            LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$logProviderRegistered$2, null);
            ((LogMessageImpl) obtain).int1 = size;
            logBuffer.commit(obtain);
            iNearbyMediaDevicesProvider.asBinder().linkToDeath(nearbyMediaDevicesManager.deathRecipient, 0);
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
            NearbyMediaDevicesManager nearbyMediaDevicesManager = NearbyMediaDevicesManager.this;
            if (((ArrayList) nearbyMediaDevicesManager.providers).remove(iNearbyMediaDevicesProvider)) {
                NearbyMediaDevicesLogger nearbyMediaDevicesLogger = nearbyMediaDevicesManager.logger;
                int size = ((ArrayList) nearbyMediaDevicesManager.providers).size();
                nearbyMediaDevicesLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                NearbyMediaDevicesLogger$logProviderUnregistered$2 nearbyMediaDevicesLogger$logProviderUnregistered$2 = new Function1() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderUnregistered$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Provider unregistered; total providers = ");
                    }
                };
                LogBuffer logBuffer = nearbyMediaDevicesLogger.buffer;
                LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$logProviderUnregistered$2, null);
                ((LogMessageImpl) obtain).int1 = size;
                logBuffer.commit(obtain);
            }
        }
    };
    public final NearbyMediaDevicesManager$deathRecipient$1 deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied(IBinder iBinder) {
            NearbyMediaDevicesManager nearbyMediaDevicesManager = NearbyMediaDevicesManager.this;
            synchronized (nearbyMediaDevicesManager.providers) {
                try {
                    int size = ((ArrayList) nearbyMediaDevicesManager.providers).size() - 1;
                    while (true) {
                        if (-1 >= size) {
                            break;
                        }
                        if (Intrinsics.areEqual(((INearbyMediaDevicesProvider) ((ArrayList) nearbyMediaDevicesManager.providers).get(size)).asBinder(), iBinder)) {
                            ((ArrayList) nearbyMediaDevicesManager.providers).remove(size);
                            NearbyMediaDevicesLogger nearbyMediaDevicesLogger = nearbyMediaDevicesManager.logger;
                            int size2 = ((ArrayList) nearbyMediaDevicesManager.providers).size();
                            nearbyMediaDevicesLogger.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            NearbyMediaDevicesLogger$logProviderBinderDied$2 nearbyMediaDevicesLogger$logProviderBinderDied$2 = new Function1() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderBinderDied$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Provider binder died; total providers = ");
                                }
                            };
                            LogBuffer logBuffer = nearbyMediaDevicesLogger.buffer;
                            LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$logProviderBinderDied$2, null);
                            ((LogMessageImpl) obtain).int1 = size2;
                            logBuffer.commit(obtain);
                            break;
                        }
                        size--;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.media.nearby.NearbyMediaDevicesManager$commandQueueCallbacks$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1] */
    public NearbyMediaDevicesManager(CommandQueue commandQueue, NearbyMediaDevicesLogger nearbyMediaDevicesLogger) {
        this.commandQueue = commandQueue;
        this.logger = nearbyMediaDevicesLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallbacks);
    }
}
