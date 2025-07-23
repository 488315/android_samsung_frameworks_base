package com.android.systemui.flags;

import android.provider.DeviceConfig;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ServerFlagReaderImpl implements ServerFlagReader {
    public final DeviceConfigProxy deviceConfig;
    public final Executor executor;
    public final boolean isTestHarness;
    public final List listeners = new ArrayList();
    public final String namespace;

    public ServerFlagReaderImpl(String str, DeviceConfigProxy deviceConfigProxy, Executor executor, boolean z) {
        this.namespace = str;
        this.deviceConfig = deviceConfigProxy;
        this.executor = executor;
        this.isTestHarness = z;
        new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.flags.ServerFlagReaderImpl$onPropertiesChangedListener$1
            /* JADX WARN: Code restructure failed: missing block: B:24:0x009e, code lost:
            
                if (((java.lang.Boolean) ((java.util.HashMap) r4.mBooleanCache).get(r8.getName())).booleanValue() != (r3 == null ? false : java.lang.Boolean.parseBoolean(r3))) goto L43;
             */
            /* JADX WARN: Code restructure failed: missing block: B:43:0x00f0, code lost:
            
                if (((java.lang.Integer) ((java.util.HashMap) r4.mIntCache).get(r8.getName())).intValue() == r3) goto L42;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties r11) {
                /*
                    Method dump skipped, instructions count: 284
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.flags.ServerFlagReaderImpl$onPropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
            }
        };
    }
}
