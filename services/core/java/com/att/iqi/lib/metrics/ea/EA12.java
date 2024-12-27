package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;

import com.att.iqi.lib.Metric;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class EA12 extends Metric {
    private static final byte FLAG_CMAS = 2;
    private static final byte FLAG_ETWS = 1;
    private static final byte FLAG_HAS_GEO = Byte.MIN_VALUE;
    private final int lCid;
    private int lCmasCategory;
    private int lCmasCertainty;
    private int lCmasMessageClass;
    private int lCmasResponseType;
    private int lCmasSeverity;
    private int lCmasUrgency;
    private int lEtwsWarningType;
    private final int lGeographicalScope;
    private final int lLac;
    private final int lSerialNumber;
    private final int lServiceCategory;
    private long llReceivedTimeMillis;
    private final String szGeometries;
    private final String szLanguage;
    private final String szPlmn;
    private final byte ucFlags;
    public static final Metric.ID ID = new Metric.ID("EA12");
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() { // from class: com.att.iqi.lib.metrics.ea.EA12.1
                @Override // android.os.Parcelable.Creator
                public EA12 createFromParcel(Parcel parcel) {
                    return new EA12(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public EA12[] newArray(int i) {
                    return new EA12[i];
                }
            };

    public EA12(Parcel parcel) {
        super(parcel);
        parcel.readInt();
        this.lGeographicalScope = parcel.readInt();
        this.lSerialNumber = parcel.readInt();
        this.lServiceCategory = parcel.readInt();
        this.lLac = parcel.readInt();
        this.lCid = parcel.readInt();
        this.ucFlags = parcel.readByte();
        byte flagsWithoutGeo = flagsWithoutGeo();
        if (flagsWithoutGeo == 1) {
            this.lEtwsWarningType = parcel.readInt();
        } else if (flagsWithoutGeo == 2) {
            this.lCmasMessageClass = parcel.readInt();
            this.lCmasCategory = parcel.readInt();
            this.lCmasResponseType = parcel.readInt();
            this.lCmasSeverity = parcel.readInt();
            this.lCmasUrgency = parcel.readInt();
            this.lCmasCertainty = parcel.readInt();
        }
        this.szPlmn = parcel.readString();
        this.szLanguage = parcel.readString();
        if (!hasGeo()) {
            this.szGeometries = "";
        } else {
            this.llReceivedTimeMillis = parcel.readLong();
            this.szGeometries = parcel.readString();
        }
    }

    public EA12(Parcelable parcelable) {
        Parcel obtain = Parcel.obtain();
        byte b = 0;
        parcelable.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        obtain.readInt();
        this.lGeographicalScope = obtain.readInt();
        this.lSerialNumber = obtain.readInt();
        this.szPlmn = obtain.readString();
        this.lLac = obtain.readInt();
        this.lCid = obtain.readInt();
        this.lServiceCategory = obtain.readInt();
        this.szLanguage = obtain.readString();
        obtain.readInt();
        obtain.readString();
        obtain.readInt();
        int readInt = obtain.readInt();
        if (readInt == 67) {
            this.lCmasMessageClass = obtain.readInt();
            this.lCmasCategory = obtain.readInt();
            this.lCmasResponseType = obtain.readInt();
            this.lCmasSeverity = obtain.readInt();
            this.lCmasUrgency = obtain.readInt();
            this.lCmasCertainty = obtain.readInt();
            b = 2;
        } else if (readInt == 69) {
            this.lEtwsWarningType = obtain.readInt();
            obtain.readInt();
            obtain.readInt();
            obtain.readInt();
            obtain.createByteArray();
            b = 1;
        }
        long readLong = obtain.readLong();
        String readString = obtain.readString();
        if (readLong == 0 || readString == null) {
            this.szGeometries = "";
        } else {
            b = (byte) (b | FLAG_HAS_GEO);
            this.llReceivedTimeMillis = readLong;
            this.szGeometries = readString;
        }
        this.ucFlags = b;
        obtain.recycle();
    }

    private byte flagsWithoutGeo() {
        return (byte) (this.ucFlags & (-129));
    }

    private boolean hasGeo() {
        return (this.ucFlags & FLAG_HAS_GEO) != 0;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.putInt(this.lServiceCategory);
        byteBuffer.putInt(this.lSerialNumber);
        byteBuffer.putInt(this.lGeographicalScope);
        byteBuffer.putInt(this.lLac);
        byteBuffer.putInt(this.lCid);
        byteBuffer.put(this.ucFlags);
        byte flagsWithoutGeo = flagsWithoutGeo();
        if (flagsWithoutGeo == 1) {
            byteBuffer.putInt(this.lEtwsWarningType);
        } else if (flagsWithoutGeo == 2) {
            byteBuffer.putInt(this.lCmasMessageClass);
            byteBuffer.putInt(this.lCmasCategory);
            byteBuffer.putInt(this.lCmasResponseType);
            byteBuffer.putInt(this.lCmasSeverity);
            byteBuffer.putInt(this.lCmasUrgency);
            byteBuffer.putInt(this.lCmasCertainty);
        }
        stringOut(byteBuffer, this.szPlmn);
        stringOut(byteBuffer, this.szLanguage);
        if (hasGeo()) {
            byteBuffer.putLong(this.llReceivedTimeMillis);
            stringOut(byteBuffer, this.szGeometries);
        }
        return byteBuffer.position();
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.lGeographicalScope);
        parcel.writeInt(this.lSerialNumber);
        parcel.writeInt(this.lServiceCategory);
        parcel.writeInt(this.lLac);
        parcel.writeInt(this.lCid);
        parcel.writeByte(this.ucFlags);
        byte flagsWithoutGeo = flagsWithoutGeo();
        if (flagsWithoutGeo == 1) {
            parcel.writeInt(this.lEtwsWarningType);
        } else if (flagsWithoutGeo == 2) {
            parcel.writeInt(this.lCmasMessageClass);
            parcel.writeInt(this.lCmasCategory);
            parcel.writeInt(this.lCmasResponseType);
            parcel.writeInt(this.lCmasSeverity);
            parcel.writeInt(this.lCmasUrgency);
            parcel.writeInt(this.lCmasCertainty);
        }
        parcel.writeString(this.szPlmn);
        parcel.writeString(this.szLanguage);
        if (hasGeo()) {
            parcel.writeLong(this.llReceivedTimeMillis);
            parcel.writeString(this.szGeometries);
        }
    }
}
