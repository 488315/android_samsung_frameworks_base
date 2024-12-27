package com.android.systemui.demomode;

import com.android.systemui.plugins.subscreen.SubRoom;
import com.google.android.collect.Lists;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.List;

public interface DemoMode extends DemoModeCommandReceiver {
    public static final List NO_COMMANDS = new ArrayList();
    public static final List COMMANDS = Lists.newArrayList(new String[]{"bars", "battery", SubRoom.EXTRA_VALUE_CLOCK, "network", "notifications", "operator", IMSParameter.CALL.STATUS, "volume"});

    default List demoCommands() {
        return NO_COMMANDS;
    }
}
