package android.view.animation;

/* loaded from: classes4.dex */
public abstract class BaseInterpolator implements Interpolator {
    private int mChangingConfiguration;

    public int getChangingConfiguration() {
        return this.mChangingConfiguration;
    }

    void setChangingConfiguration(int i) {
        this.mChangingConfiguration = i;
    }
}
