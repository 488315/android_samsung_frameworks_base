package android.view;

import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import java.util.Objects;

/* loaded from: classes4.dex */
public class DisplayAdjustments {
    public static final DisplayAdjustments DEFAULT_DISPLAY_ADJUSTMENTS = new DisplayAdjustments();
    private volatile CompatibilityInfo mCompatInfo;
    private final Configuration mConfiguration;

    public DisplayAdjustments() {
        this.mCompatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        this.mConfiguration = new Configuration(Configuration.EMPTY);
    }

    public DisplayAdjustments(Configuration configuration) {
        this.mCompatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        Configuration configuration2 = new Configuration(Configuration.EMPTY);
        this.mConfiguration = configuration2;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
    }

    public DisplayAdjustments(DisplayAdjustments displayAdjustments) {
        this.mCompatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        Configuration configuration = new Configuration(Configuration.EMPTY);
        this.mConfiguration = configuration;
        setCompatibilityInfo(displayAdjustments.mCompatInfo);
        configuration.setTo(displayAdjustments.getConfiguration());
    }

    public void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        if (this == DEFAULT_DISPLAY_ADJUSTMENTS) {
            throw new IllegalArgumentException("setCompatbilityInfo: Cannot modify DEFAULT_DISPLAY_ADJUSTMENTS");
        }
        if (compatibilityInfo != null && (compatibilityInfo.isScalingRequired() || !compatibilityInfo.supportsScreen())) {
            this.mCompatInfo = compatibilityInfo;
        } else {
            this.mCompatInfo = CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
    }

    public CompatibilityInfo getCompatibilityInfo() {
        return this.mCompatInfo;
    }

    public void setConfiguration(Configuration configuration) {
        if (this == DEFAULT_DISPLAY_ADJUSTMENTS) {
            throw new IllegalArgumentException("setConfiguration: Cannot modify DEFAULT_DISPLAY_ADJUSTMENTS");
        }
        Configuration configuration2 = this.mConfiguration;
        if (configuration == null) {
            configuration = Configuration.EMPTY;
        }
        configuration2.setTo(configuration);
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    public int hashCode() {
        return ((527 + Objects.hashCode(this.mCompatInfo)) * 31) + Objects.hashCode(this.mConfiguration);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DisplayAdjustments)) {
            return false;
        }
        DisplayAdjustments displayAdjustments = (DisplayAdjustments) obj;
        return Objects.equals(displayAdjustments.mCompatInfo, this.mCompatInfo) && Objects.equals(displayAdjustments.mConfiguration, this.mConfiguration);
    }
}
