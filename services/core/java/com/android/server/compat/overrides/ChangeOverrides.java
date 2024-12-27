package com.android.server.compat.overrides;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ChangeOverrides {
    public Long changeId;
    public Raw deferred;
    public Raw raw;
    public Raw validated;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
