package android.media;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import com.android.internal.vibrator.persistence.XmlConstants;
import com.samsung.android.share.SemShareConstants;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/* loaded from: classes2.dex */
public class MediaMetrics {
    private static final Charset MEDIAMETRICS_CHARSET = StandardCharsets.UTF_8;
    public static final String SEPARATOR = ".";
    public static final String TAG = "MediaMetrics";
    private static final int TYPE_CSTRING = 4;
    private static final int TYPE_DOUBLE = 3;
    private static final int TYPE_INT32 = 1;
    private static final int TYPE_INT64 = 2;
    private static final int TYPE_NONE = 0;
    private static final int TYPE_RATE = 5;

    public interface Key<T> {
        String getName();

        Class<T> getValueClass();
    }

    public static class Name {
        public static final String AUDIO = "audio";
        public static final String AUDIO_BLUETOOTH = "audio.bluetooth";
        public static final String AUDIO_DEVICE = "audio.device";
        public static final String AUDIO_FOCUS = "audio.focus";
        public static final String AUDIO_FORCE_USE = "audio.forceUse";
        public static final String AUDIO_MIC = "audio.mic";
        public static final String AUDIO_MIDI = "audio.midi";
        public static final String AUDIO_MODE = "audio.mode";
        public static final String AUDIO_SERVICE = "audio.service";
        public static final String AUDIO_VOLUME = "audio.volume";
        public static final String AUDIO_VOLUME_EVENT = "audio.volume.event";
        public static final String METRICS_MANAGER = "metrics.manager";
    }

    public static class Property {
        public static final Key<String> ADDRESS = MediaMetrics.createKey("address", String.class);
        public static final Key<String> ATTRIBUTES = MediaMetrics.createKey("attributes", String.class);
        public static final Key<String> CALLING_PACKAGE = MediaMetrics.createKey("callingPackage", String.class);
        public static final Key<String> CLIENT_NAME = MediaMetrics.createKey("clientName", String.class);
        public static final Key<Integer> CLOSED_COUNT = MediaMetrics.createKey("closedCount", Integer.class);
        public static final Key<Integer> DELAY_MS = MediaMetrics.createKey(XmlConstants.ATTRIBUTE_DELAY_MS, Integer.class);
        public static final Key<String> DEVICE = MediaMetrics.createKey("device", String.class);
        public static final Key<String> DEVICE_DISCONNECTED = MediaMetrics.createKey("deviceDisconnected", String.class);
        public static final Key<Integer> DEVICE_ID = MediaMetrics.createKey(SemShareConstants.INTENT_EXTRA_CHOOSER_SHARE_DEVICE_ID, Integer.class);
        public static final Key<String> DIRECTION = MediaMetrics.createKey("direction", String.class);
        public static final Key<Long> DURATION_NS = MediaMetrics.createKey("durationNs", Long.class);
        public static final Key<String> EARLY_RETURN = MediaMetrics.createKey("earlyReturn", String.class);
        public static final Key<String> ENCODING = MediaMetrics.createKey("encoding", String.class);
        public static final Key<String> EVENT = MediaMetrics.createKey("event#", String.class);
        public static final Key<String> ENABLED = MediaMetrics.createKey("enabled", String.class);
        public static final Key<String> EXTERNAL = MediaMetrics.createKey("external", String.class);
        public static final Key<Integer> FLAGS = MediaMetrics.createKey("flags", Integer.class);
        public static final Key<String> FOCUS_CHANGE_HINT = MediaMetrics.createKey("focusChangeHint", String.class);
        public static final Key<String> FORCE_USE_DUE_TO = MediaMetrics.createKey("forceUseDueTo", String.class);
        public static final Key<String> FORCE_USE_MODE = MediaMetrics.createKey("forceUseMode", String.class);
        public static final Key<Double> GAIN_DB = MediaMetrics.createKey("gainDb", Double.class);
        public static final Key<String> GROUP = MediaMetrics.createKey("group", String.class);
        public static final Key<String> HAS_HEAD_TRACKER = MediaMetrics.createKey("hasHeadTracker", String.class);
        public static final Key<Integer> HARDWARE_TYPE = MediaMetrics.createKey("hardwareType", Integer.class);
        public static final Key<String> HEAD_TRACKER_ENABLED = MediaMetrics.createKey("headTrackerEnabled", String.class);
        public static final Key<Integer> INDEX = MediaMetrics.createKey("index", Integer.class);
        public static final Key<Integer> OLD_INDEX = MediaMetrics.createKey("oldIndex", Integer.class);
        public static final Key<Integer> INPUT_PORT_COUNT = MediaMetrics.createKey("inputPortCount", Integer.class);
        public static final Key<String> IS_SHARED = MediaMetrics.createKey("isShared", String.class);
        public static final Key<String> LOG_SESSION_ID = MediaMetrics.createKey("logSessionId", String.class);
        public static final Key<Integer> MAX_INDEX = MediaMetrics.createKey("maxIndex", Integer.class);
        public static final Key<Integer> MIN_INDEX = MediaMetrics.createKey("minIndex", Integer.class);
        public static final Key<String> MODE = MediaMetrics.createKey("mode", String.class);
        public static final Key<String> MUTE = MediaMetrics.createKey("mute", String.class);
        public static final Key<String> NAME = MediaMetrics.createKey("name", String.class);
        public static final Key<Integer> OBSERVERS = MediaMetrics.createKey("observers", Integer.class);
        public static final Key<Integer> OPENED_COUNT = MediaMetrics.createKey("openedCount", Integer.class);
        public static final Key<Integer> OUTPUT_PORT_COUNT = MediaMetrics.createKey("outputPortCount", Integer.class);
        public static final Key<String> REQUEST = MediaMetrics.createKey("request", String.class);
        public static final Key<String> REQUESTED_MODE = MediaMetrics.createKey("requestedMode", String.class);
        public static final Key<String> SCO_AUDIO_MODE = MediaMetrics.createKey("scoAudioMode", String.class);
        public static final Key<Integer> SDK = MediaMetrics.createKey("sdk", Integer.class);
        public static final Key<String> STATE = MediaMetrics.createKey("state", String.class);
        public static final Key<Integer> STATUS = MediaMetrics.createKey("status", Integer.class);
        public static final Key<String> STREAM_TYPE = MediaMetrics.createKey(TextToSpeech.Engine.KEY_PARAM_STREAM, String.class);
        public static final Key<String> SUPPORTS_MIDI_UMP = MediaMetrics.createKey("supportsMidiUmp", String.class);
        public static final Key<Integer> TOTAL_INPUT_BYTES = MediaMetrics.createKey("totalInputBytes", Integer.class);
        public static final Key<Integer> TOTAL_OUTPUT_BYTES = MediaMetrics.createKey("totalOutputBytes", Integer.class);
        public static final Key<String> USING_ALSA = MediaMetrics.createKey("usingAlsa", String.class);
    }

