package android.internal.aconfig.storage;

/* loaded from: classes2.dex */
public class SipHasher13 {

    static class State {
        private long v0;
        private long v1;
        private long v2;
        private long v3;

        public State(long j, long j2) {
            this.v0 = 8317987319222330741L ^ j;
            this.v1 = 7237128888997146477L ^ j2;
            this.v2 = j ^ 7816392313619706465L;
            this.v3 = 8387220255154660723L ^ j2;
        }

        public void compress(long j) {
            this.v3 ^= j;
            cRounds();
            this.v0 = j ^ this.v0;
        }

        public long finish() {
            this.v2 ^= 255;
            dRounds();
            return ((this.v0 ^ this.v1) ^ this.v2) ^ this.v3;
        }

        private void cRounds() {
            long j = this.v0;
            long j2 = this.v1;
            this.v0 = j + j2;
            long jRotateLeft = Long.rotateLeft(j2, 13);
            long j3 = this.v0;
            this.v1 = jRotateLeft ^ j3;
            this.v0 = Long.rotateLeft(j3, 32);
            long j4 = this.v2;
            long j5 = this.v3;
            this.v2 = j4 + j5;
            long jRotateLeft2 = Long.rotateLeft(j5, 16) ^ this.v2;
            this.v3 = jRotateLeft2;
            this.v0 += jRotateLeft2;
            this.v3 = Long.rotateLeft(jRotateLeft2, 21) ^ this.v0;
            long j6 = this.v2;
            long j7 = this.v1;
            this.v2 = j6 + j7;
            long jRotateLeft3 = Long.rotateLeft(j7, 17);
            long j8 = this.v2;
            this.v1 = jRotateLeft3 ^ j8;
            this.v2 = Long.rotateLeft(j8, 32);
        }

        private void dRounds() {
            for (int i = 0; i < 3; i++) {
                long j = this.v0;
                long j2 = this.v1;
                this.v0 = j + j2;
                long jRotateLeft = Long.rotateLeft(j2, 13);
                long j3 = this.v0;
                this.v1 = jRotateLeft ^ j3;
                this.v0 = Long.rotateLeft(j3, 32);
                long j4 = this.v2;
                long j5 = this.v3;
                this.v2 = j4 + j5;
                long jRotateLeft2 = Long.rotateLeft(j5, 16) ^ this.v2;
                this.v3 = jRotateLeft2;
                this.v0 += jRotateLeft2;
                this.v3 = Long.rotateLeft(jRotateLeft2, 21) ^ this.v0;
                long j6 = this.v2;
                long j7 = this.v1;
                this.v2 = j6 + j7;
                long jRotateLeft3 = Long.rotateLeft(j7, 17);
                long j8 = this.v2;
                this.v1 = jRotateLeft3 ^ j8;
                this.v2 = Long.rotateLeft(j8, 32);
            }
        }
    }

    public static long hash(byte[] bArr) {
        long j = 0;
        State state = new State(0L, 0L);
        int length = bArr.length;
        int i = length & 7;
        int i2 = 0;
        while (i2 < length - i) {
            long jLoadLe = loadLe(bArr, i2, 8);
            i2 += 8;
            state.compress(jLoadLe);
        }
        long jLoadLe2 = loadLe(bArr, i2, i) | (255 << (i * 8));
        if (i == 7) {
            state.compress(jLoadLe2);
        } else {
            j = jLoadLe2;
        }
        state.compress(j | (((length + 1) & 255) << 56));
        return state.finish();
    }

    private static long loadLe(byte[] bArr, int i, int i2) {
        long j = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j |= (bArr[i3 + i] & 255) << (i3 * 8);
        }
        return j;
    }
}
