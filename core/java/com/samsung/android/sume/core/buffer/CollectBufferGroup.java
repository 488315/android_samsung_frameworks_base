package com.samsung.android.sume.core.buffer;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.types.MediaType;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class CollectBufferGroup extends MediaBufferGroup {
    private static final String TAG = Def.tagOf((Class<?>) CollectBufferGroup.class);
    public static final Parcelable.Creator<CollectBufferGroup> CREATOR =
            new Parcelable.Creator<
                    CollectBufferGroup>() { // from class:
                                            // com.samsung.android.sume.core.buffer.CollectBufferGroup.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CollectBufferGroup createFromParcel(Parcel in) {
                    return new CollectBufferGroup(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public CollectBufferGroup[] newArray(int size) {
                    return new CollectBufferGroup[size];
                }
            };

    public CollectBufferGroup(int primaryId, List<MediaBuffer> buffers) {
        super(
                buffers.isEmpty()
                        ? MediaFormat.of(MediaType.NONE, new Object[0])
                        : buffers.get(primaryId).getFormat(),
                buffers);
        this.primaryId = primaryId;
        this.extra.putAll(getPrimaryBuffer().getExtra());
    }

    public CollectBufferGroup(Parcel in) {
        super(in);
        this.primaryId = in.readInt();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup,
              // com.samsung.android.sume.core.buffer.MediaBufferBase, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.primaryId);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> MediaBuffer convertTo(final Class<T> clazz) {
        return MediaBuffer.groupOf(
                this.primaryId,
                (List<MediaBuffer>)
                        this.buffers.stream()
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.sume.core.buffer.CollectBufferGroup$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                MediaBuffer convertTo;
                                                convertTo = ((MediaBuffer) obj).convertTo(clazz);
                                                return convertTo;
                                            }
                                        })
                                .collect(Collectors.toList()));
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> T getData() {
        return (T) this.buffers.get(this.primaryId).getData();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public Class<?> getDataClass() {
        if (this.buffers.isEmpty()) {
            return null;
        }
        return this.buffers.get(this.primaryId).getDataClass();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public <T> T getTypedData(Class<T> cls) {
        return (T) this.buffers.get(this.primaryId).getTypedData(cls);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferGroup
    public MediaBuffer getPrimaryBuffer() {
        return this.buffers.get(this.primaryId);
    }

    public String toString() {
        return contentToString();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString() {
        return super.contentToString(this);
    }
}
