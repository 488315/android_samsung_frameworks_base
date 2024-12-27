package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;

public abstract class AsyncFilter extends DecorateFilter {
    private int id;
    protected BufferChannel inputChannel;
    protected BufferChannel outputChannel;

    protected AsyncFilter(MediaFilter successor) {
        super(successor);
        this.id = -1;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public String getId() {
        return this.id == -1 ? super.getId() : super.getId() + "@" + this.id;
    }

    public AsyncFilter addBufferChannels(BufferChannel inputChannel, BufferChannel outputChannel) {
        this.inputChannel = inputChannel;
        this.outputChannel = outputChannel;
        return this;
    }

    public BufferChannel getInputChannel() {
        return this.inputChannel;
    }

    public BufferChannel getOutputChannel() {
        return this.outputChannel;
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        Def.require(
                (this.inputChannel == null || this.outputChannel == null) ? false : true,
                "either input-channel or output-channel is not given",
                new Object[0]);
        super.prepare();
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        throw new UnsupportedOperationException("do not call, instead call prepare & release");
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter,
              // com.samsung.android.sume.core.filter.MediaFilter
    public void release() {
        super.release();
        this.inputChannel.close();
        this.outputChannel.close();
    }
}
