package com.android.server.policy;

import android.content.Context;
import android.os.Environment;

import com.android.server.devicestate.DeviceStatePolicy;

import java.io.File;
import java.io.PrintWriter;

public final class DeviceStatePolicyImpl extends DeviceStatePolicy {
    public final DeviceStateProviderImpl mProvider;

    public DeviceStatePolicyImpl(Context context) {
        DeviceStateProviderImpl$$ExternalSyntheticLambda0
                deviceStateProviderImpl$$ExternalSyntheticLambda0 =
                        DeviceStateProviderImpl.TRUE_BOOLEAN_SUPPLIER;
        File buildPath =
                Environment.buildPath(
                        Environment.getDataDirectory(),
                        new String[] {"system/devicestate/", "device_state_configuration.xml"});
        if (!buildPath.exists()) {
            buildPath =
                    Environment.buildPath(
                            Environment.getVendorDirectory(),
                            new String[] {"etc/devicestate/", "device_state_configuration.xml"});
            if (!buildPath.exists()) {
                buildPath = null;
            }
        }
        this.mProvider =
                buildPath == null
                        ? DeviceStateProviderImpl.createFromConfig(context, null)
                        : DeviceStateProviderImpl.createFromConfig(
                                context, new DeviceStateProviderImpl.ReadableFileConfig(buildPath));
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.mProvider.dump(printWriter, strArr);
    }
}
