package com.samsung.android.knox.knoxai;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes5.dex */
public class TaProcessBuffer implements Parcelable {
    public static final Parcelable.Creator<TaProcessBuffer> CREATOR = new Parcelable.Creator<TaProcessBuffer>() { // from class: com.samsung.android.knox.knoxai.TaProcessBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaProcessBuffer createFromParcel(Parcel in) {
            return new TaProcessBuffer(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TaProcessBuffer[] newArray(int size) {
            return new TaProcessBuffer[size];
        }
    };
    private byte[] buffer;
    private int processBufferType;

    public enum ProcessType {
        RETURN_PLAIN_FACTORY_MODEL_KEY,
        SET_PROV,
        GET_CERT;

        public int getValue() {
            return ordinal();
        }
    }

    public enum ProcessBufferType {
        ENCRYPTED_MODEL_ROOT_KEY,
        WRAPPED_DEK,
        WRAPPED_DRK_CERT,
        ENCRYPTED_KEY_BLOB,
        WRAPPED_DRK_SERVICE_KEY,
        OUTPUT_RESULT,
        DEFAULT;

        public int getValue() {
            return ordinal();
        }
    }

    public TaProcessBuffer() {
    }

    private TaProcessBuffer(Parcel in) {
        readFromParcel(in);
    }

    public void setProcessBufferType(ProcessBufferType type) {
        this.processBufferType = type.getValue();
    }

    public int getProcessBufferType() {
        return this.processBufferType;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public int getBufferLength() {
        byte[] bArr = this.buffer;
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(this.processBufferType);
        out.writeByteArray(this.buffer);
    }

    public void readFromParcel(Parcel in) {
        this.processBufferType = in.readInt();
        this.buffer = in.createByteArray();
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }
}
