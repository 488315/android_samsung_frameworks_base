package com.samsung.android.allshare.media;

import com.samsung.android.allshare.Item;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SearchCriteria {
  private String mKeyword;
  private ArrayList<Item.MediaType> mMediaTypes;

  private SearchCriteria(Builder builder) {
    this.mKeyword = builder.mKeyword;
    this.mMediaTypes = builder.mMediaTypes;
  }

  public String getKeyword() {
    return this.mKeyword;
  }

  public boolean equals(Object o) {
    String str;
    ArrayList<Item.MediaType> arrayList;
    if (!(o instanceof SearchCriteria)) {
      return false;
    }
    SearchCriteria sc = (SearchCriteria) o;
    boolean ret = false;
    String str2 = sc.mKeyword;
    if ((str2 == null && this.mKeyword != null) || (str2 != null && this.mKeyword == null)) {
      return false;
    }
    if (str2 == null && this.mKeyword == null) {
      ret = true;
    } else if (str2 != null && (str = this.mKeyword) != null && str2.compareTo(str) == 0) {
      ret = true;
    }
    if (!ret) {
      return false;
    }
    ArrayList<Item.MediaType> arrayList2 = sc.mMediaTypes;
    if (arrayList2 == null || (arrayList = this.mMediaTypes) == null) {
      return arrayList2 == null && this.mMediaTypes == null;
    }
    Iterator<Item.MediaType> itr1 = arrayList.iterator();
    while (itr1.hasNext()) {
      Item.MediaType type1 = itr1.next();
      boolean isSame = false;
      Iterator<Item.MediaType> itr2 = sc.mMediaTypes.iterator();
      while (itr2.hasNext()) {
        Item.MediaType type2 = itr2.next();
        if (type1 == type2) {
          isSame = true;
        }
      }
      if (!isSame) {
        return false;
      }
    }
    return true;
  }

  public int hashCode() {
    String str = this.mKeyword;
    int code = str != null ? 0 ^ str.hashCode() : 0;
    ArrayList<Item.MediaType> arrayList = this.mMediaTypes;
    if (arrayList != null) {
      Iterator<Item.MediaType> it = arrayList.iterator();
      while (it.hasNext()) {
        Item.MediaType mediaType = it.next();
        code ^= mediaType.hashCode();
      }
    }
    return code;
  }

  public boolean isMatchedItemType(Item.MediaType type) {
    ArrayList<Item.MediaType> arrayList = this.mMediaTypes;
    if (arrayList == null) {
      return false;
    }
    Iterator<Item.MediaType> it = arrayList.iterator();
    while (it.hasNext()) {
      Item.MediaType temp = it.next();
      if (temp.equals(type)) {
        return true;
      }
    }
    return false;
  }

  public static class Builder {
    private String mKeyword;
    private ArrayList<Item.MediaType> mMediaTypes;

    public Builder() {
      if (this.mKeyword == null) {
        this.mKeyword = "";
      }
      this.mMediaTypes = new ArrayList<>();
    }

    public Builder setKeyword(String value) {
      this.mKeyword = value;
      return this;
    }

    public Builder addItemType(Item.MediaType type) {
      if (type != null) {
        this.mMediaTypes.add(type);
      }
      return this;
    }

    public SearchCriteria build() {
      return new SearchCriteria(this);
    }
  }
}
