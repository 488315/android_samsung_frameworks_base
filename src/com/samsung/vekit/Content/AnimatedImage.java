package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ImageDecoderType;
import com.samsung.vekit.Common.VEContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class AnimatedImage extends Content {
    private ImageDecoderType imageDecoderType;
    private ArrayList<String> imagePathList;

    public AnimatedImage(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.ANIMATED_IMAGE, i, str);
        this.imagePathList = new ArrayList<>();
        this.imageDecoderType = ImageDecoderType.DEFAULT;
    }

    @Override // com.samsung.vekit.Content.Content
    public AnimatedImage setWidth(int i) {
        return (AnimatedImage) super.setWidth(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public AnimatedImage setHeight(int i) {
        return (AnimatedImage) super.setHeight(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public AnimatedImage setDuration(long j) {
        return (AnimatedImage) super.setDuration(j);
    }

    public List<String> getFilePathList() {
        return Collections.unmodifiableList(this.imagePathList);
    }

    public AnimatedImage setFilePathList(ArrayList<String> arrayList) {
        this.imagePathList = arrayList;
        return this;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public void update() {
        super.update();
    }

    public void setImageDecoderType(ImageDecoderType imageDecoderType) {
        this.imageDecoderType = imageDecoderType;
    }

    public ImageDecoderType getImageDecoderType() {
        return this.imageDecoderType;
    }
}
