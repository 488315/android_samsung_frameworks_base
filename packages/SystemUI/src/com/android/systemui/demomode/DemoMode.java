package com.android.systemui.demomode;

import com.android.systemui.plugins.subscreen.SubRoom;
import com.google.android.collect.Lists;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface DemoMode extends DemoModeCommandReceiver {
    public static final List NO_COMMANDS = new ArrayList();
    public static final List COMMANDS = Lists.newArrayList(new String[]{"bars", "battery", SubRoom.EXTRA_VALUE_CLOCK, "network", "notifications", "operator", IMSParameter.CALL.STATUS, "volume"});

    default List demoCommands() {
        return NO_COMMANDS;
    }
}
