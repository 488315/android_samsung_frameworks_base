package com.android.server.am.mars.filter.filter;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class BlueToothConnectedFilter implements IFilter {
    public List mBTAllowList;
    public List mBTTargetList;
    public BluetoothAdapter mBluetoothAdapter;

    public abstract class BlueToothConnectedFilterHolder {
        public static final BlueToothConnectedFilter INSTANCE = new BlueToothConnectedFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public BlueToothConnectedFilter() {
        this.mBTAllowList = new ArrayList();
        this.mBTTargetList = new ArrayList();
    }

    public static BlueToothConnectedFilter getInstance() {
        return BlueToothConnectedFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        return (MARsPolicyManager.getInstance().isChinaPolicyEnabled() && isInBTAllowList(i2)) ? 27 : 0;
    }

    public boolean isInBTAllowList(int i) {
        Integer valueOf = Integer.valueOf(i);
        List list = this.mBTAllowList;
        return list != null && list.contains(valueOf);
    }

    public boolean isInBTTargetList(int i) {
        Integer valueOf = Integer.valueOf(i);
        List list = this.mBTTargetList;
        return list != null && list.contains(valueOf);
    }

    public void updateBTUsingPackages() {
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
        Iterator it = hWUsingApps.keySet().iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (intValue == 1) {
                this.mBTAllowList = (List) hWUsingApps.get(Integer.valueOf(intValue));
            } else if (intValue == 2) {
                this.mBTTargetList = (List) hWUsingApps.get(Integer.valueOf(intValue));
            }
        }
    }
}
