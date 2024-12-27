package com.android.server.pm;

import android.R;
import android.util.Slog;
import android.util.Xml;

import com.android.modules.utils.TypedXmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageInstallerService$$ExternalSyntheticLambda7
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PackageInstallerService$$ExternalSyntheticLambda7(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int size;
        PackageInstallerSession[] packageInstallerSessionArr;
        boolean z;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PackageInstallerService packageInstallerService = (PackageInstallerService) obj;
                if (PackageInstallerService.LOGD) {
                    packageInstallerService.getClass();
                    Slog.v("PackageInstaller", "writeSessions()");
                }
                synchronized (packageInstallerService.mSessions) {
                    try {
                        size = packageInstallerService.mSessions.size();
                        packageInstallerSessionArr = new PackageInstallerSession[size];
                        z = false;
                        for (int i2 = 0; i2 < size; i2++) {
                            packageInstallerSessionArr[i2] =
                                    (PackageInstallerSession)
                                            packageInstallerService.mSessions.valueAt(i2);
                        }
                    } finally {
                    }
                }
                FileOutputStream fileOutputStream = null;
                try {
                    FileOutputStream startWrite =
                            packageInstallerService.mSessionsFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "sessions");
                        for (int i3 = 0; i3 < size; i3++) {
                            packageInstallerSessionArr[i3].write(
                                    resolveSerializer, packageInstallerService.mSessionsDir);
                        }
                        resolveSerializer.endTag((String) null, "sessions");
                        resolveSerializer.endDocument();
                        packageInstallerService.mSessionsFile.finishWrite(startWrite);
                        z = true;
                    } catch (IOException unused) {
                        fileOutputStream = startWrite;
                        if (fileOutputStream != null) {
                            packageInstallerService.mSessionsFile.failWrite(fileOutputStream);
                        }
                        return Boolean.valueOf(z);
                    }
                } catch (IOException unused2) {
                }
                return Boolean.valueOf(z);
            default:
                return ((PackageInstallerService.PackageDeleteObserverAdapter) obj)
                        .mContext.getString(R.string.sms_short_code_confirm_message);
        }
    }
}
