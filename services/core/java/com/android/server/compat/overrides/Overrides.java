package com.android.server.compat.overrides;

import java.util.ArrayList;
import java.util.List;

public final class Overrides {
    public List changeOverrides;

    public final List getChangeOverrides() {
        if (this.changeOverrides == null) {
            this.changeOverrides = new ArrayList();
        }
        return this.changeOverrides;
    }
}
