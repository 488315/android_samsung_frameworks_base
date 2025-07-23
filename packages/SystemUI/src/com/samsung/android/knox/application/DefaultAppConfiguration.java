package com.samsung.android.knox.application;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class DefaultAppConfiguration implements Parcelable {
    public static final Parcelable.Creator<DefaultAppConfiguration> CREATOR = new Parcelable.Creator<DefaultAppConfiguration>() { // from class: com.samsung.android.knox.application.DefaultAppConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultAppConfiguration createFromParcel(Parcel parcel) {
            return new DefaultAppConfiguration(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultAppConfiguration[] newArray(int i) {
            return new DefaultAppConfiguration[i];
        }
    };
    public ComponentName mComponentName;
    public Intent mTaskType;

    public /* synthetic */ DefaultAppConfiguration(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Intent getTaskType() {
        return this.mTaskType;
    }

    public void readFromParcel(Parcel parcel) {
        this.mTaskType = (Intent) Intent.CREATOR.createFromParcel(parcel);
        this.mComponentName = ComponentName.readFromParcel(parcel);
    }

    public void setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
    }

    public void setTaskType(Intent intent) {
        this.mTaskType = intent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mTaskType.writeToParcel(parcel, i);
        ComponentName.writeToParcel(this.mComponentName, parcel);
    }

    public DefaultAppConfiguration(Intent intent, ComponentName componentName) {
        this.mTaskType = intent;
        this.mComponentName = componentName;
    }

    private DefaultAppConfiguration(Parcel parcel) {
        readFromParcel(parcel);
    }

    public DefaultAppConfiguration() {
    }
}
