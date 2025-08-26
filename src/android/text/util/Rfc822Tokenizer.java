package android.text.util;

import android.widget.MultiAutoCompleteTextView;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes4.dex */
public class Rfc822Tokenizer implements MultiAutoCompleteTextView.Tokenizer {
    public static void tokenize(CharSequence charSequence, Collection<Rfc822Token> collection) {
        char cCharAt;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        int length = charSequence.length();
        int i = 0;
        while (i < length) {
            char cCharAt2 = charSequence.charAt(i);
            if (cCharAt2 != ',' && cCharAt2 != ';') {
                if (cCharAt2 != '\"') {
                    if (cCharAt2 != '(') {
                        if (cCharAt2 == '<') {
                            while (true) {
                                i++;
                                if (i >= length) {
                                    break;
                                }
                                char cCharAt3 = charSequence.charAt(i);
                                if (cCharAt3 == '>') {
                                    break;
                                } else {
                                    sb2.append(cCharAt3);
                                }
                            }
                        } else if (cCharAt2 == ' ') {
                            sb.append((char) 0);
                        } else {
                            sb.append(cCharAt2);
                        }
                        i++;
                        break;
                        break;
                    }
                    i++;
                    int i2 = 1;
                    while (i < length && i2 > 0) {
                        char cCharAt4 = charSequence.charAt(i);
                        if (cCharAt4 == ')') {
                            if (i2 > 1) {
                                sb3.append(cCharAt4);
                            }
                            i2--;
                        } else if (cCharAt4 == '(') {
                            sb3.append(cCharAt4);
                            i2++;
                        } else if (cCharAt4 == '\\') {
                            int i3 = i + 1;
                            if (i3 < length) {
                                sb3.append(charSequence.charAt(i3));
                            }
                            i += 2;
                        } else {
                            sb3.append(cCharAt4);
                        }
                        i++;
                    }
                } else {
                    while (true) {
                        i++;
                        while (i < length) {
                            cCharAt = charSequence.charAt(i);
                            if (cCharAt == '\"') {
                                break;
                            }
                            if (cCharAt == '\\') {
                                int i4 = i + 1;
                                if (i4 < length) {
                                    sb.append(charSequence.charAt(i4));
                                }
                                i += 2;
                            }
                        }
                        sb.append(cCharAt);
                    }
                    i++;
                    break;
                }
            } else {
                do {
                    i++;
                    if (i >= length) {
                        break;
                    }
                } while (charSequence.charAt(i) == ' ');
                crunch(sb);
                if (sb2.length() > 0) {
                    collection.add(new Rfc822Token(sb.toString(), sb2.toString(), sb3.toString()));
                } else if (sb.length() > 0) {
                    collection.add(new Rfc822Token(null, sb.toString(), sb3.toString()));
                }
                sb.setLength(0);
                sb2.setLength(0);
                sb3.setLength(0);
            }
        }
        crunch(sb);
        if (sb2.length() > 0) {
            collection.add(new Rfc822Token(sb.toString(), sb2.toString(), sb3.toString()));
        } else if (sb.length() > 0) {
            collection.add(new Rfc822Token(null, sb.toString(), sb3.toString()));
        }
    }

    public static Rfc822Token[] tokenize(CharSequence charSequence) {
        ArrayList arrayList = new ArrayList();
        tokenize(charSequence, arrayList);
        return (Rfc822Token[]) arrayList.toArray(new Rfc822Token[arrayList.size()]);
    }

    private static void crunch(StringBuilder sb) {
        int length = sb.length();
        int i = 0;
        while (i < length) {
            if (sb.charAt(i) == 0) {
                if (i != 0 && i != length - 1) {
                    int i2 = i - 1;
                    if (sb.charAt(i2) != ' ' && sb.charAt(i2) != 0) {
                        int i3 = i + 1;
                        if (sb.charAt(i3) != ' ' && sb.charAt(i3) != 0) {
                            i = i3;
                        }
                    }
                }
                sb.deleteCharAt(i);
                length--;
            } else {
                i++;
            }
        }
        for (int i4 = 0; i4 < length; i4++) {
            if (sb.charAt(i4) == 0) {
                sb.setCharAt(i4, ' ');
            }
        }
    }

    @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
    public int findTokenStart(CharSequence charSequence, int i) {
        int iFindTokenEnd = 0;
        while (true) {
            int i2 = iFindTokenEnd;
            while (iFindTokenEnd < i) {
                iFindTokenEnd = findTokenEnd(charSequence, iFindTokenEnd);
                if (iFindTokenEnd < i) {
                    do {
                        iFindTokenEnd++;
                        if (iFindTokenEnd >= i) {
                            break;
                        }
                    } while (charSequence.charAt(iFindTokenEnd) == ' ');
                    if (iFindTokenEnd < i) {
                        break;
                    }
                }
            }
            return i2;
        }
    }

    @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
    public int findTokenEnd(CharSequence charSequence, int i) {
        int length = charSequence.length();
        while (i < length) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt != ',' && cCharAt != ';') {
                if (cCharAt != '\"') {
                    if (cCharAt != '(') {
                        if (cCharAt == '<') {
                            do {
                                i++;
                                if (i < length) {
                                }
                            } while (charSequence.charAt(i) != '>');
                        }
                        i++;
                        break;
                        break;
                    }
                    i++;
                    int i2 = 1;
                    while (i < length && i2 > 0) {
                        char cCharAt2 = charSequence.charAt(i);
                        if (cCharAt2 == ')') {
                            i2--;
                        } else if (cCharAt2 == '(') {
                            i2++;
                        } else if (cCharAt2 == '\\' && i + 1 < length) {
                            i += 2;
                        }
                        i++;
                    }
                } else {
                    while (true) {
                        i++;
                        while (i < length) {
                            char cCharAt3 = charSequence.charAt(i);
                            if (cCharAt3 == '\"') {
                                break;
                            }
                            if (cCharAt3 != '\\' || i + 1 >= length) {
                                break;
                            }
                            i += 2;
                        }
                    }
                    i++;
                    break;
                }
            } else {
                break;
            }
        }
        return i;
    }

    @Override // android.widget.MultiAutoCompleteTextView.Tokenizer
    public CharSequence terminateToken(CharSequence charSequence) {
        return ((Object) charSequence) + ", ";
    }
}
