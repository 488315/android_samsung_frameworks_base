package com.android.systemui.plank.protocol;

import com.android.systemui.plank.ApiLogger;
import com.android.systemui.plank.command.PlankDispatcherFactory;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.android.systemui.plank.protocol.Protocol;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ProtocolManagerImpl {
    public final ApiLogger apiLogger;
    public final PlankDispatcherFactory plankDispatcherFactory;
    public final Protocol protocol;
    public final TestInputMonitor testInputMonitor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Protocol.Command.values().length];
            try {
                iArr[Protocol.Command.start_monitor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Protocol.Command.stop_monitor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Protocol.Command.check_api.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ProtocolManagerImpl(TestInputMonitor testInputMonitor, Protocol protocol, ApiLogger apiLogger, PlankDispatcherFactory plankDispatcherFactory) {
        this.testInputMonitor = testInputMonitor;
        this.protocol = protocol;
        this.apiLogger = apiLogger;
        this.plankDispatcherFactory = plankDispatcherFactory;
    }
}
