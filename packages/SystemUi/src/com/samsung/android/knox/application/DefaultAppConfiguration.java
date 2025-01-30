package com.samsung.android.knox.application;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DefaultAppConfiguration implements Parcelable {
    public static final Parcelable.Creator<DefaultAppConfiguration> CREATOR = new Parcelable.Creator<DefaultAppConfiguration>() { // from class: com.samsung.android.knox.application.DefaultAppConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DefaultAppConfiguration[] newArray(int i) {
            return new DefaultAppConfiguration[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DefaultAppConfiguration createFromParcel(Parcel parcel) {
            return new DefaultAppConfiguration(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final DefaultAppConfiguration[] newArray(int i) {
            return new DefaultAppConfiguration[i];
        }
    };
    public ComponentName mComponentName;
    public Intent mTaskType;

    public /* synthetic */ DefaultAppConfiguration(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final ComponentName getComponentName() {
        return this.mComponentName;
    }

    public final Intent getTaskType() {
        return this.mTaskType;
    }

    public final void readFromParcel(Parcel parcel) {
        this.mTaskType = (Intent) Intent.CREATOR.createFromParcel(parcel);
        this.mComponentName = ComponentName.readFromParcel(parcel);
    }

    public final void setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
    }

    public final void setTaskType(Intent intent) {
        this.mTaskType = intent;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
