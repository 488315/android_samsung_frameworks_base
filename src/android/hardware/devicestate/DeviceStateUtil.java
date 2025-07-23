package android.hardware.devicestate;

import android.hardware.devicestate.DeviceState;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class DeviceStateUtil {
    private DeviceStateUtil() {
    }

    public static int calculateBaseStateIdentifier(DeviceState deviceState, List<DeviceState> list) {
        DeviceState.Configuration configuration = deviceState.getConfiguration();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getConfiguration().getPhysicalProperties().isEmpty() && isDeviceStateMatchingPhysicalProperties(configuration.getPhysicalProperties(), list.get(i))) {
                return list.get(i).getIdentifier();
            }
        }
        return -1;
    }

    private static boolean isDeviceStateMatchingPhysicalProperties(Set<Integer> set, DeviceState deviceState) {
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            if (!deviceState.hasProperty(it.next().intValue())) {
                return false;
            }
        }
        return true;
    }
}
