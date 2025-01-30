package com.android.systemui.media.nearby;

import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.os.IBinder;
import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NearbyMediaDevicesManager {
    public final NearbyMediaDevicesManager$deathRecipient$1 deathRecipient;
    public final NearbyMediaDevicesLogger logger;
    public final List providers = new ArrayList();
    public final List activeCallbacks = new ArrayList();

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1] */
    public NearbyMediaDevicesManager(CommandQueue commandQueue, NearbyMediaDevicesLogger nearbyMediaDevicesLogger) {
        this.logger = nearbyMediaDevicesLogger;
        CommandQueue.Callbacks callbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$commandQueueCallbacks$1
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
                NearbyMediaDevicesLogger nearbyMediaDevicesLogger2 = nearbyMediaDevicesManager.logger;
                int size = ((ArrayList) nearbyMediaDevicesManager.providers).size();
                nearbyMediaDevicesLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                NearbyMediaDevicesLogger$logProviderRegistered$2 nearbyMediaDevicesLogger$logProviderRegistered$2 = new Function1() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderRegistered$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return AbstractC0000x2c234b15.m0m("Provider registered; total providers = ", ((LogMessage) obj).getInt1());
                    }
                };
                LogBuffer logBuffer = nearbyMediaDevicesLogger2.buffer;
                LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$logProviderRegistered$2, null);
                obtain.setInt1(size);
                logBuffer.commit(obtain);
                iNearbyMediaDevicesProvider.asBinder().linkToDeath(nearbyMediaDevicesManager.deathRecipient, 0);
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
                NearbyMediaDevicesManager nearbyMediaDevicesManager = NearbyMediaDevicesManager.this;
                if (((ArrayList) nearbyMediaDevicesManager.providers).remove(iNearbyMediaDevicesProvider)) {
                    NearbyMediaDevicesLogger nearbyMediaDevicesLogger2 = nearbyMediaDevicesManager.logger;
                    int size = ((ArrayList) nearbyMediaDevicesManager.providers).size();
                    nearbyMediaDevicesLogger2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    NearbyMediaDevicesLogger$logProviderUnregistered$2 nearbyMediaDevicesLogger$logProviderUnregistered$2 = new Function1() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderUnregistered$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            return AbstractC0000x2c234b15.m0m("Provider unregistered; total providers = ", ((LogMessage) obj).getInt1());
                        }
                    };
                    LogBuffer logBuffer = nearbyMediaDevicesLogger2.buffer;
                    LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$logProviderUnregistered$2, null);
                    obtain.setInt1(size);
                    logBuffer.commit(obtain);
                }
            }
        };
        this.deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesManager$deathRecipient$1
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied(IBinder iBinder) {
                NearbyMediaDevicesManager nearbyMediaDevicesManager = NearbyMediaDevicesManager.this;
                synchronized (nearbyMediaDevicesManager.providers) {
                    int size = ((ArrayList) nearbyMediaDevicesManager.providers).size() - 1;
                    while (true) {
                        if (-1 >= size) {
                            break;
                        }
                        if (Intrinsics.areEqual(((INearbyMediaDevicesProvider) ((ArrayList) nearbyMediaDevicesManager.providers).get(size)).asBinder(), iBinder)) {
                            ((ArrayList) nearbyMediaDevicesManager.providers).remove(size);
                            NearbyMediaDevicesLogger nearbyMediaDevicesLogger2 = nearbyMediaDevicesManager.logger;
                            int size2 = ((ArrayList) nearbyMediaDevicesManager.providers).size();
                            nearbyMediaDevicesLogger2.getClass();
                            LogLevel logLevel = LogLevel.DEBUG;
                            NearbyMediaDevicesLogger$logProviderBinderDied$2 nearbyMediaDevicesLogger$logProviderBinderDied$2 = new Function1() { // from class: com.android.systemui.media.nearby.NearbyMediaDevicesLogger$logProviderBinderDied$2
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    return AbstractC0000x2c234b15.m0m("Provider binder died; total providers = ", ((LogMessage) obj).getInt1());
                                }
                            };
                            LogBuffer logBuffer = nearbyMediaDevicesLogger2.buffer;
                            LogMessage obtain = logBuffer.obtain("NearbyMediaDevices", logLevel, nearbyMediaDevicesLogger$logProviderBinderDied$2, null);
                            obtain.setInt1(size2);
                            logBuffer.commit(obtain);
                            break;
                        }
                        size--;
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
        commandQueue.addCallback(callbacks);
    }

    public final void unregisterNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
        ((ArrayList) this.activeCallbacks).remove(iNearbyMediaDevicesUpdateCallback);
        Iterator it = this.providers.iterator();
        while (it.hasNext()) {
            ((INearbyMediaDevicesProvider) it.next()).unregisterNearbyDevicesCallback(iNearbyMediaDevicesUpdateCallback);
        }
    }
}
