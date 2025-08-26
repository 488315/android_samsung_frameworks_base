package com.android.wm.shell.shared.split;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* loaded from: classes3.dex */
public class SplitBounds implements Parcelable {
    public static final Parcelable.Creator<SplitBounds> CREATOR = new Parcelable.Creator() { // from class: com.android.wm.shell.shared.split.SplitBounds.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new SplitBounds(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SplitBounds[i];
        }
    };
    public final boolean appsStackedVertically;
    public final Rect cellDividerBounds;
    public final float cellDividerHeightPercent;
    public final float cellDividerWidthPercent;
    public final float cellLeftTaskPercent;
    public final int cellPosition;
    public final Rect cellTaskBounds;
    public final int cellTaskId;
    public final float cellTopTaskPercent;
    public final float dividerHeightPercent;
    public final float dividerWidthPercent;
    public final float leftTaskPercent;
    public final Rect leftTopBounds;
    public final int leftTopTaskId;
    public final boolean parallelMultiSplit;
    public final Rect rightBottomBounds;
    public final int rightBottomTaskId;
    public final int snapPosition;
    public final float topTaskPercent;
    public final Rect visualDividerBounds;

    public SplitBounds(Rect rect, Rect rect2, int i, int i2, int i3) {
        this(rect, rect2, null, i, i2, i3, -1, 0, -1, false);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SplitBounds)) {
            return false;
        }
        SplitBounds splitBounds = (SplitBounds) obj;
        return Objects.equals(this.leftTopBounds, splitBounds.leftTopBounds) && Objects.equals(this.rightBottomBounds, splitBounds.rightBottomBounds) && this.leftTopTaskId == splitBounds.leftTopTaskId && this.rightBottomTaskId == splitBounds.rightBottomTaskId && Objects.equals(this.cellTaskBounds, splitBounds.cellTaskBounds) && this.cellTaskId == splitBounds.cellTaskId && this.parallelMultiSplit == splitBounds.parallelMultiSplit;
    }

    public final int hashCode() {
        return Objects.hash(this.leftTopBounds, this.rightBottomBounds, Integer.valueOf(this.leftTopTaskId), Integer.valueOf(this.rightBottomTaskId));
    }

    public final String toString() {
        return "LeftTop: " + this.leftTopBounds + ", taskId: " + this.leftTopTaskId + "\nRightBottom: " + this.rightBottomBounds + ", taskId: " + this.rightBottomTaskId + "\nDivider: " + this.visualDividerBounds + "\nAppsVertical? " + this.appsStackedVertically + "\nsnapPosition: " + this.snapPosition;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.leftTopBounds, i);
        parcel.writeTypedObject(this.rightBottomBounds, i);
        parcel.writeTypedObject(this.visualDividerBounds, i);
        parcel.writeFloat(this.topTaskPercent);
        parcel.writeFloat(this.leftTaskPercent);
        parcel.writeBoolean(this.appsStackedVertically);
        parcel.writeInt(this.leftTopTaskId);
        parcel.writeInt(this.rightBottomTaskId);
        parcel.writeFloat(this.dividerWidthPercent);
        parcel.writeFloat(this.dividerHeightPercent);
        parcel.writeInt(this.snapPosition);
        parcel.writeTypedObject(this.cellTaskBounds, i);
        parcel.writeInt(this.cellTaskId);
        parcel.writeInt(this.cellPosition);
        parcel.writeTypedObject(this.cellDividerBounds, i);
        parcel.writeFloat(this.cellTopTaskPercent);
        parcel.writeFloat(this.cellLeftTaskPercent);
        parcel.writeFloat(this.cellDividerWidthPercent);
        parcel.writeFloat(this.cellDividerHeightPercent);
        parcel.writeBoolean(this.parallelMultiSplit);
    }

    public SplitBounds(Rect rect, Rect rect2, Rect rect3, int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        float f;
        float fWidth;
        float fHeight;
        float f2;
        float fWidth2;
        float fHeight2;
        this.leftTopBounds = rect;
        this.rightBottomBounds = rect2;
        this.leftTopTaskId = i;
        this.rightBottomTaskId = i2;
        this.snapPosition = i3;
        this.cellTaskBounds = rect3;
        this.cellTaskId = i4;
        this.cellPosition = i5;
        this.parallelMultiSplit = z;
        if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || i4 == -1) {
            this.cellDividerBounds = null;
            this.cellTopTaskPercent = 0.5f;
            this.cellLeftTaskPercent = 0.5f;
            this.cellDividerWidthPercent = 0.0f;
            this.cellDividerHeightPercent = 0.0f;
            if (rect2.top > rect.top) {
                this.visualDividerBounds = new Rect(rect.left, rect.bottom, rect.right, rect2.top);
                this.appsStackedVertically = true;
            } else {
                this.visualDividerBounds = new Rect(rect.right, rect.top, rect2.left, rect.bottom);
                this.appsStackedVertically = false;
            }
            float f3 = rect2.right - rect.left;
            float f4 = rect2.bottom - rect.top;
            this.leftTaskPercent = rect.width() / f3;
            this.topTaskPercent = rect.height() / f4;
            this.dividerWidthPercent = this.visualDividerBounds.width() / f3;
            this.dividerHeightPercent = this.visualDividerBounds.height() / f4;
            return;
        }
        Rect rect4 = new Rect(rect3);
        if (!CoreRune.MW_PARALLEL_MULTI_SPLIT || !z) {
            if (i6 == 0) {
                this.appsStackedVertically = false;
                if ((i5 & 8) != 0) {
                    rect4.union(rect);
                    fWidth = rect2.right - rect4.left;
                    this.visualDividerBounds = new Rect(rect4.right, rect4.top, rect2.left, rect2.bottom);
                    if ((i5 & 16) != 0) {
                        this.cellDividerBounds = new Rect(rect3.left, rect3.bottom, rect.right, rect.top);
                    } else {
                        this.cellDividerBounds = new Rect(rect.left, rect.bottom, rect3.right, rect3.top);
                    }
                } else {
                    rect4.union(rect2);
                    fWidth = rect4.right - rect.left;
                    this.visualDividerBounds = new Rect(rect.right, rect.top, rect4.left, rect4.bottom);
                    if ((i5 & 16) != 0) {
                        this.cellDividerBounds = new Rect(rect3.left, rect3.bottom, rect2.right, rect2.top);
                    } else {
                        this.cellDividerBounds = new Rect(rect2.left, rect2.bottom, rect3.right, rect3.top);
                    }
                }
                this.leftTaskPercent = rect.width() / rect2.right;
                this.cellTopTaskPercent = rect3.height() / rect4.height();
                this.topTaskPercent = 1.0f;
                this.cellLeftTaskPercent = 1.0f;
                fHeight = rect4.height();
            } else {
                this.appsStackedVertically = true;
                if ((i5 & 16) != 0) {
                    rect4.union(rect);
                    f = rect2.bottom - rect4.top;
                    this.visualDividerBounds = new Rect(rect4.left, rect4.bottom, rect2.right, rect2.top);
                    if ((i5 & 8) != 0) {
                        this.cellDividerBounds = new Rect(rect3.right, rect3.top, rect.left, rect.bottom);
                    } else {
                        this.cellDividerBounds = new Rect(rect.right, rect.top, rect3.left, rect3.bottom);
                    }
                } else {
                    rect4.union(rect2);
                    f = rect4.bottom - rect.top;
                    this.visualDividerBounds = new Rect(rect.left, rect.bottom, rect4.right, rect4.top);
                    if ((i5 & 8) != 0) {
                        this.cellDividerBounds = new Rect(rect3.right, rect3.top, rect2.left, rect2.bottom);
                    } else {
                        this.cellDividerBounds = new Rect(rect2.right, rect2.top, rect3.left, rect3.bottom);
                    }
                }
                this.topTaskPercent = rect.height() / (rect2.bottom - rect.top);
                this.cellLeftTaskPercent = rect3.width() / rect4.right;
                this.leftTaskPercent = 1.0f;
                this.cellTopTaskPercent = 1.0f;
                float f5 = f;
                fWidth = rect4.width();
                fHeight = f5;
            }
            this.dividerWidthPercent = this.visualDividerBounds.width() / fWidth;
            this.dividerHeightPercent = this.visualDividerBounds.height() / fHeight;
            this.cellDividerWidthPercent = this.cellDividerBounds.width() / rect4.width();
            this.cellDividerHeightPercent = this.cellDividerBounds.height() / rect4.height();
            return;
        }
        if (i6 == 0) {
            this.appsStackedVertically = false;
            if ((i5 & 8) != 0) {
                rect4.union(rect);
                this.visualDividerBounds = new Rect(rect.right, rect.top, rect2.left, rect2.bottom);
                if ((i5 & 16) != 0) {
                    this.cellDividerBounds = new Rect(rect3.right, rect3.top, rect.left, rect.bottom);
                } else {
                    this.cellDividerBounds = new Rect(rect.right, rect.top, rect3.left, rect3.bottom);
                }
                fWidth2 = rect2.right - rect3.left;
                this.leftTaskPercent = rect4.width() / fWidth2;
                this.cellLeftTaskPercent = rect3.width() / rect4.width();
            } else {
                rect4.union(rect2);
                this.visualDividerBounds = new Rect(rect.right, rect.top, rect2.left, rect2.bottom);
                if ((i5 & 16) != 0) {
                    this.cellDividerBounds = new Rect(rect3.right, rect3.top, rect2.left, rect2.bottom);
                } else {
                    this.cellDividerBounds = new Rect(rect2.right, rect2.top, rect3.left, rect3.bottom);
                }
                fWidth2 = rect3.right - rect.left;
                this.leftTaskPercent = rect.width() / fWidth2;
                this.cellLeftTaskPercent = rect3.width() / rect4.width();
            }
            this.cellTopTaskPercent = 1.0f;
            this.topTaskPercent = 1.0f;
            fHeight2 = rect.height();
        } else {
            this.appsStackedVertically = true;
            if ((i5 & 16) != 0) {
                rect4.union(rect);
                this.visualDividerBounds = new Rect(rect.left, rect.bottom, rect2.right, rect2.top);
                if ((i5 & 8) != 0) {
                    this.cellDividerBounds = new Rect(rect3.left, rect3.bottom, rect.right, rect.top);
                } else {
                    this.cellDividerBounds = new Rect(rect.left, rect.bottom, rect3.right, rect3.top);
                }
                f2 = rect2.bottom - rect3.top;
                this.topTaskPercent = rect4.height() / f2;
                this.cellTopTaskPercent = rect3.height() / rect4.height();
            } else {
                rect4.union(rect2);
                this.visualDividerBounds = new Rect(rect.left, rect.bottom, rect2.right, rect2.top);
                if ((i5 & 8) != 0) {
                    this.cellDividerBounds = new Rect(rect3.left, rect3.bottom, rect2.right, rect2.top);
                } else {
                    this.cellDividerBounds = new Rect(rect2.left, rect2.bottom, rect3.right, rect3.top);
                }
                f2 = rect3.bottom - rect.top;
                this.topTaskPercent = rect.height() / f2;
                this.cellTopTaskPercent = rect3.height() / rect4.height();
            }
            this.cellLeftTaskPercent = 1.0f;
            this.leftTaskPercent = 1.0f;
            float f6 = f2;
            fWidth2 = rect.width();
            fHeight2 = f6;
        }
        this.dividerWidthPercent = this.visualDividerBounds.width() / fWidth2;
        this.dividerHeightPercent = this.visualDividerBounds.height() / fHeight2;
        this.cellDividerWidthPercent = this.cellDividerBounds.width() / fWidth2;
        this.cellDividerHeightPercent = this.cellDividerBounds.height() / fHeight2;
    }

    public SplitBounds(Parcel parcel) {
        Parcelable.Creator creator = Rect.CREATOR;
        this.leftTopBounds = (Rect) parcel.readTypedObject(creator);
        this.rightBottomBounds = (Rect) parcel.readTypedObject(creator);
        this.visualDividerBounds = (Rect) parcel.readTypedObject(creator);
        this.topTaskPercent = parcel.readFloat();
        this.leftTaskPercent = parcel.readFloat();
        this.appsStackedVertically = parcel.readBoolean();
        this.leftTopTaskId = parcel.readInt();
        this.rightBottomTaskId = parcel.readInt();
        this.dividerWidthPercent = parcel.readFloat();
        this.dividerHeightPercent = parcel.readFloat();
        this.snapPosition = parcel.readInt();
        this.cellTaskBounds = (Rect) parcel.readTypedObject(creator);
        this.cellTaskId = parcel.readInt();
        this.cellPosition = parcel.readInt();
        this.cellDividerBounds = (Rect) parcel.readTypedObject(creator);
        this.cellTopTaskPercent = parcel.readFloat();
        this.cellLeftTaskPercent = parcel.readFloat();
        this.cellDividerWidthPercent = parcel.readFloat();
        this.cellDividerHeightPercent = parcel.readFloat();
        this.parallelMultiSplit = parcel.readBoolean();
    }
}
