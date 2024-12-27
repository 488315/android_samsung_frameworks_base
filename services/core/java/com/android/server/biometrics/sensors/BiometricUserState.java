package com.android.server.biometrics.sensors;

import android.content.Context;
import android.hardware.biometrics.BiometricAuthenticator;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.biometrics.Utils;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BiometricUserState {
    public final Context mContext;
    public final File mFile;
    public boolean mInvalidationInProgress;
    public final ArrayList mBiometrics = new ArrayList();
    public final BiometricUserState$$ExternalSyntheticLambda0 mWriteStateRunnable =
            new Runnable() { // from class:
                             // com.android.server.biometrics.sensors.BiometricUserState$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FileOutputStream startWrite;
                    BiometricUserState biometricUserState = BiometricUserState.this;
                    biometricUserState.getClass();
                    AtomicFile atomicFile = new AtomicFile(biometricUserState.mFile);
                    FileOutputStream fileOutputStream = null;
                    try {
                        startWrite = atomicFile.startWrite();
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.setFeature(
                                "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag(
                                (String) null, "authenticatorIdInvalidation_tag");
                        resolveSerializer.attributeBoolean(
                                (String) null,
                                "authenticatorIdInvalidation_attr",
                                biometricUserState.mInvalidationInProgress);
                        resolveSerializer.endTag((String) null, "authenticatorIdInvalidation_tag");
                        biometricUserState.doWriteState(resolveSerializer);
                        resolveSerializer.endDocument();
                        atomicFile.finishWrite(startWrite);
                        IoUtils.closeQuietly(startWrite);
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = startWrite;
                        try {
                            Slog.wtf("UserState", "Failed to write settings, restoring backup", th);
                            atomicFile.failWrite(fileOutputStream);
                            Slog.e(
                                    "UserState",
                                    "Failed to write to file: " + biometricUserState.mFile,
                                    th);
                        } finally {
                            IoUtils.closeQuietly(fileOutputStream);
                        }
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.biometrics.sensors.BiometricUserState$$ExternalSyntheticLambda0] */
    public BiometricUserState(Context context, String str, int i) {
        File file = new File(Environment.getUserSystemDirectory(i), str);
        this.mFile = file;
        this.mContext = context;
        synchronized (this) {
            try {
                if (!file.exists()) {
                    File file2 =
                            new File(Environment.getUserSystemDirectory(i), getLegacyFileName());
                    if (file2.exists()) {
                        Slog.i(
                                "UserState",
                                "BiometricUserState: migration result = " + file2.renameTo(file));
                    }
                }
                readStateSyncLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void doWriteState(TypedXmlSerializer typedXmlSerializer);

    public final List getBiometrics() {
        ArrayList copy;
        synchronized (this) {
            copy = getCopy(this.mBiometrics);
        }
        return copy;
    }

    public abstract String getBiometricsTag();

    public abstract ArrayList getCopy(ArrayList arrayList);

    public abstract String getLegacyFileName();

    public abstract int getNameTemplateResource();

    public final String getUniqueName() {
        int i = 1;
        while (true) {
            String string = this.mContext.getString(getNameTemplateResource(), Integer.valueOf(i));
            Iterator it = this.mBiometrics.iterator();
            while (it.hasNext()) {
                if (((BiometricAuthenticator.Identifier) it.next()).getName().equals(string)) {
                    break;
                }
            }
            return string;
            i++;
        }
    }

    public abstract void parseBiometricsLocked(TypedXmlPullParser typedXmlPullParser);

    public final void parseStateLocked(TypedXmlPullParser typedXmlPullParser) {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals(getBiometricsTag())) {
                    parseBiometricsLocked(typedXmlPullParser);
                } else if (name.equals("authenticatorIdInvalidation_tag")) {
                    this.mInvalidationInProgress =
                            typedXmlPullParser.getAttributeBoolean(
                                    (String) null, "authenticatorIdInvalidation_attr");
                }
            }
        }
    }

    public final void readStateSyncLocked() {
        if (this.mFile.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(this.mFile);
                try {
                    try {
                        parseStateLocked(Xml.resolvePullParser(fileInputStream));
                    } catch (IOException | XmlPullParserException e) {
                        Slog.e("UserState", "Failed parsing settings file: " + this.mFile, e);
                        IoUtils.closeQuietly(fileInputStream);
                        boolean delete = this.mFile.delete();
                        Slog.e("UserState", "file corruption: delete result = " + delete);
                        if (!delete) {
                            AsyncTask.execute(this.mWriteStateRunnable);
                        }
                        Utils.mDBCorrupted = true;
                    }
                } finally {
                    IoUtils.closeQuietly(fileInputStream);
                }
            } catch (FileNotFoundException unused) {
                Slog.i("UserState", "No fingerprint state");
            }
        }
    }

    public final void removeBiometric(int i) {
        synchronized (this) {
            int i2 = 0;
            while (true) {
                try {
                    if (i2 >= this.mBiometrics.size()) {
                        break;
                    }
                    if (((BiometricAuthenticator.Identifier) this.mBiometrics.get(i2))
                                    .getBiometricId()
                            == i) {
                        this.mBiometrics.remove(i2);
                        AsyncTask.execute(this.mWriteStateRunnable);
                        break;
                    }
                    i2++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
