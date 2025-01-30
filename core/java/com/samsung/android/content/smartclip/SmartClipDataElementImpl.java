package com.samsung.android.content.smartclip;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import com.samsung.android.ims.options.SemCapabilities;

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
    SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
    if (smartClipMetaTagArrayImpl == null) {
      return new SmartClipMetaTagArrayImpl();
    }
    SemSmartClipMetaTagArray typedArray = smartClipMetaTagArrayImpl.getMetaTags(tagType);
    return typedArray;
  }

  @Override // com.samsung.android.content.smartclip.SemSmartClipDataElement
  public int removeTags(String tagType) {
    SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
    if (smartClipMetaTagArrayImpl == null) {
      return 0;
    }
    return smartClipMetaTagArrayImpl.removeMetaTags(tagType);
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

  public SmartClipDataElementImpl(
      SemSmartClipDataRepository repository, View view, Rect screenRect) {
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
    SemSmartClipDataRepository semSmartClipDataRepository = this.mRepository;
    if (semSmartClipDataRepository == null
        || (cropper =
                (SmartClipDataCropperImpl) semSmartClipDataRepository.getSmartClipDataCropper())
            == null) {
      return 0;
    }
    return cropper.getExtractionMode();
  }

  public int getExtractionLevel() {
    SmartClipDataCropperImpl cropper;
    SemSmartClipDataRepository semSmartClipDataRepository = this.mRepository;
    if (semSmartClipDataRepository == null
        || (cropper =
                (SmartClipDataCropperImpl) semSmartClipDataRepository.getSmartClipDataCropper())
            == null) {
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
      SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
      return smartClipMetaTagArrayImpl == null || smartClipMetaTagArrayImpl.size() <= 0;
    }
    SmartClipDataElementImpl element = this;
    while (element != null) {
      SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl2 = element.mTags;
      if (smartClipMetaTagArrayImpl2 != null && smartClipMetaTagArrayImpl2.size() > 0) {
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
      Log.m96e(TAG, "removeChild : Incorrect parent of SemSmartClipDataElement. element=" + child);
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
    for (SmartClipDataElementImpl element = this.mFirstChild;
        element != null;
        element = element.getNextSibling()) {
      childCount++;
    }
    return childCount;
  }

  public int getParentCount() {
    int parentCount = 0;
    for (SmartClipDataElementImpl element = getParent();
        element != null;
        element = element.getParent()) {
      parentCount++;
    }
    return parentCount;
  }

  /* JADX WARN: Code restructure failed: missing block: B:21:0x0022, code lost:

     return r1.mNextSibling;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public SmartClipDataElementImpl traverseNextElement(
      SmartClipDataElementImpl topMostNodeOfTraverse) {
    SmartClipDataElementImpl smartClipDataElementImpl = this.mFirstChild;
    if (smartClipDataElementImpl != null) {
      return smartClipDataElementImpl;
    }
    if (this == topMostNodeOfTraverse) {
      return null;
    }
    SmartClipDataElementImpl smartClipDataElementImpl2 = this.mNextSibling;
    if (smartClipDataElementImpl2 != null) {
      return smartClipDataElementImpl2;
    }
    SmartClipDataElementImpl n = this;
    while (n != null
        && n.mNextSibling == null
        && (topMostNodeOfTraverse == null || n.mParent != topMostNodeOfTraverse)) {
      n = n.mParent;
    }
    return null;
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
      result.append(
          "Rect("
              + this.mRectOnScreen.left
              + ", "
              + this.mRectOnScreen.top
              + ", "
              + this.mRectOnScreen.right
              + ", "
              + this.mRectOnScreen.bottom
              + ")\t");
    } else {
      result.append("mRectOnScreen(NULL)\t");
    }
    View view = this.mView;
    if (view != null) {
      result.append(view.getClass().getSimpleName());
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
    SmartClipMetaTagArrayImpl smartClipMetaTagArrayImpl = this.mTags;
    if (smartClipMetaTagArrayImpl != null) {
      int tagCount = smartClipMetaTagArrayImpl.size();
      for (int i2 = 0; i2 < tagCount; i2++) {
        SemSmartClipMetaTag tag = (SemSmartClipMetaTag) this.mTags.get(i2);
        String type = tag.getType();
        String value = tag.getValue();
        String extra = "";
        if (value == null) {
          value = SemCapabilities.FEATURE_TAG_NULL;
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
          result
              .append(type)
              .append(NavigationBarInflaterView.KEY_CODE_START)
              .append(value)
              .append(extra)
              .append(")\t");
        }
      }
    } else {
      result.append("No meta tag\t");
    }
    return result.toString();
  }

  public boolean dump() {
    String dumpStr = getDumpString(true, true);
    Log.m96e(TAG, dumpStr);
    for (SmartClipDataElementImpl element = getFirstChild();
        element != null;
        element = element.getNextSibling()) {
      element.dump();
    }
    return true;
  }
}
