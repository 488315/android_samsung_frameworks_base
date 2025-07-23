package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;

/* loaded from: classes6.dex */
public abstract class AsyncFilter extends DecorateFilter {
    private int id;
    protected BufferChannel inputChannel;
    protected BufferChannel outputChannel;

    protected AsyncFilter(MediaFilter mediaFilter) {
        super(mediaFilter);
        this.id = -1;
    }

    public void setId(int i) {
        this.id = i;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public String getId() {
        if (this.id == -1) {
            return super.getId();
        }
        return super.getId() + "@" + this.id;
    }

    public AsyncFilter addBufferChannels(BufferChannel bufferChannel, BufferChannel bufferChannel2) {
        this.inputChannel = bufferChannel;
        this.outputChannel = bufferChannel2;
        return this;
    }

    public BufferChannel getInputChannel() {
        return this.inputChannel;
    }

    public BufferChannel getOutputChannel() {
        return this.outputChannel;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        Def.require((this.inputChannel == null || this.outputChannel == null) ? false : true, "either input-channel or output-channel is not given", new Object[0]);
        super.prepare();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        throw new UnsupportedOperationException("do not call, instead call prepare & release");
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        super.release();
        this.inputChannel.close();
        this.outputChannel.close();
    }
}
