package com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.crypto.params.AEADParameters;
import com.android.internal.org.bouncycastle.crypto.params.KeyParameter;
import com.android.internal.org.bouncycastle.internal.asn1.cms.GCMParameters;
import com.android.internal.org.bouncycastle.util.Integers;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* loaded from: classes5.dex */
public class GcmSpecUtil {
    private static final Constructor constructor;
    static final Class gcmSpecClass;
    private static final Method iv;
    private static final Method tLen;

    static {
        Class loadClass = ClassUtil.loadClass(GcmSpecUtil.class, "javax.crypto.spec.GCMParameterSpec");
        gcmSpecClass = loadClass;
        if (loadClass != null) {
            constructor = extractConstructor();
            tLen = extractMethod("getTLen");
            iv = extractMethod("getIV");
        } else {
            constructor = null;
            tLen = null;
            iv = null;
        }
    }

    private static Constructor extractConstructor() {
        try {
            return (Constructor) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws Exception {
                    return GcmSpecUtil.gcmSpecClass.getConstructor(Integer.TYPE, byte[].class);
                }
            });
        } catch (PrivilegedActionException unused) {
            return null;
        }
    }

    private static Method extractMethod(final String str) {
        try {
            return (Method) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.2
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws Exception {
                    Class[] clsArr = new Class[0];
                    return GcmSpecUtil.gcmSpecClass.getDeclaredMethod(str, null);
                }
            });
        } catch (PrivilegedActionException unused) {
            return null;
        }
    }

    public static boolean gcmSpecExists() {
        return gcmSpecClass != null;
    }

    public static boolean gcmSpecExtractable() {
        return constructor != null;
    }

    public static boolean isGcmSpec(AlgorithmParameterSpec algorithmParameterSpec) {
        Class cls = gcmSpecClass;
        return cls != null && cls.isInstance(algorithmParameterSpec);
    }

    public static boolean isGcmSpec(Class cls) {
        return gcmSpecClass == cls;
    }

    public static AlgorithmParameterSpec extractGcmSpec(ASN1Primitive aSN1Primitive) throws InvalidParameterSpecException {
        try {
            GCMParameters gCMParameters = GCMParameters.getInstance(aSN1Primitive);
            return (AlgorithmParameterSpec) constructor.newInstance(Integers.valueOf(gCMParameters.getIcvLen() * 8), gCMParameters.getNonce());
        } catch (Exception e) {
            throw new InvalidParameterSpecException("Construction failed: " + e.getMessage());
        }
    }

    static AEADParameters extractAeadParameters(final KeyParameter keyParameter, final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        try {
            return (AEADParameters) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.3
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws Exception {
                    return new AEADParameters(KeyParameter.this, ((Integer) GcmSpecUtil.tLen.invoke(algorithmParameterSpec, null)).intValue(), (byte[]) GcmSpecUtil.iv.invoke(algorithmParameterSpec, null));
                }
            });
        } catch (Exception unused) {
            throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
        }
    }

    public static GCMParameters extractGcmParameters(final AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        try {
            return (GCMParameters) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: com.android.internal.org.bouncycastle.jcajce.provider.symmetric.util.GcmSpecUtil.4
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws Exception {
                    return new GCMParameters((byte[]) GcmSpecUtil.iv.invoke(algorithmParameterSpec, null), ((Integer) GcmSpecUtil.tLen.invoke(algorithmParameterSpec, null)).intValue() / 8);
                }
            });
        } catch (Exception unused) {
            throw new InvalidParameterSpecException("Cannot process GCMParameterSpec");
        }
    }
}
