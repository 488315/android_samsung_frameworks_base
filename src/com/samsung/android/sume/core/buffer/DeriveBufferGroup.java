package com.samsung.android.sume.core.buffer;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sume.core.Def;
import java.util.List;

/* loaded from: classes6.dex */
public class DeriveBufferGroup extends MediaBufferGroup {
    private MediaBuffer primaryBuffer;
    private static final String TAG = Def.tagOf((Class<?>) DeriveBufferGroup.class);
    public static final Parcelable.Creator<DeriveBufferGroup> CREATOR = new Parcelable.Creator<DeriveBufferGroup>() { // from class: com.samsung.android.sume.core.buffer.DeriveBufferGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeriveBufferGroup createFromParcel(Parcel parcel) {
            return new DeriveBufferGroup(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeriveBufferGroup[] newArray(int i) {
            return new DeriveBufferGroup[i];
        }
    };

    public DeriveBufferGroup(MediaBuffer mediaBuffer, List<MediaBuffer> list) {
        super(mediaBuffer.getFormat(), list);
        this.primaryBuffer = mediaBuffer;
    }

    public DeriveBufferGroup(Parcel parcel) {
        super(parcel);
        this.primaryBuffer = (MediaBuffer) parcel.readParcelable(GenericMediaBuffer.class.getClassLoader());
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBufferBase, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.primaryBuffer, i);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> MediaBuffer convertTo(Class<T> cls) {
        return this.primaryBuffer.convertTo(cls);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public Class<?> getDataClass() {
        return this.primaryBuffer.getDataClass();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> T getData() {
        return (T) this.primaryBuffer.getData();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> T getTypedData(Class<T> cls) {
        return (T) this.primaryBuffer.getTypedData(cls);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer asRef() {
        this.primaryBuffer.asRef();
        return super.asRef();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public void release() {
        super.release();
        this.primaryBuffer.release();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.format.Copyable
    public MediaBuffer copy() {
        DeriveBufferGroup deriveBufferGroup = (DeriveBufferGroup) super.copy();
        deriveBufferGroup.primaryBuffer = this.primaryBuffer.copy();
        return deriveBufferGroup;
    }

    public String toString() {
        return contentToString();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(Def.taglnOf(obj));
        StringBuilder sb2 = new StringBuilder("primary-buffer=");
        MediaBuffer mediaBuffer = this.primaryBuffer;
        sb2.append(mediaBuffer.contentToString(mediaBuffer));
        sb.append(Def.contentToStringln("    ", sb2.toString(), "derivative-buffers=" + super.contentToString(MediaBufferGroup.class)));
        return sb.toString();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString() {
        return contentToString(this);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup
    public MediaBuffer getPrimaryBuffer() {
        return this.primaryBuffer;
    }
}
