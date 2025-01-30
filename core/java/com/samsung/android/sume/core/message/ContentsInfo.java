package com.samsung.android.sume.core.message;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda1;
import com.samsung.android.sume.core.buffer.MediaBufferBase$$ExternalSyntheticLambda2;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class ContentsInfo implements Parcelable {
  private final Map<String, Object> data;
  private static final String TAG = Def.tagOf((Class<?>) ContentsInfo.class);
  public static final Parcelable.Creator<ContentsInfo> CREATOR =
      new Parcelable.Creator<
          ContentsInfo>() { // from class: com.samsung.android.sume.core.message.ContentsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentsInfo createFromParcel(Parcel in) {
          return new ContentsInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentsInfo[] newArray(int size) {
          return new ContentsInfo[size];
        }
      };

  public ContentsInfo() {
    this.data = new HashMap();
    setStatusCode(0);
  }

  public ContentsInfo(ContentValues contentValues) {
    this.data =
        (Map)
            contentValues.valueSet().stream()
                .collect(
                    Collectors.toMap(
                        new MediaBufferBase$$ExternalSyntheticLambda1(),
                        new MediaBufferBase$$ExternalSyntheticLambda2()));
    setStatusCode(0);
  }

  protected ContentsInfo(Parcel in) {
    this();
    in.readMap(this.data, null);
  }

  ContentsInfo(Message message) {
    this.data = message.get();
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    Def.require(this.data != null);
    dest.writeMap(this.data);
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public int getId() {
    return getAsInteger("id");
  }

  public ContentsInfo setId(int id) {
    this.data.put("id", Integer.valueOf(id));
    return this;
  }

  public int getContentsId() {
    return getAsInteger(Message.KEY_CONTENTS_ID);
  }

  public ContentsInfo setContentsId(int id) {
    this.data.put(Message.KEY_CONTENTS_ID, Integer.valueOf(id));
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

  public ContentsInfo setStatusCode(int code) {
    this.data.put(Message.KEY_STATUS, Integer.valueOf(code));
    return this;
  }

  public int getWidth() {
    return getAsInteger("width");
  }

  public ContentsInfo setWidth(int width) {
    this.data.put("width", Integer.valueOf(width));
    return this;
  }

  public int getHeight() {
    return getAsInteger("height");
  }

  public ContentsInfo setHeight(int width) {
    this.data.put("height", Integer.valueOf(width));
    return this;
  }

  public int getRotation() {
    if (this.data.containsKey("rotation-degrees")) {
      return getAsInteger("rotation-degrees");
    }
    return 0;
  }

  public ContentsInfo setRotation(int rotation) {
    this.data.put("rotation-degrees", Integer.valueOf(rotation));
    return this;
  }

  public String getOutPath() {
    return getAsString(Message.KEY_OUT_FILE);
  }

  public ContentsInfo setOutPath(String outPath) {
    this.data.put(Message.KEY_OUT_FILE, outPath);
    return this;
  }

  public int getPosition() {
    return getAsInteger("position");
  }

  public ContentsInfo setPosition(int position) {
    this.data.put("position", Integer.valueOf(position));
    return this;
  }

  public long getStartTime(TimeUnit timeUnit) {
    long time = getAsLong(Message.KEY_START_TIME_MS);
    return timeUnit.convert(time, TimeUnit.MILLISECONDS);
  }

  public ContentsInfo setStartTime(TimeUnit timeUnit, long time) {
    this.data.put(Message.KEY_START_TIME_MS, Long.valueOf(timeUnit.toMillis(time)));
    return this;
  }

  public long getEndTime(TimeUnit timeUnit) {
    long time = getAsLong(Message.KEY_END_TIME_MS);
    return timeUnit.convert(time, TimeUnit.MILLISECONDS);
  }

  public ContentsInfo setEndTime(TimeUnit timeUnit, long time) {
    this.data.put(Message.KEY_END_TIME_MS, Long.valueOf(timeUnit.toMillis(time)));
    return this;
  }

  public long getAudioDuration(TimeUnit timeUnit) {
    long timestampUs = getAsLong("last-audio-timestamp-us");
    return timeUnit.convert(timestampUs, TimeUnit.MICROSECONDS);
  }

  public long getVideoDuration(TimeUnit timeUnit) {
    long timestampUs = getAsLong("last-video-timestamp-us");
    return timeUnit.convert(timestampUs, TimeUnit.MICROSECONDS);
  }

  public long getProcessingTime(TimeUnit timeUnit) {
    long duration =
        getAsLongOr(Message.KEY_END_TIME_MS, 0L) - getAsLongOr(Message.KEY_START_TIME_MS, 0L);
    return timeUnit.convert(duration, TimeUnit.MILLISECONDS);
  }

  public ContentsInfo setDuration(TimeUnit timeUnit, long time) {
    this.data.put("duration", Long.valueOf(timeUnit.toMillis(time)));
    return this;
  }

  public long getDuration(TimeUnit timeUnit) {
    return timeUnit.convert(getAsLongOr("duration", -1L), TimeUnit.MILLISECONDS);
  }

  public int getWholeFrames() {
    return getAsIntegerOr(Message.KEY_WHOLE_FRAMES, -1);
  }

  public ContentsInfo setWholeFrames(int wholeFrames) {
    this.data.put(Message.KEY_WHOLE_FRAMES, Integer.valueOf(wholeFrames));
    return this;
  }

  public int getProcessedFrames() {
    return getAsIntegerOr(Message.KEY_PROCESSED_FRAMES, 0);
  }

  public ContentsInfo setProcessedFrames(int processedFrames) {
    this.data.put(Message.KEY_PROCESSED_FRAMES, Integer.valueOf(processedFrames));
    return this;
  }

  public boolean isFullyProcessed() {
    return getWholeFrames() <= getProcessedFrames();
  }

  public ContentsInfo setOriginalDataType(DataType inputDataType) {
    this.data.put("original-data-type", inputDataType);
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

  public ContentsInfo setData(String key, Object data) {
    this.data.put(key, data);
    return this;
  }

  public <T> T getData(String str) {
    return (T) this.data.get(str);
  }

  public <T> T getDataOr(String str, T t) {
    return (T) this.data.getOrDefault(str, t);
  }

  public int getAsInteger(String key) {
    return ((Integer) getData(key)).intValue();
  }

  public int getAsIntegerOr(String key, int defaultValue) {
    return ((Integer) getDataOr(key, Integer.valueOf(defaultValue))).intValue();
  }

  public long getAsLong(String key) {
    return ((Long) getData(key)).longValue();
  }

  public long getAsLongOr(String key, long defaultValue) {
    return ((Long) getDataOr(key, Long.valueOf(defaultValue))).longValue();
  }

  public String getAsString(String key) {
    return (String) getData(key);
  }

  public String getAsStringOr(String key, String defaultValue) {
    return (String) getDataOr(key, defaultValue);
  }

  public byte getAsByte(String key) {
    return ((Byte) getData(key)).byteValue();
  }

  public byte getAsByteOr(String key, byte defaultValue) {
    return ((Byte) getDataOr(key, Byte.valueOf(defaultValue))).byteValue();
  }

  public byte[] getAsByteArray(String key) {
    return (byte[]) getData(key);
  }

  public byte[] getAsByteArrayOr(String key, byte[] defaultValue) {
    return (byte[]) getDataOr(key, defaultValue);
  }

  public boolean getAsBoolean(String key) {
    return ((Boolean) getData(key)).booleanValue();
  }

  public boolean getAsBooleanOr(String key, boolean defaultValue) {
    return ((Boolean) getDataOr(key, Boolean.valueOf(defaultValue))).booleanValue();
  }

  public double getAsDouble(String key) {
    return ((Double) getData(key)).doubleValue();
  }

  public double getAsDoubleOr(String key, double defaultValue) {
    return ((Double) getDataOr(key, Double.valueOf(defaultValue))).doubleValue();
  }

  public float getAsFloat(String key) {
    return ((Float) getData(key)).floatValue();
  }

  public float getAsFloatOr(String key, float defaultValue) {
    return ((Float) getDataOr(key, Float.valueOf(defaultValue))).floatValue();
  }

  public short getAsShort(String key) {
    return ((Short) getData(key)).shortValue();
  }

  public short getAsShortOr(String key, short defaultValue) {
    return ((Short) getDataOr(key, Short.valueOf(defaultValue))).shortValue();
  }

  public static ContentsInfo wrap(Message message) {
    return new ContentsInfo(message);
  }

  public String toString() {
    return (String)
        this.data.keySet().stream()
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.message.ContentsInfo$$ExternalSyntheticLambda0
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return ContentsInfo.this.m418x3d006615((String) obj);
                  }
                })
            .collect(Collectors.joining(", ", "{", "}"));
  }

  /* renamed from: lambda$toString$0$com-samsung-android-sume-core-message-ContentsInfo */
  /* synthetic */ String m418x3d006615(String it) {
    return it + "=" + this.data.get(it);
  }
}
