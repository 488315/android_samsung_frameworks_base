package com.android.systemui.plank.protocol;

import com.android.systemui.plank.ApiLogger;
import com.android.systemui.plank.command.PlankDispatcherFactory;
import com.android.systemui.plank.monitor.TestInputMonitor;
import com.android.systemui.plank.protocol.Protocol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ProtocolManagerImpl {
    public final ApiLogger apiLogger;
    public final PlankDispatcherFactory plankDispatcherFactory;
    public final Protocol protocol;
    public final TestInputMonitor testInputMonitor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
