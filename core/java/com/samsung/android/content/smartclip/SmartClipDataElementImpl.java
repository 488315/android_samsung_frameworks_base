package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

/* loaded from: classes5.dex */
public class SmartClipDataElementImpl implements SemSmartClipDataElement {
    protected static final String TAG = "SmartClipDataElementImpl";
    protected SmartClipDataElementImpl mFirstChild;
    protected int mId;
    protected SmartClipDataElementImpl mLastChild;
    protected SmartClipDataElementImpl mNextSibling;
    protected SmartClipDataElementImpl mParent;
    protected SmartClipDataElementImpl mPrevSibling;
    protected Rect mRectOnScreen;
    protected SemSmartClipDataRepository mRepository;
    public SmartClipMetaTagArrayImpl mTags;
    protected View mView;

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public void setMetaAreaRect(Rect rect) {
        this.mRectOnScreen = rect;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public Rect getMetaAreaRect() {
        return this.mRectOnScreen;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public SemSmartClipMetaTagArray getAllTags() {
        if (this.mTags == null) {
            return new SmartClipMetaTagArrayImpl();
        }
        return this.mTags.getCopy();
    }

    public SemSmartClipMetaTagArray getTagTable() {
        return this.mTags;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public boolean setTag(SemSmartClipMetaTag metaTag) {
        if (metaTag == null) {
            return false;
        }
        if (this.mTags == null) {
            this.mTags = new SmartClipMetaTagArrayImpl();
        }
        if (metaTag.getType() == null) {
            return false;
        }
        this.mTags.removeMetaTags(metaTag.getType());
        this.mTags.add(metaTag);
        return true;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public SemSmartClipMetaTagArray getTags(String tagType) {
        if (this.mTags == null) {
            return new SmartClipMetaTagArrayImpl();
        }
        SemSmartClipMetaTagArray typedArray = this.mTags.getMetaTags(tagType);
        return typedArray;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public int removeTags(String tagType) {
        if (this.mTags == null) {
            return 0;
        }
        return this.mTags.removeMetaTags(tagType);
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public boolean addTag(SemSmartClipMetaTag metaTag) {
        if (metaTag == null) {
            return false;
        }
        if (this.mTags == null) {
            this.mTags = new SmartClipMetaTagArrayImpl();
        }
        if (!SmartClipUtils.isValidMetaTag(metaTag)) {
            return false;
        }
        this.mTags.add(metaTag);
        return true;
    }

    public void addTag(SmartClipMetaTagArrayImpl tagSet) {
        if (this.mTags == null) {
            this.mTags = new SmartClipMetaTagArrayImpl();
        }
        this.mTags.addAll(tagSet);
    }

    public void setTagTable(SmartClipMetaTagArrayImpl tagsArray) {
        this.mTags = tagsArray;
    }

    public SmartClipDataElementImpl() {
        this.mId = -1;
        this.mRectOnScreen = null;
        this.mView = null;
        this.mRepository = null;
        this.mTags = null;
        this.mParent = null;
        this.mFirstChild = null;
        this.mLastChild = null;
        this.mNextSibling = null;
        this.mPrevSibling = null;
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository repository) {
        this.mId = -1;
        this.mRectOnScreen = null;
        this.mView = null;
        this.mRepository = null;
        this.mTags = null;
        this.mParent = null;
        this.mFirstChild = null;
        this.mLastChild = null;
        this.mNextSibling = null;
        this.mPrevSibling = null;
        this.mRepository = repository;
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository repository, View view) {
        this(repository);
        this.mView = view;
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository repository, Rect screenRect) {
        this(repository);
        this.mRectOnScreen = new Rect(screenRect);
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository repository, View view, Rect screenRect) {
        this(repository, view);
        this.mRectOnScreen = new Rect(screenRect);
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public void clearMetaData() {
        this.mRectOnScreen = null;
        setTagTable(null);
    }

    public void setView(View view) {
        this.mView = view;
    }

    public View getView() {
        return this.mView;
    }

    public void setDataRepository(SemSmartClipDataRepository repository) {
        this.mRepository = repository;
    }

    public SemSmartClipDataRepository getDataRepository() {
        return this.mRepository;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public int getExtractionMode() {
        SmartClipDataCropperImpl cropper;
        if (this.mRepository == null || (cropper = (SmartClipDataCropperImpl) this.mRepository.getSmartClipDataCropper()) == null) {
            return 0;
        }
        return cropper.getExtractionMode();
    }

    public int getExtractionLevel() {
        SmartClipDataCropperImpl cropper;
        if (this.mRepository == null || (cropper = (SmartClipDataCropperImpl) this.mRepository.getSmartClipDataCropper()) == null) {
            return 0;
        }
        return cropper.getExtractionLevel();
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public boolean sendSuspendedExtractionData() {
        SmartClipDataCropperImpl cropper = null;
        SemSmartClipDataRepository repository = getDataRepository();
        if (repository != null) {
            cropper = (SmartClipDataCropperImpl) repository.getSmartClipDataCropper();
        }
        if (cropper != null) {
            return cropper.setPendingExtractionResult(this);
        }
        return false;
    }

    public boolean isEmptyTag(boolean includeChild) {
        if (!includeChild) {
            return this.mTags == null || this.mTags.size() <= 0;
        }
        SmartClipDataElementImpl element = this;
        while (element != null) {
            if (element.mTags != null && element.mTags.size() > 0) {
                return false;
            }
            element = element.traverseNextElement(this);
        }
        return true;
    }

    public SemSmartClipDataElement createChildInstance() {
        SemSmartClipDataElement newElement = newInstance();
        addChild(newElement);
        return newElement;
    }

    public SemSmartClipDataElement newInstance() {
        return new SmartClipDataElementImpl(this.mRepository);
    }

    public boolean addChild(SemSmartClipDataElement childToAdd) {
        if (childToAdd == null) {
            return false;
        }
        SmartClipDataElementImpl child = (SmartClipDataElementImpl) childToAdd;
        if (this.mFirstChild == null) {
            this.mFirstChild = child;
            this.mLastChild = child;
            child.setNextSibling(null);
            child.setPrevSibling(null);
            child.setParent(this);
            return true;
        }
        if (this.mLastChild == null) {
            return false;
        }
        SmartClipDataElementImpl lastChild = this.mLastChild;
        this.mLastChild = child;
        lastChild.setNextSibling(child);
        child.setPrevSibling(lastChild);
        child.setParent(this);
        return true;
    }

    public boolean removeChild(SemSmartClipDataElement childToRemove) {
        if (childToRemove == null) {
            return false;
        }
        SmartClipDataElementImpl child = (SmartClipDataElementImpl) childToRemove;
        if (child.getParent() != this) {
            Log.e(TAG, "removeChild : Incorrect parent of SemSmartClipDataElement. element=" + child);
            child.dump();
            return false;
        }
        if (this.mFirstChild == child) {
            this.mFirstChild = child.getNextSibling();
        }
        if (this.mLastChild == child) {
            this.mLastChild = child.getPrevSibling();
        }
        if (child.getPrevSibling() != null) {
            child.getPrevSibling().setNextSibling(child.getNextSibling());
        }
        if (child.getNextSibling() != null) {
            child.getNextSibling().setPrevSibling(child.getPrevSibling());
            return true;
        }
        return true;
    }

    private void setPrevSibling(SmartClipDataElementImpl sibling) {
        this.mPrevSibling = sibling;
    }

    private void setNextSibling(SmartClipDataElementImpl sibling) {
        this.mNextSibling = sibling;
    }

    private void setParent(SmartClipDataElementImpl parent) {
        this.mParent = parent;
    }

    public SmartClipDataElementImpl getParent() {
        return this.mParent;
    }

    public SmartClipDataElementImpl getFirstChild() {
        return this.mFirstChild;
    }

    public SmartClipDataElementImpl getLastChild() {
        return this.mLastChild;
    }

    public SmartClipDataElementImpl getNextSibling() {
        return this.mNextSibling;
    }

    public SmartClipDataElementImpl getPrevSibling() {
        return this.mPrevSibling;
    }

    public int getChildCount() {
        int childCount = 0;
        for (SmartClipDataElementImpl element = this.mFirstChild; element != null; element = element.getNextSibling()) {
            childCount++;
        }
        return childCount;
    }

    public int getParentCount() {
        int parentCount = 0;
        for (SmartClipDataElementImpl element = getParent(); element != null; element = element.getParent()) {
            parentCount++;
        }
        return parentCount;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0026, code lost:
    
        return r1.mNextSibling;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.samsung.android.content.smartclip.SmartClipDataElementImpl traverseNextElement(com.samsung.android.content.smartclip.SmartClipDataElementImpl r4) {
        /*
            r3 = this;
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r0 = r3.mFirstChild
            if (r0 == 0) goto L7
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r0 = r3.mFirstChild
            return r0
        L7:
            r0 = 0
            if (r3 != r4) goto Lb
            return r0
        Lb:
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r1 = r3.mNextSibling
            if (r1 == 0) goto L12
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r0 = r3.mNextSibling
            return r0
        L12:
            r1 = r3
        L13:
            if (r1 == 0) goto L22
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r2 = r1.mNextSibling
            if (r2 != 0) goto L22
            if (r4 == 0) goto L1f
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r2 = r1.mParent
            if (r2 == r4) goto L22
        L1f:
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r1 = r1.mParent
            goto L13
        L22:
            if (r1 == 0) goto L27
            com.samsung.android.content.smartclip.SmartClipDataElementImpl r0 = r1.mNextSibling
            return r0
        L27:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.content.smartclip.SmartClipDataElementImpl.traverseNextElement(com.samsung.android.content.smartclip.SmartClipDataElementImpl):com.samsung.android.content.smartclip.SmartClipDataElementImpl");
    }

    public String getDumpString(boolean addIndent, boolean showValue) {
        StringBuilder result = new StringBuilder();
        int parentCount = getParentCount();
        if (addIndent) {
            for (int i = 0; i < parentCount; i++) {
                result.append("\t");
            }
        }
        if (this.mRectOnScreen != null) {
            result.append("Rect(" + this.mRectOnScreen.left + ", " + this.mRectOnScreen.top + ", " + this.mRectOnScreen.right + ", " + this.mRectOnScreen.bottom + ")\t");
        } else {
            result.append("mRectOnScreen(NULL)\t");
        }
        if (this.mView != null) {
            result.append(this.mView.getClass().getSimpleName());
            int resId = this.mView.getId();
            if (resId == -1 || resId < 0) {
                result.append("@").append(Integer.toHexString(this.mView.hashCode())).append("\t");
            } else {
                try {
                    String viewResName = this.mView.getResources().getResourceEntryName(resId);
                    result.append("/").append(viewResName).append("\t");
                } catch (Exception e) {
                    result.append("@").append(Integer.toHexString(this.mView.hashCode())).append("\t");
                }
            }
            Drawable background = this.mView.getBackground();
            if (background != null && background.isVisible() && background.getOpacity() != -2) {
                result.append("Opacity BG(" + background.getOpacity() + ")\t");
            }
        }
        if (this.mTags != null) {
            int tagCount = this.mTags.size();
            for (int i2 = 0; i2 < tagCount; i2++) {
                SemSmartClipMetaTag tag = (SemSmartClipMetaTag) this.mTags.get(i2);
                String type = tag.getType();
                String value = tag.getValue();
                String extra = "";
                if (value == null) {
                    value = "null";
                }
                if (tag instanceof SemSmartClipExtendedMetaTag) {
                    SemSmartClipExtendedMetaTag tagImpl = (SemSmartClipExtendedMetaTag) tag;
                    if (tagImpl.getExtraData() != null) {
                        extra = ", Extra data size = " + tagImpl.getExtraData().length;
                    }
                    if (tagImpl.getParcelableData() != null) {
                        Parcelable obj = tagImpl.getParcelableData();
                        extra = (extra + ", Extra parcelable = ") + obj.toString();
                    }
                }
                if (!showValue) {
                    result.append(type).append("\t");
                } else {
                    result.append(type).append(NavigationBarInflaterView.KEY_CODE_START).append(value).append(extra).append(")\t");
                }
            }
        } else {
            result.append("No meta tag\t");
        }
        return result.toString();
    }

    public boolean dump() {
        String dumpStr = getDumpString(true, true);
        Log.e(TAG, dumpStr);
        for (SmartClipDataElementImpl element = getFirstChild(); element != null; element = element.getNextSibling()) {
            element.dump();
        }
        return true;
    }
}
