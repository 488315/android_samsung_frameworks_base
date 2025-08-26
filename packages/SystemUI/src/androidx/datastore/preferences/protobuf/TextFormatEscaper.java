package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
public final class TextFormatEscaper {

    /* renamed from: androidx.datastore.preferences.protobuf.TextFormatEscaper$1, reason: invalid class name */
    public class AnonymousClass1 {
        public final /* synthetic */ ByteString val$input;

        public AnonymousClass1(ByteString byteString) {
            this.val$input = byteString;
        }
    }

    private TextFormatEscaper() {
    }

    public static String escapeBytes(ByteString byteString) {
        ByteString byteString2 = new AnonymousClass1(byteString).val$input;
        StringBuilder sb = new StringBuilder(byteString2.size());
        for (int i = 0; i < byteString2.size(); i++) {
            byte bByteAt = byteString2.byteAt(i);
            if (bByteAt == 34) {
                sb.append("\\\"");
            } else if (bByteAt == 39) {
                sb.append("\\'");
            } else if (bByteAt != 92) {
                switch (bByteAt) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bByteAt < 32 || bByteAt > 126) {
                            sb.append('\\');
                            sb.append((char) (((bByteAt >>> 6) & 3) + 48));
                            sb.append((char) (((bByteAt >>> 3) & 7) + 48));
                            sb.append((char) ((bByteAt & 7) + 48));
                            break;
                        } else {
                            sb.append((char) bByteAt);
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
