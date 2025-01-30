package com.android.server.net.watchlist;

import com.android.internal.util.HexDump;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class HarmfulDigests {
  public final Set mDigestSet;

  public HarmfulDigests(List list) {
    HashSet hashSet = new HashSet();
    int size = list.size();
    for (int i = 0; i < size; i++) {
      hashSet.add(HexDump.toHexString((byte[]) list.get(i)));
    }
    this.mDigestSet = Collections.unmodifiableSet(hashSet);
  }

  public boolean contains(byte[] bArr) {
    return this.mDigestSet.contains(HexDump.toHexString(bArr));
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    Iterator it = this.mDigestSet.iterator();
    while (it.hasNext()) {
      printWriter.println((String) it.next());
    }
    printWriter.println("");
  }
}
