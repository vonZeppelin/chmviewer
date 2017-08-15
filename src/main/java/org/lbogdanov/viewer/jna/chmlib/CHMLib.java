package org.lbogdanov.viewer.jna.chmlib;

import static java.util.Collections.singletonMap;

import java.util.Set;

import org.lbogdanov.viewer.jna.JNAEnum;
import org.lbogdanov.viewer.jna.Mapper;

import com.sun.jna.*;

public interface CHMLib extends Library {
    enum Param implements JNAEnum {
        MAX_BLOCKS_CACHED;
    }

    enum Resolve implements JNAEnum {
        SUCCESS, FAILURE;
    }

    enum Enumerate implements JNAEnum {
        NORMAL(1), META(2), SPECIAL(4), FILES(8), DIRS(16), ALL(31);

        private final int value;

        @Override
        public int toInt() {
            return value;
        }

        Enumerate(int value) {
            this.value = value;
        }
    }

    enum Enumerator implements JNAEnum {
        FAILURE, CONTINUE, SUCCESS;
    }

    public class CHMFile extends PointerType {}

    interface CHMEnumerator extends Callback {
        Enumerator invoke(CHMFile h, CHMUnitInfo ui, Pointer context);
    }

    CHMLib instance = (CHMLib) Native.loadLibrary("chm", CHMLib.class, singletonMap(OPTION_TYPE_MAPPER, new Mapper()));

    CHMFile chm_open(String filename);

    void chm_close(CHMFile h);

    void chm_set_param(CHMFile h, Param paramType, int paramVal);

    Resolve chm_resolve_object(CHMFile h, String objPath, CHMUnitInfo ui);

    long chm_retrieve_object(CHMFile h, CHMUnitInfo ui, Memory buf, long addr, long len);

    int chm_enumerate(CHMFile h, Set<Enumerate> what, CHMEnumerator e, Pointer context);

    int chm_enumerate_dir(CHMFile h, String prefix, Set<Enumerate> what, CHMEnumerator e, Pointer context);
}
