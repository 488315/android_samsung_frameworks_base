package com.android.systemui.statusbar.commandline;

import java.util.Iterator;

public interface Param extends Describable {
    void parseArgsFromIter(Iterator it);
}
