package com.samsung.android.knox.zt.service.wrapper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AttestParameterSpec {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Builder {
        public final Object mInstance;

        public Builder(String str, byte[] bArr) {
            try {
                this.mInstance = ClassLoaderHelper.getInstance().getSakClassLoader().loadClass("com.samsung.android.security.keystore.AttestParameterSpec$Builder").getDeclaredConstructor(String.class, byte[].class).newInstance(str, bArr);
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public Object build() {
            try {
                Class[] clsArr = new Class[0];
                return this.mInstance.getClass().getMethod("build", null).invoke(this.mInstance, null);
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public Builder setDeviceAttestation(boolean z) {
            try {
                this.mInstance.getClass().getMethod("setDeviceAttestation", Boolean.TYPE).invoke(this.mInstance, Boolean.valueOf(z));
                return this;
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public Builder setPackageName(String str) {
            try {
                this.mInstance.getClass().getMethod("setPackageName", str.getClass()).invoke(this.mInstance, str);
                return this;
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public Builder setSAKUidRequired(boolean z) {
            try {
                this.mInstance.getClass().getMethod("setSAKUidRequired", Boolean.TYPE).invoke(this.mInstance, Boolean.valueOf(z));
                return this;
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException(th.toString());
            }
        }

        public Builder setVerifiableIntegrity(boolean z) {
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
