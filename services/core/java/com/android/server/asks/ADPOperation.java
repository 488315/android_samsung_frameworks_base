package com.android.server.asks;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public abstract class ADPOperation {
  public static Pattern getRegexFromVType(int i) {
    return Pattern.compile(
        i != 0
            ? i != 5
                ? i != 2 ? i != 3 ? "" : "^(\\d?\\d{4})\\d{2}(\\d{3})\\z" : "^(\\d?\\d{6})\\d{3}\\z"
                : "^(\\d?\\d{9})\\z"
            : "^(\\d?\\d{4})\\d{5}\\z");
  }

  public static int getConvertedNumber(Pattern pattern, String str) {
    StringBuffer stringBuffer = new StringBuffer();
    Matcher matcher = pattern.matcher(str);
    if (matcher.find()) {
      for (int i = 1; i <= matcher.groupCount(); i++) {
        stringBuffer.append(matcher.group(i));
      }
    }
    return Integer.valueOf(stringBuffer.toString()).intValue();
  }

  public static boolean isGreaterOrEqual(int i, String str, String str2) {
    return isGreaterOrEqual(getRegexFromVType(i), str, str2);
  }

  public static boolean isGreaterOrEqual(String str, ADPContainer.ADPPolicy aDPPolicy) {
    if (aDPPolicy.getVersionType() == 1703114115) {
      if (aDPPolicy.getPattern() == null) {
        return false;
      }
      return isGreaterOrEqual(Pattern.compile(aDPPolicy.getFormat()), str, aDPPolicy.getHashCode());
    }
    return isGreaterOrEqual(aDPPolicy.getVersionType(), str, aDPPolicy.getHashCode());
  }

  public static boolean isGreaterOrEqual(Pattern pattern, String str, String str2) {
    try {
      return getConvertedNumber(pattern, str) >= getConvertedNumber(pattern, str2);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
  }

  public static Predicate isSameCategoryByHashCode(final String str) {
    return new Predicate() { // from class:
                             // com.android.server.asks.ADPOperation$$ExternalSyntheticLambda0
      @Override // java.util.function.Predicate
      public final boolean test(Object obj) {
        boolean lambda$isSameCategoryByHashCode$0;
        lambda$isSameCategoryByHashCode$0 =
            ADPOperation.lambda$isSameCategoryByHashCode$0(str, (ADPContainer.ADPPolicy) obj);
        return lambda$isSameCategoryByHashCode$0;
      }
    };
  }

  public static /* synthetic */ boolean lambda$isSameCategoryByHashCode$0(
      String str, ADPContainer.ADPPolicy aDPPolicy) {
    return aDPPolicy.findMatcherByHashCode(str);
  }

  public static List filterADPPolicy(List list, Predicate predicate) {
    return (List) list.stream().filter(predicate).collect(Collectors.toList());
  }
}
