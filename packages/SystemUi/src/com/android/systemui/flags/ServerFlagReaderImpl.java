package com.android.systemui.flags;

import android.provider.DeviceConfig;
import android.util.Log;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
            /* JADX WARN: Code restructure failed: missing block: B:26:0x009d, code lost:
            
                if (((java.lang.Boolean) ((java.util.HashMap) r1.mBooleanCache).get(r5.getName())).booleanValue() != (r0 == null ? false : java.lang.Boolean.parseBoolean(r0))) goto L44;
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:0x00ec, code lost:
            
                r3 = true;
             */
            /* JADX WARN: Code restructure failed: missing block: B:40:0x00be, code lost:
            
                if (((java.util.HashMap) r1.mStringCache).get(r5.getName()) != r0) goto L44;
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x00ea, code lost:
            
                if (((java.lang.Integer) ((java.util.HashMap) r1.mIntCache).get(r5.getName())).intValue() == r0) goto L45;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                int parseInt;
                ServerFlagReaderImpl serverFlagReaderImpl = ServerFlagReaderImpl.this;
                if (serverFlagReaderImpl.isTestHarness) {
                    Log.w(serverFlagReaderImpl.TAG, "Ignore server flag changes in Test Harness mode.");
                    return;
                }
                if (Intrinsics.areEqual(properties.getNamespace(), ServerFlagReaderImpl.this.namespace)) {
                    Iterator it = ((ArrayList) ServerFlagReaderImpl.this.listeners).iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        FeatureFlagsRelease.C13911 c13911 = (FeatureFlagsRelease.C13911) pair.component1();
                        Collection<Flag> collection = (Collection) pair.component2();
                        Iterator it2 = properties.getKeyset().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                String str2 = (String) it2.next();
                                for (Flag flag : collection) {
                                    if (Intrinsics.areEqual(str2, flag.getName())) {
                                        String string = properties.getString(str2, (String) null);
                                        FeatureFlagsRelease featureFlagsRelease = c13911.this$0;
                                        boolean z2 = false;
                                        if (!((HashMap) featureFlagsRelease.mBooleanCache).containsKey(flag.getName())) {
                                            if (!((HashMap) featureFlagsRelease.mStringCache).containsKey(flag.getName())) {
                                                if (((HashMap) featureFlagsRelease.mIntCache).containsKey(flag.getName())) {
                                                    if (string != null) {
                                                        try {
                                                            parseInt = Integer.parseInt(string);
                                                        } catch (NumberFormatException unused) {
                                                        }
                                                    }
                                                    parseInt = 0;
                                                }
                                                if (z2) {
                                                    featureFlagsRelease.mRestarter.restartSystemUI("Server flag change: " + flag.getNamespace() + "." + flag.getName());
                                                }
                                            } else if (string == null) {
                                                string = "";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
