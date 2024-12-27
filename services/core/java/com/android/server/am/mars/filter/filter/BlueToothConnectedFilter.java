package com.android.server.am.mars.filter.filter;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.filter.IFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class BlueToothConnectedFilter implements IFilter {
    public List mBTAllowList;
    public List mBTTargetList;
    public BluetoothAdapter mBluetoothAdapter;

    public abstract class BlueToothConnectedFilterHolder {
        public static final BlueToothConnectedFilter INSTANCE;

        static {
            BlueToothConnectedFilter blueToothConnectedFilter = new BlueToothConnectedFilter();
            blueToothConnectedFilter.mBTAllowList = new ArrayList();
            blueToothConnectedFilter.mBTTargetList = new ArrayList();
            INSTANCE = blueToothConnectedFilter;
        }
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void deInit() {}

    @Override // com.android.server.am.mars.filter.IFilter
    public final int filter(int i, int i2, int i3, String str) {
        if (!MARsUtils.isChinaPolicyEnabled()) {
            return 0;
        }
        Integer valueOf = Integer.valueOf(i2);
        List list = this.mBTAllowList;
        return (list == null || !list.contains(valueOf)) ? 0 : 27;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public final void init(Context context) {}

    public final void updateBTUsingPackages() {
        Map hWUsingApps;
        List list = this.mBTAllowList;
        if (list != null) {
            list.clear();
        }
        if (this.mBluetoothAdapter == null) {
            this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null || (hWUsingApps = bluetoothAdapter.getHWUsingApps()) == null) {
            return;
        }
        for (Map.Entry entry : hWUsingApps.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            if (intValue == 1) {
                this.mBTAllowList = (List) entry.getValue();
            } else if (intValue == 2) {
                this.mBTTargetList = (List) entry.getValue();
            }
        }
    }
}
