package com.android.server.usage;

import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.LongArrayQueue;
import android.util.TimeUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.XmlUtils;

/* loaded from: classes3.dex */
public class UserBroadcastEvents {
  public ArrayMap mBroadcastEvents = new ArrayMap();

  public ArraySet getBroadcastEvents(String str) {
    return (ArraySet) this.mBroadcastEvents.get(str);
  }

  public ArraySet getOrCreateBroadcastEvents(String str) {
    ArraySet arraySet = (ArraySet) this.mBroadcastEvents.get(str);
    if (arraySet != null) {
      return arraySet;
    }
    ArraySet arraySet2 = new ArraySet();
    this.mBroadcastEvents.put(str, arraySet2);
    return arraySet2;
  }

  public void onPackageRemoved(String str) {
    this.mBroadcastEvents.remove(str);
  }

  public void onUidRemoved(int i) {
    clear(i);
  }

  public void clear(int i) {
    for (int size = this.mBroadcastEvents.size() - 1; size >= 0; size--) {
      ArraySet arraySet = (ArraySet) this.mBroadcastEvents.valueAt(size);
      for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
        if (((BroadcastEvent) arraySet.valueAt(size2)).getSourceUid() == i) {
          arraySet.removeAt(size2);
        }
      }
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    for (int i = 0; i < this.mBroadcastEvents.size(); i++) {
      String str = (String) this.mBroadcastEvents.keyAt(i);
      ArraySet arraySet = (ArraySet) this.mBroadcastEvents.valueAt(i);
      indentingPrintWriter.println(str + XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      if (arraySet.size() == 0) {
        indentingPrintWriter.println("<empty>");
      } else {
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
          BroadcastEvent broadcastEvent = (BroadcastEvent) arraySet.valueAt(i2);
          indentingPrintWriter.println(broadcastEvent);
          indentingPrintWriter.increaseIndent();
          LongArrayQueue timestampsMs = broadcastEvent.getTimestampsMs();
          for (int i3 = 0; i3 < timestampsMs.size(); i3++) {
            if (i3 > 0) {
              indentingPrintWriter.print(',');
            }
            TimeUtils.formatDuration(timestampsMs.get(i3), indentingPrintWriter);
          }
          indentingPrintWriter.println();
          indentingPrintWriter.decreaseIndent();
        }
      }
      indentingPrintWriter.decreaseIndent();
    }
  }
}
