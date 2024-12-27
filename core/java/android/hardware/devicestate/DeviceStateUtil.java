package android.hardware.devicestate;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class DeviceStateUtil {
    private DeviceStateUtil() {}

    public static int calculateBaseStateIdentifier(
            DeviceState currentState, List<DeviceState> supportedStates) {
        DeviceState.Configuration stateConfiguration = currentState.getConfiguration();
        for (int i = 0; i < supportedStates.size(); i++) {
            DeviceState stateToCompare = supportedStates.get(i);
            if (!stateToCompare.getConfiguration().getPhysicalProperties().isEmpty()
                    && isDeviceStateMatchingPhysicalProperties(
                            stateConfiguration.getPhysicalProperties(), supportedStates.get(i))) {
                return supportedStates.get(i).getIdentifier();
            }
        }
        return -1;
    }

    private static boolean isDeviceStateMatchingPhysicalProperties(
            Set<Integer> physicalProperties, DeviceState state) {
        Iterator<Integer> iterator = physicalProperties.iterator();
        while (iterator.hasNext()) {
            if (!state.hasProperty(iterator.next().intValue())) {
                return false;
            }
        }
        return true;
    }
}
