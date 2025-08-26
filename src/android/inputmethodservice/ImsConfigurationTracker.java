package android.inputmethodservice;

import android.content.res.Configuration;
import android.content.res.Flags;
import android.content.res.Resources;
import com.android.internal.util.Preconditions;

/* loaded from: classes2.dex */
public final class ImsConfigurationTracker {
    private static final int CONFIG_CHANGED = -1;
    private Configuration mLastKnownConfig = null;
    private int mHandledConfigChanges = 0;
    private boolean mInitialized = false;

    public void onInitialize(int i) {
        Preconditions.checkState(!this.mInitialized, "onInitialize can be called only once.");
        this.mInitialized = true;
        this.mHandledConfigChanges = i;
    }

    public void onBindInput(Resources resources) {
        if (this.mInitialized && this.mLastKnownConfig == null && resources != null) {
            this.mLastKnownConfig = new Configuration(resources.getConfiguration());
        }
    }

    public void setHandledConfigChanges(int i) {
        this.mHandledConfigChanges = i;
    }

    public void onConfigurationChanged(Configuration configuration, Runnable runnable) {
        if (this.mInitialized) {
            boolean z = Flags.handleAllConfigChanges() && (this.mHandledConfigChanges & 134217728) != 0;
            Configuration configuration2 = this.mLastKnownConfig;
            int iDiffPublicOnly = configuration2 != null ? configuration2.diffPublicOnly(configuration) : -1;
            if (((~this.mHandledConfigChanges) & iDiffPublicOnly) != 0 && !z) {
                runnable.run();
            }
            if (iDiffPublicOnly != 0) {
                this.mLastKnownConfig = new Configuration(configuration);
            }
        }
    }
}
