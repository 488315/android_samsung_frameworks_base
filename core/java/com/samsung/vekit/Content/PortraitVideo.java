package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

public class PortraitVideo extends Video {
    public PortraitVideo(VEContext context, int id, String name) {
        super(context, id, name);
        this.contentType = ContentType.PORTRAIT_VIDEO;
    }
}
