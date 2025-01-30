package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Audio extends Content {
    private String filePath;

    public Audio(VEContext context, int id, String name) {
        super(context, ContentType.AUDIO, id, name);
    }

    public Audio setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override // com.samsung.vekit.Content.Content
    public Audio setDuration(long duration) {
        return (Audio) super.setDuration(duration);
    }
}
