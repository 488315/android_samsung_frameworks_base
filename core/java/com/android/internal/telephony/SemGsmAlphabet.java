package com.android.internal.telephony;

import android.telephony.PhoneNumberUtils;
import android.text.format.DateFormat;
import android.util.SparseIntArray;
import com.android.internal.logging.nano.MetricsProto;

/* loaded from: classes5.dex */
public class SemGsmAlphabet {
    private static int sGsmSpaceChar;
    private static final SparseIntArray charToGsm = new SparseIntArray();
    private static final SparseIntArray gsmToChar = new SparseIntArray();
    private static final SparseIntArray charToGsmExtended = new SparseIntArray();
    private static final SparseIntArray gsmExtendedToChar = new SparseIntArray();

    public static char convertEachCharacter(char c) {
        if (GsmAlphabet.getEnabledSingleShiftTables().length + GsmAlphabet.getEnabledLockingShiftTables().length != 0) {
            return c;
        }
        if (charToGsm.get(c, -1) != -1) {
            return c;
        }
        if (charToGsmExtended.get(c, -1) != -1) {
            return c;
        }
        char ret = convertNonGSMCharacter(c);
        return ret;
    }

    private static char convertNonGSMCharacter(char c) {
        char temp = c;
        System.out.println("temp char :" + ((int) c));
        switch (temp) {
            case 192:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case 193:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case 194:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case 195:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case 200:
                temp = DateFormat.DAY;
                break;
            case 202:
                temp = DateFormat.DAY;
                break;
            case 203:
                temp = DateFormat.DAY;
                break;
            case 204:
                temp = 'I';
                break;
            case 205:
                temp = 'I';
                break;
            case 206:
                temp = 'I';
                break;
            case 207:
                temp = 'I';
                break;
            case 210:
                temp = 'O';
                break;
            case 211:
                temp = 'O';
                break;
            case 212:
                temp = 'O';
                break;
            case 213:
                temp = 'O';
                break;
            case 217:
                temp = 'U';
                break;
            case 218:
                temp = 'U';
                break;
            case 219:
                temp = 'U';
                break;
            case 221:
                temp = 'Y';
                break;
            case 225:
                temp = DateFormat.AM_PM;
                break;
            case 226:
                temp = DateFormat.AM_PM;
                break;
            case 227:
                temp = DateFormat.AM_PM;
                break;
            case 231:
                temp = 'c';
                break;
            case 233:
                temp = 'e';
                break;
            case 234:
                temp = 'e';
                break;
            case 235:
                temp = 'e';
                break;
            case 237:
                temp = 'i';
                break;
            case 238:
                temp = 'i';
                break;
            case 239:
                temp = 'i';
                break;
            case 243:
                temp = 'o';
                break;
            case 244:
                temp = 'o';
                break;
            case 245:
                temp = 'o';
                break;
            case 246:
                temp = 'o';
                break;
            case 250:
                temp = 'u';
                break;
            case 251:
                temp = 'u';
                break;
            case 252:
                temp = 'u';
                break;
            case 253:
                temp = 'y';
                break;
            case 255:
                temp = 'y';
                break;
            case 256:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case 257:
                temp = DateFormat.AM_PM;
                break;
            case 260:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case 261:
                temp = DateFormat.AM_PM;
                break;
            case 262:
                temp = 'C';
                break;
            case 263:
                temp = 'c';
                break;
            case 268:
                temp = 'C';
                break;
            case 269:
                temp = 'c';
                break;
            case 270:
                temp = 'D';
                break;
            case 271:
                temp = DateFormat.DATE;
                break;
            case 274:
                temp = DateFormat.DAY;
                break;
            case 275:
                temp = 'e';
                break;
            case 280:
                temp = DateFormat.DAY;
                break;
            case 281:
                temp = 'e';
                break;
            case 282:
                temp = DateFormat.DAY;
                break;
            case 283:
                temp = 'e';
                break;
            case 286:
                temp = 'G';
                break;
            case 287:
                temp = 'g';
                break;
            case 298:
                temp = 'I';
                break;
            case 299:
                temp = 'i';
                break;
            case 304:
                temp = 'I';
                break;
            case 305:
                temp = 'i';
                break;
            case 313:
                temp = DateFormat.STANDALONE_MONTH;
                break;
            case 314:
                temp = 'l';
                break;
            case 317:
                temp = DateFormat.STANDALONE_MONTH;
                break;
            case 318:
                temp = 'l';
                break;
            case 321:
                temp = DateFormat.STANDALONE_MONTH;
                break;
            case 322:
                temp = 'l';
                break;
            case 323:
                temp = PhoneNumberUtils.WILD;
                break;
            case 324:
                temp = 'n';
                break;
            case 327:
                temp = PhoneNumberUtils.WILD;
                break;
            case 328:
                temp = 'n';
                break;
            case 332:
                temp = 'O';
                break;
            case 333:
                temp = 'o';
                break;
            case 336:
                temp = 'O';
                break;
            case 337:
                temp = 'o';
                break;
            case 338:
                temp = 'O';
                break;
            case 339:
                temp = 'o';
                break;
            case 340:
                temp = 'R';
                break;
            case 341:
                temp = 'r';
                break;
            case 344:
                temp = 'R';
                break;
            case 345:
                temp = 'r';
                break;
            case 346:
                temp = 'S';
                break;
            case 347:
                temp = 's';
                break;
            case 350:
                temp = 'S';
                break;
            case 351:
                temp = 's';
                break;
            case 352:
                temp = 'S';
                break;
            case 353:
                temp = 's';
                break;
            case 356:
                temp = 'T';
                break;
            case 357:
                temp = 't';
                break;
            case 362:
                temp = 'U';
                break;
            case 363:
                temp = 'u';
                break;
            case 366:
                temp = 'U';
                break;
            case 367:
                temp = 'u';
                break;
            case 368:
                temp = 'U';
                break;
            case 369:
                temp = 'u';
                break;
            case 376:
                temp = 'Y';
                break;
            case 377:
                temp = 'Z';
                break;
            case 378:
                temp = DateFormat.TIME_ZONE;
                break;
            case 379:
                temp = 'Z';
                break;
            case 380:
                temp = DateFormat.TIME_ZONE;
                break;
            case 381:
                temp = 'Z';
                break;
            case 382:
                temp = DateFormat.TIME_ZONE;
                break;
            case MetricsProto.MetricsEvent.AUTOFILL_DATASET_APPLIED /* 913 */:
                temp = DateFormat.CAPITAL_AM_PM;
                break;
            case MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VALUES /* 914 */:
                temp = 'B';
                break;
            case MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_IDS /* 917 */:
                temp = DateFormat.DAY;
                break;
            case MetricsProto.MetricsEvent.AUTOFILL_DATA_SAVE_REQUEST /* 918 */:
                temp = 'Z';
                break;
            case MetricsProto.MetricsEvent.AUTOFILL_SESSION_FINISHED /* 919 */:
                temp = 'H';
                break;
            case 921:
                temp = 'I';
                break;
            case 922:
                temp = 'K';
                break;
            case 924:
                temp = DateFormat.MONTH;
                break;
            case MetricsProto.MetricsEvent.ACTION_QS_CLICK /* 925 */:
                temp = PhoneNumberUtils.WILD;
                break;
            case MetricsProto.MetricsEvent.FIELD_QS_POSITION /* 927 */:
                temp = 'O';
                break;
            case MetricsProto.MetricsEvent.ACTION_QS_MORE_SETTINGS /* 929 */:
                temp = 'P';
                break;
            case MetricsProto.MetricsEvent.FIELD_FLAGS /* 932 */:
                temp = 'T';
                break;
            case MetricsProto.MetricsEvent.FIELD_NAV_ACTION /* 933 */:
                temp = 'Y';
                break;
            case 935:
                temp = 'X';
                break;
            default:
                if (temp > 127 || temp == '`') {
                    if (temp == 128) {
                        temp = ' ';
                        break;
                    } else {
                        temp = 65279;
                        break;
                    }
                }
                break;
        }
        System.out.println("temp char :" + temp);
        return temp;
    }

