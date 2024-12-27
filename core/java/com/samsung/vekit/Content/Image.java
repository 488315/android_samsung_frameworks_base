package com.samsung.vekit.Content;

import android.graphics.Bitmap;

import com.samsung.vekit.Common.Object.ImageInfo;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Util.ImageUtil;
import com.samsung.vekit.Common.VEContext;

public class Image extends Content {
    private static final int IMAGE_BUFFER_SIZE = 3840;
    private Bitmap bitmap;
    private String filePath;
    private ImageStorageType imageStorageType;
    private boolean needUpdate;
    private int orientation;
    private int targetHeight;
    private int targetWidth;

    enum ImageStorageType {
        BUFFER,
        PATH
    }

    public Image(VEContext context, int id, String name) {
        super(context, ContentType.IMAGE, id, name);
        this.needUpdate = true;
        this.targetWidth = 0;
        this.targetHeight = 0;
        this.orientation = 0;
        this.imageStorageType = ImageStorageType.PATH;
    }

    public Image setFilePath(String filePath) {
        this.imageStorageType = ImageStorageType.PATH;
        resetBitmap();
        updateImage(filePath);
        this.filePath = filePath;
        this.needUpdate = true;
        return this;
    }

    @Override // com.samsung.vekit.Content.Content
    public Image setWidth(int width) {
        return (Image) super.setWidth(width);
    }

    @Override // com.samsung.vekit.Content.Content
    public Image setHeight(int height) {
        return (Image) super.setHeight(height);
    }

    @Override // com.samsung.vekit.Content.Content
    public Image setDuration(long duration) {
        return (Image) super.setDuration(duration);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public Image setBitmap(Bitmap bitmap) {
        resetBitmap();
        this.needUpdate = true;
        this.bitmap = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.imageStorageType = ImageStorageType.BUFFER;
        return this;
    }

    public ImageStorageType getImageStorageType() {
        return this.imageStorageType;
    }

    public Image setImageStorageType(ImageStorageType imageStorageType) {
        this.imageStorageType = imageStorageType;
        return this;
    }

    public Image setOrientation(int orientation) {
        this.orientation = orientation;
        return this;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public Image setTargetSize(int targetwidth, int targetHeight) {
        if (targetwidth <= 0 || targetHeight <= 0) {
            return this;
        }
        this.targetWidth = targetwidth;
        this.targetHeight = targetHeight;
        return this;
    }

    public Image setSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            return this;
        }
        setWidth(width);
        setHeight(height);
        int sampleSize = ImageUtil.calculateInSampleSize(width, height, 3840, 3840);
        setTargetSize(width / sampleSize, height / sampleSize);
        return this;
    }

    private void updateImage(String filePath) {
        ImageInfo info = ImageUtil.parseImage(filePath);
        setOrientation(info.getOrientation());
    }

    private void resetBitmap() {
        this.filePath = null;
        if (this.bitmap != null && !this.bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
    }

    @Override // com.samsung.vekit.Common.Object.Element
    public void update() {
        super.update();
        this.needUpdate = false;
    }

    public int getTargetWidth() {
        return this.targetWidth;
    }

    public int getTargetHeight() {
        return this.targetHeight;
    }
}
