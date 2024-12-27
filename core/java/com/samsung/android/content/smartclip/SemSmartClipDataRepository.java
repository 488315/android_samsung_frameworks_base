package com.samsung.android.content.smartclip;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/* loaded from: classes5.dex */
public class SemSmartClipDataRepository implements Parcelable {
    public static final String CONTENT_TYPE_AUDIO = "music";
    public static final String CONTENT_TYPE_DEFAULT = "image";
    public static final String CONTENT_TYPE_IMAGE = "image";
    public static final String CONTENT_TYPE_VIDEO = "video";
    public static final String CONTENT_TYPE_WEB = "web";
    public static final String CONTENT_TYPE_YOUTUBE = "youtube";
    public static final Parcelable.Creator<SemSmartClipDataRepository> CREATOR =
            new Parcelable.Creator<
                    SemSmartClipDataRepository>() { // from class:
                                                    // com.samsung.android.content.smartclip.SemSmartClipDataRepository.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSmartClipDataRepository createFromParcel(Parcel in) {
                    Log.d(
                            SemSmartClipDataRepository.TAG,
                            "SemSmartClipDataRepository.createFromParcel called");
                    SemSmartClipDataRepository data = new SemSmartClipDataRepository();
                    data.readFromParcel(in);
                    return data;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemSmartClipDataRepository[] newArray(int size) {
                    return new SemSmartClipDataRepository[size];
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

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SemSmartClipDataRepository() {
        this((SemSmartClipDataCropper) null);
    }

    public SemSmartClipDataRepository(String encodedStr) {
        this();
        if (encodedStr != null) {
            setupRepositoryFromString(encodedStr, this);
            return;
        }
        throw new IllegalArgumentException();
    }

    public SemSmartClipDataRepository(SemSmartClipDataCropper cropper) {
        this(cropper, new Rect(0, 0, 0, 0), new RectF(0.0f, 0.0f, 1.0f, 1.0f));
    }

    public SemSmartClipDataRepository(
            SemSmartClipDataCropper cropper, Rect winFrameRect, RectF scaleRect) {
        this(cropper, new Rect(0, 0, 0, 0), new RectF(0.0f, 0.0f, 1.0f, 1.0f), 0);
    }

    public SemSmartClipDataRepository(
            SemSmartClipDataCropper cropper,
            Rect winFrameRect,
            RectF scaleRect,
            int penWindowBorderWidth) {
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
        this.mCropper = (SmartClipDataCropperImpl) cropper;
        this.mWinFrameRect = new Rect(winFrameRect);
        this.mScaleRect = new RectF(scaleRect);
        this.mPenWindowBorder = penWindowBorderWidth;
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

    public void setCapturedImageFilePath(String capturedImageFilePath) {
        setCapturedImage(capturedImageFilePath, 1);
    }

    public void setCapturedImage(String capturedImageFilePath, int imageStyle) {
        this.mCapturedImageFilePath = capturedImageFilePath;
        this.mCapturedImageFileStyle = imageStyle;
    }

    public void setAppPackageName(String packageName) {
        this.mAppPackageName = packageName;
    }

    public boolean determineContentType() {
        String contentType;
        boolean bHaveBrowserView = false;
        boolean bHaveYoutubeView = false;
        boolean bHaveAudioFilePath = false;
        boolean bHaveVideoFilePath = false;
        boolean bHaveImageFilePath = false;
        SmartClipDataElementImpl element = this.mRootElement;
        while (element != null) {
            View view = element.getView();
            if (view != null) {
                boolean bHaveUrlTag = false;
                SemSmartClipMetaTagArray urlMetaArray = getMetaTag("url");
                Iterator<SemSmartClipMetaTag> it = urlMetaArray.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    SemSmartClipMetaTag urlMeta = it.next();
                    String url = urlMeta.getValue();
                    if (url != null && !url.isEmpty()) {
                        bHaveUrlTag = true;
                        break;
                    }
                }
                if (getMetaTag(SemSmartClipMetaTagType.FILE_PATH_AUDIO).size() > 0) {
                    bHaveAudioFilePath = true;
                }
                if (getMetaTag(SemSmartClipMetaTagType.FILE_PATH_VIDEO).size() > 0) {
                    bHaveVideoFilePath = true;
                }
                if (getMetaTag(SemSmartClipMetaTagType.FILE_PATH_IMAGE).size() > 0) {
                    bHaveImageFilePath = true;
                }
                if (bHaveUrlTag) {
                    if ((view instanceof WebView)
                            || view.getClass().getName().equals("android.webkitsec.WebView")
                            || view.getClass()
                                    .getName()
                                    .equals("org.chromium.content.browser.ChromeView")
                            || view.getClass()
                                    .getName()
                                    .equals("org.samsung.content.sbrowser.SbrContentView")
                            || view.getClass()
                                    .getName()
                                    .equals("com.sec.chromium.content.browser.SbrContentView")
                            || view.getClass()
                                    .getName()
                                    .equals("org.chromium.content.browser.JellyBeanContentView")) {
                        bHaveBrowserView = true;
                    } else if (this.mAppPackageName != null
                            && this.mAppPackageName.equals("com.google.android.youtube")
                            && view.getClass().getName().endsWith("PlayerView")) {
                        bHaveYoutubeView = true;
                    }
                }
                if (getMetaTag(SemSmartClipMetaTagType.HTML).size() > 0) {
                    bHaveBrowserView = true;
                }
            }
            element = element.traverseNextElement(this.mRootElement);
        }
        if (bHaveAudioFilePath) {
            contentType = CONTENT_TYPE_AUDIO;
        } else if (bHaveVideoFilePath) {
            contentType = "video";
        } else if (bHaveImageFilePath) {
            contentType = "image";
        } else if (bHaveYoutubeView) {
            contentType = CONTENT_TYPE_YOUTUBE;
        } else if (bHaveBrowserView) {
            contentType = CONTENT_TYPE_WEB;
        } else {
            contentType = "image";
        }
        this.mContentType = contentType;
        return true;
    }

    public SemSmartClipDataElement getRootElement() {
        return this.mRootElement;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }

    public String getRepositoryId() {
        return this.mRepositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.mRepositoryId = repositoryId;
    }

    public int getWindowLayer() {
        return this.mTargetWindowLayer;
    }

    public void setWindowLayer(int layer) {
        this.mTargetWindowLayer = layer;
    }

    public String getMergedPlainTextTag() {
        if (this.mRootElement == null) {
            return null;
        }
        return this.mRootElement.collectPlainTextTag();
    }

    public Rect getContentRect() {
        if (this.mContentRect != null) {
            return this.mContentRect;
        }
        SmartClipDataElementImpl element = this.mRootElement;
        Rect contentRect = new Rect(Process.LAST_ISOLATED_UID, Process.LAST_ISOLATED_UID, 0, 0);
        while (element != null) {
            if (element.getChildCount() != 1) {
                if (element.getChildCount() > 1) {
                    for (SmartClipDataElementImpl child = element.getFirstChild();
                            child != null;
                            child = child.getNextSibling()) {
                        Rect rect = child.getMetaAreaRect();
                        if (rect != null) {
                            if (contentRect.left > rect.left && rect.width() > 0) {
                                contentRect.left = rect.left;
                            }
                            if (contentRect.top > rect.top && rect.height() > 0) {
                                contentRect.top = rect.top;
                            }
                            if (contentRect.right < rect.right && rect.width() > 0) {
                                contentRect.right = rect.right;
                            }
                            if (contentRect.bottom < rect.bottom && rect.height() > 0) {
                                contentRect.bottom = rect.bottom;
                            }
                        }
                    }
                } else {
                    Rect rect2 = element.getMetaAreaRect();
                    if (rect2 != null) {
                        if (contentRect.left > rect2.left && rect2.width() > 0) {
                            contentRect.left = rect2.left;
                        }
                        if (contentRect.top > rect2.top && rect2.height() > 0) {
                            contentRect.top = rect2.top;
                        }
                        if (contentRect.right < rect2.right && rect2.width() > 0) {
                            contentRect.right = rect2.right;
                        }
                        if (contentRect.bottom < rect2.bottom && rect2.height() > 0) {
                            contentRect.bottom = rect2.bottom;
                        }
                    }
                }
            }
            element = element.traverseNextElement(this.mRootElement);
        }
        if (contentRect.left > contentRect.right) {
            return new Rect();
        }
        if (this.mScaleRect.width() != 1.0f || this.mScaleRect.height() != 1.0f) {
            float hScale = this.mScaleRect.width();
            float vScale = this.mScaleRect.height();
            if (hScale != 0.0f && vScale != 0.0f) {
                Rect unScaledWinFrame = new Rect();
                unScaledWinFrame.left = this.mWinFrameRect.left;
                unScaledWinFrame.top = this.mWinFrameRect.top;
                unScaledWinFrame.right =
                        (int)
                                (this.mWinFrameRect.left
                                        + (this.mWinFrameRect.width() / hScale)
                                        + 0.5f);
                unScaledWinFrame.bottom =
                        (int)
                                (this.mWinFrameRect.top
                                        + (this.mWinFrameRect.height() / vScale)
                                        + 0.5f);
                if (this.mPenWindowBorder > 0) {
                    if (contentRect.left < this.mPenWindowBorder) {
                        contentRect.left += this.mPenWindowBorder;
                    }
                    if (contentRect.right > unScaledWinFrame.width() - this.mPenWindowBorder) {
                        contentRect.right -= this.mPenWindowBorder;
                    }
                    if (contentRect.top < this.mPenWindowBorder) {
                        contentRect.top += this.mPenWindowBorder;
                    }
                    if (contentRect.bottom > unScaledWinFrame.height() - this.mPenWindowBorder) {
                        contentRect.bottom -= this.mPenWindowBorder;
                    }
                }
                int contentWidth = contentRect.width();
                int contentHeight = contentRect.height();
                contentRect.left = unScaledWinFrame.left + ((int) (contentRect.left * hScale));
                contentRect.top = unScaledWinFrame.top + ((int) (contentRect.top * vScale));
                contentRect.right = contentRect.left + ((int) (contentWidth * hScale));
                contentRect.bottom = contentRect.top + ((int) (contentHeight * vScale));
            }
        }
        return contentRect;
    }

    public SemSmartClipMetaTagArray getAllMetaTags() {
        if (this.mTags != null) {
            return this.mTags;
        }
        SmartClipMetaTagArrayImpl metaList = new SmartClipMetaTagArrayImpl();
        for (SmartClipDataElementImpl element = this.mRootElement;
                element != null;
                element = element.traverseNextElement(null)) {
            SmartClipMetaTagArrayImpl tags = (SmartClipMetaTagArrayImpl) element.getTagTable();
            if (tags != null) {
                int tagCount = tags.size();
                for (int i = 0; i < tagCount; i++) {
                    SemSmartClipMetaTag curTag = (SemSmartClipMetaTag) tags.get(i);
                    if (!curTag.getType().equals(SemSmartClipMetaTagType.PLAIN_TEXT)) {
                        metaList.add(curTag);
                    }
                }
            }
        }
        String plainText = getMergedPlainTextTag();
        if (plainText != null) {
            metaList.add(
                    new SemSmartClipExtendedMetaTag(SemSmartClipMetaTagType.PLAIN_TEXT, plainText));
        }
        return metaList;
    }

    public SemSmartClipMetaTagArray getMetaTag(String tagType) {
        SmartClipMetaTagArrayImpl metaList = new SmartClipMetaTagArrayImpl();
        if (this.mTags != null) {
            int tagCount = this.mTags.size();
            for (int i = 0; i < tagCount; i++) {
                String curTagType = ((SemSmartClipMetaTag) this.mTags.get(i)).getType();
                if (curTagType != null && curTagType.equals(tagType)) {
                    metaList.add((SemSmartClipMetaTag) this.mTags.get(i));
                }
            }
        } else if (SemSmartClipMetaTagType.PLAIN_TEXT.equals(tagType)) {
            String plainText = getMergedPlainTextTag();
            if (plainText != null) {
                metaList.add(
                        new SemSmartClipExtendedMetaTag(
                                SemSmartClipMetaTagType.PLAIN_TEXT, plainText));
            }
        } else {
            for (SmartClipDataElementImpl element = this.mRootElement;
                    element != null;
                    element = element.traverseNextElement(null)) {
                SmartClipMetaTagArrayImpl tags = (SmartClipMetaTagArrayImpl) element.getTagTable();
                if (tags != null) {
                    int tagCount2 = tags.size();
                    for (int i2 = 0; i2 < tagCount2; i2++) {
                        SemSmartClipMetaTag curTag = (SemSmartClipMetaTag) tags.get(i2);
                        if (curTag.getValue() != null && curTag.getType().equals(tagType)) {
                            metaList.add(curTag);
                        }
                    }
                }
            }
        }
        return metaList;
    }

    public SemSmartClipMetaTagArray extractMetaTagFromString(Context context, String srcString) {
        return null;
    }

    public boolean dump(boolean dumpMetaTags) {
        Log.d(TAG, "----- Start of SmartClip repository informations -----");
        Log.d(TAG, "** Content type : " + getContentType());
        Log.d(TAG, "** Meta area rect : " + getContentRect().toString());
        Log.d(TAG, "** Captured image file path : " + this.mCapturedImageFilePath);
        if (dumpMetaTags) {
            Log.d(TAG, "** mTags");
            if (this.mTags != null) {
                this.mTags.dump();
            } else {
                Log.d(TAG, "mTags is null");
            }
            Log.d(TAG, "** Element tree **");
            if (this.mRootElement != null) {
                this.mRootElement.dump();
            }
        }
        Log.d(TAG, "----- End of SmartClip repository informations -----");
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (this.mContentType == null) {
            determineContentType();
        }
        out.writeString(this.mContentType);
        out.writeString(this.mRepositoryId);
        this.mContentRect = getContentRect();
        out.writeParcelable(this.mContentRect, flags);
        out.writeString(this.mCapturedImageFilePath);
        out.writeInt(this.mCapturedImageFileStyle);
        out.writeString(this.mAppPackageName);
        out.writeInt(this.mTargetWindowLayer);
        this.mTags = (SmartClipMetaTagArrayImpl) getAllMetaTags();
        out.writeParcelable(this.mTags, flags);
    }

    public void readFromParcel(Parcel in) {
        this.mContentType = in.readString();
        this.mRepositoryId = in.readString();
        this.mContentRect = (Rect) in.readParcelable(Rect.class.getClassLoader());
        this.mCapturedImageFilePath = in.readString();
        this.mCapturedImageFileStyle = in.readInt();
        this.mAppPackageName = in.readString();
        this.mTargetWindowLayer = in.readInt();
        SmartClipMetaTagArrayImpl listArray =
                (SmartClipMetaTagArrayImpl)
                        in.readParcelable(SemSmartClipExtendedMetaTag.class.getClassLoader());
        this.mTags = listArray;
    }

    private void setupRepositoryFromString(String jsonStr, SemSmartClipDataRepository repository) {
        SemSmartClipMetaTag metaTag;
        try {
            try {
                JSONObject json_repository = new JSONObject(jsonStr);
                if (json_repository.has(FIELD_CONTENT_TYPE)) {
                    repository.mContentType = json_repository.getString(FIELD_CONTENT_TYPE);
                }
                if (json_repository.has(FIELD_REPOSITORY_ID)) {
                    repository.mRepositoryId = json_repository.getString(FIELD_REPOSITORY_ID);
                }
                if (json_repository.has(FIELD_CONTENT_RECT)) {
                    JSONArray json_rect = json_repository.getJSONArray(FIELD_CONTENT_RECT);
                    repository.mContentRect =
                            new Rect(
                                    json_rect.getInt(0),
                                    json_rect.getInt(1),
                                    json_rect.getInt(2),
                                    json_rect.getInt(3));
                }
                if (json_repository.has(FIELD_CAPTURED_IMAGE_PATH)) {
                    String capturedImageFilePath =
                            json_repository.getString(FIELD_CAPTURED_IMAGE_PATH);
                    int capturedImageFileStyle = json_repository.getInt(FIELD_CAPTURED_IMAGE_STYLE);
                    if (capturedImageFilePath != null) {
                        repository.setCapturedImage(capturedImageFilePath, capturedImageFileStyle);
                    }
                }
                if (json_repository.has(FIELD_META_TAGS)) {
                    repository.mTags = new SmartClipMetaTagArrayImpl();
                    JSONArray json_tagArray = json_repository.getJSONArray(FIELD_META_TAGS);
                    int tagCount = json_tagArray.length();
                    for (int i = 0; i < tagCount; i++) {
                        JSONObject json_tag = json_tagArray.getJSONObject(i);
                        String tagType = json_tag.getString(FIELD_META_TAG_TYPE);
                        String tagValue = "";
                        try {
                            tagValue = json_tag.getString(FIELD_META_TAG_VALUE);
                        } catch (JSONException e) {
                            Log.e(TAG, "There is no meta value! type=" + tagType);
                        }
                        try {
                            String extraDataStr = json_tag.getString(FIELD_META_TAG_EXTRA_DATA);
                            byte[] extraData = Base64.decode(extraDataStr, 0);
                            Log.d(TAG, "Decoding : Length of extra data = " + extraData.length);
                            metaTag = new SemSmartClipExtendedMetaTag(tagType, tagValue, extraData);
                        } catch (JSONException e2) {
                            metaTag = new SemSmartClipMetaTag(tagType, tagValue);
                        }
                        repository.mTags.addMetaTag(metaTag);
                    }
                }
            } catch (JSONException e3) {
                e = e3;
                Log.e(TAG, "JSONException throwed : " + e.toString());
                e.printStackTrace();
            }
        } catch (JSONException e4) {
            e = e4;
        }
    }

    public String encodeRepositoryToString() {
        try {
            JSONObject jSONObject = new JSONObject();
            if (getContentType() != null) {
                jSONObject.put(FIELD_CONTENT_TYPE, getContentType());
            }
            if (getRepositoryId() != null) {
                jSONObject.put(FIELD_REPOSITORY_ID, getRepositoryId());
            }
            Rect contentRect = getContentRect();
            JSONArray json_rect = new JSONArray();
            json_rect.put(0, contentRect.left);
            json_rect.put(1, contentRect.top);
            json_rect.put(2, contentRect.right);
            json_rect.put(3, contentRect.bottom);
            jSONObject.put(FIELD_CONTENT_RECT, json_rect);
            String capturedImageFilePath = getCapturedImageFilePath();
            int capturedImageFileStyle = getCapturedImageFileStyle();
            if (capturedImageFilePath != null) {
                jSONObject.put(FIELD_CAPTURED_IMAGE_PATH, capturedImageFilePath);
                jSONObject.put(FIELD_CAPTURED_IMAGE_STYLE, capturedImageFileStyle);
            }
            SemSmartClipMetaTagArray tagArray = getAllMetaTags();
            JSONArray json_tagArray = new JSONArray();
            Iterator<SemSmartClipMetaTag> it = tagArray.iterator();
            while (it.hasNext()) {
                SemSmartClipMetaTag curTag = it.next();
                if (curTag != null) {
                    JSONObject json_tag = new JSONObject();
                    json_tag.put(FIELD_META_TAG_TYPE, curTag.getType());
                    json_tag.put(FIELD_META_TAG_VALUE, curTag.getValue());
                    if (curTag instanceof SemSmartClipExtendedMetaTag) {
                        SemSmartClipExtendedMetaTag curTagImpl =
                                (SemSmartClipExtendedMetaTag) curTag;
                        if (curTagImpl.getExtraData() != null) {
                            byte[] extraData = curTagImpl.getExtraData();
                            Log.d(TAG, "Encoding : Length of extra data = " + extraData.length);
                            json_tag.put(
                                    FIELD_META_TAG_EXTRA_DATA, Base64.encodeToString(extraData, 0));
                        }
                    }
                    json_tagArray.put(json_tag);
                }
            }
            if (json_tagArray.length() > 0) {
                jSONObject.put(FIELD_META_TAGS, json_tagArray);
            }
            return jSONObject.toString(1);
        } catch (JSONException e) {
            Log.e(TAG, "JSONException throwed : " + e.toString());
            e.printStackTrace();
            return "";
        }
    }
}
