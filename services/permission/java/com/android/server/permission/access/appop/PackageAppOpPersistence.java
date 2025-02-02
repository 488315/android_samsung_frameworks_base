package com.android.server.permission.access.appop;

import android.util.ArrayMap;
import android.util.Log;
import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.UserState;
import com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: PackageAppOpPersistence.kt */
/* loaded from: classes2.dex */
public final class PackageAppOpPersistence extends BaseAppOpPersistence {
  public static final Companion Companion = new Companion(null);
  public static final String LOG_TAG = PackageAppOpPersistence.class.getSimpleName();

  /* compiled from: PackageAppOpPersistence.kt */
  public final class Companion {
    public Companion() {}

    public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
      this();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:47:0x00ac, code lost:

     r0 = r11.next();
  */
  /* JADX WARN: Code restructure failed: missing block: B:48:0x00b0, code lost:

     if (r0 == 1) goto L70;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x00b2, code lost:

     if (r0 == 2) goto L71;
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x00b4, code lost:

     if (r0 == 3) goto L72;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void parsePackageAppOps(
      BinaryXmlPullParser binaryXmlPullParser, AccessState accessState, int i) {
    int next;
    int next2;
    int next3;
    UserState userState = (UserState) accessState.getUserStates().get(i);
    int eventType = binaryXmlPullParser.getEventType();
    if (eventType != 0 && eventType != 2) {
      throw new XmlPullParserException("Unexpected event type " + eventType);
    }
    do {
      next = binaryXmlPullParser.next();
      if (next == 1 || next == 2) {
        break;
      }
    } while (next != 3);
    while (true) {
      int eventType2 = binaryXmlPullParser.getEventType();
      if (eventType2 == 1) {
        break;
      }
      if (eventType2 == 2) {
        int depth = binaryXmlPullParser.getDepth();
        if (Intrinsics.areEqual(binaryXmlPullParser.getName(), "package")) {
          parsePackage(binaryXmlPullParser, userState);
        } else {
          Log.w(
              LOG_TAG,
              "Ignoring unknown tag "
                  + binaryXmlPullParser.getName()
                  + " when parsing app-op state");
        }
        int depth2 = binaryXmlPullParser.getDepth();
        if (depth2 != depth) {
          throw new XmlPullParserException(
              "Unexpected post-block depth " + depth2 + ", expected " + depth);
        }
        while (true) {
          int eventType3 = binaryXmlPullParser.getEventType();
          if (eventType3 == 2) {
            do {
              next3 = binaryXmlPullParser.next();
              if (next3 != 1 && next3 != 2) {}
            } while (next3 != 3);
          } else {
            if (eventType3 != 3) {
              throw new XmlPullParserException("Unexpected event type " + eventType3);
            }
            if (binaryXmlPullParser.getDepth() <= depth) {
              break;
            }
            do {
              next2 = binaryXmlPullParser.next();
              if (next2 != 1 && next2 != 2) {}
            } while (next2 != 3);
          }
        }
      } else if (eventType2 != 3) {
        throw new XmlPullParserException("Unexpected event type " + eventType2);
      }
    }
    ArrayMap packageAppOpModes = userState.getPackageAppOpModes();
    for (int size = packageAppOpModes.size() - 1; -1 < size; size--) {
      Object keyAt = packageAppOpModes.keyAt(size);
      String str = (String) keyAt;
      boolean containsKey = accessState.getSystemState().getPackageStates().containsKey(str);
      if (!containsKey) {
        Log.w(LOG_TAG, "Dropping unknown package " + str + " when parsing app-op state");
      }
      if (!containsKey) {
        packageAppOpModes.removeAt(size);
      }
    }
  }

  public final void serializePackage(
      BinaryXmlSerializer binaryXmlSerializer, String str, ArrayMap arrayMap) {
    binaryXmlSerializer.startTag((String) null, "package");
    binaryXmlSerializer.attributeInterned((String) null, "name", str);
    serializeAppOps(binaryXmlSerializer, arrayMap);
    binaryXmlSerializer.endTag((String) null, "package");
  }

  public final void serializePackageAppOps(
      BinaryXmlSerializer binaryXmlSerializer, UserState userState) {
    binaryXmlSerializer.startTag((String) null, "package-app-ops");
    ArrayMap packageAppOpModes = userState.getPackageAppOpModes();
    int size = packageAppOpModes.size();
    for (int i = 0; i < size; i++) {
      serializePackage(
          binaryXmlSerializer,
          (String) packageAppOpModes.keyAt(i),
          (ArrayMap) packageAppOpModes.valueAt(i));
    }
    binaryXmlSerializer.endTag((String) null, "package-app-ops");
  }

  @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
  public void serializeUserState(
      BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
    serializePackageAppOps(binaryXmlSerializer, (UserState) accessState.getUserStates().get(i));
  }

  @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
  public void parseUserState(
      BinaryXmlPullParser binaryXmlPullParser, AccessState accessState, int i) {
    if (Intrinsics.areEqual(binaryXmlPullParser.getName(), "package-app-ops")) {
      parsePackageAppOps(binaryXmlPullParser, accessState, i);
    }
  }

  public final void parsePackage(BinaryXmlPullParser binaryXmlPullParser, UserState userState) {
    String intern =
        binaryXmlPullParser
            .getAttributeValue(binaryXmlPullParser.getAttributeIndexOrThrow((String) null, "name"))
            .intern();
    Intrinsics.checkNotNullExpressionValue(intern, "this as java.lang.String).intern()");
    ArrayMap arrayMap = new ArrayMap();
    userState.getPackageAppOpModes().put(intern, arrayMap);
    parseAppOps(binaryXmlPullParser, arrayMap);
  }
}
