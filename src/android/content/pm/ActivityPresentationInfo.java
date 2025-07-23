package android.content.pm;

import android.content.ComponentName;

/* loaded from: classes.dex */
public final class ActivityPresentationInfo {
    public final ComponentName componentName;
    public final int displayId;
    public final int taskId;

    public ActivityPresentationInfo(int i, int i2, ComponentName componentName) {
        this.taskId = i;
        this.displayId = i2;
        this.componentName = componentName;
    }
}
