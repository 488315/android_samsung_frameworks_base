package com.samsung.android.infoextraction;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class SemStrokeData implements Parcelable {
    public static final Parcelable.Creator<SemStrokeData> CREATOR =
            new Parcelable.Creator<
                    SemStrokeData>() { // from class:
                                       // com.samsung.android.infoextraction.SemStrokeData.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemStrokeData createFromParcel(Parcel in) {
                    SemStrokeData semStrokeData = new SemStrokeData();
                    semStrokeData.readFromParcel(in);
                    return semStrokeData;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemStrokeData[] newArray(int size) {
                    return new SemStrokeData[size];
                }
            };
    private List<PointF> mStroke;

    public SemStrokeData() {
        this.mStroke = null;
        this.mStroke = new ArrayList();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setPoints(PointF[] points) {
        if (points != null) {
            for (PointF p : points) {
                this.mStroke.add(p);
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
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(this.mStroke);
    }

    public void readFromParcel(Parcel in) {
        if (this.mStroke == null) {
            this.mStroke = new ArrayList();
        }
        in.readTypedList(this.mStroke, PointF.CREATOR);
    }
}
