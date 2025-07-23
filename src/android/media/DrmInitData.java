package android.media;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes2.dex */
public abstract class DrmInitData {
    @Deprecated
    public abstract SchemeInitData get(UUID uuid);

    public int getSchemeInitDataCount() {
        return 0;
    }

    DrmInitData() {
    }

    public SchemeInitData getSchemeInitDataAt(int i) {
        throw new IndexOutOfBoundsException();
    }

    public static final class SchemeInitData {
        public static final UUID UUID_NIL = new UUID(0, 0);
        public final byte[] data;
        public final String mimeType;
        public final UUID uuid;

        public SchemeInitData(UUID uuid, String str, byte[] bArr) {
            this.uuid = (UUID) Objects.requireNonNull(uuid);
            this.mimeType = (String) Objects.requireNonNull(str);
            this.data = (byte[]) Objects.requireNonNull(bArr);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SchemeInitData)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            SchemeInitData schemeInitData = (SchemeInitData) obj;
            return this.uuid.equals(schemeInitData.uuid) && this.mimeType.equals(schemeInitData.mimeType) && Arrays.equals(this.data, schemeInitData.data);
        }

        public int hashCode() {
            return this.uuid.hashCode() + ((this.mimeType.hashCode() + (Arrays.hashCode(this.data) * 31)) * 31);
        }
    }
}
