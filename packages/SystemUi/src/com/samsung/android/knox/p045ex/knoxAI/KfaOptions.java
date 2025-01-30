package com.samsung.android.knox.p045ex.knoxAI;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KfaOptions implements Parcelable {
    public static final Parcelable.Creator<KfaOptions> CREATOR = new Parcelable.Creator<KfaOptions>() { // from class: com.samsung.android.knox.ex.knoxAI.KfaOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final KfaOptions createFromParcel(Parcel parcel) {
            return new KfaOptions(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final KfaOptions[] newArray(int i) {
            return new KfaOptions[i];
        }

        @Override // android.os.Parcelable.Creator
        public final KfaOptions createFromParcel(Parcel parcel) {
            return new KfaOptions(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final KfaOptions[] newArray(int i) {
            return new KfaOptions[i];
        }
    };
    public boolean allowReshape;
    public int compUnit;
    public int cpuThreadCount;
    public SharedMemory dataShared;
    public int execType;

    /* renamed from: fd */
    public FileDescriptor f483fd;
    public long fd_StartOffSet;
    public int flag;
    public ArrayList<String> inputNames;
    public InputShapeVector[] input_shape;
    public int mType;
    public int mode;
    public int modelInputType;
    public int model_buffer_len;
    public byte[] model_buffer_ptr;
    public String model_file;
    public String model_name;
    public int model_package_buffer_len;
    public byte[] model_package_buffer_ptr;
    public ArrayList<String> outputNames;
    public String weights_file;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class InputShapeVector implements Parcelable {
        public final Parcelable.Creator<InputShapeVector> CREATOR = new Parcelable.Creator<InputShapeVector>() { // from class: com.samsung.android.knox.ex.knoxAI.KfaOptions.InputShapeVector.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public final InputShapeVector[] newArray(int i) {
                return new InputShapeVector[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public final InputShapeVector createFromParcel(Parcel parcel) {
                return KfaOptions.this.new InputShapeVector(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final InputShapeVector[] newArray(int i) {
                return new InputShapeVector[i];
            }
        };
        public int[] input;

        public InputShapeVector(Parcel parcel) {
            this.input = parcel.createIntArray();
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final void readFromParcel(Parcel parcel) {
            parcel.readIntArray(this.input);
        }

        public final String toString() {
            return "IpShapeVec [" + Arrays.toString(this.input) + "]";
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeIntArray(this.input);
        }
    }

    public KfaOptions() {
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final SharedMemory getDataShared() {
        return this.dataShared;
    }

    public final int getExecType() {
        return this.execType;
    }

    public final FileDescriptor getFd() {
        return this.f483fd;
    }

    public final ArrayList<String> getInputNames() {
        return this.inputNames;
    }

    public final int getModelBufferLen() {
        return this.model_buffer_len;
    }

    public final byte[] getModelBufferPtr() {
        return this.model_buffer_ptr;
    }

    public final String getModelFile() {
        return this.model_file;
    }

    public final int getModelInputType() {
        return this.modelInputType;
    }

    public final String getModelName() {
        return this.model_name;
    }

    public final int getModelPackageBufferLen() {
        return this.model_package_buffer_len;
    }

    public final byte[] getModelPackageBufferPtr() {
        return this.model_package_buffer_ptr;
    }

    public final ArrayList<String> getOutputNames() {
        return this.outputNames;
    }

    public final long getStartOffSet() {
        return this.fd_StartOffSet;
    }

    public final String getWeightsFile() {
        return this.weights_file;
    }

    public final void readFromParcel(Parcel parcel) {
        this.execType = parcel.readInt();
        this.compUnit = parcel.readInt();
        this.mType = parcel.readInt();
        String[] strArr = new String[parcel.readInt()];
        parcel.readStringArray(strArr);
        ArrayList<String> arrayList = new ArrayList<>();
        this.inputNames = arrayList;
        Collections.addAll(arrayList, strArr);
        String[] strArr2 = new String[parcel.readInt()];
        parcel.readStringArray(strArr2);
        ArrayList<String> arrayList2 = new ArrayList<>();
        this.outputNames = arrayList2;
        Collections.addAll(arrayList2, strArr2);
        this.modelInputType = parcel.readInt();
        this.model_file = parcel.readString();
        int i = this.modelInputType;
        if (i == 0) {
            this.weights_file = parcel.readString();
            return;
        }
        if (i == 1) {
            this.fd_StartOffSet = parcel.readLong();
            this.f483fd = parcel.readFileDescriptor().getFileDescriptor();
            return;
        }
        if (i != 2) {
            if (i == 3) {
                this.dataShared = (SharedMemory) parcel.readParcelable(SharedMemory.class.getClassLoader());
                return;
            }
            return;
        }
        int readInt = parcel.readInt();
        this.model_package_buffer_len = readInt;
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.model_package_buffer_ptr = bArr;
            parcel.readByteArray(bArr);
        }
    }

    public final void setCompUnit(int i) {
        this.compUnit = i;
    }

    public final void setDataShared(SharedMemory sharedMemory) {
        this.dataShared = sharedMemory;
    }

    public final void setExecType(int i) {
        this.execType = i;
    }

    public final void setFd(FileDescriptor fileDescriptor) {
        this.f483fd = fileDescriptor;
    }

    public final void setInputNames(ArrayList<String> arrayList) {
        this.inputNames = arrayList;
    }

    public final void setModelBufferLen(int i) {
        this.model_buffer_len = i;
    }

    public final void setModelBufferPtr(byte[] bArr) {
        this.model_buffer_ptr = bArr;
    }

    public final void setModelFile(String str) {
        this.model_file = str;
    }

    public final void setModelInputType(int i) {
        this.modelInputType = i;
    }

    public final void setModelName(String str) {
        this.model_name = str;
    }

    public final void setModelPackageBufferLen(int i) {
        this.model_package_buffer_len = i;
    }

    public final void setModelPackageBufferPtr(byte[] bArr) {
        this.model_package_buffer_ptr = bArr;
    }

    public final void setOutputNames(ArrayList<String> arrayList) {
        this.outputNames = arrayList;
    }

    public final void setStartOffset(long j) {
        this.fd_StartOffSet = j;
    }

    public final void setWeightsFile(String str) {
        this.weights_file = str;
    }

    public final void setmType(int i) {
        this.mType = i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("mdl[");
        sb.append(this.model_file);
        sb.append("], fl [");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.weights_file, "]");
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.execType);
        parcel.writeInt(this.compUnit);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.inputNames.size());
        ArrayList<String> arrayList = this.inputNames;
        parcel.writeStringArray((String[]) arrayList.toArray(new String[arrayList.size()]));
        parcel.writeInt(this.outputNames.size());
        ArrayList<String> arrayList2 = this.outputNames;
        parcel.writeStringArray((String[]) arrayList2.toArray(new String[arrayList2.size()]));
        parcel.writeInt(this.modelInputType);
        String str = this.model_file;
        if (str == null) {
            parcel.writeString("");
        } else {
            parcel.writeString(str);
        }
        int i2 = this.modelInputType;
        if (i2 == 0) {
            parcel.writeString(this.weights_file);
            return;
        }
        if (i2 == 1) {
            parcel.writeLong(this.fd_StartOffSet);
            parcel.writeFileDescriptor(this.f483fd);
        } else if (i2 != 2) {
            if (i2 == 3) {
                parcel.writeParcelable(this.dataShared, 0);
            }
        } else {
            parcel.writeInt(this.model_package_buffer_len);
            if (this.model_package_buffer_len > 0) {
                parcel.writeByteArray(this.model_package_buffer_ptr);
            }
        }
    }

    public KfaOptions(Parcel parcel) {
        readFromParcel(parcel);
    }
}
