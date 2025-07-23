package android.speech.tts;

import android.media.AudioFormat;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/* loaded from: classes3.dex */
class FileSynthesisCallback extends AbstractSynthesisCallback {
    private static final boolean DBG = false;
    private static final int MAX_AUDIO_BUFFER_SIZE = 8192;
    private static final String TAG = "FileSynthesisRequest";
    private static final short WAV_FORMAT_PCM = 1;
    private static final int WAV_HEADER_LENGTH = 44;
    private int mAudioFormat;
    private int mChannelCount;
    private final TextToSpeechService.UtteranceProgressDispatcher mDispatcher;
    private boolean mDone;
    private FileChannel mFileChannel;
    private int mSampleRateInHz;
    private boolean mStarted;
    private final Object mStateLock;
    protected int mStatusCode;

    @Override // android.speech.tts.SynthesisCallback
    public int getMaxBufferSize() {
        return 8192;
    }

    FileSynthesisCallback(FileChannel fileChannel, TextToSpeechService.UtteranceProgressDispatcher utteranceProgressDispatcher, boolean z) {
        super(z);
        this.mStateLock = new Object();
        this.mStarted = false;
        this.mDone = false;
        this.mFileChannel = fileChannel;
        this.mDispatcher = utteranceProgressDispatcher;
        this.mStatusCode = 0;
    }

    @Override // android.speech.tts.AbstractSynthesisCallback
    void stop() {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                return;
            }
            if (this.mStatusCode == -2) {
                return;
            }
            this.mStatusCode = -2;
            cleanUp();
            this.mDispatcher.dispatchOnStop();
        }
    }

    private void cleanUp() {
        closeFile();
    }

    private void closeFile() {
        this.mFileChannel = null;
    }

    @Override // android.speech.tts.SynthesisCallback
    public int start(int i, int i2, int i3) {
        if (i2 != 3 && i2 != 2 && i2 != 4) {
            Log.e(TAG, "Audio format encoding " + i2 + " not supported. Please use one of AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT or AudioFormat.ENCODING_PCM_FLOAT");
        }
        this.mDispatcher.dispatchOnBeginSynthesis(i, i2, i3);
        synchronized (this.mStateLock) {
            int i4 = this.mStatusCode;
            if (i4 == -2) {
                return errorCodeOnStop();
            }
            if (i4 != 0) {
                return -1;
            }
            if (this.mStarted) {
                Log.e(TAG, "Start called twice");
                return -1;
            }
            this.mStarted = true;
            this.mSampleRateInHz = i;
            this.mAudioFormat = i2;
            this.mChannelCount = i3;
            this.mDispatcher.dispatchOnStart();
            FileChannel fileChannel = this.mFileChannel;
            try {
                fileChannel.write(ByteBuffer.allocate(44));
                return 0;
            } catch (IOException e) {
                Log.e(TAG, "Failed to write wav header to output file descriptor", e);
                synchronized (this.mStateLock) {
                    cleanUp();
                    this.mStatusCode = -5;
                    return -1;
                }
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int audioAvailable(byte[] bArr, int i, int i2) {
        synchronized (this.mStateLock) {
            int i3 = this.mStatusCode;
            if (i3 == -2) {
                return errorCodeOnStop();
            }
            if (i3 != 0) {
                return -1;
            }
            FileChannel fileChannel = this.mFileChannel;
            if (fileChannel == null) {
                Log.e(TAG, "File not open");
                this.mStatusCode = -5;
                return -1;
            }
            if (!this.mStarted) {
                Log.e(TAG, "Start method was not called");
                return -1;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            this.mDispatcher.dispatchOnAudioAvailable(bArr2);
            try {
                fileChannel.write(ByteBuffer.wrap(bArr, i, i2));
                return 0;
            } catch (IOException e) {
                Log.e(TAG, "Failed to write to output file descriptor", e);
                synchronized (this.mStateLock) {
                    cleanUp();
                    this.mStatusCode = -5;
                    return -1;
                }
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public int done() {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                Log.w(TAG, "Duplicate call to done()");
                return -1;
            }
            int i = this.mStatusCode;
            if (i == -2) {
                return errorCodeOnStop();
            }
            if (i != 0 && i != -2) {
                this.mDispatcher.dispatchOnError(i);
                return -1;
            }
            FileChannel fileChannel = this.mFileChannel;
            if (fileChannel == null) {
                Log.e(TAG, "File not open");
                return -1;
            }
            this.mDone = true;
            int i2 = this.mSampleRateInHz;
            int i3 = this.mAudioFormat;
            int i4 = this.mChannelCount;
            try {
                fileChannel.position(0L);
                fileChannel.write(makeWavHeader(i2, i3, i4, (int) (fileChannel.size() - 44)));
                synchronized (this.mStateLock) {
                    closeFile();
                    this.mDispatcher.dispatchOnSuccess();
                }
                return 0;
            } catch (IOException e) {
                Log.e(TAG, "Failed to write to output file descriptor", e);
                synchronized (this.mStateLock) {
                    cleanUp();
                    return -1;
                }
            }
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public void error() {
        error(-3);
    }

    @Override // android.speech.tts.SynthesisCallback
    public void error(int i) {
        synchronized (this.mStateLock) {
            if (this.mDone) {
                return;
            }
            cleanUp();
            this.mStatusCode = i;
        }
    }

    @Override // android.speech.tts.SynthesisCallback
    public boolean hasStarted() {
        boolean z;
        synchronized (this.mStateLock) {
            z = this.mStarted;
        }
        return z;
    }

    @Override // android.speech.tts.SynthesisCallback
    public boolean hasFinished() {
        boolean z;
        synchronized (this.mStateLock) {
            z = this.mDone;
        }
        return z;
    }

    private ByteBuffer makeWavHeader(int i, int i2, int i3, int i4) {
        int bytesPerSample = AudioFormat.getBytesPerSample(i2);
        ByteBuffer wrap = ByteBuffer.wrap(new byte[44]);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        wrap.put(new byte[]{82, 73, 70, 70});
        wrap.putInt(i4 + 36);
        wrap.put(new byte[]{87, 65, 86, 69});
        wrap.put(new byte[]{102, 109, 116, 32});
        wrap.putInt(16);
        wrap.putShort((short) 1);
        wrap.putShort((short) i3);
        wrap.putInt(i);
        wrap.putInt(i * bytesPerSample * i3);
        wrap.putShort((short) (bytesPerSample * i3));
        wrap.putShort((short) (bytesPerSample * 8));
        wrap.put(new byte[]{100, SprAttributeBase.TYPE_ANIMATOR_SET, 116, SprAttributeBase.TYPE_ANIMATOR_SET});
        wrap.putInt(i4);
        wrap.flip();
        return wrap;
    }

    @Override // android.speech.tts.SynthesisCallback
    public void rangeStart(int i, int i2, int i3) {
        this.mDispatcher.dispatchOnRangeStart(i, i2, i3);
    }
}
