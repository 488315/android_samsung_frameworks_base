package com.android.server.power.stats;

import android.hardware.power.stats.EnergyConsumer;
import android.hardware.power.stats.EnergyConsumerAttribution;
import android.hardware.power.stats.EnergyConsumerResult;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EnergyConsumerSnapshot {
    public final SparseArray mAttributionSnapshots;
    public final SparseLongArray mEnergyConsumerSnapshots;
    public final SparseArray mEnergyConsumers;
    public final int mNumCpuClusterOrdinals;
    public final int mNumDisplayOrdinals;
    public final int mNumOtherOrdinals;
    public final SparseIntArray mVoltageSnapshots;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EnergyConsumerDeltaData {
        public long bluetoothChargeUC;
        public long cameraChargeUC;
        public long[] cpuClusterChargeUC;
        public long[] displayChargeUC;
        public long gnssChargeUC;
        public long mobileRadioChargeUC;
        public long[] otherTotalChargeUC;
        public SparseLongArray[] otherUidChargesUC;
        public long wifiChargeUC;
    }

    public EnergyConsumerSnapshot(SparseArray sparseArray) {
        this.mEnergyConsumers = sparseArray;
        this.mEnergyConsumerSnapshots = new SparseLongArray(sparseArray.size());
        this.mVoltageSnapshots = new SparseIntArray(sparseArray.size());
        this.mNumCpuClusterOrdinals = calculateNumOrdinals(2, sparseArray);
        this.mNumDisplayOrdinals = calculateNumOrdinals(3, sparseArray);
        int calculateNumOrdinals = calculateNumOrdinals(0, sparseArray);
        this.mNumOtherOrdinals = calculateNumOrdinals;
        this.mAttributionSnapshots = new SparseArray(calculateNumOrdinals);
    }

    public static int calculateNumOrdinals(int i, SparseArray sparseArray) {
        int size = sparseArray.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (((EnergyConsumer) sparseArray.valueAt(i3)).type == i) {
                i2++;
            }
        }
        return i2;
    }

    public final String[] getOtherOrdinalNames() {
        String sb;
        String[] strArr = new String[this.mNumOtherOrdinals];
        int size = this.mEnergyConsumers.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            EnergyConsumer energyConsumer = (EnergyConsumer) this.mEnergyConsumers.valueAt(i2);
            if (energyConsumer.type == 0) {
                int i3 = i + 1;
                String str = energyConsumer.name;
                if (str == null) {
                    sb = "";
                } else {
                    StringBuilder sb2 = new StringBuilder(str.length());
                    for (char c : str.toCharArray()) {
                        if (Character.isWhitespace(c)) {
                            sb2.append(' ');
                        } else if (Character.isISOControl(c)) {
                            sb2.append('_');
                        } else {
                            sb2.append(c);
                        }
                    }
                    sb = sb2.toString();
                }
                strArr[i] = sb;
                i = i3;
            }
        }
        return strArr;
    }

    public final EnergyConsumerDeltaData updateAndGetDelta(EnergyConsumerResult[] energyConsumerResultArr, int i) {
        int i2;
        int i3;
        int i4;
        SparseLongArray sparseLongArray;
        long j;
        long j2;
        long j3;
        long j4;
        int i5;
        EnergyConsumerSnapshot energyConsumerSnapshot = this;
        EnergyConsumerResult[] energyConsumerResultArr2 = energyConsumerResultArr;
        int i6 = i;
        boolean z = false;
        if (energyConsumerResultArr2 == null || energyConsumerResultArr2.length == 0) {
            return null;
        }
        if (i6 <= 0) {
            Slog.wtf("EnergyConsumerSnapshot", "Unexpected battery voltage (" + i6 + " mV) when taking energy consumer snapshot");
            return null;
        }
        EnergyConsumerDeltaData energyConsumerDeltaData = new EnergyConsumerDeltaData();
        long j5 = -1;
        energyConsumerDeltaData.bluetoothChargeUC = -1L;
        energyConsumerDeltaData.cpuClusterChargeUC = null;
        energyConsumerDeltaData.displayChargeUC = null;
        energyConsumerDeltaData.gnssChargeUC = -1L;
        energyConsumerDeltaData.mobileRadioChargeUC = -1L;
        energyConsumerDeltaData.wifiChargeUC = -1L;
        energyConsumerDeltaData.cameraChargeUC = -1L;
        energyConsumerDeltaData.otherTotalChargeUC = null;
        energyConsumerDeltaData.otherUidChargesUC = null;
        int length = energyConsumerResultArr2.length;
        int i7 = 0;
        while (i7 < length) {
            EnergyConsumerResult energyConsumerResult = energyConsumerResultArr2[i7];
            int i8 = energyConsumerResult.id;
            long j6 = energyConsumerResult.energyUWs;
            EnergyConsumerAttribution[] energyConsumerAttributionArr = energyConsumerResult.attribution;
            EnergyConsumer energyConsumer = (EnergyConsumer) energyConsumerSnapshot.mEnergyConsumers.get(i8, z);
            if (energyConsumer == null) {
                NandswapManager$$ExternalSyntheticOutline0.m(i8, "updateAndGetDelta given invalid consumerId ", "EnergyConsumerSnapshot");
                i5 = i6;
                i2 = length;
                i4 = i7;
            } else {
                byte b = energyConsumer.type;
                int i9 = energyConsumer.ordinal;
                i2 = length;
                long j7 = energyConsumerSnapshot.mEnergyConsumerSnapshots.get(i8, j5);
                int i10 = energyConsumerSnapshot.mVoltageSnapshots.get(i8);
                energyConsumerSnapshot.mEnergyConsumerSnapshots.put(i8, j6);
                energyConsumerSnapshot.mVoltageSnapshots.put(i8, i6);
                int i11 = ((i10 + i6) + 1) / 2;
                if (energyConsumer.type != 0) {
                    i3 = i10;
                    j = j7;
                    i4 = i7;
                } else {
                    if (energyConsumerAttributionArr == null) {
                        energyConsumerAttributionArr = new EnergyConsumerAttribution[0];
                    }
                    SparseLongArray sparseLongArray2 = (SparseLongArray) energyConsumerSnapshot.mAttributionSnapshots.get(energyConsumer.id, null);
                    if (sparseLongArray2 == null) {
                        SparseLongArray sparseLongArray3 = new SparseLongArray(energyConsumerAttributionArr.length);
                        energyConsumerSnapshot.mAttributionSnapshots.put(energyConsumer.id, sparseLongArray3);
                        int length2 = energyConsumerAttributionArr.length;
                        int i12 = 0;
                        while (i12 < length2) {
                            int i13 = length2;
                            EnergyConsumerAttribution energyConsumerAttribution = energyConsumerAttributionArr[i12];
                            sparseLongArray3.put(energyConsumerAttribution.uid, energyConsumerAttribution.energyUWs);
                            i12++;
                            length2 = i13;
                            i7 = i7;
                            i10 = i10;
                        }
                        i3 = i10;
                        i4 = i7;
                        j = j7;
                    } else {
                        i3 = i10;
                        i4 = i7;
                        sparseLongArray = new SparseLongArray();
                        int length3 = energyConsumerAttributionArr.length;
                        int i14 = 0;
                        while (i14 < length3) {
                            EnergyConsumerAttribution energyConsumerAttribution2 = energyConsumerAttributionArr[i14];
                            int i15 = energyConsumerAttribution2.uid;
                            long j8 = j6;
                            long j9 = energyConsumerAttribution2.energyUWs;
                            int i16 = i14;
                            long j10 = j7;
                            int i17 = length3;
                            long j11 = sparseLongArray2.get(i15, 0L);
                            sparseLongArray2.put(i15, j9);
                            if (j11 >= 0 && j9 != j11) {
                                long j12 = j9 - j11;
                                if (j12 < 0 || i11 <= 0) {
                                    StringBuilder sb = new StringBuilder("EnergyConsumer ");
                                    sb.append(energyConsumer.name);
                                    sb.append(": new energy (");
                                    sb.append(j9);
                                    BootReceiver$$ExternalSyntheticOutline0.m(sb, ") but old energy (", j11, "). Average voltage (");
                                    NandswapManager$$ExternalSyntheticOutline0.m(sb, i11, ")Skipping. ", "EnergyConsumerSnapshot");
                                } else {
                                    sparseLongArray.put(i15, ((j12 * 1000) + (i11 / 2)) / i11);
                                }
                            }
                            i14 = i16 + 1;
                            length3 = i17;
                            j6 = j8;
                            j7 = j10;
                        }
                        j = j7;
                        j2 = j6;
                        j3 = 0;
                        if (j >= j3 && j2 != j) {
                            j4 = j2 - j;
                            if (j4 >= j3 || i3 <= 0) {
                                energyConsumerSnapshot = this;
                                StringBuilder sb2 = new StringBuilder("Bad data! EnergyConsumer ");
                                sb2.append(energyConsumer.name);
                                sb2.append(": new energy (");
                                sb2.append(j2);
                                BootReceiver$$ExternalSyntheticOutline0.m(sb2, ") < old energy (", j, "), new voltage (");
                                i5 = i;
                                Slog.e("EnergyConsumerSnapshot", ActivityManagerService$$ExternalSyntheticOutline0.m(i5, i3, "), old voltage (", "). Skipping. ", sb2));
                            } else {
                                long j13 = ((j4 * 1000) + (i11 / 2)) / i11;
                                switch (b) {
                                    case 0:
                                        energyConsumerSnapshot = this;
                                        if (energyConsumerDeltaData.otherTotalChargeUC == null) {
                                            int i18 = energyConsumerSnapshot.mNumOtherOrdinals;
                                            energyConsumerDeltaData.otherTotalChargeUC = new long[i18];
                                            energyConsumerDeltaData.otherUidChargesUC = new SparseLongArray[i18];
                                        }
                                        energyConsumerDeltaData.otherTotalChargeUC[i9] = j13;
                                        energyConsumerDeltaData.otherUidChargesUC[i9] = sparseLongArray;
                                        break;
                                    case 1:
                                        energyConsumerSnapshot = this;
                                        energyConsumerDeltaData.bluetoothChargeUC = j13;
                                        break;
                                    case 2:
                                        energyConsumerSnapshot = this;
                                        if (energyConsumerDeltaData.cpuClusterChargeUC == null) {
                                            energyConsumerDeltaData.cpuClusterChargeUC = new long[energyConsumerSnapshot.mNumCpuClusterOrdinals];
                                        }
                                        energyConsumerDeltaData.cpuClusterChargeUC[i9] = j13;
                                        break;
                                    case 3:
                                        if (energyConsumerDeltaData.displayChargeUC == null) {
                                            energyConsumerSnapshot = this;
                                            energyConsumerDeltaData.displayChargeUC = new long[energyConsumerSnapshot.mNumDisplayOrdinals];
                                        } else {
                                            energyConsumerSnapshot = this;
                                        }
                                        energyConsumerDeltaData.displayChargeUC[i9] = j13;
                                        break;
                                    case 4:
                                        energyConsumerDeltaData.gnssChargeUC = j13;
                                        break;
                                    case 5:
                                        energyConsumerDeltaData.mobileRadioChargeUC = j13;
                                        break;
                                    case 6:
                                        energyConsumerDeltaData.wifiChargeUC = j13;
                                        break;
                                    case 7:
                                        energyConsumerDeltaData.cameraChargeUC = j13;
                                        break;
                                    default:
                                        Slog.w("EnergyConsumerSnapshot", "Ignoring consumer " + energyConsumer.name + " of unknown type " + ((int) b));
                                        break;
                                }
                                i5 = i;
                            }
                        }
                        energyConsumerSnapshot = this;
                        i5 = i;
                    }
                }
                j2 = j6;
                j3 = 0;
                sparseLongArray = null;
                if (j >= j3) {
                    j4 = j2 - j;
                    if (j4 >= j3) {
                    }
                    energyConsumerSnapshot = this;
                    StringBuilder sb22 = new StringBuilder("Bad data! EnergyConsumer ");
                    sb22.append(energyConsumer.name);
                    sb22.append(": new energy (");
                    sb22.append(j2);
                    BootReceiver$$ExternalSyntheticOutline0.m(sb22, ") < old energy (", j, "), new voltage (");
                    i5 = i;
                    Slog.e("EnergyConsumerSnapshot", ActivityManagerService$$ExternalSyntheticOutline0.m(i5, i3, "), old voltage (", "). Skipping. ", sb22));
                }
                energyConsumerSnapshot = this;
                i5 = i;
            }
            i7 = i4 + 1;
            energyConsumerResultArr2 = energyConsumerResultArr;
            i6 = i5;
            length = i2;
            z = false;
            j5 = -1;
        }
        return energyConsumerDeltaData;
    }
}
