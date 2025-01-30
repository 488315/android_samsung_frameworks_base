package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.filter.MediaParserFilter;
import com.samsung.android.sume.core.types.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class MediaParserDescriptor extends MFDescriptorBase {
  private long maxDurationUs;
  private final List<MediaType> mediaTypeList;

  public MediaParserDescriptor(MediaType... mediaTypes) {
    this.mediaTypeList =
        (List)
            Arrays.stream(
                    mediaTypes.length == 0
                        ? new MediaType[] {MediaType.COMPRESSED_AUDIO, MediaType.COMPRESSED_VIDEO}
                        : mediaTypes)
                .collect(Collectors.toList());
  }

  @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase,
            // com.samsung.android.sume.core.descriptor.MFDescriptor
  public String getFilterId() {
    return MediaParserFilter.class.getName();
  }

  public boolean needToParse(MediaType mediaType) {
    return this.mediaTypeList.contains(mediaType);
  }

  public int countToParse() {
    return this.mediaTypeList.size();
  }

  public void setMaxDurationUs(long maxDurationUs) {
    this.maxDurationUs = maxDurationUs;
  }

  public long getMaxDurationUs() {
    return this.maxDurationUs;
  }
}
