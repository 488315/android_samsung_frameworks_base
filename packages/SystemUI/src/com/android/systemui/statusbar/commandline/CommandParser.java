package com.android.systemui.statusbar.commandline;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CommandParser {
    public final List _flags;
    public final List _params;
    public final List _subCommands;
    public final List flags;
    public final List params;
    public final List subCommands;
    public final Set tokenSet;

    public CommandParser() {
        ArrayList arrayList = new ArrayList();
        this._flags = arrayList;
        this.flags = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this._params = arrayList2;
        this.params = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this._subCommands = arrayList3;
        this.subCommands = arrayList3;
        this.tokenSet = new LinkedHashSet();
    }

    public final String checkCliNames(String str, String str2) {
        if (str != null && this.tokenSet.contains(str)) {
            return str;
        }
        if (this.tokenSet.contains(str2)) {
            return str2;
        }
        return null;
    }

    public final List getUnhandledParams() {
        List list = this.params;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            Param param = (Param) obj;
            if ((param instanceof SingleArgParam) && !((SingleArgParam) param).handled) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final List getUnhandledSubCmds() {
        List list = this.subCommands;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            SubCommand subCommand = (SubCommand) obj;
            if ((subCommand instanceof RequiredSubCommand) && !((RequiredSubCommand) subCommand).handled) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final boolean parse(List list) {
        Object obj;
        Object obj2;
        boolean z;
        Object obj3;
        if (list.isEmpty()) {
            return validateRequiredParams();
        }
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            String str = (String) listIterator.next();
            ArrayList arrayList = (ArrayList) this.flags;
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (true) {
                obj = null;
                if (i2 >= size) {
                    obj2 = null;
                    break;
                }
                obj2 = arrayList.get(i2);
                i2++;
                if (((Flag) obj2).matches(str)) {
                    break;
                }
            }
            Flag flag = (Flag) obj2;
            boolean z2 = true;
            if (flag != null) {
                flag.inner = true;
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                ArrayList arrayList2 = (ArrayList) this.params;
                int size2 = arrayList2.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size2) {
                        obj3 = null;
                        break;
                    }
                    obj3 = arrayList2.get(i3);
                    i3++;
                    if (((Param) obj3).matches(str)) {
                        break;
                    }
                }
                Param param = (Param) obj3;
                if (param != null) {
                    param.parseArgsFromIter(listIterator);
                    z = true;
                }
                if (z) {
                    continue;
                } else {
                    ArrayList arrayList3 = (ArrayList) this.subCommands;
                    int size3 = arrayList3.size();
                    while (true) {
                        if (i >= size3) {
                            break;
                        }
                        Object obj4 = arrayList3.get(i);
                        i++;
                        if (((SubCommand) obj4).matches(str)) {
                            obj = obj4;
                            break;
                        }
                    }
                    SubCommand subCommand = (SubCommand) obj;
                    if (subCommand != null) {
                        subCommand.parseSubCommandArgs(listIterator);
                    } else {
                        z2 = z;
                    }
                    if (!z2) {
                        throw new ArgParseError(AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Unknown token: ", str));
                    }
                }
            }
        }
        return validateRequiredParams();
    }

    public final boolean parseAsSubCommand(ListIterator listIterator) {
        Object obj;
        Object obj2;
        boolean z;
        if (((ArrayList) this.flags).isEmpty() && ((ArrayList) this.params).isEmpty()) {
            return validateRequiredParams();
        }
        while (true) {
            if (!listIterator.hasNext()) {
                break;
            }
            String str = (String) listIterator.next();
            ArrayList arrayList = (ArrayList) this.flags;
            int size = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (true) {
                obj = null;
                if (i2 >= size) {
                    obj2 = null;
                    break;
                }
                obj2 = arrayList.get(i2);
                i2++;
                if (((Flag) obj2).matches(str)) {
                    break;
                }
            }
            Flag flag = (Flag) obj2;
            boolean z2 = true;
            if (flag != null) {
                flag.inner = true;
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                ArrayList arrayList2 = (ArrayList) this.params;
                int size2 = arrayList2.size();
                while (true) {
                    if (i >= size2) {
                        break;
                    }
                    Object obj3 = arrayList2.get(i);
                    i++;
                    if (((Param) obj3).matches(str)) {
                        obj = obj3;
                        break;
                    }
                }
                Param param = (Param) obj;
                if (param != null) {
                    param.parseArgsFromIter(listIterator);
                } else {
                    z2 = z;
                }
                if (!z2) {
                    listIterator.previous();
                    break;
                }
            }
        }
        return validateRequiredParams();
    }

    public final boolean validateRequiredParams() {
        if (((ArrayList) getUnhandledParams()).isEmpty() && ((ArrayList) getUnhandledSubCmds()).isEmpty()) {
            List list = this.subCommands;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                if (!((SubCommand) obj).getValidationStatus()) {
                    arrayList.add(obj);
                }
            }
            if (arrayList.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
