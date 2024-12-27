package com.android.server.slice;

import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SliceProviderPermissions implements DirtyTracker, DirtyTracker.Persistable {
    public final ArrayMap mAuths = new ArrayMap();
    public final SlicePermissionManager.PkgUser mPkg;
    public final DirtyTracker mTracker;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SliceAuthority implements DirtyTracker.Persistable {
        public final String mAuthority;
        public final ArraySet mPkgs = new ArraySet();
        public final DirtyTracker mTracker;

        public SliceAuthority(String str, SliceProviderPermissions sliceProviderPermissions) {
            this.mAuthority = str;
            this.mTracker = sliceProviderPermissions;
        }

        public final boolean equals(Object obj) {
            if (!SliceAuthority.class.equals(obj != null ? obj.getClass() : null)) {
                return false;
            }
            SliceAuthority sliceAuthority = (SliceAuthority) obj;
            return Objects.equals(this.mAuthority, sliceAuthority.mAuthority)
                    && Objects.equals(this.mPkgs, sliceAuthority.mPkgs);
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
                            && "pkg".equals(xmlPullParser.getName())) {
                        this.mPkgs.add(
                                new SlicePermissionManager.PkgUser(xmlPullParser.nextText()));
                    }
                    xmlPullParser.next();
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        public final String toString() {
            return OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                    new StringBuilder("("), this.mAuthority, ": ", this.mPkgs.toString(), ")");
        }

        @Override // com.android.server.slice.DirtyTracker.Persistable
        public final synchronized void writeTo(XmlSerializer xmlSerializer) {
            int size = this.mPkgs.size();
            for (int i = 0; i < size; i++) {
                xmlSerializer.startTag(null, "pkg");
                xmlSerializer.text(
                        ((SlicePermissionManager.PkgUser) this.mPkgs.valueAt(i)).toString());
                xmlSerializer.endTag(null, "pkg");
            }
        }
    }

    public SliceProviderPermissions(
            SlicePermissionManager.PkgUser pkgUser, DirtyTracker dirtyTracker) {
        this.mPkg = pkgUser;
        this.mTracker = dirtyTracker;
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
                        new SlicePermissionManager.PkgUser(
                                xmlPullParser.getAttributeValue(null, "pkg")),
                        dirtyTracker);
        xmlPullParser.next();
        while (xmlPullParser.getDepth() > depth) {
            if (xmlPullParser.getEventType() == 2 && "authority".equals(xmlPullParser.getName())) {
                try {
                    SliceAuthority sliceAuthority =
                            new SliceAuthority(
                                    xmlPullParser.getAttributeValue(null, "authority"),
                                    sliceProviderPermissions);
                    sliceAuthority.readFrom(xmlPullParser);
                    sliceProviderPermissions.mAuths.put(sliceAuthority.mAuthority, sliceAuthority);
                } catch (IllegalArgumentException e) {
                    Slog.e("SliceProviderPermissions", "Couldn't read PkgUser", e);
                }
            }
            xmlPullParser.next();
        }
        return sliceProviderPermissions;
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public final String getFileName() {
        return "provider_".concat(this.mPkg.toString());
    }

    @Override // com.android.server.slice.DirtyTracker
    public final void onPersistableDirty(DirtyTracker.Persistable persistable) {
        this.mTracker.onPersistableDirty(this);
    }

    @Override // com.android.server.slice.DirtyTracker.Persistable
    public final synchronized void writeTo(XmlSerializer xmlSerializer) {
        try {
            xmlSerializer.startTag(null, "provider");
            xmlSerializer.attribute(null, "pkg", this.mPkg.toString());
            int size = this.mAuths.size();
            for (int i = 0; i < size; i++) {
                xmlSerializer.startTag(null, "authority");
                xmlSerializer.attribute(
                        null, "authority", ((SliceAuthority) this.mAuths.valueAt(i)).mAuthority);
                ((SliceAuthority) this.mAuths.valueAt(i)).writeTo(xmlSerializer);
                xmlSerializer.endTag(null, "authority");
            }
            xmlSerializer.endTag(null, "provider");
        } catch (Throwable th) {
            throw th;
        }
    }
}
