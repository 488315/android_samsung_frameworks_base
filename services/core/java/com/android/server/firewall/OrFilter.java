package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes2.dex */
public class OrFilter extends FilterList {
  public static final FilterFactory FACTORY =
      new FilterFactory("or") { // from class: com.android.server.firewall.OrFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public Filter newFilter(XmlPullParser xmlPullParser) {
          return new OrFilter().readFromXml(xmlPullParser);
        }
      };

  @Override // com.android.server.firewall.Filter
  public boolean matches(
      IntentFirewall intentFirewall,
      ComponentName componentName,
      Intent intent,
      int i,
      int i2,
      String str,
      int i3) {
    for (int i4 = 0; i4 < this.children.size(); i4++) {
      if (((Filter) this.children.get(i4))
          .matches(intentFirewall, componentName, intent, i, i2, str, i3)) {
        return true;
      }
    }
    return false;
  }
}
