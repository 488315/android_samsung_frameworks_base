package com.android.server.compat.overrides;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Overrides {
    public List changeOverrides;

    public final List getChangeOverrides() {
        if (this.changeOverrides == null) {
            this.changeOverrides = new ArrayList();
        }
        return this.changeOverrides;
    }
}
