package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreEncryptor;
import com.samsung.android.server.util.CoreLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* loaded from: classes2.dex */
public class PackageFeatureGroupDataUtilWithEncryption extends PackageFeatureGroupDataUtil {
    public final CoreEncryptor mEncryptor;

    public PackageFeatureGroupDataUtilWithEncryption(Context context, CoreLogger coreLogger, PackageFeatureGroup packageFeatureGroup) {
        super(context, coreLogger, packageFeatureGroup);
        this.mEncryptor = CoreEncryptor.getCoreEncryptor(context);
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupDataUtil
    public void saveToFileOutputStream(PackageFeatureGroupData packageFeatureGroupData, FileOutputStream fileOutputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(packageFeatureGroupData);
                objectOutputStream.flush();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                try {
                    if (!this.mEncryptor.encrypt(byteArrayInputStream, fileOutputStream)) {
                        logFailed("encrypt", null);
                        throw new Exception("encrypt");
                    }
                    if (CoreRune.SAFE_DEBUG) {
                        logSucceeded("encrypt");
                    }
                    byteArrayInputStream.close();
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupDataUtil
    public Object loadFromFileInputStream(FileInputStream fileInputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (!this.mEncryptor.decrypt(fileInputStream, byteArrayOutputStream)) {
                logFailed("decrypt", null);
                throw new Exception("decrypt");
            }
            if (CoreRune.SAFE_DEBUG) {
                logSucceeded("decrypt");
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                try {
                    Object readObject = objectInputStream.readObject();
                    objectInputStream.close();
                    byteArrayInputStream.close();
                    byteArrayOutputStream.close();
                    return readObject;
                } finally {
                }
            } finally {
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
