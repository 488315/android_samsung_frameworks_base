package com.samsung.android.sume.core.buffer;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import com.samsung.android.sume.core.Def;
import java.util.List;

/* loaded from: classes4.dex */
public class DeriveBufferGroup extends MediaBufferGroup {
    private MediaBuffer primaryBuffer;
    private static final String TAG = Def.tagOf((Class<?>) DeriveBufferGroup.class);
    public static final Parcelable.Creator<DeriveBufferGroup> CREATOR = new Parcelable.Creator<DeriveBufferGroup>() { // from class: com.samsung.android.sume.core.buffer.DeriveBufferGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeriveBufferGroup createFromParcel(Parcel in) {
            return new DeriveBufferGroup(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DeriveBufferGroup[] newArray(int size) {
            return new DeriveBufferGroup[size];
        }
    };

    public DeriveBufferGroup(MediaBuffer primaryBuffer, List<MediaBuffer> buffers) {
        super(primaryBuffer.getFormat(), buffers);
        this.primaryBuffer = primaryBuffer;
    }

    public DeriveBufferGroup(Parcel in) {
        super(in);
        this.primaryBuffer = (MediaBuffer) in.readParcelable(GenericMediaBuffer.class.getClassLoader());
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBufferBase, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.primaryBuffer, flags);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> MediaBuffer convertTo(Class<T> clazz) {
        return this.primaryBuffer.convertTo(clazz);
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
        DeriveBufferGroup copied = (DeriveBufferGroup) super.copy();
        copied.primaryBuffer = this.primaryBuffer.copy();
        return copied;
    }

    public String toString() {
        return contentToString();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup, com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString(Object obj) {
        StringBuilder append = new StringBuilder().append(Def.taglnOf(obj));
        StringBuilder append2 = new StringBuilder().append("primary-buffer=");
        MediaBuffer mediaBuffer = this.primaryBuffer;
        return append.append(Def.contentToStringln("    ", append2.append(mediaBuffer.contentToString(mediaBuffer)).toString(), "derivative-buffers=" + super.contentToString(MediaBufferGroup.class))).toString();
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
