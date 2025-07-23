package com.android.internal.midi;

import android.media.midi.MidiReceiver;
import android.media.midi.MidiSender;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes5.dex */
public final class MidiDispatcher extends MidiReceiver {
    private final MidiReceiverFailureHandler mFailureHandler;
    private final CopyOnWriteArrayList<MidiReceiver> mReceivers;
    private final MidiSender mSender;

    public interface MidiReceiverFailureHandler {
        void onReceiverFailure(MidiReceiver midiReceiver, IOException iOException);
    }

    public MidiDispatcher() {
        this(null);
    }

    public MidiDispatcher(MidiReceiverFailureHandler midiReceiverFailureHandler) {
        this.mReceivers = new CopyOnWriteArrayList<>();
        this.mSender = new MidiSender() { // from class: com.android.internal.midi.MidiDispatcher.1
            @Override // android.media.midi.MidiSender
            public void onConnect(MidiReceiver midiReceiver) {
                MidiDispatcher.this.mReceivers.add(midiReceiver);
            }

            @Override // android.media.midi.MidiSender
            public void onDisconnect(MidiReceiver midiReceiver) {
                MidiDispatcher.this.mReceivers.remove(midiReceiver);
            }
        };
        this.mFailureHandler = midiReceiverFailureHandler;
    }

    public int getReceiverCount() {
        return this.mReceivers.size();
    }

    public MidiSender getSender() {
        return this.mSender;
    }

    @Override // android.media.midi.MidiReceiver
    public void onSend(byte[] bArr, int i, int i2, long j) throws IOException {
        Iterator<MidiReceiver> it = this.mReceivers.iterator();
        while (it.hasNext()) {
            MidiReceiver next = it.next();
            byte[] bArr2 = bArr;
            int i3 = i;
            int i4 = i2;
            long j2 = j;
            try {
                next.send(bArr2, i3, i4, j2);
            } catch (IOException e) {
                this.mReceivers.remove(next);
                if (this.mFailureHandler != null) {
                    this.mFailureHandler.onReceiverFailure(next, e);
                }
            }
            bArr = bArr2;
            i = i3;
            i2 = i4;
            j = j2;
        }
    }

    @Override // android.media.midi.MidiReceiver
    public void onFlush() throws IOException {
        Iterator<MidiReceiver> it = this.mReceivers.iterator();
        while (it.hasNext()) {
            MidiReceiver next = it.next();
            try {
                next.flush();
            } catch (IOException e) {
                this.mReceivers.remove(next);
                if (this.mFailureHandler != null) {
                    this.mFailureHandler.onReceiverFailure(next, e);
                }
            }
        }
    }
}
