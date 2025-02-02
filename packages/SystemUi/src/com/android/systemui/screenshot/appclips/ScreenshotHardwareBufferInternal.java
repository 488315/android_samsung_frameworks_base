package com.android.systemui.screenshot.appclips;

import android.graphics.ParcelableColorSpace;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.ScreenCapture;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotHardwareBufferInternal implements Parcelable {
    public static final Parcelable.Creator<ScreenshotHardwareBufferInternal> CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.screenshot.appclips.ScreenshotHardwareBufferInternal.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ScreenshotHardwareBufferInternal(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ScreenshotHardwareBufferInternal[i];
        }
    };
    public final HardwareBuffer mHardwareBuffer;
    public final ParcelableColorSpace mParcelableColorSpace;

    public /* synthetic */ ScreenshotHardwareBufferInternal(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ScreenshotHardwareBufferInternal)) {
            return false;
        }
        ScreenshotHardwareBufferInternal screenshotHardwareBufferInternal = (ScreenshotHardwareBufferInternal) obj;
        return this.mHardwareBuffer.equals(screenshotHardwareBufferInternal.mHardwareBuffer) && this.mParcelableColorSpace.equals(screenshotHardwareBufferInternal.mParcelableColorSpace);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mHardwareBuffer, i);
        parcel.writeParcelable(this.mParcelableColorSpace, i);
    }

    public ScreenshotHardwareBufferInternal(ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer) {
        this.mHardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
        this.mParcelableColorSpace = new ParcelableColorSpace(screenshotHardwareBuffer.getColorSpace());
    }

    private ScreenshotHardwareBufferInternal(Parcel parcel) {
        this.mHardwareBuffer = (HardwareBuffer) parcel.readParcelable(HardwareBuffer.class.getClassLoader(), HardwareBuffer.class);
        this.mParcelableColorSpace = (ParcelableColorSpace) parcel.readParcelable(ParcelableColorSpace.class.getClassLoader(), ParcelableColorSpace.class);
    }
}