    public static class Value {
        public static final String CONNECT = "connect";
        public static final String CONNECTED = "connected";
        public static final String DISCONNECT = "disconnect";
        public static final String DISCONNECTED = "disconnected";
        public static final String DOWN = "down";
        public static final String MUTE = "mute";
        public static final String NO = "no";
        public static final String OFF = "off";
        public static final String ON = "on";
        public static final String UNMUTE = "unmute";
        public static final String UP = "up";
        public static final String YES = "yes";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native int native_submit_bytebuffer(ByteBuffer byteBuffer, int i);

    public static <T> Key<T> createKey(String str, Class<T> cls) {
        return new Key<T>(str, cls) { // from class: android.media.MediaMetrics.1
            private final String mName;
            private final Class<T> mType;
            final /* synthetic */ String val$name;
            final /* synthetic */ Class val$type;

            {
                this.val$name = str;
                this.val$type = cls;
                this.mName = str;
                this.mType = cls;
            }

            @Override // android.media.MediaMetrics.Key
            public String getName() {
                return this.mName;
            }

            @Override // android.media.MediaMetrics.Key
            public Class<T> getValueClass() {
                return this.mType;
            }

            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Key)) {
                    return false;
                }
                Key key = (Key) obj;
                return this.mName.equals(key.getName()) && this.mType.equals(key.getValueClass());
            }

