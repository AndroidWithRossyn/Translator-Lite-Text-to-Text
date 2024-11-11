package com.test2.test2;

public class Languages {

    String code;
    String lang;
    String urlImag;

    public Languages() {
    }

    public Languages(String code, String lang, String urlImag) {
        this.code = code;
        this.lang = lang;
        this.urlImag = urlImag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUrlImag() {
        return urlImag;
    }

    public void setUrlImag(String urlImag) {
        this.urlImag = urlImag;
    }
}
