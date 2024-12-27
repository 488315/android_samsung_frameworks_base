package com.android.systemui;

import com.android.systemui.dump.nano.SystemUIProtoDump;

public interface ProtoDumpable extends Dumpable {
    void dumpProto(SystemUIProtoDump systemUIProtoDump);
}
