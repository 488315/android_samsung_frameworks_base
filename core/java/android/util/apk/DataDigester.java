package android.util.apk;

import java.nio.ByteBuffer;
import java.security.DigestException;

/* loaded from: classes4.dex */
interface DataDigester {
  void consume(ByteBuffer byteBuffer) throws DigestException;
}
