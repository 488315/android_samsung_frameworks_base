package com.samsung.android.knox.zt.service.wrapper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AttestParameterSpec {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        public final Object mInstance;

        public Builder(String str, byte[] bArr) {
            try {
                this.mInstance = ClassLoaderHelper.getInstance().mSakClassLoader.loadClass("com.samsung.android.security.keystore.AttestParameterSpec$Builder").getDeclaredConstructor(String.class, byte[].class).newInstance(str, bArr);
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public final Object build() {
            try {
                return this.mInstance.getClass().getMethod("build", new Class[0]).invoke(this.mInstance, new Object[0]);
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public final Builder setDeviceAttestation(boolean z) {
            try {
                this.mInstance.getClass().getMethod("setDeviceAttestation", Boolean.TYPE).invoke(this.mInstance, Boolean.valueOf(z));
                return this;
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public final Builder setPackageName(String str) {
            try {
                this.mInstance.getClass().getMethod("setPackageName", str.getClass()).invoke(this.mInstance, str);
                return this;
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public final Builder setVerifiableIntegrity(boolean z) {
            try {
                this.mInstance.getClass().getMethod("setVerifiableIntegrity", Boolean.TYPE).invoke(this.mInstance, Boolean.valueOf(z));
                return this;
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }
    }
}
