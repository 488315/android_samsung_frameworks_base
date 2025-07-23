package com.samsung.android.infoextraction;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemStrokeData implements Parcelable {
    public static final Parcelable.Creator<SemStrokeData> CREATOR = new Parcelable.Creator<SemStrokeData>() { // from class: com.samsung.android.infoextraction.SemStrokeData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemStrokeData createFromParcel(Parcel parcel) {
            SemStrokeData semStrokeData = new SemStrokeData();
            semStrokeData.readFromParcel(parcel);
            return semStrokeData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemStrokeData[] newArray(int i) {
            return new SemStrokeData[i];
        }
    };
    private List<PointF> mStroke;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemStrokeData() {
        this.mStroke = null;
        this.mStroke = new ArrayList();
    }

    public void setPoints(PointF[] pointFArr) {
        if (pointFArr != null) {
            for (PointF pointF : pointFArr) {
                this.mStroke.add(pointF);
            }
        }
    }

    public List<PointF> getPoints() {
        return this.mStroke;
    }

    public int size() {
        return this.mStroke.size();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mStroke);
    }

    public void readFromParcel(Parcel parcel) {
        if (this.mStroke == null) {
            this.mStroke = new ArrayList();
        }
        parcel.readTypedList(this.mStroke, PointF.CREATOR);
    }
}
