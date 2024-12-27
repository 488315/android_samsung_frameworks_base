package com.android.server.slice;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SliceClientPermissions implements DirtyTracker, DirtyTracker.Persistable {
    public final ArrayMap mAuths = new ArrayMap();
    public boolean mHasFullAccess;
    public final SlicePermissionManager.PkgUser mPkg;
    public final DirtyTracker mTracker;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SliceAuthority implements DirtyTracker.Persistable {
        public final String mAuthority;
        public final ArraySet mPaths = new ArraySet();
        public final SlicePermissionManager.PkgUser mPkg;
        public final DirtyTracker mTracker;

        public SliceAuthority(
                String str, SlicePermissionManager.PkgUser pkgUser, DirtyTracker dirtyTracker) {
            this.mAuthority = str;
            this.mPkg = pkgUser;
            this.mTracker = dirtyTracker;
        }

        public static boolean isPathPrefixMatch(String[] strArr, String[] strArr2) {
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

        public final boolean equals(Object obj) {
            if (!SliceAuthority.class.equals(obj != null ? obj.getClass() : null)) {
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
                            new SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda0(
                                    0)));
            arrayList2.sort(
                    Comparator.comparing(
                            new SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda0(
                                    1)));
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

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public final String getFileName() {
            return null;
        }

        public final synchronized void readFrom(XmlPullParser xmlPullParser) {
            try {
                xmlPullParser.next();
                int depth = xmlPullParser.getDepth();
                while (xmlPullParser.getDepth() >= depth) {
                    if (xmlPullParser.getEventType() == 2
                            && "path".equals(xmlPullParser.getName())) {
                        ArraySet arraySet = this.mPaths;
                        String[] split = xmlPullParser.nextText().split("/", -1);
                        for (int i = 0; i < split.length; i++) {
                            split[i] = Uri.decode(split[i]);
                        }
                        arraySet.add(split);
                    }
                    xmlPullParser.next();
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final String toString() {
            String pkgUser = this.mPkg.toString();
            String join =
                    TextUtils.join(
                            ", ",
                            (Iterable)
                                    this.mPaths.stream()
                                            .map(
                                                    new SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda0(
                                                            2))
                                            .collect(Collectors.toList()));
            StringBuilder sb = new StringBuilder("(");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    sb, this.mAuthority, ", ", pkgUser, ": ");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, join, ")");
        }

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public final synchronized void writeTo(XmlSerializer xmlSerializer) {
            int size = this.mPaths.size();
            for (int i = 0; i < size; i++) {
                String[] strArr = (String[]) this.mPaths.valueAt(i);
                if (strArr != null) {
                    xmlSerializer.startTag(null, "path");
                    String[] strArr2 = new String[strArr.length];
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        strArr2[i2] = Uri.encode(strArr[i2]);
                    }
                    xmlSerializer.text(TextUtils.join("/", strArr2));
                    xmlSerializer.endTag(null, "path");
                }
            }
        }
    }

    public SliceClientPermissions(
            SlicePermissionManager.PkgUser pkgUser, DirtyTracker dirtyTracker) {
        this.mPkg = pkgUser;
        this.mTracker = dirtyTracker;
    }

    public static SliceClientPermissions createFrom(
            XmlPullParser xmlPullParser, DirtyTracker dirtyTracker) {
        while (true) {
            if (xmlPullParser.getEventType() == 2 && "client".equals(xmlPullParser.getName())) {
                int depth = xmlPullParser.getDepth();
                SliceClientPermissions sliceClientPermissions =
                        new SliceClientPermissions(
                                new SlicePermissionManager.PkgUser(
                                        xmlPullParser.getAttributeValue(null, "pkg")),
                                dirtyTracker);
                String attributeValue = xmlPullParser.getAttributeValue(null, "fullAccess");
                if (attributeValue == null) {
                    attributeValue = "0";
                }
                sliceClientPermissions.mHasFullAccess = Integer.parseInt(attributeValue) != 0;
                xmlPullParser.next();
                while (xmlPullParser.getDepth() > depth && xmlPullParser.getEventType() != 1) {
                    if (xmlPullParser.getEventType() == 2
                            && "authority".equals(xmlPullParser.getName())) {
                        try {
                            SlicePermissionManager.PkgUser pkgUser =
                                    new SlicePermissionManager.PkgUser(
                                            xmlPullParser.getAttributeValue(null, "pkg"));
                            SliceAuthority sliceAuthority =
                                    new SliceAuthority(
                                            xmlPullParser.getAttributeValue(null, "authority"),
                                            pkgUser,
                                            sliceClientPermissions);
                            sliceAuthority.readFrom(xmlPullParser);
                            sliceClientPermissions.mAuths.put(
                                    new SlicePermissionManager.PkgUser(
                                            sliceAuthority.mAuthority, pkgUser.mUserId),
                                    sliceAuthority);
                        } catch (IllegalArgumentException e) {
                            Slog.e("SliceClientPermissions", "Couldn't read PkgUser", e);
                        }
                    }
                    xmlPullParser.next();
                }
                return sliceClientPermissions;
            }
            if (xmlPullParser.getEventType() == 1) {
                throw new XmlPullParserException("Can't find client tag in xml");
            }
            xmlPullParser.next();
        }
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public final String getFileName() {
        return "client_".concat(this.mPkg.toString());
    }

    public final synchronized SliceAuthority getOrCreateAuthority(
            SlicePermissionManager.PkgUser pkgUser, SlicePermissionManager.PkgUser pkgUser2) {
        SliceAuthority sliceAuthority;
        sliceAuthority = (SliceAuthority) this.mAuths.get(pkgUser);
        if (sliceAuthority == null) {
            sliceAuthority = new SliceAuthority(pkgUser.mPkg, pkgUser2, this);
            this.mAuths.put(pkgUser, sliceAuthority);
            onPersistableDirty(sliceAuthority);
        }
        return sliceAuthority;
    }

    @Override // com.android.server.slice.DirtyTracker
    public final void onPersistableDirty(DirtyTracker.Persistable persistable) {
        this.mTracker.onPersistableDirty(this);
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public final synchronized void writeTo(XmlSerializer xmlSerializer) {
        try {
            xmlSerializer.startTag(null, "client");
            xmlSerializer.attribute(null, "pkg", this.mPkg.toString());
            xmlSerializer.attribute(null, "fullAccess", this.mHasFullAccess ? "1" : "0");
            int size = this.mAuths.size();
            for (int i = 0; i < size; i++) {
                xmlSerializer.startTag(null, "authority");
                xmlSerializer.attribute(
                        null, "authority", ((SliceAuthority) this.mAuths.valueAt(i)).mAuthority);
                xmlSerializer.attribute(
                        null, "pkg", ((SliceAuthority) this.mAuths.valueAt(i)).mPkg.toString());
                ((SliceAuthority) this.mAuths.valueAt(i)).writeTo(xmlSerializer);
                xmlSerializer.endTag(null, "authority");
            }
            xmlSerializer.endTag(null, "client");
        } catch (Throwable th) {
            throw th;
        }
    }
}
