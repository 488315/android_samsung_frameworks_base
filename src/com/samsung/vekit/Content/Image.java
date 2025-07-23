package com.samsung.vekit.Content;

import android.graphics.Bitmap;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ImageDecoderType;
import com.samsung.vekit.Common.Util.ImageUtil;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Image extends Content {
    private static final int IMAGE_BUFFER_SIZE = 3840;
    private Bitmap bitmap;
    private String filePath;
    private ImageDecoderType imageDecoderType;
    private ImageStorageType imageStorageType;
    private boolean needUpdate;
    private int orientation;
    private int targetHeight;
    private int targetWidth;

    enum ImageStorageType {
        BUFFER,
        PATH
    }

    public Image(VEContext vEContext, int i, String str) {
        super(vEContext, ContentType.IMAGE, i, str);
        this.needUpdate = true;
        this.targetWidth = 0;
        this.targetHeight = 0;
        this.orientation = 0;
        this.imageDecoderType = ImageDecoderType.DEFAULT;
        this.imageStorageType = ImageStorageType.PATH;
    }

    public Image setFilePath(String str) {
        this.imageStorageType = ImageStorageType.PATH;
        resetBitmap();
        updateImage(str);
        this.filePath = str;
        this.needUpdate = true;
        return this;
    }

    @Override // com.samsung.vekit.Content.Content
    public Image setWidth(int i) {
        return (Image) super.setWidth(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Image setHeight(int i) {
        return (Image) super.setHeight(i);
    }

    @Override // com.samsung.vekit.Content.Content
    public Image setDuration(long j) {
        return (Image) super.setDuration(j);
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

    public Image setOrientation(int i) {
        this.orientation = i;
        return this;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public Image setTargetSize(int i, int i2) {
        if (i > 0 && i2 > 0) {
            this.targetWidth = i;
            this.targetHeight = i2;
        }
        return this;
    }

    public Image setSize(int i, int i2) {
        if (i > 0 && i2 > 0) {
            setWidth(i);
            setHeight(i2);
            int calculateInSampleSize = ImageUtil.calculateInSampleSize(i, i2, 3840, 3840);
            setTargetSize(i / calculateInSampleSize, i2 / calculateInSampleSize);
        }
        return this;
    }

    private void updateImage(String str) {
        setOrientation(ImageUtil.parseImage(str).getOrientation());
    }

    private void resetBitmap() {
        this.filePath = null;
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
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

    public void setImageDecoderType(ImageDecoderType imageDecoderType) {
        this.imageDecoderType = imageDecoderType;
    }

    public ImageDecoderType getImageDecoderType() {
        return this.imageDecoderType;
    }
}
