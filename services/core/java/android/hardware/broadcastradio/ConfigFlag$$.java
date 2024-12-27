package android.hardware.broadcastradio;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface ConfigFlag$$ {
    static String toString(int i) {
        return i == 1 ? "FORCE_MONO" : i == 2 ? "FORCE_ANALOG" : i == 3 ? "FORCE_DIGITAL" : i == 4 ? "RDS_AF" : i == 5 ? "RDS_REG" : i == 6 ? "DAB_DAB_LINKING" : i == 7 ? "DAB_FM_LINKING" : i == 8 ? "DAB_DAB_SOFT_LINKING" : i == 9 ? "DAB_FM_SOFT_LINKING" : i == 10 ? "FORCE_ANALOG_FM" : i == 11 ? "FORCE_ANALOG_AM" : Integer.toString(i);
    }
}
