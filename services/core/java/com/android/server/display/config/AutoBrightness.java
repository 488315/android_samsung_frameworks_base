package com.android.server.display.config;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
