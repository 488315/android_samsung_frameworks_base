package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Caption extends Content {
    private String filePath;

    public Caption(VEContext context, int id, String name) {
        super(context, ContentType.CAPTION, id, name);
    }

    public Caption setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override // com.samsung.vekit.Content.Content
    public Caption setWidth(int width) {
        return (Caption) super.setWidth(width);
    }

    @Override // com.samsung.vekit.Content.Content
    public Caption setHeight(int height) {
        return (Caption) super.setHeight(height);
    }

    @Override // com.samsung.vekit.Content.Content
    public Caption setDuration(long duration) {
        return (Caption) super.setDuration(duration);
    }
}
