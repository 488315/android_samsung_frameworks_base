package com.android.systemui.media.nearby;

import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.os.IBinder;
import android.os.RemoteException;
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
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class NearbyMediaDevicesManager implements CoreStartable {
    public final CommandQueue commandQueue;
    public final NearbyMediaDevicesLogger logger;
    public final List providers = new ArrayList();
    public final List activeCallbacks = new ArrayList();
    public final NearbyMediaDevicesManager$commandQueueCallbacks$1 commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$commandQueueCallbacks$1
        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
            NearbyMediaDevicesManager nearbyMediaDevicesManager = this.this$0;
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
            NearbyMediaDevicesLogger$$ExternalSyntheticLambda0 nearbyMediaDevicesLogger$$ExternalSyntheticLambda0 = new NearbyMediaDevicesLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = nearbyMediaDevicesLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = size;
            logBuffer.commit(logMessageObtain);
            iNearbyMediaDevicesProvider.asBinder().linkToDeath(nearbyMediaDevicesManager.deathRecipient, 0);
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
            NearbyMediaDevicesManager nearbyMediaDevicesManager = this.this$0;
            if (((ArrayList) nearbyMediaDevicesManager.providers).remove(iNearbyMediaDevicesProvider)) {
                NearbyMediaDevicesLogger nearbyMediaDevicesLogger = nearbyMediaDevicesManager.logger;
                int size = ((ArrayList) nearbyMediaDevicesManager.providers).size();
                nearbyMediaDevicesLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                NearbyMediaDevicesLogger$$ExternalSyntheticLambda0 nearbyMediaDevicesLogger$$ExternalSyntheticLambda0 = new NearbyMediaDevicesLogger$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = nearbyMediaDevicesLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).int1 = size;
                logBuffer.commit(logMessageObtain);
            }
        }
    };
    public final NearbyMediaDevicesManager$deathRecipient$1 deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied(IBinder iBinder) {
            NearbyMediaDevicesManager nearbyMediaDevicesManager = this.this$0;
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
                            NearbyMediaDevicesLogger$$ExternalSyntheticLambda0 nearbyMediaDevicesLogger$$ExternalSyntheticLambda0 = new NearbyMediaDevicesLogger$$ExternalSyntheticLambda0(0);
                            LogBuffer logBuffer = nearbyMediaDevicesLogger.buffer;
                            LogMessage logMessageObtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).int1 = size2;
                            logBuffer.commit(logMessageObtain);
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
