package com.android.internal.midi;

import android.media.midi.MidiReceiver;
import com.android.internal.midi.EventScheduler;
import java.io.IOException;

/* loaded from: classes5.dex */
public class MidiEventScheduler extends EventScheduler {
    private static final int POOL_EVENT_SIZE = 16;
    private static final String TAG = "MidiEventScheduler";
    private MidiReceiver mReceiver = new SchedulingReceiver();

    private class SchedulingReceiver extends MidiReceiver {
        private SchedulingReceiver() {
        }

        @Override // android.media.midi.MidiReceiver
        public void onSend(byte[] bArr, int i, int i2, long j) throws IOException {
            MidiEvent createScheduledEvent = MidiEventScheduler.this.createScheduledEvent(bArr, i, i2, j);
            if (createScheduledEvent != null) {
                MidiEventScheduler.this.add(createScheduledEvent);
            }
        }

        @Override // android.media.midi.MidiReceiver
        public void onFlush() {
            MidiEventScheduler.this.flush();
        }
    }

    public static class MidiEvent extends EventScheduler.SchedulableEvent {
        public int count;
        public byte[] data;

        private MidiEvent(int i) {
            super(0L);
            this.count = 0;
            this.data = new byte[i];
        }

        private MidiEvent(byte[] bArr, int i, int i2, long j) {
            super(j);
            this.count = 0;
            byte[] bArr2 = new byte[i2];
            this.data = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, i2);
            this.count = i2;
        }

        public String toString() {
            String str = "Event: ";
            for (int i = 0; i < this.count; i++) {
                str = str + ((int) this.data[i]) + ", ";
            }
            return str;
        }
    }

    public MidiEvent createScheduledEvent(byte[] bArr, int i, int i2, long j) {
        int i3 = 16;
        if (i2 > 16) {
            return new MidiEvent(bArr, i, i2, j);
        }
        MidiEvent midiEvent = (MidiEvent) removeEventfromPool();
        if (midiEvent == null) {
            midiEvent = new MidiEvent(i3);
        }
        System.arraycopy(bArr, i, midiEvent.data, 0, i2);
        midiEvent.count = i2;
        midiEvent.setTimestamp(j);
        return midiEvent;
    }

    @Override // com.android.internal.midi.EventScheduler
    public void addEventToPool(EventScheduler.SchedulableEvent schedulableEvent) {
        if ((schedulableEvent instanceof MidiEvent) && ((MidiEvent) schedulableEvent).data.length == 16) {
            super.addEventToPool(schedulableEvent);
        }
    }

    public MidiReceiver getReceiver() {
        return this.mReceiver;
    }
}
