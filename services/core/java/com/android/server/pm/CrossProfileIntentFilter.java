package com.android.server.pm;

import android.content.IntentFilter;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.utils.SnapshotCache;

/* loaded from: classes3.dex */
public class CrossProfileIntentFilter extends WatchedIntentFilter {
  public final int mAccessControlLevel;
  public final int mFlags;
  public final String mOwnerPackage;
  public final SnapshotCache mSnapshot;
  public final int mTargetUserId;

  public final SnapshotCache makeCache() {
    return new SnapshotCache(
        this, this) { // from class: com.android.server.pm.CrossProfileIntentFilter.1
      @Override // com.android.server.utils.SnapshotCache
      public CrossProfileIntentFilter createSnapshot() {
        CrossProfileIntentFilter crossProfileIntentFilter = new CrossProfileIntentFilter();
        crossProfileIntentFilter.seal();
        return crossProfileIntentFilter;
      }
    };
  }

  public CrossProfileIntentFilter(IntentFilter intentFilter, String str, int i, int i2, int i3) {
    super(intentFilter);
    this.mTargetUserId = i;
    this.mOwnerPackage = str;
    this.mFlags = i2;
    this.mAccessControlLevel = i3;
    this.mSnapshot = makeCache();
  }

  public CrossProfileIntentFilter(
      WatchedIntentFilter watchedIntentFilter, String str, int i, int i2, int i3) {
    this(watchedIntentFilter.mFilter, str, i, i2, i3);
  }

  public CrossProfileIntentFilter(CrossProfileIntentFilter crossProfileIntentFilter) {
    super(crossProfileIntentFilter);
    this.mTargetUserId = crossProfileIntentFilter.mTargetUserId;
    this.mOwnerPackage = crossProfileIntentFilter.mOwnerPackage;
    this.mFlags = crossProfileIntentFilter.mFlags;
    this.mAccessControlLevel = crossProfileIntentFilter.mAccessControlLevel;
    this.mSnapshot = new SnapshotCache.Sealed();
  }

  public int getTargetUserId() {
    return this.mTargetUserId;
  }

  public int getFlags() {
    return this.mFlags;
  }

  public String getOwnerPackage() {
    return this.mOwnerPackage;
  }

  public CrossProfileIntentFilter(TypedXmlPullParser typedXmlPullParser) {
    this.mTargetUserId = typedXmlPullParser.getAttributeInt((String) null, "targetUserId", -10000);
    this.mOwnerPackage = getStringFromXml(typedXmlPullParser, "ownerPackage", "");
    this.mAccessControlLevel =
        typedXmlPullParser.getAttributeInt((String) null, "accessControl", 0);
    this.mFlags = typedXmlPullParser.getAttributeInt((String) null, "flags", 0);
    this.mSnapshot = makeCache();
    int depth = typedXmlPullParser.getDepth();
    String name = typedXmlPullParser.getName();
    while (true) {
      int next = typedXmlPullParser.next();
      if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
        break;
      }
      name = typedXmlPullParser.getName();
      if (next != 3 && next != 4 && next == 2) {
        if (name.equals("filter")) {
          break;
        }
        PackageManagerService.reportSettingsProblem(
            5,
            "Unknown element under crossProfile-intent-filters: "
                + name
                + " at "
                + typedXmlPullParser.getPositionDescription());
        XmlUtils.skipCurrentTag(typedXmlPullParser);
      }
    }
    if (name.equals("filter")) {
      this.mFilter.readFromXml(typedXmlPullParser);
      return;
    }
    PackageManagerService.reportSettingsProblem(
        5,
        "Missing element under CrossProfileIntentFilter: filter at "
            + typedXmlPullParser.getPositionDescription());
    XmlUtils.skipCurrentTag(typedXmlPullParser);
  }

  public final String getStringFromXml(
      TypedXmlPullParser typedXmlPullParser, String str, String str2) {
    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, str);
    if (attributeValue != null) {
      return attributeValue;
    }
    PackageManagerService.reportSettingsProblem(
        5,
        "Missing element under CrossProfileIntentFilter: "
            + str
            + " at "
            + typedXmlPullParser.getPositionDescription());
    return str2;
  }

  public void writeToXml(TypedXmlSerializer typedXmlSerializer) {
    typedXmlSerializer.attributeInt((String) null, "targetUserId", this.mTargetUserId);
    typedXmlSerializer.attributeInt((String) null, "flags", this.mFlags);
    typedXmlSerializer.attribute((String) null, "ownerPackage", this.mOwnerPackage);
    typedXmlSerializer.attributeInt((String) null, "accessControl", this.mAccessControlLevel);
    typedXmlSerializer.startTag((String) null, "filter");
    this.mFilter.writeToXml(typedXmlSerializer);
    typedXmlSerializer.endTag((String) null, "filter");
  }

  public String toString() {
    return "CrossProfileIntentFilter{0x"
        + Integer.toHexString(System.identityHashCode(this))
        + " "
        + Integer.toString(this.mTargetUserId)
        + "}";
  }

  public boolean equalsIgnoreFilter(CrossProfileIntentFilter crossProfileIntentFilter) {
    return this.mTargetUserId == crossProfileIntentFilter.mTargetUserId
        && this.mOwnerPackage.equals(crossProfileIntentFilter.mOwnerPackage)
        && this.mFlags == crossProfileIntentFilter.mFlags
        && this.mAccessControlLevel == crossProfileIntentFilter.mAccessControlLevel;
  }

  @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
  public CrossProfileIntentFilter snapshot() {
    return (CrossProfileIntentFilter) this.mSnapshot.snapshot();
  }
}
