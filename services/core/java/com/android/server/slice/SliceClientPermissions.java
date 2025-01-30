package com.android.server.slice;

import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class SliceClientPermissions implements DirtyTracker, DirtyTracker.Persistable {
  public static final String NAMESPACE = null;
  public final ArrayMap mAuths = new ArrayMap();
  public boolean mHasFullAccess;
  public final SlicePermissionManager.PkgUser mPkg;
  public final DirtyTracker mTracker;

  public SliceClientPermissions(SlicePermissionManager.PkgUser pkgUser, DirtyTracker dirtyTracker) {
    this.mPkg = pkgUser;
    this.mTracker = dirtyTracker;
  }

  public SlicePermissionManager.PkgUser getPkg() {
    return this.mPkg;
  }

  public synchronized SliceAuthority getOrCreateAuthority(
      SlicePermissionManager.PkgUser pkgUser, SlicePermissionManager.PkgUser pkgUser2) {
    SliceAuthority sliceAuthority;
    sliceAuthority = (SliceAuthority) this.mAuths.get(pkgUser);
    if (sliceAuthority == null) {
      sliceAuthority = new SliceAuthority(pkgUser.getPkg(), pkgUser2, this);
      this.mAuths.put(pkgUser, sliceAuthority);
      onPersistableDirty(sliceAuthority);
    }
    return sliceAuthority;
  }

  public synchronized SliceAuthority getAuthority(SlicePermissionManager.PkgUser pkgUser) {
    return (SliceAuthority) this.mAuths.get(pkgUser);
  }

  public boolean hasFullAccess() {
    return this.mHasFullAccess;
  }

  public void setHasFullAccess(boolean z) {
    if (this.mHasFullAccess == z) {
      return;
    }
    this.mHasFullAccess = z;
    this.mTracker.onPersistableDirty(this);
  }

  public void removeAuthority(String str, int i) {
    if (this.mAuths.remove(new SlicePermissionManager.PkgUser(str, i)) != null) {
      this.mTracker.onPersistableDirty(this);
    }
  }

  public synchronized boolean hasPermission(Uri uri, int i) {
    boolean z = false;
    if (!"content".equals(uri.getScheme())) {
      return false;
    }
    SliceAuthority authority =
        getAuthority(new SlicePermissionManager.PkgUser(uri.getAuthority(), i));
    if (authority != null) {
      if (authority.hasPermission(uri.getPathSegments())) {
        z = true;
      }
    }
    return z;
  }

  public void grantUri(Uri uri, SlicePermissionManager.PkgUser pkgUser) {
    getOrCreateAuthority(
            new SlicePermissionManager.PkgUser(uri.getAuthority(), pkgUser.getUserId()), pkgUser)
        .addPath(uri.getPathSegments());
  }

  public void revokeUri(Uri uri, SlicePermissionManager.PkgUser pkgUser) {
    getOrCreateAuthority(
            new SlicePermissionManager.PkgUser(uri.getAuthority(), pkgUser.getUserId()), pkgUser)
        .removePath(uri.getPathSegments());
  }

  public void clear() {
    if (this.mHasFullAccess || !this.mAuths.isEmpty()) {
      this.mHasFullAccess = false;
      this.mAuths.clear();
      onPersistableDirty(this);
    }
  }

  @Override // com.android.server.slice.DirtyTracker
  public void onPersistableDirty(DirtyTracker.Persistable persistable) {
    this.mTracker.onPersistableDirty(this);
  }

  @Override // com.android.server.slice.DirtyTracker.Persistable
  public String getFileName() {
    return getFileName(this.mPkg);
  }

  @Override // com.android.server.slice.DirtyTracker.Persistable
  public synchronized void writeTo(XmlSerializer xmlSerializer) {
    String str = NAMESPACE;
    xmlSerializer.startTag(str, "client");
    xmlSerializer.attribute(str, "pkg", this.mPkg.toString());
    xmlSerializer.attribute(str, "fullAccess", this.mHasFullAccess ? "1" : "0");
    int size = this.mAuths.size();
    for (int i = 0; i < size; i++) {
      String str2 = NAMESPACE;
      xmlSerializer.startTag(str2, "authority");
      xmlSerializer.attribute(
          str2, "authority", ((SliceAuthority) this.mAuths.valueAt(i)).mAuthority);
      xmlSerializer.attribute(
          str2, "pkg", ((SliceAuthority) this.mAuths.valueAt(i)).mPkg.toString());
      ((SliceAuthority) this.mAuths.valueAt(i)).writeTo(xmlSerializer);
      xmlSerializer.endTag(str2, "authority");
    }
    xmlSerializer.endTag(NAMESPACE, "client");
  }

  public static SliceClientPermissions createFrom(
      XmlPullParser xmlPullParser, DirtyTracker dirtyTracker) {
    while (true) {
      if (xmlPullParser.getEventType() != 2 || !"client".equals(xmlPullParser.getName())) {
        if (xmlPullParser.getEventType() == 1) {
          throw new XmlPullParserException("Can't find client tag in xml");
        }
        xmlPullParser.next();
      } else {
        int depth = xmlPullParser.getDepth();
        String str = NAMESPACE;
        SliceClientPermissions sliceClientPermissions =
            new SliceClientPermissions(
                new SlicePermissionManager.PkgUser(xmlPullParser.getAttributeValue(str, "pkg")),
                dirtyTracker);
        String attributeValue = xmlPullParser.getAttributeValue(str, "fullAccess");
        if (attributeValue == null) {
          attributeValue = "0";
        }
        sliceClientPermissions.mHasFullAccess = Integer.parseInt(attributeValue) != 0;
        xmlPullParser.next();
        while (xmlPullParser.getDepth() > depth && xmlPullParser.getEventType() != 1) {
          if (xmlPullParser.getEventType() == 2 && "authority".equals(xmlPullParser.getName())) {
            try {
              String str2 = NAMESPACE;
              SlicePermissionManager.PkgUser pkgUser =
                  new SlicePermissionManager.PkgUser(xmlPullParser.getAttributeValue(str2, "pkg"));
              SliceAuthority sliceAuthority =
                  new SliceAuthority(
                      xmlPullParser.getAttributeValue(str2, "authority"),
                      pkgUser,
                      sliceClientPermissions);
              sliceAuthority.readFrom(xmlPullParser);
              sliceClientPermissions.mAuths.put(
                  new SlicePermissionManager.PkgUser(
                      sliceAuthority.getAuthority(), pkgUser.getUserId()),
                  sliceAuthority);
            } catch (IllegalArgumentException e) {
              Slog.e("SliceClientPermissions", "Couldn't read PkgUser", e);
            }
          }
          xmlPullParser.next();
        }
        return sliceClientPermissions;
      }
    }
  }

  public static String getFileName(SlicePermissionManager.PkgUser pkgUser) {
    return String.format("client_%s", pkgUser.toString());
  }

  public class SliceAuthority implements DirtyTracker.Persistable {
    public final String mAuthority;
    public final ArraySet mPaths = new ArraySet();
    public final SlicePermissionManager.PkgUser mPkg;
    public final DirtyTracker mTracker;

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public String getFileName() {
      return null;
    }

    public SliceAuthority(
        String str, SlicePermissionManager.PkgUser pkgUser, DirtyTracker dirtyTracker) {
      this.mAuthority = str;
      this.mPkg = pkgUser;
      this.mTracker = dirtyTracker;
    }

    public String getAuthority() {
      return this.mAuthority;
    }

    public void addPath(List list) {
      String[] strArr = (String[]) list.toArray(new String[list.size()]);
      for (int size = this.mPaths.size() - 1; size >= 0; size--) {
        String[] strArr2 = (String[]) this.mPaths.valueAt(size);
        if (isPathPrefixMatch(strArr2, strArr)) {
          return;
        }
        if (isPathPrefixMatch(strArr, strArr2)) {
          this.mPaths.removeAt(size);
        }
      }
      this.mPaths.add(strArr);
      this.mTracker.onPersistableDirty(this);
    }

    public void removePath(List list) {
      String[] strArr = (String[]) list.toArray(new String[list.size()]);
      boolean z = false;
      for (int size = this.mPaths.size() - 1; size >= 0; size--) {
        if (isPathPrefixMatch(strArr, (String[]) this.mPaths.valueAt(size))) {
          this.mPaths.removeAt(size);
          z = true;
        }
      }
      if (z) {
        this.mTracker.onPersistableDirty(this);
      }
    }

    public boolean hasPermission(List list) {
      Iterator it = this.mPaths.iterator();
      while (it.hasNext()) {
        if (isPathPrefixMatch(
            (String[]) it.next(), (String[]) list.toArray(new String[list.size()]))) {
          return true;
        }
      }
      return false;
    }

    public final boolean isPathPrefixMatch(String[] strArr, String[] strArr2) {
      int length = strArr.length;
      if (strArr2.length < length) {
        return false;
      }
      for (int i = 0; i < length; i++) {
        if (!Objects.equals(strArr2[i], strArr[i])) {
          return false;
        }
      }
      return true;
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public synchronized void writeTo(XmlSerializer xmlSerializer) {
      int size = this.mPaths.size();
      for (int i = 0; i < size; i++) {
        String[] strArr = (String[]) this.mPaths.valueAt(i);
        if (strArr != null) {
          xmlSerializer.startTag(SliceClientPermissions.NAMESPACE, "path");
          xmlSerializer.text(encodeSegments(strArr));
          xmlSerializer.endTag(SliceClientPermissions.NAMESPACE, "path");
        }
      }
    }

    public synchronized void readFrom(XmlPullParser xmlPullParser) {
      xmlPullParser.next();
      int depth = xmlPullParser.getDepth();
      while (xmlPullParser.getDepth() >= depth) {
        if (xmlPullParser.getEventType() == 2 && "path".equals(xmlPullParser.getName())) {
          this.mPaths.add(decodeSegments(xmlPullParser.nextText()));
        }
        xmlPullParser.next();
      }
    }

    public final String encodeSegments(String[] strArr) {
      String[] strArr2 = new String[strArr.length];
      for (int i = 0; i < strArr.length; i++) {
        strArr2[i] = Uri.encode(strArr[i]);
      }
      return TextUtils.join("/", strArr2);
    }

    public final String[] decodeSegments(String str) {
      String[] split = str.split("/", -1);
      for (int i = 0; i < split.length; i++) {
        split[i] = Uri.decode(split[i]);
      }
      return split;
    }

    public boolean equals(Object obj) {
      if (!getClass().equals(obj != null ? obj.getClass() : null)) {
        return false;
      }
      SliceAuthority sliceAuthority = (SliceAuthority) obj;
      if (this.mPaths.size() != sliceAuthority.mPaths.size()) {
        return false;
      }
      ArrayList arrayList = new ArrayList(this.mPaths);
      ArrayList arrayList2 = new ArrayList(sliceAuthority.mPaths);
      arrayList.sort(
          Comparator.comparing(
              new Function() { // from class:
                               // com.android.server.slice.SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                  String lambda$equals$0;
                  lambda$equals$0 =
                      SliceClientPermissions.SliceAuthority.lambda$equals$0((String[]) obj2);
                  return lambda$equals$0;
                }
              }));
      arrayList2.sort(
          Comparator.comparing(
              new Function() { // from class:
                               // com.android.server.slice.SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                  String lambda$equals$1;
                  lambda$equals$1 =
                      SliceClientPermissions.SliceAuthority.lambda$equals$1((String[]) obj2);
                  return lambda$equals$1;
                }
              }));
      for (int i = 0; i < arrayList.size(); i++) {
        String[] strArr = (String[]) arrayList.get(i);
        String[] strArr2 = (String[]) arrayList2.get(i);
        if (strArr.length != strArr2.length) {
          return false;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
          if (!Objects.equals(strArr[i2], strArr2[i2])) {
            return false;
          }
        }
      }
      return Objects.equals(this.mAuthority, sliceAuthority.mAuthority)
          && Objects.equals(this.mPkg, sliceAuthority.mPkg);
    }

    public static /* synthetic */ String lambda$equals$0(String[] strArr) {
      return TextUtils.join(",", strArr);
    }

    public static /* synthetic */ String lambda$equals$1(String[] strArr) {
      return TextUtils.join(",", strArr);
    }

    public String toString() {
      return String.format(
          "(%s, %s: %s)", this.mAuthority, this.mPkg.toString(), pathToString(this.mPaths));
    }

    public static /* synthetic */ String lambda$pathToString$2(String[] strArr) {
      return TextUtils.join("/", strArr);
    }

    public final String pathToString(ArraySet arraySet) {
      return TextUtils.join(
          ", ",
          (Iterable)
              arraySet.stream()
                  .map(
                      new Function() { // from class:
                                       // com.android.server.slice.SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          String lambda$pathToString$2;
                          lambda$pathToString$2 =
                              SliceClientPermissions.SliceAuthority.lambda$pathToString$2(
                                  (String[]) obj);
                          return lambda$pathToString$2;
                        }
                      })
                  .collect(Collectors.toList()));
    }
  }
}
