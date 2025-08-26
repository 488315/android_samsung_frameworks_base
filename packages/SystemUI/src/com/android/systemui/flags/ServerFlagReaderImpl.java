package com.android.systemui.flags;

import android.provider.DeviceConfig;
import android.util.Log;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

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
            /* JADX WARN: Removed duplicated region for block: B:42:0x00f3  */
            /* JADX WARN: Removed duplicated region for block: B:51:0x00f6 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:54:0x002a A[SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onPropertiesChanged(DeviceConfig.Properties properties) throws NumberFormatException {
                int i;
                if (this.this$0.isTestHarness) {
                    Log.w("ServerFlagReader", "Ignore server flag changes in Test Harness mode.");
                    return;
                }
                if (Intrinsics.areEqual(properties.getNamespace(), this.this$0.namespace)) {
                    ArrayList arrayList = (ArrayList) this.this$0.listeners;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        Pair pair = (Pair) obj;
                        FeatureFlagsClassicRelease.AnonymousClass1 anonymousClass1 = (FeatureFlagsClassicRelease.AnonymousClass1) pair.component1();
                        Collection<Flag> collection = (Collection) pair.component2();
                        Iterator it = properties.getKeyset().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                String str2 = (String) it.next();
                                for (Flag flag : collection) {
                                    if (Intrinsics.areEqual(str2, flag.getName())) {
                                        String string = properties.getString(str2, (String) null);
                                        FeatureFlagsClassicRelease featureFlagsClassicRelease = anonymousClass1.this$0;
                                        boolean zEquals = true;
                                        if (((HashMap) featureFlagsClassicRelease.mBooleanCache).containsKey(flag.getName())) {
                                            if (((Boolean) ((HashMap) featureFlagsClassicRelease.mBooleanCache).get(flag.getName())).booleanValue() == (string == null ? false : Boolean.parseBoolean(string))) {
                                                zEquals = false;
                                            }
                                            if (!zEquals) {
                                                featureFlagsClassicRelease.mRestarter.restartSystemUI("Server flag change: " + flag.getNamespace() + "." + flag.getName());
                                            }
                                        } else {
                                            if (((HashMap) featureFlagsClassicRelease.mStringCache).containsKey(flag.getName())) {
                                                if (string == null) {
                                                    string = "";
                                                }
                                                zEquals = true ^ ((String) ((HashMap) featureFlagsClassicRelease.mStringCache).get(flag.getName())).equals(string);
                                            } else if (((HashMap) featureFlagsClassicRelease.mIntCache).containsKey(flag.getName())) {
                                                if (string == null) {
                                                    i = 0;
                                                    if (((Integer) ((HashMap) featureFlagsClassicRelease.mIntCache).get(flag.getName())).intValue() == i) {
                                                    }
                                                } else {
                                                    try {
                                                        i = Integer.parseInt(string);
                                                    } catch (NumberFormatException unused) {
                                                    }
                                                    if (((Integer) ((HashMap) featureFlagsClassicRelease.mIntCache).get(flag.getName())).intValue() == i) {
                                                    }
                                                }
                                            }
                                            if (!zEquals) {
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