            public int hashCode() {
                return Objects.hash(this.mName, this.mType);
            }
        };
    }

    public static class Item {
        public static final String BUNDLE_HEADER_SIZE = "_headerSize";
        public static final String BUNDLE_KEY = "_key";
        public static final String BUNDLE_KEY_SIZE = "_keySize";
        public static final String BUNDLE_PID = "_pid";
        public static final String BUNDLE_PROPERTY_COUNT = "_propertyCount";
        public static final String BUNDLE_TIMESTAMP = "_timestamp";
        public static final String BUNDLE_TOTAL_SIZE = "_totalSize";
        public static final String BUNDLE_UID = "_uid";
        public static final String BUNDLE_VERSION = "_version";
        private static final int FORMAT_VERSION = 0;
        private static final int HEADER_SIZE_OFFSET = 4;
        private static final int MINIMUM_PAYLOAD_SIZE = 4;
        private static final int TOTAL_SIZE_OFFSET = 0;
        private ByteBuffer mBuffer;
        private final int mHeaderSize;
        private final String mKey;
        private final int mPidOffset;
        private int mPropertyCount;
        private final int mPropertyCountOffset;
        private final int mPropertyStartOffset;
        private final int mTimeNsOffset;
        private final int mUidOffset;

        public Item(String str) {
            this(str, -1, -1, 0L, 2048);
        }

        public Item(String str, int i, int i2, long j, int i3) {
            this.mPropertyCount = 0;
            byte[] bytes = str.getBytes(MediaMetrics.MEDIAMETRICS_CHARSET);
            int length = bytes.length;
            if (length > 65534) {
                throw new IllegalArgumentException("Key length too large");
            }
            int i4 = length + 29;
            this.mHeaderSize = i4;
            this.mPidOffset = length + 13;
            this.mUidOffset = length + 17;
            this.mTimeNsOffset = length + 21;
            this.mPropertyCountOffset = i4;
            this.mPropertyStartOffset = length + 33;
            this.mKey = str;
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(Math.max(i3, length + 33));
            this.mBuffer = byteBufferAllocateDirect;
            byteBufferAllocateDirect.order(ByteOrder.nativeOrder()).putInt(0).putInt(i4).putChar((char) 0).putChar((char) (length + 1)).put(bytes).put((byte) 0).putInt(i).putInt(i2).putLong(j);
            if (i4 != this.mBuffer.position()) {
                throw new IllegalStateException("Mismatched sizing");
            }
            this.mBuffer.putInt(0);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public <T> Item set(Key<T> key, T t) {
            if (t instanceof Integer) {
                putInt(key.getName(), ((Integer) t).intValue());
                return this;
            }
            if (t instanceof Long) {
                putLong(key.getName(), ((Long) t).longValue());
                return this;
            }
            if (t instanceof Double) {
                putDouble(key.getName(), ((Double) t).doubleValue());
                return this;
            }
            if (t instanceof String) {
                putString(key.getName(), (String) t);
            }
            return this;
        }

        public Item putInt(String str, int i) {
            byte[] bytes = str.getBytes(MediaMetrics.MEDIAMETRICS_CHARSET);
            char cReserveProperty = (char) reserveProperty(bytes, 4);
            int iPosition = this.mBuffer.position() + cReserveProperty;
            this.mBuffer.putChar(cReserveProperty).put((byte) 1).put(bytes).put((byte) 0).putInt(i);
            this.mPropertyCount++;
            if (this.mBuffer.position() == iPosition) {
                return this;
            }
            throw new IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + iPosition);
        }

        public Item putLong(String str, long j) {
            byte[] bytes = str.getBytes(MediaMetrics.MEDIAMETRICS_CHARSET);
            char cReserveProperty = (char) reserveProperty(bytes, 8);
            int iPosition = this.mBuffer.position() + cReserveProperty;
            this.mBuffer.putChar(cReserveProperty).put((byte) 2).put(bytes).put((byte) 0).putLong(j);
            this.mPropertyCount++;
            if (this.mBuffer.position() == iPosition) {
                return this;
            }
            throw new IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + iPosition);
        }

        public Item putDouble(String str, double d) {
            byte[] bytes = str.getBytes(MediaMetrics.MEDIAMETRICS_CHARSET);
            char cReserveProperty = (char) reserveProperty(bytes, 8);
            int iPosition = this.mBuffer.position() + cReserveProperty;
            this.mBuffer.putChar(cReserveProperty).put((byte) 3).put(bytes).put((byte) 0).putDouble(d);
            this.mPropertyCount++;
            if (this.mBuffer.position() == iPosition) {
                return this;
            }
            throw new IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + iPosition);
        }

        public Item putString(String str, String str2) {
            byte[] bytes = str.getBytes(MediaMetrics.MEDIAMETRICS_CHARSET);
            byte[] bytes2 = str2.getBytes(MediaMetrics.MEDIAMETRICS_CHARSET);
            char cReserveProperty = (char) reserveProperty(bytes, bytes2.length + 1);
            int iPosition = this.mBuffer.position() + cReserveProperty;
            this.mBuffer.putChar(cReserveProperty).put((byte) 4).put(bytes).put((byte) 0).put(bytes2).put((byte) 0);
            this.mPropertyCount++;
            if (this.mBuffer.position() == iPosition) {
                return this;
            }
            throw new IllegalStateException("Final position " + this.mBuffer.position() + " != estimatedFinalPosition " + iPosition);
        }

        public Item setPid(int i) {
            this.mBuffer.putInt(this.mPidOffset, i);
            return this;
        }

        public Item setUid(int i) {
            this.mBuffer.putInt(this.mUidOffset, i);
            return this;
        }

        public Item setTimestamp(long j) {
            this.mBuffer.putLong(this.mTimeNsOffset, j);
            return this;
        }

        public Item clear() {
            this.mBuffer.position(this.mPropertyStartOffset);
            ByteBuffer byteBuffer = this.mBuffer;
            byteBuffer.limit(byteBuffer.capacity());
            this.mBuffer.putLong(this.mTimeNsOffset, 0L);
            this.mPropertyCount = 0;
            return this;
        }

        public boolean record() {
            updateHeader();
            ByteBuffer byteBuffer = this.mBuffer;
            return MediaMetrics.native_submit_bytebuffer(byteBuffer, byteBuffer.limit()) >= 0;
        }

        public Bundle toBundle() {
            updateHeader();
            ByteBuffer byteBufferDuplicate = this.mBuffer.duplicate();
            byteBufferDuplicate.order(ByteOrder.nativeOrder()).flip();
            return toBundle(byteBufferDuplicate);
        }

        public static Bundle toBundle(ByteBuffer byteBuffer) {
            Bundle bundle = new Bundle();
            int i = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            char c = byteBuffer.getChar();
            char c2 = byteBuffer.getChar();
            if (i < 0 || i2 < 0) {
                throw new IllegalArgumentException("Item size cannot be > 2147483647");
            }
            if (c2 > 0) {
                String stringFromBuffer = getStringFromBuffer(byteBuffer, c2);
                int i3 = byteBuffer.getInt();
                int i4 = byteBuffer.getInt();
                long j = byteBuffer.getLong();
                int iPosition = byteBuffer.position();
                if (c != 0) {
                    if (iPosition > i2) {
                        throw new IllegalArgumentException("Item key:" + stringFromBuffer + " headerRead:" + iPosition + " > headerSize:" + i2);
                    }
                    if (iPosition < i2) {
                        byteBuffer.position(i2);
                    }
                } else if (iPosition != i2) {
                    throw new IllegalArgumentException("Item key:" + stringFromBuffer + " headerRead:" + iPosition + " != headerSize:" + i2);
                }
                int i5 = byteBuffer.getInt();
                if (i5 < 0) {
                    throw new IllegalArgumentException("Cannot have more than 2147483647 properties");
                }
                bundle.putInt(BUNDLE_TOTAL_SIZE, i);
                bundle.putInt(BUNDLE_HEADER_SIZE, i2);
                bundle.putChar(BUNDLE_VERSION, c);
                bundle.putChar(BUNDLE_KEY_SIZE, c2);
                bundle.putString(BUNDLE_KEY, stringFromBuffer);
                bundle.putInt(BUNDLE_PID, i3);
                bundle.putInt(BUNDLE_UID, i4);
                bundle.putLong(BUNDLE_TIMESTAMP, j);
                bundle.putInt(BUNDLE_PROPERTY_COUNT, i5);
                for (int i6 = 0; i6 < i5; i6++) {
                    int iPosition2 = byteBuffer.position();
                    char c3 = byteBuffer.getChar();
                    byte b = byteBuffer.get();
                    String stringFromBuffer2 = getStringFromBuffer(byteBuffer);
                    if (b != 0) {
                        if (b == 1) {
                            bundle.putInt(stringFromBuffer2, byteBuffer.getInt());
                        } else if (b == 2) {
                            bundle.putLong(stringFromBuffer2, byteBuffer.getLong());
                        } else if (b == 3) {
                            bundle.putDouble(stringFromBuffer2, byteBuffer.getDouble());
                        } else if (b == 4) {
                            bundle.putString(stringFromBuffer2, getStringFromBuffer(byteBuffer));
                        } else if (b == 5) {
                            byteBuffer.getLong();
                            byteBuffer.getLong();
                        } else {
                            if (c == 0) {
                                throw new IllegalArgumentException("Property " + stringFromBuffer2 + " has unsupported type " + ((int) b));
                            }
                            byteBuffer.position(iPosition2 + c3);
                        }
                    }
                    int iPosition3 = byteBuffer.position() - iPosition2;
                    if (iPosition3 != c3) {
                        throw new IllegalArgumentException("propSize:" + c3 + " != deltaPosition:" + iPosition3);
                    }
                }
                int iPosition4 = byteBuffer.position();
                if (iPosition4 == i) {
                    return bundle;
                }
                throw new IllegalArgumentException("totalSize:" + i + " != finalPosition:" + iPosition4);
            }
            throw new IllegalArgumentException("Illegal null key");
        }

        private int reserveProperty(byte[] bArr, int i) {
            int length = bArr.length;
            if (length > 65535) {
                throw new IllegalStateException("property key too long ".concat(new String(bArr, MediaMetrics.MEDIAMETRICS_CHARSET)));
            }
            if (i > 65535) {
                throw new IllegalStateException("payload too large " + i);
            }
            int i2 = length + 4 + i;
            if (i2 > 65535) {
                throw new IllegalStateException("Item property " + new String(bArr, MediaMetrics.MEDIAMETRICS_CHARSET) + " is too large to send");
            }
            if (this.mBuffer.remaining() >= i2) {
                return i2;
            }
            int iPosition = this.mBuffer.position() + i2;
            if (iPosition > 1073741823) {
                throw new IllegalStateException("Item memory requirements too large: " + iPosition);
            }
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(iPosition << 1);
            byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
            this.mBuffer.flip();
            byteBufferAllocateDirect.put(this.mBuffer);
            this.mBuffer = byteBufferAllocateDirect;
            return i2;
        }

        private static String getStringFromBuffer(ByteBuffer byteBuffer) {
            return getStringFromBuffer(byteBuffer, Integer.MAX_VALUE);
        }

        private static String getStringFromBuffer(ByteBuffer byteBuffer, int i) {
            int i2;
            int iPosition = byteBuffer.position();
            int iLimit = byteBuffer.limit();
            if (i < Integer.MAX_VALUE - iPosition && (i2 = iPosition + i) < iLimit) {
                iLimit = i2;
            }
            while (iPosition < iLimit) {
                if (byteBuffer.get(iPosition) == 0) {
                    int i3 = iPosition + 1;
                    if (i != Integer.MAX_VALUE && i3 - byteBuffer.position() != i) {
                        throw new IllegalArgumentException("chars consumed at " + iPosition + ": " + (i3 - byteBuffer.position()) + " != size: " + i);
                    }
                    if (byteBuffer.hasArray()) {
                        String str = new String(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), iPosition - byteBuffer.position(), MediaMetrics.MEDIAMETRICS_CHARSET);
                        byteBuffer.position(i3);
                        return str;
                    }
                    byte[] bArr = new byte[iPosition - byteBuffer.position()];
                    byteBuffer.get(bArr);
                    String str2 = new String(bArr, MediaMetrics.MEDIAMETRICS_CHARSET);
                    byteBuffer.get();
                    return str2;
                }
                iPosition++;
            }
            throw new IllegalArgumentException("No zero termination found in string position: " + byteBuffer.position() + " end: " + iPosition);
        }

        private void updateHeader() {
            ByteBuffer byteBuffer = this.mBuffer;
            byteBuffer.putInt(0, byteBuffer.position()).putInt(this.mPropertyCountOffset, (char) this.mPropertyCount);
        }
    }
}
