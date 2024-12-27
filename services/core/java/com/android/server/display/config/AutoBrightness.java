package com.android.server.display.config;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AutoBrightness {
    public BigInteger brighteningLightDebounceIdleMillis;
    public BigInteger brighteningLightDebounceMillis;
    public BigInteger darkeningLightDebounceIdleMillis;
    public BigInteger darkeningLightDebounceMillis;
    public Boolean enabled;
    public List luxToBrightnessMapping;

    public final List getLuxToBrightnessMapping() {
        if (this.luxToBrightnessMapping == null) {
            this.luxToBrightnessMapping = new ArrayList();
        }
        return this.luxToBrightnessMapping;
    }
}
