package com.android.server.am.mars.filter.filter;

import android.content.Context;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.filter.IFilter;
import com.sec.android.sdhms.ISamsungDeviceHealthManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes.dex */
public class ActiveSensorFilter implements IFilter {
    public final String TAG;
    public final List mFilterTypeList;
    public List mSensorList;

    public abstract class ActiveSensorFilterHolder {
        public static final ActiveSensorFilter INSTANCE = new ActiveSensorFilter();
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void deInit() {
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public void init(Context context) {
    }

    public ActiveSensorFilter() {
        this.TAG = "MARs:" + ActiveSensorFilter.class.getSimpleName();
        this.mFilterTypeList = new ArrayList(Arrays.asList(1));
    }

    public static ActiveSensorFilter getInstance() {
        return ActiveSensorFilterHolder.INSTANCE;
    }

    @Override // com.android.server.am.mars.filter.IFilter
    public int filter(String str, int i, int i2, int i3) {
        List list;
        return (MARsPolicyManager.getInstance().isChinaPolicyEnabled() || !MARsPolicyManager.getInstance().isForegroundServicePkg(i2) || (list = this.mSensorList) == null || !list.contains(Integer.valueOf(i2))) ? 0 : 23;
    }

    public void getActiveSensorList() {
        ISamsungDeviceHealthManager asInterface;
        IBinder service = ServiceManager.getService("sdhms");
        if (service == null || (asInterface = ISamsungDeviceHealthManager.Stub.asInterface(service)) == null) {
            return;
        }
        try {
            this.mSensorList = parseActiveSensor(asInterface.getActiveSensorList());
        } catch (Exception e) {
            Slog.e(this.TAG, "Exception at getActiveSensorList:" + e);
        }
    }

    public final List parseActiveSensor(String str) {
        if (str == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        for (String str2 : str.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
            String[] split = str2.split(" ");
            if (split.length > 1) {
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                if (isTarget(parseInt2, parseInt)) {
                    hashSet.add(Integer.valueOf(parseInt2));
                }
            }
        }
        return new ArrayList(hashSet);
    }

    public final boolean isTarget(int i, int i2) {
        return this.mFilterTypeList.contains(Integer.valueOf(i2)) && i != 1000;
    }
}
