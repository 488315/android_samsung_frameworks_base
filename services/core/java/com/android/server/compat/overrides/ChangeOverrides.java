package com.android.server.compat.overrides;

import java.util.ArrayList;
import java.util.List;

public final class ChangeOverrides {
    public Long changeId;
    public Raw deferred;
    public Raw raw;
    public Raw validated;

    public final class Raw {
        public List rawOverrideValue;

        public List getOverrideValue() {
            if (this.rawOverrideValue == null) {
                this.rawOverrideValue = new ArrayList();
            }
            return this.rawOverrideValue;
        }

        public List getRawOverrideValue() {
            if (this.rawOverrideValue == null) {
                this.rawOverrideValue = new ArrayList();
            }
            return this.rawOverrideValue;
        }
    }
}
