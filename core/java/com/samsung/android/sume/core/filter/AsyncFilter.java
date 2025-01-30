package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;

/* loaded from: classes4.dex */
public abstract class AsyncFilter extends DecorateFilter {

  /* renamed from: id */
  private int f3056id;
  protected BufferChannel inputChannel;
  protected BufferChannel outputChannel;

  protected AsyncFilter(MediaFilter successor) {
    super(successor);
    this.f3056id = -1;
  }

  public void setId(int id) {
    this.f3056id = id;
  }

  @Override // com.samsung.android.sume.core.filter.MediaFilter
  public String getId() {
    return this.f3056id == -1 ? super.getId() : super.getId() + "@" + this.f3056id;
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
