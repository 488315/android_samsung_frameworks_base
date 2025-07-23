package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Caption extends Content {
    private String filePath;

    public Caption(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.CAPTION, i, str);
    }

    public Caption setFilePath(String str) {
        this.filePath = str;
        return this;
    }

    public String getFilePath() {
        return this.filePath;
    }

    @Override // com.samsung.vekit.Content.Content
    public Caption setWidth(int i) {
        return (Caption) super.setWidth(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Caption setHeight(int i) {
        return (Caption) super.setHeight(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Caption setDuration(long j) {
        return (Caption) super.setDuration(j);
    }
}
