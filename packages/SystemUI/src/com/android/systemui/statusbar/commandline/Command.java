package com.android.systemui.statusbar.commandline;

import java.io.PrintWriter;
import java.util.List;

public interface Command {
    void execute(PrintWriter printWriter, List list);
}
