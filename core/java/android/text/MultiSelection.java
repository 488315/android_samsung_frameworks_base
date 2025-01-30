package android.text;

import android.graphics.Paint;

/* loaded from: classes3.dex */
public class MultiSelection {
  public static final Object CURRENT_SELECTION_START = new START();
  public static final Object CURRENT_SELECTION_END = new END();
  private static boolean mIsSelecting = false;
  private static boolean mIsTextViewHovered = false;
  private static boolean mNeedToScroll = false;
  private static int mHoveredIcon = -1;

  private MultiSelection() {}

  public static final int getSelectionStart(CharSequence text) {
    if (text instanceof Spanned) {
      return ((Spanned) text).getSpanStart(CURRENT_SELECTION_START);
    }
    return -1;
  }

  public static final int getSelectionEnd(CharSequence text) {
    if (text instanceof Spanned) {
      return ((Spanned) text).getSpanStart(CURRENT_SELECTION_END);
    }
    return -1;
  }

  /* JADX WARN: Code restructure failed: missing block: B:53:0x00d1, code lost:

     if (r4 == r2) goto L88;
  */
  /* JADX WARN: Code restructure failed: missing block: B:54:?, code lost:

     return;
  */
  /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static void setSelection(Spannable text, int start, int stop) {
    boolean needCheckPosition;
    int start2 = start;
    int stop2 = stop;
    if (start2 == stop2 || start2 < 0 || stop2 < 0) {
      return;
    }
    int ostart = getSelectionStart(text);
    int oend = getSelectionEnd(text);
    int len = text.length();
    if ((start2 > 0 && start2 < len) || (stop2 > 0 && stop2 < len)) {
      boolean needCheckPosition2 = false;
      if (start2 > 0 && start2 < len) {
        char startChar = text.charAt(start);
        if (Character.isLowSurrogate(startChar)) {
          start2++;
        } else if (TextUtils.isIndianChar(startChar)
            || TextUtils.isThaiChar(startChar)
            || TextUtils.isKhmerChar(startChar)
            || TextUtils.isMyanmarChar(startChar)
            || TextUtils.isLaoChar(startChar)) {
          needCheckPosition2 = true;
        }
      }
      if (stop2 > 0 && stop2 < len) {
        char stopChar = text.charAt(stop2);
        if (Character.isLowSurrogate(stopChar)) {
          stop2++;
          needCheckPosition = needCheckPosition2;
        } else if (!needCheckPosition2
            && (TextUtils.isIndianChar(stopChar)
                || TextUtils.isThaiChar(stopChar)
                || TextUtils.isKhmerChar(stopChar)
                || TextUtils.isMyanmarChar(stopChar)
                || TextUtils.isLaoChar(stopChar))) {
          needCheckPosition = true;
        }
        if (needCheckPosition) {
          float[] widths = new float[len];
          char[] chars = new char[len];
          Paint p = new Paint(1);
          TextUtils.getChars(text, 0, len, chars, 0);
          p.getTextRunAdvances(chars, 0, len, 0, len, false, widths, 0);
          while (start2 < len && widths[start2] == 0.0f && chars[start2] != '\n') {
            start2++;
          }
          while (stop2 < len && widths[stop2] == 0.0f && chars[stop2] != '\n') {
            stop2++;
          }
        }
      }
      needCheckPosition = needCheckPosition2;
      if (needCheckPosition) {}
    }
    START[] startSpans = (START[]) text.getSpans(0, text.length(), START.class);
    END[] endSpans = (END[]) text.getSpans(0, text.length(), END.class);
    for (int i = 0; i < startSpans.length; i++) {
      int starts = text.getSpanStart(startSpans[i]);
      int ends = text.getSpanStart(endSpans[i]);
      if ((starts <= start2 && start2 < ends) || (starts < stop2 && stop2 <= ends)) {
        text.removeSpan(startSpans[i]);
        text.removeSpan(endSpans[i]);
      }
    }
    text.setSpan(CURRENT_SELECTION_START, start2, start2, 546);
    text.setSpan(CURRENT_SELECTION_END, stop2, stop2, 34);
  }

  public static final void removeCurSelection(Spannable text) {
    text.removeSpan(CURRENT_SELECTION_START);
    text.removeSpan(CURRENT_SELECTION_END);
  }

  public static final void selectAll(Spannable text) {
    setSelection(text, 0, text.length());
  }

  /* JADX WARN: Multi-variable type inference failed */
  public static final void addMultiSelection(Spannable spannable, int i, int i2) {
    if (i < 0 || i2 < 0) {
      return;
    }
    START start = new START();
    END end = new END();
    spannable.setSpan(start, i, i, 546);
    spannable.setSpan(end, i2, i2, 34);
  }

  public static final boolean removeMultiSelection(Spannable text, int start, int stop) {
    START[] spansStarts = (START[]) text.getSpans(start, start, START.class);
    END[] spansEnds = (END[]) text.getSpans(stop, stop, END.class);
    boolean ret = true;
    if (spansStarts.length == 1) {
      text.removeSpan(spansStarts[0]);
    } else {
      ret = false;
    }
    if (spansEnds.length == 1) {
      text.removeSpan(spansEnds[0]);
      return ret;
    }
    return false;
  }

  public static final void clearMultiSelection(Spannable text) {
    START[] spansStarts = (START[]) text.getSpans(0, text.length(), START.class);
    END[] spansEnds = (END[]) text.getSpans(0, text.length(), END.class);
    for (int i = 0; i < spansStarts.length; i++) {
      text.removeSpan(spansStarts[i]);
      text.removeSpan(spansEnds[i]);
    }
  }

  public static final int[] getMultiSelectionStart(Spannable text) {
    START[] spans = (START[]) text.getSpans(0, text.length(), START.class);
    int[] starts = new int[spans.length];
    for (int i = 0; i < spans.length; i++) {
      starts[i] = text.getSpanStart(spans[i]);
    }
    return starts;
  }

  public static final int[] getMultiSelectionEnd(Spannable text) {
    END[] spans = (END[]) text.getSpans(0, text.length(), END.class);
    int[] ends = new int[spans.length];
    for (int i = 0; i < spans.length; i++) {
      ends[i] = text.getSpanStart(spans[i]);
    }
    return ends;
  }

  public static final int getMultiSelectionCount(Spannable text) {
    START[] spans = (START[]) text.getSpans(0, text.length(), START.class);
    return spans.length;
  }

  public static final void setIsMultiSelectingText(boolean bSelecting) {
    mIsSelecting = bSelecting;
  }

  public static final boolean getIsMultiSelectingText() {
    return mIsSelecting;
  }

  public static final void setTextViewHovered(boolean bSelecting) {
    setTextViewHovered(bSelecting, -1);
  }

  public static final void setTextViewHovered(boolean bSelecting, int type) {
    mIsTextViewHovered = bSelecting;
    mHoveredIcon = type;
  }

  public static final boolean isTextViewHovered() {
    return mIsTextViewHovered;
  }

  public static final int getHoveredIcon() {
    return mHoveredIcon;
  }

  public static final void setNeedToScroll(boolean bflag) {
    mNeedToScroll = bflag;
  }

  public static final boolean isNeedToScroll() {
    return mNeedToScroll;
  }

  private static final class START implements NoCopySpan {
    private START() {}
  }

  private static final class END implements NoCopySpan {
    private END() {}
  }
}
