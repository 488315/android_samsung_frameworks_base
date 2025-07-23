package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.ContentColorType;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Content extends Element {
    protected ContentColorType colorType;
    protected ContentType contentType;
    protected long duration;
    protected int height;
    protected int width;

    protected Content(VEContext vEContext, ContentType contentType, int i, String str) {
        super(vEContext, ElementType.CONTENT, i, str);
        this.contentType = contentType;
        this.TAG = getClass().getSimpleName();
        this.width = 0;
        this.height = 0;
        this.duration = 0L;
        this.colorType = ContentColorType.SDR;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public long getDuration() {
        return this.duration;
    }

    public Content setWidth(int i) {
        this.width = i;
        return this;
    }

    public Content setHeight(int i) {
        this.height = i;
        return this;
    }

    public Content setDuration(long j) {
        this.duration = j;
        return this;
    }

    public ContentColorType getColorType() {
        return this.colorType;
    }

    public void setColorType(ContentColorType contentColorType) {
        this.colorType = contentColorType;
    }
}
