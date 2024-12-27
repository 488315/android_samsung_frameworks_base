package com.android.systemui.statusbar.commandline;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

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
        for (Object obj : list) {
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
        for (Object obj : list) {
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
            return false;
        }
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            String str = (String) listIterator.next();
            Iterator it = ((ArrayList) this.flags).iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    obj2 = null;
                    break;
                }
                obj2 = it.next();
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
                Iterator it2 = ((ArrayList) this.params).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj3 = null;
                        break;
                    }
                    obj3 = it2.next();
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
                    Iterator it3 = ((ArrayList) this.subCommands).iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        Object next = it3.next();
                        if (((SubCommand) next).matches(str)) {
                            obj = next;
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
            Iterator it = ((ArrayList) this.flags).iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    obj2 = null;
                    break;
                }
                obj2 = it.next();
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
                Iterator it2 = ((ArrayList) this.params).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Object next = it2.next();
                    if (((Param) next).matches(str)) {
                        obj = next;
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
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (true ^ ((SubCommand) next).getValidationStatus()) {
                    arrayList.add(next);
                }
            }
            if (arrayList.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
