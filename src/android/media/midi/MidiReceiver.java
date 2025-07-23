package android.media.midi;

import java.io.IOException;

/* loaded from: classes3.dex */
public abstract class MidiReceiver {
    private final int mMaxMessageSize;

    public void onFlush() throws IOException {
    }

    public abstract void onSend(byte[] bArr, int i, int i2, long j) throws IOException;

    public MidiReceiver() {
        this.mMaxMessageSize = Integer.MAX_VALUE;
    }

    public MidiReceiver(int i) {
        this.mMaxMessageSize = i;
    }

    public void flush() throws IOException {
        onFlush();
    }

    public final int getMaxMessageSize() {
        return this.mMaxMessageSize;
    }

    public void send(byte[] bArr, int i, int i2) throws IOException {
        send(bArr, i, i2, 0L);
    }

    public void send(byte[] bArr, int i, int i2, long j) throws IOException {
        int maxMessageSize = getMaxMessageSize();
        int i3 = i2;
        while (i3 > 0) {
            int i4 = i3 > maxMessageSize ? maxMessageSize : i3;
            onSend(bArr, i, i4, j);
            int i5 = i4;
            i3 -= i5;
            i += i5;
        }
    }
}
