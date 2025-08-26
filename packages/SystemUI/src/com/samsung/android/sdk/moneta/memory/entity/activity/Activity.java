package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class Activity implements Parcelable {
    private final ActivityType activityType;

    public Activity(ActivityType activityType) {
        this.activityType = activityType;
    }

    public final ActivityType getActivityType() {
        return this.activityType;
    }

    public abstract List<Content> getContents();

    public abstract String getId();
}
