package com.android.server.slice;

import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class SliceProviderPermissions implements DirtyTracker, DirtyTracker.Persistable {
  public static final String NAMESPACE = null;
  public final ArrayMap mAuths = new ArrayMap();
  public final SlicePermissionManager.PkgUser mPkg;
  public final DirtyTracker mTracker;

  public SliceProviderPermissions(
      SlicePermissionManager.PkgUser pkgUser, DirtyTracker dirtyTracker) {
    this.mPkg = pkgUser;
    this.mTracker = dirtyTracker;
  }

  public SlicePermissionManager.PkgUser getPkg() {
    return this.mPkg;
  }

  public synchronized Collection getAuthorities() {
    return new ArrayList(this.mAuths.values());
  }

  public synchronized SliceAuthority getOrCreateAuthority(String str) {
    SliceAuthority sliceAuthority;
    sliceAuthority = (SliceAuthority) this.mAuths.get(str);
    if (sliceAuthority == null) {
      sliceAuthority = new SliceAuthority(str, this);
      this.mAuths.put(str, sliceAuthority);
      onPersistableDirty(sliceAuthority);
    }
    return sliceAuthority;
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
    xmlSerializer.startTag(str, "provider");
    xmlSerializer.attribute(str, "pkg", this.mPkg.toString());
    int size = this.mAuths.size();
    for (int i = 0; i < size; i++) {
      String str2 = NAMESPACE;
      xmlSerializer.startTag(str2, "authority");
      xmlSerializer.attribute(
          str2, "authority", ((SliceAuthority) this.mAuths.valueAt(i)).mAuthority);
      ((SliceAuthority) this.mAuths.valueAt(i)).writeTo(xmlSerializer);
      xmlSerializer.endTag(str2, "authority");
    }
    xmlSerializer.endTag(NAMESPACE, "provider");
  }

  public static SliceProviderPermissions createFrom(
      XmlPullParser xmlPullParser, DirtyTracker dirtyTracker) {
    while (true) {
      if (xmlPullParser.getEventType() == 2 && "provider".equals(xmlPullParser.getName())) {
        break;
      }
      xmlPullParser.next();
    }
    int depth = xmlPullParser.getDepth();
    SliceProviderPermissions sliceProviderPermissions =
        new SliceProviderPermissions(
            new SlicePermissionManager.PkgUser(xmlPullParser.getAttributeValue(NAMESPACE, "pkg")),
            dirtyTracker);
    xmlPullParser.next();
    while (xmlPullParser.getDepth() > depth) {
      if (xmlPullParser.getEventType() == 2 && "authority".equals(xmlPullParser.getName())) {
        try {
          SliceAuthority sliceAuthority =
              new SliceAuthority(
                  xmlPullParser.getAttributeValue(NAMESPACE, "authority"),
                  sliceProviderPermissions);
          sliceAuthority.readFrom(xmlPullParser);
          sliceProviderPermissions.mAuths.put(sliceAuthority.getAuthority(), sliceAuthority);
        } catch (IllegalArgumentException e) {
          Slog.e("SliceProviderPermissions", "Couldn't read PkgUser", e);
        }
      }
      xmlPullParser.next();
    }
    return sliceProviderPermissions;
  }

  public static String getFileName(SlicePermissionManager.PkgUser pkgUser) {
    return String.format("provider_%s", pkgUser.toString());
  }

  public class SliceAuthority implements DirtyTracker.Persistable {
    public final String mAuthority;
    public final ArraySet mPkgs = new ArraySet();
    public final DirtyTracker mTracker;

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public String getFileName() {
      return null;
    }

    public SliceAuthority(String str, DirtyTracker dirtyTracker) {
      this.mAuthority = str;
      this.mTracker = dirtyTracker;
    }

    public String getAuthority() {
      return this.mAuthority;
    }

    public synchronized void addPkg(SlicePermissionManager.PkgUser pkgUser) {
      if (this.mPkgs.add(pkgUser)) {
        this.mTracker.onPersistableDirty(this);
      }
    }

    public synchronized Collection getPkgs() {
      return new ArraySet(this.mPkgs);
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public synchronized void writeTo(XmlSerializer xmlSerializer) {
      int size = this.mPkgs.size();
      for (int i = 0; i < size; i++) {
        xmlSerializer.startTag(SliceProviderPermissions.NAMESPACE, "pkg");
        xmlSerializer.text(((SlicePermissionManager.PkgUser) this.mPkgs.valueAt(i)).toString());
        xmlSerializer.endTag(SliceProviderPermissions.NAMESPACE, "pkg");
      }
    }

    public synchronized void readFrom(XmlPullParser xmlPullParser) {
      xmlPullParser.next();
      int depth = xmlPullParser.getDepth();
      while (xmlPullParser.getDepth() >= depth) {
        if (xmlPullParser.getEventType() == 2 && "pkg".equals(xmlPullParser.getName())) {
          this.mPkgs.add(new SlicePermissionManager.PkgUser(xmlPullParser.nextText()));
        }
        xmlPullParser.next();
      }
    }

    public boolean equals(Object obj) {
      if (!getClass().equals(obj != null ? obj.getClass() : null)) {
        return false;
      }
      SliceAuthority sliceAuthority = (SliceAuthority) obj;
      return Objects.equals(this.mAuthority, sliceAuthority.mAuthority)
          && Objects.equals(this.mPkgs, sliceAuthority.mPkgs);
    }

    public String toString() {
      return String.format("(%s: %s)", this.mAuthority, this.mPkgs.toString());
    }
  }
}
