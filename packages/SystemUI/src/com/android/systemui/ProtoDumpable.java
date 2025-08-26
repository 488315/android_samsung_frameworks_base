package com.android.systemui;

import com.android.systemui.dump.nano.SystemUIProtoDump;

/* loaded from: classes.dex */
public interface ProtoDumpable extends Dumpable {
    void dumpProto(SystemUIProtoDump systemUIProtoDump);
}
