package edu.pucmm.eict.javalincsrf.security.csrf.plugin;

import edu.pucmm.eict.javalincsrf.security.csrf.CsrfReader;
import edu.pucmm.eict.javalincsrf.security.csrf.FormCsrfReader;
import edu.pucmm.eict.javalincsrf.security.csrf.HeaderCsrfFilter;

public class CsrfReaderFactory {

    public static CsrfReader get(CsrfReaderType type) {
        if(type.equals(CsrfReaderType.FORM_PARAM)) {
            return new FormCsrfReader();
        }

        if(type.equals(CsrfReaderType.HEADER)) {
            return new HeaderCsrfFilter();
        }
        return null;
    }
}
