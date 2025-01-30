package com.android.server.autofill;

import android.util.ArraySet;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class HintsHelper {
  public static Set getHintsForSaveType(int i) {
    ArraySet arraySet = new ArraySet();
    if (i == 1) {
      arraySet.add("newUsername");
      arraySet.add("username");
      arraySet.add("newPassword");
      arraySet.add("password");
      return arraySet;
    }
    if (i != 2) {
      if (i != 4) {
        if (i == 8) {
          arraySet.add("newUsername");
          arraySet.add("username");
          return arraySet;
        }
        if (i == 16) {
          arraySet.add("emailAddress");
          return arraySet;
        }
        if (i != 32 && i != 64 && i != 128) {
          return arraySet;
        }
      }
      arraySet.add("creditCardExpirationDate");
      arraySet.add("creditCardExpirationDay");
      arraySet.add("creditCardExpirationMonth");
      arraySet.add("creditCardExpirationYear");
      arraySet.add("creditCardNumber");
      arraySet.add("creditCardSecurityCode");
      return arraySet;
    }
    arraySet.add("postalAddress");
    arraySet.add("aptNumber");
    arraySet.add("addressCountry");
    arraySet.add("dependentLocality");
    arraySet.add("extendedAddress");
    arraySet.add("extendedPostalCode");
    arraySet.add("addressLocality");
    arraySet.add("addressRegion");
    arraySet.add("streetAddress");
    arraySet.add("postalCode");
    return arraySet;
  }
}
