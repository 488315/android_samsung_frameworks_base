package com.android.systemui.flags;

import android.provider.DeviceConfig;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class ServerFlagReaderImpl implements ServerFlagReader {
    public final DeviceConfigProxy deviceConfig;
    public final Executor executor;
    public final boolean isTestHarness;
    public final String namespace;
    public final String TAG = "ServerFlagReader";
    public final List listeners = new ArrayList();

    public ServerFlagReaderImpl(String str, DeviceConfigProxy deviceConfigProxy, Executor executor, boolean z) {
        this.namespace = str;
        this.deviceConfig = deviceConfigProxy;
        this.executor = executor;
        this.isTestHarness = z;
        new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.flags.ServerFlagReaderImpl$onPropertiesChangedListener$1
            /* JADX WARN: Code restructure failed: missing block: B:26:0x009e, code lost:
            
                if (((java.lang.Boolean) ((java.util.HashMap) r1.mBooleanCache).get(r5.getName())).booleanValue() != (r0 == null ? false : java.lang.Boolean.parseBoolean(r0))) goto L44;
             */
            /* JADX WARN: Code restructure failed: missing block: B:45:0x00f0, code lost:
            
                if (((java.lang.Integer) ((java.util.HashMap) r1.mIntCache).get(r5.getName())).intValue() == r0) goto L43;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties r8) {
                /*
                    Method dump skipped, instructions count: 284
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.flags.ServerFlagReaderImpl$onPropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
            }
        };
    }
}
