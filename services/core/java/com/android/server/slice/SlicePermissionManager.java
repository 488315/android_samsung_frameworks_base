package com.android.server.slice;

import android.content.ContentProvider;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SlicePermissionManager implements DirtyTracker {
    public final H mHandler;
    public final File mSliceDir;
    public final ArrayMap mCachedProviders = new ArrayMap();
    public final ArrayMap mCachedClients = new ArrayMap();
    public final ArraySet mDirty = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                SlicePermissionManager.this.mDirty.add((DirtyTracker.Persistable) message.obj);
                return;
            }
            if (i == 2) {
                SlicePermissionManager.this.handlePersist();
                return;
            }
            if (i == 3) {
                SlicePermissionManager slicePermissionManager = SlicePermissionManager.this;
                PkgUser pkgUser = (PkgUser) message.obj;
                slicePermissionManager.getClass();
                slicePermissionManager.getFile("client_".concat(pkgUser.toString())).delete();
                slicePermissionManager.getFile("provider_".concat(pkgUser.toString())).delete();
                slicePermissionManager.mDirty.remove(
                        slicePermissionManager.mCachedClients.remove(pkgUser));
                slicePermissionManager.mDirty.remove(
                        slicePermissionManager.mCachedProviders.remove(pkgUser));
                return;
            }
            if (i == 4) {
                synchronized (SlicePermissionManager.this.mCachedClients) {
                    SlicePermissionManager.this.mCachedClients.remove(message.obj);
                }
            } else {
                if (i != 5) {
                    return;
                }
                synchronized (SlicePermissionManager.this.mCachedProviders) {
                    SlicePermissionManager.this.mCachedProviders.remove(message.obj);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ParserHolder implements AutoCloseable {
        public InputStream input;
        public XmlPullParser parser;

        @Override // java.lang.AutoCloseable
        public final void close() {
            this.input.close();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PkgUser {
        public final String mPkg;
        public final int mUserId;

        public PkgUser(String str) {
            try {
                String[] split = str.split("@", 2);
                this.mPkg = split[0];
                this.mUserId = Integer.parseInt(split[1]);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }

        public PkgUser(String str, int i) {
            this.mPkg = str;
            this.mUserId = i;
        }

        public final boolean equals(Object obj) {
            if (!PkgUser.class.equals(obj != null ? obj.getClass() : null)) {
                return false;
            }
            PkgUser pkgUser = (PkgUser) obj;
            return Objects.equals(pkgUser.mPkg, this.mPkg) && pkgUser.mUserId == this.mUserId;
        }

        public final int hashCode() {
            return this.mPkg.hashCode() + this.mUserId;
        }

        public final String toString() {
            return String.format("%s@%d", this.mPkg, Integer.valueOf(this.mUserId));
        }
    }

    public SlicePermissionManager(Context context, Looper looper, File file) {
        this.mHandler = new H(looper);
        this.mSliceDir = file;
    }

    public void addDirtyImmediate(DirtyTracker.Persistable persistable) {
        this.mDirty.add(persistable);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.slice.SliceClientPermissions getClient(
            com.android.server.slice.SlicePermissionManager.PkgUser r7) {
        /*
            r6 = this;
            android.util.ArrayMap r0 = r6.mCachedClients
            monitor-enter(r0)
            android.util.ArrayMap r1 = r6.mCachedClients     // Catch: java.lang.Throwable -> L70
            java.lang.Object r1 = r1.get(r7)     // Catch: java.lang.Throwable -> L70
            com.android.server.slice.SliceClientPermissions r1 = (com.android.server.slice.SliceClientPermissions) r1     // Catch: java.lang.Throwable -> L70
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L70
            if (r1 != 0) goto L6f
            java.lang.String r0 = r7.toString()     // Catch: org.xmlpull.v1.XmlPullParserException -> L3c java.io.IOException -> L3e java.io.FileNotFoundException -> L5d
            java.lang.String r1 = "client_"
            java.lang.String r0 = r1.concat(r0)     // Catch: org.xmlpull.v1.XmlPullParserException -> L3c java.io.IOException -> L3e java.io.FileNotFoundException -> L5d
            com.android.server.slice.SlicePermissionManager$ParserHolder r0 = r6.getParser(r0)     // Catch: org.xmlpull.v1.XmlPullParserException -> L3c java.io.IOException -> L3e java.io.FileNotFoundException -> L5d
            org.xmlpull.v1.XmlPullParser r1 = r0.parser     // Catch: java.lang.Throwable -> L40
            com.android.server.slice.SliceClientPermissions r1 = com.android.server.slice.SliceClientPermissions.createFrom(r1, r6)     // Catch: java.lang.Throwable -> L40
            android.util.ArrayMap r2 = r6.mCachedClients     // Catch: java.lang.Throwable -> L40
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L40
            android.util.ArrayMap r3 = r6.mCachedClients     // Catch: java.lang.Throwable -> L42
            r3.put(r7, r1)     // Catch: java.lang.Throwable -> L42
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L42
            com.android.server.slice.SlicePermissionManager$H r2 = r6.mHandler     // Catch: java.lang.Throwable -> L40
            r3 = 4
            android.os.Message r3 = r2.obtainMessage(r3, r7)     // Catch: java.lang.Throwable -> L40
            r4 = 300000(0x493e0, double:1.482197E-318)
            r2.sendMessageDelayed(r3, r4)     // Catch: java.lang.Throwable -> L40
            r0.close()     // Catch: org.xmlpull.v1.XmlPullParserException -> L3c java.io.IOException -> L3e java.io.FileNotFoundException -> L5d
            return r1
        L3c:
            r0 = move-exception
            goto L4e
        L3e:
            r0 = move-exception
            goto L56
        L40:
            r1 = move-exception
            goto L45
        L42:
            r1 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L42
            throw r1     // Catch: java.lang.Throwable -> L40
        L45:
            r0.close()     // Catch: java.lang.Throwable -> L49
            goto L4d
        L49:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: org.xmlpull.v1.XmlPullParserException -> L3c java.io.IOException -> L3e java.io.FileNotFoundException -> L5d
        L4d:
            throw r1     // Catch: org.xmlpull.v1.XmlPullParserException -> L3c java.io.IOException -> L3e java.io.FileNotFoundException -> L5d
        L4e:
            java.lang.String r1 = "SlicePermissionManager"
            java.lang.String r2 = "Can't read client"
            android.util.Log.e(r1, r2, r0)
            goto L5d
        L56:
            java.lang.String r1 = "SlicePermissionManager"
            java.lang.String r2 = "Can't read client"
            android.util.Log.e(r1, r2, r0)
        L5d:
            com.android.server.slice.SliceClientPermissions r1 = new com.android.server.slice.SliceClientPermissions
            r1.<init>(r7, r6)
            android.util.ArrayMap r0 = r6.mCachedClients
            monitor-enter(r0)
            android.util.ArrayMap r6 = r6.mCachedClients     // Catch: java.lang.Throwable -> L6c
            r6.put(r7, r1)     // Catch: java.lang.Throwable -> L6c
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6c
            goto L6f
        L6c:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6c
            throw r6
        L6f:
            return r1
        L70:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L70
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.slice.SlicePermissionManager.getClient(com.android.server.slice.SlicePermissionManager$PkgUser):com.android.server.slice.SliceClientPermissions");
    }

    public final AtomicFile getFile(String str) {
        if (!this.mSliceDir.exists()) {
            this.mSliceDir.mkdir();
        }
        return new AtomicFile(new File(this.mSliceDir, str));
    }

    public final ParserHolder getParser(String str) {
        AtomicFile file = getFile(str);
        ParserHolder parserHolder = new ParserHolder();
        parserHolder.input = file.openRead();
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        parserHolder.parser = newPullParser;
        newPullParser.setInput(parserHolder.input, Xml.Encoding.UTF_8.name());
        return parserHolder;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0066 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.slice.SliceProviderPermissions getProvider(
            com.android.server.slice.SlicePermissionManager.PkgUser r7) {
        /*
            r6 = this;
            android.util.ArrayMap r0 = r6.mCachedProviders
            monitor-enter(r0)
            android.util.ArrayMap r1 = r6.mCachedProviders     // Catch: java.lang.Throwable -> L71
            java.lang.Object r1 = r1.get(r7)     // Catch: java.lang.Throwable -> L71
            com.android.server.slice.SliceProviderPermissions r1 = (com.android.server.slice.SliceProviderPermissions) r1     // Catch: java.lang.Throwable -> L71
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L71
            if (r1 != 0) goto L70
            java.lang.String r0 = r7.toString()     // Catch: org.xmlpull.v1.XmlPullParserException -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L5e
            java.lang.String r1 = "provider_"
            java.lang.String r0 = r1.concat(r0)     // Catch: org.xmlpull.v1.XmlPullParserException -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L5e
            com.android.server.slice.SlicePermissionManager$ParserHolder r0 = r6.getParser(r0)     // Catch: org.xmlpull.v1.XmlPullParserException -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L5e
            org.xmlpull.v1.XmlPullParser r1 = r0.parser     // Catch: java.lang.Throwable -> L41
            com.android.server.slice.SliceProviderPermissions r1 = com.android.server.slice.SliceProviderPermissions.createFrom(r1, r6)     // Catch: java.lang.Throwable -> L41
            android.util.ArrayMap r2 = r6.mCachedProviders     // Catch: java.lang.Throwable -> L41
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L41
            android.util.ArrayMap r3 = r6.mCachedProviders     // Catch: java.lang.Throwable -> L43
            r3.put(r7, r1)     // Catch: java.lang.Throwable -> L43
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L43
            com.android.server.slice.SlicePermissionManager$H r2 = r6.mHandler     // Catch: java.lang.Throwable -> L41
            r3 = 5
            android.os.Message r3 = r2.obtainMessage(r3, r7)     // Catch: java.lang.Throwable -> L41
            r4 = 300000(0x493e0, double:1.482197E-318)
            r2.sendMessageDelayed(r3, r4)     // Catch: java.lang.Throwable -> L41
            r0.close()     // Catch: org.xmlpull.v1.XmlPullParserException -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L5e
            return r1
        L3d:
            r0 = move-exception
            goto L4f
        L3f:
            r0 = move-exception
            goto L57
        L41:
            r1 = move-exception
            goto L46
        L43:
            r1 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L43
            throw r1     // Catch: java.lang.Throwable -> L41
        L46:
            r0.close()     // Catch: java.lang.Throwable -> L4a
            goto L4e
        L4a:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch: org.xmlpull.v1.XmlPullParserException -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L5e
        L4e:
            throw r1     // Catch: org.xmlpull.v1.XmlPullParserException -> L3d java.io.IOException -> L3f java.io.FileNotFoundException -> L5e
        L4f:
            java.lang.String r1 = "SlicePermissionManager"
            java.lang.String r2 = "Can't read provider"
            android.util.Log.e(r1, r2, r0)
            goto L5e
        L57:
            java.lang.String r1 = "SlicePermissionManager"
            java.lang.String r2 = "Can't read provider"
            android.util.Log.e(r1, r2, r0)
        L5e:
            com.android.server.slice.SliceProviderPermissions r1 = new com.android.server.slice.SliceProviderPermissions
            r1.<init>(r7, r6)
            android.util.ArrayMap r0 = r6.mCachedProviders
            monitor-enter(r0)
            android.util.ArrayMap r6 = r6.mCachedProviders     // Catch: java.lang.Throwable -> L6d
            r6.put(r7, r1)     // Catch: java.lang.Throwable -> L6d
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            goto L70
        L6d:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6d
            throw r6
        L70:
            return r1
        L71:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L71
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.slice.SlicePermissionManager.getProvider(com.android.server.slice.SlicePermissionManager$PkgUser):com.android.server.slice.SliceProviderPermissions");
    }

    public final void grantSliceAccess(String str, int i, String str2, int i2, Uri uri) {
        SliceProviderPermissions.SliceAuthority sliceAuthority;
        PkgUser pkgUser = new PkgUser(str, i);
        PkgUser pkgUser2 = new PkgUser(str2, i2);
        SliceClientPermissions.SliceAuthority orCreateAuthority =
                getClient(pkgUser)
                        .getOrCreateAuthority(new PkgUser(uri.getAuthority(), i2), pkgUser2);
        List<String> pathSegments = uri.getPathSegments();
        String[] strArr = (String[]) pathSegments.toArray(new String[pathSegments.size()]);
        int size = orCreateAuthority.mPaths.size() - 1;
        while (true) {
            if (size < 0) {
                orCreateAuthority.mPaths.add(strArr);
                orCreateAuthority.mTracker.onPersistableDirty(orCreateAuthority);
                break;
            }
            String[] strArr2 = (String[]) orCreateAuthority.mPaths.valueAt(size);
            if (SliceClientPermissions.SliceAuthority.isPathPrefixMatch(strArr2, strArr)) {
                break;
            }
            if (SliceClientPermissions.SliceAuthority.isPathPrefixMatch(strArr, strArr2)) {
                orCreateAuthority.mPaths.removeAt(size);
            }
            size--;
        }
        SliceProviderPermissions provider = getProvider(pkgUser2);
        String authority = ContentProvider.getUriWithoutUserId(uri).getAuthority();
        synchronized (provider) {
            sliceAuthority =
                    (SliceProviderPermissions.SliceAuthority) provider.mAuths.get(authority);
            if (sliceAuthority == null) {
                sliceAuthority = new SliceProviderPermissions.SliceAuthority(authority, provider);
                provider.mAuths.put(authority, sliceAuthority);
                provider.onPersistableDirty(sliceAuthority);
            }
        }
        synchronized (sliceAuthority) {
            if (sliceAuthority.mPkgs.add(pkgUser)) {
                sliceAuthority.mTracker.onPersistableDirty(sliceAuthority);
            }
        }
    }

    public void handlePersist() {
        synchronized (this) {
            Iterator it = this.mDirty.iterator();
            while (it.hasNext()) {
                DirtyTracker.Persistable persistable = (DirtyTracker.Persistable) it.next();
                AtomicFile file = getFile(persistable.getFileName());
                try {
                    FileOutputStream startWrite = file.startWrite();
                    try {
                        XmlSerializer newSerializer =
                                XmlPullParserFactory.newInstance().newSerializer();
                        newSerializer.setOutput(startWrite, Xml.Encoding.UTF_8.name());
                        persistable.writeTo(newSerializer);
                        newSerializer.flush();
                        file.finishWrite(startWrite);
                    } catch (IOException | RuntimeException | XmlPullParserException e) {
                        Slog.w(
                                "SlicePermissionManager",
                                "Failed to save access file, restoring backup",
                                e);
                        file.failWrite(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("SlicePermissionManager", "Failed to save access file", e2);
                    return;
                }
            }
            this.mDirty.clear();
        }
    }

    @Override // com.android.server.slice.DirtyTracker
    public final void onPersistableDirty(DirtyTracker.Persistable persistable) {
        H h = this.mHandler;
        h.removeMessages(2);
        h.obtainMessage(1, persistable).sendToTarget();
        h.sendEmptyMessageDelayed(2, 500L);
    }

    public final void readRestore(XmlPullParser xmlPullParser) {
        synchronized (this) {
            while (true) {
                try {
                    if (xmlPullParser.getEventType() == 2
                            && "slice-access-list".equals(xmlPullParser.getName())) {
                        break;
                    }
                    if (xmlPullParser.getEventType() == 1) {
                        break;
                    } else {
                        xmlPullParser.next();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (XmlUtils.readIntAttribute(xmlPullParser, "version", 0) < 2) {
                return;
            }
            while (xmlPullParser.getEventType() != 1) {
                if (xmlPullParser.getEventType() != 2) {
                    xmlPullParser.next();
                } else if ("client".equals(xmlPullParser.getName())) {
                    SliceClientPermissions createFrom =
                            SliceClientPermissions.createFrom(xmlPullParser, this);
                    synchronized (this.mCachedClients) {
                        this.mCachedClients.put(createFrom.mPkg, createFrom);
                    }
                    onPersistableDirty(createFrom);
                    H h = this.mHandler;
                    h.sendMessageDelayed(h.obtainMessage(4, createFrom.mPkg), 300000L);
                } else if ("provider".equals(xmlPullParser.getName())) {
                    SliceProviderPermissions createFrom2 =
                            SliceProviderPermissions.createFrom(xmlPullParser, this);
                    synchronized (this.mCachedProviders) {
                        this.mCachedProviders.put(createFrom2.mPkg, createFrom2);
                    }
                    onPersistableDirty(createFrom2);
                    H h2 = this.mHandler;
                    h2.sendMessageDelayed(h2.obtainMessage(5, createFrom2.mPkg), 300000L);
                } else {
                    xmlPullParser.next();
                }
            }
        }
    }

    public final void removePkg(int i, String str) {
        ArrayList arrayList;
        ArraySet arraySet;
        PkgUser pkgUser = new PkgUser(str, i);
        SliceProviderPermissions provider = getProvider(pkgUser);
        synchronized (provider) {
            arrayList = new ArrayList(provider.mAuths.values());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SliceProviderPermissions.SliceAuthority sliceAuthority =
                    (SliceProviderPermissions.SliceAuthority) it.next();
            synchronized (sliceAuthority) {
                arraySet = new ArraySet(sliceAuthority.mPkgs);
            }
            Iterator it2 = arraySet.iterator();
            while (it2.hasNext()) {
                SliceClientPermissions client = getClient((PkgUser) it2.next());
                if (client.mAuths.remove(new PkgUser(sliceAuthority.mAuthority, i)) != null) {
                    client.mTracker.onPersistableDirty(client);
                }
            }
        }
        SliceClientPermissions client2 = getClient(pkgUser);
        if (client2.mHasFullAccess || !client2.mAuths.isEmpty()) {
            client2.mHasFullAccess = false;
            client2.mAuths.clear();
            client2.onPersistableDirty(client2);
        }
        this.mHandler.obtainMessage(3, pkgUser).sendToTarget();
    }

    public final void writeBackup(XmlSerializer xmlSerializer) {
        DirtyTracker.Persistable persistable;
        synchronized (this) {
            try {
                xmlSerializer.startTag(null, "slice-access-list");
                xmlSerializer.attribute(null, "version", String.valueOf(2));
                SlicePermissionManager$$ExternalSyntheticLambda0
                        slicePermissionManager$$ExternalSyntheticLambda0 =
                                new SlicePermissionManager$$ExternalSyntheticLambda0();
                if (this.mHandler.hasMessages(2)) {
                    this.mHandler.removeMessages(2);
                    handlePersist();
                }
                for (String str : new File(this.mSliceDir.getAbsolutePath()).list()) {
                    ParserHolder parser = getParser(str);
                    while (true) {
                        try {
                            if (parser.parser.getEventType() == 1) {
                                persistable = null;
                                break;
                            } else if (parser.parser.getEventType() == 2) {
                                persistable =
                                        "client".equals(parser.parser.getName())
                                                ? SliceClientPermissions.createFrom(
                                                        parser.parser,
                                                        slicePermissionManager$$ExternalSyntheticLambda0)
                                                : SliceProviderPermissions.createFrom(
                                                        parser.parser,
                                                        slicePermissionManager$$ExternalSyntheticLambda0);
                            } else {
                                parser.parser.next();
                            }
                        } finally {
                            try {
                                parser.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                    }
                    if (persistable != null) {
                        persistable.writeTo(xmlSerializer);
                    } else {
                        Slog.w(
                                "SlicePermissionManager",
                                "Invalid or empty slice permissions file: " + str);
                    }
                    parser.close();
                }
                xmlSerializer.endTag(null, "slice-access-list");
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }
}
