package com.samsung.android.sume.core.buffer;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.types.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class MediaBufferGroup extends MediaBufferBase {
    private static final String TAG = Def.tagOf((Class<?>) MediaBufferGroup.class);
    protected List<MediaBuffer> buffers;
    protected int primaryId;

    public abstract MediaBuffer getPrimaryBuffer();

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ void addExtra(Map map) {
        super.addExtra(map);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containFlags(int[] iArr) {
        return super.containFlags(iArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containsAllExtra(String[] strArr) {
        return super.containsAllExtra(strArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containsAnyExtra(String[] strArr) {
        return super.containsAnyExtra(strArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ boolean containsExtra(String str) {
        return super.containsExtra(str);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, android.os.Parcelable
    public /* bridge */ /* synthetic */ int describeContents() {
        return super.describeContents();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Align getAlign() {
        return super.getAlign();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getChannels() {
        return super.getChannels();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getCols() {
        return super.getCols();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object getExtra(String str) {
        return super.getExtra(str);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object getExtra(String str, Object obj) {
        return super.getExtra(str, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Map getExtra() {
        return super.getExtra();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaFormat getFormat() {
        return super.getFormat();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getRows() {
        return super.getRows();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getScanline() {
        return super.getScanline();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ int getStride() {
        return super.getStride();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object getTypedDataOr(Class cls, Object obj) {
        return super.getTypedDataOr(cls, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ Object removeExtra(String str) {
        return super.removeExtra(str);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ void setExtra(String str, Object obj) {
        super.setExtra(str, obj);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ void setExtra(Map map) {
        super.setExtra(map);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaBuffer setFlags(int[] iArr) {
        return super.setFlags(iArr);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaBuffer setScanline(int i) {
        return super.setScanline(i);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public /* bridge */ /* synthetic */ MediaBuffer setStride(int i) {
        return super.setStride(i);
    }

    public MediaBufferGroup(MediaFormat mediaFormat, List<MediaBuffer> list) {
        super(mediaFormat);
        this.primaryId = -1;
        this.buffers = list;
    }

    public MediaBufferGroup(Parcel parcel) {
        super(parcel);
        this.primaryId = -1;
        ArrayList arrayList = new ArrayList();
        this.buffers = arrayList;
        parcel.readParcelableList(arrayList, GenericMediaBuffer.class.getClassLoader());
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelableList(this.buffers, i);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public Stream<MediaBuffer> stream() {
        return this.buffers.stream();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public long size() {
        return ((Integer) Optional.ofNullable(this.buffers).map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((List) obj).size());
            }
        }).orElse(0)).intValue();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public List<MediaBuffer> getMetaDataBuffers() {
        return (List) this.buffers.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaBufferGroup.lambda$getMetaDataBuffers$0((MediaBuffer) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getMetaDataBuffers$0(MediaBuffer mediaBuffer) {
        return mediaBuffer.getFormat().getMediaType() == MediaType.META;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer getExifBuffer() {
        return this.buffers.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaBufferGroup.lambda$getExifBuffer$1((MediaBuffer) obj);
            }
        }).findAny().orElse(null);
    }

    static /* synthetic */ boolean lambda$getExifBuffer$1(MediaBuffer mediaBuffer) {
        return mediaBuffer.getFormat().getMediaType() == MediaType.META && mediaBuffer.getFormat().contains("exif");
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer getIccBuffer() {
        return this.buffers.stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MediaBufferGroup.lambda$getIccBuffer$2((MediaBuffer) obj);
            }
        }).findAny().orElse(null);
    }

    static /* synthetic */ boolean lambda$getIccBuffer$2(MediaBuffer mediaBuffer) {
        return mediaBuffer.getFormat().getMediaType() == MediaType.META && mediaBuffer.getFormat().contains("icc");
    }

    public MediaBuffer setMetaDataBuffer(List<MediaBuffer> list) {
        return addBuffer(list);
    }

    public MediaBufferGroup addBuffer(MediaBuffer... mediaBufferArr) {
        return addBuffer(Arrays.asList(mediaBufferArr));
    }

    public MediaBufferGroup addBuffer(List<MediaBuffer> list) {
        this.buffers.addAll(list);
        return this;
    }

    public boolean removeBuffer(MediaBuffer mediaBuffer) {
        return this.buffers.remove(mediaBuffer);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.format.Copyable
    public MediaBuffer copy() {
        MediaBufferGroup mediaBufferGroup = (MediaBufferGroup) super.copy();
        mediaBufferGroup.buffers = (List) this.buffers.stream().map(new Function() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MediaBuffer) obj).copy();
            }
        }).collect(Collectors.toList());
        return mediaBufferGroup;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.format.Copyable
    /* renamed from: deepCopy */
    public MediaBuffer deepCopy2() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public MediaBuffer asRef() {
        int incrementAndGet = this.sharedCount.incrementAndGet();
        Log.d(TAG, "use count: " + incrementAndGet + NavigationBarInflaterView.KEY_CODE_START + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
        this.buffers.forEach(new Consumer() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((MediaBuffer) obj).asRef();
            }
        });
        return this;
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferBase, com.samsung.android.sume.core.buffer.MediaBuffer
    public void release() {
        this.buffers.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
        if (this.sharedCount.decrementAndGet() > 0) {
            Log.d(TAG, "use count remained, skip release: " + this.sharedCount.get() + NavigationBarInflaterView.KEY_CODE_START + hashCode() + NavigationBarInflaterView.KEY_CODE_END);
            return;
        }
        super.release();
        try {
            this.buffers.clear();
        } catch (UnsupportedOperationException unused) {
        }
    }

    protected String dataToString() {
        return (String) IntStream.range(0, this.buffers.size()).mapToObj(new IntFunction() { // from class: com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda6
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return MediaBufferGroup.this.m9515xde53324e(i);
            }
        }).collect(Collectors.joining(ShaderAssembler.NEWLINE));
    }

    /* renamed from: lambda$dataToString$3$com-samsung-android-sume-core-buffer-MediaBufferGroup, reason: not valid java name */
    /* synthetic */ String m9515xde53324e(int i) {
        return NavigationBarInflaterView.KEY_CODE_START + i + "-th)" + this.buffers.get(i).contentToString();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBuffer
    public String contentToString(Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(Def.taglnOf(obj));
        sb.append(Def.contentToStringln("    ", "format=" + ((String) Optional.ofNullable(this.format).map(new GenericMediaBuffer$$ExternalSyntheticLambda1()).orElse("n/a")), "extra=" + Collections.singletonList(this.extra), "data[#" + this.buffers.size() + "]\n" + dataToString()));
        return sb.toString();
    }

    public int getPrimaryId() {
        return this.primaryId;
    }
}
