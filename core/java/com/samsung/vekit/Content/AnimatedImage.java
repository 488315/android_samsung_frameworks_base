package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.VEContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimatedImage extends Content {
    private ArrayList<String> imagePathList;

    public AnimatedImage(VEContext context, int id, String name) {
        super(context, ContentType.ANIMATED_IMAGE, id, name);
        this.imagePathList = new ArrayList<>();
    }

    @Override // com.samsung.vekit.Content.Content
    public AnimatedImage setWidth(int width) {
        return (AnimatedImage) super.setWidth(width);
    }

    @Override // com.samsung.vekit.Content.Content
    public AnimatedImage setHeight(int height) {
        return (AnimatedImage) super.setHeight(height);
    }

    @Override // com.samsung.vekit.Content.Content
    public AnimatedImage setDuration(long duration) {
        return (AnimatedImage) super.setDuration(duration);
    }

    public List<String> getFilePathList() {
        return Collections.unmodifiableList(this.imagePathList);
    }

    public AnimatedImage setFilePathList(ArrayList<String> imagePathList) {
        this.imagePathList = imagePathList;
        return this;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public void update() {
        super.update();
    }
}
