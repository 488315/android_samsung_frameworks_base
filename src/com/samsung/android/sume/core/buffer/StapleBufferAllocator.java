package com.samsung.android.sume.core.buffer;

import android.os.ParcelFileDescriptor;
import com.samsung.android.sume.core.buffer.MediaBufferAllocator;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.message.Message;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
class StapleBufferAllocator extends MediaBufferAllocator {
    protected StapleBufferAllocator(MediaFormat mediaFormat) {
        super(mediaFormat);
    }

    protected StapleBufferAllocator(MediaFormat mediaFormat, Align align) {
        super(mediaFormat, align);
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
    <T> MediaBuffer wrap(T t) {
        if (t instanceof ParcelFileDescriptor) {
            GenericMediaBuffer genericMediaBuffer = new GenericMediaBuffer(this.format, new MediaBufferAllocator.Nothing());
            genericMediaBuffer.setExtra(Message.KEY_FILE_DESCRIPTOR, t);
            return genericMediaBuffer;
        }
        return new GenericMediaBuffer(this.format, this.align, t);
    }

    private MediaBuffer allocAsNumber() {
        if (this.format.getMediaType().isScala()) {
            if (this.format.getDataType().isInt()) {
                return new GenericMediaBuffer(this.format, 0);
            }
            if (this.format.getDataType().isLong()) {
                return new GenericMediaBuffer(this.format, 0L);
            }
            if (this.format.getDataType().isFloat()) {
                return new GenericMediaBuffer(this.format, Float.valueOf(0.0f));
            }
            if (this.format.getDataType().isByte()) {
                return new GenericMediaBuffer(this.format, (byte) 0);
            }
            if (this.format.getDataType().isShort()) {
                return new GenericMediaBuffer(this.format, (short) 0);
            }
            throw new UnsupportedOperationException("not implemented alloc data-type yet");
        }
        throw new UnsupportedOperationException("not implemented alloc yet");
    }

    private MediaBuffer allocAsByteBuffer() {
        return new GenericMediaBuffer(this.format, this.align, ByteBuffer.allocateDirect((int) (this.format.bytePerPixel() * this.align.getDimension())));
    }
}
