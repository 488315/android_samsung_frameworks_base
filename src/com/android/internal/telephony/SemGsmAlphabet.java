package com.android.internal.telephony;

import android.text.format.DateFormat;
import android.util.SparseIntArray;
import com.android.internal.logging.nano.MetricsProto;

/* loaded from: classes4.dex */
public class SemGsmAlphabet {
    private static final SparseIntArray charToGsm;
    private static final SparseIntArray charToGsmExtended;
    private static final SparseIntArray gsmExtendedToChar;
    private static final SparseIntArray gsmToChar;
    private static int sGsmSpaceChar;

    public static char convertEachCharacter(char c) {
        return (GsmAlphabet.getEnabledSingleShiftTables().length + GsmAlphabet.getEnabledLockingShiftTables().length == 0 && charToGsm.get(c, -1) == -1 && charToGsmExtended.get(c, -1) == -1) ? convertNonGSMCharacter(c) : c;
    }

    private static char convertNonGSMCharacter(char c) {
        char c2 = c;
        System.out.println("temp char :" + ((int) c2));
        switch (c2) {
            case 192:
            case 193:
            case 194:
            case 195:
            case 256:
            case 260:
            case MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED /* 913 */:
                c2 = 'A';
                break;
            case 200:
            case 202:
            case 203:
            case 274:
            case 280:
            case 282:
            case 917:
                c2 = 'E';
                break;
            case 204:
            case 205:
            case 206:
            case 207:
            case 298:
            case 304:
            case 921:
                c2 = 'I';
                break;
            case 210:
            case 211:
            case 212:
            case 213:
            case 332:
            case 336:
            case 338:
            case MetricsProto.MetricsEvent.FIELD_QS_POSITION /* 927 */:
                c2 = 'O';
                break;
            case 217:
            case 218:
            case 219:
            case 362:
            case 366:
            case 368:
                c2 = 'U';
                break;
            case 221:
            case 376:
            case 933:
                c2 = 'Y';
                break;
            case 225:
            case 226:
            case 227:
            case 257:
            case 261:
                c2 = 'a';
                break;
            case 231:
            case 263:
            case 269:
                c2 = 'c';
                break;
            case 233:
            case 234:
            case 235:
            case 275:
            case 281:
            case 283:
                c2 = 'e';
                break;
            case 237:
            case 238:
            case 239:
            case 299:
            case 305:
                c2 = 'i';
                break;
            case 243:
            case 244:
            case 245:
            case 246:
            case 333:
            case 337:
            case 339:
                c2 = 'o';
                break;
            case 250:
            case 251:
            case 252:
            case 363:
            case 367:
            case 369:
                c2 = 'u';
                break;
            case 253:
            case 255:
                c2 = 'y';
                break;
            case 262:
            case 268:
                c2 = 'C';
                break;
            case 270:
                c2 = 'D';
                break;
            case 271:
                c2 = DateFormat.DATE;
                break;
            case 286:
                c2 = 'G';
                break;
            case 287:
                c2 = 'g';
                break;
            case 313:
            case 317:
            case 321:
                c2 = 'L';
                break;
            case 314:
            case 318:
            case 322:
                c2 = 'l';
                break;
            case 323:
            case 327:
            case MetricsProto.MetricsEvent.ACTION_QS_CLICK /* 925 */:
                c2 = 'N';
                break;
            case 324:
            case 328:
                c2 = 'n';
                break;
            case 340:
            case 344:
                c2 = 'R';
                break;
            case 341:
            case 345:
                c2 = 'r';
                break;
            case 346:
            case 350:
            case 352:
                c2 = 'S';
                break;
            case 347:
            case 351:
            case 353:
                c2 = 's';
                break;
            case 356:
            case 932:
                c2 = 'T';
                break;
            case 357:
                c2 = 't';
                break;
            case 377:
            case 379:
            case 381:
            case MetricsProto.MetricsEvent.AUTOFILL_DATA_SAVE_REQUEST /* 918 */:
                c2 = 'Z';
                break;
            case 378:
            case 380:
            case 382:
                c2 = 'z';
                break;
            case MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES /* 914 */:
                c2 = 'B';
                break;
            case MetricsProto.MetricsEvent.AUTOFILL_SESSION_FINISHED /* 919 */:
                c2 = 'H';
                break;
            case 922:
                c2 = 'K';
                break;
            case 924:
                c2 = DateFormat.MONTH;
                break;
            case MetricsProto.MetricsEvent.ACTION_QS_MORE_SETTINGS /* 929 */:
                c2 = 'P';
                break;
            case 935:
                c2 = 'X';
                break;
            default:
                if (c2 > 127 || c2 == '`') {
                    if (c2 != 128) {
                        c2 = 65279;
                        break;
                    } else {
                        c2 = ' ';
                        break;
                    }
                }
                break;
        }
        System.out.println("temp char :" + c2);
        return c2;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        charToGsm = sparseIntArray;
        gsmToChar = new SparseIntArray();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        charToGsmExtended = sparseIntArray2;
        gsmExtendedToChar = new SparseIntArray();
        sparseIntArray.put(64, 0);
        sparseIntArray.put(163, 1);
        sparseIntArray.put(36, 2);
        sparseIntArray.put(165, 3);
        sparseIntArray.put(232, 4);
        sparseIntArray.put(233, 5);
        sparseIntArray.put(249, 6);
        sparseIntArray.put(236, 7);
        sparseIntArray.put(242, 8);
        sparseIntArray.put(199, 9);
        sparseIntArray.put(10, 10);
        sparseIntArray.put(216, 11);
        sparseIntArray.put(248, 12);
        sparseIntArray.put(13, 13);
        sparseIntArray.put(197, 14);
        sparseIntArray.put(229, 15);
        sparseIntArray.put(MetricsProto.MetricsEvent.AUTOFILL_SAVE_UI, 16);
        sparseIntArray.put(95, 17);
        sparseIntArray.put(934, 18);
        sparseIntArray.put(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, 19);
        sparseIntArray.put(923, 20);
        sparseIntArray.put(MetricsProto.MetricsEvent.ACTION_TEXT_SELECTION_MENU_ITEM_ASSIST, 21);
        sparseIntArray.put(MetricsProto.MetricsEvent.FIELD_QS_VALUE, 22);
        sparseIntArray.put(MetricsProto.MetricsEvent.TEXT_SELECTION_MENU_ITEM_ASSIST, 23);
        sparseIntArray.put(931, 24);
        sparseIntArray.put(MetricsProto.MetricsEvent.METRICS_CHECKPOINT, 25);
        sparseIntArray.put(926, 26);
        sparseIntArray.put(65535, 27);
        sparseIntArray.put(198, 28);
        sparseIntArray.put(230, 29);
        sparseIntArray.put(223, 30);
        sparseIntArray.put(201, 31);
        sparseIntArray.put(32, 32);
        sparseIntArray.put(33, 33);
        sparseIntArray.put(34, 34);
        sparseIntArray.put(35, 35);
        sparseIntArray.put(164, 36);
        sparseIntArray.put(37, 37);
        sparseIntArray.put(38, 38);
        sparseIntArray.put(39, 39);
        sparseIntArray.put(40, 40);
        sparseIntArray.put(41, 41);
        sparseIntArray.put(42, 42);
        sparseIntArray.put(43, 43);
        sparseIntArray.put(44, 44);
        sparseIntArray.put(45, 45);
        sparseIntArray.put(46, 46);
        sparseIntArray.put(47, 47);
        sparseIntArray.put(48, 48);
        sparseIntArray.put(49, 49);
        sparseIntArray.put(50, 50);
        sparseIntArray.put(51, 51);
        sparseIntArray.put(52, 52);
        sparseIntArray.put(53, 53);
        sparseIntArray.put(54, 54);
        sparseIntArray.put(55, 55);
        sparseIntArray.put(56, 56);
        sparseIntArray.put(57, 57);
        sparseIntArray.put(58, 58);
        sparseIntArray.put(59, 59);
        sparseIntArray.put(60, 60);
        sparseIntArray.put(61, 61);
        sparseIntArray.put(62, 62);
        sparseIntArray.put(63, 63);
        sparseIntArray.put(161, 64);
        sparseIntArray.put(65, 65);
        sparseIntArray.put(66, 66);
        sparseIntArray.put(67, 67);
        sparseIntArray.put(68, 68);
        sparseIntArray.put(69, 69);
        sparseIntArray.put(70, 70);
        sparseIntArray.put(71, 71);
        sparseIntArray.put(72, 72);
        sparseIntArray.put(73, 73);
        sparseIntArray.put(74, 74);
        sparseIntArray.put(75, 75);
        sparseIntArray.put(76, 76);
        sparseIntArray.put(77, 77);
        sparseIntArray.put(78, 78);
        sparseIntArray.put(79, 79);
        sparseIntArray.put(80, 80);
        sparseIntArray.put(81, 81);
        sparseIntArray.put(82, 82);
        sparseIntArray.put(83, 83);
        sparseIntArray.put(84, 84);
        sparseIntArray.put(85, 85);
        sparseIntArray.put(86, 86);
        sparseIntArray.put(87, 87);
        sparseIntArray.put(88, 88);
        sparseIntArray.put(89, 89);
        sparseIntArray.put(90, 90);
        sparseIntArray.put(196, 91);
        sparseIntArray.put(214, 92);
        sparseIntArray.put(209, 93);
        sparseIntArray.put(220, 94);
        sparseIntArray.put(167, 95);
        sparseIntArray.put(191, 96);
        sparseIntArray.put(97, 97);
        sparseIntArray.put(98, 98);
        sparseIntArray.put(99, 99);
        sparseIntArray.put(100, 100);
        sparseIntArray.put(101, 101);
        sparseIntArray.put(102, 102);
        sparseIntArray.put(103, 103);
        sparseIntArray.put(104, 104);
        sparseIntArray.put(105, 105);
        sparseIntArray.put(106, 106);
        sparseIntArray.put(107, 107);
        sparseIntArray.put(108, 108);
        sparseIntArray.put(109, 109);
        sparseIntArray.put(110, 110);
        sparseIntArray.put(111, 111);
        sparseIntArray.put(112, 112);
        sparseIntArray.put(113, 113);
        sparseIntArray.put(114, 114);
        sparseIntArray.put(115, 115);
        sparseIntArray.put(116, 116);
        sparseIntArray.put(117, 117);
        sparseIntArray.put(118, 118);
        sparseIntArray.put(119, 119);
        sparseIntArray.put(120, 120);
        sparseIntArray.put(121, 121);
        sparseIntArray.put(122, 122);
        sparseIntArray.put(228, 123);
        sparseIntArray.put(246, 124);
        sparseIntArray.put(241, 125);
        sparseIntArray.put(252, 126);
        sparseIntArray.put(224, 127);
        sparseIntArray2.put(12, 10);
        sparseIntArray2.put(94, 20);
        sparseIntArray2.put(123, 40);
        sparseIntArray2.put(125, 41);
        sparseIntArray2.put(92, 47);
        sparseIntArray2.put(91, 60);
        sparseIntArray2.put(126, 61);
        sparseIntArray2.put(93, 62);
        sparseIntArray2.put(124, 64);
        sparseIntArray2.put(8364, 101);
        int size = sparseIntArray.size();
        for (int i = 0; i < size; i++) {
            SparseIntArray sparseIntArray3 = gsmToChar;
            SparseIntArray sparseIntArray4 = charToGsm;
            sparseIntArray3.put(sparseIntArray4.valueAt(i), sparseIntArray4.keyAt(i));
        }
        int size2 = charToGsmExtended.size();
        for (int i2 = 0; i2 < size2; i2++) {
            SparseIntArray sparseIntArray5 = gsmExtendedToChar;
            SparseIntArray sparseIntArray6 = charToGsmExtended;
            sparseIntArray5.put(sparseIntArray6.valueAt(i2), sparseIntArray6.keyAt(i2));
        }
        sGsmSpaceChar = charToGsm.get(32);
    }
}
