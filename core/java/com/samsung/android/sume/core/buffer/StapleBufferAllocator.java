package com.samsung.android.sume.core.buffer;

import android.p009os.ParcelFileDescriptor;
import com.samsung.android.sume.core.buffer.MediaBufferAllocator;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.message.Message;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
class StapleBufferAllocator extends MediaBufferAllocator {
    protected StapleBufferAllocator(MediaFormat format) {
        super(format);
    }

    protected StapleBufferAllocator(MediaFormat format, Align align) {
        super(format, align);
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferAllocator
    MediaBuffer allocate() {
        if (this.format.getMediaType().isScala()) {
            return allocAsNumber();
        }
        return allocAsByteBuffer();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferAllocator
    MediaBuffer allocate(Align align) {
        if (align.getDimension() != 0) {
            this.align = align;
        } else if (align.getAlignOfWidth() != 0) {
            this.align.set(align.getAlignOfWidth(), align.getAlignOfHeight());
            this.align.adjustAlign();
        }
        return allocate();
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferAllocator
    MediaBuffer allocateShared() {
        return new GenericMediaBuffer(this.format, SharedBufferManager.create(this.format));
    }

    @Override // com.samsung.android.sume.core.buffer.MediaBufferAllocator
    <T> MediaBuffer wrap(T data) {
        if (data instanceof ParcelFileDescriptor) {
            MediaBuffer buf = new GenericMediaBuffer(this.format, new MediaBufferAllocator.Nothing());
            buf.setExtra(Message.KEY_FILE_DESCRIPTOR, data);
            return buf;
        }
        return new GenericMediaBuffer(this.format, this.align, data);
    }

    private MediaBuffer allocAsNumber() {
        if (this.format.getMediaType().isScala()) {
            if (this.format.getDataType().isInt()) {
                MediaBuffer buf = new GenericMediaBuffer(this.format, 0);
                return buf;
            }
            if (this.format.getDataType().isLong()) {
                MediaBuffer buf2 = new GenericMediaBuffer(this.format, 0L);
                return buf2;
            }
            if (this.format.getDataType().isFloat()) {
                MediaBuffer buf3 = new GenericMediaBuffer(this.format, Float.valueOf(0.0f));
                return buf3;
            }
            if (this.format.getDataType().isByte()) {
                MediaBuffer buf4 = new GenericMediaBuffer(this.format, (byte) 0);
                return buf4;
            }
            if (this.format.getDataType().isShort()) {
                MediaBuffer buf5 = new GenericMediaBuffer(this.format, (short) 0);
                return buf5;
            }
            throw new UnsupportedOperationException("not implemented alloc data-type yet");
        }
        throw new UnsupportedOperationException("not implemented alloc yet");
    }

    private MediaBuffer allocAsByteBuffer() {
        return new GenericMediaBuffer(this.format, this.align, ByteBuffer.allocateDirect((int) (this.format.bytePerPixel() * this.align.getDimension())));
    }
}
