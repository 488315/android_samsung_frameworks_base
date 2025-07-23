package com.samsung.android.sume.core.message;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda4;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class ContentsInfo implements Parcelable {
    private final Map<String, Object> data;
    private static final String TAG = Def.tagOf((Class<?>) ContentsInfo.class);
    public static final Parcelable.Creator<ContentsInfo> CREATOR = new Parcelable.Creator<ContentsInfo>() { // from class: com.samsung.android.sume.core.message.ContentsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentsInfo createFromParcel(Parcel parcel) {
            return new ContentsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentsInfo[] newArray(int i) {
            return new ContentsInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentsInfo() {
        this.data = new HashMap();
        setStatusCode(0);
    }

    public ContentsInfo(ContentValues contentValues) {
        this.data = (Map) contentValues.valueSet().stream().collect(Collectors.toMap(new MediaBufferBase$$ExternalSyntheticLambda3(), new MediaBufferBase$$ExternalSyntheticLambda4()));
        setStatusCode(0);
    }

    protected ContentsInfo(Parcel parcel) {
        this();
        parcel.readMap(this.data, null);
    }

    ContentsInfo(Message message) {
        this.data = message.get();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Def.require(this.data != null);
        parcel.writeMap(this.data);
    }

    public int getId() {
        return getAsInteger("id");
    }

    public ContentsInfo setId(int i) {
        this.data.put("id", Integer.valueOf(i));
        return this;
    }

    public int getContentsId() {
        return getAsInteger(Message.KEY_CONTENTS_ID);
    }

    public ContentsInfo setContentsId(int i) {
        this.data.put(Message.KEY_CONTENTS_ID, Integer.valueOf(i));
        return this;
    }

    public boolean isStatusOk() {
        return Message.isOk(getStatusCode());
    }

    public boolean isStatusWarn() {
        return Message.isWarn(getStatusCode());
    }

    public boolean isStatusError() {
        return Message.isError(getStatusCode());
    }

    public int getStatusCode() {
        return getAsInteger(Message.KEY_STATUS);
    }

    public ContentsInfo setStatusCode(int i) {
        this.data.put(Message.KEY_STATUS, Integer.valueOf(i));
        return this;
    }

    public int getWidth() {
        return getAsInteger("width");
    }

    public ContentsInfo setWidth(int i) {
        this.data.put("width", Integer.valueOf(i));
        return this;
    }

    public int getHeight() {
        return getAsInteger("height");
    }

    public ContentsInfo setHeight(int i) {
        this.data.put("height", Integer.valueOf(i));
        return this;
    }

    public int getRotation() {
        if (this.data.containsKey("rotation-degrees")) {
            return getAsInteger("rotation-degrees");
        }
        return 0;
    }

    public ContentsInfo setRotation(int i) {
        this.data.put("rotation-degrees", Integer.valueOf(i));
        return this;
    }

    public String getOutPath() {
        return getAsString(Message.KEY_OUT_FILE);
    }

    public ContentsInfo setOutPath(String str) {
        this.data.put(Message.KEY_OUT_FILE, str);
        return this;
    }

    public int getPosition() {
        return getAsInteger("position");
    }

    public ContentsInfo setPosition(int i) {
        this.data.put("position", Integer.valueOf(i));
        return this;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(getAsLong(Message.KEY_START_TIME_MS), TimeUnit.MILLISECONDS);
    }

    public ContentsInfo setStartTime(TimeUnit timeUnit, long j) {
        this.data.put(Message.KEY_START_TIME_MS, Long.valueOf(timeUnit.toMillis(j)));
        return this;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(getAsLong(Message.KEY_END_TIME_MS), TimeUnit.MILLISECONDS);
    }

    public ContentsInfo setEndTime(TimeUnit timeUnit, long j) {
        this.data.put(Message.KEY_END_TIME_MS, Long.valueOf(timeUnit.toMillis(j)));
        return this;
    }

    public long getAudioDuration(TimeUnit timeUnit) {
        return timeUnit.convert(getAsLong("last-audio-timestamp-us"), TimeUnit.MICROSECONDS);
    }

    public long getVideoDuration(TimeUnit timeUnit) {
        return timeUnit.convert(getAsLong("last-video-timestamp-us"), TimeUnit.MICROSECONDS);
    }

    public long getProcessingTime(TimeUnit timeUnit) {
        return timeUnit.convert(getAsLongOr(Message.KEY_END_TIME_MS, 0L) - getAsLongOr(Message.KEY_START_TIME_MS, 0L), TimeUnit.MILLISECONDS);
    }

    public ContentsInfo setDuration(TimeUnit timeUnit, long j) {
        this.data.put("duration", Long.valueOf(timeUnit.toMillis(j)));
        return this;
    }

    public long getDuration(TimeUnit timeUnit) {
        return timeUnit.convert(getAsLongOr("duration", -1L), TimeUnit.MILLISECONDS);
    }

    public int getWholeFrames() {
        return getAsIntegerOr(Message.KEY_WHOLE_FRAMES, -1);
    }

    public ContentsInfo setWholeFrames(int i) {
        this.data.put(Message.KEY_WHOLE_FRAMES, Integer.valueOf(i));
        return this;
    }

    public int getProcessedFrames() {
        return getAsIntegerOr(Message.KEY_PROCESSED_FRAMES, 0);
    }

    public ContentsInfo setProcessedFrames(int i) {
        this.data.put(Message.KEY_PROCESSED_FRAMES, Integer.valueOf(i));
        return this;
    }

    public boolean isFullyProcessed() {
        return getWholeFrames() <= getProcessedFrames();
    }

    public ContentsInfo setOriginalDataType(DataType dataType) {
        this.data.put("original-data-type", dataType);
        return this;
    }

    public DataType getOriginalDataType() {
        return (DataType) this.data.getOrDefault("original-data-type", DataType.NONE);
    }

    public ContentsInfo setOriginalColorFormat(ColorFormat colorFormat) {
        this.data.put("original-color-format", colorFormat);
        return this;
    }

    public ColorFormat getOriginalColorFormat() {
        return (ColorFormat) this.data.getOrDefault("original-color-format", ColorFormat.NONE);
    }

    public ContentsInfo compose(ContentsInfo contentsInfo) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public ContentsInfo join(ContentsInfo contentsInfo) {
        this.data.putAll(contentsInfo.data);
        return this;
    }

    public Stream<Map.Entry<String, Object>> stream() {
        return this.data.entrySet().stream();
    }

    public ContentsInfo setData(String str, Object obj) {
        this.data.put(str, obj);
        return this;
    }

    public <T> T getData(String str) {
        return (T) this.data.get(str);
    }

    public <T> T getDataOr(String str, T t) {
        return (T) this.data.getOrDefault(str, t);
    }

    public int getAsInteger(String str) {
        return ((Integer) getData(str)).intValue();
    }

    public int getAsIntegerOr(String str, int i) {
        return ((Integer) getDataOr(str, Integer.valueOf(i))).intValue();
    }

    public long getAsLong(String str) {
        return ((Long) getData(str)).longValue();
    }

    public long getAsLongOr(String str, long j) {
        return ((Long) getDataOr(str, Long.valueOf(j))).longValue();
    }

    public String getAsString(String str) {
        return (String) getData(str);
    }

    public String getAsStringOr(String str, String str2) {
        return (String) getDataOr(str, str2);
    }

    public byte getAsByte(String str) {
        return ((Byte) getData(str)).byteValue();
    }

    public byte getAsByteOr(String str, byte b) {
        return ((Byte) getDataOr(str, Byte.valueOf(b))).byteValue();
    }

    public byte[] getAsByteArray(String str) {
        return (byte[]) getData(str);
    }

    public byte[] getAsByteArrayOr(String str, byte[] bArr) {
        return (byte[]) getDataOr(str, bArr);
    }

    public boolean getAsBoolean(String str) {
        return ((Boolean) getData(str)).booleanValue();
    }

    public boolean getAsBooleanOr(String str, boolean z) {
        return ((Boolean) getDataOr(str, Boolean.valueOf(z))).booleanValue();
    }

    public double getAsDouble(String str) {
        return ((Double) getData(str)).doubleValue();
    }

    public double getAsDoubleOr(String str, double d) {
        return ((Double) getDataOr(str, Double.valueOf(d))).doubleValue();
    }

    public float getAsFloat(String str) {
        return ((Float) getData(str)).floatValue();
    }

    public float getAsFloatOr(String str, float f) {
        return ((Float) getDataOr(str, Float.valueOf(f))).floatValue();
    }

    public short getAsShort(String str) {
        return ((Short) getData(str)).shortValue();
    }

    public short getAsShortOr(String str, short s) {
        return ((Short) getDataOr(str, Short.valueOf(s))).shortValue();
    }

    public static ContentsInfo wrap(Message message) {
        return new ContentsInfo(message);
    }

    public String toString() {
        return (String) this.data.keySet().stream().map(new Function() { // from class: com.samsung.android.sume.core.message.ContentsInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContentsInfo.this.m9587x3d006615((String) obj);
            }
        }).collect(Collectors.joining(", ", "{", "}"));
    }

    /* renamed from: lambda$toString$0$com-samsung-android-sume-core-message-ContentsInfo, reason: not valid java name */
    /* synthetic */ String m9587x3d006615(String str) {
        return str + "=" + this.data.get(str);
    }
}
