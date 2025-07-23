package com.samsung.android.content.smartclip;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SemSmartClipDataRepository implements Parcelable {
    public static final String CONTENT_TYPE_AUDIO = "music";
    public static final String CONTENT_TYPE_DEFAULT = "image";
    public static final String CONTENT_TYPE_IMAGE = "image";
    public static final String CONTENT_TYPE_VIDEO = "video";
    public static final String CONTENT_TYPE_WEB = "web";
    public static final String CONTENT_TYPE_YOUTUBE = "youtube";
    public static final Parcelable.Creator<SemSmartClipDataRepository> CREATOR = new Parcelable.Creator<SemSmartClipDataRepository>() { // from class: com.samsung.android.content.smartclip.SemSmartClipDataRepository.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSmartClipDataRepository createFromParcel(Parcel parcel) {
            Log.d(SemSmartClipDataRepository.TAG, "SemSmartClipDataRepository.createFromParcel called");
            SemSmartClipDataRepository semSmartClipDataRepository = new SemSmartClipDataRepository();
            semSmartClipDataRepository.readFromParcel(parcel);
            return semSmartClipDataRepository;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemSmartClipDataRepository[] newArray(int i) {
            return new SemSmartClipDataRepository[i];
        }
    };
    protected static final String FIELD_CAPTURED_IMAGE_PATH = "captured_image_path";
    protected static final String FIELD_CAPTURED_IMAGE_STYLE = "captured_image_style";
    protected static final String FIELD_CONTENT_RECT = "content_rect";
    protected static final String FIELD_CONTENT_TYPE = "content_type";
    protected static final String FIELD_META_TAGS = "meta_tags";
    protected static final String FIELD_META_TAG_EXTRA_DATA = "meta_tag_extra_value";
    protected static final String FIELD_META_TAG_TYPE = "meta_tag_type";
    protected static final String FIELD_META_TAG_VALUE = "meta_tag_value";
    protected static final String FIELD_REPOSITORY_ID = "repository_id";
    public static final int IMAGE_STYLE_LASSO = 0;
    public static final int IMAGE_STYLE_PIN_MODE = 3;
    public static final int IMAGE_STYLE_RECTANGLE = 1;
    public static final int IMAGE_STYLE_SEGMENTATION = 2;
    protected static final String TAG = "SemSmartClipDataRepository";
    protected String mAppPackageName;
    protected String mCapturedImageFilePath;
    protected int mCapturedImageFileStyle;
    protected Rect mContentRect;
    protected String mContentType;
    protected SmartClipDataCropperImpl mCropper;
    private int mPenWindowBorder;
    protected String mRepositoryId;
    protected SmartClipDataRootElement mRootElement;
    private RectF mScaleRect;
    protected SmartClipMetaTagArrayImpl mTags;
    protected int mTargetWindowLayer;
    private Rect mWinFrameRect;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemSmartClipMetaTagArray extractMetaTagFromString(Context context, String str) {
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SemSmartClipDataRepository() {
        this(null);
    }

    public SemSmartClipDataRepository(SemSmartClipDataCropper semSmartClipDataCropper) {
        this(semSmartClipDataCropper, new Rect(0, 0, 0, 0), new RectF(0.0f, 0.0f, 1.0f, 1.0f));
    }

    public SemSmartClipDataRepository(SemSmartClipDataCropper semSmartClipDataCropper, Rect rect, RectF rectF) {
        this(semSmartClipDataCropper, new Rect(0, 0, 0, 0), new RectF(0.0f, 0.0f, 1.0f, 1.0f), 0);
    }

    public SemSmartClipDataRepository(SemSmartClipDataCropper semSmartClipDataCropper, Rect rect, RectF rectF, int i) {
        this.mRootElement = new SmartClipDataRootElement();
        this.mContentType = null;
        this.mContentRect = null;
        this.mTags = null;
        this.mCropper = null;
        this.mCapturedImageFilePath = null;
        this.mCapturedImageFileStyle = 1;
        this.mAppPackageName = null;
        this.mTargetWindowLayer = -1;
        this.mRepositoryId = null;
        this.mWinFrameRect = null;
        this.mScaleRect = null;
        this.mPenWindowBorder = 0;
        this.mCropper = (SmartClipDataCropperImpl) semSmartClipDataCropper;
        this.mWinFrameRect = new Rect(rect);
        this.mScaleRect = new RectF(rectF);
        this.mPenWindowBorder = i;
    }

    public SemSmartClipDataCropper getSmartClipDataCropper() {
        return this.mCropper;
    }

    public String getCapturedImageFilePath() {
        return this.mCapturedImageFilePath;
    }

    public int getCapturedImageFileStyle() {
        return this.mCapturedImageFileStyle;
    }

    public String getAppPackageName() {
        return this.mAppPackageName;
    }

    public void setCapturedImageFilePath(String str) {
        setCapturedImage(str, 1);
    }

    public void setCapturedImage(String str, int i) {
        this.mCapturedImageFilePath = str;
        this.mCapturedImageFileStyle = i;
    }

    public void setAppPackageName(String str) {
        this.mAppPackageName = str;
    }

    public boolean determineContentType() {
        String str;
        boolean z;
        SmartClipDataElementImpl smartClipDataElementImpl = this.mRootElement;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        while (smartClipDataElementImpl != null) {
            View view = smartClipDataElementImpl.getView();
            if (view != null) {
                Iterator<SemSmartClipMetaTag> it = getMetaTag("url").iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    String value = it.next().getValue();
                    if (value != null && !value.isEmpty()) {
                        z = true;
                        break;
                    }
                }
                if (getMetaTag(SemSmartClipMetaTagType.FILE_PATH_AUDIO).size() > 0) {
                    z2 = true;
                }
                if (getMetaTag(SemSmartClipMetaTagType.FILE_PATH_VIDEO).size() > 0) {
                    z3 = true;
                }
                if (getMetaTag(SemSmartClipMetaTagType.FILE_PATH_IMAGE).size() > 0) {
                    z4 = true;
                }
                if (z) {
                    if ((view instanceof WebView) || view.getClass().getName().equals("android.webkitsec.WebView") || view.getClass().getName().equals("org.chromium.content.browser.ChromeView") || view.getClass().getName().equals("org.samsung.content.sbrowser.SbrContentView") || view.getClass().getName().equals("com.sec.chromium.content.browser.SbrContentView") || view.getClass().getName().equals("org.chromium.content.browser.JellyBeanContentView")) {
                        z6 = true;
                    } else {
                        String str2 = this.mAppPackageName;
                        if (str2 != null && str2.equals("com.google.android.youtube") && view.getClass().getName().endsWith("PlayerView")) {
                            z5 = true;
                        }
                    }
                }
                if (getMetaTag(SemSmartClipMetaTagType.HTML).size() > 0) {
                    z6 = true;
                }
            }
            smartClipDataElementImpl = smartClipDataElementImpl.traverseNextElement(this.mRootElement);
        }
        if (z2) {
            str = CONTENT_TYPE_AUDIO;
        } else if (z3) {
            str = "video";
        } else {
            str = "image";
            if (!z4) {
                if (z5) {
                    str = CONTENT_TYPE_YOUTUBE;
                } else if (z6) {
                    str = CONTENT_TYPE_WEB;
                }
            }
        }
        this.mContentType = str;
        return true;
    }

    public SemSmartClipDataElement getRootElement() {
        return this.mRootElement;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public int getWindowLayer() {
        return this.mTargetWindowLayer;
    }

    public void setWindowLayer(int i) {
        this.mTargetWindowLayer = i;
    }

    public String getMergedPlainTextTag() {
        SmartClipDataRootElement smartClipDataRootElement = this.mRootElement;
        if (smartClipDataRootElement == null) {
            return null;
        }
        return smartClipDataRootElement.collectPlainTextTag();
    }

    public Rect getContentRect() {
        Rect rect = this.mContentRect;
        if (rect != null) {
            return rect;
        }
        SmartClipDataElementImpl smartClipDataElementImpl = this.mRootElement;
        Rect rect2 = new Rect(Process.LAST_ISOLATED_UID, Process.LAST_ISOLATED_UID, 0, 0);
        while (smartClipDataElementImpl != null) {
            if (smartClipDataElementImpl.getChildCount() != 1) {
                if (smartClipDataElementImpl.getChildCount() > 1) {
                    for (SmartClipDataElementImpl firstChild = smartClipDataElementImpl.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                        Rect metaAreaRect = firstChild.getMetaAreaRect();
                        if (metaAreaRect != null) {
                            if (rect2.left > metaAreaRect.left && metaAreaRect.width() > 0) {
                                rect2.left = metaAreaRect.left;
                            }
                            if (rect2.top > metaAreaRect.top && metaAreaRect.height() > 0) {
                                rect2.top = metaAreaRect.top;
                            }
                            if (rect2.right < metaAreaRect.right && metaAreaRect.width() > 0) {
                                rect2.right = metaAreaRect.right;
                            }
                            if (rect2.bottom < metaAreaRect.bottom && metaAreaRect.height() > 0) {
                                rect2.bottom = metaAreaRect.bottom;
                            }
                        }
                    }
                } else {
                    Rect metaAreaRect2 = smartClipDataElementImpl.getMetaAreaRect();
                    if (metaAreaRect2 != null) {
                        if (rect2.left > metaAreaRect2.left && metaAreaRect2.width() > 0) {
                            rect2.left = metaAreaRect2.left;
                        }
                        if (rect2.top > metaAreaRect2.top && metaAreaRect2.height() > 0) {
                            rect2.top = metaAreaRect2.top;
                        }
                        if (rect2.right < metaAreaRect2.right && metaAreaRect2.width() > 0) {
                            rect2.right = metaAreaRect2.right;
                        }
                        if (rect2.bottom < metaAreaRect2.bottom && metaAreaRect2.height() > 0) {
                            rect2.bottom = metaAreaRect2.bottom;
                        }
                    }
                }
            }
            smartClipDataElementImpl = smartClipDataElementImpl.traverseNextElement(this.mRootElement);
        }
        if (rect2.left > rect2.right) {
            return new Rect();
        }
        if (this.mScaleRect.width() != 1.0f || this.mScaleRect.height() != 1.0f) {
            float width = this.mScaleRect.width();
            float height = this.mScaleRect.height();
            if (width != 0.0f && height != 0.0f) {
                Rect rect3 = new Rect();
                rect3.left = this.mWinFrameRect.left;
                rect3.top = this.mWinFrameRect.top;
                rect3.right = (int) (this.mWinFrameRect.left + (this.mWinFrameRect.width() / width) + 0.5f);
                rect3.bottom = (int) (this.mWinFrameRect.top + (this.mWinFrameRect.height() / height) + 0.5f);
                if (this.mPenWindowBorder > 0) {
                    if (rect2.left < this.mPenWindowBorder) {
                        rect2.left += this.mPenWindowBorder;
                    }
                    if (rect2.right > rect3.width() - this.mPenWindowBorder) {
                        rect2.right -= this.mPenWindowBorder;
                    }
                    if (rect2.top < this.mPenWindowBorder) {
                        rect2.top += this.mPenWindowBorder;
                    }
                    if (rect2.bottom > rect3.height() - this.mPenWindowBorder) {
                        rect2.bottom -= this.mPenWindowBorder;
                    }
                }
                int width2 = rect2.width();
                int height2 = rect2.height();
                rect2.left = rect3.left + ((int) (rect2.left * width));
                rect2.top = rect3.top + ((int) (rect2.top * height));
                rect2.right = rect2.left + ((int) (width2 * width));
                rect2.bottom = rect2.top + ((int) (height2 * height));
            }
        }
        return rect2;
    }

    public SemSmartClipMetaTagArray getAllMetaTags() {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
        if (smartClipMetaTagArrayImpl != null) {
            return smartClipMetaTagArrayImpl;
        }
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl2 = new SmartClipMetaTagArrayImpl();
        for (SmartClipDataElementImpl smartClipDataElementImpl = this.mRootElement; smartClipDataElementImpl != null; smartClipDataElementImpl = smartClipDataElementImpl.traverseNextElement(null)) {
            SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl3 = (SmartClipMetaTagArrayImpl) smartClipDataElementImpl.getTagTable();
            if (smartClipMetaTagArrayImpl3 != null) {
                int size = smartClipMetaTagArrayImpl3.size();
                for (int i = 0; i < size; i++) {
                    SemSmartClipMetaTag semSmartClipMetaTag = (SemSmartClipMetaTag) smartClipMetaTagArrayImpl3.get(i);
                    if (!semSmartClipMetaTag.getType().equals(SemSmartClipMetaTagType.PLAIN_TEXT)) {
                        smartClipMetaTagArrayImpl2.add(semSmartClipMetaTag);
                    }
                }
            }
        }
        return smartClipMetaTagArrayImpl2;
    }

    public SemSmartClipMetaTagArray getMetaTag(String str) {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = new SmartClipMetaTagArrayImpl();
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl2 = this.mTags;
        if (smartClipMetaTagArrayImpl2 != null) {
            int size = smartClipMetaTagArrayImpl2.size();
            for (int i = 0; i < size; i++) {
                String type = ((SemSmartClipMetaTag) this.mTags.get(i)).getType();
                if (type != null && type.equals(str)) {
                    smartClipMetaTagArrayImpl.add((SemSmartClipMetaTag) this.mTags.get(i));
                }
            }
        } else if (SemSmartClipMetaTagType.PLAIN_TEXT.equals(str)) {
            String mergedPlainTextTag = getMergedPlainTextTag();
            if (mergedPlainTextTag != null) {
                smartClipMetaTagArrayImpl.add(new SemSmartClipMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, mergedPlainTextTag));
                return smartClipMetaTagArrayImpl;
            }
        } else {
            for (SmartClipDataElementImpl smartClipDataElementImpl = this.mRootElement; smartClipDataElementImpl != null; smartClipDataElementImpl = smartClipDataElementImpl.traverseNextElement(null)) {
                SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl3 = (SmartClipMetaTagArrayImpl) smartClipDataElementImpl.getTagTable();
                if (smartClipMetaTagArrayImpl3 != null) {
                    int size2 = smartClipMetaTagArrayImpl3.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        SemSmartClipMetaTag semSmartClipMetaTag = (SemSmartClipMetaTag) smartClipMetaTagArrayImpl3.get(i2);
                        if (semSmartClipMetaTag.getValue() != null && semSmartClipMetaTag.getType().equals(str)) {
                            smartClipMetaTagArrayImpl.add(semSmartClipMetaTag);
                        }
                    }
                }
            }
        }
        return smartClipMetaTagArrayImpl;
    }

    public boolean dump(boolean z) {
        Log.d(TAG, "----- Start of SmartClip repository informations -----");
        Log.d(TAG, "** Content type : " + getContentType());
        Log.d(TAG, "** Meta area rect : " + getContentRect().toString());
        Log.d(TAG, "** Captured image file path : " + this.mCapturedImageFilePath);
        if (z) {
            Log.d(TAG, "** mTags");
            SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
            if (smartClipMetaTagArrayImpl != null) {
                smartClipMetaTagArrayImpl.dump();
            } else {
                Log.d(TAG, "mTags is null");
            }
            Log.d(TAG, "** Element tree **");
            SmartClipDataRootElement smartClipDataRootElement = this.mRootElement;
            if (smartClipDataRootElement != null) {
                smartClipDataRootElement.dump();
            }
        }
        Log.d(TAG, "----- End of SmartClip repository informations -----");
        return true;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mContentType == null) {
            determineContentType();
        }
        parcel.writeString(this.mContentType);
        parcel.writeString(this.mRepositoryId);
        Rect contentRect = getContentRect();
        this.mContentRect = contentRect;
        parcel.writeParcelable(contentRect, i);
        parcel.writeString(this.mCapturedImageFilePath);
        parcel.writeInt(this.mCapturedImageFileStyle);
        parcel.writeString(this.mAppPackageName);
        parcel.writeInt(this.mTargetWindowLayer);
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = (SmartClipMetaTagArrayImpl) getAllMetaTags();
        this.mTags = smartClipMetaTagArrayImpl;
        parcel.writeParcelable(smartClipMetaTagArrayImpl, i);
    }

    public void readFromParcel(Parcel parcel) {
        this.mContentType = parcel.readString();
        this.mRepositoryId = parcel.readString();
        this.mContentRect = (Rect) parcel.readParcelable(Rect.class.getClassLoader());
        this.mCapturedImageFilePath = parcel.readString();
        this.mCapturedImageFileStyle = parcel.readInt();
        this.mAppPackageName = parcel.readString();
        this.mTargetWindowLayer = parcel.readInt();
        this.mTags = (SmartClipMetaTagArrayImpl) parcel.readParcelable(SmartClipMetaTagArrayImpl.class.getClassLoader());
    }
}
