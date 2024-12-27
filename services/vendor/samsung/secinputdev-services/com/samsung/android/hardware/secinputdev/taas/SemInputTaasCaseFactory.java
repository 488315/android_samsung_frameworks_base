package com.samsung.android.hardware.secinputdev.taas;

/* loaded from: classes.dex */
public class SemInputTaasCaseFactory {
    public static final String TAAS_CASEA = "CASA";
    public static final String TAAS_CASEB = "CASB";

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public SemInputTaasTestCase create(String type) {
        char c;
        switch (type.hashCode()) {
            case 2061100:
                if (type.equals("CASA")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2061101:
                if (type.equals("CASB")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new SemInputTaasTestCaseA();
            case 1:
                return new SemInputTaasTestCaseB();
            default:
                return new SemInputTaasTestCaseDefault();
        }
    }
}
