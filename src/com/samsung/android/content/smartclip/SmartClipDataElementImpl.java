package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.view.View;
import com.android.internal.protolog.PerfettoProtoLogImpl;

/* loaded from: classes6.dex */
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
    public Rect getMetaAreaRect() {
        return this.mRectOnScreen;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public SemSmartClipMetaTagArray getAllTags() {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
        if (smartClipMetaTagArrayImpl == null) {
            return new SmartClipMetaTagArrayImpl();
        }
        return smartClipMetaTagArrayImpl.getCopy();
    }

    public SemSmartClipMetaTagArray getTagTable() {
        return this.mTags;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public boolean setTag(SemSmartClipMetaTag semSmartClipMetaTag) {
        if (semSmartClipMetaTag == null) {
            return false;
        }
        if (this.mTags == null) {
            this.mTags = new SmartClipMetaTagArrayImpl();
        }
        if (semSmartClipMetaTag.getType() == null) {
            return false;
        }
        this.mTags.removeMetaTags(semSmartClipMetaTag.getType());
        this.mTags.add(semSmartClipMetaTag);
        return true;
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public SemSmartClipMetaTagArray getTags(String str) {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
        if (smartClipMetaTagArrayImpl == null) {
            return new SmartClipMetaTagArrayImpl();
        }
        return smartClipMetaTagArrayImpl.getMetaTags(str);
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public int removeTags(String str) {
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
        if (smartClipMetaTagArrayImpl == null) {
            return 0;
        }
        return smartClipMetaTagArrayImpl.removeMetaTags(str);
    }

    @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
    public boolean addTag(SemSmartClipMetaTag semSmartClipMetaTag) {
        if (semSmartClipMetaTag == null) {
            return false;
        }
        if (this.mTags == null) {
            this.mTags = new SmartClipMetaTagArrayImpl();
        }
        if (!SmartClipUtils.isValidMetaTag(semSmartClipMetaTag)) {
            return false;
        }
        this.mTags.add(semSmartClipMetaTag);
        return true;
    }

    public void addTag(SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl) {
        if (this.mTags == null) {
            this.mTags = new SmartClipMetaTagArrayImpl();
        }
        this.mTags.addAll(smartClipMetaTagArrayImpl);
    }

    public void setTagTable(SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl) {
        this.mTags = smartClipMetaTagArrayImpl;
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

    public SmartClipDataElementImpl(SemSmartClipDataRepository semSmartClipDataRepository) {
        this.mId = -1;
        this.mRectOnScreen = null;
        this.mView = null;
        this.mTags = null;
        this.mParent = null;
        this.mFirstChild = null;
        this.mLastChild = null;
        this.mNextSibling = null;
        this.mPrevSibling = null;
        this.mRepository = semSmartClipDataRepository;
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository semSmartClipDataRepository, View view) {
        this(semSmartClipDataRepository);
        this.mView = view;
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository semSmartClipDataRepository, Rect rect) {
        this(semSmartClipDataRepository);
        this.mRectOnScreen = new Rect(rect);
    }

    public SmartClipDataElementImpl(SemSmartClipDataRepository semSmartClipDataRepository, View view, Rect rect) {
        this(semSmartClipDataRepository, view);
        this.mRectOnScreen = new Rect(rect);
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

    public void setDataRepository(SemSmartClipDataRepository semSmartClipDataRepository) {
        this.mRepository = semSmartClipDataRepository;
    }

    public SemSmartClipDataRepository getDataRepository() {
        return this.mRepository;
    }

    public int getExtractionLevel() {
        SmartClipDataCropperImpl smartClipDataCropperImpl;
        SemSmartClipDataRepository semSmartClipDataRepository = this.mRepository;
        if (semSmartClipDataRepository == null || (smartClipDataCropperImpl = (SmartClipDataCropperImpl) semSmartClipDataRepository.getSmartClipDataCropper()) == null) {
            return 0;
        }
        return smartClipDataCropperImpl.getExtractionLevel();
    }

    public boolean isEmptyTag(boolean z) {
        if (!z) {
            SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
            return smartClipMetaTagArrayImpl == null || smartClipMetaTagArrayImpl.size() <= 0;
        }
        SmartClipDataElementImpl smartClipDataElementImplTraverseNextElement = this;
        while (smartClipDataElementImplTraverseNextElement != null) {
            SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl2 = smartClipDataElementImplTraverseNextElement.mTags;
            if (smartClipMetaTagArrayImpl2 != null && smartClipMetaTagArrayImpl2.size() > 0) {
                return false;
            }
            smartClipDataElementImplTraverseNextElement = smartClipDataElementImplTraverseNextElement.traverseNextElement(this);
        }
        return true;
    }

    public SemSmartClipDataElement createChildInstance() {
        SemSmartClipDataElement semSmartClipDataElementNewInstance = newInstance();
        addChild(semSmartClipDataElementNewInstance);
        return semSmartClipDataElementNewInstance;
    }

    public SemSmartClipDataElement newInstance() {
        return new SmartClipDataElementImpl(this.mRepository);
    }

    public boolean addChild(SemSmartClipDataElement semSmartClipDataElement) {
        if (semSmartClipDataElement == null) {
            return false;
        }
        SmartClipDataElementImpl smartClipDataElementImpl = (SmartClipDataElementImpl) semSmartClipDataElement;
        if (this.mFirstChild == null) {
            this.mFirstChild = smartClipDataElementImpl;
            this.mLastChild = smartClipDataElementImpl;
            smartClipDataElementImpl.setNextSibling(null);
            smartClipDataElementImpl.setPrevSibling(null);
            smartClipDataElementImpl.setParent(this);
            return true;
        }
        SmartClipDataElementImpl smartClipDataElementImpl2 = this.mLastChild;
        if (smartClipDataElementImpl2 == null) {
            return false;
        }
        this.mLastChild = smartClipDataElementImpl;
        smartClipDataElementImpl2.setNextSibling(smartClipDataElementImpl);
        smartClipDataElementImpl.setPrevSibling(smartClipDataElementImpl2);
        smartClipDataElementImpl.setParent(this);
        return true;
    }

    public boolean removeChild(SemSmartClipDataElement semSmartClipDataElement) {
        if (semSmartClipDataElement == null) {
            return false;
        }
        SmartClipDataElementImpl smartClipDataElementImpl = (SmartClipDataElementImpl) semSmartClipDataElement;
        if (smartClipDataElementImpl.getParent() != this) {
            Log.e(TAG, "removeChild : Incorrect parent of SemSmartClipDataElement. element=" + smartClipDataElementImpl);
            smartClipDataElementImpl.dump();
            return false;
        }
        if (this.mFirstChild == smartClipDataElementImpl) {
            this.mFirstChild = smartClipDataElementImpl.getNextSibling();
        }
        if (this.mLastChild == smartClipDataElementImpl) {
            this.mLastChild = smartClipDataElementImpl.getPrevSibling();
        }
        if (smartClipDataElementImpl.getPrevSibling() != null) {
            smartClipDataElementImpl.getPrevSibling().setNextSibling(smartClipDataElementImpl.getNextSibling());
        }
        if (smartClipDataElementImpl.getNextSibling() == null) {
            return true;
        }
        smartClipDataElementImpl.getNextSibling().setPrevSibling(smartClipDataElementImpl.getPrevSibling());
        return true;
    }

    private void setPrevSibling(SmartClipDataElementImpl smartClipDataElementImpl) {
        this.mPrevSibling = smartClipDataElementImpl;
    }

    private void setNextSibling(SmartClipDataElementImpl smartClipDataElementImpl) {
        this.mNextSibling = smartClipDataElementImpl;
    }

    private void setParent(SmartClipDataElementImpl smartClipDataElementImpl) {
        this.mParent = smartClipDataElementImpl;
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
        int i = 0;
        for (SmartClipDataElementImpl nextSibling = this.mFirstChild; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            i++;
        }
        return i;
    }

    public int getParentCount() {
        int i = 0;
        for (SmartClipDataElementImpl parent = getParent(); parent != null; parent = parent.getParent()) {
            i++;
        }
        return i;
    }

    public SmartClipDataElementImpl traverseNextElement(SmartClipDataElementImpl smartClipDataElementImpl) {
        SmartClipDataElementImpl smartClipDataElementImpl2 = this.mFirstChild;
        if (smartClipDataElementImpl2 != null) {
            return smartClipDataElementImpl2;
        }
        if (this == smartClipDataElementImpl) {
            return null;
        }
        SmartClipDataElementImpl smartClipDataElementImpl3 = this.mNextSibling;
        if (smartClipDataElementImpl3 != null) {
            return smartClipDataElementImpl3;
        }
        while (this != null && this.mNextSibling == null && (smartClipDataElementImpl == null || this.mParent != smartClipDataElementImpl)) {
            this = this.mParent;
        }
        if (this != null) {
            return this.mNextSibling;
        }
        return null;
    }

    public String getDumpString(boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder();
        int parentCount = getParentCount();
        if (z) {
            for (int i = 0; i < parentCount; i++) {
                sb.append("\t");
            }
        }
        if (this.mRectOnScreen != null) {
            sb.append("Rect(" + this.mRectOnScreen.left + ", " + this.mRectOnScreen.top + ", " + this.mRectOnScreen.right + ", " + this.mRectOnScreen.bottom + ")\t");
        } else {
            sb.append("mRectOnScreen(NULL)\t");
        }
        View view = this.mView;
        if (view != null) {
            sb.append(view.getClass().getSimpleName());
            int id = this.mView.getId();
            if (id != -1 && id >= 0) {
                try {
                    String resourceEntryName = this.mView.getResources().getResourceEntryName(id);
                    sb.append("/");
                    sb.append(resourceEntryName);
                    sb.append("\t");
                } catch (Exception unused) {
                    sb.append("@");
                    sb.append(Integer.toHexString(this.mView.hashCode()));
                    sb.append("\t");
                }
            } else {
                sb.append("@");
                sb.append(Integer.toHexString(this.mView.hashCode()));
                sb.append("\t");
            }
            Drawable background = this.mView.getBackground();
            if (background != null && background.isVisible() && background.getOpacity() != -2) {
                sb.append("Opacity BG(" + background.getOpacity() + ")\t");
            }
        }
        SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
        if (smartClipMetaTagArrayImpl != null) {
            int size = smartClipMetaTagArrayImpl.size();
            for (int i2 = 0; i2 < size; i2++) {
                SemSmartClipMetaTag semSmartClipMetaTag = (SemSmartClipMetaTag) this.mTags.get(i2);
                String type = semSmartClipMetaTag.getType();
                String value = semSmartClipMetaTag.getValue();
                if (value == null) {
                    value = PerfettoProtoLogImpl.NULL_STRING;
                }
                if (z2) {
                    sb.append(type);
                    sb.append(NavigationBarInflaterView.KEY_CODE_START);
                    sb.append(value);
                    sb.append(")\t");
                } else {
                    sb.append(type);
                    sb.append("\t");
                }
            }
        } else {
            sb.append("No meta tag\t");
        }
        return sb.toString();
    }

    public boolean dump() {
        Log.e(TAG, getDumpString(true, true));
        for (SmartClipDataElementImpl firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            firstChild.dump();
        }
        return true;
    }
}