    static {
        int i = 0 + 1;
        charToGsm.put(64, 0);
        int i2 = i + 1;
        charToGsm.put(163, i);
        int i3 = i2 + 1;
        charToGsm.put(36, i2);
        int i4 = i3 + 1;
        charToGsm.put(165, i3);
        int i5 = i4 + 1;
        charToGsm.put(232, i4);
        int i6 = i5 + 1;
        charToGsm.put(233, i5);
        int i7 = i6 + 1;
        charToGsm.put(249, i6);
        int i8 = i7 + 1;
        charToGsm.put(236, i7);
        int i9 = i8 + 1;
        charToGsm.put(242, i8);
        int i10 = i9 + 1;
        charToGsm.put(199, i9);
        int i11 = i10 + 1;
        charToGsm.put(10, i10);
        int i12 = i11 + 1;
        charToGsm.put(216, i11);
        int i13 = i12 + 1;
        charToGsm.put(248, i12);
        int i14 = i13 + 1;
        charToGsm.put(13, i13);
        int i15 = i14 + 1;
        charToGsm.put(197, i14);
        int i16 = i15 + 1;
        charToGsm.put(229, i15);
        int i17 = i16 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.AUTOFILL_SAVE_UI, i16);
        int i18 = i17 + 1;
        charToGsm.put(95, i17);
        int i19 = i18 + 1;
        charToGsm.put(934, i18);
        int i20 = i19 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.FIELD_AUTOFILL_NUM_VIEWS_FILLED, i19);
        int i21 = i20 + 1;
        charToGsm.put(923, i20);
        int i22 = i21 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.ACTION_TEXT_SELECTION_MENU_ITEM_ASSIST, i21);
        int i23 = i22 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.FIELD_QS_VALUE, i22);
        int i24 = i23 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.TEXT_SELECTION_MENU_ITEM_ASSIST, i23);
        int i25 = i24 + 1;
        charToGsm.put(931, i24);
        int i26 = i25 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.METRICS_CHECKPOINT, i25);
        int i27 = i26 + 1;
        charToGsm.put(MetricsProto.MetricsEvent.ACTION_QS_SECONDARY_CLICK, i26);
        int i28 = i27 + 1;
        charToGsm.put(65535, i27);
        int i29 = i28 + 1;
        charToGsm.put(198, i28);
        int i30 = i29 + 1;
        charToGsm.put(230, i29);
        int i31 = i30 + 1;
        charToGsm.put(223, i30);
        int i32 = i31 + 1;
        charToGsm.put(201, i31);
        int i33 = i32 + 1;
        charToGsm.put(32, i32);
        int i34 = i33 + 1;
        charToGsm.put(33, i33);
        int i35 = i34 + 1;
        charToGsm.put(34, i34);
        int i36 = i35 + 1;
        charToGsm.put(35, i35);
        int i37 = i36 + 1;
        charToGsm.put(164, i36);
        int i38 = i37 + 1;
        charToGsm.put(37, i37);
        int i39 = i38 + 1;
        charToGsm.put(38, i38);
        int i40 = i39 + 1;
        charToGsm.put(39, i39);
        int i41 = i40 + 1;
        charToGsm.put(40, i40);
        int i42 = i41 + 1;
        charToGsm.put(41, i41);
        int i43 = i42 + 1;
        charToGsm.put(42, i42);
        int i44 = i43 + 1;
        charToGsm.put(43, i43);
        int i45 = i44 + 1;
        charToGsm.put(44, i44);
        int i46 = i45 + 1;
        charToGsm.put(45, i45);
        int i47 = i46 + 1;
        charToGsm.put(46, i46);
        int i48 = i47 + 1;
        charToGsm.put(47, i47);
        int i49 = i48 + 1;
        charToGsm.put(48, i48);
        int i50 = i49 + 1;
        charToGsm.put(49, i49);
        int i51 = i50 + 1;
        charToGsm.put(50, i50);
        int i52 = i51 + 1;
        charToGsm.put(51, i51);
        int i53 = i52 + 1;
        charToGsm.put(52, i52);
        int i54 = i53 + 1;
        charToGsm.put(53, i53);
        int i55 = i54 + 1;
        charToGsm.put(54, i54);
        int i56 = i55 + 1;
        charToGsm.put(55, i55);
        int i57 = i56 + 1;
        charToGsm.put(56, i56);
        int i58 = i57 + 1;
        charToGsm.put(57, i57);
        int i59 = i58 + 1;
        charToGsm.put(58, i58);
        int i60 = i59 + 1;
        charToGsm.put(59, i59);
        int i61 = i60 + 1;
        charToGsm.put(60, i60);
        int i62 = i61 + 1;
        charToGsm.put(61, i61);
        int i63 = i62 + 1;
        charToGsm.put(62, i62);
        int i64 = i63 + 1;
        charToGsm.put(63, i63);
        int i65 = i64 + 1;
        charToGsm.put(161, i64);
        int i66 = i65 + 1;
        charToGsm.put(65, i65);
        int i67 = i66 + 1;
        charToGsm.put(66, i66);
        int i68 = i67 + 1;
        charToGsm.put(67, i67);
        int i69 = i68 + 1;
        charToGsm.put(68, i68);
        int i70 = i69 + 1;
        charToGsm.put(69, i69);
        int i71 = i70 + 1;
        charToGsm.put(70, i70);
        int i72 = i71 + 1;
        charToGsm.put(71, i71);
        int i73 = i72 + 1;
        charToGsm.put(72, i72);
        int i74 = i73 + 1;
        charToGsm.put(73, i73);
        int i75 = i74 + 1;
        charToGsm.put(74, i74);
        int i76 = i75 + 1;
        charToGsm.put(75, i75);
        int i77 = i76 + 1;
        charToGsm.put(76, i76);
        int i78 = i77 + 1;
        charToGsm.put(77, i77);
        int i79 = i78 + 1;
        charToGsm.put(78, i78);
        int i80 = i79 + 1;
        charToGsm.put(79, i79);
        int i81 = i80 + 1;
        charToGsm.put(80, i80);
        int i82 = i81 + 1;
        charToGsm.put(81, i81);
        int i83 = i82 + 1;
        charToGsm.put(82, i82);
        int i84 = i83 + 1;
        charToGsm.put(83, i83);
        int i85 = i84 + 1;
        charToGsm.put(84, i84);
        int i86 = i85 + 1;
        charToGsm.put(85, i85);
        int i87 = i86 + 1;
        charToGsm.put(86, i86);
        int i88 = i87 + 1;
        charToGsm.put(87, i87);
        int i89 = i88 + 1;
        charToGsm.put(88, i88);
        int i90 = i89 + 1;
        charToGsm.put(89, i89);
        int i91 = i90 + 1;
        charToGsm.put(90, i90);
        int i92 = i91 + 1;
        charToGsm.put(196, i91);
        int i93 = i92 + 1;
        charToGsm.put(214, i92);
        int i94 = i93 + 1;
        charToGsm.put(209, i93);
        int i95 = i94 + 1;
        charToGsm.put(220, i94);
        int i96 = i95 + 1;
        charToGsm.put(167, i95);
        int i97 = i96 + 1;
        charToGsm.put(191, i96);
        int i98 = i97 + 1;
        charToGsm.put(97, i97);
        int i99 = i98 + 1;
        charToGsm.put(98, i98);
        int i100 = i99 + 1;
        charToGsm.put(99, i99);
        int i101 = i100 + 1;
        charToGsm.put(100, i100);
        int i102 = i101 + 1;
        charToGsm.put(101, i101);
        int i103 = i102 + 1;
        charToGsm.put(102, i102);
        int i104 = i103 + 1;
        charToGsm.put(103, i103);
        int i105 = i104 + 1;
        charToGsm.put(104, i104);
        int i106 = i105 + 1;
        charToGsm.put(105, i105);
        int i107 = i106 + 1;
        charToGsm.put(106, i106);
        int i108 = i107 + 1;
        charToGsm.put(107, i107);
        int i109 = i108 + 1;
        charToGsm.put(108, i108);
        int i110 = i109 + 1;
        charToGsm.put(109, i109);
        int i111 = i110 + 1;
        charToGsm.put(110, i110);
        int i112 = i111 + 1;
        charToGsm.put(111, i111);
        int i113 = i112 + 1;
        charToGsm.put(112, i112);
        int i114 = i113 + 1;
        charToGsm.put(113, i113);
        int i115 = i114 + 1;
        charToGsm.put(114, i114);
        int i116 = i115 + 1;
        charToGsm.put(115, i115);
        int i117 = i116 + 1;
        charToGsm.put(116, i116);
        int i118 = i117 + 1;
        charToGsm.put(117, i117);
        int i119 = i118 + 1;
        charToGsm.put(118, i118);
        int i120 = i119 + 1;
        charToGsm.put(119, i119);
        int i121 = i120 + 1;
        charToGsm.put(120, i120);
        int i122 = i121 + 1;
        charToGsm.put(121, i121);
        int i123 = i122 + 1;
        charToGsm.put(122, i122);
        int i124 = i123 + 1;
        charToGsm.put(228, i123);
        int i125 = i124 + 1;
        charToGsm.put(246, i124);
        int i126 = i125 + 1;
        charToGsm.put(241, i125);
        int i127 = i126 + 1;
        charToGsm.put(252, i126);
        int i128 = i127 + 1;
        charToGsm.put(224, i127);
        charToGsmExtended.put(12, 10);
        charToGsmExtended.put(94, 20);
        charToGsmExtended.put(123, 40);
        charToGsmExtended.put(125, 41);
        charToGsmExtended.put(92, 47);
        charToGsmExtended.put(91, 60);
        charToGsmExtended.put(126, 61);
        charToGsmExtended.put(93, 62);
        charToGsmExtended.put(124, 64);
        charToGsmExtended.put(8364, 101);
        int size = charToGsm.size();
        for (int j = 0; j < size; j++) {
            gsmToChar.put(charToGsm.valueAt(j), charToGsm.keyAt(j));
        }
        int size2 = charToGsmExtended.size();
        for (int j2 = 0; j2 < size2; j2++) {
            gsmExtendedToChar.put(charToGsmExtended.valueAt(j2), charToGsmExtended.keyAt(j2));
        }
        sGsmSpaceChar = charToGsm.get(32);
    }
}
