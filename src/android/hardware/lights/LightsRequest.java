package android.hardware.lights;

import android.annotation.SystemApi;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class LightsRequest {
    final List<Integer> mLightIds;
    final List<LightState> mLightStates;
    final Map<Light, LightState> mRequests;

    private LightsRequest(Map<Light, LightState> map) {
        HashMap map2 = new HashMap();
        this.mRequests = map2;
        this.mLightIds = new ArrayList();
        this.mLightStates = new ArrayList();
        map2.putAll(map);
        ArrayList arrayList = new ArrayList(map2.keySet());
        for (int i = 0; i < arrayList.size(); i++) {
            Light light = (Light) arrayList.get(i);
            this.mLightIds.add(i, Integer.valueOf(light.getId()));
            this.mLightStates.add(i, this.mRequests.get(light));
        }
    }

    public List<Integer> getLights() {
        return this.mLightIds;
    }

    public List<LightState> getLightStates() {
        return this.mLightStates;
    }

    public Map<Light, LightState> getLightsAndStates() {
        return this.mRequests;
    }

    public static final class Builder {
        final Map<Light, LightState> mChanges = new HashMap();

        public Builder addLight(Light light, LightState lightState) {
            Preconditions.checkNotNull(light);
            Preconditions.checkNotNull(lightState);
            this.mChanges.put(light, lightState);
            return this;
        }

        @SystemApi
        @Deprecated
        public Builder setLight(Light light, LightState lightState) {
            return addLight(light, lightState);
        }

        public Builder clearLight(Light light) {
            Preconditions.checkNotNull(light);
            this.mChanges.put(light, null);
            return this;
        }

        public LightsRequest build() {
            return new LightsRequest(this.mChanges);
        }
    }
}
