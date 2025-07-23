package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
