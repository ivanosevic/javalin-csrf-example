package edu.pucmm.eict.javalincsrf.security.csrf.plugin;

import java.util.List;

public class CsrfPluginConfig {
    private final List<String> safeMethods;
    private final List<String> stateChangingMethods;
    private final CsrfReaderType csrfReaderType;

    /**
     * Default constructor for CsrfPluginConfiguration. It's the configuration by default and has the following:
     * Allowed methods are: GET, OPTIONS and HEAD
     */
    public CsrfPluginConfig() {
        this.csrfReaderType = CsrfReaderType.FORM_PARAM;
        this.safeMethods = List.of("GET", "OPTIONS", "HEAD");
        this.stateChangingMethods = List.of("POST", "DELETE", "PUT", "PATCH");
    }

    public List<String> getSafeMethods() {
        return safeMethods;
    }

    public List<String> getStateChangingMethods() {
        return stateChangingMethods;
    }

    public CsrfReaderType getCsrfReaderType() {
        return csrfReaderType;
    }
}
