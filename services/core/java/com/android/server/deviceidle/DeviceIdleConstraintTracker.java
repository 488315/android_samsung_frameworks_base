package com.android.server.deviceidle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceIdleConstraintTracker {
    public final int minState;
    public final String name;
    public boolean active = false;
    public boolean monitoring = false;

    public DeviceIdleConstraintTracker(String str, int i) {
        this.name = str;
        this.minState = i;
    }
}
