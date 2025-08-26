package android.database.sqlite;

import android.security.Credentials;
import android.telephony.PhoneNumberUtils;
import android.text.format.DateFormat;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import com.samsung.android.os.SemDvfsManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class SQLiteTokenizer {
    public static final int OPTION_NONE = 0;
    public static final int OPTION_TOKEN_ONLY = 1;

    private static boolean isAlpha(char c) {
        if ('a' > c || c > 'z') {
            return ('A' <= c && c <= 'Z') || c == '_';
        }
        return true;
    }

    private static boolean isNum(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isAlNum(char c) {
        return isAlpha(c) || isNum(c);
    }

    private static boolean isAnyOf(char c, String str) {
        return str.indexOf(c) >= 0;
    }

    private static IllegalArgumentException genException(String str, String str2) {
        throw new IllegalArgumentException(str + " in '" + str2 + "'");
    }

    private static char peek(String str, int i) {
        if (i < str.length()) {
            return str.charAt(i);
        }
        return (char) 0;
    }

    public static List<String> tokenize(String str, int i) {
        final ArrayList arrayList = new ArrayList();
        tokenize(str, i, new Consumer() { // from class: android.database.sqlite.SQLiteTokenizer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((String) obj);
            }
        });
        return arrayList;
    }

    public static void tokenize(String str, int i, Consumer<String> consumer) {
        if (str == null) {
            return;
        }
        int length = str.length();
        int iIndexOf = 0;
        while (iIndexOf < length) {
            char cPeek = peek(str, iIndexOf);
            if (isAlpha(cPeek)) {
                int i2 = iIndexOf + 1;
                while (isAlNum(peek(str, i2))) {
                    i2++;
                }
                consumer.accept(str.substring(iIndexOf, i2));
                iIndexOf = i2;
            } else if (isAnyOf(cPeek, "'\"`")) {
                int i3 = iIndexOf + 1;
                int i4 = i3;
                while (true) {
                    int iIndexOf2 = str.indexOf(cPeek, i4);
                    if (iIndexOf2 < 0) {
                        throw genException("Unterminated quote", str);
                    }
                    int i5 = iIndexOf2 + 1;
                    if (peek(str, i5) != cPeek) {
                        if (cPeek != '\'') {
                            String strSubstring = str.substring(i3, iIndexOf2);
                            if (strSubstring.indexOf(cPeek) >= 0) {
                                strSubstring = strSubstring.replaceAll(String.valueOf(cPeek) + cPeek, String.valueOf(cPeek));
                            }
                            consumer.accept(strSubstring);
                        } else {
                            i &= 1;
                            if (i != 0) {
                                throw genException("Non-token detected", str);
                            }
                        }
                        iIndexOf = i5;
                    } else {
                        i4 = iIndexOf2 + 2;
                    }
                }
            } else if (cPeek == '[') {
                int i6 = iIndexOf + 1;
                int iIndexOf3 = str.indexOf(93, i6);
                if (iIndexOf3 < 0) {
                    throw genException("Unterminated quote", str);
                }
                consumer.accept(str.substring(i6, iIndexOf3));
                iIndexOf = iIndexOf3 + 1;
            } else {
                i &= 1;
                if (i != 0) {
                    throw genException("Non-token detected", str);
                }
                if (cPeek == '-' && peek(str, iIndexOf + 1) == '-') {
                    iIndexOf = str.indexOf(10, iIndexOf + 2);
                    if (iIndexOf < 0) {
                        throw genException("Unterminated comment", str);
                    }
                } else if (cPeek == '/' && peek(str, iIndexOf + 1) == '*') {
                    int iIndexOf4 = str.indexOf("*/", iIndexOf + 2);
                    if (iIndexOf4 < 0) {
                        throw genException("Unterminated comment", str);
                    }
                    iIndexOf = iIndexOf4 + 2;
                } else if (cPeek == ';') {
                    throw genException("Semicolon is not allowed", str);
                }
                iIndexOf++;
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static boolean isKeyword(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        upperCase.hashCode();
        char c = 65535;
        switch (upperCase.hashCode()) {
            case -2137067054:
                if (upperCase.equals("IGNORE")) {
                    c = 0;
                    break;
                }
                break;
            case -2130463047:
                if (upperCase.equals("INSERT")) {
                    c = 1;
                    break;
                }
                break;
            case -2125979215:
                if (upperCase.equals("ISNULL")) {
                    c = 2;
                    break;
                }
                break;
            case -2032180703:
                if (upperCase.equals("DEFAULT")) {
                    c = 3;
                    break;
                }
                break;
            case -1986874255:
                if (upperCase.equals("NOCASE")) {
                    c = 4;
                    break;
                }
                break;
            case -1966450541:
                if (upperCase.equals("OFFSET")) {
                    c = 5;
                    break;
                }
                break;
            case -1953474717:
                if (upperCase.equals("OTHERS")) {
                    c = 6;
                    break;
                }
                break;
            case -1926899396:
                if (upperCase.equals("PRAGMA")) {
                    c = 7;
                    break;
                }
                break;
            case -1881469687:
                if (upperCase.equals("REGEXP")) {
                    c = '\b';
                    break;
                }
                break;
            case -1881265346:
                if (upperCase.equals("RENAME")) {
                    c = '\t';
                    break;
                }
                break;
            case -1852692228:
                if (upperCase.equals("SELECT")) {
                    c = '\n';
                    break;
                }
                break;
            case -1848073207:
                if (upperCase.equals("NATURAL")) {
                    c = 11;
                    break;
                }
                break;
            case -1787199535:
                if (upperCase.equals("UNIQUE")) {
                    c = '\f';
                    break;
                }
                break;
            case -1785516855:
                if (upperCase.equals("UPDATE")) {
                    c = '\r';
                    break;
                }
                break;
            case -1770751051:
                if (upperCase.equals("VACUUM")) {
                    c = 14;
                    break;
                }
                break;
            case -1770483422:
                if (upperCase.equals("VALUES")) {
                    c = 15;
                    break;
                }
                break;
            case -1757367375:
                if (upperCase.equals("INITIALLY")) {
                    c = 16;
                    break;
                }
                break;
            case -1734422544:
                if (upperCase.equals("WINDOW")) {
                    c = 17;
                    break;
                }
                break;
            case -1722875525:
                if (upperCase.equals("DATABASE")) {
                    c = 18;
                    break;
                }
                break;
            case -1633692463:
                if (upperCase.equals("INDEXED")) {
                    c = 19;
                    break;
                }
                break;
            case -1619411166:
                if (upperCase.equals("INSTEAD")) {
                    c = 20;
                    break;
                }
                break;
            case -1447660627:
                if (upperCase.equals("NOTHING")) {
                    c = 21;
                    break;
                }
                break;
            case -1447470406:
                if (upperCase.equals("NOTNULL")) {
                    c = 22;
                    break;
                }
                break;
            case -1322009984:
                if (upperCase.equals("AUTOINCREMENT")) {
                    c = 23;
                    break;
                }
                break;
            case -1308685805:
                if (upperCase.equals("SAVEPOINT")) {
                    c = 24;
                    break;
                }
                break;
            case -1005357825:
                if (upperCase.equals("INTERSECT")) {
                    c = 25;
                    break;
                }
                break;
            case -742456719:
                if (upperCase.equals("FOLLOWING")) {
                    c = 26;
                    break;
                }
                break;
            case -603166278:
                if (upperCase.equals("EXCLUDE")) {
                    c = 27;
                    break;
                }
                break;
            case -591179561:
                if (upperCase.equals("EXPLAIN")) {
                    c = 28;
                    break;
                }
                break;
            case -479705388:
                if (upperCase.equals("CURRENT_DATE")) {
                    c = 29;
                    break;
                }
                break;
            case -479221261:
                if (upperCase.equals("CURRENT_TIME")) {
                    c = 30;
                    break;
                }
                break;
            case -383989871:
                if (upperCase.equals("IMMEDIATE")) {
                    c = 31;
                    break;
                }
                break;
            case -342592494:
                if (upperCase.equals("RECURSIVE")) {
                    c = ' ';
                    break;
                }
                break;
            case -341909096:
                if (upperCase.equals("TRIGGER")) {
                    c = '!';
                    break;
                }
                break;
            case -262905456:
                if (upperCase.equals("CURRENT_TIMESTAMP")) {
                    c = '\"';
                    break;
                }
                break;
            case -146347732:
                if (upperCase.equals("ANALYZE")) {
                    c = '#';
                    break;
                }
                break;
            case -760130:
                if (upperCase.equals("TRANSACTION")) {
                    c = '$';
                    break;
                }
                break;
            case 2098:
                if (upperCase.equals("AS")) {
                    c = '%';
                    break;
                }
                break;
            case 2135:
                if (upperCase.equals("BY")) {
                    c = '&';
                    break;
                }
                break;
            case 2187:
                if (upperCase.equals("DO")) {
                    c = DateFormat.QUOTE;
                    break;
                }
                break;
            case 2333:
                if (upperCase.equals("IF")) {
                    c = '(';
                    break;
                }
                break;
            case SemDvfsManager.HINT_TYPE_SAMSUNG_KEYBOARD_RE_ENTER_SHOW /* 2341 */:
                if (upperCase.equals("IN")) {
                    c = ')';
                    break;
                }
                break;
            case 2346:
                if (upperCase.equals("IS")) {
                    c = '*';
                    break;
                }
                break;
            case 2497:
                if (upperCase.equals("NO")) {
                    c = '+';
                    break;
                }
                break;
            case 2519:
                if (upperCase.equals("OF")) {
                    c = ',';
                    break;
                }
                break;
            case 2527:
                if (upperCase.equals("ON")) {
                    c = '-';
                    break;
                }
                break;
            case 2531:
                if (upperCase.equals("OR")) {
                    c = '.';
                    break;
                }
                break;
            case 2683:
                if (upperCase.equals("TO")) {
                    c = '/';
                    break;
                }
                break;
            case 64641:
                if (upperCase.equals("ADD")) {
                    c = '0';
                    break;
                }
                break;
            case 64897:
                if (upperCase.equals("ALL")) {
                    c = '1';
                    break;
                }
                break;
            case 64951:
                if (upperCase.equals("AND")) {
                    c = '2';
                    break;
                }
                break;
            case 65105:
                if (upperCase.equals("ASC")) {
                    c = '3';
                    break;
                }
                break;
            case 68795:
                if (upperCase.equals("END")) {
                    c = '4';
                    break;
                }
                break;
            case 69801:
                if (upperCase.equals("FOR")) {
                    c = '5';
                    break;
                }
                break;
            case 74303:
                if (upperCase.equals(Credentials.EXTRA_PUBLIC_KEY)) {
                    c = '6';
                    break;
                }
                break;
            case 77491:
                if (upperCase.equals("NOT")) {
                    c = '7';
                    break;
                }
                break;
            case 81338:
                if (upperCase.equals("ROW")) {
                    c = '8';
                    break;
                }
                break;
            case 81986:
                if (upperCase.equals("SET")) {
                    c = '9';
                    break;
                }
                break;
            case 2061104:
                if (upperCase.equals("CASE")) {
                    c = ShortcutConstants.SERVICES_SEPARATOR;
                    break;
                }
                break;
            case 2061119:
                if (upperCase.equals("CAST")) {
                    c = ';';
                    break;
                }
                break;
            case 2094737:
                if (upperCase.equals("DESC")) {
                    c = '<';
                    break;
                }
                break;
            case 2107119:
                if (upperCase.equals("DROP")) {
                    c = '=';
                    break;
                }
                break;
            case 2120193:
                if (upperCase.equals("EACH")) {
                    c = '>';
                    break;
                }
                break;
            case 2131257:
                if (upperCase.equals("ELSE")) {
                    c = '?';
                    break;
                }
                break;
            case 2150174:
                if (upperCase.equals("FAIL")) {
                    c = '@';
                    break;
                }
                break;
            case 2166698:
                if (upperCase.equals("FROM")) {
                    c = DateFormat.CAPITAL_AM_PM;
                    break;
                }
                break;
            case 2169487:
                if (upperCase.equals("FULL")) {
                    c = 'B';
                    break;
                }
                break;
            case 2190712:
                if (upperCase.equals("GLOB")) {
                    c = 'C';
                    break;
                }
                break;
            case 2252384:
                if (upperCase.equals("INTO")) {
                    c = 'D';
                    break;
                }
                break;
            case 2282794:
                if (upperCase.equals("JOIN")) {
                    c = DateFormat.DAY;
                    break;
                }
                break;
            case 2332679:
                if (upperCase.equals("LEFT")) {
                    c = 'F';
                    break;
                }
                break;
            case 2336663:
                if (upperCase.equals("LIKE")) {
                    c = 'G';
                    break;
                }
                break;
            case 2407815:
                if (upperCase.equals("NULL")) {
                    c = 'H';
                    break;
                }
                break;
            case 2438356:
                if (upperCase.equals("OVER")) {
                    c = 'I';
                    break;
                }
                break;
            case 2458409:
                if (upperCase.equals("PLAN")) {
                    c = 'J';
                    break;
                }
                break;
            case 2521561:
                if (upperCase.equals("ROWS")) {
                    c = 'K';
                    break;
                }
                break;
            case 2571220:
                if (upperCase.equals("TEMP")) {
                    c = DateFormat.STANDALONE_MONTH;
                    break;
                }
                break;
            case 2573853:
                if (upperCase.equals("THEN")) {
                    c = DateFormat.MONTH;
                    break;
                }
                break;
            case 2574819:
                if (upperCase.equals("TIES")) {
                    c = PhoneNumberUtils.WILD;
                    break;
                }
                break;
            case 2634405:
                if (upperCase.equals("VIEW")) {
                    c = 'O';
                    break;
                }
                break;
            case 2663226:
                if (upperCase.equals("WHEN")) {
                    c = 'P';
                    break;
                }
                break;
            case 2664646:
                if (upperCase.equals("WITH")) {
                    c = 'Q';
                    break;
                }
                break;
            case 40307892:
                if (upperCase.equals("FOREIGN")) {
                    c = 'R';
                    break;
                }
                break;
            case 62073616:
                if (upperCase.equals("ABORT")) {
                    c = 'S';
                    break;
                }
                break;
            case 62197180:
                if (upperCase.equals("AFTER")) {
                    c = 'T';
                    break;
                }
                break;
            case 62375926:
                if (upperCase.equals("ALTER")) {
                    c = 'U';
                    break;
                }
                break;
            case 63078537:
                if (upperCase.equals("BEGIN")) {
                    c = 'V';
                    break;
                }
                break;
            case 64089320:
                if (upperCase.equals("CHECK")) {
                    c = 'W';
                    break;
                }
                break;
            case 64397344:
                if (upperCase.equals("CROSS")) {
                    c = 'X';
                    break;
                }
                break;
            case 68091487:
                if (upperCase.equals("GROUP")) {
                    c = 'Y';
                    break;
                }
                break;
            case 69808306:
                if (upperCase.equals("INDEX")) {
                    c = 'Z';
                    break;
                }
                break;
            case 69817910:
                if (upperCase.equals("INNER")) {
                    c = '[';
                    break;
                }
                break;
            case 72438683:
                if (upperCase.equals("LIMIT")) {
                    c = '\\';
                    break;
                }
                break;
            case 73130405:
                if (upperCase.equals("MATCH")) {
                    c = ']';
                    break;
                }
                break;
            case 75468590:
                if (upperCase.equals("ORDER")) {
                    c = '^';
                    break;
                }
                break;
            case 75573339:
                if (upperCase.equals("OUTER")) {
                    c = '_';
                    break;
                }
                break;
            case 77406376:
                if (upperCase.equals("QUERY")) {
                    c = '`';
                    break;
                }
                break;
            case 77737932:
                if (upperCase.equals("RAISE")) {
                    c = DateFormat.AM_PM;
                    break;
                }
                break;
            case 77742365:
                if (upperCase.equals("RANGE")) {
                    c = 'b';
                    break;
                }
                break;
            case 77974012:
                if (upperCase.equals("RIGHT")) {
                    c = 'c';
                    break;
                }
                break;
            case 78312308:
                if (upperCase.equals("RTRIM")) {
                    c = DateFormat.DATE;
                    break;
                }
                break;
            case 79578030:
                if (upperCase.equals("TABLE")) {
                    c = 'e';
                    break;
                }
                break;
            case 80895663:
                if (upperCase.equals("UNION")) {
                    c = 'f';
                    break;
                }
                break;
            case 81044580:
                if (upperCase.equals("USING")) {
                    c = 'g';
                    break;
                }
                break;
            case 82560199:
                if (upperCase.equals("WHERE")) {
                    c = DateFormat.HOUR;
                    break;
                }
                break;
            case 178245246:
                if (upperCase.equals("EXCLUSIVE")) {
                    c = 'i';
                    break;
                }
                break;
            case 202578898:
                if (upperCase.equals("CONFLICT")) {
                    c = 'j';
                    break;
                }
                break;
            case 273740228:
                if (upperCase.equals("UNBOUNDED")) {
                    c = DateFormat.HOUR_OF_DAY;
                    break;
                }
                break;
            case 294715869:
                if (upperCase.equals("CONSTRAINT")) {
                    c = 'l';
                    break;
                }
                break;
            case 337882266:
                if (upperCase.equals("DEFERRABLE")) {
                    c = DateFormat.MINUTE;
                    break;
                }
                break;
            case 403216866:
                if (upperCase.equals("PRIMARY")) {
                    c = 'n';
                    break;
                }
                break;
            case 446081724:
                if (upperCase.equals("RESTRICT")) {
                    c = 'o';
                    break;
                }
                break;
            case 476614193:
                if (upperCase.equals("TEMPORARY")) {
                    c = 'p';
                    break;
                }
                break;
            case 501348328:
                if (upperCase.equals("BETWEEN")) {
                    c = 'q';
                    break;
                }
                break;
            case 522907364:
                if (upperCase.equals("ROLLBACK")) {
                    c = 'r';
                    break;
                }
                break;
            case 986784458:
                if (upperCase.equals("PARTITION")) {
                    c = 's';
                    break;
                }
                break;
            case 1071324924:
                if (upperCase.equals("DISTINCT")) {
                    c = 't';
                    break;
                }
                break;
            case 1184148203:
                if (upperCase.equals("VIRTUAL")) {
                    c = 'u';
                    break;
                }
                break;
            case 1272812180:
                if (upperCase.equals("CASCADE")) {
                    c = 'v';
                    break;
                }
                break;
            case 1406276771:
                if (upperCase.equals("PRECEDING")) {
                    c = 'w';
                    break;
                }
                break;
            case 1430517727:
                if (upperCase.equals("DEFERRED")) {
                    c = EpicenterTranslateClipReveal.StateProperty.TARGET_X;
                    break;
                }
                break;
            case 1667424262:
                if (upperCase.equals("COLLATE")) {
                    c = 'y';
                    break;
                }
                break;
            case 1806077535:
                if (upperCase.equals("REINDEX")) {
                    c = DateFormat.TIME_ZONE;
                    break;
                }
                break;
            case 1808577511:
                if (upperCase.equals("RELEASE")) {
                    c = '{';
                    break;
                }
                break;
            case 1812479636:
                if (upperCase.equals("REPLACE")) {
                    c = '|';
                    break;
                }
                break;
            case 1844922713:
                if (upperCase.equals("CURRENT")) {
                    c = '}';
                    break;
                }
                break;
            case 1870042760:
                if (upperCase.equals("REFERENCES")) {
                    c = '~';
                    break;
                }
                break;
            case 1925345846:
                if (upperCase.equals("ACTION")) {
                    c = 127;
                    break;
                }
                break;
            case 1941037637:
                if (upperCase.equals("ATTACH")) {
                    c = 128;
                    break;
                }
                break;
            case 1955410815:
                if (upperCase.equals("BEFORE")) {
                    c = 129;
                    break;
                }
                break;
            case 1959329793:
                if (upperCase.equals("BINARY")) {
                    c = 130;
                    break;
                }
                break;
            case 1993459542:
                if (upperCase.equals("COLUMN")) {
                    c = 131;
                    break;
                }
                break;
            case 1993481527:
                if (upperCase.equals("COMMIT")) {
                    c = 132;
                    break;
                }
                break;
            case 1996002556:
                if (upperCase.equals("CREATE")) {
                    c = 133;
                    break;
                }
                break;
            case 2012838315:
                if (upperCase.equals(SQLiteDatabase.JOURNAL_MODE_DELETE)) {
                    c = 134;
                    break;
                }
                break;
            case 2013072275:
                if (upperCase.equals("DETACH")) {
                    c = 135;
                    break;
                }
                break;
            case 2054124673:
                if (upperCase.equals("ESCAPE")) {
                    c = 136;
                    break;
                }
                break;
            case 2058746137:
                if (upperCase.equals("EXCEPT")) {
                    c = 137;
                    break;
                }
                break;
            case 2058938460:
                if (upperCase.equals("EXISTS")) {
                    c = 138;
                    break;
                }
                break;
            case 2073136296:
                if (upperCase.equals("WITHOUT")) {
                    c = 139;
                    break;
                }
                break;
            case 2073804664:
                if (upperCase.equals("FILTER")) {
                    c = 140;
                    break;
                }
                break;
            case 2110836180:
                if (upperCase.equals("GROUPS")) {
                    c = 141;
                    break;
                }
                break;
            case 2123962405:
                if (upperCase.equals("HAVING")) {
                    c = 142;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '{':
            case '|':
            case '}':
            case '~':
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
                return true;
            default:
                return false;
        }
    }

    public static boolean isFunction(String str) {
        String lowerCase = str.toLowerCase(Locale.US);
        lowerCase.hashCode();
        switch (lowerCase) {
            case "ifnull":
            case "length":
            case "likely":
            case "nullif":
            case "coalesce":
            case "random":
            case "substr":
            case "typeof":
            case "group_concat":
            case "likelihood":
            case "unicode":
            case "unlikely":
            case "abs":
            case "avg":
            case "hex":
            case "max":
            case "min":
            case "sum":
            case "char":
            case "glob":
            case "like":
            case "trim":
            case "count":
            case "instr":
            case "lower":
            case "ltrim":
            case "round":
            case "rtrim":
            case "total":
            case "upper":
            case "randomblob":
            case "zeroblob":
            case "replace":
                return true;
            default:
                return false;
        }
    }

    public static boolean isType(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        upperCase.hashCode();
        switch (upperCase) {
            case "DECIMAL":
            case "DATETIME":
            case "INTEGER":
            case "NUMERIC":
            case "TINYINT":
            case "NVARCHAR":
            case "INT":
            case "BLOB":
            case "CLOB":
            case "DATE":
            case "INT2":
            case "INT8":
            case "REAL":
            case "TEXT":
            case "CHARACTER":
            case "FLOAT":
            case "NCHAR":
            case "SMALLINT":
            case "MEDIUMINT":
            case "BOOLEAN":
            case "VARCHAR":
            case "BIGINT":
            case "DOUBLE":
                return true;
            default:
                return false;
        }
    }
}
